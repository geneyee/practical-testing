package sample.cafekiosk.unit.beverage;

// Beverage의 속성을 갖는 구현체
public class Americano implements Beverage {

    @Override
    public String getName() {
        return "아메리카노";
    }

    @Override
    public int getPrice() {
        return 4000;
    }
}
