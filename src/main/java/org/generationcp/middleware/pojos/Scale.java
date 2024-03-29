/*******************************************************************************
 * Copyright (c) 2012, All Rights Reserved.
 * 
 * Generation Challenge Programme (GCP)
 * 
 * 
 * This software is licensed for use under the terms of the GNU General Public
 * License (http://bit.ly/8Ztv8M) and the provisions of Part F of the Generation
 * Challenge Programme Amended Consortium Agreement (http://bit.ly/KQX1nL)
 * 
 *******************************************************************************/

package org.generationcp.middleware.pojos;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tmsscales")
public class Scale implements Serializable{

    private static final long serialVersionUID = 1L;
    
    public static final String GET_BY_TRAIT_ID = "SELECT DISTINCT {s.*} FROM tmsscales s JOIN tmsmeasuredin mi "
        + "ON s.scaleid = mi.scaleid WHERE mi.traitid = :traitid";

    @Id
    @Basic(optional = false)
    @Column(name = "scaleid")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "scname")
    private String name;

    @Basic(optional = false)
    @Column(name = "sctype")
    private String type;

    public Scale() {

    }

    public Scale(Integer id) {
        super();
        this.id = id;
    }

    public Scale(Integer id, String name, Integer traitId, String type) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Scale [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", traitId=");
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Scale other = (Scale) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
