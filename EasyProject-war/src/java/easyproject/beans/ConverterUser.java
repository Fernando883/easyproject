/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.entities.Usuario;
import java.util.Collection;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author inftel12
 */
@Named(value = "converterUser")
@FacesConverter("userConverter")
@Dependent
public class ConverterUser implements Converter{

    @ManagedProperty(value = "#{projectBean}")
    private UserBean projectBean;
        
    /**
     * Creates a new instance of ConverterUser
     */
    public ConverterUser() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Collection<Usuario> userProject = projectBean.getProjectSelected().getUsuarioCollection();
        
        for (Usuario user: userProject) {
            if (user.getNombreU().equals(value)) {
                return user;
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Usuario user = (Usuario) value;
        return user.getNombreU();
    }
    
}
