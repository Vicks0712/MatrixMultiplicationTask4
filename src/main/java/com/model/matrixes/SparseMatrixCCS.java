package com.model.matrixes;

import com.model.Matrix;

import java.util.ArrayList;

public class SparseMatrixCCS implements Matrix {
    public int size;
    public ArrayList<Double> values;
    public ArrayList<Integer> rows;
    public int[] colPointers;

    public SparseMatrixCCS(int size) {
        this.size = size;
        this.values = new ArrayList<Double>();
        this.rows = new ArrayList<Integer>();
        this.colPointers = new int[size + 1];

    }

    @Override
    public int size() {
        return this.size;
    }

    public SparseMatrixCCS setRow_indexes(ArrayList<Integer> rowIndexes) {
        this.rows = rowIndexes;
        return this;
    }

    public SparseMatrixCCS setPointers_to_the_first_non_null_elements_of_each_column_pointers(int[] colPointers) {
        this.colPointers = colPointers;
        return this;
    }

    public SparseMatrixCCS setValues(ArrayList<Double> values) {
        this.values = values;
        return this;
    }
}
