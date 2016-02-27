package object_server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import serializable_classes.Product;

public class SerializeObjects {

	public static void serializeObj(List<Product> products) {

		String path = "C:/Users/VMate/Workspace/movies_v3_serverside/saves/products.ser";
		try (FileOutputStream fo = new FileOutputStream(path)) {
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(products);
			out.close();
			fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
