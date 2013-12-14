package test.usr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firefly.db.QueryHelper;
import com.firefly.oid.Oid;
import com.firefly.tools.Class2Sql;
import com.firefly.utils.log.Log;
import com.firefly.utils.log.LogFactory;

public class UserBo {
	private static Log log = LogFactory.getInstance().getLog("firefly-system");

	public static String addUser(UserModel user){
		String id=Oid.getOid();
		user.setId(id);
		
		String sql=Class2Sql.createInsertSql("user", user);
		log.info(sql);
		
		Object[] params=new Object[]{};
		int res=-1;
		
		try {
			res=QueryHelper.update(sql, params);
			QueryHelper.commit();
		} catch (SQLException e) {
			log.info(e.getMessage());
		} finally{
			QueryHelper.closeConnection();
		}
		
		return id;
	}
	
	public static int updateUser(UserModel user,UserModel queryuser){
		
		String sql=Class2Sql.createUpdateSql("user", user, queryuser);
		log.info(sql);
		
		Object[] params=new Object[]{};
		int res=-1;
		
		try {
			res=QueryHelper.update(sql, params);
			QueryHelper.commit();
		} catch (SQLException e) {
			log.info(e.getMessage());
		} finally{
			QueryHelper.closeConnection();
		}
		
		return res;
	}
	
	
	public static List<UserModel> userList(String fields,UserModel queryObj,Map<String, String> eqs,String querysql,Object[] params,Map<String, String> orders,Integer page,Integer nums) throws SQLException{
		List<UserModel> ulist=new ArrayList<UserModel>();
		
		String sql=Class2Sql.createSelectSql("user", fields, queryObj, eqs, querysql, params,orders,page,nums);
		log.info(sql);
		
		//Object[] params=new Object[]{};
		
		ulist=(List<UserModel>) QueryHelper.query(UserModel.class, sql);
		QueryHelper.closeConnection();
		return ulist;
	}
	
	public static UserModel singleModel(UserModel um) throws SQLException{
		UserModel model=new UserModel();
		//model.setId(id);
		
		String sql=Class2Sql.createSelectSql("user", "*", um, null, null, null,null,null,null);
		log.info(sql);
		
		model=QueryHelper.read(UserModel.class, sql);
		QueryHelper.closeConnection();
		return model;
	}
}
