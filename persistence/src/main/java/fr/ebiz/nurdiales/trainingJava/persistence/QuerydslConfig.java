package fr.ebiz.nurdiales.trainingJava.persistence;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager em;

    /**
     * Create and return a jpa query factory.
     * @return JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
