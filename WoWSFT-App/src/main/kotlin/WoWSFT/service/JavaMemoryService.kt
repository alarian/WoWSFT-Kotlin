package WoWSFT.service

import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class JavaMemoryService {
    companion object {
        private val r = Runtime.getRuntime()
        private const val gigabyte = (1024 * 1024 * 1024).toDouble()
        private val format = DecimalFormat("##0.######")
    }

    fun showMemory(): String = """
        Max: ${format.format(r.maxMemory() / gigabyte)} GB
        Total: ${format.format(r.totalMemory() / gigabyte)} GB
        Free: ${format.format(r.freeMemory() / gigabyte)} GB
    """.trimIndent()
}