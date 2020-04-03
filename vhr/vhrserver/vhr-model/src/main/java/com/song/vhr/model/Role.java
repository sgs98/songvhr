package com.song.vhr.model;

/**
 *
 * @author song
 * @data 2020/2/19
 */
public class Role {
    private Integer id;

    private String name;

    /**
    * 角色名称
    */
    private String nameZh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamezh() {
        return nameZh;
    }

    public void setNamezh(String namezh) {
        this.nameZh = namezh;
    }
}