import java.util.ArrayList;
import java.util.List;

public class GildedRose {

	private static List<Item> items = null;
	final static String brie = "Aged Brie";
	final static String passes = "Backstage passes to a TAFKAL80ETC concert";
	final static String sulfuras = "Sulfuras, Hand of Ragnaros";
	final static String conjurer = "Conjured Mana Cake";
	final static int normalItemDelta = -1;
	final static int increaseItemDelta = 1;

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

		for (final Item item : items) {
			final String itemName = item.getName();
			int itemQuality = item.getQuality();
			int itemSellIn = item.getSellIn();

			if (!handleSulfuras(item)) {
				if (!handleBrie(item)) {
					if (!handlePasses(item)) {
						if (!handleConjurer(item)) {
							if (itemQuality > 0) {

								item.setQuality(itemQuality + normalItemDelta);
								if (conjurer.equals(itemName)) {
									item.setQuality(itemQuality
											+ (2 * normalItemDelta));
								}
							}

							itemQuality = item.getQuality();

							item.setSellIn(itemSellIn - 1);

							itemSellIn = item.getSellIn();

							if (itemSellIn < 0) {
								if (itemQuality > 0) {

									if (!conjurer.equals(itemName)) {
										item.setQuality(itemQuality
												+ normalItemDelta);
									} else {
										item.setQuality(itemQuality + 2
												* normalItemDelta);
									}

								}
							}
						}
					}

				}
			}
		}

	}

	private static boolean handleConjurer(final Item item) {
		if (conjurer.equals(item.getName())) {
			int itemQuality = item.getQuality();
			int itemSellIn = item.getSellIn();

			if (itemQuality > 0) {
				item.setQuality(itemQuality + (2 * normalItemDelta));
			}

			itemQuality = item.getQuality();

			item.setSellIn(itemSellIn - 1);

			itemSellIn = item.getSellIn();

			if (itemSellIn < 0) {
				if (itemQuality > 0) {
					item.setQuality(itemQuality + 2 * normalItemDelta);
				}
			}

			itemQuality = item.getQuality();
			if (itemQuality < 0)
				item.setQuality(0);

			return true;
		}
		return false;
	}

	private static boolean handlePasses(final Item item) {
		if (passes.equals(item.getName())) {
			int itemQuality = item.getQuality();
			final int itemSellIn = item.getSellIn();

			if (itemQuality < 50) {
				if (itemSellIn > 10) {
					item.setQuality(itemQuality + increaseItemDelta);
				} else if (itemSellIn > 5) {
					item.setQuality(itemQuality + 2 * increaseItemDelta);

				} else {
					item.setQuality(itemQuality + 3 * increaseItemDelta);
				}

			}

			itemQuality = item.getQuality();

			if (itemQuality > 50)
				item.setQuality(50);

			if (itemSellIn == 0) {
				item.setQuality(0);
			}

			item.setSellIn(itemSellIn - 1);

			return true;
		}
		return false;
	}

	private static boolean handleSulfuras(final Item item) {
		if (sulfuras.equals(item.getName()))
			return true; // do nothing!
		return false;
	}

	private static boolean handleBrie(final Item item) {
		if (brie.equals(item.getName())) {
			int itemQuality = item.getQuality();
			int itemSellIn = item.getSellIn();

			item.setSellIn(itemSellIn - 1);
			itemSellIn = item.getSellIn();

			if (itemSellIn < 0) {
				item.setQuality(itemQuality + 2 * increaseItemDelta);
			} else {
				item.setQuality(itemQuality + increaseItemDelta);
			}

			itemQuality = item.getQuality();
			if (itemQuality > 50)
				item.setQuality(50);

			return true;
		}
		return false;
	}
}