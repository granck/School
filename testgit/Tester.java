
import java.util.Scanner;

private class Tester{
	
	private int userNumber;
	public Tester(){
		userNumber = 0;
	}//end constructor
	
	//@return userNumber
	//	userNumber that user has provided last
	public int getNumber(){
		
		return userNumber;

	}//end method getNumber
	
	//@param int number
	//	number to set userNumber to
	private void setNumber(int number){
		
		userNumber = number;
	}//end method setNumber

	public void askUser(){
		
		Scanner input = new Scanner(System.in);
		System.out.print("Pick a number: ");
		
		try{
			setNumber(input.nextInt());
		}catch(Exception e){
			System.out.println("Failed to get number.");
		}//end try catch



	}//end method askUser

}//end Tester Class
