package sample.cafekiosk.unit;

import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafekioskTest {

	@Test
	void add_manual_test() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());

		// 이것이 제대로 된 테스트일까?
		// console에 결과 찍어보는 것 = 사람이 개입해야 된다.
		// 무엇을 테스트 하는 건지 알 수 없다. 무조건 성공하는 테스트이므로..
		// 수동 테스트와 자동화된 테스트의 차이를 인지해야 한다.
		// 자동화된 테스트 -> jUnit
	}

//	@DisplayName("음료 1개 추가 테스트")
	@DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
		assertThat(cafeKiosk.getBeverages()).hasSize(1); // 위에랑 같은 뜻인데 .hasSize()는 list의 사이즈가 무엇인지
		assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	// 해피 케이스
	@Test
	void addSeveralBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano, 2);

		assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
		assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
	}

	// 경계값 테스트
	@Test
	void addZeroBeverages() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

//		cafeKiosk.add(americano, 0);
		assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		// 제거하려면 우선 음료가 있는 상황을 만들어야 함
		cafeKiosk.add(americano); // 아메리카노 추가
		assertThat(cafeKiosk.getBeverages()).hasSize(1);

		cafeKiosk.remove(americano);
		assertThat(cafeKiosk.getBeverages()).hasSize(0);
		assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	void clear() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		assertThat(cafeKiosk.getBeverages()).hasSize(2);

		cafeKiosk.clear();
		assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	// calculateTotalPrice() TDD로 구현하기
	@DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
	@Test
	void calculateTotalPrice() {
		// given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);

		// when
		int totalPrice = cafeKiosk.calculateTotalPrice();

		// then
		assertThat(totalPrice).isEqualTo(8500);
	}

	@Test
	void createOrder() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder();
		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

		// 이것이 항상 통과하는 테스트일까?? 테스트시간이 주문시간 범위 내를 벗어나면 테스트를 실패한다.
	}

	@Test
	void createOrderWithCurrentTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 10, 17, 10, 0));

		assertThat(order.getBeverages()).hasSize(1);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void createOrderOutsideOpenTime() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);

		assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 59)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
	}

}
