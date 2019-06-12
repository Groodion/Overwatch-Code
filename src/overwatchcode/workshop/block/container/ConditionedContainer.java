package overwatchcode.workshop.block.container;

import java.util.List;

import overwatchcode.workshop.Block;

public abstract class ConditionedContainer extends Container {

	private Block condition;

	
	public Block getCondition() {
		return condition;
	}
	public void setCondition(Block condition) {
		this.condition = condition;
	}
	
	@Override
	protected List<Block> buildBlocks() {
		return null;
	}
}