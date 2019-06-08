package overwatchcode.workshop.block;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class AndCondition extends Condition {
	
	private Block a;
	private Block b;
	
	
	public AndCondition() {
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
	public List<Block> unwrap() {
		List<Block> conditions = new ArrayList<>();
		
		if(a instanceof AndCondition) conditions.addAll(((AndCondition) a).unwrap());
		else						  conditions.add(a);
		if(b instanceof AndCondition) conditions.addAll(((AndCondition) b).unwrap());
		else						  conditions.add(b);
		
		return conditions;
	}
	
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		a.updateNames(event, ruleName, functions);
		b.updateNames(event, ruleName, functions);
	}
	
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("And");
		sB.append('(');
		if(!min) sB.append(' ');
		sB.append(a.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(b.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
