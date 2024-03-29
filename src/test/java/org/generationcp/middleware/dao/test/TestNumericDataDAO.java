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

package org.generationcp.middleware.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.dao.NumericDataDAO;
import org.generationcp.middleware.pojos.NumericRange;
import org.generationcp.middleware.pojos.TraitCombinationFilter;
import org.generationcp.middleware.util.HibernateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNumericDataDAO{

    private static final String CONFIG = "test-hibernate.cfg.xml";
    private HibernateUtil hibernateUtil;
    private NumericDataDAO dao;

    @Before
    public void setUp() throws Exception {
        hibernateUtil = new HibernateUtil(CONFIG);
        dao = new NumericDataDAO();
        dao.setSession(hibernateUtil.getCurrentSession());
    }

    @Test
    public void testGetObservationUnitIdsByTraitScaleMethodAndValueCombinations() throws Exception {
        Integer traitId = Integer.valueOf(1003);
        Integer scaleId = Integer.valueOf(9);
        Integer methodId = Integer.valueOf(30);
        NumericRange range = new NumericRange(new Double(2000), new Double(3000));
        TraitCombinationFilter combination = new TraitCombinationFilter(traitId, scaleId, methodId, range);
        List<TraitCombinationFilter> filters = new ArrayList<TraitCombinationFilter>();
        filters.add(combination);

        List<Integer> results = dao.getObservationUnitIdsByTraitScaleMethodAndValueCombinations(filters, 0, 10);
        System.out.println("testGetObservationUnitIdsByTraitScaleMethodAndValueCombinations RESULTS:");
        System.out.println("  filters: traitId = " + traitId + 
                                    ", scaleId = " + scaleId + 
                                    ", methodId = " + methodId + 
                                    ", range start = " + range.getStart() + 
                                    ", range end = " + range.getEnd());
        for (Integer result : results){
            System.out.println("  " + result);
        }
    }

    @After
    public void tearDown() throws Exception {
        dao.setSession(null);
        dao = null;
        hibernateUtil.shutdown();
    }

}
