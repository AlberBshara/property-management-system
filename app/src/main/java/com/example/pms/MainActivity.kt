package com.example.pms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pms.ui.theme.PMSTheme
import com.example.pms.viewmodel.destinations.PmsNavHost

class MainActivity : ComponentActivity() {

    private lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMSTheme {
                //init the navigation component:
                navController = rememberNavController()
                PmsNavHost(navController = navController)
               Surface(
                   color= MaterialTheme.colors.background
               ) {

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PMSTheme {

    }
}