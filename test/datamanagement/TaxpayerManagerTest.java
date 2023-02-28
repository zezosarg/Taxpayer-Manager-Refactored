package datamanagement;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import parser.WrongFileFormatException;
import parser.WrongReceiptDateException;
import parser.WrongReceiptKindException;

public class TaxpayerManagerTest {

	private TaxpayerManager tpManager;

	@Before
	public void setUp() throws Exception {
		tpManager = new TaxpayerManager();
		tpManager.createTaxpayer("Apostolos Zarras", 130456093, "Married Filing Jointly", 22570.0f);
		tpManager.createTaxpayer("Apostolos Zarras", 123456789, "Married Filing Jointly", 22570.0f);
	}

	@Test
	public void testAddReceipt() throws WrongTaxpayerStatusException, WrongReceiptDateException, WrongReceiptKindException, IOException, ReceiptAlreadyExistsException {
        tpManager.addReceipt(4, "13/12/2023", 50.0f, "Health", "XtremeStores", "Greece", "Ioannina", "Dodonis", 6, 130456093);
		Receipt expectedReceipt = new Receipt(4, "13/12/2023", 50.0f, "Health", new Company("XtremeStores", "Greece", "Ioannina", "Dodonis", 6));
		assertEquals(expectedReceipt, tpManager.getTaxpayerHashMap().get(130456093).getReceiptHashMap().get(4));
	}

	@Test
	public void testRemoveReceipt() throws WrongReceiptDateException, WrongReceiptKindException, IOException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException {
        tpManager.addReceipt(2, "12/12/2022", 100.0f, "Basic", "Zara", "Greece", "Ioannina", "Trikoupi", 28, 130456093);
        tpManager.removeReceipt(2);
        assertNull(tpManager.getTaxpayerHashMap().get(130456093).getReceiptHashMap().get(2));
	}

	@Test
	public void testLoadTaxpayer() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
		Taxpayer taxpayer = new TaxpayerFactory().createTaxpayer("Apostolos Zarras", 130456093, "Married Filing Jointly", 22570.0f);
		tpManager.loadTaxpayer("130456093_INFO.txt");
		assertEquals(taxpayer, tpManager.getTaxpayerHashMap().get(130456093));
	}

	@Test
	public void testRemoveTaxpayer() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
		tpManager.loadTaxpayer("130456093_INFO.txt");
		tpManager.removeTaxpayer(130456093);
		assertNull(tpManager.getTaxpayerHashMap().get(130456093));
	}

	@Test
	public void testSaveLogFile() throws IOException, WrongFileFormatException, WrongTaxpayerStatusException, NumberFormatException, WrongFileEndingException, WrongReceiptKindException, WrongReceiptDateException {
		/* This is what 123456789_LOG.txt contains after savaLogFile in legacy with input original 123456789_INFO.txt.
		 * It differs from original 123456789_LOG.txt. Albeit behavior is same as legacy.
		 */
		String expected = "Name: Apostolos Zarras"+"AFM: 123456789"+"Income: 22570.0"+"Basic Tax: 1207.495"+"Tax Increase: 48.2998"+
		"Total Tax: 1255.7948"+"TotalReceiptsGathered: 5"+"Entertainment: 0.0"+"Basic: 4801.0"+"Travel: 100.0"+"Health: 0.0"+"Other: 1000.0";
				
		tpManager.loadTaxpayer("123456789_INFO.txt");
		tpManager.saveLogFile(123456789, "txt");		
		
		String actual = "";
        BufferedReader reader = new BufferedReader(new FileReader("123456789_LOG.txt"));
        String line = reader.readLine();
		while (line != null) {
			actual += line;
			line = reader.readLine();
		}
		reader.close();

		assertEquals(expected, actual);			 
	}
}
