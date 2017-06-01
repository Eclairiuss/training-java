package fr.ebiz.nurdiales.trainingjava.persistence.security;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fr.ebiz.nurdiales.trainingjava.core.security.QUser;
import fr.ebiz.nurdiales.trainingjava.core.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    private final JPAQueryFactory query;
    /**
     * Constructor.
     * @param query .
     */
    @Autowired
    public UserDAOImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public User getByName(String username) {
        QUser u = QUser.user;
        return query.selectFrom(u).where(u.username.eq(username)).fetchOne();
    }
}
