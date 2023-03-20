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
class Timer(val title: String, val duration: Long, val period: Long = 20, val delay: Long = 0) {

    var tick: Timer.() -> Unit = {}
    var start: Timer.() -> Unit = {}
    var end: Timer.() -> Unit = {}

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

    fun run() {
        var elapsed = 0L
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