// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ObjectDefinition
{
	public static ObjectDefinition lookup(int i) {
		for(int j = 0; j < 20; j++)
			if(cache[j].type == i)
				return cache[j];
			cacheIndex = (cacheIndex + 1) % 20;
			ObjectDefinition objectDefinition = cache[cacheIndex];
			buffer.currentPosition = streamIndices[i];
			objectDefinition.type = i;
			objectDefinition.setDefaults();
			objectDefinition.readValues(buffer);
			if (i == 1992) {
				objectDefinition.name = "Shrine of Remembrance";
			}
			if (i==563) {
				objectDefinition.name ="Mythical Statue";
				objectDefinition.interactions = new String[5];
				objectDefinition.interactions[0] = "Inspect";
				
			}
			/*if (i == 1503) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}
			if (i == 26392) {
				objectDefinition.modelIds = new int[2];
				objectDefinition.modelIds[0] = 27816;
				objectDefinition.modelIds[1] = 27836;
				objectDefinition.isInteractive = false;
			}
			if (i == 6788) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}
			if (i == 8935) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}
			if (i == 1460) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}
			if (i == 9383) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}
			if (i == 1434) {
				objectDefinition.modelIds = new int[1];
				objectDefinition.modelIds[0] = 28124;
				objectDefinition.isInteractive = false;
			}*/
		return objectDefinition;
	}
	
    private void setDefaults()
    {
        modelIds = null;
        modelTypes = null;
        name = null;
        description = null;
        modifiedModelColors = null;
        originalModelColors = null;
        objectSizeX = 1;
        objectSizeY = 1;
        solid = true;
        impenetrable = true;
        isInteractive = false;
        contouredGround = false;
        delayShading = false;
        occludes = false;
        animation = -1;
        decorDisplacement = 16;
        ambientLighting = 0;
        aByte742 = 0;
        interactions = null;
        minimapFunction = -1;
        mapscene = -1;
        aBoolean751 = false;
        castsShadow = true;
        anInt748 = 128;
        anInt772 = 128;
        anInt740 = 128;
        anInt768 = 0;
        anInt738 = 0;
        anInt745 = 0;
        anInt783 = 0;
        obstructsGround = false;
        removeClipping = false;
        supportItems = -1;
        anInt774 = -1;
        anInt749 = -1;
        childrenIDs = null;
    }

    public void method574(ResourceProvider class42_sub1)
    {
        if(modelIds == null)
            return;
        for(int j = 0; j < modelIds.length; j++)
            class42_sub1.method560(modelIds[j] & 0xffff, 0);
    }

    public static void nullLoader()
    {
        referenceCache1 = null;
        referenceCache2 = null;
        streamIndices = null;
        cache = null;
		buffer = null;
    }

    public static void unpackConfig(FileArchive fileArchive)
    {
        ObjectDefinition.buffer = new Buffer(fileArchive.readFile("loc.dat"));
        Buffer buffer = new Buffer(fileArchive.readFile("loc.idx"));
        int totalObjects = buffer.readUShort();
        streamIndices = new int[totalObjects+35000];
        int i = 2;
        for(int j = 0; j < totalObjects; j++)
        {
            streamIndices[j] = i;
            i += buffer.readUShort();
        }
        cache = new ObjectDefinition[20];
        for(int k = 0; k < 20; k++)
            cache[k] = new ObjectDefinition();
    }

    public boolean method577(int i)
    {
        if(modelTypes == null)
        {
            if(modelIds == null)
                return true;
            if(i != 10)
                return true;
            boolean flag1 = true;
            for(int k = 0; k < modelIds.length; k++)
                flag1 &= Model.isCached(modelIds[k] & 0xffff);

            return flag1;
        }
        for(int j = 0; j < modelTypes.length; j++)
            if(modelTypes[j] == i)
                return Model.isCached(modelIds[j] & 0xffff);

        return true;
    }

    public Model modelAt(int i, int j, int k, int l, int i1, int j1, int k1)
    {
        Model model = method581(i, k1, j);
        if(model == null)
            return null;
        if(contouredGround || delayShading)
            model = new Model(contouredGround, delayShading, model);
        if(contouredGround)
        {
            int l1 = (k + l + i1 + j1) / 4;
            for(int i2 = 0; i2 < model.numVertices; i2++)
            {
                int j2 = model.vertexX[i2];
                int k2 = model.vertexZ[i2];
                int l2 = k + ((l - k) * (j2 + 64)) / 128;
                int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
                int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
                model.vertexY[i2] += j3 - l1;
            }

            model.computeSphericalBounds();
        }
        return model;
    }

    public boolean method579()
    {
        if(modelIds == null)
            return true;
        boolean flag1 = true;
        for(int i = 0; i < modelIds.length; i++)
            flag1 &= Model.isCached(modelIds[i] & 0xffff);
            return flag1;
    }

    public ObjectDefinition method580()
    {
        int i = -1;
        if(anInt774 != -1)
        {
            VarBit varBit = VarBit.cache[anInt774];
            int j = varBit.anInt648;
            int k = varBit.anInt649;
            int l = varBit.anInt650;
            int i1 = Client.anIntArray1232[l - k];
            i = clientInstance.variousSettings[j] >> k & i1;
        } else
        if(anInt749 != -1)
            i = clientInstance.variousSettings[anInt749];
        if(i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1)
            return null;
        else
            return lookup(childrenIDs[i]);
    }

    private Model method581(int j, int k, int l)
    {
        Model model = null;
        long l1;
        if(modelTypes == null)
        {
            if(j != 10)
                return null;
            l1 = (long)((type << 6) + l) + ((long)(k + 1) << 32);
            Model model_1 = (Model) referenceCache2.get(l1);
            if(model_1 != null)
                return model_1;
            if(modelIds == null)
                return null;
            boolean flag1 = aBoolean751 ^ (l > 3);
            int k1 = modelIds.length;
            for(int i2 = 0; i2 < k1; i2++)
            {
                int l2 = modelIds[i2];
                if(flag1)
                    l2 += 0x10000;
                model = (Model) referenceCache1.get(l2);
                if(model == null)
                {
                    model = Model.getModel(l2 & 0xffff);
                    if(model == null)
                        return null;
                    if(flag1)
                        model.method477();
                    referenceCache1.put(model, l2);
                }
                if(k1 > 1)
                    aModelArray741s[i2] = model;
            }

            if(k1 > 1)
                model = new Model(k1, aModelArray741s);
        } else
        {
            int i1 = -1;
            for(int j1 = 0; j1 < modelTypes.length; j1++)
            {
                if(modelTypes[j1] != j)
                    continue;
                i1 = j1;
                break;
            }

            if(i1 == -1)
                return null;
            l1 = (long)((type << 6) + (i1 << 3) + l) + ((long)(k + 1) << 32);
            Model model_2 = (Model) referenceCache2.get(l1);
            if(model_2 != null)
                return model_2;
            int j2 = modelIds[i1];
            boolean flag3 = aBoolean751 ^ (l > 3);
            if(flag3)
                j2 += 0x10000;
            model = (Model) referenceCache1.get(j2);
            if(model == null)
            {
                model = Model.getModel(j2 & 0xffff);
                if(model == null)
                    return null;
                if(flag3)
                    model.method477();
                referenceCache1.put(model, j2);
            }
        }
        boolean flag;
        flag = anInt748 != 128 || anInt772 != 128 || anInt740 != 128;
        boolean flag2;
        flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
        Model model_3 = new Model(modifiedModelColors == null, Frame.noAnimationInProgress(k), l == 0 && k == -1 && !flag && !flag2, model);
        if(k != -1)
        {
            model_3.skin();
            model_3.applyTransform(k);
            model_3.faceGroups = null;
            model_3.vertexGroups = null;
        }
        while(l-- > 0) 
            model_3.rotate90Degrees();
        if(modifiedModelColors != null)
        {
            for(int k2 = 0; k2 < modifiedModelColors.length; k2++)
                model_3.recolor(modifiedModelColors[k2], originalModelColors[k2]);

        }
        if(flag)
            model_3.scale(anInt748, anInt740, anInt772);
        if(flag2)
            model_3.translate(anInt738, anInt745, anInt783);
			if (Client.newRendering)
			model_3.light(74 + ambientLighting, 1000 + aByte742 * 5, -90, -580, -90, !delayShading);
		else 
			model_3.light(64 + ambientLighting, 768 + aByte742 * 5, -50, -10, -50, !delayShading);
        if(supportItems == 1)
            model_3.itemDropHeight = model_3.modelBaseY;
        referenceCache2.put(model_3, l1);
        return model_3;
    }

    private void readValues(Buffer buffer)
    {
        int flag = -1;
	do {
		int type = buffer.readUnsignedByte();
		if(type == 0)
			break;
		if(type == 1)
		{
			int len = buffer.readUnsignedByte();
			if(len > 0)
			{
				if(modelIds == null || lowMem)
				{
					modelTypes = new int[len];
                            		modelIds = new int[len];
                            		for(int k1 = 0; k1 < len; k1++)
                            		{
                                		modelIds[k1] = buffer.readUShort();
                                		modelTypes[k1] = buffer.readUnsignedByte();
                            		}
                        	} else
                        	{
                            		buffer.currentPosition += len * 3;
                        	}
       			}
		} else
		if(type == 2)
			name = buffer.readNewString();
		else
		if(type == 3)
			description = buffer.readBytes();
		else
		if(type == 5)
		{
			int len = buffer.readUnsignedByte();
			if(len > 0)
			{
				if(modelIds == null || lowMem)
				{
					modelTypes = null;
                            		modelIds = new int[len];
                            		for(int l1 = 0; l1 < len; l1++)
                                		modelIds[l1] = buffer.readUShort();
				} else
                        	{
                            		buffer.currentPosition += len * 2;
                        	}
			}
		} else
		if(type == 14)
			objectSizeX = buffer.readUnsignedByte();
		else
		if(type == 15)
			objectSizeY = buffer.readUnsignedByte();
		else
                if(type == 17)
                    solid = false;
                else
                if(type == 18)
                    impenetrable = false;
                else
				
                //if(interfaceType == 19)
                //    isInteractive = (item_data.readUnsignedByte() == 1);//ENABLE TO FIND OBJECT IDs with BAD MODELS!!!!!
				if(type == 19) {
					flag = buffer.readUnsignedByte();
					if(flag == 1)
						isInteractive = true;
				}//Enable to have no names with null and whatever	
                else
                if(type == 21)
                    contouredGround = true;
                else
                if(type == 22)
                    delayShading = true;
                else
                if(type == 23)
                    occludes = true;
                else
                if(type == 24)
                {
                    animation = buffer.readUShort();
                    if(animation == 65535)
                        animation = -1;
                } else
                if(type == 28)
                    decorDisplacement = buffer.readUnsignedByte();
                else
                if(type == 29)
                    ambientLighting = buffer.readSignedByte();
                else
                if(type == 39)
                    aByte742 = buffer.readSignedByte();
                else
                if(type >= 30 && type < 39)
               {
                    if(interactions == null)
                        interactions = new String[5];
                    interactions[type - 30] = buffer.readNewString();
                    if(interactions[type - 30].equalsIgnoreCase("hidden"))
                        interactions[type - 30] = null;
                } else
		if(type == 40)
                {
                    int i1 = buffer.readUnsignedByte();
                    modifiedModelColors = new int[i1];
                    originalModelColors = new int[i1];
                    for(int i2 = 0; i2 < i1; i2++)
                    {
                        modifiedModelColors[i2] = buffer.readUShort();
                        originalModelColors[i2] = buffer.readUShort();
                    }

                } else
                if(type == 60)
                    minimapFunction = buffer.readUShort();
                else
                if(type == 62)
                    aBoolean751 = true;
                else
                if(type == 64)
                    castsShadow = false;
                else
                if(type == 65)
                    anInt748 = buffer.readUShort();
                else
                if(type == 66)
                    anInt772 = buffer.readUShort();
                else
                if(type == 67)
                    anInt740 = buffer.readUShort();
                else
                if(type == 68)
                    mapscene = buffer.readUShort();
                else
                if(type == 69)
                    anInt768 = buffer.readUnsignedByte();
                else
                if(type == 70)
                    anInt738 = buffer.readSignedWord();
                else
                if(type == 71)
                    anInt745 = buffer.readSignedWord();
                else
                if(type == 72)
                    anInt783 = buffer.readSignedWord();
                else
		if(type == 73)
                    obstructsGround = true;
                else
                if(type == 74)
                    removeClipping = true;
                else
		if(type == 75)
		    supportItems = buffer.readUnsignedByte();
		else
		if(type == 77)
		{
			anInt774 = buffer.readUShort();
            		if(anInt774 == 65535)
                		anInt774 = -1;
            		anInt749 = buffer.readUShort();
            		if(anInt749 == 65535)
                		anInt749 = -1;
            		int j1 = buffer.readUnsignedByte();
            		childrenIDs = new int[j1 + 1];
            		for(int j2 = 0; j2 <= j1; j2++)
            		{
                		childrenIDs[j2] = buffer.readUShort();
                		if(childrenIDs[j2] == 65535)
                    			childrenIDs[j2] = -1;
            		}
		}
	} while(true);
        if (name != null && !name.equals("null")) {
            isInteractive = modelIds != null && (modelTypes == null || modelTypes[0] == 10);
            if (interactions != null)
                isInteractive = true;
        }

        if (removeClipping) {
            solid = false;
            impenetrable = false;
        }

        if (supportItems == -1) {
            supportItems = solid ? 1 : 0;
        }
    }

    private ObjectDefinition()
    {
        type = -1;
    }

    public boolean obstructsGround;
    private byte ambientLighting;
    private int anInt738;
    public String name;
    private int anInt740;
    private static final Model[] aModelArray741s = new Model[4];
    private byte aByte742;
    public int objectSizeX;
    private int anInt745;
    public int minimapFunction;
    private int[] originalModelColors;
    private int anInt748;
    public int anInt749;
    private boolean aBoolean751;
    public static boolean lowMem;
    private static Buffer buffer;
    public int type;
    private static int[] streamIndices;
    public boolean impenetrable;
    public int mapscene;
    public int childrenIDs[];
    private int supportItems;
    public int objectSizeY;
    public boolean contouredGround;
    public boolean occludes;
    public static Client clientInstance;
    private boolean removeClipping;
    public boolean solid;
    public int anInt768;
    private boolean delayShading;
    private static int cacheIndex;
    private int anInt772;
    private int[] modelIds;
    public int anInt774;
    public int decorDisplacement;
    private int[] modelTypes;
    public byte description[];
    public boolean isInteractive;
    public boolean castsShadow;
    public static ReferenceCache referenceCache2 = new ReferenceCache(30);
    public int animation;
    private static ObjectDefinition[] cache;
    private int anInt783;
    private int[] modifiedModelColors;
    public static ReferenceCache referenceCache1 = new ReferenceCache(500);
	public String interactions[];

}