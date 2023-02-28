package reporter;

import parser.WrongFileFormatException;

public class LogWriterFactory {

	public LogWriter createLogWriter(String fileFormat) throws WrongFileFormatException {
	    if (fileFormat.equals("txt")) {
	    	return new TXTLogWriter();
	    } else if (fileFormat.equals("xml")) {
	    	return new XMLLogWriter();
	    } else {
	        throw new WrongFileFormatException();
	    }
	}
	
}
