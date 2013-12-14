package com.firefly.tools;

import java.sql.SQLException;
import java.util.List;

import com.firefly.db.QueryHelper;

import test.usr.UserModel;

public class Test {

	public static void main(String[] args) throws SQLException {
		UserModel um=new UserModel();
		um.setAccount("vicshe");
		
		String sql="";//Class2Sql.createSelectSql("user", "*", um);
		System.out.println(sql);
		
		List<UserModel> list=QueryHelper.query(UserModel.class, sql);
		
		
		for(UserModel u:list){
			System.out.println(u.getPassword());
			
		}
		System.out.println(list.size());
	}
}
