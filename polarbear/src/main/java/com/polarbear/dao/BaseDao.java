package com.polarbear.dao;

import static com.polarbear.util.Constants.ResultState.DB_ERR;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao<T> {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public void store(T obj) throws DaoException {
        try {
            hibernateTemplate.saveOrUpdate(obj);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public void delete(T obj) throws DaoException {
        try {
            hibernateTemplate.delete(obj);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(Class clazz, long id) throws DaoException {
        try {
            return (T) hibernateTemplate.get(clazz, id);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(Class clazz) throws DaoException {
        StringBuilder sb = new StringBuilder("from ");
        try {
            return hibernateTemplate.find(sb.append(clazz.getSimpleName()).toString());
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public T findByNamedQueryObject(String nameQuery, Object... values) throws DaoException {
        List<T> list = findByNamedQuery(nameQuery, values);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String nameQuery, Object... values) throws DaoException {
        try {
            return hibernateTemplate.findByNamedQuery(nameQuery, values);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public List<T> findByNamedQueryByPage(final String nameQuery, final int pageNo, final int pageSize) throws DaoException {
        return findByNamedQueryByPage(nameQuery, null, pageNo, pageSize);
    }

    public List<T> findByNamedQueryByPage(String nameQuery, final Object[] values, String pageNo, String pageSize) throws DaoException {
        pageSize = !NumberUtils.isDigits(pageSize) ? "10" : pageSize;
        return findByNamedQueryByPage(nameQuery, values, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQueryByPage(final String nameQuery, final Object[] values, final int pageNo, final int pageSize) throws DaoException {
        try {
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
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(final String nameQuery, final Map<String, List> paramLst) throws DaoException {
        try {
            return hibernateTemplate.executeFind(new HibernateCallback<T>() {
                public T doInHibernate(Session session) {
                    Query query = (Query) session.getNamedQuery(nameQuery);
                    Set<String> keySet = paramLst.keySet();
                    for (String paramName : keySet) {
                        query.setParameterList(paramName, paramLst.get(paramName));
                    }
                    return (T) query.list();
                }
            });
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public T executeUpdate(final String nameQuery, final Object... values) throws DaoException {
        try {
            return hibernateTemplate.execute(new HibernateCallback<T>() {
                @SuppressWarnings("unchecked")
                public T doInHibernate(Session session) {
                    Query query = session.getNamedQuery(nameQuery);
                    for (int i = 0; values != null && i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                    return (T) Integer.valueOf(query.executeUpdate());
                }
            });
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

}