package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class FileChecksumHash {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// Create checksum for this file
		File file = new File("e:\\Seagate Dashboard Installer.dmg");

		// Use MD5 algorithm
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");

		// Get the checksum
		String checksum = getFileChecksum(md5Digest, file);

		// see checksum
		System.out.println(checksum);

		// Use SHA-1 algorithm
		MessageDigest shaDigest = MessageDigest.getInstance("SHA-1");

		// SHA-1 checksum
		String shaChecksum = getFileChecksum(shaDigest, file);
		
		// see checksum
		System.out.println(shaChecksum);
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
}
