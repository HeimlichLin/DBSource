package idv.heimlich.dbSource.domain.bean;

public class UserSourceDo {
	
	public enum COLUMNS {
		NAME("名稱"), // 程式名稱
		TYPE("類別"), // 程式類型：PROCEDURE, TRIGGER, FUNCTION
		LINE("行數"), // 程式行數
		TEXT("內容") // 程式碼
		;
		
		final String name;
		
		private COLUMNS(final String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private String name;	
	private String type;	
	private String line;	
	private String text;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
