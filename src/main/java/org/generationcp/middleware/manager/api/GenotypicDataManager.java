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
import org.generationcp.middleware.pojos.Name;

/**
 * This is the API for retrieving and storing genotypic data 
 * 
 */
public interface GenotypicDataManager{

    public List<Integer> getNameIdsByGermplasmIds(List<Integer> gIds) throws QueryException;

    public List<Name> getNamesByNameIds(List<Integer> nIds) throws QueryException;


}