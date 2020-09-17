package WoWSFT.parser

import WoWSFT.model.Constant.JSON_PARSER
import WoWSFT.model.Constant.LOAD_FINISH
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import java.util.*

@Component
class AsyncHashMap(
    @Qualifier(JSON_PARSER) private val jsonParser: JsonParser,
    @Qualifier(LOAD_FINISH) private val loadFinish: HashMap<String, Int>,
    private val executor: ThreadPoolTaskExecutor
) : CommandLineRunner {
    companion object {
        private val log = LoggerFactory.getLogger(AsyncHashMap::class.java)
        private val job = SupervisorJob()
    }

    @Throws(Exception::class)
    override fun run(vararg strings: String) {
        log.info("Start")

        val scope = CoroutineScope(executor.asCoroutineDispatcher() + job)
        runBlocking {
            val deferred = listOf(
                scope.async { jsonParser.setNotification() },
                scope.async { jsonParser.setGlobal() },
                scope.async { jsonParser.setMisc() }
            )
            deferred.awaitAll()
        }

        loadFinish[LOAD_FINISH] = 1
        log.info("finish")
    }

}