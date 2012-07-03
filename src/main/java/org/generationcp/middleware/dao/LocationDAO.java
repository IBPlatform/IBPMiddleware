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

package org.generationcp.middleware.dao;

import java.util.List;

import org.generationcp.middleware.manager.Operation;
import org.generationcp.middleware.pojos.Location;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class LocationDAO extends GenericDAO<Location, Integer>{
	
    @SuppressWarnings("unchecked")
    public List<Location> findByName(String name, int start, int numOfRows, Operation operation) {
        Criteria criteria = getSession().createCriteria(Location.class);

        if (operation == null || operation == Operation.EQUAL) {
            criteria.add(Restrictions.eq("lname", name));
        } else if (operation == Operation.LIKE) {
            criteria.add(Restrictions.like("lname", name));
        }

        criteria.setFirstResult(start);
        criteria.setMaxResults(numOfRows);

        return criteria.list();
    }

    public Long countByName(String name, Operation operation) {
        Criteria criteria = getSession().createCriteria(Location.class);
        criteria.setProjection(Projections.rowCount());

        if (operation == null || operation == Operation.EQUAL) {
            criteria.add(Restrictions.eq("lname", name));
        } else if (operation == Operation.LIKE) {
            criteria.add(Restrictions.like("lname", name));
        }

        return (Long) criteria.uniqueResult(); //count
    }

	
}
