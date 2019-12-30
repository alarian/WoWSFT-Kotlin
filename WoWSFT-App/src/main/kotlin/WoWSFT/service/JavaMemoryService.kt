package WoWSFT.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class JavaMemoryService
{
    companion object {
//        private val log = LoggerFactory.getLogger(JavaMemoryService::class.java)
        private val r = Runtime.getRuntime()
        private const val gigabyte = (1024 * 1024 * 1024).toDouble()
    }

    fun showMemory(): String
    {
        val format = DecimalFormat("##0.######")
        //JVM이 현재 시스템에 요구 가능한 최대 메모리량, 이 값을 넘으면 OutOfMemory 오류가 발생 합니다.
        val max = r.maxMemory() / gigabyte
        //JVM이 현재 시스템에 얻어 쓴 메모리의 총량
        val total = r.totalMemory() / gigabyte
        //JVM이 현재 시스템에 청구하여 사용중인 최대 메모리(total)중에서 사용 가능한 메모리
        val free = r.freeMemory() / gigabyte

        return "Max: ${format.format(max)} GB, Total: ${format.format(total)} GB, Free: ${format.format(free)} GB"
    }
}