package com.caffeine.capin.di



//@Module
//@InstallIn(SingletonComponent::class)
object SharedPrefsModule {
//    @Provides
//    @Singleton
//    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
//        EncryptedSharedPreferences.create(
//            PREFS_NAME,
//            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//
//    @Provides
//    @Singleton
//    fun providePreferenceManager(prefs: SharedPreferences): PreferenceManager =
//        PreferenceManager(prefs)
//
//    @Provides
//    @Singleton
//    fun provideUserPreferenceManager(preferenceManager: PreferenceManager): UserPreferenceManager =
//        UserPreferenceManager(preferenceManager)

    private const val PREFS_NAME = "Capin_SharedPreferences"
}