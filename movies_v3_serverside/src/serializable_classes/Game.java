package serializable_classes;

import java.io.Serializable;
import java.util.List;

public class Game extends Product implements Buyable, Serializable {

	private static final long serialVersionUID = -1344066014904773762L;
	boolean preOrdered;
	List<Person> staff;
	int price;

	public Game(String title, Person person, boolean preOrdered, List<Person> staff, int price) {
		super(title, person);
		this.preOrdered = preOrdered;
		this.staff = staff;
		this.price = price;
	}

	public boolean isPreOrdered() {
		return preOrdered;
	}

	public void setPreOrdered(boolean preOrdered) {
		this.preOrdered = preOrdered;
	}

	public List<Person> getStaff() {
		return staff;
	}

	public void setStaff(List<Person> staff) {
		this.staff = staff;
	}

	@Override
	public int getPrice() {
		if (isPreOrdered()) {
			return price - price / 5;
		}
		return price;

	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getInvestment() {
		long investment = 0;

		for (Person person : staff) {
			investment += person.getSalary();
		}
		return investment;
	}

	@Override
	public String toString() {
		return "\n\t|Id: " + id + " |Title: " + title + " |Buyer: " + person + " |Price: " + price + "|";
	}
}
