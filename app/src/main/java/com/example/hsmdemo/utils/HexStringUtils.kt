package com.example.hsmdemo.utils

class HexStringUtils {
    companion object {
        fun hexStringToByteArray(hexParam: String): ByteArray {
            val hex = hexParam.replace("\\s".toRegex(), "")
            val length = hex.length
            val byteArray = ByteArray(length / 2)

            for (i in 0 until length step 2) {
                val byte = hex.substring(i, i + 2).toInt(16).toByte()
                byteArray[i / 2] = byte
            }

            return byteArray
        }

        fun byteArrayToHexString(byteArray: ByteArray): String {
            val hexChars = CharArray(byteArray.size * 2)

            for (i in byteArray.indices) {
                val v = byteArray[i].toInt() and 0xFF
                hexChars[i * 2] = "0123456789ABCDEF"[v shr 4]
                hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
            }

            return String(hexChars)
        }
    }
}