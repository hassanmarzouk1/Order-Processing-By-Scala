# 📦 Order Processing Project (Scala)

This project was developed as part of the **Scala Course** in the **ITI Data Management Track**.  
It demonstrates reading, processing, and analyzing order data using Scala.

---

## 👨‍🎓 Author & Instructor

- **Student:** Hassan Marzouk  
- **Instructor:** Youseef Etmaan  
- **Track:** ITI - Data Management

---

## 📌 Project Overview

This application reads order data from a CSV file, processes it by applying discount rules, and stores the results into an SQLite database. It uses Scala’s powerful standard library for date handling, file I/O, and custom logging.

---

## 🏗️ Project Structure

```
Project/
├── build.sbt                       # SBT build configuration
├── src/
│   ├── main/
│   │   ├── scala/
│   │   │   ├── OrderProcessing.scala            # Main application entry point
│   │   │   └── orderProcessing/
│   │   │       ├── model/
│   │   │       │   └── Order.scala              # Order case class definition
│   │   │       ├── rules/
│   │   │       │   └── DiscountRules.scala      # Business rules for discounts
│   │   │       └── utils/
│   │   │           ├── DBWriter.scala           # Handles database operations
│   │   │           ├── Logger.scala             # Simple custom logger
│   │   │           └── Parser.scala             # Parsing and date validation
│   └── resources/
│       ├── TRX1000.csv                          # Sample order data
│       ├── rule_engine new requirements.pdf     # Extra Rules used in the project
│       └── Scala Project.pdf                    # Project Specs
```

---

## 🔍 Key Features

- ✅ Read CSV data containing orders.
- 📆 Parse dates in multiple formats (`M/d/yyyy` and ISO formats).
- 🧠 Apply different dynamic business rules
- 📝 Log all actions with custom messages: INFO and ERROR.
- 🗃️ Save the results into a local SQLite database.

---

## ▶️ How to Run

1. **Install Prerequisites**
   - [Java JDK 8+](https://adoptopenjdk.net/)
   - [SBT (Scala Build Tool)](https://www.scala-sbt.org/)

2. **Clone & Run the Project**
   ```bash
   sbt run
   ```

3. **Output**
   - A SQLite database file will be created containing the final processed orders.

---

## 🧪 Sample Input (CSV)

File: `TRX1000.csv`

```csv
timestamp,product_name,expiry_date,quantity,unit_price,channel,payment_method
2024-05-01T12:30:00Z,Milk,2024-06-01,10,2.5,Online,CreditCard
```

---

## 🗃️ Output Example (Order Fields)

- `timestamp`
- `product_name`
- `expiry_date`
- `quantity`
- `unit_price`
- `channel`
- `payment_method`
- `total_price`
- `discount`
- `final_price`

---

## 📓 Notes

- The code is well-organized and modular.
- Logging is enabled to help track processing and errors.
- Date parsing supports multiple formats (e.g., `ISO_INSTANT`, `M/d/yyyy`).

---

## 📜 License

This project is for educational use as part of the ITI curriculum.

---

## 🙌 Acknowledgements

Thanks to **Youseef Etmaan** for guidance and supervision during this course project.

