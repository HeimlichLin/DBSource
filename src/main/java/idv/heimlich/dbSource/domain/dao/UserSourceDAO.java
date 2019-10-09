package idv.heimlich.dbSource.domain.dao;

import idv.heimlich.dbSource.common.db.IConverter;
import idv.heimlich.dbSource.common.db.IDBSession;
import idv.heimlich.dbSource.common.db.RowMap;
import idv.heimlich.dbSource.common.db.RowMapList;
import idv.heimlich.dbSource.common.db.code.DBOrderStyle;
import idv.heimlich.dbSource.common.db.sql.SqlCode;
import idv.heimlich.dbSource.common.db.sql.SqlObject;
import idv.heimlich.dbSource.common.db.sql.SqlWhere;
import idv.heimlich.dbSource.common.exception.ApBusinessException;
import idv.heimlich.dbSource.domain.bean.UserSourceDo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserSourceDAO {
	
	public static final UserSourceDAO INSTANCE = new UserSourceDAO();
	public static final String TABLENAME = "USER_SOURCE";
	
	private static IConverter<UserSourceDo> CONVERTER = new IConverter<UserSourceDo>() {

		@Override
		public UserSourceDo convert(RowMap paramDataObject) {
			final UserSourceDo userSourceDo = new UserSourceDo();
			userSourceDo.setName(paramDataObject.getString(UserSourceDo.COLUMNS.NAME.name()));
			userSourceDo.setType(paramDataObject.getString(UserSourceDo.COLUMNS.TYPE.name()));
			userSourceDo.setLine(paramDataObject.getString(UserSourceDo.COLUMNS.LINE.name()));
			userSourceDo.setText(paramDataObject.getString(UserSourceDo.COLUMNS.TEXT.name()));
			return userSourceDo;
		}		
	};
	
	public IConverter<UserSourceDo> getConverter() {
		return CONVERTER;
	}

	public SqlWhere getPkSqlWhere(UserSourceDo vo) {
		throw new ApBusinessException("無pk不支援");
	} 
	
	public List<String> selectDistinctName(IDBSession session) {
		return this.selectDistinct(session, UserSourceDo.COLUMNS.NAME.name());
	}
	
	public List<String> selectDistinctType(IDBSession session) {
		return this.selectDistinct(session, UserSourceDo.COLUMNS.TYPE.name());
	}
	
	public List<UserSourceDo> selectDistinctTypeAndName(IDBSession session) {
		SqlObject sqlObject = new SqlObject(TABLENAME); 
		sqlObject.setSqlColumn(" distinct " + UserSourceDo.COLUMNS.NAME.name());
		sqlObject.setSqlColumn(UserSourceDo.COLUMNS.TYPE.name());
		sqlObject.setSqlOrderBy(UserSourceDo.COLUMNS.NAME.name());
		sqlObject.setSqlOrderBy(UserSourceDo.COLUMNS.TYPE.name(), DBOrderStyle.ASC.name());
		return session.select(CONVERTER, SqlCode.creatSelectSql(sqlObject));
	}
	
	public List<String> selectDistinctNameByType(IDBSession session, String type) {
		List<String> result = new ArrayList<String>();
		SqlObject sqlObject = new SqlObject(TABLENAME); 
		sqlObject.setSqlColumn(" distinct " + UserSourceDo.COLUMNS.NAME.name());
		sqlObject.setSqlWhere(UserSourceDo.COLUMNS.TYPE.name(), type);
		sqlObject.setSqlOrderBy(UserSourceDo.COLUMNS.NAME.name());
		RowMapList list = session.query(SqlCode.creatSelectSql(sqlObject));
		final Iterator<RowMap> rowMapIterator = list.iterator();		
		while (rowMapIterator.hasNext()) {
			result.add(rowMapIterator.next().getString(UserSourceDo.COLUMNS.NAME.name()));
		}
		return result;
	}
	
	public List<UserSourceDo> selectUserSource(IDBSession session, String name, String type) {
		SqlObject sqlObject = new SqlObject(TABLENAME);
		sqlObject.setSqlWhere(UserSourceDo.COLUMNS.NAME.name(), name);
		sqlObject.setSqlWhere(UserSourceDo.COLUMNS.TYPE.name(), type);
		sqlObject.setSqlOrderBy(UserSourceDo.COLUMNS.LINE.name());		
		return session.select(CONVERTER, SqlCode.creatSelectSql(sqlObject));
	}
	
	private List<String> selectDistinct(IDBSession session, String column) {
		List<String> result = new ArrayList<String>();
		SqlObject sqlObject = new SqlObject(TABLENAME); 
		sqlObject.setSqlColumn(" distinct " + column);
		sqlObject.setSqlOrderBy(column);
		RowMapList list = session.query(SqlCode.creatSelectSql(sqlObject));
		final Iterator<RowMap> rowMapIterator = list.iterator();		
		while (rowMapIterator.hasNext()) {
			result.add(rowMapIterator.next().getString(column));
		}
		return result;
	}

}
