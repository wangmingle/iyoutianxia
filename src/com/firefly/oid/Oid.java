package com.firefly.oid;

public class Oid {
	 private static java.security.SecureRandom sr = new java.security.SecureRandom();
	 private static String HDSeries = null;
	
	public Oid() {
		super();
	}
	
	public static String getOid() {
		String sOid = System.currentTimeMillis() +""+sr.nextInt()+sr.nextInt();
		if(sOid.length()<32){
		for(int i=sOid.length()+1;i<=32;i++)
		sOid+="0";
		}else if(sOid.length()>32)
		sOid=sOid.substring(0,32);
		
		sOid=sOid.replace('-','0');
		
		return sOid;
	}
	
	public static void main(String[] args) {
		String sd[] = new String[10];
		for (int i = 0; i < 10; i++) {
			sd[i] = getOid();
		}
		String temp = sd[0];
		for(int i=1;i<10;i++){
			if(temp.equals(sd[i])){
			 break;
			}
		}
	}
}
