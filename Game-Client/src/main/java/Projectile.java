// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Projectile extends Renderable {

	public Projectile(int i, int j, int l, int i1, int j1, int k1,
					  int l1)
	{
		aBoolean1567 = false;
		projectileGFX = Graphic.cache[i1];
		anInt1560 = i;
		anInt1561 = l1;
		anInt1562 = k1;
		anInt1563 = j1;
		anInt1564 = j + l;
			aBoolean1567 = false;
	}

	public Model getRotatedModel()
	{
		Model model = projectileGFX.getModel();
		if(model == null)
			return null;
		int j = projectileGFX.animationSequence.primaryFrames[anInt1569];
		Model model_1 = new Model(true, Frame.noAnimationInProgress(j), false, model);
		if(!aBoolean1567)
		{
			model_1.skin();
			model_1.applyTransform(j);
			model_1.faceGroups = null;
			model_1.vertexGroups = null;
		}
		if(projectileGFX.resizeXY != 128 || projectileGFX.resizeZ != 128)
			model_1.scale(projectileGFX.resizeXY, projectileGFX.resizeXY, projectileGFX.resizeZ);
		if(projectileGFX.rotation != 0)
		{
			if(projectileGFX.rotation == 90)
				model_1.rotate90Degrees();
			if(projectileGFX.rotation == 180)
			{
				model_1.rotate90Degrees();
				model_1.rotate90Degrees();
			}
			if(projectileGFX.rotation == 270)
			{
				model_1.rotate90Degrees();
				model_1.rotate90Degrees();
				model_1.rotate90Degrees();
			}
		}
		model_1.light(64 + projectileGFX.modelBrightness, 850 + projectileGFX.modelShadow, -30, -50, -30, true);
		return model_1;
	}

	public void method454(int i)
	{
		for(anInt1570 += i; anInt1570 > projectileGFX.animationSequence.method258(anInt1569);)
		{
			anInt1570 -= projectileGFX.animationSequence.method258(anInt1569) + 1;
			anInt1569++;
			if(anInt1569 >= projectileGFX.animationSequence.anInt352 && (anInt1569 < 0 || anInt1569 >= projectileGFX.animationSequence.anInt352))
			{
				anInt1569 = 0;
				aBoolean1567 = true;
			}
		}

	}

	public final int anInt1560;
	public final int anInt1561;
	public final int anInt1562;
	public final int anInt1563;
	public final int anInt1564;
	public boolean aBoolean1567;
	private final Graphic projectileGFX;
	private int anInt1569;
	private int anInt1570;
}
