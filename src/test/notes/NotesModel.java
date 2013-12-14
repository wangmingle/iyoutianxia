package test.notes;

public class NotesModel {

	private Integer rownum;
	
	
	private String id;
	private String name;
	private String context;
	private String uid;
	private String tags;
	private Long createdate;
	private Integer stype;
	private Integer status;
	
	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public NotesModel(){}
	
	
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}


	public Integer getRownum() {
		return rownum;
	}


	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public Long getCreatedate() {
		return createdate;
	}


	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}


	public Integer getStype() {
		return stype;
	}


	public void setStype(Integer stype) {
		this.stype = stype;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
