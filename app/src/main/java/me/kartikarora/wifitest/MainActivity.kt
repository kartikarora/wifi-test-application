package me.kartikarora.wifitest

import android.net.wifi.WifiNetworkSuggestion
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.kartikarora.wifitest.ui.theme.WifiTestApplicationTheme

class MainActivity : ComponentActivity() {


    private val addWifiContract = registerForActivityResult(AddWiFiNetworksContract()) { success ->
        Log.i("Status: ", success.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WifiTestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(onClick = { addWifiContract.launch(getWiFiSSIDSuggestions()) }) {
                        Text(text = "Add Wifi Network")
                    }
                }
            }
        }
    }

    private fun getWiFiSSIDSuggestions(): List<WifiNetworkSuggestion> {
        return resources.getStringArray(R.array.wifi_ssid_suggestions).map { ssid ->
            WifiNetworkSuggestion.Builder().setSsid(ssid).setIsUserInteractionRequired(true).build()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WifiTestApplicationTheme {
        Greeting("Android")
    }
}