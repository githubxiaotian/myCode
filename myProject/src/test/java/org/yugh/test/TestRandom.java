package org.yugh.test;

import java.util.List;
import java.util.Map;

import org.yugh.test.mvc.common.Running;
import org.yugh.test.mvc.dao.BaseDaoImpl;

public class TestRandom {

	public BaseDaoImpl<Object> impl;

	public BaseDaoImpl<Object> getImpl() {
		return impl;
	}

	public void setImpl(BaseDaoImpl<Object> impl) {
		this.impl = impl;
	}
	

	public static void main(String[] args) {
		try {
			String sql = "select a.task_sql,a.THREAD_NAME,a.class from conf_thread a where a.app_name='VOP'";
			List<Map<String, Object>> list = Running.TEMPLATE_TOOL.getJdbcTemplate().queryForList(sql);
			Map<String, Object> aList = Running.TEMPLATE_PROXY.queryForMap(sql);
			System.out.println(aList);
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
