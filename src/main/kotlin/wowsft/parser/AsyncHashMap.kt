package wowsft.parser

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

import java.io.IOException
import java.util.HashMap

/**
 * Created by Aesis on 2016-11-15.
 */
@Component
class AsyncHashMap(
        @Autowired @Qualifier(value = "jsonParser") private val jsonParser: JsonParser,
        @Autowired @Qualifier(value = "loadFinish") private val loadFinish: HashMap<String, Int>
) : CommandLineRunner
{
    companion object {
        private val log = LoggerFactory.getLogger(AsyncHashMap::class.java)
    }

    @Throws(IOException::class)
    override fun run(vararg strings: String)
    {
        log.info("Start")

        jsonParser.setTranslation()
        jsonParser.setNotification()
        jsonParser.setGlobal()
        jsonParser.setGameParams()

        loadFinish["loadFinish"] = 1
        log.info("finish")
    }
}
