package com.github.chengpohi.uuid

import java.util.Base64
import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

class TimeBasedUUID {
  private val sequenceNumber = new AtomicInteger(
    UUIDFactors.SECURE_RANDOM.nextInt)
  private val lastTimestamp = new AtomicLong(0)

  def getBase64UUID: String = {
    val sequenceId = sequenceNumber.incrementAndGet & 0xffffff
    val timestamp = lastTimestamp.updateAndGet(i => {
      Math.max(i, System.currentTimeMillis())
    })

    val uuidBytes: Array[Byte] = getHighBits(sequenceId, timestamp) ++
      UUIDFactors.MAC_ADDRESS ++
      getLowBits(sequenceId, timestamp)

    Base64.getUrlEncoder.withoutPadding.encodeToString(uuidBytes)
  }
  private def getLowBits(sequenceId: Int, timestamp: Long) = {
    Array(
      (timestamp >>> 8).toByte,
      (sequenceId >>> 8).toByte,
      timestamp.toByte
    )
  }
  private def getHighBits(sequenceId: Int, timestamp: Long) = {
    Array(
      sequenceId.toByte,
      (sequenceId >>> 16).toByte,
      (timestamp >>> 16).toByte,
      (timestamp >>> 24).toByte,
      (timestamp >>> 32).toByte,
      (timestamp >>> 40).toByte
    )
  }
}
