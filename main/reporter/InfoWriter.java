package reporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import datamanagement.Receipt;
import datamanagement.TaxpayerManager;

public abstract class InfoWriter implements FileWriter {

	public abstract String formatPrefix(int index);
	public abstract String formatPostfix(int index);
	public abstract String formatEnding();
	protected String attributes[] = {"Name", "AFM", "Status", "Income", "Receipts", "Receipt ID", "Date", "Kind", "Amount", "Company", "Country", "City", "Street", "Number"};
	
	  @Override
	  public void generateFile(int taxRegistrationNumber) throws IOException {
	    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + formatEnding()));
		TaxpayerManager manager = new TaxpayerManager();
	    outputStream.println(formatPrefix(0) + manager.getTaxpayerName(taxRegistrationNumber) + formatPostfix(0));
	    outputStream.println(formatPrefix(1) + taxRegistrationNumber + formatPostfix(1));
	    outputStream.println(formatPrefix(2) + manager.getTaxpayerStatus(taxRegistrationNumber) + formatPostfix(2));
	    outputStream.println(formatPrefix(3) + manager.getTaxpayerIncome(taxRegistrationNumber) + formatPostfix(3));
	    outputStream.println();
	    outputStream.println(formatPrefix(4));
	    outputStream.println();
	    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
	    outputStream.close();
	  }	

	  private void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
			TaxpayerManager manager = new TaxpayerManager();
		    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
		    Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
		    while (iterator.hasNext()) {
		      HashMap.Entry<Integer, Receipt> entry = iterator.next();
		      Receipt receipt = entry.getValue();
		      outputStream.println(formatPrefix(5) + receipt.getId() + formatPostfix(5));
		      outputStream.println(formatPrefix(6) + receipt.getIssueDate() + formatPostfix(6));
		      outputStream.println(formatPrefix(7) + receipt.getKind() + formatPostfix(7));
		      outputStream.println(formatPrefix(8) + receipt.getAmount() + formatPostfix(8));
		      outputStream.println(formatPrefix(9) + receipt.getCompany().getName() + formatPostfix(9));
		      outputStream.println(formatPrefix(10) + receipt.getCompany().getCountry() + formatPostfix(10));
		      outputStream.println(formatPrefix(11) + receipt.getCompany().getCity() + formatPostfix(11));
		      outputStream.println(formatPrefix(12) + receipt.getCompany().getStreet() + formatPostfix(12));
		      outputStream.println(formatPrefix(13) + receipt.getCompany().getNumber() + formatPostfix(13));
		      outputStream.println();
		    }
	   }
	
}
