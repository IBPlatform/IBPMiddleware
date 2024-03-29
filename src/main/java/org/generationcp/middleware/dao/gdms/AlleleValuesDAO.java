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
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.pojos.gdms.AlleleValues;
import org.generationcp.middleware.pojos.gdms.AllelicValueElement;
import org.generationcp.middleware.pojos.gdms.AllelicValueWithMarkerIdElement;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 * The Class AlleleValuesDAO.
 * 
 * @author Joyce Avestro
 * 
 */
public class AlleleValuesDAO extends GenericDAO<AlleleValues, Integer>{

    /**
     * Gets the allelic values based on the given dataset id. The result is limited by the start and numOfRows parameters.
     * 
     * @param datasetId the dataset id
     * @param start the start of the rows to retrieve
     * @param numOfRows the number of rows to retrieve
     * @return the Allelic Values (germplasm id, data value, and marker id) for the given dataset id
     * @throws MiddlewareQueryException the MiddlewareQueryException
     */
    @SuppressWarnings("rawtypes")
    public List<AllelicValueWithMarkerIdElement> getAllelicValuesByDatasetId(Integer datasetId, int start, int numOfRows)
            throws MiddlewareQueryException {

        List<AllelicValueWithMarkerIdElement> toReturn = new ArrayList<AllelicValueWithMarkerIdElement>();

        if (datasetId == null) {
            return toReturn;
        }

        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.GET_ALLELIC_VALUES_BY_DATASET_ID);
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
            throw new MiddlewareQueryException("Error with getAllelicValuesByDatasetId(datasetId=" + datasetId + ") query from AlleleValues: " + e.getMessage(), e);
        }

    }

    /**
     * Count by dataset id.
     *
     * @param datasetId the dataset id
     * @return the number of entries in allele_values table corresponding to the given datasetId
     * @throws MiddlewareQueryException the MiddlewareQueryException
     */
    public long countByDatasetId(Integer datasetId) throws MiddlewareQueryException {
        try {
            Query query = getSession().createSQLQuery(AlleleValues.COUNT_BY_DATASET_ID);
            query.setParameter("datasetId", datasetId);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            } else {
                return 0;
            }
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countByDatasetId(datasetId=" + datasetId + ") query from AlleleValues: "
                    + e.getMessage(), e);
        }
    }

    /**
     * Gets the gids by marker id.
     *
     * @param markerId the marker id
     * @param start the start
     * @param numOfRows the num of rows
     * @return the gI ds by marker id
     * @throws MiddlewareQueryException the MiddlewareQueryException
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getGIDsByMarkerId(Integer markerId, int start, int numOfRows) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.GET_GIDS_BY_MARKER_ID);
            query.setParameter("markerId", markerId);
            query.setFirstResult(start);
            query.setMaxResults(numOfRows);
            List<Integer> gids = query.list();
            return gids;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getGIDsByMarkerId(markerId=" + markerId + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    /**
     * Count gids by marker id.
     *
     * @param markerId the marker id
     * @return the number of GIds by Marker Id
     * @throws MiddlewareQueryException the MiddlewareQueryException
     */
    public long countGIDsByMarkerId(Integer markerId) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.COUNT_GIDS_BY_MARKER_ID);
            query.setParameter("markerId", markerId);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            }
            return 0;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countGIDsByMarkerId(markerId=" + markerId + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    public long countAlleleValuesByGids(List<Integer> gids) throws MiddlewareQueryException{
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.COUNT_ALLELE_VALUES_BY_GIDS);
            query.setParameterList("gids", gids);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            }
            return 0;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countAlleleValuesByGids(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }


    @SuppressWarnings("rawtypes")
    public List<AllelicValueElement> getIntAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids, int start,
            int numOfRows) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.GET_INT_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            query.setFirstResult(start);
            query.setMaxResults(numOfRows);
            List results = query.list();

            List<AllelicValueElement> values = new ArrayList<AllelicValueElement>();

            for (Object o : results) {
                Object[] result = (Object[]) o;
                if (result != null) {
                    Integer datasetId = (Integer) result[0];
                    Integer gid = (Integer) result[1];
                    String markerName = (String) result[2];
                    String alleleBinValue = (String) result[3];
                    AllelicValueElement allelicValueElement = new AllelicValueElement(datasetId, gid, markerName, alleleBinValue);
                    values.add(allelicValueElement);
                }
            }

            return values;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getCharAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }    }

    public long countIntAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.COUNT_INT_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            }
            return 0;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countCharAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<AllelicValueElement> getCharAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids, int start,
            int numOfRows) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.GET_CHAR_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            query.setFirstResult(start);
            query.setMaxResults(numOfRows);
            List results = query.list();

            List<AllelicValueElement> values = new ArrayList<AllelicValueElement>();

            for (Object o : results) {
                Object[] result = (Object[]) o;
                if (result != null) {
                    Integer datasetId = (Integer) result[0];
                    Integer gid = (Integer) result[1];
                    String markerName = (String) result[2];
                    String charValue = (String) result[3];
                    AllelicValueElement allelicValueElement = new AllelicValueElement(datasetId, gid, markerName, charValue);
                    values.add(allelicValueElement);
                }
            }

            return values;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getCharAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    public long countCharAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.COUNT_CHAR_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            }
            return 0;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countCharAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<AllelicValueElement> getMappingAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids,
            int start, int numOfRows) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.GET_MAPPING_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            query.setFirstResult(start);
            query.setMaxResults(numOfRows);
            List results = query.list();

            List<AllelicValueElement> values = new ArrayList<AllelicValueElement>();

            for (Object o : results) {
                Object[] result = (Object[]) o;
                if (result != null) {
                    Integer datasetId = (Integer) result[0];
                    Integer gid = (Integer) result[1];
                    String markerName = (String) result[2];
                    String data = (String) result[3];
                    AllelicValueElement allelicValueElement = new AllelicValueElement(datasetId, gid, markerName, data);
                    values.add(allelicValueElement);
                }
            }

            return values;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with getMappingAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

    public long countMappingAlleleValuesForPolymorphicMarkersRetrieval(List<Integer> gids) throws MiddlewareQueryException {
        try {
            SQLQuery query = getSession().createSQLQuery(AlleleValues.COUNT_MAPPING_ALLELE_VALUES_FOR_POLYMORPHIC_MARKERS_RETRIEVAL_BY_GIDS);
            query.setParameterList("gids", gids);
            BigInteger result = (BigInteger) query.uniqueResult();
            if (result != null) {
                return result.longValue();
            }
            return 0;
        } catch (HibernateException e) {
            throw new MiddlewareQueryException("Error with countMappingAlleleValuesForPolymorphicMarkersRetrieval(gids=" + gids + ") query from AlleleValues: " + e.getMessage(), e);
        }
    }

}
