import java.util.ArrayList;
import java.util.List;

public class GildedRose {

	private static List<Item> items = null;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		System.out.println("OMGHAI!");

		items = new ArrayList<Item>();
		items.add(new Item("+5 Dexterity Vest", 10, 20));
		items.add(new Item("Aged Brie", 2, 0));
		items.add(new Item("Elixir of the Mongoose", 5, 7));
		items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		items.add(new Item("Conjured Mana Cake", 3, 6));

		updateQuality();
	}

	/*
	 * need some way to get result of operations on items
	 */
	public static List<Item> getItems() {
		return items;
	}

	/*
	 * need some way to set the items
	 */
	public static void setItems(final List<Item> items) {
		GildedRose.items = items;
	}

	public static void updateQuality() {
		for (int i = 0; i < items.size(); i++) {
			final Item item = items.get(i);
			final String itemName = item.getName();
			int itemQuality = item.getQuality();
			int itemSellIn = item.getSellIn();
			if ((!"Aged Brie".equals(itemName))
					&& !"Backstage passes to a TAFKAL80ETC concert"
							.equals(itemName)) {
				if (itemQuality > 0) {
					if (!"Sulfuras, Hand of Ragnaros".equals(itemName)) {
						item.setQuality(itemQuality - 1);
					}
				}
			} else {
				if (itemQuality < 50) {
					item.setQuality(itemQuality + 1);
					itemQuality = item.getQuality();
					if ("Backstage passes to a TAFKAL80ETC concert"
							.equals(itemName)) {
						if (itemSellIn < 11) {
							if (itemQuality < 50) {
								item.setQuality(itemQuality + 1);
							}
						}
						itemQuality = item.getQuality();

						if (itemSellIn < 6) {
							if (itemQuality < 50) {
								item.setQuality(itemQuality + 1);
							}
						}
					}
				}
			}
			itemQuality = item.getQuality();

			if (!"Sulfuras, Hand of Ragnaros".equals(itemName)) {
				item.setSellIn(itemSellIn - 1);
			}

			itemSellIn = item.getSellIn();

			if (itemSellIn < 0) {
				if (!"Aged Brie".equals(itemName)) {
					if (!"Backstage passes to a TAFKAL80ETC concert"
							.equals(itemName)) {
						if (itemQuality > 0) {
							if (!"Sulfuras, Hand of Ragnaros".equals(itemName)) {
								item.setQuality(itemQuality - 1);
							}
						}
					} else {
						item.setQuality(itemQuality - itemQuality);
					}
				} else {
					if (itemQuality < 50) {
						item.setQuality(itemQuality + 1);
					}
				}
			}
		}
	}
}