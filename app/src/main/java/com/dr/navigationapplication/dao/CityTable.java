package com.dr.navigationapplication.dao;

/**
 * Created by only乐秘 on 2015-09-20.
 * <p/>
 * city表
 */
public class CityTable extends InfoBaseTable {

    private int id;
    private String name;

    public CityTable() {

    }

    public CityTable(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CityTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String toJson() {
        String json = "{" +
                "\"id\":" + "\"" + id + "\"" +
                ",\"name\":" + "\"" + name + "\"" +
                "}";
        return json;
    }
}
