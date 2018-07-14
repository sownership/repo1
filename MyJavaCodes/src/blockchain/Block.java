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
	 * �����Ϳ� ���� �ؽ� ���� �Է¹��� �� ����� �ʱ�ȭ. ���� �ؽ� ���� ���� calculateHash() �޼��带 ���ؼ� ����.
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
	 * sha-256 �˰����� �����Ͽ� ���ο� �ؽ� ���� ������ �޼���. ����� ��� ������ �ؽ�ȭ(��ȣȭ). nonce�� �̿��Ͽ� �ٸ�
	 * ������ ��� �ٲ� �� ����.
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
	 * ����� ä���ϴ� �޼���. ä���� ���̵��� �Է¹޾� ������ ����. �ش� ������ �ذ�� ������ hash �� ����.
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
