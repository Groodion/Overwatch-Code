package overwatchcode.workshop.block.container;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;
import overwatchcode.workshop.block.Rule;
import overwatchcode.workshop.block.RuleEvent;

public abstract class Container extends Block {
	
	private List<Block> actions;
	
	
	public Container() {
		actions = new ArrayList<>();
	}
	
	public List<Block> getActions() {
		return actions;
	}

	public List<Block> resolveContainers(Rule rule) {
		List<Block> blocks = new ArrayList<>();
		
		// remove container blocks
		for(Block block: actions) {
			if(block instanceof Container) {
				blocks.addAll(((Container) block).resolveContainers(rule));
			} else {
				blocks.add(block);
			}
		}
		
		actions.clear();
		actions.addAll(blocks);
			
		return buildBlocks();
	}
	protected abstract List<Block> buildBlocks();
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		throw new RuntimeException();
	}
	@Override
	public String toOVWCode(boolean min) {
		throw new RuntimeException();
	}
}