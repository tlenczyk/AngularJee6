/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.repo;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author tomek
 */
public abstract class AbstractRepo<T> {

    @Inject
    EntityManager em;
    private final Class<T> entity;

    public AbstractRepo(Class<T> type) {
        this.entity = type;
    }

    public long count() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(this.entity)));

        try {
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            return 0;
        }
    }

    public void save(T t) {
        em.persist(t);

        em.flush();
    }

    public int deleteAll() {
        int deleted = 0;
        List<T> all = this.findAll();
        for (T e : all) {
            em.remove(e);
            deleted++;
        }
        return deleted;
    }

    public void delete(final T entity) {
        em.remove(entity);
    }

    public T deleteById(final Long id) {
        T object = findById(id);
        delete(object);

        return object;
    }

    public T update(final T t) {
        return this.em.merge(t);
    }

    public List<T> findAll() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(this.entity);
        query.from(this.entity);

        return em.createQuery(query).getResultList();
    }

    @SuppressWarnings("unchecked")
    public T findById(final Long id) {
        Class<?> clazz = getObjectClass(this.entity);

        return (T) em.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(final String namedQueryName, final Object... params) {
        Query query = em.createNamedQuery(namedQueryName);
        int i = 1;

        for (Object p : params) {
            query.setParameter(i++, p);
        }

        return query.getResultList();
    }

    public Class<?> getObjectClass(final Object type) throws IllegalArgumentException {
        Class<?> clazz = null;

        if (type == null) {
            throw new IllegalArgumentException("Null has no type. You must pass an Object");
        } else if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        } else {
            clazz = type.getClass();
        }

        return clazz;
    }
}
