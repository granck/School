package csci4401.service;

import java.io.Serializable;

/**
 *   A more advanced implementaion of a service pool for matrix multiplication workers, 
 *   which matches the number of outstanding jobs to the number of available hardware-supported threads.
 *   <b>TODO:</b> Implement this class.
 **/
public class BalancedMMServicePool extends MatrixMultiplyServicePool {
		

	    /**
		  * @param poolMin   minimum pool size (not used)
		  * @param poolMax   maximum pool size (not used)
		  */
	    public BalancedMMServicePool(int poolMin, int poolMax) {
			         super(poolMin, poolMax);
						    }
		/**
		* Notifies the service pool that a worker has completed the computation with the given result.
		* If there are any outstanding requests, the first in line should be serviced.
		* <b>TODO:</b> Implement this method.
		*                          */
	    public void addResult(Serializable result) {}
			synchronized(this){
			this.notify();
			}//notifying service pool that a worker has finished a task
}
