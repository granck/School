/****************************
 * Permutation.java
 * By: Garrick Ranck
 * Java solution for Programming Language Structures Final Paper
 * Finds all possible sets for a provided max value 'n'
*****************************/

import java.util.Scanner;
import java.util.ArrayList;

public class Permutation{
	
	public static void main(String[] args){

		Scanner input = new Scanner(System.in);

		System.out.print("Enter max value to find sets for: ");
		int permutationValue = input.nextInt();

		ArrayList<String> permutationValues = new ArrayList<String>(permutationValue);
		
		for(int counter = 1; counter <= permutationValue; counter++){
			permutationValues.add(Integer.toString(counter));
		}//end for

		System.out.println(permutate(permutationValues));

		

	}//end main method

	public static int findFactorial(int permutationValue){
		
		int factorial = 0;

		if(permutationValue >= 1){
			factorial = 1;
		}
		for(int counter = 1; counter <= permutationValue; counter++){
			factorial *= counter; 	
		}//end for

		return factorial;

	}//end method findFactorial

	public static String permutate(ArrayList<String> array){
		
		if(array.size() == 1){
			System.out.println();
			return array.get(0);
		}//end if
		else{
			for(int counter = 0; counter < array.size(); counter++){
				
				ArrayList<String> subarray = new ArrayList<String>(array);
				System.out.println("main" + array);
				String removed = array.get(counter);
				subarray.remove(counter);
				System.out.println("sub" + subarray);
				System.out.println("main after " + array);
				return removed + permutate(subarray);
			}//end for
		return "\ndone";
		}//end else

	}//end method permute
}//end class Permutation
