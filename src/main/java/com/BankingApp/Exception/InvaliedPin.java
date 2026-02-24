package com.BankingApp.Exception;

public class InvaliedPin extends RuntimeException{

	public InvaliedPin(String msg) {
		super(msg);
	}
}
