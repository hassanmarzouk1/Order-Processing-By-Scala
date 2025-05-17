package orderProcessing

import orderProcessing.utils.{Logger, Parser}
import orderProcessing.rules.DiscountRules
import orderProcessing.utils.DBWriter
import java.time.{Instant, LocalDate}

import scala.io.Source

object OrderProcessing extends App {
  Logger.log("🚀 Starting order processing")

  try {
    val lines = Source.fromFile("src/main/resources/TRX1000.csv").getLines().toList.tail
    Logger.log(s"📄 Loaded ${lines.size} lines from file")

    val orders = lines.map { line =>
      try {
        Parser.orderRow(line)
      } catch {
        case e: Exception =>
          Logger.error(s"❌ Failed to parse line: $line\nReason: ${e.getMessage}")
          throw e // optionally continue instead of fail here
      }
    }

    Logger.log(s"✅ Successfully parsed ${orders.size} orders")

    val orderFinal = orders.map { order =>
      try {
        val discount = DiscountRules.computeDiscount(order)
        val finalPrice = DiscountRules.computePrice(order, discount)
        (
          order.timestamp,
          order.product_name,
          order.expiry_date,
          order.quantity,
          order.unit_price,
          order.channel,
          order.payment_method,
          order.total_price,
          discount,
          finalPrice
        )
      } catch {
        case e: Exception =>
          Logger.error(s"❌ Failed to compute discount/final price for order: ${order.product_name}\nReason: ${e.getMessage}")
          throw e
      }
    }

    Logger.log("🧮 Calculated discount and final price for orders")

    try {
      DBWriter.writeToDB(orderFinal)
      Logger.log("💾 Orders written to database")
    } catch {
      case e: Exception =>
        Logger.error(s"❌ Failed to write to database\nReason: ${e.getMessage}")
    }

  } catch {
    case e: Exception =>
      Logger.error(s"💥 Unexpected error during processing: ${e.getMessage}")
  }

  Logger.log("🏁 Order processing completed.")
  Logger.log("===================================")
}
