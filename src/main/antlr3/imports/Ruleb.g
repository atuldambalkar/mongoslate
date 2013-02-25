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

// A single rule in a grammar that must be imported by the demo
// parser Tparser.g
// This is just here to show that import grammars are stored in their
// own imports directory: src/main/antlr3/imports
//
parser grammar Ruleb;

b
   : keyser SEMI!
   | expression SEMI!
   ;
