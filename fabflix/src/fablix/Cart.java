package fablix;
import java.util.LinkedHashMap;

public class Cart {

	// Map of items and item info
	private LinkedHashMap<String, CartItem> cart;
	private int totalPrice;
	public Cart() {
		cart = new LinkedHashMap<String, CartItem>();
		setTotalPrice(0);
	}
	
	public CartItem get(String item) {
		return cart.get(item);
	}
	public boolean contains(String item) {
		return cart.containsKey(item);
	}
	
	public void addItem(String item, String id) {
		if (!contains(item)){
			cart.put(item, new CartItem(1,5, item, id));
			setTotalPrice(getTotalPrice() + 5);
		}
	}
	
	public void deleteItem(String item) {
		if (contains(item)){
			CartItem in = cart.get(item);
			setTotalPrice(getTotalPrice() - in.getCount()*in.getPrice());
			cart.remove(item);
		}
	}
	public void modifyAmount(String item, int amount) {
		if ( amount == 0 ) {
			this.deleteItem(item);
		} else {
			CartItem in = cart.get(item);
			setTotalPrice(getTotalPrice() - in.getCount()*in.getPrice());
			cart.get(item).setCount(amount);
			setTotalPrice(getTotalPrice() + 5*amount);
		}
			
	}
	
	public LinkedHashMap<String,CartItem> items() {
		return cart;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
	
	
