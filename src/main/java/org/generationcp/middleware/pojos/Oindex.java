package org.generationcp.middleware.pojos;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "oindex")
public class Oindex implements Serializable
{
	private static final long serialVersionUID = -6609291577310766245L;
	
	public static final String GET_BY_REPRESENTATION_ID = "select distinct ounitid " +
		"from oindex where represno = :representationId";

	@Id
	@Basic(optional = false)
	@Column(name = "oindexid")
	private Integer id;
	
	@Basic(optional = false)
    @Column(name = "ounitid")
	private Integer observationUnitId;
	
	@Basic(optional = false)
    @Column(name = "factorid")
	private Integer factorId;
	
	@Basic(optional = false)
    @Column(name = "levelno")
	private Integer levelNumber;
	
	@Basic(optional = false)
    @Column(name = "represno")
	private Integer representationNumber;

	public Oindex()
	{
	}
	
	public Oindex(Integer id, Integer observationUnitId, Integer factorId,
			Integer levelNumber, Integer representationNumber)
	{
		super();
		this.id = id;
		this.observationUnitId = observationUnitId;
		this.factorId = factorId;
		this.levelNumber = levelNumber;
		this.representationNumber = representationNumber;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getObservationUnitId()
	{
		return observationUnitId;
	}

	public void setObservationUnitId(Integer observationUnitId)
	{
		this.observationUnitId = observationUnitId;
	}

	public Integer getFactorId()
	{
		return factorId;
	}

	public void setFactorId(Integer factorId)
	{
		this.factorId = factorId;
	}

	public Integer getLevelNumber()
	{
		return levelNumber;
	}

	public void setLevelNumber(Integer levelNumber)
	{
		this.levelNumber = levelNumber;
	}

	public Integer getRepresentationNumber()
	{
		return representationNumber;
	}

	public void setRepresentationNumber(Integer representationNumber)
	{
		this.representationNumber = representationNumber;
	}

	@Override
	public String toString()
	{
		return "Oindex [id=" + id + ", observationUnitId=" + observationUnitId
				+ ", factorId=" + factorId + ", levelNumber=" + levelNumber
				+ ", representationNumber=" + representationNumber + "]";
	}

	@Override
	public boolean equals(Object obj) 
	{
	   if (obj == null)
		   return false;
	   if (obj == this) 
		   return true; 
	   if (!(obj instanceof Oindex)) 
		   return false;
	
	   Oindex rhs = (Oindex) obj;
	   return new EqualsBuilder()
	                 .appendSuper(super.equals(obj))
	                 .append(id, rhs.id)
	                 .isEquals();
	}
	
	@Override
	public int hashCode() 
	{
	     return new HashCodeBuilder(9, 29).
	       append(id).
	       toHashCode();
	}
	
}
