package org.themoviedb.example.home.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.themoviedb.example.home.data.repository.FakeTvShowsRepositoryImpl
import org.themoviedb.example.home.model.Results

class GetTvShowsTest {

    private lateinit var getTvShows: GetTvShows
    private lateinit var fakeTvShowsRepositoryImpl: FakeTvShowsRepositoryImpl

    @Before
    fun setUp(){
        fakeTvShowsRepositoryImpl = FakeTvShowsRepositoryImpl()
        getTvShows = GetTvShows(fakeTvShowsRepositoryImpl)

        val results = arrayListOf<Results>()
        ('a'..'d').forEachIndexed{ index, c ->
            results.add(
                Results(
                    id = index,
                    name = c.toString()
                )
            )
        }
        runBlocking {
            fakeTvShowsRepositoryImpl.insertTrendingMovies(results)
        }
    }

    /*assign
      act
      assert
      */
    @Test
    fun `trending movies are not empty`() = runBlocking {
        var size = 0
        getTvShows.execute().collect { tvShows ->
            size =  tvShows.results.size
        }
        assert(size > 0)
    }

}