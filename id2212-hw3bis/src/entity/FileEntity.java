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
    public boolean privacy;
    public boolean permission;
    private byte[] content;
    
    public FileEntity(String name, Integer size, String owner, boolean privacy, boolean perm) {
        this.name=name;
        this.size=size;
        this.ownerName=owner;
        this.privacy=privacy;
        this.permission=perm;
    }
    
    public void setContent(byte[] c) {
        this.content = c;
    }
}
