package test.album;

public class AlbumModel {

	private String id;
	private String uid;
	private String name;
	private String frontcover;
	private Long createdate;
	
	public String getFrontcover() {
		return frontcover;
	}
	public void setFrontcover(String frontcover) {
		this.frontcover = frontcover;
	}
	public Long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
