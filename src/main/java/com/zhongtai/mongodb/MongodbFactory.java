package com.zhongtai.mongodb;


import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.zhongtai.commontypes.DBCollectionInfo;
import com.zhongtai.commontypes.DBConnection;
import com.zhongtai.commontypes.QueryOptions;
import com.zhongtai.tools.Conf;
import com.zhongtai.tools.Logs;


/**
 * Created by rain on 2016/12/13.
 * @param <T>
 */
public class MongodbFactory implements IMongodbFactory{
    public Mongo mongoDB = null;
  //  public DBCollectionInfo collectioninfo = null;
    public List<DBConnection> connections = null;
 //   public static final MongodbFactory2 I = new MongodbFactory2();
   
    /*public MongodbFactory2(T t) {
		// TODO Auto-generated constructor stub
    	collectioninfo = new DBCollectionInfo();
        collectioninfo.setCollectionName(t.getClass().getSimpleName());
        collectioninfo.setDBName(Conf.mongodb.collectioninfo.dbName);
	}*/
    
    
    @PostConstruct
    public void create(){
        Logs.Console.info("init mongodb...");
        connections = Conf.mongodb.options.nodes;
        
        if(this.mongoDB == null) {
            try {
                MongoOptions e = new MongoOptions();
                e.safe = true;
                e.autoConnectRetry = Conf.mongodb.options.autoConnectRetry;
                e.connectionsPerHost = Conf.mongodb.options.connectionsPerHost;
                e.connectTimeout = Conf.mongodb.options.connectTimeout;
                e.maxWaitTime = Conf.mongodb.options.maxWaitTime;
                e.socketKeepAlive = Conf.mongodb.options.socketKeepAlive;
                e.socketTimeout = Conf.mongodb.options.socketTimeout;
                e.threadsAllowedToBlockForConnectionMultiplier =  Conf.mongodb.options.ThreadsPerConnection;
                if(connections.size() == 1) {
                    this.mongoDB = new Mongo(new ServerAddress(((DBConnection)connections.get(0)).getDBAddress(), ((DBConnection)connections.get(0)).getDBPort()), e);
                    Logs.Console.info("链接地址为："+connections.get(0).getDBAddress()+"端口："+connections.get(0).getDBPort());
                } else {
                    ArrayList addrs = new ArrayList();
                    Iterator iterConnections = connections.iterator();
                    DBConnection tempConnection = null;

                    while(iterConnections.hasNext()) {
                        tempConnection = (DBConnection)iterConnections.next();
                        if(tempConnection.getDBAddress() != null && !"".equalsIgnoreCase(tempConnection.getDBAddress()) && tempConnection.getDBPort() >= 0) {
                            addrs.add(new ServerAddress(tempConnection.getDBAddress(), tempConnection.getDBPort()));
                            Logs.Console.info("init mongodb success...  host："+tempConnection.getDBAddress()+"port："+tempConnection.getDBPort());
                        }else{
                            Logs.Console.info("读取链接地址失败，请检查配置文件");
                        }
                    }
                    this.mongoDB = new Mongo(addrs, e);
                }
            } catch (MongoException var7) {
                Logs.Console.error(var7.getMessage());
                throw var7;
            } catch (UnknownHostException e) {
                Logs.Console.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean insert(Map<String, Object> document,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        return this.insert(document, true,collectioninfo);
    }

    public boolean insert(Map<String, Object> document, boolean autocreatecol,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        boolean blnInsert = false;
        DBCollection dbcol = null;
        if(autocreatecol) {
            dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        } else {
            if(!this.mongoDB.getDB(collectioninfo.getDBName()).collectionExists(collectioninfo.getCollectionName())) {
                Logs.Console.warn("数据插入失败,传入文档集合不存在,且不允许自动创建");
                return false;
            }

            dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        }

        BasicDBObject bObject = new BasicDBObject();
        bObject.putAll(document);

        try {
            WriteResult e = dbcol.save(bObject);
            if(e.getError() == null) {
                blnInsert = true;
                Logs.Console.info("数据插入成功.  "+bObject.toString());
            } else {
                Logs.Console.warn("数据插入失败: " + e.getError());
            }
        } catch (RuntimeException var8) {
            Logs.Console.error("数据插入失败: " + var8.getMessage());
        }

        return blnInsert;
    }

    public boolean insertFile(InputStream file, Map<String, Object> document,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        GridFS gridFS = new GridFS(this.mongoDB.getDB(collectioninfo.getDBName()), collectioninfo.getCollectionName());
        GridFSInputFile gfs = gridFS.createFile(file);
        Iterator i$ = document.keySet().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            gfs.put(key, document.get(key));
        }
        gfs.save();
        Logs.Console.info("gfs保存成功.  id:"+gfs.getId());
        return true;
    }

    public boolean update(Map<String, Object> document, Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        boolean blnUpdate = false;
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        BasicDBObject update = new BasicDBObject();
        update.putAll(document);
        BasicDBObject query = new BasicDBObject();
        query.putAll(whereClause);

        try {
            WriteResult e = dbcol.update(query, update);
            if(e != null) {
                blnUpdate = true;
                Logs.Console.info("数据更新成功.  "+document.toString());
            } else {
                Logs.Console.warn("根据查询条件无法获取要更新的文档对象");
            }
        } catch (RuntimeException var9) {
            Logs.Console.error("数据插入失败: " + var9.getMessage());
        }

        return blnUpdate;
    }

    public boolean accurateUpdate(Map<String, Object> document, Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        boolean blnUpdate = false;
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        BasicDBObject update = new BasicDBObject();
        update.putAll(document);
        BasicDBObject query = new BasicDBObject();
        query.putAll(whereClause);

        try {
            DBObject e = dbcol.findAndModify(query, update);
            if(e != null) {
                blnUpdate = true;
            } else {
                Logs.Console.warn("根据查询条件无法获取要更新的文档对象");
            }
        } catch (RuntimeException var9) {
            Logs.Console.error("数据插入失败: " + var9.getMessage());
        }

        return blnUpdate;
    }

    public boolean delete(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        boolean blnDelete = false;
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        BasicDBObject query = new BasicDBObject();
        query.putAll(whereClause);

        try {
            WriteResult e = dbcol.remove(query);
            if(e.getError() == null) {
                blnDelete = true;
                Logs.Console.info("删除成功.  "+e.toString());
            } else {
                Logs.Console.warn("数据删除失败: " + e.getError());
            }
        } catch (RuntimeException var7) {
            Logs.Console.error("数据插入失败: " + var7.getMessage());
        }

        return blnDelete;
    }

    public boolean deleteFile(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        GridFS gridFS = new GridFS(this.mongoDB.getDB(collectioninfo.getDBName()), collectioninfo.getCollectionName());
        BasicDBObject query = new BasicDBObject(whereClause);
        gridFS.remove(query);
        Logs.Console.info("gfs删除成功.  "+query.toString());
        return true;
    }

    public Map<String, Object> accurateQuery(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        GridFS gridFS = new GridFS(this.mongoDB.getDB(collectioninfo.getDBName()), collectioninfo.getCollectionName());
        BasicDBObject query = new BasicDBObject(whereClause);
        GridFSDBFile doc = gridFS.findOne(query);
        HashMap document = new HashMap();
        Iterator i$ = doc.keySet().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            document.put(key, doc.get(key));
        }

        document.put("$file", doc.getInputStream());
        return document;
    }

    public Map<String, Object> accurateQueryFile(DBCollectionInfo var1, Map<String, Object> var2,DBCollectionInfo collectioninfo) {
        return null;
    }

    public Map<String, Object> accurateQuery(Map<String, Object> whereClause, Map<String, Object> resultOptions,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        Map result = null;
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        DBObject dbResult = null;
        if(whereClause != null && whereClause.size() > 0) {
            BasicDBObject query = new BasicDBObject();
            query.putAll(whereClause);

            try {
                if(resultOptions != null && resultOptions.size() > 0) {
                    BasicDBObject e = new BasicDBObject();
                    e.putAll(resultOptions);
                    dbResult = dbcol.findOne(query, e);
                } else {
                    dbResult = dbcol.findOne(query);
                }

                if(dbResult != null) {
                    result = dbResult.toMap();
                }
            } catch (RuntimeException var9) {
                Logs.Console.error("数据查询失败: " + var9.getMessage());
            }
        } else {
            dbResult = dbcol.findOne();
        }

        if(dbResult != null) {
            result = dbResult.toMap();
        }

        return result;
    }

    public List<Map<String, Object>> query(DBCollectionInfo collectioninfo) {
        return this.query((Map)null, collectioninfo);
    }

    public List<Map<String, Object>> queryFiles(DBCollectionInfo collectioninfo) {
        return this.queryFiles((Map)null,collectioninfo);
    }

    public List<Map<String, Object>> query(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        QueryOptions options = new QueryOptions();
        List result = this.query(whereClause, options,collectioninfo);
        return result;
    }

    public List<Map<String, Object>> queryFiles(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        ArrayList result = null;
        GridFS gridFS = new GridFS(this.mongoDB.getDB(collectioninfo.getDBName()), collectioninfo.getCollectionName());
        List files = null;
        BasicDBObject tempRecord;
        if(whereClause != null && whereClause.size() > 0) {
            tempRecord = new BasicDBObject(whereClause);
            files = gridFS.find(tempRecord);
        } else {
            tempRecord = new BasicDBObject();
            files = gridFS.find(tempRecord);
        }

        if(files != null && !files.isEmpty()) {
            result = new ArrayList();
            
            for(int i = 0; i < files.size(); ++i) {
               
            	HashMap var10 = new HashMap();
            	Iterator i$ = ((GridFSDBFile)files.get(i)).keySet().iterator();

                while(i$.hasNext()) {
                    String key = (String)i$.next();
                    var10.put(key, ((GridFSDBFile)files.get(i)).get(key));
                }

                var10.put("$file", ((GridFSDBFile)files.get(i)).getInputStream());
                result.add(var10);
            }

            return result;
        } else {
            return result;
        }
    }

    public List<Map<String, Object>> query(Map<String, Object> whereClause, QueryOptions options,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        ArrayList mapResult = new ArrayList();
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        BasicDBObject returnFields = new BasicDBObject();
        int tempRecord;
        if(options.getReturnFields() != null && options.getReturnFields().length > 0) {
            String[] dbCursor = options.getReturnFields();

            for(tempRecord = 0; tempRecord < dbCursor.length; ++tempRecord) {
                if(dbCursor[tempRecord] != null && !"".equalsIgnoreCase(dbCursor[tempRecord])) {
                    returnFields.put(dbCursor[tempRecord], Integer.valueOf(1));
                }
            }
        }

        DBCursor var11 = null;
        BasicDBObject var12;
        if(whereClause != null && whereClause.size() > 0) {
            var12 = new BasicDBObject();
            var12.putAll(whereClause);

            try {
                var11 = dbcol.find(var12, returnFields);
            } catch (RuntimeException var10) {
                Logs.Console.error("数据查询失败: " + var10.getMessage());
            }
        } else if(returnFields.size() > 0) {
            var11 = dbcol.find((DBObject)null, returnFields);
        } else {
            var11 = dbcol.find();
        }

        if(var11.count() < 1) {
            return mapResult;
        } else {
            if(options.getSortField() != null && !"".equalsIgnoreCase(options.getSortField().trim())) {
                var12 = new BasicDBObject();
                if(options.isASC()) {
                    var12.put(options.getSortField().trim(), Integer.valueOf(1));
                } else {
                    var12.put(options.getSortField().trim(), Integer.valueOf(-1));
                }

                var11.sort(var12);
            }

            if(options.getPageSize() > 0 && options.getPageIndex() > 0) {
                tempRecord = options.getPageSize() * (options.getPageIndex() - 1);
                var11.skip(tempRecord);
                var11.limit(options.getPageSize());
            }

            var12 = null;

            while(var11.hasNext()) {
                Map var13 = var11.next().toMap();
                mapResult.add(var13);
            }

            return mapResult;
        }
    }

    public long count(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        long lgCount = 0L;
        DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
        BasicDBObject query = new BasicDBObject();
        query.putAll(whereClause);
        lgCount = dbcol.count(query);
        return lgCount;
    }

    public long countFile(Map<String, Object> whereClause,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        GridFS gridFS = new GridFS(this.mongoDB.getDB(collectioninfo.getDBName()), collectioninfo.getCollectionName());
        DBCursor dbCursor = null;
        if(whereClause != null && whereClause.size() > 0) {
            BasicDBObject query = new BasicDBObject(whereClause);
            dbCursor = gridFS.getFileList(query);
        } else {
            dbCursor = gridFS.getFileList();
        }

        return (long)dbCursor.count();
    }

    public void deleteCollection(DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        DB db = this.mongoDB.getDB(collectioninfo.getDBName());
        boolean blnExist = db.collectionExists(collectioninfo.getCollectionName());
        if(blnExist) {
            DBCollection col = db.getCollection(collectioninfo.getCollectionName());
            col.dropIndexes();
            col.drop();
        }
    }

    public void deleteDatabase(String dbName) {
        if(this.mongoDB==null){
            create();
        }
        this.mongoDB.dropDatabase(dbName);
    }

    public void createIndex(Map<String, Integer> fields,DBCollectionInfo collectioninfo) {
        if(this.mongoDB==null){
            create();
        }
        if(this.mongoDB.getDB(collectioninfo.getDBName()).collectionExists(collectioninfo.getCollectionName())) {
            DBCollection dbcol = this.mongoDB.getDB(collectioninfo.getDBName()).getCollection(collectioninfo.getCollectionName());
            BasicDBObject dbObject = new BasicDBObject();
            dbObject.putAll(fields);
            dbcol.ensureIndex(dbObject);
        } else {
            Logs.Console.warn("创建索引失败,待创建索引的文档集合不存在");
        }
    }

    public void closeDB() {
        if(this.mongoDB==null){
            create();
        }else if(this.mongoDB != null) {
            this.mongoDB.close();
            this.mongoDB = null;
        }
    }
}
