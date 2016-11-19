import java.util.Scanner;

public class encrypt{

	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a string to encrypt.");

		String nonEncrypt = input.nextLine();
		char[] nonEncryptTokens = nonEncrypt.toCharArray();
		System.out.println(nonEncryptTokens);
		System.out.println("what is the key: ");
		String key = input.nextLine();
		char[] keyArray = key.toCharArray();

		char[] encrypted = encryptMessage(nonEncryptTokens, keyArray);
		System.out.println(encrypted);



	}//end main method

	public static char[] encryptMessage(char[] message, char[] key){
		
		int lengthMessage = message.length;
		int lengthKey = key.length;
		int keyMarker = 0;

		for(int counter = 0; counter < lengthMessage; counter++){
			
			if(keyMarker >= lengthKey){
				keyMarker = 0;
			}//end if

			message[counter] = (char)(((message[counter] + key[keyMarker]) % 95) + ' ');
			keyMarker++;


		}//end outer for

		return message;

	}//end method encryptMesssage

	//not currently implemented
	public static char[] decryptMessage(char[] message, char[] key){
		
		int lengthMessage = message.length;
		int lengthKey = key.length;
		int keyMarker = 0;

		for(int counter = 0; counter < lengthMessage; counter++){
			
			if(keyMarker >= lengthKey){
				keyMarker = 0;
			}//end if
		

		}//end for

		return message;
	}//end method decryptMessage
}//end class encrypt
