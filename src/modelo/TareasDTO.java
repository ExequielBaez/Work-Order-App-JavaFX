package modelo;

/**
 *
 * @author Exe
 */
public class TareasDTO {
    
    private String fechaTarea, tecnico, materiales, estado, tarea, tipo;
    private int idTarea, cantidadTecnico, cantidadSoft, cantidadHard;

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(String fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadTecnico() {
        return cantidadTecnico;
    }

    public void setCantidadTecnico(int cantidadTecnico) {
        this.cantidadTecnico = cantidadTecnico;
    }

    public int getCantidadSoft() {
        return cantidadSoft;
    }

    public void setCantidadSoft(int cantidadSoft) {
        this.cantidadSoft = cantidadSoft;
    }

    public int getCantidadHard() {
        return cantidadHard;
    }

    public void setCantidadHard(int cantidadHard) {
        this.cantidadHard = cantidadHard;
    }
    
    
    
}
