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
package  com.entrib.mongoslate.visitor.stmt.translator.select;

import com.entrib.mongoslate.api.mongo.Aggregation;
import com.entrib.mongoslate.api.mongo.Distinct;
import com.entrib.mongoslate.api.mongo.Find;
import com.entrib.mongoslate.expr.Expression;
import com.entrib.mongoslate.expr.LimitExpression;
import com.entrib.mongoslate.expr.OrderByListExpression;
import com.entrib.mongoslate.stmt.SelectStatement;
import com.entrib.mongoslate.type.IntValue;
import com.entrib.mongoslate.visitor.expr.translator.ExpressionTranslatorVisitor;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * SQL Select evaluation -
 * 
 * First the product of all tables in the from clause is formed.
 * The where clause is then evaluated to eliminate rows that do not satisfy the search_condition.
 * Next, the rows are grouped using the columns in the group by clause.
 * Then, Groups that do not satisfy the search_condition in the having clause are eliminated.
 * Next, the expressions in the select clause target list are evaluated.
 * If the distinct keyword in present in the select clause, duplicate rows are now eliminated.
 * The union is taken after each sub-select is evaluated.
 * Finally, the resulting rows are sorted according to the columns specified in the order by clause.
 * 
 *  mongoslate sql
 *  1. If a group by clause is specified then for each tag used in group by clause, the tag has to be used in the select clause.
 *     The tag used in the select clause may or may not use the aggr function.
 *     This behaviour is same as traditional sql
 *
 *  2. Having can be used without group by - check is not needed
 *     This behavior is slightly different than traditional sql but SQL server allows this.
 *
 *  3. Having can be used with tag names defined in the alias_clause in the tag_clause - check needed,
 *     This is different behaviour than the traditional sql, as it allows using the alias in the having clause.
 *
 *     In this case the $match pipeline for having will be generated after the $group pipeline
 *
 *  4. Having can be used just like where clause without having to use any names defined in alias clause. - check needed,
 *     This is different behaviour than the traditional sql, as it like defining a where expression.
 *     In this case having will be and'ed with the $match generated for where clause
 * 
 *  5. Throw a warning if the alias used is as the same name as a valid tag name used in the select clause,
 *     then the valid tag name will get shadowed
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class SelectStatementTranslator {

    private static final String AGGREGATE = "aggregate";
    private static final String PIPELINE = "pipeline";
    
    private SelectStatement selectStatement;
    private ExpressionTranslatorVisitor expressionTranslatorVisitor;

    public SelectStatementTranslator(SelectStatement selectStatement) {
        this.selectStatement = selectStatement;
        this.expressionTranslatorVisitor = new ExpressionTranslatorVisitor();

    }

    public Object translate() {
        if (selectStatement.isAggregation()) { // this is aggregation query
            return buildMongoAggregation(selectStatement);
        } else {
            return buildMongoFind(selectStatement);
        }
    }

    private Object buildMongoFind(SelectStatement selectStatement) {
        Find find =
                selectStatement.isDistinct()?
                    new Distinct(): new Find();

        find.setCollection(selectStatement.getCollection().getValueAsString());

        BasicDBObject keys =
                (BasicDBObject)selectStatement.getTagListExpression()
                        .accept(expressionTranslatorVisitor);
        find.setKeys(keys);

        BasicDBObject query =
                (BasicDBObject)selectStatement.getLogicalExpression()
                        .accept(expressionTranslatorVisitor);
        find.setQuery(query);

        OrderByListExpression orderByExpression =
                selectStatement.getOrderByListExpression();
        if (orderByExpression != null) {
            BasicDBObject sort =
                    (BasicDBObject)orderByExpression.accept(expressionTranslatorVisitor);
            find.setSort(sort);
        }

        LimitExpression limitExpression =
                selectStatement.getLimitExpression();
        if (limitExpression != null) {
            BasicDBObject skipAndLimit =
                    (BasicDBObject)limitExpression.accept(expressionTranslatorVisitor);
            find.setSkip((Integer)((IntValue)skipAndLimit.get("skip")).getValueAsObject());
            find.setLimit((Integer)((IntValue)skipAndLimit.get("limit")).getValueAsObject());
        }

        return find;
    }

    /**
     * Distinct keyword if used gets ignored.
     *
     * For the select_clause find all the column names where aggr function is used
     *   
     *   if alias clause is used
     *
     * @param selectStatement
     * @return
     */
    private Aggregation buildMongoAggregation(SelectStatement selectStatement) {
        Aggregation aggregation = new Aggregation();

        BasicDBObject aggregate = new BasicDBObject();
        aggregate.append(AGGREGATE, selectStatement.getCollection().getValueAsString());

        BasicDBList pipeline = new BasicDBList();
        aggregate.append(PIPELINE, pipeline);

        Expression whereClause = selectStatement.getLogicalExpression();
        if (whereClause != null) {
            BasicDBObject query =
                    (BasicDBObject)selectStatement.getLogicalExpression()
                            .accept(expressionTranslatorVisitor);
            pipeline.add(new BasicDBObject("$match", query));
        }

        // if group by clause is used, go through tag list expressions
        // through each tag and prepare the $group pipeline object

        LimitExpression limitExpression =
                selectStatement.getLimitExpression();
        if (limitExpression != null) {
            BasicDBObject skipAndLimit =
                    (BasicDBObject)limitExpression.accept(expressionTranslatorVisitor);
            pipeline.add(new BasicDBObject("$skip", ((IntValue)skipAndLimit.get("skip")).getValueAsObject()));
            pipeline.add(new BasicDBObject("$limit", ((IntValue)skipAndLimit.get("skip")).getValueAsObject()));
        }

        return aggregation;
    }

}
