package Stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * 自定义一个收集器
 */
public class PrimeNumbersCollector implements Collector {

    /**
     *
     * @return 返回调用时创建累加器的方法
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {    //这层{作用是构造匿名类
            {   //这层{作用是实例初始化
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
            }
        };
    }

    @Override
    public BiConsumer accumulator() {
        return null;
    }

    @Override
    public BinaryOperator combiner() {
        return null;
    }

    @Override
    public Function finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }

    public static boolean isPrime(List<Integer> primes, Integer candidate) {
        int candidateRoot = (int)Math.sqrt((double)candidate);
        return true;
    }
    
}
