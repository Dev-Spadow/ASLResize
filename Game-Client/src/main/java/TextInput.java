final class TextInput {

	public static String method525(int i, Buffer buffer) {
		int j = 0;
		int k = -1;
		for (int l = 0; l < i; l++) {
			int i1 = buffer.readUnsignedByte();
			aCharArray631[j++] = validChars[i1];
		}
		boolean flag1 = true;
		for (int k1 = 0; k1 < j; k1++) {
			char c = aCharArray631[k1];
			if (flag1 && c >= 'a' && c <= 'z') {
				aCharArray631[k1] += '\uFFE0';
				flag1 = false;
			}
			if (c == '.' || c == '!' || c == '?')
				flag1 = true;
		}
		return new String(aCharArray631, 0, j);
	}

	public static void method526(String s, Buffer buffer) {
		if (s.length() > 80)
			s = s.substring(0, 80);
		s = s.toLowerCase();
		int i = -1;
		for (int j = 0; j < s.length(); j++) {
			char c = s.charAt(j);
			int k = 0;
			for (int l = 0; l < validChars.length; l++) {
				if (c != validChars[l])
					continue;
				k = l;
				break;
			}
			buffer.writeWordBigEndian(k);
		}
	}

	public static String processText(String s) {
		BUFFER.currentPosition = 0;
		method526(s, BUFFER);
		int j = BUFFER.currentPosition;
		BUFFER.currentPosition = 0;
		String s1 = method525(j, BUFFER);
		return s1;
	}

	public static final boolean aBoolean630 = true;
	public static final char[] aCharArray631 = new char[100];
	public static final Buffer BUFFER = new Buffer(new byte[100]);
	/*public static char validChars[] = { 
		' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 
		'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p',
		'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2',
		'3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', 
		'.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', 
		'\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', 
		']', '>', '<', '^', '/', '_' 
	};*/
	private static char validChars[] = {
        ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 
        'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 
        'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', 
        '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', 
        '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', 
        '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', 
        ']', '>', '<', '^', '/', '_'
    };

}
