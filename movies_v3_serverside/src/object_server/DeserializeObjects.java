package object_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import serializable_classes.Product;

public class DeserializeObjects {

	@SuppressWarnings("unchecked")
	public static List<Product> deserializeObj(File filePath) {
		List<Product> productList = new ArrayList<>();

		try (FileInputStream fi = new FileInputStream(filePath)) {
			ObjectInputStream in = new ObjectInputStream(fi);
			List<Product> products = (List<Product>) in.readObject();
			productList = products;
			in.close();
			fi.close();
		} catch (IOException e) {
			System.out.println("File not found!");
		} catch (ClassNotFoundException e) {
			System.out.println("Product class not found!");
		}
		return productList;
	}
}
