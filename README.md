# Network Sdk


A lightweight, dependency-free library for effortless HTTP GET requests. With support for queries, paths, Network Sdk streamlines your HTTP interactions without the need for third-party frameworks like Retrofit or Volley.


![imresizer-1711939060575](https://github.com/shaykhshaya/NetworkRequestGET/assets/119520622/92decca0-c512-4893-8b40-955304b2e5e4)


## Usage:

**A. Gradle Dependency**:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
```kotlin
dependencies {
     implementation("com.github.shaykhshaya:NetworkRequestGET:1.1")
}
```

**B. The Configuration is created using the Builder Pattern *in Kotlin***:


```kotlin
NetworkBuilder.Build()
            .baseUrl(Constants.BASE_URL)
            .endPoint(Constants.END_POINT)
            .path(Constants.PATH")
            .query(hashMapOf(
                    KEY_QUERY_PAGE to PAGE_NUMBER,
                    KEY_QUERY_KEY to API_KEY
                )
            )
            .create()
```
```kotlin
val response = NetworkCall<[RESPONSE_TYPE]>(
            responseType = RESPONSE_TYPE::class.java
        ) { data, message ->
            lifecycleScope.launch(Dispatchers.Main) {
                //get the response here...
            }
        }

```
```kotlin
lifecycleScope.launch {
            response.get()
        }
```






