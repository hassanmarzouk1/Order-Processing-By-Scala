package orderProcessing.rules


import orderProcessing.model.Order
import orderProcessing.utils.{Logger, Parser}

import java.time.ZoneId
import java.time.temporal.ChronoUnit

object DiscountRules {

  val Rules: List[(Order => Boolean, Order => Double, String)] = List(
    (isAQualified, calcA, "Rule A"),
    (isBQualified, calcB, "Rule B"),
    (isCQualified, calcC, "Rule C"),
    (isDQualified, calcD, "Rule D"),
    (isEQualified, calcE, "Rule E"),
    (isFQualified, calcF, "Rule F")
  )
  
  // Rule 1
  def isAQualified(r: Order): Boolean = {
    // Convert timestamp instant to LocalDate in system default zone
    val transactionDate = r.timestamp.atZone(ZoneId.systemDefault()).toLocalDate
    // Calculate Days between TransactionDate and ExpiryDate
    val daysBetween = ChronoUnit.DAYS.between(transactionDate, r.expiry_date)

    daysBetween < 30
  }
  def calcA(r: Order): Double = {
    // Convert timestamp instant to LocalDate in system default zone
    val transactionDate = r.timestamp.atZone(ZoneId.systemDefault()).toLocalDate
    // Calculate Days between TransactionDate and ExpiryDate
    val daysBetween = ChronoUnit.DAYS.between(transactionDate, r.expiry_date)
    // Calculate Discount
    val discount = (30 - daysBetween) / 100

    discount
  }

  // Rule 2
  def isBQualified(r: Order): Boolean = {
    // Split the channel to get the product
    val product = r.product_name.split(" ")(0)
    // Check the name of the product
    (product == "Cheese" || product == "Wine")
  }
  def calcB(r: Order): Double = {
    // Split the channel to get the product
    val product = r.product_name.split(" ")(0)
    // Calculate the discount based on the product
    val discount =
      if (product == "Cheese")
        0.1
      else
        0.05

    discount
  }

  // Rule 3
  def isCQualified(r: Order): Boolean = {
    // Split the month and day from the timeStamp column
    val dayMonth = r.timestamp.toString.split("T")(0)
    // Check the date of order
    (dayMonth.equals("2023-03-23"))
  }
  def calcC(r: Order): Double = {
    val discount = 0.5
    discount
  }

  // Rule 4
  def isDQualified(r: Order): Boolean = {
    ((r.quantity > 5))
  }
  def calcD(r: Order): Double = {
    val quantity = r.quantity
    // Calculate the discount
    val discount =
      if (quantity >= 6 && quantity <= 9) 0.05
      else if (quantity >= 10 && quantity <= 14) 0.07
      else if (quantity > 15) 0.10
      else 0.0
      
    discount
  }

  // Rule 5
  def isEQualified(r: Order): Boolean = {
    (r.channel == "App")
  }
  def calcE(r: Order): Double = {
    val roundQuantity = ((r.quantity + 4) / 5) * 5 // Round UP to the nearest 5
    val discount = (roundQuantity / 5) * 0.05
    discount
  }

  // Rule 6
  def isFQualified(r: Order): Boolean = {
    (r.payment_method == "Visa")
  }
  def calcF(r: Order): Double = {
    val discount = 0.05
    discount
  }

  // Calculate the total of discount for an Order
  def computeDiscount(order: Order): Double = {
    // Collect all applicable discounts
    val validDiscounts = Rules.collect {
      case (qualifier, calculator, name) if qualifier(order) => calculator(order)
    }
    // Sort discounts in descending order and average the top 2 (if exists)
    val totalDiscount = validDiscounts.sorted.reverse.take(2) match {
      case Nil => 0.0 // No discounts
      case discounts => discounts.sum / discounts.length // Average of top 2 discounts
    }
    // Return the avg of top 2 discounts
    totalDiscount
  }

  // Compute the Final Price of an order
  def computePrice(order: Order, discountRate: Double): Double = {
    val final_price = Parser.roundDec(order.total_price * (1 - discountRate))
    final_price
  }
}
