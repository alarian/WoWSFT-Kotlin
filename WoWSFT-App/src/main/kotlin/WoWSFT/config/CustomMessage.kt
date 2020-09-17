package WoWSFT.config

data class CustomMessage(
    var status: String = "200",
    var message: String = ""
) {
    constructor(status: String): this() {
        this.status = status
    }
}