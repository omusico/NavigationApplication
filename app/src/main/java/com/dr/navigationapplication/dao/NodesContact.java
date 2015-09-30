package com.dr.abcd.dao;

import com.dr.navigationapplication.dao.InfoBaseTable;

/**
 * Created by only乐秘 on 2015-09-20.
 *
 * nodes_contract表
 */
public class NodesContact extends InfoBaseTable {

    private int id;
    private int n1;
    private int n2;
    private int pid;
    private int fn;

    public NodesContact() {
    }

    public NodesContact(int id, int n1, int n2, int pid, int fn) {
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        this.pid = pid;
        this.fn = fn;
    }

    public int getId() {
        return id;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NodesContact{" +
                "id=" + id +
                ", n1=" + n1 +
                ", n2=" + n2 +
                ", pid=" + pid +
                ", fn=" + fn +
                '}';
    }


    @Override
    public String toJson() {
        String json = "{" +
                "\"id\":" + "\"" + id + "\"" +
                ",\"n1\":" + "\"" + n1 + "\"" +
                ",\"n2\":" + "\"" + n2 + "\"" +
                ",\"pid\":" + "\"" + pid + "\"" +
                ",\"fn\":" + "\"" + fn + "\"" +
                "}";
        return json;
    }
}
