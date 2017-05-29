package fr.ebiz.nurdiales.trainingJava.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.LiteralExpression;
import fr.ebiz.nurdiales.trainingJava.core.Company;
import fr.ebiz.nurdiales.trainingJava.core.Computer;
import fr.ebiz.nurdiales.trainingJava.core.QComputer;
import fr.ebiz.nurdiales.trainingJava.core.Page;
import fr.ebiz.nurdiales.trainingJava.core.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("computerDAO")
public class ComputerDAOImpl implements ComputerDAO {
    private final JPAQueryFactory query;

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
        QComputer c = QComputer.computer;
        return (int) query.update(c)
                .set(c.name, computer.getName())
                .set(c.introduced, computer.getIntroduced())
                .set(c.discontinued, computer.getDiscontinued())
                .set(c.company, computer.getCompany())
                .execute();
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
        Page retour = new Page();

        QComputer c = QComputer.computer;
        LiteralExpression<?> byHandled = null;
        switch (params.getTrierPar()) {
            case NAME:
                byHandled = c.name;
                break;
            case INTRODUCED:
                byHandled = c.introduced;
                break;
            case DISCONTINUED:
                byHandled = c.discontinued;
                break;
            case COMPANY:
                byHandled = c.company.name;
                break;
            default:
                break;
        }
        OrderSpecifier<?> orderHandled = params.isReversed() ? byHandled.desc() : byHandled.asc();

        retour.setComputers(query.selectFrom(c)
                .where(c.name.contains(params.getName())
                        .or(c.company.name.contains(params.getNameCompany())))
                .orderBy(orderHandled)
                .limit(params.getSize())
                .offset(params.getSize() * params.getPage())
                .fetch());

        retour.setQuantity(query.selectFrom(c)
                .where(c.name.contains(params.getName())
                        .or(c.company.name.contains(params.getNameCompany())))
                .fetchCount());

        return retour;
    }
}