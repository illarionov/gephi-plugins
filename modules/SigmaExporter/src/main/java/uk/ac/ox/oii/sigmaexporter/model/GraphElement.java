/*
 Copyright Scott A. Hale, 2016
 */
package uk.ac.ox.oii.sigmaexporter.model;

import java.util.HashMap;

public class GraphElement {
	private HashMap<String,String> attributes;
        private String color;
        protected double size;
	
	public GraphElement() {
		attributes=new HashMap<String,String>();
                color="";
	}
	
	public void putAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void putAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
        
        public String getColor() {
		return color;
	}
        
        public void setColor(String color) {
		this.color = color;
	}

        public double getSize() {
            return size;
        }

        public void setSize(double size) {
            this.size = size;
        }

	
}
