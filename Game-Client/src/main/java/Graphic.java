public final class Graphic {

	public static void unpackConfig(FileArchive fileArchive) {
		Buffer buffer = new Buffer(fileArchive.readFile("spotanim.dat"));
		int length = buffer.readUShort();
		if (cache == null)
			cache = new Graphic[length];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null)
				cache[j] = new Graphic();
			cache[j].anInt404 = j;
			cache[j].readValues(buffer);
		}

	}

	public void readValues(Buffer buffer) {
		do {
			int i = buffer.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				modelId = buffer.readUShort();
			else if (i == 2) {
				animationId = buffer.readUShort();
				if (Animation.animations != null)
					animationSequence = Animation.animations[animationId];
			} else if (i == 4)
				resizeXY = buffer.readUShort();
			else if (i == 5)
				resizeZ = buffer.readUShort();
			else if (i == 6)
				rotation = buffer.readUShort();
			else if (i == 7)
				modelBrightness = buffer.readUnsignedByte();
			else if (i == 8)
				modelShadow = buffer.readUnsignedByte();
			else if (i == 40) {
				int j = buffer.readUnsignedByte();
				for (int k = 0; k < j; k++) {
					originalModelColours[k] = buffer.readUShort();
					modifiedModelColours[k] = buffer.readUShort();
				}
			} else
				System.out.println("Error unrecognised spotanim config code: "
						+ i);
		} while (true);
	}

	public Model getModel() {
		Model model = (Model) aReferenceCache_415.get(anInt404);
		if (model != null)
			return model;
		model = Model.getModel(modelId);
		if (model == null)
			return null;
		for (int i = 0; i < 10; i++)
			if (originalModelColours[0] != 0)
				model.recolor(originalModelColours[i], modifiedModelColours[i]);

		aReferenceCache_415.put(model, anInt404);
		return model;
	}

	public Graphic() {
		anInt400 = 9;
		animationId = -1;
		originalModelColours = new int[10];
		modifiedModelColours = new int[10];
		resizeXY = 128;
		resizeZ = 128;
	}

	public final int anInt400;
	public static Graphic cache[];
	public int anInt404;
	public int modelId;
	public int animationId;
	public Animation animationSequence;
	public final int[] originalModelColours;
	public final int[] modifiedModelColours;
	public int resizeXY;
	public int resizeZ;
	public int rotation;
	public int modelBrightness;
	public int modelShadow;
	public static ReferenceCache aReferenceCache_415 = new ReferenceCache(30);

}
