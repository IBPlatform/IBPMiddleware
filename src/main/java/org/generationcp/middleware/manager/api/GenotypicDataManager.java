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

package org.generationcp.middleware.manager.api;

import java.util.List;

import org.generationcp.middleware.exceptions.QueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.pojos.Name;
import org.generationcp.middleware.pojos.gdms.DatasetElement;
import org.generationcp.middleware.pojos.gdms.Map;
import org.generationcp.middleware.pojos.gdms.MapInfo;

/**
 * This is the API for retrieving and storing genotypic data.
 * 
 * @author Joyce Avestro
 */
public interface GenotypicDataManager{

    /**
     * Gets the name ids by germplasm ids.
     *
     * @param gIds the g ids
     * @return the name ids by germplasm ids
     * @throws QueryException the query exception
     */
    public List<Integer> getNameIdsByGermplasmIds(List<Integer> gIds) throws QueryException;

    /**
     * Gets the names by name ids.
     *
     * @param nIds the n ids
     * @return the names by name ids
     * @throws QueryException the query exception
     */
    public List<Name> getNamesByNameIds(List<Integer> nIds) throws QueryException;

    /**
     * Gets the name by name id.
     *
     * @param nId the n id
     * @return the name by name id
     * @throws QueryException the query exception
     */
    public Name getNameByNameId(Integer nId) throws QueryException;
    
    /**
     * Gets the all maps.
     *
     * @param instance the instance
     * @return the all maps
     * @throws QueryException the query exception
     */
    public List<Map> getAllMaps(Database instance) throws QueryException;
    
    /**
     * Gets the all maps.
     *
     * @param start the start
     * @param numOfRows the num of rows
     * @param instance the instance
     * @return the all maps
     * @throws QueryException the query exception
     */
    public List<Map> getAllMaps(Integer start, Integer numOfRows,  Database instance) throws QueryException;
    
    /**
     * Gets the map info by map name.
     *
     * @param mapName the map name
     * @return the map info by map name
     * @throws QueryException the query exception
     */
    public List<MapInfo> getMapInfoByMapName(String mapName) throws QueryException;
    
    
    public List<String> getDatasetNames() throws QueryException;
    
    
    public List<DatasetElement> getDatasetDetailsByDatasetName(String datasetName) throws QueryException;
    
    
}
