package wowsft.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "wowsft")
open class CustomProperties {
    var language: String? = null
    var server: String? = null
    var globalLanguage: String? = null
    var protocol: String? = null
    var env: String? = null
}
