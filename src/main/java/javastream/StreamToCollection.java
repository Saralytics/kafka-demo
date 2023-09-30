package javastream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamToCollection {
    public static void main(String[] args) {
        List<Integer> sum = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toList());

        System.out.println(sum);

    }
}
