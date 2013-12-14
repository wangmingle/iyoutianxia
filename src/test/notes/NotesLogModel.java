package test.notes;

public class NotesLogModel {

	private String id;
	private String notes_id;
	private String title;
	private String context;
	private String pid;
	private String author;
	private Long sdate;
	
	public NotesLogModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotes_id() {
		return notes_id;
	}

	public void setNotes_id(String notes_id) {
		this.notes_id = notes_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getSdate() {
		return sdate;
	}

	public void setSdate(Long sdate) {
		this.sdate = sdate;
	}
	
	
}
