
package javaapplication33;

public class Nhanvien {
    protected String id, name;
    protected int tuoi;
    protected String gt, taik, password, chucvu,hlam;
    public Nhanvien() {
    }

    public Nhanvien(String id, String name, int tuoi, String gt, String taik, String password, String chucvu, String hlam) {
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

    public String getName() {
        return name;
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

    public int getTuoi() {
        return tuoi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    @Override
    public String toString() {
        return "Nhanvien{" + "id=" + id + ", name=" + name + ", gt=" + gt + ", taik=" + taik + ", password=" + password + ", chucvu=" + chucvu + ", hlam=" + hlam + ", tuoi=" + tuoi + '}';
    }

   
    
}
