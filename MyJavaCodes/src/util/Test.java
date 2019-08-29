package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) throws IOException {
		
        Path p = Paths.get("c:\\ljg\\tmp\\123.java");
        List<String> lines = Files.readAllLines(p);
        IntStream.range(0, 533).forEach(i->{
        	String tmp = lines.get(i);
        	lines.set(i, reverse(lines.get(1065-i)));
        	lines.set(1065-i, reverse(tmp));
        });
        
        Path p2 = Paths.get("c:\\ljg\\tmp\\1234.java");
        Files.write(p2, lines);
        
        Path p3 = Paths.get("c:\\ljg\\tmp\\12345.java");
        IntStream.range(0, 533).forEach(i->{
        	String tmp = lines.get(i);
        	lines.set(i, reverse(lines.get(1065-i)));
        	lines.set(1065-i, reverse(tmp));
        });
        Files.write(p3, lines);
	}
	
	private static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}
}
