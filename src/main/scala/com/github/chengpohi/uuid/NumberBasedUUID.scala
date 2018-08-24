package com.github.chengpohi.uuid

import java.nio.ByteBuffer
import java.util.Base64

class NumberBasedUUID {
  def getBase64IdByLong(number: Long, salt: Array[Byte] = Array()): String = {
    val buffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.putLong(number)
    val bs = buffer.array()
    encode(salt, bs)
  }
  def getBase64IdByInt(number: Int, salt: Array[Byte] = Array()): String = {
    val buffer = ByteBuffer.allocate(java.lang.Integer.BYTES)
    buffer.putInt(number)
    val bs = buffer.array()
    encode(salt, bs)
  }

  def decodeLongId(id: String, salt: Array[Byte]): Long = {
    val bytes = Base64.getUrlDecoder.decode(id)
    val saltBytes: Array[Byte] = paddSaltBytes(salt, bytes.length)
    val source = bytes.zip(saltBytes).map(i => (i._1 ^ i._2).toByte)

    val buffer = ByteBuffer.wrap(source)
    buffer.getLong
  }

  def decodeIntId(id: String, salt: Array[Byte]): Int = {
    val bytes = Base64.getUrlDecoder.decode(id)
    val saltBytes = paddSaltBytes(salt, bytes.length)
    val source = bytes.zip(saltBytes).map(i => (i._1 ^ i._2).toByte)

    val buffer = ByteBuffer.wrap(source)
    buffer.getInt
  }

  private def encode(salt: Array[Byte], bs: Array[Byte]) = {
    val saltBytes = Array
      .fill(bs.length)(1.toByte)
      .zipAll(salt, 1.toByte, 1.toByte)
      .map(i => (i._1 ^ i._2).toByte)

    val bytes = bs.zip(saltBytes).map(i => (i._1 ^ i._2).toByte)
    Base64.getUrlEncoder.withoutPadding().encodeToString(bytes)
  }

  private def paddSaltBytes(salt: Array[Byte], length: Int) = {
    val saltBytes = Array
      .fill(length)(1.toByte)
      .zipAll(salt, 1.toByte, 1.toByte)
      .map(i => (i._1 ^ i._2).toByte)
    saltBytes
  }
}
