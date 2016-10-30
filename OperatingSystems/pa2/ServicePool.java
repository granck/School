package csci4401.service;

import java.io.Serializable;

/**
 * A service pool spawns service workers (threads) in response to request messages, and provides the means to retrieve the results.
 * <b>Provided class--do not modify</b>.
 */
public interface ServicePool {

    /**
     * Add a request to the pool queue (non-blocking).
     */
    public void addRequest(Serializable request);

    /**
     * Retrieve the response at the head of the queue; if none, the caller is blocked.
     *
     * @return the response at the head of the response queue.
     */
    public Serializable getResponse() throws InterruptedException;

}
