// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Rasterizer2D extends Cacheable {

	public static void initDrawingArea(int height, int width, int pixels[]) {
		Rasterizer2D.pixels = pixels;
		Rasterizer2D.width = width;
		Rasterizer2D.height = height;
		setDrawingArea(height, 0, width, 0);
	}

	public static void defaultDrawingAreaSize()
	{
			leftX = 0;
			topY = 0;
			bottomX = width;
			bottomY = height;
			lastX = bottomX;
			viewportCenterX = bottomX / 2;
	}
	
	public static void transparentBox(int i, int j, int k, int l, int i1, int j1, int opac)
    {
	int j3 = 256 - opac;
        if(k < leftX)
        {
            i1 -= leftX - k;
            k = leftX;
        }
        if(j < topY)
        {
            i -= topY - j;
            j = topY;
        }
        if(k + i1 > bottomX)
            i1 = bottomX - k;
        if(j + i > bottomY)
            i = bottomY - j;
        int k1 = width - i1;
        int l1 = k + j * width;
        if(j1 != 0)
            viewportCenterY = -374;
        for(int i2 = -i; i2 < 0; i2++)
        {
            for(int j2 = -i1; j2 < 0; j2++){
				int i3 = pixels[l1];
                pixels[l1++] = ((l & 0xff00ff) * opac + (i3 & 0xff00ff) * j3 & 0xff00ff00) + ((l & 0xff00) * opac + (i3 & 0xff00) * j3 & 0xff0000) >> 8;
			}
            l1 += k1;
        }

    }

	public static void setDrawingArea(int i, int j, int k, int l)
	{
		if(j < 0)
			j = 0;
		if(l < 0)
			l = 0;
		if(k > width)
			k = width;
		if(i > height)
			i = height;
		leftX = j;
		topY = l;
		bottomX = k;
		bottomY = i;
		lastX = bottomX;
		viewportCenterX = bottomX / 2;
		viewportCenterY = bottomY / 2;
	}

	public static void clear()
	{
		int i = width * height;
		for(int j = 0; j < i; j++)
			pixels[j] = 0;

	}

	public static void drawPixels(int i, int j, int k, int l, int i1)
	{
		if(k < leftX)
		{
			i1 -= leftX - k;
			k = leftX;
		}
		if(j < topY)
		{
			i -= topY - j;
			j = topY;
		}
		if(k + i1 > bottomX)
			i1 = bottomX - k;
		if(j + i > bottomY)
			i = bottomY - j;
		int k1 = width - i1;
		int l1 = k + j * width;
		for(int i2 = -i; i2 < 0; i2++)
		{
			for(int j2 = -i1; j2 < 0; j2++)
				pixels[l1++] = l;

			l1 += k1;
		}

	}

	public static void fillPixels(int i, int j, int k, int l, int i1)
	{
		method339(i1, l, j, i);
		method339((i1 + k) - 1, l, j, i);
		method341(i1, l, k, i);
		method341(i1, l, k, (i + j) - 1);
	}

	public static void drawTransparentBoxOutline(int i, int j, int k, int l, int i1, int j1)
	{
		method340(l, i1, i, k, j1);
		method340(l, i1, (i + j) - 1, k, j1);
		if(j >= 3)
		{
			method342(l, j1, k, i + 1, j - 2);
			method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
		}
	}

	public static void method339(int i, int j, int k, int l)
	{
		if(i < topY || i >= bottomY)
			return;
		if(l < leftX)
		{
			k -= leftX - l;
			l = leftX;
		}
		if(l + k > bottomX)
			k = bottomX - l;
		int i1 = l + i * width;
		for(int j1 = 0; j1 < k; j1++)
			pixels[i1 + j1] = j;

	}

	private static void method340(int i, int j, int k, int l, int i1)
	{
		if(k < topY || k >= bottomY)
			return;
		if(i1 < leftX)
		{
			j -= leftX - i1;
			i1 = leftX;
		}
		if(i1 + j > bottomX)
			j = bottomX - i1;
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * width;
		for(int j3 = 0; j3 < j; j3++)
		{
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	public static void method341(int i, int j, int k, int l)
	{
		if(l < leftX || l >= bottomX)
			return;
		if(i < topY)
		{
			k -= topY - i;
			i = topY;
		}
		if(i + k > bottomY)
			k = bottomY - i;
		int j1 = l + i * width;
		for(int k1 = 0; k1 < k; k1++)
			pixels[j1 + k1 * width] = j;

	}

	private static void method342(int i, int j, int k, int l, int i1) {
		if(j < leftX || j >= bottomX)
			return;
		if(l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if(l + i1 > bottomY)
			i1 = bottomY - l;
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for(int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}
	}

	Rasterizer2D() {}

	public static int pixels[];
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int leftX;
	public static int bottomX;
	public static int lastX;
	public static int viewportCenterX;
	public static int viewportCenterY;

	/**
	 * Draws a 1 pixel thick box outline in a certain colour.
	 *
	 * @param leftX     The left edge X-Coordinate.
	 * @param topY      The top edge Y-Coordinate.
	 * @param width     The width.
	 * @param height    The height.
	 * @param rgbColour The RGB-Colour.
	 */
	public static void drawBoxOutline(int leftX, int topY, int width, int height, int rgbColour) {
		drawHorizontalLine(leftX, topY, width, rgbColour);
		drawHorizontalLine(leftX, (topY + height) - 1, width, rgbColour);
		drawVerticalLine(leftX, topY, height, rgbColour);
		drawVerticalLine((leftX + width) - 1, topY, height, rgbColour);
	}

	/**
	 * Draws a coloured horizontal line in the drawingArea.
	 *
	 * @param xPosition The start X-Position of the line.
	 * @param yPosition The Y-Position of the line.
	 * @param width     The width of the line.
	 * @param rgbColour The colour of the line.
	 */
	public static void drawHorizontalLine(int xPosition, int yPosition, int width, int rgbColour) {
		if (yPosition < topY || yPosition >= bottomY)
			return;
		if (xPosition < leftX) {
			width -= leftX - xPosition;
			xPosition = leftX;
		}
		if (xPosition + width > bottomX)
			width = bottomX - xPosition;
		int pixelIndex = xPosition + yPosition * Rasterizer2D.width;
		for (int i = 0; i < width; i++)
			pixels[pixelIndex + i] = rgbColour;
	}

	public static void drawHorizontalLine(int x, int y, int width, int color, int alpha) {
		if (y < topY || y >= bottomY)
			return;
		if (x < leftX) {
			width -= leftX - x;
			x = leftX;
		}
		if (x + width > bottomX)
			width = bottomX - x;
		int transparency = 256 - alpha;
		int red = (color >> 16 & 0xff) * alpha;
		int green = (color >> 8 & 0xff) * alpha;
		int blue = (color & 0xff) * alpha;
		int pixelIndex = x + y * Rasterizer2D.width;
		for (int j3 = 0; j3 < width; j3++) {
			int otherRed = (pixels[pixelIndex] >> 16 & 0xff) * transparency;
			int otherGreen = (pixels[pixelIndex] >> 8 & 0xff) * transparency;
			int otherBlue = (pixels[pixelIndex] & 0xff) * transparency;
			int transparentColour = ((red + otherRed >> 8) << 16) + ((green + otherGreen >> 8) << 8) + (blue + otherBlue >> 8);
			pixels[pixelIndex++] = transparentColour;
		}
	}

	/**
	 * Draws a coloured vertical line in the drawingArea.
	 *
	 * @param xPosition The X-Position of the line.
	 * @param yPosition The start Y-Position of the line.
	 * @param height    The height of the line.
	 * @param rgbColour The colour of the line.
	 */
	public static void drawVerticalLine(int xPosition, int yPosition, int height, int rgbColour) {
		if (xPosition < leftX || xPosition >= bottomX)
			return;
		if (yPosition < topY) {
			height -= topY - yPosition;
			yPosition = topY;
		}
		if (yPosition + height > bottomY)
			height = bottomY - yPosition;
		int pixelIndex = xPosition + yPosition * width;
		for (int rowIndex = 0; rowIndex < height; rowIndex++)
			pixels[pixelIndex + rowIndex * width] = rgbColour;
	}

	/**
	 * Draws a box filled with a certain colour.
	 *
	 * @param leftX     The left edge X-Coordinate of the box.
	 * @param topY      The top edge Y-Coordinate of the box.
	 * @param width     The width of the box.
	 * @param height    The height of the box.
	 * @param rgbColour The RGBColour of the box.
	 */
	public static void drawBox(int leftX, int topY, int width, int height, int rgbColour) {
		if (leftX < Rasterizer2D.leftX) {
			width -= Rasterizer2D.leftX - leftX;
			leftX = Rasterizer2D.leftX;
		}
		if (topY < Rasterizer2D.topY) {
			height -= Rasterizer2D.topY - topY;
			topY = Rasterizer2D.topY;
		}
		if (leftX + width > bottomX)
			width = bottomX - leftX;
		if (topY + height > bottomY)
			height = bottomY - topY;
		int leftOver = Rasterizer2D.width - width;
		int pixelIndex = leftX + topY * Rasterizer2D.width;
		for (int rowIndex = 0; rowIndex < height; rowIndex++) {
			for (int columnIndex = 0; columnIndex < width; columnIndex++)
				pixels[pixelIndex++] = rgbColour;
			pixelIndex += leftOver;
		}
	}

	/**
	 * Draws a transparent box.
	 *
	 * @param leftX     The left edge X-Coordinate of the box.
	 * @param topY      The top edge Y-Coordinate of the box.
	 * @param width     The box width.
	 * @param height    The box height.
	 * @param rgbColour The box colour.
	 * @param opacity   The opacity value ranging from 0 to 256.
	 */
	public static void drawTransparentBox(int leftX, int topY, int width, int height, int rgbColour, int opacity) {
		if (leftX < Rasterizer2D.leftX) {
			width -= Rasterizer2D.leftX - leftX;
			leftX = Rasterizer2D.leftX;
		}
		if (topY < Rasterizer2D.topY) {
			height -= Rasterizer2D.topY - topY;
			topY = Rasterizer2D.topY;
		}
		if (leftX + width > bottomX)
			width = bottomX - leftX;
		if (topY + height > bottomY)
			height = bottomY - topY;
		int transparency = 256 - opacity;
		int red = (rgbColour >> 16 & 0xff) * opacity;
		int green = (rgbColour >> 8 & 0xff) * opacity;
		int blue = (rgbColour & 0xff) * opacity;
		int leftOver = Rasterizer2D.width - width;
		int pixelIndex = leftX + topY * Rasterizer2D.width;
		for (int rowIndex = 0; rowIndex < height; rowIndex++) {
			for (int columnIndex = 0; columnIndex < width; columnIndex++) {
				int otherRed = (pixels[pixelIndex] >> 16 & 0xff) * transparency;
				int otherGreen = (pixels[pixelIndex] >> 8 & 0xff) * transparency;
				int otherBlue = (pixels[pixelIndex] & 0xff) * transparency;
				int transparentColour = ((red + otherRed >> 8) << 16) + ((green + otherGreen >> 8) << 8) + (blue + otherBlue >> 8);
				pixels[pixelIndex++] = transparentColour;
			}
			pixelIndex += leftOver;
		}
	}

	public static void fillGradientRectangle(int x, int y, int w, int h, int startColour, int endColour) {
		int k1 = 0;
		int l1 = 0x10000 / h;
		if (x < leftX) {
			w -= leftX - x;
			x = leftX;
		}
		if (y < topY) {
			k1 += (topY - y) * l1;
			h -= topY - y;
			y = topY;
		}
		if (x + w > bottomX)
			w = bottomX - x;
		if (y + h > bottomY)
			h = bottomY - y;
		int lineGap = width - w;
		int pixelOffset = x + y * width;
		for (int yi = -h; yi < 0; yi++) {
			int blendAmount = 0x10000 - k1 >> 8;
			int blendInverse = k1 >> 8;
			int combinedColour = ((startColour & 0xff00ff) * blendAmount + (endColour & 0xff00ff) * blendInverse & 0xff00ff00) + ((startColour & 0xff00) * blendAmount + (endColour & 0xff00) * blendInverse & 0xff0000) >>> 8;
			int alpha = ((((startColour >> 24) & 0xff) * blendAmount + ((endColour >> 24) & 0xff) * blendInverse) >>> 8) + 5;
			for (int index = -w; index < 0; index++) {
				int backingPixel = pixels[pixelOffset];
				pixels[pixelOffset++] = ((backingPixel & 0xff00ff) * (256 - alpha) + (combinedColour & 0xff00ff) * alpha & 0xff00ff00) + ((backingPixel & 0xff00) * (256 - alpha) + (combinedColour & 0xff00) * alpha & 0xff0000) >>> 8;
			}
			pixelOffset += lineGap;
			k1 += l1;
		}
	}

	public static void fillRectangle(int x, int y, int w, int h, int color, int alpha) {
		if (x < leftX) {
			w -= leftX - x;
			x = leftX;
		}
		if (y < topY) {
			h -= topY - y;
			y = topY;
		}
		if (x + w > bottomX)
			w = bottomX - x;
		if (y + h > bottomY)
			h = bottomY - y;
		int a2 = 256 - alpha;
		int r1 = (color >> 16 & 0xff) * alpha;
		int g1 = (color >> 8 & 0xff) * alpha;
		int b1 = (color & 0xff) * alpha;
		int k3 = Rasterizer2D.width - w;
		int pixel = x + y * Rasterizer2D.width;
		for (int i4 = 0; i4 < h; i4++) {
			for (int index = -w; index < 0; index++) {
				int r2 = (pixels[pixel] >> 16 & 0xff) * a2;
				int g2 = (pixels[pixel] >> 8 & 0xff) * a2;
				int b2 = (pixels[pixel] & 0xff) * a2;
				int rgb = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
				pixels[pixel++] = rgb;
			}
			pixel += k3;
		}
	}

	/**
	 * Draws a transparent coloured vertical line in the drawingArea.
	 *
	 * @param xPosition The X-Position of the line.
	 * @param yPosition The start Y-Position of the line.
	 * @param height    The height of the line.
	 * @param rgbColour The colour of the line.
	 * @param opacity   The opacity value ranging from 0 to 256.
	 */
	public static void drawTransparentVerticalLine(int xPosition, int yPosition, int height, int rgbColour, int opacity) {
		if (xPosition < leftX || xPosition >= bottomX) {
			return;
		}
		if (yPosition < topY) {
			height -= topY - yPosition;
			yPosition = topY;
		}
		if (yPosition + height > bottomY) {
			height = bottomY - yPosition;
		}
		final int transparency = 256 - opacity;
		final int red = (rgbColour >> 16 & 0xff) * opacity;
		final int green = (rgbColour >> 8 & 0xff) * opacity;
		final int blue = (rgbColour & 0xff) * opacity;
		int pixelIndex = xPosition + yPosition * width;
		for (int i = 0; i < height; i++) {
			final int otherRed = (pixels[pixelIndex] >> 16 & 0xff) * transparency;
			final int otherGreen = (pixels[pixelIndex] >> 8 & 0xff) * transparency;
			final int otherBlue = (pixels[pixelIndex] & 0xff) * transparency;
			final int transparentColour = (red + otherRed >> 8 << 16) + (green + otherGreen >> 8 << 8) + (blue + otherBlue >> 8);
			pixels[pixelIndex] = transparentColour;
			pixelIndex += width;
		}
	}
}
