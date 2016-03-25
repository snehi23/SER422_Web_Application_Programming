package edu.asupoly.ser422.info;

public class TransactionStatus {
	
	Boolean status;
	int count;
	
	public TransactionStatus(Boolean status, int count) {
		super();
		this.status = status;
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
