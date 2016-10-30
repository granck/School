package csci4401.service;

import java.io.Serializable;

/**
 * Defines a factory interface for producing workers.
 * <b>Provided class--do not modify</b>.
 */
public interface ServiceWorkerFactory {
    /**
     * Instantiates a new <pre>ServiceWorker</pre>.
     *
     * @param parameters    parameters of the computation (passed on to the worker's constructor).
     * @param resultQ       a queue to which the worker should deposit its results.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, MsgQ resultQ);
}
