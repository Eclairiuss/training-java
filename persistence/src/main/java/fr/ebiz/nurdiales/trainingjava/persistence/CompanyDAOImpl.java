package fr.ebiz.nurdiales.trainingjava.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.QCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("companyDAO")
public class CompanyDAOImpl implements CompanyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    private JPAQueryFactory query;
    @PersistenceContext
    private EntityManager em;

    /**
     * Constructor.
     * @param query .
     */
    @Autowired
    public CompanyDAOImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public int create(Company company) {
        return (int) em.createNativeQuery("INSERT INTO company (name) VALUES (?)")
                .setParameter(1, company.getName())
                .executeUpdate();
    }

    @Override
    public Company getCompany(Integer id) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.id.eq(id).and(c.id.ne(44)))
                .fetchOne();
    }

    @Override
    public List<Company> listCompanies() {
        return query.selectFrom(QCompany.company)
                .fetch();
    }

    @Override
    public int delete(Integer id) {
        QCompany c = QCompany.company;
        return (int) query.delete(c)
                .where(c.id.eq(id).and(c.id.ne(44)))
                .execute();
    }

    @Override
    public int update(Company company) {
        QCompany c = QCompany.company;
        return (int) query.update(c)
                .where(c.id.eq(company.getId()).and(c.id.ne(44)))
                .set(c.name, company.getName())
                .execute();
    }

    @Override
    public List<Company> listCompanies(Integer page, Integer size) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .limit(size)
                .offset(size * page)
                .fetch();
    }

    @Override
    public List<Company> listCompanies(String name) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.name.contains(name).and(c.id.ne(44)))
                .fetch();
    }

    @Override
    public List<Company> listCompanies(String name, Integer page, Integer size) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.name.contains(name).and(c.id.ne(44)))
                .limit(size)
                .offset(size * page)
                .fetch();
    }
}
