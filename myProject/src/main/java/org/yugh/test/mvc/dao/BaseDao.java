package org.yugh.test.mvc.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseDao<T> {
	
	public void save(T entity);  
	  
    public void update(T entity);  
  
    public void delete(Serializable id);  
  
    public T findById(Serializable id);  
    
    public List<T> findAll();
    
    public List<T> findByHQL(String hql, Object... params);

	public List<T> findByHQLforup(String hql, Object pam1, Object pam2); 

	public List<T> findByHQLforup(String hql, String pam1);  
	
	public Object findBySql(String sql ,String pam1);
	
	public T findByOthers(String hql ,String pam1);

	List<T> queryForHql(String hql);

	public  int executeSql(String sql, Map<String, Object> params);
	
	//SQL
	public List<T> createSQLQuery(String sql, List<Object> values);
	
	public abstract List<T> findByNativeSQL(String sql, List<Object> params);
	
}
