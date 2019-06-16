package overwatchcode.workshop.block.container;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.Rule;


public class For extends While {
	
	private List<Block> initBlocks;
	private List<Block> incBlocks;
	
	
	public For() {
		initBlocks = new ArrayList<>();
		incBlocks = new ArrayList<>();
	}
	
	public List<Block> getInitBlocks() {
		return initBlocks;
	}
	public List<Block> getIncBlocks() {
		return incBlocks;
	}
	
	@Override
	protected List<Block> buildBlocks() {
		getActions().addAll(incBlocks);
		
		List<Block> blocks = super.buildBlocks();
		
		setContinuePosition(blocks.size() - incBlocks.size() - 3);
		
		return blocks;
	}
	@Override
	public List<Block> resolveContainers(Rule rule) {
		Rule initRule = rule.cloneConditions("_for_init");
		
		initRule.getActions().addAll(initBlocks);
		
		rule.getPreRules().add(initRule);
		
		return super.resolveContainers(rule);
	}
}