package se.inera.ifv.casebox.core.service;

import java.util.List;

import se.inera.ifv.casebox.core.entity.User;
import se.inera.ifv.casebox.exception.CaseboxException;

/**
 * Service interface for user interactions. Create, edit and delete a user in casebox.
 */
public interface UserService {

    /**
     *
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @return
     * @throws CaseboxException
     */
    User createUser(String userName, String password, String firstName, String lastName) throws CaseboxException;
    
    /**
     * Delete the user with the given id. 
     * @param username
     * @throws CaseboxException throws exception if user is not possible to delete
     */
    void deleteUser(String username) throws CaseboxException;
    
    /**
     * Returns the username of the authenticated user. 
     * @return String the username
     */
    User getAuthenticatedUser();
    
    /**
     * Returns a list with all users in the system. 
     * @return {@link List} 
     */
    List<User> findAllUsers(); 
    
}
