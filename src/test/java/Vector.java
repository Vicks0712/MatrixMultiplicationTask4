import com.model.Matrix;
import com.model.matrixes.DenseMatrix;

import java.util.Arrays;
import java.util.Random;

class Vector {
    public int size;
    public double[] v;
    public static final double EPSILON = 1E-8;


    public Vector (int size) {
        this.size = size;
        this.v = new double[size];
    }

    public Vector createVector() {
        Random random = new Random();
        for(int i = 0; i < this.size; i++){
            this.v[i] = random.nextDouble();
        }
        return this;
    }

    public Vector multiply(DenseMatrix a) {
        Vector finalVector = new Vector(this.size);
        for (int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                finalVector.v[i] += a.get(i,j) * this.v[j];
            }
        }
        return finalVector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        if(size != vector.size) return false;
        for (int i = 0; i < size; i++){
            if(v[i] - vector.v[i] > EPSILON) return false;
        }
        return true;
    }
}
