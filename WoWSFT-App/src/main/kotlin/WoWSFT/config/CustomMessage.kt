package WoWSFT.config

class CustomMessage()
{
    var status = "200"
    var message = ""

    constructor(status: String) : this() {
        this.status = status
    }

    constructor(status: String, message: String) : this() {
        this.status = status
        this.message = message
    }

}