package WoWSFT.config

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.commander.Commander
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.flag.Flag
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.ShipIndex
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.*

@Component
@EnableConfigurationProperties(CustomProperties::class)
class CustomComponent {
    private val notification = LinkedHashMap<String, LinkedHashMap<String, String>>()
    private val translation = LinkedHashMap<String, LinkedHashMap<String, String>>()
    private val nameToId = HashMap<String, String>()
    private val idToName = HashMap<String, String>()
    private val global = HashMap<String, HashMap<String, Any>>()
    private val gameParamsHM = HashMap<String, Any>()
    private val loadFinish = HashMap<String, Int>()
    private val consumables = LinkedHashMap<String, Consumable>()
    private val shipsList = LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>()
    private val upgrades = LinkedHashMap<Int, LinkedHashMap<String, Modernization>>()
    private val commanders = LinkedHashMap<String, Commander>()
    private val flags = LinkedHashMap<String, Flag>()
    private val misc = LinkedHashMap<String, Any>()

    @Bean(TYPE_MISC)
    fun misc(): LinkedHashMap<String, Any> = misc

    @Bean(TYPE_CONSUMABLE)
    fun consumables(): LinkedHashMap<String, Consumable> = consumables

    @Bean(TYPE_SHIP_LIST)
    fun shipsList(): LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>> = shipsList

    @Bean(TYPE_UPGRADE)
    fun upgrades(): LinkedHashMap<Int, LinkedHashMap<String, Modernization>> = upgrades

    @Bean(TYPE_COMMANDER)
    fun commanders(): LinkedHashMap<String, Commander> = commanders

    @Bean(TYPE_FLAG)
    fun flags(): LinkedHashMap<String, Flag> = flags

    @Bean("nameToId")
    fun nameToId(): HashMap<String, String> = nameToId

    @Bean("idToName")
    fun idToName(): HashMap<String, String> = idToName

    @Bean(NOTIFICATION)
    fun notification(): LinkedHashMap<String, LinkedHashMap<String, String>> = notification

    @Bean("translation")
    fun translation(): LinkedHashMap<String, LinkedHashMap<String, String>> = translation

    @Bean(GLOBAL)
    fun global(): HashMap<String, HashMap<String, Any>> = global

    @Bean("gameParamsHM")
    fun gameParamsHM(): HashMap<String, Any> = gameParamsHM

    @Bean(LOAD_FINISH)
    fun loadFinish(): HashMap<String, Int> {
        loadFinish[LOAD_FINISH] = 0
        return loadFinish
    }
}