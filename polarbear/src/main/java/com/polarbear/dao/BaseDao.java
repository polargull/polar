package com.polarbear.dao;

import static com.polarbear.util.Constants.ResultState.DB_DATA_NOT_UNIQUE_ERR;
import static com.polarbear.util.Constants.ResultState.DB_ERR;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.polarbear.service.PageList;

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
        } catch (IllegalArgumentException e) {
            throw new DaoException(DB_ERR);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public T findByIdLock(Class clazz, long id) throws DaoException {
        try {
            return (T) hibernateTemplate.get(clazz, id, LockMode.UPGRADE_NOWAIT);
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
        StringBuilder hqlSb = new StringBuilder("from ").append(clazz.getSimpleName());
        try {
            return hibernateTemplate.find(hqlSb.toString());
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public PageList<T> findPageListByDynamicCondition(final Class clazz, final int pageNo, final int pageSize, final String hqlCondition) throws DaoException {
        try {
            List<T> list = hibernateTemplate.execute(new HibernateCallback<List<T>>() {
                public List<T> doInHibernate(Session session) {
                    Query query = (Query) session.createQuery(generateHql(clazz, hqlCondition));
                    query.setFirstResult((pageNo - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    return query.list();
                }
            });
            long count = countByDynamicCondition(clazz, hqlCondition);
            return new PageList<T>(count, pageNo, pageSize, list);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    private String generateHql(Class clazz, String hqlCondition) {
        StringBuilder hqlSb = new StringBuilder("from ").append(clazz.getSimpleName()).append(" where 1=1");
        if (StringUtils.isEmpty(hqlCondition)) {
            return hqlSb.toString();
        }
        return hqlSb.append(" and ").append(hqlCondition).append(" order by id desc").toString();
    }

    @SuppressWarnings("unchecked")
    public long countByDynamicCondition(final Class clazz, final String hqlCondition) throws DaoException {
        try {
            return (Long) hibernateTemplate.execute(new HibernateCallback<Long>() {
                public Long doInHibernate(Session session) {
                    Query query = (Query) session.createQuery("select count(*) " + generateHql(clazz, hqlCondition));
                    return (Long) query.uniqueResult();
                }
            });
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public T findByNamedQueryObject(String nameQuery, Object... values) throws DaoException {
        List<T> list = findByNamedQuery(nameQuery, values);
        int size = list.size();
        if (size == 0)
            return null;
        if (size > 1)
            throw new DaoException(DB_DATA_NOT_UNIQUE_ERR);
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

    @SuppressWarnings("unchecked")
    public PageList<T> findByNamedQueryByPage(final String nameQuery, final int pageNo, final int pageSize, final Object... values) throws DaoException {
        try {
            List list = hibernateTemplate.executeFind(new HibernateCallback<List>() {
                public List doInHibernate(Session session) {
                    Query query = (Query) session.getNamedQuery(nameQuery);
                    query.setFirstResult((pageNo - 1) * pageSize);
                    query.setMaxResults(pageSize);
                    for (int i = 0; values != null && i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                    return query.list();
                }
            });
            long count = countByNamedQuery(nameQuery, values);
            return new PageList<T>(count, pageNo, pageSize, list);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public long countByNamedQuery(final String nameQuery, final Object... values) throws DaoException {
        try {
            return (Long) hibernateTemplate.execute(new HibernateCallback<Long>() {
                public Long doInHibernate(Session session) {
                    Query queryName = (Query) session.getNamedQuery(nameQuery);
                    Query query = session.createQuery("select count(*) " + queryName.getQueryString());
                    for (int i = 0; values != null && i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                    return (Long) query.uniqueResult();
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