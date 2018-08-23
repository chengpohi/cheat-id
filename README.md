# cheat-id
cheat id 

# UUID

## RANDOM UUID

**Version 3** random UUID

```java
UUIDs.getBase64UUID
```

## Time Based UUID

This will generate time based uuid, and it's continuous increment by the time

```java
UUIDs.getTimeBasedBase64UUID
```

## Name Time Based UUID

This will generate base on the **name**, it can be used with actual **user id**, 
so every user will generate distinct user id, never conflict.

```java
UUIDs.getNameTimeBasedBase64UUID
```

# Short ID

```java
UUIDs.getShortUUID
```
