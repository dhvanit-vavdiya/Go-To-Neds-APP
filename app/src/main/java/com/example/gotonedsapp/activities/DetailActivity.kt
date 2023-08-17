package com.example.gotonedsapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.gotonedsapp.ui.theme.GoToNedsAppTheme

class DetailActivity:  ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var item = intent.getStringExtra("student")
        setContent {
            GoToNedsAppTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(color = MaterialTheme.colors.background) {
                    if (item != null) {
                        DisplayItem(item)
                    }
                }*/
            }
        }
    }
}

@Composable
fun DisplayItem(name: String) {
    /*Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$name",
        )
    }*/

}

