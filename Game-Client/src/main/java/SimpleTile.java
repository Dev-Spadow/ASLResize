// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class SimpleTile {

	private final int northEastColor;
	private final int northColor;
	private final int centerColor;
	private final int eastColor;
	private final int texture;
	private final boolean flat;
	private final int colorRGB;

	public SimpleTile(int northEastColor, int northColor, int centerColor, int eastColor, int texture, int colorRGB, boolean flat) {
		this.northEastColor = northEastColor;
		this.northColor = northColor;
		this.centerColor = centerColor;
		this.eastColor = eastColor;
		this.texture = texture;
		this.colorRGB = colorRGB;
		this.flat = flat;
	}

	public int getNorthEastColor() {
		return northEastColor;
	}

	public int getNorthColor() {
		return northColor;
	}

	public int getCenterColor() {
		return centerColor;
	}

	public int getEastColor() {
		return eastColor;
	}

	public int getTexture() {
		return texture;
	}

	public boolean isFlat() {
		return flat;
	}

	public int getColourRGB() {
		return colorRGB;
	}

}
