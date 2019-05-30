package Stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCase_2 {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2013,950)
                );

        //1.找出2011年发生的所有交易，并按交易额排序（升序）
        List<Transaction> trans1 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        //2.交易员都来自哪些城市
        List<String> cities1 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct().collect(Collectors.toList());

        Set<String> cities2 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());

        //3.查找所有来自剑桥的交易员，按姓名排序
        List<Trader> traders1 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        //4.返回所有交易员的姓名字符串，按字母顺序排序
        String name1 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1,n2) -> n1 + n2);

        //5.有没有交易员在米兰工作？
        boolean inMilan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));

        //6.打印生活在剑桥的交易员的所有交易额
        double value1 = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0.0, (n1,n2) -> n1+n2);

        //7.所有交易中最高的交易额
        double value2 = transactions.stream()
                .map(Transaction::getValue)     //返回double类型
                .reduce(0.0, Double::max);  //含有装箱成本

        /**
         * 为了减少隐藏的装箱成本，使用原始类型流
         *
         * 原始类型流
         * IntStream、DoubleStream、LongStream
         * 对应Optional原始类型特化
         * OptionalInt、OptionalDouble、OptionalLong
         */
        OptionalDouble value3 = transactions.stream()
                .mapToDouble(Transaction::getValue)     //返回DoubleStream
                .max();
        System.out.println(value3.getAsDouble());

        //boxed():原始流转换成一般流
        DoubleStream doubleStream = transactions.stream().mapToDouble(Transaction::getValue);
        Stream<Double> stream = doubleStream.boxed();

        //8.找到交易额最小的交易
        Optional<Transaction> trans2 = transactions.stream()
                .reduce((t1,t2) -> t1.getValue() < t2.getValue() ? t1 : t2);


        /**
         * 使用原始类型流确定数值范围
         * range()、rangeClosed()
         */
        //案例：生成1-100之间（包括100）的偶数
        IntStream evenNums = IntStream.rangeClosed(1,100).filter(n -> n % 2 == 0);
        System.out.println(evenNums.count());

        System.out.println("------------案例：找出1-100之间勾股数勾股数--------------");
        Stream<int[]> result1 = IntStream.rangeClosed(1,100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a,100)
                        .filter(b -> (Math.sqrt(a*a+b*b) % 1 == 0))     //缺陷，需计算2次平方根
                        .mapToObj(b -> new int[]{a,b,(int)Math.sqrt(a*a + b*b)})
                );
        result1.limit(5).forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));

        Stream<double[]> result2 = IntStream.rangeClosed(1,100)
                .boxed()
                //每个a值对应一个stream，最终需要合并成一个流
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
                        .filter(t -> t[2]%1==0)
                );
        //Stream<double[]> r = IntStream.rangeClosed(0,100).mapToObj(b -> new double[]{1,b,Math.sqrt(1+b*b)}).filter(t -> t[2]%1==0);
        result2.limit(5).forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));

    }
}
