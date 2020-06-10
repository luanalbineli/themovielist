# The movie list

This is "basically" an Android application that consumes the services exposed by [TMDb](https://www.themoviedb.org).

### Patters, libraries and technology:
  * MVVM
  * Dagger 2.x
  * Kotlin (+ Coroutines)
  * Retrofit 2
  * Room
  * Timber
  * Glide
  * AndroidX
  * And so on

##### Why architecture patterns?
Split the responsibilities. When you follow an architecture pattern,
I's easier to understand the flow of information, the responsibilities of each class/object, to maintain the code and test.


### Features

The application was divided into four tabs:

  * :white_check_mark: Home - The default tab of the application
    * :white_check_mark: List of the first twenty movies ordered by popularity;
    * :white_check_mark: List of the first twenty movies ordered by rating;
    * :white_check_mark: Option to see all movies ordered by popularity (infinite scroll);
    * :white_check_mark: Option to see all movies ordered by rating (infinite scroll);

  * :x: Browse - Tab that let's the user to search for movies

  * :white_check_mark: Cinema
    * :x: List of the movies in theaters
    * :x: Show the upcoming movies

  * :white_check_mark: Favorite
    * :x: List your favorite movies
    * :x: Option to select the sorted by (added order, release date)

 * :white_check_mark: Movie detail screen
   * :x: Show the backdrop and poster;
   * :x: Show the user's average rating;
   * :x: Show the first two reviews;
   * :x: Show all reviews;
   * :x: Show the first two trailers;
   * :x: Show all trailers;
   * :x: Option to favorite the movie;
   * :x: Show the cast;
   * :x: Show recommendations (of movies, based on the movie you are seeing);
   
### Building yourself

To build/run this project, you I'll need [Android Studio 3.0](https://developer.android.com/studio/index.html).
Besides, you need to create a [TMDb account](https://www.themoviedb.org/account/signup) and get an API key.
Then, with your API key in hands, just create a `gradle.properties` file on root folder, and add the following line:

API_KEY = "YOUR_API_KEY"                                              
                                              
   
Some screenshots:

<img src="https://raw.github.com/luanalbineli/themovielist/master/screenshots/Screenshot_1591756538.png" width="250">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.github.com/luanalbineli/themovielist/master/screenshots/Screenshot_1591756643.png" width="250">