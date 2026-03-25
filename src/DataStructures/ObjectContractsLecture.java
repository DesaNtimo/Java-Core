package DataStructures;

import java.util.Objects;

/// # Superclass Object: Контракты
/// Все классы в Java неявно наследуют `Object`.
///
/// ### Правила equals и hashCode
/// - `hashCode` по умолчанию не зависит от полей. Он возвращает внутренний идентификатор (часто случайное число или адрес).
/// - Если `equals` возвращает `true`, `hashCode` объектов обязан совпадать.
@SuppressWarnings("ALL")
public class ObjectContractsLecture {

    public static void main(String[] args) {
        User user1 = new User(1, "Alice");
        User user2 = new User(1, "Alice");

        // Демонстрация контракта
        System.out.println("user1.equals(user2): " + user1.equals(user2)); // true
        System.out.println("user1 hashCode: " + user1.hashCode());
        System.out.println("user2 hashCode: " + user2.hashCode());
    }

    /// Пример правильного переопределения методов для консистентности.
    static class User {
        private final int id;
        private final String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            User user = (User) obj;
            return id == user.id && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}