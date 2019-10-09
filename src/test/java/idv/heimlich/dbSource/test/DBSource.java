package idv.heimlich.dbSource.test;

import idv.heimlich.dbSource.DBSourceController;
import idv.heimlich.dbSource.common.code.SourceType;
import idv.heimlich.dbSource.common.log.LogFactory;

import org.slf4j.Logger;


public class DBSource {
	
	private static Logger LOGGER = LogFactory.getInstance();
	
	public static void main(String[] args) {
		DBSourceController controller = new DBSourceController();
		controller.execute(SourceType.ALL);
//		controller.executeByTypeAndName(SourceType.TRIGGER, "TGA_MODLOG_ALL");
	}

}
