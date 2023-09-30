package javastream;

import java.util.Arrays;
import java.util.List;

public class MapDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Noodles","Curry","Bun","Veggies","Soup");
        names.stream()
                .map(v -> v.toLowerCase())
                .map(v -> new Eater(v))
                .forEach(v-> System.out.println(v.getName()));

    }

    static class Eater {
        public String name;
        public Integer age = 30;

        public Eater(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
