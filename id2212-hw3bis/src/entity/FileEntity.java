/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author alfredo
 */
public class FileEntity {

    public static final String PUBLICACCESS = "pubacc";
    public static final String PRIVATEACCESS = "privateacc";
    public String name;
    public Integer size;
    public String ownerName;
    public String access;
    public byte[] content;
    
    public FileEntity(String filename, String ownerName, String access, byte[] content){
        this.name=filename;
        this.size=content.length;
        this.ownerName=ownerName;
        this.access=access;
        this.content=content;
    }
}
