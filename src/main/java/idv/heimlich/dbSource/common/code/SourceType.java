package idv.heimlich.dbSource.common.code;

import org.apache.commons.lang.StringUtils;


public enum SourceType {
	
	ALL("PROCEDURE, TRIGGER, FUNCTION") {
		@Override
		public String getSourceType() {			
			return null;
		}
	}, // 
	PROCEDURE("Stored Procedure(預儲程序)") {
		@Override
		public String getSourceType() {			
			return PROCEDURE.getName();
		}
	}, //
	TRIGGER("Trigger (觸發程序)") {
		@Override
		public String getSourceType() {			
			return TRIGGER.getName();
		}
	}, //
	FUNCTION("Stored Function(預儲函數)") {
		@Override
		public String getSourceType() {			
			return FUNCTION.getName();
		}
	},
//	VIEW("檢視表")	
	;
	
	final String name;
	final String description;

	private SourceType(String description) {
		this.name = name();
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String toText() {
		return description;
	}

	public abstract String getSourceType();
	
	public String toSql() {
		return String.format("Select distinct name FROM USER_SOURCE %s", this.getSourceTypeSql());
	};
	
	private String getSourceTypeSql() {
		if (StringUtils.isBlank(this.getSourceType())) {
			return "";
		}
		return String.format(" where type = '%s' ", this.getSourceType());
	}

}
