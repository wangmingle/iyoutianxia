package com.firefly.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import test.usr.UserModel;

import com.firefly.oid.Oid;

public class Class2Sql {

	/**
	 * 创建Insert 语句
	 * @param table 表名
	 * @param obj 对象 对象中的属性不为空的才会有数据插入
	 * @return
	 */
	public static String createInsertSql(String table,Object obj){
		StringBuffer sql=new StringBuffer();
		sql.append("insert into ");
		sql.append(table);
		
		String fieldsql=" (id";
		//String valuesql=" values ('"+Oid.getOid()+"'";
		String valuesql=" values (";
		
		Class clazz = obj.getClass();
		Field fields[] = clazz.getDeclaredFields();
		
		int flag=-1;
		
		try {
			Field f= clazz.getDeclaredField("id");
			f.setAccessible(true);
			valuesql+=("'"+String.valueOf(f.get(obj))+"'");
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0;i<fields.length;i++){
			Field fd=fields[i];
			fd.setAccessible(true);
			
			String filedName = fd.getName(); 
			if(filedName.lastIndexOf("Str")!=-1){
				continue;
			}
			String firstLetter = filedName.substring(0,1).toUpperCase(); 
			String getMethodName = "get"+firstLetter+filedName.substring(1);
			
			try {
				Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(obj, new Object[] {});
				
				if(!"id".equals(filedName)&& !"rownum".equals(filedName)){
					//判断值不为空
					if(value!=null){
						
						
						if(!"id".equals(filedName) ){
							fieldsql+=(","+filedName);
							
							if (fd.getType().getName().equals(java.lang.String.class.getName())){
								valuesql+=(",'"+value+"'");
							}else{
								valuesql+=(","+value);
							}
						}
						
						//if (fd.getType().getName().equals(java.util.Date.class.getName())){
							//valuesql+=(",'"+value+"'");
						//}
						
						flag+=1;
					}else{
						fieldsql+=(","+filedName);
						
						valuesql+=(",null");
						
						//if (fd.getType().getName().equals(java.util.Date.class.getName())){
							//valuesql+=(",'"+value+"'");
						//}
					}
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(flag!=-1){
			sql.append(fieldsql+")");
			sql.append(valuesql+")");
			
			return sql.toString();
		}else{
			return "对象为空，不允许添加";
		}
	}
	
	/**
	 * 创建update语句
	 * @param table 需要修改的表的表名
	 * @param obj 需要修改的值 传入修改对象 属性不为空的才会修改
	 * @param queryObj 修改的条件 传入修改对象 属性不为空的才会作为修改条件
	 * @return
	 */
	public static String createUpdateSql(String table,Object obj,Object queryObj){
		StringBuffer sql=new StringBuffer();
		sql.append("update ");
		sql.append(table+" set");
		
		String fieldsql=" ";
		String wheresql=" where 1=1";
		
		int setFlag=-1;
		int whereFlag=-1;
		
		Class setClazz = obj.getClass();
		Field setFields[] = setClazz.getDeclaredFields();
		
		for(int i=0;i<setFields.length;i++){
			Field fd=setFields[i];
			fd.setAccessible(true);
			
			String filedName = fd.getName(); 
			if(filedName.lastIndexOf("Str")!=-1){
				continue;
			}
			String firstLetter = filedName.substring(0,1).toUpperCase(); 
			String getMethodName = "get"+firstLetter+filedName.substring(1);
			
			try {
				Method getMethod = setClazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(obj, new Object[] {});
				
				if(!"id".equals(filedName) && !"rownum".equals(filedName)){
					//判断值不为空
					if(value!=null){
						if (fd.getType().getName().equals(java.lang.String.class.getName())){
							fieldsql+=(filedName+"='"+value+"',");
						}else{
							fieldsql+=(filedName+"="+value+",");
						}
						
						//if (fd.getType().getName().equals(java.util.Date.class.getName())){
							//valuesql+=(",'"+value+"'");
						//}
						
						setFlag+=1;
					}
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Class whereClazz = queryObj.getClass();
		Field whereFields[] = whereClazz.getDeclaredFields();
		
		for(int i=0;i<whereFields.length;i++){
			Field fd=whereFields[i];
			fd.setAccessible(true);
			
			String filedName = fd.getName(); 
			if(filedName.lastIndexOf("Str")!=-1){
				continue;
			}
			String firstLetter = filedName.substring(0,1).toUpperCase(); 
			String getMethodName = "get"+firstLetter+filedName.substring(1);
			
			try {
				Method getMethod = whereClazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(queryObj, new Object[] {});
				
				//判断值不为空
				if(value!=null){
					if (fd.getType().getName().equals(java.lang.String.class.getName())){
						wheresql+=(" and "+filedName+"='"+value+"'");
					}else{
						wheresql+=(" and "+filedName+"="+value);
					}
					
					whereFlag+=1;
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(setFlag!=-1){
			sql.append(fieldsql.substring(0,fieldsql.length()-1));
			sql.append(wheresql);
			
			return sql.toString();
		}else{
			return "对象为空，不允许修改";
		}
	}
	
	/**
	 * 创建delete语句
	 * @param table 表名
	 * @param queryObj 需要删除的条件  传入对象 属性不为空的才会作为删除的条件
	 * @return
	 */
	public static String createDeleteSql(String table,Object queryObj){
		StringBuffer sql=new StringBuffer();
		sql.append("delete from ");
		sql.append(table+" where 1=1");
		
		String wheresql=" ";
		
		int whereFlag=-1;
		
		Class whereClazz = queryObj.getClass();
		Field whereFields[] = whereClazz.getDeclaredFields();
		
		for(int i=0;i<whereFields.length;i++){
			Field fd=whereFields[i];
			fd.setAccessible(true);
			
			String filedName = fd.getName(); 
			if(filedName.lastIndexOf("Str")!=-1){
				continue;
			}
			String firstLetter = filedName.substring(0,1).toUpperCase(); 
			String getMethodName = "get"+firstLetter+filedName.substring(1);
			
			try {
				Method getMethod = whereClazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(queryObj, new Object[] {});
				
				//判断值不为空
				if(value!=null){
					if (fd.getType().getName().equals(java.lang.String.class.getName())){
						wheresql+=(" and "+filedName+"='"+value+"'");
					}else{
						wheresql+=(" and "+filedName+"="+value);
					}
					
					whereFlag+=1;
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		sql.append(wheresql);
			
		return sql.toString();
	}
	
	/**
	 * 创建select语句
	 * @param table 查询的表名
	 * @param fields 查询的字段 字符串
	 * @param queryObj 查询对象 属性不为空的才会作为查询条件
	 * @param eqs 查询条件的表达式  修改查询对象中有的属性 默认“=” 可修改为“like、>=、<=等” 可以null
	 * @param querysql 自定义查询条件 字符串 值可以使用 /? 替代，也可以赋值
	 * @param params 自定义查询条件赋值 一定要与 querysql 中的 /? 个数匹配
	 * @return
	 */
	public static String createSelectSql(String table,String fields,Object queryObj,Map<String, String> eqs,String querysql,Object[] params,Map<String, String> orders,Integer page,Integer nums ){
		StringBuffer sql=new StringBuffer();
		sql.append("select ((@x:=ifnull(@x,0)+1)-1)%5 as rownum");
		//sql.append(table);
		
		String fieldsql=" ";
		String wheresql=" where 1=1";
		
		int setFlag=-1;
		int whereFlag=-1;
		
		
		Class whereClazz = queryObj.getClass();
		Field whereFields[] = whereClazz.getDeclaredFields();
		
		for(int i=0;i<whereFields.length;i++){
			Field fd=whereFields[i];
			fd.setAccessible(true);
			
			String filedName = fd.getName(); 
			if(filedName.lastIndexOf("Str")!=-1){
				continue;
			}
			String firstLetter = filedName.substring(0,1).toUpperCase(); 
			String getMethodName = "get"+firstLetter+filedName.substring(1);
			
			try {
				Method getMethod = whereClazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(queryObj, new Object[] {});
				
				//判断值不为空
				if(value!=null){
					if (fd.getType().getName().equals(java.lang.String.class.getName())){
						if(eqs!=null){
							if(eqs.containsKey(filedName)){
								wheresql+=(" and "+filedName+" "+eqs.get(filedName)+" '%"+value+"%'");
							}else{
								wheresql+=(" and "+filedName+"='"+value+"'");
							}
							
						}else{
							wheresql+=(" and "+filedName+"='"+value+"'");
						}
						
					}else{
						if(eqs!=null){
							if(eqs.containsKey(filedName)){
								wheresql+=(" and "+filedName+" "+eqs.get(filedName)+" "+value);
							}else{
								wheresql+=(" and "+filedName+"="+value);
							}
							
						}else{
							wheresql+=(" and "+filedName+"="+value);
						}
						
					}
					whereFlag+=1;
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(setFlag==-1){
			if(fields!=null && "*".equals(fields)){
				for(int i=0;i<whereFields.length;i++){
					Field fd=whereFields[i];
					fd.setAccessible(true);
					
					String filedName = fd.getName(); 
					if(filedName.lastIndexOf("Str")!=-1){
						continue;
					}
					if(!"rownum".equals(filedName)){
						sql.append(","+filedName);
					}
				}
			}else{
				sql.append(" ,"+fields);
			}
			sql.append(" from "+table);
			sql.append(wheresql);
			
			if(querysql!=null && !"".equals(querysql)){
				String s="";
				if(querysql.indexOf("/")!=-1){
					
					String[] sqls=querysql.split("/");
					if(params==null || params.length!=sqls.length-1){
						return "参数个数不匹配";
					}
					if(sqls!=null&&sqls.length>0){
						int i=0;
						for(int j=0;j<sqls.length;j++){
							if(sqls[j]!=null && sqls[j].indexOf("?")!=-1){
								//System.out.println(sqls[j].replace("?", params[j].toString()));
								s+=sqls[j].replace("?", String.valueOf(params[i]==null?"":params[i]));
								i+=1;
							}else{
								s+=sqls[j];
							}
							
						}
					}
					sql.append(s);
				}else{
					sql.append(querysql);
				}
			}
			if(orders!=null&&orders.size()>0){
				int i=0;
				for(String key:orders.keySet()){
					if(i==0){
						sql.append(" order by "+key+" "+orders.get(key));
						i++;
					}else{
						sql.append(" "+key+" "+orders.get(key));
					}
				}
			}
			
			if(page!=null && page!=-1 && nums!=null){
				sql.append(" limit "+((page-1)*nums)+","+nums);
			}
			
			return sql.toString();
		}else{
			return "对象为空，不允许修改";
		}
	}
	
	
	public static void main(String[] args) {
		UserModel um=new UserModel();
		um.setAccount("vicshe");
		um.setBirthday((new Date()).getTime());
		um.setPassword("dfdfdf");
		um.setGender(1);
		
		UserModel queryum=new UserModel();
		//queryum.setId("dddddddddddddd");
		//queryum.setAccount("vic");
		//queryum.setGender(2);
		//queryum.setBirthday((new Date()).getTime());
		
		Map<String, String> map=new HashMap<String, String>();
		//map.put("account", "like");
		map.put("gender", ">=");
		
		String wheresql=null;//" and birthday <= /? and password like '%/?%'";
		
		Object[] objs=new Object[]{"dfdfd"};//new Object[]{(new Date()).getTime(),"abc"};
		
		System.out.println(createInsertSql("user",um));
		System.out.println(createUpdateSql("user", um, queryum));
		//System.out.println(createDeleteSql("user", queryum));
		
		Map<String, String> orders=new HashMap<String, String>();
		orders.put("account", "desc");
		//map.put("birthday", ">=");
		
		System.out.println(createSelectSql("user", "id,name", queryum,map," and name='/?'",objs,orders,2,10));
	}
}
