package reporter;

public class TXTLogWriter extends LogWriter {

  	@Override
	public String formatEnding() {
		return "_LOG.txt";
	}
	
	@Override
	public String formatPrefix(int index) {
		return attributes[index]+": ";
	}
	
	@Override
	public String formatPostfix(int index) {
		return "";
	}

}
