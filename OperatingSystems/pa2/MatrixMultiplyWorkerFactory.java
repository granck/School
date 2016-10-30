package csci4401.service;

import java.io.Serializable;

/**
 * An implementation of the factory specialized for matrix multiplication workers.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixMultiplyWorkerFactory implements ServiceWorkerFactory {
    /**
     * Instantiates a new matrix multiplication service worker.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, MsgQ resultQ) {
        return new MatrixMultiplyWorker( (MatrixMultiplyParameters)parameters, resultQ);
    }
}
