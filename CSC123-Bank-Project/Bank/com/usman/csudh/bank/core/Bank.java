package com.usman.csudh.bank.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection; 
import java.util.Map;
import java.util.TreeMap;

public class Bank {

	private static Map<Integer, Account> accounts = new TreeMap<Integer, Account>();
	private static TreeMap<String, ExchangeRate> exchangeRates = new TreeMap<>();

	public static Account openCheckingAccount(String firstName, String lastName, String ssn, double overdraftLimit,
			String Currency) {
		Customer c = new Customer(firstName, lastName, ssn);
		Account a = new CheckingAccount(c, overdraftLimit, Currency);
		accounts.put(a.getAccountNumber(), a);
		return a;

	}

	public static Account openSavingAccount(String firstName, String lastName, String ssn, String Currency) {
		Customer c = new Customer(firstName, lastName, ssn);
		Account a = new SavingAccount(c, Currency);
		accounts.put(a.getAccountNumber(), a);
		return a;

	}

	public static Account lookup(int accountNumber) throws NoSuchAccountException {
		if (!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: " + accountNumber + " not found!\n\n");
		}

		return accounts.get(accountNumber);
	}

	public static void makeDeposit(int accountNumber, double amount)
			throws AccountClosedException, NoSuchAccountException {

		lookup(accountNumber).deposit(amount);

	}

	public static void makeWithdrawal(int accountNumber, double amount)
			throws InsufficientBalanceException, NoSuchAccountException {
		lookup(accountNumber).withdraw(amount);
	}

	public static void SaveCurrency(TreeMap map) {
		exchangeRates = map;
	}

	public static Object[] currencyConversion(String buyingCurrency, double amount, String sellingCurrency)
			throws USDNotFoundException, ExchangeRateException {

		double finalAmt = 0, Erate = 0;
		if ((buyingCurrency.equals("USD") || sellingCurrency.equals("USD")) == false) {
			throw new USDNotFoundException("Error: One of the currencies must be USD.\n\n");
		}
		
		// USD is the base currency, so only the non-USD currency needs to be in the map
		String nonUSDCurrency = sellingCurrency.equals("USD") ? buyingCurrency : sellingCurrency;
		if (!exchangeRates.containsKey(nonUSDCurrency)) {
			throw new ExchangeRateException("Error: the exchange rate is not found\n\n");
		}

		if (sellingCurrency.equals("USD")) {
			// Selling USD, buying foreign currency: multiply by rate
			ExchangeRate currency1 = exchangeRates.get(buyingCurrency);
			Erate = currency1.getRate();
			finalAmt = amount * Erate;
		} else {
			// Selling foreign currency, buying USD: divide by rate
			ExchangeRate currency1 = exchangeRates.get(sellingCurrency);
			Erate = currency1.getRate();
			finalAmt = amount / Erate;
		}

		return new Object[] { Erate, buyingCurrency, finalAmt };

	}

	public static double Rate(String Currency) {
		if (Currency != null && Currency.equals("USD")) {
			return 1;
		}
		ExchangeRate currency1 = exchangeRates.get(Currency);
		if (currency1 == null) {
			return 1; // Default to 1 if currency not found
		}
		double Erate = currency1.getRate();
		return Erate;
	}

	public static void closeAccount(int accountNumber) throws NoSuchAccountException, AccountClosedException {
		lookup(accountNumber).close();
	}

	public static double getBalance(int accountNumber) throws NoSuchAccountException {
		return lookup(accountNumber).getBalance();
	}

	public static void listAccounts(OutputStream out) throws IOException {

		out.write((byte) 10);
		Collection<Account> col = accounts.values();

		for (Account a : col) {
			out.write(a.toString().getBytes());
			out.write((byte) 10);
		}

		out.write((byte) 10);
		out.flush();
	}

	public static void printAccountTransactions(int accountNumber, OutputStream out)
			throws IOException, NoSuchAccountException {
		lookup(accountNumber).printTransactions(out);
	}

	public static void printAccontDetail(int accountNumber, OutputStream out)
			throws IOException, NoSuchAccountException {
		lookup(accountNumber).printDetail(out);
	}

	public static boolean ValidCur(String Cur) {
		if (exchangeRates.containsKey(Cur)) {
			return true;
		}
		return false;
	}
}