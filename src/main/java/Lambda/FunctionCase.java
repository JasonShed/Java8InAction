package Lambda;

import chp2.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class FunctionCase {
    //Predicate<T>
    public static <T> void filter(List<T> list, Predicate<T> p) {
        for (T t : list) {
            if (p.test(t)) {
                System.out.println(t);
            }
        }
    }

    //Consumer<T>
    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    //Function<T,R>
    //将一个string列表映射成包含每个string长度的Integer列表
    public static <T,R> void map(List<T> list, Function<T,R> f) {
        for (T t : list) {
            System.out.println(f.apply(t));
        }
    }

    //相同的Lambda，不同的函数式接口
    public static <T> void compare1(T t1, T t2, ToIntBiFunction<T,T> t) {
        System.out.println(t.applyAsInt(t1,t2));
    }
    public static <T> void compare2(T t1, T t2, Comparator<T> t) {
        System.out.println(t.compare(t1, t2));
    }


    public static void main(String[] args) {
        System.out.println("case 1:");
        filter(Arrays.asList(1,2,3,4,5), (Integer i) -> i >= 3);
        System.out.println("---------------------");
        System.out.println("case 2:");
        forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i));
        System.out.println("---------------------");
        System.out.println("case 3:");
        map(Arrays.asList("lambda", "in", "action"), (String s) -> s.length());
        System.out.println("---------------------");
        System.out.println("case 4:");
        compare1(111,33,(Integer i1, Integer i2) -> i1.compareTo(i2));
        compare2(111,33,(Integer i1, Integer i2) -> i1.compareTo(i2));


        //构造方法引用
        BiFunction<Integer,String,Apple> temp = Apple::new;
        temp.apply(Integer.valueOf(100), "red");
        //等同于
        BiFunction<Integer,String,Apple> tmp = (weight,color) -> new Apple(weight,color);
        tmp.apply(Integer.valueOf(100), "red");




        /**
         * Lambda和方法引用案例
         */
        //原始代码
        List<Apple> inventory = new ArrayList<>();
        inventory.sort(new AppleComparator());
        //使用匿名类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight());
            }
        });
        //使用lambda表达式
        inventory.sort((a1, a2) -> Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight()));
        //使用Comparator.comparing()
        inventory.sort(Comparator.comparing((a) -> a.getWeight()));
        //使用方法引用
        inventory.sort(Comparator.comparing(Apple::getWeight));


        //复合函数
        Function<Integer,Integer> f = x -> x+1;
        Function<Integer,Integer> g = x -> x*2;
        Function<Integer,Integer> h1 = f.andThen(g);    //等于g(f(x))
        Function<Integer,Integer> h2 = f.compose(g);    //等于f(g(x))

    }

}

class AppleComparator implements Comparator<Apple> {
    @Override
    public int compare(Apple a1, Apple a2) {
        return Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight());
    }
}