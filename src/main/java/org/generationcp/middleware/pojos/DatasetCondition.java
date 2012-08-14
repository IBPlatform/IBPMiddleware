/***************************************************************
 * Copyright (c) 2012, All Rights Reserved.
 * 
 * Generation Challenge Programme (GCP)
 * 
 * @author Kevin L. Manansala
 * 
 * This software is licensed for use under the terms of the 
 * GNU General Public License (http://bit.ly/8Ztv8M) and the 
 * provisions of Part F of the Generation Challenge Programme 
 * Amended Consortium Agreement (http://bit.ly/KQX1nL)
 * 
 **************************************************************/

package org.generationcp.middleware.pojos;

import java.io.Serializable;

/**
 * Represents a condition of a dataset.
 * 
 * The value can either be a String or a Double, depending on the type,
 * if it is a "C" (String) or a "N" (Double).
 * 
 * @author Kevin L. Manansala
 *
 */
public class DatasetCondition implements Serializable{

    private static final long serialVersionUID = 1883187407218392570L;
    
    private String name;
    private Object value;
    private Integer traitId;
    private Integer scaleId;
    private Integer methodId;
    private String type;
    
    public DatasetCondition(String name, Object value, Integer traitId, Integer scaleId, Integer methodId, String type) {
        super();
        this.name = name;
        this.value = value;
        this.traitId = traitId;
        this.scaleId = scaleId;
        this.methodId = methodId;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    public Object getValue() {
        return value;
    }

    
    public void setValue(Object value) {
        this.value = value;
    }

    
    public Integer getTraitId() {
        return traitId;
    }

    
    public void setTraitId(Integer traitId) {
        this.traitId = traitId;
    }

    
    public Integer getScaleId() {
        return scaleId;
    }

    
    public void setScaleId(Integer scaleId) {
        this.scaleId = scaleId;
    }

    
    public Integer getMethodId() {
        return methodId;
    }

    
    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DatasetCondition [name=" + name + ", value=" + value + ", traitId=" + traitId + ", scaleId=" + scaleId + ", methodId="
                + methodId + ", type=" + type + "]";
    }
    
}
