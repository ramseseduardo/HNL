package mx.gob.nl.fragment.model;

/**
 * Created by Ramses on 26/10/14.
 */
public class ModelList {

    private int id;
    private String name;
    private String presentacion;
    private String urlFoto;

    public ModelList(int id,String name,String presentacion, String urlFoto) {
        super();
        this.id = id;
        this.name = name;
        this.presentacion = presentacion;
        this.urlFoto = urlFoto;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    public String getUrlFoto() {
        return urlFoto;
    }
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
