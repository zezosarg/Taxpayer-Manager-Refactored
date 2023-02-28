package parser;

public class XMLFileReader extends FileReader {

  @Override
  protected int getReceiptId(String[] values) {
	  return Integer.parseInt(values[1].trim());
  }
  
  @Override
  protected boolean headMatchesFormat(String[] values) {
	  return values[0].equals("<ReceiptID>");
  }

  @Override
  protected String getValue(String[] values) {
      String valueReversed[] = new StringBuilder(values[1]).reverse().toString().trim().split(" ", 2);
      return new StringBuilder(valueReversed[1]).reverse().toString();
  } 

}