package orderProcessing.model
import java.time.{Instant, LocalDate}

case class Order(
                  timestamp: Instant,
                  product_name: String,
                  expiry_date: LocalDate,
                  quantity: Int,
                  unit_price: Double,
                  channel: String,
                  payment_method: String,
                  total_price: Double
                )