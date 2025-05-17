package orderProcessing.utils

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.time.{Instant, LocalDate}
import orderProcessing.utils.Logger
import org.sqlite.JDBC

object DBWriter {
  val url = "jdbc:sqlite:order_output.db"

  val createTable: String =
    """
      |CREATE TABLE IF NOT EXISTS orders_final (
      | timestamp TEXT,
      | product_name TEXT,
      | expiry_date TEXT,
      | quantity INTEGER,
      | unit_price REAL,
      | channel TEXT,
      | payment_method TEXT,
      | total_price REAL,
      | discount REAL,
      | final_price REAL
      |);
      |""".stripMargin

  val insertSQL: String =
    """
      |INSERT INTO orders_final (
      | timestamp, product_name, expiry_date, quantity, unit_price,
      | channel, payment_method, total_price, discount, final_price
      |) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
      |""".stripMargin

  def writeToDB(orderFinal: List[(Instant, String, LocalDate, Int, Double, String, String, Double, Double, Double)]): Unit = {
    DriverManager.registerDriver(new JDBC())
    val conn: Connection = DriverManager.getConnection(url)
    val stmt = conn.createStatement()
    stmt.execute(createTable)

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSQL)
    orderFinal.foreach {
      case (timestamp, productName, expiryDate, quantity, unitPrice, channel, paymentMethod, totalPrice, discount, finalPrice) =>
        preparedStmt.setString(1, timestamp.toString)
        preparedStmt.setString(2, productName)
        preparedStmt.setString(3, expiryDate.toString)
        preparedStmt.setInt(4, quantity)
        preparedStmt.setDouble(5, unitPrice)
        preparedStmt.setString(6, channel)
        preparedStmt.setString(7, paymentMethod)
        preparedStmt.setDouble(8, totalPrice)
        preparedStmt.setDouble(9, discount)
        preparedStmt.setDouble(10, finalPrice)
        preparedStmt.addBatch()
    }

    preparedStmt.executeBatch()
    conn.close()
    Logger.log("✅ Data inserted successfully.")
  }
}
