package WoWSFT.parser

import WoWSFT.model.Constant.JSON_PARSER
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class DataGenerator(
    @Qualifier(JSON_PARSER) private val jsonParser: JsonParser,
    private var context: ApplicationContext
): CommandLineRunner, ApplicationContextAware
{
    companion object {
        private val log = LoggerFactory.getLogger(DataGenerator::class.java)
    }

    @Throws(IOException::class)
    override fun run(vararg strings: String)
    {
        log.info("Start")

        jsonParser.setGlobal()
        jsonParser.setGameParams()
        jsonParser.generateShipData()
        jsonParser.generateShellPenetration()

        log.info("finish")

        (context as ConfigurableApplicationContext?)!!.close()
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(context: ApplicationContext)
    {
        this.context = context
    }

}