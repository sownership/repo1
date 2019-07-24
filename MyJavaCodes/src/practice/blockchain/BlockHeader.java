package practice.blockchain;

import java.util.Date;

public class BlockHeader {
	public String previousHash;
	public int difficulty;
	private int merkleRootHash;
	private long timeStamp;
	int nonce;

	public BlockHeader(String previousHash, int difficulty, int merkleRootHash) {
		super();
		this.previousHash = previousHash;
		this.difficulty = difficulty;
		this.merkleRootHash = merkleRootHash;
		this.timeStamp = new Date().getTime();
	}

	public byte[] toByteArray() {
		StringBuffer sb = new StringBuffer();
		sb.append(previousHash);
		sb.append(timeStamp);
		sb.append(nonce);
		sb.append(merkleRootHash);
		sb.append(difficulty);
		return sb.toString().getBytes();
	}

}
