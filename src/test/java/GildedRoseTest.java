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
	public void testNormalItemQualityNeverNegative() {

		items.add(new Item("Normal item", 10, 10));
		GildedRose.setItems(items);
		addDays(100); // way over sellIn date
		items = GildedRose.getItems();
		assertFalse(items.get(0).getQuality() < 0);

	}

	@Test
	public void testTheTruth() {
		assertTrue(true);
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
