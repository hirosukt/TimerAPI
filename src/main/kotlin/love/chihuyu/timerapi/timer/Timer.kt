package love.chihuyu.timerapi.timer

import love.chihuyu.timerapi.TimerAPI
import love.chihuyu.timerapi.TimerAPIPlugin
import love.chihuyu.timerapi.utils.Schedular.async
import love.chihuyu.timerapi.utils.Schedular.sync
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.CompletableFuture

/**
 * @param title title of timer.
 * @param duration tick length of timer (20tick/sec),
 * @param period span of per tick (default = 20).
 * @param delay ticks until start timer.
 */
open class Timer(var title: String, var duration: Long, var period: Long = 20, var delay: Long = 0) {

    var tick: Timer.() -> Unit = {}
    var start: Timer.() -> Unit = {}
    var end: Timer.() -> Unit = {}
    var elapsed = 0L

    lateinit var task: BukkitTask

    fun tick(builder: Timer.() -> Unit): Timer {
        return this.apply {
            tick = builder
        }
    }

    fun start(builder: Timer.() -> Unit): Timer {
        return this.apply {
            start = builder
        }
    }

    fun end(builder: Timer.() -> Unit): Timer {
        return this.apply {
            end = builder
        }
    }

    fun kill() {
        end.invoke(this@Timer)
        task.cancel()
        TimerAPI.unregister(task)
    }

    fun run() {
        task = TimerAPIPlugin.TimerAPIPlugin.sync(delay, period) {
            if (elapsed == 0L) {
                start.invoke(this@Timer)
            }

            elapsed++
            tick.invoke(this@Timer)

            if (elapsed == duration) {
                end.invoke(this@Timer)
                cancel()
                TimerAPI.unregister(task)
            }
        }
        TimerAPI.register(task)
    }

    fun runAsync() {
        var elapsed = 0L
        task = TimerAPIPlugin.TimerAPIPlugin.async(delay, period) {
            if (elapsed == 0L) {
                CompletableFuture<Unit>().complete(start.invoke(this@Timer))
            }

            elapsed++
            CompletableFuture<Unit>().complete(tick.invoke(this@Timer))

            if (elapsed == duration) {
                CompletableFuture<Unit>().complete(end.invoke(this@Timer))
                cancel()
            }
        }
    }
}