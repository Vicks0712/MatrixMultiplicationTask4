package benchmarking;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.builders.SparseMatrixCCSBuilder;
import com.model.builders.SparseMatrixCOOBuilder;
import com.model.builders.SparseMatrixCRSBuilder;
import com.model.matrixes.*;
import com.model.operations.parallelism.executor.ESSparseMatrixMultiplication;
import com.model.operations.parallelism.streams.StreamSparseMatrixMultiplication;
import com.model.operations.sequential.SparseMatrixMultiplication;
import com.model.operations.synchronization.semaphores.ParallelSemaphoreSparseMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)

public class SparseBenchMark {

    public static final int SIZE = 1024;
    public static final DenseMatrix denseMatrixA = createRandomMatrix(SIZE);
    public static final SparseMatrixCOO sparseMatrixCOO = denseToSparse(denseMatrixA);

    public static final SparseMatrixCCS ccs = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
    public static final SparseMatrixCRS crs = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();



    @Benchmark
    public void standardSparseMultiplication() {
        executeWith(new SparseMatrixMultiplication());
    }

    @Benchmark
    public void executorServiceSparseMultiplication() {
        executeWith(new ESSparseMatrixMultiplication());
    }

    @Benchmark
    public void streamSparseMultiplication() {
        executeWith(new StreamSparseMatrixMultiplication());
    }

    @Benchmark
    public void semaphoreSparseMultiplication() {
        executeWith(new ParallelSemaphoreSparseMatrixMultiplication());
    }

    private void executeWith(MatrixMultiplication matrixMultiplication)  {
        matrixMultiplication.multiply(crs, ccs);
    }


    private static DenseMatrix createRandomMatrix(int size) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(size, size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                builder.set(i, j, random.nextDouble());
            }
        }
        return builder.toMatrix();
    }

    private static SparseMatrixCOO denseToSparse(DenseMatrix denseMatrix) {
        SparseMatrixCOOBuilder sparseMatrixCOOBuilder = new SparseMatrixCOOBuilder(denseMatrix.size());
        for (int i = 0; i < denseMatrix.size(); i++) {
            for (int j = 0; j < denseMatrix.size(); j++) {
                if (denseMatrix.get(i,j) > 1E-5) {
                    sparseMatrixCOOBuilder.set(i,j,denseMatrix.get(i,j));
                }
            }
        }
        return sparseMatrixCOOBuilder.toMatrix();

    }

}

