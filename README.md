# ArchitectureMVVM framework

> :warning: *UNDER CONSTRUCTION*

MVVM architecture abstract layer in kotlin for android, with koin and architecture components.

Goals:

1. Unify view types(activity, fragment, dialog) with the same api. Change parent class must morph the view type without any additional changes
2. Automatically react to async logic on UI with `PoweView` and `BaseViewModel.launch()`
3. Exclude native segregation of views when jetpack navigation lib will be stable

```groovy
    implementation "com.link184:common-mvvm:0.9.1-SNAPSHOT"
```
