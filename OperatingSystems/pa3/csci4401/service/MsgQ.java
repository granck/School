package csci4401.service;

import java.io.Serializable;

/**
 * Defines a simple message queue service.
 * <b>Provided class--do not modify</b>.
 */
public interface MsgQ {
    /**
     * Appends a message to the end of the queue. If any threads are blocked waiting, ONE of them is unblocked.
     * The caller is NEVER blocked.
     * @param message the message to be enqueued.
     */
    public void append(Serializable message);

    /**
     * Extracts and returns the a message at the head of the queue--BLOCKING version.
     *
     * @return the message at the head of the queue. If the queue is empty, the caller is blocked until an <pre>append</pre> call (eventually) unblocks it.
     */
    public Serializable pop() throws InterruptedException;

    /**
     * Extracts and returns the a message at the head of the queue--NON-BLOCKING version.

     * @return the message at the head of the queue. If the queue is empty, the method returns <pre>null</pre>; under NO circumstances does it block the calling thread.
     */
    public Serializable asyncPop();

}
