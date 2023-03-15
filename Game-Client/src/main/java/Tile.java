// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


import java.awt.*;

public final class Tile extends Linkable {

	public Tile(int i, int j, int k)
	{
		gameObjects = new GameObject[5];
		tiledObjectMasks = new int[5];
		anInt1310 = z1AnInt1307 = i;
		anInt1308 = j;
		anInt1309 = k;
	}

	int z1AnInt1307;
	final int anInt1308;
	final int anInt1309;
	final int anInt1310;
	public SimpleTile mySimpleTile;
	public ShapedTile myShapedTile;
	public WallObject wallObject;
	public WallDecoration wallDecoration;
	public GroundDecoration groundDecoration;
	public GroundItemTile groundItemTile;
	int gameObjectIndex;
	public final GameObject[] gameObjects;
	final int[] tiledObjectMasks;
	int totalTiledObjectMask;
	int logicHeight;
	boolean aBoolean1322;
	boolean aBoolean1323;
	boolean aBoolean1324;
	int someTileMask;
	int anInt1326;
	int anInt1327;
	int anInt1328;
	public Tile firstFloorTile;
	boolean tileMarked;
	Color markerColor;
}
