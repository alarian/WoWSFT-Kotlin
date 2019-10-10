package wowsft.controller

import wowsft.model.gameparams.commander.Commander
import wowsft.model.gameparams.consumable.Consumable
import wowsft.model.gameparams.flag.Flag
import wowsft.model.gameparams.modernization.Modernization
import wowsft.model.gameparams.ship.Ship
import wowsft.model.gameparams.ship.ShipIndex
import wowsft.model.gameparams.ship.component.artillery.Shell
import wowsft.service.GPService
import wowsft.service.ParamService
import wowsft.service.ParserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import wowsft.model.Constant

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.HashMap
import java.util.LinkedHashMap

/**
 * Created by Aesis on 2016-10-15.
 */
@Controller
class GPController(
        @Autowired @Qualifier("loadFinish") private val loadFinish: HashMap<String, Int>,
        @Autowired @Qualifier("gameParamsHM") private val gameParamsHM: HashMap<String, Any>,
        @Autowired @Qualifier("notification") private val notification: LinkedHashMap<String, LinkedHashMap<String, String>>,
        @Autowired @Qualifier("translation") private val translation: LinkedHashMap<String, LinkedHashMap<String, String>>,
        @Autowired @Qualifier("global") private val global: HashMap<String, HashMap<String, Any>>,
        @Autowired @Qualifier(Constant.TYPE_SHIP) private val ships: LinkedHashMap<String, Ship>,
        @Autowired @Qualifier(Constant.TYPE_CONSUMABLE) private val consumables: LinkedHashMap<String, Consumable>,
        @Autowired @Qualifier(Constant.TYPE_SHIP_LIST) private val shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>,
        @Autowired @Qualifier(Constant.TYPE_UPGRADE) private val upgrades: LinkedHashMap<Int, LinkedHashMap<String, Modernization>>,
        @Autowired @Qualifier(Constant.TYPE_COMMANDER) private val commanders: LinkedHashMap<String, Commander>,
        @Autowired @Qualifier(Constant.TYPE_FLAG) private val flags: LinkedHashMap<String, Flag>,
        @Autowired private val gpService: GPService,
        @Autowired private val paramService: ParamService,
        @Autowired private val parserService: ParserService
) : ExceptionController()
{
    private val mapper = ObjectMapper()

    @ModelAttribute("language")
    fun setLanguage(model: Model, request: HttpServletRequest, response: HttpServletResponse)
    {
        val lang = request.parameterMap["lang"]
        if (lang.isNullOrEmpty()) {
            model.addAttribute("lang", "en")
        } else {
            var l = "en"
            for (s in lang) {
                if (Constant.globalLanguage.contains(s.toLowerCase())) {
                    l = s
                    break
                }
            }
            model.addAttribute("lang", l)
        }
    }

    @ResponseBody
    @GetMapping("/data")
    @Throws(Exception::class)
    fun tester(@RequestParam(required = false, defaultValue = "") type: String,
               @RequestParam(required = false, defaultValue = "") index: String,
               @RequestParam(required = false, defaultValue = "") lang: String): Any?
    {
        return when {
            type.equals(Constant.TYPE_SHIP, ignoreCase = true) -> if (StringUtils.isNotEmpty(lang)) gpService.getShip(index) else ships[index]
            type.equals(Constant.TYPE_UPGRADE, ignoreCase = true) -> upgrades
            type.equals(Constant.TYPE_CONSUMABLE, ignoreCase = true) -> consumables
            type.equals(Constant.TYPE_COMMANDER, ignoreCase = true) -> commanders
            type.equals(Constant.TYPE_SHIP_LIST, ignoreCase = true) -> shipsList
            type.equals(Constant.TYPE_FLAG, ignoreCase = true) -> flags
            else -> gameParamsHM[index]
        }
    }

    @GetMapping("")
    fun getHome(model: Model, @RequestParam(required = false, defaultValue = "en") lang: String): String
    {
        if (loadFinish["loadFinish"] == 0) {
            return "loadPage"
        }

        model.addAttribute("notification", notification[lang.toLowerCase()])
        model.addAttribute("trans", translation[lang.toLowerCase()])

        return "home"
    }

    @RequestMapping("/ship", method = [RequestMethod.GET, RequestMethod.POST])
    @Throws(Exception::class)
    fun getWarship(request: HttpServletRequest, model: Model,
                   @RequestParam(required = false, defaultValue = "en") lang: String,
                   @RequestParam(required = false, defaultValue = "") index: String,
                   @RequestParam(required = false, defaultValue = "") modules: String,
                   @RequestParam(required = false, defaultValue = "") upgrades: String,
                   @RequestParam(required = false, defaultValue = "") consumables: String,
                   @RequestParam(required = false, defaultValue = "PCW001") commander: String,
                   @RequestParam(required = false, defaultValue = "0") skills: Long,
                   @RequestParam(required = false, defaultValue = "100") ar: Int): String
    {
        model.addAttribute("single", true)
        model.addAttribute("IDS", Constant.IDS)

        if (!"en".equals(lang.toLowerCase(), ignoreCase = true)) {
            model.addAttribute("english", global["en"])
        }
        model.addAttribute("global", global[lang.toLowerCase()])
        model.addAttribute("trans", translation[lang.toLowerCase()])

        if (StringUtils.isNotEmpty(index)) {
            model.addAttribute("index", index.toUpperCase())
            model.addAttribute("dataIndex", 0)
            model.addAttribute("commanders", commanders)

            val ship = getShip(index.toUpperCase(), modules, upgrades, consumables, if (skills > Constant.maxBitsToInt) 0 else skills, commander.toUpperCase(), ar, false)
            model.addAttribute(Constant.TYPE_WARSHIP, ship)

//            log.info(request.getRequestURL() + (StringUtils.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));

            if ("post".equals(request.method, ignoreCase = true)) {
                return "Joint/rightInfo :: rightInfo"
//                return "Joint/shipSelect :: warshipStats";
            }
        }
        model.addAttribute("nations", shipsList)

        return "FittingTool/ftHome"
    }

    @ResponseBody
    @PostMapping("/shipData")
    @Throws(Exception::class)
    fun getWarshipData(@RequestParam(required = false, defaultValue = "en") lang: String,
                       @RequestParam(required = false, defaultValue = "") index: String,
                       @RequestParam(required = false, defaultValue = "") modules: String,
                       @RequestParam(required = false, defaultValue = "") upgrades: String,
                       @RequestParam(required = false, defaultValue = "") consumables: String,
                       @RequestParam(required = false, defaultValue = "PCW001") commander: String,
                       @RequestParam(required = false, defaultValue = "0") skills: Long): Ship
    {
        if (StringUtils.isNotEmpty(index)) {
            return getShip(index.toUpperCase(), modules, upgrades, consumables, skills, commander.toUpperCase(), 100, true)
        }
        throw NullPointerException()
    }

    @GetMapping("/WarshipStats")
    @Throws(Exception::class)
    fun legacyUrl(request: HttpServletRequest, response: HttpServletResponse): String
    {
        val url = parserService.parseLegacyUrl(request)

        return "redirect:$url"
    }

    @Throws(Exception::class)
    private fun getShip(index: String, modules: String, upgrades: String, consumables: String, skills: Long, commander: String, ar: Int, data: Boolean): Ship
    {
        var tCommander = commander
        val ship = mapper.readValue(mapper.writeValueAsString(gpService.getShip(index)), Ship::class.java)
        parserService.parseModules(ship, modules)
        gpService.setShipAmmo(ship)
        parserService.parseConsumables(ship, consumables)
        parserService.parseUpgrades(ship, upgrades)
        parserService.parseSkills(ship, skills, ar)
        paramService.setAA(ship)

        if ("PCW001" != commander && (commanders[commander] == null || !commanders[commander]!!.crewPersonality?.ships?.nation?.contains(ship.typeinfo!!.nation)!!)) {
            tCommander = "PCW001"
        }
        ship.commander = commanders[tCommander]!!
        paramService.setParameters(ship)

        return ship
    }

    @GetMapping("/arty")
    fun getArtyChart(model: Model,
                     @RequestParam(required = false, defaultValue = "en") lang: String): String
    {
        model.addAttribute("IDS", Constant.IDS)
        model.addAttribute("global", global[lang.toLowerCase()])
        if (!"en".equals(lang.toLowerCase(), ignoreCase = true)) {
            model.addAttribute("english", global["en"])
        }
        model.addAttribute("trans", translation[lang.toLowerCase()])
        model.addAttribute("nations", shipsList)

        return "ArtyChart/acHome"
    }

    @ResponseBody
    @PostMapping("/arty")
    @Throws(Exception::class)
    fun getShellData(@RequestParam index: String, @RequestParam artyId: String): Shell? {
        return gpService
                .getArtyAmmoOnly(index, artyId)
    }

    @GetMapping("/research")
    fun getResearch(model: Model,
                    @RequestParam(required = false, defaultValue = "en") lang: String): String
    {
        model.addAttribute("global", global[lang.toLowerCase()])
        if (!"en".equals(lang.toLowerCase(), ignoreCase = true)) {
            model.addAttribute("english", global["en"])
        }
        model.addAttribute("trans", translation[lang.toLowerCase().toLowerCase()])
        model.addAttribute("IDS", Constant.IDS)
        model.addAttribute("nations", shipsList)

        return "Research/shipTree"
    }
}