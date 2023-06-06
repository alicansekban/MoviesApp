package com.alican.mvvm_starter.util.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.blankj.utilcode.util.SPUtils
import com.alican.mvvm_starter.util.utils.SPPARAM.SELECTED_UI_MODE

class ThemeHelper {

    fun applyTheme(selectedMode: Int) {
        when (selectedMode) {
            UiMode.MODE_NIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            UiMode.MODE_LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
        SPUtils.getInstance().put(SELECTED_UI_MODE,selectedMode)
    }

}