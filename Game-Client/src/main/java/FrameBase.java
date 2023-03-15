public final class FrameBase {

	public FrameBase(Buffer buffer) {
		int anInt341 = buffer.readUnsignedByte();
		transformationType = new int[anInt341];
		skinList = new int[anInt341][];
		for (int j = 0; j < anInt341; j++)
			transformationType[j] = buffer.readUnsignedByte();

		for (int j = 0; j < anInt341; j++)
			skinList[j] = new int[buffer.readUnsignedByte()];

		for (int j = 0; j < anInt341; j++)
			for (int l = 0; l < skinList[j].length; l++)
				skinList[j][l] = buffer.readUnsignedByte();

	}

	public final int[] transformationType;
	public final int[][] skinList;
}
