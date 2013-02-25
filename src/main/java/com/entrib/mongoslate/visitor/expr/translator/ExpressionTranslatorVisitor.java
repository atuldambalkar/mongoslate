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
package  com.entrib.mongoslate.visitor.expr.translator;

import com.entrib.mongoslate.expr.*;
import com.entrib.mongoslate.type.*;
import com.entrib.mongoslate.util.RegexUtils;
import com.entrib.mongoslate.visitor.expr.ExpressionVisitor;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It's guaranteed that logical expression, negation expression will not have value nodes after semantics check
 *
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ExpressionTranslatorVisitor implements ExpressionVisitor {

    public ExpressionTranslatorVisitor() {
    }

    public Object visit(ArithExpression expression) {
        return expression.getResult().getValueAsObject();
    }

    public Object visit(ExistsExpression expression) {
        return new BasicDBObject(expression.getIdentifierValue().getValueAsString(),
                                 new BasicDBObject("$exists", true));
    }

    public Object visit(LogicalExpression expression) {
        BasicDBObject left = (BasicDBObject)expression.getLeft().accept(this);
        BasicDBObject right = (BasicDBObject)expression.getRight().accept(this);

        List<BasicDBObject> list = new ArrayList<BasicDBObject>();
        list.add(left);
        list.add(right);

        switch (expression.getLogicalOperatorEnum()) {
            case AND:
              return new BasicDBObject("$and", list);

            case NOR:
              return new BasicDBObject("$nor", list);

            case OR:
              return new BasicDBObject("$or", list);
        }
        throw new IllegalArgumentException("Illegal logical operator!");
    }

    public Object visit(MinusExpression expression) {
        return expression.getResult();
    }

    public Object visit(MultiplyingExpression expression) {
        return expression.getResult();
    }

    public Object visit(NegationExpression expression) {
        BasicDBObject child = (BasicDBObject)expression.getChild().accept(this);
        return new BasicDBObject("$not", child);
    }

    public Object visit(RelationalExpression expression) {
        Object left = expression.getLeft().accept(this);
        Object right = expression.getRight().accept(this);

        switch (expression.getRelationalOperatorEnum()) {
            case ALL:
                return new BasicDBObject((String)left, new BasicDBObject("$all", right));

            case IN:
                return new BasicDBObject((String)left, new BasicDBObject("$in", right));

            case NIN:
                return new BasicDBObject((String)left, new BasicDBObject("$nin", right));

            case STARTS:
                right = RegexUtils.escapeRegex((String)right);
                Pattern startsWith = Pattern.compile(right + ".*$", Pattern.CASE_INSENSITIVE);
                return new BasicDBObject((String)left, startsWith);

            case ENDS:
                right = RegexUtils.escapeRegex((String)right);
                Pattern endsWith = Pattern.compile("^.*" + right, Pattern.CASE_INSENSITIVE);
                return new BasicDBObject((String)left, endsWith);

            case CONTAINS:
                right = RegexUtils.escapeRegex((String)right);
                Pattern contains = Pattern.compile("^.*" + right + ".*$", Pattern.CASE_INSENSITIVE);
                return new BasicDBObject((String)left, contains);

            case NEAR:
                LocationValue.LocationQuery locQuery = (LocationValue.LocationQuery)right;
                GeoCoordinate geoCoordinate =
                        (GeoCoordinate)locQuery.geoCoordinateValue.getValueAsObject();
                float distance = locQuery.distance;
                float[] nearArr = { geoCoordinate.getLatitude(), geoCoordinate.getLongitude() };
                BasicDBObject near = new BasicDBObject("$near", nearArr);
                if (distance > 0) {
                    near.put("$maxDistance", distance);
                }
                return new BasicDBObject((String)left, near);

            case WITHIN:
                if (right instanceof BoxValue.Box) {
                    BoxValue.Box box = (BoxValue.Box)right;
                    GeoCoordinate bottomLeft = (GeoCoordinate)box.bottomLeft.getValueAsObject();
                    GeoCoordinate topRight = (GeoCoordinate)box.topRight.getValueAsObject();
                    List<float[]> boxList = new ArrayList<float[]>();
                    boxList.add(new float[] {bottomLeft.getLatitude(), bottomLeft.getLongitude()});
                    boxList.add(new float[] {topRight.getLatitude(), topRight.getLongitude()});
                    BasicDBObject withinBox =
                            new BasicDBObject("$within",
                                new BasicDBObject("$box", boxList));
                    return new BasicDBObject((String)left, withinBox);
                } else if (right instanceof PolygonValue.Polygon) {
                    PolygonValue.Polygon polygon = (PolygonValue.Polygon)right;
                    List<GeoCoordinateValue> coordinates = polygon.coordinates;
                    List<float[]> polygonList = new ArrayList<float[]>();
                    for (GeoCoordinateValue geoValue: coordinates) {
                        GeoCoordinate coordinate = (GeoCoordinate)geoValue.getValueAsObject();
                        polygonList.add(new float[] {coordinate.getLatitude(), coordinate.getLongitude()});
                    }
                    BasicDBObject withinPolygon =
                            new BasicDBObject("$within",
                                new BasicDBObject("$polygon", polygonList));
                    return new BasicDBObject((String)left, withinPolygon);
                } else if (right instanceof CircleValue.Circle) {
                    CircleValue.Circle circle = (CircleValue.Circle)right;
                    GeoCoordinate center = (GeoCoordinate)circle.geoCoordinateValue.getValueAsObject();
                    float radius = circle.radius;
                    List circleList = new ArrayList();
                    circleList.add(new float[] {center.getLatitude(), center.getLongitude()});
                    circleList.add(radius);
                    BasicDBObject withinCircle =
                            new BasicDBObject("$within",
                                new BasicDBObject("$center", circleList));
                    return new BasicDBObject((String)left, withinCircle);

                }
                throw new IllegalArgumentException("Illegal right operand for within operator!");
            
            case EQ:
                return (left instanceof String)?
                        new BasicDBObject((String)left, right):
                        new BasicDBObject((String)right, left);

            case NEQ:
                return (left instanceof String)?
                        new BasicDBObject((String)left, new BasicDBObject("$ne", right)):
                        new BasicDBObject((String)right, new BasicDBObject("$ne", left));

            case GT:
                return (left instanceof String)?
                        new BasicDBObject((String)left, new BasicDBObject("$gt", right)):
                        new BasicDBObject((String)right, new BasicDBObject("$lt", left));

            case GTE:
                return (left instanceof String)?
                        new BasicDBObject((String)left, new BasicDBObject("$gte", right)):
                        new BasicDBObject((String)right, new BasicDBObject("$lte", left));

            case LT:
                return (left instanceof String)?
                        new BasicDBObject((String)left, new BasicDBObject("$lt", right)):
                        new BasicDBObject((String)right, new BasicDBObject("$gt", left));

            case LTE:
                return (left instanceof String)?
                        new BasicDBObject((String)left, new BasicDBObject("$lte", right)):
                        new BasicDBObject((String)right, new BasicDBObject("$gte", left));
        }
        throw new IllegalArgumentException("Illegal relational operator!");

    }

    public Object visit(ValueListExpression expression) {
        BasicDBList list = new BasicDBList();
        List<Value> values = expression.getValues();
        for (Value value: values) {
            list.add(value.getValueAsObject());
        }
        return list;
    }

    public Object visit(TagListExpression expression) {
        BasicDBObject selectFields = new BasicDBObject();
        for (IdentifierValue value: expression.getValues()) {
            selectFields.put(value.getValueAsString(), 1);
        }
        return selectFields;
    }

    public Object visit(GroupByListExpression expression) {
        return null;
    }
    
    public Object visit(OrderByListExpression expression) {
        BasicDBObject orderByFields = new BasicDBObject();
        for (OrderByValue value: expression.getValues()) {
            orderByFields.put(value.getValueAsString(),
                    value.getOrderEnum() == OrderByValue.OrderEnum.ASC? 1: -1);
        }
        return orderByFields;

    }

    public Object visit(LimitExpression expression) {
        // using BasicDBObject here, just so as to be consistent with
        // the rest of the code in this visitor class
        // slightly hackish, but it's internal code
        BasicDBObject skipAndLimit = new BasicDBObject();
        skipAndLimit.put("skip", expression.getStart());
        skipAndLimit.put("limit", expression.getCount());
        return skipAndLimit;
    }

    public Object visit(Value expression) {
        return expression.getValueAsObject();
    }

    public Object getResult() {
        return null;
    }

    public String getMessage() {
        return null;
    }

}
