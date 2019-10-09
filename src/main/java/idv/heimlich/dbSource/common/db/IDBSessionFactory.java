package idv.heimlich.dbSource.common.db;

public interface IDBSessionFactory {

	IDBSession getXdaoSession(String conn);

}
