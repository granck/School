package csci4401.service;

import java.io.Serializable;

/**
 * Simple implementaion of a service pool for matrix multiplication workers.
 * Most of the work is done by the worker (computation) and the message queue implementation (synchronization).
 * <b>Provided class--do not modify</b>.
 */
public class MatrixMultiplyServicePool implements ServicePool {

    int poolMin, poolMax, poolSize=0;
    MsgQ resultQ = new BasicMsgQ();
    MatrixMultiplyWorkerFactory factory = new MatrixMultiplyWorkerFactory();

    /**
     * @param poolMin   minimum pool size (not used)
     * @param poolMax   maximum pool size (not used)
     */
    public MatrixMultiplyServicePool(int poolMin, int poolMax) {
        this.poolMin = poolMin;
        this.poolMax = poolMax;
    }
    /**
     * Trivial implementation: every request triggers the creation of a new thread, which at the end of the computation dies (it is NOT reused).
     */
    public void addRequest(Serializable request) {
        factory.newServiceWorker(request, resultQ).start();
    }

    /**
     * Pops the first response from the queue. BLOCKING.
     */
    public Serializable getResponse() throws InterruptedException {
        return resultQ.pop();
    }
}
