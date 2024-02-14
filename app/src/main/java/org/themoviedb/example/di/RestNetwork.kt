package org.themoviedb.example.di

import android.content.Context
import android.net.ConnectivityManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.themoviedb.example.BuildConfig
import org.themoviedb.example.db.MovieDbDataBase
import org.themoviedb.example.details.repository.DetailsApiInterface
import org.themoviedb.example.home.data.repository.TvShowsLocalDataSourceImpl
import org.themoviedb.example.home.data.repository.TvShowsRemoteDataSourceImpl
import org.themoviedb.example.home.domain.repository.TvShowsLocalDataSource
import org.themoviedb.example.home.domain.repository.TvShowsRemoteDataSource
import org.themoviedb.example.home.domain.usecase.GetTvShows
import org.themoviedb.example.home.repository.TvShowsApiInterface
import org.themoviedb.example.home.repository.TvShowsRepository
import org.themoviedb.example.home.repository.TvShowsRepositoryImpl
import org.themoviedb.example.util.NetworkInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [DataBaseModule::class])
@InstallIn(SingletonComponent::class)
object RestNetwork {
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return BuildConfig.BASE_URL
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    @Singleton
    @Provides
    fun provideOkHttp(
        interceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(networkInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun networkInterceptor(): NetworkInterceptor {
        return NetworkInterceptor()
    }

    @Singleton
    @Provides
    fun provideRestAdapter(
        baseURL: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideWeeklyTrendingApiService(retrofit: Retrofit): TvShowsApiInterface {
        return retrofit.create(TvShowsApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailsApiService(retrofit: Retrofit): DetailsApiInterface {
        return retrofit.create(DetailsApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowsRemoteDataSource(apiInterface: TvShowsApiInterface): TvShowsRemoteDataSource {
        return TvShowsRemoteDataSourceImpl(apiInterface)
    }

    @Provides
    @Singleton
    fun provideTvShowsRepository(remoteDataSource: TvShowsRemoteDataSource, localDataSource: TvShowsLocalDataSource): TvShowsRepository {
        return TvShowsRepositoryImpl(remoteDataSource,localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetTvShowsUseCase(repository: TvShowsRepository): GetTvShows {
        return GetTvShows(repository)
    }


//    @Singleton
//    @Provides
//    fun provideSearchApiService(retrofit: Retrofit): SearchApiInterface {
//        return retrofit.create(SearchApiInterface::class.java)
//    }

}