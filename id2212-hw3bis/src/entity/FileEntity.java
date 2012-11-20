/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author alfredo
 */
public class FileEntity implements Serializable{

    private FileEntityDescription description;
    private byte[] content;
    
    public FileEntity(FileEntityDescription fe, byte[] content) {
        this.description=fe;
        this.content=content;
    }
    
    public void setContent(byte[] c) {
        this.content = c;
    }

    public byte[] getContent() {
        return content;
    }

    public FileEntityDescription getDescription() {
        return description;
    }

    public void setDescription(FileEntityDescription description) {
        this.description = description;
    }
    
}
