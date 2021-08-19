package id.go.pajak.ibk.model;

import java.io.Serializable;

/**
 * Created by usrdjp on 2/16/2021.
 */
public class ResponData implements Serializable {
    private String npwp;
    private String namaWp;
    private String nik;
    private String stsNasabah;
    private Integer jmlData;

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getNamaWp() {
        return namaWp;
    }

    public void setNamaWp(String namaWp) {
        this.namaWp = namaWp;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getStsNasabah() {
        return stsNasabah;
    }

    public void setStsNasabah(String stsNasabah) {
        this.stsNasabah = stsNasabah;
    }

    public Integer getJmlData() {
        return jmlData;
    }

    public void setJmlData(Integer jmlData) {
        this.jmlData = jmlData;
    }
}
