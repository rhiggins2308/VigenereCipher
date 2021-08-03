package ie.gmit.dip;

public class Runner {

	public static void main(String[] args) {
		String key = "THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOGS";
		String plainText = "ATTACK THE CASTLE WALL AT DAWN UNTIL DUCK WITH GREAT FURY";

		try {
			Vigenere cipher = new Vigenere(key);
			String cipherText = cipher.encrypt(plainText);
			System.out.println(cipherText);
			System.out.println(cipher.decrypt(cipherText));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
