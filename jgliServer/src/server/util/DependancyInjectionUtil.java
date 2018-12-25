package server.util;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DependancyInjectionUtil {

	private static Map<String, Map<String, String>> fileKeyTypeMap = Collections.synchronizedMap(new HashMap<>());

	public static <T> T get(String file, String key, String msg, Class<T> c) {
		try {
			Class cls = Class.forName(fileKeyTypeMap.get(file).get(key));
			Class partypes[] = new Class[1];
			partypes[0] = String.class;
			Constructor ct = cls.getConstructor(partypes);
			Object arglist[] = new Object[1];
			arglist[0] = msg;
			return (T) ct.newInstance(arglist);
		} catch (Throwable e) {
			System.err.println(e);
		}
		return null;
	}

	public static <T> T[] getArray(String string, String command, String command2, Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}
}
