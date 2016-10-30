/*
 * Garrick Ranck
 * MakeArrayList.java
 * Implements MyArrayList.java to make arrayList
 **/

 import java.util.*;

public class MakeArrayList{

   public static void main(String[] args){

      MyArrayList testArray = new MyArrayList();
      System.out.println("\nTesting size method. No elements. Size: " + testArray.size());

      System.out.println("\nCreating iterator with 0 elements.");
      Iterator iteratorOne = testArray.iterator();
      System.out.println("Empty iterator has next?: " + iteratorOne.hasNext());

      testArray.addItem(23);
      System.out.println("\nAdding 23 to array at index: " + testArray.size());
      testArray.addItem(5);
      System.out.println("Adding 5 to array at index: " + testArray.size());
      testArray.addItem(100);
      System.out.println("Adding 100 to array at index: " + testArray.size());
      
      System.out.println("\nCreating iterator with three elements.");
      iteratorOne = testArray.iterator();
      for(int i = 0; i < testArray.size(); i++){
         System.out.println("Iterator has next: " + iteratorOne.hasNext());
         System.out.println("Iterator value at index " + i + " : " + iteratorOne.next());
      }//end for
      System.out.println("Iterator has next: " + iteratorOne.hasNext());

      testArray.addAtIndex(0, 25);
      System.out.println("\nAdding value 25 to index 0: " + testArray.size());

      System.out.println("\nCreating iterator after shifted elements from addAtIndex method.");
      iteratorOne = testArray.iterator();
      for(int i = 0; i < testArray.size(); i++){
         System.out.println("Iterator has next: " + iteratorOne.hasNext());
         System.out.println("Iterator value at index " + i + " : " + iteratorOne.next());
      }//end for
      System.out.println("Iterator has next: " + iteratorOne.hasNext());
 
      




   }//end main method
}//end class MakeArrayList
