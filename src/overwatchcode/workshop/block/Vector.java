package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class Vector extends Block {
	
	private Block x;
	private Block y;
	private Block z;
	
		
	public Block getX() {
		return x;
	}
	public void setX(Block x) {
		this.x = x;
	}
	public Block getY() {
		return y;
	}
	public void setY(Block y) {
		this.y = y;
	}
	public Block getZ() {
		return z;
	}
	public void setZ(Block z) {
		this.z = z;
	}

	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		x.updateNames(event, ruleName, functions);
		y.updateNames(event, ruleName, functions);
		z.updateNames(event, ruleName, functions);
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Vector");
		sB.append('(');
		sB.append(x.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(y.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(z.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
