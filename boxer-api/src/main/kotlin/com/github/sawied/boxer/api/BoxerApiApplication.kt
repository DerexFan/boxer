package com.github.sawied.boxer.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoxerApiApplication

fun main(args: Array<String>) {
	runApplication<BoxerApiApplication>(*args)
}
