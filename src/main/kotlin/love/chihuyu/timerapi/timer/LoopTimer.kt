package love.chihuyu.timerapi.timer

import love.chihuyu.timerapi.TimerAPI
import love.chihuyu.timerapi.TimerAPIPlugin
import love.chihuyu.timerapi.utils.Schedular.async
import love.chihuyu.timerapi.utils.Schedular.sync
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.CompletableFuture

class LoopTimer(var title: String, var period: Long = 20, var delay: Long = 0) {

    var tick: LoopTimer.() -> Unit = {}
    var start: LoopTimer.() -> Unit = {}
    var end: LoopTimer.() -> Unit = {}
    var elapsed = 0L

    lateinit var task: BukkitTask

    fun tick(builder: LoopTimer.() -> Unit): LoopTimer {
        return this.apply {
            tick = builder
        }
    }

    fun start(builder: LoopTimer.() -> Unit): LoopTimer {
        return this.apply {
            start = builder
        }
    }

    fun kill() {
        end.invoke(this@LoopTimer)
        task.cancel()
        TimerAPI.unregister(task)
    }

    fun run() {
        task = TimerAPIPlugin.TimerAPIPlugin.sync(delay, period) {
            if (elapsed == 0L) {
                start.invoke(this@LoopTimer)
            }

            elapsed++
            tick.invoke(this@LoopTimer)
        }
        TimerAPI.register(task)
    }

    fun runAsync() {
        var elapsed = 0L
        task = TimerAPIPlugin.TimerAPIPlugin.async(delay, period) {
            if (elapsed == 0L) {
                CompletableFuture<Unit>().complete(start.invoke(this@LoopTimer))
            }

            elapsed++
            CompletableFuture<Unit>().complete(tick.invoke(this@LoopTimer))
        }
    }
}