package overwatchcode.workshop.block.container;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.Terminal;

public class While extends ConditionedContainer {

	@Override
	protected List<Block> buildBlocks() {
		If ifBlock = new If();
		ifBlock.setCondition(getCondition());
		ifBlock.getActions().addAll(getActions());
		
		Block waitBlock = new Terminal("Wait(0.001, Ignore Condition)");
		Block loopBlock = new Terminal("Loop");
		
		ifBlock.getActions().add(waitBlock);
		ifBlock.getActions().add(loopBlock);
		
		return ifBlock.buildBlocks();
	}

}
