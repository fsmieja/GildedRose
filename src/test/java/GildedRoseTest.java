import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GildedRoseTest {

	private List<Item> items = null;

	private final String[] oddItemNames = { "Aged Brie",
			"Sulfuras, Hand of Ragnaros",
			"Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake" };

	@Before
	public void setUp() throws Exception {
		items = new ArrayList<Item>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBrieIncreasesInQuality() {
		items.add(new Item(oddItemNames[0], 10, 10)); // this is Aged Brie
		setItems();
		addDays(1);
		getItems();
		assertTrue(items.get(0).quality > 10);
	}

	@Test
	public void testNormalItemLossOfQualityDoublesAfterSellDate() {
		items.add(new Item("normal item", 2, 20));
		setItems();
		addDays(1);
		getItems();
		final int qualityLossBeforeSellIn = 20 - items.get(0).quality;
		addDays(1); // sellIn day
		getItems();
		final int qualityAtSellIn = items.get(0).quality;
		addDays(1);
		getItems();
		final int qualityAfterSellIn = items.get(0).quality;
		final int qualityLossAfterSellIn = qualityAtSellIn - qualityAfterSellIn;
		assertTrue(qualityLossAfterSellIn == (qualityLossBeforeSellIn * 2));
	}

	@Test
	public void testNormalItemQualityNeverNegative() {

		items.add(new Item("Normal item", 10, 10));
		setItems();
		addDays(100); // way over sellIn date
		getItems();
		assertFalse(items.get(0).getQuality() < 0);

	}

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}

	/*
	 * a bit easier to set items
	 */
	private void setItems() {
		GildedRose.setItems(items);
	}

	/*
	 * a bit easier to retrieve items
	 */
	private void getItems() {
		items = GildedRose.getItems();
	}

	/*
	 * fast forward fixed number of days
	 */
	private void addDays(final int numDays) {
		for (int i = 0; i < numDays; i++) {
			GildedRose.updateQuality();
		}
	}

}
