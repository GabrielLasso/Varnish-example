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
        get("/time1") {
            val time = Date()
            call.respondText("It is $time", contentType = ContentType.Text.Plain)
        }
        get("/time2") {
            val time = Date()
            call.respondText("It is $time", contentType = ContentType.Text.Plain)
        }
    }
}

