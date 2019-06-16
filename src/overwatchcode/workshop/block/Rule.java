package overwatchcode.workshop.block;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.Manager;
import overwatchcode.workshop.Block;
import overwatchcode.workshop.KeywordContainer;
import overwatchcode.workshop.WorkshopFunction;
import overwatchcode.workshop.block.container.Container;

public class Rule extends Container {

	private RuleEvent event;
	private TeamScope teamScope;
	private PlayerScope playerScope;
	private String name;
	private List<RuleCondition> conditions;
	
	private List<Rule> preRules;
	
	
	public Rule() {
		conditions = new ArrayList<>();
		
		preRules = new ArrayList<>();
		
		teamScope = TeamScope.All;
		playerScope = PlayerScope.ALL;
	}
	
	public RuleEvent getEvent() {
		return event;
	}
	public void setEvent(RuleEvent event) {
		this.event = event;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TeamScope getTeamScope() {
		return teamScope;
	}
	public void setTeamScope(TeamScope teamScope) {
		this.teamScope = teamScope;
	}
	public PlayerScope getPlayerScope() {
		return playerScope;
	}
	public void setPlayerScope(PlayerScope playerScope) {
		this.playerScope = playerScope;
	}
	
	public List<RuleCondition> getConditions() {
		return conditions;
	}
	
	public List<Rule> getPreRules() {
		return preRules;
	}
	
	public void resolveContainers() {
		resolveContainers(this);
	}
	@Override
	protected List<Block> buildBlocks() {
		return getActions();
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		List<WorkshopFunction> f = new ArrayList<>();
		
		for(RuleCondition condition: conditions) {
			condition.updateNames(this.event, name, f);
		}
		for(Block action: getActions()) {
			action.updateNames(this.event, name, f);
		}
	}	
	
	@Override
	public String toOVWCode(boolean min) {
		KeywordContainer kwc = Manager.INSTANCE.getKeywordContainer();
		StringBuilder sB = new StringBuilder();
		
		for(Rule preRule: preRules) {
			sB.append(preRule.toOVWCode(min));
			if(!min) sB.append('\n');			
		}
		
		sB.append(kwc.getRule());
		sB.append("(\"");
		sB.append(name);
		sB.append("\")");
		if(!min) sB.append('\n');
		sB.append('{');
		if(!min) sB.append("\n\t");
		sB.append(kwc.getEvent());
		if(!min) sB.append("\n\t");
		sB.append('{');
		if(!min) sB.append("\n\t\t");
		sB.append(event.getName());
		sB.append(';');
		if(!event.isGlobal()) {
			if(!min) sB.append("\n\t\t");
			sB.append(teamScope.getText());
			sB.append(';');
			if(!min) sB.append("\n\t\t");
			sB.append(playerScope.getText());
			sB.append(';');
		}
		if(!min) sB.append("\n\t");
		sB.append('}');
		if(!min) sB.append("\n\t");
		sB.append(kwc.getConditions());
		if(!min) sB.append("\n\t");
		sB.append('{');
		for(RuleCondition condition: conditions) {
			if(!min) sB.append("\n\t\t");
			sB.append(condition.toOVWCode(min));
			sB.append(';');
		}
		if(!min) sB.append("\n\t");
		sB.append('}');
		if(!min) sB.append("\n\t");
		sB.append(kwc.getActions());
		if(!min) sB.append("\n\t");
		sB.append('{');
		for(Block action: getActions()) {
			if(!min) sB.append("\n\t\t");
			sB.append(action.toOVWCode(min));
			sB.append(';');
		}
		if(!min) sB.append("\n\t");
		sB.append('}');
		if(!min) sB.append("\n");
		sB.append('}');
		
		return sB.toString();
	}

	public Rule cloneConditions(String nameExtension) {
		Rule clone = new Rule();
		
		clone.setName(getName() + nameExtension);
		clone.setEvent(getEvent());
		clone.setPlayerScope(getPlayerScope());
		clone.setTeamScope(getTeamScope());
		clone.getConditions().addAll(getConditions());
		
		return clone;
	}
}