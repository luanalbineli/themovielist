# The movie list

This is "basically" an Android application that consumes the services exposed by [TMDb](https://www.themoviedb.org).

### Patters, libraries and technology:
  * MVVM (design pattern)
  * Hilt (dependency injection)
  * Kotlin (+ Coroutines)
  * Retrofit 2 (HTTP calls)
  * Room (SQLite ORM)
  * Timber (Logging)
  * Glide (Network image fetcher/caching)
  * Data binding (make the Activity/Fragment/Adapter dummy as possible)
  * LiveData (lifecycle-aware components)
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
   * :white_check_mark: Show the backdrop and poster;
   * :white_check_mark: Show the user's average rating;
   * :white_check_mark: Show the first two reviews;
   * :white_check_mark: Show all reviews;
   * :white_check_mark: Show the first two trailers;
   * :white_check_mark: Show all trailers;
   * :white_check_mark: Option to favorite the movie;
   * :x: Show the cast;
   * :white_check_mark: Show recommendations (of movies, based on the movie you are seeing);
   
### Building yourself

To build/run this project, you need to create a [TMDb account](https://www.themoviedb.org/account/signup) and get an API key.
Then, with your API key in hands, just create a `gradle.properties` file on root folder, and add the following line:

API_KEY = "YOUR_API_KEY"                                              
                                              
   
Some screenshots:

<img src="https://raw.github.com/luanalbineli/themovielist/master/screenshots/Screenshot_1591756538.png" width="250">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://raw.github.com/luanalbineli/themovielist/master/screenshots/Screenshot_1591756643.png" width="250">