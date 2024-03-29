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

package org.generationcp.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.generationcp.middleware.dao.CropTypeDAO;
import org.generationcp.middleware.dao.IbdbUserMapDAO;
import org.generationcp.middleware.dao.PersonDAO;
import org.generationcp.middleware.dao.ProjectActivityDAO;
import org.generationcp.middleware.dao.ProjectDAO;
import org.generationcp.middleware.dao.ProjectLocationMapDAO;
import org.generationcp.middleware.dao.ProjectMethodDAO;
import org.generationcp.middleware.dao.ProjectUserMysqlAccountDAO;
import org.generationcp.middleware.dao.ProjectUserRoleDAO;
import org.generationcp.middleware.dao.RoleDAO;
import org.generationcp.middleware.dao.SecurityQuestionDAO;
import org.generationcp.middleware.dao.ToolConfigurationDAO;
import org.generationcp.middleware.dao.ToolDAO;
import org.generationcp.middleware.dao.UserDAO;
import org.generationcp.middleware.dao.WorkbenchDatasetDAO;
import org.generationcp.middleware.dao.WorkbenchRuntimeDataDAO;
import org.generationcp.middleware.dao.WorkbenchSettingDAO;
import org.generationcp.middleware.dao.WorkflowTemplateDAO;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.hibernate.HibernateSessionProvider;
import org.generationcp.middleware.manager.api.WorkbenchDataManager;
import org.generationcp.middleware.pojos.Person;
import org.generationcp.middleware.pojos.User;
import org.generationcp.middleware.pojos.workbench.CropType;
import org.generationcp.middleware.pojos.workbench.IbdbUserMap;
import org.generationcp.middleware.pojos.workbench.Project;
import org.generationcp.middleware.pojos.workbench.ProjectActivity;
import org.generationcp.middleware.pojos.workbench.ProjectLocationMap;
import org.generationcp.middleware.pojos.workbench.ProjectMethod;
import org.generationcp.middleware.pojos.workbench.ProjectUserMysqlAccount;
import org.generationcp.middleware.pojos.workbench.ProjectUserRole;
import org.generationcp.middleware.pojos.workbench.Role;
import org.generationcp.middleware.pojos.workbench.SecurityQuestion;
import org.generationcp.middleware.pojos.workbench.Tool;
import org.generationcp.middleware.pojos.workbench.ToolConfiguration;
import org.generationcp.middleware.pojos.workbench.ToolType;
import org.generationcp.middleware.pojos.workbench.WorkbenchDataset;
import org.generationcp.middleware.pojos.workbench.WorkbenchRuntimeData;
import org.generationcp.middleware.pojos.workbench.WorkbenchSetting;
import org.generationcp.middleware.pojos.workbench.WorkflowTemplate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkbenchDataManagerImpl implements WorkbenchDataManager{

    private static final Logger LOG = LoggerFactory.getLogger(WorkbenchDataManagerImpl.class);

    private HibernateSessionProvider sessionProvider;

    public WorkbenchDataManagerImpl(HibernateSessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public Session getCurrentSession() {
        return sessionProvider.getSession();
    }

    @Override
    public List<Project> getProjects() throws MiddlewareQueryException {
        ProjectDAO projectDao = new ProjectDAO();
        projectDao.setSession(getCurrentSession());
        return projectDao.getAll();
    }

    @Override
    public List<Project> getProjects(int start, int numOfRows) throws MiddlewareQueryException {
        ProjectDAO projectDao = new ProjectDAO();
        projectDao.setSession(getCurrentSession());
        return projectDao.getAll(start, numOfRows);
    }
    
    @Override
    public List<Project> getProjectsByUser(User user) throws MiddlewareQueryException {
        ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
        dao.setSession(getCurrentSession());
        return dao.getProjectsByUser(user);
    }

    @Override
    public Project saveOrUpdateProject(Project project) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        try {
            trans = session.beginTransaction();

            ProjectDAO projectDao = new ProjectDAO();
            projectDao.setSession(session);
            projectDao.merge(project);

            // TODO: copy the workbench template created by the project into the
            // project_workflow_step table

            // commit transaction
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException("Cannot save Project: WorkbenchDataManager.saveOrUpdateProject(project=" + project + "): "
                    + e.getMessage(), e);
        }

        return project;
    }
    
    public Project addProject(Project project) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ProjectDAO projectDao = new ProjectDAO();
            projectDao.setSession(session);
            projectDao.save(project);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Cannot save Project: WorkbenchDataManager.addProject(project=" + project + "): "
                + e.getMessage(), e);
        }
        
        return project;
    }
    
    public Project mergeProject(Project project) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ProjectDAO projectDao = new ProjectDAO();
            projectDao.setSession(session);
            projectDao.merge(project);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Cannot save Project: WorkbenchDataManager.updateProject(project=" + project + "): "
                + e.getMessage(), e);
        }
        
        return project;
    }

    @Override
    public void deleteProject(Project project) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        try {
            trans = session.beginTransaction();

            ProjectDAO projectDao = new ProjectDAO();
            projectDao.setSession(session);
            projectDao.makeTransient(project);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException("Cannot delete Project: WorkbenchDataManager.deleteProject(project=" + project + "): "
                    + e.getMessage(), e);
        }
    }

    @Override
    public List<WorkflowTemplate> getWorkflowTemplates() throws MiddlewareQueryException {
        WorkflowTemplateDAO workflowTemplateDAO = new WorkflowTemplateDAO();
        workflowTemplateDAO.setSession(getCurrentSession());
        return workflowTemplateDAO.getAll();
    }
    
    @Override
    public List<WorkflowTemplate> getWorkflowTemplateByName(String name) throws MiddlewareQueryException {
        WorkflowTemplateDAO workflowTemplateDAO = new WorkflowTemplateDAO();
        workflowTemplateDAO.setSession(getCurrentSession());
        return workflowTemplateDAO.getByName(name);
    }

    @Override
    public List<WorkflowTemplate> getWorkflowTemplates(int start, int numOfRows) throws MiddlewareQueryException {
        WorkflowTemplateDAO workflowTemplateDAO = new WorkflowTemplateDAO();
        workflowTemplateDAO.setSession(getCurrentSession());
        return workflowTemplateDAO.getAll(start, numOfRows);
    }
    
    @Override
    public List<Tool> getAllTools() throws MiddlewareQueryException {
        ToolDAO toolDAO = new ToolDAO();
        toolDAO.setSession(getCurrentSession());
        return toolDAO.getAll();
    }

    @Override
    public Tool getToolWithName(String toolId) throws MiddlewareQueryException {
        ToolDAO toolDAO = new ToolDAO();
        toolDAO.setSession(getCurrentSession());
        return toolDAO.getByToolName(toolId);
    }

    @Override
    public List<Tool> getToolsWithType(ToolType toolType) throws MiddlewareQueryException {
        ToolDAO toolDAO = new ToolDAO();
        toolDAO.setSession(getCurrentSession());
        return toolDAO.getToolsByToolType(toolType);
    }

    @Override
    public boolean isValidUserLogin(String username, String password) throws MiddlewareQueryException {
        UserDAO dao = new UserDAO();
        dao.setSession(getCurrentSession());
        User user = dao.getByUsernameAndPassword(username, password);
        if (user != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isPersonExists(String firstName, String lastName) throws MiddlewareQueryException {
        PersonDAO dao = new PersonDAO();
        dao.setSession(getCurrentSession());
        return dao.isPersonExists(firstName, lastName);
    }

    @Override
    public boolean isUsernameExists(String userName) throws MiddlewareQueryException {
        UserDAO dao = new UserDAO();
        dao.setSession(getCurrentSession());

        return dao.isUsernameExists(userName);
    }

    @Override
    public Integer addPerson(Person person) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        
        Integer idPersonSaved;
        try {
            // begin save transaction
            trans = session.beginTransaction();

            SQLQuery q = session.createSQLQuery("SELECT MAX(personid) FROM persons");
            Integer personId = (Integer) q.uniqueResult();

            if (personId == null || personId.intValue() < 0) {
                person.setId(1);
            } else {
                person.setId(personId + 1);
            }

            PersonDAO dao = new PersonDAO();
            dao.setSession(session);

            Person recordSaved = dao.saveOrUpdate(person);
            idPersonSaved = recordSaved.getId();

            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addPerson: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Error encountered while saving Person: WorkbenchDataManager.addPerson(person=" + person
                    + "): " + e.getMessage(), e);
        }
        return idPersonSaved;
    }

    @Override
    public Integer addUser(User user) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        
        Integer idUserSaved;
        try {
            // begin save transaction
            trans = session.beginTransaction();

            SQLQuery q = session.createSQLQuery("SELECT MAX(userid) FROM users");
            Integer userId = (Integer) q.uniqueResult();

            if (userId == null || userId.intValue() < 0) {
                user.setUserid(1);
            } else {
                user.setUserid(userId + 1);
            }

            UserDAO dao = new UserDAO();
            dao.setSession(session);

            User recordSaved = dao.saveOrUpdate(user);
            idUserSaved = recordSaved.getUserid();

            trans.commit();
        } catch (Exception e) {
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Error encountered while saving User: WorkbenchDataManager.addUser(user=" + user + "): "
                    + e.getMessage(), e);
        }
        
        return idUserSaved;

    }

    public Project getProjectById(Long projectId) throws MiddlewareQueryException {
        ProjectDAO projectDao = new ProjectDAO();
        projectDao.setSession(getCurrentSession());
        return projectDao.getById(projectId);
    }
    
    public Project getProjectByName(String projectName) throws MiddlewareQueryException{
        ProjectDAO projectDao = new ProjectDAO();
        projectDao.setSession(getCurrentSession());
        return projectDao.getByName(projectName);
    }


    public Integer addWorkbenchDataset(WorkbenchDataset dataset) throws MiddlewareQueryException {
        Session session = getCurrentSession();

        Transaction trans = null;
        int recordSaved = 0;
        Integer workbenchDatasetSaved;
        try {
            // begin save transaction
            trans = session.beginTransaction();
            WorkbenchDatasetDAO datasetDAO = new WorkbenchDatasetDAO();
            datasetDAO.setSession(session);
            WorkbenchDataset datasetSaved = datasetDAO.saveOrUpdate(dataset);
            workbenchDatasetSaved = datasetSaved.getDatasetId();
            recordSaved++;
            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addDataset: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while saving workbench dataset: WorkbenchDataManager.addWorkbenchDataset(dataset=" + dataset + "): "
                            + e.getMessage(), e);
        }

        return workbenchDatasetSaved;
    }

    @Override
    public WorkbenchDataset getWorkbenchDatasetById(Long datasetId) throws MiddlewareQueryException {
        WorkbenchDatasetDAO datasetDAO = new WorkbenchDatasetDAO();
        datasetDAO.setSession(getCurrentSession());
        return datasetDAO.getById(datasetId);
    }

    @Override
    public void deleteWorkbenchDataset(WorkbenchDataset dataset) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        try {
            trans = session.beginTransaction();
            WorkbenchDatasetDAO datasetDAO = new WorkbenchDatasetDAO();
            datasetDAO.setSession(session);
            datasetDAO.makeTransient(dataset);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException("Cannot delete WorkbenchDataset: WorkbenchDataManager.deleteWorkbenchDataset(dataset="
                    + dataset + "):  " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() throws MiddlewareQueryException {
        UserDAO dao = new UserDAO();

        List<User> users = new ArrayList<User>();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            users.addAll(dao.getAll());
        }

        return users;
    }
    
    @Override
    public List<User> getAllUsersSorted() throws MiddlewareQueryException {
        UserDAO dao = new UserDAO();

        List<User> users = new ArrayList<User>();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            users.addAll(dao.getAllUsersSorted());
        }

        return users;
    }

    public long countAllUsers() throws MiddlewareQueryException {
        long count = 0;
        UserDAO dao = new UserDAO();
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = count + dao.countAll();
        }
        return count;
    }

    @Override
    public User getUserById(int id) throws MiddlewareQueryException {
        UserDAO dao = new UserDAO();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
        } else {
            return null;
        }

        return dao.getById(id, false);
    }

    @Override
    public List<User> getUserByName(String name, int start, int numOfRows, Operation op) throws MiddlewareQueryException {

        UserDAO dao = new UserDAO();

        List<User> users = new ArrayList<User>();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
        } else {
            return users;
        }

        if (op == Operation.EQUAL) {
            users = dao.getByNameUsingEqual(name, start, numOfRows);
        } else if (op == Operation.LIKE) {
            users = dao.getByNameUsingLike(name, start, numOfRows);
        }

        return users;

    }

    @Override
    public void deleteUser(User user) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            UserDAO dao = new UserDAO();
            dao.setSession(session);

            dao.makeTransient(user);

            trans.commit();
        } catch (Exception e) {
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Error encountered while deleting User: WorkbenchDataManager.deleteUser(user=" + user
                    + "):  " + e.getMessage(), e);
        }
    }

    @Override
    public List<Person> getAllPersons() throws MiddlewareQueryException {
        PersonDAO dao = new PersonDAO();

        List<Person> persons = new ArrayList<Person>();

        // get the list of Persons from the local instance
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            persons.addAll(dao.getAll());
        }

        return persons;
    }

    public long countAllPersons() throws MiddlewareQueryException {
        long count = 0;
        PersonDAO dao = new PersonDAO();
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = count + dao.countAll();
        }

        return count;
    }

    @Override
    public Person getPersonById(int id) throws MiddlewareQueryException {
        PersonDAO dao = new PersonDAO();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
        } else {
            return null;
        }

        return dao.getById(id, false);
    }

    @Override
    public void deletePerson(Person person) throws MiddlewareQueryException {
        Transaction trans = null;

        Session session = getCurrentSession();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            PersonDAO dao = new PersonDAO();
            dao.setSession(session);

            dao.makeTransient(person);

            trans.commit();
        } catch (Exception e) {
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Error encountered while deleting Person: WorkbenchDataManager.deletePerson(person="
                    + person + "): " + e.getMessage(), e);
        }
    }

    @Override
    public Project getLastOpenedProject(Integer userId) throws MiddlewareQueryException {
        ProjectDAO dao = new ProjectDAO();
        dao.setSession(getCurrentSession());
        return dao.getLastOpenedProject(userId);
    }

    @Override
    public List<WorkbenchDataset> getWorkbenchDatasetByProjectId(Long projectId, int start, int numOfRows) throws MiddlewareQueryException {

        WorkbenchDatasetDAO dao = new WorkbenchDatasetDAO();
        List<WorkbenchDataset> list;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            list = dao.getByProjectId(projectId, start, numOfRows);
        } else {
            list = new ArrayList<WorkbenchDataset>();
        }

        return list;
    }

    @Override
    public long countWorkbenchDatasetByProjectId(Long projectId) throws MiddlewareQueryException {
        WorkbenchDatasetDAO dao = new WorkbenchDatasetDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countByProjectId(projectId);
        }

        return count;
    }

    @Override
    public List<WorkbenchDataset> getWorkbenchDatasetByName(String name, Operation op, int start, int numOfRows)
            throws MiddlewareQueryException {

        WorkbenchDatasetDAO dao = new WorkbenchDatasetDAO();
        List<WorkbenchDataset> list;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            list = dao.getByName(name, op, start, numOfRows);
        } else {
            list = new ArrayList<WorkbenchDataset>();
        }

        return list;
    }

    @Override
    public long countWorkbenchDatasetByName(String name, Operation op) throws MiddlewareQueryException {
        WorkbenchDatasetDAO dao = new WorkbenchDatasetDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countByName(name, op);
        }

        return count;
    }

    @Override
    public List<Long> getLocationIdsByProjectId(Long projectId, int start, int numOfRows) throws MiddlewareQueryException {

        ProjectLocationMapDAO dao = new ProjectLocationMapDAO();
        List<Long> ids;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            ids = dao.getLocationIdsByProjectId(projectId, start, numOfRows);
        } else {
            ids = new ArrayList<Long>();
        }

        return ids;
    }

    @Override
    public long countLocationIdsByProjectId(Long projectId) throws MiddlewareQueryException {
        ProjectLocationMapDAO dao = new ProjectLocationMapDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countLocationIdsByProjectId(projectId);
        }

        return count;
    }

    @Override
    public List<Integer> getMethodIdsByProjectId(Long projectId, int start, int numOfRows) throws MiddlewareQueryException {

        ProjectMethodDAO dao = new ProjectMethodDAO();
        List<Integer> list;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            list = dao.getByProjectId(projectId, start, numOfRows);
        } else {
            list = new ArrayList<Integer>();
        }

        return list;

    }

    @Override
    public long countMethodIdsByProjectId(Long projectId) throws MiddlewareQueryException {
        ProjectMethodDAO dao = new ProjectMethodDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countByProjectId(projectId);
        }

        return count;
    }

    @Override
    public Integer addProjectUserRole(Project project, User user, Role role) throws MiddlewareQueryException {
        ProjectUserRole projectUserRole = new ProjectUserRole();
        projectUserRole.setProject(project);
        projectUserRole.setUserId(user.getUserid());
        projectUserRole.setRole(role);
        return addProjectUserRole(projectUserRole);
    }

    @Override
    public Integer addProjectUserRole(ProjectUserRole projectUserRole) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return 0;
        }

        Transaction trans = null;

        int recordsSaved = 0;
        Integer idSaved;
        try {
            // begin save transaction
            trans = session.beginTransaction();
            ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
            dao.setSession(session);

            ProjectUserRole recordSaved = dao.saveOrUpdate(projectUserRole);
            idSaved = recordSaved.getProjectUserId();
            recordsSaved++;

            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addProjectUser: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while saving ProjectUserRole: WorkbenchDataManager.addProjectUserRole(projectUserRole=" + projectUserRole + "): "
                            + e.getMessage(), e);
        }

        return idSaved;
    }

    @Override
    public List<Integer> addProjectUserRole(List<ProjectUserRole> projectUserRoles) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return new ArrayList<Integer>();
        }
        Transaction trans = null;

        int recordsSaved = 0;
        List<Integer> idsSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();
            ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
            dao.setSession(session);

            for (ProjectUserRole projectUser : projectUserRoles) {
                ProjectUserRole recordSaved = dao.saveOrUpdate(projectUser);
                idsSaved.add(recordSaved.getProjectUserId());
                recordsSaved++;
            }

            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addProjectUserRoles: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while saving ProjectUserRoles: WorkbenchDataManager.addProjectUserRoles(projectUserRoles=" + projectUserRoles
                            + "): " + e.getMessage(), e);
        }

        return idsSaved;
    }

    @Override
    public ProjectUserRole getProjectUserRoleById(Integer id) throws MiddlewareQueryException {
        ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            return dao.getById(id);
        }
        return null;
    }
    
    @Override
    public List<ProjectUserRole> getProjectUserRolesByProject(Project project) throws MiddlewareQueryException{
        ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            return dao.getByProject(project);
        }
        return null;
    }
    
    @Override
    public void deleteProjectUserRole(ProjectUserRole projectUserRole) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return;
        }

        Transaction trans = null;

        try {
            // begin save transaction
            trans = session.beginTransaction();

            ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
            dao.setSession(session);

            dao.makeTransient(projectUserRole);

            trans.commit();
        } catch (Exception e) {
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while deleting ProjectUser: WorkbenchDataManager.deleteProjectUser(projectUser=" + projectUserRole
                            + "): " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getUsersByProjectId(Long projectId) throws MiddlewareQueryException {
        Session session = getCurrentSession();

        ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
        List<User> users;
        if (session != null) {
            dao.setSession(session);
            users = dao.getUsersByProjectId(projectId);
        } else {
            users = new ArrayList<User>();
        }

        return users;
    }

    @Override
    public long countUsersByProjectId(Long projectId) throws MiddlewareQueryException {
        ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countUsersByProjectId(projectId);
        }

        return count;
    }

    @Override
    public List<CropType> getInstalledCentralCrops() throws MiddlewareQueryException {
        CropTypeDAO dao = new CropTypeDAO();
        List<CropType> cropTypes = new ArrayList<CropType>();

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            cropTypes = (List<CropType>) dao.getAll();
        }

        return cropTypes;

    }

    @Override
    public CropType getCropTypeByName(String cropName) throws MiddlewareQueryException {
        CropTypeDAO dao = new CropTypeDAO();
        CropType cropType = null;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            cropType = dao.getByName(cropName);
        }

        return cropType;
    }

    @Override
    public String addCropType(CropType cropType) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return null;
        }

        CropTypeDAO dao = new CropTypeDAO();
        dao.setSession(session);

        if (dao.getByName(cropType.getCropName()) != null) {
            LOG.info("Crop type already exists");
            throw new MiddlewareQueryException("Crop type already exists.");
        }

        Transaction trans = null;
        int recordsSaved = 0;
        String idSaved;
        try {
            // begin save transaction
            trans = session.beginTransaction();
            CropType recordSaved = dao.saveOrUpdate(cropType);
            idSaved = recordSaved.getCropName();
            recordsSaved++;
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addCropType: WorkbenchDataManager.addCropType(cropType=" + cropType + "): " + e.getMessage() + "\n"
                    + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException("Error encountered while adding crop type: WorkbenchDataManager.addCropType(cropType="
                    + cropType + "): " + e.getMessage(), e);
        }

        return idSaved;
    }

    @Override
    public Integer addProjectLocationMap(ProjectLocationMap projectLocationMap) throws MiddlewareQueryException {
        List<ProjectLocationMap> list = new ArrayList<ProjectLocationMap>();
        list.add(projectLocationMap);
        List<Integer> ids = addProjectLocationMap(list);
        return ids.size() > 0 ? ids.get(0) : null;
    }

    public List<Integer> addProjectLocationMap(List<ProjectLocationMap> projectLocationMapDatas) throws MiddlewareQueryException {
        return addOrUpdateProjectLocationMapData(projectLocationMapDatas, Operation.ADD);
    }

    private List<Integer> addOrUpdateProjectLocationMapData(List<ProjectLocationMap> projectLocationMapDatas, Operation operation)
            throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return new ArrayList<Integer>();
        }

        Transaction trans = null;

        int recordsSaved = 0;
        List<Integer> idsSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            ProjectLocationMapDAO dao = new ProjectLocationMapDAO();
            dao.setSession(session);

            for (ProjectLocationMap projectLocationMapData : projectLocationMapDatas) {
                ProjectLocationMap recordSaved = dao.saveOrUpdate(projectLocationMapData);
                idsSaved.add(recordSaved.getId());
                recordsSaved++;

            }
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addProjectLocation: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while adding ProjectLocation: WorkbenchDataManager.addOrUpdateProjectLocationMapData(projectLocationMapDatas="
                            + projectLocationMapDatas + ", operation=" + operation + "): " + e.getMessage(), e);
        }

        return idsSaved;
    }

    @Override
    public List<ProjectLocationMap> getProjectLocationMapByProjectId(Long projectId, int start, int numOfRows)
            throws MiddlewareQueryException {
        ProjectLocationMapDAO dao = new ProjectLocationMapDAO();
        List<ProjectLocationMap> projectLocationMap;
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            projectLocationMap = (List<ProjectLocationMap>) dao.getByProjectId(projectId, start, numOfRows);
        } else {
            projectLocationMap = new ArrayList<ProjectLocationMap>();
        }

        return projectLocationMap;

    }

    @Override
    public void deleteProjectLocationMap(ProjectLocationMap projectLocationMap) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ProjectLocationMapDAO dao = new ProjectLocationMapDAO();
            dao.setSession(session);

            dao.makeTransient(projectLocationMap);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while deleting ProjectLocationMap: WorkbenchDataManager.deleteProjectLocationMap(projectLocationMap="
                            + projectLocationMap + "): " + e.getMessage(), e);
        }
    }

    @Override
    public Integer addProjectMethod(ProjectMethod projectMethod) throws MiddlewareQueryException {
        List<ProjectMethod> list = new ArrayList<ProjectMethod>();
        list.add(projectMethod);
        List<Integer> ids = addProjectMethod(list);
        return ids.size() > 0 ? ids.get(0) : null;
    }

    @Override
    public List<Integer> addProjectMethod(List<ProjectMethod> projectMethodList) throws MiddlewareQueryException {
        return addOrUpdateProjectMethodData(projectMethodList, Operation.ADD);
    }

    private List<Integer> addOrUpdateProjectMethodData(List<ProjectMethod> projectMethodList, Operation operation) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return new ArrayList<Integer>();
        }

        Transaction trans = null;

        int recordsSaved = 0;
        List<Integer> idsSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            ProjectMethodDAO dao = new ProjectMethodDAO();
            dao.setSession(session);

            for (ProjectMethod projectMethodListData : projectMethodList) {
                ProjectMethod recordSaved = dao.saveOrUpdate(projectMethodListData);
                idsSaved.add(recordSaved.getProjectMethodId());
                recordsSaved++;
            }
            // end transaction, commit to database
            trans.commit();

            // remove ProjectMethod data from session cache
            for (ProjectMethod projectMethodListData : projectMethodList) {
                session.evict(projectMethodListData);
                recordsSaved++;
            }
            session.evict(projectMethodList);

        } catch (Exception e) {
            LOG.error("Error in addProjectMethod: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while adding ProjectMethod: WorkbenchDataManager.addOrUpdateProjectMethodData(projectMethodList="
                            + projectMethodList + ", operation=" + operation + "): " + e.getMessage(), e);
        }

        return idsSaved;
    }

    @Override
    public List<ProjectMethod> getProjectMethodByProject(Project project, int start, int numOfRows) throws MiddlewareQueryException {
        Session session = getCurrentSession();

        ProjectMethodDAO dao = new ProjectMethodDAO();
        List<ProjectMethod> projectMethods;
        if (session != null) {
            dao.setSession(session);
            projectMethods = (List<ProjectMethod>) dao.getProjectMethodByProject(project, start, numOfRows);
        } else {
            projectMethods = new ArrayList<ProjectMethod>();
        }
        return projectMethods;

    }

    @Override
    public void deleteProjectMethod(ProjectMethod projectMethod) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ProjectMethodDAO dao = new ProjectMethodDAO();
            dao.setSession(session);

            dao.makeTransient(projectMethod);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while deleting ProjectMethod: WorkbenchDataManager.deleteProjectMethod(projectMethod="
                            + projectMethod + "): " + e.getMessage(), e);
        }
    }

    @Override
    public Integer addProjectActivity(ProjectActivity projectActivity) throws MiddlewareQueryException {
        List<ProjectActivity> list = new ArrayList<ProjectActivity>();
        list.add(projectActivity);
        List<Integer> ids = addProjectActivity(list);
        return ids.size() > 0 ? ids.get(0) : null;
    }

    @Override
    public List<Integer> addProjectActivity(List<ProjectActivity> projectActivityList) throws MiddlewareQueryException {
        return addOrUpdateProjectActivityData(projectActivityList, Operation.ADD);
    }

    private List<Integer> addOrUpdateProjectActivityData(List<ProjectActivity> projectActivityList, Operation operation)
            throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return new ArrayList<Integer>();
        }

        Transaction trans = null;

        int recordsSaved = 0;
        List<Integer> idsSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            ProjectActivityDAO dao = new ProjectActivityDAO();
            dao.setSession(session);

            for (ProjectActivity projectActivityListData : projectActivityList) {
                ProjectActivity recordSaved = dao.saveOrUpdate(projectActivityListData);
                idsSaved.add(recordSaved.getProjectActivityId());
                recordsSaved++;

            }
            // end transaction, commit to database
            trans.commit();
        } catch (Exception e) {
            LOG.error("Error in addProjectActivity: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while adding addProjectActivity: WorkbenchDataManager.addOrUpdateProjectActivityData(projectActivityList="
                            + projectActivityList + ", operation=" + operation + "): " + e.getMessage(), e);
        }

        return idsSaved;
    }

    @Override
    public List<ProjectActivity> getProjectActivitiesByProjectId(Long projectId, int start, int numOfRows) throws MiddlewareQueryException {
        ProjectActivityDAO dao = new ProjectActivityDAO();
        List<ProjectActivity> projectActivities;
        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            projectActivities = (List<ProjectActivity>) dao.getByProjectId(projectId, start, numOfRows);
        } else {
            projectActivities = new ArrayList<ProjectActivity>();
        }

        return projectActivities;
    }

    @Override
    public void deleteProjectActivity(ProjectActivity projectActivity) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ProjectActivityDAO dao = new ProjectActivityDAO();
            dao.setSession(session);

            dao.makeTransient(projectActivity);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while deleting ProjectActivity: WorkbenchDataManager.deleteProjectActivity(projectActivity="
                            + projectActivity + "): " + e.getMessage(), e);
        }
    }

    @Override
    public long countProjectActivitiesByProjectId(Long projectId) throws MiddlewareQueryException {
        ProjectActivityDAO dao = new ProjectActivityDAO();
        long count = 0;

        Session session = getCurrentSession();
        if (session != null) {
            dao.setSession(session);
            count = dao.countByProjectId(projectId);
        }

        return count;
    }

    @Override
    public Integer addToolConfiguration(ToolConfiguration toolConfig) throws MiddlewareQueryException {
        return addOrUpdateToolConfiguration(toolConfig, Operation.ADD);
    }

    @Override
    public Integer updateToolConfiguration(ToolConfiguration toolConfig) throws MiddlewareQueryException {
        return addOrUpdateToolConfiguration(toolConfig, Operation.UPDATE);
    }

    private Integer addOrUpdateToolConfiguration(ToolConfiguration toolConfig, Operation op) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        Integer idSaved;
        try {
            trans = session.beginTransaction();

            ToolConfigurationDAO dao = new ToolConfigurationDAO();
            dao.setSession(session);

            //            Do specific add/update operations here
            //            if(Operation.ADD.equals(op)) {
            //                
            //            } else if (Operation.UPDATE.equals(op)) {
            //                
            //            }
            ToolConfiguration recordSaved = dao.saveOrUpdate(toolConfig);
            idSaved = recordSaved.getConfigId();

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while saving ToolConfiguration: WorkbenchDataManager.addOrUpdateToolConfiguration(toolConfig="
                            + toolConfig + ", operation=" + op + "): " + e.getMessage(), e);
        }
        return idSaved;
    }

    @Override
    public void deleteToolConfiguration(ToolConfiguration toolConfig) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            ToolConfigurationDAO dao = new ToolConfigurationDAO();
            dao.setSession(session);

            dao.makeTransient(toolConfig);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while deleting ToolConfiguration: WorkbenchDataManager.deleteToolConfiguration(toolConfig="
                            + toolConfig + "): " + e.getMessage(), e);
        }
    }

    @Override
    public List<ToolConfiguration> getListOfToolConfigurationsByToolId(Long toolId) throws MiddlewareQueryException {
        ToolConfigurationDAO dao = new ToolConfigurationDAO();
        dao.setSession(getCurrentSession());
        return dao.getListOfToolConfigurationsByToolId(toolId);
    }

    @Override
    public ToolConfiguration getToolConfigurationByToolIdAndConfigKey(Long toolId, String configKey) throws MiddlewareQueryException {

        ToolConfigurationDAO dao = new ToolConfigurationDAO();
        dao.setSession(getCurrentSession());
        return dao.getToolConfigurationByToolIdAndConfigKey(toolId, configKey);
    }

    @Override
    public Integer addIbdbUserMap(IbdbUserMap userMap) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            IbdbUserMapDAO dao = new IbdbUserMapDAO();
            dao.setSession(session);
            dao.saveOrUpdate(userMap);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException("Error encountered while adding IbdbUserMap: WorkbenchDataManager.addIbdbUserMap(userMap="
                    + userMap + "): " + e.getMessage(), e);
        }

        return userMap.getIbdbUserId();
    }

    @Override
    public Integer getLocalIbdbUserId(Integer workbenchUserId, Long projectId) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        Integer ibdbUserId;
        try {
            trans = session.beginTransaction();

            IbdbUserMapDAO dao = new IbdbUserMapDAO();
            dao.setSession(session);
            ibdbUserId = dao.getLocalIbdbUserId(workbenchUserId, projectId);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException(
                    "Error encountered while retrieving Local IBDB user id: WorkbenchDataManager.getLocalIbdbUserId(workbenchUserId="
                            + workbenchUserId + ", projectId=" + projectId + "): " + e.getMessage(), e);
        }

        return ibdbUserId;
    }

    @Override
    public Integer updateWorkbenchRuntimeData(WorkbenchRuntimeData workbenchRuntimeData) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            WorkbenchRuntimeDataDAO dao = new WorkbenchRuntimeDataDAO();
            dao.setSession(session);
            dao.saveOrUpdate(workbenchRuntimeData);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException(
                    "Error encountered while adding IbdbUserMap: WorkbenchDataManager.updateWorkbenchRuntimeData(workbenchRuntimeData="
                            + workbenchRuntimeData + "): " + e.getMessage(), e);
        }

        return workbenchRuntimeData.getId();
    }

    @Override
    public WorkbenchRuntimeData getWorkbenchRuntimeData() throws MiddlewareQueryException {
        try {
            WorkbenchRuntimeDataDAO dao = new WorkbenchRuntimeDataDAO();
            dao.setSession(getCurrentSession());
            List<WorkbenchRuntimeData> list = dao.getAll(0, 1);

            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting WorkbenchRuntimeData: WorkbenchDataManager.getWorkbenchRuntimeData(): "
                            + e.getMessage(), e);
        }
    }

    @Override
    public Role getRoleById(Integer id) throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getById(id);
        } catch (Exception e) {
            throw new MiddlewareQueryException("Error encountered while getting role by id: WorkbenchDataManager.getRoleById(id=" + id
                    + "): " + e.getMessage(), e);
        }
    }

    @Override
    public Role getRoleByNameAndWorkflowTemplate(String name, WorkflowTemplate workflowTemplate) throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getByNameAndWorkflowTemplate(name, workflowTemplate);
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting role by name and workflow template: WorkbenchDataManager.getRoleByNameAndWorkflowTemplate(name="
                            + name + ", workflowTemplate=" + workflowTemplate + "): " + e.getMessage(), e);
        }

    }

    @Override
    public List<Role> getRolesByWorkflowTemplate(WorkflowTemplate workflowTemplate) throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getByWorkflowTemplate(workflowTemplate);
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting roles by workflow template: WorkbenchDataManager.getRolesByWorkflowTemplate(workflowTemplate="
                            + workflowTemplate + "): " + e.getMessage(), e);
        }
    }

    @Override
    public WorkflowTemplate getWorkflowTemplateByRole(Role role) throws MiddlewareQueryException {
        return role.getWorkflowTemplate();
    }

    @Override
    public List<Role> getRolesByProjectAndUser(Project project, User user) throws MiddlewareQueryException {
        try {
            ProjectUserRoleDAO dao = new ProjectUserRoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getRolesByProjectAndUser(project, user);
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting role given project and user: WorkbenchDataManager.getRoleByProjectAndUser(project="
                            + project + ", user=" + user + "): " + e.getMessage(), e);
        }
    }

    @Override
    public List<Role> getAllRoles() throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getAll();
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting all roles: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Role> getAllRolesDesc() throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getAllRolesDesc();
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting all roles (sorted): " + e.getMessage(), e);
        }
    }

    @Override
    public List<Role> getAllRolesOrderedByLabel() throws MiddlewareQueryException {
        try {
            RoleDAO dao = new RoleDAO();
            dao.setSession(getCurrentSession());
            return dao.getAllRolesOrderedByLabel();
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                    "Error encountered while getting all roles (sorted): " + e.getMessage(), e);
        }
    }
    
    @Override
    public WorkbenchSetting getWorkbenchSetting() throws MiddlewareQueryException {
        try {
            WorkbenchSettingDAO dao = new WorkbenchSettingDAO();
            dao.setSession(getCurrentSession());
            List<WorkbenchSetting> list = dao.getAll();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            throw new MiddlewareQueryException(
                                               "Error encountered while workbench setting: " + e.getMessage(), e);
        }
    }

    @Override
    public void addSecurityQuestion(SecurityQuestion securityQuestion) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        Transaction trans = null;

        try {
            trans = session.beginTransaction();

            SecurityQuestionDAO dao = new SecurityQuestionDAO();
            dao.setSession(session);
            dao.saveOrUpdate(securityQuestion);

            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            throw new MiddlewareQueryException("Error encountered while adding Security Question: " +
            		"WorkbenchDataManager.addSecurityQuestion(securityQuestion=" + securityQuestion + "): " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<SecurityQuestion> getQuestionsByUserId(Integer userId) throws MiddlewareQueryException {
        try {
            SecurityQuestionDAO dao = new SecurityQuestionDAO();
            dao.setSession(getCurrentSession());
            return dao.getByUserId(userId);
        } catch (Exception e) {
            throw new MiddlewareQueryException("Error encountered while getting Security Questions: " +
                    "WorkbenchDataManager.getQuestionsByUserId(userId=" + userId + "): " + e.getMessage(), e);
        }
    }
    
    @Override
    public ProjectUserMysqlAccount getProjectUserMysqlAccountByProjectIdAndUserId(Integer projectId, Integer userId)
            throws MiddlewareQueryException {
        ProjectUserMysqlAccountDAO dao = new ProjectUserMysqlAccountDAO();
        dao.setSession(getCurrentSession());
        return dao.getByProjectIdAndUserId(projectId, userId);
    }
    
    @Override
    public Integer addProjectUserMysqlAccount(ProjectUserMysqlAccount record) throws MiddlewareQueryException {
        List<ProjectUserMysqlAccount> tosave = new ArrayList<ProjectUserMysqlAccount>();
        tosave.add(record);
        List<Integer> idsOfRecordsSaved = addProjectUserMysqlAccount(tosave);
        if(!idsOfRecordsSaved.isEmpty()){
            return idsOfRecordsSaved.get(0);
        } else{
            return null;
        }
    }
    
    @Override
    public List<Integer> addProjectUserMysqlAccounts(List<ProjectUserMysqlAccount> records) throws MiddlewareQueryException {
        return addProjectUserMysqlAccount(records);
    }
    
    private List<Integer> addProjectUserMysqlAccount(List<ProjectUserMysqlAccount> records) throws MiddlewareQueryException {
        Session session = getCurrentSession();
        if (session == null) {
            return new ArrayList<Integer>();
        }

        Transaction trans = null;

        List<Integer> idsSaved = new ArrayList<Integer>();
        try {
            // begin save transaction
            trans = session.beginTransaction();

            ProjectUserMysqlAccountDAO dao = new ProjectUserMysqlAccountDAO();
            dao.setSession(session);

            for(ProjectUserMysqlAccount record : records) {
                ProjectUserMysqlAccount recordSaved = dao.saveOrUpdate(record);
                idsSaved.add(recordSaved.getId());
            }
            
            // end transaction, commit to database
            trans.commit();

            // remove ProjectUserMysqlAccount data from session cache
            for(ProjectUserMysqlAccount record : records) {
                session.evict(record);
            }
            session.evict(records);
            
        } catch (Exception e) {
            LOG.error("Error in addProjectUserMysqlAccount: " + e.getMessage() + "\n" + e.getStackTrace());
            e.printStackTrace();
            // rollback transaction in case of errors
            if (trans != null) {
                trans.rollback();
            }
            throw new MiddlewareQueryException(
                    "Error encountered while adding ProjectUserMysqlAccount: WorkbenchDataManager.addProjectUserMysqlAccount(records="
                            + records + "): " + e.getMessage(), e);
        }

        return idsSaved;
    }
}
