package reporter;

import java.io.IOException;
import java.io.PrintWriter;

import datamanagement.TaxpayerManager;

public abstract class LogWriter implements FileWriter {
	
	private static final short ENTERTAINMENT = 0;
	private static final short BASIC = 1;
	private static final short TRAVEL = 2;
	private static final short HEALTH = 3;
	private static final short OTHER = 4;
	
	public abstract String formatPrefix(int index);
	public abstract String formatPostfix(int index);
	public abstract String formatEnding();
	protected String attributes[] = {"Name", "AFM", "Income", "Basic Tax", "Tax Increase", "Tax Decrease", "Total Tax", "TotalReceiptsGathered", "Entertainment", "Basic", "Travel", "Health", "Other"};

	  @Override
	  public void generateFile(int taxRegistrationNumber) throws IOException {
	    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + formatEnding()));
		TaxpayerManager manager = new TaxpayerManager();
	    outputStream.println(formatPrefix(0) + manager.getTaxpayerName(taxRegistrationNumber) + formatPostfix(0));
	    outputStream.println(formatPrefix(1) + taxRegistrationNumber + formatPostfix(1));
	    outputStream.println(formatPrefix(2) + manager.getTaxpayerIncome(taxRegistrationNumber) + formatPostfix(2));
	    outputStream.println(formatPrefix(3) + manager.getTaxpayerBasicTax(taxRegistrationNumber) + formatPostfix(3));
	    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
	      outputStream.println(formatPrefix(4) + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + formatPostfix(4));
	    } else {
	      outputStream.println(formatPrefix(5) + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + formatPostfix(5));
	    }
	    outputStream.println(formatPrefix(6) + manager.getTaxpayerTotalTax(taxRegistrationNumber) + formatPostfix(6));
	    outputStream.println(formatPrefix(7) + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + formatPostfix(7));
	    outputStream.println(formatPrefix(8) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT) + formatPostfix(8));
	    outputStream.println(formatPrefix(9) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC) + formatPostfix(9));
	    outputStream.println(formatPrefix(10) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL) + formatPostfix(10));
	    outputStream.println(formatPrefix(11) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH) + formatPostfix(11));
	    outputStream.println(formatPrefix(12) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER) + formatPostfix(12));
	    outputStream.close();
	  }

}
