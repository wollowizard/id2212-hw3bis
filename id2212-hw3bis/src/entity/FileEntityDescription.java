/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alfredo
 */
public class FileEntityDescription implements Serializable{

    public static final String PUBLICACCESS = "pubacc";
    public static final String PRIVATEACCESS = "privateacc";
    public String name;
    public Integer size;
    public String ownerName;
    public boolean privateFile;
    public boolean writepermission;
    public Date lastModified;

    public FileEntityDescription(String name, Integer size, String owner, boolean privateFile, boolean writepermission, Date last) {
        this.name = name;
        this.size = size;
        this.ownerName = owner;
        this.privateFile = privateFile;
        this.writepermission = writepermission;
        this.lastModified=last;
    }
}
