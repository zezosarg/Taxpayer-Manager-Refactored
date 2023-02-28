package reporter;

public class TXTInfoWriter extends InfoWriter {

	@Override
	public String formatEnding() {
		return "_INFO.txt";
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