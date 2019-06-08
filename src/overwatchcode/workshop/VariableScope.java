package overwatchcode.workshop;

public enum VariableScope {
	GLOBAL,
	PLAYER;
	
	public static VariableScope parse(String value) {
		value = value.toUpperCase();
		
		return valueOf(value);
	}
}