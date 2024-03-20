package me.kartikarora.wifitest

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiNetworkSuggestion
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ADD_WIFI_RESULT_ALREADY_EXISTS
import android.provider.Settings.ADD_WIFI_RESULT_SUCCESS
import android.provider.Settings.EXTRA_WIFI_NETWORK_RESULT_LIST
import androidx.activity.result.contract.ActivityResultContract

class AddWiFiNetworksContract : ActivityResultContract<List<WifiNetworkSuggestion>, Boolean>() {
    override fun createIntent(context: Context, input: List<WifiNetworkSuggestion>): Intent {
        val bundle = Bundle().apply {
            putParcelableArrayList(Settings.EXTRA_WIFI_NETWORK_LIST, ArrayList(input))
        }
        return Intent(Settings.ACTION_WIFI_ADD_NETWORKS).apply {
            putExtras(bundle)
        }
    }
    
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode != RESULT_OK || intent == null || !intent.hasExtra(EXTRA_WIFI_NETWORK_RESULT_LIST)) {
            return false
        }
        
        return intent.getIntegerArrayListExtra(EXTRA_WIFI_NETWORK_RESULT_LIST)?.fold(false) { acc, code ->
            acc || when (code) {
                ADD_WIFI_RESULT_SUCCESS, ADD_WIFI_RESULT_ALREADY_EXISTS -> true
                else -> false
            }
        } ?: false
    }
}