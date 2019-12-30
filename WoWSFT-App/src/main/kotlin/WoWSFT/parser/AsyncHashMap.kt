package WoWSFT.parser

import WoWSFT.model.Constant.JSON_PARSER
import WoWSFT.model.Constant.LOAD_FINISH
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class AsyncHashMap(
    @Autowired @Qualifier(JSON_PARSER) private val jsonParser: JsonParser,
    @Autowired @Qualifier(LOAD_FINISH) private val loadFinish: HashMap<String, Int>
) : CommandLineRunner
{
    companion object {
        private val log = LoggerFactory.getLogger(AsyncHashMap::class.java)
    }

    @Throws(Exception::class)
    override fun run(vararg strings: String)
    {
        log.info("Start")

        val notification = jsonParser.setNotification()
        val global = jsonParser.setGlobal()
        val misc = jsonParser.setMisc()

        notification.get()
        global.get()
        misc.get()

        loadFinish[LOAD_FINISH] = 1
        log.info("finish")
    }

}