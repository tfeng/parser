grammar JavaProperties;

properties
  :  line* EOF
  ;

line
  :  Space* keyValue
  |  Space* comment eol
  |  Space* lineBreak
  ;

keyValue
  :  key (Space Space* (Colon | Equals)? Space* | Colon Space* | Equals Space*) value eol
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
  :  AlphaNum 
  |  Backslash (Colon | Equals)
  ;

value
  : valueChar+
  ;

valueChar
  :  AlphaNum 
  |  Space 
  |  Backslash LineBreak
  |  Equals
  |  Colon
  ;

eol
  :  LineBreak
  |  EOF
  ;

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

AlphaNum
  :  'a'..'z'
  |  'A'..'Z'
  |  '0'..'9'
  ;
