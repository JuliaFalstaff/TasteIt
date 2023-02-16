package com.jfalstaff.tasteit.di

import com.jfalstaff.tasteit.data.Constants
import com.jfalstaff.tasteit.data.RepositoryImpl
import com.jfalstaff.tasteit.data.remote.ApiService
import com.jfalstaff.tasteit.domain.IRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun bindRepository(impl: RepositoryImpl): IRepository

    companion object {
        @Provides
        @Singleton
        fun provideOkHttp(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS).build()
        }

        @Provides
        @Singleton
        fun provideGsonConverter(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }

        @Provides
        @Singleton
        fun provideApiServer(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

}