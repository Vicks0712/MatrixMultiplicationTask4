package com.model.builders;

import com.model.Builder;
import com.model.matrixes.SparseMatrixCRS;
import com.model.matrixes.SparseMatrixCOO;

public class SparseMatrixCRSBuilder implements Builder {
    public SparseMatrixCRS compressedByRow;

    public SparseMatrixCRSBuilder(int size){
        this.compressedByRow = new SparseMatrixCRS(size);
    }

    @Override
    public SparseMatrixCRS toMatrix(){
        return this.compressedByRow;
    }

    public SparseMatrixCRSBuilder compressByRow(SparseMatrixCOO sparseMatrix) {

        for (int pointer = 0; pointer < sparseMatrix.getRow_indexes().size(); pointer++) {
            int position = this.compressedByRow.rowPointers[sparseMatrix.getRow_indexes().get(pointer)];
            while (position < this.compressedByRow.rowPointers[sparseMatrix.getRow_indexes().get(pointer) + 1]){
                if(this.compressedByRow.columns.get(position) > sparseMatrix.getColumn_indexes().get(pointer)) break;
                position++;
            }
            this.setValue(position, sparseMatrix.getValues().get(pointer), sparseMatrix.getColumn_indexes().get(pointer));
            this.modifyRowPointer(sparseMatrix.getRow_indexes().get(pointer));
        }
        return this;
    }

    public void setValue(int position, double value, int column) {
        this.compressedByRow.values.add(position, value);
        this.compressedByRow.columns.add(position, column);
    }

    public void setRowPointers(int row, int rowPointer){
        this.compressedByRow.rowPointers[row] = rowPointer;
    }
    public void modifyRowPointer(int row) {
        for (int i = row + 1; i <= this.compressedByRow.size; i++)
            this.compressedByRow.rowPointers[i] += 1;
    }
}
