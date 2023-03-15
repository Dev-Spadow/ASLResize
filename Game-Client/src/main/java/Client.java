// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.applet.AppletContext;
import java.awt.*;
import java.io.*;
import java.net.*;
//import sign.Signlink;
import javax.swing.*;

//import Rasterizer2D;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.FileInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.List;

public class Client extends GameApplet {

	public static double brightnessState = 0.8D;
	public static boolean shiftDown;
	public int positions[] = new int[2000];
	public int landScapes[] = new int[2000];
	public int objects[] = new int[2000];
	private String consoleInput;
    public static boolean consoleOpen;
    private final String[] consoleMessages;
	private boolean hunterTitleButtons = false;
	private boolean clientDevelopment = false;
	public static boolean is459 = false;
	public static boolean debugServerIP = true;
	public static boolean showClicking = false;
	public static boolean newRendering = false;
	
					public int followPlayer;
					public int followNPC;
					public int followDistance;
	
	public static int totalSettings = 16;
	public static ScreenMode frameMode = ScreenMode.FIXED;
	public static int frameWidth = 765;
	public static int frameHeight = 503;
	public static int screenAreaWidth = 512;
	public static int screenAreaHeight = 334;
	public static int cameraZoom = 600;
	public static boolean showChatComponents = true;
	public static boolean showTabComponents = true;
	public static boolean changeChatArea = false;
	public static boolean stackSideStones = false;
	public static boolean transparentTabArea = false;
	private boolean rememberUsernameHover;
	private boolean rememberUsername;
	private boolean rememberPasswordHover;
	private boolean rememberPassword;
	private boolean forgottenPasswordHover;
	private Mob currentInteract;
	private SecondsTimer combatBoxTimer = new SecondsTimer();

	final static int[] IDs = {1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274,
			1283, 1573, 1290, 1299, 1308, 1315, 1324, 1333, 1340, 1349, 1358,
			1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421, 1430,
			1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478,
			1485, 1494, 1503, 1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635,
			12426, 12436, 12446, 12456, 6004, 18471,
			/* Ancients */
			12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920,
			12882, 13062, 12952, 13000, 13070, 12912, 12872, 13080, 12976, 13024,
			13088, 12930, 12892, 13096};
	final static int[] runeChildren = {1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228,
			1234, 1235, 1236, 1243, 1244, 1245, 1252, 1253, 1254, 1261, 1262,
			1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293, 1294, 1295,
			1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329,
			1336, 1337, 1343, 1344, 1345, 1352, 1353, 1354, 1361, 1362, 1363,
			1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392, 1393, 1400, 1401,
			1407, 1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440,
			1441, 1442, 1449, 1450, 1456, 1457, 1463, 1464, 1465, 1472, 1473,
			1474, 1481, 1482, 1488, 1489, 1490, 1497, 1498, 1499, 1506, 1507,
			1508, 1515, 1516, 1517, 1524, 1525, 1526, 1533, 1534, 1535, 1547,
			1548, 1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578,
			1586, 1587, 1588, 1596, 1597, 1598, 1605, 1606, 1607, 1616, 1617,
			1618, 1627, 1628, 1629, 1638, 1639, 1640, 6007, 6008, 6011, 8673,
			8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449,
			12450, 12451, 12459, 12460, 15881, 15882, 15885, 18474, 18475, 18478};
	private int spellId;
	private int dropdownInversionFlag;

	public void tabToReplyPm() {
        String name = null;
        for (int k = 0; k < 100; k++) {
            if (chatMessages[k] == null) {
                continue;
            }
            int l = chatTypes[k];
            if (l == 3 || l == 7) {
                name = chatNames[k];
                break;
            }
        }

        if (name == null) {
            pushMessage("You haven't received any messages to which you can reply.", 0, "");
            return;
        }

        if (name.startsWith("@cr")) {
            name = name.substring(5);
        }

        long nameAsLong = TextClass.longForName(name.trim());
        int k3 = -1;
        for (int i4 = 0; i4 < friendsCount; i4++) {
            if (friendsListAsLongs[i4] != nameAsLong) continue;
            k3 = i4;
            break;
        }

        if (k3 != -1) {
            if (friendsNodeIDs[k3] > 0) {
                updateChatbox = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 3;
                aLong953 = friendsListAsLongs[k3];
                aString1121 = "Enter message to send to " + friendsList[k3];
            } else {
                pushMessage("That player is currently offline.", 0, "");
            }
        }
    }
	
	public void readSettings() {
		File file = new File(signlink.findcachedir() + "settings.dat");
		if (!file.exists()) {
			return;
		}
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(signlink.findcachedir() + "settings.dat"));
			int totalSettings = in.readShort();
			readSettingsValues(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getExamine(int id) {
	
		String examine = "";
		try {
			String name = ItemDefinition.lookup(id).name;
			URL url = new URL("http://runescape.wikia.com/wiki/" + name.replaceAll(" ", "_"));
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			int next = 0;
			while ((line = in.readLine()) != null) {
				if (line.contains("<th nowrap=\"nowrap\"><a href=\"/wiki/Examine\" title=\"Examine\">Examine</a>")) {
					next++;
				}
				if (line.contains("</th><td> ") && next == 1) {
					examine = line.replace("</th><td> ", "");
					return examine;
				}
			}
			in.close();
			return examine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return examine;
	}
	
	public void readSettingsValues(DataInputStream in) {
		try {
			do {
				int opCode = in.readByte();
				if (opCode == 1) {
					signlink.musicOn = true;
				}
				if (opCode == 2) {
					signlink.musicOn = false;
				}
				if (opCode == 15) {
					//midiVolume = in.readShort();
				}
			} while (true);
		} catch (IOException e) { 
		}
	}
	
	public String indexLocation(int cacheIndex, int index) {
		return signlink.findcachedir() + "index" + cacheIndex + "/" + (index != -1 ? index + ".gz" : "");
	}

	public void repackCacheIndex(int cacheIndex) {
		System.out.println("Started repacking index " + cacheIndex + ".");
		int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
		File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
		try {
			for (int index = 0; index < indexLength; index++) {
				int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file[index].toString()));
				byte[] data = fileToByteArray(cacheIndex, fileIndex);
				if(data != null && data.length > 0) {
					decompressors[cacheIndex].method234(data.length, data, fileIndex);
					System.out.println("Repacked " + fileIndex + ".");
				} else {
					System.out.println("Unable to locate index " + fileIndex + ".");
				}
			}
		} catch(Exception e) {
			System.out.println("Error packing kits index " + cacheIndex + ".");
		}
		System.out.println("Finished repacking " + cacheIndex + ".");
	}

	public byte[] fileToByteArray(int cacheIndex, int index) {
		try {
			if (indexLocation(cacheIndex, index).length() <= 0 || indexLocation(cacheIndex, index) == null) {
				return null;
			}
			File file = new File(indexLocation(cacheIndex, index));
			byte[] fileData = new byte[(int)file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileData);
			fis.close();
			return fileData;
		} catch(Exception e) {
			return null;
		}
	}
	
	public void writeSettings() {
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(signlink.findcachedir() + "settings.dat"));
			out.writeShort(totalSettings);
				if (signlink.musicOn) {
					out.writeByte(1);
				}
				if (!signlink.musicOn) {
					out.writeByte(2);
				}
				/*if (midiVolume != -1) {
					out.writeByte(15);
					out.writeShort(midiVolume);
				}*/
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void setNewMaps() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(signlink.findcachedir()+"Maps/mapConfig.txt"));
			String s;
			int D = 0;
			while ((s = in.readLine()) != null)  {
				positions[D] = Integer.parseInt(s.substring(s.indexOf("=")+1,s.indexOf("(")));
				landScapes[D] = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
				objects[D] = Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					D++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSidebarInterface(int sidebarID, int interfaceID) {
		tabInterfaceIDs[sidebarID] = interfaceID;
		tabId = sidebarID;
		tabAreaAltered = true;
	}
	
	int messager = 1;

	public void drawConsoleMessage(int Message)
	{
		if (Message == 1)
		{
			printConsoleMessage("This is the developer console. To close, press the ` key on your keyboard.", 1);	
			return;
		} 
		
	}
	
	private void drawConsole()
    {
		GameFont textDrawingArea = regularText;
        if(consoleOpen)
        {
			drawConsoleMessage(messager);
			messager++;
            Rasterizer2D.transparentBox(334, 0, 0, 5320850, 512, 0, 97);
            Rasterizer2D.drawPixels(1, 315, 0, 16777215, 512);
            //newBoldFont.drawBasicString("-->", 11, 328, 16777215, 0);
           textDrawingArea.render(16777215,"-->", 328, 11);
            if(tick % 20 < 10)
            {
            //newBoldFont.drawBasicString(consoleInput+"|", 38, 328, 16777215, 0);
            textDrawingArea.render(16777215,consoleInput+"|", 328, 38);
                return;
            } else
            {
            //newBoldFont.drawBasicString(consoleInput, 38, 328, 16777215, 0);
            textDrawingArea.render(16777215,consoleInput, 328, 38);
                return;
            }
        }
    }
    
    private void drawConsoleArea() {
		GameFont textDrawingArea = regularText;
        if(consoleOpen) {
            for(int i = 0, j = 308; i < 17;i++, j -= 18) {
                if(consoleMessages[i] != null)
                    //newRegularFont.drawBasicString(consoleMessages[i], 9, j, 16777215, 0);
                   textDrawingArea.render(16777215,consoleMessages[i], j, 9);
            }
        }
    }

    public void printConsoleMessage(String s, int i) {
        if(backDialogueId == -1)
            updateChatbox = true;
        for(int j = 16; j > 0; j--) {
            consoleMessages[j] = consoleMessages[j - 1];
        }
        if(i == 0)
        consoleMessages[0] = "--> "+s;
        else 
        consoleMessages[0] = s;
    }
  private void sendCommandPacket(String cmd) {    
    if(cmd.equalsIgnoreCase("cls")){
        for(int j = 0; j < 17; j++) 
            consoleMessages[j] = null;
    }
    /**Add Commands Here**/
    buffer.createFrame(103);
    buffer.writeWordBigEndian(cmd.length()+1);
    buffer.writeString(cmd);
  }


	public static String capitalize(String s) {

		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format( "%s%s",
                         Character.toUpperCase(s.charAt(0)),
                         s.substring(1) );
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format( "%s%s%s",
                             s.subSequence(0, i+1),
                             Character.toUpperCase(s.charAt(i + 1)),
                             s.substring(i+2) );
				}
			}
		}
		return s;
	}

	public int MapX, MapY;
		private static String intToKOrMilLongName(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3)
			s = s.substring(0, k) + "," + s.substring(k);
		if (s.length() > 8)
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@("
					+ s + ")";
		else if (s.length() > 4)
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		return " " + s;
	}

	public final String methodR(int j) {
		if (j >= 0 && j < 10000)
			return String.valueOf(j);
		if (j >= 10000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j < 999999999)
			return j / 1000000 + "M";
		if (j >= 999999999)
			return "*";
		else
			return "?";
	}

	public void playSong(int id) {
		if (currentSong != id) {
			nextSong = id;
			songChanging = true;
			resourceProvider.provide(2, nextSong);
			currentSong = id;
		}
	}

	public void stopMidi() {
		if (signlink.music != null) {
			signlink.music.stop();
		}
			signlink.fadeMidi = 0;
			signlink.midi = "stop";
	}
	
	private boolean menuHasAddFriend(int j) {
		if(j < 0)
			return false;
		int k = menuActionTypes[j];
		if(k >= 2000)
			k -= 2000;
		return k == 337;
	}

	public void drawChannelButtons() {
		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 165;
		spriteCache.draw(49, 0, 143 + yOffset);
		String text[] = {"On", "Friends", "Off", "Hide"};
		int textColor[] = {65280, 0xffff00, 0xff0000, 65535};
		switch (cButtonCPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				spriteCache.draw(16, channelButtonsX[cButtonCPos], 143 + yOffset);
				break;
		}
		if (cButtonHPos == cButtonCPos) {
			switch (cButtonHPos) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
					spriteCache.draw(17, channelButtonsX[cButtonHPos], 143 + yOffset);
					break;
			}
		} else {
			switch (cButtonHPos) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					spriteCache.draw(15, channelButtonsX[cButtonHPos], 143 + yOffset);
					break;
				case 7:
					spriteCache.draw(18, channelButtonsX[cButtonHPos], 143 + yOffset);
					break;
			}
		}
		int[] modes = {publicChatMode, privateChatMode, clanChatMode, tradeMode, yellMode};
		for (int i = 0; i < modeNamesX.length; i++) {
			smallText.drawTextWithPotentialShadow(true, modeNamesX[i], 0xffffff, modeNames[i], modeNamesY[i] + yOffset);
		}
		for (int i = 0; i < modeX.length; i++) {
			smallText.drawCenteredString(textColor[modes[i]], modeX[i], text[modes[i]], 164 + yOffset, true);
		}
	}


	private boolean chatStateCheck() {
		return messagePromptRaised || inputDialogState != 0 || clickToContinueString != null || backDialogueId != -1 || dialogueId != -1;
	}

	public void drawChatArea() {
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 165;
		if (frameMode == ScreenMode.FIXED) {
			chatboxImageProducer.initDrawingArea();
		}
		Rasterizer3D.scanOffsets = anIntArray1180;
		if (chatStateCheck()) {
			showChatComponents = true;
			spriteCache.draw(Configuration.skin.equals(Configuration.Skin.AROUND_2005) ? 61 : Configuration.skin.equals(Configuration.Skin.AROUND_2006) ? 61 : 20, 0, yOffset);
		}
		if (showChatComponents) {
			if ((changeChatArea && frameMode != ScreenMode.FIXED) && !chatStateCheck()) {
				Rasterizer2D.drawHorizontalLine(7, 7 + yOffset, 506, 0x575757);
				Rasterizer2D.fillGradientRectangle(7, 7 + yOffset, 510, 130, 0x00000000, 0x5A000000);
			} else {
				spriteCache.draw(Configuration.skin.equals(Configuration.Skin.AROUND_2005) ? 61 : Configuration.skin.equals(Configuration.Skin.AROUND_2006) ? 61 : 20, 0, yOffset);
			}
		}
		drawChannelButtons();
		GameFont font = regularText;
		if(messagePromptRaised) {
			boldText.drawText(0, aString1121, 60 + yOffset, 259);
			boldText.drawText(128, promptInput + "*", 80 + yOffset, 259);
		} else if(inputDialogState == 1) {
			boldText.drawText(0, "Enter amount:", 60 + yOffset, 259);
			boldText.drawText(128, amountOrNameInput + "*", 80 + yOffset, 259);
		} else if(inputDialogState == 2) {
			boldText.drawText(0, "Enter name:", 60 + yOffset, 259);
			boldText.drawText(128, amountOrNameInput + "*", 80 + yOffset, 259);
		} else if(clickToContinueString != null) {
			boldText.drawText(0, clickToContinueString, 60 + yOffset, 259);
			boldText.drawText(128, "Click to continue", 80 + yOffset, 259);
		} else if(backDialogueId != -1) {
			try {
				drawInterface(0, 20, Widget.interfaceCache[backDialogueId], 20 + yOffset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(dialogueId != -1) {
			try {
				drawInterface(0, 20, Widget.interfaceCache[dialogueId], 20 + yOffset);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (showChatComponents) {
			int j77 = -3;
			int j = 0;
			int shadow = (changeChatArea && frameMode != ScreenMode.FIXED) ? 0 : -1;
			Rasterizer2D.setDrawingArea(122 + yOffset, 8, 497, 7 + yOffset);
			for(int k = 0; k < 500; k++) {
				if(chatMessages[k] != null) {
					int chatType = chatTypes[k];
					int yPos = (70 - j77 * 14) + anInt1089 + 5;
					String s1 = chatNames[k];
					byte byte0 = 0;
					if(s1 != null && s1.startsWith("@cr1@")) {
						s1 = s1.substring(5);
						byte0 = 1;
					} else if(s1 != null && s1.startsWith("@cr2@")) {
						s1 = s1.substring(5);
						byte0 = 2;
					} else if(s1 != null && s1.startsWith("@cr3@")) {
						s1 = s1.substring(5);
						byte0 = 3;
					} else if(s1 != null && s1.startsWith("@cr4@")) {
						s1 = s1.substring(5);
						byte0 = 4;
					} else if(s1 != null && s1.startsWith("@cr5@")) {
						s1 = s1.substring(5);
						byte0 = 5;
					} else if(s1 != null && s1.startsWith("@cr6@ ")) {
						s1 = s1.substring(5);
						byte0 = 6;
					} else if(s1 != null && s1.startsWith("@cr7@")) {
						s1 = s1.substring(5);
						byte0 = 7;
					}

					if(chatType == 0) {
						if (chatTypeView == 5 || chatTypeView == 0) {
							newRegularFont.drawBasicString(chatMessages[k], 11,yPos + yOffset, (changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0, shadow);
							j++;
							j77++;
						}
					}
					if((chatType == 1 || chatType == 2) && (chatType == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 1 || chatTypeView == 0) {
							int xPos = 11;
							if(byte0 == 1) {
								modIcons[0].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 2) {
								modIcons[1].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 3) {
								modIcons[2].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 4) {
								modIcons[3].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 5) {
								modIcons[4].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 6) {
								modIcons[5].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += 14;
							} else if(byte0 == 7) {
								modIcons[6].draw(xPos + 1, yPos - 12 + yOffset);
								xPos += modIcons[6].width;
							}

							newRegularFont.drawBasicString(s1 + ":", xPos,
									yPos + yOffset, (changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0, shadow);
							xPos += font.getTextWidth(s1) + 8;
							newRegularFont.drawBasicString(chatMessages[k], xPos,
									yPos + yOffset,
									(changeChatArea && frameMode != ScreenMode.FIXED) ? 0x7FA9FF : 255, shadow);
						}
						j++;
						j77++;
					}
					if((chatType == 3 || chatType == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (chatType == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							int k1 = 11;
							newRegularFont.drawBasicString("From", k1, yPos + yOffset,
									(changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0, shadow);
							k1 += font.getTextWidth("From ");
							if(byte0 == 1) {
								modIcons[0].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 2) {
								modIcons[1].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 3) {
								modIcons[2].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 4) {
								modIcons[3].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 5) {
								modIcons[4].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 6) {
								modIcons[5].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							} else if(byte0 == 7) {
								modIcons[6].draw(k1, yPos - 12 + yOffset);
								k1 += 12;
							}
							newRegularFont.drawBasicString(s1 + ":", k1,
									yPos + yOffset, (changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0, shadow);
							k1 += font.getTextWidth(s1) + 8;
							newRegularFont.drawBasicString(chatMessages[k], k1,
									yPos + yOffset, 0x800000, shadow);
						}
						j++;
						j77++;
					}
					if(chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k],
									11, yPos + yOffset, 0x800080, shadow);
							j++;
							j77++;
						}
					}
					if(chatType == 5 && splitPrivateChat == 0 && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k],
									11, yPos + yOffset, 0x800080, shadow);
							j++;
							j77++;
						}
					}
					if(chatType == 6 && (splitPrivateChat == 0 || chatTypeView == 2) && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							newRegularFont.drawBasicString("To " + s1 + ":", 11,
									yPos + yOffset, (changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0,
									shadow);
							newRegularFont.drawBasicString(chatMessages[k],
									15 + font.getTextWidth("To :" + s1),
									yPos + yOffset, 0x800000, shadow);
							j++;
							j77++;
						}
					}
					if(chatType == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s1))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							newRegularFont.drawBasicString(s1 + " " + chatMessages[k], 11, yPos + yOffset, 0x7e3200, shadow);
							j++;
							j77++;
						}
						if(chatType == 11 && (clanChatMode == 0)) {
							if (chatTypeView == 11) {
								newRegularFont.drawBasicString(
										s1 + " " + chatMessages[k], 11,
										yPos + yOffset, 0x7e3200, shadow);
								j++;
								j77++;
							}
							if(chatType == 12) {
								newRegularFont.drawBasicString(chatMessages[k] + "",
										11, yPos + yOffset, 0x7e3200, shadow);
								j++;
							}
						}
					}
					if(chatType == 16) {
						int j2 = 40;
						int clanNameWidth = font.getTextWidth(clanname);
						if(chatTypeView == 11 || chatTypeView == 0) {
							switch(chatRights[k]) {
								case 1:
									j2 += clanNameWidth;
									modIcons[0].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								case 2:
									j2 += clanNameWidth;
									modIcons[1].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								case 3:
									j2 += clanNameWidth;
									modIcons[2].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								case 4:
									j2 += clanNameWidth;
									modIcons[3].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								case 5:
									j2 += clanNameWidth;
									modIcons[4].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								case 6:
									j2 += clanNameWidth;
									modIcons[5].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;
								case 7:
									j2 += clanNameWidth;
									modIcons[6].draw(j2 - 18, yPos - 12 + yOffset);
									j2 += 14;
									break;

								default:
									j2 += clanNameWidth;
									break;
							}
							font.render(0, "[", yPos + yOffset, 8);
							font.render(255, ""+clanname+"", yPos + yOffset, 14);
							font.render(0, "]", yPos + yOffset, clanNameWidth + 14);

							font.render(0, capitalize(chatNames[k]) + ":", yPos + yOffset, j2 - 17); //j2
							j2 += font.getTextWidth(chatNames[k]) + 7;
							font.render(0x800000, chatMessages[k], yPos + yOffset, j2 - 16);//j2
							j++;
							j77++;
						}
					}
				}
			}
			Rasterizer2D.defaultDrawingAreaSize();
			anInt1211 = j * 14 + 7 + 5;
			if(anInt1211 < 111)
				anInt1211 = 111;
			drawScrollbar(114, anInt1211 - anInt1089 - 113, 7 + yOffset, 496, anInt1211);
			String s = "";
			if(localPlayer != null && localPlayer.name != null) {
				s = localPlayer.name;
			} else {
				s = TextClass.fixName(capitalize(myUsername));
			}
			int xOffset = 10;
			newRegularFont.drawBasicString(s + ":", xOffset, 133 + yOffset,
					(changeChatArea && frameMode != ScreenMode.FIXED) ? 0xFFFFFF : 0, shadow);
			newRegularFont.drawBasicString(inputString + "*",
					xOffset + font.getTextWidth(s + ": "), 133 + yOffset,
					(changeChatArea && frameMode != ScreenMode.FIXED) ? 0x7FA9FF : 255, shadow);
			Rasterizer2D.drawHorizontalLine(7, 121 + yOffset, 506, (changeChatArea && frameMode != ScreenMode.FIXED) ? 0x575757 : 0x807660);
			Rasterizer2D.defaultDrawingAreaSize();
		}
		if (menuOpen) {
			drawMenu(0, frameMode == ScreenMode.FIXED ? 338 : 0);
		} else {
			drawHoverMenu(0, frameMode == ScreenMode.FIXED ? 338 : 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			chatboxImageProducer.drawGraphics(338, super.graphics, 0);
		}
		gameScreenImageProducer.initDrawingArea();
		Rasterizer3D.scanOffsets = anIntArray1182;
	}

	public Thread startRunnable(Runnable runnable, int priority) {
		if (priority < 1)
			priority = 1;
		if (priority > 10)
			priority = 10;
		return super.startRunnable(runnable, priority);
	}

	public Socket openSocket(int port) throws IOException {
			return new Socket(InetAddress.getByName(server), port);
	}

	private void processMenuClick() {
		if (activeInterfaceType != 0)
			return;
		int j = super.clickMode3;
		if (spellSelected == 1 && super.saveClickX >= 516 && super.saveClickY >= 160
				&& super.saveClickX <= 765 && super.saveClickY <= 205)
			j = 0;
		if (menuOpen) {
			if (j != 1) {
				int k = super.mouseX;
				int j1 = super.mouseY;
				if (menuScreenArea == 0) {
					k -= 4;
					j1 -= 4;
				}
				if (menuScreenArea == 1) {
					k -= 519;
					j1 -= 168;
				}
				if (menuScreenArea == 2) {
					k -= 17;
					j1 -= 338;
				}
				if (menuScreenArea == 3) {
					k -= 519;
					j1 -= 0;
				}
				if (k < menuOffsetX - 10 || k > menuOffsetX + menuWidth + 10
						|| j1 < menuOffsetY - 10
						|| j1 > menuOffsetY + menuHeight + 10) {
					menuOpen = false;
					if (menuScreenArea == 1) {
					}
					if (menuScreenArea == 2)
						updateChatbox = true;
				}
			}
			if (j == 1) {
				int l = menuOffsetX;
				int k1 = menuOffsetY;
				int i2 = menuWidth;
				int k2 = super.saveClickX;
				int l2 = super.saveClickY;
				switch (menuScreenArea) {
					case 0:
						k2 -= 4;
						l2 -= 4;
						break;
					case 1:
						k2 -= 519;
						l2 -= 168;
						break;
					case 2:
						k2 -= 5;
						l2 -= 338;
						break;
					case 3:
						k2 -= 519;
						l2 -= 0;
						break;
				}
				int i3 = -1;
				for (int j3 = 0; j3 < menuActionRow; j3++) {
					int k3 = k1 + 31 + (menuActionRow - 1 - j3) * 15;
					if (k2 > l && k2 < l + i2 && l2 > k3 - 13 && l2 < k3 + 3)
						i3 = j3;
				}
				if (i3 != -1)
					processMenuActions(i3);
				menuOpen = false;
				if (menuScreenArea == 1) {
				}
				if (menuScreenArea == 2) {
					updateChatbox = true;
				}
			}
		} else {
			if (j == 1 && menuActionRow > 0) {
				int i1 = menuActionTypes[menuActionRow - 1];
				if (i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74
						|| i1 == 454 || i1 == 539 || i1 == 493 || i1 == 847 || i1 == 447
						|| i1 == 1125) {
					int l1 = firstMenuAction[menuActionRow - 1];
					int j2 = secondMenuAction[menuActionRow - 1];
					Widget class9 = Widget.interfaceCache[j2];
					if (class9.allowSwapItems || class9.replaceItems) {
						aBoolean1242 = false;
						dragItemDelay = 0;
						anInt1084 = j2;
						anInt1085 = l1;
						activeInterfaceType = 2;
						anInt1087 = super.saveClickX;
						anInt1088 = super.saveClickY;
						if (Widget.interfaceCache[j2].parent == openInterfaceId)
							activeInterfaceType = 1;
						if (Widget.interfaceCache[j2].parent == backDialogueId)
							activeInterfaceType = 3;
						return;
					}
				}
			}
			if (j == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1))
					&& menuActionRow > 2)
				j = 2;
			if (j == 1 && menuActionRow > 0)
				processMenuActions(menuActionRow - 1);
			if (j == 2 && menuActionRow > 0)
				determineMenuSize();
			processMainScreenClick();
			processTabClick();
			processChatModeClick();
			minimapHovers();
		}
	}

	public byte[] getModel(int index) {
		try {
			File model = new File(signlink.findcachedir() + "/Models/" + index + ".gz");
			byte[] aByte = new byte[(int)model.length()];
			FileInputStream Fis = new FileInputStream(model);
			Fis.read(aByte);
			pushMessage("aByte = [" + aByte + "]!", 0, "");
			Fis.close();
			return aByte;
		} catch(Exception e) {
			return null;
		}
	}

	public boolean shouldDrawCombatBox() {
		if (!Configuration.combatOverlayBox) {
			return false;
		}
		return currentInteract != null && !combatBoxTimer.finished();
	}

	public void drawCombatBox() {

		int currentHealth = currentInteract.currentHealth;
		int maxHealth = currentInteract.maxHealth;

		//Get name..
		String name = null;
		if (currentInteract instanceof Player) {
			name = ((Player) currentInteract).name;
		} else if (currentInteract instanceof Npc) {
			if (((Npc) currentInteract).desc != null) {
				name = ((Npc) currentInteract).desc.name;
			}
		}

		if (name == null || currentHealth == 0) {
			return;
		}

		List<String> wrapName = StringUtils.wrapText(name, 20);

		int x = 5;
		int y = 20;
		int width = 170;
		int height = 42;
		int yOffset = wrapName.size() > 1 ? 15 : 0;
		Rasterizer2D.drawTransparentBox(x, y, width, height + yOffset, 0x4D5041, 195);
		Rasterizer2D.drawBoxOutline(x, y, width, height + yOffset, 0x000000);
		if (wrapName.size() > 1) {
			for (int i = 0; i < wrapName.size(); i++) {
				if (name != null) {
					boldText.drawCenteredText(wrapName.get(i), (width / 2) + 5, y + 15 * (i) + 15, 0xffffff, true);
				}
			}
		} else {
			boldText.drawCenteredText(name, (width / 2) + 5, y + 15, 0xffffff, true);
		}
		double percent = (int) (((double) currentHealth / (double) maxHealth) * (width - 4));
		if (percent >= width - 4) {
			percent = width - 4;
		}
		Rasterizer2D.drawTransparentBox(7, y + 20 + yOffset, width - 4, 20, 0xff0000, 160);
		Rasterizer2D.drawTransparentBox(7, y + 20 + yOffset, (int) percent, 20, 0x00ff00, 160);
		Rasterizer2D.drawBoxOutline(7, y + 20 + yOffset, width - 4, 20, 0x000000);
		boldText.drawCenteredText(currentHealth + " / " + maxHealth, (width / 2) + 5, y + 35 + yOffset, 0xffffff, true);
	}

	public static int totalRead = 0;

	public static String getFileNameWithoutExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
			return tmpFile.getName().substring(0, whereDot);
		}
		return "";
	}

	public void preloadModels() {
		File file = new File(signlink.findcachedir() + "/Raw/");
		File[] fileArray = file.listFiles();
		for(int y = 0; y < fileArray.length; y++) {
			String s = fileArray[y].getName();
			byte[] buffer = ReadFile(signlink.findcachedir() + "/Raw/" +s);
			Model.method460(buffer,Integer.parseInt(getFileNameWithoutExtension(s)));
		}
	}

	public static final byte[] ReadFile(String s) {
		try {
			byte abyte0[];
			File file = new File(s);
			int i = (int)file.length();
			abyte0 = new byte[i];
			DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
			datainputstream.readFully(abyte0, 0, i);
			datainputstream.close();
			totalRead++;
			return abyte0;
		} catch(Exception e) {
			System.out.println((new StringBuilder()).append("Read Error: ").append(s).toString());
			return null;
		}
	}

	public void models() {
		for(int ModelIndex = 0; ModelIndex < 14927; ModelIndex++) {
			byte[] abyte0 = getModel(ModelIndex);
			if(abyte0 != null && abyte0.length > 0) {
			decompressors[1].method234(abyte0.length, abyte0, ModelIndex);
			//pushMessage("Model added successfully!", 0, "");
			}
		}
	}

	public void saveMidi(boolean flag, byte abyte0[])
	{
		signlink.midifade = flag ? 1 : 0;
		signlink.midisave(abyte0, abyte0.length);
	}
	
	public static void writeFile(byte[] data, String fileName) throws IOException{
		OutputStream out = new FileOutputStream(fileName);
		out.write(data);
		out.close();
	}

	public void method22() {
		try {
			anInt985 = -1;
			projectiles.removeAll();
			aClass19_1013.removeAll();
			Rasterizer3D.clearTextureCache();
			unlinkMRUNodes();
			scene.initToNull();
			System.gc();
			for (int i = 0; i < 4; i++)
				collisionMaps[i].setDefault();

			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++)
						tileFlags[l][k1][j2] = 0;

				}

			}

			MapRegion mapRegion = new MapRegion(tileFlags, tileHeights);
            int k2 = aByteArrayArray1183.length;
			int k18 = 62;
			 for(int A = 0; A < k2; A++)
				for(int B = 0; B < 2000; B++)
					if(anIntArray1234[A] == positions[B]){
						anIntArray1235[A] = landScapes[B];
						anIntArray1236[A] = objects[B];
					}
			buffer.createFrame(0);
			  if(!aBoolean1159) {
                for(int i3 = 0; i3 < k2; i3++) {
                    int i4 = (anIntArray1234[i3] >> 8) * 64 - regionBaseX;
                    int k5 = (anIntArray1234[i3] & 0xff) * 64 - regionBaseY;
                    byte abyte0[] = aByteArrayArray1183[i3];
                    if (FileOperations.FileExists(signlink.findcachedir()+"Maps/Maps/"+anIntArray1235[i3]+".dat"))
                    abyte0 = FileOperations.ReadFile(signlink.findcachedir()+"Maps/Maps/"+anIntArray1235[i3]+".dat");
                    if(abyte0 != null)
						mapRegion.method180(abyte0, k5, i4, (currentRegionX - 6) * 8, (currentRegionY - 6) * 8, collisionMaps);
				}
                for(int j4 = 0; j4 < k2; j4++) {
                    int l5 = (anIntArray1234[j4] >> 8) * k18 - regionBaseX;
                    int k7 = (anIntArray1234[j4] & 0xff) * k18 - regionBaseY;
                    byte abyte2[] = aByteArrayArray1183[j4];
                    if(abyte2 == null && currentRegionY < 800)
						mapRegion.initiateVertexHeights(k7, 64, 64, l5);
                }
                anInt1097++;
                if(anInt1097 > 160) {
                    anInt1097 = 0;
                    buffer.createFrame(238);
                    buffer.writeWordBigEndian(96);
                }
                buffer.createFrame(0);
                for(int i6 = 0; i6 < k2; i6++) {
                    byte abyte1[] = aByteArrayArray1247[i6];
                    if (FileOperations.FileExists(signlink.findcachedir()+"Maps/Maps/"+anIntArray1236[i6]+".dat"))
                    abyte1 = FileOperations.ReadFile(signlink.findcachedir()+"Maps/Maps/"+anIntArray1236[i6]+".dat");
                    if(abyte1 != null) {
                        int l8 = (anIntArray1234[i6] >> 8) * 64 - regionBaseX;
                        int k9 = (anIntArray1234[i6] & 0xff) * 64 - regionBaseY;
						mapRegion.method190(l8, collisionMaps, k9, scene, abyte1);
                    }
                }
            }
			if (aBoolean1159) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = constructRegionData[j3][k4][j6];
							if (l7 != -1) {
								int i9 = l7 >> 24 & 3;
						int l9 = l7 >> 1 & 3;
				int j10 = l7 >> 14 & 0x3ff;
		int l10 = l7 >> 3 & 0x7ff;
		int j11 = (j10 / 8 << 8) + l10 / 8;
		for (int l11 = 0; l11 < anIntArray1234.length; l11++) {
			if (anIntArray1234[l11] != j11
					|| aByteArrayArray1183[l11] == null)
				continue;
			mapRegion.loadMapChunk(i9, l9,
					collisionMaps, k4 * 8,
					(j10 & 7) * 8,
					aByteArrayArray1183[l11],
					(l10 & 7) * 8, j3, j6 * 8);
			break;
		}

							}
						}

					}

				}

				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = constructRegionData[0][l4][k6];
						if (i8 == -1)
							mapRegion.initiateVertexHeights(k6 * 8, 8, 8, l4 * 8);
					}

				}

				buffer.createFrame(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = constructRegionData[l6][j8][j9];
							if (i10 != -1) {
								int k10 = i10 >> 24 & 3;
						int i11 = i10 >> 1 & 3;
				int k11 = i10 >> 14 & 0x3ff;
				int i12 = i10 >> 3 & 0x7ff;
		int j12 = (k11 / 8 << 8) + i12 / 8;
		for (int k12 = 0; k12 < anIntArray1234.length; k12++) {
			if (anIntArray1234[k12] != j12
					|| aByteArrayArray1247[k12] == null)
				continue;
			mapRegion.readObjectMap(collisionMaps,
					scene, k10, j8 * 8,
					(i12 & 7) * 8, l6,
					aByteArrayArray1247[k12],
					(k11 & 7) * 8, i11, j9 * 8);
			break;
		}

							}
						}

					}

				}

			}
			buffer.createFrame(0);
			mapRegion.createRegionScene(collisionMaps, scene);
			gameScreenImageProducer.initDrawingArea();
			buffer.createFrame(0);
			int k3 = MapRegion.maximumPlane;
			if (k3 > plane)
				k3 = plane;
			if (k3 < plane - 1)
				k3 = plane - 1;
			if (lowMem)
				scene.method275(MapRegion.maximumPlane);
			else
				scene.method275(0);
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++)
					spawnGroundItem(i5, i7);

			}

			anInt1051++;
			if (anInt1051 > 98) {
				anInt1051 = 0;
				buffer.createFrame(150);
			}
			method63();
		} catch (Exception exception) {
		}
		ObjectDefinition.referenceCache1.unlinkAll();
		if (super.gameFrame != null) {
			buffer.createFrame(210);
			buffer.writeDWord(0x3f008edd);
		}
		if (lowMem && signlink.cache_dat != null) {
			int j = resourceProvider.getVersionCount(0);
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = resourceProvider.getModelIndex(i1);
				if ((l1 & 0x79) == 0)
					Model.method461(i1);
			}

		}
		System.gc();
		Rasterizer3D.initiateRequestBuffers();
		resourceProvider.method566();
		int k = (currentRegionX - 6) / 8 - 1;
		int j1 = (currentRegionX + 6) / 8 + 1;
		int i2 = (currentRegionY - 6) / 8 - 1;
		int l2 = (currentRegionY + 6) / 8 + 1;
		if (aBoolean1141) {
			k = 49;
			j1 = 50;
			i2 = 49;
			l2 = 50;
		}
		for (int l3 = k; l3 <= j1; l3++) {
			for (int j5 = i2; j5 <= l2; j5++)
				if (l3 == k || l3 == j1 || j5 == i2 || j5 == l2) {
					int j7 = resourceProvider.method562(0, j5, l3);
					if (j7 != -1)
						resourceProvider.method560(j7, 3);
					int k8 = resourceProvider.method562(1, j5, l3);
					if (k8 != -1)
						resourceProvider.method560(k8, 3);
				}

		}

	}

	public void unlinkMRUNodes()
	{
		ObjectDefinition.referenceCache1.unlinkAll();
		ObjectDefinition.referenceCache2.unlinkAll();
		NpcDefinition.modelCache.unlinkAll();
		ItemDefinition.models.unlinkAll();
		ItemDefinition.sprites.unlinkAll();
		Player.referenceCache.unlinkAll();
		Graphic.aReferenceCache_415.unlinkAll();
	}

	private void renderMapScene(int plane) {
		int pixels[] = minimapImage.myPixels;
		int length = pixels.length;

		for (int pixel = 0; pixel < length; pixel++) {
			pixels[pixel] = 0;
		}


		for (int y = 1; y < 103; y++) {
			int i1 = 24628 + (103 - y) * 512 * 4;
			for (int x = 1; x < 103; x++) {
				if ((tileFlags[plane][x][y] & 0x18) == 0)
					scene.drawTileOnMinimapSprite(pixels, i1, plane, x, y);
				if (plane < 3 && (tileFlags[plane + 1][x][y] & 8) != 0)
					scene.drawTileOnMinimapSprite(pixels, i1, plane + 1, x, y);
				i1 += 4;
			}

		}

		int j1 = 0xFFFFFF;
		int l1 = 0xEE0000;
		minimapImage.init();

		for (int y = 1; y < 103; y++) {
			for (int x = 1; x < 103; x++) {
				if ((tileFlags[plane][x][y] & 0x18) == 0)
					drawMapScenes(y, j1, x, l1, plane);
				if (plane < 3 && (tileFlags[plane + 1][x][y] & 8) != 0)
					drawMapScenes(y, j1, x, l1, plane + 1);
			}

		}

		gameScreenImageProducer.initDrawingArea();
		anInt1071 = 0;

		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				int id = scene.getGroundDecorationUid(plane, x, y);
				if (id != 0) {
					id = id >> 14 & 0x7fff;

					int function = ObjectDefinition.lookup(id).minimapFunction;
					if (function >= 15 && function <= 67) {
						function -= 2;
					} else if (function == 13 || function >= 68 && function <= 84) {
						function -= 1;
					}
					if (function >= 0) {
						int viewportX = x;
						int viewportY = y;
						minimapHint[anInt1071] = mapFunctions[function];
						minimapHintX[anInt1071] = viewportX;
						minimapHintY[anInt1071] = viewportY;
						anInt1071++;
					}
				}
			}

		}
	}

	public void spawnGroundItem(int i, int j)
	{
		Deque class19 = groundItems[plane][i][j];
		if(class19 == null)
		{
			scene.removeGroundItemTile(plane, i, j);
			return;
		}
		int k = 0xfa0a1f01;
		Object obj = null;
		for(Item item = (Item)class19.reverseGetFirst(); item != null; item = (Item)class19.reverseGetNext())
		{
			ItemDefinition itemDefinition = ItemDefinition.lookup(item.ID);
			int l = itemDefinition.value;
			if(itemDefinition.stackable)
				l *= item.anInt1559 + 1;
//	notifyItemSpawn(item, i + regionBaseX, j + regionBaseY);
	
			if(l > k)
			{
				k = l;
				obj = item;
			}
		}

		class19.insertTail(((Linkable) (obj)));
		Object obj1 = null;
		Object obj2 = null;
		for(Item class30_sub2_sub4_sub2_1 = (Item)class19.reverseGetFirst(); class30_sub2_sub4_sub2_1 != null; class30_sub2_sub4_sub2_1 = (Item)class19.reverseGetNext())
		{
			if(class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && obj1 == null)
				obj1 = class30_sub2_sub4_sub2_1;
			if(class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && class30_sub2_sub4_sub2_1.ID != ((Item) (obj1)).ID && obj2 == null)
				obj2 = class30_sub2_sub4_sub2_1;
		}

		int i1 = i + (j << 7) + 0x60000000;
		scene.addGroundItemTile(i, i1, ((Renderable) (obj1)), getCenterHeight(plane, j * 128 + 64, i * 128 + 64), ((Renderable) (obj2)), ((Renderable) (obj)), plane, j);
	}

	public void method26(boolean flag)
	{
		for(int j = 0; j < npcCount; j++)
		{
			Npc npc = npcs[npcIndices[j]];
			int k = 0x20000000 + (npcIndices[j] << 14);
			if(npc == null || !npc.isVisible() || npc.desc.priorityRender != flag)
				continue;
			int l = npc.x >> 7;
			int i1 = npc.y >> 7;
			if(l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
				continue;
			if(npc.size == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64)
			{
				if(anIntArrayArray929[l][i1] == anInt1265)
					continue;
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if(!npc.desc.clickable)
				k += 0x80000000;
			scene.addAnimableA(plane, npc.anInt1552, getCenterHeight(plane, npc.y, npc.x), k, npc.y, (npc.size - 1) * 64 + 60, npc.x, npc, npc.animationScretches);
		}
	}

	private boolean replayWave()
	{
			return signlink.wavereplay();
	}

	public void loadError()
	{
		String s = "ondemand";//was a constant parameter
		System.out.println(s);
		try
		{
			getAppletContext().showDocument(new URL(getCodeBase(), "loaderror_" + s + ".html"));
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		do
			try
			{
				Thread.sleep(1000L);
			}
			catch(Exception _ex) { }
		while(true);
	}

	private void buildInterfaceMenu(int i, Widget widget, int k, int l, int i1, int j1) {
		if (widget == null || widget.type != 0 || widget.children == null || widget.invisible || widget.hidden)
			return;
		if (k < i || i1 < l || k > i + widget.width || i1 > l + widget.height)
			return;
		int size = widget.children.length;
		for (int l1 = 0; l1 < size; l1++) {
			int i2 = widget.childX[l1] + i;
			int j2 = (widget.childY[l1] + l) - j1;
			Widget widget_child = Widget.interfaceCache[widget.children[l1]];
			if (widget_child == null || widget_child.hidden) {
				continue;
			}
			i2 += widget_child.horizontalOffset;
			j2 += widget_child.verticalOffset;
			if ((widget_child.hoverType >= 0 || widget_child.defaultHoverColor != 0)
					&& k >= i2 && i1 >= j2 && k < i2 + widget_child.width
					&& i1 < j2 + widget_child.height)
				if (widget_child.hoverType >= 0)
					anInt886 = widget_child.hoverType;
				else
					anInt886 = widget_child.id;
			if (widget_child.type == 8 && k >= i2 && i1 >= j2
					&& k < i2 + widget_child.width && i1 < j2 + widget_child.height) {
				anInt1315 = widget_child.id;
			}
			if (widget_child.type == Widget.TYPE_CONTAINER) {
				buildInterfaceMenu(i2, widget_child, k, j2, i1,
						widget_child.scrollPosition);
				if (widget_child.scrollMax > widget_child.height)
					method65(i2 + widget_child.width, widget_child.height, k, i1,
							widget_child, j2, true, widget_child.scrollMax);
			} else {
				if (widget_child.atActionType == Widget.OPTION_OK && k >= i2 && i1 >= j2 && k < i2 + widget_child.width && i1 < j2 + widget_child.height) {
					boolean flag = false;

					if (widget_child.contentType != 0 && (widget_child.parent == 5065 || widget_child.parent == 5715))
						flag = buildFriendsListMenu(widget_child);

					if (widget_child.tooltip == null || widget_child.tooltip.length() == 0) {
						flag = true;
					}

					if (!flag) {
						if ((myPrivilege >= 2 && myPrivilege <= 4)) {
							menuActionText[menuActionRow] = widget_child.tooltip + " " + widget_child.id;
							menuActionTypes[menuActionRow] = 315;
							secondMenuAction[menuActionRow] = widget_child.id;
							menuActionRow++;
						} else {
							menuActionText[menuActionRow] = widget_child.tooltip;
							menuActionTypes[menuActionRow] = 315;
							secondMenuAction[menuActionRow] = widget_child.id;
							menuActionRow++;
						}
					}
					if (widget_child.type == Widget.TYPE_HOVER || widget_child.type == Widget.TYPE_CONFIG_HOVER || widget_child.type == Widget.TYPE_ADJUSTABLE_CONFIG
							|| widget_child.type == Widget.TYPE_BOX) {
						widget_child.toggled = true;
					}
				} else if (widget_child.atActionType == Widget.OPTION_CLOSE && k >= i2 && i1 >= j2 && k < i2 + widget_child.width && i1 < j2 + widget_child.height) {
					if (widget_child.type == Widget.TYPE_HOVER) {
						widget_child.toggled = true;
					}
				} else {
					if (widget_child.type == Widget.TYPE_HOVER || widget_child.type == Widget.TYPE_CONFIG_HOVER || widget_child.type == Widget.TYPE_ADJUSTABLE_CONFIG
							|| widget_child.type == Widget.TYPE_BOX) {
						widget_child.toggled = false;
					}
				}
				if (widget_child.atActionType == Widget.OPTION_USABLE && spellSelected == 0
						&& k >= i2 && i1 >= j2 && k < i2 + widget_child.width
						&& i1 < j2 + widget_child.height) {
					String s = widget_child.selectedActionName;
					if (s.indexOf(" ") != -1)
						s = s.substring(0, s.indexOf(" "));
					if (widget_child.spellName.endsWith("Rush")
							|| widget_child.spellName.endsWith("Burst")
							|| widget_child.spellName.endsWith("Blitz")
							|| widget_child.spellName.endsWith("Barrage")
							|| widget_child.spellName.endsWith("strike")
							|| widget_child.spellName.endsWith("bolt")
							|| widget_child.spellName.equals("Crumble undead")
							|| widget_child.spellName.endsWith("blast")
							|| widget_child.spellName.endsWith("wave")
							|| widget_child.spellName.equals("Claws of Guthix")
							|| widget_child.spellName.equals("Flames of Zamorak")
							|| widget_child.spellName.equals("Magic Dart")) {
						menuActionText[menuActionRow] =
								"Autocast @gre@" + widget_child.spellName;

						menuActionTypes[menuActionRow] = 104;
						secondMenuAction[menuActionRow] = widget_child.id;
						menuActionRow++;
					}
					if ((myPrivilege >= 2 && myPrivilege <= 4)) {
						menuActionText[menuActionRow] =
								s + " @gre@" + widget_child.spellName + " " + widget_child.id;
						menuActionTypes[menuActionRow] = 626;
						secondMenuAction[menuActionRow] = widget_child.id;
						menuActionRow++;
					} else {
						menuActionText[menuActionRow] =
								s + " @gre@" + widget_child.spellName;
						menuActionTypes[menuActionRow] = 626;
						secondMenuAction[menuActionRow] = widget_child.id;
						menuActionRow++;
					}
				}
				if (widget_child.atActionType == Widget.OPTION_CLOSE && k >= i2 && i1 >= j2
						&& k < i2 + widget_child.width
						&& i1 < j2 + widget_child.height) {
					menuActionText[menuActionRow] = "Close";
					menuActionTypes[menuActionRow] = 200;
					secondMenuAction[menuActionRow] = widget_child.id;
					menuActionRow++;
				}
				if (widget_child.atActionType == Widget.OPTION_TOGGLE_SETTING && k >= i2
						&& i1 >= j2 && k < i2 + widget_child.width
						&& i1 < j2 + widget_child.height) {
					if ((myPrivilege >= 2 && myPrivilege <= 4)) {
						menuActionText[menuActionRow] = widget_child.tooltip + " @lre@(" + widget_child.id + ")";
					} else {
						menuActionText[menuActionRow] = widget_child.tooltip;
					}
					menuActionTypes[menuActionRow] = 169;
					secondMenuAction[menuActionRow] = widget_child.id;
					menuActionRow++;
				}

				if (widget_child.atActionType == Widget.OPTION_RESET_SETTING && k >= i2
						&& i1 >= j2 && k < i2 + widget_child.width
						&& i1 < j2 + widget_child.height) {
					boolean flag = false;
					if (widget_child.tooltip == null ||
							widget_child.tooltip.length() == 0) {
						flag = true;
					}
					if (!flag) {
						if ((myPrivilege >= 2 && myPrivilege <= 4)) {
							menuActionText[menuActionRow] = widget_child.tooltip + " @lre@(" + widget_child.id + ")";
						} else {
							menuActionText[menuActionRow] = widget_child.tooltip;
						}
						menuActionTypes[menuActionRow] = 646;
						secondMenuAction[menuActionRow] = widget_child.id;
						menuActionRow++;
					}
				}

				if (widget_child.atActionType == Widget.OPTION_CONTINUE
						&& !continuedDialogue && k >= i2 && i1 >= j2
						&& k < i2 + widget_child.width
						&& i1 < j2 + widget_child.height) {
					menuActionText[menuActionRow] = widget_child.tooltip;
					menuActionTypes[menuActionRow] = 679;
					secondMenuAction[menuActionRow] = widget_child.id;
					menuActionRow++;
				}
				if (widget_child.atActionType == Widget.OPTION_DROPDOWN) {

					boolean flag = false;
					widget_child.hovered = false;
					widget_child.dropdownHover = -1;

					if (widget_child.dropdown.isOpen()) {

						// Inverted keybinds dropdown
						if (widget_child.type == Widget.TYPE_KEYBINDS_DROPDOWN && widget_child.inverted && k >= i2 &&
								k < i2 + (widget_child.dropdown.getWidth() - 16) && i1 >= j2 - widget_child.dropdown.getHeight() - 10 && i1 < j2) {

							int yy = i1 - (j2 - widget_child.dropdown.getHeight());

							if (k > i2 + (widget_child.dropdown.getWidth() / 2)) {
								widget_child.dropdownHover = ((yy / 15) * 2) + 1;
							} else {
								widget_child.dropdownHover = (yy / 15) * 2;
							}
							flag = true;
						} else if (!widget_child.inverted && k >= i2 && k < i2 + (widget_child.dropdown.getWidth() - 16) &&
								i1 >= j2 + 19 && i1 < j2 + 19 + widget_child.dropdown.getHeight()) {

							int yy = i1 - (j2 + 19);

							if (widget_child.type == Widget.TYPE_KEYBINDS_DROPDOWN && widget_child.dropdown.doesSplit()) {
								if (k > i2 + (widget_child.dropdown.getWidth() / 2)) {
									widget_child.dropdownHover = ((yy / 15) * 2) + 1;
								} else {
									widget_child.dropdownHover = (yy / 15) * 2;
								}
							} else {
								widget_child.dropdownHover = yy / 14; // Regular dropdown hover
							}
							flag = true;
						}
						if (flag) {
							if (menuActionRow != 1) {
								menuActionRow = 1;
							}
							menuActionText[menuActionRow] = "Select";
							menuActionTypes[menuActionRow] = 770;
							secondMenuAction[menuActionRow] = widget_child.id;
							firstMenuAction[menuActionRow] = widget_child.dropdownHover;
							selectedMenuActions[menuActionRow] = widget.id;
							menuActionRow++;
						}
					}
					if (k >= i2 && i1 >= j2 && k < i2 + widget_child.dropdown.getWidth() && i1 < j2 + 24 && menuActionRow == 1) {
						widget_child.hovered = true;
						menuActionText[menuActionRow] = widget_child.dropdown.isOpen() ? "Hide" : "Show";
						menuActionTypes[menuActionRow] = 769;
						secondMenuAction[menuActionRow] = widget_child.id;
						selectedMenuActions[menuActionRow] = widget.id;
						menuActionRow++;
					}
				}

				if (k >= i2 && i1 >= j2 && k < i2 + widget_child.width && i1 < j2 + widget_child.height) {

					if (widget_child.actions != null && !widget_child.invisible && !widget_child.hidden) {

						if (!(widget_child.contentType == 206 && interfaceIsSelected(widget_child))) {
							if ((widget_child.type == 4 && widget_child.defaultText.length() > 0) || widget_child.type == 5) {

								boolean drawOptions = true;
								if (drawOptions) {
									for (int action = widget_child.actions.length
											- 1; action >= 0; action--) {
										if (widget_child.actions[action] != null) {
											String s = widget_child.actions[action] + (widget_child.type == 4 ? " @or1@" + widget_child.defaultText : "");

											if (s.contains("img")) {
												int prefix = s.indexOf("<img=");
												int suffix = s.indexOf(">");
												s = s.replaceAll(s.substring(prefix + 5, suffix), "");
												s = s.replaceAll("</img>", "");
												s = s.replaceAll("<img=>", "");
											}

											menuActionText[menuActionRow] = s;
											menuActionTypes[menuActionRow] = 647;
											firstMenuAction[menuActionRow] = action;
											secondMenuAction[menuActionRow] = widget_child.id;
											menuActionRow++;
										}
									}
								}
							}
						}
					}
				}

				if (widget_child.type == Widget.TYPE_INVENTORY && !widget_child.invisible && !widget_child.hidden && !(widget_child.id >= 22035 && widget_child.id <= 22042)) {
					int k2 = 0;
					for (int l2 = 0; l2 < widget_child.height; l2++) {
						for (int i3 = 0; i3 < widget_child.width; i3++) {
							int j3 = i2 + i3 * (32 + widget_child.spritePaddingX);
							int k3 = j2 + l2 * (32 + widget_child.spritePaddingY);
							if (k2 < 20) {
								j3 += widget_child.spritesX[k2];
								k3 += widget_child.spritesY[k2];
							}
							if (k >= j3 && i1 >= k3 && k < j3 + 32 && i1 < k3 + 32) {
								mouseInvInterfaceIndex = k2;
								lastActiveInvInterface = widget_child.id;
								if (k2 >= widget_child.inventoryItemId.length) {
									continue;
								}

								if (widget_child.inventoryItemId[k2] > 0) {

									boolean hasDestroyOption = false;
									ItemDefinition itemDef = ItemDefinition.lookup(widget_child.inventoryItemId[k2] - 1);
									if (itemSelected == 1 && widget_child.hasActions) {
										if (widget_child.id != anInt1284 || k2 != anInt1283) {
											menuActionText[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
											menuActionTypes[menuActionRow] = 870;
											selectedMenuActions[menuActionRow] = itemDef.id;
											firstMenuAction[menuActionRow] = k2;
											secondMenuAction[menuActionRow] = widget_child.id;
											menuActionRow++;
										}
									} else if (spellSelected == 1
											&& widget_child.hasActions) {
										if ((spellUsableOn & 0x10) == 16) {
											menuActionText[menuActionRow] =
													spellTooltip + " @lre@"
															+ itemDef.name;
											menuActionTypes[menuActionRow] =
													543;
											selectedMenuActions[menuActionRow] =
													itemDef.id;
											firstMenuAction[menuActionRow] =
													k2;
											secondMenuAction[menuActionRow] =
													widget_child.id;
											menuActionRow++;
										}
									} else {
										if (widget_child.hasActions) {
											for (int l3 = 4; l3 >= 3; l3--)
												if (itemDef.actions != null
														&& itemDef.actions[l3] != null) {
													menuActionText[menuActionRow] =
															itemDef.actions[l3]
																	+ " @lre@"
																	+ itemDef.name;
													if (l3 == 3)
														menuActionTypes[menuActionRow] =
																493;
													if (l3 == 4) {
														menuActionTypes[menuActionRow] = 847;
														hasDestroyOption = itemDef.actions[l3].contains("Destroy");
													}
													selectedMenuActions[menuActionRow] = itemDef.id;
													firstMenuAction[menuActionRow] = k2;
													secondMenuAction[menuActionRow] = widget_child.id;
													menuActionRow++;
												} else if (l3 == 4) {
													menuActionText[menuActionRow] = "Drop @lre@" + itemDef.name;
													menuActionTypes[menuActionRow] = 847;
													selectedMenuActions[menuActionRow] = itemDef.id;
													firstMenuAction[menuActionRow] = k2;
													secondMenuAction[menuActionRow] = widget_child.id;
													menuActionRow++;
												}
										}
										if (widget_child.usableItems) {
											menuActionText[menuActionRow] = "Use @lre@" + itemDef.name;
											menuActionTypes[menuActionRow] = 447;
											selectedMenuActions[menuActionRow] = itemDef.id;
											firstMenuAction[menuActionRow] = k2;
											secondMenuAction[menuActionRow] = widget_child.id;
											menuActionRow++;

										}
										if (widget_child.hasActions && itemDef.actions != null) {
											for (int i4 = 2; i4 >= 0; i4--) {
												if (itemDef.actions[i4] != null) {
													menuActionText[menuActionRow] = itemDef.actions[i4] + " @lre@" + itemDef.name;
													if (i4 == 0)
														menuActionTypes[menuActionRow] = 74;
													if (i4 == 1)
														menuActionTypes[menuActionRow] = 454;
													if (i4 == 2)
														menuActionTypes[menuActionRow] = 539;
													selectedMenuActions[menuActionRow] = itemDef.id;
													firstMenuAction[menuActionRow] = k2;
													secondMenuAction[menuActionRow] = widget_child.id;
													menuActionRow++;
												}
											}
										}

										// Menu actions, item options etc in interfaces
										// Hardcoding
										if (widget_child.actions != null) {
											for (int type =
												 4; type >= 0; type--) {
												if (widget_child.actions[type] != null) {
													String action = widget_child.actions[type];

													// HARDCODING OF MENU ACTIONS
													if (openInterfaceId == 42000) {
														action = action.replace("Offer", "Pricecheck");
														action += " " + itemDef.id;
													}

													menuActionText[menuActionRow] = action + " @lre@" + itemDef.name;

													if (type == 0)
														menuActionTypes[menuActionRow] =
																632;
													if (type == 1)
														menuActionTypes[menuActionRow] =
																78;
													if (type == 2)
														menuActionTypes[menuActionRow] =
																867;
													if (type == 3)
														menuActionTypes[menuActionRow] =
																431;
													if (type == 4)
														menuActionTypes[menuActionRow] =
																53;
													selectedMenuActions[menuActionRow] =
															itemDef.id;
													firstMenuAction[menuActionRow] =
															k2;
													secondMenuAction[menuActionRow] =
															widget_child.id;
													menuActionRow++;
												}
											}

										}
										if ((myPrivilege >= 2 && myPrivilege <= 4)) {
											menuActionText[menuActionRow] = "Examine @lre@" + itemDef.name + " @gre@(@whi@" + (widget_child.inventoryItemId[k2] - 1) + "@gre@) int: " + widget_child.id;
										} else {
											menuActionText[menuActionRow] = "Examine @lre@" + itemDef.name;
										}
										menuActionTypes[menuActionRow] = 1125;
										selectedMenuActions[menuActionRow] = itemDef.id;
										firstMenuAction[menuActionRow] = k2;
										secondMenuAction[menuActionRow] = widget_child.id;
										menuActionRow++;
									}
								}
							}
							k2++;
						}
					}
				}
			}
		}
	}

	public void drawTransparentScrollBar(int x, int y, int height, int maxScroll, int pos) {
		spriteCache.draw(29, x, y, 120, true);
		spriteCache.draw(30, x, y + height - 16, 120, true);
		Rasterizer2D.drawTransparentVerticalLine(x, y + 16, height - 32, 0xffffff, 64);
		Rasterizer2D.drawTransparentVerticalLine(x + 15, y + 16, height - 32, 0xffffff, 64);
		int barHeight = (height - 32) * height / maxScroll;
		if (barHeight < 10) {
			barHeight = 10;
		}
		int barPos = 0;
		if (maxScroll != height) {
			barPos = (height - 32 - barHeight) * pos / (maxScroll - height);
		}
		Rasterizer2D.drawTransparentBoxOutline(x, y + 16 + barPos, 16,
				5 + y + 16 + barPos + barHeight - 5 - (y + 16 + barPos), 0xffffff, 32);
	}

	public void drawScrollbar(int height, int pos, int y, int x, int maxScroll, boolean transparent) {
		if (transparent) {
			drawTransparentScrollBar(x, y, height, maxScroll, pos);
		} else {
			scrollBar1.drawSprite(x, y);
			scrollBar2.drawSprite(x, (y + height) - 16);
			Rasterizer2D.drawBox(x, y + 16, 16, height - 32, 0x000001);
			Rasterizer2D.drawBox(x, y + 16, 15, height - 32, 0x3d3426);
			Rasterizer2D.drawBox(x, y + 16, 13, height - 32, 0x342d21);
			Rasterizer2D.drawBox(x, y + 16, 11, height - 32, 0x2e281d);
			Rasterizer2D.drawBox(x, y + 16, 10, height - 32, 0x29241b);
			Rasterizer2D.drawBox(x, y + 16, 9, height - 32, 0x252019);
			Rasterizer2D.drawBox(x, y + 16, 1, height - 32, 0x000001);
			int k1 = ((height - 32) * height) / maxScroll;
			if (k1 < 8) {
				k1 = 8;
			}
			int l1 = ((height - 32 - k1) * pos) / (maxScroll - height);
			Rasterizer2D.drawBox(x, y + 16 + l1, 16, k1, barFillColor);
			Rasterizer2D.drawVerticalLine(x, y + 16 + l1, k1, 0x000001);
			Rasterizer2D.drawVerticalLine(x + 1, y + 16 + l1, k1, 0x817051);
			Rasterizer2D.drawVerticalLine(x + 2, y + 16 + l1, k1, 0x73654a);
			Rasterizer2D.drawVerticalLine(x + 3, y + 16 + l1, k1, 0x6a5c43);
			Rasterizer2D.drawVerticalLine(x + 4, y + 16 + l1, k1, 0x6a5c43);
			Rasterizer2D.drawVerticalLine(x + 5, y + 16 + l1, k1, 0x655841);
			Rasterizer2D.drawVerticalLine(x + 6, y + 16 + l1, k1, 0x655841);
			Rasterizer2D.drawVerticalLine(x + 7, y + 16 + l1, k1, 0x61553e);
			Rasterizer2D.drawVerticalLine(x + 8, y + 16 + l1, k1, 0x61553e);
			Rasterizer2D.drawVerticalLine(x + 9, y + 16 + l1, k1, 0x5d513c);
			Rasterizer2D.drawVerticalLine(x + 10, y + 16 + l1, k1, 0x5d513c);
			Rasterizer2D.drawVerticalLine(x + 11, y + 16 + l1, k1, 0x594e3a);
			Rasterizer2D.drawVerticalLine(x + 12, y + 16 + l1, k1, 0x594e3a);
			Rasterizer2D.drawVerticalLine(x + 13, y + 16 + l1, k1, 0x514635);
			Rasterizer2D.drawVerticalLine(x + 14, y + 16 + l1, k1, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 16 + l1, 15, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 15, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 14, 0x655841);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 13, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 11, 0x6d5f48);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 10, 0x73654a);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 7, 0x76684b);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 5, 0x7b6a4d);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 4, 0x7e6e50);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 3, 0x817051);
			Rasterizer2D.drawHorizontalLine(x, y + 17 + l1, 2, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 15, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 14, 0x5d513c);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 11, 0x625640);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 10, 0x655841);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 7, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 5, 0x6e6046);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 4, 0x716247);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 3, 0x7b6a4d);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 2, 0x817051);
			Rasterizer2D.drawHorizontalLine(x, y + 18 + l1, 1, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 15, 0x514635);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 14, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 11, 0x5d513c);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 9, 0x61553e);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 7, 0x655841);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 5, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 4, 0x6e6046);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 3, 0x73654a);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 2, 0x817051);
			Rasterizer2D.drawHorizontalLine(x, y + 19 + l1, 1, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 15, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 14, 0x544936);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 13, 0x594e3a);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 10, 0x5d513c);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 8, 0x61553e);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 6, 0x655841);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 4, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 3, 0x73654a);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 2, 0x817051);
			Rasterizer2D.drawHorizontalLine(x, y + 20 + l1, 1, 0x000001);
			Rasterizer2D.drawVerticalLine(x + 15, y + 16 + l1, k1, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 15 + l1 + k1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 15, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 14, 0x3f372a);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 10, 0x443c2d);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 9, 0x483e2f);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 7, 0x4a402f);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 4, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 3, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 14 + l1 + k1, 2, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 15, 0x443c2d);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 11, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 9, 0x514635);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 7, 0x544936);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 6, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 4, 0x594e3a);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 3, 0x625640);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 2, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 13 + l1 + k1, 1, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 15, 0x443c2d);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 14, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 12, 0x544936);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 11, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 10, 0x594e3a);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 7, 0x5d513c);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 4, 0x61553e);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 3, 0x6e6046);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 2, 0x7b6a4d);
			Rasterizer2D.drawHorizontalLine(x, y + 12 + l1 + k1, 1, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 16, 0x000001);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 15, 0x4b4131);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 14, 0x514635);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 13, 0x564b38);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 11, 0x594e3a);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 9, 0x5d513c);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 7, 0x61553e);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 5, 0x655841);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 4, 0x6a5c43);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 3, 0x73654a);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 2, 0x7b6a4d);
			Rasterizer2D.drawHorizontalLine(x, y + 11 + l1 + k1, 1, 0x000001);
		}
	}

	public void drawScrollbar(int j, int k, int l, int i1, int j1) {
		scrollBar1.drawSprite(i1, l);
		scrollBar2.drawSprite(i1, (l + j) - 16);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x000001, 16);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x3d3426, 15);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x342d21, 13);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x2e281d, 11);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x29241b, 10);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x252019, 9);
		Rasterizer2D.drawPixels(j - 32, l + 16, i1, 0x000001, 1);
		int k1 = ((j - 32) * j) / j1;
		if(k1 < 8)
			k1 = 8;
		int l1 = ((j - 32 - k1) * k) / (j1 - j);
		Rasterizer2D.drawPixels(k1, l + 16 + l1, i1, barFillColor, 16);
		Rasterizer2D.method341(l + 16 + l1, 0x000001, k1, i1);
		Rasterizer2D.method341(l + 16 + l1, 0x817051, k1, i1 + 1);
		Rasterizer2D.method341(l + 16 + l1, 0x73654a, k1, i1 + 2);
		Rasterizer2D.method341(l + 16 + l1, 0x6a5c43, k1, i1 + 3);
		Rasterizer2D.method341(l + 16 + l1, 0x6a5c43, k1, i1 + 4);
		Rasterizer2D.method341(l + 16 + l1, 0x655841, k1, i1 + 5);
		Rasterizer2D.method341(l + 16 + l1, 0x655841, k1, i1 + 6);
		Rasterizer2D.method341(l + 16 + l1, 0x61553e, k1, i1 + 7);
		Rasterizer2D.method341(l + 16 + l1, 0x61553e, k1, i1 + 8);
		Rasterizer2D.method341(l + 16 + l1, 0x5d513c, k1, i1 + 9);
		Rasterizer2D.method341(l + 16 + l1, 0x5d513c, k1, i1 + 10);
		Rasterizer2D.method341(l + 16 + l1, 0x594e3a, k1, i1 + 11);
		Rasterizer2D.method341(l + 16 + l1, 0x594e3a, k1, i1 + 12);
		Rasterizer2D.method341(l + 16 + l1, 0x514635, k1, i1 + 13);
		Rasterizer2D.method341(l + 16 + l1, 0x4b4131, k1, i1 + 14);
		Rasterizer2D.method339(l + 16 + l1, 0x000001, 15, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x000001, 15, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x655841, 14, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x6a5c43, 13, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x6d5f48, 11, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x73654a, 10, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x76684b, 7, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x7b6a4d, 5, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x7e6e50, 4, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x817051, 3, i1);
		Rasterizer2D.method339(l + 17 + l1, 0x000001, 2, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x564b38, 15, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x5d513c, 14, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x625640, 11, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x655841, 10, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x6a5c43, 7, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x6e6046, 5, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x716247, 4, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x7b6a4d, 3, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x817051, 2, i1);
		Rasterizer2D.method339(l + 18 + l1, 0x000001, 1, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x514635, 15, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x564b38, 14, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x5d513c, 11, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x61553e, 9, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x655841, 7, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x6a5c43, 5, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x6e6046, 4, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x73654a, 3, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x817051, 2, i1);
		Rasterizer2D.method339(l + 19 + l1, 0x000001, 1, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x4b4131, 15, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x544936, 14, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x594e3a, 13, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x5d513c, 10, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x61553e, 8, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x655841, 6, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x6a5c43, 4, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x73654a, 3, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x817051, 2, i1);
		Rasterizer2D.method339(l + 20 + l1, 0x000001, 1, i1);
		Rasterizer2D.method341(l + 16 + l1, 0x000001, k1, i1 + 15);
		Rasterizer2D.method339(l + 15 + l1 + k1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x000001, 15, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x3f372a, 14, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x443c2d, 10, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x483e2f, 9, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x4a402f, 7, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x4b4131, 4, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x564b38, 3, i1);
		Rasterizer2D.method339(l + 14 + l1 + k1, 0x000001, 2, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x443c2d, 15, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x4b4131, 11, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x514635, 9, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x544936, 7, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x564b38, 6, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x594e3a, 4, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x625640, 3, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x6a5c43, 2, i1);
		Rasterizer2D.method339(l + 13 + l1 + k1, 0x000001, 1, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x443c2d, 15, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x4b4131, 14, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x544936, 12, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x564b38, 11, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x594e3a, 10, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x5d513c, 7, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x61553e, 4, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x6e6046, 3, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x7b6a4d, 2, i1);
		Rasterizer2D.method339(l + 12 + l1 + k1, 0x000001, 1, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x000001, 16, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x4b4131, 15, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x514635, 14, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x564b38, 13, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x594e3a, 11, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x5d513c, 9, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x61553e, 7, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x655841, 5, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x6a5c43, 4, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x73654a, 3, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x7b6a4d, 2, i1);
		Rasterizer2D.method339(l + 11 + l1 + k1, 0x000001, 1, i1);
		
	}

	public void updateNPCs(Buffer buffer, int i)
	{
		anInt839 = 0;
		mobsAwaitingUpdateCount = 0;
		method139(buffer);
		updateNPCMovement(i, buffer);
		method86(buffer);
		for(int k = 0; k < anInt839; k++)
		{
			int l = anIntArray840[k];
			if(npcs[l].time != tick)
			{
				npcs[l].desc = null;
				npcs[l] = null;
			}
		}

		if(buffer.currentPosition != i)
		{
			signlink.reporterror(myUsername + " size mismatch in getnpcpos - pos:" + buffer.currentPosition + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for(int i1 = 0; i1 < npcCount; i1++)
			if(npcs[npcIndices[i1]] == null)
			{
				signlink.reporterror(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}

	}

	private int cButtonHPos;
	private int cButtonHCPos;
	private int cButtonCPos;
	private int setChannel;

	public void processChatModeClick() {

		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
		if (super.mouseX >= 5 && super.mouseX <= 61 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 0;
			updateChatbox = true;
		} else if (super.mouseX >= 69 && super.mouseX <= 125 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 1;
			updateChatbox = true;
		} else if (super.mouseX >= 133 && super.mouseX <= 189 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 2;
			updateChatbox = true;
		} else if (super.mouseX >= 197 && super.mouseX <= 253 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 3;
			updateChatbox = true;
		} else if (super.mouseX >= 261 && super.mouseX <= 317 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 4;
			updateChatbox = true;
		} else if (super.mouseX >= 325 && super.mouseX <= 381 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 5;
			updateChatbox = true;
		} else if (super.mouseX >= 389 && super.mouseX <= 445 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 6;
			updateChatbox = true;
		} else if (super.mouseX >= 453 && super.mouseX <= 509 && super.mouseY >= yOffset + 482
				&& super.mouseY <= yOffset + 503) {
			cButtonHPos = 7;
			updateChatbox = true;
		} else {
			cButtonHPos = -1;
			updateChatbox = true;
		}
		if (super.clickMode3 == 1) {
			if (super.saveClickX >= 5 && super.saveClickX <= 61
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 0) {
						cButtonCPos = 0;
						chatTypeView = 0;
						updateChatbox = true;
						setChannel = 0;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 0;
					chatTypeView = 0;
					updateChatbox = true;
					setChannel = 0;
				}
			} else if (super.saveClickX >= 69 && super.saveClickX <= 125
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 1 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 1;
						chatTypeView = 5;
						updateChatbox = true;
						setChannel = 1;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 1;
					chatTypeView = 5;
					updateChatbox = true;
					setChannel = 1;
				}
			} else if (super.saveClickX >= 133 && super.saveClickX <= 189
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 2 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 2;
						chatTypeView = 1;
						updateChatbox = true;
						setChannel = 2;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 2;
					chatTypeView = 1;
					updateChatbox = true;
					setChannel = 2;
				}
			} else if (super.saveClickX >= 197 && super.saveClickX <= 253
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 3 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 3;
						chatTypeView = 2;
						updateChatbox = true;
						setChannel = 3;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 3;
					chatTypeView = 2;
					updateChatbox = true;
					setChannel = 3;
				}
			} else if (super.saveClickX >= 261 && super.saveClickX <= 317
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 4 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 4;
						chatTypeView = 11;
						updateChatbox = true;
						setChannel = 4;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 4;
					chatTypeView = 11;
					updateChatbox = true;
					setChannel = 4;
				}
			} else if (super.saveClickX >= 325 && super.saveClickX <= 381
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 5 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 5;
						chatTypeView = 3;
						updateChatbox = true;
						setChannel = 5;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 5;
					chatTypeView = 3;
					updateChatbox = true;
					setChannel = 5;
				}
			} else if (super.saveClickX >= 389 && super.saveClickX <= 445
					&& super.saveClickY >= yOffset + 482
					&& super.saveClickY <= yOffset + 505) {
				if (frameMode != ScreenMode.FIXED) {
					if (setChannel != 6 && frameMode != ScreenMode.FIXED) {
						cButtonCPos = 6;
						chatTypeView = 12;
						updateChatbox = true;
						setChannel = 6;
					} else {
						showChatComponents = showChatComponents ? false : true;
					}
				} else {
					cButtonCPos = 6;
					chatTypeView = 12;
					updateChatbox = true;
					setChannel = 6;
				}
			}
		}
	}

	public void method33(int i)
	{
		int j = Varp.cache[i].anInt709;
		if(j == 0)
			return;
		int k = variousSettings[i];
		if(j == 1)
		{
			if(k == 1)
				Rasterizer3D.setBrightness(0.90000000000000002D);
			if(k == 2)
				Rasterizer3D.setBrightness(0.80000000000000004D);
			if(k == 3)
				Rasterizer3D.setBrightness(0.69999999999999996D);
			if(k == 4)
				Rasterizer3D.setBrightness(0.59999999999999998D);
			ItemDefinition.sprites.unlinkAll();
			welcomeScreenRaised = true;
		}
			if(j == 3)
		{
			boolean flag1 = musicEnabled;
			if (k == 0) {
				if (signlink.music != null)
				adjustVolume(musicEnabled, 500);
				musicEnabled = true;
			}
			if (k == 1) {
				if (signlink.music != null)
				adjustVolume(musicEnabled, 300);
				musicEnabled = true;
			}
			if (k == 2) {
				if (signlink.music != null)
				adjustVolume(musicEnabled, 100);
				musicEnabled = true;
			}
			if (k == 3) {
				if (signlink.music != null)
				adjustVolume(musicEnabled, 0);
				musicEnabled = true;
			}
			if(k == 4)
				musicEnabled = false;
			if (musicEnabled != flag1 && !lowMem) {
				if (musicEnabled) {
					nextSong = currentSong;
					songChanging = true;
					resourceProvider.provide(2, nextSong);
				} else {
					stopMidi();
				}
				prevSong = 0;
			}
		}
		if (j == 4) {
			SoundPlayer.setVolume(k);
			if (k == 0) {
				aBoolean848 = true;
				setWaveVolume(0);
			}
			if (k == 1) {
				aBoolean848 = true;
				setWaveVolume(-400);
			}
			if (k == 2) {
				aBoolean848 = true;
				setWaveVolume(-800);
			}
			if (k == 3) {
				aBoolean848 = true;
				setWaveVolume(-1200);
			}
			if (k == 4) {
				aBoolean848 = false;
			}
		}
		if(j == 5)
			anInt1253 = k;
		if(j == 6)
			anInt1249 = k;
		if(j == 8)
		{
			splitPrivateChat = k;
			updateChatbox = true;
		}
		if(j == 9)
			anInt913 = k;
	}

	public void updateEntities() {
		try{
			int anInt974 = 0;
			for(int j = -1; j < playerCount + npcCount; j++) {
			Object obj;
			if(j == -1)
				obj = localPlayer;
			else
			if(j < playerCount)
				obj = players[playerList[j]];
			else
				obj = npcs[npcIndices[j - playerCount]];
			if(obj == null || !((Mob) (obj)).isVisible())
				continue;
			if(obj instanceof Npc) {
				NpcDefinition npcDefinition = ((Npc)obj).desc;
				if(npcDefinition.childrenIDs != null)
					npcDefinition = npcDefinition.morph();
				if(npcDefinition == null)
					continue;
			}
			if(j < playerCount) {
				int l = 30;
				Player player = (Player)obj;
				if(player.headIcon >= 0) {
					npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height + 15);
					if(spriteDrawX > -1) {
						if (player.skullIcon < 2) {
							skullIcons[player.skullIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
							l += 25;
						}
						if (player.headIcon < 7) {
							headIcons[player.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
							l += 18;
						}
					}
				}
				if(j >= 0 && hintIconDrawType == 10 && hintIconPlayerId == playerList[j]) {
					npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height + 15);
					if(spriteDrawX > -1)
						headIconsHint[player.hintIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
				}
			} else {
				NpcDefinition npcDefinition_1 = ((Npc)obj).desc;
				if(npcDefinition_1.headIcon >= 0 && npcDefinition_1.headIcon < headIcons.length) {
					npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height + 15);
					if(spriteDrawX > -1)
						headIcons[npcDefinition_1.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
				}
				if(hintIconDrawType == 1 && hintIconNpcId == npcIndices[j - playerCount] && tick % 20 < 10) {
					npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height + 15);
					if(spriteDrawX > -1)
						headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
				}
			}
			if(((Mob) (obj)).textSpoken != null && (j >= playerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player)obj).name)))
			{
				npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height);
				if(spriteDrawX > -1 && anInt974 < anInt975)
				{
					anIntArray979[anInt974] = boldText.method384(((Mob) (obj)).textSpoken) / 2;
					anIntArray978[anInt974] = boldText.verticalSpace;
					anIntArray976[anInt974] = spriteDrawX;
					anIntArray977[anInt974] = spriteDrawY;
					anIntArray980[anInt974] = ((Mob) (obj)).anInt1513;
					anIntArray981[anInt974] = ((Mob) (obj)).anInt1531;
					anIntArray982[anInt974] = ((Mob) (obj)).textCycle;
					aStringArray983[anInt974++] = ((Mob) (obj)).textSpoken;
					if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 >= 1 && ((Mob) (obj)).anInt1531 <= 3)
					{
						anIntArray978[anInt974] += 10;
						anIntArray977[anInt974] += 5;
					}
					if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 == 4)
						anIntArray979[anInt974] = 60;
					if(anInt1249 == 0 && ((Mob) (obj)).anInt1531 == 5)
						anIntArray978[anInt974] += 5;
				}
			}

				if (obj instanceof Npc) {
					Npc npc = ((Npc) obj);
					if (localPlayer.interactingEntity == -1) {

						//Is the npc interacting with us?
						//If we aren't interacting with others,
						//Start combat box timer.
						if ((npc.interactingEntity - 32768) == localPlayerIndex) {
							currentInteract = npc;
							combatBoxTimer.start(10);
						}

					} else {

						//Are we interacting with the npc?
						//Start combat box timer.
						if (npc.index == localPlayer.interactingEntity) {
							currentInteract = npc;
							combatBoxTimer.start(10);
						}
					}
				} else if (obj instanceof Player) {
					Player player = ((Player) obj);
					if (localPlayer.interactingEntity == -1) {

						//Is the player interacting with us?
						//If we aren't interacting with others,
						//Start combat box timer.
						if ((player.interactingEntity - 32768) == localPlayerIndex) {
							currentInteract = player;
							combatBoxTimer.start(10);
						}

					} else {
						//Are we interacting with the player?
						//Start combat box timer.
						if (player.index == localPlayer.interactingEntity - 32768) {
							currentInteract = player;
							combatBoxTimer.start(10);
						}
					}
				}

			if(((Mob) (obj)).loopCycleStatus > tick)
			{
				try{
					npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height + 15);
					if(spriteDrawX > -1)
					{
						int i1 = (((Mob) (obj)).currentHealth * 30) / ((Mob) (obj)).maxHealth;
						if(i1 > 30)
							i1 = 30;
						Rasterizer2D.drawPixels(5, spriteDrawY - 3, spriteDrawX - 15, 65280, i1);
						Rasterizer2D.drawPixels(5, spriteDrawY - 3, (spriteDrawX - 15) + i1, 0xff0000, 30 - i1);
					}
				}catch(Exception e){ }
				}
				for(int j1 = 0; j1 < 4; j1++)
					if(((Mob) (obj)).hitsLoopCycle[j1] > tick)
					{
						npcScreenPos(((Mob) (obj)), ((Mob) (obj)).height / 2);
						if(spriteDrawX > -1)
						{
							if(j1 == 1)
								spriteDrawY -= 20;
							if(j1 == 2)
							{
								spriteDrawX -= 15;
								spriteDrawY -= 10;
							}
							if(j1 == 3)
							{
								spriteDrawX += 15;
								spriteDrawY -= 10;
							}
							hitMarks[((Mob) (obj)).hitMarkTypes[j1]].drawSprite(spriteDrawX - 12, spriteDrawY - 12);
							smallText.drawText(0, String.valueOf(((Mob) (obj)).hitDamages[j1]), spriteDrawY + 4, spriteDrawX);
							smallText.drawText(0xffffff, String.valueOf(((Mob) (obj)).hitDamages[j1]), spriteDrawY + 3, spriteDrawX - 1);
						}
					}
			}
			for(int k = 0; k < anInt974; k++) {
				int k1 = anIntArray976[k];
				int l1 = anIntArray977[k];
				int j2 = anIntArray979[k];
				int k2 = anIntArray978[k];
				boolean flag = true;
				while(flag) 
				{
					flag = false;
					for(int l2 = 0; l2 < k; l2++)
						if(l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1)
						{
							l1 = anIntArray977[l2] - anIntArray978[l2];
							flag = true;
						}

				}
				spriteDrawX = anIntArray976[k];
				spriteDrawY = anIntArray977[k] = l1;
				String s = aStringArray983[k];
				if(anInt1249 == 0)
				{
					int i3 = 0xffff00;
					if(anIntArray980[k] < 6)
						i3 = anIntArray965[anIntArray980[k]];
					if(anIntArray980[k] == 6)
						i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
					if(anIntArray980[k] == 7)
						i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
					if(anIntArray980[k] == 8)
						i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
					if(anIntArray980[k] == 9) {
						int j3 = 150 - anIntArray982[k];
						if(j3 < 50)
							i3 = 0xff0000 + 1280 * j3;
						else
						if(j3 < 100)
							i3 = 0xffff00 - 0x50000 * (j3 - 50);
						else
						if(j3 < 150)
							i3 = 65280 + 5 * (j3 - 100);
					}
					if(anIntArray980[k] == 10) {
						int k3 = 150 - anIntArray982[k];
						if(k3 < 50)
							i3 = 0xff0000 + 5 * k3;
						else
						if(k3 < 100)
							i3 = 0xff00ff - 0x50000 * (k3 - 50);
						else
						if(k3 < 150)
							i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
					}
					if(anIntArray980[k] == 11) {
						int l3 = 150 - anIntArray982[k];
						if(l3 < 50)
							i3 = 0xffffff - 0x50005 * l3;
						else
						if(l3 < 100)
							i3 = 65280 + 0x50005 * (l3 - 50);
						else
						if(l3 < 150)
							i3 = 0xffffff - 0x50000 * (l3 - 100);
					}
					if(anIntArray981[k] == 0) {
						boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX);
						boldText.drawText(i3, s, spriteDrawY, spriteDrawX);
					}
					if(anIntArray981[k] == 1) {
						boldText.method386(0, s, spriteDrawX, anInt1265, spriteDrawY + 1);
						boldText.method386(i3, s, spriteDrawX, anInt1265, spriteDrawY);
					}
					if(anIntArray981[k] == 2) {
						boldText.method387(spriteDrawX, s, anInt1265, spriteDrawY + 1, 0);
						boldText.method387(spriteDrawX, s, anInt1265, spriteDrawY, i3);
					}
					if(anIntArray981[k] == 3) {
						boldText.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY + 1, spriteDrawX, 0);
						boldText.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY, spriteDrawX, i3);
					}
					if(anIntArray981[k] == 4) {
						int i4 = boldText.method384(s);
						int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
						Rasterizer2D.setDrawingArea(334, spriteDrawX - 50, spriteDrawX + 50, 0);
						boldText.render(0, s, spriteDrawY + 1, (spriteDrawX + 50) - k4);
						boldText.render(i3, s, spriteDrawY, (spriteDrawX + 50) - k4);
						Rasterizer2D.defaultDrawingAreaSize();
					}
					if(anIntArray981[k] == 5) {
						int j4 = 150 - anIntArray982[k];
						int l4 = 0;
						if(j4 < 25)
							l4 = j4 - 25;
						else
						if(j4 > 125)
							l4 = j4 - 125;
						Rasterizer2D.setDrawingArea(spriteDrawY + 5, 0, 512, spriteDrawY - boldText.verticalSpace - 1);
						boldText.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX);
						boldText.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
						Rasterizer2D.defaultDrawingAreaSize();
					}
				} else {
					boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX);
					boldText.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
				}
			}
		} catch(Exception e){ }
	}

	public void delFriend(long l)
	{
		try
		{
			if(l == 0L)
				return;
			for(int i = 0; i < friendsCount; i++)
			{
				if(friendsListAsLongs[i] != l)
					continue;
				friendsCount--;
				for(int j = i; j < friendsCount; j++)
				{
					friendsList[j] = friendsList[j + 1];
					friendsNodeIDs[j] = friendsNodeIDs[j + 1];
					friendsListAsLongs[j] = friendsListAsLongs[j + 1];
				}

				buffer.createFrame(215);
				buffer.writeQWord(l);
				break;
			}
		}
		catch(RuntimeException runtimeexception)
		{
			signlink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void drawSideIcons() {
		// local variables will not stay in memory which will help with performance.
		int[] sideIconsX = new int[0];
		int[] sideIconsY = new int[0];
		int[] sideIconsId;
		int[] sideIconsTab;
		int[] iconId = new int[0];
		int[] iconX = new int[0];
		int[] iconY = new int[0];
		sideIconsId = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		sideIconsTab = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		if (Configuration.skin.equals(Configuration.Skin.AROUND_2005)) {
			if (frameMode == ScreenMode.FIXED) {
				sideIconsX = new int[]{33, 55, 86, 114, 156, 184, 213, 29, 56, 85, 118, 156, 187, 213};
				sideIconsY = new int[]{10, 7, 9, 5, 7, 6, 9, 303, 304, 304, 306, 303, 303, 303, 303};
			} else {
				sideIconsX = new int[]{17, 49, 83, 112, 147, 180, 214, 14, 49, 82, 114, 148, 184, 216};
				sideIconsY = new int[]{9, 7, 7, 5, 5, 4, 6, 304, 305, 305, 304, 303, 303, 304, 303};
			}
		}
		if (Configuration.skin.equals(Configuration.Skin.AROUND_2006)) {
			sideIconsX = new int[]{17, 48, 83, 112, 147, 180, 214, 16, 49, 82, 116, 148, 184, 216};
			sideIconsY = new int[]{8, 5, 7, 5, 4, 4, 6, 303, 306, 306, 302, 305, 303, 303, 303};
		}
		if (Configuration.skin.equals(Configuration.Skin.STANDARD)) {
			sideIconsX = new int[]{13, 46, 79, 114, 144, 177, 212, 16, 49, 82, 116, 148, 184, 216};
			sideIconsY = new int[]{4, 5, 4, 5, 2, 1, 4, 303, 306, 306, 302, 305, 303, 303, 303};
		}
		int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 247;
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED || frameMode != ScreenMode.FIXED && !stackSideStones) {
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (sideIconsId[i] != -1) {
						if (Configuration.skin.equals(Configuration.Skin.AROUND_2005) || Configuration.skin.equals(Configuration.Skin.AROUND_2006))
							sideIcons2[sideIconsId[i]].drawSprite(sideIconsX[i] + xOffset,sideIconsY[i] + yOffset);
						else
							sideIcons[sideIconsId[i]].drawSprite(sideIconsX[i] + xOffset,sideIconsY[i] + yOffset);

					}
				}
			}
		} else if (stackSideStones && frameWidth < 1000) {
			iconId = new int[]{0, 1, 2, 3, 4, 5, 6, -1, 8, 9, 7, 11, 12, 13};
			iconX = new int[]{219, 189, 156, 126, 94, 62, 30, 219, 189, 156, 124, 92, 59, 28};
			iconY = new int[]{67, 69, 67, 69, 72, 72, 69, 32, 29, 29, 32, 30, 33, 31, 32};
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (iconId[i] != -1) {
						if (Configuration.skin.equals(Configuration.Skin.AROUND_2005) || Configuration.skin.equals(Configuration.Skin.AROUND_2006))
							sideIcons2[iconId[i]].drawSprite(frameWidth - iconX[i],frameHeight - iconY[i]);
						else
							sideIcons[iconId[i]].drawSprite(frameWidth - iconX[i],frameHeight - iconY[i]);
					}
				}
			}
		} else if (stackSideStones && frameWidth >= 1000) {
			iconId = new int[]{0, 1, 2, 3, 4, 5, 6, -1, 8, 9, 7, 11, 12, 13};
			iconX = new int[]{50, 80, 114, 143, 176, 208, 240, 242, 273, 306, 338, 370, 404, 433};
			iconY = new int[]{30, 32, 30, 32, 34, 34, 32, 32, 29, 29, 32, 31, 32, 32, 32};
			for (int i = 0; i < sideIconsTab.length; i++) {
				if (tabInterfaceIDs[sideIconsTab[i]] != -1) {
					if (iconId[i] != -1) {
						if (Configuration.skin.equals(Configuration.Skin.AROUND_2005) || Configuration.skin.equals(Configuration.Skin.AROUND_2006))
							sideIcons2[iconId[i]].drawSprite(frameWidth - 461 + iconX[i],frameHeight - iconY[i]);
						else
							sideIcons[iconId[i]].drawSprite(frameWidth - 461 + iconX[i],frameHeight - iconY[i]);
					}
				}
			}
		}
	}

	private void drawRedStones() {

		int[] redStonesX = new int[0];
		int[] redStonesY = new int[0];
		int[] redStonesId = new int[0];
		if (frameMode == ScreenMode.FIXED) {
			if (Configuration.skin.equals(Configuration.Skin.AROUND_2005)) {
				redStonesX = new int[]{
						21, 52, 80, 110, 154, 183, 209,
						21, 52, 80, 110, 154, 183, 209};
				redStonesY = new int[]{
						0, 0, 0, 0, 0, 0, 0,
						298, 298, 298, 298, 298, 298, 298};
				redStonesId = new int[]{
						63, 68, 68, 65, 70, 70, 64,
						66, 71, 71, 69, 72, 72, 67};
			}
			if (Configuration.skin.equals(Configuration.Skin.AROUND_2006)) {
				redStonesX = new int[]{6, 44, 77, 110, 143, 176, 209, 6, 44, 77, 110, 143, 176, 209};
				redStonesY = new int[]{0, 0, 0, 0, 0, 0, 0, 298, 298, 298, 298, 298, 298, 298};
				redStonesId = new int[]{35, 39, 39, 39, 39, 39, 36, 37, 39, 39, 39, 39, 39, 38};
			}
			if (Configuration.skin.equals(Configuration.Skin.STANDARD)) {
				redStonesX = new int[]{6, 44, 77, 110, 143, 176, 209, 6, 44, 77, 110, 143, 176, 209};
				redStonesY = new int[]{0, 0, 0, 0, 0, 0, 0, 298, 298, 298, 298, 298, 298, 298};
				redStonesId = new int[]{35, 39, 39, 39, 39, 39, 36, 37, 39, 39, 39, 39, 39, 38};
			}
		} else {
			redStonesX = new int[]{6, 44, 77, 110, 143, 176, 209, 6, 44, 77, 110, 143, 176, 209};
			redStonesY = new int[]{0, 0, 0, 0, 0, 0, 0, 298, 298, 298, 298, 298, 298, 298};
			redStonesId = new int[]{35, 39, 39, 39, 39, 39, 36, 37, 39, 39, 39, 39, 39, 38};
		}

		int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 247;
		int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED || frameMode != ScreenMode.FIXED && !stackSideStones) {
			if (tabInterfaceIDs[tabId] != -1 && tabId != 15) {
				spriteCache.draw(redStonesId[tabId], redStonesX[tabId] + xOffset,
						redStonesY[tabId] + yOffset);
			}
		} else if (stackSideStones && frameWidth < 1000) {
			int[] stoneX = {226, 194, 162, 130, 99, 65, 34, 219, 195, 161, 130, 98, 65, 33};
			int[] stoneY = {73, 73, 73, 73, 73, 73, 73, -1, 37, 37, 37, 37, 37, 37, 37};
			if (tabInterfaceIDs[tabId] != -1 && tabId != 10 && showTabComponents) {
				if (tabId == 7) {
					spriteCache.draw(39, frameWidth - 130, frameHeight - 37);
				}
				spriteCache.draw(39, frameWidth - stoneX[tabId],
						frameHeight - stoneY[tabId]);
			}
		} else if (stackSideStones && frameWidth >= 1000) {
			int[] stoneX =
					{417, 385, 353, 321, 289, 256, 224, 129, 193, 161, 130, 98, 65, 33};
			if (tabInterfaceIDs[tabId] != -1 && tabId != 10 && showTabComponents) {
				spriteCache.draw(39, frameWidth - stoneX[tabId], frameHeight - 37);
			}
		}
	}

	private void drawTabArea() {
		final int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 241;
		final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 336;
		if (frameMode == ScreenMode.FIXED) {
			tabImageProducer.initDrawingArea();
		}
		Rasterizer3D.scanOffsets = anIntArray1181;
		if (frameMode == ScreenMode.FIXED) {
			spriteCache.draw(Configuration.skin.equals(Configuration.Skin.AROUND_2005) ? 62 : Configuration.skin.equals(Configuration.Skin.AROUND_2006) ? 652 : 21, 0, 0);
		} else if (frameMode != ScreenMode.FIXED && !stackSideStones) {
			Rasterizer2D.drawTransparentBox(frameWidth - 217, frameHeight - 304, 195, 270, 0x3E3529, transparentTabArea ? 80 : 256);
			spriteCache.draw(47, xOffset, yOffset);
		} else {
			if (frameWidth >= 1000) {
				if (showTabComponents) {
					Rasterizer2D.drawTransparentBox(frameWidth - 197, frameHeight - 304, 197, 265, 0x3E3529, transparentTabArea ? 80 : 256);
					spriteCache.draw(50, frameWidth - 204, frameHeight - 311);
				}
				for (int x = frameWidth - 417, y = frameHeight - 37, index = 0; x <= frameWidth - 30 && index < 13; x += 32, index++) {
					spriteCache.draw(46, x, y);
				}
			} else if (frameWidth < 1000) {
				if (showTabComponents) {
					Rasterizer2D.drawTransparentBox(frameWidth - 197, frameHeight - 341, 195, 265, 0x3E3529, transparentTabArea ? 80 : 256);
					spriteCache.draw(50, frameWidth - 204, frameHeight - 348);
				}
				for (int x = frameWidth - 226, y = frameHeight - 73, index = 0; x <= frameWidth - 32 && index < 7; x += 32, index++) {
					spriteCache.draw(46, x, y);
				}
				for (int x = frameWidth - 226, y = frameHeight - 37, index = 0; x <= frameWidth - 32 && index < 7; x += 32, index++) {
					spriteCache.draw(46, x, y);
				}
			}
		}
		if (overlayInterfaceId == -1) {
			drawRedStones();
			drawSideIcons();
		}
		if (showTabComponents) {
			int x = frameMode == ScreenMode.FIXED ? Configuration.skin.equals(Configuration.Skin.AROUND_2005) ? 37 : 31 : frameWidth - 215;
			int y = frameMode == ScreenMode.FIXED ? 37 : frameHeight - 299;
			if (stackSideStones) {
				x = frameWidth - 197;
				y = frameWidth >= 1000 ? frameHeight - 303 : frameHeight - 340;
			}
			try {
				if (overlayInterfaceId != -1) {
					drawInterface(0, x, Widget.interfaceCache[overlayInterfaceId], y);
				} else if (tabInterfaceIDs[tabId] != -1) {
					drawInterface(0, x, Widget.interfaceCache[tabInterfaceIDs[tabId]], y);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (menuOpen) {
			drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, frameMode == ScreenMode.FIXED ? 168 : 0);
		} else {
			drawHoverMenu(frameMode == ScreenMode.FIXED ? 516 : 0, frameMode == ScreenMode.FIXED ? 168 : 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			tabImageProducer.drawGraphics(168, super.graphics, 516);
			gameScreenImageProducer.initDrawingArea();
		}
		Rasterizer3D.scanOffsets = anIntArray1182;
	}

	public void writeBackgroundTextures(int j) {
		if(!lowMem) {
			if(Rasterizer3D.textureLastUsed[17] >= j) {
				IndexedImage indexedImage = Rasterizer3D.textures[17];
				int k = indexedImage.width * indexedImage.height - 1;
				int j1 = indexedImage.width * tickDelta * 2;
				byte abyte0[] = indexedImage.palettePixels;
				byte abyte3[] = aByteArray912;
				for(int i2 = 0; i2 <= k; i2++)
					abyte3[i2] = abyte0[i2 - j1 & k];

				indexedImage.palettePixels = abyte3;
				aByteArray912 = abyte0;
				Rasterizer3D.requestTextureUpdate(17);
				anInt854++;
				if(anInt854 > 1235) {
					anInt854 = 0;
					buffer.createFrame(226);
					buffer.writeWordBigEndian(0);
					int l2 = buffer.currentPosition;
					buffer.writeWord(58722);
					buffer.writeWordBigEndian(240);
					buffer.writeWord((int)(Math.random() * 65536D));
					buffer.writeWordBigEndian((int)(Math.random() * 256D));
					if((int)(Math.random() * 2D) == 0)
						buffer.writeWord(51825);
					buffer.writeWordBigEndian((int)(Math.random() * 256D));
					buffer.writeWord((int)(Math.random() * 65536D));
					buffer.writeWord(7130);
					buffer.writeWord((int)(Math.random() * 65536D));
					buffer.writeWord(61657);
					buffer.writeBytes(buffer.currentPosition - l2);
				}
			}
			if(Rasterizer3D.textureLastUsed[24] >= j) {
				IndexedImage indexedImage_1 = Rasterizer3D.textures[24];
				int l = indexedImage_1.width * indexedImage_1.height - 1;
				int k1 = indexedImage_1.width * tickDelta * 2;
				byte abyte1[] = indexedImage_1.palettePixels;
				byte abyte4[] = aByteArray912;
				for(int j2 = 0; j2 <= l; j2++)
					abyte4[j2] = abyte1[j2 - k1 & l];

				indexedImage_1.palettePixels = abyte4;
				aByteArray912 = abyte1;
				Rasterizer3D.requestTextureUpdate(24);
			}
			if(Rasterizer3D.textureLastUsed[34] >= j) {
				IndexedImage indexedImage_2 = Rasterizer3D.textures[34];
				int i1 = indexedImage_2.width * indexedImage_2.height - 1;
				int l1 = indexedImage_2.width * tickDelta * 2;
				byte abyte2[] = indexedImage_2.palettePixels;
				byte abyte5[] = aByteArray912;
				for(int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				indexedImage_2.palettePixels = abyte5;
				aByteArray912 = abyte2;
				Rasterizer3D.requestTextureUpdate(34);
			}
			if(Rasterizer3D.textureLastUsed[40] >= j);
			{
				IndexedImage indexedImage_2 = Rasterizer3D.textures[40];
				int i1 = indexedImage_2.width * indexedImage_2.height - 1;
				int l1 = indexedImage_2.width * tickDelta * 2;
				byte abyte2[] = indexedImage_2.palettePixels;
				byte abyte5[] = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				indexedImage_2.palettePixels = abyte5;
				aByteArray912 = abyte2;
				Rasterizer3D.requestTextureUpdate(40);
			}
		}
	}

	public void method38() {
		for(int i = -1; i < playerCount; i++) {
			int j;
			if(i == -1)
				j = internalLocalPlayerIndex;
			else
				j = playerList[i];
			Player player = players[j];
			if(player != null && player.textCycle > 0) {
				player.textCycle--;
				if(player.textCycle == 0)
					player.textSpoken = null;
			}
		}
		for(int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			Npc npc = npcs[l];
			if(npc != null && npc.textCycle > 0) {
				npc.textCycle--;
				if(npc.textCycle == 0)
					npc.textSpoken = null;
			}
		}
	}

	public void calcCameraPos() {
		int i = anInt1098 * 128 + 64;
		int j = anInt1099 * 128 + 64;
		int k = getCenterHeight(plane, j, i) - anInt1100;
		if(xCameraPos < i) {
			xCameraPos += anInt1101 + ((i - xCameraPos) * anInt1102) / 1000;
			if(xCameraPos > i)
				xCameraPos = i;
		}
		if(xCameraPos > i) {
			xCameraPos -= anInt1101 + ((xCameraPos - i) * anInt1102) / 1000;
			if(xCameraPos < i)
				xCameraPos = i;
		}
		if(zCameraPos < k) {
			zCameraPos += anInt1101 + ((k - zCameraPos) * anInt1102) / 1000;
			if(zCameraPos > k)
				zCameraPos = k;
		}
		if(zCameraPos > k) {
			zCameraPos -= anInt1101 + ((zCameraPos - k) * anInt1102) / 1000;
			if(zCameraPos < k)
				zCameraPos = k;
		}
		if(yCameraPos < j) {
			yCameraPos += anInt1101 + ((j - yCameraPos) * anInt1102) / 1000;
			if(yCameraPos > j)
				yCameraPos = j;
		}
		if(yCameraPos > j) {
			yCameraPos -= anInt1101 + ((yCameraPos - j) * anInt1102) / 1000;
			if(yCameraPos < j)
				yCameraPos = j;
		}
		i = anInt995 * 128 + 64;
		j = anInt996 * 128 + 64;
		k = getCenterHeight(plane, j, i) - anInt997;
		int l = i - xCameraPos;
		int i1 = k - zCameraPos;
		int j1 = j - yCameraPos;
		int k1 = (int)Math.sqrt(l * l + j1 * j1);
		int l1 = (int)(Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
		int i2 = (int)(Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
		if(l1 < 128)
			l1 = 128;
		if(l1 > 383)
			l1 = 383;
		if(yCameraCurve < l1) {
			yCameraCurve += anInt998 + ((l1 - yCameraCurve) * anInt999) / 1000;
			if(yCameraCurve > l1)
				yCameraCurve = l1;
		}
		if(yCameraCurve > l1) {
			yCameraCurve -= anInt998 + ((yCameraCurve - l1) * anInt999) / 1000;
			if(yCameraCurve < l1)
				yCameraCurve = l1;
		}
		int j2 = i2 - xCameraCurve;
		if(j2 > 1024)
			j2 -= 2048;
		if(j2 < -1024)
			j2 += 2048;
		if(j2 > 0) {
			xCameraCurve += anInt998 + (j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		if(j2 < 0) {
			xCameraCurve -= anInt998 + (-j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		int k2 = i2 - xCameraCurve;
		if(k2 > 1024)
			k2 -= 2048;
		if(k2 < -1024)
			k2 += 2048;
		if(k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0)
			xCameraCurve = i2;
	}

	private boolean hoverMenuActive;

	private void drawHoverMenu(int x, int y) {
		boolean active = hoverMenuActive;
		if (Configuration.enableTooltipHovers && menuActionRow > 0 && super.mouseX >= 0 && super.mouseY >= 0) {
			buildHoverMenu(x, y);
		} else {
			hoverMenuActive = false;
		}
		if (active != hoverMenuActive) {
			updateChatbox = true;
		}
	}

	private void buildHoverMenu(int x, int y) {
		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
			hoverMenuActive = false;
			return;
		} else if (itemSelected == 1 && menuActionRow < 2) {
			hoverMenuActive = false;
			return;
		} else if (spellSelected != 0 && menuActionRow < 2) {
			hoverMenuActive = false;
			return;
		}

		String text = menuActionText[menuActionRow - 1];
		if (text.contains("Walk here") || text.isEmpty() || text.equalsIgnoreCase(" ")) {
			hoverMenuActive = false;
			return;
		}

		GameFont font = smallText;

		int drawX = super.mouseX + 10;
		int drawY = super.mouseY;
		int width = font.getTextWidth(text) + 7;
		int height = font.verticalSpace + 8;
		if (drawX < frameWidth && drawY < frameHeight) {
			if (drawX + width + 3 > frameWidth) {
				drawX = frameWidth - width - 3;
			}
			if (drawY + height + 3 > frameHeight) {
				drawY = frameHeight - height - 3;
			}
		}
		drawX -= x;
		drawY -= y;
		Rasterizer2D.fillRectangle(drawX, drawY, width, height, 0x534B40, 250);
		Rasterizer2D.drawBoxOutline(drawX, drawY, width, height, 0x383023);
		int textY = drawY + font.verticalSpace + 4;
		font.drawTextWithPotentialShadow(true, drawX + 3, 0xffffff, text, textY);
		hoverMenuActive = true;
	}

	private void drawTooltip() {
		if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
			return;
		String s;
		if (itemSelected == 1 && menuActionRow < 2)
			s = "Use " + selectedItemName + " with...";
		else if (spellSelected == 1 && menuActionRow < 2)
			s = spellTooltip + "...";
		else
			s = menuActionText[menuActionRow - 1];
		if (menuActionRow > 2)
			s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
		boldText.method390(4, 0xffffff, s, tick / 1000, 15);
	}

	public void drawMenu(int x, int y) {
		int xPos = menuOffsetX - (x - 4);
		int yPos = (-y + 4) + menuOffsetY;
		int w = menuWidth;
		int h = menuHeight + 1;
		updateChatbox = true;
		tabAreaAltered = true;
		int menuColor = 0x5d5447;
		Rasterizer2D.drawBox(xPos, yPos, w, h, menuColor);
		Rasterizer2D.drawBox(xPos + 1, yPos + 1, w - 2, 16, 0);
		Rasterizer2D.drawBoxOutline(xPos + 1, yPos + 18, w - 2, h - 19, 0);
		boldText.render(menuColor, "Choose Option", yPos + 14, xPos + 3);
		int mouseX = super.mouseX - (x);
		int mouseY = (-y) + super.mouseY;
		for (int i = 0; i < menuActionRow; i++) {
			int textY = yPos + 31 + (menuActionRow - 1 - i) * 15;
			int textColor = 0xffffff;
			if (mouseX > xPos && mouseX < xPos + w && mouseY > textY - 13
					&& mouseY < textY + 3) {
				textColor = 0xffff00;
			}
			boldText.drawTextWithPotentialShadow(true, xPos + 3, textColor, menuActionText[i],
					textY);
		}
	}

	public void addFriend(long l) {
		try {
			if(l == 0L)
				return;
			if(friendsCount >= 100 && anInt1046 >= 1) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for donators.", 0, "");
				return;
			}
			if(friendsCount >= 200) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for donators.", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			for(int i = 0; i < friendsCount; i++)
				if(friendsListAsLongs[i] == l) {
					pushMessage(s + " is already on your friend list", 0, "");
					return;
				}
			for(int j = 0; j < ignoreCount; j++)
				if(ignoreListAsLongs[j] == l) {
					pushMessage("Please remove " + s + " from your ignore list first", 0, "");
					return;
				}

			if(s.equals(localPlayer.name)) {
				return;
			} else {
				friendsList[friendsCount] = s;
				friendsListAsLongs[friendsCount] = l;
				friendsNodeIDs[friendsCount] = 0;
				friendsCount++;
				buffer.createFrame(188);
				buffer.writeQWord(l);
				return;
			}
		} catch(RuntimeException runtimeexception) {
			signlink.reporterror("15283, " + (byte)68 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private int getCenterHeight(int i, int j, int k) {
		int l = k >> 7;
		int i1 = j >> 7;
		if(l < 0 || i1 < 0 || l > 103 || i1 > 103)
			return 0;
		int j1 = i;
		if(j1 < 3 && (tileFlags[1][l][i1] & 2) == 2)
			j1++;
		int k1 = k & 0x7f;
		int l1 = j & 0x7f;
		int i2 = tileHeights[j1][l][i1] * (128 - k1) + tileHeights[j1][l + 1][i1] * k1 >> 7;
		int j2 = tileHeights[j1][l][i1 + 1] * (128 - k1) + tileHeights[j1][l + 1][i1 + 1] * k1 >> 7;
		return i2 * (128 - l1) + j2 * l1 >> 7;
	}

	private static String intToKOrMil(int j) {
		if(j == 0)
			return "@inf@";
		if(j < 0x186a0)
			return String.valueOf(j);
		if(j < 0x989680)
			return j / 1000 + "K";
		else
			return j / 0xf4240 + "M";
	}

	public void resetLogout() {
		try {
			if(socketStream != null)
				socketStream.close();
		}
		catch(Exception _ex) { }
		socketStream = null;
		loggedIn = false;
		loginScreenState = 0;
		clickedQuickPrayers = false;
		writeSettings();
		unlinkMRUNodes();
		scene.initToNull();
		for(int i = 0; i < 4; i++)
			collisionMaps[i].setDefault();
		System.gc();
		stopMidi();
		currentSong = -1;
		nextSong = -1;
		prevSong = 0;
		frameMode(ScreenMode.FIXED);
		if (signlink.musicOn) {
			playMusicButton();
		}
	}

	public void changeCharacterGender() {
		aBoolean1031 = true;
		for(int j = 0; j < 7; j++) {
			anIntArray1065[j] = -1;
			for(int k = 0; k < IdentityKits.length; k++) {
				if(IdentityKits.kits[k].validStyle || IdentityKits.kits[k].bodyPartId != j + (maleCharacter ? 0 : 7))
					continue;
				anIntArray1065[j] = k;
				break;
			}
		}
	}
	
	public void tradePlayer(int index) {
		if (loggedIn) {
			buffer.createFrame(39);
			//item_data.writeWord(index);
			buffer.method431(index);
		}
	}

	public void updateNPCMovement(int i, Buffer buffer) {
		while(buffer.bitPosition + 21 < i * 8) {
			int k = buffer.readBits(14);
			if(k == 16383)
				break;
			if(npcs[k] == null)
				npcs[k] = new Npc();
			Npc npc = npcs[k];
			npcIndices[npcCount++] = k;
			npc.time = tick;
			int l = buffer.readBits(5);
			if(l > 15)
				l -= 32;
			int i1 = buffer.readBits(5);
			if(i1 > 15)
				i1 -= 32;
			int j1 = buffer.readBits(1);
			npc.desc = NpcDefinition.forID(buffer.readBits(14));
			int k1 = buffer.readBits(1);
			if(k1 == 1)
				mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = k;
			npc.size = npc.desc.size;
			npc.degreesToTurn = npc.desc.degreesToTurn;
			npc.walkAnimIndex = npc.desc.walkAnim;
			npc.turn180AnimIndex = npc.desc.turn180AnimIndex;
			npc.turn90CWAnimIndex = npc.desc.turn90CWAnimIndex;
			npc.turn90CCWAnimIndex = npc.desc.turn90CCWAnimIndex;
			npc.idleAnimation = npc.desc.standAnim;
			npc.setPos(localPlayer.pathX[0] + i1, localPlayer.pathY[0] + l, j1 == 1);
		}
		buffer.finishBitAccess();
	}

	public void processGameLoop() {
		if(rsAlreadyLoaded || loadingError || genericLoadingError)
			return;
		tick++;
		if(!loggedIn)
			processLoginScreenInput();
		else
			mainGameProcessor();
		processOnDemandQueue();
	}

	public void method47(boolean flag) {
		if(localPlayer.x >> 7 == destinationX && localPlayer.y >> 7 == destinationY)
			destinationX = 0;
		int j = playerCount;
		if(flag)
			j = 1;
		for(int l = 0; l < j; l++) {
			Player player;
			int i1;
			if(flag) {
				player = localPlayer;
				i1 = internalLocalPlayerIndex << 14;
			} else {
				player = players[playerList[l]];
				i1 = playerList[l] << 14;
			}
			if(player == null || !player.isVisible())
				continue;
			player.aBoolean1699 = (lowMem && playerCount > 50 || playerCount > 200) && !flag && player.movementAnimation == player.idleAnimation;
			int j1 = player.x >> 7;
			int k1 = player.y >> 7;
			if(j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104)
				continue;
			if(player.aModel_1714 != null && tick >= player.anInt1707 && tick < player.anInt1708) {
				player.aBoolean1699 = false;
				player.anInt1709 = getCenterHeight(plane, player.y, player.x);
				scene.addToScenePlayerAsObject(plane, player.y, player, player.anInt1552, player.anInt1722, player.x, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
				continue;
			}
			if((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
				if(anIntArrayArray929[j1][k1] == anInt1265)
					continue;
				anIntArrayArray929[j1][k1] = anInt1265;
			}
			player.anInt1709 = getCenterHeight(plane, player.y, player.x);
			scene.addAnimableA(plane, player.anInt1552, player.anInt1709, i1, player.y, 60, player.x, player, player.animationScretches);
		}
	}

	private boolean promptUserForInput(Widget class9) {
		int j = class9.contentType;
		if(anInt900 == 2) {
			if(j == 201) {
				updateChatbox = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if(j == 202) {
				updateChatbox = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if(j == 205) {
			anInt1011 = 250;
			return true;
		}
		if(j == 501) {
			updateChatbox = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if(j == 502) {
			updateChatbox = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if(j == 550) {
			updateChatbox = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 6;
			aString1121 = "Enter the name of the chat you wish to join";
		}
		if(j >= 300 && j <= 313) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = anIntArray1065[k];
			if(i2 != -1) {
				do {
					if(j1 == 0 && --i2 < 0)
						i2 = IdentityKits.length - 1;
					if(j1 == 1 && ++i2 >= IdentityKits.length)
						i2 = 0;
				} while(IdentityKits.kits[i2].validStyle || IdentityKits.kits[i2].bodyPartId != k + (maleCharacter ? 0 : 7));
				anIntArray1065[k] = i2;
				aBoolean1031 = true;
			}
		}
		if(j >= 314 && j <= 323) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if(k1 == 0 && --j2 < 0)
				j2 = anIntArrayArray1003[l].length - 1;
			if(k1 == 1 && ++j2 >= anIntArrayArray1003[l].length)
				j2 = 0;
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if(j == 324 && !maleCharacter) {
			maleCharacter = true;
			changeCharacterGender();
		}
		if(j == 325 && maleCharacter) {
			maleCharacter = false;
			changeCharacterGender();
		}
		if(j == 326) {
			buffer.createFrame(101);
			buffer.writeWordBigEndian(maleCharacter ? 0 : 1);
			for(int i1 = 0; i1 < 7; i1++)
				buffer.writeWordBigEndian(anIntArray1065[i1]);

			for(int l1 = 0; l1 < 5; l1++)
				buffer.writeWordBigEndian(anIntArray990[l1]);

			return true;
		}
		if(j == 620)
			canMute = !canMute;
		if(j >= 601 && j <= 613) {
			clearTopInterfaces();
			if(reportAbuseInput.length() > 0) {
				buffer.createFrame(218);
				buffer.writeQWord(TextClass.longForName(reportAbuseInput));
				buffer.writeWordBigEndian(j - 601);
				buffer.writeWordBigEndian(canMute ? 1 : 0);
			}
		}
		return false;
	}

	public void method49(Buffer buffer) {
		for(int j = 0; j < mobsAwaitingUpdateCount; j++) {
			int k = mobsAwaitingUpdate[j];
			Player player = players[k];
			int l = buffer.readUnsignedByte();
			if((l & 0x40) != 0)
				l += buffer.readUnsignedByte() << 8;
			method107(l, k, buffer, player);
		}
	}

	private void drawMapScenes(int i, int k, int l, int i1, int j1) {
		int k1 = scene.getWallObjectUid(j1, l, i);
		if (k1 != 0) {
			int l1 = scene.getMask(j1, l, i, k1);
			int k2 = l1 >> 6 & 3;
			int i3 = l1 & 0x1f;
			int k3 = k;
			if (k1 > 0)
				k3 = i1;
			int ai[] = minimapImage.myPixels;
			int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
			int i5 = k1 >> 14 & 0x7fff;
			ObjectDefinition def = ObjectDefinition.lookup(i5);
			if (def.mapscene != -1) {
				IndexedImage background_2 = mapScenes[def.mapscene];
				if (background_2 != null) {
					int i6 = (def.objectSizeX * 4 - background_2.width) / 2;
					int j6 = (def.objectSizeY * 4 - background_2.height) / 2;
					background_2.draw(48 + l * 4 + i6,
							48 + (104 - i - def.objectSizeY) * 4 + j6);
				}
			} else {
				if (i3 == 0 || i3 == 2)
					if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 1) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 2) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
				if (i3 == 3)
					if (k2 == 0)
						ai[k4] = k3;
					else if (k2 == 1)
						ai[k4 + 3] = k3;
					else if (k2 == 2)
						ai[k4 + 3 + 1536] = k3;
					else if (k2 == 3)
						ai[k4 + 1536] = k3;
				if (i3 == 2)
					if (k2 == 3) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 2) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
			}
		}
		k1 = scene.getGameObjectUid(j1, l, i);
		if (k1 != 0) {
			int i2 = scene.getMask(j1, l, i, k1);
			int l2 = i2 >> 6 & 3;
			int j3 = i2 & 0x1f;
			int l3 = k1 >> 14 & 0x7fff;
			ObjectDefinition class46_1 = ObjectDefinition.lookup(l3);
			if (class46_1.mapscene != -1) {
				IndexedImage background_1 = mapScenes[class46_1.mapscene];
				if (background_1 != null) {
					int j5 = (class46_1.objectSizeX * 4 - background_1.width) / 2;
					int k5 = (class46_1.objectSizeY * 4 - background_1.height) / 2;
					background_1.draw(48 + l * 4 + j5,
							48 + (104 - i - class46_1.objectSizeY) * 4 + k5);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (k1 > 0)
					l4 = 0xee0000;
				int ai1[] = minimapImage.myPixels;
				int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
				if (l2 == 0 || l2 == 2) {
					ai1[l5 + 1536] = l4;
					ai1[l5 + 1024 + 1] = l4;
					ai1[l5 + 512 + 2] = l4;
					ai1[l5 + 3] = l4;
				} else {
					ai1[l5] = l4;
					ai1[l5 + 512 + 1] = l4;
					ai1[l5 + 1024 + 2] = l4;
					ai1[l5 + 1536 + 3] = l4;
				}
			}
		}
		k1 = scene.getGroundDecorationUid(j1, l, i);
		if (k1 != 0) {
			int j2 = k1 >> 14 & 0x7fff;
			ObjectDefinition class46 = ObjectDefinition.lookup(j2);
			if (class46.mapscene != -1) {
				IndexedImage background = mapScenes[class46.mapscene];
				if (background != null) {
					int i4 = (class46.objectSizeX * 4 - background.width) / 2;
					int j4 = (class46.objectSizeY * 4 - background.height) / 2;
					background.draw(48 + l * 4 + i4,
							48 + (104 - i - class46.objectSizeY) * 4 + j4);
				}
			}
		}
	}

	public void loadTitleScreen() {
	
		if (!hunterTitleButtons) {
			titleBoxIndexedImage = new IndexedImage(titleFileArchive, "titlebox", 0);
			titleButtonIndexedImage = new IndexedImage(titleFileArchive, "titlebutton", 0);
		} else {
			titleBox = new Sprite("Login/titlebox");
			titleButton = new Sprite("Login/titlebutton");
		}
		aIndexedImageArray1152s = new IndexedImage[12];
		int j = 0;
		try {
			j = Integer.parseInt(getParameter("fl_icon"));
		} catch(Exception _ex) {
		}
		if(j == 0) {
			for(int k = 0; k < 12; k++)
				aIndexedImageArray1152s[k] = new IndexedImage(titleFileArchive, "runes", k);

		} else {
			for(int l = 0; l < 12; l++)
				aIndexedImageArray1152s[l] = new IndexedImage(titleFileArchive, "runes", 12 + (l & 3));

		}
		aClass30_Sub2_Sub1_Sub1_1201 = new Sprite(128, 265);
		aClass30_Sub2_Sub1_Sub1_1202 = new Sprite(128, 265);
		System.arraycopy(flameLeftBackground.canvasRaster, 0, aClass30_Sub2_Sub1_Sub1_1201.myPixels, 0, 33920);

		System.arraycopy(flameRightBackground.canvasRaster, 0, aClass30_Sub2_Sub1_Sub1_1202.myPixels, 0, 33920);

		anIntArray851 = new int[256];
		for(int k1 = 0; k1 < 64; k1++)
			anIntArray851[k1] = k1 * 0x40000;

		for(int l1 = 0; l1 < 64; l1++)
			anIntArray851[l1 + 64] = 0xff0000 + 1024 * l1;

		for(int i2 = 0; i2 < 64; i2++)
			anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;

		for(int j2 = 0; j2 < 64; j2++)
			anIntArray851[j2 + 192] = 0xffffff;

		anIntArray852 = new int[256];
		for(int k2 = 0; k2 < 64; k2++)
			anIntArray852[k2] = k2 * 1024;

		for(int l2 = 0; l2 < 64; l2++)
			anIntArray852[l2 + 64] = 65280 + 4 * l2;

		for(int i3 = 0; i3 < 64; i3++)
			anIntArray852[i3 + 128] = 65535 + 0x40000 * i3;

		for(int j3 = 0; j3 < 64; j3++)
			anIntArray852[j3 + 192] = 0xffffff;

		anIntArray853 = new int[256];
		for(int k3 = 0; k3 < 64; k3++)
			anIntArray853[k3] = k3 * 4;

		for(int l3 = 0; l3 < 64; l3++)
			anIntArray853[l3 + 64] = 255 + 0x40000 * l3;

		for(int i4 = 0; i4 < 64; i4++)
			anIntArray853[i4 + 128] = 0xff00ff + 1024 * i4;

		for(int j4 = 0; j4 < 64; j4++)
			anIntArray853[j4 + 192] = 0xffffff;

		anIntArray850 = new int[256];
		anIntArray1190 = new int[32768];
		anIntArray1191 = new int[32768];
		randomizeBackground(null);
		anIntArray828 = new int[32768];
		anIntArray829 = new int[32768];
		drawLoadingText(10, "Connecting to fileserver");
		if(!aBoolean831) {
			drawFlames = true;
			aBoolean831 = true;
			startRunnable(this, 2);
		}
	}

	private static void setHighMem() {
		SceneGraph.lowMem = false;
		Rasterizer3D.lowMem = false;
		lowMem = false;
		MapRegion.lowMem = false;
		ObjectDefinition.lowMem = false;
	}

	public static Client instance;

	@Override
	public void init() {
		try {
			nodeID = 10;
			portOff = 0;
			setHighMem();
			isMembers = true;
			signlink.storeid = 32;
			signlink.startpriv(InetAddress.getLocalHost());
			frameMode(ScreenMode.FIXED);
			instance = this;
			initClientFrame(503, 765);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadingStages() {
		if(lowMem && loadingStage == 2 && MapRegion.anInt131 != plane) {
			gameScreenImageProducer.initDrawingArea();
			regularText.drawText(0, "Loading - please wait.", 151, 257);
			regularText.drawText(0xffffff, "Loading - please wait.", 150, 256);
			gameScreenImageProducer.drawGraphics(4, super.graphics, 4);
			loadingStage = 1;
			aLong824 = System.currentTimeMillis();
		}
		if(loadingStage == 1) {
			int j = method54();
			if(j != 0 && System.currentTimeMillis() - aLong824 > 0x57e40L) {
				signlink.reporterror(myUsername + " glcfb " + aLong1215 + "," + j + "," + lowMem + "," + decompressors[0] + "," + resourceProvider.getNodeCount() + "," + plane + "," + currentRegionX + "," + currentRegionY);
				aLong824 = System.currentTimeMillis();
			}
		}
		if(loadingStage == 2 && plane != anInt985) {
			anInt985 = plane;
			renderMapScene(plane);
		}
	}

	private int method54() {
		for(int i = 0; i < aByteArrayArray1183.length; i++) {
			if(aByteArrayArray1183[i] == null && anIntArray1235[i] != -1)
				return -1;
			if(aByteArrayArray1247[i] == null && anIntArray1236[i] != -1)
				return -2;
		}
		boolean flag = true;
		for(int j = 0; j < aByteArrayArray1183.length; j++) {
			byte abyte0[] = aByteArrayArray1247[j];
			if(abyte0 != null) {
				int k = (anIntArray1234[j] >> 8) * 64 - regionBaseX;
				int l = (anIntArray1234[j] & 0xff) * 64 - regionBaseY;
				if(aBoolean1159) {
					k = 10;
					l = 10;
				}
				flag &= MapRegion.method189(k, abyte0, l);
			}
		}
		if(!flag)
			return -3;
		if(aBoolean1080) {
			return -4;
		} else {
			loadingStage = 2;
			MapRegion.anInt131 = plane;
			method22();
			buffer.createFrame(121);
			return 0;
		}
	}

	public void method55()
	{
		for(Renderable_Sub4 class30_sub2_sub4_sub4 = (Renderable_Sub4)aClass19_1013.reverseGetFirst(); class30_sub2_sub4_sub4 != null; class30_sub2_sub4_sub4 = (Renderable_Sub4)aClass19_1013.reverseGetNext())
			if(class30_sub2_sub4_sub4.anInt1597 != plane || tick > class30_sub2_sub4_sub4.anInt1572)
				class30_sub2_sub4_sub4.unlink();
			else
			if(tick >= class30_sub2_sub4_sub4.anInt1571)
			{
				if(class30_sub2_sub4_sub4.anInt1590 > 0)
				{
					Npc npc = npcs[class30_sub2_sub4_sub4.anInt1590 - 1];
					if(npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312)
						class30_sub2_sub4_sub4.method455(tick, npc.y, getCenterHeight(class30_sub2_sub4_sub4.anInt1597, npc.y, npc.x) - class30_sub2_sub4_sub4.anInt1583, npc.x);
				}
				if(class30_sub2_sub4_sub4.anInt1590 < 0)
				{
					int j = -class30_sub2_sub4_sub4.anInt1590 - 1;
					Player player;
					if(j == localPlayerIndex)
						player = localPlayer;
					else
						player = players[j];
					if(player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312)
						class30_sub2_sub4_sub4.method455(tick, player.y, getCenterHeight(class30_sub2_sub4_sub4.anInt1597, player.y, player.x) - class30_sub2_sub4_sub4.anInt1583, player.x);
				}
				class30_sub2_sub4_sub4.method456(tickDelta);
				scene.addAnimableA(plane, class30_sub2_sub4_sub4.anInt1595, (int)class30_sub2_sub4_sub4.aDouble1587, -1, (int)class30_sub2_sub4_sub4.aDouble1586, 60, (int)class30_sub2_sub4_sub4.aDouble1585, class30_sub2_sub4_sub4, false);
			}

	}

	public AppletContext getAppletContext()
	{
		if(signlink.mainapp != null)
			return signlink.mainapp.getAppletContext();
		else
			return super.getAppletContext();
	}

	private void drawLogo() {
		byte sprites[] = titleFileArchive.readFile("title.dat");
		Sprite sprite = new Sprite(sprites, this);
		flameLeftBackground.initDrawingArea();
		sprite.method346(0, 0);
		flameRightBackground.initDrawingArea();
		sprite.method346(-637, 0);
		topLeft1BackgroundTile.initDrawingArea();
		sprite.method346(-128, 0);
		bottomLeft1BackgroundTile.initDrawingArea();
		sprite.method346(-202, -371);
		loginBoxImageProducer.initDrawingArea();
		sprite.method346(-202, -171);
		loginScreenAccessories.initDrawingArea();
		sprite.method346(0, -400);
		bottomLeft0BackgroundTile.initDrawingArea();
		sprite.method346(0, -265);
		bottomRightImageProducer.initDrawingArea();
		sprite.method346(-562, -265);
		loginMusicImageProducer.initDrawingArea();
		sprite.method346(-562, -265);
		middleLeft1BackgroundTile.initDrawingArea();
		sprite.method346(-128, -171);
		aRSImageProducer_1115.initDrawingArea();
		sprite.method346(-562, -171);
		int ai[] = new int[sprite.myWidth];
		for (int j = 0; j < sprite.myHeight; j++) {
			for (int k = 0; k < sprite.myWidth; k++)
				ai[k] = sprite.myPixels[(sprite.myWidth - k - 1) + sprite.myWidth * j];

			System.arraycopy(ai, 0, sprite.myPixels, sprite.myWidth * j, sprite.myWidth);
		}
		/*
		flameLeftBackground.initDrawingArea();
		sprite.method346(382, 0);
		flameRightBackground.initDrawingArea();
		sprite.method346(-255, 0);
		topLeft1BackgroundTile.initDrawingArea();
		sprite.method346(254, 0);
		bottomLeft1BackgroundTile.initDrawingArea();
		sprite.method346(180, -371);
		loginBoxImageProducer.initDrawingArea();
		sprite.method346(180, -171);
		bottomLeft0BackgroundTile.initDrawingArea();
		sprite.method346(382, -265);
		bottomRightImageProducer.initDrawingArea();
		sprite.method346(-180, -265);
		loginMusicImageProducer.initDrawingArea();
		sprite.method346(-180, -265);
		middleLeft1BackgroundTile.initDrawingArea();
		sprite.method346(254, -171);
		aRSImageProducer_1115.initDrawingArea();
		sprite.method346(-180, -171);
		sprite = new Sprite(titleFileArchive, "logo", 0);
		topLeft1BackgroundTile.initDrawingArea();
		sprite.drawSprite(382 - sprite.myWidth / 2 - 128, 18);
		*/
		sprite = null;
		System.gc();
	}

	public void processOnDemandQueue() {
		do {
			Resource resource;
			do {
				resource = resourceProvider.getNextNode();
				if (resource == null)
					return;
				if (resource.dataType == 0) {
					Model.method460(resource.buffer, resource.ID);
					if (backDialogueId != -1)
						updateChatbox = true;
				}
				if (resource.dataType == 1 && resource.buffer != null)
					Frame.method529(resource.buffer, resource.ID);
				if (resource.dataType == 2 && resource.ID == nextSong
						&& resource.buffer != null)
					saveMidi(songChanging, resource.buffer);
				if (resource.dataType == 3 && loadingStage == 1) {
					try {
					writeFile(resource.buffer, "./maps/" + resource.ID + ".dat");
					} catch (Exception e) {}
					for (int i = 0; i < aByteArrayArray1183.length; i++) {
						if (anIntArray1235[i] == resource.ID) {
							aByteArrayArray1183[i] = resource.buffer;
							if (resource.buffer == null) {
								anIntArray1235[i] = -1;
								System.out.println("Landscape nulled.");
							}
							break;
						}
						if (anIntArray1236[i] != resource.ID)
							continue;
						aByteArrayArray1247[i] = resource.buffer;
						if (resource.buffer == null) {
							anIntArray1236[i] = -1;
							System.out.println("Objects nulled.");
						}
						break;
					}

				}
			} while (resource.dataType != 93
					|| !resourceProvider.landscapePresent(resource.ID));
		} while (true);
	}

	public void calcFlamesPosition()
	{
		char c = '\u0100';
		for(int j = 10; j < 117; j++)
		{
			int k = (int)(Math.random() * 100D);
			if(k < 50)
				anIntArray828[j + (c - 2 << 7)] = 255;
		}
		for(int l = 0; l < 100; l++)
		{
			int i1 = (int)(Math.random() * 124D) + 2;
			int k1 = (int)(Math.random() * 128D) + 128;
			int k2 = i1 + (k1 << 7);
			anIntArray828[k2] = 192;
		}

		for(int j1 = 1; j1 < c - 1; j1++)
		{
			for(int l1 = 1; l1 < 127; l1++)
			{
				int l2 = l1 + (j1 << 7);
				anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128] + anIntArray828[l2 + 128]) / 4;
			}

		}

		anInt1275 += 128;
		if(anInt1275 > anIntArray1190.length)
		{
			anInt1275 -= anIntArray1190.length;
			int i2 = (int)(Math.random() * 12D);
			randomizeBackground(aIndexedImageArray1152s[i2]);
		}
		for(int j2 = 1; j2 < c - 1; j2++)
		{
			for(int i3 = 1; i3 < 127; i3++)
			{
				int k3 = i3 + (j2 << 7);
				int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
				if(i4 < 0)
					i4 = 0;
				anIntArray828[k3] = i4;
			}

		}

		System.arraycopy(anIntArray969, 1, anIntArray969, 0, c - 1);

		anIntArray969[c - 1] = (int)(Math.sin((double) tick / 14D) * 16D + Math.sin((double) tick / 15D) * 14D + Math.sin((double) tick / 16D) * 12D);
		if(anInt1040 > 0)
			anInt1040 -= 4;
		if(anInt1041 > 0)
			anInt1041 -= 4;
		if(anInt1040 == 0 && anInt1041 == 0)
		{
			int l3 = (int)(Math.random() * 2000D);
			if(l3 == 0)
				anInt1040 = 1024;
			if(l3 == 1)
				anInt1041 = 1024;
		}
	}

	private boolean saveWave(byte abyte0[], int i)
	{
		return abyte0 == null || signlink.wavesave(abyte0, i);
	}

	public void method60(int i)
	{
		Widget class9 = Widget.interfaceCache[i];
		if (class9 == null)
			return;
		for(int j = 0; j < class9.children.length; j++)
		{
			if(class9.children[j] == -1)
				break;
			Widget class9_1 = Widget.interfaceCache[class9.children[j]];
			if(class9_1.type == 1)
				method60(class9_1.id);
			class9_1.currentFrame = 0;
			class9_1.anInt208 = 0;
		}
	}

	private void drawHeadIcon()
	{
		if(hintIconDrawType != 2)
			return;
		calcEntityScreenPos((hintIconX - regionBaseX << 7) + anInt937, anInt936 * 2, (hintIconY - regionBaseY << 7) + anInt938);
		if(spriteDrawX > -1 && tick % 20 < 10)
			headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
	}

	public int canWalkDelay = 0;
     public int getDis(int coordX1, int coordY1, int coordX2, int coordY2)
    {
        int deltaX = coordX2 - coordX1;
        int deltaY = coordY2 - coordY1;
        return ((int)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
    }
	 public int random(int range)
    {
        return (int)(Math.random() * range);
    }
   public boolean withinDistance(int x1, int y1, int x2, int y2, int dis)
    {
        for (int i = 0; i <= dis; i++)
        {
			try{
            if ((x1 + i) == x2 && ((y1 + i) == y2 || (y1 - i) == y2 || y1 == y2))
                return true;
            else
            if ((x1 - i) == x2 && ((x1 + i) == y2 || (y1 - i) == y2 || y1 == y2))
                return true;
            else
            if (x1 == x2 && ((x1 + i) == y2 || (y1 - i) == y2 || y1 == y2))
                return true;
								} catch(Exception ex){
		System.out.println("Exception in following, method : WithingDistance");
		}
        }
        return false;
    }

	public void mainGameProcessor() {
		refreshFrameSize();
		if (getGameComponent().getFocusTraversalKeysEnabled()) {
			getGameComponent().setFocusTraversalKeysEnabled(false);
		}
		if(systemUpdateTime > 1)
			systemUpdateTime--;
		if(anInt1011 > 0)
			anInt1011--;
		for(int j = 0; j < 5; j++)
			if(!parsePacket())
				break;

		if(!loggedIn)
			return;
		synchronized(mouseDetection.syncObject)
		{
			if(flagged)
			{
				if(super.clickMode3 != 0 || mouseDetection.coordsIndex >= 40)
				{
					buffer.createFrame(45);
					buffer.writeWordBigEndian(0);
					int j2 = buffer.currentPosition;
					int j3 = 0;
					for(int j4 = 0; j4 < mouseDetection.coordsIndex; j4++)
					{
						if(j2 - buffer.currentPosition >= 240)
							break;
						j3++;
						int l4 = mouseDetection.coordsY[j4];
						if(l4 < 0)
							l4 = 0;
						else
						if(l4 > 502)
							l4 = 502;
						int k5 = mouseDetection.coordsX[j4];
						if(k5 < 0)
							k5 = 0;
						else
						if(k5 > 764)
							k5 = 764;
						int i6 = l4 * 765 + k5;
						if(mouseDetection.coordsY[j4] == -1 && mouseDetection.coordsX[j4] == -1)
						{
							k5 = -1;
							l4 = -1;
							i6 = 0x7ffff;
						}
						if(k5 == anInt1237 && l4 == anInt1238)
						{
							if(anInt1022 < 2047)
								anInt1022++;
						} else
						{
							int j6 = k5 - anInt1237;
							anInt1237 = k5;
							int k6 = l4 - anInt1238;
							anInt1238 = l4;
							if(anInt1022 < 8 && j6 >= -32 && j6 <= 31 && k6 >= -32 && k6 <= 31)
							{
								j6 += 32;
								k6 += 32;
								buffer.writeWord((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else
							if(anInt1022 < 8)
							{
								buffer.writeDWordBigEndian(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else
							{
								buffer.writeDWord(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}

					buffer.writeBytes(buffer.currentPosition - j2);
					if(j3 >= mouseDetection.coordsIndex)
					{
						mouseDetection.coordsIndex = 0;
					} else
					{
						mouseDetection.coordsIndex -= j3;
						for(int i5 = 0; i5 < mouseDetection.coordsIndex; i5++)
						{
							mouseDetection.coordsX[i5] = mouseDetection.coordsX[i5 + j3];
							mouseDetection.coordsY[i5] = mouseDetection.coordsY[i5 + j3];
						}

					}
				}
			} else
			{
				mouseDetection.coordsIndex = 0;
			}
		}
		if(super.clickMode3 != 0)
		{
			long l = (super.aLong29 - aLong1220) / 50L;
			if(l > 4095L)
				l = 4095L;
			aLong1220 = super.aLong29;
			int k2 = super.saveClickY;
			if(k2 < 0)
				k2 = 0;
			else
			if(k2 > 502)
				k2 = 502;
			int k3 = super.saveClickX;
			if(k3 < 0)
				k3 = 0;
			else
			if(k3 > 764)
				k3 = 764;
			int k4 = k2 * 765 + k3;
			int j5 = 0;
			if(super.clickMode3 == 2)
				j5 = 1;
			int l5 = (int)l;
			buffer.createFrame(241);
			buffer.writeDWord((l5 << 20) + (j5 << 19) + k4);
		}
		if(anInt1016 > 0)
			anInt1016--;
		if(super.keyArray[1] == 1 || super.keyArray[2] == 1 || super.keyArray[3] == 1 || super.keyArray[4] == 1)
			aBoolean1017 = true;
		if(aBoolean1017 && anInt1016 <= 0)
		{
			anInt1016 = 20;
			aBoolean1017 = false;
			buffer.createFrame(86);
			buffer.writeWord(anInt1184);
			buffer.method432(cameraHorizontal);
		}
		if(super.awtFocus && !aBoolean954)
		{
			aBoolean954 = true;
			buffer.createFrame(3);
			buffer.writeWordBigEndian(1);
		}
		if(!super.awtFocus && aBoolean954)
		{
			aBoolean954 = false;
			buffer.createFrame(3);
			buffer.writeWordBigEndian(0);
		}
		loadingStages();
		method115();
		processTrackUpdates();
		anInt1009++;
		if(anInt1009 > 750)
			dropClient();
		method114();
		method95();
		method38();
		tickDelta++;
		if(crossType != 0)
		{
			crossIndex += 20;
			if(crossIndex >= 400)
				crossType = 0;
		}
		if(atInventoryInterfaceType != 0)
		{
			atInventoryLoopCycle++;
			if(atInventoryLoopCycle >= 15)
			{
				if(atInventoryInterfaceType == 2) {
				}
				if(atInventoryInterfaceType == 3)
					updateChatbox = true;
				atInventoryInterfaceType = 0;
			}
		}
		if(activeInterfaceType != 0)
		{
			dragItemDelay++;
			if(super.mouseX > anInt1087 + 5 || super.mouseX < anInt1087 - 5 || super.mouseY > anInt1088 + 5 || super.mouseY < anInt1088 - 5)
				aBoolean1242 = true;
			if(super.clickMode2 == 0)
			{
				if(activeInterfaceType == 2) {
				}
				if(activeInterfaceType == 3)
					updateChatbox = true;
				activeInterfaceType = 0;
				if(aBoolean1242 && dragItemDelay >= 10)
				{
					lastActiveInvInterface = -1;
					processRightClick();
					if(lastActiveInvInterface == anInt1084 && mouseInvInterfaceIndex != anInt1085)
					{
						Widget class9 = Widget.interfaceCache[anInt1084];
						int j1 = 0;
						if(anInt913 == 1 && class9.contentType == 206)
							j1 = 1;
						if(class9.inventoryItemId[mouseInvInterfaceIndex] <= 0)
							j1 = 0;
						if(class9.replaceItems)
						{
							int l2 = anInt1085;
							int l3 = mouseInvInterfaceIndex;
							class9.inventoryItemId[l3] = class9.inventoryItemId[l2];
							class9.inventoryAmounts[l3] = class9.inventoryAmounts[l2];
							class9.inventoryItemId[l2] = -1;
							class9.inventoryAmounts[l2] = 0;
						} else
						if(j1 == 1)
						{
							int i3 = anInt1085;
							for(int i4 = mouseInvInterfaceIndex; i3 != i4;)
								if(i3 > i4)
								{
									class9.swapInventoryItems(i3, i3 - 1);
									i3--;
								} else
								if(i3 < i4)
								{
									class9.swapInventoryItems(i3, i3 + 1);
									i3++;
								}

						} else
						{
							class9.swapInventoryItems(anInt1085, mouseInvInterfaceIndex);
						}
						buffer.createFrame(214);
						buffer.method433(anInt1084);
						buffer.method424(j1);
						buffer.method433(anInt1085);
						buffer.method431(mouseInvInterfaceIndex);
					}
				} else
				if((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
					determineMenuSize();
				else
				if(menuActionRow > 0)
					processMenuActions(menuActionRow - 1);
				atInventoryLoopCycle = 10;
				super.clickMode3 = 0;
			}
		}
		if(SceneGraph.clickedTileX != -1)
		{
			int k = SceneGraph.clickedTileX;
			int k1 = SceneGraph.clickedTileY;
			boolean flag = doWalkTo(0, 0, 0, 0, localPlayer.pathY[0], 0, 0, k1, localPlayer.pathX[0], true, k);
			SceneGraph.clickedTileX = -1;
			if(flag)
			{
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 1;
				crossIndex = 0;
			}
		}
		if(super.clickMode3 == 1 && clickToContinueString != null)
		{
			clickToContinueString = null;
			updateChatbox = true;
			super.clickMode3 = 0;
		}
		processMenuClick();
		if(super.clickMode2 == 1 || super.clickMode3 == 1)
			anInt1213++;
		if(anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0)
		{
            if(anInt1501 < 100)
			{
                anInt1501++;
                if(anInt1501 == 100)
				{
                    if(anInt1500 != 0)
					{
                        updateChatbox = true;
                    }
                    if(anInt1044 != 0)
					{
					}
                }
            }
        } else if(anInt1501 > 0) {
            anInt1501--;
        }
		if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
			if (anInt1501 < 100) {
				anInt1501++;
				if (anInt1501 == 100) {
					if (anInt1500 != 0) {
						updateChatbox = true;
					}
					if (anInt1044 != 0) {
					}
				}
			}
		} else if (anInt1501 > 0) {
			anInt1501--;
		}
		if(loadingStage == 2)
			method108();
		if(loadingStage == 2 && inCutScene)
			calcCameraPos();
		for(int i1 = 0; i1 < 5; i1++)
			anIntArray1030[i1]++;

		method73();
		super.idleTime++;
		if(super.idleTime > 4500)
		{
			anInt1011 = 250;
			super.idleTime -= 500;
			buffer.createFrame(202);
		}
		anInt988++;
		if(anInt988 > 500)
		{
			anInt988 = 0;
			int l1 = (int)(Math.random() * 8D);
			if((l1 & 1) == 1)
				anInt1278 += anInt1279;
			if((l1 & 2) == 2)
				anInt1131 += anInt1132;
			if((l1 & 4) == 4)
				anInt896 += anInt897;
		}
		if(anInt1278 < -50)
			anInt1279 = 2;
		if(anInt1278 > 50)
			anInt1279 = -2;
		if(anInt1131 < -55)
			anInt1132 = 2;
		if(anInt1131 > 55)
			anInt1132 = -2;
		if(anInt896 < -40)
			anInt897 = 1;
		if(anInt896 > 40)
			anInt897 = -1;
		anInt1254++;
		if(anInt1254 > 500)
		{
			anInt1254 = 0;
			int i2 = (int)(Math.random() * 8D);
			if((i2 & 1) == 1)
				minimapRotation += anInt1210;
			if((i2 & 2) == 2)
				minimapZoom += anInt1171;
		}
		if(minimapRotation < -60)
			anInt1210 = 2;
		if(minimapRotation > 60)
			anInt1210 = -2;
		if(minimapZoom < -20)
			anInt1171 = 1;
		if(minimapZoom > 10)
			anInt1171 = -1;
		anInt1010++;
		if(anInt1010 > 50)
			buffer.createFrame(0);
		try
		{
			if(socketStream != null && buffer.currentPosition > 0)
			{
				socketStream.queueBytes(buffer.currentPosition, buffer.payload);
				buffer.currentPosition = 0;
				anInt1010 = 0;
			}
		}
		catch(IOException _ex)
		{
			dropClient();
		}
		catch(Exception exception)
		{
			resetLogout();
		}
	}

	public void method63()
	{
		SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst();
		for(; spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext())
			if(spawnedObject.getLongetivity == -1)
			{
				spawnedObject.delay = 0;
				method89(spawnedObject);
			} else
			{
				spawnedObject.unlink();
			}

	}

	private void setupLoginScreen() {
		if (topLeft1BackgroundTile != null)
			return;
		super.fullGameScreen = null;
		chatboxImageProducer = null;
		minimapImageProducer = null;
		tabImageProducer = null;
		gameScreenImageProducer = null;
		chatSettingImageProducer = null;
		Rasterizer2D.clear();
		flameLeftBackground = new ProducingGraphicsBuffer(128, 265);
		Rasterizer2D.clear();
		flameRightBackground = new ProducingGraphicsBuffer(128, 265);
		Rasterizer2D.clear();
		topLeft1BackgroundTile = new ProducingGraphicsBuffer(509, 171);
		Rasterizer2D.clear();
		bottomLeft1BackgroundTile = new ProducingGraphicsBuffer(360, 132);
		Rasterizer2D.clear();
		loginBoxImageProducer = new ProducingGraphicsBuffer(360, 200);
		Rasterizer2D.clear();
		loginScreenAccessories = new ProducingGraphicsBuffer(300, 800);
		Rasterizer2D.clear();
		bottomLeft0BackgroundTile = new ProducingGraphicsBuffer(202, 238);
		Rasterizer2D.clear();
		bottomRightImageProducer = new ProducingGraphicsBuffer(203, 238);
		Rasterizer2D.clear();
		loginMusicImageProducer = new ProducingGraphicsBuffer(203, 238);
		Rasterizer2D.clear();
		middleLeft1BackgroundTile = new ProducingGraphicsBuffer(74, 94);
		Rasterizer2D.clear();
		aRSImageProducer_1115 = new ProducingGraphicsBuffer(75, 94);
		Rasterizer2D.clear();
		if (titleFileArchive != null) {
			drawLogo();
			loadTitleScreen();
		}
		welcomeScreenRaised = true;
	}
	void drawLoadingText(int i, String s)
	{
		anInt1079 = i;
		aString1049 = s;
		setupLoginScreen();
		if(titleFileArchive == null)
		{
			super.drawLoadingText(i, s);
			return;
		}
		loginBoxImageProducer.initDrawingArea();
		char c = '\u0168';
		char c1 = '\310';
		byte byte1 = 20;
		boldText.drawText(0xffffff, "Allstarlegends is loading - please wait...", c1 / 2 - 26 - byte1, c / 2);
		int j = c1 / 2 - 18 - byte1;
		Rasterizer2D.fillPixels(c / 2 - 152, 304, 34, 0x8c1111, j);
		Rasterizer2D.fillPixels(c / 2 - 151, 302, 32, 0, j + 1);
		Rasterizer2D.drawPixels(30, j + 2, c / 2 - 150, 0x8c1111, i * 3);
		Rasterizer2D.drawPixels(30, j + 2, (c / 2 - 150) + i * 3, 0, 300 - i * 3);
		boldText.drawText(0xffffff, s, (c1 / 2 + 5) - byte1, c / 2);
		loginBoxImageProducer.drawGraphics(171, super.graphics, 202);
		if(welcomeScreenRaised)
		{
			welcomeScreenRaised = false;
			if(!aBoolean831)
			{
				flameLeftBackground.drawGraphics(0, super.graphics, 0);
				flameRightBackground.drawGraphics(0, super.graphics, 637);
			}
			topLeft1BackgroundTile.drawGraphics(0, super.graphics, 128);
			bottomLeft1BackgroundTile.drawGraphics(371, super.graphics, 202);
			bottomLeft0BackgroundTile.drawGraphics(265, super.graphics, 0);
			bottomRightImageProducer.drawGraphics(265, super.graphics, 562);
			middleLeft1BackgroundTile.drawGraphics(171, super.graphics, 128);
			aRSImageProducer_1115.drawGraphics(171, super.graphics, 562);
		}
	}

	public void method65(int i, int j, int k, int l, Widget class9, int i1, boolean flag,
						 int j1)
	{
		int anInt992;
		if(aBoolean972)
			anInt992 = 32;
		else
			anInt992 = 0;
		aBoolean972 = false;
		if(k >= i && k < i + 16 && l >= i1 && l < i1 + 16)
		{
			class9.scrollPosition -= anInt1213 * 4;
			if(flag)
			{
			}
		} else
		if(k >= i && k < i + 16 && l >= (i1 + j) - 16 && l < i1 + j)
		{
			class9.scrollPosition += anInt1213 * 4;
			if(flag)
			{
			}
		} else
		if(k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < (i1 + j) - 16 && anInt1213 > 0)
		{
			int l1 = ((j - 32) * j) / j1;
			if(l1 < 8)
				l1 = 8;
			int i2 = l - i1 - 16 - l1 / 2;
			int j2 = j - 32 - l1;
			class9.scrollPosition = ((j1 - j) * i2) / j2;
			if(flag) {
			}
			aBoolean972 = true;
		}
	}

	private boolean method66(int i, int j, int k)
	{
		int i1 = i >> 14 & 0x7fff;
		int j1 = scene.getMask(plane, k, j, i);
		if(j1 == -1)
			return false;
		int k1 = j1 & 0x1f;
		int l1 = j1 >> 6 & 3;
		if(k1 == 10 || k1 == 11 || k1 == 22)
		{
			ObjectDefinition class46 = ObjectDefinition.lookup(i1);
			int i2;
			int j2;
			if(l1 == 0 || l1 == 2)
			{
				i2 = class46.objectSizeX;
				j2 = class46.objectSizeY;
			} else
			{
				i2 = class46.objectSizeY;
				j2 = class46.objectSizeX;
			}
			int k2 = class46.anInt768;
			if(l1 != 0)
				k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
			doWalkTo(2, 0, j2, 0, localPlayer.pathY[0], i2, k2, j, localPlayer.pathX[0], false, k);
		} else
		{
			doWalkTo(2, l1, 0, k1 + 1, localPlayer.pathY[0], 0, 0, j, localPlayer.pathX[0], false, k);
		}
		crossX = super.saveClickX;
		crossY = super.saveClickY;
		crossType = 2;
		crossIndex = 0;
		return true;
	}

	private FileArchive streamLoaderForName(int i, String s, String s1, int j,
											int k) {
		byte abyte0[] = null;
		int l = 5;
		try {
			if (decompressors[0] != null)
				abyte0 = decompressors[0].decompress(i);
		} catch (Exception _ex) {
		}
		if (abyte0 != null) {
			// aCRC32_930.reset();
			// aCRC32_930.update(abyte0);
			// int i1 = (int)aCRC32_930.getValue();
			// if(i1 != j)
		}
		if (abyte0 != null) {
			FileArchive fileArchive = new FileArchive(abyte0);
			return fileArchive;
		}
		int j1 = 0;
		while (abyte0 == null) {
			String s2 = "Unknown error";
			drawLoadingText(k, "Requesting " + s);
			Object obj = null;
			try {
				int k1 = 0;
				DataInputStream datainputstream = openJagGrabInputStream(s1 + j);
				byte abyte1[] = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Buffer buffer = new Buffer(abyte1);
				buffer.currentPosition = 3;
				int i2 = buffer.readTriByte() + 6;
				int j2 = 6;
				abyte0 = new byte[i2];
				System.arraycopy(abyte1, 0, abyte0, 0, 6);

				while (j2 < i2) {
					int l2 = i2 - j2;
					if (l2 > 1000)
						l2 = 1000;
					int j3 = datainputstream.read(abyte0, j2, l2);
					if (j3 < 0) {
						s2 = "Length error: " + j2 + "/" + i2;
						throw new IOException("EOF");
					}
					j2 += j3;
					int k3 = (j2 * 100) / i2;
					if (k3 != k1)
						drawLoadingText(k, "Loading " + s + " - " + k3 + "%");
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (decompressors[0] != null)
						decompressors[0].method234(abyte0.length, abyte0, i);
				} catch (Exception _ex) {
					decompressors[0] = null;
				}
				/*
				 * if(abyte0 != null) { aCRC32_930.reset();
				 * aCRC32_930.update(abyte0); int i3 =
				 * (int)aCRC32_930.getValue(); if(i3 != j) { abyte0 = null;
				 * j1++; s2 = "Checksum error: " + i3; } }
				 */
			} catch (IOException ioexception) {
				if (s2.equals("Unknown error"))
					s2 = "Connection error";
				abyte0 = null;
			} catch (NullPointerException _ex) {
				s2 = "Null error";
				abyte0 = null;
				if (!signlink.reporterror)
					return null;
			} catch (ArrayIndexOutOfBoundsException _ex) {
				s2 = "Bounds error";
				abyte0 = null;
				if (!signlink.reporterror)
					return null;
			} catch (Exception _ex) {
				s2 = "Unexpected error";
				abyte0 = null;
				if (!signlink.reporterror)
					return null;
			}
			if (abyte0 == null) {
				for (int l1 = l; l1 > 0; l1--) {
					if (j1 >= 3) {
						drawLoadingText(k, "Game updated - please reload page");
						l1 = 10;
					} else {
						drawLoadingText(k, s2 + " - Retrying in " + l1);
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				l *= 2;
				if (l > 60)
					l = 60;
				aBoolean872 = !aBoolean872;
			}

		}

		FileArchive fileArchive_1 = new FileArchive(abyte0);
		return fileArchive_1;
	}

	public void dropClient()
	{
		if(anInt1011 > 0)
		{
			resetLogout();
			return;
		}
		if (gameScreenImageProducer != null)
			gameScreenImageProducer.initDrawingArea();
		Rasterizer2D.fillPixels(2, 229, 39, 0xffffff, 2);
		Rasterizer2D.drawPixels(37, 3, 3, 0, 227);
		regularText.drawText(0, "Connection Lost", 19, 120);
		regularText.drawText(0xffffff, "Connection Lost", 18, 119);
		regularText.drawText(0, "Please Wait - Reconnecting", 34, 117);
		regularText.drawText(0xffffff, "Please Wait - Reconnecting", 34, 116);
		if (gameScreenImageProducer != null)
			gameScreenImageProducer.drawGraphics(4, super.graphics, 4);
		minimapState = 0;
		destinationX = 0;
		RSSocket rsSocket = socketStream;
		loggedIn = false;
		loginFailures = 0;
		login(capitalize(myUsername), myPassword, true);
		if(!loggedIn)
			resetLogout();
		try
		{
			rsSocket.close();
		}
		catch(Exception _ex)
		{
		}
	}

	public void processMenuActions(int i)
	{
		if(i < 0)
			return;
		if(inputDialogState != 0)
		{
			inputDialogState = 0;
			updateChatbox = true;
		}
		int first = firstMenuAction[i];
		int button = secondMenuAction[i];
		int action = menuActionTypes[i];
		int clicked = selectedMenuActions[i];
		if(action >= 2000)
			action -= 2000;
		if(action == 582)
		{
			Npc npc = npcs[clicked];
			if(npc != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, npc.pathY[0], localPlayer.pathX[0], false, npc.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(57);
				buffer.method432(anInt1285);
				buffer.method432(clicked);
				buffer.method431(anInt1283);
				buffer.method432(anInt1284);
			}
		}
		if(action == 234)
		{
			boolean flag1 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag1)
				flag1 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(236);
			buffer.method431(button + regionBaseY);
			buffer.writeWord(clicked);
			buffer.method431(first + regionBaseX);
		//}
		//if(l == 1505){
		//	setSidebarInterface(7, 17011);
		//	needDrawTabArea = true;
		//	tabAreaAltered = true;
		}
		if(action == 62 && method66(clicked, button, first))
		{
			buffer.createFrame(192);
			buffer.writeWord(anInt1284);
			buffer.method431(clicked >> 14 & 0x7fff);
			buffer.method433(button + regionBaseY);
			buffer.method431(anInt1283);
			buffer.method433(first + regionBaseX);
			buffer.writeWord(anInt1285);
		}
		if (action == 696) { //face north when clicking compass
			cameraHorizontal = 0;
			anInt1184 = 120;

		}
		if(action == 511)
		{
			boolean flag2 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag2)
				flag2 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(25);
			buffer.method431(anInt1284);
			buffer.method432(anInt1285);
			buffer.writeWord(clicked);
			buffer.method432(button + regionBaseY);
			buffer.method433(anInt1283);
			buffer.writeWord(first + regionBaseX);
		}
		if(action == 74)
		{
			buffer.createFrame(122);
			buffer.method433(button);
			buffer.method432(first);
			buffer.method431(clicked);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if (action == 769) {
			Widget d = Widget.interfaceCache[button];
			Widget p = Widget.interfaceCache[clicked];
			if (!d.dropdown.isOpen()) {
				if (p.dropdownOpen != null) {
					p.dropdownOpen.dropdown.setOpen(false);
				}
				p.dropdownOpen = d;
			} else {
				p.dropdownOpen = null;
			}
			d.dropdown.setOpen(!d.dropdown.isOpen());
		} else if (action == 770) {
			Widget d = Widget.interfaceCache[button];
			Widget p = Widget.interfaceCache[clicked];
			if (first >= d.dropdown.getOptions().length)
				return;
			d.dropdown.setSelected(d.dropdown.getOptions()[first]);
			d.dropdown.setOpen(false);
			d.dropdown.getDrop().selectOption(first, d);
			p.dropdownOpen = null;
		}
		switch(action){
			case 1050:
				runState = 1;
				if(!runClicked) {
					runClicked = true;
					musicOrb = false;
					restOrb = false;
					buffer.createFrame(185);
					buffer.writeWord(153);
				} else {
					runClicked = false;
					buffer.createFrame(185);
					buffer.writeWord(152);
				}
			break;

			case 1500:
				//runState = 1;
				if(!prayClicked) {
					prayClicked = true;
					buffer.createFrame(185);
					buffer.writeWord(5002);
				} else {
					prayClicked = false;
					buffer.createFrame(185);
					buffer.writeWord(5001);
				}
			break;


			//case 1500: //Toggle quick prayers
			//	prayClicked = !prayClicked;
			//	item_data.createFrame(185);
			//	item_data.writeWord(5001);
			//break;

			case 1506: //Select quick prayers
				buffer.createFrame(185);
				buffer.writeWord(5001);
			break;

		}
		if(action == 315) {
			Widget widget = Widget.interfaceCache[button];
			boolean flag8 = true;
			if (widget.type == Widget.TYPE_CONFIG || widget.id == 50007) { // Placeholder toggle
				widget.active = !widget.active;
			} else if (widget.type == Widget.TYPE_CONFIG_HOVER) {
				Widget.handleConfigHover(widget);
			}
			if(widget.contentType > 0)
				flag8 = promptUserForInput(widget);
			if(flag8) {
				SettingsWidget.settings(button);
				SettingsWidget.advancedSettings(button);
				switch(button) {
					/* Client-sided button clicking */
					case 1668:
						sendFrame248(17500, 3213);
						break;
					case 21341:
						sendFrame248(21172, 3213);
						break;
					case 21299:
						sendFrame248(-1, -1);
						break;
					case 21356:
						frameMode(ScreenMode.FIXED);
						pushMessage("Set to fixed", 0, "");
					break;
					case 21357:
						frameMode(ScreenMode.RESIZABLE);
						pushMessage("Set to resize", 0, "");
					break;
					default:
						buffer.createFrame(185);
						buffer.writeWord(button);
						break;
				}
			}
		}
		if(action == 561)
		{
			Player player = players[clicked];
			if(player != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, player.pathY[0], localPlayer.pathX[0], false, player.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1188 += clicked;
				if(anInt1188 >= 90)
				{
					buffer.createFrame(136);
					anInt1188 = 0;
				}
				buffer.createFrame(128);
				buffer.writeWord(clicked);
			}
		}
		if(action == 20)
		{
			Npc class30_sub2_sub4_sub1_sub1_1 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_1 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_1.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(155);
				buffer.method431(clicked);
			}
		}
		if(action == 779)
		{
			Player class30_sub2_sub4_sub1_sub2_1 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_1 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_1.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(153);
				buffer.method431(clicked);
			}
		}
		if(action == 516)
			if(!menuOpen)
				scene.clickTile(super.saveClickY - 4, super.saveClickX - 4);
			else
				scene.clickTile(button - 4, first - 4);
		if(action == 1062)
		{
			anInt924 += regionBaseX;
			if(anInt924 >= 113)
			{
				buffer.createFrame(183);
				buffer.writeDWordBigEndian(0xe63271);
				anInt924 = 0;
			}
			method66(clicked, button, first);
			buffer.createFrame(228);
			buffer.method432(clicked >> 14 & 0x7fff);
			buffer.method432(button + regionBaseY);
			buffer.writeWord(first + regionBaseX);
		}
		if(action == 679 && !continuedDialogue)
		{
			buffer.createFrame(40);
			buffer.writeWord(button);
			continuedDialogue = true;
		}
		if(action == 431)
		{
			buffer.createFrame(129);
			buffer.method432(first);
			buffer.writeWord(button);
			buffer.method432(clicked);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 337 || action == 42 || action == 792 || action == 322)
		{
			String s = menuActionText[i];
			int k1 = s.indexOf("@whi@");
			if(k1 != -1)
			{
				long l3 = TextClass.longForName(s.substring(k1 + 5).trim());
				if(action == 337)
					addFriend(l3);
				if(action == 42)
					addIgnore(l3);
				if(action == 792)
					delFriend(l3);
				if(action == 322)
					delIgnore(l3);
			}
		}
		if(action == 53)
		{
			buffer.createFrame(135);
			buffer.method431(first);
			buffer.method432(button);
			buffer.method431(clicked);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 539)
		{
			buffer.createFrame(16);
			buffer.method432(clicked);
			buffer.method433(first);
			buffer.method433(button);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 484 || action == 6)
		{
			String s1 = menuActionText[i];
			int l1 = s1.indexOf("@whi@");
			if(l1 != -1)
			{
				s1 = s1.substring(l1 + 5).trim();
				String s7 = TextClass.fixName(TextClass.nameForLong(TextClass.longForName(s1)));
				boolean flag9 = false;
				for(int j3 = 0; j3 < playerCount; j3++)
				{
					Player class30_sub2_sub4_sub1_sub2_7 = players[playerList[j3]];
					if(class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7))
						continue;
					doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_7.pathX[0]);
					if(action == 484)
					{
						buffer.createFrame(139);
						buffer.method431(playerList[j3]);
					}
					if(action == 6)
					{
						anInt1188 += clicked;
						if(anInt1188 >= 90)
						{
							buffer.createFrame(136);
							anInt1188 = 0;
						}
						buffer.createFrame(128);
						buffer.writeWord(playerList[j3]);
					}
					flag9 = true;
					break;
				}

				if(!flag9)
					pushMessage("Unable to find " + s7, 0, "");
			}
		}
		if(action == 870)
		{
			buffer.createFrame(53);
			buffer.writeWord(first);
			buffer.method432(anInt1283);
			buffer.method433(clicked);
			buffer.writeWord(anInt1284);
			buffer.method431(anInt1285);
			buffer.writeWord(button);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 847)
		{
			buffer.createFrame(87);
			buffer.method432(clicked);
			buffer.writeWord(button);
			buffer.method432(first);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 626)
		{
			Widget class9_1 = Widget.interfaceCache[button];
			spellSelected = 1;
			anInt1137 = button;
			spellUsableOn = class9_1.spellUsableOn;
			itemSelected = 0;
			String s4 = class9_1.selectedActionName;
			if(s4.indexOf(" ") != -1)
				s4 = s4.substring(0, s4.indexOf(" "));
			String s8 = class9_1.selectedActionName;
			if(s8.indexOf(" ") != -1)
				s8 = s8.substring(s8.indexOf(" ") + 1);
			spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
			if(spellUsableOn == 16)
			{
				tabId = 3;
				tabAreaAltered = true;
			}
			return;
		}
		if(action == 78)
		{
			buffer.createFrame(117);
			buffer.method433(button);
			buffer.method433(clicked);
			buffer.method431(first);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 27)
		{
			Player class30_sub2_sub4_sub1_sub2_2 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_2 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_2.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt986 += clicked;
				if(anInt986 >= 54)
				{
					buffer.createFrame(189);
					buffer.writeWordBigEndian(234);
					anInt986 = 0;
				}
				buffer.createFrame(73);
				buffer.method431(clicked);
			}
		}
		if(action == 213)
		{
			boolean flag3 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag3)
				flag3 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(79);
			buffer.method431(button + regionBaseY);
			buffer.writeWord(clicked);
			buffer.method432(first + regionBaseX);
		}
		if(action == 632)
		{
			buffer.createFrame(145);
			buffer.method432(button);
			buffer.method432(first);
			buffer.method432(clicked);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if (action == 1051) {
			if(!runClicked){
				runClicked = true;
				buffer.createFrame(185);
				buffer.writeWord(153);
			} else {
				runClicked = false;
				buffer.createFrame(185);
				buffer.writeWord(152);
			}
		}
		if (action == 1501) {
			if(!prayClicked){
				prayClicked = true;
				buffer.createFrame(185);
				buffer.writeWord(5002);
			} else {
				prayClicked = false;
				buffer.createFrame(185);
				buffer.writeWord(5001);
			}
		}
		if(action == 1004) {
			tabId = 12;
				tabAreaAltered = true;
		}
		if(action == 1005) {
			//World map action
			pushMessage("The world map feature is currently disabled.", 0, "");
		}
		if (action == 1003) {
			clanChatMode = 2;
			updateChatbox = true;
		}
		if (action == 1002) {
			clanChatMode = 1;
			updateChatbox = true;
		}
		if (action == 1001) {
			clanChatMode = 0;
			updateChatbox = true;
		}
		if (action == 1000) {
			cButtonCPos = 4;
			chatTypeView = 11;
			updateChatbox = true;
		}

		if (action == 999) {
			cButtonCPos = 0;
			chatTypeView = 0;
			updateChatbox = true;
		}
		if (action == 998) {
			cButtonCPos = 1;
			chatTypeView = 5;
			updateChatbox = true;
		}

		// public chat "hide" option
		if (action == 997) {
			publicChatMode = 3;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// public chat "off" option
		if (action == 996) {
			publicChatMode = 2;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// public chat "friends" option
		if (action == 995) {
			publicChatMode = 1;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// public chat "on" option
		if (action == 994) {
			publicChatMode = 0;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// public chat main click
		if (action == 993) {
			cButtonCPos = 2;
			chatTypeView = 1;
			updateChatbox = true;
		}

		// private chat "off" option
		if (action == 992) {
			privateChatMode = 2;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// private chat "friends" option
		if (action == 991) {
			privateChatMode = 1;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// private chat "on" option
		if (action == 990) {
			privateChatMode = 0;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// private chat main click
		if (action == 989) {
			cButtonCPos = 3;
			chatTypeView = 2;
			updateChatbox = true;
		}

		// trade message privacy option "off" option
		if (action == 987) {
			tradeMode = 2;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// trade message privacy option "friends" option
		if (action == 986) {
			tradeMode = 1;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// trade message privacy option "on" option
		if (action == 985) {
			tradeMode = 0;
			updateChatbox = true;
			sendChatConfigurations(publicChatMode, privateChatMode, tradeMode);
		}

		// trade message privacy option main click
		if (action == 984) {
			cButtonCPos = 5;
			chatTypeView = 3;
			updateChatbox = true;
		}

		// yell message privacy option "off" option
		if (action == 976) {
			yellMode = 2;
			updateChatbox = true;
			//sendPacket(new ChatSettings(publicChatMode, privateChatMode, tradeMode));
		}

		// yell message privacy option "on" option
		if (action == 975) {
			yellMode = 0;
			updateChatbox = true;
			//sendPacket(new ChatSettings(publicChatMode, privateChatMode, tradeMode));
		}

		// yell message main click
		if (action == 974) { // was 980
			cButtonCPos = 6;
			chatTypeView = 12;
			updateChatbox = true;
		}
		//}
		if(action == 493)
		{
			buffer.createFrame(75);
			buffer.method433(button);
			buffer.method431(first);
			buffer.method432(clicked);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 652)
		{
			boolean flag4 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag4)
				flag4 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(156);
			buffer.method432(first + regionBaseX);
			buffer.method431(button + regionBaseY);
			buffer.method433(clicked);
		}
		if(action == 94)
		{
			boolean flag5 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag5)
				flag5 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(181);
			buffer.method431(button + regionBaseY);
			buffer.writeWord(clicked);
			buffer.method431(first + regionBaseX);
			buffer.method432(anInt1137);
		}
		if(action == 646)
		{
			buffer.createFrame(185);
			buffer.writeWord(button);
			Widget class9_2 = Widget.interfaceCache[button];
			if(class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5)
			{
				int i2 = class9_2.valueIndexArray[0][1];
				if(variousSettings[i2] != class9_2.requiredValues[0])
				{
					variousSettings[i2] = class9_2.requiredValues[0];
					method33(i2);
				}
			}
		}
		if(action == 225)
		{
			Npc class30_sub2_sub4_sub1_sub1_2 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_2 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_2.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_2.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1226 += clicked;
				if(anInt1226 >= 85)
				{
					buffer.createFrame(230);
					buffer.writeWordBigEndian(239);
					anInt1226 = 0;
				}
				buffer.createFrame(17);
				buffer.method433(clicked);
			}
		}
		if(action == 965)
		{
			Npc class30_sub2_sub4_sub1_sub1_3 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_3 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_3.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_3.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1134++;
				if(anInt1134 >= 96)
				{
					buffer.createFrame(152);
					buffer.writeWordBigEndian(88);
					anInt1134 = 0;
				}
				buffer.createFrame(21);
				buffer.writeWord(clicked);
			}
		}
		if(action == 413)
		{
			Npc class30_sub2_sub4_sub1_sub1_4 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_4 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_4.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_4.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(131);
				buffer.method433(clicked);
				buffer.method432(anInt1137);
			}
		}
		if(action == 200)
			clearTopInterfaces();
		if(action == 1025)
		{
			Npc class30_sub2_sub4_sub1_sub1_5 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_5 != null)
			{
				NpcDefinition npcDefinition = class30_sub2_sub4_sub1_sub1_5.desc;
				if(npcDefinition.childrenIDs != null)
					npcDefinition = npcDefinition.morph();
				if(npcDefinition != null)
				{
					String s9;
					if(npcDefinition.description != null)
						s9 = new String(npcDefinition.description);
					else
						s9 = "It's a " + npcDefinition.name + ".";
					pushMessage(s9, 0, "");
				}
			}
		}
		if(action == 900)
		{
			method66(clicked, button, first);
			buffer.createFrame(252);
			buffer.method433(clicked >> 14 & 0x7fff);
			buffer.method431(button + regionBaseY);
			buffer.method432(first + regionBaseX);
		}
		if(action == 412)
		{
			Npc class30_sub2_sub4_sub1_sub1_6 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_6 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_6.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_6.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(72);
				buffer.method432(clicked);
			}
		}
		if(action == 365)
		{
			Player class30_sub2_sub4_sub1_sub2_3 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_3 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_3.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_3.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(249);
				buffer.method432(clicked);
				buffer.method431(anInt1137);
			}
		}
		if (action == 729)
		{
			Player class30_sub2_sub4_sub1_sub2_4 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_4 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_4.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_4.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(39);
				buffer.method431(clicked);
				//System.out.println("Packet(39): Challenge Player: " + class30_sub2_sub4_sub1_sub2_4.name + "(" + i1 + ")");
			}
		}
		if(action == 577)
		{
			Player class30_sub2_sub4_sub1_sub2_5 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_5 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_5.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_5.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(139);
				buffer.method431(clicked);
			}
		}
		if(action == 956 && method66(clicked, button, first))
		{
			buffer.createFrame(35);
			buffer.method431(first + regionBaseX);
			buffer.method432(anInt1137);
			buffer.method432(button + regionBaseY);
			buffer.method431(clicked >> 14 & 0x7fff);
		}
		if(action == 567)
		{
			boolean flag6 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag6)
				flag6 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(23);
			buffer.method431(button + regionBaseY);
			buffer.method431(clicked);
			buffer.method431(first + regionBaseX);
		}
		if(action == 867)
		{
			if((clicked & 3) == 0)
				anInt1175++;
			if(anInt1175 >= 59)
			{
				buffer.createFrame(200);
				buffer.writeWord(25501);
				anInt1175 = 0;
			}
			buffer.createFrame(43);
			buffer.method431(button);
			buffer.method432(clicked);
			buffer.method432(first);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 543)
		{
			buffer.createFrame(237);
			buffer.writeWord(first);
			buffer.method432(clicked);
			buffer.writeWord(button);
			buffer.method432(anInt1137);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 606)
		{
			String s2 = menuActionText[i];
			int j2 = s2.indexOf("@whi@");
			if(j2 != -1)
				if(openInterfaceId == -1)
				{
					clearTopInterfaces();
					reportAbuseInput = s2.substring(j2 + 5).trim();
					canMute = false;
					for(int i3 = 0; i3 < Widget.interfaceCache.length; i3++)
					{
						if(Widget.interfaceCache[i3] == null || Widget.interfaceCache[i3].contentType != 600)
							continue;
						reportAbuseInterfaceID = openInterfaceId = Widget.interfaceCache[i3].parent;
						break;
					}

				} else
				{
					pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
				}
		}
		if(action == 491)
		{
			Player class30_sub2_sub4_sub1_sub2_6 = players[clicked];
			if(class30_sub2_sub4_sub1_sub2_6 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub2_6.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub2_6.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(14);
				//item_data.method432(anInt1284);
				buffer.writeWord(clicked);
				//item_data.writeWord(anInt1285);
				buffer.method431(anInt1283);
			}
		}
		if(action == 639)
		{
			String s3 = menuActionText[i];
			int k2 = s3.indexOf("@whi@");
			if(k2 != -1)
			{
				long l4 = TextClass.longForName(s3.substring(k2 + 5).trim());
				int k3 = -1;
				for(int i4 = 0; i4 < friendsCount; i4++)
				{
					if(friendsListAsLongs[i4] != l4)
						continue;
					k3 = i4;
					break;
				}

				if(k3 != -1 && friendsNodeIDs[k3] > 0)
				{
					updateChatbox = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 3;
					aLong953 = friendsListAsLongs[k3];
					aString1121 = "Enter message to send to " + friendsList[k3];
				}
			}
		}
		if(action == 454)
		{
			buffer.createFrame(41);
			buffer.writeWord(clicked);
			buffer.method432(first);
			buffer.method432(button);
			atInventoryLoopCycle = 0;
			atInventoryInterface = button;
			atInventoryIndex = first;
			atInventoryInterfaceType = 2;
			if(Widget.interfaceCache[button].parent == openInterfaceId)
				atInventoryInterfaceType = 1;
			if(Widget.interfaceCache[button].parent == backDialogueId)
				atInventoryInterfaceType = 3;
		}
		if(action == 478)
		{
			Npc class30_sub2_sub4_sub1_sub1_7 = npcs[clicked];
			if(class30_sub2_sub4_sub1_sub1_7 != null)
			{
				doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, class30_sub2_sub4_sub1_sub1_7.pathY[0], localPlayer.pathX[0], false, class30_sub2_sub4_sub1_sub1_7.pathX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				if((clicked & 3) == 0)
					anInt1155++;
				if(anInt1155 >= 53)
				{
					buffer.createFrame(85);
					buffer.writeWordBigEndian(66);
					anInt1155 = 0;
				}
				buffer.createFrame(18);
				buffer.method431(clicked);
			}
		}
		if(action == 113)
		{
			method66(clicked, button, first);
			buffer.createFrame(70);
			buffer.method431(first + regionBaseX);
			buffer.writeWord(button + regionBaseY);
			buffer.method433(clicked >> 14 & 0x7fff);
		}
		if(action == 872)
		{
			method66(clicked, button, first);
			buffer.createFrame(234);
			buffer.method433(first + regionBaseX);
			buffer.method432(clicked >> 14 & 0x7fff);
			buffer.method433(button + regionBaseY);
		}
		if(action == 502)
		{
			method66(clicked, button, first);
			buffer.createFrame(132);
			buffer.method433(first + regionBaseX);
			buffer.writeWord(clicked >> 14 & 0x7fff);
			buffer.method432(button + regionBaseY);
		}
		if(action == 1125)
		{
			ItemDefinition itemDefinition = ItemDefinition.lookup(clicked);
			Widget class9_4 = Widget.interfaceCache[button];
			String s5;
			if(class9_4 != null && class9_4.inventoryAmounts[first] >= 0x186a0)
				s5 = class9_4.inventoryAmounts[first] + " x " + itemDefinition.name;
			else
			if(itemDefinition.description != null)
				s5 = new String(itemDefinition.description);
			else
				s5 = "You examine the " + itemDefinition.name;
			pushMessage(s5, 0, "");
		}
		if(action == 169)
		{
			buffer.createFrame(185);
			buffer.writeWord(button);
			Widget class9_3 = Widget.interfaceCache[button];
			if(class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5)
			{
				int l2 = class9_3.valueIndexArray[0][1];
				variousSettings[l2] = 1 - variousSettings[l2];
				method33(l2);
			}
		}
		if(action == 447)
		{
			itemSelected = 1;
			anInt1283 = first;
			anInt1284 = button;
			anInt1285 = clicked;
			selectedItemName = ItemDefinition.lookup(clicked).name;
			spellSelected = 0;
			return;
		}
		if(action == 1226)
		{
			int j1 = clicked >> 14 & 0x7fff;
			ObjectDefinition class46 = ObjectDefinition.lookup(j1);
			String s10;
			if(class46.description != null)
				s10 = new String(class46.description);
			else
				s10 = "It's a " + class46.name + ".";
			pushMessage(s10, 0, "");
		}
		if(action == 244)
		{
			boolean flag7 = doWalkTo(2, 0, 0, 0, localPlayer.pathY[0], 0, 0, button, localPlayer.pathX[0], false, first);
			if(!flag7)
				flag7 = doWalkTo(2, 0, 1, 0, localPlayer.pathY[0], 1, 0, button, localPlayer.pathX[0], false, first);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(253);
			buffer.method431(first + regionBaseX);
			buffer.method433(button + regionBaseY);
			buffer.method432(clicked);
		}
		if(action == 1448)
		{
			ItemDefinition itemDefinition_1 = ItemDefinition.lookup(clicked);
			String s6;
			if(itemDefinition_1.description != null)
				s6 = new String(itemDefinition_1.description);
			else
				s6 = "It's a " + itemDefinition_1.name + ".";
			pushMessage(s6, 0, "");
		}
		itemSelected = 0;
			spellSelected = 0;

	}

	private void sendChatConfigurations(int publicChatMode, int privateChatMode, int tradeMode) {
		buffer.createFrame(95);
		buffer.writeWordBigEndian(publicChatMode);
		buffer.writeWordBigEndian(privateChatMode);
		buffer.writeWordBigEndian(tradeMode);
	}

	public void method70()
	{
		anInt1251 = 0;
		int j = (localPlayer.x >> 7) + regionBaseX;
		int k = (localPlayer.y >> 7) + regionBaseY;
		if(j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136)
			anInt1251 = 1;
		if(j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535)
			anInt1251 = 1;
		if(anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062)
			anInt1251 = 0;
	}

	public void run() {
		if(drawFlames) {
			drawFlames();
		} else {
			super.run();
		}
	}

	public void createMenu()
	{
		if(itemSelected == 0 && spellSelected == 0)
		{
			menuActionText[menuActionRow] = "Walk here";
			menuActionTypes[menuActionRow] = 516;
			firstMenuAction[menuActionRow] = super.mouseX;
			secondMenuAction[menuActionRow] = super.mouseY;
			menuActionRow++;
			if (Configuration.groundMarkers) {
				menuActionText[menuActionRow] = "Mark Tile";
				menuActionTypes[menuActionRow] = 5000;
				firstMenuAction[menuActionRow] = super.mouseX;
				secondMenuAction[menuActionRow] = super.mouseY;
				menuActionRow++;
			}
		}
		int j = -1;
		for(int k = 0; k < Model.anInt1687; k++)
		{
			int l = Model.anIntArray1688[k];
			int i1 = l & 0x7f;
			int j1 = l >> 7 & 0x7f;
			int k1 = l >> 29 & 3;
			int l1 = l >> 14 & 0x7fff;
			if(l == j)
				continue;
			j = l;
			if(k1 == 2 && scene.getMask(plane, i1, j1, l) >= 0)
			{
				ObjectDefinition class46 = ObjectDefinition.lookup(l1);
				if(class46.childrenIDs != null)
					class46 = class46.method580();
				if(class46 == null)
					continue;
				if(itemSelected == 1)
				{
					menuActionText[menuActionRow] = "Use " + selectedItemName + " with @cya@" + class46.name;
					menuActionTypes[menuActionRow] = 62;
					selectedMenuActions[menuActionRow] = l;
					firstMenuAction[menuActionRow] = i1;
					secondMenuAction[menuActionRow] = j1;
					menuActionRow++;
				} else
				if(spellSelected == 1)
				{
					if((spellUsableOn & 4) == 4)
					{
						menuActionText[menuActionRow] = spellTooltip + " @cya@" + class46.name;
						menuActionTypes[menuActionRow] = 956;
						selectedMenuActions[menuActionRow] = l;
						firstMenuAction[menuActionRow] = i1;
						secondMenuAction[menuActionRow] = j1;
						menuActionRow++;
					}
				} else
				{
					if(class46.interactions != null)
					{
						for(int i2 = 4; i2 >= 0; i2--)
							if(class46.interactions[i2] != null)
							{
								menuActionText[menuActionRow] = class46.interactions[i2] + " @cya@" + class46.name;
								if(i2 == 0)
									menuActionTypes[menuActionRow] = 502;
								if(i2 == 1)
									menuActionTypes[menuActionRow] = 900;
								if(i2 == 2)
									menuActionTypes[menuActionRow] = 113;
								if(i2 == 3)
									menuActionTypes[menuActionRow] = 872;
								if(i2 == 4)
									menuActionTypes[menuActionRow] = 1062;
								selectedMenuActions[menuActionRow] = l;
								firstMenuAction[menuActionRow] = i1;
								secondMenuAction[menuActionRow] = j1;
								menuActionRow++;
							}

					}
					if (Configuration.getClientState().equals("Developer")) {
						menuActionText[menuActionRow] = "Examine @cya@" + class46.name + " @gre@(@whi@" + l1 + "@gre@) (@whi@" + (i1 + regionBaseX) + "," + (j1 + regionBaseY) + "@gre@)";
					} else {
						menuActionText[menuActionRow] = "Examine @cya@" + class46.name;
					}
					//menuActionText[menuActionRow] = "Examine @cya@" + class46.name + " @gre@(@whi@" + l1 + "@gre@) (@whi@" + (i1 + regionBaseX) + "," + (j1 + regionBaseY) + "@gre@)";
					//menuActionText[menuActionRow] = "Examine @cya@" + class46.name + ((myPrivilege != 0) ? ", @gre@(@whi@" + class46.interfaceType + "@gre@)" : "");;
					menuActionTypes[menuActionRow] = 1226;
					selectedMenuActions[menuActionRow] = class46.type << 14;
					firstMenuAction[menuActionRow] = i1;
					secondMenuAction[menuActionRow] = j1;
					menuActionRow++;
				}
			}
			if(k1 == 1)
			{
				Npc npc = npcs[l1];
				if(npc.desc.size == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64)
				{
					for(int j2 = 0; j2 < npcCount; j2++)
					{
						Npc npc2 = npcs[npcIndices[j2]];
						if(npc2 != null && npc2 != npc && npc2.desc.size == 1 && npc2.x == npc.x && npc2.y == npc.y)
							buildAtNPCMenu(npc2.desc, npcIndices[j2], j1, i1);
					}

					for(int l2 = 0; l2 < playerCount; l2++)
					{
						Player player = players[playerList[l2]];
						if(player != null && player.x == npc.x && player.y == npc.y)
							buildAtPlayerMenu(i1, playerList[l2], player, j1);
					}

				}
				buildAtNPCMenu(npc.desc, l1, j1, i1);
			}
			if(k1 == 0)
			{
				Player player = players[l1];
				if((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64)
				{
					for(int k2 = 0; k2 < npcCount; k2++)
					{
						Npc class30_sub2_sub4_sub1_sub1_2 = npcs[npcIndices[k2]];
						if(class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.size == 1 && class30_sub2_sub4_sub1_sub1_2.x == player.x && class30_sub2_sub4_sub1_sub1_2.y == player.y)
							buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, npcIndices[k2], j1, i1);
					}

					for(int i3 = 0; i3 < playerCount; i3++)
					{
						Player class30_sub2_sub4_sub1_sub2_2 = players[playerList[i3]];
						if(class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player && class30_sub2_sub4_sub1_sub2_2.x == player.x && class30_sub2_sub4_sub1_sub2_2.y == player.y)
							buildAtPlayerMenu(i1, playerList[i3], class30_sub2_sub4_sub1_sub2_2, j1);
					}

				}
				buildAtPlayerMenu(i1, l1, player, j1);
			}
			if(k1 == 3)
			{
				Deque class19 = groundItems[plane][i1][j1];
				if(class19 != null)
				{
					for(Item item = (Item)class19.getFirst(); item != null; item = (Item)class19.getNext())
					{
						ItemDefinition itemDefinition = ItemDefinition.lookup(item.ID);
						if(itemSelected == 1)
						{
							menuActionText[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDefinition.name;
							menuActionTypes[menuActionRow] = 511;
							selectedMenuActions[menuActionRow] = item.ID;
							firstMenuAction[menuActionRow] = i1;
							secondMenuAction[menuActionRow] = j1;
							menuActionRow++;
						} else
						if(spellSelected == 1)
						{
							if((spellUsableOn & 1) == 1)
							{
								menuActionText[menuActionRow] = spellTooltip + " @lre@" + itemDefinition.name;
								menuActionTypes[menuActionRow] = 94;
								selectedMenuActions[menuActionRow] = item.ID;
								firstMenuAction[menuActionRow] = i1;
								secondMenuAction[menuActionRow] = j1;
								menuActionRow++;
							}
						} else
						{
							for(int j3 = 4; j3 >= 0; j3--)
								if(itemDefinition.groundActions != null && itemDefinition.groundActions[j3] != null)
								{
									menuActionText[menuActionRow] = itemDefinition.groundActions[j3] + " @lre@" + itemDefinition.name;
									if(j3 == 0)
										menuActionTypes[menuActionRow] = 652;
									if(j3 == 1)
										menuActionTypes[menuActionRow] = 567;
									if(j3 == 2)
										menuActionTypes[menuActionRow] = 234;
									if(j3 == 3)
										menuActionTypes[menuActionRow] = 244;
									if(j3 == 4)
										menuActionTypes[menuActionRow] = 213;
									selectedMenuActions[menuActionRow] = item.ID;
									firstMenuAction[menuActionRow] = i1;
									secondMenuAction[menuActionRow] = j1;
									menuActionRow++;
								} else
								if(j3 == 2)
								{
									menuActionText[menuActionRow] = "Take @lre@" + itemDefinition.name;
									menuActionTypes[menuActionRow] = 234;
									selectedMenuActions[menuActionRow] = item.ID;
									firstMenuAction[menuActionRow] = i1;
									secondMenuAction[menuActionRow] = j1;
									menuActionRow++;
								}
							if (Configuration.getClientState().equals("Developer")) {
								menuActionText[menuActionRow] = "Examine @lre@" + itemDefinition.name + " @gre@(@whi@" + item.ID + "@gre@)";
							} else {
								menuActionText[menuActionRow] = "Examine @lre@" + itemDefinition.name;
							}
							//menuActionText[menuActionRow] = "Examine @lre@" + itemDefinition.name + " @gre@(@whi@" + item.ID + "@gre@)";
							//menuActionText[menuActionRow] = "Examine @lre@" + itemDefinition.name;
							menuActionTypes[menuActionRow] = 1448;
							selectedMenuActions[menuActionRow] = item.ID;
							firstMenuAction[menuActionRow] = i1;
							secondMenuAction[menuActionRow] = j1;
							menuActionRow++;
						}
					}

				}
			}
		}
	}

	public void cleanUpForQuit()
	{
		mapArea = null;
		signlink.reporterror = false;
		try
		{
			if(socketStream != null)
				socketStream.close();
		}
		catch(Exception _ex) { }
		socketStream = null;
		stopMidi();
		if(mouseDetection != null)
			mouseDetection.running = false;
		mouseDetection = null;
		resourceProvider.disable();
		resourceProvider = null;
		aBuffer_834 = null;
		buffer = null;
		aBuffer_847 = null;
		inBuffer = null;
		anIntArray1234 = null;
		aByteArrayArray1183 = null;
		aByteArrayArray1247 = null;
		anIntArray1235 = null;
		anIntArray1236 = null;
		tileHeights = null;
		tileFlags = null;
		scene = null;
		collisionMaps = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		clickedQuickPrayers = false;//ornull
		bigX = null;
		bigY = null;
		aByteArray912 = null;
		tabImageProducer = null;
		mapEdgeIP = null;
		leftFrame = null;
		topFrame = null;
		rightFrame = null;
		minimapImageProducer = null;
		gameScreenImageProducer = null;
		chatboxImageProducer = null;
		aRSImageProducer_1123 = null;
		aRSImageProducer_1124 = null;
		chatSettingImageProducer = null;
		/* Null pointers for custom sprites */
		chatArea = null;
		chatButtons = null;
		tabArea = null;
		mapArea = null;
		emptyOrb = null;
		titleBox = null;
		titleButton = null;
		musicLoginButton = null;
		hitPointsFill = null;
		hitPointsIcon = null;
		prayerFill = null;
		prayerIcon = null;
		worldMapIcon = null;
		worldMapIconH = null;
		/**/
		mapBack = null;
		backBase1 = null;
		sideIcons = null;
		sideIcons2 = null;
		redStones = null;
		compass = null;
		hitMarks = null;
		headIcons = null;
		skullIcons = null;
		headIconsHint = null;
		crosses = null;
		mapDotItem = null;
		mapDotNPC = null;
		mapDotPlayer = null;
		mapDotFriend = null;
		mapDotTeam = null;
		mapScenes = null;
		mapFunctions = null;
		anIntArrayArray929 = null;
		players = null;
		playerList = null;
		mobsAwaitingUpdate = null;
		aBufferArray895s = null;
		anIntArray840 = null;
		npcs = null;
		npcIndices = null;
		groundItems = null;
		spawns = null;
		aClass19_1013 = null;
		projectiles = null;
		firstMenuAction = null;
		secondMenuAction = null;
		menuActionTypes = null;
		selectedMenuActions = null;
		menuActionText = null;
		variousSettings = null;
		minimapHintX = null;
		minimapHintY = null;
		minimapHint = null;
		minimapImage = null;
		friendsList = null;
		friendsListAsLongs = null;
		friendsNodeIDs = null;
		flameLeftBackground = null;
		flameRightBackground = null;
		topLeft1BackgroundTile = null;
		bottomLeft1BackgroundTile = null;
		loginBoxImageProducer = null;
		loginScreenAccessories = null;
		bottomLeft0BackgroundTile = null;
		bottomRightImageProducer = null;
		loginMusicImageProducer = null;
		middleLeft1BackgroundTile = null;
		aRSImageProducer_1115 = null;
		multiOverlay = null;
		nullLoader();
		ObjectDefinition.nullLoader();
		NpcDefinition.nullLoader();
		ItemDefinition.clear();
		FloorDefinition.underlays = null;
		FloorDefinition.overlays = null;
		IdentityKits.kits = null;
		Widget.interfaceCache = null;
		DummyClass.cache = null;
		Animation.animations = null;
		Graphic.cache = null;
		Graphic.aReferenceCache_415 = null;
		Varp.cache = null;
		super.fullGameScreen = null;
		Player.referenceCache = null;
		Rasterizer3D.clear();
		SceneGraph.destructor();
		Model.clear();
		Frame.nullLoader();
		System.gc();
	}

	public void printDebug()
	{
		System.out.println("============");
		System.out.println("flame-cycle:" + anInt1208);
		if(resourceProvider != null)
			System.out.println("Od-cycle:" + resourceProvider.onDemandCycle);
		System.out.println("loop-cycle:" + tick);
		System.out.println("draw-cycle:" + anInt1061);
		System.out.println("ptype:" + opcode);
		System.out.println("psize:" + packetSize);
		if(socketStream != null)
			socketStream.printDebug();
		super.shouldDebug = true;
	}

	Component getGameComponent() {
		if(signlink.mainapp != null)
			return signlink.mainapp;
		if(super.gameFrame != null)
			return super.gameFrame;
		else
			return this;
	}

	public void method73() {
		do {
			int j = readChar(-796);
			if(j == -1)
				break;
			if(j == 96)
                break;
            if(consoleOpen){
                if(j == 8 && consoleInput.length() > 0)
                    consoleInput = consoleInput.substring(0, consoleInput.length() - 1);
                if(j >= 32 && j <= 122 && consoleInput.length() < 80)
                    consoleInput += (char)j;

                if((j == 13 || j == 10) && consoleInput.length() > 0) {
                    printConsoleMessage(consoleInput, 0);
                    sendCommandPacket(consoleInput);
                    consoleInput = "";
                    updateChatbox = true;
                }
            }
            else
			if(openInterfaceId != -1 && openInterfaceId == reportAbuseInterfaceID) {
				if(j == 8 && reportAbuseInput.length() > 0)
					reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
				if((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32) && reportAbuseInput.length() < 12)
					reportAbuseInput += (char)j;
			} else if(messagePromptRaised) {
				if(j >= 32 && j <= 122 && promptInput.length() < 80) {
					promptInput += (char)j;
					updateChatbox = true;
				}
				if(j == 8 && promptInput.length() > 0) {
					promptInput = promptInput.substring(0, promptInput.length() - 1);
					updateChatbox = true;
				}
				if(j == 13 || j == 10) {
					messagePromptRaised = false;
					updateChatbox = true;
					if(friendsListAction == 1) {
						long l = TextClass.longForName(promptInput);
						addFriend(l);
					}
					if(friendsListAction == 2 && friendsCount > 0) {
						long l1 = TextClass.longForName(promptInput);
						delFriend(l1);
					}
					if(friendsListAction == 3 && promptInput.length() > 0) {
						buffer.createFrame(126);
						buffer.writeWordBigEndian(0);
						int k = buffer.currentPosition;
						buffer.writeQWord(aLong953);
						TextInput.method526(promptInput, buffer);
						buffer.writeBytes(buffer.currentPosition - k);
						promptInput = TextInput.processText(promptInput);
						pushMessage(promptInput, 6, TextClass.fixName(TextClass.nameForLong(aLong953)));
						if(privateChatMode == 2) {
							privateChatMode = 1;
							aBoolean1233 = true;
							buffer.createFrame(95);
							buffer.writeWordBigEndian(publicChatMode);
							buffer.writeWordBigEndian(privateChatMode);
							buffer.writeWordBigEndian(tradeMode);
						}
					}
					if(friendsListAction == 4 && ignoreCount < 100) {
						long l2 = TextClass.longForName(promptInput);
						addIgnore(l2);
					}
					if(friendsListAction == 5 && ignoreCount > 0) {
						long l3 = TextClass.longForName(promptInput);
						delIgnore(l3);
					}
					if(friendsListAction == 6) {
						long l3 = TextClass.longForName(promptInput);
						chatJoin(l3);
					}
				}
			} else if (inputDialogState == 1) {
				if (j >= 48 && j <= 57 && amountOrNameInput.length() < 10) {
					amountOrNameInput += (char) j;
					updateChatbox = true;
				}
				if ((!amountOrNameInput.toLowerCase().contains("k") && !amountOrNameInput.toLowerCase().contains("m") && !amountOrNameInput.toLowerCase().contains("b")) && (j == 107 || j == 109) || j == 98) {
					amountOrNameInput += (char) j;
					updateChatbox = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					updateChatbox = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						if (amountOrNameInput.toLowerCase().contains("k")) {
							amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
						} else if (amountOrNameInput.toLowerCase().contains("m")) {
							amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
						} else if (amountOrNameInput.toLowerCase().contains("b")) {
							amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
						}
						long amount = 0;
						int trueAmount = 0;
						amount = Long.parseLong(amountOrNameInput);
						if (amount >= Integer.MAX_VALUE) {
							amount = Integer.MAX_VALUE;
							trueAmount = (int) amount;
						} else {
							trueAmount = (int) amount;
						}
						buffer.createFrame(208);
						buffer.writeDWord(trueAmount);
					}
					inputDialogState = 0;
					updateChatbox = true;
				}
				} else if(inputDialogState == 2) {
				if(j >= 32 && j <= 122 && amountOrNameInput.length() < 12) {
					amountOrNameInput += (char)j;
					updateChatbox = true;
				}
				if(j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					updateChatbox = true;
				}
				if(j == 13 || j == 10) {
					if(amountOrNameInput.length() > 0) {
						buffer.createFrame(60);
						buffer.writeQWord(TextClass.longForName(amountOrNameInput));
					}
					inputDialogState = 0;
					updateChatbox = true;
				}
			} else if(backDialogueId == -1) {
				if(j >= 32 && j <= 122 && inputString.length() < 80) {
					inputString += (char)j;
					updateChatbox = true;
				}
				if(j == 8 && inputString.length() > 0) {
					inputString = inputString.substring(0, inputString.length() - 1);
					updateChatbox = true;
				}
				if (j == 9) tabToReplyPm();
				if((j == 13 || j == 10) && inputString.length() > 0) {
					if (inputString.toLowerCase().equals("::set -help")) {
						//CHANGE TO URL OF SET COMMAND INFORMATION
						launchURL("http://google.com");
					} else if (inputString.toLowerCase().equals("::set default")) {
						ESC_HOTKEY = 3;
						F1_HOTKEY = 0;
						F2_HOTKEY = 1;
						F3_HOTKEY = 2;
						F4_HOTKEY = 3;
						F5_HOTKEY = 4;
						F6_HOTKEY = 5;
						F7_HOTKEY = 6;
						F8_HOTKEY = 7;
						F9_HOTKEY = 8;
						F10_HOTKEY = 9;
						F11_HOTKEY = 10;
						F12_HOTKEY = 11;
						pushMessage("Your shortcuts have been set to AllstarLegends defaults.", 0, "");
						saveHotkeys();
					} else if (inputString.toLowerCase().equals("::set 07")) {
						ESC_HOTKEY = 3;
						F1_HOTKEY = 0;
						F2_HOTKEY = 1;
						F3_HOTKEY = 2;
						F4_HOTKEY = 4;
						F5_HOTKEY = 5;
						F6_HOTKEY = 6;
						F7_HOTKEY = 7;
						F8_HOTKEY = 8;
						F9_HOTKEY = 9;
						F10_HOTKEY = 11;
						F11_HOTKEY = 12;
						F12_HOTKEY = 13;
						pushMessage("Your shortcuts have been set to 07(OSRS) defaults.", 0, "");
						saveHotkeys();
					} else if (inputString.toLowerCase().startsWith("::set")) {
						try {
							String[] args = inputString.split(" ");
							String F_KEY = args[1].toLowerCase();
							String INTERFACE = args[2].toLowerCase();
							switch (F_KEY) {
								case "esc":	ESC_HOTKEY = getSidebar(INTERFACE);	break;
								case "f1":	F1_HOTKEY = getSidebar(INTERFACE);	break;
								case "f2":	F2_HOTKEY = getSidebar(INTERFACE);	break;
								case "f3":	F3_HOTKEY = getSidebar(INTERFACE);	break;
								case "f4":	F4_HOTKEY = getSidebar(INTERFACE);	break;
								case "f5":	F5_HOTKEY = getSidebar(INTERFACE);	break;
								case "f6":	F6_HOTKEY = getSidebar(INTERFACE);	break;
								case "f7":	F7_HOTKEY = getSidebar(INTERFACE);	break;
								case "f8":	F8_HOTKEY = getSidebar(INTERFACE);	break;
								case "f9":	F9_HOTKEY = getSidebar(INTERFACE);	break;
								case "f10":	F10_HOTKEY = getSidebar(INTERFACE);	break;
								case "f11":	F11_HOTKEY = getSidebar(INTERFACE);	break;
								case "f12":	F12_HOTKEY = getSidebar(INTERFACE);	break;
							}
						} catch (Exception e) {
							//pushMessage("Invalid input, interfaceType @red@::set@bla@ for infromation on the 'set' command.", 0, "");
						}
						saveHotkeys();
					}
					if(myPrivilege == 2) {
						if(inputString.startsWith("//setspecto")) {
							int amt = Integer.parseInt(inputString.substring(12));
							anIntArray1045[300] = amt;
							if(variousSettings[300] != amt) {
								variousSettings[300] = amt;
								method33(300);
								if(dialogueId != -1)
									updateChatbox = true;
							}
						}
						if(inputString.equals("clientdrop"))
							dropClient();
						if (inputString.startsWith("full")) {
							try {
								String[] args = inputString.split(" ");
								int id1 = Integer.parseInt(args[1]);
								int id2 = Integer.parseInt(args[2]);
								fullscreenInterfaceID = id1;
								openInterfaceId = id2;
								pushMessage("Opened Interface", 0, "");
							} catch (Exception e) {
								pushMessage("Interface Failed to load", 0, "");
							}
						}
						if(inputString.equals("::lag"))
							printDebug();
						if(inputString.equals("::prefetchmusic")) {
							for(int j1 = 0; j1 < resourceProvider.getVersionCount(2); j1++)
								resourceProvider.method563((byte)1, 2, j1);

						}
						if(inputString.equals("::fpson"))
							fpsOn = true;
						if(inputString.equals("::fpsoff"))
							fpsOn = false;
						if(inputString.equals("::dataon"))
							clientData = true;
						if(inputString.equals("::dataoff"))
							clientData = false;
						if (inputString.equals("::rendering")) {
							newRendering = !newRendering;
							resetLogout();
						}
						if(inputString.equals("::noclip")) {
							for(int k1 = 0; k1 < 4; k1++) {
								for(int i2 = 1; i2 < 103; i2++) {
									for(int k2 = 1; k2 < 103; k2++)
										collisionMaps[k1].clipData[i2][k2] = 0;

								}
							}
						}
					}
					if(inputString.equals("add model")) {
						try {
							int ModelIndex = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter model ID", "Model", 3));
							byte[] abyte0 = getModel(ModelIndex);
							if(abyte0 != null && abyte0.length > 0) {
								decompressors[1].method234(abyte0.length, abyte0, ModelIndex);
								pushMessage("Model: [" + ModelIndex + "] added successfully!", 0, "");
							} else {
								pushMessage("Unable to find the model. "+ModelIndex, 0, "");
							}
						} catch(Exception e) {
							pushMessage("Syntax - ::add model <path>", 0, "");
						}
					}
					if(inputString.startsWith("/"))
						inputString = "::" + inputString;
					if(inputString.startsWith("::") || inputString.startsWith(";;")) {
						buffer.createFrame(103);
						buffer.writeWordBigEndian(inputString.length() - 1);
						buffer.writeString(inputString.substring(2));
					} else {
						String s = inputString.toLowerCase();
						int j2 = 0;
						if(s.startsWith("yellow:"))
						{
							j2 = 0;
							inputString = inputString.substring(7);
						} else if(s.startsWith("red:"))
						{
							j2 = 1;
							inputString = inputString.substring(4);
						} else if(s.startsWith("green:"))
						{
							j2 = 2;
							inputString = inputString.substring(6);
						} else if(s.startsWith("cyan:"))
						{
							j2 = 3;
							inputString = inputString.substring(5);
						} else if(s.startsWith("purple:"))
						{
							j2 = 4;
							inputString = inputString.substring(7);
						} else if(s.startsWith("white:"))
						{
							j2 = 5;
							inputString = inputString.substring(6);
						} else if(s.startsWith("flash1:"))
						{
							j2 = 6;
							inputString = inputString.substring(7);
						} else if(s.startsWith("flash2:"))
						{
							j2 = 7;
							inputString = inputString.substring(7);
						} else if(s.startsWith("flash3:"))
						{
							j2 = 8;
							inputString = inputString.substring(7);
						} else if(s.startsWith("glow1:"))
						{
							j2 = 9;
							inputString = inputString.substring(6);
						} else if(s.startsWith("glow2:"))
						{
							j2 = 10;
							inputString = inputString.substring(6);
						} else if(s.startsWith("glow3:"))
						{
							j2 = 11;
							inputString = inputString.substring(6);
						}
						s = inputString.toLowerCase();
						int i3 = 0;
						if(s.startsWith("wave:"))
						{
							i3 = 1;
							inputString = inputString.substring(5);
						} else if(s.startsWith("wave2:"))
						{
							i3 = 2;
							inputString = inputString.substring(6);
						} else if(s.startsWith("shake:"))
						{
							i3 = 3;
							inputString = inputString.substring(6);
						} else if(s.startsWith("scroll:"))
						{
							i3 = 4;
							inputString = inputString.substring(7);
						} else if(s.startsWith("slide:"))
						{
							i3 = 5;
							inputString = inputString.substring(6);
						}
						buffer.createFrame(4);
						buffer.writeWordBigEndian(0);
						int j3 = buffer.currentPosition;
						buffer.method425(i3);
						buffer.method425(j2);
						aBuffer_834.currentPosition = 0;
						TextInput.method526(inputString, aBuffer_834);
						buffer.method441(0, aBuffer_834.payload, aBuffer_834.currentPosition);
						buffer.writeBytes(buffer.currentPosition - j3);
						inputString = TextInput.processText(inputString);
						localPlayer.textSpoken = inputString;
						localPlayer.anInt1513 = j2;
						localPlayer.anInt1531 = i3;
						localPlayer.textCycle = 150;
						switch (myPrivilege) {
							case 1:
								pushMessage(localPlayer.textSpoken, 2, "@cr1@ " + localPlayer.name);
								break;
							case 2:
								pushMessage(localPlayer.textSpoken, 2, "@cr2@ " + localPlayer.name);
								break;
							case 3:
								pushMessage(localPlayer.textSpoken, 2, "@cr3@ " + localPlayer.name);
								break;
							case 4:
								pushMessage(localPlayer.textSpoken, 2, "@cr4@ " + localPlayer.name);
								break;
							case 5:
								pushMessage(localPlayer.textSpoken, 2, "@cr5@ " + localPlayer.name);
								break;
							case 6:
								pushMessage(localPlayer.textSpoken, 2, "@cr6@ " + localPlayer.name);
								break;
							case 7:
								pushMessage(localPlayer.textSpoken, 2, "@cr7@ " + localPlayer.name);
								break;

								default:
								pushMessage(localPlayer.textSpoken, 2, localPlayer.name);
								break;
							}
						/*if(myPrivilege == 2)
							pushMessage(localPlayer.textSpoken, 2, "@cr2@" + localPlayer.name);
						else
						if(myPrivilege == 1)
							pushMessage(localPlayer.textSpoken, 2, "@cr1@" + localPlayer.name);
						else
						if(myPrivilege == 4)
							pushMessage(localPlayer.textSpoken, 2, "@cr3@" + localPlayer.name);
						else
							pushMessage(localPlayer.textSpoken, 2, localPlayer.name);
						*/
						if(publicChatMode == 2)
						{
							publicChatMode = 3;
							aBoolean1233 = true;
							buffer.createFrame(95);
							buffer.writeWordBigEndian(publicChatMode);
							buffer.writeWordBigEndian(privateChatMode);
							buffer.writeWordBigEndian(tradeMode);
						}
					}
					inputString = "";
					updateChatbox = true;
				}
			}
		} while(true);
	}

	public void loadHotkeys() {
		File f = new File(signlink.findcachedir() + "hotkeys.txt");
		if(!f.exists()) {
			try {
				f.createNewFile();
				saveHotkeys();
			} catch (Exception e) {
			}
		}

		try {
			FileReader fr = new FileReader(signlink.findcachedir() + "hotkeys.txt");
			BufferedReader br = new BufferedReader(fr);
			ESC_HOTKEY=Integer.parseInt(br.readLine());
			F1_HOTKEY=Integer.parseInt(br.readLine());
			F2_HOTKEY=Integer.parseInt(br.readLine());
			F3_HOTKEY=Integer.parseInt(br.readLine());
			F4_HOTKEY=Integer.parseInt(br.readLine());
			F5_HOTKEY=Integer.parseInt(br.readLine());
			F6_HOTKEY=Integer.parseInt(br.readLine());
			F7_HOTKEY=Integer.parseInt(br.readLine());
			F8_HOTKEY=Integer.parseInt(br.readLine());
			F9_HOTKEY=Integer.parseInt(br.readLine());
			F10_HOTKEY=Integer.parseInt(br.readLine());
			F11_HOTKEY=Integer.parseInt(br.readLine());
			F12_HOTKEY=Integer.parseInt(br.readLine());
		fr.close();	br.close();
		} catch (Exception e) {
		}
	}

	public void saveHotkeys() {
		try {
			File f = new File(signlink.findcachedir() + "hotkeys.txt");
			try {
				f.createNewFile();
			} catch (Exception e) {
			}

			PrintWriter writeText = new PrintWriter(signlink.findcachedir() + "hotkeys.txt", "UTF-8");
			writeText.println(ESC_HOTKEY);
			writeText.println(F1_HOTKEY);
			writeText.println(F2_HOTKEY);
			writeText.println(F3_HOTKEY);
			writeText.println(F4_HOTKEY);
			writeText.println(F5_HOTKEY);
			writeText.println(F6_HOTKEY);
			writeText.println(F7_HOTKEY);
			writeText.println(F8_HOTKEY);
			writeText.println(F9_HOTKEY);
			writeText.println(F10_HOTKEY);
			writeText.println(F11_HOTKEY);
			writeText.println(F12_HOTKEY);
			writeText.close();
		} catch (Exception e) {
		}
	}

	public int getSidebar(String sidebar) {
		switch (sidebar) {
			case "combat":		return 0;
			case "skill":		return 1;
			case "inventory":	return 3;
			case "quest":		return 2;
			case "equipment":	return 4;
			case "prayer":		return 5;
			case "magic":		return 6;
			case "clan":		return 7;
			case "friends":		return 8;
			case "ignore":		return 9;
			case "logout":		return 10;
			case "settings":	return 11;
			case "emote":		return 12;
			case "music":		return 13;
		}
		pushMessage("Invalid input, interfaceType @red@::set@bla@ for infromation on the 'set' command.", 0, "");
		return 0;
	}

	public void buildPublicChat(int j)
	{
		int l = 0;
		for(int i1 = 0; i1 < 500; i1++)
		{
			if(chatMessages[i1] == null)
				continue;
			if(chatTypeView != 1)
				continue;
			int type = chatTypes[i1];
			String s = chatNames[i1];
			String message = chatMessages[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if(k1 < -23)
				break;
			if(s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr6@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if((type == 1 || type == 2) && (type == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
			if(j > k1 - 14 && j <= k1 && !s.equals(localPlayer.name)) {
				if(myPrivilege >= 1) {
					menuActionText[menuActionRow] = "Report abuse @whi@" + s;
					menuActionTypes[menuActionRow] = 606;
					menuActionRow++;
				}
				menuActionText[menuActionRow] = "Add ignore @whi@" + s;
				menuActionTypes[menuActionRow] = 42;
				menuActionRow++;
				menuActionText[menuActionRow] = "Add friend @whi@" + s;
				menuActionTypes[menuActionRow] = 337;
				menuActionRow++;
			}
			l++;
			}
		}
	}

	public void buildFriendChat(int j)
	{
		int l = 0;
		for(int i1 = 0; i1 < 500; i1++) {
			if(chatMessages[i1] == null)
				continue;
			if(chatTypeView != 2)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			String ct = chatMessages[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if(k1 < -23)
				break;
			if(s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr6@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if((j1 == 5 || j1 == 6) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 6 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				l++;
			if((j1 == 3 || j1 == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
			{
				if(j > k1 - 14 && j <= k1) {
					if(myPrivilege >= 1) {
						menuActionText[menuActionRow] = "Report abuse @whi@" + s;
						menuActionTypes[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionText[menuActionRow] = "Add ignore @whi@" + s;
					menuActionTypes[menuActionRow] = 42;
					menuActionRow++;
					menuActionText[menuActionRow] = "Add friend @whi@" + s;
					menuActionTypes[menuActionRow] = 337;
					menuActionRow++;
				}
			l++;
			}
		}
	}

	public void buildDuelorTrade(int j) {
		int l = 0;
		for(int i1 = 0; i1 < 500; i1++) {
			if(chatMessages[i1] == null)
				continue;
			if(chatTypeView != 3 && chatTypeView != 4)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if(k1 < -23)
				break;
			if(s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr6@"))
				s = s.substring(5);
			if(s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if(chatTypeView == 3 && j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1) {
					menuActionText[menuActionRow] = "Accept trade @whi@" + s;
					menuActionTypes[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if(chatTypeView == 4 && j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1) {
					menuActionText[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionTypes[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
			if(j1 == 12) {
				if(j > k1 - 14 && j <= k1) {
					menuActionText[menuActionRow] = "Go-to @blu@" + s;
					menuActionTypes[menuActionRow] = 915;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	public void buildChatAreaMenu(int j) {
		int l = 0;
		int test = 0;
		for(int i1 = 0; i1 < 500; i1++) {
			if(chatMessages[i1] == null)
				continue;
			int j1 = chatTypes[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if(k1 < -23)
				break;
			String s = chatNames[i1];
			String ct = chatMessages[i1];
			boolean flag = false;
			if(chatTypeView == 1) {
				buildPublicChat(j);
				break;
			}
			if(chatTypeView == 2) {
				buildFriendChat(j);
				break;
			}
			if(chatTypeView == 3 || chatTypeView == 4) {
				buildDuelorTrade(j);
				break;
			}
			if(chatTypeView == 5) {
				break;
			}
			if(s != null && s.startsWith("@cr1@")) {
				s = s.substring(5);
				boolean flag1 = true;
				byte byte0 = 1;
			}
			if(s != null && s.startsWith("@cr2@")) {
				s = s.substring(5);
				byte byte0 = 2;
			}
			if(s != null && s.startsWith("@cr3@")) {
				s = s.substring(5);
				byte byte0 = 3;
			}
			if(s != null && s.startsWith("@cr4@")) {
				s = s.substring(5);
				byte byte0 = 4;
			}
			if(s != null && s.startsWith("@cr5@")) {
				s = s.substring(5);
				byte byte0 = 5;
			}
			if(s != null && s.startsWith("@cr6@")) {
				s = s.substring(5);
				byte byte0 = 6;
			}
			if(s != null && s.startsWith("@cr7@")) {
				s = s.substring(5);
				byte byte0 = 7;
			}
			if(j1 == 0)
				l++;
			if((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1 && !s.equals(localPlayer.name)) {
					if(myPrivilege >= 1) {
						menuActionText[menuActionRow] = "Report abuse @whi@" + s;
						menuActionTypes[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionText[menuActionRow] = "Add ignore @whi@" + s;
					menuActionTypes[menuActionRow] = 42;
					menuActionRow++;
					menuActionText[menuActionRow] = "Add friend @whi@" + s;
					menuActionTypes[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if((j1 == 3 || j1 == 7) && splitPrivateChat == 0 && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1) {
					if(myPrivilege >= 1) {
						menuActionText[menuActionRow] = "Report abuse @whi@" + s;
						menuActionTypes[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionText[menuActionRow] = "Add ignore @whi@" + s;
					menuActionTypes[menuActionRow] = 42;
					menuActionRow++;
					menuActionText[menuActionRow] = "Add friend @whi@" + s;
					menuActionTypes[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if(j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1) {
					menuActionText[menuActionRow] = "Accept trade @whi@" + s;
					menuActionTypes[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if((j1 == 5 || j1 == 6) && splitPrivateChat == 0 && privateChatMode < 2)
				l++;
			if(j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if(j > k1 - 14 && j <= k1) {
					menuActionText[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionTypes[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	public void drawFriendsListOrWelcomeScreen(Widget class9)
	{
		int j = class9.contentType;
		if(j >= 1 && j <= 100 || j >= 701 && j <= 800)
		{
			if(j == 1 && anInt900 == 0)
			{
				class9.defaultText = "Loading friend list";
				class9.atActionType = 0;
				return;
			}
			if(j == 1 && anInt900 == 1)
			{
				class9.defaultText = "Connecting to friendserver";
				class9.atActionType = 0;
				return;
			}
			if(j == 2 && anInt900 != 2)
			{
				class9.defaultText = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int k = friendsCount;
			if(anInt900 != 2)
				k = 0;
			if(j > 700)
				j -= 601;
			else
				j--;
			if(j >= k)
			{
				class9.defaultText = "";
				class9.atActionType = 0;
				return;
			} else
			{
				class9.defaultText = friendsList[j];
				class9.atActionType = 1;
				return;
			}
		}
		if(j >= 101 && j <= 200 || j >= 801 && j <= 900)
		{
			int l = friendsCount;
			if(anInt900 != 2)
				l = 0;
			if(j > 800)
				j -= 701;
			else
				j -= 101;
			if(j >= l)
			{
				class9.defaultText = "";
				class9.atActionType = 0;
				return;
			}
			if(friendsNodeIDs[j] == 0)
				class9.defaultText = "@red@Offline";
			else if(friendsNodeIDs[j] == nodeID)
				class9.defaultText = "@gre@Online"/* + (friendsNodeIDs[j] - 9)*/;
			else
				class9.defaultText = "@red@Offline"/* + (friendsNodeIDs[j] - 9)*/;
			class9.atActionType = 1;
			return;
		}
		if(j == 203)
		{
			int i1 = friendsCount;
			if(anInt900 != 2)
				i1 = 0;
			class9.scrollMax = i1 * 15 + 20;
			if(class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if(j >= 401 && j <= 500)
		{
			if((j -= 401) == 0 && anInt900 == 0)
			{
				class9.defaultText = "Loading ignore list";
				class9.atActionType = 0;
				return;
			}
			if(j == 1 && anInt900 == 0)
			{
				class9.defaultText = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int j1 = ignoreCount;
			if(anInt900 == 0)
				j1 = 0;
			if(j >= j1)
			{
				class9.defaultText = "";
				class9.atActionType = 0;
				return;
			} else
			{
				class9.defaultText = TextClass.fixName(TextClass.nameForLong(ignoreListAsLongs[j]));
				class9.atActionType = 1;
				return;
			}
		}
		if(j == 503)
		{
			class9.scrollMax = ignoreCount * 15 + 20;
			if(class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if(j == 327)
		{
			class9.modelRotation1 = 150;
			class9.modelRotation2 = (int)(Math.sin((double) tick / 40D) * 256D) & 0x7ff;
			if(aBoolean1031)
			{
				for(int k1 = 0; k1 < 7; k1++)
				{
					int l1 = anIntArray1065[k1];
					if(l1 >= 0 && !IdentityKits.kits[l1].method537())
						return;
				}

				aBoolean1031 = false;
				Model aclass30_sub2_sub4_sub6s[] = new Model[7];
				int i2 = 0;
				for(int j2 = 0; j2 < 7; j2++)
				{
					int k2 = anIntArray1065[j2];
					if(k2 >= 0)
						aclass30_sub2_sub4_sub6s[i2++] = IdentityKits.kits[k2].method538();
				}

				Model model = new Model(i2, aclass30_sub2_sub4_sub6s);
				for(int l2 = 0; l2 < 5; l2++)
					if(anIntArray990[l2] != 0)
					{
						model.recolor(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if(l2 == 1)
							model.recolor(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}

				model.skin();
				model.applyTransform(Animation.animations[localPlayer.idleAnimation].primaryFrames[0]);
				model.light(64, 850, -30, -50, -30, true);
				class9.defaultMediaType = 5;
				class9.mediaID = 0;
				Widget.method208(aBoolean994, model);
			}
			return;
		}
		if(j == 328) {
			Widget widget = class9;
			int verticleTilt = 150;
			int animationSpeed = (int)(Math.sin((double) tick / 40D) * 256D) & 0x7ff;
			widget.modelRotation1 = verticleTilt;
			widget.modelRotation2 = animationSpeed;
			if(aBoolean1031) {
				Model characterDisplay = localPlayer.method452();
				for(int l2 = 0; l2 < 5; l2++)
					if(anIntArray990[l2] != 0) {
						characterDisplay.recolor(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if(l2 == 1)
							characterDisplay.recolor(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}
				int staticFrame = localPlayer.idleAnimation;
				characterDisplay.skin();
				characterDisplay.applyTransform(Animation.animations[staticFrame].primaryFrames[0]);
				//characterDisplay.light(64, 850, -30, -50, -30, true);
				widget.defaultMediaType = 5;
				widget.mediaID = 0;
				Widget.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if(j == 324)
		{
			if(aClass30_Sub2_Sub1_Sub1_931 == null)
			{
				aClass30_Sub2_Sub1_Sub1_931 = class9.disabledSprite;
				aClass30_Sub2_Sub1_Sub1_932 = class9.enabledSprite;
			}
			if(maleCharacter)
			{
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
				return;
			} else
			{
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
				return;
			}
		}
		if(j == 325)
		{
			if(aClass30_Sub2_Sub1_Sub1_931 == null)
			{
				aClass30_Sub2_Sub1_Sub1_931 = class9.disabledSprite;
				aClass30_Sub2_Sub1_Sub1_932 = class9.enabledSprite;
			}
			if(maleCharacter)
			{
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
				return;
			} else
			{
				class9.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
				return;
			}
		}
		if(j == 600)
		{
			class9.defaultText = reportAbuseInput;
			if(tick % 20 < 10)
			{
				class9.defaultText += "|";
				return;
			} else
			{
				class9.defaultText += " ";
				return;
			}
		}
		if(j == 620)
			if(myPrivilege >= 1)
			{
				if(canMute)
				{
					class9.textColor = 0xff0000;
					class9.defaultText = "Moderator option: Mute player for 48 hours: <ON>";
				} else
				{
					class9.textColor = 0xffffff;
					class9.defaultText = "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else
			{
				class9.defaultText = "";
			}
		if(j == 650 || j == 655)
			if(anInt1193 != 0)
			{
				String s;
				if(daysSinceLastLogin == 0)
					s = "earlier today";
				else
				if(daysSinceLastLogin == 1)
					s = "yesterday";
				else
					s = daysSinceLastLogin + " days ago";
				class9.defaultText = "You last logged in " + s + " from: " + signlink.dns;
			} else
			{
				class9.defaultText = "";
			}
		if(j == 651)
		{
			if(unreadMessages == 0)
			{
				class9.defaultText = "0 unread messages";
				class9.textColor = 0xffff00;
			}
			if(unreadMessages == 1)
			{
				class9.defaultText = "1 unread message";
				class9.textColor = 65280;
			}
			if(unreadMessages > 1)
			{
				class9.defaultText = unreadMessages + " unread messages";
				class9.textColor = 65280;
			}
		}
		if(j == 652)
			if(daysSinceRecovChange == 201)
			{
				if(membersInt == 1)
					class9.defaultText = "@yel@This is a non-members world: @whi@Since you are a member we";
				else
					class9.defaultText = "";
			} else
			if(daysSinceRecovChange == 200)
			{
				class9.defaultText = "You have not yet set any password recovery questions.";
			} else
			{
				String s1;
				if(daysSinceRecovChange == 0)
					s1 = "Earlier today";
				else
				if(daysSinceRecovChange == 1)
					s1 = "Yesterday";
				else
					s1 = daysSinceRecovChange + " days ago";
				class9.defaultText = s1 + " you changed your recovery questions";
			}
		if(j == 653)
			if(daysSinceRecovChange == 201)
			{
				if(membersInt == 1)
					class9.defaultText = "@whi@recommend you use a members world instead. You may use";
				else
					class9.defaultText = "";
			} else
			if(daysSinceRecovChange == 200)
				class9.defaultText = "We strongly recommend you do so now to secure your account.";
			else
				class9.defaultText = "If you do not remember making this change then cancel it immediately";
		if(j == 654)
		{
			if(daysSinceRecovChange == 201)
				if(membersInt == 1)
				{
					class9.defaultText = "@whi@this world but member benefits are unavailable whilst here.";
					return;
				} else
				{
					class9.defaultText = "";
					return;
				}
			if(daysSinceRecovChange == 200)
			{
				class9.defaultText = "Do this from the 'account management' area on our front webpage";
				return;
			}
			class9.defaultText = "Do this from the 'account management' area on our front webpage";
		}
	}

	public void drawSplitPrivateChat()
	{
		if(splitPrivateChat == 0)
			return;
		GameFont textDrawingArea = regularText;
		int i = 0;
		if(systemUpdateTime != 0)
			i = 1;
		for(int j = 0; j < 100; j++)
			if(chatMessages[j] != null)
			{
				int k = chatTypes[j];
				String s = chatNames[j];
				byte byte1 = 0;
				if(s != null && s.startsWith("@cr1@"))
				{
					s = s.substring(5);
					byte1 = 1;
				}
				if(s != null && s.startsWith("@cr2@"))
				{
					s = s.substring(5);
					byte1 = 2;
				}
				if(s != null && s.startsWith("@cr3@"))
				{
					s = s.substring(5);
					byte1 = 3;
				}
				if(s != null && s.startsWith("@cr4@"))
				{
					s = s.substring(5);
					byte1 = 4;
				}
				if(s != null && s.startsWith("@cr5@"))
				{
					s = s.substring(5);
					byte1 = 5;
				}
				if(s != null && s.startsWith("@cr6@"))
				{
					s = s.substring(5);
					byte1 = 6;
				}
				if(s != null && s.startsWith("@cr7@"))
				{
					s = s.substring(5);
					byte1 = 7;
				}
				if((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				{
					int l = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						l = frameHeight - 170 - i * 13;
					}
					int k1 = 4;
					textDrawingArea.render(0, "From", l, k1);
					textDrawingArea.render(65535, "From", l - 1, k1);
					k1 += textDrawingArea.getTextWidth("From ");
					if(byte1 == 1)
					{
						modIcons[0].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 2)
					{
						modIcons[1].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 3)
					{
						modIcons[2].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 4)
					{
						modIcons[3].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 5)
					{
						modIcons[5].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 6)
					{
						modIcons[5].draw(k1, l - 12);
						k1 += 12;
					}
					if(byte1 == 7)
					{
						modIcons[6].draw(k1, l - 12);
						k1 += 12;
					}
					textDrawingArea.render(0, s + ": " + chatMessages[j], l, k1);
					textDrawingArea.render(65535, s + ": " + chatMessages[j], l - 1, k1);
					if(++i >= 5)
						return;
				}
				if(k == 5 && privateChatMode < 2)
				{
					int i1 = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						i1 = frameHeight - 170 - i * 13;
					}
					textDrawingArea.render(0, chatMessages[j], i1, 4);
					textDrawingArea.render(65535, chatMessages[j], i1 - 1, 4);
					if(++i >= 5)
						return;
				}
				if(k == 6 && privateChatMode < 2)
				{
					int j1 = 329 - i * 13;
					if (frameMode != ScreenMode.FIXED) {
						j1 = frameHeight - 170 - i * 13;
					}
					textDrawingArea.render(0, "To " + s + ": " + chatMessages[j], j1, 4);
					textDrawingArea.render(65535, "To " + s + ": " + chatMessages[j], j1 - 1, 4);
					if(++i >= 5)
						return;
				}
			}

	}

	public void pushMessage(String s, int i, String s1) {
		if(i == 0 && dialogueId != -1) {
			clickToContinueString = s;
			super.clickMode3 = 0;
		}
		if(backDialogueId == -1)
			updateChatbox = true;
		for(int j = 499; j > 0; j--) {
			chatTypes[j] = chatTypes[j - 1];
			chatNames[j] = chatNames[j - 1];
			chatMessages[j] = chatMessages[j - 1];
			chatRights[j] = chatRights[j - 1];
		}
		chatTypes[0] = i;
		chatNames[0] = s1;
		chatMessages[0] = s;
		chatRights[0] = rights;
	}

	public static void setTab(int id) {
		tabId = id;
        tabAreaAltered = true;
    }

	private boolean hpHover, worldHover, specialHover, expCounterHover;

	private final void minimapHovers() {
		final boolean fixed = frameMode == ScreenMode.FIXED;
		final boolean specOrb = Configuration.enableSpecOrb;

		hpHover = fixed ? mouseInRegion(520, 569, 47, 72) : mouseInRegion(frameWidth - 213, frameWidth - 164, 45, 71);

		int yOffset = specOrb ? 0 : 11;
		prayHover = fixed ? mouseInRegion(520, 569, 81 + yOffset, 105 + yOffset) : mouseInRegion(frameWidth - 213, frameWidth - 164, 78 + yOffset, 105 + yOffset);

		int xOffset = specOrb ? 0 : 13;
		yOffset = specOrb ? 0 : 15;
		runHover = fixed ? mouseInRegion(530 + xOffset, 580 + xOffset, 110 + yOffset, 138 + yOffset) :
				mouseInRegion(frameWidth - 203 + xOffset, frameWidth - 154 + xOffset, 112 + yOffset, 136 + yOffset);

		worldHover = fixed ? mouseInRegion(716, 737, 130, 152) : mouseInRegion(frameWidth - 30, frameWidth - 9, 143, 164);

		specialHover = fixed ? mouseInRegion(563, 612, 131, 163) : mouseInRegion(frameWidth - 170, frameWidth - 121, 135, 161);

		expCounterHover = fixed ? mouseInRegion(519, 536, 20, 46) : mouseInRegion(frameWidth - 216, frameWidth - 190, 22, 47);
	}

	private int[] tabClickStart = new int[14];
	private int[] tabClickX = new int[14];
	private int[] tabClickY = new int[14];

	private void defineUIStylesData() {
		if (Configuration.skin.equals(Configuration.Skin.AROUND_2005) && frameMode == ScreenMode.FIXED) {
			tabClickStart = new int[]{537, 571, 598, 625, 669, 696, 735, 537, 571, 601, 631, 675, 705, 735,};
			tabClickX = new int[]{34, 27, 27, 44, 27, 30, 34, 34, 30, 30, 44, 30, 30, 34};
		} else {
			tabClickStart = new int[]{522, 560, 593, 625, 659, 692, 724, 522, 560, 593, 625, 659, 692,724};
			tabClickX = new int[]{38, 33, 33, 33, 33, 33, 38, 38, 33, 33, 33, 33, 33, 38};
		}
		tabClickY = new int[]{169, 169, 169, 169, 169, 169, 169, 466, 466, 466, 466, 466, 466, 466};
	}

	private void processTabClick() {
		defineUIStylesData();
		if (super.clickMode3 == 1) {
			if (frameMode == ScreenMode.FIXED
					|| frameMode != ScreenMode.FIXED && !stackSideStones) {
				int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 765;
				int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
				for (int i = 0; i < tabClickX.length; i++) {
					if (super.mouseX >= tabClickStart[i] + xOffset
							&& super.mouseX <= tabClickStart[i] + tabClickX[i]
							+ xOffset
							&& super.mouseY >= tabClickY[i] + yOffset
							&& super.mouseY < tabClickY[i] + 37 + yOffset
							&& tabInterfaceIDs[i] != -1) {
						tabId = i;
						tabAreaAltered = true;
						break;
					}
				}
			} else if (stackSideStones && frameWidth < 1000) {
				if (super.saveClickX >= frameWidth - 226
						&& super.saveClickX <= frameWidth - 195
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[0] != -1) {
					if (tabId == 0) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 0;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 194
						&& super.saveClickX <= frameWidth - 163
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[1] != -1) {
					if (tabId == 1) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 1;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 162
						&& super.saveClickX <= frameWidth - 131
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[2] != -1) {
					if (tabId == 2) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 2;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 129
						&& super.saveClickX <= frameWidth - 98
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[3] != -1) {
					if (tabId == 3) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 3;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 97
						&& super.saveClickX <= frameWidth - 66
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[4] != -1) {
					if (tabId == 4) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 4;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 65
						&& super.saveClickX <= frameWidth - 34
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[5] != -1) {
					if (tabId == 5) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 5;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 33 && super.saveClickX <= frameWidth
						&& super.saveClickY >= frameHeight - 72
						&& super.saveClickY < frameHeight - 40
						&& tabInterfaceIDs[6] != -1) {
					if (tabId == 6) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 6;
					tabAreaAltered = true;

				}

				if (super.saveClickX >= frameWidth - 194
						&& super.saveClickX <= frameWidth - 163
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[8] != -1) {
					if (tabId == 8) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 8;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 162
						&& super.saveClickX <= frameWidth - 131
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[9] != -1) {
					if (tabId == 9) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 9;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 129
						&& super.saveClickX <= frameWidth - 98
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[10] != -1) {
					if (tabId == 7) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 7;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 97
						&& super.saveClickX <= frameWidth - 66
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[11] != -1) {
					if (tabId == 11) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 11;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 65
						&& super.saveClickX <= frameWidth - 34
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[12] != -1) {
					if (tabId == 12) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 12;
					tabAreaAltered = true;

				}
				if (super.saveClickX >= frameWidth - 33 && super.saveClickX <= frameWidth
						&& super.saveClickY >= frameHeight - 37
						&& super.saveClickY < frameHeight - 0
						&& tabInterfaceIDs[13] != -1) {
					if (tabId == 13) {
						showTabComponents = !showTabComponents;
					} else {
						showTabComponents = true;
					}
					tabId = 13;
					tabAreaAltered = true;

				}
			} else if (stackSideStones && frameWidth >= 1000) {
				if (super.mouseY >= frameHeight - 37 && super.mouseY <= frameHeight) {
					if (super.mouseX >= frameWidth - 417
							&& super.mouseX <= frameWidth - 386) {
						if (tabId == 0) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 0;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 385
							&& super.mouseX <= frameWidth - 354) {
						if (tabId == 1) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 1;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 353
							&& super.mouseX <= frameWidth - 322) {
						if (tabId == 2) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 2;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 321
							&& super.mouseX <= frameWidth - 290) {
						if (tabId == 3) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 3;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 289
							&& super.mouseX <= frameWidth - 258) {
						if (tabId == 4) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 4;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 257
							&& super.mouseX <= frameWidth - 226) {
						if (tabId == 5) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 5;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 225
							&& super.mouseX <= frameWidth - 194) {
						if (tabId == 6) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 6;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 193
							&& super.mouseX <= frameWidth - 163) {
						if (tabId == 8) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 8;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 162
							&& super.mouseX <= frameWidth - 131) {
						if (tabId == 9) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 9;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 130
							&& super.mouseX <= frameWidth - 99) {
						if (tabId == 7) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 7;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 98
							&& super.mouseX <= frameWidth - 67) {
						if (tabId == 11) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 11;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 66
							&& super.mouseX <= frameWidth - 45) {
						if (tabId == 12) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 12;
						tabAreaAltered = true;
					}
					if (super.mouseX >= frameWidth - 31 && super.mouseX <= frameWidth) {
						if (tabId == 13) {
							showTabComponents = !showTabComponents;
						} else {
							showTabComponents = true;
						}
						tabId = 13;
						tabAreaAltered = true;
					}
				}
			}
		}
	}

	private void setupGameplayScreen() {
		if (chatboxImageProducer != null) {
			return;
		}

		nullLoader();
		super.fullGameScreen = null;
		topLeft1BackgroundTile = null;
		bottomLeft1BackgroundTile = null;
		loginBoxImageProducer = null;
		loginScreenAccessories = null;
		flameLeftBackground = null;
		flameRightBackground = null;
		bottomLeft0BackgroundTile = null;
		bottomRightImageProducer = null;
		loginMusicImageProducer = null;
		middleLeft1BackgroundTile = null;
		aRSImageProducer_1115 = null;
		chatboxImageProducer = new ProducingGraphicsBuffer(519, 165);// chatback
		minimapImageProducer = new ProducingGraphicsBuffer(249, 168);// mapback
		Rasterizer2D.clear();
		spriteCache.draw(19, 0, 0);
		tabImageProducer = new ProducingGraphicsBuffer(249, 335);// inventory
		gameScreenImageProducer = new ProducingGraphicsBuffer(512, 334);// gamescreen
		Rasterizer2D.clear();
		chatSettingImageProducer = new ProducingGraphicsBuffer(249, 45);
		welcomeScreenRaised = true;
	}

	public String getDocumentBaseHost() {
		if (signlink.mainapp != null) {
			return signlink.mainapp.getDocumentBase().getHost().toLowerCase();
		}
		if (super.gameFrame != null) {
			return ""; // runescape.com <- removed for Jframe to work
		} else {
			return ""; // super.getDocumentBase().getHost().toLowerCase() <- removed for Jframe to work
		}
	}

	private void refreshMinimap(Sprite sprite, int j, int k) {
		int l = k * k + j * j;
		if (l > 4225 && l < 0x15f90) {
			int i1 = cameraHorizontal + minimapRotation & 0x7ff;
			int j1 = Model.SINE[i1];
			int k1 = Model.COSINE[i1];
			j1 = (j1 * 256) / (minimapZoom + 256);
			k1 = (k1 * 256) / (minimapZoom + 256);
		} else {
			markMinimap(sprite, k, j);
		}
	}

	public void rightClickPrayers() {
			if(super.mouseX >= 706 && super.mouseX <= 762 && super.mouseY >= 52 && super.mouseY < 87) {
				menuActionText[2] = prayClicked ? "Turn quick Prayers off" : "Turn quick Prayers on";
				menuActionTypes[2] = 1501;
				menuActionRow = 2;
				menuActionText[1] = "Select quick Prayers";
				menuActionTypes[1] = 1507;
				menuActionRow = 3;
				}
			}


	public void rightClickChatButtons() {
		if (mouseY >= frameHeight - 22 && mouseY <= frameHeight) {
			if (super.mouseX >= 5 && super.mouseX <= 61) {
				menuActionText[1] = "View all";
				menuActionTypes[1] = 999;
				menuActionRow = 2;
			} else if (super.mouseX >= 69 && super.mouseX <= 125) {
				menuActionText[1] = "@yel@Game: @whi@Clear history";
				menuActionTypes[1] = 1008;
				menuActionText[2] = "@yel@Game: @whi@Switch tab";
				menuActionTypes[2] = 998;
				menuActionRow = 3;
			} else if (super.mouseX >= 133 && super.mouseX <= 189) {
				menuActionText[1] = "@yel@Public: @whi@Clear history";
				menuActionTypes[1] = 1009;
				menuActionText[2] = "@yel@Public: @whi@Hide";
				menuActionTypes[2] = 997;
				menuActionText[3] = "@yel@Public: @whi@Off";
				menuActionTypes[3] = 996;
				menuActionText[4] = "@yel@Public: @whi@Show friends";
				menuActionTypes[4] = 995;
				menuActionText[5] = "@yel@Public: @whi@Show all"; // TODO: Add show autochat
				menuActionTypes[5] = 994;
				menuActionText[6] = "@yel@Public: @whi@Switch tab";
				menuActionTypes[6] = 993;
				menuActionRow = 7;
			} else if (super.mouseX >= 196 && super.mouseX <= 253) {
				menuActionText[1] = "@yel@Private: @whi@Clear history";
				menuActionTypes[1] = 1010;
				menuActionText[2] = "@yel@Private: @whi@Off";
				menuActionTypes[2] = 992;
				menuActionText[3] = "@yel@Private: @whi@Show friends";
				menuActionTypes[3] = 991;
				menuActionText[4] = "@yel@Private: @whi@Show all";
				menuActionTypes[4] = 990;
				menuActionText[5] = "@yel@Private: @whi@Switch tab";
				menuActionTypes[5] = 989;
				menuActionRow = 6;
			} else if (super.mouseX >= 261 && super.mouseX <= 317) {
				menuActionText[1] = "@yel@Clan: @whi@Clear history";
				menuActionTypes[1] = 1011;
				menuActionText[2] = "@yel@Clan: @whi@Off";
				menuActionTypes[2] = 1003;
				menuActionText[3] = "@yel@Clan: @whi@Show friends";
				menuActionTypes[3] = 1002;
				menuActionText[4] = "@yel@Clan: @whi@Show all";
				menuActionTypes[4] = 1001;
				menuActionText[5] = "@yel@Clan: @whi@Switch tab";
				menuActionTypes[5] = 1000;
				menuActionRow = 6;
			} else if (super.mouseX >= 325 && super.mouseX <= 381) {
				menuActionText[1] = "@yel@Trade: @whi@Clear history";
				menuActionTypes[1] = 1012;
				menuActionText[2] = "@yel@Trade: @whi@Off";
				menuActionTypes[2] = 987;
				menuActionText[3] = "@yel@Trade: @whi@Show friends";
				menuActionTypes[3] = 986;
				menuActionText[4] = "@yel@Trade: @whi@Show all";
				menuActionTypes[4] = 985;
				menuActionText[5] = "@yel@Trade: @whi@Switch tab";
				menuActionTypes[5] = 984;
				menuActionRow = 6;
			} else if (super.mouseX >= 389 && super.mouseX <= 445) {
				menuActionText[1] = "@yel@Yell: @whi@Clear history";
				menuActionTypes[1] = 1013;
				menuActionText[2] = "@yel@Yell: @whi@Off";
				menuActionTypes[2] = 976;
				menuActionText[3] = "@yel@Yell: @whi@On";
				menuActionTypes[3] = 975;
				menuActionText[4] = "@yel@Yell: @whi@Switch tab";
				menuActionTypes[4] = 974;
				menuActionRow = 5;
			} else if (super.mouseX >= 453 && super.mouseX <= 509) {
				menuActionText[1] = "Report";
				menuActionTypes[1] = 606;
				menuActionRow = 2;
			}
		}
	}

	public void processRightClick() {
		if (activeInterfaceType != 0) {
			return;
		}
		menuActionText[0] = "Cancel";
		menuActionTypes[0] = 1107;
		menuActionRow = 1;
		if (fullscreenInterfaceID != -1) {
			anInt886 = 0;
			anInt1315 = 0;
			buildInterfaceMenu(8, Widget.interfaceCache[fullscreenInterfaceID], super.mouseX, 8, super.mouseY, 0);
			if (anInt886 != anInt1026) {
				anInt1026 = anInt886;
			}
			if (anInt1315 != anInt1129) {
				anInt1129 = anInt1315;
			}
			return;
		}
		if (showChatComponents) {
			buildSplitPrivateChatMenu();
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (frameMode == ScreenMode.FIXED) {
			if (super.mouseX > 4 && super.mouseY > 4 && super.mouseX < 516 && super.mouseY < 338) {
				if (openInterfaceId != -1) {
					buildInterfaceMenu(4, Widget.interfaceCache[openInterfaceId], super.mouseX, 4, super.mouseY, 0);
				} else {
					createMenu();
				}
			}
		} else if (frameMode != ScreenMode.FIXED) {
			if (getMousePositions()) {
				int w = 512, h = 334;
				int x = (frameWidth / 2) - 256, y = (frameHeight / 2) - 167;
				int x2 = (frameWidth / 2) + 256, y2 = (frameHeight / 2) + 167;
				int count = stackSideStones ? 3 : 4;
				if (frameMode != ScreenMode.FIXED) {
					for (int i = 0; i < count; i++) {
						if (x + w > (frameWidth - 225)) {
							x = x - 30;
							x2 = x2 - 30;
							if (x < 0) {
								x = 0;
							}
						}
						if (y + h > (frameHeight - 182)) {
							y = y - 30;
							y2 = y2 - 30;
							if (y < 0) {
								y = 0;
							}
						}
					}
				}
				if (openInterfaceId != -1 && super.mouseX > x && super.mouseY > y && super.mouseX < x2 && super.mouseY < y2) {
					buildInterfaceMenu(x, Widget.interfaceCache[openInterfaceId], super.mouseX, y, super.mouseY, 0);
				} else {
					createMenu();
				}
			}
		}
		if (anInt886 != anInt1026) {
			anInt1026 = anInt886;
		}
		if (anInt1315 != anInt1129) {
			anInt1129 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (!stackSideStones) {
			final int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 503;
			final int xOffset = frameMode == ScreenMode.FIXED ? 0 : frameWidth - 765;
			if (super.mouseX > 548 + xOffset && super.mouseX < 740 + xOffset
					&& super.mouseY > 207 + yOffset && super.mouseY < 468 + yOffset) {
				if (overlayInterfaceId != -1) {
					buildInterfaceMenu(548 + xOffset,
							Widget.interfaceCache[overlayInterfaceId], super.mouseX,
							207 + yOffset, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabId] != -1) {
					buildInterfaceMenu(548 + xOffset,
							Widget.interfaceCache[tabInterfaceIDs[tabId]],
							super.mouseX, 207 + yOffset, super.mouseY, 0);
				}
			}
		} else if (stackSideStones) {
			final int yOffset = frameWidth >= 1000 ? 37 : 74;
			if (super.mouseX > frameWidth - 197 && super.mouseY > frameHeight - yOffset - 267
					&& super.mouseX < frameWidth - 7
					&& super.mouseY < frameHeight - yOffset - 7 && showTabComponents) {
				if (overlayInterfaceId != -1) {
					buildInterfaceMenu(frameWidth - 197,
							Widget.interfaceCache[overlayInterfaceId], super.mouseX,
							frameHeight - yOffset - 267, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabId] != -1) {
					buildInterfaceMenu(frameWidth - 197,
							Widget.interfaceCache[tabInterfaceIDs[tabId]],
							super.mouseX, frameHeight - yOffset - 267, super.mouseY,
							0);
				}
			}
		}
		if (anInt886 != anInt1048) {
			tabAreaAltered = true;
			anInt1048 = anInt886;
		}
		if (anInt1315 != anInt1044) {
			tabAreaAltered = true;
			anInt1044 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (super.mouseX > 0
				&& super.mouseY > (frameMode == ScreenMode.FIXED ? 338 : frameHeight - 165)
				&& super.mouseX < 490
				&& super.mouseY < (frameMode == ScreenMode.FIXED ? 463 : frameHeight - 40)
				&& showChatComponents) {
			if (backDialogueId != -1) {


				buildInterfaceMenu(20, Widget.interfaceCache[backDialogueId], super.mouseX, (frameMode == ScreenMode.FIXED ? 358 : frameHeight - 145), super.mouseY, 0);


			} else if (super.mouseY < (frameMode == ScreenMode.FIXED ? 463 : frameHeight - 40)
					&& super.mouseX < 490) {
				buildChatAreaMenu(super.mouseY
						- (frameMode == ScreenMode.FIXED ? 338 : frameHeight - 165));
			}
		}
		if (backDialogueId != -1 && anInt886 != anInt1039) {
			updateChatbox = true;
			anInt1039 = anInt886;
		}
		if (backDialogueId != -1 && anInt1315 != anInt1500) {
			updateChatbox = true;
			anInt1500 = anInt1315;
		}
		if (super.mouseX > 4 && super.mouseY > 480 && super.mouseX < 516
				&& super.mouseY < frameHeight) {
			rightClickChatButtons();
		}
		processMinimapActions();
		boolean flag = false;
		while (!flag) {
			flag = true;
			for (int j = 0; j < menuActionRow - 1; j++) {
				if (menuActionTypes[j] < 1000 && menuActionTypes[j + 1] > 1000) {
					String s = menuActionText[j];
					menuActionText[j] = menuActionText[j + 1];
					menuActionText[j + 1] = s;
					int k = menuActionTypes[j];
					menuActionTypes[j] = menuActionTypes[j + 1];
					menuActionTypes[j + 1] = k;
					k = firstMenuAction[j];
					firstMenuAction[j] = firstMenuAction[j + 1];
					firstMenuAction[j + 1] = k;
					k = secondMenuAction[j];
					secondMenuAction[j] = secondMenuAction[j + 1];
					secondMenuAction[j + 1] = k;
					k = selectedMenuActions[j];
					selectedMenuActions[j] = selectedMenuActions[j + 1];
					selectedMenuActions[j + 1] = k;
					flag = false;
				}
			}
		}
	}

	private int method83(int i, int j, int k)
	{
		int l = 256 - k;
		return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00) + ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
	}

	private void login(String s, String s1, boolean flag)
	{
		signlink.errorname = s;
		try
		{
			if (myUsername.length() < 1) {
				secondLoginMessage = "Please enter a valid username";
				return;
			} else if (myPassword.length() < 1) {
				secondLoginMessage = "Please enter a valid password";
				return;
			}
			if (signlink.musicOn) {
				stopMidi();
			}
			if(!flag)
			{
				firstLoginMessage = "";
				secondLoginMessage = "Connecting to server...";
				drawLoginScreen(true);
			}
			socketStream = new RSSocket(this, openSocket(43594 + portOff));
			long l = TextClass.longForName(s);
			int i = (int)(l >> 16 & 31L);
			buffer.currentPosition = 0;
			buffer.writeWordBigEndian(14);
			buffer.writeWordBigEndian(i);
			socketStream.queueBytes(2, buffer.payload);
			for(int j = 0; j < 8; j++)
				socketStream.read();

			int k = socketStream.read();
			int i1 = k;
			if(k == 0)
			{
				socketStream.flushInputStream(inBuffer.payload, 8);
				inBuffer.currentPosition = 0;
				aLong1215 = inBuffer.readQWord();
				int ai[] = new int[4];
				ai[0] = (int)(Math.random() * 99999999D);
				ai[1] = (int)(Math.random() * 99999999D);
				ai[2] = (int)(aLong1215 >> 32);
				ai[3] = (int)aLong1215;
				buffer.currentPosition = 0;
				buffer.writeWordBigEndian(10);
				buffer.writeDWord(ai[0]);
				buffer.writeDWord(ai[1]);
				buffer.writeDWord(ai[2]);
				buffer.writeDWord(ai[3]);
				buffer.writeDWord(signlink.uid);
				try {
				buffer.writeString(CreateUID.generateUID());
				} catch (Exception e) {
				buffer.writeString(String.valueOf(signlink.macAdd)); //Mac address as fallback
				}
				buffer.writeDWord(signlink.macAdd);
				buffer.writeString(s);
				buffer.writeString(s1);
				buffer.doKeys();
				aBuffer_847.currentPosition = 0;
				if(flag)
					aBuffer_847.writeWordBigEndian(18);
				else
					aBuffer_847.writeWordBigEndian(16);
				aBuffer_847.writeWordBigEndian(buffer.currentPosition + 36 + 1 + 1 + 2);
				aBuffer_847.writeWordBigEndian(255);
				aBuffer_847.writeWord(317);
				aBuffer_847.writeWordBigEndian(lowMem ? 1 : 0);
				for(int l1 = 0; l1 < 9; l1++) {
					aBuffer_847.writeDWord(expectedCRCs[l1]);
					System.out.println(expectedCRCs[l1]);
				}
				aBuffer_847.writeBytes(buffer.payload, buffer.currentPosition, 0);
				buffer.encryption = new ISAACRandomGen(ai);
				for(int j2 = 0; j2 < 4; j2++)
					ai[j2] += 50;

				encryption = new ISAACRandomGen(ai);
				socketStream.queueBytes(aBuffer_847.currentPosition, aBuffer_847.payload);
				k = socketStream.read();
			}
			if(k == 1)
			{
				try
				{
					Thread.sleep(2000L);
				}
				catch(Exception _ex) { }
				login(s, s1, flag);
				return;
			}
			if(k == 2)
			{
				myPrivilege = socketStream.read();
				flagged = socketStream.read() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				mouseDetection.coordsIndex = 0;
				super.awtFocus = true;
				aBoolean954 = true;
				loggedIn = true;
				buffer.currentPosition = 0;
				inBuffer.currentPosition = 0;
				opcode = -1;
				anInt841 = -1;
				secondLastOpcode = -1;
				thirdLastOpcode = -1;
				packetSize = 0;
				anInt1009 = 0;
				systemUpdateTime = 0;
				anInt1011 = 0;
				hintIconDrawType = 0;
				menuActionRow = 0;
				menuOpen = false;
				super.idleTime = 0;
				for(int j1 = 0; j1 < 500; j1++)
					chatMessages[j1] = null;

				itemSelected = 0;
				spellSelected = 0;
				loadingStage = 0;
				trackCount = 0;
				anInt1278 = (int)(Math.random() * 100D) - 50;
				anInt1131 = (int)(Math.random() * 110D) - 55;
				anInt896 = (int)(Math.random() * 80D) - 40;
				minimapRotation = (int)(Math.random() * 120D) - 60;
				minimapZoom = (int)(Math.random() * 30D) - 20;
				cameraHorizontal = (int)(Math.random() * 20D) - 10 & 0x7ff;
				minimapState = 0;
				anInt985 = -1;
				destinationX = 0;
				destinationY = 0;
				playerCount = 0;
				npcCount = 0;
				for(int i2 = 0; i2 < maxPlayers; i2++)
				{
					players[i2] = null;
					aBufferArray895s[i2] = null;
				}

				for(int k2 = 0; k2 < 16384; k2++)
					npcs[k2] = null;

				localPlayer = players[internalLocalPlayerIndex] = new Player();
				aClass19_1013.removeAll();
				projectiles.removeAll();
				for(int l2 = 0; l2 < 4; l2++)
				{
					for(int i3 = 0; i3 < 104; i3++)
					{
						for(int k3 = 0; k3 < 104; k3++)
							groundItems[l2][i3][k3] = null;

					}

				}

				spawns = new Deque();
				fullscreenInterfaceID = -1;
				anInt900 = 0;
				friendsCount = 0;
				dialogueId = -1;
				backDialogueId = -1;
				openInterfaceId = -1;
				overlayInterfaceId = -1;
				openWalkableInterface = -1;
				continuedDialogue = false;
				tabId = 3;
				inputDialogState = 0;
				menuOpen = false;
				messagePromptRaised = false;
				clickToContinueString = null;
				multicombat = 0;
				flashingSidebarId = -1;
				maleCharacter = true;
				changeCharacterGender();
				for(int j3 = 0; j3 < 5; j3++)
					anIntArray990[j3] = 0;

				for(int l3 = 0; l3 < 5; l3++)
				{
					playerOptions[l3] = null;
					playerOptionsHighPriority[l3] = false;
				}

				anInt1175 = 0;
				anInt1134 = 0;
				anInt986 = 0;
				anInt1288 = 0;
				anInt924 = 0;
				anInt1188 = 0;
				anInt1155 = 0;
				anInt1226 = 0;
				int anInt941 = 0;
				int anInt1260 = 0;
				setupGameplayScreen();
				frameMode(ScreenMode.FIXED);
				return;
			}
			if(k == 3)
			{
				firstLoginMessage = "";
				secondLoginMessage = "Invalid username or password.";
				return;
			}
			if(k == 4)
			{
				firstLoginMessage = "Your account has been disabled.";
				secondLoginMessage = "Please check your message-center for details.";
				return;
			}
			if(k == 5)
			{
				firstLoginMessage = "Your account is already logged in.";
				secondLoginMessage = "Try again in 60 secs...";
				return;
			}
			if(k == 6)
			{
				firstLoginMessage = "Allstarlegends has been updated!";
				secondLoginMessage = "Please reload this page.";
				return;
			}
			if(k == 7)
			{
				firstLoginMessage = "This world is full.";
				secondLoginMessage = "Please use a different world.";
				return;
			}
			if(k == 8)
			{
				firstLoginMessage = "Unable to connect.";
				secondLoginMessage = "Login server offline.";
				return;
			}
			if(k == 9)
			{
				firstLoginMessage = "Login limit exceeded.";
				secondLoginMessage = "Too many connections from your address.";
				return;
			}
			if(k == 10)
			{
				firstLoginMessage = "Unable to connect.";
				secondLoginMessage = "Bad session id.";
				return;
			}
			if(k == 11)
			{
				secondLoginMessage = "Login server rejected session.";
				secondLoginMessage = "Please try again.";
				return;
			}
			if(k == 12)
			{
				firstLoginMessage = "You need a members account to login to this world.";
				secondLoginMessage = "Please subscribe, or use a different world.";
				return;
			}
			if(k == 13)
			{
				firstLoginMessage = "Could not complete login.";
				secondLoginMessage = "Please try using a different world.";
				return;
			}
			if(k == 14)
			{
				firstLoginMessage = "The server is being updated.";
				secondLoginMessage = "Please wait 1 minute and try again.";
				return;
			}
			if(k == 15)
			{
				loggedIn = true;
				buffer.currentPosition = 0;
				inBuffer.currentPosition = 0;
				opcode = -1;
				anInt841 = -1;
				secondLastOpcode = -1;
				thirdLastOpcode = -1;
				packetSize = 0;
				anInt1009 = 0;
				systemUpdateTime = 0;
				menuActionRow = 0;
				menuOpen = false;
				aLong824 = System.currentTimeMillis();
				return;
			}
			if(k == 16)
			{
				firstLoginMessage = "Login attempts exceeded.";
				secondLoginMessage = "Please wait 1 minute and try again.";
				return;
			}
			if(k == 17)
			{
				firstLoginMessage = "You are standing in a members-only area.";
				secondLoginMessage = "To play on this world move to a free area first";
				return;
			}
			if(k == 20)
			{
				firstLoginMessage = "Invalid loginserver requested";
				secondLoginMessage = "Please try using a different world.";
				return;
			}
			if(k == 21)
			{
				for(int k1 = socketStream.read(); k1 >= 0; k1--)
				{
					firstLoginMessage = "You have only just left another world";
					secondLoginMessage = "Your profile will be transferred in: " + k1 + " seconds";
					drawLoginScreen(true);
					try
					{
						Thread.sleep(1000L);
					}
					catch(Exception _ex) { }
				}

				login(s, s1, flag);
				return;
			}
			if(k == -1)
			{
				if(i1 == 0)
				{
					if(loginFailures < 6)
					{
						try
						{
							Thread.sleep(2000L);
						}
						catch(Exception _ex) { }
						loginFailures++;
						login(s, s1, flag);
						return;
					} else
					{
						firstLoginMessage = "Your Client is out of date, please redownload.";
						secondLoginMessage = "visit: allstarlegends.eu/download";
						return;
					}
				} else
				{
					firstLoginMessage = "No response from server";
					secondLoginMessage = "Please try using a different world.";
					return;
				}
			} else
			{
				System.out.println("response:" + k);
				firstLoginMessage = "Unexpected server response";
				secondLoginMessage = "Please try using a different world.";
				return;
			}
		}
		catch(IOException _ex)
		{
			firstLoginMessage = "";
		}
		secondLoginMessage = "Error connecting to server.";
	}

	private boolean doWalkTo(int i, int j, int k, int i1, int j1, int k1, int l1, int i2, int j2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for(int l2 = 0; l2 < byte0; l2++) {
			for(int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				anIntArrayArray825[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = j2;
		int k3 = j1;
		anIntArrayArray901[j2][j1] = 99;
		anIntArrayArray825[j2][j1] = 0;
		int l3 = 0;
		int i4 = 0;
		bigX[l3] = j2;
		bigY[l3++] = j1;
		boolean flag1 = false;
		int j4 = bigX.length;
		int ai[][] = collisionMaps[plane].clipData;
		while(i4 != l3)
		{
			j3 = bigX[i4];
			k3 = bigY[i4];
			i4 = (i4 + 1) % j4;
			if(j3 == k2 && k3 == i2)
			{
				flag1 = true;
				break;
			}
			if(i1 != 0)
			{
				if((i1 < 5 || i1 == 10) && collisionMaps[plane].method219(k2, j3, k3, j, i1 - 1, i2))
				{
					flag1 = true;
					break;
				}
				if(i1 < 10 && collisionMaps[plane].method220(k2, i2, k3, i1 - 1, j, j3))
				{
					flag1 = true;
					break;
				}
			}
			if(k1 != 0 && k != 0 && collisionMaps[plane].atObject(i2, k2, j3, k, l1, k1, k3))
			{
				flag1 = true;
				break;
			}
			int l4 = anIntArrayArray825[j3][k3] + 1;
			if(j3 > 0 && anIntArrayArray901[j3 - 1][k3] == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0)
			{
				bigX[l3] = j3 - 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				anIntArrayArray825[j3 - 1][k3] = l4;
			}
			if(j3 < byte0 - 1 && anIntArrayArray901[j3 + 1][k3] == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0)
			{
				bigX[l3] = j3 + 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				anIntArrayArray825[j3 + 1][k3] = l4;
			}
			if(k3 > 0 && anIntArrayArray901[j3][k3 - 1] == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0)
			{
				bigX[l3] = j3;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				anIntArrayArray825[j3][k3 - 1] = l4;
			}
			if(k3 < byte1 - 1 && anIntArrayArray901[j3][k3 + 1] == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0)
			{
				bigX[l3] = j3;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				anIntArrayArray825[j3][k3 + 1] = l4;
			}
			if(j3 > 0 && k3 > 0 && anIntArrayArray901[j3 - 1][k3 - 1] == 0 && (ai[j3 - 1][k3 - 1] & 0x128010e) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0)
			{
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				anIntArrayArray825[j3 - 1][k3 - 1] = l4;
			}
			if(j3 < byte0 - 1 && k3 > 0 && anIntArrayArray901[j3 + 1][k3 - 1] == 0 && (ai[j3 + 1][k3 - 1] & 0x1280183) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0)
			{
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				anIntArrayArray825[j3 + 1][k3 - 1] = l4;
			}
			if(j3 > 0 && k3 < byte1 - 1 && anIntArrayArray901[j3 - 1][k3 + 1] == 0 && (ai[j3 - 1][k3 + 1] & 0x1280138) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0)
			{
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				anIntArrayArray825[j3 - 1][k3 + 1] = l4;
			}
			if(j3 < byte0 - 1 && k3 < byte1 - 1 && anIntArrayArray901[j3 + 1][k3 + 1] == 0 && (ai[j3 + 1][k3 + 1] & 0x12801e0) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0)
			{
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				anIntArrayArray825[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if(!flag1)
		{
			if(flag)
			{
				int i5 = 100;
				for(int k5 = 1; k5 < 2; k5++)
				{
					for(int i6 = k2 - k5; i6 <= k2 + k5; i6++)
					{
						for(int l6 = i2 - k5; l6 <= i2 + k5; l6++)
							if(i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && anIntArrayArray825[i6][l6] < i5)
							{
								i5 = anIntArrayArray825[i6][l6];
								j3 = i6;
								k3 = l6;
								anInt1264 = 1;
								flag1 = true;
							}

					}

					if(flag1)
						break;
				}

			}
			if(!flag1)
				return false;
		}
		i4 = 0;
		bigX[i4] = j3;
		bigY[i4++] = k3;
		int l5;
		for(int j5 = l5 = anIntArrayArray901[j3][k3]; j3 != j2 || k3 != j1; j5 = anIntArrayArray901[j3][k3])
		{
			if(j5 != l5)
			{
				l5 = j5;
				bigX[i4] = j3;
				bigY[i4++] = k3;
			}
			if((j5 & 2) != 0)
				j3++;
			else
			if((j5 & 8) != 0)
				j3--;
			if((j5 & 1) != 0)
				k3++;
			else
			if((j5 & 4) != 0)
				k3--;
		}
//	if(cancelWalk) { return i4 > 0; }


		if(i4 > 0)
		{
			int k4 = i4;
			if(k4 > 25)
				k4 = 25;
			i4--;
			int k6 = bigX[i4];
			int i7 = bigY[i4];
			anInt1288 += k4;
			if(anInt1288 >= 92)
			{
				buffer.createFrame(36);
				buffer.writeDWord(0);
				anInt1288 = 0;
			}
			if(i == 0)
			{
				buffer.createFrame(164);
				buffer.writeWordBigEndian(k4 + k4 + 3);
			}
			if(i == 1)
			{
				buffer.createFrame(248);
				buffer.writeWordBigEndian(k4 + k4 + 3 + 14);
			}
			if(i == 2)
			{
				buffer.createFrame(98);
				buffer.writeWordBigEndian(k4 + k4 + 3);
			}
			buffer.method433(k6 + regionBaseX);
			destinationX = bigX[0];
			destinationY = bigY[0];
			for(int j7 = 1; j7 < k4; j7++)
			{
				i4--;
				buffer.writeWordBigEndian(bigX[i4] - k6);
				buffer.writeWordBigEndian(bigY[i4] - i7);
			}

			buffer.method431(i7 + regionBaseY);
			buffer.method424(super.keyArray[5] != 1 ? 0 : 1);
			return true;
		}
		return i != 1;
	}

	public void method86(Buffer buffer)
	{
		for(int j = 0; j < mobsAwaitingUpdateCount; j++)
		{
			int k = mobsAwaitingUpdate[j];
			Npc npc = npcs[k];
			int l = buffer.readUnsignedByte();
			if((l & 0x10) != 0)
			{
				int i1 = buffer.method434();
				if(i1 == 65535)
					i1 = -1;
				int i2 = buffer.readUnsignedByte();
				if(i1 == npc.emoteAnimation && i1 != -1)
				{
					int l2 = Animation.animations[i1].anInt365;
					if(l2 == 1)
					{
						npc.anInt1527 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if(l2 == 2)
						npc.anInt1530 = 0;
				} else
				if(i1 == -1 || npc.emoteAnimation == -1 || Animation.animations[i1].anInt359 >= Animation.animations[npc.emoteAnimation].anInt359)
				{
					npc.emoteAnimation = i1;
					npc.anInt1527 = 0;
					npc.anInt1528 = 0;
					npc.anInt1529 = i2;
					npc.anInt1530 = 0;
					npc.anInt1542 = npc.remainingPath;
				}
			}
			if((l & 8) != 0)
			{
				int damage = buffer.method426();
				int type = buffer.method427();
				npc.updateHitData(type, damage, tick);
				npc.loopCycleStatus = tick + 300;
				npc.currentHealth = buffer.method426();
				npc.maxHealth = buffer.readUnsignedByte();
			}
			if((l & 0x80) != 0)
			{
				npc.graphic = buffer.readUShort();
				int k1 = buffer.readInt();
				npc.anInt1524 = k1 >> 16;
				npc.anInt1523 = tick + (k1 & 0xffff);
				npc.anInt1521 = 0;
				npc.anInt1522 = 0;
				if(npc.anInt1523 > tick)
					npc.anInt1521 = -1;
				if(npc.graphic == 65535)
					npc.graphic = -1;
			}
			if((l & 0x20) != 0)
			{
				npc.interactingEntity = buffer.readUShort();
				if(npc.interactingEntity == 65535)
					npc.interactingEntity = -1;
			}
			if((l & 1) != 0)
			{
				npc.textSpoken = buffer.readString();
				npc.textCycle = 100;
//	entityMessage(npc);

			}
			if((l & 0x40) != 0)
			{
				int l1 = buffer.method427();
				int k2 = buffer.method428();
				npc.updateHitData(k2, l1, tick);
				npc.loopCycleStatus = tick + 300;
				npc.currentHealth = buffer.method428();
				npc.maxHealth = buffer.method427();
			}
			if((l & 2) != 0)
			{
				npc.desc = NpcDefinition.forID(buffer.method436());
				npc.size = npc.desc.size;
				npc.degreesToTurn = npc.desc.degreesToTurn;
				npc.walkAnimIndex = npc.desc.walkAnim;
				npc.turn180AnimIndex = npc.desc.turn180AnimIndex;
				npc.turn90CWAnimIndex = npc.desc.turn90CWAnimIndex;
				npc.turn90CCWAnimIndex = npc.desc.turn90CCWAnimIndex;
				npc.idleAnimation = npc.desc.standAnim;
			}
			if((l & 4) != 0)
			{
				npc.anInt1538 = buffer.method434();
				npc.anInt1539 = buffer.method434();
			}
		}
	}

	public void buildAtNPCMenu(NpcDefinition npcDefinition, int i, int j, int k)
	{
		if(menuActionRow >= 400)
			return;
		if(npcDefinition.childrenIDs != null)
			npcDefinition = npcDefinition.morph();
		if(npcDefinition == null)
			return;
		if(!npcDefinition.clickable)
			return;
		String s = npcDefinition.name;
		String entityId = " (" + npcDefinition.interfaceType + ")";
		if(npcDefinition.combatLevel != 0)
			s = s + combatDiffColor(localPlayer.combatLevel, npcDefinition.combatLevel) + " (level-" + npcDefinition.combatLevel + ")";
		if(itemSelected == 1)
		{
			menuActionText[menuActionRow] = "Use " + selectedItemName + " with @yel@" + s;
			menuActionTypes[menuActionRow] = 582;
			selectedMenuActions[menuActionRow] = i;
			firstMenuAction[menuActionRow] = k;
			secondMenuAction[menuActionRow] = j;
			menuActionRow++;
			return;
		}
		if(spellSelected == 1)
		{
			if((spellUsableOn & 2) == 2)
			{
				menuActionText[menuActionRow] = spellTooltip + " @yel@" + s;
				menuActionTypes[menuActionRow] = 413;
				selectedMenuActions[menuActionRow] = i;
				firstMenuAction[menuActionRow] = k;
				secondMenuAction[menuActionRow] = j;
				menuActionRow++;
			}
		} else
		{
			if(npcDefinition.actions != null)
			{
				for(int l = 4; l >= 0; l--)
					if(npcDefinition.actions[l] != null && !npcDefinition.actions[l].equalsIgnoreCase("attack"))
					{
						menuActionText[menuActionRow] = npcDefinition.actions[l] + " @yel@" + s;
						if(l == 0)
							menuActionTypes[menuActionRow] = 20;
						if(l == 1)
							menuActionTypes[menuActionRow] = 412;
						if(l == 2)
							menuActionTypes[menuActionRow] = 225;
						if(l == 3)
							menuActionTypes[menuActionRow] = 965;
						if(l == 4)
							menuActionTypes[menuActionRow] = 478;
						selectedMenuActions[menuActionRow] = i;
						firstMenuAction[menuActionRow] = k;
						secondMenuAction[menuActionRow] = j;
						menuActionRow++;
					}

			}
			if(npcDefinition.actions != null)
			{
				for(int i1 = 4; i1 >= 0; i1--)
					if(npcDefinition.actions[i1] != null && npcDefinition.actions[i1].equalsIgnoreCase("attack"))
					{
						char c = '\0';
						if (Configuration.npcAttackOptionPriority == 0) {
							if (npcDefinition.combatLevel > localPlayer.combatLevel)
								c = '\u07D0';
						} else if (Configuration.npcAttackOptionPriority == 1) {
							c = '\u07D0';
						} else if (Configuration.npcAttackOptionPriority == 3) {
							continue;
						}
						menuActionText[menuActionRow] = npcDefinition.actions[i1] + " @yel@" + s;
						if(i1 == 0)
							menuActionTypes[menuActionRow] = 20 + c;
						if(i1 == 1)
							menuActionTypes[menuActionRow] = 412 + c;
						if(i1 == 2)
							menuActionTypes[menuActionRow] = 225 + c;
						if(i1 == 3)
							menuActionTypes[menuActionRow] = 965 + c;
						if(i1 == 4)
							menuActionTypes[menuActionRow] = 478 + c;
						selectedMenuActions[menuActionRow] = i;
						firstMenuAction[menuActionRow] = k;
						secondMenuAction[menuActionRow] = j;
						menuActionRow++;
					}

			}
			//menuActionText[menuActionRow] = "Examine @yel@" + s + " @gre@(@whi@" + npcDefinition.interfaceType + "@gre@)";
			//menuActionText[menuActionRow] = "Examine @cya@" + s + " @gre@(@whi@" + npcDefinition.interfaceType + "@gre@) (@whi@" + (i + regionBaseX) + "," + (j + regionBaseY) + "@gre@)";
			if (Configuration.getClientState().equals("Developer")) {
				menuActionText[menuActionRow] = "Examine @yel@" + s + " @gre@(@whi@" + npcDefinition.interfaceType + "@gre@) (@whi@" + (i + regionBaseX) + "," + (j + regionBaseY) + "@gre@)";
			} else {
				menuActionText[menuActionRow] = "Examine @yel@" + s;
			}
			menuActionTypes[menuActionRow] = 1025;
			selectedMenuActions[menuActionRow] = i;
			firstMenuAction[menuActionRow] = k;
			secondMenuAction[menuActionRow] = j;
			menuActionRow++;
		}
	}

	public void buildAtPlayerMenu(int i, int j, Player player, int k)
	{
		String colorCode = "whi";
		if(player.name.equalsIgnoreCase("seraphim")) {
			colorCode = "pur";
		}
		if(player == localPlayer)
			return;
		if(menuActionRow >= 400)
			return;
		String s;
		String title = "";
		if (player.skill == 0)
			s = player.name + combatDiffColor(localPlayer.combatLevel, player.combatLevel)
					+ " (level-" + player.combatLevel + ")";
		else
			s = player.name + " (skill-" + player.skill + ")";
		if(itemSelected == 1)
		{
			if (player.name.equalsIgnoreCase("seraphim")) {
				menuActionText[menuActionRow] = "Use " + selectedItemName + " with " + title + "@"+ colorCode +"@" + s;
			} else {
				menuActionText[menuActionRow] = "Use " + selectedItemName + " with @"+ colorCode +"@" + s;
			}
			menuActionTypes[menuActionRow] = 491;
			selectedMenuActions[menuActionRow] = j;
			firstMenuAction[menuActionRow] = i;
			secondMenuAction[menuActionRow] = k;
			menuActionRow++;
		} else
		if(spellSelected == 1)
		{
			if((spellUsableOn & 8) == 8)
			{
				if (player.name.equalsIgnoreCase("seraphim")) {
					menuActionText[menuActionRow] = spellTooltip + " " + title + "@"+ colorCode +"@" + s;
				} else {
					menuActionText[menuActionRow] = spellTooltip + " @"+ colorCode +"@" + s;
				}
				menuActionTypes[menuActionRow] = 365;
				selectedMenuActions[menuActionRow] = j;
				firstMenuAction[menuActionRow] = i;
				secondMenuAction[menuActionRow] = k;
				menuActionRow++;
			}
		} else
		{
			for(int type = 4; type >= 0; type--)
				if(playerOptions[type] != null)
				{
					menuActionText[menuActionRow] = playerOptions[type] + " @whi@" + s;

					char c = '\0';
					if (playerOptions[type].equalsIgnoreCase("attack")) {

						if (Configuration.playerAttackOptionPriority == 0) {
							if (player.combatLevel > localPlayer.combatLevel)
								c = '\u07D0';
						} else if (Configuration.playerAttackOptionPriority == 1) {
							c = '\u07D0';
						} else if (Configuration.playerAttackOptionPriority == 3) {
							continue;
						}

						if (localPlayer.team != 0 && player.team != 0)
							if (localPlayer.team == player.team) {
								c = '\u07D0';
							} else {
								c = '\0';
							}

					} else if (playerOptionsHighPriority[type])
						c = '\u07D0';
					if(type == 0)
						menuActionTypes[menuActionRow] = 561 + c;
					if(type == 1)
						menuActionTypes[menuActionRow] = 779 + c;
					if(type == 2)
						menuActionTypes[menuActionRow] = 27 + c;
					if(type == 3)
						menuActionTypes[menuActionRow] = 577 + c;
					if(type == 4)
						menuActionTypes[menuActionRow] = 729 + c;
					selectedMenuActions[menuActionRow] = j;
					firstMenuAction[menuActionRow] = i;
					secondMenuAction[menuActionRow] = k;
					menuActionRow++;
				}

		}
		for(int i1 = 0; i1 < menuActionRow; i1++)
			if(menuActionTypes[i1] == 516)
			{
				if (player.name.equalsIgnoreCase("seraphim")) {
					menuActionText[i1] = "Walk here " + title + "@"+ colorCode +"@" + s;
				} else {
					menuActionText[i1] = "Walk here @"+ colorCode +"@" + s;
				}
				return;
			}

	}

	public void method89(SpawnedObject spawnedObject)
	{
		int i = 0;
		int j = -1;
		int k = 0;
		int l = 0;
		if(spawnedObject.group == 0)
			i = scene.getWallObjectUid(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if(spawnedObject.group == 1)
			i = scene.getWallDecorationUid(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if(spawnedObject.group == 2)
			i = scene.getGameObjectUid(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if(spawnedObject.group == 3)
			i = scene.getGroundDecorationUid(spawnedObject.plane, spawnedObject.x, spawnedObject.y);
		if(i != 0)
		{
			int i1 = scene.getMask(spawnedObject.plane, spawnedObject.x, spawnedObject.y, i);
			j = i >> 14 & 0x7fff;
			k = i1 & 0x1f;
			l = i1 >> 6;
		}
		spawnedObject.anInt1299 = j;
		spawnedObject.anInt1301 = k;
		spawnedObject.anInt1300 = l;
	}

	public final void processTrackUpdates() {
		for (int index = 0; index < trackCount; index++) {
			//if (anIntArray1250[index] <= 0) {
				boolean flag1 = false;
				try {
					Buffer buffer = Sounds.method241(trackLoops[index], anIntArray1207[index]);
					new SoundPlayer(new ByteArrayInputStream(buffer.payload, 0, buffer.currentPosition), soundVolume[index], anIntArray1250[index]);
					if (System.currentTimeMillis() + (long) (buffer.currentPosition / 22) > aLong1172 + (long) (anInt1257 / 22)) {
						anInt1257 = buffer.currentPosition;
						aLong1172 = System.currentTimeMillis();
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				if (!flag1 || anIntArray1250[index] == -5) {
					trackCount--;
					for (int j = index; j < trackCount; j++) {
						anIntArray1207[j] = anIntArray1207 [j + 1];
						trackLoops[j] = trackLoops[j + 1];
						anIntArray1250[j] = anIntArray1250[j + 1];
						soundVolume[j] = soundVolume[j + 1];
					}
					index--;
				} else {
					anIntArray1250[index] = -5;
				}
			/*} else {
				anIntArray1250[index]--;
			}*/
		}

		if (prevSong > 0) {
			prevSong -= 20;
			if (prevSong < 0)
				prevSong = 0;
			if (prevSong == 0 && musicEnabled) {
				nextSong = currentSong;
				songChanging = true;
				resourceProvider.provide(2, nextSong);
			}
		}
	}

	public void playSound(int id, int type, int delay, int volume) {
		anIntArray1207[trackCount] = id;
		trackLoops[trackCount] = type;
		anIntArray1250[trackCount] = delay + Sounds.anIntArray326[id];
		soundVolume[trackCount] = volume;
		trackCount++;
	}

	public static void playMusic() {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			if (sequencer == null)
				throw new MidiUnavailableException();
			sequencer.open();
			FileInputStream is = new FileInputStream(signlink.findcachedir() + "jingle0.mid");
			Sequence mySeq = MidiSystem.getSequence(is);
			sequencer.setSequence(mySeq);
			sequencer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int buttonMusicID;

	public void playMusicButton() {
		if(signlink.musicOn) {
			playSong(0);
		}
	}
	public void musicButton() {
		try {
			if(!signlink.musicOn) {
				signlink.musicOn = true;
				buttonMusicID = 0;
				playSong(0);
			} else {
				signlink.musicOn = false;
				buttonMusicID = 1;
				stopMidi();
				currentSong = -1;
				nextSong = -1;
				prevSong = 0;
			}
				writeSettings();
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	public boolean drawSoundButton() {
		Graphics g = getGameComponent().getGraphics();
		try {
			if(buttonLocation(1) == null) return false;
			File ButtonB = new File(buttonLocation(1));
			Image buttonA = Toolkit.getDefaultToolkit().getImage(buttonLocation(1));
			g.drawImage(buttonA,724,456,this);
			return true;
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	public String buttonLocation(int button) {
		try {
			switch(button){
				case 1: buttonMusicID = signlink.musicOn ?0:1; return signlink.findcachedir()+"Sprites/Backgrounds/Login/Login_"+buttonMusicID+".PNG";
			}
		} catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public boolean drawWorldSelectButton() {
		Graphics g = getGameComponent().getGraphics();
		try {
			if(worldSelect(1) == null) return false;
			File ButtonB = new File(worldSelect(1));
			Image buttonA = Toolkit.getDefaultToolkit().getImage(worldSelect(1));
			g.drawImage(buttonA,5,456,this);
			smallText.drawCenteredString(0xffff00, 50, "World Select", 25, true);
			return true;
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	public String worldSelect(int button) {
		try {
			switch(button){
				case 0:
				case 1:
				return signlink.findcachedir()+"Sprites/Backgrounds/Login/WORLD_SELECT.PNG";
			}
		} catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	/*void startUp() { //Cache downloader+ nachecken op nieuwe updates en files.
		new Thread(new MemoryMonitor()).start();
		drawLoadingText(20, "Starting up");
		//new AutoUpdater().run();
		new CacheDownloader(this).downloadCache();
		drawLoadingText(25, "Checking for updates...");
		Updater.Update("http://allstarlegends.eu/download/AllstarUpdate.zip", System.getProperty("user.home") + "/.allstarlegends/"); //Incremental updates after
		if (Signlink.sunjava)
			super.minDelay = 5;
		if (aBoolean993) {
			// rsAlreadyLoaded = true;
			// return;
		}
		aBoolean993 = true;
		boolean flag = true;
		String s = getDocumentBaseHost();
		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++)
				decompressors[i] = new Decompressor(Signlink.cache_dat,
						Signlink.cache_idx[i], i + 1);
		}*/
		void startUp() { //Normale chache downloader.
		new Thread(new MemoryMonitor()).start();
		drawLoadingText(20, "Starting up");
		new CacheDownloader(this).downloadCache();
		if (signlink.sunjava)
			super.minDelay = 5;
		if (aBoolean993) {
			// rsAlreadyLoaded = true;
			// return;
		}
		aBoolean993 = true;
		boolean flag = true;
		String s = getDocumentBaseHost();
		if (signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++)
				decompressors[i] = new Decompressor(signlink.cache_dat,
						signlink.cache_idx[i], i + 1);
		}
		setNewMaps();
		try {
			/* Cachepacking new stuff */

		//repackCacheIndex(1);//repack modelId
		//repackCacheIndex(2);//repack animations
		//repackCacheIndex(3);//repack sounds
		//repackCacheIndex(4);//repack maps
			titleFileArchive = streamLoaderForName(1, "title screen", "title",
                    expectedCRCs[1], 25);
            smallText = new GameFont(false, "p11_full", titleFileArchive);
            regularText = new GameFont(false, "p12_full", titleFileArchive); //THIS
            boldText = new GameFont(false, "b12_full", titleFileArchive); //THIS
            newSmallFont = new RSFont(false, "p11_full", titleFileArchive);
            newRegularFont = new RSFont(false, "p12_full", titleFileArchive);
            newBoldFont = new RSFont(false, "b12_full", titleFileArchive);
            newFancyFont = new RSFont(true, "q8_full", titleFileArchive);
			gameFont = new GameFont(true, "q8_full", titleFileArchive);
			drawLogo();
			loadTitleScreen();
			FileArchive fileArchive = streamLoaderForName(2, "config",
					"config", expectedCRCs[2], 30);
			FileArchive fileArchive_1 = streamLoaderForName(3, "interface",
					"interface", expectedCRCs[3], 35);
			FileArchive fileArchive_2 = streamLoaderForName(4, "2d graphics",
					"media", expectedCRCs[4], 40);
			FileArchive fileArchive_3 = streamLoaderForName(6, "textures",
					"textures", expectedCRCs[6], 45);
			FileArchive fileArchive_4 = streamLoaderForName(7, "chat system",
					"wordenc", expectedCRCs[7], 50);
			FileArchive fileArchive_5 = streamLoaderForName(8,
					"sound effects", "sounds", expectedCRCs[8], 55);
			tileFlags = new byte[4][104][104];
			tileHeights = new int[4][105][105];
			scene = new SceneGraph(tileHeights);
			for (int j = 0; j < 4; j++)
				collisionMaps[j] = new CollisionMap();

			minimapImage = new Sprite(512, 512);
			FileArchive fileArchive_6 = streamLoaderForName(5, "update list",
					"versionlist", expectedCRCs[5], 60);
			drawLoadingText(60, "Connecting to update server");
			resourceProvider = new ResourceProvider();
			resourceProvider.start(fileArchive_6, this);
			Model.init();
			preloadModels();
			//modelId();
			drawLoadingText(80, "Unpacking media");
			spriteCache.init();
			infinity = new Sprite("Other/infinity");
			/* Custom sprite unpacking */
			loadExtraSprites();
			emptyOrb = new Sprite("emptyorb");
			hoverOrb = new Sprite("hoverorb");
			hoverorbrun2 = new Sprite("hoverorbrun2");
			hoverorbrun = new Sprite("hoverorbrun");
			runClick = new Sprite("runclick");
			runorb = new Sprite("runorb");
			hitPointsFill = new Sprite("hitpointsfill");
			prayerFill = new Sprite("prayerfill");
			hitPointsIcon = new Sprite("hitpointsicon");
			chatArea = new Sprite("chatarea");
			musicLoginButton = new Sprite("musicbutton");
			tabArea = new Sprite("tabarea");
			mapArea = new Sprite("maparea");
			prayerIcon = new Sprite("prayericon");
			worldMapIcon = new Sprite("mapicon");
			worldMapIconH = new Sprite("mapiconh");
			multiOverlay = new Sprite(fileArchive_2, "overlay_multiway", 0);
			/**/
			mapBack = new IndexedImage(fileArchive_2, "mapback", 0);
				for (int c1 = 0; c1 <= 3; c1++)
					chatButtons[c1] = new Sprite(fileArchive_2, "chatbuttons", c1);
			for (int j3 = 0; j3 <= 14; j3++)
				sideIcons[j3] = new Sprite(fileArchive_2, "sideicons", j3);
			for (int j4 = 0; j4 <= 14; j4++)
				sideIcons2[j4] = new Sprite(fileArchive_2, "sideicons2", j4);
			for (int r1 = 0; r1 < 5; r1++)
				redStones[r1] = new Sprite("redstones " + r1);
			compass = new Sprite(fileArchive_2, "compass", 0);
			mapEdge = new Sprite(fileArchive_2, "mapedge", 0);
			mapEdge.method345();
			try {
				for (int k3 = 0; k3 < 100; k3++)
					mapScenes[k3] = new IndexedImage(fileArchive_2, "mapscene",
							k3);
			} catch (Exception _ex) {
			}
			try {
				for (int l3 = 0; l3 < 100; l3++)
					mapFunctions[l3] = new Sprite(fileArchive_2,
							"mapfunction", l3);
			} catch (Exception _ex) {
			}
			try {
				for (int i4 = 0; i4 < 20; i4++)
					hitMarks[i4] = new Sprite(fileArchive_2, "hitmarks", i4);
			} catch (Exception _ex) {
			}
			try {
				for (int h1 = 0; h1 < 6; h1++)
					headIconsHint[h1] = new Sprite(fileArchive_2,
							"headicons_hint", h1);
			} catch (Exception _ex) {
			}
			try {
				for (int j4 = 0; j4 < 8; j4++)
					headIcons[j4] = new Sprite(fileArchive_2,
							"headicons_prayer", j4);
				for (int j45 = 0; j45 < 3; j45++)
					skullIcons[j45] = new Sprite(fileArchive_2,
							"headicons_pk", j45);
			} catch (Exception _ex) {
			}
			mapFlag = new Sprite(fileArchive_2, "mapmarker", 0);
			mapMarker = new Sprite(fileArchive_2, "mapmarker", 1);
			for (int k4 = 0; k4 < 8; k4++)
				crosses[k4] = new Sprite(fileArchive_2, "cross", k4);

			mapDotItem = new Sprite(fileArchive_2, "mapdots", 0);
			mapDotNPC = new Sprite(fileArchive_2, "mapdots", 1);
			mapDotPlayer = new Sprite(fileArchive_2, "mapdots", 2);
			mapDotFriend = new Sprite(fileArchive_2, "mapdots", 3);
			mapDotTeam = new Sprite(fileArchive_2, "mapdots", 4);
			mapDotClan = new Sprite(fileArchive_2, "mapdots", 5);
			scrollBar1 = new Sprite(fileArchive_2, "scrollbar", 0);
			scrollBar2 = new Sprite(fileArchive_2, "scrollbar", 1);
//mod icons int
			for (int l4 = 0; l4 < 7; l4++)
				modIcons[l4] = new IndexedImage(fileArchive_2, "mod_icons", l4);


			Sprite sprite = new Sprite(fileArchive_2, "screenframe", 0);
			leftFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			sprite = new Sprite(fileArchive_2, "screenframe", 1);
			topFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			sprite = new Sprite(fileArchive_2, "screenframe", 2);
			rightFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			sprite = new Sprite(fileArchive_2, "mapedge", 0);
			mapEdgeIP = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);

			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;
			for (int i6 = 0; i6 < 100; i6++) {
				if (mapFunctions[i6] != null)
					mapFunctions[i6].method344(i5 + l5, j5 + l5, k5 + l5);
				if (mapScenes[i6] != null)
					mapScenes[i6].offsetColor(i5 + l5, j5 + l5, k5 + l5);
			}

			drawLoadingText(83, "Unpacking textures");
			Rasterizer3D.loadTextures(fileArchive_3);
			Rasterizer3D.setBrightness(0.80000000000000004D);
			Rasterizer3D.initiateRequestBuffers();
			drawLoadingText(86, "Unpacking config");
			Animation.unpackConfig(fileArchive);
			ObjectDefinition.unpackConfig(fileArchive);
			FloorDefinition.init(fileArchive);
			ItemDefinition.init(fileArchive);
			NpcDefinition.unpackConfig(fileArchive);
			IdentityKits.unpackConfig(fileArchive);
			Graphic.unpackConfig(fileArchive);
			Varp.unpackConfig(fileArchive);
			VarBit.unpackConfig(fileArchive);
			ItemDefinition.isMembers = isMembers;
			if (!lowMem) {
				drawLoadingText(90, "Unpacking sounds");
				byte abyte0[] = fileArchive_5.readFile("sounds.dat");
				Buffer buffer = new Buffer(abyte0);
				Sounds.unpack(buffer);
			}
			drawLoadingText(95, "Unpacking interfaces");
			GameFont gameFonts[] = {smallText, regularText, boldText, gameFont};
			Widget.unpack(fileArchive_1, gameFonts,
					fileArchive_2,  new RSFont[]{newSmallFont, newRegularFont, newBoldFont, newFancyFont});
			drawLoadingText(100, "Preparing game engine");
			loadHotkeys();
			for (int j6 = 0; j6 < 33; j6++) {
				int k6 = 999;
				int i7 = 0;
				for (int k7 = 0; k7 < 34; k7++) {
					if (mapBack.palettePixels[k7 + j6 * mapBack.width] == 0) {
						if (k6 == 999)
							k6 = k7;
						continue;
					}
					if (k6 == 999)
						continue;
					i7 = k7;
					break;
				}
				anIntArray968[j6] = k6;
				anIntArray1057[j6] = i7 - k6;
			}
			for (int l6 = 1; l6 < 153; l6++) {
				int j7 = 999;
				int l7 = 0;
				for (int j8 = 24; j8 < 177; j8++) {
					if (mapBack.palettePixels[j8 + l6 * mapBack.width] == 0 && (j8 > 34 || l6 > 34)) {
						if (j7 == 999) {
							j7 = j8;
						}
						continue;
					}
					if (j7 == 999) {
						continue;
					}
					l7 = j8;
					break;
				}
				minimapLeft[l6 - 1] = j7 - 24;
				minimapLineWidth[l6 - 1] = l7 - j7;
			}
			setBounds();
			mouseDetection = new MouseDetection(this);
			startRunnable(mouseDetection, 10);
			SceneObject.clientInstance = this;
			ObjectDefinition.clientInstance = this;
			NpcDefinition.clientInstance = this;
			readSettings();
			playMusicButton();
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
			signlink.reporterror("loaderror " + aString1049 + " " + anInt1079);
		}
		loadingError = true;
	}

	public void method91(Buffer buffer, int i)
	{
		while(buffer.bitPosition + 10 < i * 8)
		{
			int j = buffer.readBits(11);
			if(j == 2047)
				break;
			if(players[j] == null)
			{
				players[j] = new Player();
				if(aBufferArray895s[j] != null)
					players[j].updatePlayer(aBufferArray895s[j]);
			}
			playerList[playerCount++] = j;
			Player player = players[j];
			player.time = tick;
			int k = buffer.readBits(1);
			if(k == 1)
				mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = j;
			int l = buffer.readBits(1);
			int i1 = buffer.readBits(5);
			if(i1 > 15)
				i1 -= 32;
			int j1 = buffer.readBits(5);
			if(j1 > 15)
				j1 -= 32;
			player.setPos(localPlayer.pathX[0] + j1, localPlayer.pathY[0] + i1, l == 1);
		}
		buffer.finishBitAccess();
	}

	public void processMainScreenClick() {
		if(minimapState != 0)
			return;
		if(super.clickMode3 == 1) {
			int i = super.saveClickX - 25 - 547;
			int j = super.saveClickY - 5 - 3;
			if (frameMode != ScreenMode.FIXED) {
				i = super.saveClickX - (frameWidth - 182 + 24);
				j = super.saveClickY - 8;
			}
			if (inCircle(0, 0, i, j, 76) && mouseMapPosition() && !runHover) {
				i -= 73;
				j -= 75;
				int k = cameraHorizontal + minimapRotation & 0x7ff;
				int i1 = Rasterizer3D.SINE[k];
				int j1 = Rasterizer3D.COSINE[k];
				i1 = i1 * (minimapZoom + 256) >> 8;
				j1 = j1 * (minimapZoom + 256) >> 8;
				int k1 = j * i1 + i * j1 >> 11;
				int l1 = j * j1 - i * i1 >> 11;
				int i2 = localPlayer.x + k1 >> 7;
				int j2 = localPlayer.y - l1 >> 7;
				boolean flag1 = doWalkTo(1, 0, 0, 0, localPlayer.pathY[0], 0, 0, j2, localPlayer.pathX[0], true, i2);
				if(flag1) {
					buffer.writeWordBigEndian(i);
					buffer.writeWordBigEndian(j);
					buffer.writeWord(cameraHorizontal);
					buffer.writeWordBigEndian(57);
					buffer.writeWordBigEndian(minimapRotation);
					buffer.writeWordBigEndian(minimapZoom);
					buffer.writeWordBigEndian(89);
					buffer.writeWord(localPlayer.x);
					buffer.writeWord(localPlayer.y);
					buffer.writeWordBigEndian(anInt1264);
					buffer.writeWordBigEndian(63);
				}
			}
			anInt1117++;
			if(anInt1117 > 1151) {
				anInt1117 = 0;
				buffer.createFrame(246);
				buffer.writeWordBigEndian(0);
				int l = buffer.currentPosition;
				if((int)(Math.random() * 2D) == 0)
					buffer.writeWordBigEndian(101);
				buffer.writeWordBigEndian(197);
				buffer.writeWord((int)(Math.random() * 65536D));
				buffer.writeWordBigEndian((int)(Math.random() * 256D));
				buffer.writeWordBigEndian(67);
				buffer.writeWord(14214);
				if((int)(Math.random() * 2D) == 0)
					buffer.writeWord(29487);
				buffer.writeWord((int)(Math.random() * 65536D));
				if((int)(Math.random() * 2D) == 0)
					buffer.writeWordBigEndian(220);
				buffer.writeWordBigEndian(180);
				buffer.writeBytes(buffer.currentPosition - l);
			}
		}
	}

	private String interfaceIntToString(int j) {
		if(j < 0x3b9ac9ff)
			return String.valueOf(j);
		else
			return "*";
	}



	public void showErrorScreen()
	{
		Graphics g = getGameComponent().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		method4(1);
		if(loadingError)
		{
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 16));
			g.setColor(Color.yellow);
			int k = 35;
			g.drawString("Sorry, an error has occured whilst loading Allstarlegends", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
			k += 30;
			g.drawString("2: Try clearing your web-browsers kits from tools->internet options", 30, k);
			k += 30;
			g.drawString("3: Try using a different game-world", 30, k);
			k += 30;
			g.drawString("4: Try rebooting your computer", 30, k);
			k += 30;
			g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
		}
		if(genericLoadingError)
		{
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play Allstarlegends make sure you play from", 50, 100);
			g.drawString("", 50, 150);
		}
		if(rsAlreadyLoaded)
		{
			aBoolean831 = false;
			g.setColor(Color.yellow);
			int l = 35;
			g.drawString("Error a copy of Allstarlegends already appears to be loaded", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
			l += 30;
			g.drawString("2: Try rebooting your computer, and reloading", 30, l);
			l += 30;
		}
	}

	public URL getCodeBase() {
		try {
			return new URL(server +":" + (80 + portOff));
		} catch(Exception _ex) {
		}
		return null;
	}

	public void method95() {
		for(int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			Npc npc = npcs[k];
			if(npc != null)
				processMovement(npc);
		}
	}

	public void processMovement(Mob mob)
	{
		if(mob.x < 128 || mob.y < 128 || mob.x >= 13184 || mob.y >= 13184)
		{
			mob.emoteAnimation = -1;
			mob.graphic = -1;
			mob.startForceMovement = 0;
			mob.endForceMovement = 0;
			mob.x = mob.pathX[0] * 128 + mob.size * 64;
			mob.y = mob.pathY[0] * 128 + mob.size * 64;
			mob.resetPath();
		}
		if(mob == localPlayer && (mob.x < 1536 || mob.y < 1536 || mob.x >= 11776 || mob.y >= 11776))
		{
			mob.emoteAnimation = -1;
			mob.graphic = -1;
			mob.startForceMovement = 0;
			mob.endForceMovement = 0;
			mob.x = mob.pathX[0] * 128 + mob.size * 64;
			mob.y = mob.pathY[0] * 128 + mob.size * 64;
			mob.resetPath();
		}
		if(mob.startForceMovement > tick)
			method97(mob);
		else
		if(mob.endForceMovement >= tick)
			method98(mob);
		else
			method99(mob);
		appendFocusDestination(mob);
		method101(mob); //@todo add size here?
	}

	public void method97(Mob mob)
	{
		int i = mob.startForceMovement - tick;
		int j = mob.anInt1543 * 128 + mob.size * 64;
		int k = mob.anInt1545 * 128 + mob.size * 64;
		mob.x += (j - mob.x) / i;
		mob.y += (k - mob.y) / i;
		mob.anInt1503 = 0;
		if(mob.anInt1549 == 0)
			mob.turnDirection = 1024;
		if(mob.anInt1549 == 1)
			mob.turnDirection = 1536;
		if(mob.anInt1549 == 2)
			mob.turnDirection = 0;
		if(mob.anInt1549 == 3)
			mob.turnDirection = 512;
	}

	public void method98(Mob mob)
	{
		if(mob.endForceMovement == tick || mob.emoteAnimation == -1 || mob.anInt1529 != 0 || mob.anInt1528 + 1 > Animation.animations[mob.emoteAnimation].method258(mob.anInt1527))
		{
			int i = mob.endForceMovement - mob.startForceMovement;
			int j = tick - mob.startForceMovement;
			int k = mob.anInt1543 * 128 + mob.size * 64;
			int l = mob.anInt1545 * 128 + mob.size * 64;
			int i1 = mob.anInt1544 * 128 + mob.size * 64;
			int j1 = mob.anInt1546 * 128 + mob.size * 64;
			mob.x = (k * (i - j) + i1 * j) / i;
			mob.y = (l * (i - j) + j1 * j) / i;
		}
		mob.anInt1503 = 0;
		if(mob.anInt1549 == 0)
			mob.turnDirection = 1024;
		if(mob.anInt1549 == 1)
			mob.turnDirection = 1536;
		if(mob.anInt1549 == 2)
			mob.turnDirection = 0;
		if(mob.anInt1549 == 3)
			mob.turnDirection = 512;
		mob.anInt1552 = mob.turnDirection;
	}

	public void method99(Mob mob)
	{
		mob.movementAnimation = mob.idleAnimation;
		if(mob.remainingPath == 0)
		{
			mob.anInt1503 = 0;
			return;
		}
		if(mob.emoteAnimation != -1 && mob.anInt1529 == 0)
		{
			Animation animation = Animation.animations[mob.emoteAnimation];
			if(mob.anInt1542 > 0 && animation.anInt363 == 0)
			{
				mob.anInt1503++;
				return;
			}
			if(mob.anInt1542 <= 0 && animation.priority == 0)
			{
				mob.anInt1503++;
				return;
			}
		}
		int i = mob.x;
		int j = mob.y;
		int k = mob.pathX[mob.remainingPath - 1] * 128 + mob.size * 64;
		int l = mob.pathY[mob.remainingPath - 1] * 128 + mob.size * 64;
		if(k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256)
		{
			mob.x = k;
			mob.y = l;
			return;
		}
		if(i < k)
		{
			if(j < l)
				mob.turnDirection = 1280;
			else
			if(j > l)
				mob.turnDirection = 1792;
			else
				mob.turnDirection = 1536;
		} else
		if(i > k)
		{
			if(j < l)
				mob.turnDirection = 768;
			else
			if(j > l)
				mob.turnDirection = 256;
			else
				mob.turnDirection = 512;
		} else
		if(j < l)
			mob.turnDirection = 1024;
		else
			mob.turnDirection = 0;
		int i1 = mob.turnDirection - mob.anInt1552 & 0x7ff;
		if(i1 > 1024)
			i1 -= 2048;
		int j1 = mob.turn180AnimIndex;
		if(i1 >= -256 && i1 <= 256)
			j1 = mob.walkAnimIndex;
		else
		if(i1 >= 256 && i1 < 768)
			j1 = mob.turn90CCWAnimIndex;
		else
		if(i1 >= -768 && i1 <= -256)
			j1 = mob.turn90CWAnimIndex;
		if(j1 == -1)
			j1 = mob.walkAnimIndex;
		mob.movementAnimation = j1;
		int k1 = 4;
		if(mob.anInt1552 != mob.turnDirection && mob.interactingEntity == -1 && mob.degreesToTurn != 0)
			k1 = 2;
		if(mob.remainingPath > 2)
			k1 = 6;
		if(mob.remainingPath > 3)
			k1 = 8;
		if(mob.anInt1503 > 0 && mob.remainingPath > 1)
		{
			k1 = 8;
			mob.anInt1503--;
		}
		if(mob.pathRun[mob.remainingPath - 1])
			k1 <<= 1;
		if(k1 >= 8 && mob.movementAnimation == mob.walkAnimIndex && mob.runAnimIndex != -1)
			mob.movementAnimation = mob.runAnimIndex;
		if(i < k)
		{
			mob.x += k1;
			if(mob.x > k)
				mob.x = k;
		} else
		if(i > k)
		{
			mob.x -= k1;
			if(mob.x < k)
				mob.x = k;
		}
		if(j < l)
		{
			mob.y += k1;
			if(mob.y > l)
				mob.y = l;
		} else
		if(j > l)
		{
			mob.y -= k1;
			if(mob.y < l)
				mob.y = l;
		}
		if(mob.x == k && mob.y == l)
		{
			mob.remainingPath--;
			if(mob.anInt1542 > 0)
				mob.anInt1542--;
		}
	}

	public void appendFocusDestination(Mob mob)
	{
		if(mob.degreesToTurn == 0)
			return;
		if(mob.interactingEntity != -1 && mob.interactingEntity < 32768)
		{
			Npc npc = npcs[mob.interactingEntity];
			if(npc != null)
			{
				int i1 = mob.x - npc.x;
				int k1 = mob.y - npc.y;
				if(i1 != 0 || k1 != 0)
					mob.turnDirection = (int)(Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
			}
		}
		if(mob.interactingEntity >= 32768)
		{
			int j = mob.interactingEntity - 32768;
			if(j == localPlayerIndex)
				j = internalLocalPlayerIndex;
			if (j <= players.length) {
				Player player = players[j];
				if(player != null)
				{
					int l1 = mob.x - player.x;
					int i2 = mob.y - player.y;
					if(l1 != 0 || i2 != 0)
						mob.turnDirection = (int)(Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
				}
			}
		}
		if((mob.anInt1538 != 0 || mob.anInt1539 != 0) && (mob.remainingPath == 0 || mob.anInt1503 > 0))
		{
			int k = mob.x - (mob.anInt1538 - regionBaseX - regionBaseX) * 64;
			int j1 = mob.y - (mob.anInt1539 - regionBaseY - regionBaseY) * 64;
			if(k != 0 || j1 != 0)
				mob.turnDirection = (int)(Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
			mob.anInt1538 = 0;
			mob.anInt1539 = 0;
		}
		int l = mob.turnDirection - mob.anInt1552 & 0x7ff;
		if(l != 0)
		{
			if(l < mob.degreesToTurn || l > 2048 - mob.degreesToTurn)
				mob.anInt1552 = mob.turnDirection;
			else
			if(l > 1024)
				mob.anInt1552 -= mob.degreesToTurn;
			else
				mob.anInt1552 += mob.degreesToTurn;
			mob.anInt1552 &= 0x7ff;
			if(mob.movementAnimation == mob.idleAnimation && mob.anInt1552 != mob.turnDirection)
			{
				if(mob.standTurnAnimIndex != -1)
				{
					mob.movementAnimation = mob.standTurnAnimIndex;
					return;
				}
				mob.movementAnimation = mob.walkAnimIndex;
			}
		}
	}

	public void method101(Mob mob)
	{
		mob.animationScretches = false;
		if(mob.movementAnimation != -1)
		{
			Animation animation = Animation.animations[mob.movementAnimation];
			mob.anInt1519++;
			if(mob.anInt1518 < animation.anInt352 && mob.anInt1519 > animation.method258(mob.anInt1518))
			{
				mob.anInt1519 = 0;
				mob.anInt1518++;
			}
			if(mob.anInt1518 >= animation.anInt352)
			{
				mob.anInt1519 = 0;
				mob.anInt1518 = 0;
			}
		}
		if(mob.graphic != -1 && tick >= mob.anInt1523)
		{
			if(mob.anInt1521 < 0)
				mob.anInt1521 = 0;
			Animation animation_1 = Graphic.cache[mob.graphic].animationSequence;
			for(mob.anInt1522++; mob.anInt1521 < animation_1.anInt352 && mob.anInt1522 > animation_1.method258(mob.anInt1521); mob.anInt1521++)
				mob.anInt1522 -= animation_1.method258(mob.anInt1521);

			if(mob.anInt1521 >= animation_1.anInt352 && (mob.anInt1521 < 0 || mob.anInt1521 >= animation_1.anInt352))
				mob.graphic = -1;
		}
		if(mob.emoteAnimation != -1 && mob.anInt1529 <= 1)
		{
			Animation animation_2 = Animation.animations[mob.emoteAnimation];
			if(animation_2.anInt363 == 1 && mob.anInt1542 > 0 && mob.startForceMovement <= tick && mob.endForceMovement < tick)
			{
				mob.anInt1529 = 1;
				return;
			}
		}
		if(mob.emoteAnimation != -1 && mob.anInt1529 == 0)
		{
			Animation animation_3 = Animation.animations[mob.emoteAnimation];
			for(mob.anInt1528++; mob.anInt1527 < animation_3.anInt352 && mob.anInt1528 > animation_3.method258(mob.anInt1527); mob.anInt1527++)
				mob.anInt1528 -= animation_3.method258(mob.anInt1527);

			if(mob.anInt1527 >= animation_3.anInt352)
			{
				mob.anInt1527 -= animation_3.anInt356;
				mob.anInt1530++;
				if(mob.anInt1530 >= animation_3.anInt362)
					mob.emoteAnimation = -1;
				if(mob.anInt1527 < 0 || mob.anInt1527 >= animation_3.anInt352)
					mob.emoteAnimation = -1;
			}
			mob.animationScretches = animation_3.aBoolean358;
		}
		if(mob.anInt1529 > 0)
			mob.anInt1529--;
	}

	private void drawGameScreen() {
		if (fullscreenInterfaceID != -1
				&& (loadingStage == 2 || super.fullGameScreen != null)) {
			if (loadingStage == 2) {
				try {
					processWidgetAnimations(tickDelta, fullscreenInterfaceID);
					if (openInterfaceId != -1) {
						processWidgetAnimations(tickDelta, openInterfaceId);
					}
				} catch (Exception ex) {

				}
				tickDelta = 0;
				resetAllImageProducers();
				super.fullGameScreen.initDrawingArea();
				Rasterizer3D.scanOffsets = fullScreenTextureArray;
				Rasterizer2D.clear();
				welcomeScreenRaised = true;
				if (openInterfaceId != -1) {
					Widget rsInterface_1 = Widget.interfaceCache[openInterfaceId];
					if (rsInterface_1.width == 512 && rsInterface_1.height == 334
							&& rsInterface_1.type == 0) {
						rsInterface_1.width = 765;
						rsInterface_1.height = 503;
					}
					try {
						drawInterface(0, 0, rsInterface_1, 8);
					} catch (Exception ex) {

					}
				}
				Widget rsInterface = Widget.interfaceCache[fullscreenInterfaceID];
				if (rsInterface.width == 512 && rsInterface.height == 334
						&& rsInterface.type == 0) {
					rsInterface.width = 765;
					rsInterface.height = 503;
				}
				try {
					drawInterface(0, 0, rsInterface, 8);
				} catch (Exception ex) {

				}
				if (!menuOpen) {
					processRightClick();
					drawTooltip();
					drawHoverMenu(frameMode == ScreenMode.FIXED ? 4 : 0,
							frameMode == ScreenMode.FIXED ? 4 : 0);
				} else {
					drawMenu(frameMode == ScreenMode.FIXED ? 4 : 0,
							frameMode == ScreenMode.FIXED ? 4 : 0);
				}
			}
			drawCount++;
			super.fullGameScreen.drawGraphics(0, super.graphics, 0);
			return;
		} else {
			if (drawCount != 0) {
				setupGameplayScreen();
			}
		}
		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
			if (frameMode == ScreenMode.FIXED) {
				topFrame.drawGraphics(0, super.graphics, 0);
				leftFrame.drawGraphics(4, super.graphics, 0);
			}
			updateChatbox = true;
			tabAreaAltered = true;
			if (loadingStage != 2) {
				if (frameMode == ScreenMode.FIXED) {
					gameScreenImageProducer.drawGraphics(
							frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics,
							frameMode == ScreenMode.FIXED ? 4 : 0);
					minimapImageProducer.drawGraphics(0, super.graphics, 516);
				}
			}
		}
		if (overlayInterfaceId != -1) {
			try {
				processWidgetAnimations(tickDelta, overlayInterfaceId);
			} catch (Exception ex) {

			}
		}
		drawTabArea();
		if (backDialogueId == -1) {
			aClass9_1059.scrollPosition = anInt1211 - anInt1089 - 110;
			if (super.mouseX >= 496 && super.mouseX <= 511
					&& super.mouseY > (frameMode == ScreenMode.FIXED ? 345
					: frameHeight - 158))
				method65(494, 110, super.mouseX,
						super.mouseY - (frameMode == ScreenMode.FIXED ? 345
								: frameHeight - 158),
						aClass9_1059, 0, false, anInt1211);
			int i = anInt1211 - 110 - aClass9_1059.scrollPosition;
			if (i < 0) {
				i = 0;
			}
			if (i > anInt1211 - 110) {
				i = anInt1211 - 110;
			}
			if (anInt1089 != i) {
				anInt1089 = i;
				updateChatbox = true;
			}
		}
		if (backDialogueId != -1) {
			boolean flag2 = false;

			try {
				flag2 = processWidgetAnimations(tickDelta, backDialogueId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (flag2) {
				updateChatbox = true;
			}
		}
		if (atInventoryInterfaceType == 3)
			updateChatbox = true;
		if (activeInterfaceType == 3)
			updateChatbox = true;
		if (clickToContinueString != null)
			updateChatbox = true;
		if (menuOpen && menuScreenArea == 2)
			updateChatbox = true;
		if (updateChatbox) {
			drawChatArea();
			updateChatbox = false;
		}
		if (loadingStage == 2)
			moveCameraWithPlayer();
		if (loadingStage == 2) {
			if (frameMode == ScreenMode.FIXED) {
				drawMinimap();
				minimapImageProducer.drawGraphics(0, super.graphics, 516);
			}
		}
		if (flashingSidebarId != -1)
			tabAreaAltered = true;
		if (tabAreaAltered) {
			if (flashingSidebarId != -1 && flashingSidebarId == tabId) {
				flashingSidebarId = -1;
				// flashing sidebar
				/*outgoing.writeOpcode(120);
				outgoing.writeByte(tabId);*/
			}
			tabAreaAltered = false;
			chatSettingImageProducer.initDrawingArea();
			gameScreenImageProducer.initDrawingArea();
		}
		tickDelta = 0;
	}

	private boolean buildFriendsListMenu(Widget class9)
	{
		int i = class9.contentType;
		if(i >= 1 && i <= 200 || i >= 701 && i <= 900)
		{
			if(i >= 801)
				i -= 701;
			else
			if(i >= 701)
				i -= 601;
			else
			if(i >= 101)
				i -= 101;
			else
				i--;
			menuActionText[menuActionRow] = "Remove @whi@" + friendsList[i];
			menuActionTypes[menuActionRow] = 792;
			menuActionRow++;
			menuActionText[menuActionRow] = "Message @whi@" + friendsList[i];
			menuActionTypes[menuActionRow] = 639;
			menuActionRow++;
			return true;
		}
		if(i >= 401 && i <= 500)
		{
			menuActionText[menuActionRow] = "Remove @whi@" + class9.defaultText;
			menuActionTypes[menuActionRow] = 322;
			menuActionRow++;
			return true;
		} else
		{
			return false;
		}
	}

	public void method104()
	{
		Projectile class30_sub2_sub4_sub3 = (Projectile) projectiles.reverseGetFirst();
		for(; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (Projectile) projectiles.reverseGetNext())
			if(class30_sub2_sub4_sub3.anInt1560 != plane || class30_sub2_sub4_sub3.aBoolean1567)
				class30_sub2_sub4_sub3.unlink();
			else
			if(tick >= class30_sub2_sub4_sub3.anInt1564)
			{
				class30_sub2_sub4_sub3.method454(tickDelta);
				if(class30_sub2_sub4_sub3.aBoolean1567)
					class30_sub2_sub4_sub3.unlink();
				else
					scene.addAnimableA(class30_sub2_sub4_sub3.anInt1560, 0, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
			}

	}


	private void drawInterface(int scroll_y, int x, Widget rsInterface, int y) throws Exception {
		if (rsInterface == null)
			return;
		if (rsInterface.type != 0 || rsInterface.children == null)
			return;
		if (rsInterface.invisible && anInt1026 != rsInterface.id && anInt1048 != rsInterface.id
				&& anInt1039 != rsInterface.id || rsInterface.hidden) {
			return;
		}

		int clipLeft = Rasterizer2D.leftX;
		int clipTop = Rasterizer2D.topY;
		int clipRight = Rasterizer2D.bottomX;
		int clipBottom = Rasterizer2D.bottomY;

		Rasterizer2D.setDrawingArea(y + rsInterface.height, x, x + rsInterface.width, y);
		int childCount = rsInterface.children.length;

		for (int childId = 0; childId < childCount; childId++) {
			int _x = rsInterface.childX[childId] + x;
			int currentY = (rsInterface.childY[childId] + y) - scroll_y;
			Widget childInterface = Widget.interfaceCache[rsInterface.children[childId]];

			if (childInterface == null) {
				continue;
			}

			if (childInterface.hidden) {
				continue;
			}

			if (childInterface.id == 27656) {
				long totalExp = 0;
				for (int i = 0; i < 23; i++) {
					totalExp += currentExp[i];
				}
				childInterface.defaultText = "Total XP: " + NumberFormat.getInstance().format(totalExp);
			}

			// Handle black box hovers in magic spellbook
			boolean drawBlackBox = false;
			for(int m5 = 0; m5 < IDs.length; m5++) {
				if(childInterface.id == IDs[m5] + 1) {
					drawBlackBox = true;
				}
			}

			_x += childInterface.horizontalOffset;
			currentY += childInterface.verticalOffset;
			if (childInterface.contentType > 0)
				drawFriendsListOrWelcomeScreen(childInterface);
			if (drawBlackBox) {
				Widget.drawBlackBox(_x, currentY);
			}
			for (int r = 0; r < runeChildren.length; r++)
				if (childInterface.id == runeChildren[r])
					childInterface.modelZoom = 775;

			if (childInterface.type == Widget.TYPE_CONTAINER) {
				if (childInterface.scrollPosition > childInterface.scrollMax - childInterface.height)
					childInterface.scrollPosition = childInterface.scrollMax - childInterface.height;
				if (childInterface.scrollPosition < 0)
					childInterface.scrollPosition = 0;
				drawInterface(childInterface.scrollPosition, _x, childInterface, currentY);
				if (childInterface.scrollMax > childInterface.height) {
					drawScrollbar(childInterface.height, childInterface.scrollPosition, currentY, _x + childInterface.width, childInterface.scrollMax, false);
				}
			} else if (childInterface.type != 1)

				if (childInterface.type == Widget.TYPE_INVENTORY) {
					boolean drawnBank = false;
					int item = 0;
					for (int row = 0; row < childInterface.height; row++) {
						for (int column = 0; column < childInterface.width; column++) {

							int tileX = _x + column * (32 + childInterface.spritePaddingX);
							int tileY = currentY + row * (32 + childInterface.spritePaddingY);
							if (item < 20) {
								tileX += childInterface.spritesX[item];
								tileY += childInterface.spritesY[item];
							}

							if (item < childInterface.inventoryItemId.length && childInterface.inventoryItemId[item] > 0) {
								int dragOffsetX = 0;
								int dragOffsetY = 0;
								int bindX = 0;
								int bindY = 0;
								int itemId = childInterface.inventoryItemId[item] - 1;

								if (tileX > Rasterizer2D.leftX - 32 && tileX < Rasterizer2D.bottomX && tileY > Rasterizer2D.topY - 32 && tileY < Rasterizer2D.bottomY
										|| activeInterfaceType != 0 && anInt1085 == item) {
									int outlineColour = 0;
									if (itemSelected == 1 && anInt1283 == item && anInt1284 == childInterface.id)
										outlineColour = 0xffffff;
									int itemOpacity = 256;
									if (childInterface.parent == 5382) {
										if (childInterface.inventoryAmounts[item] == 0) {
											itemOpacity = 95;
										}
									}
									Sprite item_icon = ItemDefinition.getSprite(itemId, childInterface.inventoryAmounts[item], outlineColour);

									if (item_icon != null) {
										if (activeInterfaceType != 0 && anInt1085 == item && anInt1084 == childInterface.id) {

											dragOffsetX = super.mouseX - anInt1087;
											dragOffsetY = super.mouseY - anInt1088;

											if (dragOffsetX < 5 && dragOffsetX > -5)
												dragOffsetX = 0;
											if (dragOffsetY < 5 && dragOffsetY > -5)
												dragOffsetY = 0;
											if (dragItemDelay < 10) {
												dragOffsetX = 0;
												dragOffsetY = 0;
											}

											bindX = tileX + dragOffsetX;
											if (bindX < Rasterizer2D.leftX) {
												bindX = Rasterizer2D.leftX - (dragOffsetX);
												if (dragOffsetX < Rasterizer2D.leftX)
													bindX = Rasterizer2D.leftX;
											}
											if (bindX > Rasterizer2D.bottomX - 32) {
												bindX = Rasterizer2D.bottomX - 32;
											}

											bindY = tileY + dragOffsetY;
											if (bindY < Rasterizer2D.topY && rsInterface.scrollPosition == 0) {
												bindY = Rasterizer2D.topY - (dragOffsetY);
												if (dragOffsetY < Rasterizer2D.topY)
													bindY = Rasterizer2D.topY;
											}
											if (bindY > Rasterizer2D.bottomY - 32)
												bindY = Rasterizer2D.bottomY - 32;

											if (tileY + dragOffsetY < Rasterizer2D.topY && rsInterface.scrollPosition > 0) {
												int notch = (tickDelta * (Rasterizer2D.topY - tileY - dragOffsetY)) / 3;
												if (notch > tickDelta * 10)
													notch = tickDelta * 10;

												if (notch > rsInterface.scrollPosition)
													notch = rsInterface.scrollPosition;

												rsInterface.scrollPosition -= notch;
												anInt1088 += notch;
												bindY = Rasterizer2D.topY;
											}

											if (tileY + dragOffsetY + 32 > Rasterizer2D.bottomY && rsInterface.scrollPosition < rsInterface.scrollMax - rsInterface.height) {
												int notch = (tickDelta * ((tileY + dragOffsetY + 32) - Rasterizer2D.bottomY)) / 3;
												if (notch > tickDelta * 10)
													notch = tickDelta * 10;

												if (notch > rsInterface.scrollMax - rsInterface.height - rsInterface.scrollPosition)
													notch = rsInterface.scrollMax - rsInterface.height - rsInterface.scrollPosition;

												rsInterface.scrollPosition += notch;
												anInt1088 -= notch;
											}
											item_icon.drawSprite1(bindX, bindY);
										} else if (atInventoryInterfaceType != 0 && atInventoryIndex == item && atInventoryInterface == childInterface.id) {
											bindX = tileX + dragOffsetX;
											bindY = tileY;
											item_icon.drawSprite1(tileX, tileY);
										} else {
											bindX = tileX + dragOffsetX;
											bindY = tileY;
											if (itemOpacity != 256) {
												item_icon.drawTransparentSprite(tileX, tileY, itemOpacity);
											} else {
												item_icon.drawSprite(tileX, tileY);
											}
										}
										if (item_icon.maxWidth == 33 || childInterface.inventoryAmounts[item] != 1) {

											boolean flag = true;
											if ((childInterface.id >= 22035 && childInterface.id <= 22043) || childInterface.id == 41042 || childInterface.id == 41107) {
												flag = false;
											}

											if (flag) {

												int k10 = childInterface.inventoryAmounts[item];

												if (k10 >= 1500000000 && childInterface.drawInfinity) {
													spriteCache.draw(105, tileX, tileY);
												} else if (k10 == 0) { // Placeholder text
													newSmallFont.setTrans(1, 0xFFFF00, 120);
													newSmallFont.drawBasicString(intToKOrMil(k10), bindX, bindY + 9);
												} else {
													smallText.render(0, intToKOrMil(k10), bindY + 10, bindX + 1); // Shadow
													if (k10 >= 1)
														smallText.render(0xFFFF00, intToKOrMil(k10), bindY + 9, bindX);
													if (k10 >= 100000)
														smallText.render(0xFFFFFF, intToKOrMil(k10), bindY + 9, bindX);
													if (k10 >= 10000000)
														smallText.render(0x00FF80, intToKOrMil(k10), bindY + 9, bindX);
												}
											}
										}
									}
								}
							} else if (childInterface.sprites != null && item < 20) {
								Sprite image = childInterface.sprites[item];
								if (image != null)
									image.drawSprite(tileX, tileY);
							}
							item++;
							/*
							// Drawing tab number etc in main bank interface
							if (childInterface.parent == 5382 && !drawnBank) {
								for (int tabId = 0; tabId < Bank.CONTAINERS.length; tabId++) {
									if (childInterface.id == Bank.CONTAINERS[tabId]) {
										if (Bank.currentBankTab == 0) {
											Rasterizer2D.drawHorizontalLine((tileX - 10), (tileY - 8), 434, 0x73654a);
											smallText.drawText(0xefa142, "Tab " + Integer.toString(tabId + 1),
													tileY - 9, tileX + 4);
											drawnBank = true;
										}
										break;
									}
								}
							} */
						}
					}

				} else if (childInterface.type == Widget.TYPE_RECTANGLE) {
					boolean hover = false;
					if (anInt1039 == childInterface.id || anInt1048 == childInterface.id
							|| anInt1026 == childInterface.id)
						hover = true;
					int colour;
					if (interfaceIsSelected(childInterface)) {
						colour = childInterface.secondaryColor;
						if (hover && childInterface.secondaryHoverColor != 0)
							colour = childInterface.secondaryHoverColor;
					} else {
						colour = childInterface.textColor;
						if (hover && childInterface.defaultHoverColor != 0)
							colour = childInterface.defaultHoverColor;
					}
					if (childInterface.opacity == 0) {
						if (childInterface.filled)
							Rasterizer2D.drawBox(_x, currentY, childInterface.width, childInterface.height, colour
							);
						else
							Rasterizer2D.drawBoxOutline(_x, currentY, childInterface.width,
									childInterface.height, colour);
					} else if (childInterface.filled)
						Rasterizer2D.drawTransparentBox(_x, currentY, childInterface.width, childInterface.height, colour,
								256 - (childInterface.opacity & 0xff));
					else
						Rasterizer2D.drawTransparentBoxOutline(_x, currentY, childInterface.width, childInterface.height,
								colour, 256 - (childInterface.opacity & 0xff)
						);
				} else if (childInterface.type == Widget.TYPE_TEXT) {
					GameFont textDrawingArea = childInterface.textDrawingAreas;
					String text = childInterface.defaultText;
					if (text == null) {
						continue;
					}

					boolean flag1 = false;
					if (anInt1039 == childInterface.id || anInt1048 == childInterface.id || anInt1026 == childInterface.id)
						flag1 = true;
					int colour;
					if (interfaceIsSelected(childInterface)) {
						colour = childInterface.secondaryColor;
						if (flag1 && childInterface.secondaryHoverColor != 0)
							colour = childInterface.secondaryHoverColor;
						if (childInterface.secondaryText.length() > 0)
							text = childInterface.secondaryText;
					} else {
						colour = childInterface.textColor;
						if (flag1 && childInterface.defaultHoverColor != 0)
							colour = childInterface.defaultHoverColor;
					}
					if (childInterface.atActionType == Widget.OPTION_CONTINUE
							&& continuedDialogue) {
						text = "Please wait...";
						colour = childInterface.textColor;
					}
					if (Rasterizer2D.width == 519) {
						if (colour == 0xffff00)
							colour = 255;
						if (colour == 49152)
							colour = 0xffffff;
					}
					if (frameMode != ScreenMode.FIXED) {
						if ((backDialogueId != -1 || dialogueId != -1
								|| childInterface.defaultText
								.contains("Click here to continue"))
								&& (rsInterface.id == backDialogueId
								|| rsInterface.id == dialogueId)) {
							if (colour == 0xffff00) {
								colour = 255;
							}
							if (colour == 49152) {
								colour = 0xffffff;
							}
						}
					}
					if ((childInterface.parent == 1151) || (childInterface.parent == 12855)) {
						switch (colour) {
							case 16773120:
								colour = 0xFE981F;
								break;
							case 7040819:
								colour = 0xAF6A1A;
								break;
						}
					}

					int image = -1;
					final String INITIAL_MESSAGE = text;
					if (text.contains("<img=")) {
						int prefix = text.indexOf("<img=");
						int suffix = text.indexOf(">");
						try {
							image = Integer.parseInt(text.substring(prefix + 5, suffix));
							text = text.replaceAll(text.substring(prefix + 5, suffix), "");
							text = text.replaceAll("</img>", "");
							text = text.replaceAll("<img=>", "");
						} catch (NumberFormatException nfe) {
							//System.out.println("Unable to draw player crown on interface. Unable to read rights.");
							text = INITIAL_MESSAGE;
						} catch (IllegalStateException ise) {
							//System.out.println("Unable to draw player crown on interface, rights too low or high.");
							text = INITIAL_MESSAGE;
						}
						if (suffix > prefix) {
							//_x += 14;
						}
					}

					for (int drawY = currentY + textDrawingArea.verticalSpace; text.length() > 0; drawY +=
							textDrawingArea.verticalSpace) {

						if (image != -1) {

							//CLAN CHAT LIST = 37128
							if (childInterface.parent == 37128) {
								Sprite sprite = spriteCache.lookup(image);
								sprite.drawAdvancedSprite(_x, drawY - sprite.myHeight - 1);
								_x += sprite.myWidth + 3;
							} else {
								Sprite sprite = spriteCache.lookup(image);
								sprite.drawAdvancedSprite(_x, drawY - sprite.myHeight + 3);
								_x += sprite.myWidth + 4;
							}
						}

						if (text.indexOf("%") != -1) {
							do {
								int index = text.indexOf("%1");
								if (index == -1)
									break;
								if (childInterface.id < 4000 || childInterface.id > 5000
										&& childInterface.id != 13921
										&& childInterface.id != 13922
										&& childInterface.id != 12171
										&& childInterface.id != 12172) {
									text = text.substring(0, index)
											+ formatCoins(executeScript(
											childInterface, 0))
											+ text.substring(index + 2);

								} else {
									text = text.substring(0, index) + interfaceIntToString(executeScript(childInterface, 0))
											+ text.substring(index + 2);

								}
							} while (true);
							do {
								int index = text.indexOf("%2");
								if (index == -1) {
									break;
								}
								text = text.substring(0, index)
										+ interfaceIntToString(executeScript(
										childInterface, 1))
										+ text.substring(index + 2);
							} while (true);
							do {
								int index = text.indexOf("%3");

								if (index == -1) {
									break;
								}

								text = text.substring(0, index)
										+ interfaceIntToString(executeScript(
										childInterface, 2))
										+ text.substring(index + 2);
							} while (true);
							do {
								int index = text.indexOf("%4");

								if (index == -1) {
									break;
								}
								text = text.substring(0, index)
										+ interfaceIntToString(executeScript(
										childInterface, 3))
										+ text.substring(index + 2);
							} while (true);
							do {
								int index = text.indexOf("%5");

								if (index == -1) {
									break;
								}

								text = text.substring(0, index)
										+ interfaceIntToString(executeScript(
										childInterface, 4))
										+ text.substring(index + 2);
							} while (true);
						}

						int line = text.indexOf("\\n");

						String drawn;

						if (line != -1) {
							drawn = text.substring(0, line);
							text = text.substring(line + 2);
						} else {
							drawn = text;
							text = "";
						}
						RSFont font = null;
						if (textDrawingArea == smallText) {
							font = newSmallFont;
						} else if (textDrawingArea == regularText) {
							font = newRegularFont;
						} else if (textDrawingArea == boldText) {
							font = newBoldFont;
						} else if (textDrawingArea == gameFont) {
							font = newFancyFont;
						}
						if (childInterface.centerText) {
							font.drawCenteredString(drawn, _x + childInterface.width / 2, drawY, colour, childInterface.textShadow ? 0 : -1);
						} else if (childInterface.rightAlignedText) {
							font.drawRightAlignedString(drawn, _x, drawY, colour, childInterface.textShadow ? 0 : -1);
						} else if (childInterface.rollingText) {
							font.drawRollingText(drawn, _x, drawY, colour, childInterface.textShadow ? 0 : -1);
						} else {
							font.drawBasicString(drawn, _x, drawY, colour, childInterface.textShadow ? 0 : -1);
						}
					}
				} else if (childInterface.type == Widget.TYPE_SPRITE) {

					Sprite sprite;

					if (childInterface.spriteXOffset != 0) {
						_x += childInterface.spriteXOffset;
					}

					if (childInterface.spriteYOffset != 0) {
						currentY += childInterface.spriteYOffset;
					}

					if (interfaceIsSelected(childInterface)) {
						sprite = childInterface.enabledSprite;
					} else {
						sprite = childInterface.disabledSprite;
					}

					if (spellSelected == 1 && childInterface.id == spellId && spellId != 0
							&& sprite != null) {
						sprite.drawSprite(_x, currentY, 0xffffff);
					} else {
						if (sprite != null) {

							boolean drawTransparent = childInterface.drawsTransparent;

							//Check if parent draws as transparent..
							if (!drawTransparent && childInterface.parent > 0 &&
									Widget.interfaceCache[childInterface.parent] != null) {
								drawTransparent = Widget.interfaceCache[childInterface.parent].drawsTransparent;
							}

							if (drawTransparent) {
								sprite.drawTransparentSprite(_x, currentY, childInterface.transparency);
							} else {
								sprite.drawSprite(_x, currentY);
							}
						}
					}
				} else if (childInterface.type == Widget.TYPE_MODEL) {
					int centreX = Rasterizer3D.originViewX;
					int centreY = Rasterizer3D.originViewY;
					Rasterizer3D.originViewX = _x + childInterface.width / 2;
					Rasterizer3D.originViewY = currentY + childInterface.height / 2;
					int sine = Rasterizer3D.SINE[childInterface.modelRotation1] * childInterface.modelZoom >> 16;
					int cosine = Rasterizer3D.COSINE[childInterface.modelRotation1] * childInterface.modelZoom >> 16;
					boolean selected = interfaceIsSelected(childInterface);
					int emoteAnimation;
					if (selected)
						emoteAnimation = childInterface.secondaryAnimationId;
					else
						emoteAnimation = childInterface.defaultAnimationId;
					Model model;
					if (emoteAnimation == -1) {
						model = childInterface.method209(-1, -1, selected);
					} else {
						Animation animation = Animation.animations[emoteAnimation];
						model = childInterface.method209(
								animation.secondaryFrames[childInterface.currentFrame],
								animation.primaryFrames[childInterface.currentFrame],
								selected);
					}
					try {
						if (model != null)
							model.method482(childInterface.modelRotation2, 0, childInterface.modelRotation1, 0, sine, cosine);
					} catch (ArithmeticException e) {
					}
					Rasterizer3D.originViewX = centreX;
					Rasterizer3D.originViewY = centreY;
				} else if (childInterface.type == Widget.TYPE_ITEM_LIST) {
					GameFont font = childInterface.textDrawingAreas;
					int slot = 0;
					for (int row = 0; row < childInterface.height; row++) {
						for (int column = 0; column < childInterface.width; column++) {
							if (childInterface.inventoryItemId[slot] > 0) {
								ItemDefinition item = ItemDefinition
										.lookup(childInterface.inventoryItemId[slot]
												- 1);
								String name = item.name;
								if (item.stackable
										|| childInterface.inventoryAmounts[slot] != 1)
									name = name + " x" + intToKOrMilLongName(
											childInterface.inventoryAmounts[slot]);
								int __x = _x + column
										* (115 + childInterface.spritePaddingX);
								int __y = currentY + row
										* (12 + childInterface.spritePaddingY);
								if (childInterface.centerText)
									font.drawCenteredString(childInterface.textColor,
											__x + childInterface.width / 2,
											name, __y,
											childInterface.textShadow);
								else
									font.drawTextWithPotentialShadow(
											childInterface.textShadow, __x,
											childInterface.textColor, name,
											__y);
							}
							slot++;
						}
					}
				} else if (childInterface.type == Widget.TYPE_OTHER
						&& (anInt1500 == childInterface.id
						|| anInt1044 == childInterface.id
						|| anInt1129 == childInterface.id)
						&& anInt1501 == 0 && !menuOpen) {

					if (childInterface.hoverXOffset != 0) {
						_x += childInterface.hoverXOffset;
					}

					if (childInterface.hoverYOffset != 0) {
						currentY += childInterface.hoverYOffset;
					}


					if (childInterface.regularHoverBox) {
						drawHoverBox(_x, currentY, childInterface.defaultText);

					} else {
						int boxWidth = 0;
						int boxHeight = 0;
						String message = childInterface.defaultText;
						if (childInterface.parent == 3917) {
							String[] msg = message.split("\\\\n");
							if (executeScript(childInterface, 1) >= 13_034_431) {
								message = msg[0];
							}
						}
						GameFont font = regularText;
						for (String text = message; text.length() > 0; ) {
							if (text.indexOf("%") != -1) {
								do {
									int index = text.indexOf("%1");
									if (index == -1)
										break;
									text = text.substring(0, index)
											+ interfaceIntToString(executeScript(
											childInterface, 0))
											+ text.substring(index + 2);
								} while (true);
								do {
									int index = text.indexOf("%2");
									if (index == -1)
										break;
									text = text.substring(0, index)
											+ interfaceIntToString(executeScript(
											childInterface, 1))
											+ text.substring(index + 2);
								} while (true);
								do {
									int index = text.indexOf("%3");
									if (index == -1)
										break;
									text = text.substring(0, index)
											+ interfaceIntToString(executeScript(
											childInterface, 2))
											+ text.substring(index + 2);
								} while (true);
								do {
									int index = text.indexOf("%4");
									if (index == -1)
										break;
									text = text.substring(0, index)
											+ interfaceIntToString(executeScript(
											childInterface, 3))
											+ text.substring(index + 2);
								} while (true);
								do {
									int index = text.indexOf("%5");
									if (index == -1)
										break;
									text = text.substring(0, index)
											+ interfaceIntToString(executeScript(
											childInterface, 4))
											+ text.substring(index + 2);
								} while (true);
								do {
									int l7 = text.indexOf("%6");
									if (l7 == -1)
										break;
									text = text.substring(0, l7)
											+ NumberFormat.getIntegerInstance().format(executeScript(childInterface, 0) - executeScript(childInterface, 1))
											+ text.substring(l7 + 2);
								}
								while (true);
							}
							int line = text.indexOf("\\n");
							String drawn;
							if (line != -1) {
								drawn = text.substring(0, line);
								text = text.substring(line + 2);
							} else {
								drawn = text;
								text = "";
							}
							int j10 = font.getTextWidth(drawn);
							if (j10 > boxWidth) {
								boxWidth = j10;
							}
							boxHeight += font.verticalSpace + 1;
						}
						boxWidth += 6;
						boxHeight += 7;

						int xPos = (_x + childInterface.width) - 5 - boxWidth;
						int yPos = currentY + childInterface.height + 5;
						if (xPos < _x + 5) {
							xPos = _x + 5;
						}

						if (xPos + boxWidth > x + rsInterface.width) {
							xPos = (x + rsInterface.width) - boxWidth;
						}
						if (yPos + boxHeight > y + rsInterface.height) {
							yPos = (currentY - boxHeight);
						}

						String s2 = message;

						Rasterizer2D.drawBox(xPos, yPos, boxWidth, boxHeight, 0xFFFFA0);
						Rasterizer2D.drawBoxOutline(xPos, yPos, boxWidth, boxHeight, 0);

						//Script hovers here

						for (int j11 = yPos + font.verticalSpace + 2; s2.length() > 0; j11 +=
								font.verticalSpace + 1) {// verticalSpace
							if (s2.indexOf("%") != -1) {

								do {
									int k7 = s2.indexOf("%1");
									if (k7 == -1)
										break;
									s2 = s2.substring(0, k7)
											+ interfaceIntToString(executeScript(
											childInterface, 0))
											+ s2.substring(k7 + 2);
								} while (true);

								do {
									int l7 = s2.indexOf("%2");
									if (l7 == -1)
										break;
									s2 = s2.substring(0, l7)
											+ interfaceIntToString(executeScript(
											childInterface, 1))
											+ s2.substring(l7 + 2);
								} while (true);
								do {
									int i8 = s2.indexOf("%3");
									if (i8 == -1)
										break;
									s2 = s2.substring(0, i8)
											+ interfaceIntToString(executeScript(
											childInterface, 2))
											+ s2.substring(i8 + 2);
								} while (true);
								do {
									int j8 = s2.indexOf("%4");
									if (j8 == -1)
										break;
									s2 = s2.substring(0, j8)
											+ interfaceIntToString(executeScript(
											childInterface, 3))
											+ s2.substring(j8 + 2);
								} while (true);
								do {
									int k8 = s2.indexOf("%5");
									if (k8 == -1)
										break;
									s2 = s2.substring(0, k8)
											+ interfaceIntToString(executeScript(
											childInterface, 4))
											+ s2.substring(k8 + 2);
								} while (true);
								do {
									int k8 = s2.indexOf("%6");
									if (k8 == -1)
										break;
									s2 = s2.substring(0, k8)
											+ NumberFormat.getIntegerInstance().format(executeScript(childInterface, 0) - executeScript(childInterface, 1))
											+ s2.substring(k8 + 2);
								}
								while (true);
							}
							int l11 = s2.indexOf("\\n");
							String s5;
							if (l11 != -1) {
								s5 = s2.substring(0, l11);
								s2 = s2.substring(l11 + 2);
							} else {
								s5 = s2;
								s2 = "";
							}
							if (childInterface.centerText) {
								font.drawCenteredString(yPos, xPos + childInterface.width / 2, s5,
										j11, false);
							} else {
								if (s5.contains("\\r")) {
									String text = s5.substring(0, s5.indexOf("\\r"));
									String text2 = s5.substring(s5.indexOf("\\r") + 2);
									font.drawTextWithPotentialShadow(false, xPos + 3, 0,
											text, j11);
									int rightX = boxWidth + xPos
											- font.getTextWidth(text2) - 2;
									font.drawTextWithPotentialShadow(false, rightX, 0,
											text2, j11);
								} else
									font.drawTextWithPotentialShadow(false, xPos + 3, 0,
											s5, j11);
							}
						}
					}
				} else if (childInterface.type == Widget.TYPE_HOVER || childInterface.type == Widget.TYPE_CONFIG_HOVER) {
					// Draw sprite
					boolean flag = false;

					if (childInterface.toggled) { // Check teleport dropdown is not open
						childInterface.disabledSprite.drawAdvancedSprite(_x, currentY, childInterface.spriteOpacity);
						flag = true;
						childInterface.toggled = false;
					} else {
						childInterface.enabledSprite.drawSprite(_x, currentY, childInterface.spriteOpacity);
					}

					// Draw text
					if (childInterface.defaultText == null) {
						continue;
					}
					if (childInterface.centerText) {
						childInterface.rsFont.drawCenteredString(childInterface.defaultText, _x + childInterface.msgX, currentY + childInterface.msgY,
								flag ? childInterface.defaultHoverColor : childInterface.textColor, 0);
					} else {
						if (childInterface.rsFont != null)
							childInterface.rsFont.drawBasicString(childInterface.defaultText, _x + 5,
									currentY + childInterface.msgY,
									flag ? childInterface.defaultHoverColor : childInterface.textColor, 0);
					}
				} else if (childInterface.type == Widget.TYPE_CONFIG) {
					Sprite sprite = childInterface.active ? childInterface.enabledSprite : childInterface.disabledSprite;
					sprite.drawSprite(_x, currentY);
				} else if (childInterface.type == Widget.TYPE_SLIDER) {
					Slider slider = childInterface.slider;
					if (slider != null) {
						slider.draw(_x, currentY);
					}
				} else if (childInterface.type == Widget.TYPE_DROPDOWN) {

					DropdownMenu d = childInterface.dropdown;

					int bgColour = childInterface.dropdownColours[2];
					int fontColour = 0xfe971e;
					int downArrow = 397;

					if (childInterface.hovered || d.isOpen()) {
						downArrow = 398;
						fontColour = 0xffb83f;
						bgColour = childInterface.dropdownColours[3];
					}

					Rasterizer2D.drawPixels(20, currentY, _x, childInterface.dropdownColours[0], d.getWidth());
					Rasterizer2D.drawPixels(18, currentY + 1, _x + 1, childInterface.dropdownColours[1], d.getWidth() - 2);
					Rasterizer2D.drawPixels(16, currentY + 2, _x + 2, bgColour, d.getWidth() - 4);

					int xOffset = childInterface.centerText ? 3 : 16;
					newSmallFont.drawCenteredString(d.getSelected(), _x + (d.getWidth() - xOffset) / 2, currentY + 14, fontColour, 0);

					if (d.isOpen()) {
						// Up arrow
						spriteCache.draw(396, _x + d.getWidth() - 18, currentY + 2);

						Rasterizer2D.drawPixels(d.getHeight(), currentY + 19, _x, childInterface.dropdownColours[0], d.getWidth());
						Rasterizer2D.drawPixels(d.getHeight() - 2, currentY + 20, _x + 1, childInterface.dropdownColours[1], d.getWidth() - 2);
						Rasterizer2D.drawPixels(d.getHeight() - 4, currentY + 21, _x + 2, childInterface.dropdownColours[3], d.getWidth() - 4);

						int yy = 2;
						for (int i = 0; i < d.getOptions().length; i++) {
							if (childInterface.dropdownHover == i) {
								if (childInterface.id == 28102) {
									Rasterizer2D.drawTransparentBox(_x + 2, currentY + 19 + yy, d.getWidth() - 4, 13, 0xd0914d, 80);
								} else {
									Rasterizer2D.drawPixels(13, currentY + 19 + yy, _x + 2, childInterface.dropdownColours[4], d.getWidth() - 4);
								}
								newSmallFont.drawCenteredString(d.getOptions()[i], _x + (d.getWidth() - xOffset) / 2, currentY + 29 + yy, 0xffb83f, 0);
							} else {
								Rasterizer2D.drawPixels(13, currentY + 19 + yy, _x + 2, childInterface.dropdownColours[3], d.getWidth() - 4);
								newSmallFont.drawCenteredString(d.getOptions()[i], _x + (d.getWidth() - xOffset) / 2, currentY + 29 + yy, 0xfe971e, 0);
							}
							yy += 14;
						}

						drawScrollbar(d.getHeight() - 4, childInterface.scrollPosition, currentY + 21, _x + d.getWidth() - 18, d.getHeight() - 5, false);
					} else {
						spriteCache.draw(downArrow, _x + d.getWidth() - 18, currentY + 2);
					}
				} else if (childInterface.type == Widget.TYPE_KEYBINDS_DROPDOWN) {

					DropdownMenu d = childInterface.dropdown;

					// If dropdown inverted, don't draw following 2 menus
					if (dropdownInversionFlag > 0) {
						dropdownInversionFlag--;
						continue;
					}

					Rasterizer2D.drawPixels(18, currentY + 1, _x + 1, 0x544834, d.getWidth() - 2);
					Rasterizer2D.drawPixels(16, currentY + 2, _x + 2, 0x2e281d, d.getWidth() - 4);
					newRegularFont.drawBasicString(d.getSelected(), _x + 7, currentY + 15, 0xff8a1f, 0);
					spriteCache.draw(449, _x + d.getWidth() - 18, currentY + 2); // Arrow

					if (d.isOpen()) {

						Widget.interfaceCache[childInterface.id - 1].active = true; // Alter stone colour

						int yPos = currentY + 18;

						// Dropdown inversion for lower stones
						if (childInterface.inverted) {
							yPos = currentY - d.getHeight() - 10;
							dropdownInversionFlag = 2;
						}

						Rasterizer2D.drawPixels(d.getHeight() + 12, yPos, _x + 1, 0x544834, d.getWidth() - 2);
						Rasterizer2D.drawPixels(d.getHeight() + 10, yPos + 1, _x + 2, 0x2e281d, d.getWidth() - 4);

						int yy = 2;
						int xx = 0;
						int bb = d.getWidth() / 2;

						for (int i = 0; i < d.getOptions().length; i++) {

							int fontColour = 0xff981f;
							if (childInterface.dropdownHover == i) {
								fontColour = 0xffffff;
							}

							if (xx == 0) {
								newRegularFont.drawBasicString(d.getOptions()[i], _x + 5, yPos + 14 + yy, fontColour, 0x2e281d);
								xx = 1;

							} else {
								newRegularFont.drawBasicString(d.getOptions()[i], _x + 5 + bb, yPos + 14 + yy, fontColour, 0x2e281d);
								xx = 0;
								yy += 15;
							}
						}
					} else {
						Widget.interfaceCache[childInterface.id - 1].active = false;
					}
				} else if (childInterface.type == Widget.TYPE_ADJUSTABLE_CONFIG) {

					int totalWidth = childInterface.width;
					int spriteWidth = childInterface.enabledSprite.myWidth;
					int totalHeight = childInterface.height;
					int spriteHeight = childInterface.enabledSprite.myHeight;

					Sprite behindSprite = childInterface.active ? childInterface.enabledAltSprite : childInterface.disabledAltSprite;

					if (childInterface.toggled) {
						behindSprite.drawSprite(_x, currentY);
						childInterface.enabledSprite.drawAdvancedSprite(_x + (totalWidth / 2) - spriteWidth / 2, currentY + (totalHeight / 2) - spriteHeight / 2, childInterface.spriteOpacity);
						childInterface.toggled = false;
					} else {
						behindSprite.drawSprite(_x, currentY);
						childInterface.enabledSprite.drawSprite(_x + (totalWidth / 2) - spriteWidth / 2, currentY + (totalHeight / 2) - spriteHeight / 2);
					}
				} else if (childInterface.type == Widget.TYPE_BOX) {
					// Draw outline
					Rasterizer2D.drawBox(_x - 2, currentY - 2, childInterface.width + 4, childInterface.height + 4, 0x0e0e0c);
					Rasterizer2D.drawBox(_x - 1, currentY - 1, childInterface.width + 2, childInterface.height + 2, 0x474745);
					// Draw base box
					if (childInterface.toggled) {
						Rasterizer2D.drawBox(_x, currentY, childInterface.width, childInterface.height, childInterface.secondaryHoverColor);
						childInterface.toggled = false;
					} else {
						Rasterizer2D.drawBox(_x, currentY, childInterface.width, childInterface.height, childInterface.defaultHoverColor);
					}
				}
		}
		/*
		if (enableGridOverlay) {
			for (int i : tabInterfaceIDs) {
				if (i == rsInterface.id) return;
			}
			drawGridOverlay();
		}*/
		Rasterizer2D.setDrawingArea(clipBottom, clipLeft, clipRight, clipTop);
	}

	public final String formatCoins(int coins) {
		if (coins >= 0 && coins < 10000)
			return String.valueOf(coins);
		if (coins >= 10000 && coins < 10000000)
			return coins / 1000 + "K";
		if (coins >= 10000000 && coins < 999999999)
			return coins / 1000000 + "M";
		if (coins >= 999999999)
			return "*";
		else
			return "?";
	}

	public void drawHoverBox(int xPos, int yPos, String text) {
    	String[] results = text.split("\n");
		int height = (results.length * 16) + 6;
    	int width = regularText.getTextWidth(results[0]) + 6;
    	for(int i = 1; i < results.length; i++)
    		if(width <= regularText.getTextWidth(results[i]) + 6)
    			width = regularText.getTextWidth(results[i]) + 6;
    	Rasterizer2D.drawPixels(height - 2, yPos, xPos, 0xFFFFA0, width);
    	Rasterizer2D.fillPixels(xPos, width, height - 2, 0, yPos);
    	yPos += 14;
    	for(int i = 0; i < results.length; i++) {
        	regularText.drawTextWithPotentialShadow(false, xPos + 3, 0, results[i], yPos);
        	yPos += 16;
    	}
    }

	public void randomizeBackground(IndexedImage indexedImage) {
		int j = 256;
		for(int k = 0; k < anIntArray1190.length; k++)
			anIntArray1190[k] = 0;

		for(int l = 0; l < 5000; l++) {
			int i1 = (int)(Math.random() * 128D * (double)j);
			anIntArray1190[i1] = (int)(Math.random() * 256D);
		}
		for(int j1 = 0; j1 < 20; j1++) {
			for(int k1 = 1; k1 < j - 1; k1++) {
				for(int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
				}

			}
			int ai[] = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if(indexedImage != null) {
			int l1 = 0;
			for(int j2 = 0; j2 < indexedImage.height; j2++) {
				for(int l2 = 0; l2 < indexedImage.width; l2++)
					if(indexedImage.palettePixels[l1++] != 0) {
						int i3 = l2 + 16 + indexedImage.drawOffsetX;
						int j3 = j2 + 16 + indexedImage.drawOffsetY;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
			}
		}
	}

	public void method107(int i, int j, Buffer buffer, Player player)
	{
		if((i & 0x400) != 0)
		{
			player.anInt1543 = buffer.method428();
			player.anInt1545 = buffer.method428();
			player.anInt1544 = buffer.method428();
			player.anInt1546 = buffer.method428();
			player.startForceMovement = buffer.method436() + tick;
			player.endForceMovement = buffer.method435() + tick;
			player.anInt1549 = buffer.method428();
			player.resetPath();
		}
		if((i & 0x100) != 0)
		{
			player.graphic = buffer.method434();
			int k = buffer.readInt();
			player.anInt1524 = k >> 16;
			player.anInt1523 = tick + (k & 0xffff);
			player.anInt1521 = 0;
			player.anInt1522 = 0;
			if(player.anInt1523 > tick)
				player.anInt1521 = -1;
			if(player.graphic == 65535)
				player.graphic = -1;
		}
		/*if ((i & 0x100) != 0) {
			int graphic = item_data.method434();
			int k = item_data.readInt();
			if (player.graphic != -1) {
			} else {
				player.graphic = graphic;
				player.anInt1524 = k >> 16;
				player.anInt1523 = tick + (k & 0xffff);
				player.anInt1521 = 0;
				player.anInt1522 = 0;
				if (player.anInt1523 > tick)
					player.anInt1521 = -1;
				if (player.graphic == 65535)
					player.graphic = -1;
			}
		}*/
		if((i & 8) != 0)
		{
			int l = buffer.method434();
			if(l == 65535)
				l = -1;
			int i2 = buffer.method427();
			if(l == player.emoteAnimation && l != -1)
			{
				int i3 = Animation.animations[l].anInt365;
				if(i3 == 1)
				{
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if(i3 == 2)
					player.anInt1530 = 0;
			} else
			if(l == -1 || player.emoteAnimation == -1 || Animation.animations[l].anInt359 >= Animation.animations[player.emoteAnimation].anInt359)
			{
				player.emoteAnimation = l;
				player.anInt1527 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.remainingPath;
			}
		}
		/*if ((i & 8) != 0) {
			int emoteAnimation = item_data.method434();
			if (emoteAnimation == 65535)
				emoteAnimation = -1;
			int delay = item_data.method427();
			if ((player.emoteAnimation != -1)) {
			} else {
				if (emoteAnimation == player.emoteAnimation && emoteAnimation != -1) {
					int i3 = Animation.animations[emoteAnimation].anInt365;
					if (i3 == 1) {
						player.anInt1527 = 0;
						player.anInt1528 = 0;
						player.anInt1529 = delay;
						player.anInt1530 = 0;
					}
					if (i3 == 2)
						player.anInt1530 = 0;
				} else if (emoteAnimation == -1 || player.emoteAnimation == -1 || Animation.animations[emoteAnimation].anInt359 >= Animation.animations[player.emoteAnimation].anInt359) {
					player.emoteAnimation = emoteAnimation;
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = delay;
					player.anInt1530 = 0;
					player.anInt1542 = player.remainingPath;
				}
			}
		}*/

		/** **/
		if((i & 4) != 0)
		{
			player.textSpoken = buffer.readString();
			if(player.textSpoken.charAt(0) == '~')
			{
				player.textSpoken = player.textSpoken.substring(1);
				pushMessage(player.textSpoken, 2, player.name);
			} else
			if(player == localPlayer)
				pushMessage(player.textSpoken, 2, player.name);
			player.anInt1513 = 0;
			player.anInt1531 = 0;
			player.textCycle = 150;
		}
		if((i & 0x80) != 0)
		{
			int i1 = buffer.method434();
			int j2 = buffer.readUnsignedByte();
			int j3 = buffer.method427();
			int k3 = buffer.currentPosition;
			if(player.name != null && player.visible)
			{
				long l3 = TextClass.longForName(player.name);
				boolean flag = false;
				if(j2 <= 1)
				{
					for(int i4 = 0; i4 < ignoreCount; i4++)
					{
						if(ignoreListAsLongs[i4] != l3)
							continue;
						flag = true;
						break;
					}

				}
				if(!flag && anInt1251 == 0)
					try
					{
						aBuffer_834.currentPosition = 0;
						buffer.method442(j3, 0, aBuffer_834.payload);
						aBuffer_834.currentPosition = 0;
						String s = TextInput.method525(j3, aBuffer_834);
						player.textSpoken = s;
						player.anInt1513 = i1 >> 8;
						player.privelage = j2;
						player.anInt1531 = i1 & 0xff;
						player.textCycle = 150;
						if(j2 == 2)
							pushMessage(s, 1, "@cr2@" + player.name);
						else if(j2 == 1)
							pushMessage(s, 1, "@cr1@" + player.name);
						else if(j2 == 3)
							pushMessage(s, 1, "@cr3@" + player.name);
						else if(j2 == 4)
							pushMessage(s, 1, "@cr4@" + player.name);
						else if(j2 == 5)
							pushMessage(s, 1, "@cr5@" + player.name);
						else if(j2 == 6)
							pushMessage(s, 1, "@cr6@ " + player.name);
						else if(j2 == 7)
							pushMessage(s, 1, "@cr7@" + player.name);
						else
							pushMessage(s, 2, player.name);
					}
					catch(Exception exception)
					{
						signlink.reporterror("cde2");
					}
			}
			buffer.currentPosition = k3 + j3;
		}
		if((i & 1) != 0)
		{
			player.interactingEntity = buffer.method434();
			if(player.interactingEntity == 65535)
				player.interactingEntity = -1;
		}
		if((i & 0x10) != 0)
		{
			int j1 = buffer.method427();
			byte abyte0[] = new byte[j1];
			Buffer buffer_1 = new Buffer(abyte0);
			buffer.readBytes(j1, 0, abyte0);
			aBufferArray895s[j] = buffer_1;
			player.updatePlayer(buffer_1);
		}
		if((i & 2) != 0)
		{
			player.anInt1538 = buffer.method436();
			player.anInt1539 = buffer.method434();
		}
		if((i & 0x20) != 0)
		{
			int k1 = buffer.readByte();
			int k2 = buffer.method426();
			player.updateHitData(k2, k1, tick);
			player.loopCycleStatus = tick + 300;
			player.currentHealth = buffer.method427();
			player.maxHealth = buffer.readUnsignedByte();
		}
		if((i & 0x200) != 0)
		{
			int l1 = buffer.readByte();
			int l2 = buffer.method428();
			player.updateHitData(l2, l1, tick);
			player.loopCycleStatus = tick + 300;
			player.currentHealth = buffer.readUnsignedByte();
			player.maxHealth = buffer.method427();
		}
	}

	public void method108()
	{
		try
		{
			int j = localPlayer.x + anInt1278;
			int k = localPlayer.y + anInt1131;
			if(anInt1014 - j < -500 || anInt1014 - j > 500 || anInt1015 - k < -500 || anInt1015 - k > 500)
			{
				anInt1014 = j;
				anInt1015 = k;
			}
			if(anInt1014 != j)
				anInt1014 += (j - anInt1014) / 16;
			if(anInt1015 != k)
				anInt1015 += (k - anInt1015) / 16;
			if(super.keyArray[1] == 1)
				anInt1186 += (-24 - anInt1186) / 2;
			else
			if(super.keyArray[2] == 1)
				anInt1186 += (24 - anInt1186) / 2;
			else
				anInt1186 /= 2;
			if(super.keyArray[3] == 1)
				anInt1187 += (12 - anInt1187) / 2;
			else
			if(super.keyArray[4] == 1)
				anInt1187 += (-12 - anInt1187) / 2;
			else
				anInt1187 /= 2;
			  cameraHorizontal = cameraHorizontal + anInt1186 / 2 & 0x7ff;
			  anInt1184 += anInt1187 / 2;
			if(anInt1184 < 128)
				anInt1184 = 128;
			if(anInt1184 > 383)
				anInt1184 = 383;
			int l = anInt1014 >> 7;
			int i1 = anInt1015 >> 7;
			int j1 = getCenterHeight(plane, anInt1015, anInt1014);
			int k1 = 0;
			if(l > 3 && i1 > 3 && l < 100 && i1 < 100)
			{
				for(int l1 = l - 4; l1 <= l + 4; l1++)
				{
					for(int k2 = i1 - 4; k2 <= i1 + 4; k2++)
					{
						int l2 = plane;
						if(l2 < 3 && (tileFlags[1][l1][k2] & 2) == 2)
							l2++;
						int i3 = j1 - tileHeights[l2][l1][k2];
						if(i3 > k1)
							k1 = i3;
					}

				}

			}
			anInt1005++;
			if(anInt1005 > 1512)
			{
				anInt1005 = 0;
				buffer.createFrame(77);
				buffer.writeWordBigEndian(0);
				int i2 = buffer.currentPosition;
				buffer.writeWordBigEndian((int)(Math.random() * 256D));
				buffer.writeWordBigEndian(101);
				buffer.writeWordBigEndian(233);
				buffer.writeWord(45092);
				if((int)(Math.random() * 2D) == 0)
					buffer.writeWord(35784);
				buffer.writeWordBigEndian((int)(Math.random() * 256D));
				buffer.writeWordBigEndian(64);
				buffer.writeWordBigEndian(38);
				buffer.writeWord((int)(Math.random() * 65536D));
				buffer.writeWord((int)(Math.random() * 65536D));
				buffer.writeBytes(buffer.currentPosition - i2);
			}
			int j2 = k1 * 192;
			if(j2 > 0x17f00)
				j2 = 0x17f00;
			if(j2 < 32768)
				j2 = 32768;
			if(j2 > anInt984)
			{
				anInt984 += (j2 - anInt984) / 24;
				return;
			}
			if(j2 < anInt984)
			{
				anInt984 += (j2 - anInt984) / 80;
			}
		}
		catch(Exception _ex)
		{
			signlink.reporterror("glfc_ex " + localPlayer.x + "," + localPlayer.y + "," + anInt1014 + "," + anInt1015 + "," + currentRegionX + "," + currentRegionY + "," + regionBaseX + "," + regionBaseY);
			throw new RuntimeException("eek");
		}
	}

	public void processDrawing()
	{
		if(rsAlreadyLoaded || loadingError || genericLoadingError)
		{
			showErrorScreen();
			return;
		}
		anInt1061++;
		if(!loggedIn)
			drawLoginScreen(false);
		else
			drawGameScreen();
		anInt1213 = 0;
	}

	private boolean isFriendOrSelf(String s)
	{
		if(s == null)
			return false;
		for(int i = 0; i < friendsCount; i++)
			if(s.equalsIgnoreCase(friendsList[i]))
				return true;
		return s.equalsIgnoreCase(localPlayer.name);
	}




	private static String combatDiffColor(int i, int j)
	{
		int k = i - j;
		if(k < -9)
			return "@red@";
		if(k < -6)
			return "@or3@";
		if(k < -3)
			return "@or2@";
		if(k < 0)
			return "@or1@";
		if(k > 9)
			return "@gre@";
		if(k > 6)
			return "@gr3@";
		if(k > 3)
			return "@gr2@";
		if(k > 0)
			return "@gr1@";
		else
			return "@yel@";
	}

	public void displayFps() {
		int textColour = 0xffff00;
		int fpsColour = 0xffff00;
		if (super.fps < 15) {
			fpsColour = 0xff0000;
		}

		int x = frameMode == ScreenMode.FIXED ? 468 : frameWidth - 265;
		int y = 12;
		if (Configuration.expCounterOpen) {
			y += 35;
		}

		regularText.render(fpsColour, "Fps: " + super.fps, y, x);
		Runtime runtime = Runtime.getRuntime();
		int clientMemory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
		regularText.render(textColour, "Mem: " + clientMemory + "k", y + 13, x - 35);
	}

	public void setWaveVolume(int i)
	{
		signlink.wavevol = i;
	}

	private void draw3dScreen() {
		if (showChatComponents) {
			drawSplitPrivateChat();
		}
		if (Configuration.expCounterOpen) {
			//drawExpCounter();
		}
		if (crossType == 1) {
			int offSet = frameMode == ScreenMode.FIXED ? 4 : 0;
			crosses[crossIndex / 100].drawSprite(crossX - 8 - offSet, crossY - 8 - offSet);
			anInt1142++;
			if (anInt1142 > 67) {
				anInt1142 = 0;
				//sendPacket(new ClearMinimapFlag()); //Not server-sided, flag is only handled in the client
			}
		}
		if (crossType == 2) {
			int offSet = frameMode == ScreenMode.FIXED ? 4 : 0;
			crosses[4 + crossIndex / 100].drawSprite(crossX - 8 - offSet,
					crossY - 8 - offSet);
		}
		if (openWalkableInterface != -1) {
			try {
				processWidgetAnimations(tickDelta, openWalkableInterface);
				Widget widget = Widget.interfaceCache[openWalkableInterface];
				if (frameMode == ScreenMode.FIXED) {
					drawInterface(0, 0, widget, 0);
				} else {
					Widget r = Widget.interfaceCache[openWalkableInterface];
					int x = frameWidth - 215;
					x -= r.width;
					int min_y = Integer.MAX_VALUE;
					for (int i = 0; i < r.children.length; i++) {
						min_y = Math.min(min_y, r.childY[i]);
					}
					drawInterface(0, x, Widget.interfaceCache[openWalkableInterface], 0 - min_y + 10);
				}
			} catch (Exception ex) {
			}
		}
		if (openInterfaceId != -1) {
			try {
				processWidgetAnimations(tickDelta, openInterfaceId);
				int w = 512, h = 334;
				int x = frameMode == ScreenMode.FIXED ? 0 : (frameWidth / 2) - 256;
				int y = frameMode == ScreenMode.FIXED ? 0 : (frameHeight / 2) - 167;
				int count = stackSideStones ? 3 : 4;
				if (frameMode != ScreenMode.FIXED) {
					for (int i = 0; i < count; i++) {
						if (x + w > (frameWidth - 225)) {
							x = x - 30;
							if (x < 0) {
								x = 0;
							}
						}
						if (y + h > (frameHeight - 182)) {
							y = y - 30;
							if (y < 0) {
								y = 0;
							}
						}
					}
				}
				drawInterface(0, x, Widget.interfaceCache[openInterfaceId], y);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if (!menuOpen) {
			processRightClick();
			drawTooltip();
			drawHoverMenu(frameMode == ScreenMode.FIXED ? 4 : 0,
					frameMode == ScreenMode.FIXED ? 4 : 0);
		} else if (menuScreenArea == 0) {
			drawMenu(frameMode == ScreenMode.FIXED ? 4 : 0,
					frameMode == ScreenMode.FIXED ? 4 : 0);
		}

		// Multi sign
		if (multicombat == 1) {
			multiOverlay.drawSprite(frameMode == ScreenMode.FIXED ? 472 : frameWidth - 255, frameMode == ScreenMode.FIXED ? 296 : 50);
		}
		int x = regionBaseX + (localPlayer.x - 6 >> 7);
		int y = regionBaseY + (localPlayer.y - 6 >> 7);
		final String screenMode = frameMode == ScreenMode.FIXED ? "Fixed" : "Resizable";
		if (Configuration.displayFps) {
			displayFps();
		}
		if (Configuration.clientData) {
			int textColour = 0xffff00;
			displayFps();
			regularText.render(textColour, "Client Zoom: " + cameraZoom, 90, 5);
			//regularText.render(textColour, "Brightness: " + brightnessState, 105, 5);

			regularText.render(textColour, "Resize Mouse X: " + (super.mouseX - frameWidth) + " , Resize Mouse Y: " + (super.mouseY - frameHeight), 15, 5);
			regularText.render(textColour, "Mouse X: " + super.mouseX + " , Mouse Y: " + super.mouseY, 30, 5);
			regularText.render(textColour, "Coords: " + x + ", " + y, 45, 5);
			regularText.render(textColour, "Client Mode: " + screenMode + "", 60, 5);
			regularText.render(textColour, "Client Resolution: " + frameWidth + "x" + frameHeight, 75, 5);
		}
		if (systemUpdateTime != 0) {
			int seconds = systemUpdateTime / 50;
			int minutes = seconds / 60;
			int yOffset = frameMode == ScreenMode.FIXED ? 0 : frameHeight - 498;
			seconds %= 60;
			if (seconds < 10)
				regularText.render(0xffff00,
						"System update in: " + minutes + ":0" + seconds, 329 + yOffset,
						4);
			else
				regularText.render(0xffff00, "System update in: " + minutes + ":" + seconds,
						329 + yOffset, 4);
			anInt849++;
			if (anInt849 > 75) {
				anInt849 = 0;
				buffer.createFrame(148);
			}
		}
	}

	public void addIgnore(long l)
	{
		try
		{
			if(l == 0L)
				return;
			if(ignoreCount >= 100)
			{
				pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			for(int j = 0; j < ignoreCount; j++)
				if(ignoreListAsLongs[j] == l)
				{
					pushMessage(s + " is already on your ignore list", 0, "");
					return;
				}
			for(int k = 0; k < friendsCount; k++)
				if(friendsListAsLongs[k] == l)
				{
					pushMessage("Please remove " + s + " from your friend list first", 0, "");
					return;
				}

			ignoreListAsLongs[ignoreCount++] = l;
			buffer.createFrame(133);
			buffer.writeQWord(l);
			return;
		}
		catch(RuntimeException runtimeexception)
		{
			signlink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void method114()
	{
		for(int i = -1; i < playerCount; i++)
		{
			int j;
			if(i == -1)
				j = internalLocalPlayerIndex;
			else
				j = playerList[i];
			Player player = players[j];
			if(player != null)
				processMovement(player);
		}

	}

	public void method115()
	{
		if(loadingStage == 2)
		{
			for(SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext())
			{
				if(spawnedObject.getLongetivity > 0)
					spawnedObject.getLongetivity--;
				if(spawnedObject.getLongetivity == 0)
				{
					if(spawnedObject.anInt1299 < 0 || MapRegion.modelReady(spawnedObject.anInt1299, spawnedObject.anInt1301))
					{
						removeObject(spawnedObject.y, spawnedObject.plane, spawnedObject.anInt1300, spawnedObject.anInt1301, spawnedObject.x, spawnedObject.group, spawnedObject.anInt1299);
						spawnedObject.unlink();
					}
				} else
				{
					if(spawnedObject.delay > 0)
						spawnedObject.delay--;
					if(spawnedObject.delay == 0 && spawnedObject.x >= 1 && spawnedObject.y >= 1 && spawnedObject.x <= 102 && spawnedObject.y <= 102 && (spawnedObject.id < 0 || MapRegion.modelReady(spawnedObject.id, spawnedObject.type)))
					{
						removeObject(spawnedObject.y, spawnedObject.plane, spawnedObject.orientation, spawnedObject.type, spawnedObject.x, spawnedObject.group, spawnedObject.id);
						spawnedObject.delay = -1;
						if(spawnedObject.id == spawnedObject.anInt1299 && spawnedObject.anInt1299 == -1)
							spawnedObject.unlink();
						else
						if(spawnedObject.id == spawnedObject.anInt1299 && spawnedObject.orientation == spawnedObject.anInt1300 && spawnedObject.type == spawnedObject.anInt1301)
							spawnedObject.unlink();
					}
				}
			}

		}
	}

	private void determineMenuSize() {
		int boxLength = boldText.getTextWidth("Choose option");
		for (int row = 0; row < menuActionRow; row++) {
			int actionLength = boldText.getTextWidth(menuActionText[row]);
			if (actionLength > boxLength)
				boxLength = actionLength;
		}
		boxLength += 8;
		int offset = 15 * menuActionRow + 21;

		if (super.saveClickX > 0 && super.saveClickY > 0 && super.saveClickX < frameWidth && super.saveClickY < frameHeight) {
			int xClick = super.saveClickX - boxLength / 2;
			if (xClick + boxLength > frameWidth - 4) {
				xClick = frameWidth - 4 - boxLength;
			}
			if (xClick < 0) {
				xClick = 0;
			}
			int yClick = super.saveClickY - 0;
			if (yClick + offset > frameHeight - 6) {
				yClick = frameHeight - 6 - offset;
			}
			if (yClick < 0) {
				yClick = 0;
			}
			menuOpen = true;

			/*
			if (removeShiftDropOnMenuOpen && secondMenuAction[menuActionRow - 1] == 3214) {
				removeShiftDropOnMenuOpen = false;
				processRightClick();
			} */

			menuOffsetX = xClick;
			menuOffsetY = yClick;
			menuWidth = boxLength;
			menuHeight = 15 * menuActionRow + 22;
		}
	}

	public void method117(Buffer buffer)
	{
		buffer.initBitAccess();
		int j = buffer.readBits(1);
		if(j == 0)
			return;
		int k = buffer.readBits(2);
		if(k == 0)
		{
			mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = internalLocalPlayerIndex;
			return;
		}
		if(k == 1)
		{
			int l = buffer.readBits(3);
			localPlayer.moveInDir(false, l);
			int k1 = buffer.readBits(1);
			if(k1 == 1)
				mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = internalLocalPlayerIndex;
			return;
		}
		if(k == 2)
		{
			int i1 = buffer.readBits(3);
			localPlayer.moveInDir(true, i1);
			int l1 = buffer.readBits(3);
			localPlayer.moveInDir(true, l1);
			int j2 = buffer.readBits(1);
			if(j2 == 1)
				mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = internalLocalPlayerIndex;
			return;
		}
		if(k == 3)
		{
			plane = buffer.readBits(2);
			int j1 = buffer.readBits(1);
			int i2 = buffer.readBits(1);
			if(i2 == 1)
				mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = internalLocalPlayerIndex;
			int k2 = buffer.readBits(7);
			int l2 = buffer.readBits(7);
			localPlayer.setPos(l2, k2, j1 == 1);
		}
	}

	public void nullLoader()
	{
		aBoolean831 = false;
		while(drawingFlames)
		{
			aBoolean831 = false;
			try
			{
				Thread.sleep(50L);
			}
			catch(Exception _ex) { }
		}
		titleBoxIndexedImage = null;
		titleButtonIndexedImage = null;
		aIndexedImageArray1152s = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
		aClass30_Sub2_Sub1_Sub1_1201 = null;
		aClass30_Sub2_Sub1_Sub1_1202 = null;
	}

	private boolean processWidgetAnimations(int i, int j)
	{
		boolean flag1 = false;
		Widget class9 = Widget.interfaceCache[j];
		for(int k = 0; k < class9.children.length; k++)
		{
			if(class9.children[k] == -1)
				break;
			Widget class9_1 = Widget.interfaceCache[class9.children[k]];
			if(class9_1.type == 1)
				flag1 |= processWidgetAnimations(i, class9_1.id);
			if(class9_1.type == 6 && (class9_1.defaultAnimationId != -1 || class9_1.secondaryAnimationId != -1))
			{
				boolean flag2 = interfaceIsSelected(class9_1);
				int l;
				if(flag2)
					l = class9_1.secondaryAnimationId;
				else
					l = class9_1.defaultAnimationId;
				if(l != -1)
				{
					Animation animation = Animation.animations[l];
					for(class9_1.anInt208 += i; class9_1.anInt208 > animation.method258(class9_1.currentFrame);)
					{
						class9_1.anInt208 -= animation.method258(class9_1.currentFrame) + 1;
						class9_1.currentFrame++;
						if(class9_1.currentFrame >= animation.anInt352)
						{
							class9_1.currentFrame -= animation.anInt356;
							if(class9_1.currentFrame < 0 || class9_1.currentFrame >= animation.anInt352)
								class9_1.currentFrame = 0;
						}
						flag1 = true;
					}

				}
			}
		}

		return flag1;
	}

	private int setCameraLocation()
	{
		if (!Configuration.enableRoofs)
			return plane;
		int j = 3;
		if(yCameraCurve < 310)
		{
			int k = xCameraPos >> 7;
			int l = yCameraPos >> 7;
			int i1 = localPlayer.x >> 7;
			int j1 = localPlayer.y >> 7;
			if((tileFlags[plane][k][l] & 4) != 0)
				j = plane;
			int k1;
			if(i1 > k)
				k1 = i1 - k;
			else
				k1 = k - i1;
			int l1;
			if(j1 > l)
				l1 = j1 - l;
			else
				l1 = l - j1;
			if(k1 > l1)
			{
				int i2 = (l1 * 0x10000) / k1;
				int k2 = 32768;
				while(k != i1)
				{
					if(k < i1)
						k++;
					else
					if(k > i1)
						k--;
					if((tileFlags[plane][k][l] & 4) != 0)
						j = plane;
					k2 += i2;
					if(k2 >= 0x10000)
					{
						k2 -= 0x10000;
						if(l < j1)
							l++;
						else
						if(l > j1)
							l--;
						if((tileFlags[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			} else
			{
				int j2 = (k1 * 0x10000) / l1;
				int l2 = 32768;
				while(l != j1)
				{
					if(l < j1)
						l++;
					else
					if(l > j1)
						l--;
					if((tileFlags[plane][k][l] & 4) != 0)
						j = plane;
					l2 += j2;
					if(l2 >= 0x10000)
					{
						l2 -= 0x10000;
						if(k < i1)
							k++;
						else
						if(k > i1)
							k--;
						if((tileFlags[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			}
		}
		if((tileFlags[plane][localPlayer.x >> 7][localPlayer.y >> 7] & 4) != 0)
			j = plane;
		return j;
	}

	private int resetCameraHeight() {
		if (!Configuration.enableRoofs)
			return plane;
		int orientation = getCenterHeight(plane, yCameraPos, xCameraPos);
		if (orientation - zCameraPos < 800
				&& (tileFlags[plane][xCameraPos >> 7][yCameraPos >> 7] & 4) != 0)
			return plane;
		else
			return 3;
	}

	public void delIgnore(long l)
	{
		try
		{
			if(l == 0L)
				return;
			for(int j = 0; j < ignoreCount; j++)
				if(ignoreListAsLongs[j] == l)
				{
					ignoreCount--;
					System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);

					buffer.createFrame(74);
					buffer.writeQWord(l);
					return;
				}

			return;
		}
		catch(RuntimeException runtimeexception)
		{
			signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private void chatJoin(long l) {
		try {
			if(l == 0L)
				return;
			buffer.createFrame(60);
			buffer.writeQWord(l);
			return;
		}
		catch(RuntimeException runtimeexception)
		{
			signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();

	}

	public String getParameter(String s)
	{
		if(signlink.mainapp != null)
			return signlink.mainapp.getParameter(s);
		else
			return super.getParameter(s);
	}

	public void adjustVolume(boolean flag, int i) {
		signlink.setVolume(i);
		signlink.midivol = i;
		if(flag)
			signlink.midi = "voladjust";
	}

	private int executeScript(Widget class9, int j)
	{
		if(class9.valueIndexArray == null || j >= class9.valueIndexArray.length)
			return -2;
		try
		{
			int ai[] = class9.valueIndexArray[j];
			int k = 0;
			int l = 0;
			int i1 = 0;
			do
			{
				int j1 = ai[l++];
				int k1 = 0;
				byte byte0 = 0;
				if(j1 == 0)
					return k;
				if(j1 == 1)
					k1 = currentLevels[ai[l++]];
				if(j1 == 2)
					k1 = maximumLevels[ai[l++]];
				if(j1 == 3)
					k1 = currentExp[ai[l++]];
				if(j1 == 4)
				{
					Widget class9_1 = Widget.interfaceCache[ai[l++]];
					int k2 = ai[l++];
					if(k2 >= 0 && k2 < ItemDefinition.totalItems && (!ItemDefinition.lookup(k2).is_members_only || isMembers))
					{
						for(int j3 = 0; j3 < class9_1.inventoryItemId.length; j3++)
							if(class9_1.inventoryItemId[j3] == k2 + 1)
								k1 += class9_1.inventoryAmounts[j3];

					}
				}
				if(j1 == 5)
					k1 = variousSettings[ai[l++]];
				if(j1 == 6)
					k1 = SKILL_EXPERIENCE[maximumLevels[ai[l++]] - 1];
				if(j1 == 7)
					k1 = (variousSettings[ai[l++]] * 100) / 46875;
				if(j1 == 8)
					k1 = localPlayer.combatLevel;
				if(j1 == 9)
				{
					for(int l1 = 0; l1 < Skills.skillsCount; l1++)
						if(Skills.skillEnabled[l1])
							k1 += maximumLevels[l1];

				}
				if(j1 == 10)
				{
					Widget class9_2 = Widget.interfaceCache[ai[l++]];
					int l2 = ai[l++] + 1;
					if(l2 >= 0 && l2 < ItemDefinition.totalItems && (!ItemDefinition.lookup(l2).is_members_only || isMembers))
					{
						for(int k3 = 0; k3 < class9_2.inventoryItemId.length; k3++)
						{
							if(class9_2.inventoryItemId[k3] != l2)
								continue;
							k1 = 0x3b9ac9ff;
							break;
						}

					}
				}
				if(j1 == 11)
					k1 = runEnergy;
				if(j1 == 12)
					k1 = weight;
				if(j1 == 13)
				{
					int i2 = variousSettings[ai[l++]];
					int i3 = ai[l++];
					k1 = (i2 & 1 << i3) == 0 ? 0 : 1;
				}
				if(j1 == 14)
				{
					int j2 = ai[l++];
					VarBit varBit = VarBit.cache[j2];
					int l3 = varBit.anInt648;
					int i4 = varBit.anInt649;
					int j4 = varBit.anInt650;
					int k4 = anIntArray1232[j4 - i4];
					k1 = variousSettings[l3] >> i4 & k4;
				}
				if(j1 == 15)
					byte0 = 1;
				if(j1 == 16)
					byte0 = 2;
				if(j1 == 17)
					byte0 = 3;
				if(j1 == 18)
					k1 = (localPlayer.x >> 7) + regionBaseX;
				if(j1 == 19)
					k1 = (localPlayer.y >> 7) + regionBaseY;
				if(j1 == 20)
					k1 = ai[l++];
				if(byte0 == 0)
				{
					if(i1 == 0)
						k += k1;
					if(i1 == 1)
						k -= k1;
					if(i1 == 2 && k1 != 0)
						k /= k1;
					if(i1 == 3)
						k *= k1;
					i1 = 0;
				} else
				{
					i1 = byte0;
				}
			} while(true);
		}
		catch(Exception _ex)
		{
			return -1;
		}
	}

	private void drawMinimap() {
		if (frameMode == ScreenMode.FIXED) {
			minimapImageProducer.initDrawingArea();
		}
		if (minimapState == 2) {
			if (frameMode == ScreenMode.FIXED) {
				spriteCache.draw(19, 0, 0);
			} else {
				spriteCache.draw(44, frameWidth - 181, 0);
				spriteCache.draw(45, frameWidth - 158, 7);
			}
			if (frameMode != ScreenMode.FIXED && stackSideStones) {
				if (super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1
						&& super.mouseY >= 2 && super.mouseY <= 24 || tabId == 15) {
					spriteCache.draw(27, frameWidth - 25, 2);
				} else {
					spriteCache.draw(27, frameWidth - 25, 2, 165, true);
				}
			}
			loadAllOrbs();
			compass.rotate(33, cameraHorizontal, anIntArray1057, 256, anIntArray968,
					(frameMode == ScreenMode.FIXED ? 25 : 24), 4,
					(frameMode == ScreenMode.FIXED ? 29 : frameWidth - 176), 33, 25);
			if (menuOpen) {
				drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
			} else {
				drawHoverMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
			}
			if (frameMode == ScreenMode.FIXED) {
				minimapImageProducer.initDrawingArea();
			}
			return;
		}
		int angle = cameraHorizontal + minimapRotation & 0x7ff;
		int centreX = 48 + localPlayer.x / 32;
		int centreY = 464 - localPlayer.y / 32;
		minimapImage.rotate(151, angle, minimapLineWidth, 256 + minimapZoom, minimapLeft, centreY, (frameMode == ScreenMode.FIXED ? 9 : 7),
				(frameMode == ScreenMode.FIXED ? 54 : frameWidth - 158), 146, centreX);
		for (int icon = 0; icon < anInt1071; icon++) {
			int mapX = (minimapHintX[icon] * 4 + 2) - localPlayer.x / 32;
			int mapY = (minimapHintY[icon] * 4 + 2) - localPlayer.y / 32;
			markMinimap(minimapHint[icon], mapX, mapY);
		}
		for (int x = 0; x < 104; x++) {
			for (int y = 0; y < 104; y++) {
				Deque class19 = groundItems[plane][x][y];
				if (class19 != null) {
					int mapX = (x * 4 + 2) - localPlayer.x / 32;
					int mapY = (y * 4 + 2) - localPlayer.y / 32;
					markMinimap(mapDotItem, mapX, mapY);
				}
			}
		}
		for (int n = 0; n < npcCount; n++) {
			Npc npc = npcs[npcIndices[n]];
			if (npc != null && npc.isVisible()) {
				NpcDefinition npcDefinition = npc.desc;
				if (npcDefinition.childrenIDs != null) {
					npcDefinition = npcDefinition.morph();
				}
				if (npcDefinition != null && npcDefinition.drawMinimapDot && npcDefinition.clickable) {
					int mapX = npc.x / 32 - localPlayer.x / 32;
					int mapY = npc.y / 32 - localPlayer.y / 32;
					markMinimap(mapDotNPC, mapX, mapY);
				}
			}
		}
		for (int p = 0; p < playerCount; p++) {
			Player player = players[playerList[p]];
			if (player != null && player.isVisible()) {
				int mapX = player.x / 32 - localPlayer.x / 32;
				int mapY = player.y / 32 - localPlayer.y / 32;
				boolean friend = false;
				boolean clanMember = false;
				long nameHash = StringUtils.encodeBase37(player.name);
				for (int f = 0; f < friendsCount; f++) {
					if (nameHash != friendsListAsLongs[f] || friendsNodeIDs[f] == 0) {
						continue;
					}
					friend = true;
					break;
				}
				boolean team = false;
				if (localPlayer.team != 0 && player.team != 0
						&& localPlayer.team == player.team) {
					team = true;
				}
				if (friend) {
					markMinimap(mapDotFriend, mapX, mapY);
				} else if (clanMember) {
					markMinimap(mapDotClan, mapX, mapY);
				} else if (team) {
					markMinimap(mapDotTeam, mapX, mapY);
				} else {
					markMinimap(mapDotPlayer, mapX, mapY);
				}
			}
		}
		if (hintIconDrawType != 0 && tick % 20 < 10) {
			if (hintIconDrawType == 1 && hintIconNpcId >= 0 && hintIconNpcId < npcs.length) {
				Npc npc = npcs[hintIconNpcId];
				if (npc != null) {
					int mapX = npc.x / 32 - localPlayer.x / 32;
					int mapY = npc.y / 32 - localPlayer.y / 32;
					refreshMinimap(mapMarker, mapY, mapX);
				}
			}
			if (hintIconDrawType == 2) {
				int mapX = ((hintIconX - regionBaseX) * 4 + 2) - localPlayer.x / 32;
				int mapY = ((hintIconY - regionBaseY) * 4 + 2) - localPlayer.y / 32;
				refreshMinimap(mapMarker, mapY, mapX);
			}
			if (hintIconDrawType == 10 && hintIconPlayerId >= 0
					&& hintIconPlayerId < players.length) {
				Player player = players[hintIconPlayerId];
				if (player != null) {
					int mapX = player.x / 32 - localPlayer.x / 32;
					int mapY = player.y / 32 - localPlayer.y / 32;
					refreshMinimap(mapMarker, mapY, mapX);
				}
			}
		}
		if (destinationX != 0) {
			int mapX = (destinationX * 4 + 2) - localPlayer.x / 32;
			int mapY = (destinationY * 4 + 2) - localPlayer.y / 32;
			markMinimap(mapFlag, mapX, mapY);
		}
		Rasterizer2D.drawBox((frameMode == ScreenMode.FIXED ? 127 : frameWidth - 88), (frameMode == ScreenMode.FIXED ? 83 : 80), 3, 3,
				0xffffff);
		if (frameMode == ScreenMode.FIXED) {
			spriteCache.draw(19, 0, 0);
		} else {
			spriteCache.draw(44, frameWidth - 181, 0);
		}
		compass.rotate(33, cameraHorizontal, anIntArray1057, 256, anIntArray968,
				(frameMode == ScreenMode.FIXED ? 25 : 24), 4,
				(frameMode == ScreenMode.FIXED ? 29 : frameWidth - 176), 33, 25);
		if (frameMode != ScreenMode.FIXED && stackSideStones) {
			if (super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1
					&& super.mouseY >= 2 && super.mouseY <= 24 || tabId == 10) {
				spriteCache.draw(27, frameWidth - 25, 2);
			} else {
				spriteCache.draw(27, frameWidth - 25, 2, 165, true);
			}
		}
		loadAllOrbs();
		if (menuOpen) {
			drawMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
		} else {
			drawHoverMenu(frameMode == ScreenMode.FIXED ? 516 : 0, 0);
		}
		if (frameMode == ScreenMode.FIXED) {
			gameScreenImageProducer.initDrawingArea();
		}
	}

	private void loadAllOrbs() {

		boolean fixed = frameMode == ScreenMode.FIXED;
		boolean specOrb = Configuration.enableSpecOrb;
		int xOffset = fixed ? 0 : frameWidth - 217;
		if (!Configuration.enableOrbs) {
			return;
		}

		loadHpOrb(xOffset);
		loadPrayerOrb(xOffset, specOrb ? 0 : 11);
		loadRunOrb(specOrb ? xOffset : xOffset + 13, specOrb ? 0 : 15);

		/* World map */
		spriteCache.draw(worldHover ? 52 : 51, fixed ? 196 : frameWidth - 34, fixed ? 126 : 139);
		/* Xp counter */
		int offSprite = Configuration.expCounterOpen ? 53 : 22;
		int onSprite = Configuration.expCounterOpen ? 54 : 23;
		spriteCache.draw(expCounterHover ? onSprite : offSprite, fixed ? 0 : frameWidth - 216, 21);
	}

	private void loadHpOrb(int xOffset) {
		Sprite bg = spriteCache.lookup(hpHover ? 8 : 7);

		int orbFillSprite = 0;
		int poisonType = 0;
		if (poisonType == 1) {
			orbFillSprite = 616;// Poison
		} else if (poisonType == 2) {
			orbFillSprite = 617;// Venom
		}

		Sprite fg = spriteCache.lookup(orbFillSprite);
		Sprite orb = spriteCache.lookup(14);
		bg.drawSprite(0 + xOffset, 41);
		fg.drawSprite(27 + xOffset, 45);
		int level = currentLevels[3];
		int max = maximumLevels[3];
		double percent = level / (double) max;
		orb.myHeight = (int) (26 * (1 - percent));
		orb.drawSprite(27 + xOffset, 45);
		if (percent <= .25) {
			spriteCache.lookup(9).drawSprite1(33 + xOffset, 52, 200 + (int) (50 * Math.sin(tick / 7.0)));
		} else {
			spriteCache.lookup(9).drawSprite(33 + xOffset, 52);
		}
		smallText.drawCenteredString(getOrbTextColor((int) (percent * 100)), 15 + xOffset, "" + level, 67, true);
	}

	private void loadPrayerOrb(int xOffset, int yOffset) {
		Sprite bg = spriteCache.lookup(prayHover ? 8 : 7);
		Sprite fg = spriteCache.lookup(prayClicked ? 2 : 1);
		Sprite orb = spriteCache.lookup(14);
		bg.drawSprite(0 + xOffset, 74 + yOffset);
		fg.drawSprite(27 + xOffset, 79 + yOffset);
		int level = currentLevels[5];
		int max = maximumLevels[5];
		double percent = level / (double) max;
		orb.myHeight = (int) (26 * (1 - percent));
		orb.drawSprite(27 + xOffset, 79 + yOffset);
		if (percent <= .25) {
			spriteCache.lookup(10).drawSprite1(30 + xOffset, 82 + yOffset, 200 + (int) (50 * Math.sin(tick / 7.0)));
		} else {
			spriteCache.lookup(10).drawSprite(30 + xOffset, 82 + yOffset);
		}
		smallText.drawCenteredString(getOrbTextColor((int) (percent * 100)), 15 + xOffset, level + "", 100 + yOffset, true);
	}

	private void loadRunOrb(int xOffset, int yOffset) {
		Sprite bg = spriteCache.lookup(runHover ? 8 : 7);
		Sprite fg = spriteCache.lookup(variousSettings[152] == 1 ? 4 : 3);
		Sprite orb = spriteCache.lookup(14);
		bg.drawSprite(10 + xOffset, 107 + yOffset);
		fg.drawSprite(37 + xOffset, 111 + yOffset);
		int level = runEnergy;
		double percent = level / (double) 100;
		orb.myHeight = (int) (26 * (1 - percent));
		orb.drawSprite(37 + xOffset, 111 + yOffset);
		if (percent <= .25) {
			spriteCache.lookup(variousSettings[152] == 1 ? 12 : 11).drawSprite1(43 + xOffset, 115 + yOffset, 200 + (int) (50 * Math.sin(tick / 7.0)));
		} else {
			spriteCache.lookup(variousSettings[152] == 1 ? 12 : 11).drawSprite(43 + xOffset, 115 + yOffset);
		}
		// cacheSprite[336].drawSprite(20 + xOffset, 125 + yOffset);
		smallText.drawCenteredString(getOrbTextColor((int) (percent * 100)), 24 + xOffset, Integer.toString(runEnergy), 132 + yOffset, true);
	}

	public void npcScreenPos(Mob mob, int i) {
		calcEntityScreenPos(mob.x, i, mob.y);
	}

	public void calcEntityScreenPos(int i, int j, int l) {
		if(i < 128 || l < 128 || i > 13056 || l > 13056) {
			spriteDrawX = -1;
			spriteDrawY = -1;
			return;
		}
		int i1 = getCenterHeight(plane, l, i) - j;
		i -= xCameraPos;
		i1 -= zCameraPos;
		l -= yCameraPos;
		int j1 = Model.SINE[yCameraCurve];
		int k1 = Model.COSINE[yCameraCurve];
		int l1 = Model.SINE[xCameraCurve];
		int i2 = Model.COSINE[xCameraCurve];
		int j2 = l * l1 + i * i2 >> 16;
		l = l * i2 - i * l1 >> 16;
		i = j2;
		j2 = i1 * k1 - l * j1 >> 16;
		l = i1 * j1 + l * k1 >> 16;
		i1 = j2;
		if(l >= 50) {
			spriteDrawX = Rasterizer3D.originViewX + (i << SceneGraph.viewDistance) / l;
			spriteDrawY = Rasterizer3D.originViewY + (i1 << SceneGraph.viewDistance) / l;
		} else {
			spriteDrawX = -1;
			spriteDrawY = -1;
		}
	}

	public void buildSplitPrivateChatMenu()
	{
		if(splitPrivateChat == 0)
			return;
		int i = 0;
		if(systemUpdateTime != 0)
			i = 1;
		for(int j = 0; j < 100; j++)
			if(chatMessages[j] != null)
			{
				int k = chatTypes[j];
				String s = chatNames[j];
				boolean flag1 = false;
				if(s != null && s.startsWith("@cr1@"))
				{
					s = s.substring(5);
					boolean flag2 = true;
				}
				if(s != null && s.startsWith("@cr2@"))
				{
					s = s.substring(5);
					byte byte0 = 2;
				}
				if(s != null && s.startsWith("@cr3@"))
				{
					s = s.substring(5);
					boolean flag2 = true;
				}
				if(s != null && s.startsWith("@cr4@"))
				{
					s = s.substring(5);
					boolean flag2 = true;
				}
				if(s != null && s.startsWith("@cr5@"))
				{
					s = s.substring(5);
					boolean flag2 = true;
				}
				if(s != null && s.startsWith("@cr6@"))
				{
					s = s.substring(5);
					boolean flag2 = true;
				}
				if(s != null && s.startsWith("@cr7@"))
				{
					s = s.substring(6);
					boolean flag2 = true;
				}
				if((k == 3 || k == 7) && (k == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				{
					int l = 329 - i * 13;
					if(super.mouseX > 4 && super.mouseY - 4 > l - 10 && super.mouseY - 4 <= l + 3)
					{
						int i1 = regularText.getTextWidth("From:  " + s + chatMessages[j]) + 25;
						if(i1 > 450)
							i1 = 450;
						if(super.mouseX < 4 + i1)
						{
							if(myPrivilege >= 1)
							{
								menuActionText[menuActionRow] = "Report abuse @whi@" + s;
								menuActionTypes[menuActionRow] = 2606;
								menuActionRow++;
							}
							menuActionText[menuActionRow] = "Add ignore @whi@" + s;
							menuActionTypes[menuActionRow] = 2042;
							menuActionRow++;
							menuActionText[menuActionRow] = "Add friend @whi@" + s;
							menuActionTypes[menuActionRow] = 2337;
							menuActionRow++;
						}
					}
					if(++i >= 5)
						return;
				}
				if((k == 5 || k == 6) && privateChatMode < 2 && ++i >= 5)
					return;
			}

	}

	private void requestSpawnObject(int longetivity, int id, int orientation, int group, int y, int type,
									int plane, int x, int delay) {
		SpawnedObject object = null;
		for (SpawnedObject node = (SpawnedObject) spawns.reverseGetFirst(); node != null; node =
				(SpawnedObject) spawns.reverseGetNext()) {
			if (node.plane != plane || node.x != x || node.y != y || node.group != group)
				continue;
			object = node;
			break;
		}

		if (object == null) {
			object = new SpawnedObject();
			object.plane = plane;
			object.group = group;
			object.x = x;
			object.y = y;
			method89(object);
			spawns.insertHead(object);
		}
		object.id = id;
		object.type = type;
		object.orientation = orientation;
		object.delay = delay;
		object.getLongetivity = longetivity;
	}

	private boolean interfaceIsSelected(Widget class9)
	{
		if(class9.valueCompareType == null)
			return false;
		for(int i = 0; i < class9.valueCompareType.length; i++)
		{
			int j = executeScript(class9, i);
			int k = class9.requiredValues[i];
			if(class9.valueCompareType[i] == 2)
			{
				if(j >= k)
					return false;
			} else
			if(class9.valueCompareType[i] == 3)
			{
				if(j <= k)
					return false;
			} else
			if(class9.valueCompareType[i] == 4)
			{
				if(j == k)
					return false;
			} else
			if(j != k)
				return false;
		}

		return true;
	}

	private DataInputStream openJagGrabInputStream(String s)
		throws IOException
	{
 //	   if(!aBoolean872)
 //		   if(Signlink.mainapp != null)
 //			   return Signlink.openurl(s);
 //		   else
 //			   return new DataInputStream((new URL(getCodeBase(), s)).openStream());
		if(aSocket832 != null)
		{
			try
			{
				aSocket832.close();
			}
			catch(Exception _ex) { }
			aSocket832 = null;
		}
		aSocket832 = openSocket(43595);
		aSocket832.setSoTimeout(10000);
		java.io.InputStream inputstream = aSocket832.getInputStream();
		OutputStream outputstream = aSocket832.getOutputStream();
		outputstream.write(("JAGGRAB /" + s + "\n\n").getBytes());
		return new DataInputStream(inputstream);
	}

	public void doFlamesDrawing()
	{
		char c = '\u0100';
		if(anInt1040 > 0)
		{
			for(int i = 0; i < 256; i++)
				if(anInt1040 > 768)
					anIntArray850[i] = method83(anIntArray851[i], anIntArray852[i], 1024 - anInt1040);
				else
				if(anInt1040 > 256)
					anIntArray850[i] = anIntArray852[i];
				else
					anIntArray850[i] = method83(anIntArray852[i], anIntArray851[i], 256 - anInt1040);

		} else
		if(anInt1041 > 0)
		{
			for(int j = 0; j < 256; j++)
				if(anInt1041 > 768)
					anIntArray850[j] = method83(anIntArray851[j], anIntArray853[j], 1024 - anInt1041);
				else
				if(anInt1041 > 256)
					anIntArray850[j] = anIntArray853[j];
				else
					anIntArray850[j] = method83(anIntArray853[j], anIntArray851[j], 256 - anInt1041);

		} else
		{
			System.arraycopy(anIntArray851, 0, anIntArray850, 0, 256);

		}
		System.arraycopy(aClass30_Sub2_Sub1_Sub1_1201.myPixels, 0, flameLeftBackground.canvasRaster, 0, 33920);

		int i1 = 0;
		int j1 = 1152;
		for(int k1 = 1; k1 < c - 1; k1++)
		{
			int l1 = (anIntArray969[k1] * (c - k1)) / c;
			int j2 = 22 + l1;
			if(j2 < 0)
				j2 = 0;
			i1 += j2;
			for(int l2 = j2; l2 < 128; l2++)
			{
				int j3 = anIntArray828[i1++];
				if(j3 != 0)
				{
					int l3 = j3;
					int j4 = 256 - j3;
					j3 = anIntArray850[j3];
					int l4 = flameLeftBackground.canvasRaster[j1];
					flameLeftBackground.canvasRaster[j1++] = ((j3 & 0xff00ff) * l3 + (l4 & 0xff00ff) * j4 & 0xff00ff00) + ((j3 & 0xff00) * l3 + (l4 & 0xff00) * j4 & 0xff0000) >> 8;
				} else
				{
					j1++;
				}
			}

			j1 += j2;
		}

		flameLeftBackground.drawGraphics(0, super.graphics, 0);
		System.arraycopy(aClass30_Sub2_Sub1_Sub1_1202.myPixels, 0, flameRightBackground.canvasRaster, 0, 33920);

		i1 = 0;
		j1 = 1176;
		for(int k2 = 1; k2 < c - 1; k2++)
		{
			int i3 = (anIntArray969[k2] * (c - k2)) / c;
			int k3 = 103 - i3;
			j1 += i3;
			for(int i4 = 0; i4 < k3; i4++)
			{
				int k4 = anIntArray828[i1++];
				if(k4 != 0)
				{
					int i5 = k4;
					int j5 = 256 - k4;
					k4 = anIntArray850[k4];
					int k5 = flameRightBackground.canvasRaster[j1];
					flameRightBackground.canvasRaster[j1++] = ((k4 & 0xff00ff) * i5 + (k5 & 0xff00ff) * j5 & 0xff00ff00) + ((k4 & 0xff00) * i5 + (k5 & 0xff00) * j5 & 0xff0000) >> 8;
				} else
				{
					j1++;
				}
			}

			i1 += 128 - k3;
			j1 += 128 - k3 - i3;
		}

		flameRightBackground.drawGraphics(0, super.graphics, 637);
	}

	public void method134(Buffer buffer)
	{
		int j = buffer.readBits(8);
		if(j < playerCount)
		{
			for(int k = j; k < playerCount; k++)
				anIntArray840[anInt839++] = playerList[k];

		}
		if(j > playerCount)
		{
			signlink.reporterror(myUsername + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for(int l = 0; l < j; l++)
		{
			int i1 = playerList[l];
			Player player = players[i1];
			int j1 = buffer.readBits(1);
			if(j1 == 0)
			{
				playerList[playerCount++] = i1;
				player.time = tick;
			} else
			{
				int k1 = buffer.readBits(2);
				if(k1 == 0)
				{
					playerList[playerCount++] = i1;
					player.time = tick;
					mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = i1;
				} else
				if(k1 == 1)
				{
					playerList[playerCount++] = i1;
					player.time = tick;
					int l1 = buffer.readBits(3);
					player.moveInDir(false, l1);
					int j2 = buffer.readBits(1);
					if(j2 == 1)
						mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = i1;
				} else
				if(k1 == 2)
				{
					playerList[playerCount++] = i1;
					player.time = tick;
					int i2 = buffer.readBits(3);
					player.moveInDir(true, i2);
					int k2 = buffer.readBits(3);
					player.moveInDir(true, k2);
					int l2 = buffer.readBits(1);
					if(l2 == 1)
						mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = i1;
				} else
				if(k1 == 3)
					anIntArray840[anInt839++] = i1;
			}
		}
	}

	private void drawLoginScreen(boolean flag) {
		setupLoginScreen();
		loginBoxImageProducer.initDrawingArea();
		titleBoxIndexedImage.draw(0, 0);
		char c = '\u0168';
		char c1 = '\310';
		if (loginScreenState == 0) {
			int i = c1 / 2 + 80;
			//smallText.drawCenteredString(0x75a9a9, c / 2, resourceProvider.loadingMessage, i, true);
			i = c1 / 2 - 20;
			boldText.drawCenteredString(0xffff00, c / 2, "Welcome to " + Configuration.CLIENT_NAME, i, true);
			i += 30;
			int l = c / 2 - 80;
			int k1 = c1 / 2 + 20;
			titleButtonIndexedImage.draw(l - 73, k1 - 20);
			boldText.drawCenteredString(0xffffff, l, "New User", k1 + 5, true);
			l = c / 2 + 80;
			titleButtonIndexedImage.draw(l - 73, k1 - 20);
			boldText.drawCenteredString(0xffffff, l, "Existing User", k1 + 5, true);
		}
		if (loginScreenState == 2) {
			int j = c1 / 2 - 45;
			if (firstLoginMessage.length() > 0) {
				boldText.drawCenteredString(0xffff00, c / 2, firstLoginMessage, j - 15, true);
				boldText.drawCenteredString(0xffff00, c / 2, secondLoginMessage, j, true);
				j += 25;
			} else {
				boldText.drawCenteredString(0xffff00, c / 2, secondLoginMessage, j - 7, true);
				j += 25;
			}

			boldText.drawTextWithPotentialShadow(true, c / 2 - 90, 0xffffff, "Login: " + myUsername
							+ ((loginScreenCursorPos == 0) & (tick % 40 < 20)
							? "@yel@|" : ""),
					j);
			j += 15;
			boldText.drawTextWithPotentialShadow(true, c / 2 - 90, 0xffffff,
					"Password: " + StringUtils.passwordAsterisks(myPassword)
							+ ((loginScreenCursorPos == 1) & (tick % 40 < 20)
							? "@yel@|" : ""),
					j);
			j += 15;

			// Remember me
			rememberUsernameHover = mouseInRegion(269, 284, 279, 292);
			if (rememberUsername) {
				spriteCache.draw(rememberUsernameHover ? 346 : 348, 67, 108, true);
			} else {
				spriteCache.draw(rememberUsernameHover ? 345 : 347, 67, 108, true);
			}
			smallText.drawCenteredString(0xffff00, 136, "Remember username", 120, true);

			// Hide username
			rememberPasswordHover = mouseInRegion(410, 425, 279, 292);
			if (rememberPassword) {
				spriteCache.draw(rememberPasswordHover ? 346 : 348, 208, 108, true);
			} else {
				spriteCache.draw(rememberPasswordHover ? 345 : 347, 208, 108, true);
			}
			smallText.drawCenteredString(0xffff00, 276, "Remember password", 120, true);

			forgottenPasswordHover = mouseInRegion(288, 471, 346, 357);
			smallText.drawCenteredString(0xffff00, 178, "Forgotten your password? @whi@Click here.", 186, true);

			if (!flag) {
				int i1 = c / 2 - 80;
				int l1 = c1 / 2 + 50;
				titleButtonIndexedImage.draw(i1 - 73, l1 - 20);
				boldText.drawCenteredString(0xffffff, i1, "Login", l1 + 5, true);
				i1 = c / 2 + 80;
				titleButtonIndexedImage.draw(i1 - 73, l1 - 20);
				boldText.drawCenteredString(0xffffff, i1, "Cancel", l1 + 5, true);
			}
		}
		loginBoxImageProducer.drawGraphics(171, super.graphics, 202);
		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
			topLeft1BackgroundTile.drawGraphics(0, super.graphics, 128);
			bottomLeft1BackgroundTile.drawGraphics(371, super.graphics, 202);
			bottomLeft0BackgroundTile.drawGraphics(265, super.graphics, 0);
			bottomRightImageProducer.drawGraphics(265, super.graphics, 562);
			middleLeft1BackgroundTile.drawGraphics(171, super.graphics, 128);
			aRSImageProducer_1115.drawGraphics(171, super.graphics, 562);
		}
		loginScreenAccessories();
	}

	private void loginScreenAccessories() {
		/*
		 * World-selection
		 */
		setupLoginScreen();

		loginScreenAccessories.drawGraphics(400, super.graphics, 0);
		loginScreenAccessories.initDrawingArea();
		spriteCache.draw(57, 6, 63);

		boldText.drawCenteredString(0xffffff, 55, "World 1", 78, true);
		smallText.drawCenteredString(0xffffff, 55, "Main world", 92, true);

		loginMusicImageProducer.drawGraphics(265, super.graphics, 562);
		loginMusicImageProducer.initDrawingArea();
		if (Configuration.enableMusic) {
			spriteCache.draw(58, 158, 196);
		} else {
			spriteCache.draw(59, 158, 196);
		}

	}


	public void drawFlames()
	{
		drawingFlames = true;
		try
		{
			long l = System.currentTimeMillis();
			int i = 0;
			int j = 20;
			while(aBoolean831)
			{
				anInt1208++;
				calcFlamesPosition();
				calcFlamesPosition();
				doFlamesDrawing();
				if(++i > 10)
				{
					long l1 = System.currentTimeMillis();
					int k = (int)(l1 - l) / 10 - j;
					j = 40 - k;
					if(j < 5)
						j = 5;
					i = 0;
					l = l1;
				}
				try
				{
					Thread.sleep(j);
				}
				catch(Exception _ex) { }
			}
		}
		catch(Exception _ex) { }
		drawingFlames = false;
	}

	public void raiseWelcomeScreen()
	{
		welcomeScreenRaised = true;
	}

	public void method137(Buffer buffer, int j)
	{
		if(j == 84)
		{
			int k = buffer.readUnsignedByte();
			int j3 = localX + (k >> 4 & 7);
			int i6 = localY + (k & 7);
			int l8 = buffer.readUShort();
			int k11 = buffer.readUShort();
			int l13 = buffer.readUShort();
			if(j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104)
			{
				Deque class19_1 = groundItems[plane][j3][i6];
				if(class19_1 != null)
				{
					for(Item class30_sub2_sub4_sub2_3 = (Item)class19_1.reverseGetFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (Item)class19_1.reverseGetNext())
					{
						if(class30_sub2_sub4_sub2_3.ID != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.anInt1559 != k11)
							continue;
						class30_sub2_sub4_sub2_3.anInt1559 = l13;
						break;
					}

					spawnGroundItem(j3, i6);
				}
			}
			return;
		}
		if(j == 105)
		{
			int l = buffer.readUnsignedByte();
			int k3 = localX + (l >> 4 & 7);
			int j6 = localY + (l & 7);
			int i9 = buffer.readUShort();
			int l11 = buffer.readUnsignedByte();
			int i14 = l11 >> 4 & 0xf;
			int i16 = l11 & 7;
			if(localPlayer.pathX[0] >= k3 - i14 && localPlayer.pathX[0] <= k3 + i14 && localPlayer.pathY[0] >= j6 - i14 && localPlayer.pathY[0] <= j6 + i14 && aBoolean848 && !lowMem && trackCount < 50)
			{
				anIntArray1207[trackCount] = i9;
				trackLoops[trackCount] = i16;
				anIntArray1250[trackCount] = Sounds.anIntArray326[i9];
				trackCount++;
			}
		}
		if(j == 215)
		{
			int i1 = buffer.method435();
			int l3 = buffer.method428();
			int k6 = localX + (l3 >> 4 & 7);
			int j9 = localY + (l3 & 7);
			int i12 = buffer.method435();
			int j14 = buffer.readUShort();
			if(k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != localPlayerIndex)
			{
				Item class30_sub2_sub4_sub2_2 = new Item();
				class30_sub2_sub4_sub2_2.ID = i1;
				class30_sub2_sub4_sub2_2.anInt1559 = j14;
				if(groundItems[plane][k6][j9] == null)
					groundItems[plane][k6][j9] = new Deque();
				groundItems[plane][k6][j9].insertHead(class30_sub2_sub4_sub2_2);
				spawnGroundItem(k6, j9);
			}
			return;
		}
		if(j == 156)
		{
			int j1 = buffer.method426();
			int i4 = localX + (j1 >> 4 & 7);
			int l6 = localY + (j1 & 7);
			int k9 = buffer.readUShort();
			if(i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104)
			{
				Deque class19 = groundItems[plane][i4][l6];
				if(class19 != null)
				{
					for(Item item = (Item)class19.reverseGetFirst(); item != null; item = (Item)class19.reverseGetNext())
					{
						if(item.ID != (k9 & 0x7fff))
							continue;
						item.unlink();
						break;
					}

					if(class19.reverseGetFirst() == null)
						groundItems[plane][i4][l6] = null;
					spawnGroundItem(i4, l6);
				}
			}
			return;
		}
		if(j == 160)
		{
			int k1 = buffer.method428();
			int j4 = localX + (k1 >> 4 & 7);
			int i7 = localY + (k1 & 7);
			int l9 = buffer.method428();
			int j12 = l9 >> 2;
			int k14 = l9 & 3;
			int j16 = anIntArray1177[j12];
			int j17 = buffer.method435();
			if(j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103)
			{
				int j18 = tileHeights[plane][j4][i7];
				int i19 = tileHeights[plane][j4 + 1][i7];
				int l19 = tileHeights[plane][j4 + 1][i7 + 1];
				int k20 = tileHeights[plane][j4][i7 + 1];
				if(j16 == 0)
				{
					WallObject class10 = scene.getWallObject(plane, j4, i7);
					if(class10 != null)
					{
						int k21 = class10.uid >> 14 & 0x7fff;
						if(j12 == 2)
						{
							class10.renderable1 = new SceneObject(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
							class10.renderable2 = new SceneObject(k21, k14 + 1 & 3, 2, i19, l19, j18, k20, j17, false);
						} else
						{
							class10.renderable1 = new SceneObject(k21, k14, j12, i19, l19, j18, k20, j17, false);
						}
					}
				}
				if(j16 == 1)
				{
					WallDecoration class26 = scene.getWallDecoration(j4, i7, plane);
					if(class26 != null)
						class26.renderable = new SceneObject(class26.uid >> 14 & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
				}
				if(j16 == 2)
				{
					GameObject class28 = scene.getGameObject(j4, i7, plane);
					if(j12 == 11)
						j12 = 10;
					if(class28 != null)
						class28.renderable = new SceneObject(class28.uid >> 14 & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
				}
				if(j16 == 3)
				{
					GroundDecoration class49 = scene.getGroundDecoration(i7, j4, plane);
					if(class49 != null)
						class49.renderable = new SceneObject(class49.uid >> 14 & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
				}
			}
			return;
		}
		if(j == 147)
		{
			int l1 = buffer.method428();
			int k4 = localX + (l1 >> 4 & 7);
			int j7 = localY + (l1 & 7);
			int i10 = buffer.readUShort();
			byte byte0 = buffer.method430();
			int l14 = buffer.method434();
			byte byte1 = buffer.method429();
			int k17 = buffer.readUShort();
			int k18 = buffer.method428();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = anIntArray1177[j19];
			byte byte2 = buffer.readSignedByte();
			int l21 = buffer.readUShort();
			byte byte3 = buffer.method429();
			Player player;
			if(i10 == localPlayerIndex)
				player = localPlayer;
			else
				player = players[i10];
			if(player != null)
			{
				ObjectDefinition class46 = ObjectDefinition.lookup(l21);
				int i22 = tileHeights[plane][k4][j7];
				int j22 = tileHeights[plane][k4 + 1][j7];
				int k22 = tileHeights[plane][k4 + 1][j7 + 1];
				int l22 = tileHeights[plane][k4][j7 + 1];
				Model model = class46.modelAt(j19, i20, i22, j22, k22, l22, -1);
				if(model != null)
				{
					requestSpawnObject(k17 + 1, -1, 0, l20, j7, 0, plane, k4, l14 + 1);
					player.anInt1707 = l14 + tick;
					player.anInt1708 = k17 + tick;
					player.aModel_1714 = model;
					int i23 = class46.objectSizeX;
					int j23 = class46.objectSizeY;
					if(i20 == 1 || i20 == 3)
					{
						i23 = class46.objectSizeY;
						j23 = class46.objectSizeX;
					}
					player.anInt1711 = k4 * 128 + i23 * 64;
					player.anInt1713 = j7 * 128 + j23 * 64;
					player.anInt1712 = getCenterHeight(plane, player.anInt1713, player.anInt1711);
					if(byte2 > byte0)
					{
						byte byte4 = byte2;
						byte2 = byte0;
						byte0 = byte4;
					}
					if(byte3 > byte1)
					{
						byte byte5 = byte3;
						byte3 = byte1;
						byte1 = byte5;
					}
					player.anInt1719 = k4 + byte2;
					player.anInt1721 = k4 + byte0;
					player.anInt1720 = j7 + byte3;
					player.anInt1722 = j7 + byte1;
				}
			}
		}
		if(j == 151)
		{
			int i2 = buffer.method426();
			int l4 = localX + (i2 >> 4 & 7);
			int k7 = localY + (i2 & 7);
			int j10 = buffer.method434();
			int k12 = buffer.method428();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = anIntArray1177[i15];
			if(l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104)
				requestSpawnObject(-1, j10, k16, l17, k7, i15, plane, l4, 0);
			return;
		}
		if(j == 4)
		{
			int j2 = buffer.readUnsignedByte();
			int i5 = localX + (j2 >> 4 & 7);
			int l7 = localY + (j2 & 7);
			int k10 = buffer.readUShort();
			int l12 = buffer.readUnsignedByte();
			int j15 = buffer.readUShort();
			if(i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104)
			{
				i5 = i5 * 128 + 64;
				l7 = l7 * 128 + 64;
				Projectile class30_sub2_sub4_sub3 = new Projectile(plane, tick, j15, k10, getCenterHeight(plane, l7, i5) - l12, l7, i5);
				projectiles.insertHead(class30_sub2_sub4_sub3);
			}
			return;
		}
		if(j == 44)
		{
			int k2 = buffer.method436();
			int j5 = buffer.readUShort();
			int i8 = buffer.readUnsignedByte();
			int l10 = localX + (i8 >> 4 & 7);
			int i13 = localY + (i8 & 7);
			if(l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104)
			{
				Item class30_sub2_sub4_sub2_1 = new Item();
				class30_sub2_sub4_sub2_1.ID = k2;
				class30_sub2_sub4_sub2_1.anInt1559 = j5;
				if(groundItems[plane][l10][i13] == null)
					groundItems[plane][l10][i13] = new Deque();
				groundItems[plane][l10][i13].insertHead(class30_sub2_sub4_sub2_1);
				spawnGroundItem(l10, i13);
			}
			return;
		}
		if(j == 101)
		{
			int l2 = buffer.method427();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int j13 = buffer.readUnsignedByte();
			if (k5 >= anIntArray1177.length) {
				//System.out.println((regionBaseX + localX) + " " + (regionBaseY + localY));
				//System.out.println(l2 + " " + j13);
				return;
			}
			int i11 = anIntArray1177[k5];
			int k15 = localX + (j13 >> 4 & 7);
			int l16 = localY + (j13 & 7);
			if(k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104)
				requestSpawnObject(-1, -1, j8, i11, l16, k5, plane, k15, 0);
			return;
		}
		if(j == 117)
		{
			int i3 = buffer.readUnsignedByte();
			int l5 = localX + (i3 >> 4 & 7);
			int k8 = localY + (i3 & 7);
			int j11 = l5 + buffer.readSignedByte();
			int k13 = k8 + buffer.readSignedByte();
			int l15 = buffer.readSignedWord();
			int i17 = buffer.readUShort();
			int i18 = buffer.readUnsignedByte() * 4;
			int l18 = buffer.readUnsignedByte() * 4;
			int k19 = buffer.readUShort();
			int j20 = buffer.readUShort();
			int i21 = buffer.readUnsignedByte();
			int j21 = buffer.readUnsignedByte();
			if(l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535)
			{
				l5 = l5 * 128 + 64;
				k8 = k8 * 128 + 64;
				j11 = j11 * 128 + 64;
				k13 = k13 * 128 + 64;
				Renderable_Sub4 class30_sub2_sub4_sub4 = new Renderable_Sub4(i21, l18, k19 + tick, j20 + tick, j21, plane, getCenterHeight(plane, k8, l5) - i18, k8, l5, l15, i17);
				class30_sub2_sub4_sub4.method455(k19 + tick, k13, getCenterHeight(plane, k13, j11) - l18, j11);
				aClass19_1013.insertHead(class30_sub2_sub4_sub4);
			}
		}
	}

	private static void setLowMem()
	{
		SceneGraph.lowMem = true;
		Rasterizer3D.lowMem = true;
		lowMem = true;
		MapRegion.lowMem = true;
		ObjectDefinition.lowMem = true;
	}

	public void method139(Buffer buffer)
	{
		buffer.initBitAccess();
		int k = buffer.readBits(8);
		if(k < npcCount)
		{
			for(int l = k; l < npcCount; l++)
				anIntArray840[anInt839++] = npcIndices[l];

		}
		if(k > npcCount)
		{
			signlink.reporterror(myUsername + " Too many npcs");
			throw new RuntimeException("eek");
		}
		npcCount = 0;
		for(int i1 = 0; i1 < k; i1++)
		{
			int j1 = npcIndices[i1];
			Npc npc = npcs[j1];
			int k1 = buffer.readBits(1);
			if(k1 == 0)
			{
				npcIndices[npcCount++] = j1;
				npc.time = tick;
			} else
			{
				int l1 = buffer.readBits(2);
				if(l1 == 0)
				{
					npcIndices[npcCount++] = j1;
					npc.time = tick;
					mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = j1;
				} else
				if(l1 == 1)
				{
					npcIndices[npcCount++] = j1;
					npc.time = tick;
					int i2 = buffer.readBits(3);
					npc.moveInDir(false, i2);
					int k2 = buffer.readBits(1);
					if(k2 == 1)
						mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = j1;
				} else
				if(l1 == 2)
				{
					npcIndices[npcCount++] = j1;
					npc.time = tick;
					int j2 = buffer.readBits(3);
					npc.moveInDir(true, j2);
					int l2 = buffer.readBits(3);
					npc.moveInDir(true, l2);
					int i3 = buffer.readBits(1);
					if(i3 == 1)
						mobsAwaitingUpdate[mobsAwaitingUpdateCount++] = j1;
				} else
				if(l1 == 3)
					anIntArray840[anInt839++] = j1;
			}
		}

	}

	public boolean getMousePositions() {
		if (mouseInRegion(frameWidth - (frameWidth <= 1000 ? 240 : 420),
				frameWidth, frameHeight - (frameWidth <= 1000 ? 90 : 37), frameHeight)) {
			return false;
		}
		if (showChatComponents) {
			if (changeChatArea && frameMode != ScreenMode.FIXED) {
				if (chatStateCheck() && super.mouseX > 0 && super.mouseX < 494
						&& super.mouseY > frameHeight - 175
						&& super.mouseY < frameHeight) {
					return false;
				} else if (!chatStateCheck() && super.mouseX > 0 && super.mouseX < 494
						&& super.mouseY > frameHeight - 175
						&& super.mouseY < frameHeight) {
					return true;
				} else {
					if (super.mouseX > 494 && super.mouseX < 515
							&& super.mouseY > frameHeight - 175
							&& super.mouseY < frameHeight) {
						return false;
					}
				}
			} else if (!changeChatArea) {
				if (super.mouseX > 0 && super.mouseX < 519
						&& super.mouseY > frameHeight - 175
						&& super.mouseY < frameHeight) {
					return false;
				}
			}
		}
		if (mouseInRegion(frameWidth - 216, frameWidth, 0, 172)) {
			return false;
		}
		if (!stackSideStones) {
			if (super.mouseX > 0 && super.mouseY > 0 && super.mouseY < frameWidth
					&& super.mouseY < frameHeight) {
				if (super.mouseX >= frameWidth - 242 && super.mouseY >= frameHeight - 335) {
					return false;
				}
				return true;
			}
			return false;
		}
		if (showTabComponents) {
			if (frameWidth > 1000) {
				if (super.mouseX >= frameWidth - 420 && super.mouseX <= frameWidth
						&& super.mouseY >= frameHeight - 37
						&& super.mouseY <= frameHeight
						|| super.mouseX > frameWidth - 225 && super.mouseX < frameWidth
						&& super.mouseY > frameHeight - 37 - 274
						&& super.mouseY < frameHeight) {
					return false;
				}
			} else {
				if (super.mouseX >= frameWidth - 210 && super.mouseX <= frameWidth
						&& super.mouseY >= frameHeight - 74
						&& super.mouseY <= frameHeight
						|| super.mouseX > frameWidth - 225 && super.mouseX < frameWidth
						&& super.mouseY > frameHeight - 74 - 274
						&& super.mouseY < frameHeight) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean mouseInRegion(int x1, int x2, int y1, int y2) {
		if (super.mouseX >= x1 && super.mouseX <= x2 && super.mouseY >= y1 && super.mouseY <= y2) {
			return true;
		}
		return false;
	}

	public boolean mouseMapPosition() {
		if (super.mouseX >= frameWidth - 21 && super.mouseX <= frameWidth && super.mouseY >= 0
				&& super.mouseY <= 21) {
			return false;
		}
		return true;
	}

	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
		return java.lang.Math.pow((circleX + radius - clickX), 2)
				+ java.lang.Math.pow((circleY + radius - clickY), 2) < java.lang.Math
				.pow(radius, 2);
	}

	private void processLoginScreenInput() {
		if (loginScreenState == 0) {
			if (super.clickMode3 == 1 && mouseInRegion(728, 763, 456, 488)) {
				musicButton();
			}

			int i = super.myWidth / 2;
			int l = super.myHeight / 2;
			if (super.clickMode3 == 1) {
				if (mouseInRegion(394, 530, 275, 307)) {
					firstLoginMessage = "";
					secondLoginMessage = "Enter your username & password.";
					loginScreenState = 2;
					if (myUsername.length() == 0) {
						loginScreenCursorPos = 0;
					}
				} else if (mouseInRegion(229, 375, 271, 312)) {
					MiscUtils.launchURL("allstarlegends.eu/");
				}
			}
		} else if (loginScreenState == 2) {
			int j = super.myHeight / 2 - 45;
			j += 25;
			j += 25;
			if (super.clickMode3 == 1 && mouseInRegion(728, 763, 456, 488)) {
				musicButton();
			}

			if (super.clickMode3 == 1 && super.saveClickY >= 239 && super.saveClickY < 252)
				loginScreenCursorPos = 0;
			j += 15;
			if (super.clickMode3 == 1 && super.saveClickY >= 257 && super.saveClickY < 266)
				loginScreenCursorPos = 1;
			j += 15;
			int i1 = super.myWidth;
			int k1 = super.myHeight;
			k1 += 20;

			// login
			if (super.clickMode3 == 1 && mouseInRegion(235, 370, 305, 339)) {
				loginFailures = 0;
				login(myUsername, myPassword, false);
				if (loggedIn)
					return;
			}

			i1 = super.myWidth / 2 + 80;

			// cancel
			if (super.clickMode3 == 1 && mouseInRegion(394, 530, 305, 339)) {
				loginScreenState = 0;
			}
			do {
				int l1 = readChar(-796);
				if (l1 == -1)
					break;
				boolean flag1 = false;
				for (int i2 = 0; i2 < validUserPassChars.length(); i2++) {
					if (l1 != validUserPassChars.charAt(i2))
						continue;
					flag1 = true;
					break;
				}

				if (loginScreenCursorPos == 0) {
					if (l1 == 8 && myUsername.length() > 0)
						myUsername = myUsername.substring(0, myUsername.length() - 1);
					if (l1 == 9 || l1 == 10 || l1 == 13)
						loginScreenCursorPos = 1;
					if (flag1)
						myUsername += (char) l1;
					if (myUsername.length() > 12)
						myUsername = myUsername.substring(0, 12);
					if (myUsername.length() > 0) {
						myUsername = StringUtils.formatText(StringUtils.capitalize(myUsername));
					}
				} else if (loginScreenCursorPos == 1) {
					if (l1 == 8 && myPassword.length() > 0)
						myPassword = myPassword.substring(0, myPassword.length() - 1);
					if (l1 == 9) {
						loginScreenCursorPos = 0;
					} else if (l1 == 10 || l1 == 13) {
						login(myUsername, myPassword, false);
						return;
					}
					if (flag1)
						myPassword += (char) l1;
					if (myPassword.length() > 15)
						myPassword = myPassword.substring(0, 15);
				}
			} while (true);
			return;
		}
	}

	private void markMinimap(Sprite sprite, int x, int y) {
		if (sprite == null) {
			return;
		}
		int angle = cameraHorizontal + minimapRotation & 0x7ff;
		int l = x * x + y * y;
		if (l > 6400) {
			return;
		}
		int sineAngle = Model.SINE[angle];
		int cosineAngle = Model.COSINE[angle];
		sineAngle = (sineAngle * 256) / (minimapZoom + 256);
		cosineAngle = (cosineAngle * 256) / (minimapZoom + 256);
		int spriteOffsetX = y * sineAngle + x * cosineAngle >> 16;
		int spriteOffsetY = y * cosineAngle - x * sineAngle >> 16;
		if (frameMode == ScreenMode.FIXED) {
			sprite.drawSprite(((94 + spriteOffsetX) - sprite.maxWidth / 2) + 4 + 30,
					83 - spriteOffsetY - sprite.maxHeight / 2 - 4 + 5);
		} else {
			sprite.drawSprite(
					((77 + spriteOffsetX) - sprite.maxWidth / 2) + 4 + 5
							+ (frameWidth - 167),
					85 - spriteOffsetY - sprite.maxHeight / 2);
		}
	}

	public void removeObject(int i, int j, int k, int l, int i1, int j1, int k1
	)
	{
		if(i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102)
		{
			if(lowMem && j != plane)
				return;
			int i2 = 0;
			if(j1 == 0)
				i2 = scene.getWallObjectUid(j, i1, i);
			if(j1 == 1)
				i2 = scene.getWallDecorationUid(j, i1, i);
			if(j1 == 2)
				i2 = scene.getGameObjectUid(j, i1, i);
			if(j1 == 3)
				i2 = scene.getGroundDecorationUid(j, i1, i);
			if(i2 != 0)
			{
				int i3 = scene.getMask(j, i1, i, i2);
				int j2 = i2 >> 14 & 0x7fff;
				int k2 = i3 & 0x1f;
				int l2 = i3 >> 6;
				if(j1 == 0)
				{
					scene.removeWallObject(i1, j, i);
					ObjectDefinition class46 = ObjectDefinition.lookup(j2);
					if(class46.solid)
						collisionMaps[j].removeObject(l2, k2, class46.impenetrable, i1, i);
				}
				if(j1 == 1)
					scene.removeWallDecoration(i, j, i1);
				if(j1 == 2)
				{
					scene.removeTiledObject(j, i1, i);
					ObjectDefinition class46_1 = ObjectDefinition.lookup(j2);
					if(i1 + class46_1.objectSizeX > 103 || i + class46_1.objectSizeX > 103 || i1 + class46_1.objectSizeY > 103 || i + class46_1.objectSizeY > 103)
						return;
					if(class46_1.solid)
						collisionMaps[j].removeObject(l2, class46_1.objectSizeX, i1, i, class46_1.objectSizeY, class46_1.impenetrable);
				}
				if(j1 == 3)
				{
					scene.removeGroundDecoration(j, i, i1);
					ObjectDefinition class46_2 = ObjectDefinition.lookup(j2);
					if(class46_2.solid && class46_2.isInteractive)
						collisionMaps[j].removeFloorDecoration(i, i1);
				}
			}
			if(k1 >= 0)
			{
				int j3 = j;
				if(j3 < 3 && (tileFlags[1][i1][i] & 2) == 2)
					j3++;
				MapRegion.placeObject(scene, k, i, l, j3, collisionMaps[j], tileHeights, i1, k1, j);
			}
		}
	}

	private int size = 0;

	public void updatePlayers(int i, Buffer buffer)
	{
		anInt839 = 0;
		mobsAwaitingUpdateCount = 0;
		method117(buffer);
		method134(buffer);
		method91(buffer, i);
		method49(buffer);
		size = i;
		for(int k = 0; k < anInt839; k++)
		{
			int l = anIntArray840[k];
			if (players[l] != null) {
				if(players[l].time != tick)
					players[l] = null;
			}
		}

		if(buffer.currentPosition != i)
		{
			signlink.reporterror("Error packet size mismatch in getplayer pos:" + buffer.currentPosition + " psize:" + i);
			//item_data.currentPosition = i;
			throw new RuntimeException("eek");
		}
		for(int i1 = 0; i1 < playerCount; i1++)
			if(players[playerList[i1]] == null)
			{
				signlink.reporterror(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
				throw new RuntimeException("eek");
			}

	}

	public void setCameraPos(int j, int k, int l, int i1, int j1, int k1)
	{
		int l1 = 2048 - k & 0x7ff;
		int i2 = 2048 - j1 & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if(l1 != 0)
		{
			int i3 = Model.SINE[l1];
			int k3 = Model.COSINE[l1];
			int i4 = k2 * k3 - l2 * i3 >> 16;
			l2 = k2 * i3 + l2 * k3 >> 16;
			k2 = i4;
		}
		if(i2 != 0)
		{
/* xxx			if(cameratoggle){
				if(zoom == 0)
				zoom = k2;
			  if(lftrit == 0)
				lftrit = j2;
			  if(fwdbwd == 0)
				fwdbwd = l2;
			  k2 = zoom;
			  j2 = lftrit;
			  l2 = fwdbwd;
			}
*/
			int j3 = Model.SINE[i2];
			int l3 = Model.COSINE[i2];
			int j4 = l2 * j3 + j2 * l3 >> 16;
			l2 = l2 * l3 - j2 * j3 >> 16;
			j2 = j4;
		}
		xCameraPos = l - j2;
		zCameraPos = i1 - k2;
		yCameraPos = k1 - l2;
		yCameraCurve = k;
		xCameraCurve = j1;
	}

	public void updateStrings(String str, int i) {
		switch(i) {
			case 1675: sendString(str, 17508); break;//Stab
			case 1676: sendString(str, 17509); break;//Slash
			case 1677: sendString(str, 17510); break;//Cursh
			case 1678: sendString(str, 17511); break;//Magic
			case 1679: sendString(str, 17512); break;//Range
			case 1680: sendString(str, 17513); break;//Stab
			case 1681: sendString(str, 17514); break;//Slash
			case 1682: sendString(str, 17515); break;//Crush
			case 1683: sendString(str, 17516); break;//Magic
			case 1684: sendString(str, 17517); break;//Range
			case 1686: sendString(str, 17518); break;//Strength
			case 1687: sendString(str, 17519); break;//Prayer
		}
	}

	public void sendString(String text, int index) {
		if (Widget.interfaceCache[index] == null) {
			return;
		}
		Widget.interfaceCache[index].defaultText = text;
		if (Widget.interfaceCache[index].parent == tabInterfaceIDs[tabId]) {
		}
	}

	public void sendPacket185(int button,int toggle,int type) {
		switch(type) {
			case 135:
				Widget class9 = Widget.interfaceCache[button];
				boolean flag8 = true;
				if(class9.contentType > 0)
					flag8 = promptUserForInput(class9);
				if(flag8) {
					buffer.createFrame(185);
					buffer.writeWord(button);
				}
				break;
			case 646:
				buffer.createFrame(185);
				buffer.writeWord(button);
				Widget class9_2 = Widget.interfaceCache[button];
				if(class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
					if(variousSettings[toggle] != class9_2.requiredValues[0]) {
						variousSettings[toggle] = class9_2.requiredValues[0];
						method33(toggle);
					}
				}
				break;
			case 169:
				buffer.createFrame(185);
				buffer.writeWord(button);
				Widget class9_3 = Widget.interfaceCache[button];
				if(class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
					variousSettings[toggle] = 1 - variousSettings[toggle];
					method33(toggle);
				}
				switch(button) {
					case 19136:
						System.out.println("toggle = "+toggle);
						if(toggle == 0)
							sendFrame36(173,toggle);
						if(toggle == 1)
							sendPacket185(153,173,646);
						break;
				}
				break;
		}
	}

	public void sendFrame36(int id,int state) {
		anIntArray1045[id] = state;
		if(variousSettings[id] != state) {
			variousSettings[id] = state;
			method33(id);
			if(dialogueId != -1)
				updateChatbox = true;
		}
	}

	public void sendFrame219() {
		if(overlayInterfaceId != -1) {
			overlayInterfaceId = -1;
			tabAreaAltered = true;
		}
		if(backDialogueId != -1) {
			backDialogueId = -1;
			updateChatbox = true;
		}
		if(inputDialogState != 0) {
			inputDialogState = 0;
			updateChatbox = true;
		}
		openInterfaceId = -1;
		continuedDialogue = false;
	}

	public void sendFrame248(int interfaceID,int sideInterfaceID) 	{
		if(backDialogueId != -1) {
			backDialogueId = -1;
			updateChatbox = true;
		}
		if(inputDialogState != 0) {
			inputDialogState = 0;
			updateChatbox = true;
		}
		openInterfaceId = interfaceID;
		overlayInterfaceId = sideInterfaceID;
		tabAreaAltered = true;
		continuedDialogue = false;
	}

	private static final Object[][] SKILL_DATA = new Object[][] {
		new Object[] { "Attack",		50023 },
		new Object[] { "Defence",		50065 },
		new Object[] { "Strength",		50044 },
		new Object[] { "Hitpoints",		50030 },
		new Object[] { "Range",			50087 },
		new Object[] { "Prayer",		50108 },
		new Object[] { "Magic",			50129 },
		new Object[] { "Cooking",		50101 },
		new Object[] { "Woodcutting", 	50143 },
		new Object[] { "Fletching",		50136 },
		new Object[] { "Fishing",		50080 },
		new Object[] { "Firemaking",	50122 },
		new Object[] { "Crafting",		50115 },
		new Object[] { "Smithing",		50058 },
		new Object[] { "Mining",		50037 },
		new Object[] { "Herblore",		50072 },
		new Object[] { "Agility",		50051 },
		new Object[] { "Theiving",		50094 },
		new Object[] { "Slayer",		50157 },
		new Object[] { "Farming",		50164 },
		new Object[] { "Runecrafting",	50150 },
		new Object[] { "Construction",	50215 },
		new Object[] { "Hunter",		50225 },
		new Object[] { "Summoning",		50235 },
	};

	private boolean parsePacket() {
		if(socketStream == null)
			return false;
		try {
			int i = socketStream.available();
			if(i == 0)
				return false;
			if(opcode == -1) {
				socketStream.flushInputStream(inBuffer.payload, 1);
				opcode = inBuffer.payload[0] & 0xff;
				if(encryption != null)
					opcode = opcode - encryption.getNextKey() & 0xff;
				packetSize = SizeConstants.packetSizes[opcode];
				i--;
			}
			if(packetSize == -1)
				if(i > 0) {
					socketStream.flushInputStream(inBuffer.payload, 1);
					packetSize = inBuffer.payload[0] & 0xff;
					i--;
				} else {
					return false;
				}
			if(packetSize == -2)
				if(i > 1) {
					socketStream.flushInputStream(inBuffer.payload, 2);
					inBuffer.currentPosition = 0;
					packetSize = inBuffer.readUShort();
					i -= 2;
				} else {
					return false;
				}
			if(i < packetSize)
				return false;
			inBuffer.currentPosition = 0;
			socketStream.flushInputStream(inBuffer.payload, packetSize);
			anInt1009 = 0;
			thirdLastOpcode = secondLastOpcode;
			secondLastOpcode = anInt841;
			anInt841 = opcode;
			switch(opcode) {
				case 81:
					try {
						updatePlayers(packetSize, inBuffer);
					} catch (Exception e) {
						e.printStackTrace();
					}
					aBoolean1080 = false;
					opcode = -1;
					return true;

				case 176:
					daysSinceRecovChange = inBuffer.method427();
					unreadMessages = inBuffer.method435();
					membersInt = inBuffer.readUnsignedByte();
					anInt1193 = inBuffer.method440();
					daysSinceLastLogin = inBuffer.readUShort();
					if(anInt1193 != 0 && openInterfaceId == -1) {
						signlink.dnslookup(TextClass.method586(anInt1193));
						clearTopInterfaces();
						char c = '\u028A';
						if(daysSinceRecovChange != 201 || membersInt == 1)
							c = '\u028F';
						reportAbuseInput = "";
						canMute = false;
						for(int k9 = 0; k9 < Widget.interfaceCache.length; k9++) {
							if(Widget.interfaceCache[k9] == null || Widget.interfaceCache[k9].contentType != c)
								continue;
							openInterfaceId = Widget.interfaceCache[k9].parent;

						}
					}
					opcode = -1;
					return true;

				case 64:
					localX = inBuffer.method427();
					localY = inBuffer.method428();
					for(int j = localX; j < localX + 8; j++) {
						for(int l9 = localY; l9 < localY + 8; l9++)
							if(groundItems[plane][j][l9] != null) {
								groundItems[plane][j][l9] = null;
								spawnGroundItem(j, l9);
							}
					}
					for(SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext())
						if(spawnedObject.x >= localX && spawnedObject.x < localX + 8 && spawnedObject.y >= localY && spawnedObject.y < localY + 8 && spawnedObject.plane == plane)
							spawnedObject.getLongetivity = 0;
					opcode = -1;
					return true;

				case 185:
					int k = inBuffer.method436();
					Widget.interfaceCache[k].defaultMediaType = 3;
					if(localPlayer.desc == null)
						Widget.interfaceCache[k].mediaID = (localPlayer.anIntArray1700[0] << 25) + (localPlayer.anIntArray1700[4] << 20) + (localPlayer.equipment[0] << 15) + (localPlayer.equipment[8] << 10) + (localPlayer.equipment[11] << 5) + localPlayer.equipment[1];
					else
						Widget.interfaceCache[k].mediaID = (int)(0x12345678L + localPlayer.desc.interfaceType);
					opcode = -1;
					return true;

				/* Clan defaultText packet */
				case 217:
					try {
						name = inBuffer.readString();
						message = inBuffer.readString();
						clanname = inBuffer.readString();
						rights = inBuffer.readUShort();
						//defaultText = TextInput.processText(defaultText);
						//System.out.println(clanname);
						pushMessage(message, 16, name);
					} catch(Exception e) {
						e.printStackTrace();
					}
					opcode = -1;
					return true;

				case 107:
					inCutScene = false;
					for(int l = 0; l < 5; l++)
						aBooleanArray876[l] = false;
					opcode = -1;
					return true;

				case 72:
					int i1 = inBuffer.method434();
					Widget class9 = Widget.interfaceCache[i1];
					for(int k15 = 0; k15 < class9.inventoryItemId.length; k15++) {
						class9.inventoryItemId[k15] = -1;
						class9.inventoryItemId[k15] = 0;
					}
					opcode = -1;
					return true;

				case 214:
					ignoreCount = packetSize / 8;
					for(int j1 = 0; j1 < ignoreCount; j1++)
						ignoreListAsLongs[j1] = inBuffer.readQWord();
					opcode = -1;
					return true;

				case 166:
					inCutScene = true;
					anInt1098 = inBuffer.readUnsignedByte();
					anInt1099 = inBuffer.readUnsignedByte();
					anInt1100 = inBuffer.readUShort();
					anInt1101 = inBuffer.readUnsignedByte();
					anInt1102 = inBuffer.readUnsignedByte();
					if(anInt1102 >= 100) {
						xCameraPos = anInt1098 * 128 + 64;
						yCameraPos = anInt1099 * 128 + 64;
						zCameraPos = getCenterHeight(plane, yCameraPos, xCameraPos) - anInt1100;
					}
					opcode = -1;
					return true;

				case 134:
					int skill = inBuffer.readUnsignedByte();
					int experience = inBuffer.method439();
					int level = inBuffer.readUnsignedByte();
					currentExp[skill] = experience;
					currentLevels[skill] = level;
					maximumLevels[skill] = 1;
					for(int k20 = 0; k20 < 98; k20++) {
						if(experience >= SKILL_EXPERIENCE[k20]) {
							maximumLevels[skill] = k20 + 2;
						}
					}
					opcode = -1;
					return true;

				case 71://both
					int l1 = inBuffer.readUShort();
					int j10 = inBuffer.method426();
					if(l1 == 65535)
						l1 = -1;
					tabInterfaceIDs[j10] = l1;
					tabAreaAltered = true;
					opcode = -1;
					return true;

				case 74:
					int i2 = inBuffer.method434();
					if(i2 == 65535)
						i2 = -1;
					if(i2 != currentSong && musicEnabled && !lowMem && prevSong == 0) {
						nextSong = i2;
						songChanging = true;
						resourceProvider.provide(2, nextSong);
					}
					currentSong = i2;
					opcode = -1;
					return true;


				case 121:
					int j2 = inBuffer.method436();
					int k10 = inBuffer.method435();
					if(musicEnabled && !lowMem) {
						nextSong = j2;
						songChanging = false;
						resourceProvider.provide(2, nextSong);
						prevSong = k10;
					}
					opcode = -1;
					return true;

				case 109:
					resetLogout();
					opcode = -1;
					return false;

				case 70:
					int k2 = inBuffer.readSignedWord();
					int l10 = inBuffer.method437();
					int i16 = inBuffer.method434();
					Widget class9_5 = Widget.interfaceCache[i16];
					class9_5.horizontalOffset = k2;
					class9_5.verticalOffset = l10;
					opcode = -1;
					return true;

				case 73:
				case 241:
					int regionX = currentRegionX;
					int regionY = currentRegionY;
					if(opcode == 73) {
						regionX = inBuffer.method435();
						regionY = inBuffer.readUShort();
						aBoolean1159 = false;
					}
					if(opcode == 241) {
						regionY = inBuffer.method435();
						inBuffer.initBitAccess();
						for(int j16 = 0; j16 < 4; j16++) {
							for(int l20 = 0; l20 < 13; l20++) {
								for(int j23 = 0; j23 < 13; j23++) {
									int i26 = inBuffer.readBits(1);
									if(i26 == 1)
										constructRegionData[j16][l20][j23] = inBuffer.readBits(26);
									else
										constructRegionData[j16][l20][j23] = -1;
								}
							}
						}
						inBuffer.finishBitAccess();
						regionX = inBuffer.readUShort();
						aBoolean1159 = true;
					}
					if(currentRegionX == regionX && currentRegionY == regionY && loadingStage == 2) {
						opcode = -1;
						return true;
					}
					currentRegionX = regionX;
					currentRegionY = regionY;
					regionBaseX = (currentRegionX - 6) * 8;
					regionBaseY = (currentRegionY - 6) * 8;
					aBoolean1141 = (currentRegionX / 8 == 48 || currentRegionX / 8 == 49) && currentRegionY / 8 == 48;
					if(currentRegionX / 8 == 48 && currentRegionY / 8 == 148)
						aBoolean1141 = true;
					loadingStage = 1;
					aLong824 = System.currentTimeMillis();
					gameScreenImageProducer.initDrawingArea();
					Rasterizer2D.fillPixels(2, 130, 22, 0xffffff, 2);
					Rasterizer2D.drawPixels(20, 3, 3, 0, 128);
					regularText.drawText(0, "Loading - Please Wait", 18, 68);
					regularText.drawText(0xffffff, "Loading - Please Wait", 17, 67);
					gameScreenImageProducer.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics,
							frameMode == ScreenMode.FIXED ? 4 : 0);
					if(opcode == 73) {
						int k16 = 0;
						for(int i21 = (currentRegionX - 6) / 8; i21 <= (currentRegionX + 6) / 8; i21++) {
							for(int k23 = (currentRegionY - 6) / 8; k23 <= (currentRegionY + 6) / 8; k23++)
								k16++;
						}
						aByteArrayArray1183 = new byte[k16][];
						aByteArrayArray1247 = new byte[k16][];
						anIntArray1234 = new int[k16];
						anIntArray1235 = new int[k16];
						anIntArray1236 = new int[k16];
						k16 = 0;
						for(int l23 = (currentRegionX - 6) / 8; l23 <= (currentRegionX + 6) / 8; l23++) {
							for(int j26 = (currentRegionY - 6) / 8; j26 <= (currentRegionY + 6) / 8; j26++) {
								anIntArray1234[k16] = (l23 << 8) + j26;
								if(aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
									anIntArray1235[k16] = -1;
									anIntArray1236[k16] = -1;
									k16++;
								} else {
									int k28 = anIntArray1235[k16] = resourceProvider.method562(0, j26, l23);
									if(k28 != -1)
										resourceProvider.provide(3, k28);
									int j30 = anIntArray1236[k16] = resourceProvider.method562(1, j26, l23);
									if(j30 != -1)
										resourceProvider.provide(3, j30);
									k16++;
								}
							}
						}
					}
					if(opcode == 241) {
						int l16 = 0;
						int ai[] = new int[676];
						for(int i24 = 0; i24 < 4; i24++) {
							for(int k26 = 0; k26 < 13; k26++) {
								for(int l28 = 0; l28 < 13; l28++) {
									int k30 = constructRegionData[i24][k26][l28];
									if(k30 != -1) {
										int k31 = k30 >> 14 & 0x3ff;
										int i32 = k30 >> 3 & 0x7ff;
										int k32 = (k31 / 8 << 8) + i32 / 8;
										for(int j33 = 0; j33 < l16; j33++) {
											if(ai[j33] != k32)
												continue;
											k32 = -1;

										}
										if(k32 != -1)
											ai[l16++] = k32;
									}
								}
							}
						}
						aByteArrayArray1183 = new byte[l16][];
						aByteArrayArray1247 = new byte[l16][];
						anIntArray1234 = new int[l16];
						anIntArray1235 = new int[l16];
						anIntArray1236 = new int[l16];
						for(int l26 = 0; l26 < l16; l26++) {
							int i29 = anIntArray1234[l26] = ai[l26];
							int l30 = i29 >> 8 & 0xff;
							int l31 = i29 & 0xff;
							int j32 = anIntArray1235[l26] = resourceProvider.method562(0, l31, l30);
							if(j32 != -1)
								resourceProvider.provide(3, j32);
							int i33 = anIntArray1236[l26] = resourceProvider.method562(1, l31, l30);
							if(i33 != -1)
								resourceProvider.provide(3, i33);
						}
					}
					int i17 = regionBaseX - anInt1036;
					int j21 = regionBaseY - anInt1037;
					anInt1036 = regionBaseX;
					anInt1037 = regionBaseY;
					for(int j24 = 0; j24 < 16384; j24++) {
						Npc npc = npcs[j24];
						if(npc != null) {
							for(int j29 = 0; j29 < 10; j29++) {
								npc.pathX[j29] -= i17;
								npc.pathY[j29] -= j21;
							}
							npc.x -= i17 * 128;
							npc.y -= j21 * 128;
						}
					}
					for(int i27 = 0; i27 < maxPlayers; i27++) {
						Player player = players[i27];
						if(player != null) {
							for(int i31 = 0; i31 < 10; i31++) {
								player.pathX[i31] -= i17;
								player.pathY[i31] -= j21;
							}
							player.x -= i17 * 128;
							player.y -= j21 * 128;
						}
					}
					aBoolean1080 = true;
					byte byte1 = 0;
					byte byte2 = 104;
					byte byte3 = 1;
					if(i17 < 0) {
						byte1 = 103;
						byte2 = -1;
						byte3 = -1;
					}
					byte byte4 = 0;
					byte byte5 = 104;
					byte byte6 = 1;
					if(j21 < 0) {
						byte4 = 103;
						byte5 = -1;
						byte6 = -1;
					}
					for(int k33 = byte1; k33 != byte2; k33 += byte3) {
						for(int l33 = byte4; l33 != byte5; l33 += byte6) {
							int i34 = k33 + i17;
							int j34 = l33 + j21;
							for(int k34 = 0; k34 < 4; k34++)
								if(i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104)
									groundItems[k34][k33][l33] = groundItems[k34][i34][j34];
								else
									groundItems[k34][k33][l33] = null;
						}
					}
					for(SpawnedObject spawnedObject = (SpawnedObject) spawns.reverseGetFirst(); spawnedObject != null; spawnedObject = (SpawnedObject) spawns.reverseGetNext()) {
						spawnedObject.x -= i17;
						spawnedObject.y -= j21;
						if(spawnedObject.x < 0 || spawnedObject.y < 0 || spawnedObject.x >= 104 || spawnedObject.y >= 104)
							spawnedObject.unlink();
					}
					if(destinationX != 0) {
						destinationX -= i17;
						destinationY -= j21;
					}
					inCutScene = false;
					opcode = -1;
					return true;

				case 208:
					int i3 = inBuffer.method437();
					if(i3 >= 0)
						method60(i3);
					openWalkableInterface = i3;
					opcode = -1;
					return true;

				case 99:
					minimapState = inBuffer.readUnsignedByte();
					opcode = -1;
					return true;

				case 75:
					int j3 = inBuffer.method436();
					int j11 = inBuffer.method436();
					Widget.interfaceCache[j11].defaultMediaType = 2;
					Widget.interfaceCache[j11].mediaID = j3;
					opcode = -1;
					return true;

				case 114:
					systemUpdateTime = inBuffer.method434() * 30;
					opcode = -1;
					return true;

				case 60:
					localY = inBuffer.readUnsignedByte();
					localX = inBuffer.method427();
					while(inBuffer.currentPosition < packetSize) {
						int k3 = inBuffer.readUnsignedByte();
						method137(inBuffer, k3);
					}
					opcode = -1;
					return true;

				case 35:
					int l3 = inBuffer.readUnsignedByte();
					int k11 = inBuffer.readUnsignedByte();
					int j17 = inBuffer.readUnsignedByte();
					int k21 = inBuffer.readUnsignedByte();
					aBooleanArray876[l3] = true;
					anIntArray873[l3] = k11;
					anIntArray1203[l3] = j17;
					anIntArray928[l3] = k21;
					anIntArray1030[l3] = 0;
					opcode = -1;
					return true;

				case 174:
					int soundId = inBuffer.readShort();
					int type = inBuffer.readUByte();
					int delay = inBuffer.readShort();
					int volume = inBuffer.readShort();
					anIntArray1207[trackCount] = soundId;
					trackLoops[trackCount] = type;
					anIntArray1250[trackCount] = delay + Sounds.anIntArray326[soundId];
					soundVolume[trackCount] = volume;
					trackCount++;
					opcode = -1;
                return true;

				case 104:
					int j4 = inBuffer.method427();
					int i12 = inBuffer.method426();
					String s6 = inBuffer.readString();
					if(j4 >= 1 && j4 <= 5) {
						if(s6.equalsIgnoreCase("null"))
							s6 = null;
						playerOptions[j4 - 1] = s6;
						playerOptionsHighPriority[j4 - 1] = i12 == 0;
					}
					opcode = -1;
					return true;

				case 78:
					destinationX = 0;
					opcode = -1;
					return true;

				case 253:
					String s = inBuffer.readString();
					if(s.endsWith(":tradereq:")) {
						String s3 = s.substring(0, s.indexOf(":"));
						long l17 = TextClass.longForName(s3);
						boolean flag2 = false;
						for(int j27 = 0; j27 < ignoreCount; j27++) {
							if(ignoreListAsLongs[j27] != l17)
								continue;
							flag2 = true;

						}
						 if(consoleOpen) {
							printConsoleMessage(s, 1);
						} else if(!flag2 && anInt1251 == 0)
							pushMessage("wishes to trade with you.", 4, s3);
					} else if (s.endsWith(":clan:")) {
						String s4 = s.substring(0, s.indexOf(":"));
						long l18 = TextClass.longForName(s4);
						pushMessage("Clan: ", 8, s4);
					} else if(s.endsWith("#url#")) {
						String link = s.substring(0, s.indexOf("#"));
						pushMessage("Join us at: ", 9, link);
					} else if(s.endsWith(":duelreq:")) {
						String s4 = s.substring(0, s.indexOf(":"));
						long l18 = TextClass.longForName(s4);
						boolean flag3 = false;
						for(int k27 = 0; k27 < ignoreCount; k27++) {
							if(ignoreListAsLongs[k27] != l18)
								continue;
							flag3 = true;

						}
						if(!flag3 && anInt1251 == 0)
							pushMessage("wishes to duel with you.", 8, s4);
					} else if(s.endsWith(":chalreq:")) {
						String s5 = s.substring(0, s.indexOf(":"));
						long l19 = TextClass.longForName(s5);
						boolean flag4 = false;
						for(int l27 = 0; l27 < ignoreCount; l27++) {
							if(ignoreListAsLongs[l27] != l19)
								continue;
							flag4 = true;

						}
						if(!flag4 && anInt1251 == 0) {
							String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
							pushMessage(s8, 8, s5);
						}
					} else {
						pushMessage(s, 0, "");
					}
					opcode = -1;
					return true;

				case 1:
					for(int k4 = 0; k4 < players.length; k4++)
						if(players[k4] != null)
							players[k4].emoteAnimation = -1;
					for(int j12 = 0; j12 < npcs.length; j12++)
						if(npcs[j12] != null)
							npcs[j12].emoteAnimation = -1;
					opcode = -1;
					return true;

				case 50:
					long l4 = inBuffer.readQWord();
					int i18 = inBuffer.readUnsignedByte();
					String s7 = TextClass.fixName(TextClass.nameForLong(l4));
					for(int k24 = 0; k24 < friendsCount; k24++) {
						if(l4 != friendsListAsLongs[k24])
							continue;
						if(friendsNodeIDs[k24] != i18) {
							friendsNodeIDs[k24] = i18;
							if(i18 >= 2) {
								pushMessage(s7 + " has logged in.", 5, "");
							}
							if(i18 <= 1) {
								pushMessage(s7 + " has logged out.", 5, "");
							}
						}
						s7 = null;

					}
					if(s7 != null && friendsCount < 200) {
						friendsListAsLongs[friendsCount] = l4;
						friendsList[friendsCount] = s7;
						friendsNodeIDs[friendsCount] = i18;
						friendsCount++;
					}
					for(boolean flag6 = false; !flag6;) {
						flag6 = true;
						for(int k29 = 0; k29 < friendsCount - 1; k29++)
							if(friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID || friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
								int j31 = friendsNodeIDs[k29];
								friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
								friendsNodeIDs[k29 + 1] = j31;
								String s10 = friendsList[k29];
								friendsList[k29] = friendsList[k29 + 1];
								friendsList[k29 + 1] = s10;
								long l32 = friendsListAsLongs[k29];
								friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
								friendsListAsLongs[k29 + 1] = l32;
								flag6 = false;
							}
					}
					opcode = -1;
					return true;

				case 110:
					runEnergy = inBuffer.readUnsignedByte();
					opcode = -1;
					return true;

				case 254:
					hintIconDrawType = inBuffer.readUnsignedByte();
					if(hintIconDrawType == 1)
						hintIconNpcId = inBuffer.readUShort();
					if(hintIconDrawType >= 2 && hintIconDrawType <= 6) {
						if(hintIconDrawType == 2) {
							anInt937 = 64;
							anInt938 = 64;
						}
						if(hintIconDrawType == 3) {
							anInt937 = 0;
							anInt938 = 64;
						}
						if(hintIconDrawType == 4) {
							anInt937 = 128;
							anInt938 = 64;
						}
						if(hintIconDrawType == 5) {
							anInt937 = 64;
							anInt938 = 0;
						}
						if(hintIconDrawType == 6) {
							anInt937 = 64;
							anInt938 = 128;
						}
						hintIconDrawType = 2;
						hintIconX = inBuffer.readUShort();
						hintIconY = inBuffer.readUShort();
						anInt936 = inBuffer.readUnsignedByte();
					}
					if(hintIconDrawType == 10)
						hintIconPlayerId = inBuffer.readUShort();
					opcode = -1;
					return true;

				case 248:
					int i5 = inBuffer.method435();
					int k12 = inBuffer.readUShort();
					if(backDialogueId != -1) {
						backDialogueId = -1;
						updateChatbox = true;
					}
					if(inputDialogState != 0) {
						inputDialogState = 0;
						updateChatbox = true;
					}
					openInterfaceId = i5;
					overlayInterfaceId = k12;
					tabAreaAltered = true;
					continuedDialogue = false;
					opcode = -1;
					return true;

				case 79:
					int j5 = inBuffer.method434();
					int l12 = inBuffer.method435();
					Widget class9_3 = Widget.interfaceCache[j5];
					if(class9_3 != null && class9_3.type == 0) {
						if(l12 < 0)
							l12 = 0;
						if(l12 > class9_3.scrollMax - class9_3.height)
							l12 = class9_3.scrollMax - class9_3.height;
						class9_3.scrollPosition = l12;
					}
					opcode = -1;
					return true;

				case 68:
					for(int k5 = 0; k5 < variousSettings.length; k5++)
						if(variousSettings[k5] != anIntArray1045[k5]) {
							variousSettings[k5] = anIntArray1045[k5];
							method33(k5);
						}
					opcode = -1;
					return true;

				case 196:
					long l5 = inBuffer.readQWord();
					int j18 = inBuffer.readInt();
					int l21 = inBuffer.readUnsignedByte();
					boolean flag5 = false;
					if(l21 <= 1) {
						for(int l29 = 0; l29 < ignoreCount; l29++) {
							if(ignoreListAsLongs[l29] != l5)
								continue;
							flag5 = true;

						}
					}
					if(!flag5 && anInt1251 == 0)
						try {
							String s9 = TextInput.method525(packetSize - 13, inBuffer);
							//if(l21 != 3)
							if(l21 == 2)
								pushMessage(s9, 7, "@cr2@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 1)
								pushMessage(s9, 7, "@cr1@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 3)
								pushMessage(s9, 7, "@cr3@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 4)
								pushMessage(s9, 7, "@cr4@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 5)
								pushMessage(s9, 7, "@cr5@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 6)
								pushMessage(s9, 7, "@cr6@ " + TextClass.fixName(TextClass.nameForLong(l5)));
							else
							if(l21 == 7)
								pushMessage(s9, 7, "@cr7@" + TextClass.fixName(TextClass.nameForLong(l5)));
							else
								pushMessage(s9, 3, TextClass.fixName(TextClass.nameForLong(l5)));
						} catch(Exception exception1) {
							signlink.reporterror("cde1");
						}
					opcode = -1;
					return true;

				case 85:
					localY = inBuffer.method427();
					localX = inBuffer.method427();
					opcode = -1;
					return true;

				case 24:
					flashingSidebarId = inBuffer.method428();
					if(flashingSidebarId == tabId) {
						if(flashingSidebarId == 3)
							tabId = 1;
						else
							tabId = 3;
					}
					opcode = -1;
					return true;

				case 246:
					int i6 = inBuffer.method434();
					int i13 = inBuffer.readUShort();
					int k18 = inBuffer.readUShort();
					if(k18 == 65535) {
						Widget.interfaceCache[i6].defaultMediaType = 0;
						opcode = -1;
						return true;
					} else {
						ItemDefinition itemDefinition = ItemDefinition.lookup(k18);
						Widget.interfaceCache[i6].defaultMediaType = 4;
						Widget.interfaceCache[i6].mediaID = k18;
						Widget.interfaceCache[i6].modelRotation1 = itemDefinition.rotation_y;
						Widget.interfaceCache[i6].modelRotation2 = itemDefinition.rotation_x;
						Widget.interfaceCache[i6].modelZoom = (itemDefinition.modelZoom * 100) / i13;
						opcode = -1;
						return true;
					}

				case 171:
					boolean flag1 = inBuffer.readUnsignedByte() == 1;
					int j13 = inBuffer.readUShort();
					Widget.interfaceCache[j13].invisible = flag1;
					opcode = -1;
					return true;

				case 142:
					int j6 = inBuffer.method434();
					method60(j6);
					if(backDialogueId != -1) {
						backDialogueId = -1;
						updateChatbox = true;
					}
					if(inputDialogState != 0) {
						inputDialogState = 0;
						updateChatbox = true;
					}
					overlayInterfaceId = j6;
					tabAreaAltered = true;
					openInterfaceId = -1;
					continuedDialogue = false;
					opcode = -1;
					return true;

				case 126:
					try {
						String text = inBuffer.readString();
						int frame = inBuffer.method435();
						if (text.startsWith("www.") || text.startsWith("http")) {
							launchURL(text);
							opcode = -1;
							return true;
						}
						if (text.startsWith(":quicks:")) {
							clickedQuickPrayers = text.substring(8).equalsIgnoreCase("on");
						}
						if (text.startsWith(":prayer:")) {
							prayerBook = text.substring(8);
						}
						updateStrings(text, frame);
						sendString(text, frame);
						if (frame >= 18144 && frame <= 18244) {
							clanList[frame - 18144] = text;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					opcode = -1;
					return true;


				case 206:
					publicChatMode = inBuffer.readUnsignedByte();
					privateChatMode = inBuffer.readUnsignedByte();
					tradeMode = inBuffer.readUnsignedByte();
					aBoolean1233 = true;
					updateChatbox = true;
					opcode = -1;
					return true;

				case 240:
					if(tabId == 12) {
					}
					weight = inBuffer.readSignedWord();
					opcode = -1;
					return true;

				case 8:
					int k6 = inBuffer.method436();
					int l13 = inBuffer.readUShort();
					Widget.interfaceCache[k6].defaultMediaType = 1;
					Widget.interfaceCache[k6].mediaID = l13;
					opcode = -1;
					return true;

				case 122:
					int l6 = inBuffer.method436();
					int i14 = inBuffer.method436();
					int i19 = i14 >> 10 & 0x1f;
					int i22 = i14 >> 5 & 0x1f;
					int l24 = i14 & 0x1f;
					Widget.interfaceCache[l6].textColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
					opcode = -1;
					return true;

				case 53:
					int i7 = inBuffer.readUShort();
					Widget class9_1 = Widget.interfaceCache[i7];
					int j19 = inBuffer.readUShort();
					for(int j22 = 0; j22 < j19; j22++) {
						int i25 = inBuffer.readUnsignedByte();
						if(i25 == 255)
							i25 = inBuffer.method440();
						class9_1.inventoryItemId[j22] = inBuffer.method436();
						class9_1.inventoryAmounts[j22] = i25;
					}
					for(int j25 = j19; j25 < class9_1.inventoryItemId.length; j25++) {
						class9_1.inventoryItemId[j25] = 0;
						class9_1.inventoryAmounts[j25] = 0;
					}
					opcode = -1;
					return true;

				case 230:
					int j7 = inBuffer.method435();
					int j14 = inBuffer.readUShort();
					int k19 = inBuffer.readUShort();
					int k22 = inBuffer.method436();
					Widget.interfaceCache[j14].modelRotation1 = k19;
					Widget.interfaceCache[j14].modelRotation2 = k22;
					Widget.interfaceCache[j14].modelZoom = j7;
					opcode = -1;
					return true;

				case 221:
					anInt900 = inBuffer.readUnsignedByte();
					opcode = -1;
					return true;

				case 177:
					inCutScene = true;
					anInt995 = inBuffer.readUnsignedByte();
					anInt996 = inBuffer.readUnsignedByte();
					anInt997 = inBuffer.readUShort();
					anInt998 = inBuffer.readUnsignedByte();
					anInt999 = inBuffer.readUnsignedByte();
					if(anInt999 >= 100) {
						int k7 = anInt995 * 128 + 64;
						int k14 = anInt996 * 128 + 64;
						int i20 = getCenterHeight(plane, k14, k7) - anInt997;
						int l22 = k7 - xCameraPos;
						int k25 = i20 - zCameraPos;
						int j28 = k14 - yCameraPos;
						int i30 = (int)Math.sqrt(l22 * l22 + j28 * j28);
						yCameraCurve = (int)(Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
						xCameraCurve = (int)(Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
						if(yCameraCurve < 128)
							yCameraCurve = 128;
						if(yCameraCurve > 383)
							yCameraCurve = 383;
					}
					opcode = -1;
					return true;

				case 249:
					anInt1046 = inBuffer.method426();
					localPlayerIndex = inBuffer.method436();
					opcode = -1;
					return true;

				case 65:
					updateNPCs(inBuffer, packetSize);
					opcode = -1;
					return true;

				case 27:
					messagePromptRaised = false;
					inputDialogState = 1;
					amountOrNameInput = "";
					updateChatbox = true;
					opcode = -1;
					return true;

				case 187:
					messagePromptRaised = false;
					inputDialogState = 2;
					amountOrNameInput = "";
					updateChatbox = true;
					opcode = -1;
					return true;

				case 97:
					int l7 = inBuffer.readUShort();
					method60(l7);
					if (overlayInterfaceId != -1) {
						overlayInterfaceId = -1;
						tabAreaAltered = true;
					}
					if (backDialogueId != -1) {
						backDialogueId = -1;
						updateChatbox = true;
					}
					if (inputDialogState != 0) {
						inputDialogState = 0;
						updateChatbox = true;
					}
					//17511 = Question Type
					//15819 = Christmas Type
					//15812 = Security Type
					//15801 = Item Scam Type
					//15791 = Password Safety ?
					//15774 = Good/Bad Password
					//15767 = Drama Type ????
					if (l7 == 15244) {
						openInterfaceId = 15812;
						fullscreenInterfaceID = 15244;
					} else {
						openInterfaceId = l7;
					}
					continuedDialogue = false;
					opcode = -1;
					return true;

				case 218:
					int i8 = inBuffer.method438();
					dialogueId = i8;
					updateChatbox = true;
					opcode = -1;
					return true;

			case 86:
			        followPlayer = 0;
			        followNPC = 0;
			int l11z = inBuffer.readUShort();
			int iq = inBuffer.readUnsignedByte();
			        followDistance = inBuffer.readUShort();
			if (iq == 0) {
			        followNPC = l11z;
			}
			    else if (iq == 1)
			    {
			         followPlayer = l11z;
			}
			opcode = -1;
			return true;

				case 87:
					int j8 = inBuffer.method434();
					int l14 = inBuffer.method439();
					anIntArray1045[j8] = l14;
					if(variousSettings[j8] != l14) {
						variousSettings[j8] = l14;
						method33(j8);
						if(dialogueId != -1)
							updateChatbox = true;
					}
					opcode = -1;
					return true;

				case 36:
					int k8 = inBuffer.method434();
					byte byte0 = inBuffer.readSignedByte();
					anIntArray1045[k8] = byte0;
					if(variousSettings[k8] != byte0) {
						variousSettings[k8] = byte0;
						method33(k8);
						if(dialogueId != -1)
							updateChatbox = true;
					}
					opcode = -1;
					return true;

				case 61:
					multicombat = inBuffer.readUnsignedByte();
					opcode = -1;
					return true;

				case 200:
					int l8 = inBuffer.readUShort();
					int i15 = inBuffer.readSignedWord();
					Widget class9_4 = Widget.interfaceCache[l8];
					class9_4.defaultAnimationId = i15;
					if(i15 == -1) {
						class9_4.currentFrame = 0;
						class9_4.anInt208 = 0;
					}
					opcode = -1;
					return true;

				case 219:
					if(overlayInterfaceId != -1) {
						overlayInterfaceId = -1;
						tabAreaAltered = true;
					}
					if(backDialogueId != -1) {
						backDialogueId = -1;
						updateChatbox = true;
					}
					if(inputDialogState != 0) {
						inputDialogState = 0;
						updateChatbox = true;
					}
					openInterfaceId = -1;
					continuedDialogue = false;
					opcode = -1;
					return true;

				case 34:
					int i9 = inBuffer.readUShort();
					Widget class9_2 = Widget.interfaceCache[i9];
					while(inBuffer.currentPosition < packetSize) {
						int j20 = inBuffer.getSmart();
						int i23 = inBuffer.readUShort();
						int l25 = inBuffer.readUnsignedByte();
						if(l25 == 255)
							l25 = inBuffer.readInt();
						if(j20 >= 0 && j20 < class9_2.inventoryItemId.length) {
							class9_2.inventoryItemId[j20] = i23;
							class9_2.inventoryAmounts[j20] = l25;
						}
					}
					opcode = -1;
					return true;

				case 4:
				case 44:
				case 84:
				case 101:
				case 105:
				case 117:
				case 147:
				case 151:
				case 156:
				case 160:
				case 215:
					method137(inBuffer, opcode);
					opcode = -1;
					return true;

				case 106:
					tabId = inBuffer.method427();
					tabAreaAltered = true;
					opcode = -1;
					return true;

				case 164:
					int j9 = inBuffer.method434();
					method60(j9);
					if(overlayInterfaceId != -1) {
						overlayInterfaceId = -1;
						tabAreaAltered = true;
					}
					backDialogueId = j9;
					updateChatbox = true;
					openInterfaceId = -1;
					continuedDialogue = false;
					opcode = -1;
					return true;
				case 123:
					opcode = -1;
					return true;
			}
			signlink.reporterror("T1 - " + opcode + "," + packetSize + " - " + secondLastOpcode + "," + thirdLastOpcode);
			//resetLogout();
		} catch(IOException _ex) {
			dropClient();
		} catch(Exception exception) {
			/** Debugging packet 126 (text frames packet) **/
			boolean debugPkt126 = true;
			if (debugPkt126 && opcode == 126) {
				String s = inBuffer.readString();
				int i = inBuffer.method435();
				System.out.println();
				System.out.println("---------------------Please report this error on our forums--------------------");
				System.out.println("Text ID: " + i);
				System.out.println(s);
				System.out.println("-------------------------------------------------------------------------------");
				System.out.println();
				/** [EO] Debugging packet 126 (text frames packet) **/
			} else {
				String s2 = "T2 - " + opcode + "," + secondLastOpcode + "," + thirdLastOpcode + " - " + packetSize + "," + (regionBaseX + localPlayer.pathX[0]) + "," + (regionBaseY + localPlayer.pathY[0]) + " - ";
				for (int j15 = 0; j15 < packetSize && j15 < 50; j15++)
					s2 = s2 + inBuffer.payload[j15] + ",";
				System.out.println(s2);
				exception.printStackTrace();
			}
		}
		opcode = -1;
		return true;
	}

	void mouseWheelDragged(int i, int j) {
		if (!mouseWheelDown)
			return;
		this.anInt1186 += i * 3;
		this.anInt1187 += (j << 1);
	}

	public void moveCameraWithPlayer() {
		anInt1265++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		method104();
		if(!inCutScene) {
			int i = anInt1184;
			if(anInt984 / 256 > i)
				i = anInt984 / 256;
			if(aBooleanArray876[4] && anIntArray1203[4] + 128 > i)
				i = anIntArray1203[4] + 128;
			int k = cameraHorizontal + anInt896 & 0x7ff;
			setCameraPos(cameraZoom + i * ((SceneGraph.viewDistance == 9)
					&& (frameMode == ScreenMode.RESIZABLE) ? 2
					: SceneGraph.viewDistance == 10 ? 5 : 3), i, anInt1014, getCenterHeight(plane, localPlayer.y, localPlayer.x) - 50, k, anInt1015);

		}
		int j;
		if(!inCutScene)
			j = setCameraLocation();
		else
			j = resetCameraHeight();
		int l = xCameraPos;
		int i1 = zCameraPos;
		int j1 = yCameraPos;
		int k1 = yCameraCurve;
		int l1 = xCameraCurve;
		for(int i2 = 0; i2 < 5; i2++)
			if(aBooleanArray876[i2]) {
				int j2 = (int)((Math.random() * (double)(anIntArray873[i2] * 2 + 1) - (double)anIntArray873[i2]) + Math.sin((double)anIntArray1030[i2] * ((double)anIntArray928[i2] / 100D)) * (double)anIntArray1203[i2]);
				if(i2 == 0)
					xCameraPos += j2;
				if(i2 == 1)
					zCameraPos += j2;
				if(i2 == 2)
					yCameraPos += j2;
				if(i2 == 3)
					xCameraCurve = xCameraCurve + j2 & 0x7ff;
				if(i2 == 4) {
					yCameraCurve += j2;
					if(yCameraCurve < 128)
						yCameraCurve = 128;
					if(yCameraCurve > 383)
						yCameraCurve = 383;
				}
			}
		int k2 = Rasterizer3D.lastTextureRetrievalCount;
		Model.aBoolean1684 = true;
		Model.anInt1687 = 0;
		Model.anInt1685 = super.mouseX - (frameMode == ScreenMode.FIXED ? 4 : 0);
		Model.anInt1686 = super.mouseY - (frameMode == ScreenMode.FIXED ? 4 : 0);
		Rasterizer2D.clear();
		scene.render(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
		scene.clearGameObjectCache();
		updateEntities();
		drawHeadIcon();
		writeBackgroundTextures(k2);
		draw3dScreen();
		drawConsoleArea();
		drawConsole();
		if (openInterfaceId == -1) {
			if (shouldDrawCombatBox()) {
				drawCombatBox();
			}
		}
		if (frameMode != ScreenMode.FIXED) {
			drawChatArea();
			drawMinimap();
			drawTabArea();
		}
		gameScreenImageProducer.drawGraphics(frameMode == ScreenMode.FIXED ? 4 : 0, super.graphics, frameMode == ScreenMode.FIXED ? 4 : 0);
		xCameraPos = l;
		zCameraPos = i1;
		yCameraPos = j1;
		yCameraCurve = k1;
		xCameraCurve = l1;
	}

	//public void drawRunOrb() {
		/* Draws empty orb */
	/*	emptyOrb.drawSprite(190, 85);
		if (!runClicked) {
			if (super.mouseX > 710 && super.mouseX < 742 && super.mouseY > 88 && super.mouseY < 122) {
				hoverorbrun.drawSprite(190, 85);
			} else {
				runorb.drawSprite(190, 85);

			}
		} else {
			if (super.mouseX > 710 && super.mouseX < 742 && super.mouseY > 88 && super.mouseY < 122) {
				hoverorbrun2.drawSprite(165, 85);
			} else {
				runClick.drawSprite(165, 85);
			}
		}
	}*/

	private void processMinimapActions() {
		if (openInterfaceId == 15244) {
			return;
		}
		final boolean fixed = frameMode == ScreenMode.FIXED;
		if (fixed ? super.mouseX >= 542 && super.mouseX <= 579 && super.mouseY >= 2
				&& super.mouseY <= 38
				: super.mouseX >= frameWidth - 180 && super.mouseX <= frameWidth - 139
				&& super.mouseY >= 0 && super.mouseY <= 40) {
			menuActionText[1] = "Look North";
			menuActionTypes[1] = 696;
			menuActionRow = 2;
		}
		if (frameMode != ScreenMode.FIXED && stackSideStones) {
			if (super.mouseX >= frameWidth - 26 && super.mouseX <= frameWidth - 1 && super.mouseY >= 2 && super.mouseY <= 24) {
				menuActionText[1] = "Logout";
				menuActionTypes[1] = 700;
				menuActionRow = 2;
			}
		}
		if (worldHover && Configuration.enableOrbs) {
			menuActionText[1] = "Floating @lre@World Map";
			menuActionTypes[1] = 850;
			menuActionRow = 2;
		}
		if (specialHover && Configuration.enableSpecOrb) {
			menuActionText[1] = "Use @gre@Special Attack";
			menuActionTypes[1] = 851;
			menuActionRow = 2;
		}
		if (hpHover && Configuration.enableOrbs) {
			menuActionText[1] = (Configuration.hpAboveHeads ? "Deactivate" : "Activate") + " Health HUD";
			menuActionTypes[1] = 1508;
			menuActionRow = 2;
		}
		if (expCounterHover) {
			menuActionText[3] = (Configuration.expCounterOpen ? "Hide" : "Show") + " @lre@Exp counter";
			menuActionTypes[3] = 258;
			menuActionText[2] = (Configuration.mergeExpDrops ? "Unmerge" : "Merge") + " @lre@Exp Drops";
			menuActionTypes[2] = 257;
			menuActionText[1] = "Toggle @lre@XP Lock";
			menuActionTypes[1] = 476;
			menuActionRow = 4;
		}
		if (prayHover && Configuration.enableOrbs) {
			menuActionText[2] = (prayClicked ? "Deactivate" : "Activate") + " Quick-prayers";
			menuActionTypes[2] = 1500;
			menuActionRow = 2;
			menuActionText[1] = "Setup Quick-prayers";
			menuActionTypes[1] = 1506;
			menuActionRow = 3;
		}
		if (runHover && Configuration.enableOrbs) {
			menuActionText[1] = "Toggle Run";
			menuActionTypes[1] = 1050;
			menuActionRow = 2;
		}
	}

		public int getOrbFill(int statusInt){
		if(statusInt <= 100 && statusInt >= 97) {
			return 0;
		} else if(statusInt <= 96 && statusInt >= 93) {
			return 1;
		} else if(statusInt <= 92 && statusInt >= 89) {
			return 2;
		} else if(statusInt <= 88 && statusInt >= 85) {
			return 3;
		} else if(statusInt <= 84 && statusInt >= 81) {
			return 4;
		} else if(statusInt <= 80 && statusInt >= 77) {
			return 5;
		} else if(statusInt <= 76 && statusInt >= 73) {
			return 6;
		} else if(statusInt <= 72 && statusInt >= 69) {
			return 7;
		} else if(statusInt <= 68 && statusInt >= 65) {
			return 8;
		} else if(statusInt <= 64 && statusInt >= 61) {
			return 9;
		} else if(statusInt <= 60 && statusInt >= 57) {
			return 10;
		} else if(statusInt <= 56 && statusInt >= 53) {
			return 11;
		} else if(statusInt <= 52 && statusInt >= 49) {
			return 12;
		} else if(statusInt <= 48 && statusInt >= 45) {
			return 13;
		} else if(statusInt <= 44 && statusInt >= 41) {
			return 14;
		} else if(statusInt <= 40 && statusInt >= 37) {
			return 15;
		} else if(statusInt <= 36 && statusInt >= 33) {
			return 16;
		} else if(statusInt <= 32 && statusInt >= 29) {
			return 17;
		} else if(statusInt <= 28 && statusInt >= 25) {
			return 18;
		} else if(statusInt <= 24 && statusInt >= 21) {
			return 19;
		} else if(statusInt <= 20 && statusInt >= 17) {
			return 20;
		} else if(statusInt <= 16 && statusInt >= 13) {
			return 21;
		} else if(statusInt <= 12 && statusInt >= 9) {
			return 22;
		} else if(statusInt <= 8 && statusInt >= 7) {
			return 23;
		} else if(statusInt <= 6 && statusInt >= 5) {
			return 24;
		} else if(statusInt <= 4 && statusInt >= 3) {
			return 25;
		} else if(statusInt <= 2 && statusInt >= 1) {
			return 26;
		} else if(statusInt <= 0) {
			return 27;
		}
		return 0;
	}

	public boolean restOrb, musicOrb, prayClicked, prayHover, runHover;
	public void loadExtraSprites(){
		for(int i = 1; i <= 15; i++) {
			ORBS[i] = new Sprite("Gameframe/Orbs/ORBS "+i+"");
		}
		for(int i = 0; i <= 4;i++) {
			LOGOUT[i] = new Sprite("Gameframe/Orbs/X "+i+"");
		}
	}

	public int getOrbTextColor(int statusInt){
		if(statusInt >= 75 && statusInt <= 100){
			return 0x00FF00;
		} else if(statusInt >= 50 && statusInt <= 74){
			return 0xFFFF00;
		} else if(statusInt >= 25 && statusInt <= 49){
			return 0xFF981F;
		} else {
			return 0xFF0000;
		}
	}

	public int runState = 1;
	public Sprite[] ORBS = new Sprite[16];
	public Sprite[] LOGOUT = new Sprite[5];
	private boolean clickedQuickPrayers;


	public void clearTopInterfaces() {
		buffer.createFrame(130);
		if (overlayInterfaceId != -1) {
			overlayInterfaceId = -1;
			continuedDialogue = false;
			tabAreaAltered = true;
		}
		if (backDialogueId != -1) {
			backDialogueId = -1;
			updateChatbox = true;
			continuedDialogue = false;
		}
		openInterfaceId = -1;
		fullscreenInterfaceID = -1;
	}

	public Client() {
		fullscreenInterfaceID = -1;
		chatRights = new int[500];
		chatTypeView = 0;
		clanChatMode = 0;
		cButtonHPos = -1;
		cButtonHCPos = -1;
		cButtonCPos = 0;
		if (Configuration.clientState.equals(Configuration.ClientState.Live)) {
			server = "allstarlegends.ddns.net";
		} else {
			server = "127.0.0.1";
		}
		anIntArrayArray825 = new int[104][104];
		friendsNodeIDs = new int[200];
		groundItems = new Deque[4][104][104];
		aBoolean831 = false;
		aBuffer_834 = new Buffer(new byte[5000]);
		npcs = new Npc[16384];
		npcIndices = new int[16384];
		anIntArray840 = new int[1000];
		aBuffer_847 = Buffer.create();
		aBoolean848 = true;
		openInterfaceId = -1;
		currentExp = new int[Skills.skillsCount];
		aBoolean872 = false;
		anIntArray873 = new int[5];
		anInt874 = -1;
		aBooleanArray876 = new boolean[5];
		drawFlames = false;
		reportAbuseInput = "";
		localPlayerIndex = -1;
		menuOpen = false;
		inputString = "";
		maxPlayers = 2048;
		internalLocalPlayerIndex = 2047;
		players = new Player[maxPlayers];
		playerList = new int[maxPlayers];
		mobsAwaitingUpdate = new int[maxPlayers];
		aBufferArray895s = new Buffer[maxPlayers];
		anInt897 = 1;
		anIntArrayArray901 = new int[104][104];
		anInt902 = 0x766654;
		aByteArray912 = new byte[16384];
		currentLevels = new int[Skills.skillsCount];
		ignoreListAsLongs = new long[100];
		loadingError = false;
		consoleInput = "";
		consoleOpen = false;
		consoleMessages = new String[17];
		anInt927 = 0x332d25;
		anIntArray928 = new int[5];
		anIntArrayArray929 = new int[104][104];
		chatTypes = new int[500];
		chatNames = new String[500];
		chatMessages = new String[500];
		chatButtons = new Sprite[4];
		sideIcons = new Sprite[15];
		sideIcons2 = new Sprite[15];
		redStones = new Sprite[5];
		aBoolean954 = true;
		friendsListAsLongs = new long[200];
		currentSong = -1;
		drawingFlames = false;
		spriteDrawX = -1;
		spriteDrawY = -1;
		anIntArray968 = new int[33];
		anIntArray969 = new int[256];
		decompressors = new Decompressor[5];
		variousSettings = new int[2000];
		aBoolean972 = false;
		anInt975 = 50;
		anIntArray976 = new int[anInt975];
		anIntArray977 = new int[anInt975];
		anIntArray978 = new int[anInt975];
		anIntArray979 = new int[anInt975];
		anIntArray980 = new int[anInt975];
		anIntArray981 = new int[anInt975];
		anIntArray982 = new int[anInt975];
		aStringArray983 = new String[anInt975];
		anInt985 = -1;
		hitMarks = new Sprite[20];
		anIntArray990 = new int[5];
		aBoolean994 = false;
		anInt1002 = 0x23201b;
		amountOrNameInput = "";
		aClass19_1013 = new Deque();
		aBoolean1017 = false;
		openWalkableInterface = -1;
		anIntArray1030 = new int[5];
		aBoolean1031 = false;
		mapFunctions = new Sprite[100];
		dialogueId = -1;
		maximumLevels = new int[Skills.skillsCount];
		anIntArray1045 = new int[2000];
		maleCharacter = true;
		minimapLeft = new int[152];
		flashingSidebarId = -1;
		projectiles = new Deque();
		anIntArray1057 = new int[33];
		aClass9_1059 = new Widget();
		mapScenes = new IndexedImage[100];
		barFillColor = 0x4d4233;
		anIntArray1065 = new int[7];
		minimapHintX = new int[1000];
		minimapHintY = new int[1000];
		aBoolean1080 = false;
		friendsList = new String[200];
		inBuffer = Buffer.create();
		expectedCRCs = new int[9];
		firstMenuAction = new int[500];
		secondMenuAction = new int[500];
		menuActionTypes = new int[500];
		selectedMenuActions = new int[500];
		headIcons = new Sprite[20];
		skullIcons = new Sprite[20];
		headIconsHint = new Sprite[20];
		tabAreaAltered = false;
		aString1121 = "";
		playerOptions = new String[5];
		playerOptionsHighPriority = new boolean[5];
		constructRegionData = new int[4][13][13];
		anInt1132 = 2;
		minimapHint = new Sprite[1000];
		aBoolean1141 = false;
		continuedDialogue = false;
		crosses = new Sprite[8];
		musicEnabled = true;
		loggedIn = false;
		canMute = false;
		aBoolean1159 = false;
		inCutScene = false;
		anInt1171 = 1;
		myUsername = "";
		myPassword = "";
		genericLoadingError = false;
		reportAbuseInterfaceID = -1;
		spawns = new Deque();
		anInt1184 = 128;
		overlayInterfaceId = -1;
		buffer = Buffer.create();
		menuActionText = new String[500];
		anIntArray1203 = new int[5];
		anIntArray1207 = new int[50];
		anInt1210 = 2;
		anInt1211 = 78;
		promptInput = "";
		//mod icons int 2
		modIcons = new IndexedImage[7];
		tabId = 3;
		updateChatbox = false;
		songChanging = true;
		minimapLineWidth = new int[152];
		collisionMaps = new CollisionMap[4];
		aBoolean1233 = false;
		anIntArray1240 = new int[100];
		trackLoops = new int[50];
		aBoolean1242 = false;
		anIntArray1250 = new int[50];
		soundVolume = new int[50];
		rsAlreadyLoaded = false;
		welcomeScreenRaised = false;
		messagePromptRaised = false;
		firstLoginMessage = "";
		secondLoginMessage = "";
		backDialogueId = -1;
		anInt1279 = 2;
		bigX = new int[4000];
		bigY = new int[4000];
		anInt1289 = -1;
	}

	public int rights;
	public String name;
	public String message;
	public String clanname;
	private final int[] chatRights;
	public int chatTypeView;
	public int clanChatMode;
	public int duelMode;
	/* Declare custom sprites */
	private Sprite chatArea;
	private Sprite musicLoginButton;
	private Sprite[] chatButtons;
	private Sprite titleButton;
	private Sprite titleBox;
	private Sprite tabArea;
	private Sprite mapArea;
	private Sprite emptyOrb;
	private Sprite hoverOrb;
	private Sprite hoverorbrun;
	private Sprite hoverorbrun2;
	private Sprite runClick;
	private Sprite runorb;
	private Sprite hitPointsFill;
	private Sprite prayerFill;
	private Sprite hitPointsIcon;
	private Sprite prayerIcon;
	private Sprite worldMapIcon;
	private Sprite worldMapIconH;
 	public boolean runClicked = true;
	//public boolean prayClicked = false;
	/**/
	private ProducingGraphicsBuffer leftFrame;
	private ProducingGraphicsBuffer topFrame;
	private ProducingGraphicsBuffer rightFrame;
	private int ignoreCount;
	private long aLong824;
	private int[][] anIntArrayArray825;
	private int[] friendsNodeIDs;
	private Deque[][][] groundItems;
	private int[] anIntArray828;
	private int[] anIntArray829;
	private volatile boolean aBoolean831;
	private Socket aSocket832;
	private int loginScreenState;
	private Buffer aBuffer_834;
	private Npc[] npcs;
	private int npcCount;
	private int[] npcIndices;
	private int anInt839;
	private int[] anIntArray840;
	private int anInt841;
	private int secondLastOpcode;
	private int thirdLastOpcode;
	private String clickToContinueString;
	private int privateChatMode;
	private Buffer aBuffer_847;
	private boolean aBoolean848;
	private static int anInt849;
	private int[] anIntArray850;
	private int[] anIntArray851;
	private int[] anIntArray852;
	private int[] anIntArray853;
	private static int anInt854;
	private int hintIconDrawType;
	public static int openInterfaceId;
	private int xCameraPos;
	private int zCameraPos;
	private int yCameraPos;
	private int yCameraCurve;
	private int xCameraCurve;
	private int myPrivilege;
	private final int[] currentExp;
	private Sprite[] redStones;
	private Sprite mapFlag;
	private Sprite mapMarker;
	private boolean aBoolean872;
	private final int[] anIntArray873;
	private int anInt874;
	private final boolean[] aBooleanArray876;
	private int weight;
	private MouseDetection mouseDetection;
	private volatile boolean drawFlames;
	private String reportAbuseInput;
	private int localPlayerIndex;
	private boolean menuOpen;
	private int anInt886;
	private String inputString;
	private final int maxPlayers;
	private final int internalLocalPlayerIndex;
	private Player[] players;
	private int playerCount;
	private int[] playerList;
	private int mobsAwaitingUpdateCount;
	private int[] mobsAwaitingUpdate;
	private Buffer[] aBufferArray895s;
	private int anInt896;
	private int anInt897;
	private int friendsCount;
	private int anInt900;
	private int[][] anIntArrayArray901;
	private final int anInt902;
	private byte[] aByteArray912;
	private int anInt913;
	private int crossX;
	private int crossY;
	private int crossIndex;
	private int crossType;
	private int plane;
	private final int[] currentLevels;
	private static int anInt924;
	private final long[] ignoreListAsLongs;
	private boolean loadingError;
	private final int anInt927;
	private final int[] anIntArray928;
	private int[][] anIntArrayArray929;
	private Sprite aClass30_Sub2_Sub1_Sub1_931;
	private Sprite aClass30_Sub2_Sub1_Sub1_932;
	private int hintIconPlayerId;
	private int hintIconX;
	private int hintIconY;
	private int anInt936;
	private int anInt937;
	private int anInt938;
	private static int anInt940;
	private final int[] chatTypes;
	private final String[] chatNames;
	private final String[] chatMessages;
	private int tickDelta;
	private SceneGraph scene;
	private Sprite[] sideIcons;
	private Sprite[] sideIcons2;
	public static final SpriteCache spriteCache = new SpriteCache();
	private int menuScreenArea;
	private int menuOffsetX;
	private int menuOffsetY;
	private int menuWidth;
	private int menuHeight;
	private long aLong953;
	private boolean aBoolean954;
	private long[] friendsListAsLongs;
	private String[] clanList = new String[100];
	private int currentSong;
	private static int nodeID = 10;
	static int portOff;
	static boolean clientData;
	private static boolean isMembers = true;
	private static boolean lowMem;
	private volatile boolean drawingFlames;
	private int spriteDrawX;
	private int spriteDrawY;
	private final int[] anIntArray965 = {
		0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff
	};
	private IndexedImage titleBoxIndexedImage;
	private IndexedImage titleButtonIndexedImage;
	private final int[] anIntArray968;
	private final int[] anIntArray969;
	final Decompressor[] decompressors;
	public int variousSettings[];
	private boolean aBoolean972;
	private final int anInt975;
	private final int[] anIntArray976;
	private final int[] anIntArray977;
	private final int[] anIntArray978;
	private final int[] anIntArray979;
	private final int[] anIntArray980;
	private final int[] anIntArray981;
	private final int[] anIntArray982;
	private final String[] aStringArray983;
	private int anInt984;
	private int anInt985;
	private static int anInt986;
	private Sprite[] hitMarks;
	private int anInt988;
	private int dragItemDelay;
	private final int[] anIntArray990;
	private static boolean aBoolean993;
	private final boolean aBoolean994;
	private int anInt995;
	private int anInt996;
	private int anInt997;
	private int anInt998;
	private int anInt999;
	private ISAACRandomGen encryption;
	private Sprite mapEdge;
	private Sprite multiOverlay;
	private final int anInt1002;
	static final int[][] anIntArrayArray1003 = {
		{
			6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433,
			2983, 54193
		}, {
			8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153,
			56621, 4783, 1341, 16578, 35003, 25239
		}, {
			25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094,
			10153, 56621, 4783, 1341, 16578, 35003
		}, {
			4626, 11146, 6439, 12, 4758, 10270
		}, {
			4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574
		}
	};
	private String amountOrNameInput;
	private static int anInt1005;
	private int daysSinceLastLogin;
	private int packetSize;
	private int opcode;
	private int anInt1009;
	private int anInt1010;
	private int anInt1011;
	private Deque aClass19_1013;
	private int anInt1014;
	private int anInt1015;
	private int anInt1016;
	private boolean aBoolean1017;
	private int openWalkableInterface;
	private static final int[] SKILL_EXPERIENCE;
	private int minimapState;
	private int anInt1022;
	private int loadingStage;
	private Sprite scrollBar1;
	private Sprite scrollBar2;
	private int anInt1026;
	private IndexedImage backBase1;
	private IndexedImage backBase2;
	private IndexedImage backHmid1;
	private final int[] anIntArray1030;
	private boolean aBoolean1031;
	private Sprite[] mapFunctions;
	private int regionBaseX;
	private int regionBaseY;
	private int anInt1036;
	private int anInt1037;
	private int loginFailures;
	private int anInt1039;
	private int anInt1040;
	private int anInt1041;
	private int dialogueId;
	private final int[] maximumLevels;
	private final int[] anIntArray1045;
	private int anInt1046;
	private boolean maleCharacter;
	private int anInt1048;
	private String aString1049;
	private static int anInt1051;
	private final int[] minimapLeft;
	private FileArchive titleFileArchive;
	private int flashingSidebarId;
	private int multicombat;
	private Deque projectiles;
	private final int[] anIntArray1057;
	public final Widget aClass9_1059;
	private IndexedImage[] mapScenes;
	private static int anInt1061;
	private int trackCount;
	private final int barFillColor;
	private int friendsListAction;
	private final int[] anIntArray1065;
	private int mouseInvInterfaceIndex;
	private int lastActiveInvInterface;
	public ResourceProvider resourceProvider;
	int currentRegionX;
	int currentRegionY;
	private int anInt1071;
	private int[] minimapHintX;
	private int[] minimapHintY;
	private Sprite mapDotItem;
	private Sprite mapDotNPC;
	private Sprite mapDotPlayer;
	private Sprite mapDotFriend;
	private Sprite mapDotTeam;
	private Sprite mapDotClan;
	private int anInt1079;
	private boolean aBoolean1080;
	private String[] friendsList;
	private Buffer inBuffer;
	private String prayerBook;
	private int anInt1084;
	private int anInt1085;
	private int activeInterfaceType;
	private int anInt1087;
	private int anInt1088;
	public static int anInt1089;
	private final int[] expectedCRCs;
	private int[] firstMenuAction;
	private int[] secondMenuAction;
	private int[] menuActionTypes;
	private int[] selectedMenuActions;
	private Sprite[] headIcons;
	private Sprite[] skullIcons;
	private Sprite[] headIconsHint;
	private static int anInt1097;
	private int anInt1098;
	private int anInt1099;
	private int anInt1100;
	private int anInt1101;
	private int anInt1102;
	public static boolean tabAreaAltered;
	private int systemUpdateTime;
	private ProducingGraphicsBuffer topLeft1BackgroundTile;
	private ProducingGraphicsBuffer bottomLeft1BackgroundTile;
	private ProducingGraphicsBuffer loginBoxImageProducer;
	private ProducingGraphicsBuffer flameLeftBackground;
	private ProducingGraphicsBuffer flameRightBackground;
	private ProducingGraphicsBuffer bottomLeft0BackgroundTile;
	private ProducingGraphicsBuffer bottomRightImageProducer;
	private ProducingGraphicsBuffer middleLeft1BackgroundTile;
	private ProducingGraphicsBuffer aRSImageProducer_1115;
	private ProducingGraphicsBuffer loginScreenAccessories;
	private ProducingGraphicsBuffer loginMusicImageProducer;
	private static int anInt1117;
	private int membersInt;
	private String aString1121;
	private Sprite compass;
	private ProducingGraphicsBuffer aRSImageProducer_1123;
	private ProducingGraphicsBuffer aRSImageProducer_1124;
	private ProducingGraphicsBuffer chatSettingImageProducer;
	public static Player localPlayer;
	private final String[] playerOptions;
	private final boolean[] playerOptionsHighPriority;
	private final int[][][] constructRegionData;
	public static final int[] tabInterfaceIDs = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1
	};
	private int anInt1131;
	private int anInt1132;
	private int menuActionRow;
	private static int anInt1134;
	private int spellSelected;
	private int anInt1137;
	private int spellUsableOn;
	private String spellTooltip;
	private Sprite[] minimapHint;
	private boolean aBoolean1141;
	private static int anInt1142;
	private int runEnergy;
	boolean continuedDialogue;
	private Sprite[] crosses;
	private boolean musicEnabled;
	private IndexedImage[] aIndexedImageArray1152s;
	private int unreadMessages;
	private static int anInt1155;
	private static boolean fpsOn;
	public static boolean loggedIn;
	private boolean canMute;
	private boolean aBoolean1159;
	private boolean inCutScene;
	static int tick;
	private static final String validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	private static ProducingGraphicsBuffer tabImageProducer;
	private ProducingGraphicsBuffer mapEdgeIP;
	private ProducingGraphicsBuffer minimapImageProducer;
	private static ProducingGraphicsBuffer gameScreenImageProducer;
	private static ProducingGraphicsBuffer chatboxImageProducer;
	private int daysSinceRecovChange;
	private RSSocket socketStream;
	private int anInt1169;
	private int minimapZoom;
	private int anInt1171;
	private long aLong1172;
	private String myUsername;
	private String myPassword;
	private static int anInt1175;
	private boolean genericLoadingError;
	private final int[] anIntArray1177 = {
		0, 0, 0, 0, 1, 1, 1, 1, 1, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 3
	};
	private int reportAbuseInterfaceID;
	private Deque spawns;
	private static int[] anIntArray1180;
	private static int[] anIntArray1181;
	private static int[] anIntArray1182;
	private byte[][] aByteArrayArray1183;
	private int anInt1184;
	private int cameraHorizontal;
	private int anInt1186;
	private int anInt1187;
	private static int anInt1188;
	private int overlayInterfaceId;
	private int[] anIntArray1190;
	private int[] anIntArray1191;
	private Buffer buffer;
	private int anInt1193;
	private int splitPrivateChat;
	private IndexedImage mapBack;
	private String[] menuActionText;
	private Sprite aClass30_Sub2_Sub1_Sub1_1201;
	private Sprite aClass30_Sub2_Sub1_Sub1_1202;
	private final int[] anIntArray1203;
	static final int[] anIntArray1204 = {
		9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145,
		58654, 5027, 1457, 16565, 34991, 25486
	};
	private static boolean flagged;
	private final int[] anIntArray1207;
	private int anInt1208;
	private int minimapRotation;
	private int anInt1210;
    public static int anInt1211;
	private String promptInput;
	private int anInt1213;
	private int[][][] tileHeights;
	private long aLong1215;
	private int loginScreenCursorPos;
	private final IndexedImage[] modIcons;
	private long aLong1220;
	public static int tabId;
	private int hintIconNpcId;
	public static boolean updateChatbox;
	int inputDialogState;
	private static int anInt1226;
	private int nextSong;
	private boolean songChanging;
	private final int[] minimapLineWidth;
	private CollisionMap[] collisionMaps;
	public static int anIntArray1232[];
	private boolean aBoolean1233;
	private int[] anIntArray1234;
	private int[] anIntArray1235;
	private int[] anIntArray1236;
	private int anInt1237;
	private int anInt1238;
	public final int anInt1239 = 100;
	private final int[] anIntArray1240;
	private final int[] trackLoops;
	private boolean aBoolean1242;
	private int atInventoryLoopCycle;
	private int atInventoryInterface;
	private int atInventoryIndex;
	private int atInventoryInterfaceType;
	private byte[][] aByteArrayArray1247;
	private int tradeMode;
	private int yellMode;
	private int anInt1249;
	private final int[] anIntArray1250;
	private final int[] soundVolume;
	private int anInt1251;
	private final boolean rsAlreadyLoaded;
	private int anInt1253;
	private int anInt1254;
	private boolean welcomeScreenRaised;
	boolean messagePromptRaised;
	private int anInt1257;
	private Sprite infinity;
	private byte[][][] tileFlags;
	private int prevSong;
	private int destinationX;
	private int destinationY;
	private Sprite minimapImage;
	public RSFont newSmallFont, newRegularFont, newBoldFont, newFancyFont;
    public Sprite[] chatImages = new Sprite[2];
	private int anInt1264;
	private int anInt1265;
	private String firstLoginMessage;
	private String secondLoginMessage;
	private int localX;
	private int localY;
	private GameFont smallText;
	private GameFont regularText;
	private GameFont boldText;
	private GameFont gameFont;
	private int anInt1275;
	int backDialogueId;
	private int anInt1278;
	private int anInt1279;
	private int[] bigX;
	private int[] bigY;
	private int itemSelected;
	private int anInt1283;
	private int anInt1284;
	private int anInt1285;
	private String selectedItemName;
	private int publicChatMode;
	private static int anInt1288;
	private int anInt1289;
	public static int anInt1290;
	public static String server = "";
	public int drawCount;
	public int fullscreenInterfaceID;
	public int anInt1044;//377
	public int anInt1129;//377
	public int anInt1315;//377
	public int anInt1500;//377
	public int anInt1501;//377
	public static int[] fullScreenTextureArray;
	private final int[] modeX = {160, 224, 288, 352, 416},
			modeNamesX = {26, 84, 146, 206, 278, 339, 408, 465},
			modeNamesY = {158, 158, 153, 153, 153, 153, 153, 158},
			channelButtonsX = {5, 69, 133, 197, 261, 325, 389, 453};
	private final String[] modeNames =
			{"All", "Game", "Public", "Private", "Clan", "Trade", "Yell", "Report"};
	//HOTKEY VARIABLES
	public static int ESC_HOTKEY = 3,
		F1_HOTKEY = 0,
		F2_HOTKEY = 1,
		F3_HOTKEY = 2,
		F4_HOTKEY = 4,
		F5_HOTKEY = 5,
		F6_HOTKEY = 6,
		F7_HOTKEY = 7,
		F8_HOTKEY = 8,
		F9_HOTKEY = 9,
		F10_HOTKEY = 10,
		F11_HOTKEY = 11,
		F12_HOTKEY = 12;

	public void resetAllImageProducers() {
		if (super.fullGameScreen != null) {
			return;
		}
		chatboxImageProducer = null;
		minimapImageProducer = null;
		tabImageProducer = null;
		gameScreenImageProducer = null;
		aRSImageProducer_1123 = null;
		aRSImageProducer_1124 = null;
		chatSettingImageProducer = null;
		topLeft1BackgroundTile = null;
		bottomLeft1BackgroundTile = null;
		loginBoxImageProducer = null;
		flameLeftBackground = null;
		flameRightBackground = null;
		bottomLeft0BackgroundTile = null;
		bottomRightImageProducer = null;
		middleLeft1BackgroundTile = null;
		aRSImageProducer_1115 = null;
		super.fullGameScreen = new ProducingGraphicsBuffer(765, 503);
		welcomeScreenRaised = true;
	}

	public static void launchURL(String url) {
		Desktop d = Desktop.getDesktop();
		if (!url.contains("http")) {
			url = "https://" + url;
		}
		try {
			d.browse(new URI(url));
		} catch (Exception e) {
		}
	}

	public void frameMode(ScreenMode screenMode) {
		if (frameMode != screenMode) {
			frameMode = screenMode;
			if (screenMode == ScreenMode.FIXED) {
				frameWidth = 765;
				frameHeight = 503;
				cameraZoom = 600;
				SceneGraph.viewDistance = 9;
			} else if (screenMode == ScreenMode.RESIZABLE) {
				frameWidth = 766;
				frameHeight = 529;
				cameraZoom = 850;
				SceneGraph.viewDistance = 10;
			} else if (screenMode == ScreenMode.FULLSCREEN) {
				cameraZoom = 600;
				SceneGraph.viewDistance = 10;
				frameWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				frameHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			}
			rebuildFrameSize(screenMode, frameWidth, frameHeight);
			setBounds();
		}
		stackSideStones = screenMode == ScreenMode.FIXED ? false : stackSideStones;
		showChatComponents = screenMode == ScreenMode.FIXED ? true : showChatComponents;
		showTabComponents = screenMode == ScreenMode.FIXED ? true : showTabComponents;
	}

	public void rebuildFrameSize(ScreenMode screenMode, int width, int height) {
		screenAreaWidth = (screenMode == ScreenMode.FIXED) ? 512 : width;
		screenAreaHeight = (screenMode == ScreenMode.FIXED) ? 334 : height;
		frameWidth = width;
		frameHeight = height;
		instance.rebuildFrame(width, height, screenMode == ScreenMode.RESIZABLE, screenMode == ScreenMode.FULLSCREEN);
	}

	private static void setBounds() {
		Rasterizer3D.reposition(frameWidth, frameHeight);
		fullScreenTextureArray = Rasterizer3D.scanOffsets;
		Rasterizer3D.reposition(
				frameMode == ScreenMode.FIXED
						? (chatboxImageProducer != null
						? chatboxImageProducer.canvasWidth : 519)
						: frameWidth,
				frameMode == ScreenMode.FIXED
						? (chatboxImageProducer != null
						? chatboxImageProducer.canvasHeight : 165)
						: frameHeight);
		anIntArray1180 = Rasterizer3D.scanOffsets;
		Rasterizer3D.reposition(
				frameMode == ScreenMode.FIXED
						? (tabImageProducer != null ? tabImageProducer.canvasWidth
						: 249)
						: frameWidth,
				frameMode == ScreenMode.FIXED ? (tabImageProducer != null
						? tabImageProducer.canvasHeight : 335) : frameHeight);
		anIntArray1181 = Rasterizer3D.scanOffsets;
		Rasterizer3D.reposition(screenAreaWidth, screenAreaHeight);
		anIntArray1182 = Rasterizer3D.scanOffsets;
		int ai[] = new int[9];
		for (int i8 = 0; i8 < 9; i8++) {
			int k8 = 128 + i8 * 32 + 15;
			int l8 = 600 + k8 * 3;
			int i9 = Rasterizer3D.SINE[k8];
			ai[i8] = l8 * i9 >> 16;
		}
		if (frameMode == ScreenMode.RESIZABLE && (frameWidth >= 765) && (frameWidth <= 1025)
				&& (frameHeight >= 503) && (frameHeight <= 850)) {
			SceneGraph.viewDistance = 9;
			cameraZoom = 575;
		} else if (frameMode == ScreenMode.FIXED) {
			cameraZoom = 600;
		} else if (frameMode == ScreenMode.RESIZABLE || frameMode == ScreenMode.FULLSCREEN) {
			SceneGraph.viewDistance = 10;
			cameraZoom = 600;
		}
		SceneGraph.setupViewport(500, 800, screenAreaWidth, screenAreaHeight, ai);
		if (loggedIn) {
			gameScreenImageProducer =
					new ProducingGraphicsBuffer(screenAreaWidth, screenAreaHeight);
		}
	}

	public void refreshFrameSize() {
		if (frameMode == ScreenMode.RESIZABLE) {
			if (frameWidth != (appletClient() ? getGameComponent().getWidth() : GameWindow.getInstance().getFrameWidth())) {
				frameWidth = (appletClient() ? getGameComponent().getWidth() : GameWindow.getInstance().getFrameWidth());
				screenAreaWidth = frameWidth;
				setBounds();
			}
			if (frameHeight != (appletClient() ? getGameComponent().getHeight() : GameWindow.getInstance().getFrameHeight())) {
				frameHeight = (appletClient() ? getGameComponent().getHeight() : GameWindow.getInstance().getFrameHeight());
				screenAreaHeight = frameHeight;
				setBounds();
			}
		}
	}

	static  {
		SKILL_EXPERIENCE = new int[120];
		int i = 0;
		for(int j = 0; j < 120; j++) {
			int l = j + 1;
			int i1 = (int)((double)l + 300D * Math.pow(2D, (double)l / 7D));
			i += i1;
			SKILL_EXPERIENCE[j] = i / 4;
		}
		anIntArray1232 = new int[32];
		i = 2;
		for(int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}
	}

	public enum ScreenMode {
		FIXED, RESIZABLE, FULLSCREEN;
	}
}

