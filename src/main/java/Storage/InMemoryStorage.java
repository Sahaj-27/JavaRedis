package Storage;

import java.util.concurrent.ConcurrentHashMap;

// This class is responsible for storing the data in memory

public class InMemoryStorage {
    // This map is used to store the data in memory
    private static final ConcurrentHashMap<String, Node> storage = new ConcurrentHashMap<>();
    // This method is used to clear the storage
    @SuppressWarnings("unused")
    public static void clear() {
        storage.clear();
    }
    // This method is used to put the data in the storage
    public static String put(String key, String value, Long expiration) {
        long currentTimeMillis = System.currentTimeMillis();
        Node node = new Node(value, expiration != null ? currentTimeMillis + expiration : null);
        Node previousValue = storage.put(key, node);
        if (previousValue != null && (previousValue.exp == null || previousValue.exp > currentTimeMillis)) {
            return previousValue.value;
        }
        return null;
    }
    // This method is used to get the data from the storage
    public static String get(String key) {
        long currentTimeMillis = System.currentTimeMillis();
        Node node = storage.get(key);
        if (node != null && (node.exp == null || node.exp > currentTimeMillis)) {
            return node.value;
        } else return null;
    }
    // This record is used to store the value and expiration time
    private record Node(String value, Long exp) { }
}