package practice.vcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) throws IOException {
		
        Path p = Paths.get("\\");
        Path p2 = Paths.get("a\\b");
        System.out.println(p2.startsWith(p));
	}
	
	private static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
}
