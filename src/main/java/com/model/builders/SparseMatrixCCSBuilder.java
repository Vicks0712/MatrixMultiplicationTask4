package com.model.builders;

import com.model.Builder;
import com.model.matrixes.SparseMatrixCCS;
import com.model.matrixes.SparseMatrixCOO;

public class SparseMatrixCCSBuilder implements Builder {
    private final int size;
    public SparseMatrixCCS compressedColumnMatrix;

    public SparseMatrixCCSBuilder(int size) {
        this.compressedColumnMatrix = new SparseMatrixCCS(size);
        this.size = size;
    }

    @Override
    public SparseMatrixCCS toMatrix(){
        return this.compressedColumnMatrix;
    }

    public SparseMatrixCCSBuilder compressByColumn(SparseMatrixCOO sparseMatrix) {

        for (int pointer = 0; pointer < sparseMatrix.getColumn_indexes().size(); pointer++) {
            int position = this.compressedColumnMatrix.colPointers[sparseMatrix.getColumn_indexes().get(pointer)];
            while (position < this.compressedColumnMatrix.colPointers[sparseMatrix.getColumn_indexes().get(pointer) + 1]){
                if(this.compressedColumnMatrix.rows.get(position) > sparseMatrix.getRow_indexes().get(pointer)){
                    break;
                }
                position++;
            }
            this.setValue(position, sparseMatrix.getValues().get(pointer), sparseMatrix.getRow_indexes().get(pointer));
            this.modifyColPointer(sparseMatrix.getColumn_indexes().get(pointer));
        }
        return this;
    }


    public void setValue(int position, double value, int row) {
        this.compressedColumnMatrix.values.add(position, value);
        this.compressedColumnMatrix.rows.add(position, row);
    }

    public void modifyColPointer(int column) {
        for (int i = column + 1; i <= this.compressedColumnMatrix.size; i++)
            this.compressedColumnMatrix.colPointers[i] += 1;
    }

    public void setColumnPointers(int column, int colPointer){
        this.compressedColumnMatrix.colPointers[column] = colPointer;
    }


}
