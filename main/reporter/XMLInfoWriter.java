package reporter;

public class XMLInfoWriter extends InfoWriter {
  
	@Override
	public String formatEnding() {
		return "_INFO.xml";
	}
	
	@Override
	public String formatPrefix(int index) {
		return "<"+attributes[index]+"> ";
	}
	
	@Override
	public String formatPostfix(int index) {
		return " </"+attributes[index]+">";
	}
	
}