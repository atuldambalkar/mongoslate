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

tree grammar TTree;

options {

    // Default but name it anyway
    //
    language   = Java;

    // Use the vocab from the parser (not the lexer)
    // The ANTLR Maven plugin knows how to work out the
    // relationships between the .g files and it will build
    // the tree parser after the parser. It will also rebuild
    // the tree parser if the parser is rebuilt.
    //
    tokenVocab = MongoslateParser;

    // Use ANTLR built-in CommonToken for tree nodes
    //
    ASTLabelType = CommonToken;
}

// What package should the generated source exist in?
//
@header {

    package com.entrib.mongoslate;
}

a : ^(SCRIPT stuff+)
  | SCRIPT
  ;

stuff
  : keyser
  | expression
  ;

keyser
  : ^(KEYSER SOZE)
    { System.out.println("Found Keyser Soze!!"); }
  ;

expression
  : ^(ADD expression expression)
  | ID
  | INT
  | STRING
  ;

