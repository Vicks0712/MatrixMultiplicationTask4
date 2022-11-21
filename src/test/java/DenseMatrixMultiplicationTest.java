import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;
import com.model.operations.parallelism.streams.StreamsDenseMatrixMultiplication;
import com.model.operations.sequential.RowMatrixMultiplication;
import com.model.operations.sequential.StandardMatrixMultiplication;
import com.model.operations.parallelism.executor.ESDenseMatrixMultiplication;
import com.model.operations.parallelism.streams.StreamDenseMatrixMultiplication;
import com.model.operations.sequential.TransposedMatrixMultiplication;
import com.model.operations.synchronization.atomic.AtomicDenseMatrixMultiplication;
import com.model.operations.synchronization.blocks.SBMatrixMultiplication;
import com.model.operations.synchronization.semaphores.ParallelSemaphoreDenseMatrixMultiplication;
import com.model.operations.synchronization.threads.ParallelMatrixMultiplication;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class DenseMatrixMultiplicationTest {

    public int size;


    public DenseMatrixMultiplicationTest(int size) {
        this.size = size;
    }


    @Test
    public void multiply_two_random_dense_matrix_with_standard_algorithm() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        StandardMatrixMultiplication standardMultiplication = new StandardMatrixMultiplication();
        DenseMatrix c = standardMultiplication.multiply(denseMatrixA,denseMatrixB);
        Vector vector = new Vector(this.size);
        Assertions.assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixA));
    }


    @Test
    public void multiply_two_random_dense_matrix_with_row_algorithm() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        RowMatrixMultiplication rowMultiplication = new RowMatrixMultiplication();
        DenseMatrix c = rowMultiplication.multiply(denseMatrixA,denseMatrixB);
        Vector vector = new Vector(this.size);
        Assertions.assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixA));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_transpose_algorithm() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        TransposedMatrixMultiplication transposedMultiplication= new TransposedMatrixMultiplication();
        DenseMatrix c = transposedMultiplication.multiply(denseMatrixA,denseMatrixB);
        Vector vector = new Vector(this.size);
        Assertions.assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixA));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_a_stream() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        StreamDenseMatrixMultiplication streamMultiplication = new StreamDenseMatrixMultiplication();
        DenseMatrix c = streamMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_Executor_Service() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        ESDenseMatrixMultiplication esDenseMatrixMultiplication = new ESDenseMatrixMultiplication();
        DenseMatrix c = esDenseMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_threads() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        ParallelMatrixMultiplication parallelMatrixMultiplication = new ParallelMatrixMultiplication();
        DenseMatrix c = parallelMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_Synchronized_Blocks() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        SBMatrixMultiplication sbMatrixMultiplication = new SBMatrixMultiplication();
        DenseMatrix c = sbMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_a_semaphore() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        ParallelSemaphoreDenseMatrixMultiplication parallelSemaphoresMatrixMultiplication = new ParallelSemaphoreDenseMatrixMultiplication();
        DenseMatrix c = parallelSemaphoresMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_three_streams() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        StreamsDenseMatrixMultiplication streamsDenseMatrixMultiplication = new StreamsDenseMatrixMultiplication();
        DenseMatrix c = streamsDenseMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    @Test
    public void multiply_two_random_dense_matrix_with_atomics() {
        DenseMatrix denseMatrixA = createRandomMatrix(this.size);
        DenseMatrix denseMatrixB = createRandomMatrix(this.size);
        AtomicDenseMatrixMultiplication atomicDenseMatrixMultiplication = new AtomicDenseMatrixMultiplication();
        DenseMatrix c = atomicDenseMatrixMultiplication.multiply(denseMatrixA, denseMatrixB);
        Vector vector = new Vector(this.size);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(denseMatrixA).multiply(denseMatrixB));
    }

    private DenseMatrix createRandomMatrix(int size) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(this.size, this.size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                builder.set(i, j, random.nextDouble());
            }
        }
        return builder.toMatrix();
    }


    @Parameterized.Parameters
    public static List<Integer> parameters(){
        return Arrays.asList(10,100,1000);
    }
}