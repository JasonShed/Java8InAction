package Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCase_3 {
    public static void main(String[] args) {
        /**
         * 创建流
         */
        //由值创建流
        Stream<String> stream1 = Stream.of("Java 8 ", "In ", "Action");
        stream1.map(String::toLowerCase).forEach(System.out::print);

        //创建空流
        Stream<String> stream2 = Stream.empty();

        //数组创建流
        int[] array1 = {1,2,3,4,5};
        int result1 = Arrays.stream(array1).sum();

        System.out.println("---------文件生成流---------");
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/data.txt"))) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(uniqueWords);


        System.out.println("---------创建无限流---------");
        Stream.iterate(0, n -> n+2).limit(10).forEach(n -> System.out.print(n + " "));
        System.out.println();
        System.out.println("---------生成斐波那契数列---------");
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1], t[0]+t[1]})
                .limit(10)
                .forEach(t -> System.out.print(t[0] + " "));

        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 1;
            }
        });
    }
}
