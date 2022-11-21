import com.model.builders.SparseMatrixCCSBuilder;
import com.model.builders.SparseMatrixCRSBuilder;
import com.model.builders.DenseMatrixBuilder;
import com.model.deserializer.COOfromMTXtoSparseMatrixCOOObjectDeserializer;
import com.model.matrixes.*;
import com.model.operations.parallelism.executor.ESSparseMatrixMultiplication;
import com.model.operations.parallelism.streams.StreamSparseMatrixMultiplication;
import com.model.operations.sequential.SparseMatrixMultiplication;
import com.model.operations.synchronization.semaphores.ParallelSemaphoreSparseMatrixMultiplication;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class SparseMatrixMultiplicationTest {
    @Test
    public void multiply_two_sparse_matrix_with_standard_algorithm() throws IOException {
        COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
        String filename = System.getProperty("user.dir") + "/src/test/mtx/paper.mtx";
        SparseMatrixCOO sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);

        SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
        SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

        SparseMatrixCOO resultCOO = new SparseMatrixMultiplication().multiply(sparseMatrixCRS,sparseMatrixCCS);

        DenseMatrix result = sparseToDenseMatrix(resultCOO, new DenseMatrixBuilder(resultCOO.size(), resultCOO.size()));
        DenseMatrix matrixToMultiply = sparseToDenseMatrix(sparseMatrixCOO, new DenseMatrixBuilder(sparseMatrixCOO.size(), sparseMatrixCOO.size()));

        Vector vector = new Vector(result.size());
        assertThat(vector.multiply(result)).isEqualTo(vector.multiply(matrixToMultiply).multiply(matrixToMultiply));
    }

    @Test
    public void multiply_two_sparse_matrix_with_a_single_stream() throws IOException {
        COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
        String filename = System.getProperty("user.dir") + "/src/test/mtx/paper.mtx";
        SparseMatrixCOO sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);

        SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
        SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

        SparseMatrixCOO resultCOO = new StreamSparseMatrixMultiplication().multiply(sparseMatrixCRS,sparseMatrixCCS);

        DenseMatrix result = sparseToDenseMatrix(resultCOO, new DenseMatrixBuilder(resultCOO.size(), resultCOO.size()));
        DenseMatrix matrixToMultiply = sparseToDenseMatrix(sparseMatrixCOO, new DenseMatrixBuilder(sparseMatrixCOO.size(), sparseMatrixCOO.size()));

        Vector vector = new Vector(result.size());
        assertThat(vector.multiply(result)).isEqualTo(vector.multiply(matrixToMultiply).multiply(matrixToMultiply));
    }

    @Test
    public void multiply_two_sparse_matrix_with_executor_service() throws IOException {
        COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
        String filename = System.getProperty("user.dir") + "/src/test/mtx/paper.mtx";
        SparseMatrixCOO sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);

        SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
        SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

        SparseMatrixCOO resultCOO = new ESSparseMatrixMultiplication().multiply(sparseMatrixCRS,sparseMatrixCCS);

        DenseMatrix result = sparseToDenseMatrix(resultCOO, new DenseMatrixBuilder(resultCOO.size(), resultCOO.size()));
        DenseMatrix matrixToMultiply = sparseToDenseMatrix(sparseMatrixCOO, new DenseMatrixBuilder(sparseMatrixCOO.size(), sparseMatrixCOO.size()));

        Vector vector = new Vector(result.size());
        assertThat(vector.multiply(result)).isEqualTo(vector.multiply(matrixToMultiply).multiply(matrixToMultiply));
    }

    @Test
    public void multiply_two_sparse_matrix_with_single_sempaahore() throws IOException {
        COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
        String filename = System.getProperty("user.dir") + "/src/test/mtx/paper.mtx";
        SparseMatrixCOO sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);

        SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
        SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

        SparseMatrixCOO resultCOO = new ParallelSemaphoreSparseMatrixMultiplication().multiply(sparseMatrixCRS,sparseMatrixCCS);

        DenseMatrix result = sparseToDenseMatrix(resultCOO, new DenseMatrixBuilder(resultCOO.size(), resultCOO.size()));
        DenseMatrix matrixToMultiply = sparseToDenseMatrix(sparseMatrixCOO, new DenseMatrixBuilder(sparseMatrixCOO.size(), sparseMatrixCOO.size()));

        Vector vector = new Vector(result.size());
        assertThat(vector.multiply(result)).isEqualTo(vector.multiply(matrixToMultiply).multiply(matrixToMultiply));
    }


    public DenseMatrix sparseToDenseMatrix(SparseMatrixCOO sparseMatrixCOO, DenseMatrixBuilder denseMatrixBuilder){
        for(int i = 0; i <  sparseMatrixCOO.getRow_indexes().size(); i++){
            denseMatrixBuilder.set(sparseMatrixCOO.getRow_indexes().get(i), sparseMatrixCOO.getColumn_indexes().get(i),
                    sparseMatrixCOO.getValues().get(i));
        }
        return denseMatrixBuilder.toMatrix();
}

}
