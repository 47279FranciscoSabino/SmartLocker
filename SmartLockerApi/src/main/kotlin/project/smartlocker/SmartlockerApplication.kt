package project.smartlocker

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartlockerApplication

private val logger = LoggerFactory.getLogger("main")

fun main(args: Array<String>) {
	logger.info("(INFO - 'http://localhost:8080')")
	logger.info("Server started")
	runApplication<SmartlockerApplication>(*args)
}
