package overwatchcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import overwatchcode.workshop.KeywordContainer;
import overwatchcode.workshop.SystemVariable;
import overwatchcode.workshop.VarNameRule;
import overwatchcode.workshop.VariableScope;
import overwatchcode.workshop.WorkshopFunction;
import overwatchcode.workshop.block.RuleEvent;

public class Manager {
	
	public static final Manager INSTANCE = new Manager();
	
	private String selectedLanguage;
	
	private Map<String, RuleEvent> events;
	private Map<String, KeywordContainer> keywordContainers;
	private Map<String, WorkshopFunction> functions;
	private Map<String, SystemVariable> systemVariables;
	
	private List<String> errors;
	private List<String> warnings;
	
	private List<VarNameRule> varNameRules;
	private HashMap<String, String> variables;

	private char fillTopDownCounter;
	

	private Manager() {
		events = new HashMap<>();
		keywordContainers = new HashMap<>();
		functions = new HashMap<>();
		systemVariables = new HashMap<>();
		varNameRules = new ArrayList<>();
		
		errors = new ArrayList<>();
		warnings = new ArrayList<>();
		
		variables = new HashMap<>();
		
		fillTopDownCounter = 'A';
		
		selectedLanguage = "EN";
		
		try {
			loadLibrary(new File("lib.json"));
		} catch (FileNotFoundException e) {
		}
	}

	public String getSelectedLanguage() {
		return selectedLanguage;
	}
	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}
	
	public Collection<RuleEvent> getEvents() {
		return events.values();
	}
	public KeywordContainer getKeywordContainer(String language) {
		return keywordContainers.get(language);
	}
	public KeywordContainer getKeywordContainer() {
		return getKeywordContainer(selectedLanguage);
	}
	
	public WorkshopFunction getFunction(String name) {
		return functions.get(name);
	}
	public String getFunctionName(String name, int size) {
		for(WorkshopFunction function: functions.values()) {
			String n = function.isCaseSensitive() ? name : name.toLowerCase();
			
			for(String text: function.getText()) {
				String t = function.isCaseSensitive() ? text : text.toLowerCase();
				
				if(t.equals(n)) {
					if(function.getArguments() != size) {
						errors.add("Not enough/Too Many Arguments in Function '" + name + "' (" + size + " != " + function.getArguments() + ")");
					}
					
					return function.getName();
				}
			}
		}
		
		errors.add("Unable to find Function '" + name + "'");		
		
		return name;
	}

	public boolean isSystemVariable(String value) {
		return systemVariables.containsKey(value);
	}
	
	public char getFillTopDownCounter() {
		char start = fillTopDownCounter;
		
		while(variables.values().contains(String.valueOf(fillTopDownCounter))) {
			incFillTopDownCounter();
			
			if(start == fillTopDownCounter) {
				break;
			}
		}
		
		return fillTopDownCounter;
	}
	public void incFillTopDownCounter() {
		fillTopDownCounter++;
		if(fillTopDownCounter > 'Z') fillTopDownCounter = 'A';
	}
	public void setVariable(String ruleName, RuleEvent event, String varNameIn, String varNameOut) {		
		variables.put(varNameIn, varNameOut.toUpperCase());
	}
	public String getVariable(String variableName) {
		return variables.containsKey(variableName) ? variables.get(variableName) : null;
	}
	public void resetVariables() {
		variables.clear();
		fillTopDownCounter = 'A';
	}
	
	public String getVarName(String name, boolean global, String ruleName, RuleEvent event, List<WorkshopFunction> functions) {
		for(SystemVariable sVariable: this.systemVariables.values()) {
			String n = sVariable.isCaseSensitive() ? name : name.toLowerCase();
			
			for(String text: sVariable.getText()) {
				String t = sVariable.isCaseSensitive() ? text: text.toLowerCase();
				
				if(n.equals(t)) {
					boolean eventOk = sVariable.getEvents().size() == 0;
					boolean functionsOk = sVariable.getFunctions().size() == 0;
					for(RuleEvent e: sVariable.getEvents()) {
						if(e == event) {
							eventOk = true;
							break;
						}
					}
					for(WorkshopFunction f: sVariable.getFunctions()) {
						if(functions.contains(f)) {
							functionsOk = true;
							break;
						}
					}
					if(!eventOk || !functionsOk) errors.add("SystemVariable '" + sVariable + "' is used outside of allowed events or functions");
					
					return sVariable.getName();
				}
			}
		}
		for(VarNameRule rule: varNameRules) {
			String nname = rule.applyRule(name, global, ruleName, event);

			if(nname != null) {
				return nname;
			}
		}
		if(name.length() == 1) setVariable(ruleName, event, name, name);
		
		return name.toUpperCase();
	}

	public String getReport() {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Code creation finnished with ");
		sB.append(errors.size());
		sB.append(" Errors and ");
		sB.append(warnings.size());
		sB.append(" Warnings:\n\n");
		
		for(String error: errors) {
			sB.append("ERROR: ");
			sB.append(error);
			sB.append('\n');
		}
		for(String error: errors) {
			sB.append("WARNING: ");
			sB.append(error);
			sB.append('\n');
		}
		
		
		errors.clear();
		warnings.clear();
		
		return sB.toString();
	}
	
	
	public void loadLibrary(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		String t = "";
		while(scanner.hasNextLine()) t += scanner.nextLine() + "\n";		
		scanner.close();

		JSONObject lib = new JSONObject(t);
		JSONObject keywords = lib.getJSONObject("keywords");
		JSONObject events = lib.getJSONObject("events");
		JSONObject systemVariables = lib.getJSONObject("systemVariables");
		JSONObject functions = lib.getJSONObject("functions");
		loadVarNameRules(lib.getJSONArray("varNameRules"));
		
		for(String keyWord: keywords.keySet()) {
			JSONObject obj = keywords.getJSONObject(keyWord);
			
			String rule = obj.has("rule") ? obj.getString("rule") : "rule";
			String event = obj.has("event") ? obj.getString("event") : "event";
			String conditions = obj.has("conditions") ? obj.getString("conditions") : "conditions";
			String actions = obj.has("actions") ? obj.getString("actions") : "actions";
			
			keywordContainers.put(keyWord, new KeywordContainer(rule, event, conditions, actions));
		}
		for(String event: events.keySet()) {
			JSONObject obj = events.getJSONObject(event);
			String[] text = new String[0];
			if(obj.has("text")) {
				JSONArray textArray = obj.getJSONArray("text");
				text = new String[textArray.length()];
				for(int i = 0; i < text.length; i++) text[i] = textArray.getString(i);
			}
			boolean global = obj.has("global") ? obj.getBoolean("global") : false;
			boolean caseSensitive = obj.has("caseSensitive") ? obj.getBoolean("caseSensitive") : true;
			this.events.put(event, new RuleEvent(event, text, global, caseSensitive));
		}
		for(String function: functions.keySet()) {
			JSONObject obj = functions.getJSONObject(function);
			int arguments = obj.has("arguments") ? obj.getInt("arguments") : 0;
			boolean caseSensitive = obj.has("caseSensitive") ? obj.getBoolean("caseSensitive") : true;
			boolean action = obj.has("action") ? obj.getBoolean("action") : false;
			String[] text = new String[0];
			if(obj.has("text")) {
				JSONArray textArray = obj.getJSONArray("text");
				text = new String[textArray.length()];
				for(int i = 0; i < text.length; i++) text[i] = textArray.getString(i);
			}
			
			this.functions.put(function, new WorkshopFunction(function, arguments, caseSensitive, text, action));
		}
		for(String systemVariable: systemVariables.keySet()) {
			JSONObject obj = systemVariables.getJSONObject(systemVariable);
			
			List<RuleEvent> eventsList = new ArrayList<>();
			if(obj.has("ruleEvents")) {
				JSONArray eventsJSON = obj.getJSONArray("ruleEvents");
				for(int i = 0; i < eventsJSON.length(); i++) {
					eventsList.add(this.events.get(eventsJSON.getString(i)));
				}
			}
			List<WorkshopFunction> functionsList = new ArrayList<>();
			if(obj.has("functions")) {
				JSONArray functionsJSON = obj.getJSONArray("functions");
				for(int i = 0; i < functionsJSON.length(); i++) {
					functionsList.add(this.functions.get(functionsJSON.getString(i)));
				}
			}
			String[] text = new String[0];
			boolean caseSensitive = obj.has("caseSensitive") ? obj.getBoolean("caseSensitive") : true;
			if(obj.has("text")) {
				JSONArray textArray = obj.getJSONArray("text");
				text = new String[textArray.length()];
				for(int i = 0; i < text.length; i++) text[i] = textArray.getString(i);
			}
			this.systemVariables.put(systemVariable, new SystemVariable(systemVariable, eventsList, functionsList, text, caseSensitive));

		}
	}
	public void loadVarNameRules(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		String text = "";
		while(scanner.hasNextLine()) text += scanner.nextLine() + "\n";		
		scanner.close();

		JSONArray rules = new JSONArray(text);
		
		loadVarNameRules(rules);
	}
	public void loadVarNameRules(JSONArray rules) {
		varNameRules.clear();
		
		for(int i = 0; i < rules.length(); i++) {
			JSONObject obj = rules.getJSONObject(i);
			
			VarNameRule rule = new VarNameRule();
			
			if(obj.has("scope")) rule.setScope(VariableScope.parse(obj.getString("scope")));
			if(obj.has("ruleEvent")) rule.setRuleEvent(obj.getString("ruleEvent"));
			if(obj.has("ruleName")) rule.setRuleName(obj.getString("ruleName"));
			if(obj.has("caseSensitive")) rule.setCaseSensitive(obj.getBoolean("caseSensitive"));
			if(obj.has("nameConnections")) {
				JSONObject nameConnections = obj.getJSONObject("nameConnections");
				
				for(String var: nameConnections.keySet()) {
					JSONArray varsJSON = nameConnections.getJSONArray(var);
					String[] vars = new String[varsJSON.length()];
					
					for(int j = 0; j < vars.length; j++) {
						vars[j] = varsJSON.getString(j);
					}
					
					rule.getNameConnections().put(var, vars);
				}
			}
			if(obj.has("fillTopDown")) rule.setFillTopDown(obj.getBoolean("fillTopDown"));
			if(obj.has("fillTopDownStart")) rule.setFillTopDownStart(obj.getString("fillTopDownStart").charAt(0));
			if(obj.has("fillTopDownEnd")) rule.setFillTopDownEnd(obj.getString("fillTopDownEnd").charAt(0));
			if(obj.has("ignoreLetters")) rule.setIgnoreLetters(obj.getBoolean("ignoreLetters"));

			
			varNameRules.add(rule);
		}
	}
}