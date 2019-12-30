package WoWSFT.model

import java.util.*

class BlockIp(private val ip: String)
{
    var created: Date
    var count = 0
    var blockCount = 0
    var blockCreated: Date? = null

    fun doCount()
    {
        count++
    }

    fun reset()
    {
        count = 1
        created = Date()
    }

    fun addBlockCount()
    {
        blockCount++
        if (blockCreated == null) {
            blockCreated = Date()
        }
    }

    fun resetBlock()
    {
        reset()
        blockCount = 0
        blockCreated = null
    }

    init
    {
        created = Date()
        count = 1
    }
}