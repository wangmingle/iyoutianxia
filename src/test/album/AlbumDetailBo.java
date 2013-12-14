package test.album;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firefly.db.QueryHelper;
import com.firefly.oid.Oid;
import com.firefly.tools.Class2Sql;
import com.firefly.utils.log.Log;
import com.firefly.utils.log.LogFactory;

public class AlbumDetailBo {
	private static Log log = LogFactory.getInstance().getLog("firefly-system");

	/*public static String addUser(UserModel user){
		String id=Oid.getOid();
		user.setId(id);
		
		String sql=Class2Sql.createInsertSql("user", user);
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
		
		return id;
	}
	
	public static int updateUser(UserModel user,UserModel queryuser){
		
		String sql=Class2Sql.createUpdateSql("user", user, queryuser);
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
	}*/
	
	
	public static List<AlbumDetailModel> albumList(String fields,AlbumDetailModel queryObj,Map<String, String> eqs,String querysql,Object[] params,Map<String, String> orders,Integer page,Integer nums) throws SQLException{
		List<AlbumDetailModel> ulist=new ArrayList<AlbumDetailModel>();
		
		String sql=Class2Sql.createSelectSql("albumdetail", fields, queryObj, eqs, querysql, params,orders,page,nums);
		log.info(sql);
		
		//Object[] params=new Object[]{};
		
		ulist=(List<AlbumDetailModel>) QueryHelper.query(AlbumDetailModel.class, sql);
		
		return ulist;
	}
	
	public static AlbumDetailModel singleModel(AlbumDetailModel um) throws SQLException{
		AlbumDetailModel model=new AlbumDetailModel();
		//model.setId(id);
		
		String sql=Class2Sql.createSelectSql("albumdetail", "*", um, null, null, null,null,null,null);
		log.info(sql);
		
		model=QueryHelper.read(AlbumDetailModel.class, sql);
		
		return model;
	}
}
