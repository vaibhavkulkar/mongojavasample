package com.vmware.cloud11.db;

import java.net.UnknownHostException;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbImpl implements MongoDb {

	DBCollection table;

	@Override
	public DBCollection connect(String ip, long port, String dbName,
			String tableName) {
		// Connect to mongodb
		MongoClient mongo = null;
		try {
			mongo = new MongoClient(ip, 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB(dbName);

		// if collection/table doesn't exists, MongoDB will create it for you
		table = db.getCollection(tableName);
		return table;

	}

	@Override
	public void insert(BasicDBObject document) {
		table.insert(document);
	}

	@Override
	public void update(BasicDBObject query, BasicDBObject updateObj) {
		table.update(query, updateObj);

	}

	@Override
	public DBCursor find(BasicDBObject searchQuery) {
		return table.find(searchQuery);
	}

	@Override
	// String aggregate = db.getCollection('host').aggregate([{ $group: { _id: {
	// name:
	// "ubuntu-kvm"}, cpu: { $avg: "$cpu" }, memory: { $avg: "$memory"}, io: {
	// $avg: "$io" }, bandwidth: { $avg: "$bandwidth" } } }])
	public Iterable<DBObject> getFreeCapacity() {
		BasicDBObject groupFields = new BasicDBObject("_id", "$name");
		groupFields.append("cpu", new BasicDBObject("$avg", "$cpu"));
		groupFields.append("memory", new BasicDBObject("$avg", "$memory"));
		groupFields.append("io", new BasicDBObject("$avg", "$io"));
		groupFields.append("diskspace", new BasicDBObject("$avg", "$diskspace"));
		groupFields
				.append("bandwidth", new BasicDBObject("$avg", "$bandwidth"));
		DBObject group = new BasicDBObject("$group", groupFields);
		AggregationOutput output = table.aggregate(group);
		return output.results();
	}
}
