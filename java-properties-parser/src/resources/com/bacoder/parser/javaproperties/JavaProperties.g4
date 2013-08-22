grammar JavaProperties;

properties
  :  line* EOF
  ;

line
  :  Space* keyValue eol
  |  Space* comment eol
  |  Space* lineBreak
  ;

keyValue
  :  key (Space Space* (Colon | Equals)? Space* | Colon Space* | Equals Space*) value
  ;

comment
  : Comment
  ;

lineBreak
  : LineBreak
  ;

key
  :  keyChar+
  ;

keyChar
  :  AnyChar
  |  Backslash (Colon | Equals | Space | LineBreak)?
  ;

value
  : valueChar*
  ;

valueChar
  :  AnyChar 
  |  Unicode
  |  Backslash (Space | LineBreak)?
  |  Colon
  |  Equals
  |  Space
  ;

eol
  :  LineBreak
  |  EOF
  ;

AnyChar
  :  ~(':' | '\\' | '=' | ' ' | '\t' | '\f' | '\r' | '\n')
  ;

Unicode : '\\u' [0-9]+;

Backslash : '\\';
Colon     : ':';
Equals    : '=';

Comment
  :  ('!' | '#') ~('\r' | '\n')*
  ;

LineBreak
  :  '\r'? '\n'
  |  '\r'
  ;

Space
  :  ' ' 
  |  '\t' 
  |  '\f'
  ;
