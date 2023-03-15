// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class IdentityKits {

	public static void unpackConfig(FileArchive fileArchive)
	{
		Buffer buffer = new Buffer(fileArchive.readFile("idk.dat"));
		length = buffer.readUShort();
		if(kits == null)
			kits = new IdentityKits[length];
		for(int j = 0; j < length; j++)
		{
			if(kits[j] == null)
				kits[j] = new IdentityKits();
			kits[j].readValues(buffer);
		}
	}

	private void readValues(Buffer buffer)
	{
		do
		{
			int i = buffer.readUnsignedByte();
			if(i == 0)
				return;
			if(i == 1)
				bodyPartId = buffer.readUnsignedByte();
			else
			if(i == 2)
			{
				int j = buffer.readUnsignedByte();
				anIntArray658 = new int[j];
				for(int k = 0; k < j; k++)
					anIntArray658[k] = buffer.readUShort();

			} else
			if(i == 3)
				validStyle = true;
			else
			if(i >= 40 && i < 50)
				anIntArray659[i - 40] = buffer.readUShort();
			else
			if(i >= 50 && i < 60)
				anIntArray660[i - 50] = buffer.readUShort();
			else
			if(i >= 60 && i < 70)
				anIntArray661[i - 60] = buffer.readUShort();
			else
				System.out.println("Error unrecognised config code: " + i);
		} while(true);
	}

	public boolean method537()
	{
		if(anIntArray658 == null)
			return true;
		boolean flag = true;
		for(int j = 0; j < anIntArray658.length; j++)
			if(!Model.isCached(anIntArray658[j]))
				flag = false;

		return flag;
	}

	public Model method538()
	{
		if(anIntArray658 == null)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray658.length];
		for(int i = 0; i < anIntArray658.length; i++)
			aclass30_sub2_sub4_sub6s[i] = Model.getModel(anIntArray658[i]);

		Model model;
		if(aclass30_sub2_sub4_sub6s.length == 1)
			model = aclass30_sub2_sub4_sub6s[0];
		else
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		for(int j = 0; j < 6; j++)
		{
			if(anIntArray659[j] == 0)
				break;
			model.recolor(anIntArray659[j], anIntArray660[j]);
		}

		return model;
	}

	public boolean method539()
	{
		boolean flag1 = true;
		for(int i = 0; i < 5; i++)
			if(anIntArray661[i] != -1 && !Model.isCached(anIntArray661[i]))
				flag1 = false;

		return flag1;
	}

	public Model method540()
	{
		Model aclass30_sub2_sub4_sub6s[] = new Model[5];
		int j = 0;
		for(int k = 0; k < 5; k++)
			if(anIntArray661[k] != -1)
				aclass30_sub2_sub4_sub6s[j++] = Model.getModel(anIntArray661[k]);

		Model model = new Model(j, aclass30_sub2_sub4_sub6s);
		for(int l = 0; l < 6; l++)
		{
			if(anIntArray659[l] == 0)
				break;
			model.recolor(anIntArray659[l], anIntArray660[l]);
		}

		return model;
	}

	private IdentityKits()
	{
		bodyPartId = -1;
		anIntArray659 = new int[6];
		anIntArray660 = new int[6];
		validStyle = false;
	}

	public static int length;
	public static IdentityKits kits[];
	public int bodyPartId;
	private int[] anIntArray658;
	private final int[] anIntArray659;
	private final int[] anIntArray660;
	private final int[] anIntArray661 = {
		-1, -1, -1, -1, -1
	};
	public boolean validStyle;
}
