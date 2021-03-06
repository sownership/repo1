package server.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// todo: weakreference
public class WhiteBoard {

	public static interface Listener {
		public void onEvent(String topic, Map<String, Object> params);
	}

	private static Map<String, Set<Listener>> topicListeners = new ConcurrentHashMap<>();

	public synchronized static void registerListener(String topic, Listener listener) {
		Set<Listener> listeners = topicListeners.get(topic);
		if (listeners == null) {
			listeners = Collections.synchronizedSet(new HashSet<>());
			topicListeners.put(topic, listeners);
		}
		listeners.add(listener);
	}

	public synchronized static void unregisterListener(String topic, Listener listener) {
		Set<Listener> listeners = topicListeners.get(topic);
		if (listeners == null) {
			return;
		}
		listeners.remove(listener);
	}

	public synchronized static void notify(String topic, Map<String, Object> params) {
		Set<Listener> listeners = topicListeners.get(topic);
		if (listeners == null) {
			return;
		}
		for (Listener l : listeners) {
			l.onEvent(topic, params);
		}
	}
}