package WoWSFT.config

import WoWSFT.model.BlockIp
import WoWSFT.model.Constant.CDN
import WoWSFT.model.Constant.LOAD_FINISH
import WoWSFT.model.Constant.SLASH
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import java.io.IOException
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Configuration
@EnableConfigurationProperties(CustomProperties::class)
@ComponentScan("WoWSFT")
class CustomFilter(
    @Autowired @Qualifier(LOAD_FINISH) private val loadFinish: HashMap<String, Int>,
    @Autowired private val customProperties: CustomProperties
) : Filter
{
    companion object {
        private val log = LoggerFactory.getLogger(CustomFilter::class.java)

        private val blockIP = HashSet<String>()
        private val ipMap = HashMap<String, BlockIp>()
        private val ignoreUri = HashSet<String>()
        private const val headerSrc = "'self' $CDN"
        private const val googleSrc =
            "https://tagmanager.google.com/ https://www.googletagmanager.com/ " +
            "https://www.gstatic.com/ fonts.googleapis.com/ https://www.google-analytics.com/"
//        private const val headerUnsafe = "";
        private const val headerUnsafe = "'unsafe-inline'"
        private const val none = "'none'"

        private const val contentSecurityPolicy =
            "default-src $none;" +
            "object-src $none;" +
            "connect-src 'self';" +
            "base-uri 'self';" +
            "img-src $headerSrc https://ssl.gstatic.com/ https://www.google-analytics.com/;" +
            "script-src $headerUnsafe $headerSrc $googleSrc data:;" +
            "style-src $headerUnsafe $headerSrc $googleSrc;" +
            "font-src https://tagmanager.google.com/ https://fonts.gstatic.com/;" +
            "form-action $none;frame-ancestors $none"

        init {
            ignoreUri.add("/favicon")
            ignoreUri.add("/js")
            ignoreUri.add("/css")
            ignoreUri.add("/images")
            ignoreUri.add("/sitemap")
        }
    }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {

    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain)
    {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        if (isRelease) {
            response.setHeader("Content-Security-Policy", contentSecurityPolicy)
            response.setHeader("Strict-Transport-Security", "max-age=15768000; includeSubDomains")
            response.setHeader("X-Content-Type-Options", "nosniff")
            response.setHeader("X-Frame-Options", "DENY")
            response.setHeader("X-XSS-Protection", "1; mode=block")
        }

        if (!(HttpMethod.GET.name.equals(request.method, ignoreCase = true)
            || HttpMethod.POST.name.equals(request.method, ignoreCase = true)
            || HttpMethod.HEAD.name.equals(request.method, ignoreCase = true))) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        val url = request.requestURL.toString()
        val uri = request.requestURI
        val queryString = request.queryString

        if (loadFinish[LOAD_FINISH] == 0 && !SLASH.equals(uri, ignoreCase = true) && isNotIgnore(uri)) {
            request.getRequestDispatcher(SLASH).forward(request, response)
            return
        }

        val ipAddress = getClientIPAddress(request)
        if (isRelease && isNotIgnore(request.requestURI) && loadFinish[LOAD_FINISH] != 0) {
            if (!ipMap.containsKey(ipAddress)) {
                ipMap[ipAddress!!] = BlockIp(ipAddress)
            } else {
                if (ipMap[ipAddress]!!.blockCount < 3) {
                    if (ipMap[ipAddress]!!.count < 10) {
                        ipMap[ipAddress]!!.doCount()
                    } else {
                        if (System.currentTimeMillis() - ipMap[ipAddress]!!.created.time < 15 * 1000) {
                            if (!blockIP.contains(ipAddress)) {
                                ipMap[ipAddress]?.also {
                                    it.created = Date()
                                    it.blockCreated = Date()
                                    it.addBlockCount()
                                }
                                blockIP.add(ipAddress!!)

                                log.error("Blocked: $ipAddress, count: ${ipMap[ipAddress]?.blockCount}")
                            }
                        } else {
                            blockIP.remove(ipAddress)
                            ipMap[ipAddress]!!.reset()
                        }
                    }
                } else {
                    if (System.currentTimeMillis() - ipMap[ipAddress]!!.blockCreated!!.time > 60 * 60 * 1000) {
                        blockIP.remove(ipAddress)
                        ipMap[ipAddress]!!.resetBlock()
                    }
                }
            }
        }

        if (blockIP.contains(ipAddress)) {
            response.status = HttpServletResponse.SC_NOT_FOUND
            return
        }

        chain.doFilter(req, res)
    }

    override fun destroy() {

    }

    private fun isNotIgnore(address: String): Boolean {
        return ignoreUri.stream()
            .noneMatch { ig: String? ->
                address.toLowerCase().contains(ig!!)
            }
    }

    private fun getClientIPAddress(request: HttpServletRequest): String? {
        var ipAddress = request.getHeader("X-FORWARDED-FOR")
        if (ipAddress == null) {
            ipAddress = request.remoteAddr
        }
        return if (ipAddress.equals("0:0:0:0:0:0:0:1", ignoreCase = true)) {
            "localhost"
        } else ipAddress
    }

    private val isRelease get() = "release".equals(customProperties.env, ignoreCase = true)

}