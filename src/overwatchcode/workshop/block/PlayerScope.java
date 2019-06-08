package overwatchcode.workshop.block;

public enum PlayerScope {
	ALL("All", "All", "all", "?"),
	SLOT_0("Slot 0", "Slot0"),
	SLOT_1("Slot 1", "Slot1"),
	SLOT_2("Slot 2", "Slot2"),
	SLOT_3("Slot 3", "Slot3"),
	SLOT_4("Slot 4", "Slot4"),
	SLOT_5("Slot 5", "Slot5"),
	SLOT_6("Slot 6", "Slot6"),
	SLOT_7("Slot 7", "Slot7"),
	SLOT_8("Slot 8", "Slot8"),
	SLOT_9("Slot 9", "Slot9"),
	SLOT_10("Slot 10", "Slot10"),
	SLOT_11("Slot 11", "Slot11"),
	REAPER("Reaper", "Reaper"),
	TRACER("Tracer", "Tracer"),
	MERCY("Mercy", "Mercy"),
	HANZO("Hanzo", "Hanzo"),
	TORBJORN("Torbjörn", "Torbjorn", "Torbjörn", "Torbjoern"),
	REINHARD("Reinhard", "Reinhard"),
	PHARA("Phara", "Phara"),
	WINSTON("Winston", "Winston"),
	WIDOWMAKER("Widowmaker", "Widowmaker"),
	BASTION("Bastion", "Bastion"),
	SYMMETRA("Symmetra", "Symmetra"),
	ZENYATTA("Zenyatta", "Zenyatta"),
	GENJI("Genji", "Genji"),
	ROADHOG("Roadhog", "Roadhog"),
	MCCREE("McCree", "McCree"),
	JUNKRAT("Junkrat", "Junkrat"),
	ZARYA("Zarya", "Zarya"),
	SOLDIER("Soldier: 76", "Soldier76"),
	LUCIO("Lúcio", "Lucio"),
	DVA("Lúcio", "Lucio"),
	MEI("Mei", "Mei"),
	SOMBRA("Sombra", "Sombra"),
	DOOMFIST("Doomfist", "Doomfist"),
	ANA("Ana", "Ana"),
	ORISA("Orisa", "Orisa"),
	BRIGITTE("Brigitte", "Brigitte"),
	MOIRA("Moira", "Moira"),
	WRECKING_BALL("Wrecking Ball", "WreckingBall"),
	ASHE("Ashe", "Ashe"),
	BAPTISTE("Baptiste", "Baptiste");
	
	
	private String text;
	private String[] parseStrings;
	
	PlayerScope(String text, String... parseStrings) {
		this.text = text;
		this.parseStrings = parseStrings;
	}
	
	public String getText() {
		return text;
	}
	
	public static PlayerScope parse(String string) {
		for(PlayerScope playerScope: values()) {
			for(String parseString: playerScope.parseStrings) {
				if(parseString.equals(string)) return playerScope;
			}
		}
		
		return null;
	}
}