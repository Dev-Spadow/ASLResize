public final class Player extends Mob {

	public Model getRotatedModel() {
		if(!visible)
			return null;
		Model model = method452();
		if(model == null)
			return null;
		super.height = model.modelBaseY;
		model.fits_on_single_square = true;
		if(aBoolean1699)
			return model;
		if(super.graphic != -1 && super.anInt1521 != -1)
		{
			Graphic graphic = Graphic.cache[super.graphic];
			Model model_2 = graphic.getModel();
			if(model_2 != null)
			{
				Model model_3 = new Model(true, Frame.noAnimationInProgress(super.anInt1521), false, model_2);
				model_3.translate(0, -super.anInt1524, 0);
				model_3.skin();
				model_3.applyTransform(graphic.animationSequence.primaryFrames[super.anInt1521]);
				model_3.faceGroups = null;
				model_3.vertexGroups = null;
				if(graphic.resizeXY != 128 || graphic.resizeZ != 128)
					model_3.scale(graphic.resizeXY, graphic.resizeXY, graphic.resizeZ);
					if (Client.newRendering)
					model_3.light(74 + graphic.modelBrightness, 1550 + graphic.modelShadow, -50, -110, -50, true);
				else
					model_3.light(64 + graphic.modelBrightness, 850 + graphic.modelShadow, -30, -50, -30, true);
				Model aclass30_sub2_sub4_sub6_1s[] = {
						model, model_3
				};
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}
		if(aModel_1714 != null)
		{
			if(Client.tick >= anInt1708)
				aModel_1714 = null;
			if(Client.tick >= anInt1707 && Client.tick < anInt1708)
			{
				Model model_1 = aModel_1714;
				model_1.translate(anInt1711 - super.x, anInt1712 - anInt1709, anInt1713 - super.y);
				if(super.turnDirection == 512)
				{
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				} else
				if(super.turnDirection == 1024)
				{
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				} else
				if(super.turnDirection == 1536)
					model_1.rotate90Degrees();
				Model aclass30_sub2_sub4_sub6s[] = {
						model, model_1
				};
				model = new Model(aclass30_sub2_sub4_sub6s);
				if(super.turnDirection == 512)
					model_1.rotate90Degrees();
				else
				if(super.turnDirection == 1024)
				{
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				} else
				if(super.turnDirection == 1536)
				{
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
					model_1.rotate90Degrees();
				}
				model_1.translate(super.x - anInt1711, anInt1709 - anInt1712, super.y - anInt1713);
			}
		}
		model.fits_on_single_square = true;
		return model;
	}

	public void updatePlayer(Buffer buffer)
	{
		buffer.currentPosition = 0;
		anInt1702 = buffer.readUnsignedByte();
		headIcon = buffer.readUnsignedByte();
		skullIcon = buffer.readUnsignedByte();
		//hintIcon = item_data.readUnsignedByte();
		desc = null;
		team = 0;
		for(int j = 0; j < 12; j++)
		{
			int k = buffer.readUnsignedByte();
			if(k == 0)
			{
				equipment[j] = 0;
				continue;
			}
			int i1 = buffer.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if(j == 0 && equipment[0] == 65535)
			{
				desc = NpcDefinition.forID(buffer.readUShort());
				break;
			}
			if(equipment[j] >= 512 && equipment[j] - 512 < ItemDefinition.totalItems)
			{
				int l1 = ItemDefinition.lookup(equipment[j] - 512).team;
				if(l1 != 0)
					team = l1;
			}
		}

		for(int l = 0; l < 5; l++)
		{
			int j1 = buffer.readUnsignedByte();
			if(j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
				j1 = 0;
			anIntArray1700[l] = j1;
		}

		super.idleAnimation = buffer.readUShort();
		if(super.idleAnimation == 65535)
			super.idleAnimation = -1;
		super.standTurnAnimIndex = buffer.readUShort();
		if(super.standTurnAnimIndex == 65535)
			super.standTurnAnimIndex = -1;
		super.walkAnimIndex = buffer.readUShort();
		if(super.walkAnimIndex == 65535)
			super.walkAnimIndex = -1;
		super.turn180AnimIndex = buffer.readUShort();
		if(super.turn180AnimIndex == 65535)
			super.turn180AnimIndex = -1;
		super.turn90CWAnimIndex = buffer.readUShort();
		if(super.turn90CWAnimIndex == 65535)
			super.turn90CWAnimIndex = -1;
		super.turn90CCWAnimIndex = buffer.readUShort();
		if(super.turn90CCWAnimIndex == 65535)
			super.turn90CCWAnimIndex = -1;
		super.runAnimIndex = buffer.readUShort();
		if(super.runAnimIndex == 65535)
			super.runAnimIndex = -1;
		name = TextClass.fixName(TextClass.nameForLong(buffer.readQWord()));
		combatLevel = buffer.readUnsignedByte();
		skill = buffer.readUShort();
		visible = true;
		aLong1718 = 0L;
		for(int k1 = 0; k1 < 12; k1++)
		{
			aLong1718 <<= 4;
			if(equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
		}

		if(equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if(equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for(int i2 = 0; i2 < 5; i2++)
		{
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	public Model method452()
	{
		if(desc != null)
		{
			int j = -1;
			if(super.emoteAnimation >= 0 && super.anInt1529 == 0)
				j = Animation.animations[super.emoteAnimation].primaryFrames[super.anInt1527];
			else
			if(super.movementAnimation >= 0)
				j = Animation.animations[super.movementAnimation].primaryFrames[super.anInt1518];
			Model model = desc.method164(-1, j, null);
			return model;
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if(super.emoteAnimation >= 0 && super.anInt1529 == 0)
		{
			Animation animation = Animation.animations[super.emoteAnimation];
			k = animation.primaryFrames[super.anInt1527];
			if(super.movementAnimation >= 0 && super.movementAnimation != super.idleAnimation)
				i1 = Animation.animations[super.movementAnimation].primaryFrames[super.anInt1518];
			if(animation.anInt360 >= 0)
			{
				j1 = animation.anInt360;
				l += j1 - equipment[5] << 40;
			}
			if(animation.anInt361 >= 0)
			{
				k1 = animation.anInt361;
				l += k1 - equipment[3] << 48;
			}
		} else
		if(super.movementAnimation >= 0)
			k = Animation.animations[super.movementAnimation].primaryFrames[super.anInt1518];
		Model model_1 = (Model) referenceCache.get(l);
		if(model_1 == null)
		{
			boolean flag = false;
			for(int i2 = 0; i2 < 12; i2++)
			{
				int k2 = equipment[i2];
				if(k1 >= 0 && i2 == 3)
					k2 = k1;
				if(j1 >= 0 && i2 == 5)
					k2 = j1;
				if(k2 >= 256 && k2 < 512 && !IdentityKits.kits[k2 - 256].method537())
					flag = true;
				if(k2 >= 512 && !ItemDefinition.lookup(k2 - 512).isEquippedModelCached(anInt1702))
					flag = true;
			}

			if(flag)
			{
				if(aLong1697 != -1L)
					model_1 = (Model) referenceCache.get(aLong1697);
				if(model_1 == null)
					return null;
			}
		}
		if(model_1 == null)
		{
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];//12
			int j2 = 0;
			for(int l2 = 0; l2 < 12; l2++)
			{
				int i3 = equipment[l2];
				if(k1 >= 0 && l2 == 3)
					i3 = k1;
				if(j1 >= 0 && l2 == 5)
					i3 = j1;
				if(i3 >= 256 && i3 < 512)
				{
					Model model_3 = IdentityKits.kits[i3 - 256].method538();
					if(model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if(i3 >= 512)
				{
					Model model_4 = ItemDefinition.lookup(i3 - 512).getEquippedModel(anInt1702);
					if(model_4 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for(int j3 = 0; j3 < 5; j3++)
				if(anIntArray1700[j3] != 0)
				{
					model_1.recolor(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if(j3 == 1)
						model_1.recolor(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
				}

			model_1.skin();
			if (Client.newRendering) {
				System.setProperty("sun.java2d.d3d", "true");
				System.setProperty("sun.java2d.d3dtexbpp", "32");
				System.setProperty("sun.java2d.ddforcevram", "true");
				System.setProperty("sun.java2d.translaccel", "true");
				System.setProperty("sun.java2d.pmoffscreen", "true");
				System.setProperty("sun.java2d.opengl", "true");
				model_1.light(86, 895, -90, -462, -90, true);
			} else
				model_1.light(64, 850, -30, -50, -30, true);
			referenceCache.put(model_1, l);
			aLong1697 = l;
		}
		if(aBoolean1699)
			return model_1;
		Model model_2 = Model.EMPTY_MODEL;
		model_2.method464(model_1, Frame.noAnimationInProgress(k) & Frame.noAnimationInProgress(i1));
		if(k != -1 && i1 != -1)
			model_2.applyAnimationFrames(Animation.animations[super.emoteAnimation].anIntArray357, i1, k);
		else
		if(k != -1)
			model_2.applyTransform(k);
		model_2.calculateDistances();
		model_2.faceGroups = null;
		model_2.vertexGroups = null;
		return model_2;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public int privelage;
	public Model method453()
	{
		if(!visible)
			return null;
		if(desc != null)
			return desc.method160();
		boolean flag = false;
		for(int i = 0; i < 12; i++)
		{
			int j = equipment[i];
			if(j >= 256 && j < 512 && !IdentityKits.kits[j - 256].method539())
				flag = true;
			if(j >= 512 && !ItemDefinition.lookup(j - 512).isDialogueModelCached(anInt1702))
				flag = true;
		}

		if(flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for(int l = 0; l < 12; l++)
		{
			int i1 = equipment[l];
			if(i1 >= 256 && i1 < 512)
			{
				Model model_1 = IdentityKits.kits[i1 - 256].method540();
				if(model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if(i1 >= 512)
			{
				Model model_2 = ItemDefinition.lookup(i1 - 512).getChatEquipModel(anInt1702);
				if(model_2 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_2;
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for(int j1 = 0; j1 < 5; j1++)
			if(anIntArray1700[j1] != 0)
			{
				model.recolor(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if(j1 == 1)
					model.recolor(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
			}

		return model;
	}

	Player()
	{
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		anInt1715 = 9;
		equipment = new int[12];
	}

	private long aLong1697;
	public NpcDefinition desc;
	boolean aBoolean1699;
	final int[] anIntArray1700;
	public int team;
	private int anInt1702;
	public String name;
	static ReferenceCache referenceCache = new ReferenceCache(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int anInt1707;
	int anInt1708;
	int anInt1709;
	boolean visible;
	int anInt1711;
	int anInt1712;
	int anInt1713;
	Model aModel_1714;
	private int anInt1715;
	public final int[] equipment;
	private long aLong1718;
	int anInt1719;
	int anInt1720;
	int anInt1721;
	int anInt1722;
	int skill;

}
