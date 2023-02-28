package parser;

public class TXTFileReader extends FileReader {

  @Override
  protected int getReceiptId(String[] values) {
	  return Integer.parseInt(values[2].trim());       
  }
  
  @Override
  protected boolean headMatchesFormat(String[] values) {
	  return values[0].equals("Receipt") && values[1].equals("ID:"); 
  }

  @Override
  protected String getValue(String[] values) {
  
      return values[1].trim();
  } 

}