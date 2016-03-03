/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author inftel12
 */
@Named(value = "colors")
@Dependent
public class Colors {

    /**
     * Creates a new instance of Colors
     */
    public List<String> colors;
    int num = 9;

    
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    
    public Colors() {
        
        colors = new ArrayList<>();
        colors.add("bg-aqua");
        colors.add("bg-green");
        colors.add("bg-yellow");
        colors.add("bg-purple");
        colors.add("bg-blue");   
        colors.add("bg-danger");
        colors.add("bg-teal");
        colors.add("bg-orange");
        colors.add("bg-olive");

    }
    
    
    public String getColor (int i) {
        return colors.get(i);
    }
}
