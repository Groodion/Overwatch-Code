package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.WorkshopFunction;

public class Terminal extends Condition {

	public static final Terminal TRUE = new Terminal("True");
	public static final Terminal FALSE = new Terminal("False");
	
	
	private String value;
	
	
	public Terminal() {
	}
	public Terminal(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		
	}
	@Override
	public String toOVWCode(boolean min) {
		return value;
	}
}
