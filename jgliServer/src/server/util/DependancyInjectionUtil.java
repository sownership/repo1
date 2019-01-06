package server.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependancyInjectionUtil {

	private static Map<String, Map<String, String>> fileKeyTypeMap = Collections.synchronizedMap(new HashMap<>());

	static {
		setFileKeyTypeMap("commandControllerMapper.properties");
		setFileKeyTypeMap("commandConverterMapper.properties");
	}

	private static void setFileKeyTypeMap(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] elements = line.split("#");
				Map<String, String> cmdController = fileKeyTypeMap.get(file);
				if (cmdController == null) {
					cmdController = Collections.synchronizedMap(new HashMap<>());
					fileKeyTypeMap.put(file, cmdController);
				}
				cmdController.put(elements[0], elements[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> T get(String file, String key, Class<T> c) {
		try {
			Class cls = Class.forName(fileKeyTypeMap.get(file).get(key));
			Class partypes[] = new Class[0];
			Constructor ct = cls.getConstructor(partypes);
			Object arglist[] = new Object[0];
			return (T) ct.newInstance(arglist);
		} catch (Throwable e) {
			System.err.println(e);
		}
		return null;
	}

	public static <T> List<T> getList(String file, String key, Class<T> c) {
		List<T> result = new ArrayList<>();
		for (String converter : fileKeyTypeMap.get(file).get(key).split("\\+")) {
			try {
				Class cls = Class.forName(fileKeyTypeMap.get(file).get(key));
				Class partypes[] = new Class[0];
				Constructor ct = cls.getConstructor(partypes);
				Object arglist[] = new Object[0];
				result.add((T) ct.newInstance(arglist));
			} catch (Throwable e) {
				System.err.println(e);
			}
		}

		return result;
	}
}
