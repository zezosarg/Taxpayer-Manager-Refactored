package datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import parser.FileReaderFactory;
import parser.WrongFileFormatException;
import parser.WrongReceiptDateException;
import parser.WrongReceiptKindException;
import reporter.InfoWriterFactory;
import reporter.LogWriterFactory;

public class TaxpayerManager {

  private static HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<Integer, Taxpayer>(0);
  private static HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<Integer, Integer>(0);

  public void createTaxpayer(String fullname, int taxRegistrationNumber, String status,float income)
  throws WrongTaxpayerStatusException {	  
    taxpayerHashMap.put(taxRegistrationNumber, new TaxpayerFactory().createTaxpayer(fullname, taxRegistrationNumber, status, income));    
  }

  public void createReceipt(int receiptId, String issueDate, float amount, String kind,
  String companyName, String country, String city, String street, int number,
  int taxRegistrationNumber) throws WrongReceiptKindException, WrongReceiptDateException {
    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, new Company(companyName, country, city, street, number));
    taxpayerHashMap.get(taxRegistrationNumber).addReceipt(receipt);
    receiptOwnerTRN.put(receiptId, taxRegistrationNumber);
  }

  public void removeTaxpayer(int taxRegistrationNumber) {
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
    taxpayerHashMap.remove(taxRegistrationNumber);
    HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
    Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
    while (iterator.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = iterator.next();
      Receipt receipt = entry.getValue();
      receiptOwnerTRN.remove(receipt.getId());
    }
  }

  public void addReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
  String country, String city, String street, int number, int taxRegistrationNumber)
  throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
    if (containsReceipt(receiptId)) { throw new ReceiptAlreadyExistsException(); }
    createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number, taxRegistrationNumber);
    updateFiles(taxRegistrationNumber);
  }

  public void removeReceipt(int receiptId) throws IOException, WrongReceiptKindException {
    taxpayerHashMap.get(receiptOwnerTRN.get(receiptId)).removeReceipt(receiptId);
    updateFiles(receiptOwnerTRN.get(receiptId));
    receiptOwnerTRN.remove(receiptId);
  }

  private void updateFiles(int taxRegistrationNumber) throws IOException {
	  if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
		  new InfoWriterFactory().createInfoWriter("txt").generateFile(taxRegistrationNumber);
	  }
	  if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
		  new InfoWriterFactory().createInfoWriter("xml").generateFile(taxRegistrationNumber);
	  }
  }

  public void saveLogFile(int taxRegistrationNumber, String fileFormat) throws IOException, WrongFileFormatException {
	  new LogWriterFactory().createLogWriter(fileFormat).generateFile(taxRegistrationNumber);
  }

  public boolean containsTaxpayer(int taxRegistrationNumber) {
    if (taxpayerHashMap.containsKey(taxRegistrationNumber)) {
      return true;
    }
    return false;
  }

  public boolean containsTaxpayer() {
    if (taxpayerHashMap.isEmpty()) {
      return false;
    }
    return true;
  }

  public boolean containsReceipt(int id) {
    if (receiptOwnerTRN.containsKey(id)) {
      return true;
    }
    return false;

  }

  public Taxpayer getTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber);
  }

  public void loadTaxpayer(String fileName) throws NumberFormatException, IOException, WrongFileFormatException,
  WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
	  new FileReaderFactory().createFileReader(fileName).readFile(fileName);
  }

  public String getTaxpayerName(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getFullname();
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingJointlyTaxpayer) {
      return "Married Filing Jointly";
    } else if (taxpayerHashMap
        .get(taxRegistrationNumber) instanceof MarriedFilingSeparatelyTaxpayer) {
      return "Married Filing Separately";
    } else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof SingleTaxpayer) {
      return "Single";
    } else {
      return "Head of Household";
    }
  }

  public String getTaxpayerIncome(int taxRegistrationNumber) {
    return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
  }

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
  }

  public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
  }

  public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, short kind) {
    return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
  }

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }

  public HashMap<Integer, Taxpayer> getTaxpayerHashMap() {
	    return taxpayerHashMap;
  }

}