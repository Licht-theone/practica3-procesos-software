package es.unican.ps.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Backing Bean minimo para la vista protegida del gerente.
 */
@Named("gerenteBean")
@RequestScoped
public class GerenteBean {

    public GerenteBean() {
    }

    public String getUsuarioAutenticado() {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return user != null ? user : "Gerente";
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }
}
