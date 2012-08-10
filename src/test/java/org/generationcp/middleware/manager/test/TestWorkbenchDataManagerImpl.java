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

package org.generationcp.middleware.manager.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.generationcp.middleware.exceptions.QueryException;
import org.generationcp.middleware.manager.Operation;
import org.generationcp.middleware.manager.WorkbenchDataManagerImpl;
import org.generationcp.middleware.manager.api.WorkbenchDataManager;
import org.generationcp.middleware.pojos.Method;
import org.generationcp.middleware.pojos.Person;
import org.generationcp.middleware.pojos.User;
import org.generationcp.middleware.pojos.workbench.CropType;
import org.generationcp.middleware.pojos.workbench.Project;
import org.generationcp.middleware.pojos.workbench.ProjectUser;
import org.generationcp.middleware.pojos.workbench.Tool;
import org.generationcp.middleware.pojos.workbench.WorkbenchDataset;
import org.generationcp.middleware.pojos.workbench.WorkflowTemplate;
import org.generationcp.middleware.util.HibernateUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestWorkbenchDataManagerImpl{

    private static WorkbenchDataManager manager;
    private static HibernateUtil hibernateUtil;

    @BeforeClass
    public static void setUp() throws Exception {
    	
    	hibernateUtil = new HibernateUtil("localhost", "3306", "workbench", "root", "");
        manager = new WorkbenchDataManagerImpl(hibernateUtil);
    }

    @Test
    public void testSaveProject() throws QueryException{
        Project project1 = new Project();
        project1.setProjectName("Test Project 1");
        project1.setUserId(1);
        project1.setCropType(CropType.CHICKPEA);
        project1.setTargetDueDate(new GregorianCalendar().getTime());
        project1.setLastOpenDate(new GregorianCalendar().getTime());

        Project project2 = new Project();
        project2.setProjectName("Test Project 2");
        project2.setCropType(CropType.CHICKPEA);
        project2.setTargetDueDate(new GregorianCalendar().getTime());
        project2.setLastOpenDate(new GregorianCalendar().getTime());

        WorkflowTemplate marsTemplate = new WorkflowTemplate();
        marsTemplate.setTemplateId(1L);

        project1.setTemplate(marsTemplate);
        project2.setTemplate(marsTemplate);

        manager.saveOrUpdateProject(project1);
        manager.saveOrUpdateProject(project2);
    }

    @Test
    public void testGetProjects()  throws QueryException{
        List<Project> projects = manager.getProjects();

        System.out.println("testGetProjects");
        for (Project project : projects) {
            System.out.println(project);
        }
    }

    /**
     * @Test public void testDeleteProject() { List<Project> projects =
     *       manager.getProjects(); manager.deleteProject(projects.get(0)); }
     **/

    @Test
    public void testFindTool() throws QueryException {
        Tool tool = manager.getToolWithName("fieldbook");
        System.out.println(tool);
    }
    
    @Test
    public void testAddUser() throws QueryException {
        User user = new User();
        user.setUserid(1000);
        user.setInstalid(-1);
        user.setStatus(-1);
        user.setAccess(-1);
        user.setUserid(-1);
        user.setType(-1);
        user.setName("user_test");
        user.setPassword("user_password");
        user.setPersonid(1000);
        user.setAdate(20120101);
        user.setCdate(20120101);
        
        manager.addUser(user);
    }
    
    @Test
    public void testAddPerson() throws QueryException {
        Person person = new Person();
//        person.setId(1000);
        person.setInstituteId(1);
        person.setFirstName("Lich");
        person.setMiddleName("Frozen");
        person.setLastName("King");
        person.setPositionName("King of Icewind Dale");
        person.setTitle("His Highness");
        person.setExtension("1");
        person.setFax("2");
        person.setEmail("lichking@blizzard.com");
        person.setNotes("notes");
        person.setContact("3");
        person.setLanguage(-1);
        person.setPhone("4");
        
        // add the person
        manager.addPerson(person);
    }
    
    
    @Test
    public void testGetProjectById()  throws QueryException{
        Project project = manager.getProjectById(Long.valueOf(1));
        System.out.println(project);
    }
    
    @Test
    public void testGetUserByName() throws QueryException {
        User user = (User) manager.getUserByName("jeff", 0, 1, Operation.EQUAL).get(0);
        System.out.println(user);
    }
    
    @Test
    public void testAddDataset() throws QueryException {
        WorkbenchDataset dataset = new WorkbenchDataset();
        dataset.setName("Test Dataset");
        dataset.setDescription("Test Dataset Description");
        dataset.setCreationDate(new Date(System.currentTimeMillis()));
        dataset.setProject(manager.getProjectById(Long.valueOf(1)));
        WorkbenchDataset result = null;
        try {
            result = manager.addDataset(dataset);
        } catch (QueryException e) {
            e.printStackTrace();
        }
        System.out.println("TestAddDataset: " + result);
    }
    
    @Test 
    public void testGetWorkbenchDatasetByProjectId() {
        try {
            List<WorkbenchDataset> list = manager.getWorkbenchDatasetByProjectId(1L, 0, 10);
            System.out.println("testGetWorkbenchDatasetByProjectId(): ");
            
            if(list.isEmpty()) {
                System.out.println("No records found.");
            }
            
            for(WorkbenchDataset d : list) {
                System.out.println(d.getDatasetId() + ": " + d.getName());
            }
        } catch (Exception e) {
            System.out.println("Error in testGetWorkbenchDatasetByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCountWorkbenchDatasetByProjectId() {
        try {
            Long result = manager.countWorkbenchDatasetByProjectId(1L);
            System.out.println("testCountWorkbenchDatasetByProjectId(): " + result);
        } catch (Exception e) {
            System.out.println("Error in testCountWorkbenchDatasetByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetWorkbenchDatasetByName() {
        try {
            List<WorkbenchDataset> list = manager.getWorkbenchDatasetByName("D", Operation.EQUAL, 0, 10);
            System.out.println("testGetWorkbenchDatasetByName(): ");
            
            if(list.isEmpty()) {
                System.out.println("No records found.");
            }
            
            for(WorkbenchDataset d : list) {
                System.out.println(d.getDatasetId() + ": " + d.getName());
            }
        } catch (Exception e) {
            System.out.println("Error in testGetWorkbenchDatasetByName(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCountWorkbenchDatasetByName() {
        try {
            Long result = manager.countWorkbenchDatasetByName("a", Operation.EQUAL);
            System.out.println("testCountWorkbenchDatasetByName(): " + result);
        } catch (Exception e) {
            System.out.println("Error in testCountWorkbenchDatasetByName(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetLocationIdsByProjectId() {
        try {
            List<Long> ids = manager.getLocationIdsByProjectId(2L, 0, 10);
            System.out.println("testgetLocationIdsByProjectId(): " + ids);
        } catch (Exception e) {
            System.out.println("Error in testGetLocationIdsByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCountLocationIdsByProjectId() {
        try {
            Long result = manager.countLocationIdsByProjectId(1L);
            System.out.println("testCountLocationIdsByProjectId(): " + result);
        } catch (Exception e) {
            System.out.println("Error in testCountLocationIdsByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testGetMethodsByProjectId() {
        try {
            List<Method> list = manager.getMethodsByProjectId(1L, 0, 10);
            System.out.println("testGetMethodsByProjectId(): ");
            
            if(list.isEmpty()) {
                System.out.println("No records found.");
            }
            
            for(Method m : list) {
                System.out.println("  " + m);
            }
        } catch (Exception e) {
            System.out.println("Error in testGetMethodsByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCountMethodsByProjectId() {
        try {
            Long result = manager.countMethodsByProjectId(1L);
            System.out.println("testCountMethodsByProjectId(): " + result);
        } catch (Exception e) {
            System.out.println("Error in testCountMethodsByProjectId(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testAddProjectUsers() throws QueryException {
        List<ProjectUser> projectUsers = new ArrayList<ProjectUser>();

        // Assumptions: Project with id=1, and Users with id=1 and id=2 exist in the database
        Project project1 = manager.getProjectById(1L);
        User user1 = manager.getUserById(1);
        User user2 = manager.getUserById(2);
        projectUsers.add(new ProjectUser(project1, user1));
        projectUsers.add(new ProjectUser(project1, user2));

        // add the projectUsers
        int projectUsersAdded = manager.addProjectUsers(projectUsers);
        
        System.out.println("ProjectUsers added: " + projectUsersAdded);
        System.out.println("  " + manager.getProjectUserByProjectAndUser(project1, user1));
        System.out.println("  " + manager.getProjectUserByProjectAndUser(project1, user2));
        
        manager.deleteProjectUser(manager.getProjectUserByProjectAndUser(project1, user1));
        manager.deleteProjectUser(manager.getProjectUserByProjectAndUser(project1, user2));
    }
    
    @Test
    public void testGetUsersByProjectId() {
        try {
            List<User> users =  manager.getUsersByProjectId(1L);
            System.out.println("testGetUsersByProjectId(): ");
            
            if(users.isEmpty()) {
                System.out.println("No records found.");
            }
            
            for(User u : users) {
                System.out.println(u.getUserid() + ": " + u.getName());
            }
        } catch (Exception e) {
            System.out.println("Error in testGetUsersByProjectId(): " + e.getMessage());
            e.printStackTrace();
        } 
    }
    
    @Test
    public void testCountUsersByProjectId() {
        try {
            Long result =  manager.countUsersByProjectId(1L);
            System.out.println("testCountUsersByProjectId(): " + result);
        } catch (Exception e) {
            System.out.println("Error in testCountUsersByProjectId(): " + e.getMessage());
            e.printStackTrace();
        } 
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        hibernateUtil.shutdown();
    }
}
