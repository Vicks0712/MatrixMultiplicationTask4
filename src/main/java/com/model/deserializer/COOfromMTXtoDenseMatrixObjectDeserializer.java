package com.model.deserializer;
import com.model.Deserializer;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;
import java.io.BufferedReader;
import java.io.IOException;
import com.model.COOfromMTXReader;

public class COOfromMTXtoDenseMatrixObjectDeserializer implements COOfromMTXReader, Deserializer {
    private int nRows;
    private int nColumns;
    private int nNonZeros;

    public COOfromMTXtoDenseMatrixObjectDeserializer() {
        this.nRows = 0;
        this.nColumns = 0;
        this.nNonZeros = 0;
    }

    @Override
    public DenseMatrix deserialize(String filename) throws IOException {

        BufferedReader br = MTXReader(filename);
        String[] matrixFormat = getMatrixFormat(br);

        setMatrixFormat(matrixFormat);
        String line;
        DenseMatrixBuilder denseMatrixBuilder = new DenseMatrixBuilder(this.nRows, this.nColumns);

        while (true) {
            line = br.readLine();
            if (line == null)  break;
            matrixFormat = line.split("( )+");

            denseMatrixBuilder.set(Integer.parseInt(matrixFormat[0]) - 1,
                    Integer.parseInt(matrixFormat[1]) - 1,
                    Double.parseDouble(matrixFormat[2]));
        }
        return denseMatrixBuilder.toMatrix();
    }

    @Override
    public void setMatrixFormat(String[] matrixFormat) {
        this.nRows = Integer.parseInt(matrixFormat[0]);
        this.nColumns = Integer.parseInt(matrixFormat[1]);
        this.nNonZeros = Integer.parseInt(matrixFormat[2]);
    }

}
