package idv.heimlich.dbSource.domain.dto;

import idv.heimlich.dbSource.domain.bean.UserSourceDo;

import java.util.List;

public class UserSourceDTO {
	
	private String name;
	private String type;
	private List<UserSourceDo> content;

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

	public List<UserSourceDo> getContent() {
		return content;
	}

	public void setContent(List<UserSourceDo> content) {
		this.content = content;
	}

}
