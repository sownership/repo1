package server.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {

	private static Map<String, AbsClient> idClientMap = Collections.synchronizedMap(new HashMap<>());

	public static void add(String id, AbsClient client) {
		idClientMap.put(id, client);
	}

	public static void remove(String id) {
		idClientMap.remove(id);
	}

	public static AbsClient getClient(String id) {
		return idClientMap.get(id);
	}
}
