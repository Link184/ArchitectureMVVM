# Kotlin multiplatform ArchitectureMVVM framework

> :warning: *UNDER CONSTRUCTION*

MVVM architecture abstract layer in kotlin for android, with koin and architecture components.

Goals:

1. Unify view types(activity, fragment, dialog) with the same api. Change parent class must morph the view type without any additional changes
2. Automatically react to async logic on UI with `PoweView` and `BaseViewModel.launch()`
3. Exclude native segregation of views when jetpack navigation lib will be stable

```groovy
    // kotlin multiplatform common
    implementation "com.link184:common-mvvm:1.0.3-SNAPSHOT"
    
    // kotlin multiplatform android
    implementation "com.link184:common-mvvm-android:1.0.3-SNAPSHOT"
    
    // kotlin multiplatform nodeJs
    implementation "com.link184:common-mvvm-nodeJs:1.0.3-SNAPSHOT"
```
