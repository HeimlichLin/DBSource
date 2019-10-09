package idv.heimlich.dbSource.common.db.impl;

import idv.heimlich.dbSource.common.db.IConverter;
import idv.heimlich.dbSource.common.db.IDBSession;
import idv.heimlich.dbSource.common.db.RowMap;
import idv.heimlich.dbSource.common.db.RowMapList;
import idv.heimlich.dbSource.common.db.sql.SqlWhere;
import idv.heimlich.dbSource.common.db.utils.ConnectionUtil;
import idv.heimlich.dbSource.common.exception.TxBusinessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBSessionImpl implements IDBSession {
	
	private Connection connection;
	
	private Connection initial() {
		if (this.connection == null) {
			this.connection = ConnectionUtil.getConnection();
		}
		return this.connection;
	}

	@Override
	public Connection getConnection() {
		return this.initial();
	}

	@Override
	public void beginTransaction() {
		try {
			this.initial().setAutoCommit(false);
		} catch (final SQLException e) {
			throw new TxBusinessException("beginTransaction fail", e);
		}
	}

	@Override
	public void commit() {
		try {
			this.initial().commit();
		} catch (final SQLException e) {
			throw new TxBusinessException("commit fail", e);
		}
	}

	@Override
	public void close() {
		try {
			this.initial().close();
		} catch (final SQLException e) {
			throw new TxBusinessException("close fail", e);
		}
	}

	@Override
	public RowMapList query(String sql) {
		try {
			final Connection connection = this.initial();
			final PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			final ResultSet resultSet = preparedStatement.executeQuery();
			RowMapList result = this.result2RowMapList(resultSet);
			return result;
		} catch (final Exception e) {
			throw new TxBusinessException("query fail", e);
		}
	}

	@Override
	public RowMapList query(String sql, SqlWhere sqlWhere) {
		try {
			final Connection connection = this.initial();
			final PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			for (String key : sqlWhere.toMap().keySet()) {
				preparedStatement.setString(Integer.parseInt(key), sqlWhere.toMap().get(key));
			}
			final ResultSet resultSet = preparedStatement.executeQuery();
			RowMapList result = this.result2RowMapList(resultSet);
			return result;
		} catch (final Exception e) {
			throw new TxBusinessException("query fail", e);
		}
	}

	@Override
	public <Po> List<Po> select(IConverter<Po> converter, String sql) {
		final RowMapList rowMapList = this.query(sql);
		final List<Po> pos = new ArrayList<Po>();
		final Iterator<RowMap> rowMapIterator = rowMapList.iterator();
		while (rowMapIterator.hasNext()) {
			pos.add(converter.convert(rowMapIterator.next()));
		}
		return pos;
	}

	@Override
	public <Po> List<Po> select(IConverter<Po> converter, String sql,
			SqlWhere sqlWhere) {
		final RowMapList rowMapList = this.query(sql, sqlWhere);
		final List<Po> pos = new ArrayList<Po>();
		final Iterator<RowMap> rowMapIterator = rowMapList.iterator();
		while (rowMapIterator.hasNext()) {
			pos.add(converter.convert(rowMapIterator.next()));
		}
		return pos;
	}
	
	private RowMapList result2RowMapList(final ResultSet resultSet) {
		int count;
		try {
			final ResultSetMetaData rsmd = resultSet.getMetaData();
			count = rsmd.getColumnCount();
			final RowMapList rowMapList = new RowMapList();
			while (resultSet.next()) {
				final RowMap rowMap = new RowMap();
				for (int i = 1; i <= count; i++) {
					rowMap.setValue(rsmd.getColumnName(i),
							resultSet.getObject(i));
				}
				rowMapList.add(rowMap);
			}
			return rowMapList;
		} catch (final Exception e) {
			throw new TxBusinessException("result2RowMapList fail", e);
		}
	}

}
