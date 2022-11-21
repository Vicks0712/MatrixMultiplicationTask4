package com.model.builders;
import com.model.Builder;
import com.model.matrixes.SparseMatrixCOO;
import java.util.ArrayList;
import java.util.List;

public class SparseMatrixCOOBuilder implements Builder {

    private final List<Integer> row_indices = new ArrayList<>();
    private final List<Integer> column_indices = new ArrayList<>();
    private final List<Double> values = new ArrayList<>();

    int size;
    public SparseMatrixCOOBuilder(int size) {
        this.size = size;
    }

    public void set(int row_prt, int column_ind, double value) {
        row_indices.add(row_prt);
        column_indices.add(column_ind);
        values.add(value);
    }

    @Override
    public SparseMatrixCOO toMatrix() {
        return new SparseMatrixCOO(size)
                .setRow_indexes(row_indices)
                .setColumn_indexes(column_indices)
                .setValues(values);
    }
}
