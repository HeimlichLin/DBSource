package idv.heimlich.dbSource.common.db;


public interface IConverter<Po> {

	Po convert(RowMap paramDataObject);

}
