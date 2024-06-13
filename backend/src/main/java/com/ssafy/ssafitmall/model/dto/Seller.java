package com.ssafy.ssafitmall.model.dto;

import org.springframework.stereotype.Component;

@Component
public class Seller {
	private int sellerCode;
	private String accountNumber;
	private String bank;
	private String userId;
	
	public Seller() {}
	
	public Seller( String accountNumber, String bank, String userId) {
		this.accountNumber = accountNumber;
		this.bank = bank;
		this.userId = userId;
	}

	public int getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(int sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Seller [sellerCode=" + sellerCode + ", accountNumber=" + accountNumber + ", bank=" + bank + ", userId="
				+ userId + "]";
	}
	
}


