package DataStructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/// # Реализации List: Динамические массивы и Связные списки
/// `List` — упорядоченная коллекция, допускающая дубликаты.
public class ListImplementationsLecture {

    public static void main(String[] args) {
        demonstrateArrayList();
        demonstrateLinkedList();
    }

    /// ## ArrayList
    /// Реализован на базе динамического массива. Емкость по умолчанию — 10. При заполнении увеличивается в 1.5 раза.
    ///
    /// **Временная сложность основных методов:**
    /// - `add(E e)` (в конец): **O(1)** амортизированное.
    /// - `add(int index, E e)` (в середину): **O(N)** — требует сдвига элементов вправо.
    /// - `get(int index)`: **O(1)** — прямой доступ к памяти.
    /// - `set(int index, E e)`: **O(1)**.
    /// - `remove(int index)` / `remove(Object o)`: **O(N)** — поиск и сдвиг элементов влево.
    /// - `contains(Object o)`: **O(N)**.
    public static void demonstrateArrayList() {
        List<String> arrayList = new ArrayList<>();

        arrayList.add("Java");      // O(1)
        arrayList.add(0, "Spring"); // O(N)

        String val = arrayList.get(1);          // O(1)
        arrayList.set(1, "Java Core");          // O(1)

        boolean hasSpring = arrayList.contains("Spring"); // O(N)
        arrayList.remove("Spring");             // O(N)

        System.out.println("ArrayList: " + arrayList);
    }

    /// ## LinkedList
    /// Реализован как двусвязный список. Каждый узел хранит ссылку на предыдущий и следующий элементы.
    /// В современной разработке используется редко из-за промахов кэша процессора (cache misses) и оверхеда на узлы.
    ///
    /// **Временная сложность основных методов:**
    /// - `add(E e)` (в конец): **O(1)**.
    /// - `addFirst(E e)` / `addLast(E e)`: **O(1)**.
    /// - `add(int index, E e)`: **O(N)** — поиск нужного узла занимает время.
    /// - `get(int index)`: **O(N)** — последовательный перебор с начала или конца.
    /// - `remove(int index)`: **O(N)** для поиска + **O(1)** для перевешивания ссылок.
    /// - `contains(Object o)`: **O(N)**.
    public static void demonstrateLinkedList() {
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("Node 1");         // O(1)
        linkedList.addFirst("Node 0");    // O(1) - специфичный метод LinkedList
        linkedList.addLast("Node 2");     // O(1)

        String val = linkedList.get(1);   // O(N)
        linkedList.removeFirst();         // O(1)

        System.out.println("LinkedList: " + linkedList);
    }
}