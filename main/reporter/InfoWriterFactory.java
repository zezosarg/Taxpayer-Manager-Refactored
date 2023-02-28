package reporter;

public class InfoWriterFactory {
	
	public InfoWriter createInfoWriter(String format) {
		
		if (format.equals("txt")) {
		      return new TXTInfoWriter();
		} else {
		      return new XMLInfoWriter();
		}
		
	}
	
}

/* 
 * The problem with moving all the conditional logic inside the factory would have been that 
 * if INFO files of both formats existed, only one of them would have been updated.
 */