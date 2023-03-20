package love.chihuyu.timerapi

import org.bukkit.scheduler.BukkitTask

object TimerAPI {

    private val timerStorage = mutableListOf<BukkitTask>()

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