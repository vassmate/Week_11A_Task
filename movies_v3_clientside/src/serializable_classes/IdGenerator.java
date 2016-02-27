package serializable_classes;

public class IdGenerator {
	static int movieCounter = 0;
	static int gameCounter = 0;
	static int bookCounter = 0;

	public static String generator(Product product) {
		if (product instanceof Movie) {
			Movie movie = (Movie) product;
			return movie.id = "MOV" + (++movieCounter);
		} else if (product instanceof Game) {
			Game game = (Game) product;
			return game.id = "GAM" + (++gameCounter);
		} else if (product instanceof Book) {
			Book book = (Book) product;
			return book.id = "BOO" + (++bookCounter);
		} else {
			return "";
		}
	}
}
