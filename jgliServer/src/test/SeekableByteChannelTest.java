package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class SeekableByteChannelTest {

	public static void main(String[] args) throws IOException {

//		createBigFile();

		searchTest();
	}

	private static void searchTest() {

		long t = System.currentTimeMillis();

		Path path = Paths.get("d:\\temp\\bigfile4.txt");
		String encoding = System.getProperty("file.encoding");
		try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path,
				EnumSet.of(StandardOpenOption.READ))) {
			ByteBuffer buffer = ByteBuffer.allocate(8000);
			buffer.clear();

			for (long i = 0; i < 10000; i++) {
				seekableByteChannel.position(i * 1000 * 1000 * 1);
				seekableByteChannel.read(buffer);
				buffer.flip();
				Charset.forName(encoding).decode(buffer);
//				System.out.println(Charset.forName(encoding).decode(buffer));
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(System.currentTimeMillis() - t);
	}

	private static File createBigFile() throws IOException {
		File f = new File("d:\\temp\\bigfile.txt");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			for (int i = 0; i < 1000 * 1000 * 10; i++) {
				for (int j = 0; j < 10; j++)
					bw.write(
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
				bw.write("\n");
			}
		}
		return null;
	}
}
