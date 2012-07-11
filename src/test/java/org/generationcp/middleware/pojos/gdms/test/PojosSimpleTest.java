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

package org.generationcp.middleware.pojos.gdms.test;

import java.util.List;

import org.generationcp.middleware.pojos.gdms.AccMetadataSet;
import org.generationcp.middleware.pojos.gdms.Dataset;
import org.generationcp.middleware.pojos.gdms.Map;
import org.generationcp.middleware.pojos.gdms.MappingData;
import org.generationcp.middleware.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class PojosSimpleTest{

    private static final String CONFIG = "test-hibernate.cfg.xml";
    private HibernateUtil hibernateUtil;

    @Before
    public void setUp() throws Exception {
        hibernateUtil = new HibernateUtil(CONFIG);
    }

    @Test
    public void testAccMetadataSet() {
        Session session = hibernateUtil.getCurrentSession();
        Query query = session.createQuery("FROM AccMetadataSet");
        query.setMaxResults(5);
        List results = query.list();

        for (Object obj : results) {
            Assert.assertTrue(obj instanceof AccMetadataSet);
            Assert.assertTrue(obj != null);
            AccMetadataSet element = (AccMetadataSet) obj;
            System.out.println(element);
        }
    }

    @Test
    public void testDataset() {
        Session session = hibernateUtil.getCurrentSession();
        Query query = session.createQuery("FROM Dataset");
        query.setMaxResults(5);
        List results = query.list();

        for (Object obj : results) {
            Assert.assertTrue(obj instanceof Dataset);
            Assert.assertTrue(obj != null);
            Dataset element = (Dataset) obj;
            System.out.println(element);
        }
    }
    
    @Test
    public void testMap() {
        Session session = hibernateUtil.getCurrentSession();
        Query query = session.createQuery("FROM Map");
        query.setMaxResults(5);
        List results = query.list();

        for (Object obj : results) {
            Assert.assertTrue(obj instanceof Map);
            Assert.assertTrue(obj != null);
            Map element = (Map) obj;
            System.out.println(element);
        }
    }

    @Test
    public void testMappingData() {
        Session session = hibernateUtil.getCurrentSession();
        Query query = session.createQuery("FROM MappingData");
        query.setMaxResults(5);
        List results = query.list();

        for (Object obj : results) {
            Assert.assertTrue(obj instanceof MappingData);
            Assert.assertTrue(obj != null);
            MappingData element = (MappingData) obj;
            System.out.println(element);
        }
    }

    @After
    public void tearDown() throws Exception {
        hibernateUtil.shutdown();
    }

}