package parser;

import java.io.BufferedReader;
import java.io.IOException;

import datamanagement.TaxpayerManager;
import datamanagement.WrongTaxpayerStatusException;

public abstract class FileReader {
	
  protected abstract int getReceiptId(String[] values);
  protected abstract String getValue(String[] values);
  protected abstract boolean headMatchesFormat(String[] values);
	
  /**
   * 
   * @throws NumberFormatException 
   * @throws IOException xcvcxcvcxvxc
   * @throws WrongTaxpayerStatusException
   * @throws WrongFileFormatException
   * @throws WrongReceiptKindException
   * @throws WrongReceiptDateException
   */
  public void readFile(String fileName)
      throws NumberFormatException, IOException, WrongTaxpayerStatusException,
      WrongFileFormatException, WrongReceiptKindException, WrongReceiptDateException {

    BufferedReader inputStream = new BufferedReader(new java.io.FileReader(fileName));
    String fullname = getValueOfField(inputStream.readLine());
    int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
    String status = getValueOfField(inputStream.readLine());
    float income = Float.parseFloat(getValueOfField(inputStream.readLine()));
    createTaxpayer(fullname, taxRegistrationNumber, income, status);
    while (readReceipt(inputStream, taxRegistrationNumber))
      ;
  }

  protected boolean readReceipt(BufferedReader inputStream, int taxRegistrationNumber)
      throws WrongFileFormatException, IOException, WrongReceiptKindException,
      WrongReceiptDateException {

    int receiptId;
    if ((receiptId = checkForReceipt(inputStream)) < 0) {
      return false;
    }
    String issueDate = getValueOfField(inputStream.readLine());
    String kind = getValueOfField(inputStream.readLine());
    float amount = Float.parseFloat(getValueOfField(inputStream.readLine()));
    String companyName = getValueOfField(inputStream.readLine());
    String country = getValueOfField(inputStream.readLine());
    String city = getValueOfField(inputStream.readLine());
    String street = getValueOfField(inputStream.readLine());
    int number = Integer.parseInt(getValueOfField(inputStream.readLine()));
    createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
        taxRegistrationNumber);
    return true;
  }
  
  protected int checkForReceipt(BufferedReader inputStream) throws NumberFormatException, IOException {
    String line;
    while (!isEmpty(line = inputStream.readLine())) {
      String values[] = line.split(" ", 3);
      if(headMatchesFormat(values)) {
    	  return getReceiptId(values);
      }
    }
    return -1;
  }

  protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
    if (isEmpty(fieldsLine)) { throw new WrongFileFormatException(); }
    try {
		String values[] = fieldsLine.split(" ", 2);
      	return getValue(values); 
      
    } catch (NullPointerException e) {
      throw new WrongFileFormatException();
    }
  }

  protected void createTaxpayer(String fullname, int taxRegistrationNumber, float income,
      String status) throws WrongTaxpayerStatusException {

    TaxpayerManager manager = new TaxpayerManager();
    manager.createTaxpayer(fullname, taxRegistrationNumber, status, income);
  }

  protected void createReceipt(int receiptId, String issueDate, float amount, String kind,
      String companyName, String country, String city, String street, int number,
      int taxRegistrationNumber) throws WrongReceiptKindException, WrongReceiptDateException {

    TaxpayerManager manager = new TaxpayerManager();
    manager.createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street,
        number, taxRegistrationNumber);
  }

  protected boolean isEmpty(String line) {
    if (line == null) {
      return true;
    } else {
      return false;
    }
  }

}