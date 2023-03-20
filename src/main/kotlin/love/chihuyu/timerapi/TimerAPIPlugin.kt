package love.chihuyu.timerapi

import org.bukkit.plugin.java.JavaPlugin

class TimerAPIPlugin: JavaPlugin() {
    companion object {
        lateinit var TimerAPIPlugin: JavaPlugin
    }

    init {
        TimerAPIPlugin = this
    }

    override fun onEnable() {
        super.onEnable()
        logger.info("Plugin has loaded.")
    }

    override fun onDisable() {
        super.onDisable()
        logger.info("Plugin has unloaded.")
    }
}