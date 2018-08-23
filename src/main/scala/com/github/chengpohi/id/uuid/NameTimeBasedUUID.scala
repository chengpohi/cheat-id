package com.github.chengpohi.id.uuid

import java.security.MessageDigest
import java.util.Base64
import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}


class NameTimeBasedUUID {
  private val sequenceNumber = new AtomicInteger(UUIDFactors.SECURE_RANDOM.nextInt)
  private val lastTimestamp = new AtomicLong(0)

  def getBase64UUID(name: Array[Byte]): String = {
    val nameBytes = MessageDigest.getInstance("MD5").digest(name).toStream.take(9).toArray
    val sequenceId = sequenceNumber.incrementAndGet & 0xffffff
    val timestamp = lastTimestamp.updateAndGet(i => {
      Math.max(i, System.currentTimeMillis())
    })

    val uuidBytes: Array[Byte] = getHighBytes(sequenceId, timestamp) ++
      nameBytes ++
      getLowBytes(sequenceId, timestamp)

    Base64.getUrlEncoder.withoutPadding.encodeToString(uuidBytes)
  }
  private def getLowBytes(sequenceId: Int, timestamp: Long) = {
    Array(
      (timestamp >>> 8).toByte,
      (sequenceId >>> 8).toByte,
      timestamp.toByte
    )
  }
  private def getHighBytes(sequenceId: Int, timestamp: Long) = {
    Array(
      sequenceId.toByte,
      (sequenceId >>> 16).toByte,
      (timestamp >>> 16).toByte,
      (timestamp >>> 24).toByte
    )
  }
}


