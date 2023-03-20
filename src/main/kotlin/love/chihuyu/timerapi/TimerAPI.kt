package love.chihuyu.timerapi

import love.chihuyu.timerapi.timer.Timer

object TimerAPI {

    private val timerStorage = mutableListOf<Timer>()

    fun all(): MutableList<Timer> {
        return timerStorage
    }

    fun register(timer: Timer): MutableList<Timer> {
        timerStorage += timer
        return timerStorage
    }

    fun unregister(timer: Timer): MutableList<Timer> {
        timerStorage -= timer
        return timerStorage
    }
}