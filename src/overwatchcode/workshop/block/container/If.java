package overwatchcode.workshop.block.container;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.Function;
import overwatchcode.workshop.block.Not;
import overwatchcode.workshop.block.Terminal;

public class If extends Container {
	
	private class Else extends Container {
		@Override
		protected List<Block> buildBlocks() {
			return null;
		}
	};
	
	private Block condition;
	private Else elseBlock;	
	
	
	public If() {
		elseBlock = new Else();
	}
	
	public Block getCondition() {
		return condition;
	}
	public void setCondition(Block condition) {
		this.condition = condition;
	}
	public Else getElse() {
		return elseBlock;
	}


	@Override
	public List<Block> resolveContainers() {
		elseBlock.resolveContainers();
		
		return super.resolveContainers();
	}
	@Override
	public List<Block> buildBlocks() {
		List<Block> blocks = new ArrayList<Block>();
		
		int ifSize = getActions().size();
		int elseSize = elseBlock.getActions().size();
		
		if(elseSize == 0) {
			Function ifCondition = new Function("Skip If", new Not(condition), new Terminal(String.valueOf(ifSize)));
			blocks.add(ifCondition);
			blocks.addAll(getActions());			
		} else {
			Function ifCondition = new Function("Skip If", condition, new Terminal("1"));
			Function skipToElse = new Function("Skip", new Terminal(String.valueOf(ifSize + 1)));
			Function skipElse = new Function("Skip", new Terminal(String.valueOf(elseSize)));
			
			blocks.add(ifCondition);
			blocks.add(skipToElse);
			blocks.addAll(getActions());
			blocks.add(skipElse);
			blocks.addAll(elseBlock.getActions());
		}
		
		return blocks;
	}
}