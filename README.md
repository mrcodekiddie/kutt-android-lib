# kutt-android-lib
Android Library for Kutt.it API calls.

### For adding this Library In Your Project

* #### add these line to  ***repositories*** block of project level build.gradle

` maven { url "https://jitpack.io" } `

Your code must look like this

```gradle

buildscript {
    repositories {
        google()
        jcenter()
         maven { url "https://jitpack.io" }
    }
```

* #### Now Add these to ***dependencies*** of app level build.gradle (The one inside the app directory)

` implementation 'com.github.mrcodekiddie:kutt-android-lib:0.0.1`

your code must look like this

``` gradle

dependencies {
        implementation 'com.github.mrcodekiddie:kutt-android-lib:0.0.1'
   }
   ```
