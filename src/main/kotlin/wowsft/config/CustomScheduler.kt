package wowsft.config

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component

class CustomScheduler {
    companion object {
        private val log = LoggerFactory.getLogger(CustomScheduler::class.java)
    }

    @CacheEvict(allEntries = true, cacheNames = ["ship"])
    @Scheduled(cron = "0 0 * * * *")
    fun cacheEvict() {
        log.info("Cache Evicted")
    }
}