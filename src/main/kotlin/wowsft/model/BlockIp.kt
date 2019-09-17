package wowsft.model

import java.util.Date

class BlockIp(val ip : String) {
    var count = 0
    var created = Date()
    var blockCount = 0
    var blockCreated : Date? = null

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
