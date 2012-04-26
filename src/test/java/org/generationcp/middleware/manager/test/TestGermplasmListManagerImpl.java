package org.generationcp.middleware.manager.test;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.Operation;
import org.generationcp.middleware.manager.api.GermplasmListManager;
import org.generationcp.middleware.pojos.Germplasm;
import org.generationcp.middleware.pojos.GermplasmList;
import org.generationcp.middleware.pojos.GermplasmListData;
import org.generationcp.middleware.pojos.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGermplasmListManagerImpl
{
	private static ManagerFactory factory;
	private static GermplasmListManager manager;

	@BeforeClass
	public static void setUp() throws Exception 
	{
		DatabaseConnectionParameters local = new DatabaseConnectionParameters("testDatabaseConfig.properties", "local");
		DatabaseConnectionParameters central = new DatabaseConnectionParameters("testDatabaseConfig.properties", "central");
		factory = new ManagerFactory(local, central);
		manager = factory.getGermplasmListManager();
	}
	
	@Test
	public void testGetGermplasmListById() throws Exception
	{
		GermplasmList list = manager.getGermplasmListById(new Integer(1));
		Assert.assertTrue(list != null);
		System.out.println(list);
	}
	
	@Test
	public void testGetAllGermplasmLists() throws Exception
	{
		List<GermplasmList> lists = manager.getAllGermplasmLists(0, 5);
		Assert.assertTrue(lists != null);
		Assert.assertTrue(!lists.isEmpty());
		Assert.assertTrue(lists.size() == 5);
		
		System.out.println("RESULTS:");
		for(GermplasmList list : lists)
		{
			System.out.println(list);
		}
	}
	
	@Test
	public void testCountAllGermplasmLists() throws Exception
	{
		System.out.println(manager.countAllGermplasmLists());
	}
	
	@Test
	public void testFindGermplasmListByName() throws Exception
	{
		List<GermplasmList> lists = manager.findGermplasmListByName("2002%", 0, 5, Operation.LIKE);
		Assert.assertTrue(lists != null);
		Assert.assertTrue(!lists.isEmpty());
		
		System.out.println("RESULTS:");
		for(GermplasmList list : lists)
		{
			System.out.println(list);
		}
	}
	
	@Test
	public void testCountGermplasmListByName() throws Exception
	{
		System.out.println(manager.countGermplasmListByName("2002%", Operation.LIKE));
	}
	
	@Test
	public void testFindGermplasmListByStatus() throws Exception
	{
		List<GermplasmList> lists = manager.findGermplasmListByStatus(new Integer(1), 0, 5);
		Assert.assertTrue(lists != null);
		Assert.assertTrue(!lists.isEmpty());
		
		System.out.println("RESULTS:");
		for(GermplasmList list : lists)
		{
			System.out.println(list);
		}
	}
	
	@Test
	public void testCountGermplasmListByStatus() throws Exception
	{
		System.out.println(manager.countGermplasmListByStatus(new Integer(1)));
	}
	
	@Test
	public void testGetGermplasmListDataByListId() throws Exception
	{
		List<GermplasmListData> results = manager.getGermplasmListDataByListId(new Integer(1), 0, 5);
		Assert.assertTrue(results != null);
		Assert.assertTrue(!results.isEmpty());
		
		System.out.println("RESULTS:");
		for(GermplasmListData data : results)
		{
			System.out.println(data);
		}
	}
	
	@Test
	public void testCountGermplasmListDataByListId() throws Exception
	{
		System.out.println(manager.countGermplasmListDataByListId(new Integer(1)));
	}
	
	@Test
	public void testGetGermplasmListDataByListIdAndGID() throws Exception
	{
		List<GermplasmListData> results = manager.getGermplasmListDataByListIdAndGID(new Integer(1), new Integer(91959));
		Assert.assertTrue(results != null);
		Assert.assertTrue(!results.isEmpty());
		
		System.out.println("RESULTS:");
		for(GermplasmListData data : results)
		{
			System.out.println(data);
		}
	}
	
	@Test
	public void testGetGermplasmListDataByListIdAndEntryId() throws Exception
	{
		GermplasmListData data = manager.getGermplasmListDataByListIdAndEntryId(new Integer(1), new Integer(1));
		Assert.assertTrue(data != null);
		System.out.println(data);
	}
	
	@Test
	public void testGetGermplasmListDataByGID() throws Exception
	{
		List<GermplasmListData> results = manager.getGermplasmListDataByGID(new Integer(91959), 0, 5);
		Assert.assertTrue(results != null);
		Assert.assertTrue(!results.isEmpty());
		
		System.out.println("RESULTS:");
		for(GermplasmListData data : results)
		{
			System.out.println(data);
		}
	}
	
	@Test
	public void testCountGermplasmListDataByGID() throws Exception
	{
		System.out.println(manager.countGermplasmListDataByGID(new Integer(91959)));
	}
	
	@Test
	public void testAddGermplasmList() throws Exception
	{
		System.out.println("********** testAddGermplasmList() **********");
		try {
			System.out.println("Inserting new Germplasm List Test #1: Complete details with ID");
			GermplasmList germplasmList = new GermplasmList(
					null, 
					"Test List #1", 
					new Long(20120305), 
					"LST",
					new Integer(1), 
					"Test List #1 for GCP-92",
					null,
					1);
			System.out.println(germplasmList);
			System.out.println("addGermplasmList(): " + manager.addGermplasmList(germplasmList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testAddGermplasmList() done **********");
	}
	
	@Test
	public void testAddGermplasmLists() throws Exception
	{
		System.out.println("********** testAddGermplasmList(List<GermplasmList>) **********");
		try {
			User user = new User(300);
			List<GermplasmList> germplasmLists = new ArrayList<GermplasmList>();
			System.out.println("Inserting new Germplasm List Test #2: Complete details with ID");
			GermplasmList germplasmList = new GermplasmList(
					null, 
					"Test List #2", 
					new Long(20120305), 
					"LST",
					new Integer(1), 
					"Test List #2 for GCP-92",
					null,
					1);
			System.out.println(germplasmList);
			germplasmLists.add(germplasmList);
			
			System.out.println("Inserting new Germplasm List Test #3: Complete details with ID");
			germplasmList = new GermplasmList(
					null, 
					"Test List #3", 
					new Long(20120305), 
					"LST",
					new Integer(1), 
					"Test List #3 for GCP-92",
					null,
					1);
			System.out.println(germplasmList);
			germplasmLists.add(germplasmList);
			
			System.out.println("Inserting new Germplasm List Test #4: Complete details with ID");
			germplasmList = new GermplasmList(
					null, 
					"Test List #4", 
					new Long(20120305), 
					"LST",
					new Integer(1), 
					"Test List #4 for GCP-92",
					null,
					1);
			System.out.println(germplasmList);
			germplasmLists.add(germplasmList);
			
			System.out.println("addGermplasmList(List<GermplasmList>): " + manager.addGermplasmList(germplasmLists));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testAddGermplasmList(List<GermplasmList>) done **********");
	}
	
	@Test
	public void testUpdateGermplasmList() throws Exception
	{
		System.out.println("********** testUpdateGermplasmList() **********");
		try {
			List<GermplasmList> germplasmLists = new ArrayList<GermplasmList>();
			System.out.println("Updating Germplasm List Test #1");
			GermplasmList germplasmList = manager.getGermplasmListById(-1);
			germplasmList.setName("Test List #1, UPDATE");
			germplasmList.setDescription("Test List #1 for GCP-92, UPDATE");
			System.out.println(germplasmList);
			germplasmLists.add(germplasmList);
			
			System.out.println("Updating Germplasm List Test #4");
			GermplasmList parent = manager.getGermplasmListById(-1);
			germplasmList = manager.getGermplasmListById(-4);
			germplasmList.setName("Test List #4, UPDATE");
			germplasmList.setDescription("Test List #4 for GCP-92 UPDATE");
			germplasmList.setParent(parent);
			System.out.println(germplasmList);
			germplasmLists.add(germplasmList);
			
			System.out.println("updateGermplasmList(): " + manager.updateGermplasmList(germplasmLists));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testUpdateGermplasmList() done **********");
	}
	
	@Test
	public void testAddGermplasmListData() throws Exception
	{
		System.out.println("********** testAddGermplasmListData() **********");
		try {
			System.out.println("Inserting new Germplasm List Data Test #1: Complete details");
			GermplasmList germList = manager.getGermplasmListById(-4);
			GermplasmListData germplasmListData = new GermplasmListData(
					null,
					germList,
					new Integer(2),
					1,
					null,
					null,
					"Germplasm Name 3",
					null,
					0,
					99992);
			System.out.println(germplasmListData);
			System.out.println("addGermplasmListData(): " + manager.addGermplasmListData(germplasmListData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testAddGermplasmListData() done **********");
	}
	
	@Test
	public void testAddGermplasmListDatas() throws Exception
	{
		System.out.println("********** testAddGermplasmListDatas() **********");
		try {
			List<GermplasmListData> germplasmListDatas = new ArrayList<GermplasmListData>();
			System.out.println("Inserting new Germplasm List Data Test #2: Complete details");
			GermplasmList germList = manager.getGermplasmListById(-4);
			GermplasmListData germplasmListData = new GermplasmListData(
					null,
					germList,
					new Integer(2),
					2,
					null,
					null,
					"Germplasm Name 4",
					null,
					0,
					99993);
			System.out.println(germplasmListData);
			germplasmListDatas.add(germplasmListData);
			
			System.out.println("Inserting new Germplasm List Data Test #3: Complete details");
			germList = manager.getGermplasmListById(-2);
			germplasmListData = new GermplasmListData(
					null,
					germList,
					new Integer(1),
					1,
					null,
					null,
					"Germplasm Name 1",
					null,
					0,
					99990);
			System.out.println(germplasmListData);
			germplasmListDatas.add(germplasmListData);
			
			System.out.println("Inserting new Germplasm List Data Test #4: Complete details");
			germplasmListData = new GermplasmListData(
					null,
					germList,
					new Integer(1),
					2,
					null,
					null,
					"Germplasm Name 2",
					null,
					0,
					99991);
			System.out.println(germplasmListData);
			germplasmListDatas.add(germplasmListData);
			
			System.out.println("addGermplasmListDatas(): " + manager.addGermplasmListData(germplasmListDatas));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testAddGermplasmListDatas() done **********");
	}
	
	@Test
	public void testDeleteGermplasmList() throws Exception
	{
		System.out.println("********** testDeleteGermplasmList() **********");
		try{
			List<GermplasmList> germplasmLists = new ArrayList<GermplasmList>();
			
			System.out.println("Deleting Germplasm List #-2: ");
			GermplasmList germplasmList = manager.getGermplasmListById(-2);
			germplasmLists.add(germplasmList);
			System.out.println("Germplasm List #-2: " + germplasmList);
			
			System.out.println("Deleting Germplasm List #-3: ");
			germplasmList = manager.getGermplasmListById(-3);
			germplasmLists.add(germplasmList);
			System.out.println("Germplasm List #-3: " + germplasmList);
			
			System.out.println("deleteGermplasmList(): " + manager.deleteGermplasmList(germplasmLists));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testDeleteGermplasmList() done **********");
	}
	
	@Test public void testUpdateGermplasmListData() throws Exception
	{
		System.out.println("********** testUpdateGermplasmListData() **********");
		try{
			List<GermplasmListData> germplasmListDatas = new ArrayList<GermplasmListData>();
			
			System.out.println("Updating Germplasm List #-4, 1: ");
			GermplasmListData germplasmListData = manager.getGermplasmListDataByListIdAndEntryId(-4, 1);
			germplasmListData.setDesignation("Germplasm Name 3, UPDATE");
			germplasmListDatas.add(germplasmListData);
			System.out.println("Germplasm List Data #-4, 1: " + germplasmListData);
			
			System.out.println("Updating Germplasm List #-4, 2: ");
			germplasmListData = manager.getGermplasmListDataByListIdAndEntryId(-4, 2);
			germplasmListData.setDesignation("Germplasm Name 4, UPDATE");
			germplasmListDatas.add(germplasmListData);
			System.out.println("Germplasm List Data #-4, 2: " + germplasmListData);
			
			System.out.println("updateGermplasmListData(): " + manager.updateGermplasmListData(germplasmListDatas));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testUpdateGermplasmListData() done **********");
	}
	
	@Test
	public void testDeleteGermplasmListData() throws Exception
	{
		System.out.println("********** testDeleteGermplasmListData() **********");
		try {
			System.out.println("Retrieving Germplasm List #-4: ");
			GermplasmList germplasmList = manager.getGermplasmListById(-4);
			
			System.out.println("Deleting Germplasm List Data #-4, 1: ");
			System.out.println("deleteGermplasmListData(): " + manager.deleteGermplasmListDataByListId((-4)));
			
			System.out.println("Viewing Germplasm List #-4's List Data: ");
			germplasmList = manager.getGermplasmListById(-4);
			System.out.println(germplasmList.getListData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********** testDeleteGermplasmList() done **********");
	}
	
	@AfterClass
	public static void tearDown() throws Exception 
	{
		factory.close();
	}
}
