package server.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClientManager {

	private static Set<AbsClient> clientSet = Collections.synchronizedSet(new HashSet<>());

	public static void add(AbsClient client) {
		if (clientSet.contains(client)) {
			// disconnect old connection
			remove(client);
		} else {
			clientSet.add(client);
		}
	}

	public static void remove(AbsClient client) {
		clientSet.remove(client);
	}
}
