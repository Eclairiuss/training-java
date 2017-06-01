package fr.ebiz.nurdiales.trainingjava.persistence;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.LiteralExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.core.QComputer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("computerDAO")
public class ComputerDAOImpl implements ComputerDAO {
    private final JPAQueryFactory query;

    @PersistenceContext
    private EntityManager em;

    /**
     * Constructor.
     * @param query .
     */
    @Autowired
    public ComputerDAOImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public int create(Computer computer) {
        return (int) em.createNativeQuery("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)")
                .setParameter(1, computer.getName())
                .setParameter(2, computer.getIntroduced())
                .setParameter(3, computer.getDiscontinued())
                .setParameter(4, computer.getCompanyId())
                .executeUpdate();
    }

    @Override
    public int delete(int id) {
        QComputer c = QComputer.computer;
        return (int) query.delete(c)
                .where(c.id.eq(id))
                .execute();
    }

    @Override
    public int delete(int[] ids) {
        int tmp = 0;
        QComputer c = QComputer.computer;
        for (Integer id : ids) {
            if (tmp == 0) {
                tmp = (int) query.delete(c)
                        .where(c.id.eq(id))
                        .execute();
            } else {
                query.delete(c)
                        .where(c.id.eq(id))
                        .execute();

            }
        }
        return tmp;
    }


    @Override
    public int deleteByCompany(int id) {
        QComputer c = QComputer.computer;
        return (int) query.delete(c)
            .where(c.company.id.eq(id))
            .execute();
    }


    @Override
    public int update(Computer computer) {
        QComputer c = QComputer.computer;
        if (computer != null) {
            boolean check = computer.checkDates();
            if (check) {
                return (int) query.update(c)
                        .where(c.id.eq(computer.getId()))
                        .set(c.name, computer.getName())
                        .set(c.introduced, computer.getIntroduced())
                        .set(c.discontinued, computer.getDiscontinued())
                        .set(c.company, computer.getCompany())
                        .execute();
            } else {
                return (int) query.update(c)
                        .where(c.id.eq(computer.getId()))
                        .set(c.name, computer.getName())
                        .set(c.company, computer.getCompany())
                        .execute();
            }
        } else {
            return 0;
        }
    }

    @Override
    public Computer getComputer(int id) {
        QComputer c = QComputer.computer;
        return query.selectFrom(c)
                        .where(c.id.eq(id))
                        .fetchOne();
    }

    @Override
    public Page listComputers(Parameters params, List<Company> list) {
        Page page = new Page();
        QComputer c = QComputer.computer;

        JPAQuery<Computer> tmpQuery = query.selectFrom(c);
        tmpQuery = tmpQuery.where(c.name.contains(params.getName())
                    .or(c.company.name.contains(params.getNameCompany())));

        page.setQuantity(tmpQuery.clone()
                .fetchCount());

        switch (params.getSortBy()) {
            case NAME:
                tmpQuery = tmpQuery.orderBy(isReversed(c.name, params.isReversed()));
                break;
            case INTRODUCED:
                tmpQuery = tmpQuery.orderBy(isReversed(c.introduced, params.isReversed()));
                break;
            case DISCONTINUED:
                tmpQuery = tmpQuery.orderBy(isReversed(c.discontinued, params.isReversed()));
                break;
            case COMPANY:
                tmpQuery = tmpQuery.orderBy(isReversed(c.company.name, params.isReversed()));
                break;
            default:
                break;
        }

        page.setComputers(tmpQuery.limit(params.getSize())
                .offset(params.getSize() * params.getPage())
                .fetch());

        return page;
    }

    /**
     * Create the OrderSpecifier from LiteralExpression and boolean for reverse or not.
     * @param le LiteralExpression.
     * @param isReversed .
     * @return orderSpecifier.
     */
    private OrderSpecifier isReversed(LiteralExpression le, boolean isReversed) {
        if (isReversed) {
            return le.desc();
        }
        return le.asc();
    }
}