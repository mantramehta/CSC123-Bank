// Mantra Mehta(mmehta2@toromail.csudh.edu)
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Bank {
	
	private static Map<Integer,Account> accounts=new TreeMap<Integer,Account>();
	private static TreeMap<String, ExchangeRate> exchangeRates = new TreeMap<>();
	
	
	
	public static Account openCheckingAccount(String firstName, String lastName, String ssn, double overdraftLimit,String Currency) {
		Customer c=new Customer(firstName,lastName, ssn);
		Account a=new CheckingAccount(c,overdraftLimit,Currency);
		accounts.put(a.getAccountNumber(), a);
		return a;
		
	}
	
	public static Account openSavingAccount(String firstName, String lastName, String ssn,String Currency) {
		Customer c=new Customer(firstName,lastName, ssn);
		Account a=new SavingAccount(c,Currency);
		accounts.put(a.getAccountNumber(), a);
		return a;
		
	}

	
	
	public static Account lookup(int accountNumber) throws NoSuchAccountException{
		if(!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: "+accountNumber+" nout found!\n\n");
		}
		
		return accounts.get(accountNumber);
	}
	
	public static void makeDeposit(int accountNumber, double amount) throws AccountClosedException, NoSuchAccountException{
		
		lookup(accountNumber).deposit(amount);
		
	}
	
	public static void makeWithdrawal(int accountNumber, double amount) throws InsufficientBalanceException, NoSuchAccountException {
		lookup(accountNumber).withdraw(amount);
	}
	
	public static void SaveCurrency() {
		// create a TreeMap to store the exchange rates
	       try {
	           // read the exchange-rate.csv file
	           BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Mantra\\eclipse-workspace\\JavaProjects\\exchange-rate.csv"));
	           String line = reader.readLine();
	           while (line != null) {
	               // split each line by comma
	               String[] parts = line.split(",");
	               String currencyCode = parts[0];
	               String currencyName = parts[1];
	               // parse the exchange rate value as a Double
	               Double rate = Double.parseDouble(parts[2]);

	               // create a new Currency object and put it into the TreeMap
	               ExchangeRate currency = new ExchangeRate(currencyName, rate);
	               exchangeRates.put(currencyCode, currency);
	               line = reader.readLine();
	           }
	           reader.close();
	           }catch (IOException e) {
	               e.printStackTrace();
	           }
	}
	
	public static Object[] currencyConversion(String buyingCurrency, double amount, String sellingCurrency)throws USDNotFoundException, ExchangeRateException {
   
		double finalAmt=0, Erate = 0;
		if ( (buyingCurrency.equals("USD") || sellingCurrency.equals("USD")) == false) {
			throw new USDNotFoundException("Error: One of the currencies must be USD.\n\n");
		}
		if ( (exchangeRates.containsKey(buyingCurrency) && exchangeRates.containsKey(sellingCurrency)) == false) {
			throw new ExchangeRateException("Error: the exchange rate is not found\n\n");
		}
		
		if (sellingCurrency.equals("USD")) {
			ExchangeRate currency1 = exchangeRates.get(buyingCurrency);
			Erate = currency1.getRate();
			finalAmt = amount / Erate ;
		}else {
			ExchangeRate currency1 = exchangeRates.get(sellingCurrency);
			Erate = currency1.getRate();
			finalAmt = amount * Erate;
		}
		
		return  new Object[]{Erate, buyingCurrency, finalAmt};
		
	}
	public static double Rate(String Currency) {
		if(Currency == "USD") {
			return 1;
		}
		ExchangeRate currency1 = exchangeRates.get(Currency);
		double Erate = currency1.getRate();
		return Erate;
	}
	
	
	public static void closeAccount(int accountNumber) throws NoSuchAccountException {
		lookup(accountNumber).close();
	}

	
	public static double getBalance(int accountNumber) throws NoSuchAccountException {
		return lookup(accountNumber).getBalance();
	}

	public static void listAccounts(OutputStream out) throws IOException{
		
		out.write((byte)10);
		Collection<Account> col=accounts.values();
		
		for (Account a:col) {
			out.write(a.toString().getBytes());
			out.write((byte)10);
		}
		
		out.write((byte)10);
		out.flush();
	}
	
	public static void printAccountTransactions(int accountNumber, OutputStream out) throws IOException,NoSuchAccountException{
		lookup(accountNumber).printTransactions(out);
	}
	
	public static void printAccontDetail(int accountNumber, OutputStream out) throws IOException,NoSuchAccountException{
		lookup(accountNumber).printDetail(out);
	}
	
	public static boolean ValidCur(String Cur) {
		if(exchangeRates.containsKey(Cur)) {
			return true;
		}
		return false;
	}
}
