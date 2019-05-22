package chp2;

import java.util.Arrays;
import java.util.List;

public class Case_1 {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(
                new Apple(40, "red"),
                new Apple(170, "green"),
                new Apple(90, "red"),
                new Apple(230, "green")
        );

        System.out.println("工厂模式");
        prettyPrintApple(apples, new AppleFancyFormatter());
        System.out.println("-----------------------");
        prettyPrintApple(apples, new AppleSimpleFormatter());

        System.out.println("-----------------------");
        System.out.println("匿名类");
        prettyPrintApple(apples, new AppleFormatter() {
            @Override
            public String accept(Apple apple) {
                if ("red".equals(apple.getColor())) {
                    return "red apple";
                } else {
                    return "not red apple";
                }
            }
        });

        System.out.println("-----------------------");
        System.out.println("Lambda表达式");
        prettyPrintApple(apples, (apple) -> apple.getColor());
    }


    public static void prettyPrintApple(List<Apple> apples, AppleFormatter appleFormatter) {
        for (Apple apple : apples) {
            String result = appleFormatter.accept(apple);
            System.out.println(result);
        }
    }
}
