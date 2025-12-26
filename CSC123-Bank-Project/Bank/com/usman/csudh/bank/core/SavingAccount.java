package com.usman.csudh.bank.core; 
public class SavingAccount extends Account{
	private static final long serialVersionUID = 1L;
	public SavingAccount(Customer customer, String Cur) { 
		super("Saving", customer, Cur);
	}



	//Withdrawals only allowed against positive balances 
	public void withdraw(double amount) throws InsufficientBalanceException {
		if (getBalance() - amount < 0) {
			throw new InsufficientBalanceException("\nNot enough funds to cover withdrawal\n\n");

		}
		super.withdraw(amount);

	}

}
