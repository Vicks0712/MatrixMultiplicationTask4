package com.model.matrixes;

import com.model.Matrix;

public class DenseMatrix implements Matrix {

    private double[][] matrix;
    public DenseMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return this.matrix.length;
    }

    public double get(int row, int column) {
        return this.matrix[row][column];
    }

    public void printear() {
        for (int x=0; x < 8; x++) {
            for (int y=0; y < 8; y++) {
                System.out.print (this.matrix[x][y]);
                if (y!=this.matrix[x].length-1) System.out.print("\t");
            }
            System.out.print("\n");
        }
    }
}
