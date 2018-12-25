package server.controller;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;

import server.encrypt.Encrypt;
import server.util.DependancyInjectionUtil;

public abstract class AbsEncryptController implements IController {

	private AsynchronousFileChannel fileChannel;
	private Encrypt[] encrypters;

	protected ByteBuffer encrypt(ByteBuffer cmd, ByteBuffer data) {
		
		if(encrypters==null) {
			String command = new String(cmd.array());
			encrypters = DependancyInjectionUtil.getArray("commandEncrypt.properties", command, command,
					Encrypt.class);
		}
		
		ByteBuffer result = ByteBuffer.wrap(data.array());
		for (Encrypt encrypter : encrypters) {
			result = encrypter.encrypt(result);
		}

		return result;
	}
	
	protected ByteBuffer encryptRemain(ByteBuffer cmd, ByteBuffer data) {
		
		ByteBuffer result = ByteBuffer.wrap(data.array());
		for (Encrypt encrypter : encrypters) {
			result = encrypter.encryptRemain(result);
		}

		return result;
	}
}
