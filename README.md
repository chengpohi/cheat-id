# cheat-id
cheat id 

# UUID

## RANDOM UUID

**Version 3** random UUID

```scala
UUIDs.getBase64UUID
```

## Time Based UUID

This will generate time based uuid, and it's continuous increment by the time

```scala
UUIDs.getTimeBasedBase64UUID
```

## Name Time Based UUID

This will generate base on the **name**, it can be used with actual **user id**, 
so every user will generate distinct user id, never conflict.

```scala
UUIDs.getNameTimeBasedBase64UUID
```

## Short ID

```scala
UUIDs.getShortUUID
```

## Number Based UUID

**Number Based UUID** is used to generate **unique**, **short**, **short id** by **number**, this can be used 
to generate like short url link, for avoid **guess**, we add the **salt** for generate **id**.

```scala
val id = UUIDs.NUMBER_BASED_UUID.getBase64IdByInt(123, "salt".getBytes())
//YHIrcQ
val number = UUIDs.NUMBER_BASED_UUID.decodeIntId(123, "salt".getBytes())
//123
```
