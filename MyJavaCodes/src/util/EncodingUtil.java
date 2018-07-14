package util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class EncodingUtil {

	public static String encodeBase64(byte[] b) throws UnsupportedEncodingException {
		Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(b);
		return encodedString;
	}

	public static byte[] decodeBase64(String s) {
		Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(s);
		return decodedBytes;
	}
}
