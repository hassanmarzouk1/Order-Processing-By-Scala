package orderProcessing.utils

import orderProcessing.model.Order
import java.time.{Instant, LocalDate}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

object Parser {
  // Format the Expiry Date based on its Pattern
  val expiryDateFormatter1 = DateTimeFormatter.ofPattern("M/d/yyyy")
  val expiryDateFormatter2 = DateTimeFormatter.ISO_LOCAL_DATE

  // Parse the Expiry Date based on its format
  def parseDate(date: String): LocalDate = {
    try LocalDate.parse(date, expiryDateFormatter1)
    catch { case _: DateTimeParseException => LocalDate.parse(date, expiryDateFormatter2) }
  }

  // Round the Prices to 2 decimal
  def roundDec(x: Double): Double =
    BigDecimal(x).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

  // Parse each line in the csv to an order object
  def orderRow(line: String): Order = {
    val columns = line.split(",")
    val timestamp = Instant.parse(columns(0))
    val productName = columns(1)
    val expireDate = parseDate(columns(2))
    val quantity = columns(3).toInt
    val unitPrice = roundDec(columns(4).toDouble)
    val channel = columns(5)
    val paymentMethod = columns(6)
    val totalPrice = roundDec(unitPrice * quantity)

    Order(timestamp, productName, expireDate, quantity, unitPrice, channel, paymentMethod, totalPrice)
  }
}