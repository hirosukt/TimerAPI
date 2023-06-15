package love.chihuyu.timerapi

import love.chihuyu.timerapi.timer.LoopTimer
import love.chihuyu.timerapi.timer.Timer
import org.bukkit.scheduler.BukkitTask

object TimerAPI {

    private val timerStorage = mutableListOf<BukkitTask>()

    fun build(title: String, duration: Long, period: Long = 20, delay: Long = 0, builder: Timer.() -> Unit = {}): Timer {
        return Timer(title, duration, period, delay).apply(builder)
    }

    fun buildLoop(title: String, period: Long = 20, delay: Long = 0, builder: LoopTimer.() -> Unit = {}): LoopTimer {
        return LoopTimer(title, period, delay).apply(builder)
    }

    fun run(title: String, duration: Long, period: Long = 20, delay: Long = 0, builder: Timer.() -> Unit = {}) {
        Timer(title, duration, period, delay).apply(builder).run()
    }

    fun runLoop(title: String, period: Long = 20, delay: Long = 0, builder: LoopTimer.() -> Unit = {}) {
        LoopTimer(title, period, delay).apply(builder).run()
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