package rent_manager;

import java.util.ArrayList;
import java.util.List;

import serializable_classes.Book;
import serializable_classes.Game;
import serializable_classes.Movie;
import serializable_classes.Movie.Genre;
import serializable_classes.Person;
import serializable_classes.Person.Gender;
import serializable_classes.Product;

public class RentManagerStart {
	private static List<Product> productList = new ArrayList<Product>();

	public static void main(String[] args) {

		// Creating some Person objects
		Person developer1 = new Person("Jhon", "Carmack", Gender.MALE, 500000);
		Person developer2 = new Person("Jhon", "Romero", Gender.MALE, 500000);
		Person developer3 = new Person("Dave", "Taylor", Gender.MALE, 450000);
		Person actor1 = new Person("Keanu", "Reeves", Gender.MALE, 280000);
		Person actor2 = new Person("Carrie-Anne", "Moss", Gender.FEMALE, 245000);
		Person actor3 = new Person("Marion", "Cotillard", Gender.FEMALE, 320000);
		Person actor4 = new Person("Leonardo", "Di Caprio", Gender.MALE, 295000);
		Person writer1 = new Person("David", "Mitchell", Gender.MALE, 2350000);
		Person writer2 = new Person("William", "Shakespear", Gender.MALE, 5250000);
		Person buyer1 = new Person("Jhon", "Doe", Gender.MALE);
		Person buyer2 = new Person("Jane", "Doe", Gender.FEMALE);

		// Creating staff for Game object(s)
		List<Person> staff1 = new ArrayList<Person>();
		staff1.add(developer1);
		staff1.add(developer2);
		staff1.add(developer3);

		// Creating cast for Movie object(s)
		List<Person> cast1 = new ArrayList<Person>();
		cast1.add(actor1);
		cast1.add(actor2);
		List<Person> cast2 = new ArrayList<Person>();
		cast2.add(actor3);
		cast2.add(actor4);

		// Creating some Products
		Game game1 = new Game("DOOM", buyer1, false, staff1, 50);
		Game game2 = new Game("DOOM II", buyer2, true, staff1, 60);
		Movie movie1 = new Movie("Matrix", buyer1, Genre.SCI_FI, 120, 9.5, cast1, 25);
		Movie movie2 = new Movie("Inception", buyer2, Genre.ACTION, 120, 9.0, cast2, 30);
		Book book1 = new Book("Cloud Atlas", buyer1, writer1);
		Book book2 = new Book("Macbeth", buyer2, writer2);

		// Adding Products to the productList
		addToList(game1);
		addToList(game2);
		addToList(movie1);
		addToList(movie2);
		addToList(book1);
		addToList(book2);

		// Creating a new client connection
		RentManager client = new RentManager(productList);
		client.connectToServer();
	}

	// Adds stuff to productList
	private static void addToList(Product prod) {
		productList.add(prod);
	}
}
