The ANTLR lexer for Mongoslate, to be used with ANTLR grammar.

# Introduction #

Mongoslate ANTLR Lexer with token definitions


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
// The lexical analyzer for Mongoslate
//
// Author: Atul Dambalkar (atul@entrib.com)

lexer grammar MongoslateLexer;

options {
    language=Java;  // Default

    // Tell ANTLR to make the generated lexer class extend the the named class,
    // which is where any supporting code and variables will be placed.
    superClass = AbstractTLexer;
}

// What package should the generated source exist in?
@header {
    package com.entrib.mongoslate;
}

IN
    : 'in'
    ;

NIN
    : 'nin'
    ;

CONTAINS
    : 'contains'
    ;

STARTS
    : 'starts'
    ;

ENDS
    : 'ends'
    ;

EXISTS
    : 'exists'
    ;

NEAR
    : 'near'
    ;

WITHIN
    : 'within'
    ;

REGEX
    : 'regex'
    ;

ALL
    : 'all'
    ;

NOR
    : 'nor'
    ;

TIMESTAMP
    : 'timestamp'
    ;

LOCATION
    : 'location'
    ;

BOX
    : 'box'
    ;
    
POLYGON
    : 'polygon'
    ;

CIRCLE
    : 'circle'
    ;

SELECT
    : 'select'
    ;
    
FROM
    : 'from'
    ;

WHERE
    : 'where'
    ;
    
ORDER
    : 'order'
    ;

BY
    : 'by'
    ;

ASC
    : 'asc'
    ;

DESC
    : 'desc'
    ;

DISTINCT
    : 'distinct'
    ;

SUM
    : 'sum'
    ;

COUNT
    : 'count'
    ;

MAX
    : 'max'
    ;

MIN
    : 'min'
    ;

AVG
    : 'avg'
    ;

YEAR
    : 'year'
    ;

MONTH
    : 'month'
    ;

WEEK
    : 'week'
    ;

DAY_OF_WEEK
    : 'day_of_week'
    ;

DAY_OF_MONTH
    : 'day_of_month'
    ;

DAY_OF_YEAR
    : 'day_of_year'
    ;

HOUR
    : 'hour'
    ;

MINUTE
    : 'minute'
    ;

SECOND
    : 'second'
    ;

TO_UPPER
    : 'to_upper'
    ;

TO_LOWER
    : 'to_lower'
    ;

UNWIND
    : 'unwind'
    ;

GROUP
    : 'group'
    ;
    
LIMIT
    : 'limit'
    ;

AS
    : 'as'
    ;

HAVING
    : 'having'
    ;

FORMATTED_DATE
    : INT '-' INT '-' INT (('-' INT '-' INT '-' INT) | ('-' INT '-' INT '-' INT '-' INT))?  
    ;

QUOTE
    : '\''
    ;

IDENTIFIER
    :	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT
    :   '0'
    |   '1'..'9' ('0'..'9')*
    ;

FLOAT
    :	('0' .. '9')+ '.' ('0' .. '9')*
    |   '.' ( '0' .. '9' )+
    ;

//COMMENT
//    :   '//' ~('\n'|'\r')* '\r'? '\n' {skip();}
//    |   '/*' ( options {greedy=false;} : . )* '*/' {skip();}
//    ;

WS
    :   ( ' '
    | '\t'
    | '\r'
    | '\n'
       ) {skip();}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'*'|'+'|'^'|'$'|'?'|'.'|'\\')
    ;

COMMA
    : ','
    ;

fragment
TAGOP
    : '$'
    ;

fragment
VALUEOP
    : '#'
    ;

fragment
DOTOP
    : '.'
    ;

LP
    : '('
    ;

RP
    : ')'
    ;

LB
    : '{'
    ;

RB
    : '}'
    ;

LSB
    : '['
    ;

RSB
    : ']'
    ;

EQ
    : '=='
    ;

NEQ
    : '!='
    ;

LT
    : '<'
    ;

LTE
    : '<='
    ;

GT
    : '>'
    ;

GTE
    : '>='
    ;

AND
    : '&&'
    ;

OR
    : '||'
    ;


PLUS
    : '+'
    ;

MINUS
    : '-'
    ;

MULTI
    : '*'
    ;

DIV
    : '/'
    ;

MOD
    : '%'
    ;

NOT
    : '!'
    ;


```