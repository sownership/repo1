package server.decoder;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordBaseDecoder implements IDecoder {

	private static Pattern fileNamePattern = Pattern.compile("[^\\.]*?\\.txt");
	private static Pattern numberPattern = Pattern.compile("[0-9]+");

	@Override
	public boolean isDecoderable(ByteBuffer buffer) {
		return buffer.position() > 0;
	}

	@Override
	public synchronized byte[] decode(ByteBuffer buffer) {
		String result = null;
		buffer.flip();
		String packetString = new String(buffer.array(), 0, buffer.remaining());
		if (packetString.startsWith("ACK")) {
			result = "ACK";
		} else if (packetString.startsWith("NAK")) {
			result = "NAK";
		} else {
			Matcher fileNameMatcher = fileNamePattern.matcher(packetString);
			if (fileNameMatcher.find()) {
				result = fileNameMatcher.group(0);
			}
			Matcher numberMatcher = numberPattern.matcher(packetString);
			if (numberMatcher.find()) {
				result = numberMatcher.group(0);
			}
		}
		if (result != null) {
			buffer.position(result.length());
			//todo compact ´Â È¿À²ÀÌ ¶³¾îÁü. ÇöÀç decoder ÀÚÃ¼°¡ È¿À²ÀÌ ¶³¾îÁü
			buffer.compact();
			return result.getBytes();
		}

		return null;
	}

}
