package WoWSFT.controller

import WoWSFT.model.Constant.*
import WoWSFT.model.gameparams.commander.Commander
import WoWSFT.model.gameparams.ship.Ship
import WoWSFT.model.gameparams.ship.ShipIndex
import WoWSFT.model.gameparams.ship.component.artillery.Shell
import WoWSFT.service.GPService
import WoWSFT.service.ParamService
import WoWSFT.service.ParserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class GPController(
    @Autowired @Qualifier(LOAD_FINISH) private val loadFinish: HashMap<String, Int>,
    @Autowired @Qualifier(NOTIFICATION) private val notification: LinkedHashMap<String, LinkedHashMap<String, String>>,
    @Autowired @Qualifier(GLOBAL) private val global: HashMap<String, HashMap<String, Any>>,
    @Autowired @Qualifier(TYPE_SHIP_LIST) private val shipsList: LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<Int, List<ShipIndex>>>>>,
    @Autowired @Qualifier(TYPE_COMMANDER) private val commanders: LinkedHashMap<String, Commander>,
    @Autowired private val gpService: GPService,
    @Autowired private val paramService: ParamService,
    @Autowired private val parserService: ParserService
) : ExceptionController()
{
    private val mapper = ObjectMapper()

    companion object {
        private const val lang = EN
    }

    @ModelAttribute(name = "language")
    fun setLanguage(model: Model)
    {
        model.addAttribute("lang", lang)
    }

    @GetMapping("")
    fun getHome(model: Model): String
    {
        if (loadFinish[LOAD_FINISH] == 0) {
            return "loadPage"
        }
        model.addAttribute(NOTIFICATION, notification[lang])
        return "home"
    }

    @RequestMapping("/ship", method = [RequestMethod.GET, RequestMethod.POST])
    @Throws(Exception::class)
    fun getWarship(
        request: HttpServletRequest,
        model: Model,
        @RequestParam(required = false, defaultValue = "") index: String,
        @RequestParam(required = false, defaultValue = "") modules: String,
        @RequestParam(required = false, defaultValue = "") upgrades: String,
        @RequestParam(required = false, defaultValue = "") consumables: String,
        @RequestParam(required = false, defaultValue = "PCW001") commander: String,
        @RequestParam(required = false, defaultValue = "0") skills: Long,
        @RequestParam(required = false, defaultValue = "100") ar: Int
    ): String
    {
        val sSkills: Long

        model.addAttribute("single", true)
        model.addAttribute(IDS, IDS_)
        model.addAttribute(GLOBAL, global[lang])

        if (index.isNotEmpty()) {
            model.addAttribute("index", index.toUpperCase())
            model.addAttribute("dataIndex", 0)
            model.addAttribute("commanders", commanders)
            sSkills = if (skills > maxBitsToInt) 0 else skills
            val ship = getShip(index.toUpperCase(), modules, upgrades, consumables, sSkills, commander.toUpperCase(), ar)
            model.addAttribute(TYPE_WARSHIP, ship)

            if ("post".equals(request.method, ignoreCase = true)) {
                return "Joint/rightInfo :: rightInfo"
            }
        }
        model.addAttribute("nations", shipsList)

        return "FittingTool/ftHome"
    }

    @Throws(Exception::class)
    private fun getShip(index: String, modules: String, upgrades: String, consumables: String, skills: Long, commander: String, ar: Int): Ship
    {
        var sCommander = commander
        val ship = mapper.readValue(mapper.writeValueAsString(gpService.getShip(index)), Ship::class.java)

        parserService.parseModules(ship, modules)
        gpService.setShipAmmo(ship)
        parserService.parseConsumables(ship, consumables)
        parserService.parseUpgrades(ship, upgrades)
        parserService.parseSkills(ship, skills, ar)
        paramService.setAA(ship)

        if ("PCW001" != sCommander && (commanders[sCommander] == null || !commanders[sCommander]!!.crewPersonality.ships.nation.contains(ship.typeinfo.nation))) {
            sCommander = "PCW001"
        }

        ship.commander = commanders[sCommander]
        paramService.setParameters(ship)

        return ship
    }

    @GetMapping("/arty")
    fun getArtyChart(model: Model): String
    {
        model.addAttribute(IDS, IDS_)
        model.addAttribute(GLOBAL, global[lang])
        model.addAttribute("nations", shipsList)

        return "ArtyChart/acHome"
    }

    @ResponseBody
    @PostMapping("/arty")
    @Throws(Exception::class)
    fun getShellData(@RequestParam index: String, @RequestParam artyId: String): Shell?
    {
        return gpService.getArtyAmmoOnly(index, artyId)
    }

    @GetMapping("/research")
    fun getResearch(model: Model): String
    {
        model.addAttribute(GLOBAL, global[lang])
        model.addAttribute(IDS, IDS_)
        model.addAttribute("nations", shipsList)

        return "Research/shipTree"
    }
}