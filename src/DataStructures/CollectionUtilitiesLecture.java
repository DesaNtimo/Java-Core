package DataStructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/// # Утилитарные классы и Фабрики коллекций
/// В стандартной библиотеке Java инструментарий для работы с коллекциями и массивами разделен на:
/// 1. `java.util.Arrays` — утилиты для работы с массивами (сортировка, поиск, преобразование).
/// 2. Статические фабрики интерфейсов (`List.of`, `Set.of`, `Map.of`) — введены в Java 9 для создания неизменяемых (immutable) коллекций.
/// 3. `java.util.Collections` — алгоритмы для готовых коллекций.
@SuppressWarnings("ALL")
public class CollectionUtilitiesLecture {

    public static void main(String[] args) {
        demonstrateArraysUtility();
        demonstrateImmutableFactories();
        demonstrateAdvancedCollectionsUtils();
    }

    /// ## Класс Arrays: Утилиты для массивов
    /// Предоставляет статические методы для манипуляции массивами.
    ///
    /// **Временная сложность основных методов:**
    /// - `Arrays.sort()`: **O(N log N)**. Использует Dual-Pivot Quicksort для примитивов и TimSort для объектов.
    /// - `Arrays.binarySearch()`: **O(log N)**. Массив обязан быть предварительно отсортирован.
    /// - `Arrays.copyOf()` / `Arrays.copyOfRange()`: **O(N)**. Выделяет новую память и копирует элементы (под капотом `System.arraycopy`).
    /// - `Arrays.asList()`: **O(1)**. Создает обертку (View) над массивом. Размер фиксирован (нельзя `add`/`remove`), но элементы можно изменять (`set`).
    /// - `Arrays.equals()` / `Arrays.mismatch()`: **O(N)**.
    public static void demonstrateArraysUtility() {
        int[] numbers = {5, 2, 9, 1, 5, 6};

        // 1. Сортировка и поиск
        Arrays.sort(numbers); // O(N log N)
        int index = Arrays.binarySearch(numbers, 5); // O(log N) -> вернет индекс элемента 5

        // 2. Копирование
        int[] copied = Arrays.copyOf(numbers, 10); // O(N). Увеличивает размер массива, новые ячейки заполнятся 0.

        // 3. Сравнение (Java 9+)
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 4};
        int mismatchIndex = Arrays.mismatch(arr1, arr2); // Вернет 2 (индекс первого несовпадения)

        // 4. Мост между массивами и коллекциями
        String[] strings = {"Java", "Spring", "SQL"};
        List<String> fixedList = Arrays.asList(strings); // O(1)
        fixedList.set(0, "Java 21"); // Допустимо, изменит и массив!
        // fixedList.add("Docker");  // Выбросит UnsupportedOperationException
    }

    /// ## Статические фабрики (Java 9+ и Java 10+)
    /// Идеальный инструмент для создания констант и возврата безопасных данных.
    /// Контракт: **Абсолютная неизменяемость** и **запрет на null** (выбросит `NullPointerException`).
    ///
    /// **Временная сложность:**
    /// - `of()`: **O(N)** на создание, **O(1)** на доступ.
    /// - `copyOf()`: **O(N)**, если передана изменяемая коллекция. **O(1)**, если передана уже immutable-коллекция (просто вернет ссылку).
    public static void demonstrateImmutableFactories() {
        // 1. Создание Immutable List и Set
        List<String> immutableList = List.of("A", "B", "C");
        Set<Integer> immutableSet = Set.of(1, 2, 3); // Если передать дубликаты (Set.of(1, 1)) — выбросит IllegalArgumentException

        // 2. Создание Immutable Map
        Map<String, Integer> mapUpTo10 = Map.of(
                "One", 1,
                "Two", 2
        ); // Работает до 10 пар "ключ-значение"

        // Для большего количества пар используется Map.ofEntries
        Map<String, Integer> largeMap = Map.ofEntries(
                Map.entry("A", 1),
                Map.entry("B", 2)
                // ... неограниченное количество
        );

        // 3. copyOf (Java 10+) - Безопасное копирование данных извне
        List<String> mutableSource = new java.util.ArrayList<>(Arrays.asList("X", "Y"));
        List<String> safeSnapshot = List.copyOf(mutableSource);

        mutableSource.add("Z"); // Изменение оригинала НЕ отразится на safeSnapshot
    }

    /// ## Дополнительные алгоритмы Collections
    /// Помимо сортировки и бинарного поиска, класс содержит утилиты для проверки состояния и генерации данных.
    ///
    /// **Временная сложность:**
    /// - `Collections.frequency()`: **O(N)**. Подсчет вхождений.
    /// - `Collections.disjoint()`: **O(N)** или **O(N log N)**. Проверяет, нет ли общих элементов. Быстрее всего работает, если одна из коллекций — `Set`.
    /// - `Collections.nCopies()`: **O(1)**. Создает виртуальный список из N одинаковых элементов (экономит память).
    public static void demonstrateAdvancedCollectionsUtils() {
        List<String> items = Arrays.asList("Apple", "Banana", "Apple", "Orange");

        // 1. Частота элемента
        int appleCount = Collections.frequency(items, "Apple"); // O(N) -> вернет 2

        // 2. Проверка пересечений
        List<String> otherItems = Arrays.asList("Kiwi", "Mango");
        boolean isDisjoint = Collections.disjoint(items, otherItems); // O(N) -> вернет true (нет общих элементов)

        // 3. Генерация заглушек
        // Виртуальный список из 1000 строк "Default". В памяти хранится только одна строка и размер.
        List<String> defaultValues = Collections.nCopies(1000, "Default");
    }
}