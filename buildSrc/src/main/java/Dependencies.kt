object Versions {
    const val compile_sdk = 28
    const val min_sdk = 21
    const val target_sdk = 28

    val app_version_major = 1
    val app_version_minor = 0
    val app_version_fix = 0

    val app_version_name = "$app_version_major.$app_version_minor.$app_version_fix"
    val app_version_code = app_version_major * 1000000 + app_version_minor * 10000 + app_version_fix * 100

    const val gradle_plugin = "3.4.0"
    const val kotlin = "1.3.31"

    const val androidx = "1.0.0"
    const val androidx_multidex = "2.0.0"
    const val androidx_constraint_layout = "1.1.3"
    const val material_design = "1.1.0-alpha05"
    const val arch_components = "2.0.0"
    const val arch_nav = "2.1.0-alpha05"
    const val room = "2.1.0-rc01"

    const val retrofit = "2.5.0"
    const val okhttp = "3.14.2"
    const val rxjava = "2.2.9"
    const val rxandroid = "2.1.1"
    const val gson = "2.8.2"
    const val dagger = "2.21"
    const val glide = "4.9.0"

    const val play_services = "15.0.1"

    const val joda = "2.10.2"
    const val groupie = "2.3.0"

    // Logging
    const val crashlytics = "2.9.1@aar"
    const val fabric = "1.27.0"
    const val timber = "4.7.0"

    // TEST
    const val junit = "4.12"
    const val mockito = "2.22.0"
    const val mockito_kotlin = "1.5.0"
    const val espresso = "3.2.0"
    const val support_test_runner = "1.1.0"
    const val live_data_test = "1.1.0"
    const val androidx_test_core = "1.2.0"
    const val androidx_test_runner = "1.2.0"
    const val androidx_test_rules = "1.2.0"
    const val androidx_junit = "1.1.1"
    const val androidx_truth = "1.2.0"
    const val fragment_test = "1.1.0-alpha05"
}

object Dependencies {
    const val gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_plugin}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //support
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
    const val androidx_core = "androidx.core:core:${Versions.androidx}"
    const val androidx_card_view = "androidx.cardview:cardview:${Versions.androidx}"
    const val androidx_constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraint_layout}"
    const val androidx_multidex = "androidx.multidex:multidex:${Versions.androidx_multidex}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"

    //Architecture components
    const val androidx_arch_viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.arch_components}"
    const val androidx_arch_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.arch_components}"
    const val androidx_arch_liveData = "androidx.lifecycle:lifecycle-livedata:${Versions.arch_components}"
    const val androidx_arch_reactive_streams = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.arch_components}"
    const val androidx_arch_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.arch_components}"
    const val androidx_arch_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.arch_components}"
    const val androidx_arch_testing =  "androidx.arch.core:core-testing:${Versions.arch_components}"

    //Room
    const val androidx_arch_room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val androidx_arch_room_compiler ="androidx.room:room-compiler:${Versions.room}"
    const val androidx_arch_room_rxjava = "androidx.room:room-rxjava2:${Versions.room}"
    const val androidx_arch_room_testing =  "androidx.room:room-testing:${Versions.arch_components}"

    //Navigation
    const val androidx_nav_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.arch_nav}"
    const val androidx_nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.arch_nav}"
    const val androidx_nav_safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.arch_nav}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofit_gson_coverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    //okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    //rxjava
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxrelay = "com.jakewharton.rxrelay2:rxrelay:2.1.0"
    const val rxSharedPrefernces = "com.f2prateek.rx.preferences2:rx-preferences:2.0.0"
    const val rxPermissions = "com.github.tbruyelle:rxpermissions:0.10.2"

    //player services
    const val play_services_location = "com.google.android.gms:play-services-location:${Versions.play_services}"

    //dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glide_okhttp_integration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}@aar"

    //others
    const val joda = "joda-time:joda-time:${Versions.joda}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val javaxAnnotation = "javax.annotation:javax.annotation-api:1.2"
    const val javaxInject = "javax.inject:javax.inject:1"

    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupie_extensions = "com.xwray:groupie-kotlin-android-extensions:${Versions.groupie}"

    // Logging
    const val fabric_plugin = "io.fabric.tools:gradle:${Versions.fabric}"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // TEST
    const val junit = "junit:junit:${Versions.junit}"

    const val kotlin_test_runtime = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlin_test_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val kotlin_test_annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
    const val kotlin_test_junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

    const val mock_webserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_android = "org.mockito:mockito-android:${Versions.mockito}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockito_kotlin = "com.nhaarman:mockito-kotlin:${Versions.mockito_kotlin}"

    const val androidx_test_runner = "androidx.test:runner:${Versions.androidx_test_runner}"
    const val androidx_test_rules = "androidx.test:rules:${Versions.androidx_test_rules}"
    const val androidx_test_core = "androidx.test:core:${Versions.androidx_test_core}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val androidx_truth = "androidx.test.ext:truth:${Versions.androidx_truth}"
    const val androidx_espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val androidx_espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val androidx_fragment_testing = "androidx.fragment:fragment-testing:${Versions.fragment_test}"

    const val live_data_test = "com.jraska.livedata:testing-ktx:${Versions.live_data_test}"

    const val support_test_runner = "androidx.test.ext:junit:1.0.0"
    const val support_test_rules = "androidx.test:rules:${Versions.support_test_runner}"
}