package sample.cafekiosk.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.cafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime; // 주문일시
    private final List<Beverage> beverages;
}
