package com.example.hsmdemo.HsmModule

import android.content.Context
import android.util.Log
import com.cloudpos.AlgorithmConstants
import com.cloudpos.DeviceException
import com.cloudpos.POSTerminal
import com.cloudpos.hsm.HSMDevice
import com.example.hsmdemo.utils.HexStringUtils

class HsmModel(private val mContext: Context?) {
    private var device: HSMDevice? = null
    private val TAG = HsmModel::class.java.simpleName

    companion object {
        const val HSM_KEY_ID = 3
    }

    init {
        device = POSTerminal.getInstance(mContext)
            .getDevice("cloudpos.device.hsm") as HSMDevice
    }

    fun open() {
        try {
            device!!.open()
            Log.d(TAG, "Opened.")
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
    }

    fun isTampered() {
        try {
            val isSuccess = device!!.isTampered
            Log.d(TAG, "HSM is ${if (isSuccess) "" else "not"} tampered.")
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
    }

    fun checkIfHsmKeyExisted() {
        try {
            val isSuccess = device!!.isKeyExist(HSM_KEY_ID)
            Log.d(TAG, "HSM key ${if (isSuccess) "existed" else "does not exist"}.")
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
    }

    fun updateKey(data: ByteArray) {
        try {
            Log.d(TAG, "updateKey date: $data")
            val rs = device!!.updateKey(HSM_KEY_ID, AlgorithmConstants.ALG_AES, data)
            Log.d(TAG, "Updated key ${if (rs < 0) "failed" else "succeeded (result is ${rs})"}.")
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
    }

    fun close() {
        try {
            device!!.close()
            Log.d(TAG, "Closed.")
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
    }

    fun encryptData(data: ByteArray): ByteArray? {
        try {
            val IV = ByteArray(16)
            val res = device!!.keyEncrypt_v1(HSM_KEY_ID, AlgorithmConstants.ALG_AES, 0, data, IV)
            return res
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
        return null
    }

    fun decryptData(data: ByteArray): ByteArray? {
        try {
            val IV = ByteArray(16)
            val res = device!!.keyDecrypt_v1(HSM_KEY_ID, AlgorithmConstants.ALG_AES, 0, data, IV)
            Log.d(TAG, "decryptData res: ${HexStringUtils.byteArrayToHexString(res)}")
            return res
        } catch (e: DeviceException) {
            e.printStackTrace()
        }
        return null
    }
}
