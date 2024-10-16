package sample.cafekiosk.unit.beverage;

// Beverage의 속성을 갖는 구현체
public class Latte implements Beverage {

    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4500;
    }
}
