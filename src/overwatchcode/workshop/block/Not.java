package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class Not extends Condition {
	
	private Block a;
	

	public Block getA() {
		return a;
	}
	public void setA(Block a) {
		this.a = a;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		a.updateNames(event, ruleName, functions);
	}
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Not");
		sB.append('(');
		sB.append(a.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
