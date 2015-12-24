package com.polarbear.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BaseDao<T> {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void store(T obj) {
        hibernateTemplate.saveOrUpdate(obj);
    }

    public void delete(T obj) {
        hibernateTemplate.delete(obj);
    }

    @SuppressWarnings("unchecked")
    public T findById(Class clazz, long id) {
        return (T) hibernateTemplate.get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(Class clazz) {
        StringBuilder sb = new StringBuilder("from ");
        return hibernateTemplate.find(sb.append(clazz.getSimpleName()).toString());
    }

    public T findByNamedQueryObject(String nameQuery, Object... values) {
        List<T> list = findByNamedQuery(nameQuery, values);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String nameQuery, Object... values) {
        return hibernateTemplate.findByNamedQuery(nameQuery, values);
    }

    public List<T> findByNamedQueryByPage(final String nameQuery, final int pageNo, final int pageSize) {
        return findByNamedQueryByPage(nameQuery, null, pageNo, pageSize);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryByPage(final String nameQuery, final Object[] values, final int pageNo, final int pageSize) {
        return hibernateTemplate.executeFind(new HibernateCallback<T>() {
            public T doInHibernate(Session session) {
                Query query = (Query) session.getNamedQuery(nameQuery);
                query.setFirstResult((pageNo - 1) * pageSize);
                query.setMaxResults(pageSize);
                for (int i = 0; values != null && i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
                return (T) query.list();
            }
        });
    }

    public T executeUpdate(final String sql, final Object... values) {
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            @SuppressWarnings("unchecked")
            public T doInHibernate(Session session) {
                Query query = session.getNamedQuery(sql);
                for (int i = 0; values != null && i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
                return (T) Integer.valueOf(query.executeUpdate());
            }
        });
    }

}