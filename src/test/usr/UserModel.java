package test.usr;

import java.util.Date;

public class UserModel {

	public UserModel(){}
	public UserModel(String account,String password,String name){
		this.name = account;
	}
	private Integer rownum;
	
	private String id;
	private String account;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String photo;
	private Integer gender;
	private Long birthday;
	private Integer guideflag;
	private Integer status;
	private String idnumber;
	private String idpic;
	private String guidenumber;
	private String guidecert;
	private String guidecertpic;
	private String showphoto;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public Integer getGuideflag() {
		return guideflag;
	}
	public void setGuideflag(Integer guideflag) {
		this.guideflag = guideflag;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getIdpic() {
		return idpic;
	}
	public void setIdpic(String idpic) {
		this.idpic = idpic;
	}
	public String getGuidenumber() {
		return guidenumber;
	}
	public void setGuidenumber(String guidenumber) {
		this.guidenumber = guidenumber;
	}
	public String getGuidecert() {
		return guidecert;
	}
	public void setGuidecert(String guidecert) {
		this.guidecert = guidecert;
	}
	public String getGuidecertpic() {
		return guidecertpic;
	}
	public void setGuidecertpic(String guidecertpic) {
		this.guidecertpic = guidecertpic;
	}
	
	////////////////////////////////////////////////////////////////////////
	public String getDateStr(){
		
		return "";
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getShowphoto() {
		return showphoto;
	}
	public void setShowphoto(String showphoto) {
		this.showphoto = showphoto;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
}
