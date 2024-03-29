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

import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.pojos.workbench.Tool;
import org.generationcp.middleware.pojos.workbench.ToolType;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class ToolDAO extends GenericDAO<Tool, Long>{

    public Tool getByToolName(String toolName) throws MiddlewareQueryException {
        try {
            Criteria criteria = getSession().createCriteria(Tool.class).add(Restrictions.eq("toolName", toolName)).setMaxResults(1);

            return (Tool) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getByToolName(toolName=" + toolName + ") query from Tool: " + e.getMessage(), e);
        }
    }

    public Tool getByToolId(Long toolId) throws MiddlewareQueryException {
        try {
            Criteria criteria = getSession().createCriteria(Tool.class).add(Restrictions.eq("toolId", toolId)).setMaxResults(1);

            return (Tool) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error withgetByToolId(toolId=" + toolId + ") query from Tool: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Tool> getToolsByToolType(ToolType toolType) throws MiddlewareQueryException {
        try {
            Criteria criteria = getSession().createCriteria(Tool.class).add(Restrictions.eq("toolType", toolType));

            return criteria.list();
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getToolsByToolType(toolType=" + toolType + ") query from Tool: "
                    + e.getMessage(), e);
        }
    }
}
