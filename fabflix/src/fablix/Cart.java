package fablix;
import java.util.LinkedHashMap;

public class Cart {

	// Map of items and item info
	private LinkedHashMap<String, CartItem> cart;
	public Cart() {
		cart = new LinkedHashMap<String, CartItem>();
	}
	
	public CartItem get(String item) {
		return cart.get(item);
	}
	public boolean contains(String item) {
		return cart.containsKey(item);
	}
	
	public void addItem(String item, String id) {
		if (!contains(item))
			cart.put(item, new CartItem(1,5, item, id));
	}
	
	public void deleteItem(String item) {
		if (contains(item))
			cart.remove(item);
	}
	public void modifyAmount(String item, int amount) {
		if ( amount == 0 ) {
			cart.remove(item);
		} else {
			cart.get(item).setCount(amount);
		}
			
	}
	
	public LinkedHashMap<String,CartItem> items() {
		return cart;
	}
}
	
	
