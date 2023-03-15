public final class Frame {

	public static java.util.Hashtable frameList = new java.util.Hashtable();

	public static void method528(int i) {
		i = 50000;
        aFrameArray635 = new Frame[i + 1];
        aBooleanArray643 = new boolean[i + 1];
        for(int j = 0; j < i + 1; j++)
            aBooleanArray643[j] = true;
    }

	public static int offset = 33600;

    public static void methodCustomAnimations(boolean flag, int file)
    {
		byte abyte0[];
		abyte0 = FileOperations.ReadFile(signlink.findcachedir()+"Anims/"+file+".dat");
		Animation.FrameStart[file] = offset;
        Buffer class30_sub2_sub2 = new Buffer(abyte0);
        class30_sub2_sub2.currentPosition = abyte0.length - 8;
        int i = class30_sub2_sub2.readUShort();
        int j = class30_sub2_sub2.readUShort();
        int k = class30_sub2_sub2.readUShort();
        int l = class30_sub2_sub2.readUShort();
        int i1 = 0;
        Buffer class30_sub2_sub2_1 = new Buffer(abyte0);
        class30_sub2_sub2_1.currentPosition = i1;
        i1 += i + 2;
        Buffer class30_sub2_sub2_2 = new Buffer(abyte0);
        class30_sub2_sub2_2.currentPosition = i1;
        i1 += j;
        Buffer class30_sub2_sub2_3 = new Buffer(abyte0);
        class30_sub2_sub2_3.currentPosition = i1;
        i1 += k;
        Buffer class30_sub2_sub2_4 = new Buffer(abyte0);
        class30_sub2_sub2_4.currentPosition = i1;
        i1 += l;
        Buffer class30_sub2_sub2_5 = new Buffer(abyte0);
        class30_sub2_sub2_5.currentPosition = i1;
        if(flag)
        {
            for(int j1 = 1; j1 > 0; j1++);
        }
        FrameBase frameBase = new FrameBase(class30_sub2_sub2_5);
        int k1 = class30_sub2_sub2_1.readUShort();
        int ai[] = new int[500];
        int ai1[] = new int[500];
        int ai2[] = new int[500];
        int ai3[] = new int[500];
        for(int l1 = 0; l1 < k1; l1++)
        {
            int i2 = class30_sub2_sub2_1.readUShort();
			i2 = offset;
			offset++;
            Frame frame = aFrameArray635[i2] = new Frame();
            frame.anInt636 = class30_sub2_sub2_4.readUnsignedByte();
            frame.base = frameBase;
            int j2 = class30_sub2_sub2_1.readUnsignedByte();
            int k2 = -1;
            int l2 = 0;
            for(int i3 = 0; i3 < j2; i3++)
            {
                int j3 = class30_sub2_sub2_2.readUnsignedByte();
                if(j3 > 0)
                {
                    if(frameBase.transformationType[i3] != 0)
                    {
                        for(int l3 = i3 - 1; l3 > k2; l3--)
                        {
                            if(frameBase.transformationType[l3] != 0)
                                continue;
                            ai[l2] = l3;
                            ai1[l2] = 0;
                            ai2[l2] = 0;
                            ai3[l2] = 0;
                            l2++;
                            break;
                        }

                    }
                    ai[l2] = i3;
                    char c = '\0';
                    if(frameBase.transformationType[i3] == 3)
                        c = '\200';
                    if((j3 & 1) != 0)
                        ai1[l2] = class30_sub2_sub2_3.readSmart();
                    else
                        ai1[l2] = c;
                    if((j3 & 2) != 0)
                        ai2[l2] = class30_sub2_sub2_3.readSmart();
                    else
                        ai2[l2] = c;
                    if((j3 & 4) != 0)
                        ai3[l2] = class30_sub2_sub2_3.readSmart();
                    else
                        ai3[l2] = c;
                    k2 = i3;
                    l2++;
                    if(frameBase.transformationType[i3] == 5)
                        aBooleanArray643[i2] = false;
                }
            }

            frame.transformationCount = l2;
            frame.transformationIndices = new int[l2];
            frame.transformX = new int[l2];
            frame.transformY = new int[l2];
            frame.transformZ = new int[l2];
            for(int k3 = 0; k3 < l2; k3++)
            {
                frame.transformationIndices[k3] = ai[k3];
                frame.transformX[k3] = ai1[k3];
                frame.transformY[k3] = ai2[k3];
               frame.transformZ[k3] = ai3[k3];
            }

        }

    }
	
	public static void method529(byte abyte0[], int fileId) {
		Buffer buffer = new Buffer(abyte0);
		buffer.currentPosition = abyte0.length - 12;
		int i = buffer.readInt();
		int j = buffer.readInt();
		int k = buffer.readInt();
		int i1 = 0;
		Buffer buffer_1 = new Buffer(abyte0);
		buffer_1.currentPosition = i1;
		i1 += i + 4;
		Buffer buffer_2 = new Buffer(abyte0);
		buffer_2.currentPosition = i1;
		i1 += j;
		Buffer buffer_3 = new Buffer(abyte0);
		buffer_3.currentPosition = i1;
		i1 += k;
		Buffer buffer_4 = new Buffer(abyte0);
		buffer_4.currentPosition = i1;
		FrameBase frameBase = new FrameBase(buffer_4);
		int k1 = buffer_1.readInt();
		int ai[] = new int[500];
		int ai1[] = new int[500];
		int ai2[] = new int[500];
		int ai3[] = new int[500];
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = buffer_1.readInt();
			Frame frame = new Frame();
			frameList.put((fileId << 16) + i2, frame);
			frame.base = frameBase;
			int j2 = buffer_1.readUnsignedByte();
			int k2 = -1;
			int l2 = 0;
			for (int i3 = 0; i3 < j2; i3++) {
				int j3 = buffer_2.readUnsignedByte();
				if (j3 > 0) {
					if (frameBase.transformationType[i3] != 0) {
						for (int l3 = i3 - 1; l3 > k2; l3--) {
							if (frameBase.transformationType[l3] != 0)
								continue;
							ai[l2] = l3;
							ai1[l2] = 0;
							ai2[l2] = 0;
							ai3[l2] = 0;
							l2++;
							break;
						}

					}
					ai[l2] = i3;
					char c = '\0';
					if (frameBase.transformationType[i3] == 3)
						c = '\200';
					if ((j3 & 1) != 0)
						ai1[l2] = buffer_3.readSmart();
					else
						ai1[l2] = c;
					if ((j3 & 2) != 0)
						ai2[l2] = buffer_3.readSmart();
					else
						ai2[l2] = c;
					if ((j3 & 4) != 0)
						ai3[l2] = buffer_3.readSmart();
					else
						ai3[l2] = c;
					k2 = i3;
					l2++;
				}
			}

			frame.transformationCount = l2;
			frame.transformationIndices = new int[l2];
			frame.transformX = new int[l2];
			frame.transformY = new int[l2];
			frame.transformZ = new int[l2];
			for (int k3 = 0; k3 < l2; k3++) {
				frame.transformationIndices[k3] = ai[k3];
				frame.transformX[k3] = ai1[k3];
				frame.transformY[k3] = ai2[k3];
				frame.transformZ[k3] = ai3[k3];
			}

		}

	}

	public static void nullLoader() {
		frameList = null;
	}

	public static Frame method531(int j) {
		try {
			int fileId = j >> 16;
			int k = j & 0xffff;
			Frame frame = (Frame) frameList.get(j);
			if (frame == null) {
				Client.instance.resourceProvider.provide(1, fileId);
				return null;
			} else
				return frame;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean noAnimationInProgress(int i) {
		return i == -1;
	}

	public Frame() {
	}

	public int anInt636;
	public FrameBase base;
	public int transformationCount;
	public int transformationIndices[];
	public int transformX[];
	public int transformY[];
	public int transformZ[];
	  private static boolean[] aBooleanArray643;
	  private static Frame[] aFrameArray635;

}