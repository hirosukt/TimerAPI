# timer-api
<img src="https://repo.hirosuke.me/api/badge/latest/snapshots/love/chihuyu/timer-api?color=40c14a&name=Reposilite"/>


An api easily create and coding as simple to timer.

## Usage

Add below to `build.gradle.kts`.
```kotlin
repositories {
    maven("https://repo.hirosuke.me/snapshots")
}
```
```kotlin
dependencies {
    compileOnly("love.chihuyu:timer-api:1.3.0-SNAPSHOT")
}
```

## Example

```kotlin
// this timer length is 180 * 20(1sec) = 3600tick(3min)
val timer = TimerAPI.build("wait ramen", duration = 180, period = 20, delay = 0)
    .start {
        hotWater.drip()
    }
    .tick {
        some()
    }
    .end {
        ramen.eat()
    }

timer.run()
```
