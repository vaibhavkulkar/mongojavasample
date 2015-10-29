package com.vmware.cloud11.sample;

import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.vmware.cloud11.db.MongoDb;
import com.vmware.cloud11.db.MongoDbImpl;

public class Sample {
	public static void main(String[] args) {
		MongoDb mongoDb = new MongoDbImpl();
		DBCollection table = mongoDb.connect("10.112.4.54", 27017, "agent",
				"host");
		try {
			/**** Insert ****/
			BasicDBObject document = new BasicDBObject();
			document.put("name", "Host1");
			document.put("cpu", 1000);
			document.put("memory", 1024);
			document.put("io", 100);
			document.put("bandwidth", 100);
			document.put("diskspace", 10);
			mongoDb.insert(document);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "Host1");
			DBCursor cursor = mongoDb.find(searchQuery);
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			BasicDBObject query = new BasicDBObject();
			query.put("name", "Host1");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Host 1");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			mongoDb.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 = new BasicDBObject().append("name",
					"Host 1");

			DBCursor cursor2 = table.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			Iterator<DBObject> itr = mongoDb.getFreeCapacity().iterator();
			System.out.println("All hosts******************");
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}

			/**** Done ****/
			System.out.println("Done");

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
