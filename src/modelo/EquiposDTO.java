package modelo;

import java.io.InputStream;


public class EquiposDTO {
    
    private boolean internet, proxy, m, intra; 
    private String nroPc, dependencia, marca, pm, proce, mem, disco, so, rutaFoto, busca, ip;
    private int idPc;
    private InputStream miFoto;
            
       
    public int getIdPc() {
        return idPc;
    }

    public void setIdPc(int idPc) {
        this.idPc = idPc;
    }
    
    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public boolean isM() {
        return m;
    }

    public void setM(boolean m) {
        this.m = m;
    }

    public boolean isIntra() {
        return intra;
    }

    public void setIntra(boolean intra) {
        this.intra = intra;
    }

    public String getNroPc() {
        return nroPc;
    }

    public void setNroPc(String nroPc) {
        this.nroPc = nroPc;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getProce() {
        return proce;
    }

    public void setProce(String proce) {
        this.proce = proce;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public InputStream getMiFoto() {
        return miFoto;
    }

    public void setMiFoto(InputStream miFoto) {
        this.miFoto = miFoto;
    }

    

    

    
    
    

    

    

    
    

    
    
    
    
    
}
