package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class OrCondition extends Condition {
	
	private Block a;
	private Block b;
	

	public OrCondition() {
		a = Terminal.TRUE;
		b = Terminal.TRUE;
	}

	public Block getA() {
		return a;
	}
	public void setA(Block a) {
		this.a = a;
	}
	public Block getB() {
		return b;
	}
	public void setB(Block b) {
		this.b = b;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		a.updateNames(event, ruleName, functions);
		b.updateNames(event, ruleName, functions);
	}
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Or");
		sB.append('(');
		sB.append(a.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(b.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
