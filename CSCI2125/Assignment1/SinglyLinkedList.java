/**
 * SinglyLinkedList.java
 * Singly linked list with inner class Iterator and inner class Node
 * Garrick Ranck, Octboer 5, 2015
 * CSCI2125
 **/

import java.util.*;

public class SinglyLinkedList<Element> implements Iterable<Element>{

   private int theSize; //holds size of arraylist
   /*variables from arraylist
     private L[] theItems; //array of type L
     private static final int DEFAULT_CAPACITY = 20; //default size of arraylist
     */
   public Node<Element> list;
   public Iterator<Element> myIterator = iterator();

   public SinglyLinkedList(Element val){
      theSize = 0;
      list = new Node<Element>(val);

   }//end constructor

   public Iterator<Element> iterator(){
      return new ArrayListIterator<Element>(this);

   }//end method iterator
   //method adds an element to list
   public void addNode(Element val){

      theSize++;

   }//end method addItem

   //returns size of arraylist
   public int size(){
      return theSize; 

   }//end method size

   //adds element to arraylist at given index
   //increases index of all elements after given index by 1
   public void addAtIndex(int index, Element val){

      theSize++;
   }//end method addAtIndex


   private static class ArrayListIterator<Element> implements java.util.Iterator<Element>{

      private int current = 0;
      private SinglyLinkedList<Element> theList;


      public ArrayListIterator(SinglyLinkedList<Element> list){
         theList = list;   
      }//end method ArrayListIterator

      public boolean hasNext(){
         return current < theList.size();
      }//end method hasNext

      public Element next(){
         return theList.list.getNext();
      }//end method next

   }//end private inner class ArrayListIterator

   //inner class contains data of an element in linked list
   private class Node<Element>{ 

      private Element data; //contains generic value of type Element
      private Node<Element> next; //next linked node in the list
      private Node<Element> head; //defines node as the first in list
      private Node<Element> tail; //defines node as last in list

      //creates node with input as value 
      public Node (Element data){
         this.data = data;

      }//end constructor

      //assigns value of next node as the input
      //@requires input = Node of type Element
      public void setNext(Node<Element> next){
         this.next = next;

      }//end method setNext

      //assigns value of current node as the input
      //@requires input = value of type Element
      public void setData(Element value){
         this.data = value;

      }//end method setValue

      //gets next node in linked list
      public Node<Element> getNext(){
         return this.next;

      }//end method getNext

      //returns value of this node
      public Element getData(){
         return this.data;

      }//end method getData

   }//end class Node

}//end class MyArrayList


