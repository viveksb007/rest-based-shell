package com.sparkjava.shell

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.streams.asSequence

class Shell {

    private var procBuilder = ProcessBuilder()
    private val userHome = System.getProperty("user.home")
    private var dir = userHome

    fun executeCommand(command: String): String {
        when {
            command.startsWith("cd ${File.separator}") -> dir = command.split(" ")[1]
            command.startsWith("cd ..") -> {
                dir = dir.substring(0, dir.lastIndexOf(File.separator))
                println("INFO : $dir")
            }
            command.startsWith("cd ~") -> {
                dir = if (command.trim().length == 4) {
                    userHome
                } else {
                    userHome + command.substring(command.indexOf('~') + 1)
                }
            }
            command.startsWith("cd ") -> dir = dir + File.separator + command.split(" ")[1]
        }

        procBuilder.command("sh", "-c", command)
        procBuilder.directory(File(dir))
        val process = procBuilder.start()
        return BufferedReader(InputStreamReader(process.inputStream)).lines().asSequence()
            .joinToString(separator = "\n")
    }
}