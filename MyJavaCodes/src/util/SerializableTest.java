package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableTest {

	private static class C1 implements Serializable {
		List<String> m1 = new ArrayList<>();

		public void init() {
			m1.add("a");
			m1.add("b");
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		C1 origin = new C1();
		origin.init();

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".\\backup.dat"))) {
			oos.writeObject(origin);
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".\\backup.dat"))) {
			C1 restored = (C1) ois.readObject();
			System.out.println(restored.m1);
		}
	}
}
