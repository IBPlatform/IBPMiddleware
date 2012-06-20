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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "level_n")
public class NumericLevel implements Serializable{

    private static final long serialVersionUID = 2864832968167403818L;

    public static final String GET_BY_OUNIT_ID_LIST = "select oi.ounitid, oi.factorid, f.fname, ln.lvalue "
            + "from oindex oi join obsunit ou on oi.ounitid = ou.ounitid "
            + "join level_n ln on ln.factorid = oi.factorid and ln.levelno = oi.levelno " + "join factor f on f.labelid = ln.labelid "
            + "where oi.ounitid in (:ounitIdList)";

    @EmbeddedId
    protected NumericLevelPK id;

    @Column(name = "lvalue")
    private Double value;

    public NumericLevel() {
    }

    public NumericLevel(NumericLevelPK id, Double value) {
        super();
        this.id = id;
        this.value = value;
    }

    public NumericLevelPK getId() {
        return id;
    }

    public void setId(NumericLevelPK id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumericLevel)) {
            return false;
        }

        NumericLevel rhs = (NumericLevel) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(id, rhs.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 67).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return "NumericLevel [id=" + id + ", value=" + value + "]";
    }

}
