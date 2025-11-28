package com.example.vpndetector

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var detailsTextView: TextView
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        detailsTextView = findViewById(R.id.detailsTextView)
        checkButton = findViewById(R.id.checkButton)

        checkButton.setOnClickListener {
            checkVpnStatus()
        }

        checkVpnStatus()
    }

    private fun checkVpnStatus() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)

        // Use activeNetwork if available, otherwise might be null (no network)
        val activeNetwork: Network? = connectivityManager.activeNetwork

        if (activeNetwork == null) {
            statusTextView.text = "No Active Network"
            detailsTextView.text = ""
            return
        }

        val caps: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(activeNetwork)

        if (caps != null && caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            statusTextView.text = getString(R.string.vpn_active)

            val sb = StringBuilder()
            sb.append("Transport: VPN\n")
            sb.append("Capabilities: $caps\n")

            detailsTextView.text = sb.toString()
        } else {
            statusTextView.text = getString(R.string.vpn_inactive)
            detailsTextView.text = ""
        }
    }
}
