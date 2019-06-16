package overwatchcode.workshop.block.container;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.Terminal;

public abstract class ConditionedContainer extends Container {

	private Block condition;

	
	public ConditionedContainer() {
		condition = Terminal.TRUE;
	}
	
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