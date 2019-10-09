package idv.heimlich.dbSource.domain.service.impl;

import idv.heimlich.dbSource.common.code.SourceType;
import idv.heimlich.dbSource.common.db.IDBSession;
import idv.heimlich.dbSource.common.db.IDBSessionFactory;
import idv.heimlich.dbSource.common.db.impl.DBSessionFactoryImpl;
import idv.heimlich.dbSource.common.file.FileCreater;
import idv.heimlich.dbSource.common.log.LogFactory;
import idv.heimlich.dbSource.domain.bean.UserSourceDo;
import idv.heimlich.dbSource.domain.dao.UserSourceDAO;
import idv.heimlich.dbSource.domain.dto.UserSourceDTO;
import idv.heimlich.dbSource.domain.service.IDBSourceService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class DBSourceServiceImpl implements IDBSourceService {
	
	private static Logger LOGGER = LogFactory.getInstance();
	private IDBSessionFactory sessionFactory = new DBSessionFactoryImpl();	
	
//	private static DBSourceServiceImpl INSTANCE;
//	public static DBSourceServiceImpl getInstance() {
//		if (null == INSTANCE) {
//			INSTANCE = new DBSourceServiceImpl();
//		}
//		return INSTANCE;
//	}

	@Override
	public void creatAllSource() {	
		List<UserSourceDTO> result = new ArrayList<UserSourceDTO>();
		List<UserSourceDo> list = UserSourceDAO.INSTANCE.selectDistinctTypeAndName(this.getDBSession());
		for (UserSourceDo po : list) {
			result.add(this.queryUserSource(po.getType(), po.getName()));
		}
		this.createFile(result);
	}
	
	@Override
	public void creatSourceByType(SourceType sourceType) {
		List<UserSourceDTO> result = new ArrayList<UserSourceDTO>();
		List<String> list = UserSourceDAO.INSTANCE.selectDistinctNameByType(this.getDBSession(), sourceType.getName());
		for (String name : list) {
			result.add(this.queryUserSource(sourceType.name(), name));
		}
		this.createFile(result);
	}
	
	@Override
	public void creatSourceByTypeAndName(SourceType sourceType, String name) {
		List<UserSourceDTO> result = new ArrayList<UserSourceDTO>();
		result.add(this.queryUserSource(sourceType.name(), name));
		this.createFile(result);
	}	
	
	private IDBSession getDBSession() {
		return sessionFactory.getXdaoSession("");
	}
	
	private void createFile(List<UserSourceDTO> list) {
		for (UserSourceDTO dto : list) {
			LOGGER.info(String.format("-----Start 產生  %s: %s -----", dto.getType(), dto.getName()));
			File file = new File(dto.getType() + "/" + dto.getName() + ".sql");
			FileCreater command = new FileCreater(file, this.convertContent(dto.getContent()));
			command.execute();
			LOGGER.info(String.format("-----End 產生  %s: %s -----", dto.getType(), dto.getName()));
		}
	}
	
	private List<String> convertContent(List<UserSourceDo> list) {
		List<String> result = new ArrayList<String>();
		for (UserSourceDo po : list) {			
			if (list.indexOf(po) == 0) {
				result.add("create or replace " + po.getText());
			} else {
				result.add(po.getText());
			}
		}
		return result;
	}
	
	private UserSourceDTO queryUserSource(String sourceType, String name) {
		UserSourceDTO dto = new UserSourceDTO();
		dto.setName(name);
		dto.setType(sourceType);
		dto.setContent(UserSourceDAO.INSTANCE.selectUserSource(this.getDBSession(), name, sourceType));
		return dto;
	}
	
}
