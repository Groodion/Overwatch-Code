package overwatchcode.workshop.block.container;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.Function;
import overwatchcode.workshop.block.Rule;
import overwatchcode.workshop.block.Terminal;

public class While extends ConditionedContainer {

	private int breakPosition;
	private int continuePosition;
	

	protected void setBreakPosition(int breakPosition) {
		this.breakPosition = breakPosition;
	}
	protected void setContinuePosition(int continuePosition) {
		this.continuePosition = continuePosition;
	}

	@Override
	protected List<Block> buildBlocks() {
		If ifBlock = new If();
		ifBlock.setCondition(getCondition());
		ifBlock.getActions().addAll(getActions());
		
		Block waitBlock = new Terminal("Wait(0.001, Ignore Condition)");
		Block loopBlock = new Terminal("Loop");
		
		ifBlock.getActions().add(waitBlock);
		ifBlock.getActions().add(loopBlock);
				
		List<Block> blocks = ifBlock.buildBlocks();

		setBreakPosition(blocks.size() - 1);
		setContinuePosition(blocks.size() - 3);

		return blocks;
	}
	
	@Override
	public List<Block> resolveContainers(Rule rule) {
		List<Block> blocks = super.resolveContainers(rule);
		
		// resolve break/continue blocks
		for(int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			
			if(block instanceof Break) {
				Function breakBlock = new Function("Skip", new Terminal(String.valueOf(breakPosition - i)));
				
				blocks.set(i, breakBlock);
			} else if(block instanceof Continue) {
				Function continueBlock = new Function("Skip", new Terminal(String.valueOf(continuePosition - i)));
				
				blocks.set(i, continueBlock);
			}
		}
		
		return blocks;
	}
}