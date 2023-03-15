// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Mob extends Renderable {

	public final void setPos(int i, int j, boolean flag)
	{
		if(emoteAnimation != -1 && Animation.animations[emoteAnimation].priority == 1)
			emoteAnimation = -1;
		if(!flag)
		{
			int k = i - pathX[0];
			int l = j - pathY[0];
			if(k >= -8 && k <= 8 && l >= -8 && l <= 8)
			{
				if(remainingPath < 9)
					remainingPath++;
				for(int i1 = remainingPath; i1 > 0; i1--)
				{
					pathX[i1] = pathX[i1 - 1];
					pathY[i1] = pathY[i1 - 1];
					pathRun[i1] = pathRun[i1 - 1];
				}

				pathX[0] = i;
				pathY[0] = j;
				pathRun[0] = false;
				return;
			}
		}
		remainingPath = 0;
		anInt1542 = 0;
		anInt1503 = 0;
		pathX[0] = i;
		pathY[0] = j;
		x = pathX[0] * 128 + size * 64;
		y = pathY[0] * 128 + size * 64;
	}

	public final void resetPath()
	{
		remainingPath = 0;
		anInt1542 = 0;
	}

	public final void updateHitData(int hitType, int hitDamage, int currentTime) {
		for (int hitPtr = 0; hitPtr < 4; hitPtr++)
			if (hitsLoopCycle[hitPtr] <= currentTime) {
				hitDamages[hitPtr] = hitDamage;
				hitMarkTypes[hitPtr] = hitType;
				hitsLoopCycle[hitPtr] = currentTime + 70;
				return;
			}
	}

	public final void moveInDir(boolean flag, int i)
	{
		int j = pathX[0];
		int k = pathY[0];
		if(i == 0)
		{
			j--;
			k++;
		}
		if(i == 1)
			k++;
		if(i == 2)
		{
			j++;
			k++;
		}
		if(i == 3)
			j--;
		if(i == 4)
			j++;
		if(i == 5)
		{
			j--;
			k--;
		}
		if(i == 6)
			k--;
		if(i == 7)
		{
			j++;
			k--;
		}
		if(emoteAnimation != -1 && Animation.animations[emoteAnimation].priority == 1)
			emoteAnimation = -1;
		if(remainingPath < 9)
			remainingPath++;
		for(int l = remainingPath; l > 0; l--)
		{
			pathX[l] = pathX[l - 1];
			pathY[l] = pathY[l - 1];
			pathRun[l] = pathRun[l - 1];
		}
			pathX[0] = j;
			pathY[0] = k;
			pathRun[0] = flag;
	}

	public int entScreenX;
	public int entScreenY;
	public final int index = -1;
	public boolean isVisible()
	{
		return false;
	}

	Mob()
	{
		pathX = new int[10];
		pathY = new int[10];
		interactingEntity = -1;
		degreesToTurn = 32;
		runAnimIndex = -1;
		height = 200;
		idleAnimation = -1;
		standTurnAnimIndex = -1;
		hitDamages = new int[4];
		hitMarkTypes = new int[4];
		hitsLoopCycle = new int[4];
		movementAnimation = -1;
		graphic = -1;
		emoteAnimation = -1;
		loopCycleStatus = -1000;
		textCycle = 100;
		size = 1;
		animationScretches = false;
		pathRun = new boolean[10];
		walkAnimIndex = -1;
		turn180AnimIndex = -1;
		turn90CWAnimIndex = -1;
		turn90CCWAnimIndex = -1;
	}

	public final int[] pathX;
	public final int[] pathY;
	public int interactingEntity;
	int anInt1503;
	int degreesToTurn;
	int runAnimIndex;
	public String textSpoken;
	public int height;
	public int turnDirection;
	int idleAnimation;
	int standTurnAnimIndex;
	int anInt1513;
	final int[] hitDamages;
	final int[] hitMarkTypes;
	final int[] hitsLoopCycle;
	int movementAnimation;
	int anInt1518;
	int anInt1519;
	int graphic;
	int anInt1521;
	int anInt1522;
	int anInt1523;
	int anInt1524;
	int remainingPath;
	public int emoteAnimation;
	int anInt1527;
	int anInt1528;
	int anInt1529;
	int anInt1530;
	int anInt1531;
	public int loopCycleStatus;
	public int currentHealth;
	public int maxHealth;
	int textCycle;
	int time;
	int anInt1538;
	int anInt1539;
	int size;
	boolean animationScretches;
	int anInt1542;
	int anInt1543;
	int anInt1544;
	int anInt1545;
	int anInt1546;
	int startForceMovement;
	int endForceMovement;
	int anInt1549;
	public int x;
	public int y;
	int anInt1552;
	final boolean[] pathRun;
	int walkAnimIndex;
	int turn180AnimIndex;
	int turn90CWAnimIndex;
	int turn90CCWAnimIndex;
}
