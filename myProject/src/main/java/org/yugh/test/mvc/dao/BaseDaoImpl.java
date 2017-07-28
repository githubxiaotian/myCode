package org.yugh.test.mvc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	private Class<T> clazz;

	/**
	 * 通过构造方法指定DAO的具体实现类
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		System.out.println("Dao实现类是：" + clazz.getName());
	}

	// Hiberbate持久层
	/**
	 * 向DAO层注入SessionFactory
	 */
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 获取当前工作的Session
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		getSession().save(entity);
		getSession().flush();
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Serializable id) {
		getSession().delete(this.findById(id));
	}

	public T findById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQLforup(String hql, Object parm1, Object parm2) {
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, parm1);
		query.setParameter(1, parm2);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQLforup(String hql, String parm1) {
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, parm1);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryForHql(String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query query = this.getSession().createQuery("from " + clazz.getSimpleName());
		return query.list();
	}

	public Object findBySql(String sql, String pam1) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, pam1);
		Object obj = query.uniqueResult();
		return obj;
	}

	@SuppressWarnings("unchecked")
	public T findByOthers(String hql, String pam1) {
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, pam1);
		return (T) query.uniqueResult();
	}

	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	//SQL语句
	@SuppressWarnings("unchecked")
	public List<T> createSQLQuery(String sql, List<Object> params) {
		Query query = getSession().createSQLQuery(sql);
		if(null != params && params.size() > 0){
			for (int i = 1; i < params.size(); i++) {
				query.setParameter(i, i-1);
			}
		}
		return (List<T>)query.list();
	}


	
	@SuppressWarnings("unchecked")
	public List<T> findByNativeSQL(String sql, List<Object> params) {
		Query query = getSession().createSQLQuery(sql);
		if(null != params && params.size() > 0){
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, i-1);
			}
		}
		return (List<T>)query.list();
	}

	
	
	
	
	
	
	
	

}
