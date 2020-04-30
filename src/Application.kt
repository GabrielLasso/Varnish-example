package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.client.*
import io.ktor.client.request.request
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        get("/") {
            val now = Date()
            call.respondHtml {
                body {
                    p {
                        text("Current time: ")
                        HTMLTag("esi:include", consumer, mapOf("src" to "/current_time"), null, false, true).visit {  }
                    }
                    p {
                        text("Last cached time: $now")
                    }
                }
            }
        }
        get("/current_time") {
            val now = Date()
            call.respondText(now.toString())
        }
    }
}

