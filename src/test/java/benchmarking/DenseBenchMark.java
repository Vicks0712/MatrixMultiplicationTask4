package benchmarking;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;
import com.model.operations.parallelism.streams.StreamsDenseMatrixMultiplication;
import com.model.operations.sequential.RowMatrixMultiplication;
import com.model.operations.sequential.StandardMatrixMultiplication;
import com.model.operations.sequential.TransposedMatrixMultiplication;
import com.model.operations.parallelism.executor.ESDenseMatrixMultiplication;
import com.model.operations.parallelism.streams.StreamDenseMatrixMultiplication;
import com.model.operations.synchronization.atomic.AtomicDenseMatrixMultiplication;
import com.model.operations.synchronization.blocks.SBMatrixMultiplication;
import com.model.operations.synchronization.semaphores.ParallelSemaphoreDenseMatrixMultiplication;
import com.model.operations.synchronization.threads.ParallelMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DenseBenchMark {
    private static final int SIZE = 1024;

    @Benchmark
    public void standardMatrixMultiplication() {
        executeWith(new StandardMatrixMultiplication());
    }

    @Benchmark
    public void rowMultiplication() {
        executeWith(new RowMatrixMultiplication());
    }

    @Benchmark
    public void transposedMultiplication() {
        executeWith(new TransposedMatrixMultiplication());
    }

    @Benchmark
    public void executorServiceMultiplication() {executeWith(new ESDenseMatrixMultiplication());
    }

    @Benchmark
    public void synchronizedBlocksMultiplication() {executeWith(new SBMatrixMultiplication());
    }

    @Benchmark
    public void parallelThreadMultiplication() {
        executeWith(new ParallelMatrixMultiplication());
    }

    @Benchmark
    public void singleStreamMultiplication() {
        executeWith(new StreamDenseMatrixMultiplication());
    }

    @Benchmark
    public void streamsMultiplication() {executeWith(new StreamsDenseMatrixMultiplication());
    }

    @Benchmark
    public void singleSemaphoresMultiplication() {executeWith(new ParallelSemaphoreDenseMatrixMultiplication());
    }

    @Benchmark
    public void atomicMultiplication() {executeWith(new AtomicDenseMatrixMultiplication());
    }

    private void executeWith(MatrixMultiplication matrixMultiplication) {
        matrixMultiplication.multiply(createRandomMatrix(SIZE), createRandomMatrix(SIZE));
    }

    private DenseMatrix createRandomMatrix(int size) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(size, size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                builder.set(i, j, random.nextDouble());
            }
        }
        return builder.toMatrix();
    }


}
