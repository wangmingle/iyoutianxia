  package test.routes;

import com.firefly.model.Model;
import com.firefly.tools.DateUtil;

public class RouteModel extends Model {

	private Integer rownum;
	
	private String id;
	private String name;
	private String tags;
	private String author;
	private Long startdate;
	private Integer days;
	private Long enddate;
	private String startcity;
	private String endcity;
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getStartcity() {
		return startcity;
	}
	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}
	public String getEndcity() {
		return endcity;
	}
	public void setEndcity(String endcity) {
		this.endcity = endcity;
	}
	private Integer status;
	private String pics;
	private String detail;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Long getStartdate() {
		return startdate;
	}
	public void setStartdate(Long startdate) {
		this.startdate = startdate;
	}
	public Long getEnddate() {
		return enddate;
	}
	public void setEnddate(Long enddate) {
		this.enddate = enddate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	public String getStartdateStr() {
		return DateUtil.long2String(getStartdate());
	}
	public void setStartdateStr(String startdateStr) {
		setStartdate(DateUtil.string2LongBegin(startdateStr));
	}
	private String startdateStr;
	private String startcityStr;
	private String endcityStr;
	public String getStartcityStr() {
		return startcityStr;
	}
	public void setStartcityStr(String startcityStr) {
		this.startcityStr = startcityStr;
	}
	public String getEndcityStr() {
		return endcityStr;
	}
	public void setEndcityStr(String endcityStr) {
		this.endcityStr = endcityStr;
	}
	
	
}
