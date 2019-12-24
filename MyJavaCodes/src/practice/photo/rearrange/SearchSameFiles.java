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
		Set<Set<File>> sameSizeFilesSet = getSameSizeFilesSet("E:\\사진_local작업중");
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		Set<Set<File>> sameContentFilesSet = getSameContentFiles(md5Digest, sameSizeFilesSet);
		print(sameContentFilesSet);
	}

	private static void print(Set<Set<File>> sameContentFilesSet) {
		// 결과를 보여준다
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

		// hashcode 가 같은 files
		Map<String, Set<File>> hashFilesMap = new HashMap<>();

		// sameSizeFiles 의 모든 files 에 대해 hash 로 분리한다.
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

		// hashFiles 에서 hash 별 2개 이상인 것들만 내용이 같은 것들이다
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

		// file lenth 별로 file들을 분류한다
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

		// size 가 같은 파일이 2개 이상인 것만 남긴다
		for (Set<File> files : sameSizeFilesMap.values()) {
			if (files.size() > 1) {
				sameSizeFilesSet.add(files);
			}
		}

		return sameSizeFilesSet;
	}
}
