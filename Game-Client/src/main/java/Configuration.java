
public class Configuration {

	public static final String NAME = "Allstarlegends.eu || Oldschool Nostalgia!";
	public static final String CLIENT_NAME = "Allstarlegends";
	public static final boolean SHADOW_LEVELS = true;
	public static boolean enableTooltipHovers = true;
	public static boolean enableSpecOrb;
	public static boolean enableOrbs = true;
	public static boolean expCounterOpen;
	public static boolean chatTimeStamps = true;
	public static boolean displayFps;
	public static boolean clientData;
	public static boolean hpAboveHeads;
	public static boolean mergeExpDrops;
	public static boolean enableMusic = false;
	public static boolean groundMarkers = true;
	public static int npcAttackOptionPriority = 2;
	public static int playerAttackOptionPriority = 0;
    public static boolean combatOverlayBox = true;
	public static boolean enableRoofs = false;
	public static boolean escapeCloseInterface;
	public static boolean enableShiftClickDrop;
	public static boolean enableFog;
	public static boolean enableGroundItemNames;
	public static boolean enableSkillOrbs;
	public static boolean enableBuffOverlay;

	enum ClientState {
		Developer,
		Live
	}
	
	/**
	 * Sets whether the Client is in developer mode or live mode
	 */
	public static ClientState clientState = ClientState.Live;
	
	/**
	 * Gets the current Client State
	 */
	
	public static ClientState getClientState() {
		return clientState;
	}

	public static final int OBJECT_CULLING = 4000;

	public enum Skin {
		STANDARD, AROUND_2005, AROUND_2006, AROUND_2010
	}

	/**
	 * UI Look and Feel
	 */
	public static Skin skin = Skin.STANDARD;

	public static void setSkin(String skinName) {
		switch (skinName) {
			case "AROUND_2005":
				skin = Skin.AROUND_2005;
				break;
			case "AROUND_2006":
				skin = Skin.AROUND_2006;
				break;
			case "AROUND_2010":
				skin = Skin.AROUND_2010;
				break;
			default:
				skin = Skin.STANDARD;
		}

	}

}
