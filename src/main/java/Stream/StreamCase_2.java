package Stream;

import java.util.*;
import java.util.stream.Collectors;

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
                .map(Transaction::getValue)
                .reduce(0.0, Double::max);

        //8.找到交易额最小的交易
        Optional<Transaction> trans2 = transactions.stream()
                .reduce((t1,t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }
}
