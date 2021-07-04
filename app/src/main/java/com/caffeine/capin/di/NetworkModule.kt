package com.caffeine.capin.di


//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class BaseClient
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class HttpClient
//
//@Module
//@InstallIn(SingletonComponent::class)
object NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideOkHttpInterceptor(): HttpLoggingInterceptor =
//        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
//            this.level = HttpLoggingInterceptor.Level.BODY
//        }
//
//    @Provides
//    @Singleton
//    @BaseClient
//    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
//        OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .writeTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .build()
//
//    @Provides
//    @Singleton
//    @HttpClient
//    fun provideHttpClient(@BaseClient client: OkHttpClient): OkHttpClient {
//        val interceptor = Interceptor { chain ->
//            val request = chain.request()
//                .newBuilder()
//                .build()
//            chain.proceed(request)
//        }
//        return client.newBuilder().addNetworkInterceptor(interceptor).build()
//    }
//
//    //Todo: Server URL 나오면 local.properties에 저장하고 baseUrl에 추가
//    @Provides
//    @Singleton
//    fun provideRetrofit(@HttpClient okHttpClient: OkHttpClient):Retrofit =
//        Retrofit.Builder()
//            .baseUrl("")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()

}