package com.dtse.map.demo.flavors.retailstores

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException

class MainActivity : AppCompatActivity() {

    val TAG = "HMS_Core"
    private var token = ""
    private var tokenView: TextView? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val myPushService = MyPushService()

        tokenView = findViewById<TextView>(R.id.tokenTxt)
        getToken()

    }

    private fun getToken() {
        // Create a thread.
        object : Thread() {
            override fun run() {
                try {
                    // Obtain the app ID from the agconnect-services.json file.
                    val appId = "108097917"

                    // Set tokenScope to HCM.
                    val tokenScope = "HCM"
                    token = HmsInstanceId.getInstance(this@MainActivity).getToken(appId, tokenScope)
                    Log.e(TAG, "Get Token: $token")
                    tokenView?.let { setText(it, "Get Token: $token") }
                    // Check whether the token is null.
                    if (!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token)
                    }
                } catch (e: ApiException) {
                    Log.e(TAG, "Get Token Failed, $e")
                    tokenView?.let { setText(it, "Get Token Failed, $e") }
                    ///tokenView!!.text = "get token failed, $e"
                }
            }
        }.start()
    }

    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }
    private fun sendRegTokenToServer(token: String?) {
        Log.e(TAG, "sending token to server. token:$token")
    }
}