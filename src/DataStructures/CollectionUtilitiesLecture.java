package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/// # Утилитарные классы и Фабрики коллекций
/// В стандартной библиотеке Java инструментарий для работы с коллекциями и массивами разделен на:
/// 1. `java.util.Arrays` — утилиты для работы с массивами (сортировка, поиск, преобразование, потоки).
/// 2. Статические фабрики интерфейсов (`List.of`, `Set.of`, `Map.of`) — для создания неизменяемых (immutable) коллекций.
/// 3. `java.util.Collections` — алгоритмы и обертки для готовых коллекций.
@SuppressWarnings("ALL")
public class CollectionUtilitiesLecture {

    public static void main(String[] args) {
        demonstrateArraysUtility();
        demonstrateDeepArrayUtils();
        demonstrateImmutableFactories();
        demonstrateAdvancedCollectionsUtils();
        demonstrateSynchronizedWrappers();
    }

    /// ## Класс Arrays: Утилиты для массивов
    /// Предоставляет статические методы для манипуляции массивами.
    ///
    /// **Временная сложность основных методов:**
    /// - `Arrays.sort()`: **O(N log N)**. Использует Dual-Pivot Quicksort (для примитивов) и TimSort (для объектов).
    /// - `Arrays.binarySearch()`: **O(log N)**. Массив обязан быть предварительно отсортирован.
    /// - `Arrays.copyOf()`: **O(N)**. Выделяет новую память и копирует элементы.
    /// - `Arrays.asList()`: **O(1)**. Создает View фиксированного размера над массивом.
    /// - `Arrays.fill()`: **O(N)**. Заполняет массив одинаковым значением.
    /// - `Arrays.stream()`: **O(1)** на создание потока. Главный мост к Stream API.
    public static void demonstrateArraysUtility() {
        int[] numbers = {5, 2, 9, 1, 5, 6};

        // Сортировка и поиск
        Arrays.sort(numbers);
        int index = Arrays.binarySearch(numbers, 5); // O(log N)

        // Копирование и заполнение
        int[] copied = Arrays.copyOf(numbers, 10); // O(N), новые ячейки будут 0
        Arrays.fill(copied, 7, 10, -1); // O(N), заполнит индексы с 7 по 9 числом -1

        // Сравнение (Java 9+)
        int mismatchIndex = Arrays.mismatch(new int[]{1, 2}, new int[]{1, 3}); // Вернет 1

        // Мост к Stream API (очень важно для бэкенда)
        IntStream stream = Arrays.stream(numbers); // O(1) на создание

        // Мост к коллекциям
        List<String> fixedList = Arrays.asList("A", "B"); // O(1)
        fixedList.set(0, "Z"); // Допустимо, изменит исходный массив под капотом
    }

    /// ## Работа с многомерными массивами
    /// Обычные `equals()` и `toString()` у массивов не работают глубоко (проверяют/выводят только верхний уровень ссылок).
    /// Для вложенных массивов (матриц) обязательно использовать методы с приставкой `deep`.
    public static void demonstrateDeepArrayUtils() {
        String[][] matrix1 = {{"A", "B"}, {"C", "D"}};
        String[][] matrix2 = {{"A", "B"}, {"C", "D"}};

        boolean isEq = Arrays.equals(matrix1, matrix2); // false! Сравнивает ссылки вложенных массивов
        boolean isDeepEq = Arrays.deepEquals(matrix1, matrix2); // true! Рекурсивно сравнивает содержимое (O(N))

        String str = Arrays.deepToString(matrix1); // [[A, B], [C, D]] (O(N))
        System.out.println("Глубокий вывод массива: " + str);
    }

    /// ## Статические фабрики (Java 9+ и Java 10+)
    /// Идеальный инструмент для создания констант.
    /// Контракт: **Абсолютная неизменяемость** и **запрет на null** (выбросит `NullPointerException`).
    ///
    /// **Временная сложность:**
    /// - `of()`: **O(N)** на создание, **O(1)** на доступ.
    /// - `copyOf()`: **O(N)** для изменяемых, **O(1)** если передана уже immutable-коллекция.
    public static void demonstrateImmutableFactories() {
        List<String> immutableList = List.of("A", "B", "C");
        Set<Integer> immutableSet = Set.of(1, 2, 3); // Дубликаты вызовут IllegalArgumentException

        // Попытка передать null вызовет NullPointerException
        // List.of("A", null);

        Map<String, Integer> map = Map.of("One", 1, "Two", 2); // До 10 пар
        Map<String, Integer> largeMap = Map.ofEntries( // Для > 10 пар
                Map.entry("A", 1), Map.entry("B", 2)
        );

        // Безопасное копирование данных извне (Java 10+)
        List<String> mutableSource = new ArrayList<>(Arrays.asList("X", "Y"));
        List<String> safeSnapshot = List.copyOf(mutableSource);
        mutableSource.add("Z"); // НЕ отразится на safeSnapshot
    }

    /// ## Дополнительные и мутирующие алгоритмы Collections
    ///
    /// **Временная сложность:**
    /// - `Collections.frequency()`, `replaceAll()`, `disjoint()`: **O(N)**.
    /// - `Collections.swap()`: **O(1)** для `ArrayList`, **O(N)** для `LinkedList`.
    /// - `Collections.nCopies()`: **O(1)**.
    public static void demonstrateAdvancedCollectionsUtils() {
        List<String> items = Arrays.asList("Apple", "Banana", "Apple", "Orange");

        // Подсчет вхождений
        int count = Collections.frequency(items, "Apple"); // 2

        // Проверка пересечений
        boolean isDisjoint = Collections.disjoint(items, List.of("Kiwi")); // true (нет общих)

        // Мутирующие операции
        Collections.replaceAll(items, "Apple", "Mango"); // Заменит все "Apple" на "Mango"
        Collections.swap(items, 0, 1); // Поменяет местами 0 и 1 элементы

        // Генерация заглушек (в памяти хранится только 1 объект)
        List<String> defaultValues = Collections.nCopies(1000, "Default");
    }

    /// ## Потокобезопасные обертки (Legacy)
    /// `Collections` позволяет сделать обычную коллекцию потокобезопасной.
    /// Все методы оборачиваются в блок `synchronized` по мьютексу самой коллекции.
    /// **Важно:** При итерации по такой коллекции требуется ручная синхронизация!
    /// (В современном коде предпочтительнее классы из `java.util.concurrent`, например `ConcurrentHashMap`).
    public static void demonstrateSynchronizedWrappers() {
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add("Thread-safe add"); // Синхронизировано внутри

        // При обходе через итератор (в т.ч. for-each) синхронизация обязательна
        synchronized (syncList) {
            for (String s : syncList) {
                System.out.println(s);
            }
        }
    }
}