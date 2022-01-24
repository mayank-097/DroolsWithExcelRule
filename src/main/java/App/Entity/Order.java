package App.Entity;

import javax.persistence.*;


@Entity
@Table(name = "DiscountCalculation")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ruleId;
	@Column(nullable = false , unique = true)
	private String name;
	@Column(nullable = false , unique = true)
	private String cardType;
	@Column(nullable = false , unique = true)
	private int discount;
	private int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
