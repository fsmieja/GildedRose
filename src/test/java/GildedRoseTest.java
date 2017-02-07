import static org.junit.Assert.assertEquals;
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
	public void testAllOddItemsQualityNeverNegative() {

		for (final String name : oddItemNames) {
			items.add(new Item(name, 5, 10));
		}
		setItems();
		addDays(100); // way over sellIn date
		for (final Item item : items) {
			assertFalse(item.getQuality() < 0);
		}
	}

	@Test
	public void testConjuredItemLossOfQualityDoublesAfterSellDate() {
		items.add(new Item(oddItemNames[3], 2, 20)); // this is Conjured
		setItems();
		addDays(1);
		final int qualityLossBeforeSellIn = 20 - items.get(0).quality;
		addDays(1); // sellIn day
		final int qualityAtSellIn = items.get(0).quality;
		addDays(1);
		final int qualityAfterSellIn = items.get(0).quality;
		final int qualityLossAfterSellIn = qualityAtSellIn - qualityAfterSellIn;
		assertTrue(qualityLossAfterSellIn == (qualityLossBeforeSellIn * 2));
	}

	@Test
	public void testConjuredItemsDecreaseTwiceAsFastAsNormalItems() {
		items.add(new Item(oddItemNames[3], 10, 10)); // this is Conjured
		items.add(new Item("Normal item", 10, 10));
		setItems();
		addDays(1);
		assertTrue((10 - items.get(0).quality) == 2 * (10 - items.get(1).quality));
	}

	@Test
	public void testPassesDecreaseValueToZeroAfterEvent() {
		items.add(new Item(oddItemNames[2], 1, 10)); // this is Passes
		setItems();
		addDays(2);
		assertTrue(items.get(0).quality == 0);
	}

	@Test
	public void testPassesIncreaseValueByTwoThenThree() {
		items.add(new Item(oddItemNames[2], 11, 10)); // this is Passes
		setItems();
		addDays(1);
		final int valueAtTen = items.get(0).quality;
		addDays(1);
		final int valueAtNine = items.get(0).quality;
		assertTrue(items.get(0).quality > 10);
		addDays(4);
		final int valueAtFive = items.get(0).quality;
		addDays(1);
		final int valueAtFour = items.get(0).quality;

		final int deltaPostTen = valueAtNine - valueAtTen;
		final int deltaPostFive = valueAtFour - valueAtFive;

		assertTrue(deltaPostTen == 2);
		assertTrue(deltaPostFive == 3);
		// interesting nowhere says that normal quality decrement/increment
		// should be 1,
		// so not testing for it
	}

	@Test
	public void testPassesIncreaseInValueWithAge() {
		items.add(new Item(oddItemNames[2], 10, 10)); // this is Sulfuras
		setItems();
		addDays(1);
		assertTrue(items.get(0).quality > 10);
	}

	@Test
	public void testSulfurasHasNoSellInAndNoQualityChange() {
		items.add(new Item(oddItemNames[1], 1, 49)); // this is Sulfuras
		setItems();
		addDays(100);
		assertEquals(items.get(0).sellIn, 1);
		assertEquals(items.get(0).quality, 49);
	}

	@Test
	public void testQualityNeverMoreThanFifty() {
		// at least for normal items and for brie and backstage
		items.add(new Item("Normal item", 9, 49));
		items.add(new Item(oddItemNames[0], 5, 40)); // this is Aged Brie
		items.add(new Item(oddItemNames[2], 9, 45)); // this is Backstage
		// ticket...
		setItems();
		addDays(100);
		assertFalse(items.get(0).getQuality() > 50);
		assertFalse(items.get(1).getQuality() > 50);
		assertFalse(items.get(2).getQuality() > 50);

	}

	@Test
	public void testBrieIncreasesInQuality() {
		items.add(new Item(oddItemNames[0], 10, 10)); // this is Aged Brie
		setItems();
		addDays(1);
		assertTrue(items.get(0).quality > 10);
	}

	@Test
	public void testNormalItemLossOfQualityDoublesAfterSellDate() {
		items.add(new Item("normal item", 2, 20));
		setItems();
		addDays(1);
		final int qualityLossBeforeSellIn = 20 - items.get(0).quality;
		addDays(1); // sellIn day
		final int qualityAtSellIn = items.get(0).quality;
		addDays(1);
		final int qualityAfterSellIn = items.get(0).quality;
		final int qualityLossAfterSellIn = qualityAtSellIn - qualityAfterSellIn;
		assertTrue(qualityLossAfterSellIn == (qualityLossBeforeSellIn * 2));
	}

	@Test
	public void testNormalItemQualityNeverNegative() {

		items.add(new Item("Normal item", 10, 10));
		setItems();
		addDays(100); // way over sellIn date
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
		// do we only need to setItems once? since items is static?
		// refactor to do in setUp?
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
