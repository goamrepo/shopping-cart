package com.eshop.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sample implementation of a store inventory
 * @author goamit
 */
public class SampleInventory implements Inventory {
	
	private final static Set<Price> prices = new HashSet<Price>();
	private final static Set<Offer> offers = new HashSet<Offer>();
	
	static {
		Product a = new Product("Lemon");
		Product b = new Product("Banana");
		Product c = new Product("Orange");
		Product d = new Product("Apple");
		Product e = new Product("Peach");
		
		addPrice(a, 0.10);
		addPrice(b, 0.20);
		addPrice(c, 0.30);
		addPrice(d, 0.40);
		addPrice(e, 0.50);

		addOffer(a, OfferType.MBP, 3, 0.20);
		addOffer(b, OfferType.MBP, 2, 0.30);
	}
	
	private static void addPrice(Product product, double price) {
		prices.add(new Price(product, price));
	}

	private static void addOffer(Product product, OfferType type, int qty, double price) {
		offers.add(new Offer(product, type, qty, price));
	}
	
	/**
	 * Return prices exactly match to given product items
	 */
	public Map<String, Price> lookupPrices(Collection<String> items) {
		Map<String, Price> result = new HashMap<String, Price>();
		for (String item : items) {
			Supplier<Stream<Price>> filteredPrices = () -> prices.stream().filter(p -> p.getProduct().getName().equalsIgnoreCase(item));
			if(filteredPrices.get().count() > 0) {
				result.put(item, filteredPrices.get().findFirst().get());
			}
		}
		return result;
	}

	/**
	 * Return offers exactly match to given product items
	 */
	public Map<String, List<Offer>> lookupOffers(Collection<String> items) {
		Map<String, List<Offer>> result = new HashMap<String, List<Offer>>();
		for (String item : items) {
			Supplier<Stream<Offer>> filteredOffers = () -> offers.stream().filter(p -> p.getProduct().getName().equalsIgnoreCase(item));
			if(filteredOffers.get().count() > 0) {
				result.put(item, filteredOffers.get().collect(Collectors.toList()));
			}
		}
		return result;
	}
	
}
