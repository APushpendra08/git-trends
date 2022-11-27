# git-trends
Trending Github Repositories in Android App

## Note
This App uses external API for getting the data.
API Link - https://rapidapi.com/targaryen.akane/api/github-trending

Generate personal API key using above link and store it in Secret.kt(Singleton) as

const val API_KEY = "<_GENERATED_API_KEY>"


## App Info

#### Features
- See trending repositories of Github
- Filter based on Author, Project Name, Description or Language

#### Architecture
- Pattern Followed : MVVM

#### App components
- API Service for fetching data
- Viewmodel to persist and handle state of UI
- UI representing the Trending repositories

#### Project Structure
Package(com.pandemonium.gittrends)
* **service**
  * models
    * BuiltBy.kt
    * ReposItem.kt
  * network
    * ApiInterface.kt
    * RetrofitInstance.kt
* **utils**
  * Constants.kt
  * Secret.kt(**Need to be added manually with generated API key**)
* **views**
  * adapters
    * TrendRepoAdapter.kt
  * viewholders
    * TrendReposViewHolder.kt
  * viewmodels
    * MainViewModel.kt
  * MainActivity.kt

#### Components used
- MVVM Architecture
- Jetpack Library components - LiveData, Lifecycle aware components, Viewmodel
- Coroutines for multi-threading
- Retrofit for fetching API 

#### Images
![image](https://user-images.githubusercontent.com/34448135/204150944-b5e6f5ce-9595-419f-b97f-f75bd8c7940a.png)
![image](https://user-images.githubusercontent.com/34448135/204150957-cd930f06-49ff-44ba-aaa5-c8ea493a9bb8.png)
![image](https://user-images.githubusercontent.com/34448135/204150969-e64fed72-8bf2-4a45-a94e-e2f04cb90d10.png)
![image](https://user-images.githubusercontent.com/34448135/204151318-aeb436f5-211f-452c-b885-e626f2518fb8.png)
![image](https://user-images.githubusercontent.com/34448135/204150909-4127c979-5e15-4c1a-ab05-39284d9d1844.png)
![image](https://user-images.githubusercontent.com/34448135/204150931-cd53880f-dd95-4a25-8d1f-1b4fab47f482.png)

