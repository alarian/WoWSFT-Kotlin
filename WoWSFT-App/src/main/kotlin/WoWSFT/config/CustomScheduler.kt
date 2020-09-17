package WoWSFT.config

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CustomScheduler {
    companion object {
        private val log = LoggerFactory.getLogger(CustomScheduler::class.java)
    }

//    @CacheEvict(allEntries = true, cacheNames = {"ship"})
//    @Scheduled(cron = "0 0 * * * *")
    fun cacheEvict() {
        log.info("Cache Evicted")
    }
}