package server.decoder;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordBaseDecoder implements IDecoder {

	private static Pattern fileNamePattern = Pattern.compile("^.+\\..{3}");
	private static Pattern numberPattern = Pattern.compile("^[0-9]+");

	@Override
	public boolean isDecoderable(ByteBuffer buffer) {
		return buffer.position() > 0;
	}

	@Override
	public synchronized Map<String, Object> decode(ByteBuffer buffer) {
		Map<String, Object> result = new HashMap<>();
		int pos = 0;
		buffer.flip();
		String packetString = new String(buffer.array(), 0, buffer.remaining());
		if (packetString.startsWith("ACK")) {
			result.put("command", "ACK");
			pos = 3;
		} else if (packetString.startsWith("NAK")) {
			result.put("command", "NAK");
			pos = 3;
		} else {
			Matcher fileNameMatcher = fileNamePattern.matcher(packetString);
			if (fileNameMatcher.find()) {
				result.put("command", "FILE");
				result.put("message", fileNameMatcher.group(0));
				pos = fileNameMatcher.group(0).length();
			} else {
				Matcher numberMatcher = numberPattern.matcher(packetString);
				if (numberMatcher.find()) {
					result.put("command", "NUM");
					result.put("message", numberMatcher.group(0));
					pos = numberMatcher.group(0).length();
				}
			}
		}
		if (result != null) {
			buffer.position(pos);
			// todo compact ´Â È¿À²ÀÌ ¶³¾îÁü. ÇöÀç decoder ÀÚÃ¼°¡ È¿À²ÀÌ ¶³¾îÁü
			buffer.compact();
		}

		return result;
	}

}
