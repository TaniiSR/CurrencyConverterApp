//    Kotlin
object KotlinDependencies {
    const val KOTLIN_REFLECTION =
        "org.jetbrains.kotlin:kotlin-reflect:${KotlinVersions.STANDARD_LIBRARY}"
    const val KOTLIN_STD_LIB =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersions.STANDARD_LIBRARY}"

    // kotlin coroutine
    const val COROUTINE_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${KotlinVersions.COROUTINE_VERSION}"
    const val COROUTINE_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${KotlinVersions.COROUTINE_VERSION}"
}

//LifeCycle_KTX
object LifeCycleKtxDependencies {
    const val CORE_KTX = "androidx.core:core-ktx:${LifeCycle_KTX.CORE_KTX}"
    const val Collection_KTX =
        "androidx.collection:collection-ktx:${LifeCycle_KTX.Collection_KTX}"
    const val VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${LifeCycle_KTX.LIFECYCLE}"
    const val VIEW_MODEL_SAVE_STATE_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${LifeCycle_KTX.LIFECYCLE}"
    const val LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${LifeCycle_KTX.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME =
        "androidx.lifecycle:lifecycle-runtime:${LifeCycle_KTX.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${LifeCycle_KTX.LIFECYCLE}"
    const val LIFECYCLE_COMMON_JAVA =
        "androidx.lifecycle:lifecycle-common-java8:${LifeCycle_KTX.LIFECYCLE}"
    const val REACTIVE_STREAMS =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${LifeCycle_KTX.LIFECYCLE}"
}

// Androidx Architecture
// Androidx

object AndroidxDependencies {
    const val APPCOMPAT = "androidx.appcompat:appcompat:${AndroidXVersions.APPCOMPAT}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${AndroidXVersions.FRAGMENT_KTX}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${AndroidXVersions.ACTIVITY_KTX}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${AndroidXVersions.CONSTRAINT_LAYOUT}"
    const val CARD_VIEW = "androidx.cardview:cardview:${AndroidXVersions.CARD_VIEW}"
    const val RECYCLERVIEW =
        "androidx.recyclerview:recyclerview:${AndroidXVersions.RECYCLERVIEW}"
    const val VECTOR_DRAWABLE =
        "androidx.vectordrawable:vectordrawable:${AndroidXVersions.VECTOR_DRAWABLE}"
    const val VECTOR_DRAWABLE_ANIMATED =
        "androidx.vectordrawable:vectordrawable-animated:${AndroidXVersions.VECTOR_DRAWABLE}"
    const val VIEWPAGER2 = "androidx.viewpager2:viewpager2:${AndroidXVersions.VIEWPAGER2}"
    const val MATERIAL = "com.google.android.material:material:${AndroidXVersions.MATERIAL}"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${AndroidXVersions.PAGING}"
    const val SWIPE = "androidx.swiperefreshlayout:swiperefreshlayout:${AndroidXVersions.SWIPE}"
    const val CRYPT = "androidx.security:security-crypto:${AndroidXVersions.CRYPT}"
    const val RECYCLERVIEW_SELECTION =
        "androidx.recyclerview:recyclerview-selection:${AndroidXVersions.RECYCLERVIEW_SELECTION}"
    const val WORK_MANAGER = "androidx.work:work-runtime-ktx:${AndroidXVersions.WORK_MANAGER}"
    const val WORK_MANAGER_HILT = "androidx.hilt:hilt-work:${HiltDaggerVersion.HILT_VM}"
}

object NavigationDependencies {
    //    Navigation KTX
    const val NAVIGATION_RUNTIME =
        "androidx.navigation:navigation-runtime-ktx:${BuildPluginsVersions.NAVIGATION}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${BuildPluginsVersions.NAVIGATION}"
    const val NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${BuildPluginsVersions.NAVIGATION}"
}

// Retrofit2 & Networking
object NetworkDependencies {
    const val GSON = "com.google.code.gson:gson:${NetworkVersions.GSON}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${NetworkVersions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${NetworkVersions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${NetworkVersions.OKHTTP}"
    const val RETROFIT_CONVERTOR_GSON =
        "com.squareup.retrofit2:converter-gson:${NetworkVersions.RETROFIT}"

    //Glide Image Loading
    const val GLIDE = "com.github.bumptech.glide:glide:${NetworkVersions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${NetworkVersions.GLIDE}"
}

object RoomDependencies {
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${AndroidXVersions.ROOM_VERSION}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${AndroidXVersions.ROOM_VERSION}"
    const val ROOM_KTX = "androidx.room:room-ktx:${AndroidXVersions.ROOM_VERSION}"
}

object HiltDaggerDependencies {
    // Hilt Dagger DI
    const val DAGGER_HILT = "com.google.dagger:hilt-android:${HiltDaggerVersion.HILT_DI}"
    const val DAGGER_COMPILER =
        "com.google.dagger:hilt-android-compiler:${HiltDaggerVersion.HILT_DI}"
    const val HILT_VM = "androidx.hilt:hilt-lifecycle-viewmodel:${HiltDaggerVersion.HILT_VM}"
    const val HILT_FRAGMENT = "androidx.hilt:hilt-navigation-fragment:${HiltDaggerVersion.HILT_VM}"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${HiltDaggerVersion.HILT_VM}"
}

//ThirdParty
object ThirdPartyDependencies {
    const val LOTTIE = "com.airbnb.android:lottie:${ThirdPartyVersions.LOTTIE}"
    const val SHIMMER = "com.facebook.shimmer:shimmer:${ThirdPartyVersions.SHIMMER}"
}

object TestDependencies {
    const val JUNIT4 = "junit:junit:${TestDependenciesVersions.JUNIT4}"
    const val MOCKK = "io.mockk:mockk:${TestDependenciesVersions.MOCKK}"
    const val ROBOLECTRIC = "org.robolectric:robolectric:${TestDependenciesVersions.ROBOLECTRIC}"
    const val TESTRUNNER = "androidx.test:runner:${TestDependenciesVersions.TESTRUNNER}"
    const val TESTRULES = "androidx.test:rules:${TestDependenciesVersions.TESTRULES}"
    const val ESPRESSOCORE =
        "androidx.test.espresso:espresso-core:${TestDependenciesVersions.ESPRESSOCORE}"
    const val ESPRESSOINTENTS =
        "androidx.test.espresso:espresso-intents:${TestDependenciesVersions.ESPRESSOINTENTS}"
    const val TESTEXTENSIONS = "androidx.test.ext:junit:${TestDependenciesVersions.TESTEXTENSIONS}"
    const val HILTTESTING =
        "com.google.dagger:hilt-android-testing:${TestDependenciesVersions.HILTTESTING}"
    const val MOCKITO_INLINE =
        "org.mockito:mockito-inline:${TestDependenciesVersions.MOCKITO_INLINE}"
    const val MOCKITO_KOTLIN =
        "org.mockito.kotlin:mockito-kotlin:${TestDependenciesVersions.MOCKITO_KOTLIN}"
    const val JUPITER =
        "org.junit.jupiter:junit-jupiter-params:${TestDependenciesVersions.JUPITER}"
    const val JUPITER_ENGINE =
        "org.junit.jupiter:junit-jupiter-engine:${TestDependenciesVersions.JUPITER}"
    const val MOCK_JSON =
        "org.json:json:${TestDependenciesVersions.MOCK_JSON}"
    const val MOCK_WEB_SERVER =
        "com.squareup.okhttp3:mockwebserver:${TestDependenciesVersions.MOCK_WEB_SERVER}"

    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestDependenciesVersions.COROUTINES}"

    const val ANDROIDX_ARCH_CORE =
        "androidx.arch.core:core-testing:${TestDependenciesVersions.ANDROIDX_ARCH_CORE}"

}