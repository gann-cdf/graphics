package org.gannacademy.cdf.graphics.curriculum;

import org.gannacademy.cdf.graphics.Drawable;

import java.util.List;
import java.util.Vector;

public class Sprite {
    private List<Drawable> components;
    
    public Sprite() {
        components = new Vector<>();
    }

    public void addComponent(Drawable component) {
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    public boolean intersects(Sprite other) {
        for(Drawable component : components) {
            for (Drawable otherComponent : other.components) {
                if (component.intersects(otherComponent.getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }

    public double getTop() {
        double top = Double.MAX_VALUE;
        for(Drawable component:components) {
            top = Math.min(top, component.getY());
        }
        if (top == Double.MAX_VALUE) {
            return Double.NaN;
        }
        return top;
    }
}
