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
import org.generationcp.middleware.pojos.Installation;
import org.generationcp.middleware.pojos.Person;
import org.generationcp.middleware.pojos.User;

/**
 * The Interface UserDataManager.
 */
public interface UserDataManager {
    
    /**
     * Returns all Persons.
     *
     * @return gets all Persons
     */   	
    public List<User> getAllUsers();
    
    /**
     * Returns number of all Users.
     *
     * @return the number of all Users
     */   
    public int countAllUsers();
    
    /**
     * Adds the user.
     *
     * @param user the user
     * @throws QueryException the query exception
     */
    public void addUser(User user) throws QueryException;
    
    /**
     * Gets the user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public User getUserById(int id);
    
    /**
     * Delete user.
     *
     * @param user the user
     * @throws QueryException the query exception
     */
    public void deleteUser(User user) throws QueryException;
    
    /**
     * Returns all Persons.
     *
     * @return gets all Persons
     */   
    public List<Person> getAllPersons();
    
    /**
     * Returns number of all Persons.
     *
     * @return the number of all Persons
     */   
    public int countAllPersons();
    
    
    /**
     * Adds the person.
     *
     * @param person the person
     * @throws QueryException the query exception
     */
    public void addPerson(Person person) throws QueryException;
    
    /**
     * Gets the person by id.
     *
     * @param id the id
     * @return the person by id
     */
    public Person getPersonById(int id);
    
    /**
     * Delete person.
     *
     * @param person the person
     * @throws QueryException the query exception
     */
    public void deletePerson(Person person) throws QueryException; 
    
    /**
     * Checks if is valid user login.
     *
     * @param username the username
     * @param password the password
     * @return true, if is valid user login
     * @throws QueryException the query exception
     */
    public boolean isValidUserLogin(String username, String password) throws QueryException;
    
    /**
     * Checks if is person exists.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @return true, if is person exists
     * @throws QueryException the query exception
     */
    public boolean isPersonExists(String firstName, String lastName) throws QueryException;
    
    /**
     * Checks if is username exists.
     *
     * @param userName the user name
     * @return true, if is username exists
     * @throws QueryException the query exception
     */
    public boolean isUsernameExists(String userName) throws QueryException;
    
    /**
     * Returns all the installation records
     * 
     * @param start
     *            - the starting index of the sublist of results to be returned
     * @param numOfRows
     *            - the number of rows to be included in the sublist of results
     *            to be returned
     * @param instance - to specify the instace from where the records will be retrieved
     *          either from the central or local
     * @return
     * @throws QueryException
     */
    public List<Installation> getAllInstallationRecords(int start, int numOfRows, Database instance) 
        throws QueryException; 
    
    /**
     * Return the installation record identified by the given id.
     * 
     * @param id 
     * @return
     * @throws QueryException
     */
    public Installation getInstallationRecordById(Long id) throws QueryException;
    
    /**
     * Get the installation records with the admin id equal to the given id.
     * 
     * @param id
     * @return
     * @throws QueryException
     */
    public List<Installation> getInstallationRecordsByAdminId(Long id) throws QueryException;
    
    /**
     * Returns the installation record which have been added last to the database.
     * 
     * @param instance
     * @return
     * @throws QueryException
     */
    public Installation getLatestInstallationRecord(Database instance) throws QueryException;
}
