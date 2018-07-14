package blockchain;

import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.DatatypeConverter;

public class Block {

	public int blockNumber;
	BlockHeader blockHeader;
	public String hash;
	private List<Transaction> transactions;

	private static AtomicInteger blockNumberSeq = new AtomicInteger();

	/**
	 * 데이터와 이전 해시 값을 입력받은 후 블록을 초기화. 현재 해시 값의 경우는 calculateHash() 메서드를 통해서 정의.
	 * 
	 * @param data
	 * @param previousHash
	 * @param difficulty
	 */
	public Block(List<Transaction> transactions, String previousHash, int difficulty) {

		this.blockNumber = blockNumberSeq.getAndIncrement();
		this.transactions = transactions;
		int merkleRootHash = transactions.hashCode();
		this.blockHeader = new BlockHeader(previousHash, difficulty, merkleRootHash);
		this.hash = calculateHash();
	}

	/**
	 * sha-256 알고리즘을 적용하여 새로운 해시 값을 만들어내는 메서드. 블록의 모든 정보를 해시화(암호화). nonce를 이용하여 다른
	 * 값으로 계속 바꿀 수 있음.
	 * 
	 * @return calculatHash
	 */
	public String calculateHash() {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(blockHeader.toByteArray());
			return DatatypeConverter.printHexBinary(hash);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 블록을 채굴하는 메서드. 채굴의 난이도를 입력받아 문제를 생성. 해당 문제가 해결될 때까지 hash 값 재계산.
	 */
	public void mineBlock() {
		String target = new String(new char[blockHeader.difficulty]).replace('\0', '0');
		while (!hash.substring(0, blockHeader.difficulty).equals(target)) {
			blockHeader.nonce++;
			hash = calculateHash();
		}
		System.out.println(blockHeader.nonce);
		System.out.println("Block Mined! : " + hash);
	}

	public boolean isValid() {
		if (!hash.equals(calculateHash())) {
			System.out.println(blockNumber + " block's hash is not equal");
			return false;
		}
		String hashTarget = new String(new char[blockHeader.difficulty]).replace('\0', '0');
		if (!hash.substring(0, blockHeader.difficulty).equals(hashTarget)) {
			System.out.println(blockNumber + " block has not been mined");
			return false;
		}
		return true;
	}
}
