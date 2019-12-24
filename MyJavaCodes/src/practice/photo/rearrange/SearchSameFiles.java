package practice.photo.rearrange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.bind.DatatypeConverter;

public class SearchSameFiles {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		Set<Set<File>> sameSizeFilesSet = getSameSizeFilesSet("E:\\����_local�۾���");
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		Set<Set<File>> sameContentFilesSet = getSameContentFiles(md5Digest, sameSizeFilesSet);
		print(sameContentFilesSet);
	}

	private static void print(Set<Set<File>> sameContentFilesSet) {
		// ����� �����ش�
		for (Set<File> sameContentFiles : sameContentFilesSet) {
			System.out.println(sameContentFiles);
		}
	}

	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {

		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
		}

		return DatatypeConverter.printHexBinary(digest.digest());
	}

	private static Set<Set<File>> getSameContentFiles(MessageDigest md5Digest, Set<Set<File>> sameSizeFilesSet)
			throws IOException {
		Set<Set<File>> sameContentFilesSet = new HashSet<>();

		// hashcode �� ���� files
		Map<String, Set<File>> hashFilesMap = new HashMap<>();

		// sameSizeFiles �� ��� files �� ���� hash �� �и��Ѵ�.
		for (Set<File> sameSizeFiles : sameSizeFilesSet) {
			for (File file : sameSizeFiles) {

				String checksum = getFileChecksum(md5Digest, file);
				Set<File> sameFiles = hashFilesMap.get(checksum);
				if (sameFiles == null) {
					sameFiles = new HashSet<File>();
					hashFilesMap.put(checksum, sameFiles);
				}
				sameFiles.add(file);
			}
		}

		// hashFiles ���� hash �� 2�� �̻��� �͵鸸 ������ ���� �͵��̴�
		for (Set<File> files : hashFilesMap.values()) {
			if (files.size() > 1) {
				sameContentFilesSet.add(files);
			}
		}

		return sameContentFilesSet;
	}

	private static Set<Set<File>> getSameSizeFilesSet(String dir) {
		Set<Set<File>> sameSizeFilesSet = new HashSet<>();
		Map<Long, Set<File>> sameSizeFilesMap = new HashMap<Long, Set<File>>();

		// file lenth ���� file���� �з��Ѵ�
		Queue<File> queue = new LinkedBlockingQueue<File>();
		queue.offer(new File(dir));
		while (!queue.isEmpty()) {
			File file = queue.poll();
			if (file.isDirectory()) {
				queue.addAll(Arrays.asList(file.listFiles()));
			} else {
				long len = file.length();
//				if (len < 1024 * 1024 * 5) {
//					continue;
//				}
				Set<File> sameSizeFiles = sameSizeFilesMap.get(len);
				if (sameSizeFiles == null) {
					sameSizeFiles = new HashSet<File>();
					sameSizeFilesMap.put(len, sameSizeFiles);
				}
				sameSizeFiles.add(file);
			}
		}

		// size �� ���� ������ 2�� �̻��� �͸� �����
		for (Set<File> files : sameSizeFilesMap.values()) {
			if (files.size() > 1) {
				sameSizeFilesSet.add(files);
			}
		}

		return sameSizeFilesSet;
	}
}
