package com.firefly.bo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.notes.NotesModel;

import com.firefly.db.QueryHelper;
import com.firefly.model.Model;
import com.firefly.oid.Oid;
import com.firefly.tools.Class2Sql;
import com.firefly.utils.log.Log;
import com.firefly.utils.log.LogFactory;

public abstract class Bo<M extends Model> implements Bi<M> {
	private static Log log = LogFactory.getInstance().getLog("firefly-system");
	
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	private String tablename;
	private final Class<M> mClass;
	
	protected Bo(){
		Type genType=this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		mClass=(Class<M>) params[0];
		try {
			M m=mClass.newInstance();
			String mname=m.getClass().getSimpleName();
			mname=mname.substring(0,mname.lastIndexOf("Model"));
			setTablename(mname.toLowerCase());
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String addModel(M m) {
		
		String id=Oid.getOid();
		m.setId(id);
		
		String sql=Class2Sql.createInsertSql(tablename, m);
		log.info(sql);
		
		int res=-1;
		try {
			res=QueryHelper.update(sql);
		} catch (SQLException e) {
			log.info(e.getMessage());
		} finally{
			QueryHelper.commit();
		}
		
		return id;
	}
	
	@Override
	public int addModel(List<M> list) {
		int res=-1;
		if(list!=null&&list.size()>0){
			for(M m:list){
				String id=Oid.getOid();
				m.setId(id);
				
				String sql=Class2Sql.createInsertSql(tablename, m);
				log.info(sql);
				
				
				try {
					res=QueryHelper.update(sql);
				} catch (SQLException e) {
					log.info(e.getMessage());
				} finally{
					QueryHelper.commit();
				}
			}
		}
		
		return res;
	}

	@Override
	public int updateModel(M m, M qm) {
		String sql=Class2Sql.createUpdateSql(tablename, m, qm);
		log.info(sql);
		
		Object[] params=new Object[]{};
		int res=-1;
		
		try {
			res=QueryHelper.update(sql, params);
			
		} catch (SQLException e) {
			log.info(e.getMessage());
		} finally{
			QueryHelper.commit();
		}
		
		return res;
	}

	@Override
	public List<M> listModel(String fields, M qm, Map<String, String> eqs,
			String querysql, Object[] params, Map<String, String> orders,
			Integer page, Integer nums) throws SQLException, InstantiationException, IllegalAccessException {
		
		List<M> ulist=new ArrayList<M>();
		
		String sql=Class2Sql.createSelectSql(tablename, fields, qm, eqs, querysql, params,orders,page,nums);
		log.info(sql);
		
		//Object[] params=new Object[]{};
		
		ulist=(List<M>) QueryHelper.query(mClass.newInstance().getClass(), sql);
		
		return ulist;
	}

	@Override
	public M singleModel(M m) throws SQLException, InstantiationException, IllegalAccessException {
		M model=mClass.newInstance();
		//model.setId(id);
		
		String sql=Class2Sql.createSelectSql(tablename, "*", m, null, null, null,null,null,null);
		log.info(sql);
		
		model=(M) QueryHelper.read(mClass.newInstance().getClass(), sql);
		
		return model;
	}
	
	@Override
	public List<M> customListModel(String[] sqls) throws SQLException, InstantiationException, IllegalAccessException {
		List<M> ulist=new ArrayList<M>();
		
		if(sqls!=null&&sqls.length>0){
			for(String sql:sqls){
				log.info(sql);
				
				List<M> l=(List<M>) QueryHelper.query(mClass.newInstance().getClass(), sql);
				ulist.addAll(l);
			}
		}
		
		
		return ulist;
	}

}
