# Simple Weather app

Simple Android Weather app that I use as a reference for new Android projects. It demonstrates the architecture, tools and guidelines that I use when developing for the Android platform.
This weather app was built using a clean architecture with MVVM for the presentation layer.

# Goal

Screen shows current conditions, temperature, wind speed and direction for your current location.
The weather information is cached for future offline use.
If i am offline and the weather that is cached less than 24 hours old then this weather should be displayed with the date that was last updated.

Libraries and tools included:

- Support libraries
- CardViews 
- AndroidX
- Android Architecture components (LiveData and ViewModel)
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Timber](https://github.com/JakeWharton/timber)

Testing Libraries

- [jUnit](http://junit.org/junit5/)
- [Mockito](https://github.com/mockito/mockito)
