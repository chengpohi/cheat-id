package com.github.chengpohi.uuid

import java.net.NetworkInterface
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util
import java.util.{Base64, Objects, UUID}

import scala.collection.JavaConverters._
import scala.util.Try

object UUIDs {
  val TIME_BASED_UUID = new TimeBasedUUID
  val SHORT_UUID = new ShortUUID
  val NAME_TIME_BASED_UUID = new NameTimeBasedUUID
  val NUMBER_BASED_UUID = new NumberBasedUUID

  /**
    * random uuid
    *
    * @return random uuid 32 length
    */
  def getUUID: String = {
    UUID.randomUUID().toString
  }

  /**
    * generate random uuid and base64 encode for length 22
    *
    * @return base64 encoded random uuid
    */
  def getBase64UUID: String = {
    val uuid = UUID.randomUUID()
    val buffer =
      ByteBuffer.wrap(new Array[Byte](UUIDFactors.AVAILABLE_ENCODED_BITS))

    buffer.putLong(uuid.getMostSignificantBits)
    buffer.putLong(uuid.getLeastSignificantBits)
    Base64.getEncoder.withoutPadding().encodeToString(buffer.array())
  }

  /**
    * generate name based uuid and base64 encode for length 22
    *
    * @return base64 encoded random uuid
    */
  def getNamedBase64UUID(named: Array[Byte]): String = {
    val uuid = UUID.nameUUIDFromBytes(named)
    val buffer =
      ByteBuffer.wrap(new Array[Byte](UUIDFactors.AVAILABLE_ENCODED_BITS))

    buffer.putLong(uuid.getMostSignificantBits)
    buffer.putLong(uuid.getLeastSignificantBits)
    Base64.getEncoder.withoutPadding().encodeToString(buffer.array())
  }

  /**
    * generate time based uuid and length 20
    *
    * @return time based base64 uuid
    */
  def getTimeBasedBase64UUID: String = TIME_BASED_UUID.getBase64UUID

  /**
    * generate name time based uuid and length 20
    * this can be use with user id
    *
    * @return name based base64 uuid
    */
  def getNameTimeBasedBase64UUID(name: Array[Byte]): String =
    NAME_TIME_BASED_UUID.getBase64UUID(name)

  def getShortUUID: String = SHORT_UUID.getBase64ID

  def decodeBase64UUID(uuid: String): UUID = {
    val bytes = ByteBuffer.wrap(Base64.getDecoder.decode(uuid))
    new UUID(bytes.getLong(), bytes.getLong())
  }
}

object UUIDFactors {
  lazy val AVAILABLE_ENCODED_BITS = 16
  lazy val SECURE_RANDOM: SecureRandom = new SecureRandom
  //6 bytes mac address
  lazy val MAC_ADDRESS: Array[Byte] = {
    val networkInterface: Try[util.Enumeration[NetworkInterface]] = Try(
      NetworkInterface.getNetworkInterfaces)
    networkInterface.toOption
      .flatMap(addr => {
        addr.asScala
          .filter({
            case i if Objects.isNull(i)         => false
            case i if i.isLoopback              => false
            case i if i.getHardwareAddress != 6 => false
          })
          .toStream
          .headOption
      })
      .map(addr => addr.getHardwareAddress)
      .getOrElse({
        val bytes = new Array[Byte](6)
        SECURE_RANDOM.nextBytes(bytes)
        bytes
      })
  }
}
