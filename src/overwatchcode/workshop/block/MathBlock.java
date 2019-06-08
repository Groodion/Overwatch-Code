package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public abstract class MathBlock extends Block {
	
	private String name;
	
	private Block a;
	private Block b;
	
	
	public MathBlock(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
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
	
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		a.updateNames(event, ruleName, functions);
		b.updateNames(event, ruleName, functions);
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
	
		sB.append(name);
		sB.append('(');
		sB.append(a.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(b.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
