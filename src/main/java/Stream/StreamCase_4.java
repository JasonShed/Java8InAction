package Stream;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class StreamCase_4 {
    public enum CaloricLevel { DIET, NORMAL, FAT }

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

        /**
         * 数据归约
         * 区分Collection、Collectors、Comparator
         */
        //查找流中最大/小值
        Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
        Optional<Dish> mostCaloriesdish = menu.stream().collect(maxBy(dishCaloriesComparator));
        Optional<Dish> leastCaloriesdish = menu.stream().collect(minBy(dishCaloriesComparator));

        //求菜单总热量
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));

        //求菜单平均热量
        double avageCalories = menu.stream().collect(averagingInt(Dish::getCalories));

        //通过一次操作求平均、最大、最小和总和
        DoubleSummaryStatistics menuStatistics = menu.stream().collect(summarizingDouble(Dish::getCalories));
        menuStatistics.getAverage();

        //连接字符串
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining(", ")));

        //reducing()
        menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        menu.stream().collect(Collectors.reducing((d1,d2) -> d1.getCalories() < d2.getCalories() ? d1 : d2));


        /**
         * 分组
         */
        //根据菜类分组
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));

        //根据热量高低分组
        Map<CaloricLevel, List<Dish>> dishesByCalories = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() < 300) return CaloricLevel.DIET;
            else if (dish.getCalories() < 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));

        //多级分组
        //通过collectingAndThen()将收集器结果转换为另一种类型
        Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)   //设置每个组数据的类型
        ));

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelByType = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        },
                        toCollection(HashSet::new)
                )));


        /**
         * 分区：结果区分true和false的两套流
         */
        //区分蔬菜
        Map<Boolean, Map<Dish.Type, List<Dish>>> partitioningBymenu = menu.stream().collect(
                partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        //找出蔬菜和非蔬菜中热量最高的菜
        Map<Boolean, Dish> mostCaloricPartitionByVegetable = menu.stream().collect(
                partitioningBy(Dish::isVegetarian, collectingAndThen(
                        maxBy(Comparator.comparingDouble(Dish::getCalories)), Optional::get)
                ));

    }
}
