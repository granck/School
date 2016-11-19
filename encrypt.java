import java.util.Scanner;

public class encrypt{

	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a string to encrypt.");
		String nonEncrypt = input.nextLine();
		char[] nonEncryptTokens = nonEncrypt.toCharArray();
		System.out.println("what is the key: ");
		String key = input.nextLine();
		char[] keyArray = key.toCharArray();
		System.out.println("Encrypting message.\n\n");
		char[] encrypted = encryptMessage(nonEncryptTokens, keyArray);
		System.out.println("\n\nDecrypting message.");
		char[] decrypted = decryptMessage(encrypted, keyArray);
		System.out.println(decrypted);

		/*
		char[] secretMessage = {'I', ' ','l', 'o', 'v', 'e', ' ', 'y', 'u', 'o', ' ', 's' , 'o', ' ', 'm', 'u', 'c', 'h'};
		char[] keyArray = {'J', 'a', 'd', 'e'};
		
		System.out.println("I have a secret message for you sweety!\n");
		char[] encrypted = encryptMessage(secretMessage, keyArray);
		System.out.println(encrypted);
		System.out.println("You'll need to input the right key in order to decrypt it...");
		System.out.println("If you need help, you can ask for hints. Otherwise type in the key.");
		System.out.println("Type \"hint1\" \"hint2\" or \"hint3\" to get a hint. Case sensitive.");

		while(true){
		
			String jadeInput = input.nextLine();
			if(jadeInput.compareTo("hint1") == 0){
				System.out.println("Your first hint is that it is something I really like.");
			}//end if
			else if(jadeInput.compareTo("hint2") == 0){
				System.out.println("Your second hint is that it looks very cute.");
			}//end else if
			else if(jadeInput.compareTo("hint3") == 0){
				System.out.println("Your third hint is that it has a green color to it.");
			}//end else if
			else if(jadeInput.compareTo("Jade") == 0){
				System.out.println("Baby girl, you are so awesome! You guessed correctly!");
				System.out.println("Let's see what this secret message says...\n");
				char[] decryptedSecret = decryptMessage(encrypted, keyArray);
				System.out.println("Decrypted...");
				System.out.println("Your secret message is..\n");
				System.out.println(decryptedSecret);
				break;

			}//end else if
			else if(jadeInput.compareTo("exit_encryption") == 0){
				break;
			}//end else if exit loop
			else{
				System.out.println("Invalid input. Try again.");
			}//end else


		}//end while loop
		**/


	}//end main method

	public static char[] encryptMessage(char[] message, char[] key){
		
		int lengthMessage = message.length;
		int lengthKey = key.length;
		int keyMarker = 0;

		for(int counter = 0; counter < lengthMessage; counter++){
			
			if(keyMarker >= lengthKey){
				keyMarker = 0;
			}//end if

			//message[counter] = (char)(((message[counter] + key[keyMarker]) % 95) + ' ');
			message[counter] = (char)(((message[counter] + key[keyMarker])) + ' ');
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

			message[counter] = (char)(((message[counter] - key[keyMarker])) - ' ');
			keyMarker++;
		

		}//end for

		return message;
	}//end method decryptMessage
}//end class encrypt
