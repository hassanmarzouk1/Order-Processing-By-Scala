package orderProcessing.utils

import java.io.{FileWriter, PrintWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
  private val logFilePath = "rules_engine.log"
  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def log(message: String, level: String = "INFO"): Unit = {
    val timestamp = LocalDateTime.now().format(formatter)
    val writer = new PrintWriter(new FileWriter(logFilePath, true))
    writer.println(s"$timestamp     $level     $message")
    writer.close()
  }
  def error(message: String, level: String = "ERROR"): Unit = {
    val timestamp = LocalDateTime.now().format(formatter)
    val writer = new PrintWriter(new FileWriter(logFilePath, true))
    writer.println(s"$timestamp     $level     $message")
    writer.close()
  }
}

