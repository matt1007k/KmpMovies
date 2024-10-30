package com.maxdev.kmpmovies

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.maxdev.kmpmovies.data.database.getDatabaseBuilder
import com.maxdev.kmpmovies.data.database.getRoomDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val darkMode = isSystemInDarkTheme()
            if(!view.isInEditMode) {
                val window = (view.context as Activity).window
                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkMode
            }
            val db = getRoomDatabase(
                getDatabaseBuilder(LocalView.current.context)
            )

            App(db.moviesDao())
        }
    }
}