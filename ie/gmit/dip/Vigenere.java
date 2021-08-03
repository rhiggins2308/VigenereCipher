package ie.gmit.dip;

public class Vigenere {

	private static final char[][] TABULA_RECTA = { 
			{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'},
			{'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A'},
			{'C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B'},
			{'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C'},
			{'E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D'},
			{'F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E'},
			{'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F'},
			{'H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G'},
			{'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H'},
			{'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I'},
			{'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J'},
			{'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K'},
			{'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L'},
			{'N','O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M'},
			{'O','P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N'},
			{'P','Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O'},
			{'Q','R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'},
			{'R','S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q'},
			{'S','T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R'},
			{'T','U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'},
			{'U','V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T'},
			{'V','W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U'},
			{'W','X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V'},
			{'X','Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W'},
			{'Y','Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X'},
			{'Z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y'}
		};
	
	public static final int MIN_KEY_LENGTH = 10;
	public static final int MAX_KEY_LENGTH = 100;
	private char[] key = null;
	
	public Vigenere(String key) throws Exception {
		setKey(key);
	}
	
	public void setKey(String key) throws Exception {
		validateKey(key);
		this.key = key.trim().toUpperCase().toCharArray();
	}

	private void validateKey(String key) throws Exception {
		if (key == null || key.length() < MIN_KEY_LENGTH || key.length() > MAX_KEY_LENGTH) {
			throw new Exception("[ERROR] Invalid cipher key. Key length must be in range [" + MIN_KEY_LENGTH + ", " + MAX_KEY_LENGTH + "].");
		}
	}
	
	public String encrypt(String plainText) throws Exception {
		return doCipher(plainText, true);
	}
		
	public String decrypt(String cipherText) throws Exception {
		return doCipher(cipherText, false);
	}
	
	private String doCipher(String s, boolean encrypt) throws Exception {
		validateText(s);
		
		char[] localKey = s.length() > key.length ? getPaddedKey(s) : key;
				
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < s.length(); i++) {
			char c = encrypt? getEncryptedCharacter(localKey[i], s.charAt(i)) : getDecryptedCharacter(localKey[i], s.charAt(i));
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	private void validateText(String text) throws Exception {
		if (text == null || text.length() < MIN_KEY_LENGTH) throw 
			new Exception("[ERROR] Invalid cipher text. The text must be at least " + MIN_KEY_LENGTH + " characters in length.");
	
	}
	
	private char[] getPaddedKey(String s) {
		char[] padded = new char[s.length()];
		
		int index = 0;
		
		for (int i = 0; i < s.length(); i++) {
			padded[i] = key[index];
			index++;
			
			if (index == key.length) index = 0;
		}
		
		return padded;
	}
	
	
	// Intersection of row with the key and the column with plain
	private char getEncryptedCharacter(char key, char plain) {
		for (int row = 0; row < TABULA_RECTA.length; row++) {
			if (TABULA_RECTA[row][0] == key) {
				for (int col = 0; col < TABULA_RECTA[row].length; col++) {
					if (TABULA_RECTA[0][col] == plain) {
						return TABULA_RECTA[row][col];
					}
				}
			}
		}
		
		return plain;
	}
	
	//Find col with the key and read down for the row with the cipher
	private char getDecryptedCharacter(char key, char cipher) {
		for (int col = 0; col < TABULA_RECTA[0].length; col++) {
			if (TABULA_RECTA[0][col] == key) {
				for (int row = 0; row < TABULA_RECTA.length; row++) {
					if (TABULA_RECTA[row][col] == cipher) {
						return TABULA_RECTA[row][0];
					}
				}
			}
		}		
		
		return cipher;
	}
}
