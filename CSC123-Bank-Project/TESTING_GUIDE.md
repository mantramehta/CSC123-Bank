# Testing Guide for Bank Project

## Step 1: Compile the Project

Open PowerShell or Command Prompt in the `CSC123-Bank-Project` folder and run:

```powershell
Get-ChildItem -Path Bank -Recurse -Filter *.java | ForEach-Object { javac -d bin -cp "lib/json-simple-1.1.jar" -sourcepath Bank $_.FullName }
```

Or if you're in the project root directory:
```powershell
javac -d bin -cp "lib/json-simple-1.1.jar" -sourcepath Bank Bank/com/usman/csudh/bank/MainBank.java Bank/com/usman/csudh/bank/core/*.java Bank/com/usman/csudh/bank/net/*.java Bank/com/usman/csudh/util/*.java
```

## Step 2: Run the Application

```powershell
java -cp "bin;lib/json-simple-1.1.jar" com.usman.csudh.bank.MainBank
```

**Note**: On Linux/Mac, use `:` instead of `;` in the classpath.

## Step 3: Test Each Feature

### Test 1: Open a Checking Account
1. Select option **1** from the menu
2. Enter:
   - First name: `John`
   - Last name: `Doe`
   - SSN: `123-45-6789`
   - Overdraft limit: `500`
   - Currency: `USD` (or any currency from exchange_rates.csv like EUR, GBP, etc.)
3. **Expected**: You should see "Account opened, account number is: 1000"

### Test 2: Open a Savings Account
1. Select option **2** from the menu
2. Enter:
   - First name: `Jane`
   - Last name: `Smith`
   - SSN: `987-65-4321`
   - Currency: `EUR`
3. **Expected**: You should see "Account opened, account number is: 1001"

### Test 3: List All Accounts
1. Select option **3** from the menu
2. **Expected**: You should see both accounts listed with their details

### Test 4: Deposit Funds
1. Select option **6** from the menu
2. Enter:
   - Account number: `1000` (or the account number you got)
   - Amount: `1000`
3. **Expected**: "Deposit was successful, account balance is: 1000.0"

### Test 5: Withdraw Funds
1. Select option **7** from the menu
2. Enter:
   - Account number: `1000`
   - Amount: `200`
3. **Expected**: "Withdrawal was successful, account balance is: 800.0"

### Test 6: View Account Statement
1. Select option **4** from the menu
2. Enter account number: `1000`
3. **Expected**: You should see all transactions (deposits and withdrawals) listed

### Test 7: Show Account Information
1. Select option **5** from the menu
2. Enter account number: `1000`
3. **Expected**: You should see account details including:
   - Account Number
   - Name
   - SSN
   - Currency
   - Currency Balance
   - USD Balance

### Test 8: Currency Conversion
1. Select option **8** from the menu
2. Enter:
   - Buying currency: `EUR`
   - Amount: `100`
   - Selling currency: `USD`
3. **Expected**: You should see the exchange rate and converted amount

### Test 9: Test Overdraft (Checking Account Only)
1. With account 1000, try to withdraw more than the balance + overdraft limit
2. **Expected**: Should throw "Not enough funds to cover withdrawal" error

### Test 10: Test Savings Account Withdrawal Limit
1. With account 1001 (Savings), try to withdraw more than the balance
2. **Expected**: Should throw "Not enough funds to cover withdrawal" error

### Test 11: Close an Account
1. Select option **9** from the menu
2. Enter account number: `1001`
3. **Expected**: "Account number 1001 has been closed, balance is: [balance]"

### Test 12: Test Error Handling
- Try to deposit to a non-existent account → Should show "Account not found"
- Try to withdraw from closed account with zero balance → Should show error
- Try currency conversion without USD → Should show error

## Step 4: Test Network Server Mode (Optional)

1. Compile the project (same as Step 1)

2. Run the network server:
```powershell
java -cp "bin;lib/json-simple-1.1.jar" com.usman.csudh.bank.net.NetworkListener
```

**Note**: This requires administrator privileges on Windows (port 80)

3. In another terminal, connect using telnet:
```powershell
telnet localhost 80
```

4. You should see the bank menu and can interact with it over the network

## Quick Test Checklist

- [ ] Project compiles without errors
- [ ] Application starts and shows menu
- [ ] Can open Checking account
- [ ] Can open Savings account
- [ ] Can list accounts
- [ ] Can deposit funds
- [ ] Can withdraw funds
- [ ] Can view account statement
- [ ] Can view account information
- [ ] Currency conversion works
- [ ] Overdraft protection works (Checking)
- [ ] Savings account withdrawal limits work
- [ ] Can close account
- [ ] Error handling works (invalid account numbers, etc.)

## Troubleshooting

**Issue**: "config.txt not found"
- **Solution**: Make sure config.txt is in the CSC123-Bank-Project folder (same level as Bank folder)

**Issue**: "ArrayIndexOutOfBoundsException" when loading currencies
- **Solution**: Fixed! The CSVReader now handles empty lines properly

**Issue**: "ClassNotFoundException"
- **Solution**: Make sure you're including `lib/json-simple-1.1.jar` in the classpath

**Issue**: Currency conversion doesn't work
- **Solution**: Check that `support.currencies=true` in config.txt and exchange_rates.csv exists

