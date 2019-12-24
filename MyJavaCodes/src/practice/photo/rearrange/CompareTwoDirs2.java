package practice.photo.rearrange;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class CompareTwoDirs2 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		print(minus("E:\\사진_local작업중", "E:\\사진"));
	}

	private static void print(Set<MyFile> fileSet) {
		List<File> files = new LinkedList<>(fileSet);
		files.sort(new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}
		});
		for (File f : files) {
			System.out.println(f);
		}
	}

	private static class MyFile extends File {

		public MyFile(String pathname) {
			super(pathname);
		}

		@Override
		public int hashCode() {
			return (int) this.length();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (this.length() != ((File)obj).length()) {
				return false;
			}
			System.out.println(this.length());
			return isSameContent(this, (File) obj);
		}

		private boolean isSameContent(File f1, File f2) {
			try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(f1));
					BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(f2));) {
				while (true) {
					int d1 = bis1.read();
					int d2 = bis2.read();
					if (d1 != d2) {
						return false;
					} else if (d1 == -1) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	private static Set<MyFile> minus(String dir2, String dir1) {
		Set<MyFile> dir1Files = files(dir1);
		Set<MyFile> dir2Files = files(dir2);
		for(MyFile mf : dir1Files) {
			dir2Files.remove(dir1Files);			
		}
		return dir2Files;
	}

	private static Set<MyFile> files(String dir) {
		Set<MyFile> fs = new HashSet<>();
		Queue<File> q = new LinkedBlockingQueue<>();
		q.offer(new File(dir));
		while (!q.isEmpty()) {
			File f = q.poll();
			if (f.isDirectory()) {
				q.addAll(Arrays.asList(f.listFiles()));
			} else {
				fs.add(new MyFile(f.getPath()));
			}
		}
		return fs;
	}
}
