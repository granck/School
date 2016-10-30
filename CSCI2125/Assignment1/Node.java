public class Node<Element>{
   private Element data;
   private Node<Element> prev; //node that is linked to previous
   private Node<Element> next; //node that is linked to next
   
   public Node (Element data){
      this.data = data;

   }//end constructor

   public void setNext(Node<Element> next){
      this.next = next;

   }//end method setNext

   public void setPrev(Node<Element> prev){
      this.prev = prev;

   }//end method setPrev

   public void setData(Element value){
      this.data = value;

   }//end method setValue

   public Node<Element> getNext(){
      return this.next;

   }//end method getNext

   public Node<Element> getPrev(){
      return this.prev;

   }//end method getPrev

   public Element getData(){
      return this.data;

   }//end method getData
}//end class Node
