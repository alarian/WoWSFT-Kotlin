package WoWSFT.controller

import WoWSFT.service.JavaMemoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JavaMemoryController(private val javaMemoryService: JavaMemoryService)
{
    @GetMapping("/javaMemory")
    fun javaMemory(): String
    {
        return javaMemoryService.showMemory()
    }
}