package com.example.passwordkeeper.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EncryptedPreferenceStorage @Inject constructor(
    context: Context
) {
    private val encryptedPrefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        encryptedPrefs = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        editor = encryptedPrefs.edit()
    }


    fun getPassword(key: String): String? = encryptedPrefs.getString(key, "not implemented")

    fun savePassword(key: String, value: String) {
        editor.putString(key, value).apply()
    }
}