package com.sparkjava.shell

import spark.kotlin.get

fun manageShell() {

    val shellSessionMap = HashMap<String, Shell>()

    get("shell") {
        val username = request.queryParams("username")
        val shell = if (shellSessionMap.containsKey(username)) {
            shellSessionMap[username]
        } else {
            val shell = Shell()
            shellSessionMap[username] = shell
            shell
        }
        val command = request.queryParams("command")
        if ("exit" == command) {
            shellSessionMap.remove(username)
            return@get "Session terminated successfully"
        } else
            return@get shell!!.executeCommand(command).replace("\n", "<br/>")
    }
}