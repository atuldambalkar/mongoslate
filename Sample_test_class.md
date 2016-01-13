Sample Mongoslate translator API invocation code

# Introduction #

Sample code for using the Mongoslate translator.


# Details #

```

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
package com.entrib.mongoslate.api;

import com.entrib.mongoslate.api.mongo.Find;
import junit.framework.TestCase;

/**
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class TranslatorTest extends TestCase {

    private Translator translator = new Translator();

   /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *
    * Collection: elements,
    * Keys: { "tag1" : 1 , "tag2" : 1 , "tag3" : 1} 
    * Query: { "tag2" : { "$lt" : { "$date" : "2012-02-10T04:53:34.000Z"}}} 
    * Sort: null 
    * Skip: 0 
    * Limit: 0 
    * Distinct
    */
    public void testDistinct() {
        String test = "select distinct tag1, tag2, tag3 from elements where timestamp('10-2-2012-10-23-34') > tag2";
        Find find = translator.translate(test);
        assertNotNull(find);
    }

   /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *     
    * Collection: elements 
    * Keys: { "tag1" : 1} 
    * Query: { "tag1" : { "$in" : [ 1 , 2 , 3 , 4 , 5]}} 
    * Sort: { "tag1" : 1} 
    * Skip: 0 
    * Limit: 10
    */
    public void testSelectInSortAscLimit() {
        String test = "select tag1 from elements where tag1 in { 1, 2, 3, 4, 5} order by tag1 asc limit 10";
        Find find = translator.translate(test);
        assertNotNull(find);        
    }

    /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *     
    * Collection: elements 
    * Keys: { "tag1" : 1} 
    * Query: { "$and" : [ { "tag1" : { "$gt" : 100}} , { "tag1" : { "$lt" : 200}}]} 
    * Sort: { "tag1" : 1} 
    * Skip: 0 
    * Limit: 10
    */
    public void testSelectLtGtSortDescLimit() {
        String test = "select tag1 from elements where tag1 > 100 && tag1 < 200 order by tag1 asc limit 10";
        Find find = translator.translate(test);
        assertNotNull(find);
    }

    /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *     
    * Collection: elements 
    * Keys: { "tag1" : 1} 
    * Query: { "$and" : [ { "tag1" : { "$gt" : 100}} , { "tag1" : { "$lt" : 200}}]} 
    * Sort: { "tag1" : 1} 
    * Skip: 5 
    * Limit: 10
    */
    public void testSelectLtGtSortDescSkipLimit() {
        String test = "select tag1 from elements where tag1 > 100 && tag1 < 200 order by tag1 asc limit 5, 10";
        Find find = translator.translate(test);
        assertNotNull(find);
    }

   /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *     
    * Collection: elements 
    * Keys: { "tag1" : 1} 
    * Query: { "$and" : [ { "tag1" : { "$gt" : 300}} , { "tag1" : { "$lt" : 200}}]} 
    * Sort: { "tag1" : 1} 
    * Skip: 5 
    * Limit: 10
    */
    public void testSelectAndLtGtArithExprSortDescSkipLimit() {
        String test = "select tag1 from elements where tag1 > 100 + 200 && tag1 < 200 order by tag1 asc limit 5, 10";
        Find find = translator.translate(test);
        assertNotNull(find);
    }

    /**
    * The 'Find' object returned by the Translator contains following 
    * for the given SQL query.
    *     
    * Collection: elements 
    * Keys: { "tag1" : 1} 
    * Query: { "$or" : [ { "$and" : [ { "tag1" : { "$gt : -100}} , { "tag1" : { "$lt" : 200}}]} , { "tag3" : { "$regex" : "blah.*$" , $options" : "i"}}]} 
    * Sort: { "tag1" : 1} 
    * Skip: 5 
    * Limit: 10
    */
    public void testSelectAndOrLtGtArithExprSortDescSkipLimit() {
        String test = "select tag1 from elements where tag1 > 100 + -200 && tag1 < 200 || tag3 starts \"blah\" order by tag1 asc limit 5, 10";
        Find find = translator.translate(test);
        assertNotNull(find);
    }
}


```