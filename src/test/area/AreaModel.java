package test.area;

import com.firefly.model.Model;

public class AreaModel extends Model {

	private Integer rownum;
	private String id;
	private String name;
	private String ename;
	private String pid;
	private Integer ssort;
	private Integer flag;
	
	private String pnameStr;

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
}
