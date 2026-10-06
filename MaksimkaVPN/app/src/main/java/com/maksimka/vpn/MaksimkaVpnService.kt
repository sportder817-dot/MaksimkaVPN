package com.maksimka.vpn

import android.net.VpnService
import android.os.ParcelFileDescriptor

class MaksimkaVpnService : VpnService() {
    private var vpnInterface: ParcelFileDescriptor? = null
    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int {
        vpnInterface?.close()
        vpnInterface = Builder().setSession("Максимка VPN").addAddress("10.8.0.2", 32).addRoute("0.0.0.0", 0).establish()
        return START_STICKY
    }
    override fun onDestroy() { vpnInterface?.close(); vpnInterface=null; super.onDestroy() }
}
