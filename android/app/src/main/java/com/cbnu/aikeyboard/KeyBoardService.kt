package com.cbnu.aikeyboard

import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.aikeyboard.R

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import com.cbnu.aikeyboard.keyboardview.KeyboardAI
import com.cbnu.aikeyboard.keyboardview.KeyboardChunjiin
import com.cbnu.aikeyboard.keyboardview.KeyboardEmoji
import com.cbnu.aikeyboard.keyboardview.KeyboardEnglish
import com.cbnu.aikeyboard.keyboardview.KeyboardKorean
import com.cbnu.aikeyboard.keyboardview.KeyboardNumpad
import com.cbnu.aikeyboard.keyboardview.KeyboardSimbols


class KeyBoardService : InputMethodService(){
    lateinit var keyboardView:LinearLayout
    lateinit var keyboardFrame:FrameLayout
    lateinit var keyboardKorean: KeyboardKorean
    lateinit var keyboardEnglish: KeyboardEnglish
    lateinit var keyboardSimbols: KeyboardSimbols
    lateinit var keyboardAI: KeyboardAI
    lateinit var keyboardChangeAi: Button
    var isQwerty = 0 // shared preference에 데이터를 저장하고 불러오는 기능 필요


    val keyboardInterationListener = object:KeyboardInterationListener{
        //inputconnection이 null일경우 재요청하는 부분 필요함
        override fun modechange(mode: Int) {
            currentInputConnection.finishComposingText()
            when(mode){
                0 ->{
                    keyboardFrame.removeAllViews()
                    keyboardEnglish.inputConnection = currentInputConnection
                    keyboardFrame.addView(keyboardEnglish.getLayout())
                }
                1 -> {
                    if(isQwerty == 0){
                        keyboardFrame.removeAllViews()
                        keyboardKorean.inputConnection = currentInputConnection
                        keyboardFrame.addView(keyboardKorean.getLayout())
                    }
                    else{
                        keyboardFrame.removeAllViews()
                        keyboardFrame.addView(KeyboardChunjiin.newInstance(applicationContext, layoutInflater, currentInputConnection, this))
                    }
                }
                2 -> {
                    keyboardFrame.removeAllViews()
                    keyboardSimbols.inputConnection = currentInputConnection
                    keyboardFrame.addView(keyboardSimbols.getLayout())
                }
                3 -> {
                    keyboardFrame.removeAllViews()
                    keyboardFrame.addView(KeyboardEmoji.newInstance(applicationContext, layoutInflater, currentInputConnection, this))
                }
                4 -> {
                    keyboardFrame.removeAllViews()
                    keyboardFrame.addView(keyboardAI.getLayout())
                    keyboardAI.inputtedText()
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as LinearLayout
        keyboardFrame = keyboardView.findViewById(R.id.keyboard_frame)
        keyboardChangeAi = keyboardView.findViewById(R.id.spellCheck)
        keyboardChangeAi.setOnClickListener {
            keyboardInterationListener.modechange(4)
        }
    }

    override fun onCreateInputView(): View {
        keyboardKorean = KeyboardKorean(applicationContext, layoutInflater, keyboardInterationListener)
        keyboardEnglish = KeyboardEnglish(applicationContext, layoutInflater, keyboardInterationListener)
        keyboardSimbols = KeyboardSimbols(applicationContext, layoutInflater, keyboardInterationListener)
        keyboardAI = KeyboardAI(applicationContext, layoutInflater, keyboardInterationListener)
        keyboardKorean.inputConnection = currentInputConnection
        keyboardKorean.init()
        keyboardEnglish.inputConnection = currentInputConnection
        keyboardEnglish.init()
        keyboardSimbols.inputConnection = currentInputConnection
        keyboardSimbols.init()
        keyboardAI.inputConnection = currentInputConnection
        keyboardAI.init()


        return keyboardView
    }

    override fun updateInputViewShown() {
        super.updateInputViewShown()
        currentInputConnection.finishComposingText()
        if(currentInputEditorInfo.inputType == EditorInfo.TYPE_CLASS_NUMBER){
            keyboardFrame.removeAllViews()
            keyboardFrame.addView(KeyboardNumpad.newInstance(applicationContext, layoutInflater, currentInputConnection, keyboardInterationListener))
        }
        else{
            keyboardInterationListener.modechange(1)
        }
    }


}
