package com.zhongtai.mongodb;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mongodb.Mongo;
import com.zhongtai.commontypes.DBCollectionInfo;
import com.zhongtai.commontypes.DBConnection;
import com.zhongtai.commontypes.QueryOptions;

public interface IMongodbFactory {
	public Mongo mongoDB = null;
	public List<DBConnection> connections = null;

	public void create();

	public boolean insert(Map<String, Object> document, DBCollectionInfo collectioninfo);

	public boolean insert(Map<String, Object> document, boolean autocreatecol, DBCollectionInfo collectioninfo);

	public boolean insertFile(InputStream file, Map<String, Object> document, DBCollectionInfo collectioninfo);

	public boolean update(Map<String, Object> document, Map<String, Object> whereClause,
			DBCollectionInfo collectioninfo);

	public boolean accurateUpdate(Map<String, Object> document, Map<String, Object> whereClause,
			DBCollectionInfo collectioninfo);

	public boolean delete(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public boolean deleteFile(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public Map<String, Object> accurateQuery(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public Map<String, Object> accurateQueryFile(DBCollectionInfo var1, Map<String, Object> var2,
			DBCollectionInfo collectioninfo);

	public Map<String, Object> accurateQuery(Map<String, Object> whereClause, Map<String, Object> resultOptions,
			DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> query(DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> queryFiles(DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> query(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> queryFiles(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> query(Map<String, Object> whereClause, QueryOptions options,
			DBCollectionInfo collectioninfo);

	public long count(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public long countFile(Map<String, Object> whereClause, DBCollectionInfo collectioninfo);

	public void deleteCollection(DBCollectionInfo collectioninfo);

	public void deleteDatabase(String dbName);

	public void createIndex(Map<String, Integer> fields, DBCollectionInfo collectioninfo);

	public void closeDB();
}
