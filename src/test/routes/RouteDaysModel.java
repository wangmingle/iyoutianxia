package test.routes;

import com.firefly.model.Model;

public class RouteDaysModel extends Model {

	private Integer rownum;
	private String id;
	private String routeid;
	private String cityid;
	private String dayflag;
	private Double days;
	private Integer ssort;
	private Integer flag;
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getDayflag() {
		return dayflag;
	}
	public void setDayflag(String dayflag) {
		this.dayflag = dayflag;
	}
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	public Integer getSsort() {
		return ssort;
	}
	public void setSsort(Integer ssort) {
		this.ssort = ssort;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	private String citynameStr;
	public String getCitynameStr() {
		return citynameStr;
	}
	public void setCitynameStr(String citynameStr) {
		this.citynameStr = citynameStr;
	}
	
}
