import java.math.BigInteger;

import sign.Signlink;

public final class Buffer extends Cacheable {

	public static Buffer create() {
		synchronized (DEQUE) {
			Buffer buffer = null;
			if (anInt1412 > 0) {
				anInt1412--;
				buffer = (Buffer) DEQUE.popHead();
			}
			if (buffer != null) {
				buffer.currentPosition = 0;
				return buffer;
			}
		}
		Buffer buffer_1 = new Buffer();
		buffer_1.currentPosition = 0;
		buffer_1.payload = new byte[5000];
		return buffer_1;
	}

	public Buffer() {
	}

	public Buffer(byte abyte0[]) {
		payload = abyte0;
		currentPosition = 0;
	}

	public void createFrame(int i) {
		payload[currentPosition++] = (byte) (i + encryption.getNextKey());
	}

	public void writeWordBigEndian(int i) {
		payload[currentPosition++] = (byte) i;
	}

	public int readUSmart2() {
		int baseVal = 0;
		int lastVal = 0;
		while ((lastVal = getSmart()) == 32767) {
			baseVal += 32767;
		}
		return baseVal + lastVal;
	}
	
	public final int v() {
  currentPosition += 3;
  return (0xff & payload[currentPosition - 3] << 16) + (0xff & payload[currentPosition - 2] << 8) + (0xff & payload[currentPosition - 1]);
 }

	public String readNewString() {
		int i = currentPosition;
		while (payload[currentPosition++] != 0)
			;
		return new String(payload, i, currentPosition - i - 1);
	}

	public void writeWord(int i) {
		payload[currentPosition++] = (byte) (i >> 8);
		payload[currentPosition++] = (byte) i;
	}

	public void method400(int i) {
		payload[currentPosition++] = (byte) i;
		payload[currentPosition++] = (byte) (i >> 8);
	}
	
	

	public void writeDWordBigEndian(int i) {
		payload[currentPosition++] = (byte) (i >> 16);
		payload[currentPosition++] = (byte) (i >> 8);
		payload[currentPosition++] = (byte) i;
	}

	public void writeDWord(int i) {
		payload[currentPosition++] = (byte) (i >> 24);
		payload[currentPosition++] = (byte) (i >> 16);
		payload[currentPosition++] = (byte) (i >> 8);
		payload[currentPosition++] = (byte) i;
	}

	public void method403(int j) {
		payload[currentPosition++] = (byte) j;
		payload[currentPosition++] = (byte) (j >> 8);
		payload[currentPosition++] = (byte) (j >> 16);
		payload[currentPosition++] = (byte) (j >> 24);
	}

	public void writeQWord(long l) {
		try {
			payload[currentPosition++] = (byte)(int)(l >> 56);
			payload[currentPosition++] = (byte)(int)(l >> 48);
			payload[currentPosition++] = (byte)(int)(l >> 40);
			payload[currentPosition++] = (byte)(int)(l >> 32);
			payload[currentPosition++] = (byte)(int)(l >> 24);
			payload[currentPosition++] = (byte)(int)(l >> 16);
			payload[currentPosition++] = (byte)(int)(l >> 8);
			payload[currentPosition++] = (byte)(int)l;
		} catch(RuntimeException runtimeexception) {
			Signlink.reporterror("14395, " + 5 + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void writeString(String s) {
		// s.getBytes(0, s.length(), payload, currentPosition); //deprecated
		System.arraycopy(s.getBytes(), 0, payload, currentPosition, s.length());
		currentPosition += s.length();
		payload[currentPosition++] = 10;
	}

	public void writeBytes(byte abyte0[], int i, int j) {
		for (int k = j; k < j + i; k++)
			payload[currentPosition++] = abyte0[k];
	}

	public void writeByte(int i) {
		payload[currentPosition++] = (byte) i;
	}

	public void writeBytes(int i) {
		payload[currentPosition - i - 1] = (byte) i;
	}

	public int readUnsignedByte() {
		return payload[currentPosition++] & 0xff;
	}

	public byte readSignedByte() {
		return payload[currentPosition++];
	}

	public int readUShort() {
		currentPosition += 2;
		return ((payload[currentPosition - 2] & 0xff) << 8)
		+ (payload[currentPosition - 1] & 0xff);
	}

	public int readSignedWord() {
		currentPosition += 2;
		int i = ((payload[currentPosition - 2] & 0xff) << 8)
		+ (payload[currentPosition - 1] & 0xff);
		if (i > 32767)
			i -= 0x10000;
		return i;
	}

	public int readTriByte() {
		currentPosition += 3;
		return ((payload[currentPosition - 3] & 0xff) << 16)
		+ ((payload[currentPosition - 2] & 0xff) << 8)
		+ (payload[currentPosition - 1] & 0xff);
	}

	public int readInt() {
		currentPosition += 4;
		return ((payload[currentPosition - 4] & 0xff) << 24)
		+ ((payload[currentPosition - 3] & 0xff) << 16)
		+ ((payload[currentPosition - 2] & 0xff) << 8)
		+ (payload[currentPosition - 1] & 0xff);
	}

	public long readQWord() {
		long l = (long) readInt() & 0xffffffffL;
		long l1 = (long) readInt() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public String readString() {
		int i = currentPosition;
		while(payload[currentPosition++] != 10);
		return new String(payload, i, currentPosition - i - 1);
	}

	public byte[] readBytes() {
		int i = currentPosition;
		while (payload[currentPosition++] != 10);
		byte abyte0[] = new byte[currentPosition - i - 1];
		System.arraycopy(payload, i, abyte0, i - i, currentPosition - 1 - i);
		return abyte0;
	}

	public void readBytes(int i, int j, byte abyte0[]) {
		for (int l = j; l < j + i; l++)
			abyte0[l] = payload[currentPosition++];
	}

	public void initBitAccess() {
		bitPosition = currentPosition * 8;
	}

	public int readBits(int i) {
		int k = bitPosition >> 3;
		int l = 8 - (bitPosition & 7);
		int i1 = 0;
		bitPosition += i;
		for (; i > l; l = 8) {
			i1 += (payload[k++] & anIntArray1409[l]) << i - l;
			i -= l;
		}
		if (i == l)
			i1 += payload[k] & anIntArray1409[l];
		else
			i1 += payload[k] >> l - i & anIntArray1409[i];
			return i1;
	}
	
	public byte readByte() {
		return payload[currentPosition++];
	}
	
	public int readShort() {
		currentPosition += 2;
		return ((payload[currentPosition - 2] & 0xff) << 8)
				+ (payload[currentPosition - 1] & 0xff);
	}
	
	public int readUByte() {
		return payload[currentPosition++] & 0xff;
	}

	public void finishBitAccess() {
		currentPosition = (bitPosition + 7) / 8;
	}

	public int readSmart() {
		int i = payload[currentPosition] & 0xff;
		if (i < 128)
			return readUnsignedByte() - 64;
		else
			return readUShort() - 49152;
	}

	public int getSmart() {
		int i = payload[currentPosition] & 0xff;
		if (i < 128)
			return readUnsignedByte();
		else
			return readUShort() - 32768;
	}
	
	private static final BigInteger RSA_MODULUS = new BigInteger("102200103636873648286099675067974502450963162824789462939669453204637534708832227397181128258393771103744972685345424289538958089969741477606431995076197844427696877493958235202388475308349765625372372259423102898146881431486875600758679527975254229662345801405480136890592902637282963233289416324923535617551");

	private static final BigInteger RSA_EXPONENT = new BigInteger("65537");

	
	public void doKeys() {
		int i = currentPosition;
		currentPosition = 0;
		byte abyte0[] = new byte[i];
		readBytes(i, 0, abyte0);
		BigInteger biginteger2 = new BigInteger(abyte0);
		BigInteger biginteger3 = biginteger2.modPow(RSA_EXPONENT, RSA_MODULUS);
		byte abyte1[] = biginteger3.toByteArray();
		currentPosition = 0;
		writeWordBigEndian(abyte1.length);
		writeBytes(abyte1, abyte1.length, 0);
	}

	public void method424(int i) {
		payload[currentPosition++] = (byte) (-i);
	}

	public void method425(int j) {
		payload[currentPosition++] = (byte) (128 - j);
	}

	public int method426() {
		return payload[currentPosition++] - 128 & 0xff;
	}

	public int method427() {
		return -payload[currentPosition++] & 0xff;
	}

	public int method428() {
		return 128 - payload[currentPosition++] & 0xff;
	}

	public byte method429() {
		return (byte) (-payload[currentPosition++]);
	}

	public byte method430() {
		return (byte) (128 - payload[currentPosition++]);
	}

	public void method431(int i) {
		payload[currentPosition++] = (byte) i;
		payload[currentPosition++] = (byte) (i >> 8);
	}

	public void method432(int j) {
		payload[currentPosition++] = (byte) (j >> 8);
		payload[currentPosition++] = (byte) (j + 128);
	}

	public void method433(int j) {
		payload[currentPosition++] = (byte) (j + 128);
		payload[currentPosition++] = (byte) (j >> 8);
	}

	public int method434() {
		currentPosition += 2;
		return ((payload[currentPosition - 1] & 0xff) << 8)
		+ (payload[currentPosition - 2] & 0xff);
	}

	public int method435() {
		currentPosition += 2;
		return ((payload[currentPosition - 2] & 0xff) << 8)
		+ (payload[currentPosition - 1] - 128 & 0xff);
	}

	public int method436() {
		currentPosition += 2;
		return ((payload[currentPosition - 1] & 0xff) << 8)
		+ (payload[currentPosition - 2] - 128 & 0xff);
	}

	public int method437() {
		currentPosition += 2;
		int j = ((payload[currentPosition - 1] & 0xff) << 8)
		+ (payload[currentPosition - 2] & 0xff);
		if (j > 32767)
			j -= 0x10000;
		return j;
	}

	public int method438() {
		currentPosition += 2;
		int j = ((payload[currentPosition - 1] & 0xff) << 8)
		+ (payload[currentPosition - 2] - 128 & 0xff);
		if (j > 32767)
			j -= 0x10000;
		return j;
	}

	public int method439() {
		currentPosition += 4;
		return ((payload[currentPosition - 2] & 0xff) << 24)
		+ ((payload[currentPosition - 1] & 0xff) << 16)
		+ ((payload[currentPosition - 4] & 0xff) << 8)
		+ (payload[currentPosition - 3] & 0xff);
	}

	public int method440() {
		currentPosition += 4;
		return ((payload[currentPosition - 3] & 0xff) << 24)
		+ ((payload[currentPosition - 4] & 0xff) << 16)
		+ ((payload[currentPosition - 1] & 0xff) << 8)
		+ (payload[currentPosition - 2] & 0xff);
	}

	public void method441(int i, byte abyte0[], int j) {
		for (int k = (i + j) - 1; k >= i; k--)
			payload[currentPosition++] = (byte) (abyte0[k] + 128);

	}

	public void method442(int i, int j, byte abyte0[]) {
		for (int k = (j + i) - 1; k >= j; k--)
			abyte0[k] = payload[currentPosition++];

	}

	public byte payload[];
	public int currentPosition;
	public int bitPosition;
	public static final int[] anIntArray1409 = { 0, 1, 3, 7, 15, 31, 63, 127,
		255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff,
		0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
		0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff,
		0x7fffffff, -1 };
	public ISAACRandomGen encryption;
	public static int anInt1412;
	public static final Deque DEQUE = new Deque();
}
