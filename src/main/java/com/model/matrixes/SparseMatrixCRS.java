package com.model.matrixes;

import com.model.Matrix;

import java.util.ArrayList;

public class SparseMatrixCRS implements Matrix {
    public int size;
    public ArrayList<Double> values;
    public ArrayList<Integer> columns;
    public int[] rowPointers;

    public SparseMatrixCRS(int size) {
        this.size = size;
        this.values = new ArrayList<Double>();
        this.columns = new ArrayList<Integer>();
        this.rowPointers = new int[size + 1];
    }

    @Override
    public int size() {
        return this.size;
    }

    public SparseMatrixCRS setPointers_to_the_first_non_null_elements_of_each_row_pointers(int[] rowPointers) {
        this.rowPointers = rowPointers;
        return this;
    }

    public SparseMatrixCRS setColumn_indexes(ArrayList<Integer> columns) {
        this.columns = columns;
        return this;
    }

    public SparseMatrixCRS setValues(ArrayList<Double> values) {
        this.values = values;
        return this;
    }
}
