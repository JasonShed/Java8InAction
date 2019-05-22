package Stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamCase_1 {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork",false,800, Dish.Type.MEAT),
                new Dish("beef",false,700, Dish.Type.MEAT),
                new Dish("chicken",false,400, Dish.Type.MEAT),
                new Dish("french fries",true,530, Dish.Type.OTHER),
                new Dish("rice",true,350, Dish.Type.OTHER),
                new Dish("season fruit",true,550, Dish.Type.OTHER),
                new Dish("pizza",true,550, Dish.Type.OTHER),
                new Dish("prawns",false,300, Dish.Type.FISH),
                new Dish("salmon",false,450, Dish.Type.FISH)
        );

        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories()<400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());


        /**
         * 流的扁平化flatmap
         * 把各个流进行合并生成一个流
         */
        //案例1：对于单词列表，返回其中含有的字符
        List<String> words = Arrays.asList("Java 8", "Stream", "In", "Action");
        List<String> uniqueCharacters = words.stream()
                .map(w -> w.split(""))
                //把数组进行合并
                .flatMap(Arrays::stream)
                .distinct().collect(Collectors.toList());

        //案例2：将2个数字列表转换成所有数对,只返回能被3整除的
        List<Integer> num1 = Arrays.asList(1,2,3);
        List<Integer> num2 = Arrays.asList(4,5);
        List<int[]> pairs = num1.stream()
                .flatMap(i -> num2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i,j}))
                .collect(Collectors.toList());


        /**
         * 查找和匹配
         */
        System.out.println("----------查找和匹配-----------");
        boolean isHealthy1 = menu.stream().anyMatch(Dish::isVegetarian);
        boolean isHealthy2 = menu.stream().allMatch(Dish::isVegetarian);
        boolean isHealthy3 = menu.stream().noneMatch(Dish::isVegetarian);
        //短路求值：不用处理整个流就能得到结果
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));
        //findAny()和findFirst()区别，在并行流场景下2个方法返回值可能会存在限制


        /**
         * 规约：reduce()
         */
        System.out.println("----------规约-----------");
        System.out.println(num1.stream().reduce(0, (a,b) -> a + b));
        //reduce()中无初始值，则返回Optional对象，防止stream为空
        System.out.println(num1.stream().reduce((a,b) -> a + b));
        System.out.println(num1.stream().reduce(Integer::max));

    }
}
