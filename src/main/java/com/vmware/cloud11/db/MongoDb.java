package com.vmware.cloud11.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface MongoDb {

	public DBCollection connect(String ip, long port, String dbName,
			String tableName);

	public void insert(BasicDBObject document);

	public void update(BasicDBObject query, BasicDBObject updateObj);

	public DBCursor find(BasicDBObject searchQuery);

	public Iterable<DBObject>  getFreeCapacity();

}
