package com.github.chengpohi.id.uuid

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

//noinspection ScalaStyle
class UUIDsTest extends FlatSpec with Matchers {

  import scala.concurrent.ExecutionContext.Implicits.global

  it should "generate base64 uuid" in {
    val ids = (0 until 2000).map(i => {
      val id = UUIDs.getBase64UUID
      id.length should equal(22)
      id
    })

    ids.toSet.size should equal(2000)
  }
  it should "generate time based base64 uuid" in {
    val ids = (0 until 2000).map(i => {
      val id = UUIDs.getTimeBasedBase64UUID
      id.length should equal(20)
      id
    })
    ids.toSet.size should equal(2000)
  }

  it should "thread safe generate time based base64 uuid" in {
    val ids = (0 until 20000).map(i => {
      Future {
        val id = UUIDs.getTimeBasedBase64UUID
        id.length should equal(20)
        id
      }
    })

    val is = Await.result(Future.sequence(ids), Duration.Inf)

    ids.toSet.size should equal(20000)
  }

  it should "generate short base64 id" in {
    //    (0 until 5000).foreach(j => {
    val ids = (0 until 20000).map(i => {
      val id = UUIDs.getShortUUID
//      println(id)
      id.length should equal(6)
      id
    })
    ids.toSet.size should equal(20000)
    //    }
    //    )
  }
}
