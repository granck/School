package csci4401.service;

/**
 * Simple class (struct) to pass on parameters for the matrix multiplication computation.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixMultiplyParameters implements java.io.Serializable {

    public int matrixSize, iterations;

    /**
     * @param matrixSize    size of the matrix (e.g. 500)
     * @param iterations    how many multiplacations should be performed.
     */
    public MatrixMultiplyParameters(int matrixSize, int iterations) {
            this.matrixSize = matrixSize;
            this.iterations = iterations;
    }

}
