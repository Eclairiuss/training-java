package fr.ebiz.nurdiales.trainingJava.persistence.security;

import fr.ebiz.nurdiales.trainingJava.core.security.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDAO {
    /**
     * Get an user by name.
     * @param username .
     * @return .
     */
    User getByName(String username);
}
