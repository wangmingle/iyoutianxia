package test.notes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.usr.UserModel;

import com.firefly.db.QueryHelper;
import com.firefly.oid.Oid;
import com.firefly.tools.Class2Sql;
import com.firefly.utils.StringUtils;
import com.firefly.utils.log.Log;
import com.firefly.utils.log.LogFactory;

public class NotesBo {
	private static Log log = LogFactory.getInstance().getLog("firefly-system");

	public static String addNotes(NotesModel notes){
		String id=Oid.getOid();
		notes.setId(id);
		
		String context=notes.getContext();
		context=context.replace("\"", "&quot;").replace("'", "&#039;");
		
		notes.setContext(context);
		
		//System.out.print(context);
		
		String sql=Class2Sql.createInsertSql("notes", notes);
		log.info(sql);
		
		Object[] params=new Object[]{};
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
	/*public static String replace(String text, String repl, String with) {
        //return StringUtils;
    }

	public static String htmlEncode(String sSource) {
		  String sTemp = sSource;
		  sTemp = replace(sTemp, "&", "&amp;");
		  sTemp = replace(sTemp, "\"", "&quot;");
		  sTemp = replace(sTemp, "'", "&#039;");
		  sTemp = replace(sTemp, "<", "&lt;");
		  sTemp = replace(sTemp, ">", "&gt;");
		  return sTemp;
		 }*/

	
	public static int updateNotes(NotesModel notes,NotesModel notequery){
		
		String sql=Class2Sql.createUpdateSql("notes", notes, notequery);
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
	
	
	public static List<NotesModel> notesList(String fields,NotesModel queryObj,Map<String, String> eqs,String querysql,Object[] params,Map<String, String> orders,Integer page,Integer nums) throws SQLException{
		List<NotesModel> ulist=new ArrayList<NotesModel>();
		
		String sql=Class2Sql.createSelectSql("notes", fields, queryObj, eqs, querysql, params,orders,page,nums);
		log.info(sql);
		
		//Object[] params=new Object[]{};
		
		ulist=(List<NotesModel>) QueryHelper.query(NotesModel.class, sql);
		
		return ulist;
	}
	
	public static NotesModel singleModel(NotesModel um) throws SQLException{
		NotesModel model=new NotesModel();
		//model.setId(id);
		
		String sql=Class2Sql.createSelectSql("notes", "*", um, null, null, null,null,null,null);
		log.info(sql);
		
		model=QueryHelper.read(NotesModel.class, sql);
		
		return model;
	}
}
