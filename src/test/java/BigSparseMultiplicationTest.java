import com.model.builders.DenseMatrixBuilder;
import com.model.builders.SparseMatrixCCSBuilder;
import com.model.builders.SparseMatrixCRSBuilder;
import com.model.deserializer.COOfromMTXtoSparseMatrixCOOObjectDeserializer;
import com.model.matrixes.DenseMatrix;
import com.model.matrixes.SparseMatrixCCS;
import com.model.matrixes.SparseMatrixCOO;
import com.model.matrixes.SparseMatrixCRS;
import com.model.operations.sequential.SparseMatrixMultiplication;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;


public class BigSparseMultiplicationTest {
    public int size;


    @Test
    public void multiply_two_MTX_Compressed_Matrix() throws IOException {

        COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
        String filename = System.getProperty("user.dir") + "/src/test/mtx/pdb1HYS.mtx";
        SparseMatrixCOO sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);

        SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
        SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

        SparseMatrixCOO resultCOO = new SparseMatrixMultiplication().multiply(sparseMatrixCRS,sparseMatrixCCS);

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