package csci4401.service;

/**
 * Example test code; lacks timing. Use as needed.
 */
public class TestService {

    public static void main(String[] argv) throws Exception {
        sequentialTest();
        parallelTest();
    }
    /**
     * Launches the workers one after the other.
     */
    static private void sequentialTest() throws Exception {
        MatrixMultiplyServicePool servicePool = new MatrixMultiplyServicePool(5, 10);

        int max = 8;
        System.out.println("Non-parallel");
        for(int i=0; i<max; i++) {
            servicePool.addRequest(new MatrixMultiplyParameters(500, 10));
            System.out.println(servicePool.getResponse());
        }
    }

    /**
     * Launches the workers in parallel.
     */
    static private void parallelTest() throws Exception {
        MatrixMultiplyServicePool servicePool = new MatrixMultiplyServicePool(5, 10);

        int max = 8;
        for(int i=0; i<max; i++) {
            servicePool.addRequest(new MatrixMultiplyParameters(500, 10));
        }
        System.out.println("parallel");
        for(int i=0; i<max; i++) {
            System.out.println(servicePool.getResponse());
        }
    }
}
