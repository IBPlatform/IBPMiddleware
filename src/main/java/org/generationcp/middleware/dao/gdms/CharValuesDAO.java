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
package org.generationcp.middleware.dao.gdms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.dao.GenericDAO;
import org.generationcp.middleware.exceptions.QueryException;
import org.generationcp.middleware.pojos.gdms.AllelicValueWithMarkerIdElement;
import org.generationcp.middleware.pojos.gdms.CharValues;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 * The Class CharValuesDAO.
 * 
 * @author Joyce Avestro
 * 
 */
public class CharValuesDAO extends GenericDAO<CharValues, Integer>{
    
    
    /**
     * Gets the allelic values based on the given dataset id. The result is limited by the start and numOfRows parameters.
     * 
     * @param datasetId the dataset id
     * @param start the start of the rows to retrieve
     * @param numOfRows the number of rows to retrieve
     * @return the Allelic Values (germplasm id, data value, and marker id) for the given dataset id
     * @throws QueryException the QueryException
     */
    @SuppressWarnings("rawtypes")
    public List<AllelicValueWithMarkerIdElement> getAllelicValuesByDatasetId(Integer datasetId, int start, int numOfRows) throws QueryException{
        List<AllelicValueWithMarkerIdElement> toReturn = new ArrayList<AllelicValueWithMarkerIdElement>();
        if (datasetId == null){
            return toReturn;
        }

        try {
            SQLQuery query = getSession().createSQLQuery(CharValues.GET_ALLELIC_VALUES_BY_DATASET_ID);
            query.setParameter("datasetId", datasetId);
            query.setFirstResult(start);
            query.setMaxResults(numOfRows);
            List results = query.list();
            
            for (Object o : results) {
                Object[] result = (Object[]) o;
                if (result != null) {
                    Integer gid = (Integer) result[0];
                    Integer markerId = (Integer) result[1];
                    String data = (String) result[2];
                    AllelicValueWithMarkerIdElement allelicValueElement = new AllelicValueWithMarkerIdElement(gid, data, markerId);
                    toReturn.add(allelicValueElement);
                }
            }

            return toReturn;            
        } catch (HibernateException e) {
            throw new QueryException("Error with get allele values from char_values by dataset id query: " + e.getMessage(), e);
        }
    }
    
    /**
     * Count by dataset id.
     *
     * @param datasetId the dataset id
     * @return the number of entries in char_values table corresponding to the given datasetId
     * @throws QueryException the QueryException
     */
    public int countByDatasetId(Integer datasetId) throws QueryException{
        try {
            Query query = getSession().createSQLQuery(CharValues.COUNT_BY_DATASET_ID);
            query.setParameter("datasetId", datasetId);
            BigInteger count = (BigInteger) query.uniqueResult();
            return count.intValue();
        } catch (HibernateException e) {
            throw new QueryException("Error with count from char_values by dataset id query: " + e.getMessage(), e);
        }        
    }
    
}