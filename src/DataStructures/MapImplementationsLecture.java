package DataStructures;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/// # Реализации Map: Словари (Ассоциативные массивы)
/// Хранят пары "ключ-значение". Ключи уникальны. Не наследуют интерфейс `Collection`.
@SuppressWarnings("ALL")
public class MapImplementationsLecture {

    public static void main(String[] args) {
        demonstrateHashMap();
        demonstrateLinkedHashMap();
        demonstrateTreeMap();
    }

    /// ## HashMap
    /// Хэш-таблица. Ключи не упорядочены. Разрешает коллизии методом цепочек (с Java 8 списки трансформируются в деревья при размере корзины >= 8).
    ///
    /// **Временная сложность:**
    /// - `put(K key, V value)`: **O(1)** в среднем (до O(log N) в корзине-дереве).
    /// - `get(Object key)`: **O(1)**.
    /// - `remove(Object key)`: **O(1)**.
    /// - `containsKey(Object key)`: **O(1)**.
    public static void demonstrateHashMap() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("One", 1); // O(1)
        hashMap.put("Two", 2);

        Integer val = hashMap.get("One"); // O(1)
        hashMap.remove("Two"); // O(1)

        // Получение представлений коллекции (Views)
        var keys = hashMap.keySet(); // Set ключей
        var values = hashMap.values(); // Collection значений
        var entries = hashMap.entrySet(); // Set пар "ключ-значение"
    }

    /// ## LinkedHashMap
    /// Хэш-таблица с двусвязным списком, проходящим через все записи. 
    /// Позволяет сохранять порядок вставки ИЛИ порядок доступа (используется для LRU кэшей).
    ///
    /// **Временная сложность:** **O(1)** для базовых операций, как и у `HashMap`.
    public static void demonstrateLinkedHashMap() {
        // Третий параметр true включает порядок доступа (access-order) для LRU
        Map<String, String> lruMap = new LinkedHashMap<>(16, 0.75f, true);
        lruMap.put("A", "Alpha");
        lruMap.put("B", "Beta");

        lruMap.get("A"); // Элемент "A" перемещается в конец списка доступа
    }

    /// ## TreeMap
    /// На базе красно-черного дерева. Ключи отсортированы.
    ///
    /// **Временная сложность:**
    /// - `put(K key, V value)`: **O(log N)**.
    /// - `get(Object key)`: **O(log N)**.
    /// - `containsKey(Object key)`: **O(log N)**.
    public static void demonstrateTreeMap() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(100, "Max"); // O(log N)
        treeMap.put(1, "Min");

        Map.Entry<Integer, String> firstEntry = treeMap.firstEntry(); // (1, "Min")
    }
}