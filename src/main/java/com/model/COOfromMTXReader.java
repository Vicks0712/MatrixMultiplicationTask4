package com.model;

import java.io.*;

public interface COOfromMTXReader {

    default BufferedReader MTXReader(String filename) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        return br;
    }

    default String[] getMatrixFormat(BufferedReader br) throws IOException {
        String line = null;
        boolean comment = true;
        while (comment) {
            line = br.readLine();
            comment = line.startsWith("%");
        }
        return line.split("( )+");
    }

}
