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
package  com.entrib.mongoslate.expr;

import com.entrib.mongoslate.MongoslateParser;
import com.entrib.mongoslate.type.*;
import com.entrib.mongoslate.type.AggregationFunctionEnum;
import org.antlr.runtime.tree.CommonTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert the given AST into Expression tree nodes. The generated expression tree
 * then can be parsed/visited using Visitor pattern.
 *  
 * @author Atul M Dambalkar (atul@entrib.com)
 */
public class ExpressionTreeBuilder {

    public static Expression build(CommonTree tree) throws ValueParseException {
        if (tree == null) {
            throw new IllegalArgumentException("Tree object can't be null!");
        }
        switch(tree.getType()) {
            case MongoslateParser.UNARY_NOT:
                return new NegationExpression(build((CommonTree)tree.getChild(0)));

            case MongoslateParser.AND:
                return new LogicalExpression(LogicalExpression.LogicalOperatorEnum.AND,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.NOR:
                return new LogicalExpression(LogicalExpression.LogicalOperatorEnum.NOR,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.OR:
                return new LogicalExpression(LogicalExpression.LogicalOperatorEnum.OR,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.GT:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.GT,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.GTE:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.GTE,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.LT:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.LT,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.LTE:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.LTE,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.IN:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.IN,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.NIN:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.NIN,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.ALL:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.ALL,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.EQ:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.EQ,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.NEQ:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.NEQ,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.CONTAINS:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.CONTAINS,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.ENDS:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.ENDS,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.STARTS:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.STARTS,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.NEAR:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.NEAR,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.WITHIN:
                return new RelationalExpression(RelationalExpression.RelationalOperatorEnum.WITHIN,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.UNARY_EXISTS:
                return new ExistsExpression((IdentifierValue)build((CommonTree)tree.getChild(0)));

            case MongoslateParser.UNARY_MINUS:
                return new MinusExpression(build((CommonTree)tree.getChild(0)));

            case MongoslateParser.PLUS:
                return new ArithExpression(ArithExpression.ArithOperatorEnum.PLUS,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.MINUS:
                return new ArithExpression(ArithExpression.ArithOperatorEnum.MINUS,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.MULTI:
                return new MultiplyingExpression(MultiplyingExpression.MultiplyingOperatorEnum.MULTI,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.DIV:
                return new MultiplyingExpression(MultiplyingExpression.MultiplyingOperatorEnum.DIV,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.MOD:
                return new MultiplyingExpression(MultiplyingExpression.MultiplyingOperatorEnum.MOD,
                        build((CommonTree)tree.getChild(0)),
                        build((CommonTree)tree.getChild(1)));

            case MongoslateParser.INT:
                return new IntValue(tree.getText());
            
            case MongoslateParser.FLOAT:
                return new FloatValue(tree.getText());

            case MongoslateParser.STRING:
                int length = tree.getText().length();
                // trim the first and last double quote characters
                String text = tree.getText().substring(1, length - 1);
                return new StringValue(text);

            case MongoslateParser.IDENTIFIER:
                return new IdentifierValue(tree.getText());

            case MongoslateParser.AGGREGATION_FUNCTION:
                String function = tree.getChild(0).getText().toUpperCase();
                AggregationFunctionEnum aggregationFunctionEnum =
                        AggregationFunctionEnum.toAggregationFunctionEnum(function);
                String tag = tree.getChild(1).getText();
                if (tree.getChildCount() == 2) {
                    return new AggregationFunctionValue(aggregationFunctionEnum, tag);
                } else if (tree.getChildCount() == 3) {
                    String alias = tree.getChild(2).getText();
                    return new AggregationFunctionAliasValue(aggregationFunctionEnum, tag, alias);
                }
                break;

            case MongoslateParser.DATA_OPERATOR:
                String operator = tree.getChild(0).getText().toUpperCase();
                DataOperatorEnum dataOperatorEnum =
                        DataOperatorEnum.toDataOperatorEnum(operator);
                tag = tree.getChild(1).getText();
                if (tree.getChildCount() == 2) {
                    return new DataOperatorValue(dataOperatorEnum, tag);
                } else if (tree.getChildCount() == 3) {
                    String alias = tree.getChild(2).getText();
                    return new DataOperatorAliasValue(dataOperatorEnum, tag, alias);
                }
                break;

            case MongoslateParser.TIMESTAMP:
                return new DateValue(tree.getChild(0).getText());

            case MongoslateParser.COLLECTION:
                return new IdentifierValue(tree.getChild(0).getText());

            case MongoslateParser.ORDER_BY_FIELD:
                OrderByValue orderByValue = new OrderByValue();
                orderByValue.setValue(tree.getChild(0).getText());
                String order = tree.getChild(1).getText().toUpperCase(); 
                OrderByValue.OrderEnum orderEnum = OrderByValue.OrderEnum.toOrderEnum(order);
                orderByValue.setOrderEnum(orderEnum);
                return orderByValue;

            case MongoslateParser.TAG_LIST:
                TagListExpression tagListExpression = new TagListExpression();
                for (int i = 0; i < tree.getChildCount(); i++) {
                    Object value = build((CommonTree)tree.getChild(i));
                    if (value instanceof AggregationFunctionValue
                            || value instanceof AggregationFunctionAliasValue
                            || value instanceof DataOperatorValue
                            || value instanceof DataOperatorAliasValue) {
                        tagListExpression.setAggregation(true);
                    }
                    tagListExpression.add((TagValue)value);
                }
                return tagListExpression;

            case MongoslateParser.TAG_CLAUSE:
                return tree.getChildCount() == 1?
                        new TagValue(tree.getChild(0).getText()):
                        new TagAliasValue(tree.getChild(0).getText(), tree.getChild(1).getText());
                
            case MongoslateParser.VALUE_LIST:
                ValueListExpression valueListExpression = new ValueListExpression();
                for (int i = 0; i < tree.getChildCount(); i++) {
                    valueListExpression.add((Value)build((CommonTree)tree.getChild(i)));
                }
                return valueListExpression;

            case MongoslateParser.GROUP_BY:
                GroupByListExpression groupByListExpression = new GroupByListExpression();
                for (int i = 0; i < tree.getChildCount(); i++) {
                    groupByListExpression.add((IdentifierValue)build((CommonTree)tree.getChild(i)));
                }
                return groupByListExpression;

            case MongoslateParser.HAVING:
                return build((CommonTree)tree.getChild(0));

            case MongoslateParser.ORDER_BY:
                OrderByListExpression orderByListExpression = new OrderByListExpression();
                for (int i = 0; i < tree.getChildCount(); i++) {
                    orderByListExpression.add((OrderByValue)build((CommonTree)tree.getChild(i)));
                }
                return orderByListExpression;

            case MongoslateParser.LOGICAL_EXPRESSION:
                return build((CommonTree)tree.getChild(0));

            case MongoslateParser.LIMIT_EXPRESSION:
                LimitExpression limitExpression = new LimitExpression();
                if (tree.getChildCount() == 1) {
                    limitExpression.setStart(new IntValue(0));
                    limitExpression.setCount((IntValue)build((CommonTree)tree.getChild(0)));
                } else { // start and limit both specified
                    limitExpression.setStart((IntValue)build((CommonTree)tree.getChild(0)));
                    limitExpression.setCount((IntValue)build((CommonTree)tree.getChild(1)));                                                            
                }
                return limitExpression;

            case MongoslateParser.GEOCOORDINATE:
                GeoCoordinateValue geoCoordinateValue = new GeoCoordinateValue();
                GeoCoordinate geoCoordinate = new GeoCoordinate();
                Object latitudeValue = ((Value)build((CommonTree)tree.getChild(1))).getValueAsObject();
                Object longitudeValue = ((Value)build((CommonTree)tree.getChild(1))).getValueAsObject();
                geoCoordinate.setLatitude(latitudeValue instanceof Integer?
                                    ((Integer)(latitudeValue)).intValue():
                                    (Float)latitudeValue);
                geoCoordinate.setLongitude(longitudeValue instanceof Integer?
                                    ((Integer)(longitudeValue)).intValue():
                                    (Float)longitudeValue);    
                geoCoordinateValue.setValue(geoCoordinate);
                return geoCoordinateValue;
            
            case MongoslateParser.LOCATION:
                LocationValue locationValue = new LocationValue();
                LocationValue.LocationQuery query = new LocationValue.LocationQuery();
                query.geoCoordinateValue = (GeoCoordinateValue)build((CommonTree)tree.getChild(0));
                if (tree.getChildCount() == 2) {
                    Object value = ((Value)build((CommonTree)tree.getChild(1))).getValueAsObject();
                    query.distance = value instanceof Integer?
                            (Integer)value: (Float)value; 
                }
                locationValue.setValue(query);
                return locationValue;

            case MongoslateParser.BOX:
                BoxValue boxValue = new BoxValue();
                BoxValue.Box box = new BoxValue.Box();
                box.bottomLeft = ((GeoCoordinateValue)build((CommonTree)tree.getChild(0)));
                box.topRight = ((GeoCoordinateValue)build((CommonTree)tree.getChild(1)));
                boxValue.setValue(box);
                return boxValue;

            case MongoslateParser.POLYGON:
                PolygonValue polygonValue = new PolygonValue();
                List<GeoCoordinateValue> coordinates = new ArrayList<GeoCoordinateValue>();
                for (int i = 0; i < tree.getChildCount(); i++) {
                    coordinates.add((GeoCoordinateValue)build((CommonTree)tree.getChild(i)));
                }
                PolygonValue.Polygon polygon = new PolygonValue.Polygon();
                polygon.coordinates = coordinates;
                polygonValue.setValue(polygon);
                return polygonValue;

            case MongoslateParser.CIRCLE:
                CircleValue circleValue = new CircleValue();
                CircleValue.Circle circle = new CircleValue.Circle();
                circle.geoCoordinateValue = (GeoCoordinateValue)build((CommonTree)tree.getChild(0));
                Object value = ((Value)build((CommonTree)tree.getChild(1))).getValueAsObject();
                circle.radius = value instanceof Integer?
                        (Integer)value: (Float)value;
                circleValue.setValue(circle);
                return circleValue;
        }
        throw new IllegalArgumentException("Illegal token type found while parsing the tree");
    }

}
