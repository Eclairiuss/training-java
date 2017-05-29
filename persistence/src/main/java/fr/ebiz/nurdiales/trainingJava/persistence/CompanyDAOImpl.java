package fr.ebiz.nurdiales.trainingJava.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fr.ebiz.nurdiales.trainingJava.core.Company;
import fr.ebiz.nurdiales.trainingJava.core.QCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    private final JPAQueryFactory query;
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
        QCompany c = QCompany.company;
        return (int) query.update(c)
                .set(c.name, company.getName())
                .execute();
    }

    @Override
    public Company getCompany(Integer id) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.id.eq(id))
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
                .where(c.id.eq(id))
                .execute();
    }

    @Override
    public int update(Company company) {
        QCompany c = QCompany.company;
        return (int) query.update(c).where(c.id.eq(company.getId())).set(c.name, company.getName()).execute();
    }

    @Override
    public List<Company> listCompanies(Integer page, Integer size) {
        return query.selectFrom(QCompany.company)
                .limit(size)
                .offset(size * page)
                .fetch();
    }

    @Override
    public List<Company> listCompanies(String name) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.name.contains(name))
                .fetch();
    }

    @Override
    public List<Company> listCompanies(String name, Integer page, Integer size) {
        QCompany c = QCompany.company;
        return query.selectFrom(c)
                .where(c.name.contains(name))
                .limit(size)
                .offset(size * page)
                .fetch();
    }
}
