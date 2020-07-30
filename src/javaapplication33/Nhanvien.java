
package javaapplication33;

public class Nhanvien {
    protected String id, name;
    protected int tuoi;
    protected String gt, taik, password, chucvu,hlam;
    protected double luong;
    public Nhanvien() {
    }

    public Nhanvien(String id, String name, int tuoi, String gt, String taik, String password, String chucvu, String hlam, double luong) {
        this.id = id;
        this.name = name;
        this.tuoi = tuoi;
        this.gt = gt;
        this.taik = taik;
        this.password = password;
        this.chucvu = chucvu;
        this.hlam = hlam;
        this.luong = luong;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public void setTaik(String taik) {
        this.taik = taik;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public void setHlam(String hlam) {
        this.hlam = hlam;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public String getGt() {
        return gt;
    }

    public String getTaik() {
        return taik;
    }

    public String getPassword() {
        return password;
    }

    public String getChucvu() {
        return chucvu;
    }

    public String getHlam() {
        return hlam;
    }

    public double getLuong() {
        return luong;
    }

    @Override
    public String toString() {
        return "Nhanvien{" + "id=" + id + ", name=" + name + ", tuoi=" + tuoi + ", gt=" + gt + ", taik=" + taik + ", password=" + password + ", chucvu=" + chucvu + ", hlam=" + hlam + ", luong=" + luong + '}';
    }

    
   
    
}
