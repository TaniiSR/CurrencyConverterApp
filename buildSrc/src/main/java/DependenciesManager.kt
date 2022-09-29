import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.initialization.dsl.ScriptHandler.CLASSPATH_CONFIGURATION

object DependenciesManager {
    val kotlinImplementation = arrayListOf<String>().apply {
        add(KotlinDependencies.KOTLIN_REFLECTION)
        add(KotlinDependencies.KOTLIN_STD_LIB)
        add(KotlinDependencies.COROUTINE_CORE)
        add(KotlinDependencies.COROUTINE_ANDROID)
    }

    val networkImplementation = arrayListOf<String>().apply {
        add(NetworkDependencies.GSON)
        add(NetworkDependencies.GLIDE)
        add(NetworkDependencies.RETROFIT)
        add(NetworkDependencies.OKHTTP)
        add(NetworkDependencies.OKHTTP_LOGGING_INTERCEPTOR)
        add(NetworkDependencies.RETROFIT_CONVERTOR_GSON)
    }

    val roomImplementation = arrayListOf<String>().apply {
        add(RoomDependencies.ROOM_RUNTIME)
        add(RoomDependencies.ROOM_KTX)
    }

    val lifeCycleKtxImplementation = arrayListOf<String>().apply {
        add(LifeCycleKtxDependencies.CORE_KTX)
        add(LifeCycleKtxDependencies.Collection_KTX)
        add(LifeCycleKtxDependencies.VIEW_MODEL_KTX)
        add(LifeCycleKtxDependencies.VIEW_MODEL_SAVE_STATE_KTX)
        add(LifeCycleKtxDependencies.LIVEDATA_KTX)
        add(LifeCycleKtxDependencies.LIFECYCLE_RUNTIME)
        add(LifeCycleKtxDependencies.LIFECYCLE_RUNTIME_KTX)
        add(LifeCycleKtxDependencies.LIFECYCLE_COMMON_JAVA)
        add(LifeCycleKtxDependencies.REACTIVE_STREAMS)
    }
    val androidxImplementation = arrayListOf<String>().apply {
        add(AndroidxDependencies.APPCOMPAT)
        add(AndroidxDependencies.FRAGMENT_KTX)
        add(AndroidxDependencies.ACTIVITY_KTX)
        add(AndroidxDependencies.CONSTRAINT_LAYOUT)
        add(AndroidxDependencies.CARD_VIEW)
        add(AndroidxDependencies.RECYCLERVIEW)
        add(AndroidxDependencies.VECTOR_DRAWABLE)
        add(AndroidxDependencies.VECTOR_DRAWABLE_ANIMATED)
        add(AndroidxDependencies.VIEWPAGER2)
        add(AndroidxDependencies.MATERIAL)
        add(AndroidxDependencies.PAGING_RUNTIME)
        add(AndroidxDependencies.SWIPE)
        add(AndroidxDependencies.CRYPT)
        add(AndroidxDependencies.RECYCLERVIEW_SELECTION)
        add(AndroidxDependencies.WORK_MANAGER)
        add(AndroidxDependencies.WORK_MANAGER_HILT)

    }
    val navigationImplementation = arrayListOf<String>().apply {
        add(NavigationDependencies.NAVIGATION_RUNTIME)
        add(NavigationDependencies.NAVIGATION_FRAGMENT_KTX)
        add(NavigationDependencies.NAVIGATION_UI_KTX)
    }

    val hiltImplementation = arrayListOf<String>().apply {
        add(HiltDaggerDependencies.DAGGER_HILT)
        add(HiltDaggerDependencies.HILT_FRAGMENT)
    }
    val hiltKapt = arrayListOf<String>().apply {
        add(HiltDaggerDependencies.DAGGER_COMPILER)
    }

    val thirdPartyImplementation = arrayListOf<String>().apply {
        add(ThirdPartyDependencies.LOTTIE)
        add(ThirdPartyDependencies.SHIMMER)
    }

    val testingImplementation = arrayListOf<String>().apply {
        add(TestDependencies.JUNIT4)
        add(TestDependencies.MOCKK)
        add(TestDependencies.ROBOLECTRIC)
        add(TestDependencies.JUPITER)
        add(TestDependencies.JUPITER_ENGINE)
        add(TestDependencies.MOCK_JSON)
        add(TestDependencies.MOCK_WEB_SERVER)
        add(TestDependencies.MOCKITO_INLINE)
        add(TestDependencies.MOCKITO_KOTLIN)

    }

    val androidTestImplementation = arrayListOf<String>().apply {
        add(TestDependencies.TESTEXTENSIONS)
        add(TestDependencies.ESPRESSOCORE)
        add(TestDependencies.TESTRUNNER)
        add(TestDependencies.TESTRULES)
        add(TestDependencies.ESPRESSOINTENTS)
        add(TestDependencies.HILTTESTING)

    }
}

//util functions for adding the different type dependencies from build.gradle file

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.classpath(list: List<String>) {
    list.forEach { classPath ->
        this.add(CLASSPATH_CONFIGURATION, classPath)
    }
}
