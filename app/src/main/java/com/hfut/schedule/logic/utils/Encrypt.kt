package com.hfut.schedule.logic.utils

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Encrypt {
    fun encryptAES(input:String, password:String): String {
        return try {
            val cipher = Cipher.getInstance("AES")
            val keySpec: SecretKeySpec = SecretKeySpec(password.toByteArray(),"AES")
            cipher.init(Cipher.ENCRYPT_MODE,keySpec)
            val encrypt = cipher.doFinal(input.toByteArray())
            Base64.encodeToString(encrypt,Base64.NO_WRAP)
        } catch (e:Exception) {
            "NULL"
        }
    }
    fun encodeToBase64(input: String): String {
        return java.util.Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))
    }

    fun md5Hash(input: String): String {
        // 获取MD5实例
        val md = MessageDigest.getInstance("MD5")

        // 将输入字符串转换为字节数组并进行MD5加密
        val digest = md.digest(input.toByteArray())

        // 将加密后的字节数组转换为16进制字符串
        val hexString = digest.joinToString("") { "%02x".format(it) }

        // 返回最后10位
        return if (hexString.length > 10) hexString.takeLast(10) else hexString
    }

    //校务行用的，就是微信小程序逆向JS
    fun encryptXiaoWuXing(plainText: String): String {
        val key = "JL$<&*l9~67?:#5p"
        val iv = "{g;,9~l4'/sw`885"

        val keyBytes = key.toByteArray(Charsets.UTF_8)
        val ivBytes = iv.toByteArray(Charsets.UTF_8)

        val keySpec = SecretKeySpec(keyBytes, "AES")
        val ivSpec = IvParameterSpec(ivBytes)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        return java.util.Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun encryptAesECB(plainText: String, key: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    //用于生成和风天气密钥
    fun getQWeatherAuth() : String {
        return "Bearer " + GenerateQWeather().generate(
            "MC4CAQAwBQYDK2VwBCIEILpLcCmyt8JbbBaEMiBvA9ys3RLb2v63rWhuFC83KDGZ",
            "4HTJ5N7F37",
            "THB3UBK56Q"
        )
    }
}


