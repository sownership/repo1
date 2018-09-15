package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class FileUtil {

	public static void main(String[] args) throws IOException {
		dirCopy("C:\\ljg\\tmp\\1", "C:\\ljg\\tmp\\2");
		dirDelete("C:\\ljg\\tmp\\2\\1");
	}

	private static void dirCopy(String src, String destParent) throws IOException {

		File srcD = new File(src);
		File destD = new File(destParent, srcD.getName());
		destD.mkdir();
		File[] fs = srcD.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				// getCanonicalPath 는 disk cost 발생한다고 함
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

	private static void dirMove(String src, String dest) throws IOException {
		dirCopy("C:\\ljg\\tmp\\1", "C:\\ljg\\tmp\\2");
		dirDelete("C:\\ljg\\tmp\\2\\1");
	}

	private static void apply(String oldFile, String newFile, Map<String, String> targets) {
		try(BufferedReader br = new BufferedReader(new FileReader(oldFile));
				BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
			String line;
			while((line=br.readLine())!=null) {
				for(Entry<String, String> e : targets.entrySet()) {
					String key = e.getKey();
					if(line.contains(key)) {
						Pattern p = Pattern.compile("^\\s*" + key + "\\s*=.*");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
