package com.hogentessentials1.essentials.data.model.DI

import com.hogentessentials1.essentials.BuildConfig
import com.hogentessentials1.essentials.data.model.Repositories.*
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
    single { provideQuestionEndpointInterface(get())}
    single { provideOrganizationEndpointInterface(get())}
    single { provideEmployeeEndpointInterface(get())}
    single { provideChangeManagerEndpointInterface(get())}
    single { provideSurveyEndpointInterface(get())}

    // TODO alle apiinterfaces appart als single (zoals hierboven)
    single { RoadMapRemoteDataSource(get()) }
    single { ChangeInitiativeRemoteDataSource(get())}
    single { ProjectRemoteDataSource(get())}
    single { QuestionRemoteDataSource(get())}
    single { OrganizationRemoteDataSource(get())}
    single { EmployeeRemoteDataSource(get())}
    single { ChangeManagerRemoteDataSource(get())}
    single { SurveyRemoteDataSource(get())}


    single { RoadMapRepository(get()) }
    single { ChangeInitiativeRepository(get())}
    single { ProjectRepository(get())}
    single { QuestionRepository(get())}
    single { OrganizationRepository(get())}
    single { EmployeeRepository(get())}
    single { ChangeManagerRepository(get())}
    single { SurveyRepository(get())}
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

private fun provideQuestionEndpointInterface(retrofit: Retrofit): QuestionsEndpointInterface =
    retrofit.create(QuestionsEndpointInterface::class.java)

private fun provideOrganizationEndpointInterface(retrofit: Retrofit) : OrganizationsEndpointInterface =
    retrofit.create(OrganizationsEndpointInterface::class.java)

private fun provideEmployeeEndpointInterface(retrofit: Retrofit) : EmployeeEndpointInterface =
    retrofit.create(EmployeeEndpointInterface::class.java)

private fun provideChangeManagerEndpointInterface(retrofit: Retrofit) : ChangeManagersEndpointInterface =
    retrofit.create(ChangeManagersEndpointInterface::class.java)

private fun provideSurveyEndpointInterface(retrofit: Retrofit) : SurveyEndpointInterface =
    retrofit.create(SurveyEndpointInterface::class.java)