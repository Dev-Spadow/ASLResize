// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
import sign.Signlink;

public class Widget {

	public static final int OPTION_OK = 1;
	public static final int OPTION_USABLE = 2;
	public static final int OPTION_CLOSE = 3;
	public static final int OPTION_TOGGLE_SETTING = 4;
	public static final int OPTION_RESET_SETTING = 5;
	public static final int OPTION_CONTINUE = 6;
	public static final int OPTION_DROPDOWN = 7;

	public static final int TYPE_CONTAINER = 0;
	public static final int TYPE_MODEL_LIST = 1;
	public static final int TYPE_INVENTORY = 2;
	public static final int TYPE_RECTANGLE = 3;
	public static final int TYPE_TEXT = 4;
	public static final int TYPE_SPRITE = 5;
	public static final int TYPE_MODEL = 6;
	public static final int TYPE_ITEM_LIST = 7;
	public static final int TYPE_OTHER = 8;
	public static final int TYPE_HOVER = 9;
	public static final int TYPE_CONFIG = 10;
	public static final int TYPE_CONFIG_HOVER = 11;
	public static final int TYPE_SLIDER = 12;
	public static final int TYPE_DROPDOWN = 13;
	public static final int TYPE_ROTATING = 14;
	public static final int TYPE_KEYBINDS_DROPDOWN = 15;
	public static final int TYPE_TICKER = 16;
	public static final int TYPE_ADJUSTABLE_CONFIG = 17;
	public static final int TYPE_BOX = 18;
	public static final int TYPE_MAP = 19;

	public static FileArchive aClass44;
	public boolean drawsTransparent;
	public Sprite disabledSprite;
	public int anInt208;
	public int interfaceType;
	public boolean interfaceShown;
	public boolean textShadowed;
	public int disabledColor;
	public Sprite sprites[];
	public Sprite enabledSprite;
	public static Widget interfaceCache[];
	public int requiredValues[];
	public int hoverType;
	public boolean textCentered;
	public int contentType;//anInt214
	public int spritesX[];
	public int defaultHoverCover;
	public int atActionType;
	public String spellName;
	public int secondaryColor;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public int enabledHoverColor;
	public String enabledMessage;
	public String disabledMessage;
	public String actions[];
	public int valueIndexArray[][];
	public boolean filled;
	public String secondaryText;
	public int disabledHoverColor;
	public int spritePaddingX;
	public int enabledColor;
	public int textColor;
	public int defaultMediaType;
	public int mediaID;
	public boolean replaceItems;
	public int parent;
	public int spellUsableOn;
	private static ReferenceCache spriteCache;
	public int secondaryHoverColor;
	public int children[];
	public int childX[];
	public boolean usableItems;
	public static GameFont textDrawingAreas;
	public static GameFont[] fonts;
	public static RSFont[] newFonts;
	public int spritePaddingY;
	public int valueCompareType[];
	public int currentFrame;
	public int spritesY[];
	public String defaultText;
	public boolean hasActions;
	public int id;
	public int inventoryAmounts[];
	public int inventoryItemId[];
	public byte opacity;
	public boolean rightAlignedText;
	public boolean rollingText;
	public boolean regularHoverBox;
	public boolean toggled = false;
	public RSFont rsFont;
	public int spriteOpacity;
	public int msgX;
	public int msgY;
	public int defaultHoverColor;
	public boolean active;
	public Slider slider;
	public DropdownMenu dropdown;
	public int[] dropdownColours;
	public boolean hovered = false;
	public Widget dropdownOpen;
	public int dropdownHover = -1;
	public boolean invisible;
	public boolean hidden;
	public boolean drawInfinity;
	private int anInt255;
	private int anInt256;
	public int defaultAnimationId;
	public int secondaryAnimationId;
	public boolean allowSwapItems;
	public int scrollMax;
	public int type;
	public int horizontalOffset;
	private static final ReferenceCache A_REFERENCE_CACHE___264 = new ReferenceCache(30);
	public int verticalOffset;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];

	public int hoverXOffset = 0;
	public int hoverYOffset = 0;
	public int spriteXOffset = 0;
	public int spriteYOffset = 0;

	public Sprite enabledAltSprite;
	public Sprite disabledAltSprite;
	public int[] buttonsToDisable;

	private static final int SPRITE_CACHE_SIZE = 50_000;
	private static final int WIDGET_CACHE_SIZE = 150_000;
	boolean inverted;

	public void swapInventoryItems(int i, int j) {
		int k = inventoryItemId[i];
		inventoryItemId[i] = inventoryItemId[j];
		inventoryItemId[j] = k;
		k = inventoryAmounts[i];
		inventoryAmounts[i] = inventoryAmounts[j];
		inventoryAmounts[j] = k;
	}

	public static void unpack(FileArchive interfaceArchive, GameFont fonts[], FileArchive graphics, RSFont newFonts[]) {
		spriteCache = new ReferenceCache(SPRITE_CACHE_SIZE);
		Buffer buffer = new Buffer(interfaceArchive.readFile("data"));
		Widget.newFonts = newFonts;
		Widget.fonts = fonts;
		int i = -1;
		int j = buffer.readUShort();
		interfaceCache = new Widget[WIDGET_CACHE_SIZE];
		while(buffer.currentPosition < buffer.payload.length) {
			int k = buffer.readUShort();
			if(k == 65535) {
				i = buffer.readUShort();
				k = buffer.readUShort();
			}
			Widget widget = interfaceCache[k] = new Widget();
			widget.id = k;
			widget.parent = i;
			widget.type = buffer.readUnsignedByte();
			widget.atActionType = buffer.readUnsignedByte();
			widget.contentType = buffer.readUShort();
			widget.width = buffer.readUShort();
			widget.height = buffer.readUShort();
			widget.opacity = (byte) buffer.readUnsignedByte();
			widget.hoverType = buffer.readUnsignedByte();
			if(widget.hoverType != 0)
				widget.hoverType = (widget.hoverType - 1 << 8) + buffer.readUnsignedByte();
			else
				widget.hoverType = -1;
			int i1 = buffer.readUnsignedByte();
			if(i1 > 0) {
				widget.valueCompareType = new int[i1];
				widget.requiredValues = new int[i1];
				for(int j1 = 0; j1 < i1; j1++) {
					widget.valueCompareType[j1] = buffer.readUnsignedByte();
					widget.requiredValues[j1] = buffer.readUShort();
				}

			}
			int k1 = buffer.readUnsignedByte();
			if(k1 > 0) {
				widget.valueIndexArray = new int[k1][];
				for(int l1 = 0; l1 < k1; l1++) {
					int i3 = buffer.readUShort();
					widget.valueIndexArray[l1] = new int[i3];
					for(int l4 = 0; l4 < i3; l4++)
						widget.valueIndexArray[l1][l4] = buffer.readUShort();

				}

			}
			if(widget.type == 0) {
				widget.drawsTransparent = false;
				widget.scrollMax = buffer.readUShort();
				widget.invisible = buffer.readUnsignedByte() == 1;
				int i2 = buffer.readUShort();
				widget.children = new int[i2];
				widget.childX = new int[i2];
				widget.childY = new int[i2];
				for(int j3 = 0; j3 < i2; j3++) {
					widget.children[j3] = buffer.readUShort();
					widget.childX[j3] = buffer.readSignedWord();
					widget.childY[j3] = buffer.readSignedWord();
				}
			}
			if(widget.type == 1) {
				buffer.readUShort();
				buffer.readUnsignedByte();
			}
			if(widget.type == 2) {
				widget.inventoryItemId = new int[widget.width * widget.height];
				widget.inventoryAmounts = new int[widget.width * widget.height];
				widget.allowSwapItems = buffer.readUnsignedByte() == 1;
				widget.hasActions = buffer.readUnsignedByte() == 1;
				widget.usableItems = buffer.readUnsignedByte() == 1;
				widget.replaceItems = buffer.readUnsignedByte() == 1;
				widget.spritePaddingX = buffer.readUnsignedByte();
				widget.spritePaddingY = buffer.readUnsignedByte();
				widget.spritesX = new int[20];
				widget.spritesY = new int[20];
				widget.sprites = new Sprite[20];
				for(int j2 = 0; j2 < 20; j2++) {
					int k3 = buffer.readUnsignedByte();
					if(k3 == 1) {
						widget.spritesX[j2] = buffer.readSignedWord();
						widget.spritesY[j2] = buffer.readSignedWord();
						String s1 = buffer.readString();
						if(graphics != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							widget.sprites[j2] = getSprite(Integer.parseInt(s1.substring(i5 + 1)), graphics, s1.substring(0, i5));
						}
					}
				}
				widget.actions = new String[5];
				for(int l3 = 0; l3 < 5; l3++) {
					widget.actions[l3] = buffer.readString();
					if(widget.actions[l3].length() == 0)
						widget.actions[l3] = null;
				}
				if(widget.parent == 3824) {
                    widget.actions[4] = "Buy 100";
				}
			}
			if(widget.type == 3)
				widget.filled = buffer.readUnsignedByte() == 1;
			if(widget.type == 4 || widget.type == 1) {
				widget.centerText = buffer.readUnsignedByte() == 1;
				int k2 = buffer.readUnsignedByte();
				if(fonts != null)
					widget.textDrawingAreas = fonts[k2];
				widget.textShadow = buffer.readUnsignedByte() == 1;
			}
			if(widget.type == 4) {
				widget.defaultText = buffer.readString().replaceAll("RuneScape", "AllstarLegends");
				widget.secondaryText = buffer.readString();
			}
			if(widget.type == 1 || widget.type == 3 || widget.type == 4)
				widget.textColor = buffer.readInt();
			if(widget.type == 3 || widget.type == 4) {
				widget.secondaryColor = buffer.readInt();
				widget.defaultHoverCover = buffer.readInt();
				widget.secondaryHoverColor = buffer.readInt();
			}
			if (widget.type == TYPE_SPRITE) {
				widget.drawsTransparent = false;
				String name = buffer.readString();
				if (graphics != null && name.length() > 0) {
					int index = name.lastIndexOf(",");
					widget.disabledSprite = getSprite(Integer.parseInt(name.substring(index + 1)), graphics,
							name.substring(0, index));
				}
				name = buffer.readString();
				if (graphics != null && name.length() > 0) {
					int index = name.lastIndexOf(",");
					widget.enabledSprite = getSprite(Integer.parseInt(name.substring(index + 1)), graphics,
							name.substring(0, index));
				}
			}
			if(widget.type == 6) {
				int l = buffer.readUnsignedByte();
				if(l != 0) {
					widget.defaultMediaType = 1;
					widget.mediaID = (l - 1 << 8) + buffer.readUnsignedByte();
				}
				l = buffer.readUnsignedByte();
				if(l != 0) {
					widget.anInt255 = 1;
					widget.anInt256 = (l - 1 << 8) + buffer.readUnsignedByte();
				}
				l = buffer.readUnsignedByte();
				if(l != 0)
					widget.defaultAnimationId = (l - 1 << 8) + buffer.readUnsignedByte();
				else
					widget.defaultAnimationId = -1;
				l = buffer.readUnsignedByte();
				if(l != 0)
					widget.secondaryAnimationId = (l - 1 << 8) + buffer.readUnsignedByte();
				else
					widget.secondaryAnimationId = -1;
				widget.modelZoom = buffer.readUShort();
				widget.modelRotation1 = buffer.readUShort();
				widget.modelRotation2 = buffer.readUShort();
			}
			if(widget.type == 7) {
				widget.inventoryItemId = new int[widget.width * widget.height];
				widget.inventoryAmounts = new int[widget.width * widget.height];
				widget.centerText = buffer.readUnsignedByte() == 1;
				int l2 = buffer.readUnsignedByte();
				if(fonts != null)
					widget.textDrawingAreas = fonts[l2];
				widget.textShadow = buffer.readUnsignedByte() == 1;
				widget.textColor = buffer.readInt();
				widget.spritePaddingX = buffer.readSignedWord();
				widget.spritePaddingY = buffer.readSignedWord();
				widget.hasActions = buffer.readUnsignedByte() == 1;
				widget.actions = new String[5];
				for(int k4 = 0; k4 < 5; k4++) {
					widget.actions[k4] = buffer.readString();
					if(widget.actions[k4].length() == 0)
						widget.actions[k4] = null;
				}

			}
			if(widget.atActionType == 2 || widget.type == 2) {
				widget.selectedActionName = buffer.readString();
				widget.spellName = buffer.readString();
				widget.spellUsableOn = buffer.readUShort();
			}

			if(widget.type == 8)
				widget.defaultText = buffer.readString();

			if(widget.atActionType == 1 || widget.atActionType == 4 || widget.atActionType == 5 || widget.atActionType == 6) {
				widget.tooltip = buffer.readString();
				if(widget.tooltip.length() == 0) {
					if(widget.atActionType == 1)
						widget.tooltip = "Ok";
					if(widget.atActionType == 4)
						widget.tooltip = "Select";
					if(widget.atActionType == 5)
						widget.tooltip = "Select";
					if(widget.atActionType == 6)
						widget.tooltip = "Continue";
				}
			}
		}
		aClass44 = interfaceArchive;

		SettingsWidget.widget();
		SettingsWidget.advancedWidget();
		printEmptyInterfaces();
		spriteCache = null;
	}

	public static void printEmptyInterfaces() {
		int firstNull = -1;
		for (int i = 0; i < WIDGET_CACHE_SIZE; i++) {
			Widget widget = interfaceCache[i];
			if (widget == null) {
				if (firstNull == -1) {
					firstNull = i;
				}
				continue;
			}

			if (firstNull != -1 && (i-1) - firstNull > 34) {
				System.out.println("Interface ids " + firstNull + " - " + (i-1) + " amount of free ids " + ((i-1) - firstNull));
				firstNull = -1;
			}
		}
	}
	
	public static void addClickableText(int id, String text, String tooltip, GameFont tda[], int idx, int color, int width, int height)
    {
        Widget Tab = addTabInterface(id);
        Tab.parent = id;
        Tab.id = id;
        Tab.type = 4;
        Tab.atActionType = 1;
        Tab.width = width;
        Tab.height = height;
        Tab.contentType = 0;
        Tab.opacity = 0;
        Tab.hoverType = -1;
        Tab.centerText = false;
        Tab.textShadow = true;
        Tab.textDrawingAreas = tda[idx];
        Tab.defaultText = text;
        Tab.tooltip = tooltip;
        Tab.secondaryText = "";
        Tab.textColor = color;
        Tab.secondaryColor = 0;
        Tab.defaultHoverCover = 0xFFFFFF;
        Tab.secondaryHoverColor = 0;
    }
	
	public static void addClickableText(int id, String text, String tooltip, GameFont tda[], int idx, int color, boolean center, boolean shadow, int width) {
        Widget tab = addTabInterface(id);
        tab.parent = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = 1;
        tab.width = width;
        tab.height = 11;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.textShadow = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.defaultText = text;
        tab.secondaryText = "";
        tab.textColor = color;
        tab.secondaryColor = 0;
        tab.defaultHoverCover = 0xffffff;
        tab.secondaryHoverColor = 0;
        tab.tooltip = tooltip;
    }
	
	public static void itemsOnDeath(GameFont[] wid) {
		Widget rsinterface = addInterface(17100);
		addSprite(17101, 2, "Death/SPRITE");
		
		//addHover(17102, 3, 0, 10601, 1, "Death/SPRITE", 17, 17, "Close Window");
		//addHovered(10601, 3, "Death/SPRITE", 17, 17, 10602);
		
		addHoverButton(17102, "Death/SPRITE", 1, 17, 17, "Close Window", 0, 3325, 3);
		addHovered(10601, 3, "Death/SPRITE", 17, 17, 10602);
		
		addText(17103, "Items Kept On Death", wid, 2, 0xff981f, false, true);
		addText(17104, "Items you will keep on death (if not skulled):", wid, 1, 0xff981f, false, true);
		addText(17105, "Items you will lose on death (if not skulled):", wid, 1, 0xff981f, false, true);
		addText(17106, "Information", wid, 1, 0xff981f, false, true);
		addText(17107, "Max items kept on death:", wid, 1, 0xffcc33, false, true);
		addText(17108, "~ 3 ~", wid, 1, 0xffcc33, false, true);
		rsinterface.scrollMax = 0;
		rsinterface.interfaceShown = false;
		rsinterface.children = new int[12];		rsinterface.childX = new int[12];	rsinterface.childY = new int[12];
		
		rsinterface.children[0] = 17101;		rsinterface.childX[0] = 7;			rsinterface.childY[0] = 8;
		rsinterface.children[1] = 17102;		rsinterface.childX[1] = 480;		rsinterface.childY[1] = 17;        
		rsinterface.children[2] = 17103;		rsinterface.childX[2] = 185;		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;		rsinterface.childX[3] = 22;			rsinterface.childY[3] = 50;
		rsinterface.children[4] = 17105;		rsinterface.childX[4] = 22;			rsinterface.childY[4] = 110;
		rsinterface.children[5] = 17106;		rsinterface.childX[5] = 347;		rsinterface.childY[5] = 47;
		rsinterface.children[6] = 17107;		rsinterface.childX[6] = 349;		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;		rsinterface.childX[7] = 398;		rsinterface.childY[7] = 298;
		rsinterface.children[8] = 17115;		rsinterface.childX[8] = 348;		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;		rsinterface.childX[9] = 26;			rsinterface.childY[9] = 74;
		rsinterface.children[10] = 10600;		rsinterface.childX[10] = 26;		rsinterface.childY[10] = 133;
		rsinterface.children[11] = 10601;		rsinterface.childX[11] = 480;		rsinterface.childY[11] = 17; 
	}
	
	public static void itemsOnDeathDATA(GameFont[] wid) {
		Widget rsinterface = addInterface(17115);
		addText(17109, "", wid, 0, 0xff981f, false, true);
		addText(17110, "The normal amount of", wid, 0, 0xff981f, false, true);
		addText(17111, "items kept is three.", wid, 0, 0xff981f, false, true);
		addText(17112, "", wid, 0, 0xff981f, false, true);
		addText(17113, "If you are skulled,", wid, 0, 0xff981f, false, true);
		addText(17114, "you will lose all your", wid, 0, 0xff981f, false, true);
		addText(17117, "items, unless an item", wid, 0, 0xff981f, false, true);
		addText(17118, "protecting prayer is", wid, 0, 0xff981f, false, true);
		addText(17119, "used.", wid, 0, 0xff981f, false, true);
		addText(17120, "", wid, 0, 0xff981f, false, true);
		addText(17121, "Item protecting prayers", wid, 0, 0xff981f, false, true);
		addText(17122, "will allow you to keep", wid, 0, 0xff981f, false, true);
		addText(17123, "one extra item.", wid, 0, 0xff981f, false, true);
		addText(17124, "", wid, 0, 0xff981f, false, true);
		addText(17125, "The items kept are", wid, 0, 0xff981f, false, true);
		addText(17126, "selected by the server", wid, 0, 0xff981f, false, true);
		addText(17127, "and include the most", wid, 0, 0xff981f, false, true);
		addText(17128, "expensive items you'renderable", wid, 0, 0xff981f, false, true);
		addText(17129, "carrying.", wid, 0, 0xff981f, false, true);
		addText(17130, "", wid, 0, 0xff981f, false, true);
		rsinterface.parent = 17115;
		rsinterface.id = 17115;
		rsinterface.interfaceType = 0;
		rsinterface.atActionType = 0;
		rsinterface.contentType = 0;
		rsinterface.width = 130;
		rsinterface.height = 197;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.scrollMax = 280;
		rsinterface.children = new int[20];		rsinterface.childX = new int[20];	rsinterface.childY = new int[20];
		rsinterface.children[0] = 17109;		rsinterface.childX[0] = 0;			rsinterface.childY[0] = 0;
		rsinterface.children[1] = 17110;		rsinterface.childX[1] = 0;			rsinterface.childY[1] = 12;
		rsinterface.children[2] = 17111;		rsinterface.childX[2] = 0;			rsinterface.childY[2] = 24;
		rsinterface.children[3] = 17112;		rsinterface.childX[3] = 0;			rsinterface.childY[3] = 36;
		rsinterface.children[4] = 17113;		rsinterface.childX[4] = 0;			rsinterface.childY[4] = 48;
		rsinterface.children[5] = 17114;		rsinterface.childX[5] = 0;			rsinterface.childY[5] = 60;
		rsinterface.children[6] = 17117;		rsinterface.childX[6] = 0;			rsinterface.childY[6] = 72;
		rsinterface.children[7] = 17118;		rsinterface.childX[7] = 0;			rsinterface.childY[7] = 84;
		rsinterface.children[8] = 17119;		rsinterface.childX[8] = 0;			rsinterface.childY[8] = 96;
		rsinterface.children[9] = 17120;		rsinterface.childX[9] = 0;			rsinterface.childY[9] = 108;
		rsinterface.children[10] = 17121;		rsinterface.childX[10] = 0;			rsinterface.childY[10] = 120;
		rsinterface.children[11] = 17122;		rsinterface.childX[11] = 0;			rsinterface.childY[11] = 132;
		rsinterface.children[12] = 17123;		rsinterface.childX[12] = 0;			rsinterface.childY[12] = 144;
		rsinterface.children[13] = 17124;		rsinterface.childX[13] = 0;			rsinterface.childY[13] = 156;
		rsinterface.children[14] = 17125;		rsinterface.childX[14] = 0;			rsinterface.childY[14] = 168;
		rsinterface.children[15] = 17126;		rsinterface.childX[15] = 0;			rsinterface.childY[15] = 180;
		rsinterface.children[16] = 17127;		rsinterface.childX[16] = 0;			rsinterface.childY[16] = 192;
		rsinterface.children[17] = 17128;		rsinterface.childX[17] = 0;			rsinterface.childY[17] = 204;
		rsinterface.children[18] = 17129;		rsinterface.childX[18] = 0;			rsinterface.childY[18] = 216;
		rsinterface.children[19] = 17130;		rsinterface.childX[19] = 0;			rsinterface.childY[19] = 228;
	}

	public static final String[] SKILL_NAMES = {
			"Attack", 
			"Defence", 
			"Strength", 
			"Hitpoints", 
			"Range", 
			"Prayer", 
			"Magic", 
			"Cooking", 
			"Woodcutting",
			"Fletching", 
			"Fishing", 
			"Firemaking", 
			"Crafting", 
			"Smithing", 
			"Mining", 
			"Herblore", 
			"Agility", 
			"Thieving",
			"Slayer", 
			"Farming", 
			"Runecrafting", 
			"Construction", 
			"Hunter", 
			"Summoning"
	};

	private static int getSkillIndex(String name) {
		for (int i = 0; i < SKILL_NAMES.length; i++) {
			if (name.equals(SKILL_NAMES[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static void Skills(GameFont[] TDA) {
		Widget Interface = addInterface(3917);
		addSprite(20000, 1, "Interfaces/Skill/SKILL");
		addText(50240, "Total Level: 0", 0xffff00, false, true, 52, TDA, 2);
		setChildren(3, Interface);
		setBounds(20000, 5, 17, 0, Interface);
		setBounds(20001, 7, 20, 1, Interface);
		setBounds(50240, 7, 1, 2, Interface);
		Interface = addInterface(20001);
		Interface.height = 226;
		Interface.width = 163;
		Interface.scrollMax = 258;
		setChildren(97, Interface); //85
		int frame = 1;
		
		int[] Height = {
			3, 3, 3, 34, 34, 34, 66,
			66, 66, 98, 98, 98, 131, 131,
			131, 162, 162, 162, 195, 195, 195,
			228, 228, 228
		};
		int[] Width = {
			24, 78, 132, 24, 78, 132, 24,
			78, 132, 24, 78, 132, 24, 78,
			132, 24, 78, 132, 24, 78, 132,
			24, 78, 132,
		};
		int[] Height1 = {
			15, 15, 15, 48, 48, 48, 80,
			80, 80, 111, 111, 111, 145, 145,
			145, 175, 175, 175, 208, 208, 208,
			241, 241, 241
		};
		int[] Width1 = {
			24+12, 78+12, 132+12, 24+12, 78+12, 132+12, 24+12,
			78+12, 132+12, 24+12, 78+12, 132+12, 24+12, 78+12,
			132+12, 24+12, 78+12, 132+12, 24+12, 78+12, 132+12,
			24+12, 78+12, 132+12,
		};
			
		int[] skillID1 = {
			4004, 4016, 4028, 4006, 4018, 4030, 4008, 
			4020, 4032, 4010, 4022, 4034, 4012, 4024, 
			4036, 4014, 4026, 4038, 4152, 12166, 13926,
			50204, 50206, 50208
		};
		
		int[] skillID2 = {
			4005, 4017, 4029, 4007, 4019, 4031, 4009,
			4021, 4033, 4010, 4023, 4035, 4013, 4025, 
			4037, 4014, 4026, 4038, 4152, 12167, 13927,
			50205, 50207, 50209
		};
		int[] X = {
			0+2, 54+2, 107+2, 0+2, 54+2, 107+2, 0+2,
			54+2, 107+2,0+2, 54+2, 107+2, 0+2, 54+2,
			107+2,0+2, 54+2, 107+2,0+2, 54+2, 107+2,
			0+2, 54+2, 107+2,
		};
		int[] Y = {
			0, 0, 0, 32, 32, 32, 64,
			64, 64, 96, 96, 96, 128, 128,
			128, 160, 160, 160, 192, 192, 192,
			224, 224, 224
		};
		int[] skillButtonIDs = {
			50002, 50003, 50004, 50005, 50006, 50007, 50008, 
			50009, 50010, 50011, 50012, 50013, 50014, 50015,
			50016, 50017, 50018, 50019, 50020, 50021, 50022,
			50210, 50220, 50230
		};
		int[] hoverIDs = {
			50023, 50030, 50037, 50044, 50051, 50058, 50065,
			50072, 50080, 50087, 50094, 50101, 50108, 50115,
			50122, 50129, 50136, 50143, 50150, 50157, 50164,
			50215, 50225, 50235
		};
		String[] skillStrings = {
			"Combat", "Combat", "Mining", "Combat", "Agility", "Smithing", "Combat", 
			"Herblore", "Fishing", "Range", "Thieving", "Cooking", "Prayer", "Crafting", 
			"Firemaking", "Magic", "Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming",
			"Construction", "Hunter", "Summoning"
		};		
		String[] skillNames = {
			"Attack", "Hitpoints", "Mining", "Strength", "Agility", "Smithing", "Defence", 
			"Herblore", "Fishing", "Range", "Thieving", "Cooking", "Prayer", "Crafting", 
			"Firemaking", "Magic", "Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming",
			"Construction", "Hunter", "Summoning"
		};	
		int[] hoverX = {
			15, 25, 28, 15, 25, 28, 15,
			25, 28, 15, 25, 28, 15, 25,
			28, 15, 25, 28, 15, 25, 28,
			15, 25, 28
		};
		int[] hoverY = {
			34, 34, 34, 66, 66, 66, 98,
			98, 98, 130, 130, 130, 162, 162,
			162, 83, 83, 83, 118, 118, 118,
			153, 153, 153
		};
		int skillCount = 0;
		for(int i : skillID1){//-.-no
			addText(i, "%1", 0xffff00, true, true, 52, TDA, 0);
			setBounds(i, Width[frame-1] + 7, Height[frame-1] + 1, frame, Interface);
			int[][] valueIndexArray = new int[1][3];
			valueIndexArray[0][0] = 1;
			valueIndexArray[0][1] = getSkillIndex(skillNames[skillCount++]);
			valueIndexArray[0][2] = 0;
			interfaceCache[i].valueIndexArray = valueIndexArray;
			frame++;		
		}
		int frame1 = 0;
		skillCount = 0;
		for(int i : skillID2){
			addText(i, "%1", 0xffff00, true, true, 52, TDA, 0);
			setBounds(i, Width1[frame1] + 7, Height1[frame1] + 1, frame, Interface);
			int[][] valueIndexArray = new int[1][3];
			valueIndexArray[0][0] = 2;
			valueIndexArray[0][1] = getSkillIndex(skillNames[skillCount++]);
			valueIndexArray[0][2] = 0;
			interfaceCache[i].valueIndexArray = valueIndexArray;
			frame++;		
			frame1++;
		}
		int frame2 = 0;
		for(int i : skillButtonIDs){
			addSkillButton(i, 0, "", skillStrings[frame2], 1, hoverIDs[frame2], TDA, skillNames[frame2]);
			setBounds(i, X[frame2], Y[frame2], frame, Interface);
			frame++;
			frame2++;
		}
		int frame3 = 0;
		for(int i : hoverIDs){
			setBounds(i, hoverX[frame3], hoverY[frame3], frame, Interface);
			frame++;
			frame3++;
		}
		addSprite(47500, 0, "Interfaces/Skill/SKILL");//skills problem
		setBounds(47500, 0, 0, 0, Interface);	
	}
	
	public static void addSkillButton(int i, int spriteID, String name, String skillGuide, int AT, int hoverID, GameFont[] TDA, String skillName) {
        Widget Interface = addInterface(i);
        Interface.id = i;
        Interface.parent = i;
        Interface.type = 5;
        Interface.atActionType = AT;
        Interface.contentType = 0;
        Interface.opacity = 0;
        Interface.hoverType = hoverID;
        Interface.disabledSprite = imageLoader(spriteID, name);
        Interface.enabledSprite = imageLoader(spriteID, name);
        Interface.width = 53;
        Interface.height = 32;
        Interface.tooltip = "View "+skillGuide+" Guide";
		
		Interface = addInterface(hoverID);
		Interface.invisible = true;
		Interface.type = 0;
		Interface.atActionType = 0;
		Interface.hoverType = -1;
		Interface.width = 512;
		Interface.height = 334;
		Interface.parent = hoverID;
        Interface.id = hoverID;
		addBox(hoverID+1, 0, false, 0x000000, skillName+" Lvl:\nCurrentXP:\nNext Lvl:\nRemainder:");
		setChildren(1, Interface);
		setBounds(hoverID+1, 0, 0, 0, Interface);
    }
	
	public static void addSkillText(int id, String text, int color, boolean centered, boolean shadowed, int mOver, GameFont[] TDA, int j) {
        Widget rsinterface = addTabInterface(id);
        rsinterface.parent = id;
        rsinterface.id = id;
        rsinterface.type = 4;
        rsinterface.atActionType = 0;
        rsinterface.width = 0;
        rsinterface.height = 0;
        rsinterface.contentType = 0;
        rsinterface.opacity = 0;
        rsinterface.hoverType = mOver;
        rsinterface.centerText = centered;
        rsinterface.textShadow = shadowed;
        rsinterface.textDrawingAreas = TDA[j];
        rsinterface.defaultText = text;
        rsinterface.popupString = "";
        rsinterface.textColor = color;
    }
	
	/*public static void SummonTab(GameFont[] wid) {
		Widget Tab = addTabInterface(17011);
		addSprite(17012, 6, "SummonTab/SUMMON");
		addButton(17013, 7, "/SummonTab/SUMMON", "Click");
		addSprite(17014, 6, "SummonTab/SUMMON");
		addConfigButton(17015, 17032, 14, 8, "/SummonTab/SUMMON", 20, 30, "Familiar Special", 1, 5, 300);
		addHoverButton(17018, "/SummonTab/SUMMON", 2, 38, 36, "Beast of burden Inventory", -1, 17028, 1);
		addHoveredButton(17028, "/SummonTab/SUMMON", 12, 38, 36, 17029);
		addHoverButton(17022, "/SummonTab/SUMMON", 1, 38, 36, "Call Familiar", -1, 17030, 1);
		addHoveredButton(17030, "/SummonTab/SUMMON", 13, 38, 36, 17031);
		addHoverButton(17023, "/SummonTab/SUMMON", 3, 38, 36, "Dismiss Familiar", -1, 17033, 1);
		addHoveredButton(17033, "/SummonTab/SUMMON", 15, 38, 36, 17034);
		addHoverButton(17038, "/SummonTab/SUMMON", 17, 38, 36, "Renew Summon", -1, 17039, 1);
		addHoveredButton(17039, "/SummonTab/SUMMON", 18, 38, 36, 17041);
		addSprite(17016, 5, "SummonTab/SUMMON");
		addText(17017, "", wid, 2, 0xDAA520, true, true);
		addSprite(17019, 9, "SummonTab/SUMMON");
		addText(17021, "", wid, 0, 0xFFA500, true, true);
		addSprite(17020, 10, "SummonTab/SUMMON");
		addSprite(17024, 11, "SummonTab/SUMMON");
		addText(17025, "", wid, 0, 0xFFA500, false, true);
		addText(17026, "", wid, 0, 0xFFA500, false, true);
		addText(17040, " ", wid, 0, 0xFFA500, false, true);
		//addHead2(17027, 75, 55, 800);
		Tab.totalChildren(21);
		Tab.child(0, 17012, 10, 25);
		Tab.child(1, 17013, 24, 7);
		Tab.child(2, 17014, 10, 25);
		Tab.child(3, 17015, 11, 25);
		Tab.child(4, 17016, 15, 140);
		Tab.child(5, 17017, 95, 143);
		Tab.child(6, 17018, 20, 170);
		Tab.child(7, 17019, 115, 167);
		Tab.child(8, 17020, 143, 170);
		Tab.child(9, 17021, 145, 197);
		Tab.child(10, 17022, 20, 213);
		Tab.child(11, 17023, 67, 170);
		Tab.child(12, 17024, 135, 214);
		Tab.child(13, 17025, 135, 240);
		Tab.child(14, 17026, 21, 59);
		//Tab.child(15, 17027, 75, 55);
		Tab.child(15, 17028, 20, 170);
		Tab.child(16, 17030, 20, 213);
		Tab.child(17, 17033, 67, 170);
		Tab.child(18, 17038, 67, 213);
		Tab.child(19, 17039, 67, 213);
		Tab.child(20, 17040, 30, 8);
	}*/
	
	public static void addButton(int id, int sid, String spriteName, String tooltip, int mOver, int atAction, int width, int height) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = mOver;
		tab.disabledSprite = imageLoader(sid, spriteName);
		tab.enabledSprite = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
		tab.inventoryhover = true;
	}
	public boolean inventoryhover;
	
		private static String[] scrollNames = {"Howl","Dreadfowl Strike","Egg Spawn","Slime Spray","Stony Shell","Pester","Electric Lash","Venom Shot","Fireball Assault","Cheese Feast","Sandstorm","Generate Compost","Explode","Vampire Touch","Insane Ferocity","Multichop","Call of Arms","Call of Arms","Call of Arms","Call of Arms","Bronze Bull Rush","Unburden","Herbcall","Evil Flames","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Petrifying gaze","Iron Bull Rush","Immense Heat","Thieving Fingers","Blood Drain","Tireless Run","Abyssal Drain","Dissolve","Steel Bull Rush","Fish Rain","Goad","Ambush","Rending","Doomsphere Device","Dust Cloud","Abyssal Stealth","Ophidian Incubation","Poisonous Blast","Mithril Bull Rush","Toad Bark","Testudo","Swallow Whole","Fruitfall","Famine","Arctic Blast","Rise from the Ashes","Volcanic Strength","Crushing Claw","Mantis Strike","Adamant Bull Rush","Inferno","Deadly Claw","Acorn Missile","Titan's Consitution","Titan's Consitution","Titan's onsitution","Regrowth","Spike Shot","Ebon Thunder","Swamp Plague","Rune Bull Rush","Healing Aura","Boil","Magic Focus","Essence Shipment","Iron Within","Winter Storage","Steel of Legends",};
		private static String[] pouchNames = {"Spirit Wolf", "Dreadfowl", "Spirit Spider", "Thorny Snail", "Granite Crab", "Spirit Mosquito", "Desert Wyrm", "Spirit Scorpian", "Spirit Tz-Kih", "Albino rat", "Spirit Kalphite", "Compost mound", "Giant Chinchompa", "Vampire Bat", "Honey badger", "Beaver", "Void Ravager", "Void Spinner", "Void Torcher", "Void Shifter", "Bronze minotaur", "Bull ant", "Macaw", "Evil Turnip", "Spirit Cockatrice", "Spirit Guthatrice", "Spirit Saratrice", "Spirit Zamatrice", "Spirit Pengatrice", "Spirit Coraxatrice", "Spirit Vulatrice", "Iron minotaur", "Pyrelord", "Magpie", "Bloated Leech", "Spirit terrorbird", "Abyssal Parasite", "Spirit Jelly", "Steel Minotaur", "Ibis","Spirit kyatt", "Spirit laurpia", "Spirit graahk", "Karamthulhu overlord", "Smoke Devil", "Abyssal Lurker", "Spirit cobra", "Stranger Plant", "Mithril minotaur", "Barker Toad", "War tortoise","Bunyip", "Fruit bat", "Ravenous Locust", "Artic Bear", "Pheonix", "Obsidian Golem", "Granite Lobster", "Praying mantis", "Forge regent", "Adamant minotaur", "Talon Beast", "Giant ent","Fire titan","Moss titan","Ice titan","Hydra","Spirit daggannoth","Lava titan","Swamp titan","Rune minotaur", "Unicorn Stallion", "Geyser titan", "Wolpertinger", "Abyssal Titan", "Iron titan","Pack Yack","Steel titan"};
		
		public static void scrolls(GameFont[] TDA) {
		Widget rsinterface = addInterface(38700);
		addButton(38701, 0, "Interfaces/Scrolls/SPRITE", "Pouches", 27640, 1, 116, 20);
		addButton(38702, 1, "Interfaces/Scrolls/SPRITE", "Scrolls", 27640, 1, 116, 20);
		
		Widget scroll = addTabInterface(38710);
		scroll.width = 430; scroll.height = 230; scroll.scrollMax = 550;
		//scroll.newScroller = true;
		int lastId = 38710;
		int lastImage = 4;
		for (int i = 0; i < 78; i++) {
			addHover(lastId+1, 1, 0, lastId+2, lastImage+1, "Interfaces/Scrolls/Sprite", 46, 50, "Select " + scrollNames[i] + " scroll");
			addHovered(lastId+2, lastImage+2, "Interfaces/Scrolls/Sprite", 46, 50, lastId+3);
			lastId += 3;
			lastImage += 2;
		}	
		
		rsinterface.children = new int[7];		rsinterface.childX = new int[7];	rsinterface.childY = new int[7];
		
		rsinterface.children[0] = 39701;		rsinterface.childX[0] = 14;			rsinterface.childY[0] = 17;
		rsinterface.children[1] = 39702;		rsinterface.childX[1] = 475;		rsinterface.childY[1] = 30;        
		rsinterface.children[2] = 39703;		rsinterface.childX[2] = 475;		rsinterface.childY[2] = 30;        
		rsinterface.children[3] = 38701;		rsinterface.childX[3] = 25;			rsinterface.childY[3] = 30;       
		rsinterface.children[4] = 38702;		rsinterface.childX[4] = 128;		rsinterface.childY[4] = 30;     
		rsinterface.children[5] = 39707;		rsinterface.childX[5] = 268;		rsinterface.childY[5] = 30;     
		rsinterface.children[6] = 38710;		rsinterface.childX[6] = 35;			rsinterface.childY[6] = 65;
		
		
		scroll.children = new int[156];		scroll.childX = new int[156];			scroll.childY = new int[156];
		
		int lastNumber = -1;
		int lastId2 = 38710;
		int lastX = -52;
		int lastY = 0;
		for (int i = 0; i < 78; i++) {
			scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX+=52;		scroll.childY[lastNumber] = lastY;
			scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX;			scroll.childY[lastNumber] = lastY;
			lastId2 += 1;
			if (lastX == 364) {
				lastX = -52;
				lastY += 55;
			}
		}
	}

		public static void commandsList(GameFont[] tda) {
            Widget tab = addTabInterface(45000);
            String dir = "/CommandList/SPRITE";
            addSprite(45001, 0, dir);
            addHoverButton(45002, dir, 1, 21, 21, "Close", -1, 45003, 1);
            addHoveredButton(45003, dir, 2, 21, 21, 45004);
            addText(45005, "Commands List", tda, 2, 0xFFA500, true, true);
            int x = 22, y = 5;
            tab.totalChildren(5);
            tab.child(0, 45001, x, y);
            tab.child(1, 45002, 421+x, 7+y);
            tab.child(2, 45003, 421+x, 7+y);
            tab.child(3, 45005, 225+x, 9+y);
            tab.child(4, 45006, 16+x, 44+y);
            
            Widget scroll = addInterface(45006);
            scroll.totalChildren(100);
            int yy = 0, zz = 3;
            for (int i = 0; i < 50; i++) {
                addSprite(45010 + i, zz, dir);
                addText(45060 + i, "Command Name", tda, 0, 0xffffff, false, true);
                scroll.child(i, 45010+i, 0, yy);
                scroll.child(i + 50, 45060+i, 4, yy + 2);
                yy += 16;
                zz++;
                if (zz == 5) {
                    zz = 3;
                }
            }
            scroll.width = 401;
            scroll.height = 260;
            scroll.scrollMax = yy;
        }
		
	public static void bossKillLog(GameFont[] tda) {
            Widget tab = addTabInterface(45200);
            String dir = "/CommandList/SPRITE";
            addSprite(45201, 0, dir);
            addHoverButton(45202, dir, 1, 21, 21, "Close", -1, 45203, 1);
            addHoveredButton(45203, dir, 2, 21, 21, 45204);
            addText(45205, "Boss Kill Counters", tda, 2, 0xFFA500, true, true);
            int x = 22, y = 5;
            tab.totalChildren(5);
            tab.child(0, 45201, x, y);
            tab.child(1, 45202, 421+x, 7+y);
            tab.child(2, 45203, 421+x, 7+y);
            tab.child(3, 45205, 225+x, 9+y);
            tab.child(4, 45206, 16+x, 44+y);
            
            Widget scroll = addInterface(45206);
            scroll.totalChildren(100);
            int yy = 0, zz = 3;
            for (int i = 0; i < 50; i++) {
                addSprite(45210 + i, zz, dir);
                addText(45260 + i, "Boss Killcount", tda, 0, 0xffffff, false, true);
                scroll.child(i, 45210+i, 0, yy);
                scroll.child(i + 50, 45260+i, 4, yy + 2);
                yy += 16;
                zz++;
                if (zz == 5) {
                    zz = 3;
                }
            }
            scroll.width = 401;
            scroll.height = 260;
            scroll.scrollMax = yy;
        }
		
	public static void playerList(GameFont[] tda) {
            Widget tab = addTabInterface(45400);
            String dir = "/CommandList/SPRITE";
            addSprite(45401, 0, dir);
            addHoverButton(45402, dir, 1, 21, 21, "Close", -1, 45403, 1);
            addHoveredButton(45403, dir, 2, 21, 21, 45204);
            addText(45405, "Players Online", tda, 2, 0xFFA500, true, true);
            int x = 22, y = 5;
            tab.totalChildren(5);
            tab.child(0, 45401, x, y);
            tab.child(1, 45402, 421+x, 7+y);
            tab.child(2, 45403, 421+x, 7+y);
            tab.child(3, 45405, 225+x, 9+y);
            tab.child(4, 45406, 16+x, 44+y);
            
            Widget scroll = addInterface(45406);
            scroll.totalChildren(100);
            int yy = 0, zz = 3;
            for (int i = 0; i < 50; i++) {
                addSprite(45410 + i, zz, dir);
                addText(45460 + i, "Player Name", tda, 0, 0xffffff, false, true);
                scroll.child(i, 45410+i, 0, yy);
                scroll.child(i + 50, 45460+i, 4, yy + 2);
                yy += 16;
                zz++;
                if (zz == 5) {
                    zz = 3;
                }
            }
            scroll.width = 401;
            scroll.height = 260;
            scroll.scrollMax = yy;
        }
	
	public static void hannah(GameFont[] tda) {
        Widget tab = addTabInterface(45600);
        String dir = "/CommandList/SPRITE";
        addSprite(45601, 0, dir);
        addHoverButton(45602, dir, 1, 21, 21, "Close", -1, 45603, 1);
        addHoveredButton(45603, dir, 2, 21, 21, 45604);
        addText(45605, "In Memory Of Hannah(Sokka212)", tda, 2, 0xFFA500, true, true);
        int x = 22, y = 5;
        tab.totalChildren(5);
        tab.child(0, 45601, x, y);
        tab.child(1, 45602, 421+x, 7+y);
        tab.child(2, 45603, 421+x, 7+y);
        tab.child(3, 45605, 225+x, 9+y);
        tab.child(4, 45606, 16+x, 44+y);
        
        Widget scroll = addInterface(45606);
        scroll.totalChildren(100);
        int yy = 0, zz = 3;
        for (int i = 0; i < 50; i++) {
            addSprite(45610 + i, zz, dir);
            addText(45660 + i, " ", tda, 0, 0xffffff, false, true);
            scroll.child(i, 45610+i, 0, yy);
            scroll.child(i + 50, 45660+i, 4, yy + 2);
            yy += 16;
            zz++;
            if (zz == 5) {
                zz = 3;
            }
        }
        scroll.width = 401;
        scroll.height = 260;
        scroll.scrollMax = yy;
    }
		
	public static void pouches(GameFont[] TDA) {
		Widget rsinterface = addInterface(39700);
		addSprite(39701, 0, "Interfaces/Pouches/SPRITE");
		addHover(39702, 3, 0, 39703, 1, "Interfaces/Pouches/SPRITE", 17, 17, "Close Window");
		addHovered(39703, 2, "Interfaces/Pouches/SPRITE", 17, 17, 39704);
		addButton(39705, 3, "Interfaces/Pouches/SPRITE", "Pouches", 27640, 1, 116, 20);
		addButton(39706, 4, "Interfaces/Pouches/SPRITE", "Scrolls", 27640, 1, 116, 20);
		addText(39707, "Summoning Pouch Creation", TDA, 2, 0xff981f, false, true);
			
		Widget scroll = addTabInterface(39710);
		scroll.width = 430; scroll.height = 230; scroll.scrollMax = 550;
		//scroll.newScroller = true;
		int lastId = 39710;
		int lastImage = 4;
		for (int i = 0; i < 78; i++) {
			addHover(lastId+1, 1, 0, lastId+2, lastImage+1, "Interfaces/Pouches/Sprite", 46, 50, "Infuse " + pouchNames[i] + " pouch");
			addHovered(lastId+2, lastImage+2, "Interfaces/Pouches/Sprite", 46, 50, lastId+3);
			lastId += 3;
			lastImage += 2;
		}
			
		
		rsinterface.children = new int[7];		rsinterface.childX = new int[7];	rsinterface.childY = new int[7];
		
		rsinterface.children[0] = 39701;		rsinterface.childX[0] = 14;			rsinterface.childY[0] = 17;
		rsinterface.children[1] = 39702;		rsinterface.childX[1] = 475;		rsinterface.childY[1] = 30;        
		rsinterface.children[2] = 39703;		rsinterface.childX[2] = 475;		rsinterface.childY[2] = 30;        
		rsinterface.children[3] = 39705;		rsinterface.childX[3] = 25;			rsinterface.childY[3] = 30;       
		rsinterface.children[4] = 39706;		rsinterface.childX[4] = 128;		rsinterface.childY[4] = 30;       
		rsinterface.children[5] = 39707;		rsinterface.childX[5] = 268;		rsinterface.childY[5] = 30;       
		rsinterface.children[6] = 39710;		rsinterface.childX[6] = 35;			rsinterface.childY[6] = 65;
		
		
		scroll.children = new int[156];		scroll.childX = new int[156];			scroll.childY = new int[156];
		
		int lastNumber = -1;
		int lastId2 = 39710;
		int lastX = -52;
		int lastY = 0;
		for (int i = 0; i < 78; i++) {
			scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX+=52;		scroll.childY[lastNumber] = lastY;
			scroll.children[lastNumber+=1] = lastId2+=1;			scroll.childX[lastNumber] = lastX;		scroll.childY[lastNumber] = lastY;
			lastId2 += 1;
			if (lastX == 364) {
				lastX = -52;
				lastY += 55;
			}
		}
	}
	
	public static void questTab(GameFont[] TDA) {
		Widget Interface = addInterface(638);
		setChildren(5, Interface);
		addText(29155, "Player Panel", 0xFF981F, false, true, 52, TDA, 2);
		addButton(29156, 1, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Achievements", 1);
		addButton(29270, 3, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Quests", 1);
		addSprite(29157, 0, "Interfaces/QuestTab/QUEST");
		setBounds(29155, 10, 5, 0, Interface);
		setBounds(29156, 154, 4, 1, Interface);
		setBounds(29157, 3, 24, 2, Interface);
		setBounds(29160, 5, 29, 3, Interface);
		setBounds(29270, 172, 4, 4, Interface);
		Interface = addInterface(29160);
		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 1700;
		Interface.newScroller = false;
		setChildren(104, Interface);
		addText(29161, "Server Information", 0xFF981F, false, true, 52, TDA, 2);
		addHoverText(29162, " ", " ", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29163, " ", " ", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29164, " ", " ", TDA, 0, 0xff0000, false, true, 150);
		addText(29165, "Player Information", 0xFF981F, false, true, 52, TDA, 2);
		addHoverText(29166, " ", " ", TDA, 0, 0xff0000, false, true, 150);
		setBounds(29161, 4, 4, 0, Interface);
		setBounds(29162, 8, 22, 1, Interface);
		setBounds(29163, 8, 35, 2, Interface);
		setBounds(29164, 8, 48, 3, Interface);
		setBounds(29165, 8, 61, 4, Interface);
		setBounds(29166, 8, 79, 5, Interface);
		int Ypos = 92;
		int frameID = 6;
		for (int iD = 29167; iD <= 29264; iD++) {
			addHoverText(iD, "", " " /* + iD/* "View Quest Journal, "+iD */,
					TDA, 0, 0xff0000, false, true, 150);
			setBounds(iD, 8, Ypos, frameID, Interface);
			frameID++;
			Ypos += 13;
			Ypos++;
		}
		Interface = addInterface(29265);
		setChildren(5, Interface);
		addText(29266, "Achievements", 0xFF981F, false, true, -1, TDA,
				2);
		addButton(29267, 2, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Player Panel", 1);
		addButton(29271, 3, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Achievements", 1);
		addSprite(29269, 0, "Interfaces/QuestTab/QUEST");
		setBounds(29266, 10, 5, 0, Interface);
		setBounds(29267, 154, 4, 1, Interface);
		setBounds(29269, 3, 24, 2, Interface);
		setBounds(29268, 5, 29, 3, Interface);
		setBounds(29271, 172, 4, 4, Interface);
		Interface = addInterface(29268);
		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 215;
		Interface.newScroller = false;
		setChildren(14, Interface);
		setBounds(29295, 8, 6, 0, Interface);
		setBounds(29287, 8, 21, 1, Interface);
		setBounds(29305, 8, 36, 2, Interface);
		setBounds(29306, 8, 51, 3, Interface);
		setBounds(29307, 8, 66, 4, Interface);
		setBounds(29308, 8, 81, 5, Interface);
		setBounds(29309, 8, 96, 6, Interface);
		setBounds(29310, 8, 110, 7, Interface);
		setBounds(29311, 8, 125, 8, Interface);
		setBounds(29312, 8, 155, 9, Interface);
		setBounds(29313, 8, 170, 10, Interface);
		setBounds(29314, 8, 140, 11, Interface);
		setBounds(29315, 8, 185, 12, Interface);
		setBounds(29316, 8, 200, 13, Interface);
		addHoverText(29295, "Tasks Completed: 0", " ", TDA, 0, 65280, false, true, 150);
		addHoverText(29287, "Easy Tasks", " ", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29305, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29306, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29307, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29308, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29309, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29310, "Medium Tasks", " ", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29311, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29314, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29312, "Hard Tasks", " ", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29313, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29315, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29316, "        Task", "View Achievements", TDA, 0, 0xff0000, false, true, 150);

		Interface = addInterface(29300);

		addText(29301, "Quests", 0xff0000, false, true, -1, TDA, 2);
		addButton(29302, 2, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Player Panel", 1);
		addButton(29303, 1, "Interfaces/QuestTab/QUEST", 18, 18, "Swap to Achievements", 1);
		addSprite(29304, 0, "Interfaces/QuestTab/QUEST");

		setChildren(5, Interface);
		setBounds(29301, 10, 5, 0, Interface);
		setBounds(29302, 154, 4, 1, Interface);
		setBounds(29304, 3, 24, 2, Interface);
		setBounds(29350, 5, 29, 3, Interface);
		setBounds(29303, 172, 4, 4, Interface);
		Interface = addInterface(29350);

		addHoverText(29352, "Cook's Assistant", "Cook's Assistant", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29353, "Dragon Slayer", "Dragon Slayer", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29351, "Eggtastrophe", "Eggtastrophe", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29354, "Recipe for Disaster", "Recipe for Disaster", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29355, "Romeo & Juliet", "Romeo & Juliet", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29356, "Santa's Workshop", "Santa's Workshop", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29357, "Turkish Delight", "Turkish Delight", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29358, "While Guthix Sleeps", "While Guthix Sleeps", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29359, "", "", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29360, "", "", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29361, "", "", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29362, "", "", TDA, 0, 0xFF981F, false, true, 150);

		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 215;
		Interface.newScroller = false;
		setChildren(12, Interface);
		setBounds(29352, 8, 6, 0, Interface);
		setBounds(29353, 8, 21, 1, Interface);
		setBounds(29351, 8, 36, 2, Interface);
		setBounds(29354, 8, 51, 3, Interface);
		setBounds(29355, 8, 66, 4, Interface);
		setBounds(29356, 8, 81, 5, Interface);
		setBounds(29357, 8, 96, 6, Interface);
		setBounds(29358, 8, 110, 7, Interface);
		setBounds(29359, 8, 125, 8, Interface);
		setBounds(29360, 8, 140, 9, Interface);
		setBounds(29361, 8, 155, 10, Interface);
		setBounds(29362, 8, 170, 11, Interface);
	}
	
	public static void addHoverText(int id, String text, String tooltip, GameFont tda[], int idx, int color, boolean centerText, boolean textShadowed, int width) {
		Widget rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parent = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = centerText;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.defaultText = text;
		rsinterface.secondaryText = "";
		rsinterface.textColor = color;
		rsinterface.secondaryColor = 0;
		rsinterface.defaultHoverCover = 0xffffff;
		rsinterface.secondaryHoverColor = 0;
		rsinterface.tooltip = tooltip;
	}
	
	 private static void addHead2(int i, int j, int k, int l)
    {
		Widget Interface = addTabInterface(i);
        Interface.type = 6;
        Interface.modelZoom = l;
        Interface.modelRotation1 = 40;
        Interface.modelRotation2 = 1900;
        Interface.height = k;
        Interface.width = j;
    }

	public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, String PrayerName, int Hover) {
		Widget Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parent = 22500;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = Hover;
		Interface.disabledSprite = imageLoader(0, "Curses/GLOW");
		Interface.enabledSprite = imageLoader(1, "Curses/GLOW");
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = "Activate@or1@ " + PrayerName;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parent = 22500;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType  = 0;
		Interface.opacity = 0;
		Interface.disabledSprite = imageLoader(prayerSpriteID, "Curses/PRAYON");
		Interface.enabledSprite = imageLoader(prayerSpriteID, "Curses/PRAYOFF");
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues + 1;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}
	public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int spriteID, String prayerName) {
        Widget tab = addTabInterface(i);
        tab.id = i;
        tab.parent = 5608;
        tab.type = 5;
        tab.atActionType = 4;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.disabledSprite = imageLoader(0, "PRAYERGLOW");
        tab.enabledSprite = imageLoader(1, "PRAYERGLOW");
        tab.width = 34;
        tab.height = 34;
        tab.valueCompareType = new int[1];
        tab.requiredValues = new int[1];
        tab.valueCompareType[0] = 1;
        tab.requiredValues[0] = configId;
        tab.valueIndexArray = new int[1][3];
        tab.valueIndexArray[0][0] = 5;
        tab.valueIndexArray[0][1] = configFrame;
        tab.valueIndexArray[0][2] = 0;
		tab.tooltip = "Activate@or2@ " + prayerName;
        Widget tab2 = addTabInterface(i + 1);
        tab2.id = i + 1;
        tab2.parent = 5608;
        tab2.type = 5;
        tab2.atActionType = 0;
        tab2.contentType  = 0;
        tab2.opacity = 0;
        tab2.hoverType = -1;
        tab2.disabledSprite = imageLoader(spriteID, "/PRAYER/PRAYON");
        tab2.enabledSprite = imageLoader(spriteID, "/PRAYER/PRAYOFF");
        tab2.width = 34;
        tab2.height = 34;
        tab2.valueCompareType = new int[1];
        tab2.requiredValues = new int[1];
        tab2.valueCompareType[0] = 2;
        tab2.requiredValues[0] = requiredValues + 1;
        tab2.valueIndexArray = new int[1][3];
        tab2.valueIndexArray[0][0] = 2;
        tab2.valueIndexArray[0][1] = 5;
        tab2.valueIndexArray[0][2] = 0;
    }
	
	public static void addPrayerHover(int i, int hoverID, int prayerSpriteID, String hoverText) {
		Widget Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parent = 5608;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = hoverID;
		Interface.enabledSprite = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.disabledSprite = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.width = 34;
		Interface.height = 34;

		Interface = addTabInterface(hoverID);
		Interface.id = hoverID;
		Interface.parent = 5608;
		Interface.type = 0;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = -1;
		Interface.width = 512;
		Interface.height = 334;
		Interface.invisible = true;
		addBox(hoverID+1, 0, false, 0x000000, hoverText);
		setChildren(1, Interface);
		setBounds(hoverID + 1, 0, 0, 0, Interface);
	}
	
	private static void addOldPrayer(int id, String prayerName) {
		Widget rsi = interfaceCache[id];
		rsi.tooltip = "Activate@or2@ " + prayerName;
	}
	
	public static void addAttackHover(int id, int hoverID, String hoverText, GameFont[] TDA) {
		Widget rsi = interfaceCache[id];
        rsi.hoverType = hoverID;
		
		rsi = addInterface(hoverID);
		rsi.invisible = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.hoverType = -1;
		rsi.parent = hoverID;
        rsi.id = hoverID;
		addBox(hoverID+1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID+1, 0, 0, 0, rsi);
    }
	
	public static void addBox(int id, int byte1, boolean filled, int color, String text){
		Widget Interface = addInterface(id);
	    Interface.id = id;
        Interface.parent = id;
        Interface.type = 9;
        Interface.opacity = (byte)byte1;
		Interface.filled = filled;
		Interface.hoverType = -1;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.textColor = color;
        Interface.defaultText = text;
	}

	public static void addTooltip(int id, int parent, String text) {
		Widget rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parent = parent;
		rsi.type = 8;
		rsi.invisible = true;
		rsi.hoverType = -1;
		rsi.defaultText = text;
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}
	/*public static void addTooltipBox(int id, String text) {
		Widget rsi = addInterface(id);
		rsi.id = id;
		rsi.parent = id;
		rsi.interfaceType = 8;
		rsi.defaultText = text;
	}
	public static void addTooltip(int id, String text) {
		Widget rsi = addInterface(id);
		rsi.id = id;
		rsi.interfaceType = 0;
		rsi.invisible = true;
		rsi.hoverType = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}*/
	
	public static void newTrade(GameFont[] TDA) {
		Widget Interface = addInterface(3323);
		setChildren(19, Interface);
		addSprite(3324, 6, "Interfaces/TradeTab/TRADE");
		addHoverButton(3442, "Interfaces/Bank/BANK", 1, 17, 17, "Close Window", 0, 3325, 3);
		addHovered(3325, 2, "Interfaces/Bank/BANK", 17, 17, 3326);
		addText(3417, "Trading With:", 0xFF981F, true, true, 52,TDA, 2);
		addText(3418, "Trader's Offer", 0xFF981F, false, true, 52,TDA, 1);
		addText(3419, "Your Offer", 0xFF981F, false, true, 52,TDA, 1);
		addText(3421, "Accept", 0x00C000, true, true, 52,TDA, 1);
		addText(3423, "Decline", 0xC00000, true, true, 52,TDA, 1);
		addText(3431, "Waiting For Other Player", 0xFFFFFF, true, true, 52,TDA, 1);
		addText(23504, "", 0xB9B855, true, true, -1,TDA, 0);
		addText(23505, "1 has\\n 28 free\\n inventory slots.", 0xFF981F, true, true, -1,TDA, 0);
		addText(23506, "", 0xB9B855, false, true, -1,TDA, 0);
		addText(23507, "", 0xB9B855, false, true, -1,TDA, 0);
		addHoverButton(3420, "Interfaces/TradeTab/TRADE", 1, 65, 32, "Accept", 0, 3327, 5);
		addHovered(3327, 2, "Interfaces/TradeTab/TRADE", 65, 32, 3328);
		addHoverButton(3422, "Interfaces//TradeTab/TRADE", 0, 65, 32, "Decline", 0, 3329, 5);
		addHovered(3329, 2, "Interfaces/TradeTab/TRADE", 65, 32, 3330);
		setBounds(3324, 0, 16, 0, Interface);
		setBounds(3442, 485, 24, 1, Interface);
		setBounds(3325, 485, 24, 2, Interface);
		setBounds(3417, 258, 25, 3, Interface);
		setBounds(3418, 355, 51, 4, Interface);
		setBounds(3419, 68, 51, 5, Interface);
		setBounds(3420, 223, 120, 6, Interface);
		setBounds(3327, 223, 120, 7, Interface);
		setBounds(3422, 223, 160, 8, Interface);
		setBounds(3329, 223, 160, 9, Interface);
		setBounds(3421, 256, 127, 10, Interface);
		setBounds(3423, 256, 167, 11, Interface);
		setBounds(3431, 256, 272, 12, Interface);
		setBounds(3415, 12, 64, 13, Interface);
		setBounds(3416, 321, 67, 14, Interface);

		setBounds(23505, 256, 67, 16, Interface);

		setBounds(23504, 255, 310, 15, Interface);
		setBounds(23506, 20, 310, 17, Interface);
		setBounds(23507, 380, 310, 18, Interface);

		Interface = addInterface(3443);
		setChildren(15, Interface);
		addSprite(3444, 3, "Interfaces/TradeTab/TRADE");
		addButton(3546, 2, "Interfaces/ShopTab/SHOP", 63, 24, "Accept", 1);
		addButton(3548, 2, "Interfaces/ShopTab/SHOP", 63, 24, "Decline", 1);
		addText(3547, "Accept", 0x00C000, true, true, 52,TDA, 1);
		addText(3549, "Decline", 0xC00000, true, true, 52,TDA, 1);
		addText(3450, "Trading With:", 0x00FFFF, true, true, 52,TDA, 2);
		addText(3451, "Yourself", 0x00FFFF, true, true, 52,TDA, 2);
		setBounds(3444, 12, 20, 0, Interface);
		setBounds(3442, 470, 32, 1, Interface);
		setBounds(3325, 470, 32, 2, Interface);
		setBounds(3535, 130, 28, 3, Interface);
		setBounds(3536, 105, 47, 4, Interface);
		setBounds(3546, 189, 295, 5, Interface);
		setBounds(3548, 258, 295, 6, Interface);
		setBounds(3547, 220, 299, 7, Interface);
		setBounds(3549, 288, 299, 8, Interface);
		setBounds(3557, 71, 87, 9, Interface);
		setBounds(3558, 315, 87, 10, Interface);
		setBounds(3533, 64, 70, 11, Interface);
		setBounds(3534, 297, 70, 12, Interface);
		setBounds(3450, 95, 289, 13, Interface);
		setBounds(3451, 95, 304, 14, Interface);
	}
	
	public static void LodestoneNetwork(GameFont[] tda) {
		Widget tab = addTabInterface(26200);
		addSprite(26201, 0, "Interfaces/Lodestones/Main");
		addHoverButton(26202, "Interfaces/Lodestones/Unlock", 14, 40, 40, "Lunar Isle", 250, 26203, 5);
		addHoveredButton(26203, "Interfaces/Lodestones/UnlockHover", 14, 50, 50, 26204);
		addHoverButton(26205, "Interfaces/Lodestones/Unlock", 0, 40, 40, "Lumbridge", 250, 26206, 5);
		addHoveredButton(26206, "Interfaces/Lodestones/UnlockHover", 0, 50, 50, 26207);
		addHoverButton(26208, "Interfaces/Lodestones/Unlock", 5, 40, 40, "Burthorpe", 250, 26209, 5);
		addHoveredButton(26209, "Interfaces/Lodestones/UnlockHover", 5, 50, 50, 26210);
		addHoverButton(26211, "Interfaces/Lodestones/Unlock", 13, 40, 40, "Bandit Camp", 250, 26212, 5);
		addHoveredButton(26212, "Interfaces/Lodestones/UnlockHover", 13, 50, 50, 26213);
		addHoverButton(26214, "Interfaces/Lodestones/Unlock", 4, 40, 40, "Taverley", 250, 26215, 5);
		addHoveredButton(26215, "Interfaces/Lodestones/UnlockHover", 4, 50, 50, 26216);
		addHoverButton(26217, "Interfaces/Lodestones/Unlock", 12, 40, 40, "Al Kharid", 250, 26218, 5);
		addHoveredButton(26218, "Interfaces/Lodestones/UnlockHover", 12, 50, 50, 26219);
		addHoverButton(26220, "Interfaces/Lodestones/Unlock", 1, 40, 40, "Varrock", 250, 26221, 5);
		addHoveredButton(26221, "Interfaces/Lodestones/UnlockHover", 1, 50, 50, 26222);
		addHoverButton(26223, "Interfaces/Lodestones/Unlock", 11, 40, 40, "Edgeville", 250, 26224, 5);
		addHoveredButton(26224, "Interfaces/Lodestones/UnlockHover", 11, 50, 50, 26225);
		addHoverButton(26226, "Interfaces/Lodestones/Unlock", 2, 40, 40, "Falador", 250, 26227, 5);
		addHoveredButton(26227, "Interfaces/Lodestones/UnlockHover", 2, 50, 50, 26228);
		addHoverButton(26229, "Interfaces/Lodestones/Unlock", 8, 40, 40, "Port Sarim", 250, 26230, 5);
		addHoveredButton(26230, "Interfaces/Lodestones/UnlockHover", 8, 50, 50, 26231);
		addHoverButton(26232, "Interfaces/Lodestones/Unlock", 9, 40, 40, "Draynor Village", 250, 26233, 5);
		addHoveredButton(26233, "Interfaces/Lodestones/UnlockHover", 9, 50, 50, 26234);
		addHoverButton(26235, "Interfaces/Lodestones/Unlock", 3, 40, 40, "Ardougne", 250, 26236, 5);
		addHoveredButton(26236, "Interfaces/Lodestones/UnlockHover", 3, 50, 50, 26237);
		addHoverButton(26238, "Interfaces/Lodestones/Unlock", 6, 40, 40, "Catherby", 250, 26239, 5);
		addHoveredButton(26239, "Interfaces/Lodestones/UnlockHover", 6, 50, 50, 26240);
		addHoverButton(26241, "Interfaces/Lodestones/Unlock", 10, 40, 40, "Yanille", 250, 26242, 5);
		addHoveredButton(26242, "Interfaces/Lodestones/UnlockHover", 10, 50, 50, 26243);
		addHoverButton(26244, "Interfaces/Lodestones/Unlock", 7, 40, 40, "Seers' Village", 250, 26245, 5);
		addHoveredButton(26245, "Interfaces/Lodestones/UnlockHover", 7, 50, 50, 26246);
		addHoverButton(26247, "Interfaces/Lodestones/Close", 0, 26, 26, "Seers' Village", 250, 26248, 3);
		addHoveredButton(26248, "Interfaces/Lodestones/Close", 1, 26, 26, 26249);
		tab.totalChildren(33);
		tab.child(0, 26201, 6, 18);
		tab.child(1, 26202, 30, 53);
		tab.child(2, 26203, 25, 48);
		tab.child(3, 26205, 302, 217);
		tab.child(4, 26206, 297, 212);
		tab.child(5, 26208, 230, 120);
		tab.child(6, 26209, 225, 115);
		tab.child(7, 26211, 300, 270);
		tab.child(8, 26212, 295, 265);
		tab.child(9, 26214, 230, 155);
		tab.child(10, 26215, 225, 150);
		tab.child(11, 26217, 340, 200);
		tab.child(12, 26218, 335, 195);
		tab.child(13, 26220, 322, 162);
		tab.child(14, 26221, 317, 157);
		tab.child(15, 26223, 275, 130);
		tab.child(16, 26224, 270, 125);
		tab.child(17, 26226, 256, 181);
		tab.child(18, 26227, 251, 176);
		tab.child(19, 26229, 260, 235);
		tab.child(20, 26230, 255, 230);
		tab.child(21, 26232, 287, 182);
		tab.child(22, 26233, 282, 177);
		tab.child(23, 26235, 145, 185);
		tab.child(24, 26236, 140, 180);
		tab.child(25, 26238, 200, 157);
		tab.child(26, 26239, 195, 153);
		tab.child(27, 26241, 135, 240);
		tab.child(28, 26242, 130, 235);
		tab.child(29, 26244, 172, 135);
		tab.child(30, 26245, 167, 130);
		tab.child(31, 26247, 480, 21);
		tab.child(32, 26248, 480, 21);
	}
	
	public static void editShopMain(GameFont[] TDA) {
		Widget Interface = addInterface(3824);
		setChildren(8, Interface);
		addSprite(3825, 0, "Interfaces/ShopTab/SHOP");
		addHoverButton(3902, "Interfaces/Bank/BANK", 1, 17, 17, "Close Window", 0, 3826, 3);
		addHovered(3826, 2, "Interfaces/Bank/BANK", 17, 17, 3827);
		addText(19679, "Main Stock", 0xFF981F, false, true, 52,TDA, 1);
		addText(19680, " ", 0xBF751D, false, true, 52,TDA, 1);
		addButton(19681, 1, "Interfaces/ShopTab/SHOP", 95, 19, "", 1);
		setBounds(3825, 12, 12, 0, Interface);
		setBounds(3902, 471, 22, 1, Interface);
		setBounds(3826, 471, 22, 2, Interface);
		setBounds(3900, 60, 85, 3, Interface);
		setBounds(3901, 240, 21, 4, Interface);
		setBounds(19679, 42, 54, 5, Interface);
		setBounds(19680, 150, 54, 6, Interface);
		setBounds(19681, 129, 50, 7, Interface);
		Interface = interfaceCache[3900];
		Interface.spritePaddingX = 8;
		Interface.width = 10;
		Interface.height = 4;
		Interface = addInterface(19682);
		addSprite(19683, 1, "Interfaces/ShopTab/SHOP");
		addText(19684, "Main Stock", 0xBF751D, false, true, 52,TDA, 1);
		addText(19685, "Store Info", 0xFF981F, false, true, 52,TDA, 1);
		addButton(19686, 2, "Interfaces/ShopTab/SHOP", 95, 19, "Main Stock", 1);
		setChildren(7, Interface);
		setBounds(19683, 12, 12, 0, Interface);
		setBounds(3901, 240, 21, 1, Interface);
		setBounds(19684, 42, 54, 2, Interface);
		setBounds(19685, 150, 54, 3, Interface);
		setBounds(19686, 23, 50, 4, Interface);
		setBounds(3902, 471, 22, 5, Interface);
		setBounds(3826, 60, 85, 6, Interface);
	}
	
	public static void addText(int id, String text, GameFont wid[], int idx, int color) {
		Widget rsinterface = addTabInterface(id);
		rsinterface.id = id;
		rsinterface.parent = id;
		rsinterface.interfaceType = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.textCentered = false;
		rsinterface.textShadowed = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.disabledMessage = text;
		rsinterface.enabledMessage = "";
		rsinterface.disabledColor = color;
		rsinterface.enabledColor = 0;
		rsinterface.disabledHoverColor = 0;
		rsinterface.enabledHoverColor = 0;	
	}
	
	public static void drawBlackBox(int xPos, int yPos) {
		Rasterizer2D.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1);
		Rasterizer2D.drawPixels(69, yPos, xPos + 174, 0x726451, 1);
		Rasterizer2D.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178);
		Rasterizer2D.drawPixels(1, yPos + 68, xPos, 0x726451, 174);
		Rasterizer2D.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1);
		Rasterizer2D.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1);
		Rasterizer2D.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175);
		Rasterizer2D.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175);
		Rasterizer2D.drawTransparentBox(0, yPos, 174, 68, 220, xPos);
	}
	
	/*public static void drawBlackBox(int xPos, int yPos) {
        ///Light Coloured Borders\\\
		Rasterizer2D.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1); // Left line
		Rasterizer2D.drawPixels(69, yPos, xPos + 174, 0x726451, 1); // Right line
		Rasterizer2D.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178); // Top Line
		Rasterizer2D.drawPixels(1, yPos + 68, xPos, 0x726451, 174); // Bottom Line

        ///Dark Coloured Borders\\\
		Rasterizer2D.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1); // Left line
		Rasterizer2D.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1); // Right line
		Rasterizer2D.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175); // Top line
		Rasterizer2D.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175); // Top line

        ///Black Box\\\
        Rasterizer2D.drawTransparentBox(0x000000, yPos, 174, 68, 220, xPos); //Yes drawTransparentBox is galkons opacity method
    }*/
	
	public static void addSprite(int i, int j, int k) {
		Widget rsinterface = interfaceCache[i] = new Widget();
		rsinterface.id = i;
		rsinterface.parent = i;
		rsinterface.interfaceType = 5;
		rsinterface.atActionType = 1;
		rsinterface.contentType = 0;
		rsinterface.width = 20;
		rsinterface.height = 20;
		rsinterface.opacity = 0;
		rsinterface.hoverType = 52;
		rsinterface.disabledSprite = imageLoader(j, "Interfaces/Equipment/SPRITE");
		rsinterface.enabledSprite = imageLoader(k, "Interfaces/Equipment/SPRITE");
	}
	
	public static void addButton(int id, int sid, String spriteName, String tooltip) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte)0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(sid, spriteName);
		tab.enabledSprite = imageLoader(sid, spriteName);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight;
		tab.tooltip = tooltip;
	}
	
	public static void prayerTab(GameFont[] tda) {
        Widget tab = addTabInterface(5608);
        Widget currentPray = interfaceCache[687];
        currentPray.textColor = 0xFF981F;
        currentPray.textShadow = true;
        currentPray.defaultText = "%1/%2";
		
		int[] ID1 = {
			18016, 18017, 18018, 18019, 18020, 18021, 18022, 18023, 18024, 18025, 18026, 18027, 18028, 18029, 18030, 18031, 18032, 18033, 18034, 18035, 18036, 18037, 18038, 18039, 18040, 18041
		};
		int[] X = {
			8, 44, 80, 114, 150, 8, 44, 80, 116, 152, 8, 42, 78, 116, 152, 8, 44, 80, 116, 150, 6, 44, 80, 116, 150, 6
		};
		int[] Y = {
			6, 6, 6, 4, 4, 42, 42, 42, 42, 42, 79, 76, 76, 78, 78, 114, 114, 114, 114, 112, 148, 150, 150, 150, 148, 184
		};

		int[] hoverIDs = {
			18050, 18052, 18054, 18056, 18058, 18060, 18062, 18064, 18066, 18068, 18070, 18072, 18074, 18076, 18078, 18080, 18082, 18084, 18086, 18088, 18090, 18092, 18094, 18096, 18098, 18100
		};
		int[] hoverX = {
			12, 8, 20, 12, 24, 2, 2, 6, 6, 50, 6, 6, 10, 6, 6, 5, 5, 5, 5, 5, 18, 28, 28, 50, 1, 1
		};
		int[] hoverY = {
			42, 42, 42, 42, 42, 80, 80, 80, 80, 80, 118, 118, 118, 118, 118, 150, 150, 150, 150, 150, 105, 80, 65, 65, 65, 110
		};	
		String[] hoverStrings = {
			"Level 01\nThick Skin\nIncreases your Defence by 5%", "Level 04\nBurst of Strength\nIncreases your Strength by 5%", "Level 07\nCharity of Thought\nIncreases your Attack by 5%", "Level 08\nSharp Eye\nIncreases your Ranged by 5%", "Level 09\nMystic Will\nIncreases your Magic by 5%",
			"Level 10\nRock Skin\nIncreases your Defence by 10%", "Level 13\nSuperhuman Strength\nIncreases your Strength by 10%", "Level 16\nImproved Reflexes\nIncreases your Attack by 10%", "Level 19\nRapid Restore\n2x restore rate for all stats\nexcept Hitpoints and Prayer", "Level 22\nRapid Heal\n2x restore rate for the\nHitpoints stat",
			"Level 25\nProtect Item\nKeep one extra item if you die", "Level 26\nHawk Eye\nIncreases your Ranged by 10%", "Level 27\nMystic Lore\nIncreases your Magic by 10%", "Level 28\nSteel Skin\nIncreases your Defence by 15%", "Level 31\nUltimate Strength\nIncreases your Strength by 15%",
			"Level 34\nIncredible Reflexes\nIncreases your Attack by 15%", "Level 37\nProtect from Magic\nProtection from magical attacks", "Level 40\nProtect from Missiles\nProtection from ranged attacks", "Level 43\nProtect from Melee\nProtection from close attacks", "Level 44\nEagle Eye\nIncreases your Ranged by 15%",
			"Level 45\nMystic Might\nIncreases your Magic by 15%", "Level 46\nRetribution\nInflicts damage to nearby\ntargets if you die", "Level 49\nRedemption\nHeals you when damaged\nand Hitpoints falls\nbelow 10%", "Level 52\nSmite\n1/4 of damage dealt is\nalso removed from\nopponents Prayer", "Level 60\nChivalry\nIncreases your Defence by 20%,\nStrength by 18% and Attack by\n15%",
			"Level 70\nPiety\nIncreases your Defence by 25%,\nStrength by 23% and Attack by\n20%"
		};
		
		int ID2[] = {
			5609, 5610, 5611, 5612, 5613, 5614, 5615, 5616, 5617, 5618, 5619, 5620, 5621, 5622, 5623, 683, 684, 685, 5632, 5633, 5634, 5635, 5636, 5637, 5638, 5639, 5640, 5641, 5642, 5643, 5644, 686, 5645, 5649, 5647, 5648, 18000, 18001, 18002, 18003, 18004, 18005, 18006, 18007, 18008, 18009, 18010, 18011, 18012, 18013, 18014, 18015, 5651, 687
		};
		int X2[] = {
			6, 42, 78, 6, 42, 78, 114, 150, 6, 114, 150, 6, 42, 78, 114, 42, 78, 114, 8, 44, 80, 8, 44, 80, 116, 152, 8, 116, 152, 8, 44, 80, 116, 44, 80, 116, 114, 117, 150, 153, 42, 45, 78, 81, 150, 153, 6, 9, 150, 157, 6, 8, 65, 14
		};
		int Y2[] = {
			4, 4, 4, 40, 40, 40, 40, 40, 76, 76, 76, 112, 112, 112, 112, 148, 148, 148, 6, 6, 6, 42, 42, 42, 42, 42, 79, 78, 78, 114, 114, 114, 114, 150, 150, 150, 4, 8, 4, 7, 76, 80, 76, 79, 112, 116, 148, 151, 148, 151, 184, 194, 242, 244
		};
		
		String[] oldPrayerNames = {
			"Thick Skin", "Burst of Strength", "Charity of Thought",
			"Rock Skin", "Superhuman Strength", "Improved Reflexes", "Rapid Restore", "Rapid Heal",
			"Protect Item", "Steel Skin", "Ultimate Strength",
			"Incredible Reflexes", "Protect from Magic", "Protect from Missiles", "Protect from Melee",
			"Retribution", "Redemption", "Smite"
		};
		
        addSprite(5651, 0, "tabs/prayer/PRAYER");
        addPrayer(18000, 0, 601, 7, 0, "Sharp Eye");
        addPrayer(18002, 0, 602, 8, 1, "Mystic Will");
        addPrayer(18004, 0, 603, 25, 2, "Hawk Eye");
        addPrayer(18006, 0, 604, 26, 3, "Mystic Lore");
        addPrayer(18008, 0, 605, 43, 4, "Eagle Eye");
        addPrayer(18010, 0, 606, 44, 5, "Mystic Might");
        addPrayer(18012, 0, 607, 59, 6, "Chivalry");
        addPrayer(18014, 0, 608, 69, 7, "Piety");
		
		for(int i = 0; i < 18; i++) {
			addOldPrayer(ID2[i], oldPrayerNames[i]);
		}
		
		for(int i = 0; i < 26; i++) {
			addPrayerHover(ID1[i], hoverIDs[i], i, hoverStrings[i]);
		}
		
        tab.totalChildren(106); //54
        for(int ii = 0; ii < 54; ii++) {
			tab.child(ii, ID2[ii], X2[ii], Y2[ii]);
		}
		
		int frame = 54;	
		int frame2 = 0;
		for(int i : ID1) {
			tab.child(frame, i, X[frame2], Y[frame2]);
			frame++;
			frame2++;
		}
		
		int frame3 = 0;
		for(int i : hoverIDs) {
			tab.child(frame, i, hoverX[frame3], hoverY[frame3]);
			frame++;
			frame3++;
		}
    }
	
	public String popupString;
	
	public static void friendsTab(GameFont[] tda) {
        Widget tab = addTabInterface(5065);
        Widget list = interfaceCache[5066];
        addText(5067, "Friends List", tda, 1, 0xff9933, true, true);
        addText(5070, "Add Friend", tda, 0, 0xff9933, false, true);
        addText(5071, "Delete Friend", tda, 0, 0xff9933, false, true);
        addSprite(16126, 4, "/Friends/SPRITE");
        addSprite(16127, 8, "/Friends/SPRITE");
        addHoverButton(5068, "/Friends/SPRITE", 6, 72, 32, "Add Friend", 201, 5072, 1);
        addHoveredButton(5072, "/Friends/SPRITE", 7, 72, 32, 5073);
        addHoverButton(5069, "/Friends/SPRITE", 6, 72, 32, "Delete Friend", 202, 5074, 1);
        addHoveredButton(5074, "/Friends/SPRITE", 7, 72, 32, 5075);
        tab.totalChildren(11);
        tab.child(0, 5067, 95, 4);
        tab.child(1, 16127, 0, 25);
        tab.child(2, 16126, 0, 221);
        tab.child(3, 5066, 0, 24);
        tab.child(4, 16126, 0, 22);
        tab.child(5, 5068, 15, 226);
        tab.child(6, 5072, 15, 226);
        tab.child(7, 5069, 103, 226);
        tab.child(8, 5074, 103, 226);
        tab.child(9, 5070, 25, 237);
        tab.child(10, 5071, 106, 237);
        list.height = 196; list.width = 174;
        for(int id = 5092, i = 0; id <= 5191 && i <= 99; id++, i++) {
            list.children[i] = id; list.childX[i] = 3; list.childY[i] = list.childY[i] - 7;
        } for(int id = 5192, i = 100; id <= 5291 && i <= 199; id++, i++) {
            list.children[i] = id; list.childX[i] = 131; list.childY[i] = list.childY[i] - 7;
        }
    }

    public static void ignoreTab(GameFont[] tda) {
        Widget tab = addTabInterface(5715);
        Widget list = interfaceCache[5716];
        addText(5717, "Ignore List", tda, 1, 0xff9933, true, true);
        addText(5720, "Add Name", tda, 0, 0xff9933, false, true);
        addText(5721, "Delete Name", tda, 0, 0xff9933, false, true);
        addHoverButton(5718, "/Friends/SPRITE", 6, 72, 32, "Add Name", 501, 5722, 1);
        addHoveredButton(5722, "/Friends/SPRITE", 7, 72, 32, 5723);
        addHoverButton(5719, "/Friends/SPRITE", 6, 72, 32, "Delete Name", 502, 5724, 1);
        addHoveredButton(5724, "/Friends/SPRITE", 7, 72, 32, 5725);
        tab.totalChildren(11);
        tab.child(0, 5717, 95, 4);
        tab.child(1, 16127, 0, 25);
        tab.child(2, 16126, 0, 221);
        tab.child(3, 5716, 0, 24);
        tab.child(4, 16126, 0, 22);
        tab.child(5, 5718, 15, 226);
        tab.child(6, 5722, 15, 226);
        tab.child(7, 5719, 103, 226);
        tab.child(8, 5724, 103, 226);
        tab.child(9, 5720, 27, 237);
        tab.child(10, 5721, 108, 237);
        list.height = 196;
        list.width = 174;
        for(int id = 5742, i = 0; id <= 5841 && i <= 99; id++, i++) {
            list.children[i] = id; list.childX[i] = 3; list.childY[i] = list.childY[i] - 7;
        }
    }

    private static Sprite CustomSpriteLoader(int id, String s)
    {
        long l = (TextClass.method585(s) << 8) + (long)id;
        Sprite sprite = (Sprite) spriteCache.get(l);
        if(sprite != null) { return sprite; }
        try {
            sprite = new Sprite("/Attack/"+id + s);
            spriteCache.put(sprite, l);
        } catch(Exception exception) { 
			return null; }
        return sprite;
    }

    public static Widget addInterface(int id)
    {
        Widget rsi = interfaceCache[id] = new Widget();
        rsi.id = id;
        rsi.parent = id;
        rsi.width = 512;
        rsi.height = 334;
        return rsi;
    }

    public static void addText(int id, String text, GameFont tda[], int idx, int color, boolean centered) {
        Widget rsi = interfaceCache[id] = new Widget();
        if(centered)
          rsi.centerText = true;
        rsi.textShadow = true;
        rsi.textDrawingAreas = tda[idx];
        rsi.defaultText = text;
        rsi.textColor = color;
        rsi.id = id;
        rsi.type = 4;
    }

    public static void textColor(int id, int color)
    { Widget rsi = interfaceCache[id]; rsi.textColor = color; }

    public static void textSize(int id, GameFont tda[], int idx)
    { Widget rsi = interfaceCache[id]; rsi.textDrawingAreas = tda[idx]; }

    public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites)
    {
        Widget rsi = interfaceCache[id] = new Widget();
        rsi.disabledSprite = getSprite(sprite1, aClass44, sprites);
        rsi.enabledSprite = getSprite(sprite2, aClass44, sprites);
        rsi.parent = id;
        rsi.id = id;
        rsi.type = 5;
    }
    
    public static void sprite1(int id, int sprite)
    { Widget class9 = interfaceCache[id];
        class9.disabledSprite = CustomSpriteLoader(sprite, "");
    }

    public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s)
    {
        Widget rsi = interfaceCache[id] = new Widget();
        rsi.disabledSprite = CustomSpriteLoader(sprite, "");
        if (sprite2 == sprite)
          rsi.enabledSprite = CustomSpriteLoader(sprite, "a");
        else
          rsi.enabledSprite = CustomSpriteLoader(sprite2, "");
        rsi.tooltip = s;
        rsi.contentType = 0;
        rsi.atActionType = 1;
        rsi.width = width;
        rsi.hoverType = 52;
        rsi.parent = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
    }

    public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s)
    {
        Widget rsi = addInterface(id);
        rsi.disabledSprite = CustomSpriteLoader(sprite, "");
        rsi.enabledSprite = CustomSpriteLoader(sprite, "a");
        rsi.requiredValues = new int[1];
        rsi.requiredValues[0] = 1;
        rsi.valueCompareType = new int[1];
        rsi.valueCompareType[0] = 1;
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = setconfig;
        rsi.valueIndexArray[0][2] = 0;
        rsi.atActionType = 4;
        rsi.width = width;
        rsi.hoverType = -1;
        rsi.parent = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
        rsi.tooltip = s;
    }

    public void totalChildren(int id, int x, int y)
    { children = new int[id]; childX = new int[x]; childY = new int[y]; }

    public static void removeSomething(int id)
    { Widget rsi = interfaceCache[id] = new Widget(); }
	
	public static void addAttackStyleButton2(int id, int sprite, int setconfig, int width, int height, String s, int hoverID, int hW, int hH, String hoverText, GameFont[] TDA) {
        Widget rsi = addInterface(id);
        rsi.disabledSprite = CustomSpriteLoader(sprite, "");
        rsi.enabledSprite = CustomSpriteLoader(sprite, "a");
        rsi.valueCompareType = new int[1];
        rsi.valueCompareType[0] = 1;
        rsi.requiredValues = new int[1];
        rsi.requiredValues[0] = 1;
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = setconfig;
        rsi.valueIndexArray[0][2] = 0;
        rsi.atActionType = 4;
        rsi.width = width;
        rsi.hoverType = hoverID;
        rsi.parent = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
        rsi.tooltip = s;
		
		rsi = addInterface(hoverID);
		rsi.invisible = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.hoverType = -1;
		rsi.parent = hoverID;
        rsi.id = hoverID;
		addBox(hoverID+1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID+1, 0, 0, 0, rsi);
    }
	
	public static void addAttackText(int id, String text, GameFont tda[], int idx, int color, boolean centered) {
        Widget rsi = interfaceCache[id] = new Widget();
        if(centered)
          rsi.centerText = true;
        rsi.textShadow = true;
        rsi.textDrawingAreas = tda[idx];
        rsi.defaultText = text;
        rsi.textColor = color;
        rsi.id = id;
        rsi.type = 4;
    }
	
	public void specialBar(int id, GameFont[] tda) //7599
    {
		addActionButton(id-12, 7587, -1, 150, 26, "Use @gre@Special Attack");
        for (int i = id-11; i < id; i++)
            removeSomething(i);

        Widget rsi = interfaceCache[id-12];
            rsi.width = 150;
            rsi.height = 26;
			rsi.hoverType = 40005;

        rsi = interfaceCache[id];
            rsi.width = 150;
            rsi.height = 26;

            rsi.child(0, id-12, 0, 0);

            rsi.child(12, id+1, 3, 7);

            rsi.child(23, id+12, 16, 8);

        for (int i = 13; i < 23; i++) {
            rsi.childY[i] -= 1;
        }

        rsi = interfaceCache[id+1];
            rsi.type = 5;
			rsi.disabledSprite = CustomSpriteLoader(7600, "");

        for (int i = id+2; i < id+12; i++) {
        rsi = interfaceCache[i];
            rsi.type = 5;
        }

        sprite1(id+2, 7601);sprite1(id+3, 7602);
        sprite1(id+4, 7603);sprite1(id+5, 7604);
        sprite1(id+6, 7605);sprite1(id+7, 7606);
        sprite1(id+8, 7607);sprite1(id+9, 7608);
        sprite1(id+10, 7609);sprite1(id+11, 7610);
		
		rsi = addInterface(40005);
		rsi.invisible = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.hoverType = -1;
		rsi.parent = 40005;
        rsi.id = 40005;
		addBox(40006, 0, false, 0x000000, "Select to perform a special\nattack.");
		setChildren(1, rsi);
		setBounds(40006, 0, 0, 0, rsi);
    }
	
		public static void monsterTele(GameFont[] TDA) {
	Widget tab = addTabInterface(46300); //Interface ID
	//IndexedImage here
	addSprite(46301, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46302, "Monster Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46303, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Allstar Training", -1, 46304, 1);
	addHoveredButton(46304, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46305);
	addHoverButton(46306, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Slayer Dungeon", -1, 46307, 1);
	addHoveredButton(46307, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46308);
	addHoverButton(46309, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Slayer Tower", -1, 46310, 1);
	addHoveredButton(46310, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46311);
	addHoverButton(46312, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Taveryly Dungeon", -1, 46313, 1);
	addHoveredButton(46313, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46314);
	addText(46315, "Allstar Training", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46316, "Slayer Dungeon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46317, "Slayer Tower", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46318, "Taverly Dungeon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46319, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46320, 1);
	addHoveredButton(46320, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46321);
	addHoverButton(46322, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 46323, 1);
	addHoveredButton(46323, "Interfaces/monsterTele/HoveredArrow", 1, 43, 35, 46324);
	
	tab.totalChildren(18);
	tab.child(0, 46301, 0, 0);
	tab.child(1, 46302, 95, 7);
	tab.child(2, 46303, 30, 36);
	tab.child(3, 46304, 30, 36);
	tab.child(4, 46306, 30, 86);
	tab.child(5, 46307, 30, 86);
	tab.child(6, 46309, 30, 139);
	tab.child(7, 46310, 30, 139);
	tab.child(8, 46312, 30, 188);
	tab.child(9, 46313, 30, 188);
	tab.child(10, 46315, 87, 43);
	tab.child(11, 46316, 87, 93);
	tab.child(12, 46317, 87, 146);
	tab.child(13, 46318, 87, 195);
	tab.child(14, 46319, 0, 221);
	tab.child(15, 46320, 0, 221);
	tab.child(16, 46322, 147, 221);
	tab.child(17, 46323, 147, 221);
	}
	
	public static void monsterTeleb(GameFont[] TDA) {
	Widget tab = addTabInterface(46400); //Interface ID
	//IndexedImage here
	addSprite(46401, 1, "Interfaces/monsterTele/BACKGROUND");
	//Title
	addText(46402, "Monster Teleports", TDA, 2, 0xd67b29, true, true);
	//Tele Buttons
	addHoverButton(46403, "Interfaces/monsterTeleb/HOVER", 1, 124, 26, "Teleport to @yel@Brimhaven Dungeon", -1, 46404, 1);
	addHoveredButton(46404, "Interfaces/monsterTeleb/SHADE", 2, 124, 26, 46405);
	addHoverButton(46406, "Interfaces/monsterTeleb/HOVER", 1, 124, 26, "Teleport to @gre@Coming Soon", -1, 46407, 1);
	addHoveredButton(46407, "Interfaces/monsterTeleb/SHADE", 2, 124, 26, 46408);
	//Tele Button Text
	addText(46409, "Brimhaven Dungeon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46410, "@gre@Coming Soon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	//Bottom Buttons
	addHoverButton(46411, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46412, 1);
	addHoveredButton(46412, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46413);
	addHoverButton(46414, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Next Page", -1, 46415, 1);
	addHoveredButton(46415, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 46416);
	
	tab.totalChildren(12); //Number of sprites/buttons
	tab.child(0, 46401, 0, 0);
	tab.child(1, 46402, 95, 7);
	tab.child(2, 46403, 30, 36);
	tab.child(3, 46404, 30, 36);
	tab.child(4, 46406, 30, 86);
	tab.child(5, 46407, 30, 86);
	tab.child(6, 46409, 90, 43);
	tab.child(7, 46410, 87, 93);
	tab.child(8, 46411, 147, 221);
	tab.child(9, 46412, 147, 221);
	tab.child(10, 46414, 0, 221);
	tab.child(11, 46415, 0, 221);
	}
	
	public static void bossTele(GameFont[] TDA) {
	Widget tab = addTabInterface(46500); //Interface ID
	//IndexedImage here
	addSprite(46501, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46502, "Boss Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46503, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Giant Mole", -1, 46504, 1);
	addHoveredButton(46504, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46505);
	addHoverButton(46506, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Barrelchest", -1, 46507, 1);
	addHoveredButton(46507, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46508);
	addHoverButton(46509, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Kalphite Queen", -1, 46510, 1);
	addHoveredButton(46510, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46511);
	addHoverButton(46512, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Godwars Dungeon", -1, 46513, 1);
	addHoveredButton(46513, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46514);
	addText(46515, "Giant Mole", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46516, "Barrelchest" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46517, "Kalphite Queen", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46518, "Godwars Dungeon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46519, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46520, 1);
	addHoveredButton(46520, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46521);
	addHoverButton(46522, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 46523, 1);
	addHoveredButton(46523, "Interfaces/monsterTele/HoveredArrow", 1, 43, 35, 46524);
	
	tab.totalChildren(18);
	tab.child(0, 46501, 0, 0);
	tab.child(1, 46502, 95, 7);
	tab.child(2, 46503, 30, 36);
	tab.child(3, 46504, 30, 36);
	tab.child(4, 46506, 30, 86);
	tab.child(5, 46507, 30, 86);
	tab.child(6, 46509, 30, 139);
	tab.child(7, 46510, 30, 139);
	tab.child(8, 46512, 30, 188);
	tab.child(9, 46513, 30, 188);
	tab.child(10, 46515, 87, 43);
	tab.child(11, 46516, 87, 93);
	tab.child(12, 46517, 87, 146);
	tab.child(13, 46518, 87, 195);
	tab.child(14, 46519, 0, 221);
	tab.child(15, 46520, 0, 221);
	tab.child(16, 46522, 147, 221);
	tab.child(17, 46523, 147, 221);
	}
	
	public static void bossTeleB(GameFont[] TDA) {
	Widget tab = addTabInterface(46600); //Interface ID
	//IndexedImage here
	addSprite(46601, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46602, "Boss Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46603, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Dagganoth Kings", -1, 46604, 1);
	addHoveredButton(46604, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46605);
	addHoverButton(46606, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Penance Queen", -1, 46607, 1);
	addHoveredButton(46607, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46608);
	addHoverButton(46609, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Chaos Elemental", -1, 46610, 1);
	addHoveredButton(46610, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46611);
	addHoverButton(46612, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@King Black Dragon", -1, 46613, 1);
	addHoveredButton(46613, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46614);
	addText(46615, "Dagganoth Kings", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46616, "Penance Queen" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46617, "@red@Chaos Elemental", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46618, "@red@King Black Dragon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46619, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 46620, 1);
	addHoveredButton(46620, "Interfaces/monsterTele/hoveredArrow", 1, 43, 35, 46621);
	addHoverButton(46622, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Previous Page", -1, 46623, 1);
	addHoveredButton(46623, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 46624);
	addHoverButton(46625, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46626, 1);
	addHoveredButton(46626, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46627);
	tab.totalChildren(20);
	tab.child(0, 46601, 0, 0);
	tab.child(1, 46602, 95, 7);
	tab.child(2, 46603, 30, 36);
	tab.child(3, 46604, 30, 36);
	tab.child(4, 46606, 30, 86);
	tab.child(5, 46607, 30, 86);
	tab.child(6, 46609, 30, 139);
	tab.child(7, 46610, 30, 139);
	tab.child(8, 46612, 30, 188);
	tab.child(9, 46613, 30, 188);
	tab.child(10, 46615, 87, 43);
	tab.child(11, 46616, 87, 93);
	tab.child(12, 46617, 87, 146);
	tab.child(13, 46618, 87, 195);
	tab.child(14, 46619, 147, 221);
	tab.child(15, 46620, 147, 221);
	tab.child(16, 46622, 0, 221);
	tab.child(17, 46623, 0, 221);
	tab.child(18, 46625, 73, 221);
	tab.child(19, 46626, 73, 221);
	}
	
	public static void bossTeleC(GameFont[] TDA) {
	Widget tab = addTabInterface(46700); //Interface ID
	//IndexedImage here
	addSprite(46701, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46702, "Boss Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46703, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Scorpia", -1, 46704, 1);
	addHoveredButton(46704, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46705);
	addHoverButton(46706, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Venenatis", -1, 46707, 1);
	addHoveredButton(46707, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46708);
	addHoverButton(46709, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Vet'ion", -1, 46710, 1);
	addHoveredButton(46710, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46711);
	addHoverButton(46712, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Callisto", -1, 46713, 1);
	addHoveredButton(46713, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46714);
	addText(46715, "@red@Scorpia", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46716, "@red@Venenatis" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46717, "@red@Vet'ion", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46718, "@red@Callisto", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46719, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 46720, 1);
	addHoveredButton(46720, "Interfaces/monsterTele/hoveredArrow", 1, 43, 35, 46721);
	addHoverButton(46722, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Previous Page", -1, 46723, 1);
	addHoveredButton(46723, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 46724);
	addHoverButton(46725, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46726, 1);
	addHoveredButton(46726, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46727);
	tab.totalChildren(22);
	tab.child(0, 46701, 0, 0);
	tab.child(1, 46702, 95, 7);
	tab.child(2, 46703, 30, 36);
	tab.child(3, 46704, 30, 36);
	tab.child(4, 46706, 30, 86);
	tab.child(5, 46707, 30, 86);
	tab.child(6, 46709, 30, 139);
	tab.child(7, 46710, 30, 139);
	tab.child(8, 46712, 30, 188);
	tab.child(9, 46713, 30, 188);
	tab.child(10, 46715, 87, 43);
	tab.child(11, 46716, 87, 93);
	tab.child(12, 46717, 87, 146);
	tab.child(13, 46718, 87, 195);
	tab.child(14, 46719, 147, 221);
	tab.child(15, 46720, 147, 221);
	tab.child(16, 46722, 0, 221);
	tab.child(17, 46723, 0, 221);
	tab.child(18, 46724, 73, 221);
	tab.child(19, 46725, 73, 221);
	tab.child(20, 46726, 73, 221);
	tab.child(21, 46627, 73, 221);
	
	
	}
	public static void bossTeleD(GameFont[] TDA) {
		Widget tab = addTabInterface(47600); //Interface ID
		//IndexedImage here
		addSprite(47601, 1, "Interfaces/monsterTele/BACKGROUND");
		addText(47602, "Boss Teleports", TDA, 2, 0xd67b29, true, true);
		addHoverButton(47603, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Skotizo", -1, 47604, 1);
		addHoveredButton(47604, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47605);
		addHoverButton(47606, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Kraken", -1, 47607, 1);
		addHoveredButton(47607, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47608);
		addHoverButton(47609, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Coming soon", -1, 47610, 1);
		addHoveredButton(47610, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47611);
		addHoverButton(47612, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Coming soon", -1, 47613, 1);
		addHoveredButton(47613, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47614);
		addText(47615, "Skotizo", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47616, "Kraken" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47617, "Coming soon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47618, "Coming soon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addHoverButton(47619, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Previous Page", -1, 47620, 1);
		addHoveredButton(47620, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 47621);
		addHoverButton(47622, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 47623, 1);
		addHoveredButton(47623, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 47624);
		tab.totalChildren(18);
		tab.child(0, 47601, 0, 0);
		tab.child(1, 47602, 95, 7);
		tab.child(2, 47603, 30, 36);
		tab.child(3, 47604, 30, 36);
		tab.child(4, 47606, 30, 86);
		tab.child(5, 47607, 30, 86);
		tab.child(6, 47609, 30, 139);
		tab.child(7, 47610, 30, 139);
		tab.child(8, 47612, 30, 188);
		tab.child(9, 47613, 30, 188);
		tab.child(10, 47615, 87, 43);
		tab.child(11, 47616, 87, 93);
		tab.child(12, 47617, 87, 146);
		tab.child(13, 47618, 87, 195);
		tab.child(14, 47619, 0, 221);
		tab.child(15, 47620, 0, 221);
		tab.child(16, 47622, 147, 221);
		tab.child(17, 47623, 147, 221);
		}
	
	public static void SkillRewards(GameFont[] paramArrayOfTextDrawingArea) {
    Widget localWidget = addTabInterface(35000);

    setChildren(55, localWidget);
    addSprite(35001, 0, "Interfaces/SkillRewards/BACKGROUND");
    addHover(35002, 3, 0, 35003, 0, "Interfaces/SkillRewards/EXIT", 21, 21, "Exit");
    addHovered(35003, 1, "Interfaces/SkillRewards/EXIT", 21, 21, 35004);
    addSprite(35005, 1, "Interfaces/SkillRewards/BANNER");
    addText(35006, "", paramArrayOfTextDrawingArea, 1, 14929103, true, true);

    addHoverButton(35007, "", 0, 46, 44, "Choose Attack", -1, 35008, 1);
    addHoveredButton(35008, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35009);
    addHoverButton(35010, "", 0, 46, 44, "Choose Magic", -1, 35011, 1);
    addHoveredButton(35011, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35012);
    addHoverButton(35013, "", 0, 46, 44, "Choose Mining", -1, 35014, 1);
    addHoveredButton(35014, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35015);
    addHoverButton(35016, "", 0, 46, 44, "Choose Woodcutting", -1, 35017, 1);
    addHoveredButton(35017, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35018);
    addHoverButton(35019, "", 0, 46, 44, "Choose Agility", -1, 35020, 1);
    addHoveredButton(35020, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35021);
    addHoverButton(35022, "", 0, 46, 44, "Choose Fletching", -1, 35023, 1);
    addHoveredButton(35023, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35024);
    addHoverButton(35025, "", 0, 46, 44, "Choose Thieving", -1, 35026, 1);
    addHoveredButton(35026, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35027);

    addHoverButton(35028, "", 0, 46, 44, "Choose Strength", -1, 35029, 1);
    addHoveredButton(35029, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35030);
    addHoverButton(35031, "", 0, 46, 44, "Choose Ranged", -1, 35032, 1);
    addHoveredButton(35032, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35033);
    addHoverButton(35034, "", 0, 46, 44, "Choose Smithing", -1, 35035, 1);
    addHoveredButton(35035, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35036);
    addHoverButton(35037, "", 0, 46, 44, "Choose Firemaking", -1, 35038, 1);
    addHoveredButton(35038, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35039);
    addHoverButton(35040, "", 0, 46, 44, "Choose Herblore", -1, 35041, 1);
    addHoveredButton(35041, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35042);
    addHoverButton(35043, "", 0, 46, 44, "Choose Slayer", -1, 35044, 1);
    addHoveredButton(35044, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35045);
    addHoverButton(35046, "", 0, 46, 44, "Choose Construction", -1, 35047, 1);
    addHoveredButton(35047, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35048);

    addHoverButton(35049, "", 0, 46, 44, "Choose Defence", -1, 35050, 1);
    addHoveredButton(35050, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35051);
    addHoverButton(35052, "", 0, 46, 44, "Choose Prayer", -1, 35053, 1);
    addHoveredButton(35053, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35054);
    addHoverButton(35055, "", 0, 46, 44, "Choose Fishing", -1, 35056, 1);
    addHoveredButton(35056, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35057);
    addHoverButton(35058, "", 0, 46, 44, "Choose Crafting", -1, 35059, 1);
    addHoveredButton(35059, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35060);
    addHoverButton(35061, "", 0, 46, 44, "Choose Farming", -1, 35062, 1);
    addHoveredButton(35062, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35063);
    addHoverButton(35064, "", 0, 46, 44, "Choose Hunter", -1, 35065, 1);
    addHoveredButton(35065, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35066);
    addHoverButton(35067, "", 0, 46, 44, "Choose Cooking", -1, 35068, 1);
    addHoveredButton(35068, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35069);

    addHoverButton(35070, "", 0, 46, 44, "Choose Constitution", -1, 35071, 1);
    addHoveredButton(35071, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35072);
    addHoverButton(35073, "", 0, 46, 44, "Choose Runecrafting", -1, 35074, 1);
    addHoveredButton(35074, "Interfaces/SkillRewards/CIRCLE", 1, 46, 44, 35075);
    addHoverButton(35076, "", 0, 46, 44, "", -1, 35077, 1);
    addHoveredButton(35077, "Interfaces/SkillRewards/CIRCLE", 2, 46, 44, 35078);
    addHoverButton(35079, "", 0, 46, 44, "", -1, 35080, 1);
    addHoveredButton(35080, "Interfaces/SkillRewards/CIRCLE", 2, 46, 44, 35081);
/*
    addHover(35082, 3, 0, 35083, 0, "Interfaces/SkillRewards/CANCEL", 127, 21, "");
    addHoveredButton(35083, 1, "Interfaces/SkillRewards/CANCEL", 127, 21, 35084);

	addHoverButton(35082, "Interfaces/SkillRewards/CANCEL", 0, 127, 21, "Cancel", -1, 35083, 1);
    addHoveredButton(35083, "Interfaces/SkillRewards/CANCEL", 1, 127, 21, 35084);
    addHoverButton(35085, "Interfaces/SkillRewards/CONFIRM", 0, 127, 21, "Confirm", -1, 35086, 1);
    addHoveredButton(35086, "Interfaces/SkillRewards/CONFIRM", 1, 127, 21, 35087);
    addText(35088, "Confirm", paramArrayOfTextDrawingArea, 1, 14929103, false, true);
    addText(35089, "Not right now", paramArrayOfTextDrawingArea, 1, 14929103, false, true);
*/
    setBounds(35001, 10, 14, 0, localWidget);
    setBounds(35002, 470, 20, 1, localWidget);
    setBounds(35003, 470, 20, 2, localWidget);
    setBounds(35005, 181, 48, 3, localWidget);
    setBounds(35006, 255, 52, 4, localWidget);

    setBounds(35007, 37, 80, 5, localWidget);
    setBounds(35008, 37, 80, 6, localWidget);
    setBounds(35010, 102, 80, 7, localWidget);
    setBounds(35011, 102, 80, 8, localWidget);
    setBounds(35013, 167, 80, 9, localWidget);
    setBounds(35014, 167, 80, 10, localWidget);
    setBounds(35016, 232, 80, 11, localWidget);
    setBounds(35017, 232, 80, 12, localWidget);
    setBounds(35019, 297, 80, 13, localWidget);
    setBounds(35020, 297, 80, 14, localWidget);
    setBounds(35022, 362, 80, 15, localWidget);
    setBounds(35023, 362, 80, 16, localWidget);
    setBounds(35025, 427, 80, 17, localWidget);
    setBounds(35026, 427, 80, 18, localWidget);

    setBounds(35028, 37, 138, 19, localWidget);
    setBounds(35029, 37, 138, 20, localWidget);
    setBounds(35031, 102, 138, 21, localWidget);
    setBounds(35032, 102, 138, 22, localWidget);
    setBounds(35034, 167, 138, 23, localWidget);
    setBounds(35035, 167, 138, 24, localWidget);
    setBounds(35037, 232, 138, 25, localWidget);
    setBounds(35038, 232, 138, 26, localWidget);
    setBounds(35040, 297, 138, 27, localWidget);
    setBounds(35041, 297, 138, 28, localWidget);
    setBounds(35043, 362, 138, 29, localWidget);
    setBounds(35044, 362, 138, 30, localWidget);
    setBounds(35046, 427, 138, 31, localWidget);
    setBounds(35047, 427, 138, 32, localWidget);

    setBounds(35049, 37, 196, 33, localWidget);
    setBounds(35050, 37, 196, 34, localWidget);
    setBounds(35052, 102, 196, 35, localWidget);
    setBounds(35053, 102, 196, 36, localWidget);
    setBounds(35055, 167, 196, 37, localWidget);
    setBounds(35056, 167, 196, 38, localWidget);
    setBounds(35058, 232, 196, 39, localWidget);
    setBounds(35059, 232, 196, 40, localWidget);
    setBounds(35061, 297, 196, 41, localWidget);
    setBounds(35062, 297, 196, 42, localWidget);
    setBounds(35064, 362, 196, 43, localWidget);
    setBounds(35065, 362, 196, 44, localWidget);
    setBounds(35067, 427, 196, 45, localWidget);
    setBounds(35068, 427, 196, 46, localWidget);

    setBounds(35070, 37, 254, 47, localWidget);
    setBounds(35071, 37, 254, 48, localWidget);
    setBounds(35073, 102, 254, 49, localWidget);
    setBounds(35074, 102, 254, 50, localWidget);
    setBounds(35076, 167, 254, 51, localWidget);
    setBounds(35077, 167, 254, 52, localWidget);
    setBounds(35079, 232, 254, 53, localWidget);
    setBounds(35080, 232, 254, 54, localWidget);
/*
    setBounds(35082, 322, 280, 55, localWidget);
    setBounds(35083, 322, 280, 56, localWidget);
    setBounds(35085, 322, 250, 57, localWidget);
    setBounds(35086, 322, 250, 58, localWidget);
    setBounds(35088, 360, 253, 59, localWidget);
    setBounds(35089, 350, 283, 60, localWidget);
  */
	}
	
	public static void pkTele(GameFont[] TDA) {
	Widget tab = addTabInterface(46800); //Interface ID
	//IndexedImage here
	addSprite(46801, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46802, "Pking Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46803, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Edgeville", -1, 46804, 1);
	addHoveredButton(46804, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46805);
	addHoverButton(46806, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Mage Bank", -1, 46807, 1);
	addHoveredButton(46807, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46808);
	addHoverButton(46809, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Varrock Pk", -1, 46810, 1);
	addHoveredButton(46810, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46811);
	addHoverButton(46812, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@West Dragons", -1, 46813, 1);
	addHoveredButton(46813, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46814);
	addText(46815, "Edgeville Pk", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46816, "Mage Bank" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46817, "Varrock Multi", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46818, "@red@Dragons Multi", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46819, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46820, 1);
	addHoveredButton(46820, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46821);
	addHoverButton(46822, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 46823, 1);
	addHoveredButton(46823, "Interfaces/monsterTele/HoveredArrow", 1, 43, 35, 46824);
	
	tab.totalChildren(18);
	tab.child(0, 46801, 0, 0);
	tab.child(1, 46802, 95, 7);
	tab.child(2, 46803, 30, 36);
	tab.child(3, 46804, 30, 36);
	tab.child(4, 46806, 30, 86);
	tab.child(5, 46807, 30, 86);
	tab.child(6, 46809, 30, 139);
	tab.child(7, 46810, 30, 139);
	tab.child(8, 46812, 30, 188);
	tab.child(9, 46813, 30, 188);
	tab.child(10, 46815, 87, 43);
	tab.child(11, 46816, 87, 93);
	tab.child(12, 46817, 87, 146);
	tab.child(13, 46818, 87, 195);
	tab.child(14, 46819, 0, 221);
	tab.child(15, 46820, 0, 221);
	tab.child(16, 46822, 147, 221);
	tab.child(17, 46823, 147, 221);
	}
	
	public static void pkTeleB(GameFont[] TDA) {
	Widget tab = addTabInterface(46900); //Interface ID
	//IndexedImage here
	addSprite(46901, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(46902, "Pking Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(46903, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @red@Bandit Camp", -1, 46904, 1);
	addHoveredButton(46904, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46905);
	addHoverButton(46906, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @gre@Coming Soon", -1, 46907, 1);
	addHoveredButton(46907, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46908);
	addHoverButton(46909, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @gre@Coming Soon", -1, 46910, 1);
	addHoveredButton(46910, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46911);
	addHoverButton(46912, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @gre@Coming Soon", -1, 46913, 1);
	addHoveredButton(46913, "Interfaces/monsterTele/SHADE", 2, 124, 26, 46914);
	addText(46915, "@red@Bandit Camp Multi", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46916, "@gre@Coming Soon" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46917, "@gre@Coming Soon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(46918, "@gre@Coming Soon", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(46919, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 46920, 1);
	addHoveredButton(46920, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 46921);
	addHoverButton(46922, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Next Page", -1, 46923, 1);
	addHoveredButton(46923, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 46924);
	
	tab.totalChildren(18);
	tab.child(0, 46901, 0, 0);
	tab.child(1, 46902, 95, 7);
	tab.child(2, 46903, 30, 36);
	tab.child(3, 46904, 30, 36);
	tab.child(4, 46906, 30, 86);
	tab.child(5, 46907, 30, 86);
	tab.child(6, 46909, 30, 139);
	tab.child(7, 46910, 30, 139);
	tab.child(8, 46912, 30, 188);
	tab.child(9, 46913, 30, 188);
	tab.child(10, 46915, 87, 43);
	tab.child(11, 46916, 87, 93);
	tab.child(12, 46917, 87, 146);
	tab.child(13, 46918, 87, 195);
	tab.child(14, 46919, 147, 221);
	tab.child(15, 46920, 147, 221);
	tab.child(16, 46922, 0, 221);
	tab.child(17, 46923, 0, 221);
	}
	public static void skillingtele(GameFont[] TDA) {
		Widget tab = addTabInterface(47400); //Interface ID
		//IndexedImage here
		addSprite(47401, 1, "Interfaces/monsterTele/BACKGROUND");
		addText(47402, "Skilling Teleports", TDA, 2, 0xd67b29, true, true);
		addHoverButton(47403, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Catherby fishing", -1, 47404, 1);
		addHoveredButton(47404, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47405);
		addHoverButton(47406, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Woodcutting guild", -1, 47407, 1);
		addHoveredButton(47407, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47408);
		addHoverButton(47409, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Thieving guild", -1, 47410, 1);
		addHoveredButton(47410, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47411);
		addHoverButton(47412, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Skilling area", -1, 47413, 1);
		addHoveredButton(47413, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47414);
		addText(47415, "Catherby fishing", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47416, "Woodcutting guild" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47417, "Thieving guild", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addText(47418, "Skilling area", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
		addHoverButton(47419, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 47420, 1);
		addHoveredButton(47420, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 47421);
		addHoverButton(47422, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 47423, 1);
		addHoveredButton(47423, "Interfaces/monsterTele/HoveredArrow", 1, 43, 35, 47424);
		
		tab.totalChildren(18);
		tab.child(0, 47401, 0, 0);
		tab.child(1, 47402, 95, 7);
		tab.child(2, 47403, 30, 36);
		tab.child(3, 47404, 30, 36);
		tab.child(4, 47406, 30, 86);
		tab.child(5, 47407, 30, 86);
		tab.child(6, 47409, 30, 139);
		tab.child(7, 47410, 30, 139);
		tab.child(8, 47412, 30, 188);
		tab.child(9, 47413, 30, 188);
		tab.child(10, 47415, 87, 43);
		tab.child(11, 47416, 87, 93);
		tab.child(12, 47417, 87, 146);
		tab.child(13, 47418, 87, 195);
		tab.child(14, 47419, 0, 221);
		tab.child(15, 47420, 0, 221);
		tab.child(16, 47422, 147, 221);
		tab.child(17, 47423, 147, 221);
		}
	public static void minigameTele(GameFont[] TDA) {
	Widget tab = addTabInterface(47000); //Interface ID
	//IndexedImage here
	addSprite(47001, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(47002, "Minigame Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(47003, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Castle Wars", -1, 47004, 1);
	addHoveredButton(47004, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47005);
	addHoverButton(47006, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Duel Arena", -1, 47007, 1);
	addHoveredButton(47007, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47008);
	addHoverButton(47009, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Pest Control", -1, 47010, 1);
	addHoveredButton(47010, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47011);
	addHoverButton(47012, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Barrows", -1, 47013, 1);
	addHoveredButton(47013, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47014);
	addText(47015, "Castle Wars", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47016, "Duel Arena" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47017, "Pest Control", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47018, "Barrows", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(47019, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 47020, 1);
	addHoveredButton(47020, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 47021);
	addHoverButton(47022, "Interfaces/monsterTele/shadedArrow", 1, 43, 35, "Next Page", -1, 47023, 1);
	addHoveredButton(47023, "Interfaces/monsterTele/HoveredArrow", 1, 43, 35, 47024);
	
	tab.totalChildren(18);
	tab.child(0, 47001, 0, 0);
	tab.child(1, 47002, 95, 7);
	tab.child(2, 47003, 30, 36);
	tab.child(3, 47004, 30, 36);
	tab.child(4, 47006, 30, 86);
	tab.child(5, 47007, 30, 86);
	tab.child(6, 47009, 30, 139);
	tab.child(7, 47010, 30, 139);
	tab.child(8, 47012, 30, 188);
	tab.child(9, 47013, 30, 188);
	tab.child(10, 47015, 87, 43);
	tab.child(11, 47016, 87, 93);
	tab.child(12, 47017, 87, 146);
	tab.child(13, 47018, 87, 195);
	tab.child(14, 47019, 0, 221);
	tab.child(15, 47020, 0, 221);
	tab.child(16, 47022, 147, 221);
	tab.child(17, 47023, 147, 221);
	}
	
	public static void minigameTeleB(GameFont[] TDA) {
	Widget tab = addTabInterface(47100); //Interface ID
	//IndexedImage here
	addSprite(47101, 1, "Interfaces/monsterTele/BACKGROUND");
	addText(47102, "Minigame Teleports", TDA, 2, 0xd67b29, true, true);
	addHoverButton(47103, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@TzHaar City", -1, 47104, 1);
	addHoveredButton(47104, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47105);
	addHoverButton(47106, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Barbarian Assault", -1, 47107, 1);
	addHoveredButton(47107, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47108);
	addHoverButton(47109, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Warrior's Guilld", -1, 47110, 1);
	addHoveredButton(47110, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47111);
	addHoverButton(47112, "Interfaces/monsterTele/HOVER", 1, 124, 26, "Teleport to @yel@Nightmare Zone", -1, 47113, 1);
	addHoveredButton(47113, "Interfaces/monsterTele/SHADE", 2, 124, 26, 47114);
	addText(47115, "TzHaar City", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47116, "Barbarian Assault" , TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47117, "Warrior's Guild", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addText(47118, "Nightmare Zone", TDA, 0, 0xFFFFFF, true, true); //0xd67b29
	addHoverButton(47119, "Interfaces/monsterTele/shadedMageBook", 1, 43, 35, "Back To Your Spell Book", -1, 47120, 1);
	addHoveredButton(47120, "Interfaces/monsterTele/hoveredMageBook", 1, 43, 35, 47121);
	addHoverButton(47122, "Interfaces/monsterTele/shadedArrow", 2, 43, 35, "Next Page", -1, 47123, 1);
	addHoveredButton(47123, "Interfaces/monsterTele/HoveredArrow", 2, 43, 35, 47124);
	
	tab.totalChildren(18);
	tab.child(0, 47101, 0, 0);
	tab.child(1, 47102, 95, 7);
	tab.child(2, 47103, 30, 36);
	tab.child(3, 47104, 30, 36);
	tab.child(4, 47106, 30, 86);
	tab.child(5, 47107, 30, 86);
	tab.child(6, 47109, 30, 139);
	tab.child(7, 47110, 30, 139);
	tab.child(8, 47112, 30, 188);
	tab.child(9, 47113, 30, 188);
	tab.child(10, 47115, 87, 43);
	tab.child(11, 47116, 87, 93);
	tab.child(12, 47117, 87, 146);
	tab.child(13, 47118, 87, 195);
	tab.child(14, 47119, 147, 221);
	tab.child(15, 47120, 147, 221);
	tab.child(16, 47122, 0, 221);
	tab.child(17, 47123, 0, 221);
	}
	/*
	public static void slayerTasks(GameFont[] TDA) {
		Widget Interface = addTabInterface(46202)
			addHoverButton(46207, "Interfaces/SlayerTasks/BANK", 17, 135, 27, "Deposit Inventory", -1, 46208, 5);
		addHoveredButton(46208, "Interfaces/SlayerTasks/BANK", 18, 35, 25, 46209);
		addHoverButton(46207, "Interfaces/SlayerTasks/BANK", 17, 135, 27, "Deposit Inventory", -1, 46208, 5);
		addHoveredButton(46208, "Interfaces/SlayerTasks/BANK", 18, 35, 25, 46209);
		addHoverButton(46207, "Interfaces/SlayerTasks/BANK", 17, 135, 27, "Deposit Inventory", -1, 46208, 5);
		addHoveredButton(46208, "Interfaces/SlayerTasks/BANK", 18, 35, 25, 46209);
		addHoverButton(46207, "Interfaces/SlayerTasks/BANK", 17, 135, 27, "Deposit Inventory", -1, 46208, 5);
		addHoveredButton(46208, "Interfaces/SlayerTasks/BANK", 18, 35, 25, 46209);
		Tab.children = new int[5;
		Tab.childX = new int[5];
		Tab.childY = new int[5];
		Tab.children[0] = 5292;
		Tab.childX[0] = 0;
		Tab.childY[0] = 0;
		Tab.children[1] = 46203;
		Tab.childX[1] = 410;
		Tab.childY[1] = 30;
		Tab.children[2] = 46204;
		Tab.childX[2] = 472;
		Tab.childY[2] = 29;
		Tab.children[3] = 46205;
		Tab.childX[3] = 472;
		Tab.childY[3] = 29;
		Tab.children[3] = 46205;
		Tab.childX[3] = 472;
		Tab.childY[3] = 29;
		}
		*/
		
		public static void editClan(GameFont[] tda) {
		Widget tab = addTabInterface(17250);
		addSprite(17251, 1, "Interfaces/Clan Chat/CLAN");
		addHoverButton(17252, "Interfaces/Clan Chat/BUTTON", 1, 150, 50, "Set name", -1, 17253, 1);
		addHoveredButton(17253, "Interfaces/Clan Chat/BUTTON", 2, 150, 50, 17254);
		addHoverButton(17255, "Interfaces/Clan Chat/BUTTON", 1, 150, 50, "Anyone", -1, 17256, 1);
		addHoveredButton(17256, "Interfaces/Clan Chat/BUTTON", 2, 150, 50, 17257);
		addHoverButton(17258, "Interfaces/Clan Chat/BUTTON", 1, 150, 50, "Anyone", -1, 17259, 1);
		addHoveredButton(17259, "Interfaces/Clan Chat/BUTTON", 2, 150, 50, 17260);
		addHoverButton(17261, "Interfaces/Clan Chat/BUTTON", 1, 150, 50, "Corporal+", -1, 17262, 1);
		addHoveredButton(17262, "Interfaces/Clan Chat/BUTTON", 2, 150, 50, 17263);
		addHoverButton(17264, "Interfaces/Clan Chat/BUTTON", 1, 150, 50, "No-one", -1, 17265, 1);
		addHoveredButton(17265, "Interfaces/Clan Chat/BUTTON", 2, 150, 50, 17266);
		addHoverButton(17267, "Interfaces/Clan Chat/CLOSE", 3, 50, 50, "Close", -1, 17268, 1);
		addHoveredButton(17268, "Interfaces/Clan Chat/CLOSE", 4, 40, 50, 17269);
     	addText(17800, "Clan name:", tda, 0, 0xff981f, false, true);
     	addText(17801, "Who can enter chat?", tda, 0, 0xff981f, false, true);
		addText(17812, "Who can talk on chat?", tda, 0, 0xff981f, false, true);
		addText(17813, "Who can kick on chat?", tda, 0, 0xff981f, false, true);
		addText(17814, "Who can share loot?", tda, 0, 0xff981f, false, true);
		tab.totalChildren(19);
		tab.child(0, 17251, 15, 15);
		tab.child(1, 17252, 25, 47);
		tab.child(2, 17253, 25, 47);
		tab.child(3, 17255, 25, 87);
		tab.child(4, 17256, 25, 87);
		tab.child(5, 17258, 25, 128);
		tab.child(6, 17259, 25, 128);
		tab.child(7, 17261, 25, 168);
		tab.child(8, 17262, 25, 168);
		tab.child(9, 17264, 25, 209);
		tab.child(10, 17265, 25, 209);
		tab.child(11, 17800, 73, 54);
		tab.child(12, 17801, 53, 95);
		tab.child(13, 14000, 0, 94);
		tab.child(14, 17812, 53, 136);
		tab.child(15, 17813, 53, 177);
		tab.child(16, 17814, 53, 218);
		tab.child(17, 17267, 476, 23);
		tab.child(18, 17268, 476, 23);
		tab = addTabInterface(14000);
		tab.width = 474;
		tab.height = 213;
		tab.scrollMax = 305;
		for(int i = 14001; i <= 14030; i++){
		addText(i, "", tda, 1, 0xffffff, false, true);
		}
		tab.totalChildren(30);
		int Child = 0;
		int Y = 5;
		for(int i = 14001; i <= 14030; i++){
		tab.child(Child, i, 248, Y);
		Child++;
		Y += 13;
		}
	}

		public static void achievementTab(GameFont[] tda) {
        Widget tab = addTabInterface(47201);
        Widget scroll = addTabInterface(47206);
        addText(47202, "Pking Achievements", tda, 2, 0xEB981F, false, true);
        addSprite(47203, 0, "ACH");
        addSprite(47204, 1, "ACH");
        addSprite(47205, 0, "ACH");
        tab.totalChildren(5);
        tab.child(0, 47202, 5, 5);
        tab.child(1, 47203, 0, 22);
        tab.child(2, 47204, 0, 25);
        tab.child(3, 47205, 0, 249);
        tab.child(4, 47206, 0, 25);
        scroll.width = 174; scroll.height = 224; scroll.scrollMax = 496;
        
        /*Tier I.*/
        addText(47207, "Tier I.", tda, 1, 0xFF9900, false, true);
        addClickableText(47208, "Beginner", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47209, "Intermediate", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47210, "Expert", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47211, "Master", "Read Log", tda, 0, 0xff0000, 90, 11);
        
		addText(47212, "Tier II.", tda, 1, 0xFF9900, false, true);
        /*Tier II.*/
        addClickableText(47213, "Beginner", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47214, "Intermediate", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47215, "Expert", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47216, "Master", "Read Log", tda, 0, 0xff0000, 90, 11);
        /*Tier III*/
        addText(47217, "Tier III.", tda, 1, 0xFF9900, false, true);
        addClickableText(47218, "Beginner", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47219, "Intermediate", "Read Log", tda, 0, 0xff0000, 90, 11);
        addClickableText(47220, "Expert", "Read Log", tda, 0, 0xff0000, 90, 11);
        /*Fremennik*/
        addClickableText(47221, "Master", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47222, "Easy", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47223, "Medium", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47224, "Hard", "Read Journal", tda, 0, 0xff0000, 90, 11);
        /*Karamja*/
        addClickableText(47225, "Karamja", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47226, "Easy", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47227, "Medium", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47228, "Hard", "Read Journal", tda, 0, 0xff0000, 90, 11);
        /*Seers*/
        addClickableText(47229, "Seers' Village", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47230, "Easy", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47231, "Medium", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47232, "Hard", "Read Journal", tda, 0, 0xff0000, 90, 11);
        /*Varrock*/
        addClickableText(47233, "Varrock", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47234, "Easy", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47235, "Medium", "Read Journal", tda, 0, 0xff0000, 90, 11);
        addClickableText(47236, "Hard", "Read Journal", tda, 0, 0xff0000, 90, 11);
        scroll.totalChildren(30);
        scroll.child(0, 47207, 14, 11);
        scroll.child(1, 47208, 28, 33);
        scroll.child(2, 47209, 28, 47);
        scroll.child(3, 47210, 28, 61);
        scroll.child(4, 47211, 28, 75);
        scroll.child(5, 47212, 14, 99);
        scroll.child(6, 47213, 28, 121);
        scroll.child(7, 47214, 28, 135);
        scroll.child(8, 47215, 28, 149);
        scroll.child(9, 47216, 28, 163);
        scroll.child(10, 47217, 14, 183);
        scroll.child(11, 47218, 28, 198);
        scroll.child(12, 47219, 28, 211);
        scroll.child(13, 47220, 28, 225);
        scroll.child(14, 47221, 14, 239);
        scroll.child(15, 47222, 28, 262);
        scroll.child(16, 47223, 28, 275);
        scroll.child(17, 47224, 28, 289);
        scroll.child(18, 47225, 14, 312);
        scroll.child(19, 47226, 28, 326);
        scroll.child(20, 47227, 28, 339);
        scroll.child(21, 47228, 28, 353);
        scroll.child(22, 47229, 14, 376);
        scroll.child(23, 47230, 28, 390);
        scroll.child(24, 47231, 28, 403);
        scroll.child(25, 47232, 28, 417);
        scroll.child(26, 47233, 14, 440);
        scroll.child(27, 47234, 28, 454);
        scroll.child(28, 47235, 28, 467);
        scroll.child(29, 47236, 28, 481);
       }
		
		public static void debugInterface(GameFont[] TDA) {
	Widget tab = addTabInterface(47300); //Interface ID
	//IndexedImage here
	addSprite(47301, 0, "Interfaces/debugInterface/BACKGROUND");
	addHoverButton(47302, "Clan Chat/SPRITE", 6, 72, 32, "Debug Sounds", -1, 47303, 5);
	addHoveredButton(47303, "Clan Chat/SPRITE", 7, 72, 32, 47304);
	//2
	addHoverButton(47305, "Clan Chat/SPRITE", 6, 72, 32, "Debug Emotes", -1, 47306, 5);
	addHoveredButton(47306, "Clan Chat/SPRITE", 7, 72, 32, 47307);
	//3
		addHoverButton(47308, "Clan Chat/SPRITE", 6, 72, 32, "Debug GFX", -1, 47309, 5);
	addHoveredButton(47309, "Clan Chat/SPRITE", 7, 72, 32, 47310);
	//4
	
		addHoverButton(47311, "Clan Chat/SPRITE", 6, 72, 32, "Debug Projectiles", -1, 47312, 5);
	addHoveredButton(47312, "Clan Chat/SPRITE", 7, 72, 32, 47313);
	//5
		addHoverButton(47314, "Clan Chat/SPRITE", 6, 72, 32, "Debug Messages", -1, 47315, 5);
	addHoveredButton(47315, "Clan Chat/SPRITE", 7, 72, 32, 47316);
	//6
		addHoverButton(47317, "Clan Chat/SPRITE", 6, 72, 32, "Debug Frames", -1, 47318, 5);
	addHoveredButton(47318, "Clan Chat/SPRITE", 7, 72, 32, 47319);
	//Close Button
	
		addHoverButton(47320, "Interfaces/Bank/BANK", 1, 17, 17, "Close", -1, 47321, 5);
	addHoveredButton(47321, "Interfaces/Bank/BANK", 2, 17, 17, 47322);
	
	
	tab.totalChildren(15);
	tab.child(0, 47301, 0, 0);
	tab.child(1, 47302, 12, 68);
	tab.child(2, 47303, 12, 68);
	tab.child(3, 47305, 12, 142);
	tab.child(4, 47306, 12, 142);
	tab.child(5, 47308, 12, 215);
	tab.child(6, 47309, 12, 215);
	tab.child(7, 47311, 104, 68);
	tab.child(8, 47312, 104, 68);
	tab.child(9, 47314, 104, 142);
	tab.child(10, 47315, 104, 142);
	tab.child(11, 47317, 104, 215);
	tab.child(12, 47318, 104, 215);
	tab.child(13, 47320, 0, 0);
	tab.child(14, 47321, 0, 0);/*yolo*/

	}
		/*
		public static void bank(GameFont[] TDA) {//bank deposit all
		Widget Tab = addTabInterface(46202);
		addText(46203, "", TDA, 0, 0xFFB000, false);//addText(46203, "", TDA, 0, 0xFFB000, false);
		addHoverButton(46207, "Interfaces/Bank/BANK", 17, 35, 27, "Deposit Inventory", -1, 46208, 5);
		addHoveredButton(46208, "Interfaces/Bank/BANK", 18, 35, 25, 46209);
		addHoverButton(46204, "Interfaces/Bank/BANK", 1, 17, 17, "Close", -1, 46205, 3);
		addHoveredButton(46205, "Interfaces/Bank/BANK", 2, 17, 17, 46206);
		addHoverButton(46210, "Interfaces/Bank/BANK", 19, 35, 27, "Deposit Worn Items", -1, 46211, 5);
		addHoveredButton(46211, "Interfaces/Bank/BANK", 20, 35, 25, 46212);
		addHoverButton(46213, "Interfaces/Bank/BANK", 14, 35, 27, "Search", -1, 46214, 5);
		addHoveredButton(46214, "Interfaces/Bank/BANK", 15, 35, 25, 46215);
		Tab.children = new int[10];//7
		Tab.childX = new int[10];//7
		Tab.childY = new int[10];//7
		Tab.children[0] = 5292;
		Tab.childX[0] = 0;
		Tab.childY[0] = 0;
		Tab.children[1] = 46203;
		Tab.childX[1] = 410;
		Tab.childY[1] = 30;
		Tab.children[2] = 46204;
		Tab.childX[2] = 472;
		Tab.childY[2] = 29;
		Tab.children[3] = 46205;
		Tab.childX[3] = 472;
		Tab.childY[3] = 29;
		Tab.children[4] = 46207;
		Tab.childX[4] = 450;
		Tab.childY[4] = 288;
		Tab.children[5] = 46208;
		Tab.childX[5] = 450;
		Tab.childY[5] = 288;
		Tab.children[6] = 46210;
		Tab.childX[6] = 5;
		Tab.childY[6] = 288;
		Tab.children[7] = 46211;
		Tab.childX[7] = 5;
		Tab.childY[7] = 288;
		Tab.children[8] = 46213;
		Tab.childX[8] = 190;
		Tab.childY[8] = 288;
		Tab.children[9] = 46214;
		Tab.childX[9] = 190;
		Tab.childY[9] = 288;
		
		Widget rsi = interfaceCache[5292];
		addText(5384, "", TDA, 0, 0xFFB000, false);//cheap hax
		rsi.childX[90] = 410;
		rsi.childY[90] = 288;
	}
	*/
	public static void Bank()
    {
        Widget rsinterface = addTabInterface(5292);
        setChildren(21, rsinterface);
        addSprite(5293, 0, "Interfaces/Bank/BANK");
        setBounds(5293, 13, 13, 0, rsinterface);
        addHover(5384, 3, 0, 5380, 1, "Interfaces/Bank/BANK", 17, 17, "Close Window");
        addHovered(5380, 2, "Interfaces/Bank/BANK", 17, 17, 5379);
        addHoverButton(62720, "Interfaces/Bank/BANK", 1, 17, 17, "Close", -1, 62721, 3);
        addHoveredButton(62721, "Interfaces/Bank/BANK", 2, 17, 17, 62733);
        setBounds(5384, 476, 16, 3, rsinterface);
        setBounds(5380, 476, 16, 4, rsinterface);
        setBounds(62720, 476, 16, 19, rsinterface);
        setBounds(62721, 476, 16, 20, rsinterface);
		addHoverButton(62743, "Interfaces/Bank/BANK", 3, 114, 25, "Set A Bank PIN", -1, 62744, 5);
        addHoveredButton(62744, "Interfaces/Bank/BANK", 4, 114, 25, 62745);
        setBounds(62743, 110, 285, 5, rsinterface);
        setBounds(62744, 110, 285, 6, rsinterface);
        addHoverButton(62700, "Interfaces/Bank/BANK", 7, 35, 25, "Swap Withdraw Mode", -1, 62701, 5);
        addHoveredButton(62701, "Interfaces/Bank/BANK", 8, 35, 25, 62730);
        setBounds(62700, 25, 285, 7, rsinterface);
        setBounds(62701, 25, 285, 8, rsinterface);
        addHoverButton(62704, "Interfaces/Bank/BANK", 14, 35, 25, "Empty Inventory", -1, 62705, 5);
        addHoveredButton(62705, "Interfaces/Bank/BANK", 15, 35, 25, 62729);
        setBounds(62704, 65, 285, 9, rsinterface);
        setBounds(62705, 65, 285, 10, rsinterface);
		addHoverButton(62708, "Interfaces/Bank/BANK", 9, 35, 25, "Swap Items Noted", -1, 62709, 5);
        addHoveredButton(62709, "Interfaces/Bank/BANK", 11, 35, 25, 62731);
        setBounds(62708, 240, 285, 11, rsinterface);
        setBounds(62709, 240, 285, 12, rsinterface);
        addHoverButton(62712, "Interfaces/Bank/BANK", 17, 35, 25, "Deposit Inventory", -1, 62713, 5);
        addHoveredButton(62713, "Interfaces/Bank/BANK", 18, 35, 25, 62732);
        setBounds(62712, 375, 285, 13, rsinterface);
        setBounds(62713, 375, 285, 14, rsinterface);
        addHoverButton(62716, "Interfaces/Bank/BANK", 19, 35, 25, "Deposit Worn Items", -1, 62717, 5);
        addHoveredButton(62717, "Interfaces/Bank/BANK", 20, 35, 25, 62723);
        setBounds(62716, 415, 285, 15, rsinterface);
        setBounds(62717, 415, 285, 16, rsinterface);
        addHoverButton(62740, "Interfaces/Bank/BANK", 21, 35, 25, "Deposit Pet", -1, 62741, 5);
        addHoveredButton(62741, "Interfaces/Bank/BANK", 22, 35, 25, 62742);
        setBounds(62740, 455, 285, 17, rsinterface);
        setBounds(62741, 455, 285, 18, rsinterface);
        setBounds(5383, 170, 15, 1, rsinterface);
        setBounds(5385, -4, 74, 2, rsinterface);
        rsinterface = interfaceCache[5385];
        rsinterface.height = 206;
        rsinterface.width = 480;
        rsinterface = interfaceCache[5382];
        rsinterface.width = 10;
        rsinterface.spritePaddingX = 12;
        rsinterface.height = 35;
    } 
	
	public static void quickPrayers(GameFont[] TDA) {
		int i = 0;
    	Widget localWidget = addTabInterface(24999);
    	addSprite(17201, 3, "/Interfaces/QuickPrayer/Sprite");
		addText(17240, "Select your quick prayers:", TDA, 0, 16750623, false, true);
    	addTransparentSprite(17249, 0, "/Interfaces/QuickPrayer/Sprite", 50);
    	int j = 17202;
		for (int k = 630; (j <= 17231) || (k <= 659); ++k) {
     		addConfigButton(j, 17200, 2, 1, "/Interfaces/QuickPrayer/Sprite", 14, 15, "Select", 0, 1, k);
			j++;
    	}
    	addHoverButton(17241, "/Interfaces/QuickPrayer/Sprite", 4, 190, 24, "Confirm Selection", -1, 17242, 1);
    	addHoveredButton(17242, "/Interfaces/QuickPrayer/Sprite", 5, 190, 24, 17243);
    	setChildren(58, localWidget);
    	setBounds(25001, 5, 28, i++, localWidget);
    	setBounds(25003, 44, 28, i++, localWidget);
    	setBounds(25005, 79, 31, i++, localWidget);
    	setBounds(25007, 116, 30, i++, localWidget);
    	setBounds(25009, 153, 29, i++, localWidget);
	setBounds(25011, 5, 68, i++, localWidget);
   	setBounds(25013, 44, 67, i++, localWidget);
    	setBounds(25015, 79, 69, i++, localWidget);
    	setBounds(25017, 116, 70, i++, localWidget);
    	setBounds(25019, 154, 70, i++, localWidget);
    	setBounds(25021, 4, 104, i++, localWidget);
	setBounds(25023, 44, 107, i++, localWidget);
   	setBounds(25025, 81, 105, i++, localWidget);
    	setBounds(25027, 117, 105, i++, localWidget);
    	setBounds(25029, 156, 107, i++, localWidget);
    	setBounds(25031, 5, 145, i++, localWidget);
    	setBounds(25033, 43, 144, i++, localWidget);
    	setBounds(25035, 83, 144, i++, localWidget);
    	setBounds(25037, 115, 141, i++, localWidget);
    	setBounds(25039, 154, 144, i++, localWidget);
    	setBounds(25041, 5, 180, i++, localWidget);
    	setBounds(25043, 41, 178, i++, localWidget);
    	setBounds(25045, 79, 183, i++, localWidget);
    	setBounds(25047, 116, 178, i++, localWidget);
    	setBounds(25049, 161, 180, i++, localWidget);
    	//setBounds(18015, 4, 210, i++, localWidget);
    	setBounds(25051, 5, 217, i++, localWidget);
    	//setBounds(18061, 78, 212, i++, localWidget);
    	//setBounds(18121, 116, 208, i++, localWidget);
    	setBounds(17249, 0, 25, i++, localWidget);
    	setBounds(17201, 0, 22, i++, localWidget);
    	setBounds(17201, 0, 237, i++, localWidget);
    	setBounds(17202, 2, 25, i++, localWidget);
	setBounds(17203, 41, 25, i++, localWidget);
    	setBounds(17204, 76, 25, i++, localWidget);
    	setBounds(17205, 113, 25, i++, localWidget);
    	setBounds(17206, 150, 25, i++, localWidget);
    	setBounds(17207, 2, 65, i++, localWidget);
    	setBounds(17208, 41, 65, i++, localWidget);
    	setBounds(17209, 76, 65, i++, localWidget);
    	setBounds(17210, 113, 65, i++, localWidget);
    	setBounds(17211, 150, 65, i++, localWidget);
    	setBounds(17212, 2, 102, i++, localWidget);
    	setBounds(17213, 41, 102, i++, localWidget);
	setBounds(17214, 76, 102, i++, localWidget);
    	setBounds(17215, 113, 102, i++, localWidget);
    	setBounds(17216, 150, 102, i++, localWidget);
    	setBounds(17217, 2, 141, i++, localWidget);
    	setBounds(17218, 41, 141, i++, localWidget);
    	setBounds(17219, 76, 141, i++, localWidget);
    	setBounds(17220, 113, 141, i++, localWidget);
    	setBounds(17221, 150, 141, i++, localWidget);
    	setBounds(17222, 2, 177, i++, localWidget);
    	setBounds(17223, 41, 177, i++, localWidget);
    	setBounds(17224, 76, 177, i++, localWidget);
    	setBounds(17225, 113, 177, i++, localWidget);
    	setBounds(17226, 150, 177, i++, localWidget);
    	setBounds(17227, 1, 211, i++, localWidget);
    	//setBounds(17228, 1, 211, i++, localWidget);
    	//setBounds(17229, 75, 211, i++, localWidget);
		//setBounds(17230, 113, 211, i++, localWidget);
   		setBounds(17240, 5, 5, i++, localWidget);
		setBounds(17241, 0, 237, i++, localWidget);
    	setBounds(17242, 0, 237, i++, localWidget);
    }
	
	public static void loginInterface(GameFont[] tda) {
	  Widget tab = addScreenInterface(46100);//16850
	addSprite(46101, 0, "Interfaces/Login/LOGIN");
	addHoverButton(46102, "Interfaces/Login/WEBSITE", 0, 135, 27, "Go to the Forums Page", -1, 46103, 5);
	addHoveredButton(46103, "Interfaces/Login/WEBSITE", 1, 135, 27, 46104);
	addHoverButton(46105, "Interfaces/Login/VOTE", 0, 65, 30, "Go to the Vote Page", -1, 46106, 5);
	addHoveredButton(46106, "Interfaces/Login/VOTE", 1, 65, 30, 46107);
	addHoverButton(46108, "Interfaces/Login/DONATE", 0, 135, 27, "Go to the Donate Page", -1, 46109, 5);
	addHoveredButton(46109, "Interfaces/Login/DONATE", 1, 135, 27, 46110);
	addHoverButton(46111, "Interfaces/Login/PLAYNOW", 1, 150, 32, "Close", -1, 46112, 5);
	addHoveredButton(46112, "Interfaces/Login/PLAYNOW", 2, 150, 32, 46113);
	addText(46114, "", tda, 1, 0xFF981F, true, false);
	addText(46115, "", tda, 1, 0xFF981F, true, false);
	tab.totalChildren(11);
	tab.child(0, 46101, 30, 30);
	tab.child(1, 46102, 68, 170);
	tab.child(2, 46103, 68, 170);
	tab.child(3, 46105, 305, 147);
	tab.child(4, 46106, 305, 147);
	tab.child(5, 46108, 68, 135);
	tab.child(6, 46109, 68, 135);
	tab.child(7, 46111, 60, 213);
	tab.child(8, 46112, 60, 213);
	tab.child(9, 46114, 310, 122);
	tab.child(10, 46115, 92, 110);
	}

	public static void emoteTab() {
        Widget tab = addTabInterface(147);
        Widget scroll = addTabInterface(148);
        tab.totalChildren(1);
        tab.child(0, 148, 0, 1);
        addButton(168, 0, "/Emotes/EMOTE", "Yes",41,47);
        addButton(169, 1, "/Emotes/EMOTE", "No",41,47);
        addButton(164, 2, "/Emotes/EMOTE", "Bow",41,47);
        addButton(165, 3, "/Emotes/EMOTE", "Angry",41,47);
        addButton(162, 4, "/Emotes/EMOTE", "Think",41,47);
        addButton(163, 5, "/Emotes/EMOTE", "Wave",41,47);
        addButton(13370, 6, "/Emotes/EMOTE", "Shrug",41,47);
        addButton(171, 7, "/Emotes/EMOTE", "Cheer",41,47);
        addButton(167, 8, "/Emotes/EMOTE", "Beckon",41,47);
        addButton(170, 9, "/Emotes/EMOTE", "Laugh",41,47);
        addButton(13366, 10, "/Emotes/EMOTE", "Jump for Joy",41,47);
        addButton(13368, 11, "/Emotes/EMOTE", "Yawn",41,47);
        addButton(166, 12, "/Emotes/EMOTE", "Dance",41,47);
        addButton(13363, 13, "/Emotes/EMOTE", "Jig",41,47);
        addButton(13364, 14, "/Emotes/EMOTE", "Spin",41,47);
        addButton(13365, 15, "/Emotes/EMOTE", "Headbang",41,47);
        addButton(161, 16, "/Emotes/EMOTE", "Cry",41,47);
        addButton(11100, 17, "/Emotes/EMOTE", "Blow kiss",41,47);
        addButton(13362, 18, "/Emotes/EMOTE", "Panic",41,47);
        addButton(13367, 19, "/Emotes/EMOTE", "Raspberry",41,47);
        addButton(172, 20, "/Emotes/EMOTE", "Clap",41,47);
        addButton(13369, 21, "/Emotes/EMOTE", "Salute",41,47);
        addButton(13383, 22, "/Emotes/EMOTE", "Goblin Bow",41,47);
        addButton(13384, 23, "/Emotes/EMOTE", "Goblin Salute",41,47);
        addButton(667, 24, "/Emotes/EMOTE", "Glass Box",41,47);
        addButton(6503, 25, "/Emotes/EMOTE", "Climb Rope",41,47);
        addButton(6506, 26, "/Emotes/EMOTE", "Lean On Air",41,47);
        addButton(666, 27, "/Emotes/EMOTE", "Glass Wall",41,47);
        addButton(18464, 28, "/Emotes/EMOTE", "Zombie Walk",41,47);
        addButton(18465, 29, "/Emotes/EMOTE", "Zombie Dance",41,47);
        addButton(15166, 30, "/Emotes/EMOTE", "Scared",41,47);
        addButton(18686, 31, "/Emotes/EMOTE", "Rabbit Hop",41,47);
		addButton(154, 32, "/Emotes/EMOTE", "Skillcape Emote", 41, 47); 
        scroll.totalChildren(33);
        scroll.child(0, 168, 10, 7);
        scroll.child(1, 169, 54, 7);
        scroll.child(2, 164, 98, 14);
        scroll.child(3, 165, 137, 7);
        scroll.child(4, 162, 9, 56);
        scroll.child(5, 163, 48, 56);
        scroll.child(6, 13370, 95, 56);
        scroll.child(7, 171, 137, 56);
        scroll.child(8, 167, 7, 105);
        scroll.child(9, 170, 51, 105);
        scroll.child(10, 13366, 95, 104);
        scroll.child(11, 13368, 139, 105);
        scroll.child(12, 166, 6, 154);
        scroll.child(13, 13363, 50, 154);
        scroll.child(14, 13364, 90, 154);
        scroll.child(15, 13365, 135, 154);
        scroll.child(16, 161, 8, 204);
        scroll.child(17, 11100, 51, 203);
        scroll.child(18, 13362, 99, 204);
        scroll.child(19, 13367, 137, 203);
        scroll.child(20, 172, 10, 253);
        scroll.child(21, 13369, 53, 253);
        scroll.child(22, 13383, 88, 258);
        scroll.child(23, 13384, 138, 252);
        scroll.child(24, 667, 2, 303);
        scroll.child(25, 6503, 49, 302);
        scroll.child(26, 6506, 93, 302);
        scroll.child(27, 666, 137, 302);
        scroll.child(28, 18464, 9, 352);
        scroll.child(29, 18465, 50, 352);
        scroll.child(30, 15166, 94, 356);
        scroll.child(31, 18686, 141, 353);
        scroll.child(32, 154, 5, 401);
        scroll.width = 173; scroll.height = 258; scroll.scrollMax = 450;
    }
	
	public static void clanChatTab(GameFont[] tda) {
        Widget tab = addTabInterface(18128);
        addHoverButton(18129, "/Clan Chat/SPRITE", 6, 72, 32, "Join Chat", 550, 18130, 1);
        addHoveredButton(18130, "/Clan Chat/SPRITE", 7, 72, 32, 18131);
        addHoverButton(18132, "/Clan Chat/SPRITE", 6, 72, 32, "Edit settings", -1, 18133, 5);
        addHoveredButton(18133, "/Clan Chat/SPRITE", 7, 72, 32, 18134);
		addButton(18250, 0, "/Clan Chat/Lootshare", "Toggle lootshare");
        addText(18135, "Join Chat", tda, 0, 0xff9b00, true, true);
        addText(18136, "Edit Settings", tda, 0, 0xff9b00, true, true);
        addSprite(18137, 37, "/Clan Chat/SPRITE");
        addText(18138, "Clan Chat", tda, 1, 0xff9b00, true, true);
        addText(18139, "Talking in: Not in chat", tda, 0, 0xff9b00, false, true);
        addText(18140, "Owner: None", tda, 0, 0xff9b00, false, true);
        tab.totalChildren(14);
        tab.child(0, 16126, 0, 221);
        tab.child(1, 16126, 0, 59);
        tab.child(2, 18137, 0, 62);
        tab.child(3, 18143, 0, 62);
        tab.child(4, 18129, 15, 226);
        tab.child(5, 18130, 15, 226);
        tab.child(6, 18132, 103, 226);
        tab.child(7, 18133, 103, 226);
        tab.child(8, 18135, 51, 237);
        tab.child(9, 18136, 139, 237);
        tab.child(10, 18138, 95, 1);
        tab.child(11, 18139, 10, 23);
        tab.child(12, 18140, 25, 38);
		tab.child(13, 18250, 145,15);
        /* Text area */
        Widget list = addTabInterface(18143);
        list.totalChildren(100);
        for(int i = 18144; i <= 18244; i++) {
            addText(i, "", tda, 0, 0xffffff, false, true);
        }
        for(int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
            list.children[i] = id; list.childX[i] = 5;
            for(int id2 = 18144, i2 = 1; id2 <= 18243 && i2<= 99; id2++, i2++) {
                list.childY[0] = 2;
                list.childY[i2] = list.childY[i2 - 1] + 14;
            }
        }
        list.height = 158; list.width = 174;
        list.scrollMax = 1405;
    }
	
	int transparency;
	private static void addTransparentSprite(int id, int spriteId, String spriteName, int transparency) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.transparency = (byte) transparency;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}
	
	public static void addText(int i, String s, int k, boolean l, boolean m, int a, GameFont[] TDA, int j) {
		Widget Widget = addInterface(i);
		Widget.parent = i;
		Widget.id = i;
		Widget.type = 4;
		Widget.atActionType = 0;
		Widget.width = 0;
		Widget.height = 0;
		Widget.contentType = 0;
		Widget.opacity = 0;
		Widget.hoverType = a;
		Widget.centerText = l;
		Widget.textShadow = m;
		Widget.textDrawingAreas = TDA[j];
		Widget.defaultText = s;
		Widget.secondaryText = "";
		Widget.textColor = k;
	}
	
	public static void Pestpanel(GameFont[] tda) {
		Widget RSinterface = addInterface(21119);
		addText(21120, "What", 0x999999, false, true, 52, tda, 1);
		addText(21121, "What", 0x33cc00, false, true, 52, tda, 1);
		addText(21122, "(Need 5 to 25 players)", 0xFFcc33, false, true, 52, tda, 1);
		addText(21123, "Points", 0x33ccff, false, true, 52, tda, 1);
		int last = 4;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21120, 15, 12, 0,RSinterface);
		setBounds(21121, 15, 30, 1,RSinterface);
		setBounds(21122, 15, 48, 2,RSinterface);
		setBounds(21123, 15, 66, 3,RSinterface);
	}
		
	public static void Pestpanel2(GameFont[] tda) {
		Widget RSinterface = addInterface(21100);
		addSprite(21101, 0, "Pest Control/PEST1");
		addSprite(21102, 1, "Pest Control/PEST1");
		addSprite(21103, 2, "Pest Control/PEST1");
		addSprite(21104, 3, "Pest Control/PEST1");
		addSprite(21105, 4, "Pest Control/PEST1");
		addSprite(21106, 5, "Pest Control/PEST1");
		addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
		addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
		addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
		addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
		addText(21111, "250", 0x99FF33, false, true, 52, tda, 1);//w purp
		addText(21112, "250", 0x99FF33, false, true, 52, tda, 1);//e blue
		addText(21113, "250", 0x99FF33, false, true, 52, tda, 1);//se yel
		addText(21114, "250", 0x99FF33, false, true, 52, tda, 1);//sw red
		addText(21115, "200", 0x99FF33, false, true, 52, tda, 1);//attacks
		addText(21116, "0", 0x99FF33, false, true, 52, tda, 1);//knights hp
		addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
		addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
		int last = 18;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21101, 361, 26, 0,RSinterface);
		setBounds(21102, 396, 26, 1,RSinterface);
		setBounds(21103, 436, 26, 2,RSinterface);
		setBounds(21104, 474, 26, 3,RSinterface);
		setBounds(21105, 3, 21, 4,RSinterface);
		setBounds(21106, 3, 50, 5,RSinterface);
		setBounds(21107, 371, 60, 6,RSinterface);
		setBounds(21108, 409, 60, 7,RSinterface);
		setBounds(21109, 443, 60, 8,RSinterface);
		setBounds(21110, 479, 60, 9,RSinterface);
		setBounds(21111, 362, 10, 10,RSinterface);
		setBounds(21112, 398, 10, 11,RSinterface);
		setBounds(21113, 436, 10, 12,RSinterface);
		setBounds(21114, 475, 10, 13,RSinterface);
		setBounds(21115, 32, 32, 14,RSinterface);
		setBounds(21116, 32, 62, 15,RSinterface);
		setBounds(21117, 8, 88, 16,RSinterface);
		setBounds(21118, 87, 88, 17,RSinterface);
	}
	
	public static void addToggleButton(int id, int bID, int bID2, String bName, String tT, int configID, int aT, int configFrame) {
		Widget tab = addTabInterface(id);
		tab.parent = id;
		tab.id = id;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = 0;//anInt214
		tab.opacity = 0;
		tab.hoverType = -1;//anInt230
		tab.valueCompareType = new int[1];
		tab.requiredValues = new int[1];
		tab.valueCompareType[0] = 1;
		tab.requiredValues[0] = configID;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
		tab.disabledSprite = imageLoader(bID, bName);
		tab.enabledSprite = imageLoader(bID2, bName);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.tooltip = tT;
	}
	
	public static void addHover(int i, int aT, int cT, int hoverid,int sId, String NAME, int W, int H, String tip) { 
		Widget rsinterfaceHover = addInterface(i);
		rsinterfaceHover.id = i;
		rsinterfaceHover.parent = i;
		rsinterfaceHover.interfaceType = 5;
		rsinterfaceHover.atActionType = aT;
		rsinterfaceHover.contentType = cT;
		rsinterfaceHover.hoverType = hoverid;
		rsinterfaceHover.disabledSprite = imageLoader(sId, NAME);
		rsinterfaceHover.enabledSprite = imageLoader(sId, NAME);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}
	public static void addHovered(int i, int j, String imageName, int w, int h, int IMAGEID) {
		Widget rsinterfaceHover = addInterface(i);
		rsinterfaceHover.parent = i;
		rsinterfaceHover.id = i;
		rsinterfaceHover.interfaceType = 0;
		rsinterfaceHover.atActionType = 0;
		rsinterfaceHover.width = w;
		rsinterfaceHover.height = h;
		rsinterfaceHover.interfaceShown = true;
		rsinterfaceHover.hoverType = -1;
		addSprite(IMAGEID, j, imageName);
		setChildren(1, rsinterfaceHover);
		setBounds(IMAGEID, 0, 0, 0, rsinterfaceHover);
	}
	
	public String hoverText;
	public static void addHoverBox(int id, String text) {
        Widget rsi = interfaceCache[id];//addTabInterface(id);
        rsi.id = id;
        rsi.parent = id;
		rsi.invisible = true;
        rsi.type = 8;
        rsi.hoverText = text;
    }
	
	public static void addText(int id, String text, GameFont tda[], int idx, int color, boolean center, boolean shadow) {
		Widget tab = addTabInterface(id);
		tab.parent = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.defaultText = text;
		tab.secondaryText = "";
		tab.textColor = color;
		tab.secondaryColor = 0;
		tab.defaultHoverCover = 0;
		tab.secondaryHoverColor = 0;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte)0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(sid, spriteName);
		tab.enabledSprite = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}
	
	public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height, String tT, int configID, int aT, int configFrame) {
        Widget Tab = addTabInterface(ID);
        Tab.parent = pID;
        Tab.id = ID;
        Tab.type = 5;
        Tab.atActionType = aT;
        Tab.contentType = 0;
        Tab.width = width;
        Tab.height = height;
        Tab.opacity = 0;
        Tab.hoverType = -1;
        Tab.valueCompareType = new int[1];
        Tab.requiredValues = new int[1];
        Tab.valueCompareType[0] = 1;
        Tab.requiredValues[0] = configID;
        Tab.valueIndexArray = new int[1][3];
        Tab.valueIndexArray[0][0] = 5;
        Tab.valueIndexArray[0][1] = configFrame;
        Tab.valueIndexArray[0][2] = 0;
        Tab.disabledSprite = imageLoader(bID, bName);
        Tab.enabledSprite = imageLoader(bID2, bName);
        Tab.tooltip = tT;
    }

	public static void addSprite(int id, int spriteId, String spriteName) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte)0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {//hoverable button
		Widget tab = addTabInterface(i);
		tab.id = i;
		tab.parent = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.disabledSprite = imageLoader(j, imageName);
		tab.enabledSprite = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {//hoverable button
		Widget tab = addTabInterface(i);
		tab.parent = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.invisible = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		Widget tab = addTabInterface(i);
		tab.id = i;
		tab.parent = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(j, name);
		tab.enabledSprite = imageLoader(k, name);
	}

	public static void addTransparentSprite(int id, int spriteId, String spriteName) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte)0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static Widget addScreenInterface(int id) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;
		tab.parent = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = (byte)0;
		tab.hoverType = 0;
		return tab;
	}

	public static Widget addTabInterface(int id) {
		Widget tab = interfaceCache[id] = new Widget();
		tab.id = id;//250
		tab.parent = id;//236
		tab.type = 0;//262
		tab.atActionType = 0;//217
		tab.contentType = 0;
		tab.width = 512;//220
		tab.height = 700;//267
		tab.opacity = (byte)0;
		tab.hoverType = -1;//Int 230
		return tab;
	}

	private static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + (long)i;
		Sprite sprite = (Sprite) spriteCache.get(l);
		if(sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s+" "+i);
			spriteCache.put(sprite, l);
		} catch(Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j)
	{
		Model model = (Model) A_REFERENCE_CACHE___264.get((i << 16) + j);
		if(model != null)
			return model;
		if(i == 1)
			model = Model.getModel(j);
		if(i == 2)
			model = NpcDefinition.forID(j).method160();
		if(i == 3)
			model = Client.localPlayer.method453();
		if(i == 4)
			model = ItemDefinition.lookup(j).getUnshadedModel(50);
		if(i == 5)
			model = null;
		if(model != null)
			A_REFERENCE_CACHE___264.put(model, (i << 16) + j);
		return model;
	}

	private static Sprite getSprite(int i, FileArchive streamLoader, String s) {
		if (spriteCache == null) {
			spriteCache = new ReferenceCache(SPRITE_CACHE_SIZE);
		}
		long l = (StringUtils.hashSpriteName(s) << 8) + (long) i;
		Sprite sprite = (Sprite) spriteCache.get(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(streamLoader, s, i);
			spriteCache.put(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;//was parameter
		int j = 5;//was parameter
		if(flag)
			return;
		A_REFERENCE_CACHE___264.unlinkAll();
		if(model != null && j != 4)
			A_REFERENCE_CACHE___264.put(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if(flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(defaultMediaType, mediaID);
		if(model == null)
			return null;
		if(k == -1 && j == -1 && model.triangleColours == null)
			return model;
		Model model_1 = new Model(true, Frame.noAnimationInProgress(k) & Frame.noAnimationInProgress(j), false, model);
		if(k != -1 || j != -1)
			model_1.skin();
		if(k != -1)
			model_1.applyTransform(k);
		if(j != -1)
			model_1.applyTransform(j);
		model_1.light(64, 768, -50, -10, -50, true);
			return model_1;
	}

	public Widget() {}

		public static void addChar(int ID) { 
			Widget t = interfaceCache[ID] = new Widget();
			t.id = ID; 
			t.parent = ID;
			t.type = 6;
			t.atActionType = 0; 
			t.contentType = 328; 
			t.width = 136; 
			t.height = 168; 
			t.opacity = 0;
			t.hoverType = 0;
			t.modelZoom = 560;
			t.modelRotation1 = 150;
			t.modelRotation2 = 0; 
			t.defaultAnimationId = -1;
			t.secondaryAnimationId = -1;
		}
		
		private static Sprite LoadLunarSprite(int i, String s) {
			Sprite sprite = imageLoader(i,"/Lunar/" + s);
			return sprite;
		}
	private static Sprite LoadSprite(int i, String s) {
        long l = (TextClass.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) spriteCache.get(l);
        if (sprite != null) {
            return sprite;
        }
        try {
            sprite = new Sprite(Signlink.findcachedir() + "/Sprites/"+s+" "+i+".PNG");
            spriteCache.put(sprite, l);
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
        return sprite;
    }
	
public static void addLunarSprite(int i, int j, String name) {
        Widget Widget = addInterface(i);
        Widget.id = i;
        Widget.parent = i;
        Widget.type = 5;
        Widget.atActionType = 0;
        Widget.drawsTransparent = true;
        //Widget.transAmount = 230;
        Widget.contentType = 0;
        Widget.opacity = 0;
        Widget.hoverType = 52;
        Widget.disabledSprite =  imageLoader(j, name);
        Widget.width = 500;
        Widget.height = 500;
        Widget.tooltip = "";
    }
	
public static void drawRune(int i,int id, String runeName) {
        Widget Widget = addInterface(i);
        Widget.type = 5;
        Widget.atActionType = 0;
        Widget.contentType = 0;
        Widget.opacity = 0;
        Widget.hoverType = 52;
        Widget.disabledSprite =  imageLoader(id, "Lunar/RUNE");
        Widget.width = 500;
        Widget.height = 500;
    }
	
	public static void addRuneText(int ID, int runeAmount, int RuneID, GameFont[] font){
		Widget widget = addInterface(ID);
		widget.id = ID;
		widget.parent = 1151;
		widget.type = 4;
		widget.atActionType = 0;
		widget.contentType = 0;
		widget.width = 0;
		widget.height = 14;
		widget.opacity = 0;
		widget.hoverType = -1;
		widget.valueCompareType = new int[1];
		widget.requiredValues = new int[1];
		widget.valueCompareType[0] = 3;
		widget.requiredValues[0] = runeAmount;
		widget.valueIndexArray = new int[1][4];
		widget.valueIndexArray[0][0] = 4;
		widget.valueIndexArray[0][1] = 3214;
		widget.valueIndexArray[0][2] = RuneID;
		widget.valueIndexArray[0][3] = 0;
		widget.centerText = true;
		widget.textDrawingAreas = font[0];
		widget.textShadow = true;
		widget.defaultText = "%1/"+runeAmount+"";
		widget.secondaryText = "";
		widget.textColor = 12582912;
		widget.secondaryColor = 49152;
	}
	
	public static void homeTeleport(){
        Widget Widget = addInterface(30000);
        Widget.tooltip = "Cast @gre@Lunar Home Teleport";
        Widget.id = 30000;
        Widget.parent = 30000;
        Widget.type = 5;
        Widget.atActionType = 5;
        Widget.contentType = 0;
        Widget.opacity = 0;
        Widget.hoverType = 30001;
        Widget.disabledSprite =  imageLoader(1, "Lunar/SPRITE");
        Widget.width = 20;
        Widget.height = 20;
        Widget Int = addInterface(30001);
        Int.invisible = true;
    	Int.hoverType = -1;
        setChildren(1, Int);
        addLunarSprite(30002, 0, "SPRITE");
        setBounds(30002, 0, 0,0, Int);
	}
public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, GameFont[] TDA, int sid, int suo, int type){
	Widget widget = addInterface(ID);
	widget.id = ID;
	widget.parent = 1151;
	widget.type = 5;
	widget.atActionType = type;
	widget.contentType = 0;
	widget.hoverType = ID+1;
	widget.spellUsableOn = suo;
	widget.selectedActionName = "Cast On";
	widget.width = 20;
	widget.height = 20;
	widget.tooltip = "Cast @gre@"+name;
	widget.spellName = name;
	widget.valueCompareType = new int[3];
	widget.requiredValues = new int[3];
	widget.valueCompareType[0] = 3;
	widget.requiredValues[0] = ra1;
	widget.valueCompareType[1] = 3;
	widget.requiredValues[1] = ra2;
	widget.valueCompareType[2] = 3;
	widget.requiredValues[2] = lvl;
	widget.valueIndexArray = new int[3][];
	widget.valueIndexArray[0] = new int[4];
	widget.valueIndexArray[0][0] = 4;
	widget.valueIndexArray[0][1] = 3214;
	widget.valueIndexArray[0][2] = r1;
	widget.valueIndexArray[0][3] = 0;
	widget.valueIndexArray[1] = new int[4];
	widget.valueIndexArray[1][0] = 4;
	widget.valueIndexArray[1][1] = 3214;
	widget.valueIndexArray[1][2] = r2;
	widget.valueIndexArray[1][3] = 0;
	widget.valueIndexArray[2] = new int[3];
	widget.valueIndexArray[2][0] = 1;
	widget.valueIndexArray[2][1] = 6;
	widget.valueIndexArray[2][2] = 0;
	widget.enabledSprite =  imageLoader(sid, "Lunar/LUNARON");
	widget.disabledSprite =  imageLoader(sid, "Lunar/LUNAROFF");
	Widget INT = addInterface(ID+1);
	INT.invisible = true;
	INT.hoverType = -1;
	setChildren(7, INT);
	addLunarSprite(ID+2, 0, "Lunar/BOX");
	setBounds(ID+2, 0, 0, 0, INT);
	addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);
	setBounds(ID+3, 90, 4, 1, INT);
	addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	
	setBounds(ID+4, 90, 19, 2, INT);
	setBounds(30016, 37, 35, 3, INT);//Rune
	setBounds(rune1, 112, 35, 4, INT);//Rune
	addRuneText(ID+5, ra1+1, r1, TDA);
	setBounds(ID+5, 50, 66, 5, INT);
	addRuneText(ID+6, ra2+1, r2, TDA);
	setBounds(ID+6, 123, 66, 6, INT);

}

public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, GameFont[] TDA, int sid, int suo, int type){
	Widget widget = addInterface(ID);
	widget.id = ID;
	widget.parent = 1151;
	widget.type = 5;
	widget.atActionType = type;
	widget.contentType = 0;
	widget.hoverType = ID+1;
	widget.spellUsableOn = suo;
	widget.selectedActionName = "Cast on";
	widget.width = 20;
	widget.height = 20;
	widget.tooltip = "Cast @gre@"+name;
	widget.spellName = name;
	widget.valueCompareType = new int[4];
	widget.requiredValues = new int[4];
	widget.valueCompareType[0] = 3;
	widget.requiredValues[0] = ra1;
	widget.valueCompareType[1] = 3;
	widget.requiredValues[1] = ra2;
	widget.valueCompareType[2] = 3;
	widget.requiredValues[2] = ra3;
	widget.valueCompareType[3] = 3;
	widget.requiredValues[3] = lvl;
	widget.valueIndexArray = new int[4][];
	widget.valueIndexArray[0] = new int[4];
	widget.valueIndexArray[0][0] = 4;
	widget.valueIndexArray[0][1] = 3214;
	widget.valueIndexArray[0][2] = r1;
	widget.valueIndexArray[0][3] = 0;
	widget.valueIndexArray[1] = new int[4];
	widget.valueIndexArray[1][0] = 4;
	widget.valueIndexArray[1][1] = 3214;
	widget.valueIndexArray[1][2] = r2;
	widget.valueIndexArray[1][3] = 0;
	widget.valueIndexArray[2] = new int[4];
	widget.valueIndexArray[2][0] = 4;
	widget.valueIndexArray[2][1] = 3214;
	widget.valueIndexArray[2][2] = r3;
	widget.valueIndexArray[2][3] = 0;
	widget.valueIndexArray[3] = new int[3];
	widget.valueIndexArray[3][0] = 1;
	widget.valueIndexArray[3][1] = 6;
	widget.valueIndexArray[3][2] = 0;
	widget.enabledSprite =  imageLoader(sid, "Lunar/LUNARON");
	widget.disabledSprite =  imageLoader(sid, "Lunar/LUNAROFF");
	Widget INT = addInterface(ID+1);
	INT.invisible = true;
	INT.hoverType = -1;
	setChildren(9, INT);
	addLunarSprite(ID+2, 0, "Lunar/BOX");
	setBounds(ID+2, 0, 0, 0, INT);
	addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
	addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	setBounds(ID+4, 90, 19, 2, INT);
	setBounds(30016, 14, 35, 3, INT);
	setBounds(rune1, 74, 35, 4, INT);
	setBounds(rune2, 130, 35, 5, INT);
	addRuneText(ID+5, ra1+1, r1, TDA);
	setBounds(ID+5, 26, 66, 6, INT);
	addRuneText(ID+6, ra2+1, r2, TDA);
	setBounds(ID+6, 87, 66, 7, INT);
	addRuneText(ID+7, ra3+1, r3, TDA);
	setBounds(ID+7, 142, 66, 8, INT);
}

public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, GameFont[] TDA, int sid, int suo, int type){
	Widget widget = addInterface(ID);
	widget.id = ID;
	widget.parent = 1151;
	widget.type = 5;
	widget.atActionType = type;
	widget.contentType = 0;
	widget.hoverType = ID+1;
	widget.spellUsableOn = suo;
	widget.selectedActionName = "Cast on";
	widget.width = 20;
	widget.height = 20;
	widget.tooltip = "Cast @gre@"+name;
	widget.spellName = name;
	widget.valueCompareType = new int[4];
	widget.requiredValues = new int[4];
	widget.valueCompareType[0] = 3;
	widget.requiredValues[0] = ra1;
	widget.valueCompareType[1] = 3;
	widget.requiredValues[1] = ra2;
	widget.valueCompareType[2] = 3;
	widget.requiredValues[2] = ra3;
	widget.valueCompareType[3] = 3;
	widget.requiredValues[3] = lvl;
	widget.valueIndexArray = new int[4][];
	widget.valueIndexArray[0] = new int[4];
	widget.valueIndexArray[0][0] = 4;
	widget.valueIndexArray[0][1] = 3214;
	widget.valueIndexArray[0][2] = r1;
	widget.valueIndexArray[0][3] = 0;
	widget.valueIndexArray[1] = new int[4];
	widget.valueIndexArray[1][0] = 4;
	widget.valueIndexArray[1][1] = 3214;
	widget.valueIndexArray[1][2] = r2;
	widget.valueIndexArray[1][3] = 0;
	widget.valueIndexArray[2] = new int[4];
	widget.valueIndexArray[2][0] = 4;
	widget.valueIndexArray[2][1] = 3214;
	widget.valueIndexArray[2][2] = r3;
	widget.valueIndexArray[2][3] = 0;
	widget.valueIndexArray[3] = new int[3];
	widget.valueIndexArray[3][0] = 1;
	widget.valueIndexArray[3][1] = 6;
	widget.valueIndexArray[3][2] = 0;
	widget.enabledSprite =  imageLoader(sid, "Lunar/LUNARON");
	widget.disabledSprite =  imageLoader(sid, "Lunar/LUNAROFF");
	Widget INT = addInterface(ID+1);
	INT.invisible = true;
	INT.hoverType = -1;
	setChildren(9, INT);
	addLunarSprite(ID+2, 1, "Lunar/BOX");
	setBounds(ID+2, 0, 0, 0, INT);
	addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
	addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	setBounds(ID+4, 90, 21, 2, INT);
	setBounds(30016, 14, 48, 3, INT);
	setBounds(rune1, 74, 48, 4, INT);
	setBounds(rune2, 130, 48, 5, INT);
	addRuneText(ID+5, ra1+1, r1, TDA);
	setBounds(ID+5, 26, 79, 6, INT);
	addRuneText(ID+6, ra2+1, r2, TDA);
	setBounds(ID+6, 87, 79, 7, INT);
	addRuneText(ID+7, ra3+1, r3, TDA);
	setBounds(ID+7, 142, 79, 8, INT);
}

public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, GameFont[] TDA, int sid, int suo, int type){
	Widget widget = addInterface(ID);
	widget.id = ID;
	widget.parent = 1151;
	widget.type = 5;
	widget.atActionType = type;
	widget.contentType = 0;
	widget.hoverType = ID+1;
	widget.spellUsableOn = suo;
	widget.selectedActionName = "Cast on";
	widget.width = 20;
	widget.height = 20;
	widget.tooltip = "Cast @gre@"+name;
	widget.spellName = name;
	widget.valueCompareType = new int[4];
	widget.requiredValues = new int[4];
	widget.valueCompareType[0] = 3;
	widget.requiredValues[0] = ra1;
	widget.valueCompareType[1] = 3;
	widget.requiredValues[1] = ra2;
	widget.valueCompareType[2] = 3;
	widget.requiredValues[2] = ra3;
	widget.valueCompareType[3] = 3;
	widget.requiredValues[3] = lvl;
	widget.valueIndexArray = new int[4][];
	widget.valueIndexArray[0] = new int[4];
	widget.valueIndexArray[0][0] = 4;
	widget.valueIndexArray[0][1] = 3214;
	widget.valueIndexArray[0][2] = r1;
	widget.valueIndexArray[0][3] = 0;
	widget.valueIndexArray[1] = new int[4];
	widget.valueIndexArray[1][0] = 4;
	widget.valueIndexArray[1][1] = 3214;
	widget.valueIndexArray[1][2] = r2;
	widget.valueIndexArray[1][3] = 0;
	widget.valueIndexArray[2] = new int[4];
	widget.valueIndexArray[2][0] = 4;
	widget.valueIndexArray[2][1] = 3214;
	widget.valueIndexArray[2][2] = r3;
	widget.valueIndexArray[2][3] = 0;
	widget.valueIndexArray[3] = new int[3];
	widget.valueIndexArray[3][0] = 1;
	widget.valueIndexArray[3][1] = 6;
	widget.valueIndexArray[3][2] = 0;
	widget.enabledSprite =  imageLoader(sid, "Lunar/LUNARON");
	widget.disabledSprite =  imageLoader(sid, "Lunar/LUNAROFF");
	Widget INT = addInterface(ID+1);
	INT.invisible = true;
	INT.hoverType = -1;
	setChildren(9, INT);
	addLunarSprite(ID+2, 2, "Lunar/BOX");
	setBounds(ID+2, 0, 0, 0, INT);
	addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);
	setBounds(ID+3, 90, 4, 1, INT);
	addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	
	setBounds(ID+4, 90, 34, 2, INT);
	setBounds(30016, 14, 61, 3, INT);
	setBounds(rune1, 74, 61, 4, INT);
	setBounds(rune2, 130, 61, 5, INT);
	addRuneText(ID+5, ra1+1, r1, TDA);
	setBounds(ID+5, 26, 92, 6, INT);
	addRuneText(ID+6, ra2+1, r2, TDA);
	setBounds(ID+6, 87, 92, 7, INT);
	addRuneText(ID+7, ra3+1, r3, TDA);
	setBounds(ID+7, 142, 92, 8, INT);
}
	public static void setChildren(int total, Widget i){
        i.children = new int[total];
        i.childX = new int[total];
        i.childY = new int[total];
	}
	
	
	public static void configureLunar(GameFont[] TDA){
		homeTeleport();
		drawRune(30003,1, "Fire");
		drawRune(30004,2, "Water");
		drawRune(30005,3, "Air");
		drawRune(30006,4, "Earth");
		drawRune(30007,5, "Mind");
		drawRune(30008,6, "Body");
		drawRune(30009,7, "Death");
		drawRune(30010,8, "Nature");
		drawRune(30011,9, "Chaos");
		drawRune(30012,10, "Law");
		drawRune(30013,11, "Cosmic");
		drawRune(30014,12, "Blood");
		drawRune(30015,13, "Soul");
		drawRune(30016,14, "Astral");
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie","Bake pies without a stove",TDA,0, 16,2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65,"Cure Plant", "Cure disease on farming patch",TDA,1, 4,2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0,0, 0, 30013, 30007, 65,"Monster Examine", "Detect the combat statistics of a\\nmonster",TDA, 2,2,2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "Npc Contact","Speak with varied NPCs",TDA,3,0,2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other","Cure poisoned players",TDA,4,8,2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify","fills certain vessels with water",TDA,5,0,5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Monster Teleport","Teleports you to various monsters",TDA,6,0,5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012,  30006, 69,"Minigames", "Teleport to various Mini's.",TDA, 7,0,5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Skilling","Teleports You To Skilling zones",TDA,8,0,5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me","Cures Poison",TDA,9,0,5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70,"Hunter Kit", "Get a kit of hunting gear",TDA,10,0,5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555,  1, 0,0, 30012, 30004, 71,"Boss Teleport", "Teleport to various bosses.",TDA,11,0,5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72,"PK Teleport", "Teleport to various Pk zones..",TDA, 12,0,5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group","Cures Poison on players",TDA,13,0,5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74,"Stat Spy", "Cast on another player to see their\\nskill levels",TDA, 14,8,2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74,"Minigame Teleport", "Teleport to various Minigames.",TDA, 15,0,5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75,"Tele Group Barbarian", "Teleports players to the Barbarian\\noutpost",TDA, 16,0,5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make","Make glass without a furnace",TDA,17, 16,2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Catherby Teleport","Teleports you to Catherby",TDA,18,0,5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Catherby","Teleports players to Catherby",TDA,19,0,5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78,"Dream", "Take a rest and restore hitpoints 3\\n times faster",TDA, 20,0,5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery","String amulets without wool",TDA,21,0,5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80,"Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers",TDA, 22,0,5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue","Combine runes without a talisman",TDA,23,0,5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82,"Fertile Soil", "Fertilise a farming patch with super\\ncompost",TDA, 24, 4,2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83,"Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers",TDA, 25, 0,5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Wilderness Teleport","Teleports you to the Wilderness",TDA,26,0,5);
		addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing\\nGuild", "Teleports players to the Fishing\\nGuild",TDA, 27,0,5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make","Turn Logs into planks",TDA,28,16,5);
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport","Teleports you to Catherby",TDA,29,0,5);//END
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby","Teleports players to Catherby",TDA,30,0,5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport","Teleports you to Ice Plateau",TDA,31,0,5);
		addLunar3RunesBigBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice\\n Plateau","Teleports players to Ice Plateau",TDA,32,0,5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer","Spend hitpoints and SA Energy to\\n give another player hitpoints and run energy",TDA,33,8,2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other","Transfer up to 75% of hitpoints\\n to another player",TDA,34,8,2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other","Allows another player to rebound\\ndamage to an opponent",TDA,35,8,2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9,30009, 30006, 93, "Vengeance","Rebound damage to an opponent",TDA,36,0,5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group","Transfer up to 75% of hitpoints to a group",TDA,37,0,5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap","Change to another spellbook for 1\\nspell cast",TDA,38,0,5);
	}


	
	public static void constructLunar(){
		Widget Interface = addInterface(29999);
		setChildren(71, Interface);
		setBounds(30000, 11, 10, 0, Interface);
		setBounds(30017, 40, 9, 1, Interface);
		setBounds(30025, 71, 12, 2, Interface);
		setBounds(30032, 103, 10, 3, Interface);
		setBounds(30040, 135, 12, 4, Interface);
		setBounds(30048, 165, 10, 5, Interface);
		setBounds(30056, 8, 38, 6, Interface);
		setBounds(30064, 39, 39, 7, Interface);
		setBounds(30075, 71, 39, 8, Interface);
		setBounds(30083, 103, 39, 9, Interface);
		setBounds(30091, 135, 39, 10, Interface);
		setBounds(30099, 165, 37, 11, Interface);
		setBounds(30106, 12, 68, 12, Interface);
		setBounds(30114, 42, 68, 13, Interface);
		setBounds(30122, 71, 68, 14, Interface);
		setBounds(30130, 103, 68, 15, Interface);
		setBounds(30138, 135, 68, 16, Interface);
		setBounds(30146, 165, 68, 17, Interface);
		setBounds(30154, 14, 97, 18, Interface);
		setBounds(30162, 42, 97, 19, Interface);
		setBounds(30170, 71, 97, 20, Interface);
		setBounds(30178, 101, 97, 21, Interface);
		setBounds(30186, 135, 98, 22, Interface);
		setBounds(30194, 168, 98, 23, Interface);
		setBounds(30202, 11, 125, 24, Interface);
		setBounds(30210, 42, 124, 25, Interface);
		setBounds(30218, 74, 125, 26, Interface);
		setBounds(30226, 103, 125, 27, Interface);
		setBounds(30234, 135, 125, 28, Interface);
		setBounds(30242, 164, 126, 29, Interface);
		setBounds(30250, 10, 155, 30, Interface);	
		setBounds(30258, 42, 155, 31, Interface);	
		setBounds(30266, 71, 155, 32, Interface);	
		setBounds(30274, 103, 155, 33, Interface);
		setBounds(30282, 136, 155, 34, Interface);	
		setBounds(30290, 165, 155, 35, Interface);	
		setBounds(30298, 13, 185, 36, Interface);	
		setBounds(30306, 42, 185, 37, Interface);	
		setBounds(30314, 71, 184, 38, Interface);
		setBounds(30322, 104, 184, 39, Interface);	
		setBounds(30001, 6, 184, 40, Interface);//hover
		setBounds(30018, 5, 176, 41, Interface);//hover
		setBounds(30026, 5, 176, 42, Interface);//hover
		setBounds(30033, 5, 163, 43, Interface);//hover
		setBounds(30041, 5, 176, 44, Interface);//hover
		setBounds(30049, 5, 176, 45, Interface);//hover
		setBounds(30057, 5, 176, 46, Interface);//hover
		setBounds(30065, 5, 176, 47, Interface);//hover
		setBounds(30076, 5, 163, 48, Interface);//hover
		setBounds(30084, 5, 176, 49, Interface);//hover
		setBounds(30092, 5, 176, 50, Interface);//hover
		setBounds(30100, 5, 176, 51, Interface);//hover
		setBounds(30107, 5, 176, 52, Interface);//hover
		setBounds(30115, 5, 163, 53, Interface);//hover
		setBounds(30123, 5, 176, 54, Interface);//hover
		setBounds(30131, 5, 163, 55, Interface);//hover
		setBounds(30139, 5, 163, 56, Interface);//hover
		setBounds(30147, 5, 163, 57, Interface);//hover
		setBounds(30155, 5, 176, 58, Interface);//hover
		setBounds(30163, 5, 176, 59, Interface);//hover
		setBounds(30171, 5, 176, 60, Interface);//hover
		setBounds(30179, 5, 163, 61, Interface);//hover
		setBounds(30187, 5, 176, 62, Interface);//hover
		setBounds(30195, 5, 149, 63, Interface);//hover
		setBounds(30203, 5, 176, 64, Interface);//hover
		setBounds(30211, 5, 163, 65, Interface);//hover
		setBounds(30219, 5, 163, 66, Interface);//hover
		setBounds(30227, 5, 176, 67, Interface);//hover
		setBounds(30235, 5, 149, 68, Interface);//hover
		setBounds(30243, 5, 176, 69, Interface);//hover
		setBounds(30251, 5, 176, 70, Interface);//hover
	}
	
	
	
		
		public static void setBounds(int ID, int X, int Y, int frame, Widget RSinterface){
			RSinterface.children[frame] = ID;
			RSinterface.childX[frame] = X;
			RSinterface.childY[frame] = Y;
		}
		
		public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
			Widget Widget = addInterface(i);
			Widget.id = i;
			Widget.parent = i;
			Widget.type = 5;
			Widget.atActionType = AT;
			Widget.contentType = 0;
			Widget.opacity = 0;
			Widget.hoverType = 52;
			Widget.disabledSprite = imageLoader(j,name);
			Widget.enabledSprite = imageLoader(j,name);
			Widget.width = W;
			Widget.height = H;
			Widget.tooltip = S;
		}
		
		public static void createEquipment() {
			interfaceCache[1688].actions[2] = "Operate";
			Sprite sprite = new Sprite("Interfaces\\Equipment\\BOX 1");
			createButton(21341, 1644, 1, 0, sprite.myWidth, sprite.myHeight, (byte) 0, 0, sprite, sprite, "Equipment");
			sprite = new Sprite("Interfaces\\Equipment\\BOX 2");
			createButton(21342, 1644, 1, 0, sprite.myWidth, sprite.myHeight, (byte) 0, 0, sprite, sprite, "Items on Death");
			interfaceCache[21343].defaultText = "";
			interfaceCache[21344].defaultText = "";
			Widget rsi = interfaceCache[1644];
			for (int i = 0; i < rsi.children.length; i++) {
				if (rsi.children[i] == 21341) {
					rsi.childX[i] = 41;
					rsi.childY[i] = 207;
				} else if (rsi.children[i] == 21342) {
					rsi.childX[i] = 106;
					rsi.childY[i] = 207;
				}
			}
		}
		
		public static Widget createButton(int id, int parentId, int actionType, int contentType, int width, int height, byte opacity, int hoverInterface, Sprite disabledSprite, Sprite enabledSprite, String tooltip) {
			Widget rsi = interfaceCache[id] = new Widget();
			rsi.id = id;
			rsi.parent = parentId;
			rsi.type = 5;
			rsi.atActionType = actionType;
			rsi.contentType = contentType;
			rsi.width = width;
			rsi.height = height;
			rsi.opacity = opacity;
			rsi.hoverType = hoverInterface;
			rsi.disabledSprite = disabledSprite;
			rsi.enabledSprite = enabledSprite;
			rsi.tooltip = tooltip;
			return rsi;
		}
		
		public boolean newScroller;
	/**
	 * Start of newer implementations
	 */
	public static void addSpriteLoader(int childId, int spriteId) {
		Widget rsi = interfaceCache[childId] = new Widget();
		rsi.id = childId;
		rsi.parent = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.disabledSprite = Client.spriteCache.lookup(spriteId);
		rsi.enabledSprite = Client.spriteCache.lookup(spriteId);
		rsi.width = rsi.disabledSprite.myWidth;
		rsi.height = rsi.enabledSprite.myHeight - 2;
	}

	public static void configHoverButton(int id, String tooltip, int enabledSprite, int disabledSprite,
										 int enabledAltSprite, int disabledAltSprite, boolean active, int... buttonsToDisable) {
		Widget tab = addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = OPTION_OK;
		tab.type = TYPE_CONFIG_HOVER;
		tab.enabledSprite = Client.spriteCache.lookup(enabledSprite);
		tab.disabledSprite = Client.spriteCache.lookup(disabledSprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.enabledAltSprite = Client.spriteCache.lookup(enabledAltSprite);
		tab.disabledAltSprite = Client.spriteCache.lookup(disabledAltSprite);
		tab.buttonsToDisable = buttonsToDisable;
		tab.active = active;
		tab.spriteOpacity = 255;
	}

	public static void configButton(int id, String tooltip, int enabledSprite, int disabledSprite) {
		Widget tab = addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = OPTION_OK;
		tab.type = TYPE_CONFIG;
		tab.enabledSprite = Client.spriteCache.lookup(enabledSprite);
		tab.disabledSprite = Client.spriteCache.lookup(disabledSprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.active = false;
	}

	public static void hoverButton(int id, String tooltip, int enabledSprite, int disabledSprite) {
		hoverButton(id, tooltip, enabledSprite, disabledSprite, 255);
	}

	public static void hoverButton(int id, String tooltip, int enabledSprite, int disabledSprite, int opacity) {
		Widget tab = addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = 1;
		tab.type = TYPE_HOVER;
		tab.enabledSprite = Client.spriteCache.lookup(enabledSprite);
		tab.disabledSprite = Client.spriteCache.lookup(disabledSprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.toggled = false;
		tab.spriteOpacity = opacity;
	}

	public static void hoverButton(int id, String tooltip, int enabledSprite, int disabledSprite, String buttonText,
								   RSFont rsFont, int colour, int hoveredColour, boolean centerText) {
		Widget tab = addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = 1;
		tab.type = TYPE_HOVER;
		tab.enabledSprite = Client.spriteCache.lookup(enabledSprite);
		tab.disabledSprite = Client.spriteCache.lookup(disabledSprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.msgX = tab.width / 2;
		tab.msgY = (tab.height / 2) + 4;
		tab.defaultText = buttonText;
		tab.toggled = false;
		tab.rsFont = rsFont;
		tab.textColor = colour;
		tab.defaultHoverColor = hoveredColour;
		tab.centerText = centerText;
		tab.spriteOpacity = 255;
	}

	public static void slider(int id, double min, double max, int icon, int background, int contentType) {
		Widget widget = addInterface(id);
		widget.slider = new Slider(Client.spriteCache.lookup(icon), Client.spriteCache.lookup(background), min, max);
		widget.type = TYPE_SLIDER;
		widget.contentType = contentType;
	}

	public static void keybindingDropdown(int id, int width, int defaultOption, String[] options, Dropdown d,
										  boolean inverted) {
		Widget widget = addInterface(id);
		widget.type = TYPE_KEYBINDS_DROPDOWN;
		widget.dropdown = new DropdownMenu(width, true, defaultOption, options, d);
		widget.atActionType = OPTION_DROPDOWN;
		widget.inverted = inverted;
	}

	public static void dropdownMenu(int id, int width, int defaultOption, String[] options, Dropdown d) {
		dropdownMenu(id, width, defaultOption, options, d,
				new int[] { 0x0d0d0b, 0x464644, 0x473d32, 0x51483c, 0x787169 }, false);
	}

	public static void dropdownMenu(int id, int width, int defaultOption, String[] options, Dropdown d,
									int[] dropdownColours, boolean centerText) {
		Widget menu = addInterface(id);
		menu.type = TYPE_DROPDOWN;
		menu.dropdown = new DropdownMenu(width, false, defaultOption, options, d);
		menu.atActionType = OPTION_DROPDOWN;
		menu.dropdownColours = dropdownColours;
		menu.centerText = centerText;
	}

	public static void closeButton(int id, int enabledSprite, int disabledSprite) {
		Widget tab = addInterface(id);
		tab.atActionType = OPTION_CLOSE;
		tab.type = TYPE_HOVER;
		tab.enabledSprite = Client.spriteCache.lookup(enabledSprite);
		tab.disabledSprite = Client.spriteCache.lookup(disabledSprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.toggled = false;
		tab.spriteOpacity = 255;
	}

	public static void handleConfigHover(Widget widget) {
		if (widget.active) {
			return;
		}
		widget.active = true;

		configHoverButtonSwitch(widget);
		disableOtherButtons(widget);
	}

	public static void configHoverButtonSwitch(Widget widget) {
		Sprite[] backup = new Sprite[] { widget.enabledSprite, widget.disabledSprite };

		widget.enabledSprite = widget.enabledAltSprite;
		widget.disabledSprite = widget.disabledAltSprite;

		widget.enabledAltSprite = backup[0];
		widget.disabledAltSprite = backup[1];
	}

	public static void disableOtherButtons(Widget widget) {
		if (widget.buttonsToDisable == null) {
			return;
		}
		for (int btn : widget.buttonsToDisable) {
			Widget btnWidget = interfaceCache[btn];

			if (btnWidget.active) {

				btnWidget.active = false;
				configHoverButtonSwitch(btnWidget);
			}
		}
	}
}
