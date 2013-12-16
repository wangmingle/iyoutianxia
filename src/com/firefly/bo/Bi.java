package com.firefly.bo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.firefly.model.Model;

public interface Bi<M extends Model> {

	public String addModel(M m);
	
	public int addModel(List<M> list);
	
	public int updateModel(M m,M qm);
	
	public List<M> listModel(String fields,M qm,Map<String, String> eqs,String querysql,Object[] params,Map<String, String> orders,Integer page,Integer nums) throws SQLException, InstantiationException, IllegalAccessException ;
	
	public M singleModel(M m) throws SQLException, InstantiationException, IllegalAccessException;
	
	public List<M> customListModel(String[] sql) throws SQLException, InstantiationException, IllegalAccessException;
	
}
