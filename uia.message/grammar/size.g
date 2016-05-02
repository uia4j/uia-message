grammar Size;

options {
    language = Java;
}


@lexer::header {
package uia.message;

}

@parser::header {
package uia.message;

import java.util.Map;
import java.util.HashMap;
}

@members {
public Map<String, Object> blockValues = new HashMap<String, Object>();
}


/* This will be the entry point of our parser. */
eval returns [int value]
    :    e=additionExp 		{ $value = $e.value; }
    ;

/* Addition and subtraction have the lowest precedence. */
additionExp returns [int value]
    :    e1=multiplyExp 	{ $value = $e1.value; }
         ( '+' e2=multiplyExp 	{ $value += $e2.value; } 
         | '-' e3=multiplyExp 	{ $value -= $e3.value; }
         )* 
    ;

/* Multiplication and division have a higher precedence. */
multiplyExp returns [int value]
    :    e1=atomExp 		{ $value = $e1.value; }
         ( '*' e2=atomExp 	{ $value *= $e2.value; }
         | '/' e3=atomExp 	{ $value /= $e3.value; }
         )* 
    ;

/* An expression atom is the smallest part of an expression: a number. Or 
   when we encounter parenthesis, we're making a recursive call back to the
   rule 'additionExp'. As you can see, an 'atomExp' has the highest precedence. */
atomExp returns [int value]
    :    e1=Number		{ $value = Integer.parseInt($e1.getText()); }
    |    e2=Param		{ $value = Integer.parseInt("" + blockValues.get($e2.getText())); }
    |    '(' e3=additionExp ')' { $value = $e3.value; }
    ;

/* A number: can be an integer value, or a decimal value */
Number
    :    ('0'..'9')+ ('.' ('0'..'9')+)?
    ;


/* We're going to ignore all white space characters */
Param
    :    Character+
    ;
    
fragment
Character
    : 	'A'..'Z' 
    | 	'a'..'z' 
    |	'0'..'9'
    |	'_'
    ;


WS  
    :   (' ' | '\t' | '\r'| '\n') {$channel=HIDDEN;}
    ;