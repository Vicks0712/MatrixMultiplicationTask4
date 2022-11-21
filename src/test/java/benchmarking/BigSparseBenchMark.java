package benchmarking;

import com.model.builders.SparseMatrixCCSBuilder;
import com.model.builders.SparseMatrixCRSBuilder;
import com.model.deserializer.COOfromMTXtoSparseMatrixCOOObjectDeserializer;
import com.model.matrixes.SparseMatrixCCS;
import com.model.matrixes.SparseMatrixCOO;
import com.model.matrixes.SparseMatrixCRS;
import com.model.operations.sequential.SparseMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 1, time = 1)
public class BigSparseBenchMark {

    public static COOfromMTXtoSparseMatrixCOOObjectDeserializer sparseMatrixCOOObjectDeserializer = new COOfromMTXtoSparseMatrixCOOObjectDeserializer();
    public static String filename = System.getProperty("user.dir") + "/src/test/mtx/pdb1HYS.mtx";
    public static SparseMatrixCOO sparseMatrixCOO;

    static {
        try {
            sparseMatrixCOO = sparseMatrixCOOObjectDeserializer.deserialize(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SparseMatrixCCS sparseMatrixCCS = new SparseMatrixCCSBuilder(sparseMatrixCOO.size()).compressByColumn(sparseMatrixCOO).toMatrix();
    public static SparseMatrixCRS sparseMatrixCRS = new SparseMatrixCRSBuilder(sparseMatrixCOO.size()).compressByRow(sparseMatrixCOO).toMatrix();

    @Benchmark
    public static void BigCompressedSparseMatrixMultiplication() {
        SparseMatrixMultiplication sparseMatrixMultiplication = new SparseMatrixMultiplication();
        SparseMatrixCOO matrix5 = sparseMatrixMultiplication.multiply(sparseMatrixCRS, sparseMatrixCCS);
    }

}
