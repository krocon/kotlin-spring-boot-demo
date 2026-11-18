package intro;

import java.util.Objects;

// Die klassische Java-Variante: alles von Hand.
public final class JavaUser {

    private final String name;
    private final int age;

    public JavaUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JavaUser other)) return false;
        return age == other.age && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "JavaUser[name=" + name + ", age=" + age + "]";
    }
}
