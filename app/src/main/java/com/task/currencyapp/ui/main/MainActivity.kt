package com.task.currencyapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.work.*
import com.task.currencyapp.R
import com.task.currencyapp.databinding.ActivityMainBinding
import com.task.currencyapp.domain.datadtos.Currency
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.domain.schedulers.SyncRatesWorker
import com.task.currencyapp.utils.SYNC_RATES_INTERVAL
import com.task.currencyapp.utils.SYNC_RATES_WORKER_TAG
import com.task.currencyapp.utils.WORKER_NAME
import com.task.currencyapp.utils.base.BaseActivity
import com.task.currencyapp.utils.base.interfaces.OnItemClickListener
import com.task.currencyapp.utils.base.sealed.UIEvent
import com.task.currencyapp.utils.extensions.gone
import com.task.currencyapp.utils.extensions.observe
import com.task.currencyapp.utils.extensions.toast
import com.task.currencyapp.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, IMain>() {

    override val viewModel: IMain by viewModels<MainVM>()
    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
        setUpRecyclerView()
        setTextChangeListener()
        viewModel.dataRequestCurrenciesAndRates()
        createPeriodicWorkRequest()
        mViewBinding.errorView.btnRetry.setOnClickListener(this)
        mViewBinding.currencySpinner.onItemSelectedListener =
            currenciesSpinnerItemListener
    }

    private fun setTextChangeListener() {
        mViewBinding.etAmount.doAfterTextChanged {
            viewModel.rateListAdapter.updateRateListItems(
                viewModel.getExchangeRateListForSelectedCurrency(
                    viewModel.exchangeRates.value?.rateList,
                    (it?.toString() ?: "0").toDoubleOrNull(),
                    viewModel.selectedCurrency?.currencyCode,
                    viewModel.exchangeRates.value?.base ?: "USD"
                )
            )
        }
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnRetry -> viewModel.dataRequestCurrenciesAndRates()
        }
    }

    private fun setUpRecyclerView() {
        viewModel.rateListAdapter.onItemClickListener = rvItemClickListener
        mViewBinding.rvRates.adapter = viewModel.rateListAdapter
    }

    private val rvItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is ExchangeRate -> {
                    toast(viewModel.supportedCurrencies.value?.get(pos)?.currencyName ?: "")
                }
            }
        }
    }

    private fun handCurrencies(currencyList: List<Currency>) {
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            currencyList.map { currency -> currency.currencyCode } as ArrayList<String>
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mViewBinding.currencySpinner.adapter = dataAdapter
    }

    private val currenciesSpinnerItemListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectedCurrency = viewModel.supportedCurrencies.value?.get(position)
            viewModel.rateListAdapter.updateRateListItems(
                viewModel.getExchangeRateListForSelectedCurrency(
                    viewModel.exchangeRates.value?.rateList,
                    (mViewBinding.etAmount.text?.toString() ?: "0").toDoubleOrNull(),
                    viewModel.selectedCurrency?.currencyCode,
                    viewModel.exchangeRates.value?.base ?: "USD"
                )
            )
        }

        override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    }

    private fun handleCurrencyUiState(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.Loading -> setLoadingView()
            is UIEvent.Success -> setSuccessView()
            is UIEvent.Error -> setErrorView()
            is UIEvent.Message -> showToast(uiEvent.message)
        }
    }

    private fun handleRateUiState(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.Loading -> setRateLoadingView()
            is UIEvent.Success -> setRateSuccessView()
            is UIEvent.Error -> setErrorView()
            is UIEvent.Message -> showToast(uiEvent.message)
        }
    }

    private fun showRateShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.visible()
        mViewBinding.shimmerLayout.shimmerFrameLayout.startShimmer()

    }

    private fun hideRateShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.gone()
        mViewBinding.shimmerLayout.shimmerFrameLayout.stopShimmer()
    }


    private fun setRateSuccessView() {
        mViewBinding.rvRates.visible()
        mViewBinding.errorView.errorView.gone()
        hideRateShimmerLoading()
    }

    private fun setRateLoadingView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.rvRates.gone()
        showRateShimmerLoading()
    }

    private fun setErrorView() {
        mViewBinding.errorView.errorView.visible()
        mViewBinding.clMain.gone()
        hideCurrencyShimmerLoading()
        hideRateShimmerLoading()
    }


    private fun setLoadingView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.clMain.gone()
        showCurrencyShimmerLoading()
    }

    private fun setSuccessView() {
        mViewBinding.clMain.visible()
        mViewBinding.errorView.errorView.gone()
        hideCurrencyShimmerLoading()
    }

    private fun showCurrencyShimmerLoading() {
        mViewBinding.shimmerCurrency.shimmerFrameLayout.visible()
        mViewBinding.shimmerCurrency.shimmerFrameLayout.startShimmer()

    }

    private fun hideCurrencyShimmerLoading() {
        mViewBinding.shimmerCurrency.shimmerFrameLayout.gone()
        mViewBinding.shimmerCurrency.shimmerFrameLayout.stopShimmer()
    }

    private fun viewModelObservers() {
        observe(viewModel.supportedCurrencies, ::handCurrencies)
        observe(viewModel.uiCurrencyState, ::handleCurrencyUiState)
        observe(viewModel.uiRateState, ::handleRateUiState)
    }

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun createPeriodicWorkRequest() {
        val workManager = WorkManager.getInstance(this)

        val syncRatesWorker = PeriodicWorkRequestBuilder<SyncRatesWorker>(
            SYNC_RATES_INTERVAL, TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag(SYNC_RATES_WORKER_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRatesWorker
        )
    }

}