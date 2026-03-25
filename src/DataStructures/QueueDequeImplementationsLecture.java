package DataStructures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

/// # Реализации Queue и Deque: Очереди
/// `Queue` — очередь (FIFO). `Deque` — двусторонняя очередь (LIFO/FIFO), заменяет устаревший `Stack`.
public class QueueDequeImplementationsLecture {

    public static void main(String[] args) {
        demonstratePriorityQueue();
        demonstrateArrayDeque();
    }

    /// ## PriorityQueue
    /// Очередь с приоритетами, основанная на структуре данных "Куча" (Min-Heap).
    /// Элементы извлекаются не в порядке добавления, а в порядке их приоритета (Comparable / Comparator).
    ///
    /// **Временная сложность:**
    /// - `offer(E e)` / `add(E e)`: **O(log N)** — просеивание вверх.
    /// - `poll()` (извлечь минимум): **O(log N)** — извлечение корня и просеивание вниз.
    /// - `peek()` (посмотреть минимум): **O(1)**.
    /// - `remove(Object o)`: **O(N)**.
    public static void demonstratePriorityQueue() {
        Queue<Integer> pq = new PriorityQueue<>();
        pq.offer(10); // O(log N)
        pq.offer(1);
        pq.offer(5);

        System.out.println(pq.poll()); // Вернет 1 (O(log N))
    }

    /// ## ArrayDeque
    /// Двусторонняя очередь на базе кольцевого массива. Быстрее `LinkedList` при работе с обоими концами. Не допускает `null`.
    ///
    /// **Временная сложность:**
    /// - `addFirst(E e)` / `addLast(E e)`: **O(1)** амортизированное.
    /// - `pollFirst()` / `pollLast()`: **O(1)**.
    /// - `peekFirst()` / `peekLast()`: **O(1)**.
    public static void demonstrateArrayDeque() {
        Deque<String> stackAndQueue = new ArrayDeque<>();

        // Как Стек (LIFO)
        stackAndQueue.push("Task 1"); // Эквивалент addFirst
        stackAndQueue.push("Task 2");
        System.out.println(stackAndQueue.pop()); // Вернет "Task 2"

        // Как Очередь (FIFO)
        stackAndQueue.offer("Task 3"); // Эквивалент offerLast
        System.out.println(stackAndQueue.poll()); // Вернет "Task 1"
    }
}