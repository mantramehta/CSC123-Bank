# Banking Management System

A comprehensive **Banking Management System** built in Java featuring multi-currency support, real-time exchange rate integration, and robust transaction processing. This project demonstrates object-oriented design principles, exception handling, API integration, and software architecture patterns.

## 🚀 Features

### Core Banking Operations
- **Account Management**: Create and manage Checking and Savings accounts with different business rules
- **Transaction Processing**: Deposit, withdraw, and maintain complete transaction history
- **Account Information**: View detailed account statements and account information
- **Account Lifecycle**: Open and close accounts with proper validation

### Multi-Currency Support
- **Multi-Currency Accounts**: Open accounts in any supported currency (USD, EUR, GBP, CAD, INR, and 40+ more)
- **Real-Time Exchange Rates**: Three flexible data source options:
  - **Local CSV File**: Read exchange rates from a local file
  - **HTTP/Web Service**: Fetch CSV data from a remote URL (e.g., GitHub raw files)
  - **REST API Integration**: Real-time exchange rates from external APIs (JSON parsing)
- **Currency Conversion Service**: Convert between any supported currencies
- **Dual Currency Display**: View balances in both account currency and USD equivalent

### Technical Highlights
- **Object-Oriented Design**: Inheritance, polymorphism, and abstraction patterns
- **Exception Handling**: Custom exceptions for robust error management
- **Configuration Management**: External configuration file for flexible deployment
- **Network Server Mode**: Optional network server for remote access
- **API Integration**: JSON parsing and HTTP client implementation
- **Design Patterns**: Factory pattern for data source selection, Singleton for configuration

## 🏗️ Architecture

### Project Structure
```
CSC123-Bank-Project/
├── Bank/                    # Source code
│   └── com/usman/csudh/
│       ├── bank/
│       │   ├── MainBank.java          # Main application entry point
│       │   ├── net/
│       │   │   └── NetworkListener.java  # Network server implementation
│       │   └── core/                   # Core banking logic
│       │       ├── Bank.java           # Bank operations facade
│       │       ├── Account.java        # Base account class
│       │       ├── CheckingAccount.java # Checking account with overdraft
│       │       ├── SavingAccount.java  # Savings account
│       │       ├── Customer.java       # Customer data model
│       │       ├── Transaction.java    # Transaction records
│       │       ├── ExchangeRate.java   # Exchange rate model
│       │       ├── CSVReader.java      # Abstract reader (Strategy pattern)
│       │       ├── InputSourceFile.java # File-based data source
│       │       ├── HTTPSource.java     # HTTP-based data source
│       │       ├── APISource.java      # REST API data source
│       │       ├── Configuration.java  # Configuration manager (Singleton)
│       │       └── [Exception classes] # Custom exception handling
│       └── util/
│           ├── UIManager.java          # User interface manager
│           └── UniqueCounter.java      # ID generator
├── bin/                     # Compiled classes
├── lib/
│   └── json-simple-1.1.jar  # JSON parsing library
├── config.txt               # Configuration file
└── exchange_rates.csv       # Sample exchange rate data
```

### Key Design Decisions

1. **Strategy Pattern for Data Sources**: Abstract `CSVReader` class with implementations for file, HTTP, and REST API sources
2. **Inheritance Hierarchy**: Base `Account` class with specialized `CheckingAccount` and `SavingAccount` subclasses
3. **Exception Handling**: Custom exceptions (`InsufficientBalanceException`, `AccountClosedException`, `NoSuchAccountException`, etc.) for clear error messaging
4. **Configuration Management**: Singleton pattern for centralized configuration access
5. **Separation of Concerns**: UI, business logic, and data access layers are clearly separated

## 💻 Technologies & Skills Demonstrated

- **Java**: Core Java programming, OOP principles, Collections framework
- **Exception Handling**: Custom exception classes and proper error management
- **API Integration**: REST API consumption, JSON parsing, HTTP clients
- **File I/O**: CSV parsing, configuration file management
- **Network Programming**: TCP server implementation
- **Design Patterns**: Strategy, Singleton, Factory patterns
- **Software Architecture**: Clean code structure, separation of concerns

## 📋 Prerequisites

- Java JDK 8 or higher
- `json-simple-1.1.jar` (included in `lib/` folder)

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone <your-repo-url>
cd CSC123-Bank-Project
```

### 2. Create Configuration File

Create a `config.txt` file in the project root:

```properties
support.currencies=true
currencies.source=rest
currency.file=exchange_rates.csv
webservice.url=https://raw.githubusercontent.com/yourusername/repo/main/exchange_rates.csv
rest.url=https://api.exchangerate-api.com/v4/latest/USD
```

### Configuration Options:
- **support.currencies**: `true` or `false` - Enable/disable multi-currency support
- **currencies.source**: `file`, `webservice`, or `rest` - Data source for exchange rates
- **currency.file**: Path to CSV file (if using `file` source)
- **webservice.url**: URL to CSV file (if using `webservice` source)
- **rest.url**: REST API endpoint URL (if using `rest` source)

### 3. Compile the Project

**Windows:**
```cmd
javac -cp "lib/json-simple-1.1.jar;." -d bin -sourcepath Bank Bank/com/usman/csudh/bank/MainBank.java
```

**Linux/Mac:**
```bash
javac -cp "lib/json-simple-1.1.jar:." -d bin -sourcepath Bank Bank/com/usman/csudh/bank/MainBank.java
```

### 4. Run the Application

**Windows:**
```cmd
java -cp "bin;lib/json-simple-1.1.jar;." com.usman.csudh.bank.MainBank
```

**Linux/Mac:**
```bash
java -cp "bin:lib/json-simple-1.1.jar:." com.usman.csudh.bank.MainBank
```

## 📖 Usage

### Console Mode

1. Start the application - you'll see a menu with 10 options
2. **Open Checking Account** (Option 1):
   - Enter customer information (first name, last name, SSN)
   - Enter overdraft limit
   - Select currency (if multi-currency is enabled)
3. **Open Savings Account** (Option 2): Similar to checking, but no overdraft
4. **List Accounts** (Option 3): View all accounts
5. **Account Statement** (Option 4): View transaction history
6. **Account Information** (Option 5): View detailed account details
7. **Deposit Funds** (Option 6): Add money to an account
8. **Withdraw Funds** (Option 7): Remove money from an account
9. **Currency Conversion** (Option 8): Convert between currencies
10. **Close Account** (Option 9): Close an account
11. **Exit** (Option 10): Exit the application

### Network Server Mode

Run the network server:
```bash
java -cp "bin;lib/json-simple-1.1.jar;." com.usman.csudh.bank.net.NetworkListener
```

Connect using telnet or any TCP client:
```bash
telnet localhost 80
```

## 🔧 Exchange Rate Data Sources

### Option 1: Local File
Set `currencies.source=file` and provide a CSV file with format:
```
EUR,European Euro,0.92
GBP,British Pound,0.79
CAD,Canadian Dollar,1.35
...
```

### Option 2: HTTP/Web Service
Set `currencies.source=webservice` and provide a URL to a CSV file (e.g., GitHub raw file URL).

### Option 3: REST API
Set `currencies.source=rest` and provide a REST API endpoint URL. The system will parse JSON responses automatically.

## 🧪 Testing

See `TESTING_GUIDE.md` for comprehensive testing instructions and test cases.

## 📝 Key Features Implemented

- ✅ Multi-currency account support with 40+ currencies
- ✅ Three flexible exchange rate data sources (file, HTTP, REST API)
- ✅ Real-time currency conversion
- ✅ Transaction history tracking
- ✅ Overdraft protection for checking accounts
- ✅ Savings account withdrawal restrictions
- ✅ Account closure with balance validation
- ✅ Comprehensive error handling
- ✅ Network server mode
- ✅ Configuration-based feature toggling

## 🐛 Troubleshooting

1. **"config.txt not found"**: Ensure `config.txt` is in the project root directory
2. **"Currency support feature is not set as true"**: Set `support.currencies=true` in config.txt
3. **ClassNotFoundException**: Include `json-simple-1.1.jar` in the classpath
4. **Port 80 already in use**: Modify the port in `NetworkListener.java` or stop the service using port 80

## 📚 Dependencies

- `json-simple-1.1.jar` - For parsing JSON responses from REST APIs (included in `lib/` folder)

## 📄 License

This project was developed as part of coursework. Starter code structure was provided by the instructor, with all business logic, features, and implementations developed independently.

## 👤 Author

**Mantra Mehta**
- Recent MSCS Graduate
- [GitHub Profile](https://github.com/mantramehta)
- [LinkedIn](https://www.linkedin.com/in/mantra-mehta/)

## 🙏 Acknowledgments

- Starter code structure provided by Professor Usman (CSUDH)
- Exchange rate data from [ExchangeRate-API](https://www.exchangerate-api.com/)

---

**Note**: This project demonstrates proficiency in Java development, object-oriented design, API integration, and software engineering best practices. All business logic, multi-currency implementation, REST API integration, and exception handling were independently developed.
