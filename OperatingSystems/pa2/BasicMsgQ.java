package csci4401.service;

import java.io.Serializable;
import java.util.*;

/**
 * <b>[TODO]</b> Basic queue implementation.
 * Implement missing functionality in this class.
 */
public class BasicMsgQ implements MsgQ {

	LinkedList<Serializable> resultQueue = new LinkedList<Serializable>();
    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public void append(Serializable message) {
    	if(resultQueue.size() == 0){
    		resultQueue.add(message);
    		synchronized(this){
    	   		this.notify();
    		}
    	}
    	resultQueue.add(message);
    }

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable pop() throws InterruptedException {
    	Serializable message = null;
    	while(resultQueue.size() == 0){
    		System.out.println(resultQueue.isEmpty());
    		System.out.println("waiting");
    		synchronized(this){
    			this.wait();
    		}
    	}
   		message = resultQueue.removeFirst();
    	return message;
    }

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable asyncPop() {
    	Serializable message = null;
    	if(resultQueue.size() != 0)
    		message = resultQueue.remove();
        return message;
    }

}
