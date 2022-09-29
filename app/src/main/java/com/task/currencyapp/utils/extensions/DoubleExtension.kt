package com.task.currencyapp.utils.extensions

import java.text.DecimalFormat


fun Double.roundToThreeDeci(): Double {
    return DecimalFormat("########0.000").format(this).toDouble()
}