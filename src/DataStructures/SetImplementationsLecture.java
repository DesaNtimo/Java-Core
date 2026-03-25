package DataStructures;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/// # Реализации Set: Множества
/// `Set` гарантирует уникальность элементов. Все стандартные реализации под капотом используют соответствующие `Map`.
@SuppressWarnings("ALL")
public class SetImplementationsLecture {

    public static void main(String[] args) {
        demonstrateHashSet();
        demonstrateLinkedHashSet();
        demonstrateTreeSet();
    }

    /// ## HashSet
    /// Базируется на `HashMap`. Элементы не упорядочены. Лучший выбор для обеспечения уникальности.
    ///
    /// **Временная сложность:**
    /// - `add(E e)`: **O(1)** (в худшем случае O(N) или O(log N) при коллизиях).
    /// - `remove(Object o)`: **O(1)**.
    /// - `contains(Object o)`: **O(1)**.
    public static void demonstrateHashSet() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("A"); // O(1)
        hashSet.add("B");
        hashSet.add("A"); // Игнорируется, O(1)

        boolean hasA = hashSet.contains("A"); // O(1)
        hashSet.remove("B"); // O(1)
    }

    /// ## LinkedHashSet
    /// Базируется на `LinkedHashMap`. Сохраняет порядок добавления элементов благодаря дополнительному двусвязному списку.
    ///
    /// **Временная сложность:** аналогична `HashSet` (**O(1)** для `add`, `remove`, `contains`), но требует чуть больше памяти.
    public static void demonstrateLinkedHashSet() {
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("First");
        linkedHashSet.add("Second");

        // Порядок итерации гарантированно: "First", затем "Second"
    }

    /// ## TreeSet
    /// Базируется на `TreeMap` (Красно-черное дерево). Элементы автоматически сортируются (Natural Ordering или по `Comparator`).
    ///
    /// **Временная сложность:**
    /// - `add(E e)`: **O(log N)**.
    /// - `remove(Object o)`: **O(log N)**.
    /// - `contains(Object o)`: **O(log N)**.
    /// - Доп. методы: `first()`, `last()`, `higher()`, `lower()` — **O(log N)**.
    public static void demonstrateTreeSet() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(10); // O(log N)
        treeSet.add(5);
        treeSet.add(20);

        Integer min = treeSet.first(); // 5
        Integer strictlyGreater = treeSet.higher(10); // 20
    }
}