// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ItemDefinition {

	public static void clear() {
		models = null;
		sprites = null;
		streamIndices = null;
		cache = null;
		item_data = null;
	}

	private int anInt174;
	private int anInt181;
	private int anInt198;
	private int anInt190;
	private int anInt169;
	private int anInt194;
	private int[] anInt160;
	private int[] anInt156;

	public boolean isDialogueModelCached(int gender) {
		int model_1 = equipped_model_male_dialogue_1;
		int model_2 = equipped_model_male_dialogue_2;
		if (gender == 1) {
			model_1 = equipped_model_female_dialogue_1;
			model_2 = equipped_model_female_dialogue_2;
		}
		if (model_1 == -1)
			return true;
		boolean cached = true;
		if (!Model.isCached(model_1))
			cached = false;
		if (model_2 != -1 && !Model.isCached(model_2))
			cached = false;
		return cached;
	}

	public static void init(FileArchive fileArchive) {
		item_data = new Buffer(fileArchive.readFile("obj.dat"));
		Buffer buffer = new Buffer(
				fileArchive.readFile("obj.idx"));
		totalItems = buffer.readShort();
		streamIndices = new int[totalItems + 5000];
		int i = 2;
		for (int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += buffer.readShort();
		}

		cache = new ItemDefinition[10];
		for (int k = 0; k < 10; k++)
			cache[k] = new ItemDefinition();
	}

	public Model getChatEquipModel(int gender) {
		int dialogueModel = equipped_model_male_dialogue_1;
		int dialogueHatModel = equipped_model_male_dialogue_2;
		if (gender == 1) {
			dialogueModel = equipped_model_female_dialogue_1;
			dialogueHatModel = equipped_model_female_dialogue_2;
		}
		if (dialogueModel == -1)
			return null;
		Model dialogueModel_ = Model.getModel(dialogueModel);
		if (dialogueHatModel != -1) {
			Model hatModel_ = Model.getModel(dialogueHatModel);
			Model models[] = {dialogueModel_, hatModel_};
			dialogueModel_ = new Model(2, models);
		}
		if (modified_model_colors != null) {
			for (int i1 = 0; i1 < modified_model_colors.length; i1++)
				dialogueModel_.recolor(modified_model_colors[i1], original_model_colors[i1]);

		}
		return dialogueModel_;
	}

	public boolean isEquippedModelCached(int gender) {
		int primaryModel = equipped_model_male_1;
		int secondaryModel = equipped_model_male_2;
		int emblem = equipped_model_male_3;
		if (gender == 1) {
			primaryModel = equipped_model_female_1;
			secondaryModel = equipped_model_female_2;
			emblem = equipped_model_female_3;
		}
		if (primaryModel == -1)
			return true;
		boolean cached = true;
		if (!Model.isCached(primaryModel))
			cached = false;
		if (secondaryModel != -1 && !Model.isCached(secondaryModel))
			cached = false;
		if (emblem != -1 && !Model.isCached(emblem))
			cached = false;
		return cached;
	}

	public Model getEquippedModel(int gender) {
		int primaryModel = equipped_model_male_1;
		int secondaryModel = equipped_model_male_2;
		int emblem = equipped_model_male_3;

		if (gender == 1) {
			primaryModel = equipped_model_female_1;
			secondaryModel = equipped_model_female_2;
			emblem = equipped_model_female_3;
		}

		if (primaryModel == -1)
			return null;
		Model primaryModel_ = Model.getModel(primaryModel);
		if (secondaryModel != -1)
			if (emblem != -1) {
				Model secondaryModel_ = Model.getModel(secondaryModel);
				Model emblemModel = Model.getModel(emblem);
				Model models[] = {primaryModel_, secondaryModel_, emblemModel};
				primaryModel_ = new Model(3, models);
			} else {
				Model model_2 = Model.getModel(secondaryModel);
				Model models[] = {primaryModel_, model_2};
				primaryModel_ = new Model(2, models);
			}
		if (gender == 0 && equipped_model_male_translation_y != 0)
			primaryModel_.translate(0, equipped_model_male_translation_y, 0);
		if (gender == 1 && equipped_model_female_translation_y != 0)
			primaryModel_.translate(0, equipped_model_female_translation_y, 0);

		if (modified_model_colors != null) {
			for (int i1 = 0; i1 < modified_model_colors.length; i1++)
				primaryModel_.recolor(modified_model_colors[i1], original_model_colors[i1]);

		}
		return primaryModel_;
	}

	public void setDefaults() {
		inventory_model = 0;
		name = null;
		description = null;
		modified_model_colors = null;
		original_model_colors = null;
		modelZoom = 2000;
		rotation_y = 0;
		rotation_x = 0;
		rotation_z = 0;
		translate_x = 0;
		translate_yz = 0;
		stackable = false;
		value = 1;
		is_members_only = false;
		groundActions = null;
		actions = null;
		equipped_model_male_1 = -1;
		equipped_model_male_2 = -1;
		equipped_model_male_translation_y = 0;
		equipped_model_female_1 = -1;
		equipped_model_female_2 = -1;
		equipped_model_female_translation_y = 0;
		equipped_model_male_3 = -1;
		equipped_model_female_3 = -1;
		equipped_model_male_dialogue_1 = -1;
		equipped_model_male_dialogue_2 = -1;
		equipped_model_female_dialogue_1 = -1;
		equipped_model_female_dialogue_2 = -1;
		stack_variant_id = null;
		stack_variant_size = null;
		unnoted_item_id = -1;
		noted_item_id = -1;
		model_scale_x = 128;
		model_scale_y = 128;
		model_scale_z = 128;
		light_intensity = 0;
		light_mag = 0;
		team = 0;
	}

	public static ItemDefinition lookup(int i) {
		for (int j = 0; j < 10; j++)
			if (cache[j].id == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 10;
		ItemDefinition itemDefinition = cache[cacheIndex];
		item_data.currentPosition = streamIndices[i];
		itemDefinition.id = i;
		itemDefinition.setDefaults();
		itemDefinition.readValues(item_data);
		if (itemDefinition.noted_item_id != -1)
			itemDefinition.toNote();
		if (!isMembers && itemDefinition.is_members_only) {
			itemDefinition.name = "Members Object";
			itemDefinition.description = "Login to a members' server to use this object."
					.getBytes();
			itemDefinition.groundActions = null;
			itemDefinition.actions = null;
			itemDefinition.team = 0;
		}
		switch (itemDefinition.id) {
			case 11722:
				itemDefinition.equipped_model_female_1 = itemDefinition.equipped_model_male_1;
				itemDefinition.equipped_model_female_2 = itemDefinition.equipped_model_male_2;
			break;
			
			case 13890:
				itemDefinition.equipped_model_female_1 = itemDefinition.equipped_model_male_1;
				itemDefinition.equipped_model_female_2 = itemDefinition.equipped_model_male_2;
			break;				
		}
		switch (i) {
			case 9470:
				itemDefinition.equipped_model_male_1 = lookup(9470).equipped_model_female_1;
				itemDefinition.equipped_model_female_1 = lookup(9470).equipped_model_female_1;
			break;
			
			case 11777:
				itemDefinition.actions = lookup(981).actions;
				itemDefinition.inventory_model = lookup(981).inventory_model;
				itemDefinition.equipped_model_male_1 = lookup(981).equipped_model_male_1;
				itemDefinition.equipped_model_female_1 = lookup(981).equipped_model_female_1;
				itemDefinition.modelZoom = lookup(981).modelZoom;
				itemDefinition.rotation_y = lookup(981).rotation_y;
				itemDefinition.rotation_x = lookup(981).rotation_x;
				itemDefinition.translate_x = lookup(981).translate_x;
				itemDefinition.translate_yz = lookup(981).translate_yz;
				itemDefinition.name = lookup(981).name;
				itemDefinition.description = lookup(981).description;
			break;
		
			case 15154:
				itemDefinition.inventory_model = 65270;
				itemDefinition.name = "Completionist cape";
				itemDefinition.description = "This cape shows your dedication. You'renderable an @red@Allstar Legend.".getBytes();
				itemDefinition.modelZoom = 1385;
				itemDefinition.rotation_y = 252;
				itemDefinition.rotation_x = 1020;
				itemDefinition.translate_x = -1;
				itemDefinition.translate_yz = 24;
				itemDefinition.equipped_model_male_1 = 65295;
				itemDefinition.equipped_model_female_1 = 65316;
				itemDefinition.groundActions = new String[5];
				itemDefinition.groundActions[2] = "Take";
				itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Customise";
				itemDefinition.actions[3] = "Features";
				itemDefinition.actions[4] = "Destroy";
				break;
			
			case 15747:
				itemDefinition.inventory_model = 65262;
				itemDefinition.name = "Max Cape";
				itemDefinition.description = "A cape worn by those who have achieved max XP in all skills.".getBytes();
				itemDefinition.modelZoom = 1385;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 24;
				itemDefinition.rotation_y = 279;
				itemDefinition.rotation_x = 948;
				itemDefinition.equipped_model_male_1 = 65300;
				itemDefinition.equipped_model_female_1 = 65322;
				itemDefinition.groundActions = new String[5];
				itemDefinition.groundActions[2] = "Take";
				itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
			break;
			
			case 15117:
				itemDefinition.inventory_model = 65261;
				itemDefinition.name = "Veteran cape";
				itemDefinition.description = "A cape given to those worthy of Veteran Status.".getBytes();
				itemDefinition.modelZoom = 1513;
				itemDefinition.rotation_y = 279;
				itemDefinition.rotation_x = 948;
				itemDefinition.translate_x = -3;
				itemDefinition.translate_yz = 24;
				itemDefinition.equipped_model_male_1 = 65305;
				itemDefinition.equipped_model_female_1 = 65318;
				itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
			break;
			
		case 15888:
				itemDefinition.name = "Donor cape";
		        itemDefinition.modelZoom = 2128;
		        itemDefinition.rotation_y = 504;
		        itemDefinition.rotation_x = 0;
		        itemDefinition.translate_x = 0;
		        itemDefinition.translate_yz = 1;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
		        itemDefinition.inventory_model = 4;
		        itemDefinition.equipped_model_male_1 = 5;
		        itemDefinition.equipped_model_female_1 = 5;
		        break; 
			case 15887:
				itemDefinition.name = "Extreme donor cape";
		        itemDefinition.modelZoom = 2128;
		        itemDefinition.rotation_y = 504;
		        itemDefinition.rotation_x = 0;
		        itemDefinition.translate_x = 0;
		        itemDefinition.translate_yz = 1;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
		        itemDefinition.inventory_model = 6;
		        itemDefinition.equipped_model_male_1 = 7;
		        itemDefinition.equipped_model_female_1 = 7;
		        break; 
				
			
			case 15746:
				itemDefinition.inventory_model = 65257;
				itemDefinition.name = "Classic Cape";
				itemDefinition.description = "A cape worn by those who've seen the world in a different light.".getBytes();
				itemDefinition.modelZoom = 1385;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 24;
				itemDefinition.rotation_y = 279;
				itemDefinition.rotation_x = 948;
				itemDefinition.equipped_model_male_1 = 65302;
				itemDefinition.equipped_model_female_1 = 65327;
				itemDefinition.groundActions = new String[5];
				itemDefinition.groundActions[2] = "Take";
				itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
			break;
			case 13785:
				itemDefinition.name = "Armadyl crossbow";
				itemDefinition.modelZoom = 1325;
				itemDefinition.rotation_y = 240;
				itemDefinition.rotation_x = 110;
				itemDefinition.translate_x = -6;
				itemDefinition.translate_yz = -40;
				itemDefinition.original_model_colors = new int[] { 115, 107, 10167, 10171 };
				itemDefinition.modified_model_colors = new int[] { 5409, 5404, 6449, 7390 };
				itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
				itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 19967;
				itemDefinition.equipped_model_male_1 = 19839;
				itemDefinition.equipped_model_female_1 = 19839;
			break;
			case 962:
				itemDefinition.name = "Christmas Cracker";
				itemDefinition.modelZoom = 630;
				itemDefinition.rotation_y = 272;
				itemDefinition.rotation_x = 1728;
				itemDefinition.translate_x = -3;
				itemDefinition.translate_yz = 40;
				itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { "Open", null, null, null, "Drop" };
				itemDefinition.inventory_model = 2637;
				itemDefinition.description = "Said to award the player with one random color Party-Hat".getBytes();
				break;
			
			case 15900:
				itemDefinition.name = "Mask Of Balance";
				itemDefinition.modelZoom = 1032;
				itemDefinition.rotation_y = 386;
				itemDefinition.rotation_x = 1995;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 4;
				itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
				itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70196;
				itemDefinition.equipped_model_male_1 = 70197;
				itemDefinition.equipped_model_female_1 = 70197;
				break;
			case 15899:
				itemDefinition.name = "Hornwood Helm";
				itemDefinition.modelZoom = 1040;
				itemDefinition.rotation_y = 12;
				itemDefinition.rotation_x = 234;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
				itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
				itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70193;
				itemDefinition.equipped_model_male_1 = 70194;
				itemDefinition.equipped_model_female_1 = 70195;
				break;
			
			
			case 15907:
	            itemDefinition.name = "Stale Baguette";
	            itemDefinition.modelZoom = 1474;
	            itemDefinition.rotation_y = 539;
	            itemDefinition.rotation_x = 552;
	            itemDefinition.translate_x = 4;
	            itemDefinition.translate_yz = -5;
	            itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
	            itemDefinition.inventory_model = 70215;
	            itemDefinition.equipped_model_female_1 = 70216;
	            itemDefinition.equipped_model_male_1 = 70216;
	            break;
			
	        case 15875:
	            itemDefinition.name = "Candy cane";
	            itemDefinition.modelZoom = 1726;
	            itemDefinition.rotation_y = 1576;
	            itemDefinition.rotation_x = 242;
	            itemDefinition.translate_x = 5;
	            itemDefinition.translate_yz = 4;
	            itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
	            itemDefinition.inventory_model = 51166;
	            itemDefinition.equipped_model_female_1 = 51311;
	            itemDefinition.equipped_model_male_1 = 51310;
	            break;
	        
	            
	        
	        case 15868:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 32799;
				itemDefinition.name = "Twisted bow";
				itemDefinition.modelZoom = 2000;
				itemDefinition.rotation_y = 720;
				itemDefinition.rotation_x = 1500;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = 1;
				itemDefinition.equipped_model_male_1 = 32674;
				itemDefinition.equipped_model_female_1 = 32674;
				itemDefinition.description = "A mystical bow carved from the twisted remains of the Great Olm.".getBytes();
				break;
	        case 15867:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 32794;
				itemDefinition.name = "Ancestral hat";
				itemDefinition.modelZoom = 1236;
				itemDefinition.rotation_y = 118;
				itemDefinition.rotation_x = 10;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = -12;
			    itemDefinition.equipped_model_female_1 = 32663;
				itemDefinition.equipped_model_male_1 = 32663;
				itemDefinition.description = "The hat of a powerful sorceress from a bygone era.".getBytes();
				break;
	        
	        case 15819:
	        	itemDefinition.name = "Abyssal Dagger P++";
	            itemDefinition.modelZoom = 1347;
                itemDefinition.rotation_y = 1589;
                itemDefinition.rotation_x = 781;
                itemDefinition.translate_x = -5;
                itemDefinition.translate_yz = 3;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70172;
                itemDefinition.equipped_model_female_1 = 70173;
                itemDefinition.equipped_model_male_1 = 70173;
	        break;
	        case 12531:
	        	itemDefinition.name = "Rainbow scarf";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70274;
	        	itemDefinition.rotation_y = 373;
	            itemDefinition.rotation_x = 73;
	            itemDefinition.translate_x = -8;
	            itemDefinition.translate_yz = 9;
	        	itemDefinition.equipped_model_male_1 = 70275;
	        	itemDefinition.equipped_model_female_1 = 70276;
	        	break;
	        case 12532:
	        	itemDefinition.name = "Obsidian helmet";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70277;
	        	itemDefinition.rotation_x = 1743;
				itemDefinition.modelZoom = 789;
				itemDefinition.translate_x = -4;
				itemDefinition.translate_yz = -3;
				itemDefinition.rotation_y = 0;
	        	itemDefinition.equipped_model_male_1 = 70278;
	        	itemDefinition.equipped_model_female_1 = 70278;
	        	break;
	        case 12533:
	        	itemDefinition.name = "Obsidian platelegs";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70279;
	        	itemDefinition.modelZoom = 1625;
				itemDefinition.rotation_y = 355;
				itemDefinition.rotation_x = 2046;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
	        	itemDefinition.equipped_model_male_1 = 70280;
	        	itemDefinition.equipped_model_female_1 = 70281;
	        	break;
	        case 12534:
	        	itemDefinition.name = "Obsidian platebody";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70282;
	        	itemDefinition.modelZoom = 1625;
				itemDefinition.rotation_y = 355;
				itemDefinition.rotation_x = 2046;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
	        	itemDefinition.equipped_model_male_1 = 70283;
	        	itemDefinition.equipped_model_female_1 = 70284;
	        	itemDefinition.equipped_model_male_2 = 70285; //male arms
				itemDefinition.equipped_model_female_2 = 70286; //female arms
	        	break;
	        case 12535:
	        	itemDefinition.name = "Hand fan";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70287;
	        	itemDefinition.modelZoom = 1730;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70288;
	        	itemDefinition.equipped_model_female_1 = 70289;
	        	break;
	        case 12536:
	        	itemDefinition.name = "Sack of presents";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70290;
	        	itemDefinition.modelZoom = 1730;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70291;
	        	itemDefinition.equipped_model_female_1 = 70291;
	        	break;
	        case 12537:
	        	itemDefinition.name = "Giant present";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70292;
	        	itemDefinition.modelZoom = 1730;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70293;
	        	itemDefinition.equipped_model_female_1 = 70293;
	        	break;
	        case 12538:
	        	itemDefinition.name = "Twisted buckler";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70294;
	        	itemDefinition.modelZoom = 1000;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70295;
	        	itemDefinition.equipped_model_female_1 = 70295;
	        	break;
	        case 12539:
	        	itemDefinition.name = "Dragon sword";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70296;
	        	itemDefinition.modelZoom = 1250;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70297;
	        	itemDefinition.equipped_model_female_1 = 70297;
	        	break;
	        case 12540:
	        	itemDefinition.name = "Kodai wand";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70298;
	        	itemDefinition.modelZoom = 1000;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70299;
	        	itemDefinition.equipped_model_female_1 = 70299;
	        	break;
	        case 12541:
	        	itemDefinition.name = "Elder maul";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70300;
	        	itemDefinition.modelZoom = 2000;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70301;
	        	itemDefinition.equipped_model_female_1 = 70301;
	        	break;
	        
	        case 12544:
	        	itemDefinition.name = "Hunting knife";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70305;
	        	itemDefinition.modelZoom = 750;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70306;
	        	itemDefinition.equipped_model_female_1 = 70306;
	        	break;
	        case 12545:
	        	itemDefinition.name = "Banshee mask";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70307;
	        	itemDefinition.modelZoom = 750;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70308;
	        	itemDefinition.equipped_model_female_1 = 70309;
	        	break;
	        case 12546:
	        	itemDefinition.name = "Banshee top";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70310;
	        	itemDefinition.modelZoom = 1400;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70311;
	        	itemDefinition.equipped_model_female_1 = 70313;
	        	itemDefinition.equipped_model_male_2 = 70312; //male arms
				itemDefinition.equipped_model_female_2 = 70314; //female arms
	        	break;
	        case 12547:
	        	itemDefinition.name = "Banshee robe";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70315;
	        	itemDefinition.modelZoom = 1550;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 120;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70316;
	        	itemDefinition.equipped_model_female_1 = 70317;
	        	break;
	        case 12548:
	        	itemDefinition.name = "Dragon warhammer";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70318;
	        	itemDefinition.modelZoom = 1250;
	        	itemDefinition.rotation_y = 344;
	        	itemDefinition.rotation_x = 456;
	        	itemDefinition.translate_x = 3;
	        	itemDefinition.translate_yz = 3;
	        	itemDefinition.equipped_model_male_1 = 70319;
	        	itemDefinition.equipped_model_female_1 = 70320;
	        	break;
	        
	        case 12550:
	        	itemDefinition.name = "Ancient wyvern shield";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70322;
	        	itemDefinition.modelZoom = 1750;
	        	itemDefinition.rotation_y = 352;
	        	itemDefinition.rotation_x = 1000;
	        	itemDefinition.translate_x = 1;
	        	itemDefinition.translate_yz = 10;
	        	itemDefinition.equipped_model_male_1 = 70323;
	        	itemDefinition.equipped_model_female_1 = 70323;
	        	break;
	        case 12551:
	        	itemDefinition.name = "Granite boots";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70325;
	        	itemDefinition.modelZoom = 770;
				itemDefinition.rotation_y = 164;
				itemDefinition.rotation_x = 156;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = -3;
	        	itemDefinition.equipped_model_male_1 = 70326;
	        	itemDefinition.equipped_model_female_1 = 70327;
	        	break;
	        case 12552:
	        	itemDefinition.name = "Granite longsword";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70328;
	        	itemDefinition.modelZoom = 1744;
				itemDefinition.rotation_y = 738;
				itemDefinition.rotation_x = 1985;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
	        	itemDefinition.equipped_model_male_1 = 70329;
	        	itemDefinition.equipped_model_female_1 = 70329;
	        	break;
	        case 12553:
	        	itemDefinition.name = "Jasons mask";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70330;
	        	itemDefinition.modelZoom = 550;
                itemDefinition.rotation_y = 593;
                itemDefinition.rotation_x = 13;
                itemDefinition.translate_yz = 17;
	        	itemDefinition.equipped_model_male_1 = 70331;
	        	itemDefinition.equipped_model_female_1 = 70332;
	        	break;
	        	case 12557:
	        	itemDefinition.name = "Granite gloves";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70339;
	        	itemDefinition.modelZoom = 789;
				itemDefinition.rotation_y = 609;
				itemDefinition.rotation_x = 111;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 0;
	        	itemDefinition.equipped_model_male_1 = 70340;
	        	itemDefinition.equipped_model_female_1 = 70341;
	        	break;
	        case 12558:
	        	itemDefinition.name = "Wise old man santa";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70342;
	        	itemDefinition.modelZoom = 600;
				itemDefinition.rotation_x = 76;
				itemDefinition.rotation_y = 1852;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 0;
	        	itemDefinition.equipped_model_male_1 = 70343;
	        	itemDefinition.equipped_model_female_1 = 70344;
	        	break;
	        case 12559:
	        	itemDefinition.name = "Mythical cape";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70345;
	        	itemDefinition.modelZoom = 2128;
            	itemDefinition.rotation_y = 504;
            	itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 1;
	        	itemDefinition.equipped_model_male_1 = 70346;
	        	itemDefinition.equipped_model_female_1 = 70347;
	        	break;
	        	case 12580:
	        	 itemDefinition.name = "Black Tourmaline Core";
	        	   itemDefinition.modelZoom = 1750;
	        	   itemDefinition.rotation_y = 567;
	        	   itemDefinition.rotation_x = 152;
	        	   itemDefinition.translate_x = -5;
	        	   itemDefinition.translate_yz = -5;
	        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
	        	   itemDefinition.inventory_model = 70402;
	        	  break;
	        	  
	        case 12581:
	        	itemDefinition.name = "Guardian boots";
		        itemDefinition.modelZoom = 750;
		        itemDefinition.rotation_y = 147;
		        itemDefinition.rotation_x = 279;
		        itemDefinition.translate_x = 5;
		        itemDefinition.translate_yz = -5;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
		        itemDefinition.inventory_model = 70403;
		        itemDefinition.equipped_model_male_1 = 70404;
		        itemDefinition.equipped_model_female_1 = 70405;
		        break; 
		        
	        case 12582:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70406;
				itemDefinition.name = "clue hunter gloves";
				itemDefinition.modelZoom = 789;
				itemDefinition.rotation_y = 609;
				itemDefinition.rotation_x = 111;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 0;
				itemDefinition.equipped_model_female_1 = 70408;
				itemDefinition.equipped_model_male_1 = 70407;
				itemDefinition.description = "A pair of gloves made for a master clue hunter".getBytes();
				break;
				
	        case 12583:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70409;
				itemDefinition.name = "clue hunter boots";
				itemDefinition.modelZoom = 1000;
				itemDefinition.rotation_y = 164;
				itemDefinition.rotation_x = 156;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = -3;
				itemDefinition.equipped_model_female_1 = 70411;
				itemDefinition.equipped_model_male_1 = 70410;
				itemDefinition.description = "A pair of boots made for a master clue hunter".getBytes();
				break;
	        	
	        case 12584:
	        	itemDefinition.name = "clue hunter cloak";
	        	itemDefinition.actions = new String[5];
	        	itemDefinition.actions[1] = "Wear";
	        	itemDefinition.inventory_model = 70412;
	        	itemDefinition.modelZoom = 2128;
            	itemDefinition.rotation_y = 504;
            	itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 1;
	        	itemDefinition.equipped_model_male_1 = 70413;
	        	itemDefinition.equipped_model_female_1 = 70414;
	        	itemDefinition.description = "A cloak made for a master clue hunter".getBytes();
	        	break;
				
	        case 12585:
	        	itemDefinition.name = "clue hunter trousers";
	        	itemDefinition.rotation_y = 525;
            	itemDefinition.rotation_x = 171;
                itemDefinition.translate_x = 10;
                itemDefinition.translate_yz = 10;
                itemDefinition.modelZoom = 1744;
                itemDefinition.inventory_model = 70263;
                itemDefinition.equipped_model_male_1 = 70416;
                itemDefinition.equipped_model_female_1 = 70417;
                itemDefinition.actions = new String[5];
                itemDefinition.actions[1] = "Wear";
                itemDefinition.actions[4] = "Drop";
                itemDefinition.description = "A pair of trousers made for a master clue hunter".getBytes();
               break;
               
	        case 12586:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70418;
				itemDefinition.name = "Clue hunter Garb";
				itemDefinition.modelZoom = 1375;
				itemDefinition.rotation_y = 553;
				itemDefinition.rotation_x = 0;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70420;
				itemDefinition.equipped_model_male_1 = 70419;
				itemDefinition.equipped_model_male_2 = 70421;//male arms
				itemDefinition.equipped_model_female_2 = 70422;//female arms
				itemDefinition.description = "A garb made for the master clue hunter".getBytes();
				break;
				
	        case 12587:
	        	 itemDefinition.name = "helm of raedwald";
	        	 itemDefinition.modelZoom = 640;
	                itemDefinition.rotation_y = 108;
	                itemDefinition.rotation_x = 156;
	                itemDefinition.translate_x = 1;
	                itemDefinition.translate_yz = -4;
	                itemDefinition.actions = new String[5];
					itemDefinition.actions[1] = "Wear";
					itemDefinition.actions[4] = "Drop";
	        	   itemDefinition.inventory_model = 70423;
	        	   itemDefinition.equipped_model_male_1 = 70424;
	        	   itemDefinition.equipped_model_female_1 = 70425;
	        	   break;
	        	   
	        case 12588:
	        	itemDefinition.name = "Western banner 1";
	        	itemDefinition.modelZoom = 2060;
                itemDefinition.rotation_y = 464;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 6;
                itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
                itemDefinition.inventory_model = 70426;
                itemDefinition.equipped_model_female_1 = 70427;
                itemDefinition.equipped_model_male_1 = 70427;
	        break;
	        
	        case 12589:
	        	itemDefinition.name = "Western banner 2";
	        	itemDefinition.modelZoom = 2060;
                itemDefinition.rotation_y = 464;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 6;
                itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
                itemDefinition.inventory_model = 70428;
                itemDefinition.equipped_model_female_1 = 70429;
                itemDefinition.equipped_model_male_1 = 70429;
	        	break;
	        	
	        case 12590:
	        	itemDefinition.name = "Western banner 3";
	        	itemDefinition.modelZoom = 2060;
                itemDefinition.rotation_y = 464;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 6;
                itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
                itemDefinition.inventory_model = 70430;
                itemDefinition.equipped_model_female_1 = 70431;
                itemDefinition.equipped_model_male_1 = 70431;
	        	break;
	        	
	        case 12591:
	        	itemDefinition.name = "Western banner 4";
	        	itemDefinition.modelZoom = 2060;
                itemDefinition.rotation_y = 464;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 6;
                itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
                itemDefinition.inventory_model = 70432;
                itemDefinition.equipped_model_female_1 = 70433;
                itemDefinition.equipped_model_male_1 = 70433;
	        	break;
	        case 12592:
	        	itemDefinition.name = "Scythe of Vitur";
                itemDefinition.modelZoom = 2105;
                itemDefinition.rotation_y = 327;
                itemDefinition.rotation_x = 23;
                itemDefinition.translate_x = 1;
                itemDefinition.translate_yz = 17;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70435;
                itemDefinition.equipped_model_female_1 = 70436;
                itemDefinition.equipped_model_male_1 = 70436;
	        break;
	        case 12593:
	        	itemDefinition.name = "Ghrazi rapier";
                itemDefinition.modelZoom = 2064;
                itemDefinition.rotation_y = 1603;
                itemDefinition.rotation_x = 552;
                itemDefinition.translate_x = 5;
                itemDefinition.translate_yz = -18;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70437;
                itemDefinition.equipped_model_female_1 = 70438;
                itemDefinition.equipped_model_male_1 = 70439;
	        break;
	        case 12594:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70440;
				itemDefinition.name = "Justiciar faceguard";
				itemDefinition.modelZoom = 777;
				itemDefinition.rotation_y = 22;
				itemDefinition.rotation_x = 1972;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70442;
				itemDefinition.equipped_model_male_1 = 70442;
				
				break;
	        case 12595:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70443;
				itemDefinition.name = "Justiciar chestplate";
				ItemDefinition plateBody = lookup(1115);
				itemDefinition.modelZoom = 1310;
				itemDefinition.rotation_y = 432;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 4;
				itemDefinition.equipped_model_female_1 = 70444;
				itemDefinition.equipped_model_male_1 = 70445;
				
				break;
	        case 12597:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70446;
				itemDefinition.name = "Justiciar legguards";
				itemDefinition.modelZoom = 1720;
				itemDefinition.rotation_y = 468;
				itemDefinition.equipped_model_female_1 = 70447;
				itemDefinition.equipped_model_male_1 = 70448;
				
				break;
	        case 12599:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70449;
				itemDefinition.name = "Sanguinesti staff ";
				itemDefinition.modelZoom = 2258;
				itemDefinition.rotation_y = 552;
				itemDefinition.rotation_x = 1558;
				itemDefinition.translate_x = -5;
				itemDefinition.translate_yz = -3;
				itemDefinition.equipped_model_female_1 = 70450;
				itemDefinition.equipped_model_male_1 = 70450;
				
				break;
	        case 12999:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70451;
				itemDefinition.name = "Avernic defender";
				itemDefinition.modelZoom = 717;
				itemDefinition.rotation_y = 498;
				itemDefinition.rotation_x = 256;
				itemDefinition.translate_x = 8;
				itemDefinition.translate_yz = 8;
				itemDefinition.equipped_model_female_1 = 70452;
				itemDefinition.equipped_model_male_1 = 70453;
				
				break;
				
	        case 13001:
	        	itemDefinition.inventory_model = 35777; // Drop/Inv Model
	        	itemDefinition.equipped_model_male_1 = 35768; // Drop/Inv Model
	        	itemDefinition.equipped_model_female_1 = 35768; // Drop/Inv Model
	        	itemDefinition.modelZoom = 2128;
	        	itemDefinition.rotation_x = 552;
	        	itemDefinition.rotation_y = 1508;
	        	itemDefinition.name = "Craw's bow"; // Item Name
	        	itemDefinition.description = "This bow once belonged to a formidable follower of Armadyl.".getBytes(); // Item
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
	        	itemDefinition.translate_x = -10;
	        	itemDefinition.translate_yz = -10;
	        		    break;
	        case 15925:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70454;
				itemDefinition.name = "Angler hat";
				itemDefinition.modelZoom = 842;
				itemDefinition.rotation_y = 2047;
				itemDefinition.rotation_x = 1751;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = -4;
				itemDefinition.equipped_model_female_1 = 70455;
				itemDefinition.equipped_model_male_1 = 70456;
				break;
	        case 15926:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70457;
				itemDefinition.name = "Angler top";
				itemDefinition.modelZoom = 1284;
				itemDefinition.rotation_y = 579;
				itemDefinition.rotation_x = 2047;
				itemDefinition.translate_x = 5;
				itemDefinition.equipped_model_female_1 = 70458;
				itemDefinition.equipped_model_male_1 = 70459;
				itemDefinition.equipped_model_male_2 = 70460;
				itemDefinition.equipped_model_female_2 = 70461;
				break;
	        case 15927:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70462;
				itemDefinition.name = "Angler waders";
				itemDefinition.modelZoom = 2042;
				itemDefinition.rotation_y = 1522;
				itemDefinition.rotation_x = 1024;
				itemDefinition.translate_x = -1;
				itemDefinition.equipped_model_female_1 = 70463;
				itemDefinition.equipped_model_male_1 = 70464;
				break;
	        case 15928:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDefinition.inventory_model = 70465;
				itemDefinition.name = "Angler boots";
				itemDefinition.modelZoom = 905;
				itemDefinition.rotation_y = 202;
				itemDefinition.rotation_x = 283;
				itemDefinition.translate_x = 4;
				itemDefinition.translate_yz = -4;
				itemDefinition.equipped_model_female_1 = 70466;
				itemDefinition.equipped_model_male_1 = 70467;
				break;
	        case 15820:
	        	itemDefinition.name = "Abyssal Bludgeon";
                itemDefinition.modelZoom = 2611;
                itemDefinition.rotation_y = 1508;
                itemDefinition.rotation_x = 552;
                itemDefinition.translate_x = -17;
                itemDefinition.translate_yz = 3;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70170;
                itemDefinition.equipped_model_female_1 = 70171;
                itemDefinition.equipped_model_male_1 = 70171;
	        break;
	        case 9005:
	        	itemDefinition.name = "Fancy boots";
	        	itemDefinition.inventory_model = 70434;
	        	itemDefinition.modelZoom = 770;
				itemDefinition.rotation_y = 164;
				itemDefinition.rotation_x = 156;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = -3;
				itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
	        	break;
	       
	        case 15821:
	        	itemDefinition.name = "Abyssal Dagger";
                itemDefinition.modelZoom = 1347;
                itemDefinition.rotation_y = 1589;
                itemDefinition.rotation_x = 781;
                itemDefinition.translate_x = -5;
                itemDefinition.translate_yz = 3;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70168;
                itemDefinition.equipped_model_female_1 = 70169;
                itemDefinition.equipped_model_male_1 = 70169;
                //itemDefinition.original_model_colors = new int[1];
				//itemDefinition.recolourTarget = new int[1];
				//itemDefinition.original_model_colors = new int[] { 10};;
		        //itemDefinition.recolourTarget = new int[] { 0};
                break;
	        
	        case 15823:
	        	itemDefinition.name = "Black Boxing Gloves";
                itemDefinition.modelZoom = 789;
                itemDefinition.rotation_y = 346;
                itemDefinition.rotation_x = 1674;
                itemDefinition.translate_x = 4;
                itemDefinition.translate_yz = 15;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 12277;
                itemDefinition.equipped_model_female_1 = 13329;
                itemDefinition.equipped_model_male_1 = 13317;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 0,};//55219
		        itemDefinition.modified_model_colors = new int[] { 947,};
                break;
	        
	        
	        case 15824:
	        	itemDefinition.name = "Purple Boxing Gloves";
                itemDefinition.modelZoom = 789;
                itemDefinition.rotation_y = 346;
                itemDefinition.rotation_x = 1674;
                itemDefinition.translate_x = 4;
                itemDefinition.translate_yz = 15;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 12277;
                itemDefinition.equipped_model_female_1 = 13329;
                itemDefinition.equipped_model_male_1 = 13317;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 55219,};
		        itemDefinition.modified_model_colors = new int[] { 947,};
                break;
				
	        case 15825:
	        	itemDefinition.name = "Yellow Boxing Gloves";
                itemDefinition.modelZoom = 789;
                itemDefinition.rotation_y = 346;
                itemDefinition.rotation_x = 1674;
                itemDefinition.translate_x = 4;
                itemDefinition.translate_yz = 15;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 12277;
                itemDefinition.equipped_model_female_1 = 13329;
                itemDefinition.equipped_model_male_1 = 13317;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 11187,};
		        itemDefinition.modified_model_colors = new int[] { 947,};
                break;
	        
	        case 15826:
	        	itemDefinition.name = "Dragon Scimitar (g)";
                itemDefinition.modelZoom = 1640;
                itemDefinition.rotation_y = 456;
                itemDefinition.rotation_x = 12;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 6;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70166;
                itemDefinition.equipped_model_female_1 = 70167;
                itemDefinition.equipped_model_male_1 = 70167;
                break;
	        case 15827:
	        	itemDefinition.name = "Gilded Chainbody";
                itemDefinition.modelZoom = 1100;
                itemDefinition.rotation_y = 568;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 2;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2558;
                itemDefinition.equipped_model_female_1 = 464;
                itemDefinition.equipped_model_male_1 = 301;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 6057,7097,7114};
		        itemDefinition.modified_model_colors = new int[] { 33,41,49};
		        break;
	        
	        case 15828:
	        	itemDefinition.name = "Gilded Med Helm";
                itemDefinition.modelZoom = 640;
                itemDefinition.rotation_y = 108;
                itemDefinition.rotation_x = 156;
                itemDefinition.translate_x = 1;
                itemDefinition.translate_yz = -4;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2833;
                itemDefinition.equipped_model_female_1 = 395;
                itemDefinition.equipped_model_male_1 = 219;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 6057,7114};
		        itemDefinition.modified_model_colors = new int[] { 926,61};
		        break;
	        
	        case 15829:
	        	itemDefinition.name = "Gilded Sq Shield";
                itemDefinition.modelZoom = 1410;
                itemDefinition.rotation_y = 268;
                itemDefinition.rotation_x = 60;
                itemDefinition.translate_x = 2;
                itemDefinition.translate_yz = 174;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2720;
                itemDefinition.equipped_model_female_1 = 541;
                itemDefinition.equipped_model_male_1 = 541;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 7114,7054};
		        itemDefinition.modified_model_colors = new int[] { 57,6057};
		        break;
	        
	        
	        case 15830:
	        	itemDefinition.name = "Gilded 2H";
                itemDefinition.modelZoom = 1380;
                itemDefinition.rotation_y = 204;
                itemDefinition.rotation_x = 1396;
                itemDefinition.translate_x = 3;
                itemDefinition.translate_yz = -9;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2754;
                itemDefinition.equipped_model_female_1 = 546;
                itemDefinition.equipped_model_male_1 = 546;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 7114};
		        itemDefinition.modified_model_colors = new int[] { 61};
		        break;
	        
	        case 15831:
	        	itemDefinition.name = "Gilded Spear";
                itemDefinition.modelZoom = 1490;
                itemDefinition.rotation_y = 1960;
                itemDefinition.rotation_x = 112;
                itemDefinition.translate_x = 7;
                itemDefinition.translate_yz = -4;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2719;
                itemDefinition.equipped_model_female_1 = 540;
                itemDefinition.equipped_model_male_1 = 540;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 7114,6057};
		        itemDefinition.modified_model_colors = new int[] { 33,7073};
		        break;
	        	
	        case 15832:
	        	itemDefinition.name = "Gilded Hasta";
                itemDefinition.modelZoom = 1762;
                itemDefinition.rotation_y = 1923;
                itemDefinition.rotation_x = 2047;
                itemDefinition.translate_x = 5;
                itemDefinition.translate_yz = -4;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 70164;
                itemDefinition.equipped_model_female_1 = 70165;
                itemDefinition.equipped_model_male_1 = 70165;
                itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 7114,6848,6967,6057};
		        itemDefinition.modified_model_colors = new int[] { 72,59,33,5652};
		        break;
	        
	        
	        case 15833:
				itemDefinition.inventory_model = 70163;
				itemDefinition.name = "Dragon claws";
				itemDefinition.actions[2] = "Wear";
				itemDefinition.description = "A mighty good weapon. If I was a fair man.".getBytes();
				itemDefinition.modelZoom = 630;
				itemDefinition.rotation_y = 268;
				itemDefinition.rotation_x = 1340;
				itemDefinition.translate_x = -7;
				itemDefinition.translate_yz = -13;
				itemDefinition.original_model_colors = new int[1];
				itemDefinition.modified_model_colors = new int[1];
				itemDefinition.original_model_colors = new int[] { 922,929,918,929};
		        itemDefinition.modified_model_colors = new int[] { 24,33,41,49};
				break;
	        
	        
	        case 15838:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Weild";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70158;
				itemDefinition.name = "Elder Maul";
				itemDefinition.modelZoom = 1130;
				itemDefinition.rotation_y = 396;
				itemDefinition.rotation_x = 952;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = -4;
				itemDefinition.equipped_model_female_1 = 70159;
				itemDefinition.equipped_model_male_1 = 70159;
				itemDefinition.description = "A maul crafted from the component parts of Tekton".getBytes();
				break;
	        
				
	        case 15841:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70151;
				itemDefinition.name = "Pyromancer Garb";
				itemDefinition.modelZoom = 1375;
				itemDefinition.rotation_y = 553;
				itemDefinition.rotation_x = 0;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70153;
				itemDefinition.equipped_model_male_1 = 70152;
				itemDefinition.equipped_model_male_2 = 70156;
				itemDefinition.equipped_model_female_2 = 70156;
				itemDefinition.description = "The robe of the Pyromancers".getBytes();
				break;
	        	        
	        case 15842:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70148;
				itemDefinition.name = "Pyromancer Robe";
				itemDefinition.modelZoom = 1897;
				itemDefinition.rotation_y = 512;
				itemDefinition.rotation_x = 14;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 7;
				itemDefinition.equipped_model_female_1 = 70150;
				itemDefinition.equipped_model_male_1 = 70149;
				itemDefinition.description = "The robe of the Pyromancers".getBytes();
				break;
	        case 15843:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70145;
				itemDefinition.name = "Pyromancer Hood";
				itemDefinition.modelZoom = 789;
				itemDefinition.rotation_y = 24;
				itemDefinition.rotation_x = 2047;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 1;
				itemDefinition.equipped_model_female_1 = 70147;
				itemDefinition.equipped_model_male_1 = 70146;
				itemDefinition.description = "The hood of the Pyromancers".getBytes();
				break;
				
	     	    case 15844:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70142;
				itemDefinition.name = "Pyromancer Boots";
				itemDefinition.modelZoom = 770;
				itemDefinition.rotation_y = 164;
				itemDefinition.rotation_x = 156;
				itemDefinition.translate_x = 3;
				itemDefinition.translate_yz = -3;
				itemDefinition.equipped_model_female_1 = 70144;
				itemDefinition.equipped_model_male_1 = 70143;
				itemDefinition.description = "The boots of the Pyromancers".getBytes();
				break;

	        case 15845:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70139;
				itemDefinition.name = "Warm Gloves";
				itemDefinition.modelZoom = 789;
				itemDefinition.rotation_y = 609;
				itemDefinition.rotation_x = 111;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 0;
				itemDefinition.equipped_model_female_1 = 70141;
				itemDefinition.equipped_model_male_1 = 70140;
				itemDefinition.description = "Possibly fireproof.Possibly".getBytes();
				break;
				
			case 15846:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
				itemDefinition.inventory_model = 70137;
				itemDefinition.name = "Zulrah Scales";
				itemDefinition.modelZoom = 1370;
				itemDefinition.rotation_y = 212;
				itemDefinition.rotation_x = 148;
				itemDefinition.translate_x = 7;
				itemDefinition.translate_yz = 0;
				itemDefinition.equipped_model_female_1 = -1;
				itemDefinition.equipped_model_male_1 = -1;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
			break;
		case 15850:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Uncharge";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70131;
				itemDefinition.name = "Toxic Staff of the Dead";
				itemDefinition.modelZoom = 2150;
				itemDefinition.rotation_y = 512;
				itemDefinition.rotation_x = 1010;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
				itemDefinition.equipped_model_female_1 = 70132;
				itemDefinition.equipped_model_male_1 = 70132;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15851:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Dismantle";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70129;
				itemDefinition.name = "Toxic Staff(Uncharged)";
				itemDefinition.modelZoom = 2150;
				itemDefinition.rotation_y = 512;
				itemDefinition.rotation_x = 1010;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 0;
				itemDefinition.equipped_model_female_1 = 70130;
				itemDefinition.equipped_model_male_1 = 70130;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15852:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Uncharge";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70127;
				itemDefinition.name = "Trident of the Swamp";
				itemDefinition.modelZoom = 2421;
				itemDefinition.rotation_y = 1549;
				itemDefinition.rotation_x = 1818;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 9;
				itemDefinition.equipped_model_female_1 = 70128;
				itemDefinition.equipped_model_male_1 = 70128;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15853:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Dismantle";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70125;
				itemDefinition.name = "Uncharged Toxic Trident";
				itemDefinition.modelZoom = 2421;
				itemDefinition.rotation_y = 1549;
				itemDefinition.rotation_x = 1818;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = 9;
				itemDefinition.equipped_model_female_1 = 70126;
				itemDefinition.equipped_model_male_1 = 70126;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15854:
	        	itemDefinition.name = "Magic Fang";
		        itemDefinition.modelZoom = 1095;
		        itemDefinition.rotation_y = 579;
		        itemDefinition.rotation_x = 1832;
		        itemDefinition.translate_x = 7;
		        itemDefinition.translate_yz = 0;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, "Dismantle", "Drop" };
		        itemDefinition.inventory_model = 70123;
		        itemDefinition.equipped_model_female_1 = -1;
				itemDefinition.equipped_model_male_1 = -1;
		        break; 
	        
	        	
	        case 15856:
	        	itemDefinition.name = "Magma Mutagen";
		        itemDefinition.modelZoom = 905;
		        itemDefinition.rotation_y = 189;
		        itemDefinition.rotation_x = 215;
		        itemDefinition.translate_x = 0;
		        itemDefinition.translate_yz = 0;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
		        itemDefinition.inventory_model = 2705;
		        itemDefinition.equipped_model_female_1 = -1;
				itemDefinition.equipped_model_male_1 = -1;
				itemDefinition.original_model_colors = new int[] { 3008 };
				itemDefinition.modified_model_colors = new int[] { 61 };
		        break; 
	        case 15857:
	        	itemDefinition.name = "Tanzanite Mutagen";
		        itemDefinition.modelZoom = 905;
		        itemDefinition.rotation_y = 189;
		        itemDefinition.rotation_x = 215;
		        itemDefinition.translate_x = 0;
		        itemDefinition.translate_yz = 0;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
		        itemDefinition.inventory_model = 2705;
		        itemDefinition.equipped_model_female_1 = -1;
				itemDefinition.equipped_model_male_1 = -1;
				itemDefinition.original_model_colors = new int[] { 29656 };
				itemDefinition.modified_model_colors = new int[] { 61 };
		        break; 
	        case 15858:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Restore";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70119;
				itemDefinition.name = "Magma Helmet";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 215;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = -17;
				itemDefinition.equipped_model_female_1 = 70121;
				itemDefinition.equipped_model_male_1 = 70120;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15859:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, null, null, "Restore", "Drop" };
	        	itemDefinition.inventory_model = 70116;
				itemDefinition.name = "Magma Helmet(Uncharged)";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 0;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = -1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70118;
				itemDefinition.equipped_model_male_1 = 70117;
				itemDefinition.description = "Needs to be charged with Zulrah scales".getBytes();
				break;
	        case 15860:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Restore";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70113;
				itemDefinition.name = "Tanzanite Helmet";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 215;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = -17;
				itemDefinition.equipped_model_female_1 = 70115;
				itemDefinition.equipped_model_male_1 = 70114;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        
	        case 15861:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, null, null, "Restore", "Drop" };
				itemDefinition.inventory_model = 70110;
				itemDefinition.name = "Tanzanite Helmet(Uncharged)";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 0;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = -1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70112;
				itemDefinition.equipped_model_male_1 = 70111;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15862:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[2] = "Check";
				itemDefinition.actions[3] = "Uncharge";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 70107;
				itemDefinition.name = "Serpentine Helmet";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 215;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = -17;
				itemDefinition.equipped_model_female_1 = 70109;
				itemDefinition.equipped_model_male_1 = 70108;
				itemDefinition.description = "Protects the wearer from poison and venom".getBytes();
				break;
	        case 15863:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, null, null, "Dismantle", "Drop" };
	        	itemDefinition.inventory_model = 70162;
				itemDefinition.name = "Serpentine Visage";
				itemDefinition.modelZoom = 716;
				itemDefinition.rotation_y = 0;
				itemDefinition.rotation_x = 250;
				itemDefinition.translate_x = 7;
				itemDefinition.translate_yz = 7;
				itemDefinition.description = "Maybe you could use a chisel to craft this into a helmet".getBytes();
				
				break;
	        case 15864:
	        	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
	            itemDefinition.actions = new String[] { null, null, null, "Dismantle", "Drop" };
	        	itemDefinition.inventory_model = 70103;
				itemDefinition.name = "Serpentine Helmet(Uncharged)";
				itemDefinition.modelZoom = 700;
				itemDefinition.rotation_y = 0;
				itemDefinition.rotation_x = 1724;
				itemDefinition.translate_x = -1;
				itemDefinition.translate_yz = -1;
				itemDefinition.equipped_model_female_1 = 70105;
				itemDefinition.equipped_model_male_1 = 70104;
				itemDefinition.description = "Needs to be charged with Zulrah scales".getBytes();
				break;
	        case 15866:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 32790;
				itemDefinition.name = "Ancestral robe top";
				itemDefinition.modelZoom = 1358;
				itemDefinition.rotation_y = 514;
				itemDefinition.rotation_x = 2041;
				itemDefinition.translate_x = 0;
				itemDefinition.translate_yz = -3;
				itemDefinition.equipped_model_female_1 = 32657;
				itemDefinition.equipped_model_male_1 = 32664;
				itemDefinition.equipped_model_male_2 = 32658; //male arms
				itemDefinition.equipped_model_female_2 = 32665; //female arms
				itemDefinition.description = "The robe top of a powerful sorceress from a bygone era.".getBytes();
				break;
	        case 15865:
	        	itemDefinition.actions = new String[5];
				itemDefinition.actions[1] = "Wear";
				itemDefinition.actions[4] = "Drop";
				itemDefinition.inventory_model = 32787;
				itemDefinition.name = "Ancestral robe bottom";
				itemDefinition.modelZoom = 1690;
				itemDefinition.rotation_y = 435;
				itemDefinition.rotation_x = 9;
				itemDefinition.translate_x = 1;
				itemDefinition.translate_yz = 7;
				itemDefinition.equipped_model_female_1 = 32653;
				itemDefinition.equipped_model_male_1 = 32662;
				itemDefinition.description = "The robe bottom of a powerful sorceress from a bygone era.".getBytes();
				break;
		case 15869:
				itemDefinition.name = "Eternal crystal";
		        itemDefinition.modelZoom = 740;
		        itemDefinition.rotation_y = 429;
		        itemDefinition.rotation_x = 225;
		        itemDefinition.translate_x = 5;
		        itemDefinition.translate_yz = 5;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
		        itemDefinition.inventory_model = 29264;
		        break; 
			case 15870:
				itemDefinition.name = "Pegasian crystal";
				itemDefinition.modelZoom = 740;
		        itemDefinition.rotation_y = 429;
		        itemDefinition.rotation_x = 225;
		        itemDefinition.translate_x = 5;
		        itemDefinition.translate_yz = 5;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
		        itemDefinition.inventory_model = 29261;
		        break; 
		         
			case 15871:
				itemDefinition.name = "Primordial crystal";
				itemDefinition.modelZoom = 740;
		        itemDefinition.rotation_y = 429;
		        itemDefinition.rotation_x = 225;
		        itemDefinition.translate_x = 5;
		        itemDefinition.translate_yz = 5;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
		        itemDefinition.inventory_model = 29263;
		        break; 
			case 15872:
				itemDefinition.name = "Eternal boots";
		        itemDefinition.modelZoom = 976;
		        itemDefinition.rotation_y = 147;
		        itemDefinition.rotation_x = 279;
		        itemDefinition.translate_x = 5;
		        itemDefinition.translate_yz = -5;
		        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
		        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
		        itemDefinition.inventory_model = 29394;
		        itemDefinition.equipped_model_male_1 = 29249;
		        itemDefinition.equipped_model_female_1 = 29254;
		        break; 
			case 15873:
				itemDefinition.name = "Pegasian boots";
				 itemDefinition.modelZoom = 976;
			        itemDefinition.rotation_y = 147;
			        itemDefinition.rotation_x = 279;
			        itemDefinition.translate_x = 5;
			        itemDefinition.translate_yz = -5;
			        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
			        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
			        itemDefinition.inventory_model = 29396;
			        itemDefinition.equipped_model_male_1 = 29252;
			        itemDefinition.equipped_model_female_1 = 29253;
			        break; 
			case 15874:
				itemDefinition.name = "Primordial boots";
				 itemDefinition.modelZoom = 976;
			        itemDefinition.rotation_y = 147;
			        itemDefinition.rotation_x = 279;
			        itemDefinition.translate_x = 5;
			        itemDefinition.translate_yz = -5;
			        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
			        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
			        itemDefinition.inventory_model = 29397;
			        itemDefinition.equipped_model_male_1 = 29250;
			        itemDefinition.equipped_model_female_1 = 29255;
			        break; 
		 /* OSRS ITEMS */
		
			
            
            case 11848:
                itemDefinition.name = "Staff of the dead";
                itemDefinition.modelZoom = 1490;
                itemDefinition.rotation_y = 138;
                itemDefinition.rotation_x = 1300;
                itemDefinition.translate_x = -5;
                itemDefinition.translate_yz = 2;
                itemDefinition.original_model_colors = new int[] { 18626, 18626, 18626, 18626 };
                itemDefinition.modified_model_colors = new int[] { 17467, -22419, 7073, 61 };
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 2810;
                itemDefinition.equipped_model_female_1 = 5232;
                itemDefinition.equipped_model_male_1 = 5232;
            break;
									
            case 11889:
                itemDefinition.name = "Zamorakian hasta";
                itemDefinition.modelZoom = 1900;
                itemDefinition.rotation_y = 1257;
                itemDefinition.rotation_x = 512;
                itemDefinition.translate_yz = -4;
                itemDefinition.translate_x = 0;
                itemDefinition.original_model_colors = new int[] { 41 };
                itemDefinition.modified_model_colors = new int[] { 78 };
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 28038;
                itemDefinition.equipped_model_female_1 = 27654;
                itemDefinition.equipped_model_male_1 = 27654;
            break;
            
            case 11893:
                itemDefinition.name = "Decorative armour";
                itemDefinition.modelZoom = 1100;
                itemDefinition.rotation_y = 620;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 5;
                itemDefinition.translate_yz = 5;
                itemDefinition.original_model_colors = new int[] { 937, -21591, -21591 };
                itemDefinition.modified_model_colors = new int[] { 61, 25238, 908 };
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                itemDefinition.inventory_model = 2542;
                itemDefinition.equipped_model_male_1 = 266;//top should be 165
                itemDefinition.equipped_model_female_1 = 430;//bottom should be 200
            break;
 
            case 11894:
                itemDefinition.name = "Decorative armour";
                itemDefinition.modelZoom = 1100;
                itemDefinition.rotation_y = 620;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 5;
                itemDefinition.translate_yz = 5;
                itemDefinition.original_model_colors = new int[] { 94, -22087, -22087 };
                itemDefinition.modified_model_colors = new int[] { 61, 25238, 908 };
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                itemDefinition.inventory_model = 2542;
                itemDefinition.equipped_model_male_1 = 266;
                itemDefinition.equipped_model_female_1 = 430;
            break;
 
            case 11895:
            	itemDefinition.name = "Decorative armour";
                itemDefinition.modelZoom = 1100;
                itemDefinition.rotation_y = 620;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 5;
                itemDefinition.translate_yz = 5;
                itemDefinition.original_model_colors = new int[] { 10929, -22256, -22256 };
                itemDefinition.modified_model_colors = new int[] { 61, 25238, 908 };
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                itemDefinition.inventory_model = 2542;
                itemDefinition.equipped_model_male_1 = 266;
                itemDefinition.equipped_model_female_1 = 430;
            break;
 
            case 11896:
            	itemDefinition.name = "Decorative armour";
                itemDefinition.modelZoom = 1400;
                itemDefinition.rotation_y = 637;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 0;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                itemDefinition.inventory_model = 16285;
                itemDefinition.equipped_model_male_1 = 28226;
                itemDefinition.equipped_model_male_2 = 28224;
                itemDefinition.equipped_model_female_1 = 28853;
                itemDefinition.equipped_model_female_2 = 28850;
            break;
 
            case 11897:
            	itemDefinition.name = "Decorative armour";
            	itemDefinition.rotation_y = 637;
            	itemDefinition.rotation_x = 0;
            	itemDefinition.translate_x = 0;
            	itemDefinition.translate_yz = 0;
            	itemDefinition.modelZoom = 2000;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 28219;
            	itemDefinition.equipped_model_male_1 = 28223;
            	itemDefinition.equipped_model_female_1 = 28849;
            break;
 
            case 11898:
            	itemDefinition.name = "Decorative armour";
            	itemDefinition.modelZoom = 1400;
            	itemDefinition.translate_x = -4;
            	itemDefinition.translate_yz = -12;
            	itemDefinition.rotation_y = 0;
            	itemDefinition.rotation_x = 0;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 16284;
            	itemDefinition.equipped_model_male_1 = 28220;
            	itemDefinition.equipped_model_female_1 = 28220;
            break;
 
            case 11899:
                itemDefinition.name = "Decorative armour";
                itemDefinition.modelZoom = 1400;
                itemDefinition.rotation_y = 637;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 9;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                itemDefinition.inventory_model = 28217;
                itemDefinition.equipped_model_male_1 = 28227;
                itemDefinition.equipped_model_male_2 = 28225;
                itemDefinition.equipped_model_female_1 = 28852;
                itemDefinition.equipped_model_female_2 = 28851;
            break;
 
            case 11900:
            	itemDefinition.name = "Decorative armour";
            	itemDefinition.rotation_y = 637;
                itemDefinition.rotation_x = 0;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 0;
                itemDefinition.modelZoom = 2000;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 28218;
            	itemDefinition.equipped_model_male_1 = 28222;
            	itemDefinition.equipped_model_female_1 = 28848;
            break;
 
            case 11901:
            	itemDefinition.name = "Decorative armour";
            	itemDefinition.modelZoom = 800;
            	itemDefinition.rotation_y = 637;
                itemDefinition.rotation_x = 0;
            	itemDefinition.translate_x = -5;
            	itemDefinition.translate_yz = -8;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 28216;
            	itemDefinition.equipped_model_male_1 = 28221;
            	itemDefinition.equipped_model_female_1 = 28221;
            break;
 
            case 11902:
                itemDefinition.name = "Leaf-bladed sword";
                itemDefinition.modelZoom = 1650;
                itemDefinition.rotation_y = 500;
                itemDefinition.rotation_x = 1300;
                itemDefinition.translate_x = -20;
                itemDefinition.translate_yz = -20;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                itemDefinition.inventory_model = 28229;
                itemDefinition.equipped_model_female_1 = 28228;
                itemDefinition.equipped_model_male_1 = 28228;
            break;
                        
            case 11905:
                itemDefinition.name = "Trident of the seas (full)";
                itemDefinition.modelZoom = 2350;
                itemDefinition.rotation_y = 1505;
                itemDefinition.rotation_x = 1850;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 0;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
                itemDefinition.inventory_model = 28232;
                itemDefinition.equipped_model_female_1 = 28230;
                itemDefinition.equipped_model_male_1 = 28230;
            break;
 
            case 11907:
                itemDefinition.name = "Trident of the seas";
                itemDefinition.modelZoom = 2350;
                itemDefinition.rotation_y = 1505;
                itemDefinition.rotation_x = 1850;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 0;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
                itemDefinition.inventory_model = 28232;
                itemDefinition.equipped_model_female_1 = 28230;
                itemDefinition.equipped_model_male_1 = 28230;
            break;
 
            case 11908:
                itemDefinition.name = "Uncharged trident";
                itemDefinition.modelZoom = 2350;
                itemDefinition.rotation_y = 1505;
                itemDefinition.rotation_x = 1850;
                itemDefinition.translate_x = 0;
                itemDefinition.translate_yz = 0;
                itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                itemDefinition.actions = new String[] { null, "Wield", "Check", null, "Drop" };
                itemDefinition.inventory_model = 28232;
                itemDefinition.equipped_model_female_1 = 28230;
                itemDefinition.equipped_model_male_1 = 28230;
            break;
                        
            case 11919:
            	itemDefinition.name = "Cow mask";
            	itemDefinition.modelZoom = 820;
            	itemDefinition.rotation_y = 2036;
            	itemDefinition.rotation_x = 1908;
            	itemDefinition.translate_x = -2;
            	itemDefinition.translate_yz = 0;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Destroy" };
            	itemDefinition.inventory_model = 28250;
            	itemDefinition.equipped_model_male_1 = 28235;
            	itemDefinition.equipped_model_female_1 = 28237;
            break;
 
            case 11920:
            	itemDefinition.name = "Dragon pickaxe";
            	itemDefinition.modelZoom = 1070;
            	itemDefinition.rotation_y = 224;
            	itemDefinition.rotation_x = 1056;
            	itemDefinition.translate_x = -2;
            	itemDefinition.translate_yz = -19;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
            	itemDefinition.inventory_model = 28315;
            	itemDefinition.equipped_model_female_1 = 48470;
            	itemDefinition.equipped_model_male_1 = 48470;
            break;
                        
            case 11924:
            	itemDefinition.name = "Malediction ward";
            	itemDefinition.modelZoom = 1200;
            	itemDefinition.rotation_y = 568;
            	itemDefinition.rotation_x = 1836;
            	itemDefinition.translate_x = 0;
            	itemDefinition.translate_yz = 3;
            	itemDefinition.original_model_colors = new int[] { -21608 };
            	itemDefinition.modified_model_colors = new int[] { 908 };
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
            	itemDefinition.inventory_model = 9354;
            	itemDefinition.equipped_model_female_1 = 9347;
            	itemDefinition.equipped_model_male_1 = 9347;
            break;
 
            case 11926:
            	itemDefinition.name = "Odium ward";
            	itemDefinition.modelZoom = 1200;
            	itemDefinition.rotation_y = 568;
            	itemDefinition.rotation_x = 1836;
            	itemDefinition.translate_x = 0;
            	itemDefinition.translate_yz = 3;
            	itemDefinition.original_model_colors = new int[] { 15252 };
            	itemDefinition.modified_model_colors = new int[] { 908 };
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
            	itemDefinition.inventory_model = 9354;
            	itemDefinition.equipped_model_female_1 = 9347;
            	itemDefinition.equipped_model_male_1 = 9347;
            break;
                        
            case 11990:
            	itemDefinition.name = "Fedora";
            	itemDefinition.modelZoom = 1284;
            	itemDefinition.rotation_y = 108;
            	itemDefinition.rotation_x = 1535;
            	itemDefinition.translate_x = -1;
            	itemDefinition.translate_yz = 0;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 8796;
            	itemDefinition.equipped_model_male_1 = 6364;
            	itemDefinition.equipped_model_female_1 = 8798;
            break;
                        
            case 11995:
            	itemDefinition.name = "Pet chaos elemental";
            	itemDefinition.modelZoom = 1600;
            	itemDefinition.rotation_x = 75;
            	itemDefinition.translate_yz = -150;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
            	itemDefinition.inventory_model = 28256;
            break;
                        
            case 11996:
            	itemDefinition.name = "TzRek-Jad";
            	itemDefinition.translate_x = -3;
            	itemDefinition.translate_yz = -30;
            	itemDefinition.rotation_x = 553;
            	itemDefinition.rotation_y = 0;
            	itemDefinition.modelZoom = 10000;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
            	itemDefinition.inventory_model = 9319;
            break;
				
            
            
            case 12002:
            	itemDefinition.name = "Occult necklace";
            	itemDefinition.modelZoom = 589;
            	itemDefinition.rotation_y = 431;
            	itemDefinition.rotation_x = 81;
            	itemDefinition.translate_x = 3;
            	itemDefinition.translate_yz = 21;
            	itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
            	itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
            	itemDefinition.inventory_model = 28438;
            	itemDefinition.equipped_model_female_1 = 28445;
            	itemDefinition.equipped_model_male_1 = 28445;
            break;
 
           case 12004:
        	   itemDefinition.name = "Kraken tentacle";
        	   itemDefinition.modelZoom = 1095;
        	   itemDefinition.rotation_y = 593;
        	   itemDefinition.rotation_x = 741;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 4;
        	   itemDefinition.original_model_colors = new int[] { 8097, 9121, 8092, 9118 };
        	   itemDefinition.modified_model_colors = new int[] { 11148, 10772, 10652, 10533 };
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
        	   itemDefinition.inventory_model = 28437;
           break;
           
           case 730:
        	   itemDefinition.description = "Use this book to be rewarded 100 Vengeance casts".getBytes();
        	   break;
           
           case 11640:
        	   itemDefinition.description = "Use this book to be rewarded 100 Barrage casts".getBytes();
        	   break;
           case 15810:
        	   itemDefinition.name = "Zamorak Halo";
        	   itemDefinition.actions = new String[5];
        	   itemDefinition.actions[1] = "Wear";
        	   itemDefinition.inventory_model = 70184;
        	   itemDefinition.modelZoom = 528;
        	   itemDefinition.rotation_y = 327;
        	   itemDefinition.rotation_x = 213;
        	   itemDefinition.translate_x = 3;
        	   itemDefinition.translate_yz = 12;
        	   itemDefinition.equipped_model_male_1 = 70185;
        	   itemDefinition.equipped_model_female_1 = 70186;
        	   break;
           case 15811:
        	   itemDefinition.name = "Saradomin Halo";
        	   itemDefinition.actions = new String[5];
        	   itemDefinition.actions[1] = "Wear";
        	   itemDefinition.inventory_model = 70181;
        	   itemDefinition.modelZoom = 550;
        	   itemDefinition.rotation_y = 228;
        	   itemDefinition.rotation_x = 141;
        	   itemDefinition.translate_x = 3;
        	   itemDefinition.translate_yz = 0;
        	   itemDefinition.equipped_model_male_1 = 70182;
        	   itemDefinition.equipped_model_female_1 = 70183;
        	   break;
           case 15812:
        	   itemDefinition.name = "Guthix Halo";
        	   itemDefinition.actions = new String[5];
        	   itemDefinition.actions[1] = "Wear";
        	   itemDefinition.inventory_model = 70178;
        	   itemDefinition.modelZoom = 528;
        	   itemDefinition.rotation_y = 294;
        	   itemDefinition.rotation_x = 123;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 20;
        	   itemDefinition.equipped_model_male_1 = 70179;
        	   itemDefinition.equipped_model_female_1 = 70180;
        	   break;
           
           case 15813:
        	   itemDefinition.name = "Unsired";
        	   itemDefinition.modelZoom = 1979;
        	   itemDefinition.rotation_y = 0;
        	   itemDefinition.rotation_x = 1939;
        	   itemDefinition.translate_x = 16;
        	   itemDefinition.translate_yz = -13;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "drop"};
        	   itemDefinition.inventory_model = 70174;
        	   break;
           case 15814:
        	   itemDefinition.name = "Bludgeon Spine";
        	   itemDefinition.modelZoom = 1916;
        	   itemDefinition.rotation_y = 606;
        	   itemDefinition.rotation_x = 483;
        	   itemDefinition.translate_x = -1;
        	   itemDefinition.translate_yz = 9;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop"};
        	   itemDefinition.inventory_model = 70175;
        	   break;
           case 15815:
        	   itemDefinition.name = "Bludgeon Claw";
        	   itemDefinition.modelZoom = 2358;
        	   itemDefinition.rotation_y = 458;
        	   itemDefinition.rotation_x = 700;
        	   itemDefinition.translate_x = -8;
        	   itemDefinition.translate_yz = -11;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop"};
        	   itemDefinition.inventory_model = 70176;
        	   break;
           case 15816:
        	   itemDefinition.name = "Bludgeon Axon";
        	   itemDefinition.modelZoom = 1095;
        	   itemDefinition.rotation_y = 566;
        	   itemDefinition.rotation_x = 1758;
        	   itemDefinition.translate_x = 8;
        	   itemDefinition.translate_yz = 1;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop"};
        	   itemDefinition.inventory_model = 70177;
        	   break;
           case 12006:
        	   itemDefinition.name = "Abyssal tentacle";
        	   itemDefinition.modelZoom = 840;
        	   itemDefinition.rotation_y = 280;
        	   itemDefinition.rotation_x = 121;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 56;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wield", null, "Check", "Drop" };
        	   itemDefinition.inventory_model = 28439;
        	   itemDefinition.equipped_model_female_1 = 28446;
        	   itemDefinition.equipped_model_male_1 = 28446;
           break;
 
           
           case 15915:
        	   itemDefinition.name = "Jar of Miasma";
        	   itemDefinition.modelZoom = 590;
        	   itemDefinition.rotation_y = 280;
        	   itemDefinition.rotation_x = 1756;
        	   itemDefinition.translate_x = 13;
        	   itemDefinition.translate_yz = 60;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
        	   itemDefinition.inventory_model = 28441;
        	   itemDefinition.modified_model_colors = new int[] {10401};
        	   itemDefinition.original_model_colors = new int[] {2349};
           break;
           case 15916:
        	   itemDefinition.name = "Shayzien Helm";
        	   itemDefinition.modelZoom = 800;
        	   itemDefinition.rotation_y = 512;
        	   itemDefinition.rotation_x = 1993;
        	   itemDefinition.translate_x = 11;
        	   itemDefinition.translate_yz = -5;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70226;
        	   itemDefinition.equipped_model_male_1 = 70227;
        	   itemDefinition.equipped_model_female_1 = 70228;
        	   break;
           case 15917:
        	   itemDefinition.name = "Shayzien Platebody";
        	   itemDefinition.modelZoom = 1284;
        	   itemDefinition.rotation_y = 512;
        	   itemDefinition.rotation_x = 0;
        	   itemDefinition.translate_x = 1;
        	   itemDefinition.translate_yz = -5;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70229;
        	   itemDefinition.equipped_model_male_1 = 70230;
        	   itemDefinition.equipped_model_female_1 = 70231;
        	   itemDefinition.equipped_model_female_2 = 70232;
        	   itemDefinition.equipped_model_male_2 = 70233;
        	   break;
           case 15918:
        	   itemDefinition.name = "Shayzien Greaves";
        	   itemDefinition.modelZoom = 1537;
        	   itemDefinition.rotation_y = 560;
        	   itemDefinition.rotation_x = 225;
        	   itemDefinition.translate_x = 5;
        	   itemDefinition.translate_yz = 4;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70234;
        	   itemDefinition.equipped_model_male_1 = 70235;
        	   itemDefinition.equipped_model_female_1 = 70236;
        	   break;
           case 15919:
        	   itemDefinition.name = "Shayzien Gloves";
        	   itemDefinition.modelZoom = 400;
        	   itemDefinition.rotation_y = 24;
        	   itemDefinition.rotation_x = 354;
        	   itemDefinition.translate_x = -1;
        	   itemDefinition.translate_yz = -3;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70237;
        	   itemDefinition.equipped_model_male_1 = 70238;
        	   itemDefinition.equipped_model_female_1 = 70239;
        	   break;
           case 15920:
        	   itemDefinition.name = "Shayzien Boots";
        	   itemDefinition.modelZoom = 653;
        	   itemDefinition.rotation_y = 201;
        	   itemDefinition.rotation_x = 423;
        	   itemDefinition.translate_x = 7;
        	   itemDefinition.translate_yz = -16;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70240;
        	   itemDefinition.equipped_model_male_1 = 70241;
        	   itemDefinition.equipped_model_female_1 = 70242;
        	   break;
           case 15921:
        	   itemDefinition.name = "Prospector Helmet";
        	   itemDefinition.modelZoom = 905;
        	   itemDefinition.rotation_y = 189;
        	   itemDefinition.rotation_x = 1885;
        	   itemDefinition.translate_x = -8;
        	   itemDefinition.translate_yz = -25;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70243;
        	   itemDefinition.equipped_model_male_1 = 70244;
        	   itemDefinition.equipped_model_female_1 = 70244;
        	   break;
           case 15922:
        	   itemDefinition.name = "Prospector Jacket";
        	   itemDefinition.modelZoom = 1474;
        	   itemDefinition.rotation_y = 404;
        	   itemDefinition.rotation_x = 94;
        	   itemDefinition.translate_x = 7;
        	   itemDefinition.translate_yz = 13;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70245;
        	   itemDefinition.equipped_model_male_1 = 70246;
        	   itemDefinition.equipped_model_female_1 = 70247;
        	   itemDefinition.equipped_model_female_2 = 70248;
        	   itemDefinition.equipped_model_male_2 = 70249;
        	   itemDefinition.modified_model_colors = new int[] {43096,70,5037,5027,5400};
        	   itemDefinition.original_model_colors = new int[] {36279,36399,10392,36393,10392};
        	   break;
           case 15923:
        	   itemDefinition.name = "Prospector Legs";
        	   itemDefinition.modelZoom = 1697;
        	   itemDefinition.rotation_y = 498;
        	   itemDefinition.rotation_x = 180;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 0;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70250;
        	   itemDefinition.equipped_model_male_1 = 70251;
        	   itemDefinition.equipped_model_female_1 = 70252;
        	   itemDefinition.modified_model_colors = new int[] {43096,70,5037};
        	   itemDefinition.original_model_colors = new int[] {36279,36399,10392};
        	   break;
           case 15924:
        	   itemDefinition.name = "Prospector Boots";
        	   itemDefinition.modelZoom = 968;
        	   itemDefinition.rotation_y = 242;
        	   itemDefinition.rotation_x = 202;
        	   itemDefinition.translate_x = 3;
        	   itemDefinition.translate_yz = -100;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70253;
        	   itemDefinition.equipped_model_male_1 = 70254;
        	   itemDefinition.equipped_model_female_1 = 70254;
        	   itemDefinition.modified_model_colors = new int[] {4626,8101};
        	   itemDefinition.original_model_colors = new int[] {5272,5157};
        	   break;
           case 15929:
        	   itemDefinition.name = "Dragon Kiteshield";
        	   itemDefinition.modelZoom = 1730;
        	   itemDefinition.rotation_y = 352;
        	   itemDefinition.rotation_x = 120;
        	   itemDefinition.translate_x = 1;
        	   itemDefinition.translate_yz = 10;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70269;
        	   itemDefinition.equipped_model_male_1 = 70270;
        	   itemDefinition.equipped_model_female_1 = 70270;
        	  break;
           case 15930://not perfect ints but they work
        	   itemDefinition.name = "Dragonfire Ward";
        	   itemDefinition.modelZoom = 1350;
        	   itemDefinition.rotation_y = 352;
        	   itemDefinition.rotation_x = 120;
        	   itemDefinition.translate_x = 1;
        	   itemDefinition.translate_yz = 10;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 70271;
        	   itemDefinition.equipped_model_male_1 = 70272;
        	   itemDefinition.equipped_model_female_1 = 70272;
        	  break;
        	  
           case 15931:
        	   itemDefinition.name = "Skeletal Visage";
        	   itemDefinition.modelZoom = 1200;
        	   itemDefinition.rotation_y = 567;
        	   itemDefinition.rotation_x = 152;
        	   itemDefinition.translate_x = -5;
        	   itemDefinition.translate_yz = -5;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
        	   itemDefinition.inventory_model = 70273;
        	  break;
           case 12543:
        	   itemDefinition.name = "Kodai insignia";
        	   itemDefinition.modelZoom = 1200;
        	   itemDefinition.rotation_y = 567;
        	   itemDefinition.rotation_x = 152;
        	   itemDefinition.translate_x = -5;
        	   itemDefinition.translate_yz = -5;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
        	   itemDefinition.inventory_model = 70304;
        	  break;
           //case 8966:
               //System.out.println("Model Info - [ID]: " + itemDefinition.inventory_model + " [Zoom]: " + itemDefinition.modelZoom + " [RotY]: " + itemDefinition.rotation_x);
               //System.out.println("Model Info - [RotX]: " + itemDefinition.rotation_y + " [Off1]: " + itemDefinition.translate_x + " [Off2]: " + itemDefinition.translate_yz);--+*
               //System.out.println("Model Info - [Male Wear]: " + itemDefinition.equipped_model_male_1 + " [Female Wear]: " + itemDefinition.equipped_model_female_1);
               //System.out.println("Model Info - [Male Arms]: " + itemDefinition.equipped_model_male_2 + " [Female Arms]: " + itemDefinition.equipped_model_female_2);
               //System.out.println("Model Info - [Rasterizer3D]: " + itemDefinition.textureFind[0]);
               //System.out.println("Model Original Color = " + itemDefinition.recolourTarget[0]);
              // System.out.println("Model Modified Color = " + itemDefinition.original_model_colors[0]);
               //System.out.print("Model Original Color1 = " + itemDefinition.recolourTarget[1]);
               //System.out.print("Model Modified Color1 = " + itemDefinition.original_model_colors[1]);
               //System.out.print("Model Original Color2 = " + itemDefinition.recolourTarget[2]);
               //System.out.print("Model Modified Color2 = " + itemDefinition.original_model_colors[2]);
               //System.out.print("Model Original Color3 = " + itemDefinition.recolourTarget[3]);
               //System.out.print("Model Modified Color3 = " + itemDefinition.original_model_colors[3]);
               //System.out.print("Model Original Color4 = " + itemDefinition.recolourTarget[4]);
               //System.out.print("Model Modified Color4 = " + itemDefinition.original_model_colors[4]);
               //System.out.print("Model Original Color5 = " + itemDefinition.recolourTarget[5]);
               //System.out.print("Model Modified Color5 = " + itemDefinition.original_model_colors[5]);
               //System.out.println("Stacks = " + itemDefinition.stack_variant_id);
           //break;
           case 12193:
        	   itemDefinition.name = "Ancient robe top";
        	   itemDefinition.modelZoom = 1221;
        	   itemDefinition.rotation_y = 619;
        	   itemDefinition.rotation_x = 0;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 0;
        	   itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
        	   itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 28747;
        	   itemDefinition.equipped_model_male_1 = 28536;
        	   itemDefinition.equipped_model_male_2 = 28527;
        	   itemDefinition.equipped_model_female_1 = 28600;
        	   itemDefinition.equipped_model_female_2 = 28594;
           break;
 
           case 12195:
        	   itemDefinition.name = "Ancient robe legs";
        	   itemDefinition.rotation_y = 525;
        	   itemDefinition.rotation_x = 0;
        	   itemDefinition.translate_x = 0;
        	   itemDefinition.translate_yz = 0;
        	   itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
        	   itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 28655;
        	   itemDefinition.equipped_model_male_1 = 28500;
        	   itemDefinition.equipped_model_female_1 = 28571;
           break;
 
           case 12197:
        	   itemDefinition.name = "Ancient cloak";
        	   itemDefinition.modelZoom = 1827;
        	   itemDefinition.rotation_y = 364;
        	   itemDefinition.rotation_x = 992;
        	   itemDefinition.translate_x = 3;
        	   itemDefinition.translate_yz = 12;
        	   itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
        	   itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
        	   itemDefinition.inventory_model = 28728;
        	   itemDefinition.equipped_model_male_1 = 28490;
        	   itemDefinition.equipped_model_female_1 = 28563;
           break;
 
           case 12199:
               itemDefinition.name = "Ancient crozier";
               itemDefinition.modelZoom = 2151;
               itemDefinition.rotation_y = 567;
               itemDefinition.rotation_x = 0;
               itemDefinition.translate_x = 3;
               itemDefinition.translate_yz = 4;
               itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
               itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
               itemDefinition.inventory_model = 28690;
               itemDefinition.equipped_model_male_1 = 28624;
               itemDefinition.equipped_model_female_1 = 28624;
           break;
 
           case 12201:
               itemDefinition.name = "Ancient stole";
               itemDefinition.modelZoom = 1697;
               itemDefinition.rotation_y = 373;
               itemDefinition.rotation_x = 73;
               itemDefinition.translate_x = 9;
               itemDefinition.translate_yz = -8;
               itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
               itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
               itemDefinition.inventory_model = 28652;
               itemDefinition.equipped_model_male_1 = 28481;
               itemDefinition.equipped_model_female_1 = 28559;
           break;
 
           case 12203:
               itemDefinition.name = "Ancient mitre";
               itemDefinition.modelZoom = 659;
               itemDefinition.rotation_y = 184;
               itemDefinition.rotation_x = 225;
               itemDefinition.translate_x = -3;
               itemDefinition.translate_yz = 0;
               itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
               itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
               itemDefinition.inventory_model = 28701;
               itemDefinition.equipped_model_male_1 = 28511;
               itemDefinition.equipped_model_male_2 = 230;
               itemDefinition.equipped_model_female_1 = 28591;
               itemDefinition.equipped_model_female_2 = 403;
           break;
 
           
 
                case 12225:
                        itemDefinition.name = "Iron platebody (t)";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 33, -22502, 4 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2378;
                        itemDefinition.equipped_model_male_1 = 3379;
                        itemDefinition.equipped_model_male_2 = 164;
                        itemDefinition.equipped_model_female_1 = 3383;
                        itemDefinition.equipped_model_female_2 = 344;
                        break;
 
                case 12227:
                        itemDefinition.name = "Iron platelegs (t)";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { 33, 24, 4 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2582;
                        itemDefinition.equipped_model_male_1 = 268;
                        itemDefinition.equipped_model_female_1 = 432;
                        break;
 
                case 12229:
                        itemDefinition.name = "Iron plateskirt (t)";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 620;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 33, 24, 4, 24 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57, 25238 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 4208;
                        itemDefinition.equipped_model_male_1 = 4206;
                        itemDefinition.equipped_model_female_1 = 4207;
                        break;
 
                case 12231:
                        itemDefinition.name = "Iron full helm (t)";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 152;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 6;
                        itemDefinition.original_model_colors = new int[] { 33, 4 };
                        itemDefinition.modified_model_colors = new int[] { 61, 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2813;
                        itemDefinition.equipped_model_male_1 = 218;
                        itemDefinition.equipped_model_female_1 = 394;
                        break;
 
                case 12233:
                        itemDefinition.name = "Iron kiteshield (t)";
                        itemDefinition.modelZoom = 1560;
                        itemDefinition.rotation_y = 344;
                        itemDefinition.rotation_x = 1104;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.original_model_colors = new int[] { 33, 4, 4 };
                        itemDefinition.modified_model_colors = new int[] { 61, 7054, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2339;
                        itemDefinition.equipped_model_male_1 = 486;
                        itemDefinition.equipped_model_female_1 = 486;
                        break;
 
                case 12235:
                        itemDefinition.name = "Iron platebody (g)";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 33, -22502, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2378;
                        itemDefinition.equipped_model_male_1 = 3379;
                        itemDefinition.equipped_model_male_2 = 164;
                        itemDefinition.equipped_model_female_1 = 3383;
                        itemDefinition.equipped_model_female_2 = 344;
                        break;
 
                case 12237:
                        itemDefinition.name = "Iron platelegs (g)";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { 33, 24, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2582;
                        itemDefinition.equipped_model_male_1 = 268;
                        itemDefinition.equipped_model_female_1 = 432;
                        break;
 
                case 12239:
                        itemDefinition.name = "Iron plateskirt (g)";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 620;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 33, 24, 7114, 24 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57, 25238 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 4208;
                        itemDefinition.equipped_model_male_1 = 4206;
                        itemDefinition.equipped_model_female_1 = 4207;
                        break;
 
                case 12241:
                        itemDefinition.name = "Iron full helm (g)";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 152;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 6;
                        itemDefinition.original_model_colors = new int[] { 33, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 61, 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2813;
                        itemDefinition.equipped_model_male_1 = 218;
                        itemDefinition.equipped_model_female_1 = 394;
                        break;
 
                case 12243:
                        itemDefinition.name = "Iron kiteshield (g)";
                        itemDefinition.modelZoom = 1560;
                        itemDefinition.rotation_y = 344;
                        itemDefinition.rotation_x = 1104;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.original_model_colors = new int[] { 33, 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 61, 7054, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2339;
                        itemDefinition.equipped_model_male_1 = 486;
                        itemDefinition.equipped_model_female_1 = 486;
                        break;
 
                case 12245:
                        itemDefinition.name = "Beanie";
                        itemDefinition.modelZoom = 526;
                        itemDefinition.rotation_y = 67;
                        itemDefinition.rotation_x = 135;
                        itemDefinition.translate_x = 3;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28730;
                        itemDefinition.equipped_model_male_1 = 28519;
                        itemDefinition.equipped_model_female_1 = 28582;
                        break;
 
                		case 12249:
                        itemDefinition.name = "Imp mask";
                        itemDefinition.modelZoom = 779;
                        itemDefinition.rotation_y = 593;
                        itemDefinition.rotation_x = 13;
                        itemDefinition.translate_yz = 17;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28741;
                        itemDefinition.equipped_model_male_1 = 18558;
                        itemDefinition.equipped_model_female_1 = 28555;
                        break;
 
                case 12251:
                        itemDefinition.name = "Goblin mask";
                        itemDefinition.modelZoom = 587;
                        itemDefinition.rotation_y = 566;
                        itemDefinition.translate_x = -1;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28666;
                        itemDefinition.equipped_model_male_1 = 18556;
                        itemDefinition.equipped_model_female_1 = 28553;
                        break;
 
                case 12253:
                        itemDefinition.name = "Armadyl robe top";
                        itemDefinition.modelZoom = 1190;
                        itemDefinition.rotation_y = 476;
                        itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
                        itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28695;
                        itemDefinition.equipped_model_male_1 = 28542;
                        itemDefinition.equipped_model_male_2 = 28529;
                        itemDefinition.equipped_model_female_1 = 28604;
                        itemDefinition.equipped_model_female_2 = 28593;
                        break;
 
                case 12255:
                        itemDefinition.name = "Armadyl robe legs";
                        itemDefinition.modelZoom = 1957;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -5;
                        itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
                        itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28689;
                        itemDefinition.equipped_model_male_1 = 28501;
                        itemDefinition.equipped_model_female_1 = 28564;
                        break;
 
                case 12257:
                        itemDefinition.name = "Armadyl stole";
                        itemDefinition.modelZoom = 1697;
                        itemDefinition.rotation_y = 373;
                        itemDefinition.rotation_x = 73;
                        itemDefinition.translate_x = 9;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28717;
                        itemDefinition.equipped_model_male_1 = 18562;
                        itemDefinition.equipped_model_female_1 = 28556;
                        break;
 
                case 12259:
                        itemDefinition.name = "Armadyl mitre";
                        itemDefinition.modelZoom = 659;
                        itemDefinition.rotation_y = 184;
                        itemDefinition.rotation_x = 225;
                        itemDefinition.translate_x = -3;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28683;
                        itemDefinition.equipped_model_male_1 = 28510;
                        itemDefinition.equipped_model_male_2 = 230;
                        itemDefinition.equipped_model_female_1 = 28579;
                        itemDefinition.equipped_model_female_2 = 403;
                        break;
 
                case 12261:
                        itemDefinition.name = "Armadyl cloak";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 364;
                        itemDefinition.rotation_x = 992;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = 12;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28673;
                        itemDefinition.equipped_model_male_1 = 28484;
                        itemDefinition.equipped_model_female_1 = 28562;
                        break;
 
                case 12263:
                        itemDefinition.name = "Armadyl crozier";
                        itemDefinition.modelZoom = 2151;
                        itemDefinition.rotation_y = 567;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = 4;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28680;
                        itemDefinition.equipped_model_female_1 = 28620;
                        itemDefinition.equipped_model_male_1 = 28620;
                        break;
 
                case 12265:
                        itemDefinition.name = "Bandos robe top";
                        itemDefinition.modelZoom = 1190;
                        itemDefinition.rotation_y = 476;
                        itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
                        itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28722;
                        itemDefinition.equipped_model_male_1 = 28533;
                        itemDefinition.equipped_model_male_2 = 28523;
                        itemDefinition.equipped_model_female_1 = 28605;
                        itemDefinition.equipped_model_female_2 = 28596;
                        break;
 
                case 12267:
                        itemDefinition.name = "Bandos robe legs";
                        itemDefinition.modelZoom = 1957;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -5;
                        itemDefinition.original_model_colors = new int[] { -21612, -21620, -22221 };
                        itemDefinition.modified_model_colors = new int[] { 916, 908, 307 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28686;
                        itemDefinition.equipped_model_male_1 = 28496;
                        itemDefinition.equipped_model_female_1 = 28572;
                        break;
 
                case 12269:
                        itemDefinition.name = "Bandos stole";
                        itemDefinition.modelZoom = 1697;
                        itemDefinition.rotation_y = 373;
                        itemDefinition.rotation_x = 73;
                        itemDefinition.translate_x = 9;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28688;
                        itemDefinition.equipped_model_male_1 = 28482;
                        itemDefinition.equipped_model_female_1 = 28557;
                        break;
 
                case 12271:
                        itemDefinition.name = "Bandos mitre";
                        itemDefinition.modelZoom = 659;
                        itemDefinition.rotation_y = 184;
                        itemDefinition.rotation_x = 225;
                        itemDefinition.translate_x = -3;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28740;
                        itemDefinition.equipped_model_male_1 = 28513;
                        itemDefinition.equipped_model_male_2 = 230;
                        itemDefinition.equipped_model_female_1 = 28580;
                        itemDefinition.equipped_model_female_2 = 403;
                        break;
 
                case 12273:
                        itemDefinition.name = "Bandos cloak";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 364;
                        itemDefinition.rotation_x = 992;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = 12;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28677;
                        itemDefinition.equipped_model_male_1 = 28489;
                        itemDefinition.equipped_model_female_1 = 28561;
                        break;
 
                case 12275:
                        itemDefinition.name = "Bandos crozier";
                        itemDefinition.modelZoom = 2151;
                        itemDefinition.rotation_y = 567;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = 4;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28640;
                        itemDefinition.equipped_model_female_1 = 28627;
                        itemDefinition.equipped_model_male_1 = 28627;
                        break;
 
                
 
                case 12299:
                        itemDefinition.name = "White headband";
                        itemDefinition.modelZoom = 380;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 56;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 119, 119 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 3377;
                        itemDefinition.equipped_model_male_1 = 201;
                        itemDefinition.equipped_model_female_1 = 376;
                        break;
 
                case 12301:
                        itemDefinition.name = "Blue headband";
                        itemDefinition.modelZoom = 380;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 56;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { -24665, -24665 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 3377;
                        itemDefinition.equipped_model_male_1 = 201;
                        itemDefinition.equipped_model_female_1 = 376;
                        break;
 
                case 12303:
                        itemDefinition.name = "Gold headband";
                        itemDefinition.modelZoom = 380;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 56;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 10805, 10805 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 3377;
                        itemDefinition.equipped_model_male_1 = 201;
                        itemDefinition.equipped_model_female_1 = 376;
                        break;
 
                case 12305:
                        itemDefinition.name = "Pink headband";
                        itemDefinition.modelZoom = 380;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 56;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { -7220, -7220 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 3377;
                        itemDefinition.equipped_model_male_1 = 201;
                        itemDefinition.equipped_model_female_1 = 376;
                        break;
 
                case 12307:
                        itemDefinition.name = "Green headband";
                        itemDefinition.modelZoom = 380;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 56;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 22451, 22451 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 3377;
                        itemDefinition.equipped_model_male_1 = 201;
                        itemDefinition.equipped_model_female_1 = 376;
                        break;
 
                case 12309:
                        itemDefinition.name = "Pink boater";
                        itemDefinition.modelZoom = 724;
                        itemDefinition.rotation_y = 166;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -11;
                        itemDefinition.original_model_colors = new int[] { -7220 };
                        itemDefinition.modified_model_colors = new int[] { 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11399;
                        itemDefinition.equipped_model_male_1 = 11330;
                        itemDefinition.equipped_model_female_1 = 11353;
                        break;
 
                case 12311:
                        itemDefinition.name = "Purple boater";
                        itemDefinition.modelZoom = 724;
                        itemDefinition.rotation_y = 166;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -11;
                        itemDefinition.original_model_colors = new int[] { -13389 };
                        itemDefinition.modified_model_colors = new int[] { 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11399;
                        itemDefinition.equipped_model_male_1 = 11330;
                        itemDefinition.equipped_model_female_1 = 11353;
                        break;
 
                case 12313:
                        itemDefinition.name = "White boater";
                        itemDefinition.modelZoom = 724;
                        itemDefinition.rotation_y = 166;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -11;
                        itemDefinition.original_model_colors = new int[] { 119 };
                        itemDefinition.modified_model_colors = new int[] { 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11399;
                        itemDefinition.equipped_model_male_1 = 11330;
                        itemDefinition.equipped_model_female_1 = 11353;
                        break;
 
 
                case 12319:
                        itemDefinition.name = "Crier hat";
                        itemDefinition.modelZoom = 779;
                        itemDefinition.rotation_y = 184;
                        itemDefinition.rotation_x = 225;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28737;
                        itemDefinition.equipped_model_female_1 = 27149;
                        itemDefinition.equipped_model_male_1 = 27149;
                        break;
 
                case 12321:
                        itemDefinition.name = "White cavalier";
                        itemDefinition.modelZoom = 690;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 1856;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { 5231, 24 };
                        itemDefinition.modified_model_colors = new int[] { 7073, 127 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2451;
                        itemDefinition.equipped_model_male_1 = 186;
                        itemDefinition.equipped_model_female_1 = 362;
                        break;
 
                case 12323:
                        itemDefinition.name = "Red cavalier";
                        itemDefinition.modelZoom = 690;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 1856;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { 662 };
                        itemDefinition.modified_model_colors = new int[] { 7073 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2451;
                        itemDefinition.equipped_model_male_1 = 186;
                        itemDefinition.equipped_model_female_1 = 362;
                        break;
 
                case 12325:
                        itemDefinition.name = "Navy cavalier";
                        itemDefinition.modelZoom = 690;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 1856;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { -23903 };
                        itemDefinition.modified_model_colors = new int[] { 7073 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2451;
                        itemDefinition.equipped_model_male_1 = 186;
                        itemDefinition.equipped_model_female_1 = 362;
                        break;
 
                case 12327:
                        itemDefinition.name = "Red d'hide body (g)";
                        itemDefinition.modelZoom = 1030;
                        itemDefinition.rotation_y = 548;
                        itemDefinition.translate_yz = -4;
                        itemDefinition.original_model_colors = new int[] { 912, 540, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11390;
                        itemDefinition.equipped_model_male_1 = 11345;
                        itemDefinition.equipped_model_female_1 = 11361;
                        break;
 
                case 12329:
                        itemDefinition.name = "Red d'hide chaps (g)";
                        itemDefinition.modelZoom = 1720;
                        itemDefinition.rotation_y = 488;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 912, 540, 7114, 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 22408, 24, 7566 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11371;
                        itemDefinition.equipped_model_male_1 = 11411;
                        itemDefinition.equipped_model_female_1 = 11355;
                        break;
 
                case 12331:
                        itemDefinition.name = "Red d'hide body (t)";
                        itemDefinition.modelZoom = 1030;
                        itemDefinition.rotation_y = 548;
                        itemDefinition.translate_yz = -4;
                        itemDefinition.original_model_colors = new int[] { 912, 540, 460 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11390;
                        itemDefinition.equipped_model_male_1 = 11345;
                        itemDefinition.equipped_model_female_1 = 11361;
                        break;
 
                case 12333:
                        itemDefinition.name = "Red d'hide chaps (t)";
                        itemDefinition.modelZoom = 1720;
                        itemDefinition.rotation_y = 488;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 912, 540, 460, 460 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24, 7566 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11371;
                        itemDefinition.equipped_model_male_1 = 11411;
                        itemDefinition.equipped_model_female_1 = 11355;
                        break;
 
                case 12335:
                        itemDefinition.name = "Briefcase";
                        itemDefinition.modelZoom = 968;
                        itemDefinition.rotation_y = 135;
                        itemDefinition.rotation_x = 714;
                        itemDefinition.translate_x = -5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28660;
                        itemDefinition.equipped_model_female_1 = 8954;
                        itemDefinition.equipped_model_male_1 = 8954;
                        break;
 
                case 12337:
                        itemDefinition.name = "Sagacious spectacles";
                        itemDefinition.modelZoom = 463;
                        itemDefinition.rotation_y = 323;
                        itemDefinition.rotation_x = 1024;
                        itemDefinition.translate_x = 1;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28681;
                        itemDefinition.equipped_model_female_1 = 28506;
                        itemDefinition.equipped_model_male_1 = 28506;
                        break;
 
                		case 12343:
                        itemDefinition.name = "Gold ele' blouse";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 11179, 10793, 10793 };
                        itemDefinition.modified_model_colors = new int[] { 105, 64, -10364 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20237;
                        itemDefinition.equipped_model_male_1 = 20159;
                        itemDefinition.equipped_model_male_2 = 20123;
                        itemDefinition.equipped_model_female_1 = 20215;
                        itemDefinition.equipped_model_female_2 = 20179;
                        break;
 
                case 12345:
                        itemDefinition.name = "Gold ele' skirt";
                        itemDefinition.modelZoom = 1187;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 11179, 11179, 10021, 8867, 11179 };
                        itemDefinition.modified_model_colors = new int[] { 105, 64, 47, 26, -10364 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20236;
                        itemDefinition.equipped_model_male_1 = 20141;
                        itemDefinition.equipped_model_female_1 = 20196;
                        break;
 
                case 12347:
                        itemDefinition.name = "Gold ele' shirt";
                        itemDefinition.modelZoom = 1373;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 7;
                        itemDefinition.original_model_colors = new int[] { 109, 11179, 9917, 9771, 8867, 10910 };
                        itemDefinition.modified_model_colors = new int[] { 105, -10364, 64, 47, 35, 26 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20235;
                        itemDefinition.equipped_model_male_1 = 20158;
                        itemDefinition.equipped_model_male_2 = 20122;
                        itemDefinition.equipped_model_female_1 = 20214;
                        itemDefinition.equipped_model_female_2 = 20178;
                        break;
 
                case 12349:
                        itemDefinition.name = "Gold ele' legs";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 11179, 11059, 11179, 9771, 10913 };
                        itemDefinition.modified_model_colors = new int[] { 105, 64, 35, 26, -10364 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20234;
                        itemDefinition.equipped_model_male_1 = 20140;
                        itemDefinition.equipped_model_female_1 = 20195;
                        break;
 
                case 12351:
                        itemDefinition.name = "Musketeer hat";
                        itemDefinition.modelZoom = 1221;
                        itemDefinition.rotation_y = 162;
                        itemDefinition.rotation_x = 1333;
                        itemDefinition.translate_x = -11;
                        itemDefinition.translate_yz = -18;
                        itemDefinition.original_model_colors = new int[] { 920 };
                        itemDefinition.modified_model_colors = new int[] { 29456 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28706;
                        itemDefinition.equipped_model_male_1 = 28516;
                        itemDefinition.equipped_model_female_1 = 28584;
                        break;
 
                
 
                case 12355:
                        itemDefinition.name = "Big pirate hat";
                        itemDefinition.modelZoom = 968;
                        itemDefinition.rotation_y = 566;
                        itemDefinition.translate_yz = 4;
						itemDefinition.original_model_colors = new int[] { 4 };
                        itemDefinition.modified_model_colors = new int[] { 0 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28709;
                        itemDefinition.equipped_model_female_1 = 15004;
                        itemDefinition.equipped_model_male_1 = 15004;
                        break;
 
                case 12357:
                        itemDefinition.name = "Katana";
                        itemDefinition.modelZoom = 2105;
                        itemDefinition.rotation_y = 431;
                        itemDefinition.rotation_x = 768;
                        itemDefinition.translate_x = 9;
                        itemDefinition.translate_yz = 1;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28647;
                        itemDefinition.equipped_model_female_1 = 5233;
                        itemDefinition.equipped_model_male_1 = 5233;
                        break;
 
                case 12359:
                        itemDefinition.name = "Leprechaun hat";
                        itemDefinition.modelZoom = 968;
                        itemDefinition.rotation_y = 0;
                        itemDefinition.rotation_x = 0;
                        itemDefinition.translate_x = 0;
                        itemDefinition.translate_yz = 0;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28642;
                        itemDefinition.equipped_model_male_1 = 28514;
                        itemDefinition.equipped_model_female_1 = 28589;
                        break;
                case 12956:
                	itemDefinition.name = " Cow Top";
                    itemDefinition.modelZoom = 1580;
                    itemDefinition.rotation_y = 544;
                    itemDefinition.rotation_x = 176;
                    itemDefinition.translate_x = 2;
                    itemDefinition.translate_yz = 6;
                    itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                    itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                    itemDefinition.inventory_model = 70201;
                    itemDefinition.equipped_model_male_1 = 70202;
                    itemDefinition.equipped_model_female_1 = 70203;
                    itemDefinition.equipped_model_male_2 = 70204;
                    itemDefinition.equipped_model_female_2 = 70205;
                   break;
                case 12957:
                	itemDefinition.name = " Cow Trousers";
                    itemDefinition.modelZoom = 1410;
                    itemDefinition.rotation_y = 272;
                    itemDefinition.rotation_x = 0;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 19;
                    itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                    itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                    itemDefinition.inventory_model = 70206;
                    itemDefinition.equipped_model_male_1 = 70207;
                    itemDefinition.equipped_model_female_1 = 70208;
                    break;
                case 12958:
                	itemDefinition.name = " Cow Gloves";
                    itemDefinition.modelZoom = 590;
                    itemDefinition.rotation_y = 472;
                    itemDefinition.rotation_x = 1844;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                    itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                    itemDefinition.inventory_model = 70209;
                    itemDefinition.equipped_model_male_1 = 70210;
                    itemDefinition.equipped_model_female_1 = 70211;
                    break;
                case 12959:
                	itemDefinition.name = " Cow Shoes";
                    itemDefinition.modelZoom = 630;
                    itemDefinition.rotation_y = 420;
                    itemDefinition.rotation_x = 0;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 3;
                    itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                    itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                    itemDefinition.inventory_model = 70212;
                    itemDefinition.equipped_model_male_1 = 70213;
                    itemDefinition.equipped_model_female_1 = 70214;
                    break;
                
                case 12845:
                	itemDefinition.name = " Grim Reaper Hood";
                    itemDefinition.modelZoom = 1762;
                    itemDefinition.rotation_y = 2047;
                    itemDefinition.rotation_x = 858;
                    itemDefinition.translate_x = 3;
                    itemDefinition.translate_yz = -26;
                    itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                    itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                    itemDefinition.inventory_model = 70198;
                    itemDefinition.equipped_model_male_1 = 70199;
                    itemDefinition.equipped_model_female_1 = 70200;
                    itemDefinition.description = "A Mask worn by the Grim Reaper himself.".getBytes();
                    break;
                
                case 15902:
                	 itemDefinition.name = " Black Leprechaun hat";
                     itemDefinition.modelZoom = 968;
                     itemDefinition.rotation_y = 0;
                     itemDefinition.rotation_x = 0;
                     itemDefinition.translate_x = 0;
                     itemDefinition.translate_yz = 0;
                     itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                     itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                     itemDefinition.inventory_model = 28642;
                     itemDefinition.original_model_colors = new int[] { 16, 24,8,28,41,0};
                     itemDefinition.modified_model_colors = new int[] { 22181, 21978,22036,22439,21933,22026};
                     itemDefinition.equipped_model_male_1 = 28514;
                     itemDefinition.equipped_model_female_1 = 28589;
                     break;
                case 12361:
                        itemDefinition.name = "Cat mask";
                        itemDefinition.modelZoom = 716;
                        itemDefinition.rotation_y = 108;
                        itemDefinition.rotation_x = 94;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = -11;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28719;
                        itemDefinition.equipped_model_male_1 = 28515;
                        itemDefinition.equipped_model_female_1 = 28586;
                        break;
 
 
 
                case 12373:
                        itemDefinition.name = "Dragon cane";
                        itemDefinition.modelZoom = 3280;
                        itemDefinition.rotation_y = 1679;
                        itemDefinition.rotation_x = 258;
                        itemDefinition.translate_x = 21;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28679;
                        itemDefinition.equipped_model_female_1 = 28626;
                        itemDefinition.equipped_model_male_1 = 28626;
                        break;
 
 
                case 12381:
                        itemDefinition.name = "Black d'hide body (g)";
                        itemDefinition.modelZoom = 1030;
                        itemDefinition.rotation_y = 548;
                        itemDefinition.translate_yz = -4;
                        itemDefinition.original_model_colors = new int[] { 4, 16, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11390;
                        itemDefinition.equipped_model_male_1 = 11345;
                        itemDefinition.equipped_model_female_1 = 11361;
                        break;
 
                case 12383:
                        itemDefinition.name = "Black d'hide chaps (g)";
                        itemDefinition.modelZoom = 1720;
                        itemDefinition.rotation_y = 488;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 4, 16, 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24, 7566 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11371;
                        itemDefinition.equipped_model_male_1 = 11411;
                        itemDefinition.equipped_model_female_1 = 11355;
                        break;
 
                case 12385:
                        itemDefinition.name = "Black d'hide body (t)";
                        itemDefinition.modelZoom = 1030;
                        itemDefinition.rotation_y = 548;
                        itemDefinition.translate_yz = -4;
                        itemDefinition.original_model_colors = new int[] { 4, 16, 47 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11390;
                        itemDefinition.equipped_model_male_1 = 11345;
                        itemDefinition.equipped_model_female_1 = 11361;
                        break;
 
                case 12387:
                        itemDefinition.name = "Black d'hide chaps (t)";
                        itemDefinition.modelZoom = 1720;
                        itemDefinition.rotation_y = 488;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 4, 16, 47, 47 };
                        itemDefinition.modified_model_colors = new int[] { 22416, 22424, 24, 7566 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11371;
                        itemDefinition.equipped_model_male_1 = 11411;
                        itemDefinition.equipped_model_female_1 = 11355;
                        break;
 
                case 12389:
                        itemDefinition.name = "Gilded scimitar";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 300;
                        itemDefinition.rotation_x = 1120;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = 4;
                        itemDefinition.original_model_colors = new int[] { 7114 };
                        itemDefinition.modified_model_colors = new int[] { 61 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 2373;
                        itemDefinition.equipped_model_female_1 = 490;
                        itemDefinition.equipped_model_male_1 = 490;
                        break;
 
                case 12391:
                        itemDefinition.name = "Gilded boots";
                        itemDefinition.modelZoom = 770;
                        itemDefinition.rotation_y = 164;
                        itemDefinition.rotation_x = 156;
                        itemDefinition.translate_x = 3;
                        itemDefinition.translate_yz = -3;
                        itemDefinition.original_model_colors = new int[] { 7114, 7104 };
                        itemDefinition.modified_model_colors = new int[] { 61, 5400 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 5037;
                        itemDefinition.equipped_model_male_1 = 4954;
                        itemDefinition.equipped_model_female_1 = 5031;
                        break;
 
                case 12393:
                        itemDefinition.name = "Royal gown top";
                        itemDefinition.modelZoom = 1347;
                        itemDefinition.rotation_y = 593;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { 1814, 10341 };
                        itemDefinition.modified_model_colors = new int[] { 8741, 8078 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28710;
                        itemDefinition.equipped_model_male_1 = 28541;
                        itemDefinition.equipped_model_male_2 = 28525;
                        itemDefinition.equipped_model_female_1 = 28606;
                        itemDefinition.equipped_model_female_2 = 28599;
                        break;
 
                case 12395:
                        itemDefinition.name = "Royal gown bottom";
                        itemDefinition.modelZoom = 2128;
                        itemDefinition.rotation_y = 516;
                        itemDefinition.rotation_x = 12;
                        itemDefinition.translate_yz = -5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28707;
                        itemDefinition.equipped_model_male_1 = 28492;
                        itemDefinition.equipped_model_female_1 = 28566;
                        break;
 
                case 12397:
                        itemDefinition.name = "Royal crown";
                        itemDefinition.modelZoom = 463;
                        itemDefinition.rotation_y = 40;
                        itemDefinition.rotation_x = 54;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28659;
                        itemDefinition.equipped_model_female_1 = 1808;
                        itemDefinition.equipped_model_male_1 = 1808;
                        break;
 
                case 12399:
                        itemDefinition.name = "Partyhat & specs";
                        itemDefinition.modelZoom = 653;
                        itemDefinition.rotation_y = 242;
                        itemDefinition.rotation_x = 0;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -59;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28693;
                        itemDefinition.equipped_model_male_1 = 28505;
                        itemDefinition.equipped_model_female_1 = 28576;
                        break;
						
				case 12026:
						itemDefinition.inventory_model = 2635;
						itemDefinition.name = "Black partyhat";
						itemDefinition.modelZoom = 440;
						itemDefinition.rotation_y = 76;
						itemDefinition.rotation_x = 1852;
						itemDefinition.translate_x = 1;
						itemDefinition.translate_yz = 1;
						itemDefinition.equipped_model_male_1 = 187;
						itemDefinition.equipped_model_female_1 = 363;
						itemDefinition.actions = new String[5];
						itemDefinition.actions[1] = "Wear";
						itemDefinition.actions[4] = "Drop";
						itemDefinition.original_model_colors = new int[1];
						itemDefinition.modified_model_colors = new int[1];
						itemDefinition.original_model_colors[0] = 1;
						itemDefinition.modified_model_colors[0] = 926;
						break;
				
				
				case 13000:
						itemDefinition.inventory_model = 9001;
						itemDefinition.name = "Rainbow partyhat";
						itemDefinition.modelZoom = 440;
						itemDefinition.rotation_y = 1845;
						itemDefinition.rotation_x = 121;
						itemDefinition.translate_x = 0;
						itemDefinition.translate_yz = 1;
						itemDefinition.equipped_model_male_1 = 9000;
						itemDefinition.equipped_model_female_1 = 9002;
						itemDefinition.groundActions = new String[5];
						itemDefinition.groundActions[2] = "Take";
						itemDefinition.actions = new String[5];
						itemDefinition.actions[1] = "Wear";
						break;
						
				case 13002:
					itemDefinition.name = "Black h'ween mask";
					itemDefinition.actions = new String[5];
					   itemDefinition.actions[1] = "Wear";
					   itemDefinition.original_model_colors = new int[2]; //This is the amount of colors your changing the model of, make sure both are the same
					   itemDefinition.modified_model_colors = new int[2]; //This is the amount of colors your changing the model of, make sure both are the same
					   itemDefinition.original_model_colors[0] = 8; //This is the color you'renderable changing the model to.
					   itemDefinition.modified_model_colors[0] = 926; //This is the Original color of the Model (This has to be exact value)
					   itemDefinition.original_model_colors[1] = 9152;
					   itemDefinition.modified_model_colors[1] = 0;
					   itemDefinition.inventory_model = 2438;
					   itemDefinition.modelZoom = 730;
					   itemDefinition.rotation_y = 516;
					   itemDefinition.rotation_x = 0;
					   itemDefinition.rotation_z = 0;
					   itemDefinition.translate_x = 0;
					   itemDefinition.translate_yz = -9;
					   itemDefinition.equipped_model_male_1 = 3188; //Male Wear Id
					   itemDefinition.equipped_model_female_1 = 3192; //Female Wear Id
					   itemDefinition.equipped_model_male_2 = -1;
					   itemDefinition.equipped_model_female_2 = -1;
					   itemDefinition.equipped_model_male_dialogue_1 = -1;
					   itemDefinition.equipped_model_female_dialogue_1 = -1;
					   itemDefinition.description = "Aaaarrrghhh ... I'm a monster.".getBytes();
					   break;
				case 15901:
					itemDefinition.actions = new String[5];
					itemDefinition.actions[1] = "Wear";
					itemDefinition.original_model_colors = new int[2];
					itemDefinition.modified_model_colors = new int[2];
					itemDefinition.original_model_colors[0] = 918;
					itemDefinition.modified_model_colors[0] = 10351;
					itemDefinition.original_model_colors[1] = 10351;
					itemDefinition.modified_model_colors[1] = 933;
					itemDefinition.inventory_model = 2537;
					itemDefinition.modelZoom = 540;
					itemDefinition.rotation_y = 72;
					itemDefinition.rotation_x = 136;
					itemDefinition.translate_x = 0;
					itemDefinition.translate_yz = -3;
					itemDefinition.equipped_model_male_1 = 189;
					itemDefinition.equipped_model_female_1 = 366;
					itemDefinition.name = "Inverted santa hat";
					break;	

					case 13004:
						itemDefinition.actions = new String[5];
						itemDefinition.actions[1] = "Wear";
						itemDefinition.original_model_colors = new int[1];
						itemDefinition.modified_model_colors = new int[1];
						itemDefinition.original_model_colors[0] = 0;
						itemDefinition.modified_model_colors[0] = 933;
						itemDefinition.inventory_model = 2537;
						itemDefinition.modelZoom = 540;
						itemDefinition.rotation_y = 72;
						itemDefinition.rotation_x = 136;
						itemDefinition.rotation_z = 0;
						itemDefinition.translate_x = 0;
						itemDefinition.translate_yz = 0;
						itemDefinition.equipped_model_male_1 = 189;
						itemDefinition.equipped_model_female_1 = 366;
						itemDefinition.name = "Black santa hat";
						break;

 
                case 12412:
                        itemDefinition.name = "Pirate hat & patch";
                        itemDefinition.modelZoom = 968;
                        itemDefinition.rotation_y = 714;
						itemDefinition.original_model_colors = new int[] { 4 };
                        itemDefinition.modified_model_colors = new int[] { 0 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28731;
                        itemDefinition.equipped_model_female_1 = 3476;
                        itemDefinition.equipped_model_male_1 = 3476;
                        break;
 
                case 12414:
                        itemDefinition.name = "Dragon chainbody (g)";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 568;
                        itemDefinition.translate_yz = 2;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 28712;
                        itemDefinition.equipped_model_male_1 = 28540;
                        itemDefinition.equipped_model_male_2 = 156;
						itemDefinition.equipped_model_female_1 = 28540;
						itemDefinition.equipped_model_female_2 = 337;
                        break;
 
                case 12415:
                        itemDefinition.name = "Dragon platelegs (g)";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 912, 908 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 5026;
                        itemDefinition.equipped_model_male_1 = 5024;
                        itemDefinition.equipped_model_female_1 = 5025;
                        break;
 
                case 12416:
                        itemDefinition.name = "Dragon plateskirt (g)";
                        itemDefinition.modelZoom = 1230;
                        itemDefinition.rotation_y = 584;
                        itemDefinition.translate_x = 4;
                        itemDefinition.translate_yz = 15;
                        itemDefinition.original_model_colors = new int[] { 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 918, 908 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 6032;
                        itemDefinition.equipped_model_male_1 = 6029;
                        itemDefinition.equipped_model_female_1 = 6030;
                        break;
 
                case 12417:
                        itemDefinition.name = "Dragon full helm (g)";
                        itemDefinition.modelZoom = 871;
                        itemDefinition.rotation_y = 291;
                        itemDefinition.rotation_x = 90;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 7114, 7114, 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 914, 902, 10314, 10306 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 26662;
                        itemDefinition.equipped_model_male_1 = 26632;
                        itemDefinition.equipped_model_female_1 = 26658;
                        break;
 
                case 12418:
                        itemDefinition.name = "Dragon sq shield (g)";
                        itemDefinition.modelZoom = 1730;
                        itemDefinition.rotation_y = 352;
                        itemDefinition.rotation_x = 120;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = 10;
                        itemDefinition.original_model_colors = new int[] { 7114, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 7054, 37 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 2840;
                        itemDefinition.equipped_model_female_1 = 565;
                        itemDefinition.equipped_model_male_1 = 565;
                        break;
 
                case 12419:
                        itemDefinition.name = "Light infinity hat";
                        itemDefinition.modelZoom = 1150;
                        itemDefinition.rotation_y = 112;
                        itemDefinition.rotation_x = 68;
                        itemDefinition.original_model_colors = new int[] { 107, 7114, 5239, 6253, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 9152, 41920, 17979 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10583;
                        itemDefinition.equipped_model_male_1 = 10684;
                        itemDefinition.equipped_model_female_1 = 10691;
                        break;
 
                case 12420:
                        itemDefinition.name = "Light infinity top";
                        itemDefinition.modelZoom = 1380;
                        itemDefinition.rotation_y = 468;
                        itemDefinition.rotation_x = 2044;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 107, 7114, 6622, 6253, 5239, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 24512, 46016, 9152, 58316 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10586;
                        itemDefinition.equipped_model_male_1 = 10687;
                        itemDefinition.equipped_model_male_2 = 10681;
                        itemDefinition.equipped_model_female_1 = 10694;
                        itemDefinition.equipped_model_female_2 = 10688;
                        break;
 
                case 12421:
                        itemDefinition.name = "Light infinity bottoms";
                        itemDefinition.modelZoom = 1760;
                        itemDefinition.rotation_y = 304;
                        itemDefinition.translate_yz = 30;
                        itemDefinition.original_model_colors = new int[] { 107, 7114, 6622, 6253, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 24512, 46016, 58316 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10585;
                        itemDefinition.equipped_model_male_1 = 10686;
                        itemDefinition.equipped_model_female_1 = 10693;
                        break;
 
                case 12422:
                        itemDefinition.name = "3rd age wand";
                        itemDefinition.modelZoom = 1347;
                        itemDefinition.rotation_y = 1468;
                        itemDefinition.rotation_x = 1805;
                        itemDefinition.translate_yz = 1;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28654;
                        itemDefinition.equipped_model_female_1 = 28619;
                        itemDefinition.equipped_model_male_1 = 28619;
                        break;
 
                case 12424:
                        itemDefinition.name = "3rd age bow";
                        itemDefinition.modelZoom = 1979;
                        itemDefinition.rotation_y = 1589;
                        itemDefinition.rotation_x = 768;
                        itemDefinition.translate_x = -20;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28678;
                        itemDefinition.equipped_model_female_1 = 28622;
                        itemDefinition.equipped_model_male_1 = 28622;
                        break;
 
                case 12426:
                        itemDefinition.name = "3rd age longsword";
                        itemDefinition.modelZoom = 1726;
                        itemDefinition.rotation_y = 1576;
                        itemDefinition.rotation_x = 242;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 4;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28633;
                        itemDefinition.equipped_model_female_1 = 28618;
                        itemDefinition.equipped_model_male_1 = 28618;
                        break;
 
                case 12428:
                        itemDefinition.name = "Penguin mask";
                        itemDefinition.modelZoom = 1032;
                        itemDefinition.rotation_y = 539;
                        itemDefinition.rotation_x = 417;
                        itemDefinition.translate_x = 8;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28674;
                        itemDefinition.equipped_model_male_1 = 18560;
                        itemDefinition.equipped_model_female_1 = 28551;
                        break;
 
                case 12430:
                        itemDefinition.name = "Afro";
                        itemDefinition.modelZoom = 779;
                        itemDefinition.rotation_x = 1441;
                        itemDefinition.translate_x = -4;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28650;
                        itemDefinition.equipped_model_male_1 = 18553;
                        itemDefinition.equipped_model_female_1 = 28550;
                        break;
 
                case 12432:
                        itemDefinition.name = "Top hat";
                        itemDefinition.modelZoom = 589;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28643;
                        itemDefinition.equipped_model_male_1 = 28517;
                        itemDefinition.equipped_model_female_1 = 28587;
                        break;
 
                
 
                case 12436:
                        itemDefinition.name = "Amulet of fury (or)";
                        itemDefinition.modelZoom = 550;
                        itemDefinition.rotation_y = 340;
                        itemDefinition.rotation_x = 26;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = 29;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 28715;
                        itemDefinition.equipped_model_male_1 = 28480;
                        itemDefinition.equipped_model_female_1 = 28558;
                        break;
 
                case 12437:
                        itemDefinition.name = "3rd age cloak";
                        itemDefinition.rotation_y = 282;
                        itemDefinition.rotation_x = 962;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28648;
                        itemDefinition.equipped_model_male_1 = 28483;
                        itemDefinition.equipped_model_female_1 = 28560;
                        break;
 
                case 12439:
                        itemDefinition.name = "Royal sceptre";
                        itemDefinition.rotation_y = 1835;
                        itemDefinition.rotation_x = 1868;
                        itemDefinition.translate_x = -5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", null, null, "Drop" };
                        itemDefinition.inventory_model = 28630;
                        itemDefinition.equipped_model_female_1 = 28625;
                        itemDefinition.equipped_model_male_1 = 28625;
                        break;
 
                
 
                case 12445:
                        itemDefinition.name = "Black skirt (g)";
                        itemDefinition.modelZoom = 1940;
                        itemDefinition.rotation_y = 572;
                        itemDefinition.translate_yz = 1;
                        itemDefinition.original_model_colors = new int[] { 4, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 25238, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11395;
                        itemDefinition.equipped_model_male_1 = 10982;
                        itemDefinition.equipped_model_female_1 = 11357;
                        break;
 
                case 12447:
                        itemDefinition.name = "Black skirt (t)";
                        itemDefinition.modelZoom = 1940;
                        itemDefinition.rotation_y = 572;
                        itemDefinition.translate_yz = 1;
                        itemDefinition.original_model_colors = new int[] { 4, 103 };
                        itemDefinition.modified_model_colors = new int[] { 25238, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11395;
                        itemDefinition.equipped_model_male_1 = 10982;
                        itemDefinition.equipped_model_female_1 = 11357;
                        break;
 
                case 12449:
                        itemDefinition.name = "Black wizard robe (g)";
                        itemDefinition.modelZoom = 1370;
                        itemDefinition.rotation_y = 536;
                        itemDefinition.translate_yz = 7;
                        itemDefinition.original_model_colors = new int[] { 4, 7114 };
                        itemDefinition.modified_model_colors = new int[] { 8741, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11396;
                        itemDefinition.equipped_model_male_1 = 10979;
                        itemDefinition.equipped_model_male_2 = 10984;
                        itemDefinition.equipped_model_female_1 = 11348;
                        itemDefinition.equipped_model_female_2 = 11359;
                        break;
 
                case 12451:
                        itemDefinition.name = "Black wizard robe (t)";
                        itemDefinition.modelZoom = 1370;
                        itemDefinition.rotation_y = 536;
                        itemDefinition.translate_yz = 7;
                        itemDefinition.original_model_colors = new int[] { 4, 103 };
                        itemDefinition.modified_model_colors = new int[] { 8741, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11396;
                        itemDefinition.equipped_model_male_1 = 10979;
                        itemDefinition.equipped_model_male_2 = 10984;
                        itemDefinition.equipped_model_female_1 = 11348;
                        itemDefinition.equipped_model_female_2 = 11359;
                        break;
 
                case 12453:
                        itemDefinition.name = "Black wizard hat (g)";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 152;
                        itemDefinition.rotation_x = 156;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = -10;
                        itemDefinition.original_model_colors = new int[] { 7114, 4 };
						itemDefinition.modified_model_colors = new int[] { 24, 38814 };
						itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11400;
                        itemDefinition.equipped_model_male_1 = 11331;
                        itemDefinition.equipped_model_female_1 = 11354;
                        break;
 
                case 12455:
                        itemDefinition.name = "Black wizard hat (t)";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 152;
                        itemDefinition.rotation_x = 156;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = -10;
                        itemDefinition.original_model_colors = new int[] { 103, 4 };
                        itemDefinition.modified_model_colors = new int[] { 24, 38814 };
						//itemDefinition.recolourTarget = new int[] { 24, -26722 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 11400;
                        itemDefinition.equipped_model_male_1 = 11331;
                        itemDefinition.equipped_model_female_1 = 11354;
                        break;
 
                case 12457:
                        itemDefinition.name = "Dark infinity hat";
                        itemDefinition.modelZoom = 1150;
                        itemDefinition.rotation_y = 112;
                        itemDefinition.rotation_x = 68;
                        itemDefinition.original_model_colors = new int[] { 4, -8535, -10344, -7220, -8535 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 9152, 41920, 17979 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10583;
                        itemDefinition.equipped_model_male_1 = 10684;
                        itemDefinition.equipped_model_female_1 = 10691;
                        break;
 
                case 12458:
                        itemDefinition.name = "Dark infinity top";
                        itemDefinition.modelZoom = 1380;
                        itemDefinition.rotation_y = 468;
                        itemDefinition.rotation_x = 2044;
                        itemDefinition.translate_x = -1;
                        itemDefinition.original_model_colors = new int[] { 4, -8535, -10344, 4, 4, -8535 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 24512, 46016, 9152, 58316 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10586;
                        itemDefinition.equipped_model_male_1 = 10687;
                        itemDefinition.equipped_model_male_2 = 10681;
                        itemDefinition.equipped_model_female_1 = 10694;
                        itemDefinition.equipped_model_female_2 = 10688;
                        break;
 
                case 12459:
                        itemDefinition.name = "Dark infinity bottoms";
                        itemDefinition.modelZoom = 1760;
                        itemDefinition.rotation_y = 304;
                        itemDefinition.translate_yz = 30;
                        itemDefinition.original_model_colors = new int[] { 4, -8535, -10344, 4, -8535 };
                        itemDefinition.modified_model_colors = new int[] { 695, 55977, 24512, 46016, 58316 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Dismantle" };
                        itemDefinition.inventory_model = 10585;
                        itemDefinition.equipped_model_male_1 = 10686;
                        itemDefinition.equipped_model_female_1 = 10693;
                        break;
 
                case 12460:
                        itemDefinition.name = "Ancient platebody";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, -10854 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28657;
                        itemDefinition.equipped_model_male_1 = 3379;
                        itemDefinition.equipped_model_male_2 = 164;
                        itemDefinition.equipped_model_female_1 = 3383;
                        itemDefinition.equipped_model_female_2 = 344;
                        break;
 
                case 12462:
                        itemDefinition.name = "Ancient platelegs";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, -10854 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2582;
                        itemDefinition.equipped_model_male_1 = 268;
                        itemDefinition.equipped_model_female_1 = 432;
                        break;
 
                case 12464:
                        itemDefinition.name = "Ancient plateskirt";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 620;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, -10854, -30316 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57, 25238 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 4208;
                        itemDefinition.equipped_model_male_1 = 4206;
                        itemDefinition.equipped_model_female_1 = 4207;
                        break;
 
                case 12466:
                        itemDefinition.name = "Ancient full helm";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 152;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 6;
                        itemDefinition.original_model_colors = new int[] { -29403, -10854 };
                        itemDefinition.modified_model_colors = new int[] { 61, 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2813;
                        itemDefinition.equipped_model_male_1 = 218;
                        itemDefinition.equipped_model_female_1 = 394;
                        break;
 
                case 12468:
                        itemDefinition.name = "Ancient kiteshield";
                        itemDefinition.modelZoom = 1560;
                        itemDefinition.rotation_y = 344;
                        itemDefinition.rotation_x = 1104;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.original_model_colors = new int[] { -29403, -10854, -10854 };
                        itemDefinition.modified_model_colors = new int[] { 61, 7054, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2339;
                        itemDefinition.equipped_model_female_1 = 486;
                        itemDefinition.equipped_model_male_1 = 486;
                        break;
 
                case 12470:
                        itemDefinition.name = "Armadyl platebody";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 53 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28725;
                        itemDefinition.equipped_model_male_1 = 3379;
                        itemDefinition.equipped_model_male_2 = 164;
                        itemDefinition.equipped_model_female_1 = 3383;
                        itemDefinition.equipped_model_female_2 = 344;
                        break;
 
                case 12472:
                        itemDefinition.name = "Armadyl platelegs";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 53 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2582;
                        itemDefinition.equipped_model_male_1 = 268;
                        itemDefinition.equipped_model_female_1 = 432;
                        break;
 
                case 12474:
                        itemDefinition.name = "Armadyl plateskirt";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 620;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 53, -30316 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57, 25238 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 4208;
                        itemDefinition.equipped_model_male_1 = 4206;
                        itemDefinition.equipped_model_female_1 = 4207;
                        break;
 
                case 12476:
                        itemDefinition.name = "Armadyl full helm";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 152;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 6;
                        itemDefinition.original_model_colors = new int[] { -29403, 53 };
                        itemDefinition.modified_model_colors = new int[] { 61, 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2813;
                        itemDefinition.equipped_model_male_1 = 218;
                        itemDefinition.equipped_model_female_1 = 394;
                        break;
 
                case 12478:
                        itemDefinition.name = "Armadyl kiteshield";
                        itemDefinition.modelZoom = 1560;
                        itemDefinition.rotation_y = 344;
                        itemDefinition.rotation_x = 1104;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.original_model_colors = new int[] { -29403, 53, 53 };
                        itemDefinition.modified_model_colors = new int[] { 61, 7054, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2339;
                        itemDefinition.equipped_model_female_1 = 486;
                        itemDefinition.equipped_model_male_1 = 486;
                        break;
 
                case 12480:
                        itemDefinition.name = "Bandos platebody";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 7845 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 24 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28704;
                        itemDefinition.equipped_model_male_1 = 3379;
                        itemDefinition.equipped_model_male_2 = 164;
                        itemDefinition.equipped_model_female_1 = 3383;
                        itemDefinition.equipped_model_female_2 = 344;
                        break;
 
                case 12482:
                        itemDefinition.name = "Bandos platelegs";
                        itemDefinition.modelZoom = 1740;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -8;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 7845 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2582;
                        itemDefinition.equipped_model_male_1 = 268;
                        itemDefinition.equipped_model_female_1 = 432;
                        break;
 
                case 12484:
                        itemDefinition.name = "Bandos plateskirt";
                        itemDefinition.modelZoom = 1100;
                        itemDefinition.rotation_y = 620;
                        itemDefinition.translate_x = 5;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.original_model_colors = new int[] { -29403, -28266, 7845, -30316 };
                        itemDefinition.modified_model_colors = new int[] { 61, 41, 57, 25238 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 4208;
                        itemDefinition.equipped_model_male_1 = 4206;
                        itemDefinition.equipped_model_female_1 = 4207;
                        break;
                case 12486:
                        itemDefinition.name = "Bandos full helm";
                        itemDefinition.modelZoom = 800;
                        itemDefinition.rotation_y = 160;
                        itemDefinition.rotation_x = 152;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = 6;
                        itemDefinition.original_model_colors = new int[] { -29403, 7845 };
                        itemDefinition.modified_model_colors = new int[] { 61, 926 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2813;
                        itemDefinition.equipped_model_male_1 = 218;
                        itemDefinition.equipped_model_female_1 = 394;
                        break;
 
                case 12488:
                        itemDefinition.name = "Bandos kiteshield";
                        itemDefinition.modelZoom = 1560;
                        itemDefinition.rotation_y = 344;
                        itemDefinition.rotation_x = 1104;
                        itemDefinition.translate_x = -6;
                        itemDefinition.translate_yz = -14;
                        itemDefinition.original_model_colors = new int[] { -29403, 7845, 7845 };
                        itemDefinition.modified_model_colors = new int[] { 61, 7054, 57 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 2339;
                        itemDefinition.equipped_model_female_1 = 486;
                        itemDefinition.equipped_model_male_1 = 486;
                        break;
 
                case 12490:
                        itemDefinition.name = "Ancient bracers";
                        itemDefinition.modelZoom = 740;
                        itemDefinition.rotation_y = 196;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = -33;
                        itemDefinition.original_model_colors = new int[] { -10854, -10329, -10329 };
                        itemDefinition.modified_model_colors = new int[] { 22156, 912, 22408 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20264;
                        itemDefinition.equipped_model_male_1 = 20129;
                        itemDefinition.equipped_model_female_1 = 20180;
                        break;
 
                case 12492:
                        itemDefinition.name = "Ancient d'hide";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -12900, -11101, -12900, -10854, -10329, -10329 };
                        itemDefinition.modified_model_colors = new int[] { -27417, 24082, 22168, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28670;
                        itemDefinition.equipped_model_male_1 = 28543;
                        itemDefinition.equipped_model_male_2 = 20121;
                        itemDefinition.equipped_model_female_1 = 28601;
                        itemDefinition.equipped_model_female_2 = 20177;
                        break;
 
                case 12494:
                        itemDefinition.name = "Ancient chaps";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -12900, -10329, -11101, -10854, -10329, -10329 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20230;
                        itemDefinition.equipped_model_male_1 = 20139;
                        itemDefinition.equipped_model_female_1 = 20194;
                        break;
 
                case 12496:
                        itemDefinition.name = "Ancient coif";
                        itemDefinition.modelZoom = 789;
                        itemDefinition.rotation_y = 194;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -35;
                        itemDefinition.original_model_colors = new int[] { -12900, -10329, -11101, -10854, -10329, -10329 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20231;
                        itemDefinition.equipped_model_male_1 = 20131;
                        itemDefinition.equipped_model_male_2 = 230;
                        itemDefinition.equipped_model_female_1 = 20184;
                        itemDefinition.equipped_model_female_2 = 403;
                        break;
 
                case 12498:
                        itemDefinition.name = "Bandos bracers";
                        itemDefinition.modelZoom = 740;
                        itemDefinition.rotation_y = 196;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = -33;
                        itemDefinition.original_model_colors = new int[] { 6323, 6352, 6352 };
                        itemDefinition.modified_model_colors = new int[] { 22156, 912, 22408 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20264;
                        itemDefinition.equipped_model_male_1 = 20129;
                        itemDefinition.equipped_model_female_1 = 20180;
                        break;
 
                case 12500:
                        itemDefinition.name = "Bandos d'hide";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 8377, 3127, 8377, 6323, 6352, 6352 };
                        itemDefinition.modified_model_colors = new int[] { -27417, 24082, 22168, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28748;
                        itemDefinition.equipped_model_male_1 = 28539;
                        itemDefinition.equipped_model_male_2 = 20121;
                        itemDefinition.equipped_model_female_1 = 28615;
                        itemDefinition.equipped_model_female_2 = 20177;
                        break;
 
                case 12502:
                        itemDefinition.name = "Bandos chaps";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 8377, 6352, 3127, 6323, 6352, 6352 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20230;
                        itemDefinition.equipped_model_male_1 = 20139;
                        itemDefinition.equipped_model_female_1 = 20194;
                        break;
 
                case 12504:
                        itemDefinition.name = "Bandos coif";
                        itemDefinition.modelZoom = 789;
                        itemDefinition.rotation_y = 194;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -35;
                        itemDefinition.original_model_colors = new int[] { 8377, 6352, 3127, 6323, 6352, 6352 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20231;
                        itemDefinition.equipped_model_male_1 = 20131;
                        itemDefinition.equipped_model_male_2 = 230;
                        itemDefinition.equipped_model_female_1 = 20184;
                        itemDefinition.equipped_model_female_2 = 403;
                        break;
 
                case 12506:
                        itemDefinition.name = "Armadyl bracers";
                        itemDefinition.modelZoom = 740;
                        itemDefinition.rotation_y = 196;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_x = 7;
                        itemDefinition.translate_yz = -33;
                        itemDefinition.original_model_colors = new int[] { 107, 119, 119 };
                        itemDefinition.modified_model_colors = new int[] { 22156, 912, 22408 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20264;
                        itemDefinition.equipped_model_male_1 = 20129;
                        itemDefinition.equipped_model_female_1 = 20180;
                        break;
 
                case 12508:
                        itemDefinition.name = "Armadyl d'hide";
                        itemDefinition.modelZoom = 1180;
                        itemDefinition.rotation_y = 452;
                        itemDefinition.translate_x = -1;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { 119, 94, -27417, 107, 119 };
                        itemDefinition.modified_model_colors = new int[] { 912, 24082, 22168, 22156, 920 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28668;
                        itemDefinition.equipped_model_male_1 = 28530;
                        itemDefinition.equipped_model_male_2 = 20121;
                        itemDefinition.equipped_model_female_1 = 28610;
                        itemDefinition.equipped_model_female_2 = 20177;
                        break;
 
                case 12510:
                        itemDefinition.name = "Armadyl chaps";
                        itemDefinition.modelZoom = 1827;
                        itemDefinition.rotation_y = 444;
                        itemDefinition.translate_yz = -1;
                        itemDefinition.original_model_colors = new int[] { -27417, 119, 94, 107, 119, 119 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20230;
                        itemDefinition.equipped_model_male_1 = 20139;
                        itemDefinition.equipped_model_female_1 = 20194;
                        break;
 
                case 12512:
                        itemDefinition.name = "Armadyl coif";
                        itemDefinition.modelZoom = 789;
                        itemDefinition.rotation_y = 194;
                        itemDefinition.rotation_x = 1784;
                        itemDefinition.translate_yz = -35;
                        itemDefinition.original_model_colors = new int[] { -27417, 119, 94, 107, 119, 119 };
                        itemDefinition.modified_model_colors = new int[] { 22168, 8070, 24082, 22156, 920, 912 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 20231;
                        itemDefinition.equipped_model_male_1 = 20131;
                        itemDefinition.equipped_model_male_2 = 230;
                        itemDefinition.equipped_model_female_1 = 20184;
                        itemDefinition.equipped_model_female_2 = 403;
                        break;
 
                case 12514:
                        itemDefinition.name = "Explorer backpack";
                        itemDefinition.modelZoom = 1040;
                        itemDefinition.rotation_y = 454;
                        itemDefinition.rotation_x = 1283;
                        itemDefinition.translate_x = -4;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28637;
                        itemDefinition.equipped_model_female_1 = 28485;
                        itemDefinition.equipped_model_male_1 = 28485;
                        break;
 
                
                		case 12530:
                        itemDefinition.name = "Light infinity colour kit";
                        itemDefinition.modelZoom = 1616;
                        itemDefinition.rotation_y = 564;
                        itemDefinition.rotation_x = 1943;
                        itemDefinition.translate_x = -10;
                        itemDefinition.translate_yz = 20;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28692;
                        break;
 
 
                case 12596:
                        itemDefinition.name = "Rangers' tunic";
                        itemDefinition.modelZoom = 1616;
                        itemDefinition.rotation_y = 564;
                        itemDefinition.rotation_x = 75;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28816;
                        itemDefinition.equipped_model_male_1 = 28810;
                        itemDefinition.equipped_model_male_2 = 28809;//male arm
                        itemDefinition.equipped_model_female_1 = 28813;
                        itemDefinition.equipped_model_female_2 = 28812;//female arm
                        break;
 
                case 12598:
                        itemDefinition.name = "Holy sandals";
                        itemDefinition.modelZoom = 848;
                        itemDefinition.rotation_y = 363;
                        itemDefinition.rotation_x = 120;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28815;
                        itemDefinition.equipped_model_male_1 = 28811;
                        itemDefinition.equipped_model_female_1 = 28814;
                        break;
 
                
 
                case 12601:
                        itemDefinition.name = "Ring of the gods";
                        itemDefinition.modelZoom = 900;
                        itemDefinition.rotation_y = 393;
                        itemDefinition.rotation_x = 1589;
                        itemDefinition.translate_x = -9;
                        itemDefinition.translate_yz = -12;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28824;
                        break;
 
                case 12603:
                        itemDefinition.name = "Tyrannical ring";
                        itemDefinition.modelZoom = 592;
                        itemDefinition.rotation_y = 285;
                        itemDefinition.rotation_x = 1163;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28823;
                        break;
 
                case 12605:
                        itemDefinition.name = "Treasonous ring";
                        itemDefinition.modelZoom = 750;
                        itemDefinition.rotation_y = 342;
                        itemDefinition.rotation_x = 252;
                        itemDefinition.translate_x = -3;
                        itemDefinition.translate_yz = -12;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDefinition.inventory_model = 28825;
                        break;
 
                
 
                case 12608:
                        itemDefinition.name = "Book of War";
                        itemDefinition.modelZoom = 830;
                        itemDefinition.rotation_y = 244;
                        itemDefinition.rotation_x = 116;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = -21;
                        itemDefinition.original_model_colors = new int[] { 5425, 11177, 61 };
                        itemDefinition.modified_model_colors = new int[] { 5018, 61, 10351 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", "Preach", null, "Drop" };
                        itemDefinition.inventory_model = 2543;
                        itemDefinition.equipped_model_female_1 = 556;
                        itemDefinition.equipped_model_male_1 = 556;
                        break;
 
               
 
                case 12610:
                        itemDefinition.name = "Book of Law";
                        itemDefinition.modelZoom = 830;
                        itemDefinition.rotation_y = 244;
                        itemDefinition.rotation_x = 116;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = -21;
                        itemDefinition.original_model_colors = new int[] { -22440, 11177, 61 };
                        itemDefinition.modified_model_colors = new int[] { 5018, 61, 10351 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", "Preach", null, "Drop" };
                        itemDefinition.inventory_model = 2543;
                        itemDefinition.equipped_model_female_1 = 556;
                        itemDefinition.equipped_model_male_1 = 556;
                        break;
 
                
 
                case 12612:
                        itemDefinition.name = "Book of Darkness";
                        itemDefinition.modelZoom = 830;
                        itemDefinition.rotation_y = 244;
                        itemDefinition.rotation_x = 116;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = -21;
                        itemDefinition.original_model_colors = new int[] { -16870, 11177, 61 };
                        itemDefinition.modified_model_colors = new int[] { 5018, 61, 10351 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, "Wield", "Preach", null, "Drop" };
                        itemDefinition.inventory_model = 2543;
                        itemDefinition.equipped_model_female_1 = 556;
                        itemDefinition.equipped_model_male_1 = 556;
                        break;
 
                
                case 12643:
                        itemDefinition.name = "Pet dagannoth supreme";
                        itemDefinition.modelZoom = 4560;
                        itemDefinition.rotation_y = 2042;
                        itemDefinition.rotation_x = 1868;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 9941;
                        break;
 
                case 12644:
                        itemDefinition.name = "Pet dagannoth prime";
                        itemDefinition.modelZoom = 4560;
                        itemDefinition.rotation_y = 2042;
                        itemDefinition.rotation_x = 1868;
                        itemDefinition.original_model_colors = new int[] { 5931, 1688, 21530, 21534 };
                        itemDefinition.original_model_colors = new int[] { 11930, 27144, 16536, 16540 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 9941;
                        break;
 
                case 12645:
                        itemDefinition.name = "Pet dagannoth rex";
                        itemDefinition.modelZoom = 4560;
                        itemDefinition.rotation_y = 2042;
                        itemDefinition.rotation_x = 1868;
                        itemDefinition.modified_model_colors = new int[] { 7322, 7326, 10403, 2595 };
                        itemDefinition.original_model_colors = new int[] { 16536, 16540, 27144, 2477 };
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 9941;
                        break;
 
                case 12646:
                        itemDefinition.name = "Baby mole";
                        itemDefinition.modelZoom = 2256;
                        itemDefinition.rotation_y = 369;
                        itemDefinition.rotation_x = 1874;
                        itemDefinition.translate_yz = 20;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 12073;
                        break;
 
                case 12647:
                        itemDefinition.name = "Kalphite princess";
                        itemDefinition.modelZoom = 8016;
                        itemDefinition.rotation_y = 342;
                        itemDefinition.rotation_x = 1778;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 24597;
                        break;
 
                case 12648:
                        itemDefinition.name = "Pet smoke devil";
                        itemDefinition.modelZoom = 3984;
                        itemDefinition.rotation_y = 9;
                        itemDefinition.rotation_x = 1862;
                        itemDefinition.translate_yz = 20;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28442;
                        break;
 
                case 12649:
                        itemDefinition.name = "Pet kree'arra";
                        itemDefinition.modelZoom = 1000;
                        itemDefinition.rotation_y = 2042;
                        itemDefinition.rotation_x = 1892;
                        itemDefinition.translate_x = -20;
                        itemDefinition.translate_yz = 5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28873;
                        break;
 
                case 12650:
                        itemDefinition.name = "Pet general graardor";
                        itemDefinition.modelZoom = 1872;
                        itemDefinition.translate_x = -3;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28874;
                        itemDefinition.modelZoom = 1800;
                        itemDefinition.rotation_y = 342;
                        itemDefinition.rotation_x = 1778;
                        break;
 
                case 12651:
                        itemDefinition.name = "Pet zilyana";
                        itemDefinition.modelZoom = 1000;
                        itemDefinition.rotation_y = 200;
                        itemDefinition.rotation_x = 1931;
                        itemDefinition.translate_x = 5;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28870;
                        break;
 
                case 12652:
                        itemDefinition.name = "Pet k'ril tsutsaroth";
                        itemDefinition.modelZoom = 1168;
                        itemDefinition.rotation_y = 2012;
                        itemDefinition.rotation_x = 100;
                        itemDefinition.translate_x = 1;
                        itemDefinition.translate_yz = 2;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28868;
                        break;
 
                case 12653:
                        itemDefinition.name = "Prince black dragon";
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28872;
                        itemDefinition.rotation_y = 700;
                        itemDefinition.modelZoom = 1300;
                        itemDefinition.rotation_x = 0;
                        break;
 
                case 12654:
                        itemDefinition.name = "Kalphite princess";
                        itemDefinition.modelZoom = 740;
                        itemDefinition.rotation_y = 279;
                        itemDefinition.rotation_x = 1808;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28871;
                        break;
 
                case 12655:
                        itemDefinition.name = "Pet kraken";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28869;
                        break;
                case 12703:
                        itemDefinition.name = "Pet penance queen";
                        itemDefinition.modelZoom = 8358;
                        itemDefinition.rotation_x = 81;
                        itemDefinition.translate_x = 17;
                        itemDefinition.translate_yz = 33;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 20711;
                        break;
				
					case 12656:
                        itemDefinition.name = "Callisto Cub";
                        itemDefinition.modelZoom = 4744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        //itemDefinition.inventory_model = 28869;
						itemDefinition.inventory_model = 28298;

						break;
						
					case 12657:
                        itemDefinition.name = "Scorpia Offspring";
                        itemDefinition.modelZoom = 4744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
						itemDefinition.inventory_model = 28293;

                        break;
						
					case 12658:
                        itemDefinition.name = "Venenatis Spiderling";
                        itemDefinition.modelZoom = 4744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
						itemDefinition.inventory_model = 28294;
                        break;
						
					case 12659:
                        itemDefinition.name = "Vet'ion Jr";
                        itemDefinition.modelZoom = 4744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
						itemDefinition.inventory_model = 28299;
                        break;
						
					case 12660:
                        itemDefinition.name = "Pet Cave kraken";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 28869;
                        break;
                        
					case 12661:
                        itemDefinition.name = "Pet Ahrim Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6668;
                        break;
                        
					case 12662:
						itemDefinition.name = "Pet Dharok Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6678;
                        
                        break;
                        
					case 12663:
						itemDefinition.name = "Pet Guthan Jr";
                        itemDefinition.modelZoom = 1744;
                        
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6678;
                        break;
					case 12664:
						itemDefinition.name = "Pet Torag Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6678;
                        break;
					case 12665:
						itemDefinition.name = "Pet Karil Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6675;
                        break;
					case 12666:
						itemDefinition.name = "Pet Verac Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 6678;
                        break;
					case 12667:
						itemDefinition.name = "Pet Barrelchest Jr";
                        itemDefinition.modelZoom = 5000;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        itemDefinition.inventory_model = 22790;
                        break;
					case 12668:
						itemDefinition.name = "Pet Boris Jr";
                        itemDefinition.modelZoom = 1744;
                        itemDefinition.rotation_y = 330;
                        itemDefinition.rotation_x = 1940;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        ItemDefinition Boris = lookup(6026);
                        itemDefinition.inventory_model = Boris.inventory_model;
                        break;
					case 12669:
						itemDefinition.name = "Pet Skeletal Drakling";
                        itemDefinition.modelZoom = 875;
                        itemDefinition.rotation_y = 30;
                        itemDefinition.rotation_x = 15;
                        itemDefinition.groundActions = new String[] { null, null, "Take", null, null };
                        itemDefinition.actions = new String[] { null, null, null, null, "Drop" };
                        ItemDefinition wyvern = lookup(6811);
                        itemDefinition.inventory_model = wyvern.inventory_model;
                        break;
                        
                        
                    
		
			
		/* Agile Set */

				case 11849:	
                	itemDefinition.name = "Mark of grace";
                	itemDefinition.description = "You can exchange these for rewards at the Rogues' Den.".getBytes();
                	itemDefinition.rotation_y = 588;
                	itemDefinition.rotation_x = 0;
                    itemDefinition.translate_x = 2;
                    itemDefinition.translate_yz = -5;
                    itemDefinition.modelZoom = 1600;
                    itemDefinition.inventory_model = 16245;
                    itemDefinition.actions = new String[5];
                    break;
                    
                case 11850:
                	itemDefinition.name = "Graceful hood";
                	itemDefinition.description = "A sign of your grace.".getBytes();
                	itemDefinition.rotation_y = 639;
                	itemDefinition.rotation_x = 24;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.modelZoom = 848;
                    itemDefinition.inventory_model = 28894;
                    itemDefinition.equipped_model_male_1 = 28875;
                    itemDefinition.equipped_model_female_1 = 28882;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
                    
                case 11852:
                	itemDefinition.name = "Graceful cape";
                	itemDefinition.description = "A sign of your grace.".getBytes();
                	itemDefinition.rotation_y = 564;
                	itemDefinition.rotation_x = 827;
                    itemDefinition.translate_x = 2;
                    itemDefinition.translate_yz = 2;
                    itemDefinition.modelZoom = 2320;
                    itemDefinition.inventory_model = 28893;
                    itemDefinition.equipped_model_male_1 = 28876;
                    itemDefinition.equipped_model_female_1 = 28883;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
                case 15908:
                	itemDefinition.name = "Cabbage cape";
                	itemDefinition.description = "A Cape meant for the masters of cabbage picking.".getBytes();
                	itemDefinition.modelZoom = 2128;
                	itemDefinition.rotation_y = 504;
                	itemDefinition.rotation_x = 0;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 1;
                    itemDefinition.inventory_model = 70217;
                    itemDefinition.equipped_model_male_1 = 70218;
                    itemDefinition.equipped_model_female_1 = 70219;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
                case 15909:
                	itemDefinition.name = "Gnome Child Hat";
                	itemDefinition.description = "A Hat meant for the Gnomiest of Gnomes.".getBytes();
                	itemDefinition.modelZoom = 400;
                	itemDefinition.rotation_y = 2047;
                	itemDefinition.rotation_x = 373;
                    itemDefinition.translate_x = 7;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.inventory_model = 70220;
                    itemDefinition.equipped_model_male_1 = 70221;
                    itemDefinition.equipped_model_female_1 = 70222;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    itemDefinition.modified_model_colors = new int[1];
                    itemDefinition.original_model_colors = new int[1];
                    itemDefinition.modified_model_colors[0] = 22426;
            		itemDefinition.original_model_colors[0] = 11592;
                    break;
                    
                case 15910:
                	itemDefinition.name = "Holy Wraps";
                	itemDefinition.modelZoom = 789;
                	itemDefinition.rotation_y = 609;
                	itemDefinition.rotation_x = 111;
                    itemDefinition.translate_x = 1;
                    itemDefinition.translate_yz = 1;
                    itemDefinition.inventory_model = 70223;
                    itemDefinition.equipped_model_male_1 = 70224;
                    itemDefinition.equipped_model_female_1 = 70225;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                   break;
                case 15911:
                	itemDefinition.name = "Farmers Strawhat";
                	itemDefinition.modelZoom = 905;
             	   itemDefinition.rotation_y = 189;
             	   itemDefinition.rotation_x = 1885;
             	   itemDefinition.translate_x = -8;
             	   itemDefinition.translate_yz = -25;
                    itemDefinition.inventory_model = 70255;
                    itemDefinition.equipped_model_male_1 = 70256;
                    itemDefinition.equipped_model_female_1 = 70257;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                   break;
                case 15912:
                	itemDefinition.name = "Farmers jacket";
                	itemDefinition.modelZoom = 1537;
                	itemDefinition.rotation_y = 458;
                	itemDefinition.rotation_x = 202;
                    itemDefinition.translate_x = 7;
                    itemDefinition.translate_yz = 17;
                    itemDefinition.inventory_model = 70258;
                    itemDefinition.equipped_model_male_1 = 70259;
                    itemDefinition.equipped_model_female_1 = 70260;
                    itemDefinition.equipped_model_female_2 = 70261;
                    itemDefinition.equipped_model_male_2 = 70262;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                   break;
                case 15913:
                	itemDefinition.name = "Boro Trousers";
                	itemDefinition.modelZoom = 1916;
                	itemDefinition.rotation_y = 1508;
                	itemDefinition.rotation_x = 741;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = -3;
                    itemDefinition.inventory_model = 70263;
                    itemDefinition.equipped_model_male_1 = 70264;
                    itemDefinition.equipped_model_female_1 = 70265;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                   break;
                case 15914:
                	itemDefinition.name = "Farmers Boots";
                	itemDefinition.rotation_y = 78;
                	itemDefinition.rotation_x = 1781;
                    itemDefinition.translate_x = 3;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.modelZoom = 976;
                    itemDefinition.inventory_model = 70266;
                    itemDefinition.equipped_model_male_1 = 70267;
                    itemDefinition.equipped_model_female_1 = 70268;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                   break;
                /*commented out due to not having arms model at the moment.
                 * case 11854:
                	itemDefinition.name = "Graceful top";
                	itemDefinition.description = "A sign of your grace.".getBytes();
                	itemDefinition.rotation_y = 666;
                	itemDefinition.rotation_x = 9;
                    itemDefinition.translate_x = 0;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.modelZoom = 1232;
                    itemDefinition.inventory_model = 28889;
                    itemDefinition.equipped_model_male_1 = 28879;
                    itemDefinition.equipped_model_female_1 = 28886;
                    itemDefinition.interactions = new String[5];
                    itemDefinition.interactions[1] = "Wear";
                    itemDefinition.interactions[4] = "Drop";
                    break;
                    */
                case 11856:
                	itemDefinition.name = "Graceful legs";
                	itemDefinition.description = "You have graceful legs. How nice.".getBytes();
                	itemDefinition.rotation_y = 525;
                	itemDefinition.rotation_x = 171;
                    itemDefinition.translate_x = 10;
                    itemDefinition.translate_yz = 10;
                    itemDefinition.modelZoom = 1744;
                    itemDefinition.inventory_model = 28891;
                    itemDefinition.equipped_model_male_1 = 28878;
                    itemDefinition.equipped_model_female_1 = 28885;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
                    
                case 11858:
                	itemDefinition.name = "Graceful gloves";
                	itemDefinition.description = "A sign of your grace.".getBytes();
                	itemDefinition.rotation_y = 636;
                	itemDefinition.rotation_x = 2015;
                    itemDefinition.translate_x = 3;
                    itemDefinition.translate_yz = 3;
                    itemDefinition.modelZoom = 592;
                    itemDefinition.inventory_model = 28892;
                    itemDefinition.equipped_model_male_1 = 28877;
                    itemDefinition.equipped_model_female_1 = 28884;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
                    
                case 11860:
                	itemDefinition.name = "Graceful boots";
                	itemDefinition.description = "A sign of your grace.".getBytes();
                	itemDefinition.rotation_y = 78;
                	itemDefinition.rotation_x = 1781;
                    itemDefinition.translate_x = 3;
                    itemDefinition.translate_yz = 0;
                    itemDefinition.modelZoom = 976;
                    itemDefinition.inventory_model = 28890;
                    itemDefinition.equipped_model_male_1 = 28881;
                    itemDefinition.equipped_model_female_1 = 28888;
                    itemDefinition.actions = new String[5];
                    itemDefinition.actions[1] = "Wear";
                    itemDefinition.actions[4] = "Drop";
                    break;
			
	

		case 14999:// 13738:
			itemDefinition.inventory_model = 40922;
			itemDefinition.name = "Arcane spirit shield";
			itemDefinition.description = "It's a Arcane spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 2000000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40944;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40944;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13739;
			itemDefinition.noted_item_id = -1;
			break;

			
		case 2704://easy clues
        case 2703:    
		case 2702:	
		case 2701:
		case 2700:
		case 2699:
		case 2698:
			itemDefinition.name = "Clue Scroll @gre@[Easy]";
            itemDefinition.description = "An easy clue scroll with low-level rewards.".getBytes();
			break;

		case 2697://medium clues
        case 2696:    
		case 2695:	
		case 2693:
		case 2692:
		case 2691:
		case 2690:
		case 2689:
		case 2688:
			itemDefinition.name = "Clue Scroll @yel@[Medium]";
            itemDefinition.description = "A medium clue scroll with mid-level rewards.".getBytes();
			break;
		
		case 2713://hard clues
        case 2712:    
		case 2711:	
		case 2710:
		case 2709:
		case 2708:
		case 2707:
		case 2706:
		case 2705:
			itemDefinition.name = "Clue Scroll @red@[Hard]";
            itemDefinition.description = "A hard clue scroll with a chance for high-level rewards.".getBytes();
			break;
		
		 case 620://100m ticket
            itemDefinition.name = "@red@100m Certificate";
            itemDefinition.description = "This can be cashed in for what it is worth.".getBytes();
			itemDefinition.actions = new String[5];
            itemDefinition.actions[4] = "Drop";
            itemDefinition.actions[3] = "Cash In";//2nd click
			break;
			
		case 15000:// 13740:
			itemDefinition.inventory_model = 40921;
			itemDefinition.name = "Divine spirit shield";
			itemDefinition.description = "It's a Divine spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 2000000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40939;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40939;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13741;
			itemDefinition.noted_item_id = -1;
			break;
		case 15001:// 13734:
			itemDefinition.inventory_model = 40919;
			itemDefinition.name = "Spirit shield";
			itemDefinition.description = "It's a Spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 70000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40943;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40943;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13735;
			itemDefinition.noted_item_id = -1;
			break;
		case 15002:// 13742:
			itemDefinition.inventory_model = 40915;
			itemDefinition.name = "Elysian spirit shield";
			itemDefinition.description = "It's a Elysian spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 2000000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40942;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40942;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13743;
			itemDefinition.noted_item_id = -1;
			break;
		case 15003:// 13744:
			itemDefinition.inventory_model = 40920;
			itemDefinition.name = "Spectral spirit shield";
			itemDefinition.description = "It's a Spectral spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 2000000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40940;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40940;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13745;
			itemDefinition.noted_item_id = -1;
			break;
		case 15004:// 13736:
			itemDefinition.inventory_model = 40913;
			itemDefinition.name = "Blessed spirit shield";
			itemDefinition.description = "It's a Blessed spirit shield".getBytes();
			itemDefinition.modelZoom = 1616;
			itemDefinition.rotation_y = 396;
			itemDefinition.rotation_x = 1050;
			itemDefinition.translate_x = -3;
			itemDefinition.translate_yz = 4;
			itemDefinition.stackable = false;
			itemDefinition.value = 1400000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 40941;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 40941;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13737;
			itemDefinition.noted_item_id = -1;
			break;
		case 13884:
			itemDefinition.inventory_model = 42602;
			itemDefinition.name = "Statius's platebody";
			itemDefinition.description = "An ancient platebody.".getBytes();
			itemDefinition.modelZoom = 1312;
			itemDefinition.rotation_y = 272;
			itemDefinition.rotation_x = 2047;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 500000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42625;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42641;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13885;
			itemDefinition.noted_item_id = -1;
			break;
		case 13887:
			itemDefinition.inventory_model = 42593;
			itemDefinition.name = "Vesta's chainbody";
			itemDefinition.description = "An ancient chainbody.".getBytes();
			itemDefinition.modelZoom = 1440;
			itemDefinition.rotation_y = 545;
			itemDefinition.rotation_x = 2;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 500000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42624;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42644;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13888;
			itemDefinition.noted_item_id = -1;
			break;
		case 13890:
			itemDefinition.inventory_model = 42590;
			itemDefinition.name = "Statius's platelegs";
			itemDefinition.description = "Ancient platelegs.".getBytes();
			itemDefinition.modelZoom = 1625;
			itemDefinition.rotation_y = 355;
			itemDefinition.rotation_x = 2046;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 41248;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42632;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42647;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13891;
			itemDefinition.noted_item_id = -1;
			break;
		case 13893:
			itemDefinition.inventory_model = 42581;
			itemDefinition.name = "Vesta's platelegs";
			itemDefinition.description = "A set of ancient platelegs.".getBytes();
			itemDefinition.modelZoom = 1753;
			itemDefinition.rotation_y = 562;
			itemDefinition.rotation_x = 1;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 500000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42633;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42649;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13894;
			itemDefinition.noted_item_id = -1;
			break;
		case 13896:
			itemDefinition.inventory_model = 42596;
			itemDefinition.name = "Statius's helmet";
			itemDefinition.description = "An ancient helmet.".getBytes();
			itemDefinition.modelZoom = 789;
			itemDefinition.rotation_y = 96;
			itemDefinition.rotation_x = 2039;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 250000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42639;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42655;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13897;
			itemDefinition.noted_item_id = -1;
			break;
		case 13899:
			itemDefinition.inventory_model = 42597;
			itemDefinition.name = "Vesta's longsword";
			itemDefinition.description = "A powerful longsword.".getBytes();
			itemDefinition.modelZoom = 1744;
			itemDefinition.rotation_y = 738;
			itemDefinition.rotation_x = 1985;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 300000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wield";
			itemDefinition.equipped_model_male_1 = 42615;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42615;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13900;
			itemDefinition.noted_item_id = -1;
			break;
		case 13902:
			itemDefinition.inventory_model = 42577;
			itemDefinition.name = "Statius's warhammer";
			itemDefinition.description = "A powerful warhammer.".getBytes();
			itemDefinition.modelZoom = 1360;
			itemDefinition.rotation_y = 507;
			itemDefinition.rotation_x = 27;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 300000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42623;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42623;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13903;
			itemDefinition.noted_item_id = -1;
			break;
		case 13905:
			itemDefinition.inventory_model = 42599;
			itemDefinition.name = "Vesta's spear";
			itemDefinition.description = "A powerful spear.".getBytes();
			itemDefinition.modelZoom = 2022;
			itemDefinition.rotation_y = 480;
			itemDefinition.rotation_x = 15;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.stackable = false;
			itemDefinition.value = 300000;
			itemDefinition.is_members_only = true;
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.equipped_model_male_1 = 42614;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_1 = 42614;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.unnoted_item_id = 13906;
			itemDefinition.noted_item_id = -1;
			break;
			case 14610:
				itemDefinition.actions = lookup(981).actions;
				itemDefinition.inventory_model = lookup(981).inventory_model;
				itemDefinition.equipped_model_male_1 = lookup(981).equipped_model_male_1;
				itemDefinition.equipped_model_female_1 = lookup(981).equipped_model_female_1;
				itemDefinition.modelZoom = lookup(981).modelZoom;
				itemDefinition.rotation_y = lookup(981).rotation_y;
				itemDefinition.rotation_x = lookup(981).rotation_x;
				itemDefinition.translate_x = lookup(981).translate_x;
				itemDefinition.translate_yz = lookup(981).translate_yz;
				itemDefinition.name = lookup(981).name;
				itemDefinition.description = lookup(981).description;
			break;
			case 13858:
			itemDefinition.name = "Zuriel's robe top";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42591;
			itemDefinition.modelZoom = 1373;
			itemDefinition.rotation_y = 373;
			itemDefinition.rotation_x = 0;
			itemDefinition.translate_yz = -7;
			itemDefinition.translate_x = 0;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42627;
			itemDefinition.equipped_model_female_1 = 42642;
			itemDefinition.description = "Zuriel's robe top, a reward from PVP.".getBytes();
			break;
		case 13861:
			itemDefinition.name = "Zuriel's robe bottom";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42588;
			itemDefinition.modelZoom = 1697;
			itemDefinition.rotation_y = 512;
			itemDefinition.rotation_x = 0;
			itemDefinition.translate_yz = -9;
			itemDefinition.translate_x = 2;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42634;
			itemDefinition.equipped_model_female_1 = 42645;
			itemDefinition.description = "Zuriel's robe bottom, a reward from PVP.".getBytes();
			break;
		case 13864:
			itemDefinition.name = "Zuriel's hood";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42604;
			itemDefinition.modelZoom = 720;
			itemDefinition.rotation_y = 28;
			itemDefinition.rotation_x = 0;
			itemDefinition.translate_yz = 1;
			itemDefinition.translate_x = 1;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42638;
			itemDefinition.equipped_model_female_1 = 42653;
			itemDefinition.description = "Zuriel's hood, a reward from PVP.".getBytes();
			break;
		case 13867:
			itemDefinition.name = "Zuriel's staff";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Equip";
			itemDefinition.inventory_model = 42595;
			itemDefinition.modelZoom = 2000;
			itemDefinition.rotation_y = 366;
			itemDefinition.rotation_x = 3;
			itemDefinition.translate_yz = 0;
			itemDefinition.translate_x = 0;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42617;
			itemDefinition.equipped_model_female_1 = 42617;
			itemDefinition.description = "Zuriel's staff, a reward from PVP.".getBytes();
			break;
			case 13870:
			itemDefinition.name = "Morrigan's leather body";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42578;
			itemDefinition.modelZoom = 1184;
			itemDefinition.rotation_y = 545;
			itemDefinition.rotation_x = 2;
			itemDefinition.translate_yz = 5;
			itemDefinition.translate_x = 4;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42626;
			itemDefinition.equipped_model_female_1 = 42643;
			itemDefinition.description = "Morrigan's leather body, a reward from PVP.".getBytes();
			break;

		case 13873:
			itemDefinition.name = "Morrigan's leather chaps";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42603;
			itemDefinition.modelZoom = 1753;
			itemDefinition.rotation_y = 482;
			itemDefinition.rotation_x = 1;
			itemDefinition.translate_yz = 11;
			itemDefinition.translate_x = -3;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42631;
			itemDefinition.equipped_model_female_1 = 42646;
			itemDefinition.description = "Morrigan's leather chaps, a reward from PVP.".getBytes();
			break;

		case 13876:
			itemDefinition.name = "Morrigan's coif";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 42583;
			itemDefinition.modelZoom = 592;
			itemDefinition.rotation_y = 537;
			itemDefinition.rotation_x = 5;
			itemDefinition.translate_yz = 6;
			itemDefinition.translate_x = -3;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42636;
			itemDefinition.equipped_model_female_1 = 42652;
			itemDefinition.description = "Morrigan's coif, a reward from PVP.".getBytes();
			break;
		/*Commented out due to broken animations and not in the game currently.
		 * case 13879:
			itemDefinition.name = "Morrigan's javelin";
			itemDefinition.interactions = new String[5];
			itemDefinition.interactions[1] = "Wield";
			itemDefinition.inventory_model = 42592;
			itemDefinition.modelZoom = 1872;
			itemDefinition.rotation_y = 282;
			itemDefinition.rotation_x = 2009;
			itemDefinition.translate_yz = 0;
			itemDefinition.translate_x = 0;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42613;
			itemDefinition.equipped_model_female_1 = 42613;
			itemDefinition.description = "Morrigan's javelin, a reward from PVP.".getBytes();
			break;
		case 13883:
			itemDefinition.name = "Morrigan's throwing axe";
			itemDefinition.interactions = new String[5];
			itemDefinition.interactions[1] = "Wield";
			itemDefinition.inventory_model = 42582;
			itemDefinition.modelZoom = 976;
			itemDefinition.rotation_y = 672;
			itemDefinition.rotation_x = 2024;
			itemDefinition.translate_yz = 4;
			itemDefinition.translate_x = -5;
			itemDefinition.rotation_z = 0;
			itemDefinition.equipped_model_male_1 = 42611;
			itemDefinition.equipped_model_female_1 = 42611;
			itemDefinition.description = "Morrigan's throwing axe, a reward from PVP.".getBytes();
			break;
			*/
		case 15271:
			itemDefinition.actions = new String[5];
			itemDefinition.inventory_model = 48722;
			itemDefinition.modelZoom = 1460;
			itemDefinition.rotation_y = 499;
			itemDefinition.rotation_x = 1926;
			itemDefinition.translate_x = 3;
			itemDefinition.translate_yz = 0;
			itemDefinition.name = "Raw rocktail";
			itemDefinition.description = "I should try cooking this.".getBytes();
		break;
		case 15274:
			itemDefinition.actions = new String[5];
			itemDefinition.inventory_model = 48725;
			itemDefinition.modelZoom = 1460;
			itemDefinition.rotation_y = 499;
			itemDefinition.rotation_x = 1926;
			itemDefinition.translate_x = 3;
			itemDefinition.translate_yz = 0;
			itemDefinition.name = "Burnt rocktail";
			itemDefinition.description = "Oops! Maybe a little less heat next time.".getBytes();
		break;
		
		case 15021:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wield";
			itemDefinition.equipped_model_female_1 = 51802;//female
			itemDefinition.translate_x = -5;
			itemDefinition.translate_yz = 2;
			itemDefinition.modelZoom = 1490;
			itemDefinition.rotation_x = 1400;
			itemDefinition.rotation_y = 148;
			itemDefinition.equipped_model_male_1 = 51800;
			itemDefinition.inventory_model = 51799;
			itemDefinition.name = "Staff of light";
			itemDefinition.description = "Humming with power.".getBytes();
		break;
		case 12000:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 44576;
			itemDefinition.modelZoom = 1360;
			itemDefinition.rotation_y = 443;
			itemDefinition.rotation_x = 2047;
			itemDefinition.translate_x = -1;
			itemDefinition.translate_yz = 0;
			itemDefinition.equipped_model_male_1 = 40207;
			itemDefinition.equipped_model_female_1 = 40427;
			itemDefinition.stackable = false;
			itemDefinition.name = "Dragon platebody";
			itemDefinition.description = "Provides excellent protection.".getBytes();
		break;
		case 13263:
			itemDefinition.name = "Slayer helmet";
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 51844;
			itemDefinition.equipped_model_male_1 = 51797;
			itemDefinition.stackable = false;
			itemDefinition.rotation_x = 1743;
			itemDefinition.modelZoom = 789;
			itemDefinition.value = 650;
			itemDefinition.equipped_model_female_1 = 51819;
			itemDefinition.translate_x = -4;
			itemDefinition.translate_yz = -3;
			itemDefinition.rotation_y = 69;
			itemDefinition.description = "It's a Slayer helmet.".getBytes();
		break;
		case 12010:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 47327;
			itemDefinition.modelZoom = 1663;
			itemDefinition.rotation_y = 593;
			itemDefinition.rotation_x = 0;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 1;
			itemDefinition.equipped_model_male_1 = 47265;
			itemDefinition.equipped_model_female_1 = 47268;
			itemDefinition.equipped_model_male_2 = 47266;
			itemDefinition.equipped_model_female_2 = 47269;
			itemDefinition.name = "Agile top";
			itemDefinition.description = "It's a Agile top.".getBytes();
		break;
		case 12011:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 47328;
			itemDefinition.modelZoom = 1979;
			itemDefinition.rotation_y = 458;
			itemDefinition.rotation_x = 0;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 4;
			itemDefinition.equipped_model_male_1 = 47264;
			itemDefinition.equipped_model_female_1 = 47267;
			itemDefinition.equipped_model_male_2 = -1;
			itemDefinition.equipped_model_female_2 = -1;
			itemDefinition.name = "Agile legs";
			itemDefinition.description = "It's a Agile legs.".getBytes();
		break;
		case 15903:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 24000;
			itemDefinition.modelZoom = 1488;
			itemDefinition.rotation_y = 595;
			itemDefinition.rotation_x = 2047;
			itemDefinition.translate_x = -8;
			itemDefinition.translate_yz = -12;
			itemDefinition.equipped_model_male_1 = 24037;
			itemDefinition.equipped_model_female_1 = 24048;
			itemDefinition.equipped_model_female_2 = 24044;
			itemDefinition.equipped_model_male_2 = 24032;
			itemDefinition.original_model_colors = new int[] { 0, 20, 142,};
			itemDefinition.modified_model_colors = new int[] { 10574, 10471, 10438,};
			itemDefinition.name = "Evil Chicken Wings";
			itemDefinition.description = "It's the wings of an evil chicken suit.".getBytes();
			break;
		case 15904:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 23999;
			itemDefinition.modelZoom = 1744;
			itemDefinition.rotation_y = 622;
			itemDefinition.rotation_x = 2047;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 0;
			itemDefinition.equipped_model_male_1 = 24035;
			itemDefinition.equipped_model_female_1 = 24047;
			itemDefinition.original_model_colors = new int[] { 4, 142};
			itemDefinition.modified_model_colors = new int[] { 10471, 9694};
			itemDefinition.name = "Evil Chicken Legs";
			itemDefinition.description = "It's the Legs of an evil chicken suit.".getBytes();
			break;
		case 15905:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 23998;
			itemDefinition.modelZoom = 1049;
			itemDefinition.rotation_y = 609;
			itemDefinition.rotation_x = 2047;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = -3;
			itemDefinition.equipped_model_male_1 = 24034;
			itemDefinition.equipped_model_female_1 = 24046;
			itemDefinition.original_model_colors = new int[] { 4, 681};
			itemDefinition.modified_model_colors = new int[] { 10471, 972,};
			itemDefinition.name = "Evil Chicken Head";
			itemDefinition.description = "It's the Head of an evil chicken suit.".getBytes();
			break;
		case 15906:
			itemDefinition.actions = new String[5];
			itemDefinition.actions[1] = "Wear";
			itemDefinition.inventory_model = 23997;
			itemDefinition.modelZoom = 848;
			itemDefinition.rotation_y = 127;
			itemDefinition.rotation_x = 1931;
			itemDefinition.translate_x = -4;
			itemDefinition.translate_yz = -5;
			itemDefinition.equipped_model_male_1 = 24033;
			itemDefinition.equipped_model_female_1 = 24045;
			itemDefinition.original_model_colors = new int[] { 681, 0,565};
			itemDefinition.modified_model_colors = new int[] { 7104, 103,7114};
			itemDefinition.name = "Evil Chicken Feet";
			itemDefinition.description = "It's the Feet of an evil chicken suit.".getBytes();
			break;
		
		
		case 6542:
			itemDefinition.name = "ASL Mystery Box";
			break;
			

			
		/*
		 * Start of herb fix
		 */
		case 199: //Guam
		case 201: //Marrentill
		case 203: //Tarromin
		case 205: //Harralander
		case 207: //Ranarr
		case 3049: //Toadflax
		case 209: //Irit
		case 211: //Avantoe
		case 213: //Kwuarm
		case 3051: //SnapDragon
		case 215: //Cadantine
		case 2485: //Lantadyme
		case 217: //Dwarfweed
		case 219: //Torstol
			itemDefinition.inventory_model = 2364;
			itemDefinition.modelZoom = 700;
			itemDefinition.rotation_y = 376;
			itemDefinition.rotation_x = 1588;
			itemDefinition.translate_x = 0;
			itemDefinition.translate_yz = 4;
			break;
		}
		return itemDefinition;
	}

	public void toNote() {
		ItemDefinition itemDefinition = lookup(noted_item_id);
		inventory_model = itemDefinition.inventory_model;
		modelZoom = itemDefinition.modelZoom;
		rotation_y = itemDefinition.rotation_y;
		rotation_x = itemDefinition.rotation_x;

		rotation_z = itemDefinition.rotation_z;
		translate_x = itemDefinition.translate_x;
		translate_yz = itemDefinition.translate_yz;
		modified_model_colors = itemDefinition.modified_model_colors;
		original_model_colors = itemDefinition.original_model_colors;
		ItemDefinition itemDefinition_1 = lookup(unnoted_item_id);
		name = itemDefinition_1.name;
		is_members_only = itemDefinition_1.is_members_only;
		value = itemDefinition_1.value;
		String s = "a";
		char c = itemDefinition_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			s = "an";
		description = ("Swap this note at any bank for " + s + " "
				+ itemDefinition_1.name + ".").getBytes();
		stackable = true;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) sprites.get(i);
			if (sprite != null && sprite.maxHeight != j
					&& sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}
			if (sprite != null)
				return sprite;
		}
		ItemDefinition itemDefinition = lookup(i);
		if (itemDefinition.stack_variant_id == null)
			j = -1;
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (j >= itemDefinition.stack_variant_size[j1]
						&& itemDefinition.stack_variant_size[j1] != 0)
					i1 = itemDefinition.stack_variant_id[j1];

			if (i1 != -1)
				itemDefinition = lookup(i1);
		}
		Model model = itemDefinition.getModel(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDefinition.noted_item_id != -1) {
			sprite = getSprite(itemDefinition.unnoted_item_id, 10, -1);
			if (sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer3D.originViewX;
		int l1 = Rasterizer3D.originViewY;
		int ai[] = Rasterizer3D.scanOffsets;
		int ai1[] = Rasterizer2D.pixels;
		int i2 = Rasterizer2D.width;
		int j2 = Rasterizer2D.height;
		int k2 = Rasterizer2D.leftX;
		int l2 = Rasterizer2D.bottomX;
		int i3 = Rasterizer2D.topY;
		int j3 = Rasterizer2D.bottomY;
		Rasterizer3D.restrict_edges = false;
		Rasterizer2D.initDrawingArea(32, 32, sprite2.myPixels);
		Rasterizer2D.drawBox(32, 0, 0, 0, 32);
		Rasterizer3D.useViewport();
		int k3 = itemDefinition.modelZoom;
		if (k == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (k > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer3D.SINE[itemDefinition.rotation_y] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[itemDefinition.rotation_y] * k3 >> 16;
		model.method482(itemDefinition.rotation_x, itemDefinition.rotation_z,
				itemDefinition.rotation_y, itemDefinition.translate_x, l3
						+ model.modelBaseY / 2 + itemDefinition.translate_yz, i4
						+ itemDefinition.translate_yz);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--)
				if (sprite2.myPixels[i5 + j4 * 32] == 0)
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 < 31
							&& sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;

		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (sprite2.myPixels[j5 + k4 * 32] == 0)
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if (k4 > 0
								&& sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if (j5 < 31
								&& sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if (k4 < 31
								&& sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;

			}

		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--)
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;

			}

		}
		if (itemDefinition.noted_item_id != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (k == 0)
			sprites.put(sprite2, i);
		Rasterizer2D.initDrawingArea(j2, i2, ai1);
		Rasterizer2D.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.originViewX = k1;
		Rasterizer3D.originViewY = l1;
		Rasterizer3D.scanOffsets = ai;
		Rasterizer3D.restrict_edges = true;
		if (itemDefinition.stackable)
			sprite2.maxWidth = 33;
		else
			sprite2.maxWidth = 32;
		sprite2.maxHeight = j;
		return sprite2;
	}

	public Model getModel(int stack_size) {
		if (stack_variant_id != null && stack_size > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (stack_size >= stack_variant_size[k] && stack_variant_size[k] != 0)
					j = stack_variant_id[k];

			if (j != -1)
				return lookup(j).getModel(1);
		}
		Model model = (Model) models.get(id);
		if (model != null)
			return model;
		model = Model.getModel(inventory_model);
		if (model == null)
			return null;
		if (model_scale_x != 128 || model_scale_y != 128 || model_scale_z != 128)
			model.scale(model_scale_x, model_scale_z, model_scale_y);
		if (modified_model_colors != null) {
			for (int l = 0; l < modified_model_colors.length; l++)
				model.recolor(modified_model_colors[l], original_model_colors[l]);

		}
		model.light(64 + light_intensity, 768 + light_mag, -50, -10, -50, true);
		model.fits_on_single_square = true;
		models.put(model, id);
		return model;
	}

	public Model getUnshadedModel(int stack_size) {
		if (stack_variant_id != null && stack_size > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (stack_size >= stack_variant_size[k] && stack_variant_size[k] != 0)
					j = stack_variant_id[k];

			if (j != -1)
				return lookup(j).getUnshadedModel(1);
		}
		Model model = Model.getModel(inventory_model);
		if (model == null)
			return null;
		if (modified_model_colors != null) {
			for (int l = 0; l < modified_model_colors.length; l++)
				model.recolor(modified_model_colors[l], original_model_colors[l]);

		}
		return model;
	}

	public void readValues(Buffer buffer) {
		do {
			int i = buffer.readUByte();
			if (i == 0)
				return;
			if (i == 1)
				inventory_model = buffer.readShort();
			else if (i == 2)
				name = buffer.readNewString();
			else if (i == 3)
				description = buffer.readBytes();
			else if (i == 4)
				modelZoom = buffer.readShort();
			else if (i == 5)
				rotation_y = buffer.readShort();
			else if (i == 6)
				rotation_x = buffer.readShort();
			else if (i == 7) {
				translate_x = buffer.readShort();
				if (translate_x > 32767)
					translate_x -= 0x10000;
			} else if (i == 8) {
				translate_yz = buffer.readShort();
				if (translate_yz > 32767)
					translate_yz -= 0x10000;
			} else if (i == 10)
				buffer.readShort();
			else if (i == 11)
				stackable = true;
			else if (i == 12)
				value = buffer.readInt();
			else if (i == 16)
				is_members_only = true;
			else if (i == 23) {
				equipped_model_male_1 = buffer.readShort();
				equipped_model_male_translation_y = buffer.readByte();
			} else if (i == 24)
				equipped_model_male_2 = buffer.readShort();
			else if (i == 25) {
				equipped_model_female_1 = buffer.readShort();
				equipped_model_female_translation_y = buffer.readByte();
			} else if (i == 26)
				equipped_model_female_2 = buffer.readShort();
			else if (i >= 30 && i < 35) {
				if (groundActions == null)
					groundActions = new String[5];
				groundActions[i - 30] = buffer.readNewString();
				if (groundActions[i - 30].equalsIgnoreCase("hidden"))
					groundActions[i - 30] = null;
			} else if (i >= 35 && i < 40) {
				if (actions == null)
					actions = new String[5];
				actions[i - 35] = buffer.readNewString();
			} else if (i == 40) {
				int j = buffer.readUByte();
				modified_model_colors = new int[j];
				original_model_colors = new int[j];
				for (int k = 0; k < j; k++) {
					modified_model_colors[k] = buffer.readShort();
					original_model_colors[k] = buffer.readShort();
				}

			} else if (i == 78)
				equipped_model_male_3 = buffer.readShort();
			else if (i == 79)
				equipped_model_female_3 = buffer.readShort();
			else if (i == 90)
				equipped_model_male_dialogue_1 = buffer.readShort();
			else if (i == 91)
				equipped_model_female_dialogue_1 = buffer.readShort();
			else if (i == 92)
				equipped_model_male_dialogue_2 = buffer.readShort();
			else if (i == 93)
				equipped_model_female_dialogue_2 = buffer.readShort();
			else if (i == 95)
				rotation_z = buffer.readShort();
			else if (i == 97)
				unnoted_item_id = buffer.readShort();
			else if (i == 98)
				noted_item_id = buffer.readShort();
			else if (i >= 100 && i < 110) {
				if (stack_variant_id == null) {
					stack_variant_id = new int[10];
					stack_variant_size = new int[10];
				}
				stack_variant_id[i - 100] = buffer.readShort();
				stack_variant_size[i - 100] = buffer.readShort();
			} else if (i == 110)
				model_scale_x = buffer.readShort();
			else if (i == 111)
				model_scale_y = buffer.readShort();
			else if (i == 112)
				model_scale_z = buffer.readShort();
			else if (i == 113)
				light_intensity = buffer.readByte();
			else if (i == 114)
				light_mag = buffer.readByte() * 5;
			else if (i == 115)
				team = buffer.readUByte();
		} while (true);
	}

	public ItemDefinition() {
		id = -1;
	}

	public byte equipped_model_female_translation_y;
	public int value;
	public int[] modified_model_colors;
	public int id;
	static ReferenceCache sprites = new ReferenceCache(100);
	public static ReferenceCache models = new ReferenceCache(50);
	public int[] original_model_colors;
	public boolean is_members_only;
	public int equipped_model_female_3;
	public int noted_item_id;
	public int equipped_model_female_2;
	public int equipped_model_male_1;
	public int equipped_model_male_dialogue_2;
	public int model_scale_x;
	public String groundActions[];
	public int translate_x;
	public String name;
	public static ItemDefinition[] cache;
	public int equipped_model_female_dialogue_2;
	public int inventory_model;
	public int equipped_model_male_dialogue_1;
	public boolean stackable;
	public byte description[];
	public int unnoted_item_id;
	public static int cacheIndex;
	public int modelZoom;
	public static boolean isMembers = true;
	public static Buffer item_data;
	public int light_mag;
	public int equipped_model_male_3;
	public int equipped_model_male_2;
	public String actions[];
	public int rotation_y;
	public int model_scale_z;
	public int model_scale_y;
	public int[] stack_variant_id;
	public int translate_yz;
	public static int[] streamIndices;
	public int light_intensity;
	public int equipped_model_female_dialogue_1;
	public int rotation_x;
	public int equipped_model_female_1;
	public int[] stack_variant_size;
	public int team;
	public static int totalItems;
	public int rotation_z;
	public byte equipped_model_male_translation_y;
	
}
