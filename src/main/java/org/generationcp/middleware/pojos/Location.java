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
import java.util.Comparator;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@NamedQueries({ @NamedQuery(name = "getAllLocation", query = "FROM Location"),
    @NamedQuery(name = "countAllLocation", query = "SELECT COUNT(l) FROM Location l")
})

/**
 * POJO for location table
 * 
 * @author Kevin Manansala, Mark Agarrado, Joyce Avestro
 */
@Entity
@Table(name = "location")
// JAXB Element Tags for JSON output
@XmlRootElement(name = "location")
@XmlType(propOrder = { "locid", "lname", "typeFname", "labbr", "countryIsofull" })
@XmlAccessorType(XmlAccessType.NONE)
public class Location implements Serializable, Comparable<Location>{

    private static final long serialVersionUID = 1L;
    
    public static final String GET_ALL = "getAllLocation";
    public static final String COUNT_ALL = "countAllLocation";

    @Id
    @Basic(optional = false)
    @Column(name = "locid")
    @XmlElement(name = "locationId")
    private Integer locid;
    
    @Basic(optional = false)
    @Column(name = "ltype")
    private Integer ltype;

    @Basic(optional = false)
    @Column(name = "nllp")
    private Integer nllp;

    @Basic(optional = false)
    @Column(name = "lname")
    @XmlElement(name = "name")
    private String lname;

    @Column(name = "labbr")
    @XmlElement(name = "nameAbbreviation")
    private String labbr;

    @Basic(optional = false)
    @Column(name = "snl3id")
    private Integer snl3id;

    @Basic(optional = false)
    @Column(name = "snl2id")
    private Integer snl2id;

    @Basic(optional = false)
    @Column(name = "snl1id")
    private Integer snl1id;

    @Basic(optional = false)
    @Column(name = "cntryid")
    private Integer cntryid;

    @Basic(optional = false)
    @Column(name = "lrplce")
    private Integer lrplce;

/*    @OneToMany(mappedBy = "location")
    private Set<Locdes> descriptions = new HashSet<Locdes>();*/

    public Location() {
    }

    public Location(Integer locid) {
        this.locid = locid;
    }

    public Location(Integer locid, Integer ltype, Integer nllp, String lname, String labbr, Integer snl3id, Integer snl2id,
            Integer snl1id, Integer cntryid, Integer lrplce) {
        super();
        this.locid = locid;
        this.ltype = ltype;
        this.nllp = nllp;
        this.lname = lname;
        this.labbr = labbr;
        this.snl3id = snl3id;
        this.snl2id = snl2id;
        this.snl1id = snl1id;
        this.cntryid = cntryid;
        this.lrplce = lrplce;
    }

    public Integer getLocid() {
        return locid;
    }

    public void setLocid(Integer locid) {
        this.locid = locid;
    }

    public Integer getLtype() {
        return ltype;
    }

    public void setLtype(Integer ltype) {
        this.ltype = ltype;
    }

    public Integer getCntryid() {
        return cntryid;
    }

    public void setCntryid(Integer cntryid) {
        this.cntryid = cntryid;
    }

    public Integer getNllp() {
        return nllp;
    }

    public void setNllp(Integer nllp) {
        this.nllp = nllp;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLabbr() {
        return labbr;
    }

    public void setLabbr(String labbr) {
        this.labbr = labbr;
    }

    public Integer getSnl3id() {
        return snl3id;
    }

    public void setSnl3id(Integer snl3id) {
        this.snl3id = snl3id;
    }

    public Integer getSnl2id() {
        return snl2id;
    }

    public void setSnl2id(Integer snl2id) {
        this.snl2id = snl2id;
    }

    public Integer getSnl1id() {
        return snl1id;
    }

    public void setSnl1id(Integer snl1id) {
        this.snl1id = snl1id;
    }

    public Integer getLrplce() {
        return lrplce;
    }

    public void setLrplce(Integer lrplce) {
        this.lrplce = lrplce;
    }

/*    public Set<Locdes> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Locdes> descriptions) {
        this.descriptions = descriptions;
    }*/

    @Override
    public int hashCode() {
        return this.getLocid();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Location) {
            Location param = (Location) obj;
            if (this.getLocid() == param.getLocid()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Location [locid=");
        builder.append(locid);
        builder.append(", ltype=");
        builder.append(ltype);
        builder.append(", nllp=");
        builder.append(nllp);
        builder.append(", lname=");
        builder.append(lname);
        builder.append(", labbr=");
        builder.append(labbr);
        builder.append(", snl3id=");
        builder.append(snl3id);
        builder.append(", snl2id=");
        builder.append(snl2id);
        builder.append(", snl1id=");
        builder.append(snl1id);
        builder.append(", cntryid=");
        builder.append(cntryid);
        builder.append(", lrplce=");
        builder.append(lrplce);
        builder.append("]");
        return builder.toString();
    }


    
    public int compareTo(Location compareLocation) {
 
        String compareName = ((Location) compareLocation).getLname(); 
 
        //ascending order
        return this.lname.compareTo(compareName);
 
        //descending order
        //return compareName.compareTo(this.lname);
 
    }
 
    public static Comparator<Location> LocationNameComparator 
                          = new Comparator<Location>() {
        @Override
        public int compare(Location location1, Location location2) {
            String locationName1 = location1.getLname().toUpperCase();
            String locationName2 = location2.getLname().toUpperCase();
   
            //ascending order
            return locationName1.compareTo(locationName2);
   
            //descending order
            //return locationName2.compareTo(locationName1);
        }
 
    };
    
}
