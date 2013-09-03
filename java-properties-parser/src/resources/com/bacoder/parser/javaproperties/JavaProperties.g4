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
  :  key (Space+ (Colon | Equals)? Space* | Colon Space* | Equals Space*) value
  ;

comment
  :  CommentChar valueChar*
  ;

lineBreak
  :  LineBreak
  ;

key
  :  keyCharFirst keyChar+
  ;

keyCharFirst
  :  AnyChar
  |  Backslash (Colon | Equals | Space | LineBreak Space*)?
  ;

keyChar
  :  AnyChar
  |  CommentChar
  |  Backslash (Colon | Equals | Space | LineBreak Space*)?
  ;

value
  :  valueChar*
  ;

valueChar
  :  AnyChar
  |  CommentChar
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
  :  ~[:\\= \t\f\r\n!#]
  ;

Unicode
  :  '\\u' [0-9]+
  ;

Backslash
  :  '\\'
  ;

Colon
  :  ':'
  ;

Equals
  :  '='
  ;

CommentChar
  :  [!#]
  ;

LineBreak
  :  '\r'? '\n'
  |  '\r'
  ;

Space
  :  [ \t\f]
  ;
