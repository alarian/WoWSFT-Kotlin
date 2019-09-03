package wowsft.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import wowsft.model.Constant

import java.util.HashMap
import java.util.LinkedHashMap

import wowsft.model.gameparams.commander.Commander
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.flag.Flag
import wowsft.model.gameparams.modernization.Modernization
import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.ShipIndex
import wowsft.parser.JsonParser

/**
 * Created by Aesis on 2016-10-15.
 */
@Component
@EnableConfigurationProperties(CustomProperties::class)
class CustomComponent {
    private val jsonParser = JsonParser()
    private val notification = LinkedHashMap<String, LinkedHashMap<String, String>>()
    private val translation = LinkedHashMap<String, LinkedHashMap<String, String>>()
    private val nameToId = HashMap<String, String>()
    private val idToName = HashMap<String, String>()
    private val global = HashMap<String, HashMap<String, Any>>()
    private val gameParamsHM = HashMap<String, Any>()
    private val loadFinish = HashMap<String, Int>()
    private val ships = LinkedHashMap<String, Ship>()
    private val consumables = LinkedHashMap<String, Consumable>()
    private val shipsList = LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>()
    private val upgrades = LinkedHashMap<Int, LinkedHashMap<String, Modernization>>()
    private val commanders = LinkedHashMap<String, Commander>()
    private val flags = LinkedHashMap<String, Flag>()

    @Bean(value = [Constant.TYPE_SHIP])
    fun ships(): LinkedHashMap<String, Ship> {
        return ships
    }

    @Bean(value = [Constant.TYPE_CONSUMABLE])
    fun consumables(): LinkedHashMap<String, Consumable> {
        return consumables
    }

    @Bean(value = [Constant.TYPE_SHIP_LIST])
    fun shipsList(): LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>> {
        return shipsList
    }

    @Bean(value = [Constant.TYPE_UPGRADE])
    fun upgrades(): LinkedHashMap<Int, LinkedHashMap<String, Modernization>> {
        return upgrades
    }

    @Bean(value = [Constant.TYPE_COMMANDER])
    fun commanders(): LinkedHashMap<String, Commander> {
        return commanders
    }

    @Bean(value = [Constant.TYPE_FLAG])
    fun flags(): LinkedHashMap<String, Flag> {
        return flags
    }

    @Bean(value = [Constant.jsonParser])
    fun jsonParser(): JsonParser {
        return jsonParser
    }

    @Bean(value = [Constant.nameToId])
    fun nameToId(): HashMap<String, String> {
        return nameToId
    }

    @Bean(value = [Constant.idToName])
    fun idToName(): HashMap<String, String> {
        return idToName
    }

    @Bean(value = [Constant.notification])
    fun notification(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        return notification
    }

    @Bean(value = [Constant.translation])
    fun translation(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        return translation
    }

    @Bean(value = [Constant.global])
    fun global(): HashMap<String, HashMap<String, Any>> {
        return global
    }

    @Bean(value = [Constant.gameParamsHM])
    fun gameParamsHM(): HashMap<String, Any> {
        return gameParamsHM
    }

    @Bean(name = [Constant.loadFinish])
    fun loadFinish(): HashMap<String, Int> {
        loadFinish[Constant.loadFinish] = 0
        return loadFinish
    }
}
