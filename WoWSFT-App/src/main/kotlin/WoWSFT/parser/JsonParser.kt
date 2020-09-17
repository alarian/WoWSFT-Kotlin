package WoWSFT.parser

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.commander.Commander
import WoWSFT.model.gameparams.consumable.Consumable
import WoWSFT.model.gameparams.flag.Flag
import WoWSFT.model.gameparams.modernization.Modernization
import WoWSFT.model.gameparams.ship.ShipIndex
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.util.*
import java.util.zip.ZipFile

@Component(JSON_PARSER)
class JsonParser(
    @Qualifier(NOTIFICATION) private var notification: LinkedHashMap<String, LinkedHashMap<String, String>>,
    @Qualifier(GLOBAL) private var global: HashMap<String, HashMap<String, Any>>,
    @Qualifier(TYPE_SHIP) private var zShip: String,
    @Qualifier(TYPE_CONSUMABLE) private var consumables: LinkedHashMap<String, Consumable>,
    @Qualifier(TYPE_UPGRADE) private var upgrades: LinkedHashMap<Int, LinkedHashMap<String, Modernization>>,
    @Qualifier(TYPE_COMMANDER) private var commanders: LinkedHashMap<String, Commander>,
    @Qualifier(TYPE_FLAG) private var flags: LinkedHashMap<String, Flag>,
    @Qualifier(TYPE_SHIP_LIST) private var shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>
) {
    private val mapper = jacksonObjectMapper()

    companion object {
        private val log = LoggerFactory.getLogger(JsonParser::class.java)
    }

    fun setNotification() {
        log.info("Setting up Notification")

        for (language in globalLanguage) {
            ClassPathResource("/json/notification/notification-$language$FILE_JSON").run {
                notification[language] = mapper.readValue(url, jacksonTypeRef<LinkedHashMap<String, String>>())
            }
        }

        log.info("Notification Done")
    }

    fun setGlobal() {
        log.info("Setting up Global")

        for (language in globalLanguage) {
            ClassPathResource("/json/live/global-$language$FILE_JSON").run {
                global[language] = mapper.readValue(inputStream, jacksonTypeRef<HashMap<String, Any>>())
            }
        }

        log.info("Global Done")
    }

    fun setMisc() {
        log.info("Setting up Misc")
        val zFile = ZipFile(zShip)

        consumables.putAll(
            mapper.readValue(
                zFile.getInputStream(zFile.getEntry("$TYPE_CONSUMABLE$FILE_JSON")),
                jacksonTypeRef<LinkedHashMap<String, Consumable>>()
            )
        )

        upgrades.putAll(
            mapper.readValue(
                zFile.getInputStream(zFile.getEntry("$TYPE_UPGRADE$FILE_JSON")),
                jacksonTypeRef<LinkedHashMap<Int, LinkedHashMap<String, Modernization>>>()
            )
        )

        commanders.putAll(
            mapper.readValue(
                zFile.getInputStream(zFile.getEntry("$TYPE_COMMANDER$FILE_JSON")),
                jacksonTypeRef<LinkedHashMap<String, Commander>>()
            )
        )

        flags.putAll(
            mapper.readValue(
                zFile.getInputStream(zFile.getEntry("$TYPE_FLAG$FILE_JSON")),
                jacksonTypeRef<LinkedHashMap<String, Flag>>()
            )
        )

        shipsList.putAll(
            mapper.readValue(
                zFile.getInputStream(zFile.getEntry("$TYPE_SHIP_LIST$FILE_JSON")),
                jacksonTypeRef<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>>()
            )
        )

        zFile.close()

        log.info("Misc Done")
    }
}