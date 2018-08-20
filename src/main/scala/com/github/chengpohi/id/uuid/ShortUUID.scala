package com.github.chengpohi.id.uuid

import java.util.Base64
import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

class ShortUUID {
  private val sequenceNumber = new AtomicInteger(UUIDFactors.INSTANCE.nextInt)
  private val lastTimestamp = new AtomicLong(0)
  lazy val SECURE_MUNGED_ADDRESS: Array[Byte] = UUIDFactors.MAC_ADDRESS

  def getBase64UUID: String = {
    val sequenceId = sequenceNumber.incrementAndGet & 0xffffff
    val timestamp = lastTimestamp.updateAndGet(i => {
      Math.max(i, System.currentTimeMillis())
    })

    val bytes: Array[Byte] = Array(
      sequenceId.toByte,
      timestamp.toByte,
      SECURE_MUNGED_ADDRESS(0),
      (sequenceId >>> 8).toByte
    )
    Base64.getUrlEncoder.withoutPadding.encodeToString(bytes)
  }
}
