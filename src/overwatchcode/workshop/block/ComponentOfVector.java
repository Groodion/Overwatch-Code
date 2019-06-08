package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class ComponentOfVector extends Block {
	
	private Block vector;
	private VectorComponent vComponent;
	
	
	public Block getVector() {
		return vector;
	}
	public void setVector(Block vector) {
		this.vector = vector;
	}
	public VectorComponent getComponent() {
		return vComponent;
	}
	public void setComponent(VectorComponent vComponent) {
		this.vComponent = vComponent;
	}
	public void setComponent(String string) {
		this.vComponent = VectorComponent.parse(string);
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		vector.updateNames(event, ruleName, functions);
	}
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append(vComponent.name());
		sB.append(" Component Of");
		sB.append('(');
		sB.append(vector.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
