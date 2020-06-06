package WoWSFT.parser

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.commander.Commander
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.flag.Flag
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.ShipIndex
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Async
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.zip.ZipFile

open class JsonParser
{
    @Autowired @Qualifier(NOTIFICATION)
    private lateinit var notification: LinkedHashMap<String, LinkedHashMap<String, String>>
    @Autowired @Qualifier(GLOBAL)
    private lateinit var global: HashMap<String, HashMap<String, Any>>
    @Autowired @Qualifier(TYPE_SHIP)
    private lateinit var zShip: ZipFile
    @Autowired @Qualifier(TYPE_CONSUMABLE)
    private lateinit var consumables: LinkedHashMap<String, Consumable>
    @Autowired @Qualifier(TYPE_UPGRADE)
    private lateinit var upgrades: LinkedHashMap<Int, LinkedHashMap<String, Modernization>>
    @Autowired @Qualifier(TYPE_COMMANDER)
    private lateinit var commanders: LinkedHashMap<String, Commander>
    @Autowired @Qualifier(TYPE_FLAG)
    private lateinit var flags: LinkedHashMap<String, Flag>
    @Autowired @Qualifier(TYPE_SHIP_LIST)
    private lateinit var shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>

    private val mapper = ObjectMapper()

    companion object {
        private val log = LoggerFactory.getLogger(JsonParser::class.java)
    }

    @Async
    open fun setNotification(): CompletableFuture<String>
    {
        log.info("Setting up notification")

        for (language in globalLanguage) {
            val notificationFile = ClassPathResource("/json/notification/notification-$language$FILE_JSON")
            val temp = mapper.readValue(notificationFile.url, object : TypeReference<LinkedHashMap<String, String>>() {})
            notification[language] = temp
        }

        log.info("Notification Done")
        return CompletableFuture.completedFuture("Done")
    }

    @Async
    open fun setGlobal(): CompletableFuture<String>
    {
        log.info("Setting up Global")

        for (language in globalLanguage) {
            val globalFile = ClassPathResource("/json/live/global-$language$FILE_JSON")
            val temp = mapper.readValue(globalFile.inputStream, object : TypeReference<HashMap<String, Any>>() {})
            global[language] = temp
        }

        log.info("Global Done")
        return CompletableFuture.completedFuture("Done")
    }

    @Async
    @Throws(IOException::class)
    open fun setMisc(): CompletableFuture<String>
    {
        log.info("Setting up Misc")

        val tempConsumables = mapper.readValue(zShip.getInputStream(zShip.getEntry("$TYPE_CONSUMABLE$FILE_JSON")),
            object : TypeReference<LinkedHashMap<String, Consumable>>() {})
        consumables.putAll(tempConsumables)

        val tempUpgrades = mapper.readValue(zShip.getInputStream(zShip.getEntry("$TYPE_UPGRADE$FILE_JSON")),
            object : TypeReference<LinkedHashMap<Int, LinkedHashMap<String, Modernization>>>() {})
        upgrades.putAll(tempUpgrades)

        val tempCommanders = mapper.readValue(zShip.getInputStream(zShip.getEntry("$TYPE_COMMANDER$FILE_JSON")),
            object : TypeReference<LinkedHashMap<String, Commander>>() {})
        commanders.putAll(tempCommanders)

        val tempFlags = mapper.readValue(zShip.getInputStream(zShip.getEntry("$TYPE_FLAG$FILE_JSON")),
            object : TypeReference<LinkedHashMap<String, Flag>>() {})
        flags.putAll(tempFlags)

        val tempShipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>
                = mapper.readValue(zShip.getInputStream(zShip.getEntry("$TYPE_SHIP_LIST$FILE_JSON")),
            object : TypeReference<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>>() {})
        shipsList.putAll(tempShipsList)

        log.info("Misc Done")
        return CompletableFuture.completedFuture("Done")
    }
}