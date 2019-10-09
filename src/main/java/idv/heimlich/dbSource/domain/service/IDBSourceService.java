package idv.heimlich.dbSource.domain.service;

import idv.heimlich.dbSource.common.code.SourceType;

public interface IDBSourceService {

	void creatAllSource();

	void creatSourceByType(SourceType sourceType);

	void creatSourceByTypeAndName(SourceType sourceType, String name);

}
