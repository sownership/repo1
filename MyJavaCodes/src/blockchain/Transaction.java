package blockchain;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Transaction {

	String from;
	String to;
	int won;

	public Transaction(String from, String to, int won) {
		super();
		this.from = from;
		this.to = to;
		this.won = won;
	}

	public String calculateHash() {
		String input = from + to + won;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			return DatatypeConverter.printHexBinary(hash);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
