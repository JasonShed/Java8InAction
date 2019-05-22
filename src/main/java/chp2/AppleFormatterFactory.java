package chp2;

/**
 * 区分apple轻重，大于等于150g为重，小于150g为轻
 */
class AppleFancyFormatter implements AppleFormatter {

    public String accept(Apple apple) {
        String characteristic = (apple.getWeight() >= 150) ? "heavy" : "light";
        return "A " + characteristic + " " + apple.getColor() + " apple";
    }
}


/**
 * 只显示apple的重量
 */
class AppleSimpleFormatter implements AppleFormatter {

    public String accept(Apple apple) {
        return "An apple of " + apple.getWeight() + "g";
    }
}
