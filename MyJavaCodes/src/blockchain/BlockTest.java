package blockchain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.GsonBuilder;

public class BlockTest {
	private static List<Block> chain = new LinkedList<Block>();
	private static int currentDifficulty = 5;

	/**
	 * 블록의 유효성을 체크하는 메서드. 1. 현재 블록의 해시 값이 유효한가? 2. 이전 블록의 해시 값이 유효한가? 3. 채굴의 결과 값이
	 * 유효한가?
	 * 
	 * @return
	 */
	private static boolean isChainValid() {
		if (chain.size() == 0) {
			return true;
		}

		Block currentBlock = chain.get(0);
		if (!currentBlock.isValid()) {
			return false;
		}

		for (int i = 1; i < chain.size() - 1; i++) {
			Block previousBlock = currentBlock;
			currentBlock = chain.get(i);
			if (!currentBlock.isValid()) {
				return false;
			}
			if (!currentBlock.blockHeader.previousHash.equals(previousBlock.hash)) {
				System.out.println(currentBlock.blockNumber + " block's previous hash is not equal");
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		makeChain();

		System.out.println("chain is valid :" + isChainValid());
		String chainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
		System.out.println(chainJson);
	}

	private static void makeChain() {
		List<Transaction> transactions = loadTransactions();

		int count = 0;
		int fromIndex = 0;
		int toIndex = 0;
		while (toIndex < transactions.size()) {
			fromIndex = count * 1000;
			toIndex = Math.min(fromIndex + 1000, transactions.size());
			List<Transaction> subTransactions = transactions.subList(fromIndex, toIndex);
			String previousHash = "0";
			if (count > 0) {
				previousHash = chain.get(count - 1).hash;
			}
			chain.add(new Block(subTransactions, previousHash, currentDifficulty));
			System.out.println("Trying to Mine Block " + count);
			chain.get(count).mineBlock();
			count++;
		}
	}

	private static List<Transaction> loadTransactions() {
		List<Transaction> transactions = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("./resource/SOURCEDIR/transactions.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] ss = line.split(",");
				Transaction transaction = new Transaction(ss[0], ss[1], Integer.valueOf(ss[2]));
				transactions.add(transaction);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transactions;
	}
}
