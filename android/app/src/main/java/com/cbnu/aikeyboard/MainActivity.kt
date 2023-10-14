package com.cbnu.aikeyboard

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import com.example.aikeyboard.R


class MainActivity : AppCompatActivity() {

    private val TAG = "CustomKeyboardApp" // 로그 태그 이름 설정
    private val inputMethodService = InputMethodService()
    private var checkFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val settingBtn = findViewById<Button>(R.id.setting_btn)
        settingBtn.setOnClickListener {
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            startActivity(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                checkFlag = inputMethodService.switchToNextInputMethod(inputMethodService.shouldOfferSwitchingToNextInputMethod())
                Log.d(TAG, checkFlag.toString())
            }
        }
    }
}