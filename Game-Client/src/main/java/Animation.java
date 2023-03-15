public final class Animation {
/*
	public static void init(FileArchive streamLoader) {
		Buffer item_data = new Buffer(streamLoader.readFile("seq.dat"));
		int length = item_data.readUShort();
		if (animations == null)
			animations = new Animation[length];
		for (int j = 0; j < length; j++) {
			if (animations[j] == null)
				animations[j] = new Animation();
			animations[j].readValues(item_data);
		}
	}
*/	
	public static int FrameStart[] = new int[40000];

    public static void unpackConfig(FileArchive fileArchive) {
		for(int j = 0; j < FrameStart.length; j++) {
			FrameStart[j] = 0;
		}
        Buffer buffer = new Buffer(fileArchive.readFile("seq.dat"));
        int length = buffer.readUShort();
        if (animations == null)
            animations = new Animation[length];
        for (int j = 0; j < length; j++) {
            if (animations[j] == null)
                animations[j] = new Animation();
            animations[j].readValues(buffer);
			try {
        //custom animations
/*		if(j == 10502) {
	int file = 2571;
	if(FrameStart[file] < 1){
	Frame.methodCustomAnimations(false, file);
	int[] frames = {92,20,81,21,30,25,65,70,87,120,53, 75,7,24,161,133,37,143,50,119,92,0};
	int[] delays = {3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,3,3,3,3,0};
	animations[j].anInt352 = frames.length-1;
	animations[j].primaryFrames = new int[frames.length-1];
	animations[j].secondaryFrames = new int[frames.length-1];
	animations[j].anIntArray355 = new int[frames.length-1];
	for (int i2 = 0; i2 < frames.length-1; i2++) {
		animations[j].primaryFrames[i2] = frames[i2]+FrameStart[file];
		animations[j].secondaryFrames[i2] = -1;
		animations[j].anIntArray355[i2] = delays[i2];
		}
	}
}
*/
if(j == 10502) {
	int file = 2571;
	if(FrameStart[file] < 1){
	Frame.methodCustomAnimations(false, file);
	int[] frames = {46,63,144,57,97,138,78,157,93, 66,42,114,114,132,149,140,109,46,0};
	int[] delays = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0};
	animations[j].anInt352 = frames.length-1;
	animations[j].primaryFrames = new int[frames.length-1];
	animations[j].secondaryFrames = new int[frames.length-1];
	animations[j].anIntArray355 = new int[frames.length-1];
	for (int i2 = 0; i2 < frames.length-1; i2++) {
		animations[j].primaryFrames[i2] = frames[i2]+FrameStart[file];
		animations[j].secondaryFrames[i2] = -1;
		animations[j].anIntArray355[i2] = delays[i2];
		}
	}
}

	//end custom animations
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

	public int method258(int i) {
		int j = anIntArray355[i];
		if (j == 0) {
			Frame frame = Frame.method531(primaryFrames[i]);
			if (frame != null)
				j = anIntArray355[i] = frame.anInt636;
		}
		if (j == 0)
			j = 1;
		return j;
	}

	public void readValues(Buffer buffer) {
		do {
			int i = buffer.readUnsignedByte();
			if (i == 0)
				break;
			if (i == 1) {
				anInt352 = buffer.readUShort();
				primaryFrames = new int[anInt352];
				secondaryFrames = new int[anInt352];
				anIntArray355 = new int[anInt352];
				for (int j = 0; j < anInt352; j++) {
					anIntArray355[j] = buffer.readUShort();
					secondaryFrames[j] = -1;
				}
				for (int j = 0; j < anInt352; j++)
					primaryFrames[j] = buffer.readUShort();
				for (int i1 = 0; i1 < anInt352; i1++) {
					primaryFrames[i1] = (buffer.readUShort() << 16)
					+ primaryFrames[i1];
				}
			} else if (i == 2)
				anInt356 = buffer.readUShort();
			else if (i == 3) {
				int k = buffer.readUnsignedByte();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++)
					anIntArray357[l] = buffer.readUnsignedByte();

				anIntArray357[k] = 0x98967f;
			} else if (i == 4)
				aBoolean358 = true;
			else if (i == 5)
				anInt359 = buffer.readUnsignedByte();
			else if (i == 6)
				anInt360 = buffer.readUShort();
			else if (i == 7)
				anInt361 = buffer.readUShort();
			else if (i == 8)
				anInt362 = buffer.readUnsignedByte();
			else if (i == 9)
				anInt363 = buffer.readUnsignedByte();
			else if (i == 10)
				priority = buffer.readUnsignedByte();
			else if (i == 11)
				anInt365 = buffer.readUnsignedByte();
			else if (i == 12)
				buffer.readInt();
			else
				System.out.println("Error unrecognised seq config code: " + i);
		} while (true);
		if (anInt352 == 0) {
			anInt352 = 1;
			primaryFrames = new int[1];
			primaryFrames[0] = -1;
			secondaryFrames = new int[1];
			secondaryFrames[0] = -1;
			anIntArray355 = new int[1];
			anIntArray355[0] = -1;
		}
		if (anInt363 == -1)
			if (anIntArray357 != null)
				anInt363 = 2;
			else
				anInt363 = 0;
		if (priority == -1) {
			if (anIntArray357 != null) {
				priority = 2;
				return;
			}
			priority = 0;
		}
	}

	public Animation() {
		anInt356 = -1;
		aBoolean358 = false;
		anInt359 = 5;
		anInt360 = -1;
		anInt361 = -1;
		anInt362 = 99;
		anInt363 = -1;
		priority = -1;
		anInt365 = 2;
	}

	public static Animation animations[];
	public int anInt352;
	public int primaryFrames[];
	public int secondaryFrames[];
	public int[] anIntArray355;
	public int anInt356;
	public int anIntArray357[];
	public boolean aBoolean358;
	public int anInt359;
	public int anInt360;
	public int anInt361;
	public int anInt362;
	public int anInt363;
	public int priority;
	public int anInt365;
	public static int anInt367;
}
