public final class Rasterizer3D extends Rasterizer2D {

	public static final int[] anIntArray1469;
	public static boolean lowMem = true;
	public static boolean textureOutOfDrawingBounds;
	public static boolean restrict_edges = true;
	public static int alpha;
	public static int originViewX;
	public static int originViewY;
	public static int SINE[];
	public static int COSINE[];
	public static int scanOffsets[];
	public static int lastTextureRetrievalCount;
	public static int hslToRgb[] = new int[0x10000];
	private static int textureAmount = 50;
	public static IndexedImage textures[] = new IndexedImage[textureAmount];
	public static int textureLastUsed[] = new int[textureAmount];
	private static boolean aBoolean1463;
	private static int[] shadowDecay;
	private static int textureCount;
	private static boolean[] textureIsTransparant = new boolean[textureAmount];
	private static int[] averageTextureColours = new int[textureAmount];
	private static int textureRequestBufferPointer;
	private static int[][] textureRequestPixelBuffer;
	private static int[][] texturesPixelBuffer = new int[textureAmount][];
	private static int[][] currentPalette = new int[textureAmount][];
	private static float[] depthBuffer;


	static {
		shadowDecay = new int[512];
		anIntArray1469 = new int[2048];
		SINE = new int[2048];
		COSINE = new int[2048];
		for (int i = 1; i < 512; i++) {
			shadowDecay[i] = 32768 / i;
		}
		for (int j = 1; j < 2048; j++) {
			anIntArray1469[j] = 0x10000 / j;
		}
		for (int k = 0; k < 2048; k++) {
			SINE[k] = (int) (65536D * Math.sin((double) k * 0.0030679614999999999D));
			COSINE[k] = (int) (65536D * Math.cos((double) k * 0.0030679614999999999D));
		}
	}

	public static void clear() {
		shadowDecay = null;
		shadowDecay = null;
		SINE = null;
		COSINE = null;
		scanOffsets = null;
		textures = null;
		textureIsTransparant = null;
		averageTextureColours = null;
		textureRequestPixelBuffer = null;
		texturesPixelBuffer = null;
		textureLastUsed = null;
		hslToRgb = null;
		currentPalette = null;
	}

	public static void clearDepthBuffer() {
		if (depthBuffer == null || depthBuffer.length != pixels.length) {
			depthBuffer = new float[pixels.length];
		}
		for (int i = 0; i < depthBuffer.length; i++) {
			depthBuffer[i] = Float.MAX_VALUE;
		}
	}

	public static void useViewport() {
		scanOffsets = new int[Rasterizer2D.height];

		for (int j = 0; j < Rasterizer2D.height; j++) {
			scanOffsets[j] = Rasterizer2D.width * j;
		}

		originViewX = Rasterizer2D.width / 2;
		originViewY = Rasterizer2D.height / 2;
	}

	public static void reposition(int width, int length) {
		scanOffsets = new int[length];
		for (int x = 0; x < length; x++) {
			scanOffsets[x] = width * x;
		}
		originViewX = width / 2;
		originViewY = length / 2;
	}

	public static void clearTextureCache() {
		textureRequestPixelBuffer = null;
		for (int i = 0; i < textureAmount; i++)
			texturesPixelBuffer[i] = null;
	}

	public static void initiateRequestBuffers() {
		if (textureRequestPixelBuffer == null) {
			textureRequestBufferPointer = 20;
			if (lowMem)
				textureRequestPixelBuffer = new int[textureRequestBufferPointer][16384];
			else
				textureRequestPixelBuffer = new int[textureRequestBufferPointer][0x10000];
			for (int i = 0; i < textureAmount; i++)
				texturesPixelBuffer[i] = null;
		}
	}

	public static void loadTextures(FileArchive archive) {
		textureCount = 0;
		for (int index = 0; index < textureAmount; index++) {
			try {
				textures[index] = new IndexedImage(archive, String.valueOf(index), 0);
				if (lowMem && textures[index].resizeWidth == 128) {
					textures[index].downscale();
				} else {
					textures[index].resize();
				}
				textureCount++;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static int getOverallColour(int textureId) {
		if (averageTextureColours[textureId] != 0)
			return averageTextureColours[textureId];
		int totalRed = 0;
		int totalGreen = 0;
		int totalBlue = 0;
		int colourCount = currentPalette[textureId].length;
		for (int ptr = 0; ptr < colourCount; ptr++) {
			totalRed += currentPalette[textureId][ptr] >> 16 & 0xff;
			totalGreen += currentPalette[textureId][ptr] >> 8 & 0xff;
			totalBlue += currentPalette[textureId][ptr] & 0xff;
		}

		int avgPaletteColour = (totalRed / colourCount << 16) + (totalGreen / colourCount << 8) + totalBlue / colourCount;
		avgPaletteColour = adjustBrightness(avgPaletteColour, 1.3999999999999999D);
		if (avgPaletteColour == 0)
			avgPaletteColour = 1;
		averageTextureColours[textureId] = avgPaletteColour;
		return avgPaletteColour;
	}

	public static void requestTextureUpdate(int textureId) {
		if (texturesPixelBuffer[textureId] == null) {
			return;
		}
		textureRequestPixelBuffer[textureRequestBufferPointer++] = texturesPixelBuffer[textureId];
		texturesPixelBuffer[textureId] = null;
	}

	private static int[] getTexturePixels(int textureId) {
		textureLastUsed[textureId] = lastTextureRetrievalCount++;
		if (texturesPixelBuffer[textureId] != null)
			return texturesPixelBuffer[textureId];
		int texturePixels[];
		if (textureRequestBufferPointer > 0) {
			texturePixels = textureRequestPixelBuffer[--textureRequestBufferPointer];
			textureRequestPixelBuffer[textureRequestBufferPointer] = null;
		} else {
			int lastUsed = 0;
			int target = -1;
			for (int l = 0; l < textureCount; l++)
				if (texturesPixelBuffer[l] != null && (textureLastUsed[l] < lastUsed || target == -1)) {
					lastUsed = textureLastUsed[l];
					target = l;
				}

			texturePixels = texturesPixelBuffer[target];
			texturesPixelBuffer[target] = null;
		}
		texturesPixelBuffer[textureId] = texturePixels;
		IndexedImage background = textures[textureId];
		int texturePalette[] = currentPalette[textureId];
		if (lowMem) {
			textureIsTransparant[textureId] = false;
			for (int i1 = 0; i1 < 4096; i1++) {
				int colour = texturePixels[i1] = texturePalette[background.palettePixels[i1]] & 0xf8f8ff;
				if (colour == 0)
					textureIsTransparant[textureId] = true;
				texturePixels[4096 + i1] = colour - (colour >>> 3) & 0xf8f8ff;
				texturePixels[8192 + i1] = colour - (colour >>> 2) & 0xf8f8ff;
				texturePixels[12288 + i1] = colour - (colour >>> 2) - (colour >>> 3) & 0xf8f8ff;
			}

		} else {
			if (background.width == 64) {
				for (int x = 0; x < 128; x++) {
					for (int y = 0; y < 128; y++)
						texturePixels[y + (x << 7)] = texturePalette[background.palettePixels[(y >> 1) + ((x >> 1) << 6)]];
				}
			} else {
				for (int i = 0; i < 16384; i++)
					texturePixels[i] = texturePalette[background.palettePixels[i]];
			}
			textureIsTransparant[textureId] = false;
			for (int i = 0; i < 16384; i++) {
				texturePixels[i] &= 0xf8f8ff;
				int colour = texturePixels[i];
				if (colour == 0)
					textureIsTransparant[textureId] = true;
				texturePixels[16384 + i] = colour - (colour >>> 3) & 0xf8f8ff;
				texturePixels[32768 + i] = colour - (colour >>> 2) & 0xf8f8ff;
				texturePixels[49152 + i] = colour - (colour >>> 2) - (colour >>> 3) & 0xf8f8ff;
			}

		}
		return texturePixels;
	}

	public static void setBrightness(double brightness) {
		int j = 0;
		for (int k = 0; k < 512; k++) {
			double d1 = (double) (k / 8) / 64D + 0.0078125D;
			double d2 = (double) (k & 7) / 8D + 0.0625D;
			for (int k1 = 0; k1 < 128; k1++) {
				double d3 = (double) k1 / 128D;
				double r = d3;
				double g = d3;
				double b = d3;
				if (d2 != 0.0D) {
					double d7;
					if (d3 < 0.5D)
						d7 = d3 * (1.0D + d2);
					else
						d7 = (d3 + d2) - d3 * d2;
					double d8 = 2D * d3 - d7;
					double d9 = d1 + 0.33333333333333331D;
					if (d9 > 1.0D)
						d9--;
					double d10 = d1;
					double d11 = d1 - 0.33333333333333331D;
					if (d11 < 0.0D)
						d11++;
					if (6D * d9 < 1.0D)
						r = d8 + (d7 - d8) * 6D * d9;
					else if (2D * d9 < 1.0D)
						r = d7;
					else if (3D * d9 < 2D)
						r = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
					else
						r = d8;
					if (6D * d10 < 1.0D)
						g = d8 + (d7 - d8) * 6D * d10;
					else if (2D * d10 < 1.0D)
						g = d7;
					else if (3D * d10 < 2D)
						g = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
					else
						g = d8;
					if (6D * d11 < 1.0D)
						b = d8 + (d7 - d8) * 6D * d11;
					else if (2D * d11 < 1.0D)
						b = d7;
					else if (3D * d11 < 2D)
						b = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
					else
						b = d8;
				}
				int byteR = (int) (r * 256D);
				int byteG = (int) (g * 256D);
				int byteB = (int) (b * 256D);
				int rgb = (byteR << 16) + (byteG << 8) + byteB;
				rgb = adjustBrightness(rgb, brightness);
				if (rgb == 0)
					rgb = 1;
				hslToRgb[j++] = rgb;
			}

		}

		for (int textureId = 0; textureId < textureAmount; textureId++)
			if (textures[textureId] != null) {
				int originalPalette[] = textures[textureId].palette;
				currentPalette[textureId] = new int[originalPalette.length];
				for (int colourId = 0; colourId < originalPalette.length; colourId++) {
					currentPalette[textureId][colourId] = adjustBrightness(originalPalette[colourId], brightness);
					if ((currentPalette[textureId][colourId] & 0xf8f8ff) == 0 && colourId != 0)
						currentPalette[textureId][colourId] = 1;
				}

			}

		for (int textureId = 0; textureId < textureAmount; textureId++)
			requestTextureUpdate(textureId);

	}

	private static int adjustBrightness(int rgb, double intensity) {
		double r = (double) (rgb >> 16) / 256D;
		double g = (double) (rgb >> 8 & 0xff) / 256D;
		double b = (double) (rgb & 0xff) / 256D;
		r = Math.pow(r, intensity);
		g = Math.pow(g, intensity);
		b = Math.pow(b, intensity);
		int r_byte = (int) (r * 256D);
		int g_byte = (int) (g * 256D);
		int b_byte = (int) (b * 256D);
		return (r_byte << 16) + (g_byte << 8) + b_byte;
	}

	public static void drawGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3) {
		int j2 = 0;
		int k2 = 0;
		if (y2 != y1) {
			j2 = (x2 - x1 << 16) / (y2 - y1);
			k2 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int l2 = 0;
		int i3 = 0;
		if (y3 != y2) {
			l2 = (x3 - x2 << 16) / (y3 - y2);
			i3 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int j3 = 0;
		int k3 = 0;
		if (y3 != y1) {
			j3 = (x1 - x3 << 16) / (y1 - y3);
			k3 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.bottomY)
				return;
			if (y2 > Rasterizer2D.bottomY)
				y2 = Rasterizer2D.bottomY;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if (y1 < 0) {
					x3 -= j3 * y1;
					x1 -= j2 * y1;
					hsl3 -= k3 * y1;
					hsl1 -= k2 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if (y2 < 0) {
					x2 -= l2 * y2;
					hsl2 -= i3 * y2;
					y2 = 0;
				}
				if (y1 != y2 && j3 < j2 || y1 == y2 && j3 > l2) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
						drawGouraudScanline(Rasterizer2D.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
						x3 += j3;
						x1 += j2;
						hsl3 += k3;
						hsl1 += k2;
					}

					while (--y3 >= 0) {
						drawGouraudScanline(Rasterizer2D.pixels, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
						x3 += j3;
						x2 += l2;
						hsl3 += k3;
						hsl2 += i3;
						y1 += Rasterizer2D.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
					drawGouraudScanline(Rasterizer2D.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
					x3 += j3;
					x1 += j2;
					hsl3 += k3;
					hsl1 += k2;
				}

				while (--y3 >= 0) {
					drawGouraudScanline(Rasterizer2D.pixels, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
					x3 += j3;
					x2 += l2;
					hsl3 += k3;
					hsl2 += i3;
					y1 += Rasterizer2D.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if (y1 < 0) {
				x2 -= j3 * y1;
				x1 -= j2 * y1;
				hsl2 -= k3 * y1;
				hsl1 -= k2 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if (y3 < 0) {
				x3 -= l2 * y3;
				hsl3 -= i3 * y3;
				y3 = 0;
			}
			if (y1 != y3 && j3 < j2 || y1 == y3 && l2 > j2) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
					drawGouraudScanline(Rasterizer2D.pixels, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x2 += j3;
					x1 += j2;
					hsl2 += k3;
					hsl1 += k2;
				}

				while (--y2 >= 0) {
					drawGouraudScanline(Rasterizer2D.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
					x3 += l2;
					x1 += j2;
					hsl3 += i3;
					hsl1 += k2;
					y1 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
				drawGouraudScanline(Rasterizer2D.pixels, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
				x2 += j3;
				x1 += j2;
				hsl2 += k3;
				hsl1 += k2;
			}

			while (--y2 >= 0) {
				drawGouraudScanline(Rasterizer2D.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
				x3 += l2;
				x1 += j2;
				hsl3 += i3;
				hsl1 += k2;
				y1 += Rasterizer2D.width;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.bottomY)
				return;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y1 > Rasterizer2D.bottomY)
				y1 = Rasterizer2D.bottomY;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if (y2 < 0) {
					x1 -= j2 * y2;
					x2 -= l2 * y2;
					hsl1 -= k2 * y2;
					hsl2 -= i3 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if (y3 < 0) {
					x3 -= j3 * y3;
					hsl3 -= k3 * y3;
					y3 = 0;
				}
				if (y2 != y3 && j2 < l2 || y2 == y3 && j2 > j3) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
						drawGouraudScanline(Rasterizer2D.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
						x1 += j2;
						x2 += l2;
						hsl1 += k2;
						hsl2 += i3;
					}

					while (--y1 >= 0) {
						drawGouraudScanline(Rasterizer2D.pixels, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
						x1 += j2;
						x3 += j3;
						hsl1 += k2;
						hsl3 += k3;
						y2 += Rasterizer2D.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
					drawGouraudScanline(Rasterizer2D.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x1 += j2;
					x2 += l2;
					hsl1 += k2;
					hsl2 += i3;
				}

				while (--y1 >= 0) {
					drawGouraudScanline(Rasterizer2D.pixels, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
					x1 += j2;
					x3 += j3;
					hsl1 += k2;
					hsl3 += k3;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if (y2 < 0) {
				x3 -= j2 * y2;
				x2 -= l2 * y2;
				hsl3 -= k2 * y2;
				hsl2 -= i3 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= j3 * y1;
				hsl1 -= k3 * y1;
				y1 = 0;
			}
			if (j2 < l2) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
					drawGouraudScanline(Rasterizer2D.pixels, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
					x3 += j2;
					x2 += l2;
					hsl3 += k2;
					hsl2 += i3;
				}

				while (--y3 >= 0) {
					drawGouraudScanline(Rasterizer2D.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
					x1 += j3;
					x2 += l2;
					hsl1 += k3;
					hsl2 += i3;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
				drawGouraudScanline(Rasterizer2D.pixels, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
				x3 += j2;
				x2 += l2;
				hsl3 += k2;
				hsl2 += i3;
			}

			while (--y3 >= 0) {
				drawGouraudScanline(Rasterizer2D.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
				x1 += j3;
				x2 += l2;
				hsl1 += k3;
				hsl2 += i3;
				y2 += Rasterizer2D.width;
			}
			return;
		}
		if (y3 >= Rasterizer2D.bottomY)
			return;
		if (y1 > Rasterizer2D.bottomY)
			y1 = Rasterizer2D.bottomY;
		if (y2 > Rasterizer2D.bottomY)
			y2 = Rasterizer2D.bottomY;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if (y3 < 0) {
				x2 -= l2 * y3;
				x3 -= j3 * y3;
				hsl2 -= i3 * y3;
				hsl3 -= k3 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= j2 * y1;
				hsl1 -= k2 * y1;
				y1 = 0;
			}
			if (l2 < j3) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
					drawGouraudScanline(Rasterizer2D.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
					x2 += l2;
					x3 += j3;
					hsl2 += i3;
					hsl3 += k3;
				}

				while (--y2 >= 0) {
					drawGouraudScanline(Rasterizer2D.pixels, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7);
					x2 += l2;
					x1 += j2;
					hsl2 += i3;
					hsl1 += k2;
					y3 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
				drawGouraudScanline(Rasterizer2D.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
				x2 += l2;
				x3 += j3;
				hsl2 += i3;
				hsl3 += k3;
			}

			while (--y2 >= 0) {
				drawGouraudScanline(Rasterizer2D.pixels, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7);
				x2 += l2;
				x1 += j2;
				hsl2 += i3;
				hsl1 += k2;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if (y3 < 0) {
			x1 -= l2 * y3;
			x3 -= j3 * y3;
			hsl1 -= i3 * y3;
			hsl3 -= k3 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if (y2 < 0) {
			x2 -= j2 * y2;
			hsl2 -= k2 * y2;
			y2 = 0;
		}
		if (l2 < j3) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
				drawGouraudScanline(Rasterizer2D.pixels, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7);
				x1 += l2;
				x3 += j3;
				hsl1 += i3;
				hsl3 += k3;
			}

			while (--y1 >= 0) {
				drawGouraudScanline(Rasterizer2D.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7);
				x2 += j2;
				x3 += j3;
				hsl2 += k2;
				hsl3 += k3;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
			drawGouraudScanline(Rasterizer2D.pixels, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7);
			x1 += l2;
			x3 += j3;
			hsl1 += i3;
			hsl3 += k3;
		}

		while (--y1 >= 0) {
			drawGouraudScanline(Rasterizer2D.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7);
			x2 += j2;
			x3 += j3;
			hsl2 += k2;
			hsl3 += k3;
			y3 += Rasterizer2D.width;
		}
	}

	private static void drawGouraudScanline(int dest[], int offset, int x1, int x2, int hsl1, int hsl2) {
		int j;
		int k;
		if (restrict_edges) {
			int l1;
			if (textureOutOfDrawingBounds) {
				if (x2 - x1 > 3)
					l1 = (hsl2 - hsl1) / (x2 - x1);
				else
					l1 = 0;
				if (x2 > Rasterizer2D.lastX)
					x2 = Rasterizer2D.lastX;
				if (x1 < 0) {
					hsl1 -= x1 * l1;
					x1 = 0;
				}
				if (x1 >= x2)
					return;
				offset += x1;
				k = x2 - x1 >> 2;
				l1 <<= 2;
			} else {
				if (x1 >= x2)
					return;
				offset += x1;
				k = x2 - x1 >> 2;
				if (k > 0)
					l1 = (hsl2 - hsl1) * shadowDecay[k] >> 15;
				else
					l1 = 0;
			}
			if (alpha == 0) {
				while (--k >= 0) {
					j = hslToRgb[hsl1 >> 8];
					hsl1 += l1;
					dest[offset] = j;
					offset++;
					dest[offset] = j;
					offset++;
					dest[offset] = j;
					offset++;
					dest[offset] = j;
					offset++;
				}
				k = x2 - x1 & 3;
				if (k > 0) {
					j = hslToRgb[hsl1 >> 8];
					do {
						dest[offset] = j;
						offset++;
					}
					while (--k > 0);
					return;
				}
			} else {
				int a1 = alpha;
				int a2 = 256 - alpha;
				while (--k >= 0) {
					j = hslToRgb[hsl1 >> 8];
					hsl1 += l1;
					j = ((j & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((j & 0xff00) * a2 >> 8 & 0xff00);
					dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
					offset++;
					dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
					offset++;
					dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
					offset++;
					dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
					offset++;
				}
				k = x2 - x1 & 3;
				if (k > 0) {
					j = hslToRgb[hsl1 >> 8];
					j = ((j & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((j & 0xff00) * a2 >> 8 & 0xff00);
					do {
						dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
						offset++;
					}
					while (--k > 0);
				}
			}
			return;
		}
		if (x1 >= x2)
			return;
		int i2 = (hsl2 - hsl1) / (x2 - x1);
		if (textureOutOfDrawingBounds) {
			if (x2 > Rasterizer2D.lastX)
				x2 = Rasterizer2D.lastX;
			if (x1 < 0) {
				hsl1 -= x1 * i2;
				x1 = 0;
			}
			if (x1 >= x2)
				return;
		}
		offset += x1;
		k = x2 - x1;
		if (alpha == 0) {
			do {
				dest[offset] = hslToRgb[hsl1 >> 8];
				offset++;
				hsl1 += i2;
			} while (--k > 0);
			return;
		}
		int a1 = alpha;
		int a2 = 256 - alpha;
		do {
			j = hslToRgb[hsl1 >> 8];
			hsl1 += i2;
			j = ((j & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((j & 0xff00) * a2 >> 8 & 0xff00);
			dest[offset] = j + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
			offset++;
		} while (--k > 0);
	}

	public static void drawFlatTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int rgb) {
		int a_to_b = 0;
		if (y2 != y1) {
			a_to_b = (x2 - x1 << 16) / (y2 - y1);
		}
		int b_to_c = 0;
		if (y3 != y2) {
			b_to_c = (x3 - x2 << 16) / (y3 - y2);
		}
		int c_to_a = 0;
		if (y3 != y1) {
			c_to_a = (x1 - x3 << 16) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.bottomY)
				return;
			if (y2 > Rasterizer2D.bottomY)
				y2 = Rasterizer2D.bottomY;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				if (y1 < 0) {
					x3 -= c_to_a * y1;
					x1 -= a_to_b * y1;
					y1 = 0;
				}
				x2 <<= 16;
				if (y2 < 0) {
					x2 -= b_to_c * y2;
					y2 = 0;
				}
				if (y1 != y2 && c_to_a < a_to_b || y1 == y2 && c_to_a > b_to_c) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
						drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x3 >> 16, x1 >> 16);
						x3 += c_to_a;
						x1 += a_to_b;
					}

					while (--y3 >= 0) {
						drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x3 >> 16, x2 >> 16);
						x3 += c_to_a;
						x2 += b_to_c;
						y1 += Rasterizer2D.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += Rasterizer2D.width) {
					drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x1 >> 16, x3 >> 16);
					x3 += c_to_a;
					x1 += a_to_b;
				}

				while (--y3 >= 0) {
					drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x2 >> 16, x3 >> 16);
					x3 += c_to_a;
					x2 += b_to_c;
					y1 += Rasterizer2D.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			if (y1 < 0) {
				x2 -= c_to_a * y1;
				x1 -= a_to_b * y1;
				y1 = 0;

			}
			x3 <<= 16;
			if (y3 < 0) {
				x3 -= b_to_c * y3;
				y3 = 0;
			}
			if (y1 != y3 && c_to_a < a_to_b || y1 == y3 && b_to_c > a_to_b) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
					drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x2 >> 16, x1 >> 16);
					x2 += c_to_a;
					x1 += a_to_b;
				}

				while (--y2 >= 0) {
					drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x3 >> 16, x1 >> 16);
					x3 += b_to_c;
					x1 += a_to_b;
					y1 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += Rasterizer2D.width) {
				drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x1 >> 16, x2 >> 16);
				x2 += c_to_a;
				x1 += a_to_b;
			}

			while (--y2 >= 0) {
				drawFlatScanline(Rasterizer2D.pixels, y1, rgb, x1 >> 16, x3 >> 16);
				x3 += b_to_c;
				x1 += a_to_b;
				y1 += Rasterizer2D.width;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.bottomY)
				return;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y1 > Rasterizer2D.bottomY)
				y1 = Rasterizer2D.bottomY;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				if (y2 < 0) {
					x1 -= a_to_b * y2;
					x2 -= b_to_c * y2;
					y2 = 0;
				}
				x3 <<= 16;
				if (y3 < 0) {
					x3 -= c_to_a * y3;
					y3 = 0;
				}
				if (y2 != y3 && a_to_b < b_to_c || y2 == y3 && a_to_b > c_to_a) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
						drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x1 >> 16, x2 >> 16);
						x1 += a_to_b;
						x2 += b_to_c;
					}

					while (--y1 >= 0) {
						drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x1 >> 16, x3 >> 16);
						x1 += a_to_b;
						x3 += c_to_a;
						y2 += Rasterizer2D.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += Rasterizer2D.width) {
					drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x2 >> 16, x1 >> 16);
					x1 += a_to_b;
					x2 += b_to_c;
				}

				while (--y1 >= 0) {
					drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x3 >> 16, x1 >> 16);
					x1 += a_to_b;
					x3 += c_to_a;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			if (y2 < 0) {
				x3 -= a_to_b * y2;
				x2 -= b_to_c * y2;
				y2 = 0;
			}
			x1 <<= 16;
			if (y1 < 0) {
				x1 -= c_to_a * y1;
				y1 = 0;
			}
			if (a_to_b < b_to_c) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
					drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x3 >> 16, x2 >> 16);
					x3 += a_to_b;
					x2 += b_to_c;
				}

				while (--y3 >= 0) {
					drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x1 >> 16, x2 >> 16);
					x1 += c_to_a;
					x2 += b_to_c;
					y2 += Rasterizer2D.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += Rasterizer2D.width) {
				drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x2 >> 16, x3 >> 16);
				x3 += a_to_b;
				x2 += b_to_c;
			}

			while (--y3 >= 0) {
				drawFlatScanline(Rasterizer2D.pixels, y2, rgb, x2 >> 16, x1 >> 16);
				x1 += c_to_a;
				x2 += b_to_c;
				y2 += Rasterizer2D.width;
			}
			return;
		}
		if (y3 >= Rasterizer2D.bottomY)
			return;
		if (y1 > Rasterizer2D.bottomY)
			y1 = Rasterizer2D.bottomY;
		if (y2 > Rasterizer2D.bottomY)
			y2 = Rasterizer2D.bottomY;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			if (y3 < 0) {
				x2 -= b_to_c * y3;
				x3 -= c_to_a * y3;
				y3 = 0;
			}
			x1 <<= 16;
			if (y1 < 0) {
				x1 -= a_to_b * y1;
				y1 = 0;
			}
			if (b_to_c < c_to_a) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
					drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x2 >> 16, x3 >> 16);
					x2 += b_to_c;
					x3 += c_to_a;
				}

				while (--y2 >= 0) {
					drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x2 >> 16, x1 >> 16);
					x2 += b_to_c;
					x1 += a_to_b;
					y3 += Rasterizer2D.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += Rasterizer2D.width) {
				drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x3 >> 16, x2 >> 16);
				x2 += b_to_c;
				x3 += c_to_a;
			}

			while (--y2 >= 0) {
				drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x1 >> 16, x2 >> 16);
				x2 += b_to_c;
				x1 += a_to_b;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		if (y3 < 0) {
			x1 -= b_to_c * y3;
			x3 -= c_to_a * y3;
			y3 = 0;
		}
		x2 <<= 16;
		if (y2 < 0) {
			x2 -= a_to_b * y2;
			y2 = 0;
		}
		if (b_to_c < c_to_a) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
				drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x1 >> 16, x3 >> 16);
				x1 += b_to_c;
				x3 += c_to_a;
			}

			while (--y1 >= 0) {
				drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x2 >> 16, x3 >> 16);
				x2 += a_to_b;
				x3 += c_to_a;
				y3 += Rasterizer2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += Rasterizer2D.width) {
			drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x3 >> 16, x1 >> 16);
			x1 += b_to_c;
			x3 += c_to_a;
		}

		while (--y1 >= 0) {
			drawFlatScanline(Rasterizer2D.pixels, y3, rgb, x3 >> 16, x2 >> 16);
			x2 += a_to_b;
			x3 += c_to_a;
			y3 += Rasterizer2D.width;
		}
	}

	private static void drawFlatScanline(int dest[], int offset, int rgb, int x1, int x2) {
		if (textureOutOfDrawingBounds) {
			if (x2 > Rasterizer2D.lastX) {
				x2 = Rasterizer2D.lastX;
			}
			if (x1 < 0) {
				x1 = 0;
			}
		}
		if (x1 >= x2) {
			return;
		}
		offset += x1;
		int pos = x2 - x1 >> 2;
		if (alpha == 0) {
			while (--pos >= 0) {
				for (int i = 0; i < 4; i++) {
					dest[offset] = rgb;
					offset++;
				}
			}
			for (pos = x2 - x1 & 3; --pos >= 0; ) {
				dest[offset] = rgb;
				offset++;
			}
			return;
		}
		int a1 = alpha;
		int a2 = 256 - alpha;
		rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
		while (--pos >= 0) {
			for (int i = 0; i < 4; i++) {
				dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
				offset++;
			}
		}
		for (pos = x2 - x1 & 3; --pos >= 0; ) {
			dest[offset++] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
		}
	}

	public static void drawTexturedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int c1, int c2, int c3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex) {
		c1 = 0x7f - c1 << 1;
		c2 = 0x7f - c2 << 1;
		c3 = 0x7f - c3 << 1;
		int ai[] = getTexturePixels(tex);
		aBoolean1463 = !textureIsTransparant[tex];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (SceneGraph.viewDistance == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (SceneGraph.viewDistance == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (SceneGraph.viewDistance == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (c2 - c1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (c3 - c2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (c1 - c3 << 16) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Rasterizer2D.bottomY)
				return;
			if (y2 > Rasterizer2D.bottomY)
				y2 = Rasterizer2D.bottomY;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				c3 = c1 <<= 16;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					c3 -= j8 * y1;
					c1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				c2 <<= 16;
				if (y2 < 0) {
					x2 -= k7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - originViewY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = scanOffsets[y1];
					while (--y2 >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x1 += i7;
						c3 += j8;
						c1 += j7;
						y1 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x2 += k7;
						c3 += j8;
						c2 += l7;
						y1 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = scanOffsets[y1];
				while (--y2 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					c3 += j8;
					c1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					c3 += j8;
					c2 += l7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			c2 = c1 <<= 16;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				c2 -= j8 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			c3 <<= 16;
			if (y3 < 0) {
				x3 -= k7 * y3;
				c3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - originViewY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = scanOffsets[y1];
				while (--y3 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					c2 += j8;
					c1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					c3 += l7;
					c1 += j7;
					y1 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = scanOffsets[y1];
			while (--y3 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				c2 += j8;
				c1 += j7;
				y1 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y1, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				c3 += l7;
				c1 += j7;
				y1 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Rasterizer2D.bottomY)
				return;
			if (y3 > Rasterizer2D.bottomY)
				y3 = Rasterizer2D.bottomY;
			if (y1 > Rasterizer2D.bottomY)
				y1 = Rasterizer2D.bottomY;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				c1 = c2 <<= 16;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					c1 -= j7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				c3 <<= 16;
				if (y3 < 0) {
					x3 -= i8 * y3;
					c3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - originViewY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = scanOffsets[y2];
					while (--y3 >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x2 += k7;
						c1 += j7;
						c2 += l7;
						y2 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x3 += i8;
						c1 += j7;
						c3 += j8;
						y2 += Rasterizer2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = scanOffsets[y2];
				while (--y3 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					c1 += j7;
					c2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					c1 += j7;
					c3 += j8;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			c3 = c2 <<= 16;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				c3 -= j7 * y2;
				c2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i8 * y1;
				c1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - originViewY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = scanOffsets[y2];
				while (--y1 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					c3 += j7;
					c2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					c1 += j8;
					c2 += l7;
					y2 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = scanOffsets[y2];
			while (--y1 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				c3 += j7;
				c2 += l7;
				y2 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y2, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				c1 += j8;
				c2 += l7;
				y2 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= Rasterizer2D.bottomY)
			return;
		if (y1 > Rasterizer2D.bottomY)
			y1 = Rasterizer2D.bottomY;
		if (y2 > Rasterizer2D.bottomY)
			y2 = Rasterizer2D.bottomY;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			c2 = c3 <<= 16;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				c2 -= l7 * y3;
				c3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i7 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - originViewY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = scanOffsets[y3];
				while (--y1 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					c2 += l7;
					c3 += j8;
					y3 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					c2 += l7;
					c1 += j7;
					y3 += Rasterizer2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = scanOffsets[y3];
			while (--y1 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				c2 += l7;
				c3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				c2 += l7;
				c1 += j7;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		c1 = c3 <<= 16;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			c1 -= l7 * y3;
			c3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		c2 <<= 16;
		if (y2 < 0) {
			x2 -= i7 * y2;
			c2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - originViewY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = scanOffsets[y3];
			while (--y2 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				c1 += l7;
				c3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				c2 += j7;
				c3 += j8;
				y3 += Rasterizer2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = scanOffsets[y3];
		while (--y2 >= 0) {
			drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			c1 += l7;
			c3 += j8;
			y3 += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawTexturedScanline(Rasterizer2D.pixels, ai, y3, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			c2 += j7;
			c3 += j8;
			y3 += Rasterizer2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawTexturedScanline(int ai[], int ai1[], int k, int x1, int x2, int l1, int l2, int a1, int i2, int j2, int k2, int a2, int i3) {
		int i = 0;
		int j = 0;
		if (x1 >= x2)
			return;
		int dl = (l2 - l1) / (x2 - x1);
		int n;
		if (textureOutOfDrawingBounds) {
			if (x2 > Rasterizer2D.lastX)
				x2 = Rasterizer2D.lastX;
			if (x1 < 0) {
				l1 -= x1 * dl;
				x1 = 0;
			}
		}
		if (x1 >= x2)
			return;
		n = x2 - x1 >> 3;
		k += x1;
		if (lowMem) {
			int i4 = 0;
			int k4 = 0;
			int k6 = x1 - originViewX;
			a1 += (k2 >> 3) * k6;
			i2 += (a2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if (i5 != 0) {
				i = a1 / i5;
				j = i2 / i5;
				if (i < 0)
					i = 0;
				else if (i > 4032)
					i = 4032;
			}
			a1 += k2;
			i2 += a2;
			j2 += i3;
			i5 = j2 >> 12;
			if (i5 != 0) {
				i4 = a1 / i5;
				k4 = i2 / i5;
				if (i4 < 7)
					i4 = 7;
				else if (i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			if (aBoolean1463) {
				int rgb;
				int l;
				while (n-- > 0) {
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					a1 += k2;
					i2 += a2;
					j2 += i3;
					int j5 = j2 >> 12;
					if (j5 != 0) {
						i4 = a1 / j5;
						k4 = i2 / j5;
						if (i4 < 7)
							i4 = 7;
						else if (i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					l1 += dl;
				}
				for (n = x2 - x1 & 7; n-- > 0; ) {
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
				}
				return;
			}
			while (n-- > 0) {
				int k8;
				int l;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int k5 = j2 >> 12;
				if (k5 != 0) {
					i4 = a1 / k5;
					k4 = i2 / k5;
					if (i4 < 7)
						i4 = 7;
					else if (i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				l1 += dl;
			}
			for (n = x2 - x1 & 7; n-- > 0; ) {
				int l8;
				int l;
				if ((l8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((l8 & 0xff00ff) * l & ~0xff00ff) + ((l8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - originViewX;
		a1 += (k2 >> 3) * l6;
		i2 += (a2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = a1 / l5;
			j = i2 / l5;
			if (i < 0)
				i = 0;
			else if (i > 16256)
				i = 16256;
		}
		a1 += k2;
		i2 += a2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = a1 / l5;
			l4 = i2 / l5;
			if (j4 < 7)
				j4 = 7;
			else if (j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if (aBoolean1463) {
			while (n-- > 0) {
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int i6 = j2 >> 14;
				if (i6 != 0) {
					j4 = a1 / i6;
					l4 = i2 / i6;
					if (j4 < 7)
						j4 = 7;
					else if (j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				l1 += dl;
			}
			for (n = x2 - x1 & 7; n-- > 0; ) {
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
			}

			return;
		}
		while (n-- > 0) {
			int i9;
			int l;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			a1 += k2;
			i2 += a2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = a1 / j6;
				l4 = i2 / j6;
				if (j4 < 7)
					j4 = 7;
				else if (j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			l1 += dl;
		}
		for (int l3 = x2 - x1 & 7; l3-- > 0; ) {
			int j9;
			int l;
			if ((j9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;
				;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
		}
	}

	public static void drawGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, float z1, float z2, float z3, int bufferOffset) {
		int rgb1 = hslToRgb[hsl1];
		int rgb2 = hslToRgb[hsl2];
		int rgb3 = hslToRgb[hsl3];
		int r1 = rgb1 >> 16 & 0xff;
		int g1 = rgb1 >> 8 & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = rgb2 >> 16 & 0xff;
		int g2 = rgb2 >> 8 & 0xff;
		int b2 = rgb2 & 0xff;
		int r3 = rgb3 >> 16 & 0xff;
		int g3 = rgb3 >> 8 & 0xff;
		int b3 = rgb3 & 0xff;
		int dx1 = 0;
		int dr1 = 0;
		int dg1 = 0;
		int db1 = 0;
		if (y2 != y1) {
			dx1 = (x2 - x1 << 16) / (y2 - y1);
			dr1 = (r2 - r1 << 16) / (y2 - y1);
			dg1 = (g2 - g1 << 16) / (y2 - y1);
			db1 = (b2 - b1 << 16) / (y2 - y1);
		}
		int dx2 = 0;
		int dr2 = 0;
		int dg2 = 0;
		int db2 = 0;
		if (y3 != y2) {
			dx2 = (x3 - x2 << 16) / (y3 - y2);
			dr2 = (r3 - r2 << 16) / (y3 - y2);
			dg2 = (g3 - g2 << 16) / (y3 - y2);
			db2 = (b3 - b2 << 16) / (y3 - y2);
		}
		int dx3 = 0;
		int dr3 = 0;
		int dg3 = 0;
		int db3 = 0;
		if (y3 != y1) {
			dx3 = (x1 - x3 << 16) / (y1 - y3);
			dr3 = (r1 - r3 << 16) / (y1 - y3);
			dg3 = (g1 - g3 << 16) / (y1 - y3);
			db3 = (b1 - b3 << 16) / (y1 - y3);
		}

		float x21 = x2 - x1;
		float y21 = y2 - y1;
		float x31 = x3 - x1;
		float y31 = y3 - y1;
		float z21 = z2 - z1;
		float z31 = z3 - z1;

		float div = x21 * y31 - x31 * y21;
		float depthSlope = (z21 * y31 - z31 * y21) / div;
		float depthScale = (z31 * x21 - z21 * x31) / div;

		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= leftX) {
				return;
			}
			if (y2 > leftX) {
				y2 = leftX;
			}
			if (y3 > leftX) {
				y3 = leftX;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				r3 = r1 <<= 16;
				g3 = g1 <<= 16;
				b3 = b1 <<= 16;
				if (y1 < 0) {
					x3 -= dx3 * y1;
					x1 -= dx1 * y1;
					r3 -= dr3 * y1;
					g3 -= dg3 * y1;
					b3 -= db3 * y1;
					r1 -= dr1 * y1;
					g1 -= dg1 * y1;
					b1 -= db1 * y1;
					z1 -= depthScale * y1;
					y1 = 0;
				}
				x2 <<= 16;
				r2 <<= 16;
				g2 <<= 16;
				b2 <<= 16;
				if (y2 < 0) {
					x2 -= dx2 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					y2 = 0;
				}
				if (y1 != y2 && dx3 < dx1 || y1 == y2 && dx3 > dx2) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += width) {
						drawGouraudScanline(pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depthSlope, bufferOffset);
						x3 += dx3;
						x1 += dx1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						z1 += depthScale;
					}
					while (--y3 >= 0) {
						drawGouraudScanline(pixels, y1, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z1, depthSlope, bufferOffset);
						x3 += dx3;
						x2 += dx2;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						y1 += width;
						z1 += depthScale;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = scanOffsets[y1]; --y2 >= 0; y1 += width) {
					drawGouraudScanline(pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depthSlope, bufferOffset);
					x3 += dx3;
					x1 += dx1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depthScale;
				}
				while (--y3 >= 0) {
					drawGouraudScanline(pixels, y1, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z1, depthSlope, bufferOffset);
					x3 += dx3;
					x2 += dx2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y1 += width;
					z1 += depthScale;
				}
				return;
			}
			x2 = x1 <<= 16;
			r2 = r1 <<= 16;
			g2 = g1 <<= 16;
			b2 = b1 <<= 16;
			if (y1 < 0) {
				x2 -= dx3 * y1;
				x1 -= dx1 * y1;
				r2 -= dr3 * y1;
				g2 -= dg3 * y1;
				b2 -= db3 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				z1 -= depthScale * y1;
				y1 = 0;
			}
			x3 <<= 16;
			r3 <<= 16;
			g3 <<= 16;
			b3 <<= 16;
			if (y3 < 0) {
				x3 -= dx2 * y3;
				r3 -= dr2 * y3;
				g3 -= dg2 * y3;
				b3 -= db2 * y3;
				y3 = 0;
			}
			if (y1 != y3 && dx3 < dx1 || y1 == y3 && dx2 > dx1) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += width) {
					drawGouraudScanline(pixels, y1, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z1, depthSlope, bufferOffset);
					x2 += dx3;
					x1 += dx1;
					r2 += dr3;
					g2 += dg3;
					b2 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depthScale;
				}
				while (--y2 >= 0) {
					drawGouraudScanline(pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depthSlope, bufferOffset);
					x3 += dx2;
					x1 += dx1;
					r3 += dr2;
					g3 += dg2;
					b3 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y1 += width;
					z1 += depthScale;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = scanOffsets[y1]; --y3 >= 0; y1 += width) {
				drawGouraudScanline(pixels, y1, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z1, depthSlope, bufferOffset);
				x2 += dx3;
				x1 += dx1;
				r2 += dr3;
				g2 += dg3;
				b2 += db3;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z1 += depthScale;
			}
			while (--y2 >= 0) {
				drawGouraudScanline(pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depthSlope, bufferOffset);
				x3 += dx2;
				x1 += dx1;
				r3 += dr2;
				g3 += dg2;
				b3 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				y1 += width;
				z1 += depthScale;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= bottomY) {
				return;
			}
			if (y3 > bottomY) {
				y3 = bottomY;
			}
			if (y1 > bottomY) {
				y1 = bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				r1 = r2 <<= 16;
				g1 = g2 <<= 16;
				b1 = b2 <<= 16;
				if (y2 < 0) {
					x1 -= dx1 * y2;
					x2 -= dx2 * y2;
					r1 -= dr1 * y2;
					g1 -= dg1 * y2;
					b1 -= db1 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					z2 -= depthScale * y2;
					y2 = 0;
				}
				x3 <<= 16;
				r3 <<= 16;
				g3 <<= 16;
				b3 <<= 16;
				if (y3 < 0) {
					x3 -= dx3 * y3;
					r3 -= dr3 * y3;
					g3 -= dg3 * y3;
					b3 -= db3 * y3;
					y3 = 0;
				}
				if (y2 != y3 && dx1 < dx2 || y2 == y3 && dx1 > dx3) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += width) {
						drawGouraudScanline(pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depthSlope, bufferOffset);
						x1 += dx1;
						x2 += dx2;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						z2 += depthScale;
					}
					while (--y1 >= 0) {
						drawGouraudScanline(pixels, y2, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z2, depthSlope, bufferOffset);
						x1 += dx1;
						x3 += dx3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						y2 += width;
						z2 += depthScale;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = scanOffsets[y2]; --y3 >= 0; y2 += width) {
					drawGouraudScanline(pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depthSlope, bufferOffset);
					x1 += dx1;
					x2 += dx2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depthScale;
				}
				while (--y1 >= 0) {
					drawGouraudScanline(pixels, y2, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z2, depthSlope, bufferOffset);
					x1 += dx1;
					x3 += dx3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					y2 += width;
					z2 += depthScale;
				}
				return;
			}
			x3 = x2 <<= 16;
			r3 = r2 <<= 16;
			g3 = g2 <<= 16;
			b3 = b2 <<= 16;
			if (y2 < 0) {
				x3 -= dx1 * y2;
				x2 -= dx2 * y2;
				r3 -= dr1 * y2;
				g3 -= dg1 * y2;
				b3 -= db1 * y2;
				r2 -= dr2 * y2;
				g2 -= dg2 * y2;
				b2 -= db2 * y2;
				z2 -= depthScale * y2;
				y2 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= dx3 * y1;
				r1 -= dr3 * y1;
				g1 -= dg3 * y1;
				b1 -= db3 * y1;
				y1 = 0;
			}
			if (dx1 < dx2) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += width) {
					drawGouraudScanline(pixels, y2, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z2, depthSlope, bufferOffset);
					x3 += dx1;
					x2 += dx2;
					r3 += dr1;
					g3 += dg1;
					b3 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depthScale;
				}
				while (--y3 >= 0) {
					drawGouraudScanline(pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depthSlope, bufferOffset);
					x1 += dx3;
					x2 += dx2;
					r1 += dr3;
					g1 += dg3;
					b1 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y2 += width;
					z2 += depthScale;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = scanOffsets[y2]; --y1 >= 0; y2 += width) {
				drawGouraudScanline(pixels, y2, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z2, depthSlope, bufferOffset);
				x3 += dx1;
				x2 += dx2;
				r3 += dr1;
				g3 += dg1;
				b3 += db1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				z2 += depthScale;
			}
			while (--y3 >= 0) {
				drawGouraudScanline(pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depthSlope, bufferOffset);
				x1 += dx3;
				x2 += dx2;
				r1 += dr3;
				g1 += dg3;
				b1 += db3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				y2 += width;
				z2 += depthScale;
			}
			return;
		}
		if (y3 >= bottomY) {
			return;
		}
		if (y1 > bottomY) {
			y1 = bottomY;
		}
		if (y2 > bottomY) {
			y2 = bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			r2 = r3 <<= 16;
			g2 = g3 <<= 16;
			b2 = b3 <<= 16;
			if (y3 < 0) {
				x2 -= dx2 * y3;
				x3 -= dx3 * y3;
				r2 -= dr2 * y3;
				g2 -= dg2 * y3;
				b2 -= db2 * y3;
				r3 -= dr3 * y3;
				g3 -= dg3 * y3;
				b3 -= db3 * y3;
				z3 -= depthScale * y3;
				y3 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= dx1 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				y1 = 0;
			}
			if (dx2 < dx3) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += width) {
					drawGouraudScanline(pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depthSlope, bufferOffset);
					x2 += dx2;
					x3 += dx3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					z3 += depthScale;
				}
				while (--y2 >= 0) {
					drawGouraudScanline(pixels, y3, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z3, depthSlope, bufferOffset);
					x2 += dx2;
					x1 += dx1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y3 += width;
					z3 += depthScale;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = scanOffsets[y3]; --y1 >= 0; y3 += width) {
				drawGouraudScanline(pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depthSlope, bufferOffset);
				x2 += dx2;
				x3 += dx3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
			}
			while (--y2 >= 0) {
				drawGouraudScanline(pixels, y3, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z3, depthSlope, bufferOffset);
				x2 += dx2;
				x1 += dx1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z3 += depthScale;
				y3 += width;
			}
			return;
		}
		x1 = x3 <<= 16;
		r1 = r3 <<= 16;
		g1 = g3 <<= 16;
		b1 = b3 <<= 16;
		if (y3 < 0) {
			x1 -= dx2 * y3;
			x3 -= dx3 * y3;
			r1 -= dr2 * y3;
			g1 -= dg2 * y3;
			b1 -= db2 * y3;
			r3 -= dr3 * y3;
			g3 -= dg3 * y3;
			b3 -= db3 * y3;
			z3 -= depthScale * y3;
			y3 = 0;
		}
		x2 <<= 16;
		r2 <<= 16;
		g2 <<= 16;
		b2 <<= 16;
		if (y2 < 0) {
			x2 -= dx1 * y2;
			r2 -= dr1 * y2;
			g2 -= dg1 * y2;
			b2 -= db1 * y2;
			y2 = 0;
		}
		if (dx2 < dx3) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += width) {
				drawGouraudScanline(pixels, y3, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z3, depthSlope, bufferOffset);
				x1 += dx2;
				x3 += dx3;
				r1 += dr2;
				g1 += dg2;
				b1 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
			}
			while (--y1 >= 0) {
				drawGouraudScanline(pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depthSlope, bufferOffset);
				x2 += dx1;
				x3 += dx3;
				r2 += dr1;
				g2 += dg1;
				b2 += db1;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
				y3 += width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = scanOffsets[y3]; --y2 >= 0; y3 += width) {
			drawGouraudScanline(pixels, y3, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z3, depthSlope, bufferOffset);
			x1 += dx2;
			x3 += dx3;
			r1 += dr2;
			g1 += dg2;
			b1 += db2;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			z3 += depthScale;
		}
		while (--y1 >= 0) {
			drawGouraudScanline(pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depthSlope, bufferOffset);
			x2 += dx1;
			x3 += dx3;
			r2 += dr1;
			g2 += dg1;
			b2 += db1;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			y3 += width;
			z3 += depthScale;
		}
	}

	public static void drawGouraudScanline(int[] dest, int offset, int x1, int x2, int r1, int g1, int b1, int r2, int g2, int b2, float z1, float z2, int bufferOffset) {
		int n = x2 - x1;
		if (n <= 0) {
			return;
		}
		r2 = (r2 - r1) / n;
		g2 = (g2 - g1) / n;
		b2 = (b2 - b1) / n;
		if (restrict_edges) {
			if (x2 > Rasterizer2D.lastX) {
				n -= x2 - Rasterizer2D.lastX;
				x2 = Rasterizer2D.lastX;
			}
			if (x1 < 0) {
				n = x2;
				r1 -= x1 * r2;
				g1 -= x1 * g2;
				b1 -= x1 * b2;
				x1 = 0;
			}
		}
		if (x1 < x2) {
			offset += x1;
			z1 += z2 * x1;
			if (alpha == 0) {
				while (--n >= 0) {
					if (z1 < depthBuffer[offset] || z1 < depthBuffer[offset] + bufferOffset) {
						dest[offset] = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			} else {
				int opacity = 256 - alpha;
				while (--n >= 0) {
					int rgb = r1 & 0xff0000 | g1 >> 8 & 0xff00 | b1 >> 16 & 0xff;
					rgb = ((rgb & 0xff00ff) * opacity >> 8 & 0xff00ff) + ((rgb & 0xff00) * opacity >> 8 & 0xff00);
					if (z1 < depthBuffer[offset] || z1 < depthBuffer[offset] + bufferOffset) {
						dest[offset] = rgb + ((dest[offset] & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * alpha >> 8 & 0xff00);
						depthBuffer[offset] = z1;
					}
					offset++;
					z1 += z2;
					r1 += r2;
					g1 += g2;
					b1 += b2;
				}
			}
		}
	}
}