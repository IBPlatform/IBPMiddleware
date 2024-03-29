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
@Table(name = "tmsmethod")
public class TraitMethod implements Serializable{

    private static final long serialVersionUID = 1L;
    
    public static final String GET_BY_TRAIT_ID = "SELECT DISTINCT {m.*} FROM tmsmethod m JOIN tmsmeasuredin mi "
        + "ON m.tmethid = mi.tmethid WHERE mi.traitid = :traitid";

    @Id
    @Basic(optional = false)
    @Column(name = "tmethid")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "tmname")
    private String name;

    @Column(name = "tmabbr")
    private String abbreviation;

    @Column(name = "tmdesc")
    private String description;

    public TraitMethod() {
    }

    public TraitMethod(Integer id) {
        super();
        this.id = id;
    }

    public TraitMethod(Integer id, String name, Integer traitId, String abbreviation, String description) {
        super();
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        TraitMethod other = (TraitMethod) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TraitMethod [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", traitId=");
        builder.append(", abbreviation=");
        builder.append(abbreviation);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
