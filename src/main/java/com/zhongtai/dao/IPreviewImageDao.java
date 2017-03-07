package com.zhongtai.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zhongtai.commontypes.DBCollectionInfo;
import com.zhongtai.commontypes.QueryOptions;
import com.zhongtai.mongodb.IMongodbFactory;

public interface IPreviewImageDao  extends IMongodbFactory{

	public boolean insert(Map<String, Object> document);

	public boolean insert(Map<String, Object> document, boolean autocreatecol);

	public boolean insertFile(InputStream file, Map<String, Object> document);

	public boolean update(Map<String, Object> document, Map<String, Object> whereClause);

	public boolean accurateUpdate(Map<String, Object> document, Map<String, Object> whereClause);

	public boolean delete(Map<String, Object> whereClause);

	public boolean deleteFile(Map<String, Object> whereClause);

	public Map<String, Object> accurateQuery(Map<String, Object> whereClause);

	public Map<String, Object> accurateQueryFile(DBCollectionInfo var1, Map<String, Object> var2);

	public Map<String, Object> accurateQuery(Map<String, Object> whereClause, Map<String, Object> resultOptions);

	public List<Map<String, Object>> query(DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> queryFiles(DBCollectionInfo collectioninfo);

	public List<Map<String, Object>> query(Map<String, Object> whereClause);

	public List<Map<String, Object>> queryFiles(Map<String, Object> whereClause);

	public List<Map<String, Object>> query(Map<String, Object> whereClause, QueryOptions options);

	public long count(Map<String, Object> whereClause);

	public long countFile(Map<String, Object> whereClause);


	public void createIndex(Map<String, Integer> fields);
}
