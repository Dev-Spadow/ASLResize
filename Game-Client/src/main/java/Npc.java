// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Npc extends Mob
{

	private Model method450()
	{
		if(super.emoteAnimation >= 0 && super.anInt1529 == 0)
		{
			int k = Animation.animations[super.emoteAnimation].primaryFrames[super.anInt1527];
			int i1 = -1;
			if(super.movementAnimation >= 0 && super.movementAnimation != super.idleAnimation)
				i1 = Animation.animations[super.movementAnimation].primaryFrames[super.anInt1518];
			return desc.method164(i1, k, Animation.animations[super.emoteAnimation].anIntArray357);
		}
		int l = -1;
		if(super.movementAnimation >= 0)
			l = Animation.animations[super.movementAnimation].primaryFrames[super.anInt1518];
		return desc.method164(-1, l, null);
	}

	public Model getRotatedModel()
	{
		if(desc == null)
			return null;
		Model model = method450();
		if(model == null)
			return null;
		super.height = model.modelBaseY;
		if(super.graphic != -1 && super.anInt1521 != -1)
		{
			Graphic graphic = Graphic.cache[super.graphic];
			Model model_1 = graphic.getModel();
			if(model_1 != null)
			{
				int j = graphic.animationSequence.primaryFrames[super.anInt1521];
				Model model_2 = new Model(true, Frame.noAnimationInProgress(j), false, model_1);
				model_2.translate(0, -super.anInt1524, 0);
				model_2.skin();
				model_2.applyTransform(j);
				model_2.faceGroups = null;
				model_2.vertexGroups = null;
				if(graphic.resizeXY != 128 || graphic.resizeZ != 128)
					model_2.scale(graphic.resizeXY, graphic.resizeXY, graphic.resizeZ);
				model_2.light(64 + graphic.modelBrightness, 850 + graphic.modelShadow, -30, -50, -30, true);
				Model aModel[] = {
						model, model_2
				};
				model = new Model(aModel);
			}
		}
		if(desc.size == 1)
			model.fits_on_single_square = true;
		return model;
	}

	public boolean isVisible()
	{
		return desc != null;
	}

	Npc()
	{
	}

	public NpcDefinition desc;
}
