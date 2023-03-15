public final class NpcDefinition {

	public static NpcDefinition forID(int i) {
		for (int j = 0; j < 20; j++)
			if (cache[j].interfaceType == (long) i)
				return cache[j];

		anInt56 = (anInt56 + 1) % 20;
		NpcDefinition npcDefinition = cache[anInt56] = new NpcDefinition();
		buffer.currentPosition = streamIndices[i];
		npcDefinition.interfaceType = i;
		npcDefinition.readValues(buffer);
		
		//Custom NPCS
		//switch (NpcDefinition.interfaceTyperfaceType) {
		switch(i) {		
		
		/*	Shops	*/
			/*
			case 6227:
				System.out.println("Walk: " + npcDefinition.walkAnim + "\t"
						+ "Stand: " + npcDefinition.standAnim);
			break;
			*/ 
		
			case 2244://lumby guide
				npcDefinition.name = "@gre@EXP Lock";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
				
			case 2589://pkmaster
				npcDefinition.name = "@gre@Wild Boss Teleport";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
		
			case 802://brother jered
				npcDefinition.name = "@gre@Stat Resets";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
				
			case 5447://agility penguin
				npcDefinition.name = "@gre@Agility Reward";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
				
			case 599://makeover
				npcDefinition.name = "@gre@Makeover Mage";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
				
			case 162://agility gnome
				npcDefinition.name = "@gre@Agility Teleports";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
				
			case 2258://abbys tele
				npcDefinition.name = "@gre@Abyss Teleport";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Activate";
				break;
		
			case 2566://skillcape
				npcDefinition.name = "@blu@Skillcapes";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 57://fairy ::skills
				npcDefinition.name = "@blu@Skilling Supplies";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
		
			case 548://Thellisia
				npcDefinition.name = "@blu@Skiller Apparel";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 549://horvik
				npcDefinition.name = "@blu@Armour";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
		
			case 2538://giles
				npcDefinition.name = "@blu@Weapons";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 522://general store
				npcDefinition.name = "@blu@General Goods";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 847://head chef
				npcDefinition.name = "@blu@Pking Gear";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 461://Mage Shop
				npcDefinition.name = "@blu@Magic Gear";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 683://bow and arrow
				npcDefinition.name = "@blu@Ranged Gear";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 1282://thieving shop
				npcDefinition.name = "@blu@Stolen Goods";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop for";
				break;
				
			case 5833://rat burgiss
				npcDefinition.name = "@cya@Slayer Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 4657://Sir prysn [honor]
				npcDefinition.name = "@cya@PkHonour Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 538://Pkpoints
				npcDefinition.name = "@cya@Pk Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 4241://vote shop
				npcDefinition.name = "@cya@Vote Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
			
			case 240://donate shop 1
				npcDefinition.name = "@cya@Donator Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 239://donate shop 2
				npcDefinition.name = "@cya@Donator Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 4906://woodcutting tutor
				npcDefinition.name = "@cya@Woodcutting Points";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Shop with";
				break;
				
			case 1596://Maschaoskdhaoiw slayer master
				npcDefinition.name = "@red@Slayer Master";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Speak to";
				npcDefinition.actions[1] = "Trade";
				break;
			
		/*	Pets	*/
			case 4000:
				npcDefinition.name = "Prince Black Dragon";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[4];
				npcDefinition.modelId[0] = 17414;
				npcDefinition.modelId[1] = 17415;
				npcDefinition.modelId[2] = 17429;
				npcDefinition.modelId[3] = 17422;
				npcDefinition.standAnim = 90;
				npcDefinition.walkAnim = 4635;
				npcDefinition.scaleY = 50;
				npcDefinition.scaleXZ = 50;
			break;

			case 4001:
				npcDefinition.name = "General Graardor Jr";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[2];
				npcDefinition.modelId[0] = 27785;
				npcDefinition.modelId[1] = 27789;
				npcDefinition.standAnim = 7059;
				npcDefinition.walkAnim = 7058;
				npcDefinition.scaleY = 40;
				npcDefinition.scaleXZ = 40;
			break;	

			case 4002:
				npcDefinition.name = "Chaos Elemental";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId[0] = 11216;
				npcDefinition.standAnim = 3144;
				npcDefinition.walkAnim = 3145;
				npcDefinition.scaleY = 40;
				npcDefinition.scaleXZ = 40;
			break;

			case 4003:
				npcDefinition.name = "Kree Arra";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[2];
				npcDefinition.modelId[0] = 28003;
				npcDefinition.modelId[1] = 28004;
				npcDefinition.standAnim = 6972;
				npcDefinition.walkAnim = 6973;
				npcDefinition.scaleY = 25;
				npcDefinition.scaleXZ = 25;
			break;

			case 4004:
				npcDefinition.name = "K'ril Tsutsaroth";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[5];
				npcDefinition.modelId[0] = 27768;
				npcDefinition.modelId[1] = 27773;
				npcDefinition.modelId[2] = 27764;
				npcDefinition.modelId[3] = 27765;
				npcDefinition.modelId[4] = 27770;
				npcDefinition.standAnim = 6943;
				npcDefinition.walkAnim = 6942;
				npcDefinition.scaleY = 30;
				npcDefinition.scaleXZ = 30;
			break;

			case 4005:
				npcDefinition.name = "Commander Zilyana";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[4];
				npcDefinition.modelId[0] = 28057;
				npcDefinition.modelId[1] = 28071;
				npcDefinition.modelId[2] = 28078;
				npcDefinition.modelId[3] = 28056;
				npcDefinition.standAnim = 6963;
				npcDefinition.walkAnim = 6962;
				npcDefinition.scaleY = 70;
				npcDefinition.scaleXZ = 70;
			break;

			case 4006:
				npcDefinition.name = "Dagannoth Supreme";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[2];
				npcDefinition.modelId[0] = 9941;
				npcDefinition.modelId[1] = 9943;
				npcDefinition.standAnim = 2850;
				npcDefinition.walkAnim = 2849;
				npcDefinition.scaleY = 70;
				npcDefinition.scaleXZ = 70;
			break;

			case 4007:
				npcDefinition.name = "Dagannoth Prime"; //9940, 9943, 9942
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[3];
				npcDefinition.modelId[0] = 9940;
				npcDefinition.modelId[1] = 9943;
				npcDefinition.modelId[2] = 9942;
				npcDefinition.recolourTarget = new int[]{11930, 27144, 16536, 16540};
				npcDefinition.recolourOriginal = new int[]{5931, 1688, 21530, 21534};
				npcDefinition.standAnim = 2850;
				npcDefinition.walkAnim = 2849;
				npcDefinition.scaleY = 70;
				npcDefinition.scaleXZ = 70;
			break;

			case 4008:
				npcDefinition.name = "Dagannoth Rex";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId[0] = 9941;
				npcDefinition.recolourTarget = new int[]{16536, 16540, 27144, 2477};
				npcDefinition.recolourOriginal = new int[]{7322, 7326, 10403, 2595};
				npcDefinition.standAnim = 2850;
				npcDefinition.walkAnim = 2849;
				npcDefinition.scaleY = 70;
				npcDefinition.scaleXZ = 70;
			break;
			case 3071:
		    	npcDefinition.name = "Skeletal Wyvern";
				npcDefinition.combatLevel = 140;
				npcDefinition.actions = new String[5];
				npcDefinition.actions[1] = "Attack";
				break;

			case 4009:
				npcDefinition.name = "Ahrim the Blighted";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId[0] = 6668;
				npcDefinition.standAnim = 813;
				npcDefinition.walkAnim = 1205;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;

			case 4010:
				npcDefinition.name = "Dharok the Wretched";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[6];
				npcDefinition.modelId[0] = 6652;
				npcDefinition.modelId[1] = 6671;
				npcDefinition.modelId[2] = 6640;
				npcDefinition.modelId[3] = 6661;
				npcDefinition.modelId[4] = 6703;
				npcDefinition.modelId[5] = 6679;
				npcDefinition.standAnim = 2065;
				npcDefinition.walkAnim = 2064;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;

			case 4011:
				npcDefinition.name = "Guthan the Infested";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[6];
				npcDefinition.modelId[0] = 6654;
				npcDefinition.modelId[1] = 6674;
				npcDefinition.modelId[2] = 6642;
				npcDefinition.modelId[3] = 6666;
				npcDefinition.modelId[4] = 6679;
				npcDefinition.modelId[5] = 6710;
				npcDefinition.standAnim = 813;
				npcDefinition.walkAnim = 1205;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;

			case 4012:
				npcDefinition.name = "Karil the Tainted";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId[0] = 6675;
				npcDefinition.standAnim = 2074;
				npcDefinition.walkAnim = 2076;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;

			case 4013:
				npcDefinition.name = "Torag the Corrupted";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[6];
				npcDefinition.modelId[0] = 6657;
				npcDefinition.modelId[1] = 6677;
				npcDefinition.modelId[2] = 6645;
				npcDefinition.modelId[3] = 6663;
				npcDefinition.modelId[4] = 6708;
				npcDefinition.modelId[5] = 6679;
				npcDefinition.standAnim = 808;
				npcDefinition.walkAnim = 819;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;

			case 4014:
				npcDefinition.name = "Verac the Defiled";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[2];
				npcDefinition.modelId[0] = 6678;
				npcDefinition.modelId[1] = 6705;
				npcDefinition.standAnim = 2061;
				npcDefinition.walkAnim = 2060;
				npcDefinition.scaleY = 100;
				npcDefinition.scaleXZ = 100;
			break;
			
			case 4015: 
		    	npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
		    	npcDefinition.modelId[0] = 28299;
		    	npcDefinition.name = "Vet'ion JR";
		    	NpcDefinition vetJR = forID(90);
		        npcDefinition.standAnim = vetJR.standAnim;
		        npcDefinition.walkAnim = vetJR.walkAnim;
		        npcDefinition.scaleY = 61;
		        npcDefinition.scaleXZ = 61;
		    break;
		        
		    case 4016:
		    	npcDefinition.name = "Callisto JR";
			    npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[1];
		        npcDefinition.modelId[0] = 28298;
		        NpcDefinition callistoJR = forID(105);
		        npcDefinition.standAnim = callistoJR.standAnim;
		        npcDefinition.walkAnim = callistoJR.walkAnim;
		        npcDefinition.scaleY = 40;
		        npcDefinition.scaleXZ = 30;
		    break;

		    case 3000: 
		    	npcDefinition.modelId = new int[2];
		        npcDefinition.name = "Venenatis JR";
		        npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
		        npcDefinition.scaleY = 40;
		        npcDefinition.scaleXZ = 40;
		        NpcDefinition VenenatisJR = forID(4018);
		        npcDefinition.modelId = VenenatisJR.modelId;
		        npcDefinition.standAnim = VenenatisJR.standAnim;
		        npcDefinition.walkAnim = VenenatisJR.walkAnim;
		      break;
		        
		    case 3001: 
		    	npcDefinition.modelId = new int[1];
		    	npcDefinition.modelId[0] = 28293;
		    	npcDefinition.name = "Scorpia JR";
		    	npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
		    	NpcDefinition scorJR = forID(107);
		        npcDefinition.scaleY = 40;
		        npcDefinition.scaleXZ = 40;
		        npcDefinition.standAnim = scorJR.standAnim;
		        npcDefinition.walkAnim = scorJR.walkAnim;
		    break;

			case 3002:
				npcDefinition.name = "Kalphite Princess";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.modelId = new int[2];
				npcDefinition.modelId[0] = 24597;
				npcDefinition.modelId[1] = 24598;
				NpcDefinition kqGreen = forID(1158);
				npcDefinition.standAnim = kqGreen.standAnim;
				npcDefinition.walkAnim = kqGreen.walkAnim;
				npcDefinition.scaleY = 30;
				npcDefinition.scaleXZ = 30;
			break;

			case 3003:
				npcDefinition.name = "Kalphite Princess";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				
				npcDefinition.modelId = new int[] { 24602, 24605, 24606 };
				npcDefinition.standAnim = 6236;
				npcDefinition.walkAnim = 6237;
				npcDefinition.scaleY = 35;
				npcDefinition.scaleXZ = 35;
			break;
		
			case 3004:
				npcDefinition.name = "Baby mole";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				npcDefinition.drawMinimapDot = false;
				npcDefinition.modelId = new int[] { 12076, 12074, 12075, 12077, };
				npcDefinition.standAnim = 3309;
				npcDefinition.walkAnim = 3313;
				npcDefinition.scaleY = 40;
				npcDefinition.scaleXZ = 40;
			break;
			
			case 3005:
				npcDefinition.name = "Barrelchest Jr";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				NpcDefinition BCJ = forID(5666);
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId = BCJ.modelId;
				npcDefinition.standAnim = BCJ.standAnim;
				npcDefinition.walkAnim = BCJ.walkAnim;
				npcDefinition.scaleY = 50;
				npcDefinition.scaleXZ = 50;
				break;
				
			case 3006:
				npcDefinition.name = "Boris Jr";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				NpcDefinition Boris = forID(6026);
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId = Boris.modelId;
				System.out.println("Model ID:" + Boris.modelId);
				npcDefinition.standAnim = Boris.standAnim;
				npcDefinition.walkAnim = Boris.walkAnim;
				npcDefinition.scaleY = 65;
				npcDefinition.scaleXZ = 65;
				break;
				
			case 3007:
				npcDefinition.name = "Skeletal Drakling";
				npcDefinition.actions = new String[5];
				npcDefinition.description = npcDefinition.name.getBytes();
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				NpcDefinition Drakling = forID(3071);
				npcDefinition.modelId = new int[1];
				npcDefinition.modelId = Drakling.modelId;
				npcDefinition.standAnim = Drakling.standAnim;
				npcDefinition.walkAnim = Drakling.walkAnim;
				npcDefinition.scaleY = 65;
				npcDefinition.scaleXZ = 65;
				break;
				
			case 3008:
				npcDefinition.name = "TzRek Jad";
				npcDefinition.actions = new String[5];
				npcDefinition.actions[0] = "Talk-to";
				npcDefinition.actions[2] = "Pick-up";
				NpcDefinition jed = forID(2745);
				npcDefinition.modelId = new int[jed.modelId.length];
				npcDefinition.modelId = jed.modelId;
				npcDefinition.standAnim = jed.standAnim;
				npcDefinition.walkAnim = jed.walkAnim;
				npcDefinition.scaleY = 25;
				npcDefinition.scaleXZ = 25;
				break;
			
				
			case 4024:
			npcDefinition.name = "Smoke Devil jr";
			npcDefinition.combatLevel = 0;
			npcDefinition.walkAnim = 1828;
			npcDefinition.standAnim = 1829;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[2] = "Pick-up";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 28442;
			npcDefinition.scaleXZ = 60;
			npcDefinition.scaleY = 60;
			npcDefinition.size = 1;
			break;
			
		case 4032:
			npcDefinition.name = "Smoke Devil";
			npcDefinition.combatLevel = 0;
			npcDefinition.walkAnim = 1828;
			npcDefinition.standAnim = 1829;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[2] = "Pick-up";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 28442;
			npcDefinition.scaleXZ = 120;
			npcDefinition.scaleY = 120;
			npcDefinition.size = 1;
			break;
			

//OSRS Bosses

 case 4017:
	           npcDefinition.name = "Callisto";
			npcDefinition.combatLevel = 470;
			npcDefinition.walkAnim = 4923;
			npcDefinition.standAnim = 4919;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[2];
			npcDefinition.modelId[0] = 28298;
			npcDefinition.modelId[1] = 18886;
			npcDefinition.scaleXZ = 128;//128
			npcDefinition.scaleY = 128;//128
			npcDefinition.size = 5;
			break;

	    case 4018: 
	    	npcDefinition.name = "Venenatis";
			npcDefinition.combatLevel = 464;
			npcDefinition.walkAnim = 5317;
			npcDefinition.standAnim = 5318;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[2];
			npcDefinition.modelId[0] = 28294;
			npcDefinition.modelId[1] = 28295;
			npcDefinition.scaleXZ = 190;
			npcDefinition.scaleY = 190;
			npcDefinition.size = 4;
			break;
			
	    case 4019: 
	    	npcDefinition.name = "Scorpia";
			npcDefinition.combatLevel = 225;
			npcDefinition.walkAnim = 6262;
			npcDefinition.standAnim = 6252;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 28293;
			npcDefinition.scaleXZ = 128;
			npcDefinition.scaleY = 128;
			npcDefinition.size = 5;
		break;

	    case 2783:
	    	System.out.println("Model Info - [standanim]: "  + npcDefinition.standAnim );
	    	System.out.println("Model Info - [walkanim]:  " + npcDefinition.walkAnim);
	    	break;
	    case 4020: 
	    	npcDefinition.name = "Vet'ion";
			npcDefinition.combatLevel = 454;
			npcDefinition.walkAnim = 5497;
			npcDefinition.standAnim = 5505;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 28299;
			npcDefinition.scaleXZ = 150;
			npcDefinition.scaleY = 150;
			npcDefinition.size = 3;
			break;	
	    case 4099: 
	    	npcDefinition.name = "sotetseg";
			npcDefinition.combatLevel = 600;
			npcDefinition.walkAnim = 5497;
			npcDefinition.standAnim = 5947;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[1];
	        npcDefinition.modelId[0] = 70468;
			npcDefinition.scaleXZ = 150;
			npcDefinition.scaleY = 150;
			break;	
	    case 4021: 
	    	npcDefinition.name = "Cave kraken";
	    	npcDefinition.actions = new String[] {null, "Attack", null, null, null};
	    	NpcDefinition cave = forID(3847);
	    	npcDefinition.modelId = new int[1];
	        npcDefinition.modelId[0] = 28231;
	        npcDefinition.combatLevel = 127;
	        npcDefinition.standAnim = 3989;
	        npcDefinition.walkAnim = 3989;
	        npcDefinition.scaleY = 42;
	        npcDefinition.scaleXZ = 42;
	        break;
	       
			
	    case 2672:
	    	npcDefinition.name = "Kraken";
	    	npcDefinition.actions = new String[] {null, "Attack", null, null, null};
	    	NpcDefinition kra = forID(3847);
	        npcDefinition.modelId = new int[1];
	        npcDefinition.modelId[0] = 28231;
	        npcDefinition.combatLevel = 291;
	        npcDefinition.standAnim = 3989;
	        npcDefinition.walkAnim = 3988;
	        npcDefinition.scaleY = 75;
	        npcDefinition.scaleXZ = 75;
	        npcDefinition.size = 2;
	        break;
	//osrs 
	
			
	    case 4029: 
	    	npcDefinition.name = "Cave kraken jr";
	    	npcDefinition.actions = new String[5];
			npcDefinition.actions[2] = "Pick-up";
	    	//NpcDefinition cave = lookup(3847);
	    	npcDefinition.modelId = new int[1];
	        npcDefinition.modelId[0] = 28231;
	        npcDefinition.standAnim = 3989;
	        npcDefinition.walkAnim = 3988;
	        npcDefinition.scaleY = 18;
	        npcDefinition.scaleXZ = 18;
	        break;
			
	    case 4030: 
	    	npcDefinition.name = "Kraken";
	    	npcDefinition.actions = new String[5];
			npcDefinition.actions[2] = "Pick-up";
	    	//NpcDefinition kra = lookup(3847);
	        npcDefinition.modelId = new int[1];
	        npcDefinition.modelId[0] = 28231;
	        npcDefinition.standAnim = 3989;
	        npcDefinition.walkAnim = 3989;
	        npcDefinition.scaleY = 35;
	        npcDefinition.scaleXZ = 35;
	        break;
			
		case 4031: 
	    	npcDefinition.name = "Scorpion Spawns";
			npcDefinition.combatLevel = 225;
			npcDefinition.walkAnim = 6262;
			npcDefinition.standAnim = 6252;
			npcDefinition.actions = new String[5];
			npcDefinition.actions[2] = "Pick-up";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 28293;
			npcDefinition.scaleXZ = 30;
			npcDefinition.scaleY = 30;
			npcDefinition.size = 5;
			break;
				
				
				
				case 4056:
			npcDefinition.name = "Zulrah";
			npcDefinition.actions = new String[5];
			npcDefinition.actions[0] = "Pick-up";
			npcDefinition.modelId = new int[2];
			npcDefinition.modelId[0] = 14407;
			npcDefinition.modelId[1] = 14407;
			npcDefinition.standAnim = 5070;
			npcDefinition.walkAnim = 5070;
			npcDefinition.combatLevel = 115;
			npcDefinition.scaleY = 100;
			npcDefinition.scaleXZ = 100;
			break;
			
		case 151:
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 70399;
			npcDefinition.standAnim = 6943;
			npcDefinition.walkAnim = 6942;
			npcDefinition.scaleY = 100;
			npcDefinition.scaleXZ = 100;
			npcDefinition.size = 6;
			npcDefinition.combatLevel = 785;
			npcDefinition.name = "Skotizo";
			npcDefinition.description = "A vision of supernatural horror.".getBytes();
			break;
		case 48:
			
					npcDefinition.actions = new String[5];
					npcDefinition.actions[1] = "Attack";
					npcDefinition.modelId = new int[1];
					npcDefinition.modelId[0] = 70470;
					npcDefinition.standAnim = 6561;
					npcDefinition.walkAnim = 6583;
					npcDefinition.combatLevel = 90;
					npcDefinition.scaleY = 75;
					npcDefinition.scaleXZ = 75;
					npcDefinition.name = "Revenant hellhound";
					npcDefinition.description = "A ghost of a hellhound slain long ago.".getBytes();
					break;
		case 2558:
			npcDefinition.actions = new String[5];
			npcDefinition.actions[1] = "Attack";
			npcDefinition.modelId = new int[1];
			npcDefinition.modelId[0] = 70471;
			npcDefinition.standAnim = 2730;
			npcDefinition.walkAnim = 2729;
			npcDefinition.combatLevel = 120;
			npcDefinition.scaleY = 75;
			npcDefinition.scaleXZ = 75;
			npcDefinition.name = "Revenant dark beast";
			npcDefinition.description = "A ghost of a hellhound slain long ago.".getBytes();
			break;
				
	    case 97: 
	    	npcDefinition.name = "Agnarok";
			npcDefinition.combatLevel = 666;
			npcDefinition.scaleXZ = 350;
			npcDefinition.scaleY = 350;
			npcDefinition.size = 6;
		break;
			
	}
		return npcDefinition;
}

	public Model method160() {
		if (childrenIDs != null) {
			NpcDefinition npcDefinition = morph();
			if (npcDefinition == null)
				return null;
			else
				return npcDefinition.method160();
		}
		if (additionalModels == null)
			return null;
		boolean flag1 = false;
		for (int i = 0; i < additionalModels.length; i++)
			if (!Model.isCached(additionalModels[i]))
				flag1 = true;

		if (flag1)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[additionalModels.length];
		for (int j = 0; j < additionalModels.length; j++)
			aclass30_sub2_sub4_sub6s[j] = Model.getModel(additionalModels[j]);

		Model model;
		if (aclass30_sub2_sub4_sub6s.length == 1)
			model = aclass30_sub2_sub4_sub6s[0];
		else
			model = new Model(aclass30_sub2_sub4_sub6s.length,
					aclass30_sub2_sub4_sub6s);
		if (recolourOriginal != null) {
			for (int k = 0; k < recolourOriginal.length; k++)
				model.recolor(recolourOriginal[k], recolourTarget[k]);

		}
		return model;
	}

	public NpcDefinition morph() {
		int j = -1;
		if (varBitID != -1) {
			VarBit varBit = VarBit.cache[varBitID];
			int k = varBit.anInt648;
			int l = varBit.anInt649;
			int i1 = varBit.anInt650;
			int j1 = Client.anIntArray1232[i1 - l];
			j = clientInstance.variousSettings[k] >> l & j1;
		} else if (settingId != -1)
			j = clientInstance.variousSettings[settingId];
		if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1)
			return null;
		else
			return forID(childrenIDs[j]);
	}

	public static void unpackConfig(FileArchive fileArchive) {
		buffer = new Buffer(fileArchive.readFile("npc.dat"));
		Buffer buffer2 = new Buffer(fileArchive.readFile("npc.idx"));
		int totalNPCs = buffer2.readUShort();
		streamIndices = new int[totalNPCs];
		int i = 2;
		for (int j = 0; j < totalNPCs; j++) {
			streamIndices[j] = i;
			i += buffer2.readUShort();
		}

		cache = new NpcDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new NpcDefinition();
		for (int index = 0; index < totalNPCs; index++) {
			NpcDefinition ed = forID(index);
			if (ed == null)
				continue;
			if (ed.name == null)
				continue;
		}
	}

	public static void nullLoader() {
		modelCache = null;
		streamIndices = null;
		cache = null;
		buffer = null;
	}

	public Model method164(int j, int frame, int ai[]) {
		if (childrenIDs != null) {
			NpcDefinition entityDef = morph();
			if (entityDef == null)
				return null;
			else
				return entityDef.method164(j, frame, ai);
		}
		Model model = (Model) modelCache.get(interfaceType);
		if (model == null) {
			boolean flag = false;
			for (int i1 = 0; i1 < modelId.length; i1++)
				if (!Model.isCached(modelId[i1]))
					flag = true;

			if (flag)
				return null;
			Model models[] = new Model[modelId.length];
			for (int j1 = 0; j1 < modelId.length; j1++)
				models[j1] = Model.getModel(modelId[j1]);

			if (models.length == 1)
				model = models[0];
			else
				model = new Model(models.length, models);
			if (recolourOriginal != null) {
				for (int k1 = 0; k1 < recolourOriginal.length; k1++)
					model.recolor(recolourOriginal[k1], recolourTarget[k1]);

			}
			model.skin();
			model.scale(132, 132, 132);
			model.light(84 + lightModifier, 1000 + shadowModifier, -90, -580, -90, true);
			modelCache.put(model, interfaceType);
		}
		Model empty = Model.EMPTY_MODEL;
		empty.method464(model, Frame.noAnimationInProgress(frame) & Frame.noAnimationInProgress(j));
		if (frame != -1 && j != -1)
			empty.applyAnimationFrames(ai, j, frame);
		else if (frame != -1)
			empty.applyTransform(frame);
		if (scaleXZ != 128 || scaleY != 128)
			empty.scale(scaleXZ, scaleXZ, scaleY);
		empty.calculateDistances();
		empty.faceGroups = null;
		empty.vertexGroups = null;
		if (size == 1)
			empty.fits_on_single_square = true;
		return empty;
	}

	public void readValues(Buffer buffer) {
		do {
			int i = buffer.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1) {
				int j = buffer.readUnsignedByte();
				modelId = new int[j];
				for (int j1 = 0; j1 < j; j1++)
					modelId[j1] = buffer.readUShort();

			} else if (i == 2)
				name = buffer.readNewString();
			else if (i == 3)
				description = buffer.readBytes();
			else if (i == 12)
				size = buffer.readSignedByte();
			else if (i == 13)
				standAnim = buffer.readUShort();
			else if (i == 14)
				walkAnim = buffer.readUShort();
			else if (i == 17) {
				walkAnim = buffer.readUShort();
				turn180AnimIndex = buffer.readUShort();
				turn90CWAnimIndex = buffer.readUShort();
				turn90CCWAnimIndex = buffer.readUShort();
			} else if (i >= 30 && i < 40) {
				if (actions == null)
					actions = new String[5];
				actions[i - 30] = buffer.readNewString();
				if (actions[i - 30].equalsIgnoreCase("hidden"))
					actions[i - 30] = null;
			} else if (i == 40) {
				int k = buffer.readUnsignedByte();
				recolourOriginal = new int[k];
				recolourTarget = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					recolourOriginal[k1] = buffer.readUShort();
					recolourTarget[k1] = buffer.readUShort();
				}

			} else if (i == 60) {
				int l = buffer.readUnsignedByte();
				additionalModels = new int[l];
				for (int l1 = 0; l1 < l; l1++)
					additionalModels[l1] = buffer.readUShort();

			} else if (i == 90)
				buffer.readUShort();
			else if (i == 91)
				buffer.readUShort();
			else if (i == 92)
				buffer.readUShort();
			else if (i == 93)
				drawMinimapDot = false;
			else if (i == 95)
				combatLevel = buffer.readUShort();
			else if (i == 97)
				scaleXZ = buffer.readUShort();
			else if (i == 98)
				scaleY = buffer.readUShort();
			else if (i == 99)
				priorityRender = true;
			else if (i == 100)
				lightModifier = buffer.readSignedByte();
			else if (i == 101)
				shadowModifier = buffer.readSignedByte() * 5;
			else if (i == 102)
				headIcon = buffer.readUShort();
			else if (i == 103)
				degreesToTurn = buffer.readUShort();
			else if (i == 106) {
				varBitID = buffer.readUShort();
				if (varBitID == 65535)
					varBitID = -1;
				settingId = buffer.readUShort();
				if (settingId == 65535)
					settingId = -1;
				int i1 = buffer.readUnsignedByte();
				childrenIDs = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					childrenIDs[i2] = buffer.readUShort();
					if (childrenIDs[i2] == 65535)
						childrenIDs[i2] = -1;
				}

			} else if (i == 107)
				clickable = false;
		} while (true);
	}

	public NpcDefinition() {
		turn90CCWAnimIndex = -1;
		varBitID = -1;
		turn180AnimIndex = -1;
		settingId = -1;
		combatLevel = -1;
		anInt64 = 1834;
		walkAnim = -1;
		size = 1;
		headIcon = -1;
		standAnim = -1;
		interfaceType = -1L;
		degreesToTurn = 32;
		turn90CWAnimIndex = -1;
		clickable = true;
		scaleY = 128;
		drawMinimapDot = true;
		scaleXZ = 128;
		priorityRender = false;
	}

	public int turn90CCWAnimIndex;
	public static int anInt56;
	public int varBitID;
	public int turn180AnimIndex;
	public int settingId;
	public static Buffer buffer;
	public int combatLevel;
	public final int anInt64;
	public String name;
	public String actions[];
	public int walkAnim;
	public byte size;
	public int[] recolourTarget;
	public static int[] streamIndices;
	public int[] additionalModels;
	public int headIcon;
	public int[] recolourOriginal;
	public int standAnim;
	public long interfaceType;
	public int degreesToTurn;
	public static NpcDefinition[] cache;
	public static Client clientInstance;
	public int turn90CWAnimIndex;
	public boolean clickable;
	public int lightModifier;
	public int scaleY;
	public boolean drawMinimapDot;
	public int childrenIDs[];
	public byte description[];
	public int scaleXZ;
	public int shadowModifier;
	public boolean priorityRender;
	public int[] modelId;
	public static ReferenceCache modelCache = new ReferenceCache(30);

}
