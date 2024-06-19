package com.example.fitcal.ui.login

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validate()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validate() {
        val parentLayout = parent.parent as? TextInputLayout ?: return
        val text = text.toString()

        when (inputType) {
            android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    parentLayout.error = "Email tidak valid"
                } else {
                    parentLayout.error = null
                }
            }
            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD,
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                if (text.length < 8) {
                    parentLayout.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    parentLayout.error = null
                }
            }
        }
    }
}