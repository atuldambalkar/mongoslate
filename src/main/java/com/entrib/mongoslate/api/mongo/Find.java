/*
   Copyright [2013] [Entrib Technologies]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package  com.entrib.mongoslate.api.mongo;

import com.mongodb.DBObject;

/**
 * Select query class for MongoDB.
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class Find {

    private String collection;
    private DBObject keys;
    private DBObject query;
    private DBObject sort;
    private int skip;
    private int limit;

    public Find() {
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     * The fields to be returned in the result set.
     *
     * @return
     */
    public DBObject getKeys() {
        return keys;
    }

    public void setKeys(DBObject keys) {
        this.keys = keys;
    }

    /**
     * The query for objects to search.
     *
     * @return
     */
    public DBObject getQuery() {
        return query;
    }

    public void setQuery(DBObject query) {
        this.query = query;
    }

    /**
     * Sort fields to be used.
     *
     * @return
     */
    public DBObject getSort() {
        return sort;
    }

    public void setSort(DBObject sort) {
        this.sort = sort;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Collection: " + collection
                + " Keys: " + keys
                + " Query: " + query
                + " Sort: " + sort
                + " Skip: " + skip
                + " Limit: " + limit;
    }
}
