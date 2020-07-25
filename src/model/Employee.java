/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class Employee {

    protected String id, name, gt, taik, password, chucvu, hlam;
    protected int tuoi;
 

    public Employee() {
    }

    public Employee(String id, String name, int tuoi, String gt, String taik, String password, String chucvu, String hlam) {
        this.id = id;
        this.name = name;
        this.tuoi = tuoi;
        this.gt = gt;
        this.taik = taik;
        this.password = password;
        this.chucvu = chucvu;
        this.hlam = hlam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getTaik() {
        return taik;
    }

    public void setTaik(String taik) {
        this.taik = taik;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getHlam() {
        return hlam;
    }

    public void setHlam(String hlam) {
        this.hlam = hlam;
    }
    
    
}
