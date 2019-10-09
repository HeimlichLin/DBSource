package idv.heimlich.dbSource;

import org.slf4j.Logger;

import idv.heimlich.dbSource.common.code.SourceType;
import idv.heimlich.dbSource.common.log.LogFactory;
import idv.heimlich.dbSource.domain.service.IDBSourceService;
import idv.heimlich.dbSource.domain.service.impl.DBSourceServiceImpl;

public class DBSourceController {

	private static Logger LOGGER = LogFactory.getInstance();
	private IDBSourceService service;

	public DBSourceController() {
		if (this.service == null) {
			this.service = new DBSourceServiceImpl();
		}
	}
	
	public void execute(SourceType sourceType) {
		switch (sourceType) {
		case ALL:
			this.service.creatAllSource();
			break;
		default:
			this.service.creatSourceByType(sourceType);
			break;
		}		
	}

	public void executeByTypeAndName(SourceType sourceType, String name) {
		this.service.creatSourceByTypeAndName(sourceType, name);
	}
}
