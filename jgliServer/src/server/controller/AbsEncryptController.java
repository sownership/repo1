package server.controller;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;

import server.convert.AbsConvert;
import server.util.DependancyInjectionUtil;

public abstract class AbsEncryptController implements IController {

	private AsynchronousFileChannel fileChannel;
	private AbsConvert[] encrypters;

	protected ByteBuffer encrypt(ByteBuffer cmd, ByteBuffer data) {
		
		if(encrypters==null) {
			String command = new String(cmd.array());
			encrypters = DependancyInjectionUtil.getArray("commandEncrypt.properties", command, data,
					AbsConvert.class);
		}
		
		ByteBuffer result = ByteBuffer.wrap(data.array());
		for (AbsConvert encrypter : encrypters) {
			result = encrypter.encrypt(result);
		}

		return result;
	}
	
	protected ByteBuffer encryptRemain(ByteBuffer cmd, ByteBuffer data) {
		
		ByteBuffer result = ByteBuffer.wrap(data.array());
		for (AbsConvert encrypter : encrypters) {
			result = encrypter.encryptRemain(result);
		}

		return result;
	}
}
