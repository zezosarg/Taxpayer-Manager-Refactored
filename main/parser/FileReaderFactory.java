package parser;

import datamanagement.WrongFileEndingException;

public class FileReaderFactory {

	public FileReader createFileReader(String fileName) throws WrongFileEndingException {
		
		String ending[] = fileName.split("\\.");
	    
	    if (ending[1].equals("txt")) {
	    	return new TXTFileReader();
	    	
	    } else if (ending[1].equals("xml")) {
	    	return new XMLFileReader();
	    	
	    } else {
	      throw new WrongFileEndingException();
	    }
	}
	
}