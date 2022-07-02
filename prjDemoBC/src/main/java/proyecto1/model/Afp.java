package proyecto1.model;

public class Afp {
    private Integer id;
    private String descripcion;

    public Afp(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Afp{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }


}
