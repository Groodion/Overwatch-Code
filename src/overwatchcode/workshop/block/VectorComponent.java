package overwatchcode.workshop.block;

public enum VectorComponent {
	X("->X"),
	Y("->Y"),
	Z("->Z");
	
	private String text;
	
	private VectorComponent(String text) {
		this.text = text;
	}
	
	public static VectorComponent parse(String string) {
		for(VectorComponent vComponent: values()) {
			if(vComponent.text.equals(string)) {
				return vComponent;
			}
		}
		
		return null;
	}
}
