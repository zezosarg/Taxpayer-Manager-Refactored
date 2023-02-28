package reporter;

public class XMLLogWriter extends LogWriter {

	@Override
	public String formatEnding() {
		return "_LOG.xml";
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
