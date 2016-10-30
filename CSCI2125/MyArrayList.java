/**
 * MyArrayList.java
 * implements several methods from iterable and creates an iterator
 * Garrick Ranck, August 31 2015
 * CSCI2125
 **/

import java.util.*;

public class MyArrayList<L> implements Iterable<L>{

   private int theSize; //holds size of arraylist
   private L[] theItems; //array of type L
   private static final int DEFAULT_CAPACITY = 20; //default size of arraylist
   public Iterator<L> myIterator = iterator();

   public MyArrayList(){
      theSize = 0;
      theItems = (L[]) new Object[DEFAULT_CAPACITY];

   }//end constructor

   public Iterator<L> iterator(){
      return new ArrayListIterator<L>(this);

   }//end method iterator
   //method adds an element to arraylist
   public void addItem(L val){

      //if size is already at max, don't add element to array
      if( theSize > DEFAULT_CAPACITY){ 
         System.out.println("No room left in array"); 
      }//end if 

      //else room left to add value to end of array 
      else{
         theItems[theSize] = val;
         theSize++;
      }//end else

   }//end method addItem

   //returns size of arraylist
   public int size(){
      return theSize; 

   }//end method size

   //adds element to arraylist at given index
   //increases index of all elements after given index by 1
   public void addAtIndex(int index, L val){

      //if size is already at max, don't add element to array
      if( theSize > DEFAULT_CAPACITY){
         System.out.println("No room left in array");
      }//end if
      //else adds val to specific index and shifts all other elements right
      else if(index > theSize)
         System.out.println("Trying to add values beyond elements in use.");
      else{
         L element;

         for(int i = theSize; i > index; i--){
            theItems[i] = theItems[i - 1];
         }//end for
         theItems[index] = val;
         theSize++;

      }//end else

   }//end method addAtIndex


   private static class ArrayListIterator<L> implements java.util.Iterator<L>{

      private int current = 0;
      private MyArrayList<L> theList;


      public ArrayListIterator(MyArrayList<L> list){
         theList = list;   
      }//end method ArrayListIterator

      public boolean hasNext(){
         return current < theList.size();
      }//end method hasNext

      public L next(){
         return theList.theItems[current++];
      }//end method next

   }//end private inner class ArrayListIterator

}//end class MyArrayList


