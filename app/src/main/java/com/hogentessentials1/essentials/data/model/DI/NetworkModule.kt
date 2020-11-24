package com.hogentessentials1.essentials.data.model.DI

import com.hogentessentials1.essentials.BuildConfig
import com.hogentessentials1.essentials.data.model.Repositories.ChangeInitiativeRepository
import com.hogentessentials1.essentials.data.model.Repositories.ProjectRepository
import com.hogentessentials1.essentials.data.model.Repositories.RoadMapRepository
import com.hogentessentials1.essentials.data.model.network.*
import com.hogentessentials1.essentials.data.model.util.Globals
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), Globals.BASE_URL) }
    single { provideRmiEndpointInterface(get()) }
    single { provideChangeInitiativeEndpointInterface(get())}
    single { provideProjectEndpointInterface(get()) }


    // TODO alle apiinterfaces appart als single (zoals hierboven)
    single { RoadMapRemoteDataSource(get()) }
    single { ChangeInitiativeRemoteDataSource(get())}
    single { ProjectRemoteDataSource(get())}

    single { RoadMapRepository(get()) }
    single { ChangeInitiativeRepository(get())}
    single { ProjectRepository(get())}
}

/**
 * Provided a OkHttpClient. In debug version, an interceptor is added
 * as to log the network information which has been received.
 */
private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

/**
 * Provide the retrofit instance
 */
private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()
            )
        )

        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
/*
 * Provide the API Service
 */

private fun provideRmiEndpointInterface(retrofit: Retrofit): RoadMapItemsEndpointInterface =
    retrofit.create(RoadMapItemsEndpointInterface::class.java)

private fun provideChangeInitiativeEndpointInterface(retrofit: Retrofit): ChangeInitiativesEndpointInterface =
    retrofit.create(ChangeInitiativesEndpointInterface::class.java)

private fun provideProjectEndpointInterface(retrofit: Retrofit): ProjectsEndpointInterface =
    retrofit.create(ProjectsEndpointInterface::class.java)