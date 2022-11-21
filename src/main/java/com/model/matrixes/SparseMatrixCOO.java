package com.model.matrixes;
import com.model.Matrix;

import java.util.List;

public class SparseMatrixCOO implements Matrix {

    private List<Integer> row_indexes;
    private List<Integer> column_indexes;
    private List<Double> values;

    int size;
    public SparseMatrixCOO(int size) {
        this.size = size;
    }

    public SparseMatrixCOO setRow_indexes(List<Integer> row_indexes) {
        this.row_indexes = row_indexes;
        return this;
    }

    public SparseMatrixCOO setColumn_indexes(List<Integer> column_indexes) {
        this.column_indexes = column_indexes;
        return this;
    }

    public SparseMatrixCOO setValues(List<Double> values) {
        this.values = values;
        return this;
    }

    public List<Integer> getRow_indexes() {
        return row_indexes;
    }

    public List<Integer> getColumn_indexes() {
        return column_indexes;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public int size() {
        return this.size;
    }
}
