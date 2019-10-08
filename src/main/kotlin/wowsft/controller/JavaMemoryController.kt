package wowsft.controller

import wowsft.service.JavaMemoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Qualson-Lee on 2017-06-13.
 */
@RestController
class JavaMemoryController(
        @Autowired private val javaMemoryService: JavaMemoryService
)
{
    @GetMapping("/javaMemory")
    fun getJavaMemory(): String
    {
        return javaMemoryService.showMemory()
    }
}
