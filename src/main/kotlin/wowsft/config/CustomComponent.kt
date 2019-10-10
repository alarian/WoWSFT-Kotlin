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
class CustomComponent
{
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
    private val shipsList = LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, MutableList<ShipIndex>>>>>()
    private val upgrades = LinkedHashMap<Int, LinkedHashMap<String, Modernization>>()
    private val commanders = LinkedHashMap<String, Commander>()
    private val flags = LinkedHashMap<String, Flag>()

    @Bean(Constant.TYPE_SHIP)
    fun ships(): LinkedHashMap<String, Ship> {
        return ships
    }

    @Bean(Constant.TYPE_CONSUMABLE)
    fun consumables(): LinkedHashMap<String, Consumable> {
        return consumables
    }

    @Bean(Constant.TYPE_SHIP_LIST)
    fun shipsList(): LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, MutableList<ShipIndex>>>>> {
        return shipsList
    }

    @Bean(Constant.TYPE_UPGRADE)
    fun upgrades(): LinkedHashMap<Int, LinkedHashMap<String, Modernization>> {
        return upgrades
    }

    @Bean(Constant.TYPE_COMMANDER)
    fun commanders(): LinkedHashMap<String, Commander> {
        return commanders
    }

    @Bean(Constant.TYPE_FLAG)
    fun flags(): LinkedHashMap<String, Flag> {
        return flags
    }

    @Bean(Constant.jsonParser)
    fun jsonParser(): JsonParser {
        return jsonParser
    }

    @Bean(Constant.nameToId)
    fun nameToId(): HashMap<String, String> {
        return nameToId
    }

    @Bean(Constant.idToName)
    fun idToName(): HashMap<String, String> {
        return idToName
    }

    @Bean(Constant.notification)
    fun notification(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        return notification
    }

    @Bean(Constant.translation)
    fun translation(): LinkedHashMap<String, LinkedHashMap<String, String>> {
        return translation
    }

    @Bean(Constant.global)
    fun global(): HashMap<String, HashMap<String, Any>> {
        return global
    }

    @Bean(Constant.gameParamsHM)
    fun gameParamsHM(): HashMap<String, Any> {
        return gameParamsHM
    }

    @Bean(Constant.loadFinish)
    fun loadFinish(): HashMap<String, Int> {
        loadFinish[Constant.loadFinish] = 0
        return loadFinish
    }
}
