package server.convert;

import java.nio.ByteBuffer;

public class WordMergeConvert extends AbsConvert {

	@Override
	public ByteBuffer convert(ByteBuffer data) {
		data.flip();
		remain.put(data);
		remain.flip();
		convertResult.clear();

		int cnt = 0;
		byte last = remain.get();
		cnt++;
		while (remain.hasRemaining()) {
			byte now = remain.get();
			if (last == now) {
				cnt++;
			} else {
				convertResult.put(String.valueOf(cnt).getBytes());
				convertResult.put(last);
				last = now;
				cnt = 1;
			}
		}
		remain.position(remain.position() - cnt);
		remain.compact();

		return convertResult;
	}

	@Override
	public ByteBuffer convertRemain(ByteBuffer data) {
		data.flip();
		remain.put(data);
		remain.flip();
		convertResult.clear();

		int cnt = 0;
		byte last = remain.get();
		cnt++;
		while (remain.hasRemaining()) {
			byte now = remain.get();
			if (last == now) {
				cnt++;
			} else {
				convertResult.put(String.valueOf(cnt).getBytes());
				convertResult.put(last);
				last = now;
				cnt = 1;
			}
		}
		if (cnt > 0) {
			convertResult.put(String.valueOf(cnt).getBytes());
			convertResult.put(last);
		}

		return convertResult;
	}

}
