package DataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

/// # Java Collections Framework и класс Collections
/// Архитектура базируется на интерфейсах `Collection` и `Map`.
/// Утилитный класс `java.util.Collections` предоставляет мощные статические методы (алгоритмы, обертки, фабрики).
@SuppressWarnings("ALL")
public class CollectionsLecture {

    public static void main(String[] args) {
        demonstrateIteratorsAndFailFast();
        demonstrateSpliterators();
        demonstrateUtilityAlgorithms();
        demonstrateSurrogateCollections();
        demonstrateCheckedCollections();
    }

    /// ## 1. Итераторы и механизм Fail-Fast
    /// `Iterator` — базовый интерфейс. Позволяет безопасно удалять элементы (`remove()`) во время обхода.
    /// `ListIterator` — расширенная версия для `List` (позволяет ходить в обе стороны и заменять элементы).
    ///
    /// **Fail-Fast поведение:** Стандартные коллекции отслеживают структурные изменения через внутренний счетчик `modCount`.
    /// Если изменить коллекцию напрямую (не через итератор) во время обхода, немедленно выбрасывается `ConcurrentModificationException`.
    public static void demonstrateIteratorsAndFailFast() {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));

        // 1. Стандартный Iterator: безопасное удаление
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("B")) {
                iterator.remove(); // Безопасно, O(N) для ArrayList
            }
        }

        // 2. Демонстрация Fail-Fast
        try {
            for (String s : list) {
                if (s.equals("A")) {
                    list.remove(s); // Прямая модификация сломает итератор (который скрыт под for-each)
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Fail-Fast: модификация коллекции в обход итератора запрещена.");
        }

        // 3. ListIterator: модификация и двунаправленность
        List<String> mutableList = new ArrayList<>(List.of("X", "Y"));
        ListIterator<String> listIterator = mutableList.listIterator();
        listIterator.next(); // Переходим за "X"
        listIterator.set("Modified_X"); // O(1), заменяет "X"
        listIterator.add("Z"); // Вставляет "Z" после "X"
    }

    /// ## 2. Spliterator (Splittable Iterator)
    /// Появился в Java 8. Специальный итератор, разработанный для параллельного обхода элементов (используется в Stream API).
    /// Умеет разделять коллекцию на части (`trySplit()`), чтобы разные потоки могли обрабатывать их независимо.
    public static void demonstrateSpliterators() {
        List<String> list = new ArrayList<>(List.of("Data1", "Data2", "Data3", "Data4"));

        Spliterator<String> mainSpliterator = list.spliterator();

        // trySplit() отщепляет половину элементов в новый Spliterator (для передачи в другой поток)
        Spliterator<String> parallelPart = mainSpliterator.trySplit();

        // tryAdvance() - аналог hasNext() + next(), принимает Consumer
        mainSpliterator.tryAdvance(item -> System.out.println("Main обрабатывает: " + item));

        if (parallelPart != null) {
            // forEachRemaining() - обработать все оставшиеся элементы
            parallelPart.forEachRemaining(item -> System.out.println("Parallel обрабатывает: " + item));
        }
    }

    /// ## 3. Алгоритмы класса Collections
    ///
    /// **Временная сложность:**
    /// - `sort()`: **O(N log N)**.
    /// - `binarySearch()`: **O(log N)** (требует отсортированной коллекции).
    /// - `addAll()`: **O(N)** — работает быстрее, чем `collection.addAll(Arrays.asList(...))`.
    public static void demonstrateUtilityAlgorithms() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 1, 9, 3, 7));

        Collections.sort(numbers); // O(N log N)
        int index = Collections.binarySearch(numbers, 5); // O(log N)

        Integer max = Collections.max(numbers); // O(N)
        Collections.reverse(numbers); // O(N)
        Collections.shuffle(numbers); // O(N)

        // Высокооптимизированное массовое добавление
        Collections.addAll(numbers, 100, 200, 300); // O(N)
    }

    /// ## 4. Суррогатные и неизменяемые коллекции
    /// Используются для защиты данных. Важно отличать "View" (обертку) от настоящей Immutable коллекции.
    public static void demonstrateSurrogateCollections() {
        List<String> emptyList = Collections.emptyList(); // O(1)
        List<String> singletonList = Collections.singletonList("A"); // O(1)

        // Unmodifiable-обертка: защищает от записи через саму обертку,
        // НО изменение оригинальной коллекции отразится в readOnlyView!
        List<String> mutableList = new ArrayList<>();
        List<String> readOnlyView = Collections.unmodifiableList(mutableList);
        mutableList.add("Data"); // readOnlyView теперь содержит "Data"

        // Настоящие Immutable коллекции (Java 9+). Копируют данные или запрещают ссылки на оригинал.
        List<String> trulyImmutable = List.of("A", "B", "C");
    }

    /// ## 5. Проверяемые коллекции (Checked Collections)
    /// Используются для динамической проверки типов. Незаменимы при интеграции с legacy-кодом,
    /// где используются Raw Types (сырые типы), чтобы избежать Heap Pollution (загрязнения кучи).
    public static void demonstrateCheckedCollections() {
        // Допустим, к нам пришел сырой список из старого Java 4 кода
        List rawList = new ArrayList();

        // Создаем проверяемую обертку.
        // Теперь при попытке добавить не String, исключение вылетит СРАЗУ на этапе add(),
        // а не позже, когда мы попытаемся сделать get().
        List<String> checkedList = Collections.checkedList(rawList, String.class);

        checkedList.add("Valid String");

        try {
            // Эмуляция поведения старого кода, который не знает о дженериках
            List legacyRef = checkedList;
            legacyRef.add(42); // Мгновенный ClassCastException! Ошибка локализована.
        } catch (ClassCastException e) {
            System.out.println("Checked collection предотвратила загрязнение кучи.");
        }
    }
}