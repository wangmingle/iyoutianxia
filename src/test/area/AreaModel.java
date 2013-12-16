package test.area;

import com.firefly.model.Model;

public class AreaModel extends Model {

	private Integer rownum;
	private String id;
	private String name;
	private String fullname;
	private String ename;
	private String pyname;
	private String shortname;
	private String stype;
	private String pid;
	private String remark;
	private Float lat;
	private Float lng;
	private String addr;
	private String author;
	private Integer status;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer ssort;
	private Integer flag;
	
	private String pnameStr;
	private String penameStr;
	private String stypeStr;
	private String stypeeStr;

	public String getStypeeStr() {
		return stypeeStr;
	}

	public void setStypeeStr(String stypeeStr) {
		this.stypeeStr = stypeeStr;
	}

	public String getPenameStr() {
		return penameStr;
	}

	public void setPenameStr(String penameStr) {
		this.penameStr = penameStr;
	}

	public String getStypeStr() {
		return stypeStr;
	}

	public void setStypeStr(String stypeStr) {
		this.stypeStr = stypeStr;
	}

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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getPnameStr() {
		return pnameStr;
	}

	public void setPnameStr(String pnameStr) {
		this.pnameStr = pnameStr;
	}
	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
