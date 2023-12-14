package com.example.hsmdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.hsmdemo.HsmModule.HsmModel
import com.example.hsmdemo.utils.HexStringUtils

class MainActivity : AppCompatActivity() {
    private val hsmModel = HsmModel(this)
    private var btnOpen: AppCompatButton? = null
    private var btnClose: AppCompatButton? = null
    private var btnCheckTempered: AppCompatButton? = null
    private var btnCheckKeyExisted: AppCompatButton? = null
    private var btnUpdateKey: AppCompatButton? = null
    private var btnEncrypt: AppCompatButton? = null
    private var btnDecrypt: AppCompatButton? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtons()
    }

    private fun setupButtons() {
        btnOpen = findViewById(R.id.btn_open)
        btnClose = findViewById(R.id.btn_close)
        btnCheckTempered = findViewById(R.id.btn_check_tempered)
        btnCheckKeyExisted = findViewById(R.id.btn_check_key_existed)
        btnUpdateKey = findViewById(R.id.btn_update_key)
        btnEncrypt = findViewById(R.id.btn_encrypt)
        btnDecrypt = findViewById(R.id.btn_decrypt)

        btnOpen?.setOnClickListener {
            hsmModel.open()
        }

        btnClose?.setOnClickListener {
            hsmModel.close()
        }

        btnCheckTempered?.setOnClickListener {
            hsmModel.isTampered()
        }

        btnCheckKeyExisted?.setOnClickListener {
            hsmModel.checkIfHsmKeyExisted()
        }

        btnUpdateKey?.setOnClickListener {
            val data = HexStringUtils.hexStringToByteArray("55 66 77 88 99 AA BB CC DD EE FF 00 11 22 33 44")
            hsmModel.updateKey(data)
        }

        btnEncrypt?.setOnClickListener {
            val data = HexStringUtils.hexStringToByteArray("01 04 CC 4E 52 7C 12 90 12 34 56 56 65 72 79 50 61 79 00 00 00 00 00 00 00 00 00 00 00 00 00 00")
            hsmModel.encryptData(data)
        }

        btnDecrypt?.setOnClickListener {
            val data = HexStringUtils.hexStringToByteArray("5A 4E E5 77 08 88 B1 B6 DF 0C 9D 36 4A 55 AB 54")
            hsmModel.decryptData(data)
        }
    }
}
