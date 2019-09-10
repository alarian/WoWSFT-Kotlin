package wowsft.model

import java.util.Date

class BlockIp(private val ip : String) {
    private var count = 0
    private var created = Date()
    private var blockCount = 0
    private var blockCreated : Date? = null

    fun doCount() {
        this.count++
    }

    fun reset() {
        this.count = 1
        this.created = Date()
    }

    fun addBlockCount() {
        blockCount++
        if (blockCreated == null) {
            blockCreated = Date()
        }
    }

    fun resetBlock() {
        reset()
        blockCount = 0
        blockCreated = null
    }
}
