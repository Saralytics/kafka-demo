package javastream;

import java.util.Arrays;
import java.util.List;

public class BasicJavaStream {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Cathy", "Dori", "Elijah");
        System.out.println("Output an array");
        System.out.println(names);
        System.out.println("Output a simple stream");
        System.out.println(names.stream());
        System.out.println("It's a location in memory");

        names.stream()
                .filter(v -> !v.startsWith("C"))
                .sorted()
                .forEach(v-> {
                    System.out.println(v);
                    System.out.println("-------------");
                }); // v is 1 record in the stream

    }

}
