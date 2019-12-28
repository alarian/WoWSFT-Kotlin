package WoWSFT.model

import java.util.*

class BlockIp(private val ip: String)
{
    private var created: Date
    private var count: Int
    private var blockCount = 0
    private var blockCreated: Date? = null

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