package com.plcoding.jwtauthktorandroid.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.plcoding.jwtauthktorandroid.data.auth.AuthApi
import com.plcoding.jwtauthktorandroid.data.auth.AuthRepository
import com.plcoding.jwtauthktorandroid.data.auth.AuthRepositoryImpl
import com.plcoding.jwtauthktorandroid.data.books.BookApi
import com.plcoding.jwtauthktorandroid.data.books.BookRepository
import com.plcoding.jwtauthktorandroid.data.books.BookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val hostname = "http://192.168.5.9"
    private const val port = 8080
    private const val baseUrl = "$hostname:$port/"
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBookApi(): BookApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, prefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, prefs)
    }

    @Provides
    @Singleton
    fun provideBookRepository(api: BookApi, prefs: SharedPreferences): BookRepository {
        return BookRepositoryImpl(api, prefs)
    }
}