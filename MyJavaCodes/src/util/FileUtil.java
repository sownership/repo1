package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {

	public static void main(String[] args) throws IOException {
		dirCopy("C:\\ljg\\tmp\\1", "C:\\ljg\\tmp\\2");
		dirDelete( "C:\\ljg\\tmp\\2\\1");
	}

	private static void dirCopy(String src, String destParent) throws IOException {

		File srcD = new File(src);
		File destD = new File(destParent, srcD.getName());
		destD.mkdir();
		File[] fs = srcD.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				//getCanonicalPath 는 disk cost 발생한다고 함
				dirCopy(f.getAbsolutePath(), destD.getAbsolutePath());
			} else {
				Files.copy(f.toPath(), new File(destD, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private static void dirDelete(String dir) throws IOException {
		File target = new File(dir);
		File[] fs = target.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				dirDelete(f.getAbsolutePath());
			} else {
				f.delete();
			}
		}
		target.delete();
	}
}
