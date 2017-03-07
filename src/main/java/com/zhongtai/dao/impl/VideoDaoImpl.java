package com.zhongtai.dao.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zhongtai.commontypes.DBCollectionInfo;
import com.zhongtai.commontypes.QueryOptions;
import com.zhongtai.dao.IVideoDao;
import com.zhongtai.modle.VideosInfo;
import com.zhongtai.mongodb.MongodbFactory;
import com.zhongtai.tools.Conf;

@Service("videoDao")
public class VideoDaoImpl extends MongodbFactory implements IVideoDao {

	DBCollectionInfo collectioninfo = null;

	public VideoDaoImpl() {

		collectioninfo = new DBCollectionInfo();
		collectioninfo.setCollectionName(VideosInfo.class.getSimpleName());
		collectioninfo.setDBName(Conf.mongodb.collectioninfo.dbName);
	}

	@Override
	public boolean insert(Map<String, Object> document) {
		// TODO Auto-generated method stub
		return this.insert(document, collectioninfo);
	}

	@Override
	public boolean insert(Map<String, Object> document, boolean autocreatecol) {
		// TODO Auto-generated method stub
		return this.insert(document, autocreatecol, collectioninfo);
	}

	@Override
	public boolean insertFile(InputStream file, Map<String, Object> document) {
		// TODO Auto-generated method stub
		return this.insertFile(file, document, collectioninfo);
	}

	@Override
	public boolean update(Map<String, Object> document, Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.update(document, whereClause, collectioninfo);
	}

	@Override
	public boolean accurateUpdate(Map<String, Object> document, Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.accurateUpdate(document, whereClause, collectioninfo);
	}

	@Override
	public boolean delete(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.delete(whereClause, collectioninfo);
	}

	@Override
	public boolean deleteFile(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.deleteFile(whereClause, collectioninfo);
	}

	@Override
	public Map<String, Object> accurateQuery(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.accurateQuery(whereClause, collectioninfo);
	}

	@Override
	public Map<String, Object> accurateQueryFile(DBCollectionInfo var1, Map<String, Object> var2) {
		// TODO Auto-generated method stub
		return this.accurateQueryFile(var1, var2, collectioninfo);
	}

	@Override
	public Map<String, Object> accurateQuery(Map<String, Object> whereClause, Map<String, Object> resultOptions) {
		// TODO Auto-generated method stub
		return this.accurateQuery(whereClause, resultOptions, collectioninfo);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.query(whereClause, collectioninfo);
	}

	@Override
	public List<Map<String, Object>> queryFiles(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.queryFiles(whereClause, collectioninfo);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> whereClause, QueryOptions options) {
		// TODO Auto-generated method stub
		return this.query(whereClause, options, collectioninfo);
	}

	@Override
	public long count(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.count(whereClause, collectioninfo);
	}

	@Override
	public long countFile(Map<String, Object> whereClause) {
		// TODO Auto-generated method stub
		return this.countFile(whereClause, collectioninfo);
	}

	@Override
	public void createIndex(Map<String, Integer> fields) {
		// TODO Auto-generated method stub
		this.createIndex(fields, collectioninfo);
	}

}
