package csci4401.service;

/**
 * <b>[TODO]</b> Matrix multiplier worker.
 * Implement missing functionality in this class.
 */
public class MatrixMultiplyWorker extends AbstractServiceWorker {

    private double[][] a, b, c;
    private int mSize;
    private int mIterations;

    public MatrixMultiplyWorker(MatrixMultiplyParameters parameters, MsgQ resultQ) {
        super(parameters, resultQ);
        mSize = parameters.matrixSize;
        mIterations = parameters.iterations;
    }

    /**
     * Initializes the matrixes based on the size parameter.
     * <b>TODO:</b> Implement this method.
     */
    private void initMatrixes() {
    	a = new double[mSize][mSize];
    	b = new double[mSize][mSize];
    	c = new double[mSize][mSize];
    	
    	for(int i = 0; i < mSize; i++){
    		for(int j = 0; j < mSize; j++){
    			a[i][j] = (i+1)*(j+1);
    			b[i][j] = (i+2)*(j+2);
    		}
    	}
    	
    }//end method initMatrixes

    /**
     * Performs one iteration of matrix multiplication.
     * <b>TODO:</b> Implement this method.
     */
    private void doMatrixMultiplication() {
    	for(int i = 0; i < mSize; i++){
    		for(int j = 0; j < mSize; j++){
    			for(int k = 0; k < mSize; k++){
    				c[i][j] += a[i][k]*b[k][j];
    			}
    		}
    	}
    }//end method doMatrixMultiplication

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations, <b>without</b> the initialization.
     * <b>TODO:</b> Implement this method.
     */
    public void run() {
    	initMatrixes();
    	long start = System.currentTimeMillis();
    	for(int its = 0; its < mIterations; its++){
    		doMatrixMultiplication();
    	}
    	long end = System.currentTimeMillis();
    	Long time = new Long((end - start) / mIterations);
    	resultQ.append(time);
    	
    }//end method run

}
