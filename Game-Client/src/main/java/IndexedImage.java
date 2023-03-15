// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class IndexedImage extends Rasterizer2D {

	public byte palettePixels[];
	public final int[] palette;
	public int width;
	public int height;
	public int drawOffsetX;
	public int drawOffsetY;
	public int resizeWidth;
	private int resizeHeight;

	public IndexedImage(FileArchive fileArchive, String s, int i)
	{
		Buffer buffer = new Buffer(fileArchive.readFile(s + ".dat"));
		Buffer buffer_1 = new Buffer(fileArchive.readFile("index.dat"));
		buffer_1.currentPosition = buffer.readUShort();
		resizeWidth = buffer_1.readUShort();
		resizeHeight = buffer_1.readUShort();
		int j = buffer_1.readUnsignedByte();
		palette = new int[j];
		for(int k = 0; k < j - 1; k++)
			palette[k + 1] = buffer_1.readTriByte();

		for(int l = 0; l < i; l++)
		{
			buffer_1.currentPosition += 2;
			buffer.currentPosition += buffer_1.readUShort() * buffer_1.readUShort();
			buffer_1.currentPosition++;
		}

		drawOffsetX = buffer_1.readUnsignedByte();
		drawOffsetY = buffer_1.readUnsignedByte();
		width = buffer_1.readUShort();
		height = buffer_1.readUShort();
		int i1 = buffer_1.readUnsignedByte();
		int j1 = width * height;
		palettePixels = new byte[j1];
		if(i1 == 0)
		{
			for(int k1 = 0; k1 < j1; k1++)
				palettePixels[k1] = buffer.readSignedByte();

			return;
		}
		if(i1 == 1)
		{
			for(int l1 = 0; l1 < width; l1++)
			{
				for(int i2 = 0; i2 < height; i2++)
					palettePixels[l1 + i2 * width] = buffer.readSignedByte();

			}

		}
	}

	public void downscale()
	{
		resizeWidth /= 2;
		resizeHeight /= 2;
		byte abyte0[] = new byte[resizeWidth * resizeHeight];
		int i = 0;
		for(int j = 0; j < height; j++)
		{
			for(int k = 0; k < width; k++)
				abyte0[(k + drawOffsetX >> 1) + (j + drawOffsetY >> 1) * resizeWidth] = palettePixels[i++];

		}

		palettePixels = abyte0;
		width = resizeWidth;
		height = resizeHeight;
		drawOffsetX = 0;
			drawOffsetY = 0;
	}

	public void resize()
	{
		if(width == resizeWidth && height == resizeHeight)
			return;
		byte raster[] = new byte[resizeWidth * resizeHeight];
		int i = 0;
		for(int j = 0; j < height; j++)
		{
			for(int k = 0; k < width; k++)
				raster[k + drawOffsetX + (j + drawOffsetY) * resizeWidth] = palettePixels[i++];

		}

		palettePixels = raster;
		width = resizeWidth;
		height = resizeHeight;
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

	public void flipHorizontally()
	{
		byte abyte0[] = new byte[width * height];
		int j = 0;
		for(int k = 0; k < height; k++)
		{
			for(int l = width - 1; l >= 0; l--)
				abyte0[j++] = palettePixels[l + k * width];

		}

		palettePixels = abyte0;
		drawOffsetX = resizeWidth - width - drawOffsetX;
	}

	public void flipVertically()
	{
		byte abyte0[] = new byte[width * height];
		int i = 0;
		for(int j = height - 1; j >= 0; j--)
		{
			for(int k = 0; k < width; k++)
				abyte0[i++] = palettePixels[k + j * width];

		}

		palettePixels = abyte0;
		drawOffsetY = resizeHeight - height - drawOffsetY;
	}

	public void offsetColor(int i, int j, int k)
	{
		for(int i1 = 0; i1 < palette.length; i1++)
		{
			int j1 = palette[i1] >> 16 & 0xff;
			j1 += i;
			if(j1 < 0)
				j1 = 0;
			else
			if(j1 > 255)
				j1 = 255;
			int k1 = palette[i1] >> 8 & 0xff;
			k1 += j;
			if(k1 < 0)
				k1 = 0;
			else
			if(k1 > 255)
				k1 = 255;
			int l1 = palette[i1] & 0xff;
			l1 += k;
			if(l1 < 0)
				l1 = 0;
			else
			if(l1 > 255)
				l1 = 255;
			palette[i1] = (j1 << 16) + (k1 << 8) + l1;
		}
	}

	public void draw(int i, int k)
	{
		i += drawOffsetX;
		k += drawOffsetY;
		int l = i + k * Rasterizer2D.width;
		int i1 = 0;
		int j1 = height;
		int k1 = width;
		int l1 = Rasterizer2D.width - k1;
		int i2 = 0;
		if(k < Rasterizer2D.topY)
		{
			int j2 = Rasterizer2D.topY - k;
			j1 -= j2;
			k = Rasterizer2D.topY;
			i1 += j2 * k1;
			l += j2 * Rasterizer2D.width;
		}
		if(k + j1 > Rasterizer2D.bottomY)
			j1 -= (k + j1) - Rasterizer2D.bottomY;
		if(i < Rasterizer2D.leftX)
		{
			int k2 = Rasterizer2D.leftX - i;
			k1 -= k2;
			i = Rasterizer2D.leftX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if(i + k1 > Rasterizer2D.bottomX)
		{
			int l2 = (i + k1) - Rasterizer2D.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if(!(k1 <= 0 || j1 <= 0))
		{
			draw(j1, Rasterizer2D.pixels, palettePixels, l1, l, k1, i1, palette, i2);
		}
	}

	private void draw(int i, int ai[], byte abyte0[], int j, int k, int l,
					  int i1, int ai1[], int j1)
	{
		int k1 = -(l >> 2);
		l = -(l & 3);
		for(int l1 = -i; l1 < 0; l1++)
		{
			for(int i2 = k1; i2 < 0; i2++)
			{
				byte byte1 = abyte0[i1++];
				if(byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = abyte0[i1++];
				if(byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = abyte0[i1++];
				if(byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = abyte0[i1++];
				if(byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
			}

			for(int j2 = l; j2 < 0; j2++)
			{
				byte byte2 = abyte0[i1++];
				if(byte2 != 0)
					ai[k++] = ai1[byte2 & 0xff];
				else
					k++;
			}

			k += j;
			i1 += j1;
		}

	}
}
