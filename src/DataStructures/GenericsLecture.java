package DataStructures;

import java.util.ArrayList;
import java.util.List;

/// # Generics (Дженерики): Полное руководство
/// Дженерики обеспечивают строгую проверку типов на этапе компиляции, устраняя необходимость ручного кастования (`ClassCastException` предотвращается до запуска программы).
///
/// ### 1. Type Erasure (Стирание типов)
/// Дженерики существуют **только на этапе компиляции**.
/// В байткоде информация о типах (`<T>`) удаляется. Типы заменяются на их границы (обычно `Object`), а компилятор автоматически вставляет инструкции `CHECKCAST` там, где происходит чтение.
/// Для сохранения полиморфизма в наследниках компилятор также генерирует невидимые *bridge-методы*.
///
/// ### 2. Правило PECS (Producer Extends, Consumer Super)
/// - **Producer (`? extends T`)**: Если коллекция только поставляет данные — она ковариантна. Из нее можно читать, но нельзя писать.
/// - **Consumer (`? super T`)**: Если коллекция только принимает данные — она контрвариантна. В нее можно писать, но чтение возвращает только `Object`.
@SuppressWarnings("ALL")
public class GenericsLecture {

    public static void main(String[] args) {
        demonstrateInvarianceVsCovariance();
        demonstratePECS();
        demonstrateGenericMethodsAndBounds();
        demonstrateRestrictions();
    }

    /// ## 1. Инвариантность vs Ковариантность
    /// Дженерики **инвариантны**: `List<String>` никак не связан с `List<Object>`. Это защищает от ошибок типизации.
    /// Массивы **ковариантны**: `String[]` является подтипом `Object[]`, что оставляет дыру для `ArrayStoreException` в рантайме.
    public static void demonstrateInvarianceVsCovariance() {
        // Ковариантность массивов (Опасно!)
        String[] stringArray = {"Java", "Spring"};
        Object[] objectArray = stringArray;
        try {
            objectArray[0] = 42; // Успешно компилируется, но падает в рантайме
        } catch (ArrayStoreException e) {
            System.out.println("Ошибка массивов: " + e.getMessage());
        }

        // Инвариантность дженериков (Безопасно)
        List<String> stringList = new ArrayList<>();
        // List<Object> objectList = stringList; // ОШИБКА КОМПИЛЯЦИИ: несовместимые типы
    }

    /// ## 2. Маски (Wildcards) и правило PECS
    /// Использование масок позволяет обойти инвариантность дженериков там, где это необходимо.
    public static void demonstratePECS() {
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3));
        List<Number> numbers = new ArrayList<>();

        // ? extends Number (Ковариантность) - Коллекция выступает как Producer
        List<? extends Number> producer = integers;
        Number num = producer.get(0); // Читать безопасно, мы точно знаем, что там как минимум Number
        // producer.add(4); // ОШИБКА: компилятор не знает конкретный тип списка (это может быть List<Double>)

        // ? super Integer (Контрвариантность) - Коллекция выступает как Consumer
        List<? super Integer> consumer = numbers;
        consumer.add(42); // Писать безопасно, любой Integer является Number
        Object obj = consumer.get(0); // Читать можно только как Object, так как точный тип неизвестен
    }

    /// ## 3. Generic-методы и границы (Bounded Type Parameters)
    /// Параметры типов можно ограничивать. Конструкция `<T extends Comparable<T>>` означает:
    /// "Тип T должен реализовывать интерфейс Comparable для самого себя".
    public static <T extends Comparable<T>> T findMax(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    public static void demonstrateGenericMethodsAndBounds() {
        List<Integer> numbers = List.of(10, 50, 20);
        System.out.println("Максимальное число: " + findMax(numbers)); // Выведет 50
    }

    /// ## 4. Жесткие ограничения дженериков в Java
    /// Из-за обратной совместимости и стирания типов в Java есть ряд запретов.
    public static void demonstrateRestrictions() {
        // 1. Нельзя использовать примитивы (из-за стирания в Object)
        // List<int> ints = new ArrayList<>(); // ОШИБКА. Нужно использовать List<Integer>

        // 2. Нельзя создать экземпляр T или массив T
        // T obj = new T(); // ОШИБКА: компилятор не знает, какой конструктор вызывать
        // T[] array = new T[10]; // ОШИБКА: массив требует точной информации о типе в рантайме

        // 3. Нельзя использовать instanceof с параметризованными типами
        List<String> strings = new ArrayList<>();
        // if (strings instanceof List<String>) {} // ОШИБКА: в рантайме это просто List

        // Разрешено только с маской без границ:
        if (strings instanceof List<?>) {
            System.out.println("Проверка через List<?> допустима");
        }
    }
}