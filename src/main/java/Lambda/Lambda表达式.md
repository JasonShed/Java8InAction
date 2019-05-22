## Lambda语法
```$xslt
() -> {}
(Integer i) -> {return "number:" + i;}  //注意return是一个语句，需要{}
() -> "Hello!"  //单纯表达式不需要{}
(List<String> list) -> list.isEmpty();
() -> new Apple(20);    //创建对象
```
Lambda表达式中可以使用局部变量，但该变量必须是final类型或显式声明为final。
原因：局部变量保存在栈中

## 环绕执行(execute around)模式
步骤1、2、3见代码

## 函数式接口
只定义一个抽象方法的接口。例如Runnable类、Comparator类。可以在函数式接口上使用Lambda表达式。

## 方法引用
本质是根据已有但方法实现来创建Lambda表达式
```aidl
inventory.sort(comparing(Apple::getWeight));
Apple::getWeight等同于(Apple a) -> a.getWeight()
```

3种方法引用
- 指向静态方法：如Integer::parseInt
- 指向任意类型实例方法：String::length
- 指向现有对象的实例方法

构造函数引用
```aidl
#对于无参构造函数
Supplier<Apple> a = Apple::new;
Apple apple = a.get();

#含参构造函数
List<Integer> weights = Arrays.asList(3,4,7,10);
List<Apple> apples = map(weights, Apple::new);

public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
    List<Apple> result = new ArrayList<>();
    for (Integer i : list) {
        result.add(f.apply(i));
    }
    return result;
}

```







