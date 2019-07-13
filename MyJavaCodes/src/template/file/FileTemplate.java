package template.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileTemplate {

	public List<String> fileReader(String file) {
		List<String> contents = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				contents.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(contents);
	}

	public void fileWriter(String file, List<String> contents) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for(String content : contents) {
				bw.write(content);
				bw.newLine();
			}
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fileInputStream(String file) {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] b = new byte[1024 * 8];
			int len;
			while ((len = bis.read(b)) != -1) {

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fileOutputStream(String file) {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
			byte[] b = new byte[1024 * 8];
			int len = 100;
			bos.write(b, 0, len);
			bos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void randomAccessFile(String fs) {
		try(RandomAccessFile file = new RandomAccessFile(fs, "rw");) {
			
			file.seek(3);
			int aByte = file.read();
			file.write("Hello World".getBytes());
			long pointer = file.getFilePointer();
			System.out.println(pointer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		FileTemplate ft = new FileTemplate();
		ft.randomAccessFile("C:\\ljg\\eclipse-jee-oxygen-2-win32\\ws\\git\\repo1\\MyJavaCodes\\resource\\SOURCEDIR\\ra.txt");
	}
}
