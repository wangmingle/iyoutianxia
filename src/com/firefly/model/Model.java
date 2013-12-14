package com.firefly.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Model implements Mi{

	/**
	 * 递归自己父的所有Field
	 * @param list
	 */
	private void collectFields(Class<?> clazz,List<Field> list){
		
		for (Field field:clazz.getDeclaredFields()){
			list.add(field);
		}
		if (clazz.getSuperclass()!=Object.class){
			this.collectFields(clazz.getSuperclass(), list);
		}
		
	}
	

	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		List<Field> list = new ArrayList<Field>();
		this.collectFields(this.getClass(), list);
		
		for (Field field:list){
			final String name = field.getName();
			field.setAccessible(true);
			try {
				final Object value = field.get(this);
				buffer.append(","+name+"="+value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return this.getClass().getSimpleName()+":"+buffer.toString().substring(1);
		
	}

}
