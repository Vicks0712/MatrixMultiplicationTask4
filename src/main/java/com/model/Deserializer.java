package com.model;

import java.io.IOException;

public interface Deserializer {
    Matrix deserialize(String filename) throws IOException;
    void setMatrixFormat(String[] matrixFormat);
}
