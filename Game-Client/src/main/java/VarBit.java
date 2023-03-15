// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class VarBit {

	public static void unpackConfig(FileArchive fileArchive)
	{
		Buffer buffer = new Buffer(fileArchive.readFile("varbit.dat"));
		int cacheSize = buffer.readUShort();
		if(cache == null)
			cache = new VarBit[cacheSize];
		for(int j = 0; j < cacheSize; j++)
		{
			if(cache[j] == null)
				cache[j] = new VarBit();
			cache[j].readValues(buffer);
			if(cache[j].aBoolean651)
				Varp.cache[cache[j].anInt648].aBoolean713 = true;
		}

		if(buffer.currentPosition != buffer.payload.length)
			System.out.println("varbit load mismatch");
	}

	private void readValues(Buffer buffer)
	{
		do
		{
			int j = buffer.readUnsignedByte();
			if(j == 0)
				return;
			if(j == 1)
			{
				anInt648 = buffer.readUShort();
				anInt649 = buffer.readUnsignedByte();
				anInt650 = buffer.readUnsignedByte();
			} else
			if(j == 10)
				buffer.readString();
			else
			if(j == 2)
				aBoolean651 = true;
			else
			if(j == 3)
				buffer.readInt();
			else
			if(j == 4)
				buffer.readInt();
			else
				System.out.println("Error unrecognised config code: " + j);
		} while(true);
	}

	private VarBit()
	{
		aBoolean651 = false;
	}

	public static VarBit cache[];
	public int anInt648;
	public int anInt649;
	public int anInt650;
	private boolean aBoolean651;
}
