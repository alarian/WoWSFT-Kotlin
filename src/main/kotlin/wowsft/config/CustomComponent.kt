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
    @Bean(Constant.TYPE_SHIP)
    fun ships() = LinkedHashMap<String, Ship>()

    @Bean(Constant.TYPE_CONSUMABLE)
    fun consumables() = LinkedHashMap<String, Consumable>()

    @Bean(Constant.TYPE_SHIP_LIST)
    fun shipsList() = LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, MutableList<ShipIndex>>>>>()

    @Bean(Constant.TYPE_UPGRADE)
    fun upgrades() = LinkedHashMap<Int, LinkedHashMap<String, Modernization>>()

    @Bean(Constant.TYPE_COMMANDER)
    fun commanders() = LinkedHashMap<String, Commander>()

    @Bean(Constant.TYPE_FLAG)
    fun flags() = LinkedHashMap<String, Flag>()

    @Bean(Constant.jsonParser)
    fun jsonParser() = JsonParser()

    @Bean(Constant.nameToId)
    fun nameToId() = HashMap<String, String>()

    @Bean(Constant.idToName)
    fun idToName() = HashMap<String, String>()

    @Bean(Constant.notification)
    fun notification() = LinkedHashMap<String, LinkedHashMap<String, String>>()

    @Bean(Constant.translation)
    fun translation() = LinkedHashMap<String, LinkedHashMap<String, String>>()

    @Bean(Constant.global)
    fun global() = HashMap<String, HashMap<String, Any>>()

    @Bean(Constant.gameParamsHM)
    fun gameParamsHM() = HashMap<String, Any>()

    @Bean(Constant.loadFinish)
    fun loadFinish() = HashMap<String, Int>(hashMapOf(Constant.loadFinish to 0))
}
