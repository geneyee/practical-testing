package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

class CafekioskTest {

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
	}

	// 이것이 제대로 된 테스트일까?
	// console에 결과 찍어보는 것 = 사람이 개입해야 된다.
	// 무엇을 테스트 하는 건지 알 수 없다. 무조건 성공하는 테스트이므로..
	// 수동 테스트와 자동화된 테스트의 차이를 인지해야 한다.
	// 자동화된 테스트 -> jUnit


}
