package com.cbnu.aikeyboard.keyboardview

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.cbnu.aikeyboard.KeyboardInterationListener
import com.example.aikeyboard.R

class KeyboardAI (var context: Context, var layoutInflater: LayoutInflater, var keyboardInterationListener: KeyboardInterationListener){
    lateinit var aiLayout: LinearLayout
    lateinit var inputText : TextView
    lateinit var correctedText : TextView
    lateinit var testToInsertBtn: Button

    var inputConnection: InputConnection? = null
        set(inputConnection){
            field = inputConnection
        }

    // 이 클래스의 초기화 및 다른 필요한 메서드를 여기에 추가할 수 있습니다.
    // 예를 들어, 키보드 레이아웃을 초기화하는 메서드 등을 추가할 수 있습니다.

    // 예를 들어, 다음과 같이 초기화 메서드를 추가할 수 있습니다.
    fun init() {
        aiLayout = layoutInflater.inflate(R.layout.keyboard_ai, null) as LinearLayout
        keyboardInterationListener = keyboardInterationListener

        inputText = aiLayout.findViewById(R.id.inputText)
        correctedText = aiLayout.findViewById(R.id.correctedText)
        testToInsertBtn = aiLayout.findViewById(R.id.textToInsert)

        testToInsertBtn.setOnClickListener {
            textToInsert()
        }
        // 사용자 지정 키보드 레이아웃 초기화 로직을 여기에 구현합니다.
        // aiLayout을 만들고 버튼, 텍스트뷰, 리스너 등을 설정할 수 있습니다.
    }

    // 현재 키보드에 있는 내용을 업뎃
    @RequiresApi(Build.VERSION_CODES.S)
    fun inputtedText() {
        if(inputConnection != null){
            val text = inputConnection?.getExtractedText(ExtractedTextRequest(), 0)
//            val text: CharSequence? = inputConnection?.getSelectedText(InputConnection.GET_TEXT_WITH_STYLES)
//            val text: SurroundingText? = inputConnection?.getSurroundingText(0,1000,InputConnection.GET_TEXT_WITH_STYLES)


            Log.d("KEYBOARDAI", "Text: $text")
            inputText?.text = text?.text
            inputText.setTextColor(Color.BLACK)
            Log.d("KEYBOARDAI", inputConnection.toString())
        }
        else{
            Log.d("KEYBOARDAI", "해제")
        }
    }

    fun getLayout():LinearLayout{
        return aiLayout
    }

    private fun setLayoutComponents(){

    }

    fun textToInsert() {
        val text_to_insert = correctedText?.text
        // inputConnection이 설정되어 있는지 확인
        if (inputConnection != null) {
            // 기존 텍스트 삭제
            Log.d("KEYBOARDAI", inputConnection.toString())

            inputConnection?.deleteSurroundingText(1000, 1000) // 이 범위는 필요에 따라 조절할 수 있습니다.
            // 텍스트를 입력창에 추가
            inputConnection?.commitText(text_to_insert, 1)
        }
        // 새로운 입력 상태를 시작하기 위해 포커스를 다시 설정
        // 저장한 텍스트를 지워주거나 초기화
    }
}