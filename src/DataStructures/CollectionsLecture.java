package DataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/// # Java Collections Framework и класс Collections
/// Архитектура для работы с коллекциями базируется на интерфейсах `Collection` и `Map`.
/// Утилитный класс `java.util.Collections` предоставляет статические методы для работы с ними (алгоритмы, обертки, фабрики).
@SuppressWarnings("ALL")
public class CollectionsLecture {

    public static void main(String[] args) {
        demonstrateIteratorsAndFailFast();
        demonstrateUtilityAlgorithms();
        demonstrateSurrogateCollections();
    }

    /// ## Итераторы и Fail-Fast поведение
    /// `Iterator` — базовый интерфейс для обхода коллекций. Позволяет безопасно удалять элементы во время обхода.
    /// `ListIterator` — расширенная версия для `List`, позволяющая ходить в обе стороны и заменять элементы.
    /// Большинство стандартных коллекций являются **fail-fast**: они выбрасывают `ConcurrentModificationException`,
    /// если коллекция была изменена напрямую (не через итератор) во время итерации.
    public static void demonstrateIteratorsAndFailFast() {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));

        // 1. Стандартный Iterator: безопасное удаление
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("B")) {
                iterator.remove(); // Безопасно, O(N) для ArrayList
            }
        }
        System.out.println("После Iterator.remove: " + list);

        // 2. Демонстрация Fail-Fast
        try {
            for (String s : list) {
                if (s.equals("A")) {
                    list.remove(s); // Изменение напрямую во время foreach (который использует Iterator под капотом)
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Получен ConcurrentModificationException из-за прямой модификации.");
        }

        // 3. ListIterator: модификация и двунаправленность
        List<String> mutableList = new ArrayList<>(List.of("X", "Y"));
        ListIterator<String> listIterator = mutableList.listIterator();
        listIterator.next(); // Переходим за "X"
        listIterator.set("Modified_X"); // Заменяем последний пройденный элемент
        listIterator.add("Z"); // Вставляем "Z" после "X"

        System.out.println("После ListIterator: " + mutableList);
    }

    /// ## Алгоритмы класса Collections
    /// Класс `Collections` содержит статические методы для сортировки, поиска и манипуляций.
    public static void demonstrateUtilityAlgorithms() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 1, 9, 3, 7));

        // Сортировка (TimSort). Временная сложность: O(N log N)
        Collections.sort(numbers);
        System.out.println("Отсортированный список: " + numbers);

        // Бинарный поиск (работает ТОЛЬКО на отсортированных списках). Временная сложность: O(log N)
        int index = Collections.binarySearch(numbers, 5);
        System.out.println("Индекс элемента '5': " + index);

        // Поиск максимума и минимума. Временная сложность: O(N)
        System.out.println("Максимум: " + Collections.max(numbers));
        System.out.println("Минимум: " + Collections.min(numbers));

        // Разворот списка. Временная сложность: O(N)
        Collections.reverse(numbers);
        System.out.println("Развернутый список: " + numbers);

        // Случайное перемешивание. Временная сложность: O(N)
        Collections.shuffle(numbers);
        System.out.println("Перемешанный список: " + numbers);
    }

    /// ## Суррогатные и неизменяемые коллекции (Unmodifiable / Immutable)
    /// Используются для экономии памяти (отсутствует оверхед) и защиты данных от случайных изменений при передаче.
    public static void demonstrateSurrogateCollections() {
        // 1. Пустые коллекции (Singleton, не создают новых объектов)
        List<String> emptyList = Collections.emptyList();

        // 2. Коллекции из одного элемента (высокооптимизированные)
        List<String> singletonList = Collections.singletonList("One");

        // 3. Unmodifiable-обертки над существующими коллекциями
        List<String> mutableList = new ArrayList<>();
        mutableList.add("Data");
        List<String> readOnlyView = Collections.unmodifiableList(mutableList);

        // Изменение оригинального списка отразится в readOnlyView!
        mutableList.add("More Data");
        System.out.println("Read-only view видит изменения оригинала: " + readOnlyView);

        // 4. Настоящие Immutable коллекции (начиная с Java 9+)
        // Изменения оригиналов (если они были) не повлияют, модифицировать нельзя, null не допускается.
        List<String> trulyImmutable = List.of("A", "B", "C");

        try {
            readOnlyView.add("Fail");
        } catch (UnsupportedOperationException e) {
            System.out.println("Попытка изменить unmodifiable-коллекцию вызывает UnsupportedOperationException.");
        }
    }
}