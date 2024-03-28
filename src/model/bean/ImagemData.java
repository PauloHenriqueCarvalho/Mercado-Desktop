/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.io.FileInputStream;

/**
 *
 * @author paulo
 */
public class ImagemData {
  
    private FileInputStream fis;
    private int size;


    public ImagemData(FileInputStream fis, int size) {
        this.fis = fis;
        this.size = size;
    }
    
    public FileInputStream getFileInputStream() {
        return fis;
    }

    public int getSize() {
        return size;
    }
    


}
