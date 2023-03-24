package love.chihuyu.timerapi

import love.chihuyu.timerapi.timer.Timer
import org.bukkit.scheduler.BukkitTask

object TimerAPI {

    private val timerStorage = mutableListOf<BukkitTask>()

    fun build(title: String, duration: Long, period: Long = 20, delay: Long = 0, builder: Timer.() -> Unit = {}): Timer {
        return Timer(title, duration, period, delay).apply(builder)
    }

    fun all(): MutableList<BukkitTask> {
        return timerStorage
    }

    fun register(timerTask: BukkitTask): MutableList<BukkitTask> {
        timerStorage += timerTask
        return timerStorage
    }

    fun unregister(timerTask: BukkitTask): MutableList<BukkitTask> {
        timerStorage -= timerTask
        return timerStorage
    }
}