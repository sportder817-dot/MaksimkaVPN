package com.maksimka.vpn

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var button: Button
    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(32,32,32,32); setBackgroundResource(com.maksimka.vpn.R.drawable.bg)
        }
        val title = TextView(this).apply { text = "Максимка VPN"; textSize = 32f; setTextColor(Color.WHITE); gravity = Gravity.CENTER }
        status = TextView(this).apply { text = "Не подключено"; textSize = 18f; setTextColor(Color.LTGRAY); gravity = Gravity.CENTER; setPadding(0,24,0,24) }
        button = Button(this).apply { text = "ПОДКЛЮЧИТЬ"; textSize = 18f }
        root.addView(title, LinearLayout.LayoutParams(-1, -2)); root.addView(status, LinearLayout.LayoutParams(-1,-2)); root.addView(button, LinearLayout.LayoutParams(-1,-2))
        setContentView(root)
        button.setOnClickListener { toggle() }
    }

    private fun toggle() {
        if (!connected) {
            val intent = VpnService.prepare(this)
            if (intent != null) startActivityForResult(intent, 10) else startVpn()
        } else { stopService(Intent(this, MaksimkaVpnService::class.java)); connected=false; updateUi() }
    }
    private fun startVpn() { startService(Intent(this, MaksimkaVpnService::class.java)); connected=true; updateUi() }
    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) { super.onActivityResult(requestCode,resultCode,data); if(requestCode==10 && resultCode==RESULT_OK) startVpn() }
    private fun updateUi(){ status.text=if(connected) "Подключено" else "Не подключено"; button.text=if(connected) "ОТКЛЮЧИТЬ" else "ПОДКЛЮЧИТЬ" }
}
