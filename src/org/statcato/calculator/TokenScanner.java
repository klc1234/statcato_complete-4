/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;
import org.statcato.spreadsheet.*;

/**
 * A scanner that read Tokens from a string representing a mathematical
 * expression.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 * @see Token
 */
public class TokenScanner {
    private String expr;
    public String error = "";
    private int index;  // position to be scanned next
    private int nextIndex;
    private double number;
    private int column;
    private int function;
    private double constant;
    private Spreadsheet spreadsheet;
    
    /**
     * Constructor.  A string of mathematical expression and the 
     * associated spreadsheet is given.
     * 
     * @param expr string of mathematical expression
     */
    public TokenScanner(String expr, Spreadsheet spreadsheet) {
        this.expr = expr.trim();
        index = 0;
        nextIndex = 0;
        this.spreadsheet = spreadsheet;
    }
    
    /**
     * Prints the string remaining to be scanned.
     */
    public void printRemaining() {
        System.out.println("* remaining: " + expr.substring(index) + "*");
    }
    
    /**
     * Consumes the number at the next scan position.
     * 
     * @return double
     */
    public double consumeNumber() {
        consumeToken();
        return number;
    }
    
    /**
     * Consumes the column at the next scan position.
     * 
     * @return column number
     */
    public int consumeColumn() {
        consumeToken();
        return column;
    }
    
    /**
     * Consumes the function at the next scan position.
     * 
     * @return function number
     */
    public int consumeFunction() {
        consumeToken();
        return function;
    }
    
    /**
     * Consumes the constant at the next scan position.
     * 
     * @return constant double
     */
    public double consumeConstant() {
        consumeToken();
        return constant;
    }
    
    /**
     * Reads and returns the next character to be processed without
     * consuming it.
     * 
     * @return 
     */
    private Character getNextCharacter() {
        if (index >= expr.length())
            return null;
        Character ch = expr.charAt(index);
        return ch;
    }
    
    /**
     * Consumes the next character by advancing the pointer to 
     * the current scan position.
     */
    private void consumeNextCharacter() {
        index++;
    }
    
    /**
     * Returns the type of the next token.  The token is not consumed.
     * 
     * @return token type
     */
    public int getToken() {
        int token = Token.UNDEFINED;
        
        if (getNextCharacter() == null)
            return Token.LAST;
        
        // skip all white spaces
        while (getNextCharacter() != null && Character.isWhitespace(getNextCharacter())) {
            consumeNextCharacter();
        }
        
        int oldIndex = index;
        Character ch = getNextCharacter();
        if (Character.isLetter(ch)) {   // letter
            column = getColumn();
            if (column != -1) {    // parse column                
                token = Token.COLUMN;
            }
            else {
                index = oldIndex;
                function = getFunction();
                if (function != -1)  { // parse function name
                    token = Token.FUNCTION;
                }
                else {
                    index = oldIndex;
                    constant = getConstant();
                    if (constant != MathFunctions.UNDEFINED)
                        token = Token.CONSTANT;
                    else
                        error("Invalid letter sequence");
                }
            }
        }
        else if (Character.isDigit(ch)) {
            number = getNumber();
            token = Token.NUMBER;
        }
        else {
            switch(ch) {
                case '.':
                    number = getDecimal();
                    if (Double.isNaN(number)) {
                        error("Invalid decimal value");
                    }
                    else {
                        token = Token.NUMBER;
                    }
                    break;
                case '+': 
                    token = Token.ADD;
                    consumeNextCharacter();
                    break;
                case '-':
                    token = Token.SUBTRACT;
                    consumeNextCharacter();
                    break;
                case '*':
                    token = Token.MULTIPLY;
                    consumeNextCharacter();
                    break;
                case '/':
                    token = Token.DIVIDE;
                    consumeNextCharacter();
                    break;
                case '^':
                    token = Token.POWER;
                    consumeNextCharacter();
                    break;
                case '(':
                    token = Token.LEFTPAREN;
                    consumeNextCharacter();
                    break;
                case ')':
                    token = Token.RIGHTPAREN;
                    consumeNextCharacter();
                    break;
                case '\"':
                    String str = getString();
                    if (str.equals(""))
                        error("unexpected column name");
                    else {
                       column = spreadsheet.getColumnNumber(str);
                       if (column == -1)
                           error("invaild column name");
                       else
                           token = Token.COLUMN;
                    }
                    break;
                default: error("Illegal character:" + ch);
                break;
            }
        }
        
        nextIndex = index;
        index = oldIndex;  // restore index position
        return token;
    }
    
    /**
     * Consumes the current token by advancing the index to the next position.
     * Should be used only after a @url(#getToken} call is called.  
     */
    public void consumeToken() {
        index = nextIndex;
    }
    
    /**
     * Attempts to parse a column label and returns the 
     * column number if successful.
     * 
     * @return column number if a valid column label is parsed
     * or -1 otherwise
     */
    public int getColumn() {
        Character ch = getNextCharacter();
        if (ch == 'c' || ch == 'C') {
            consumeNextCharacter(); // move pass 'c' or 'C'
            if (Character.isDigit(getNextCharacter())) {
                int col = getInteger();
                return spreadsheet.getColumnNumber("c" + col);
            }
        }
        return -1;
    }
    
    /**
     * Attempts to parse a function name and returns the 
     * function number if successful.
     * 
     * @return function number if a valid function name is parsed
     * or -1 otherwise
     */
    public int getFunction() {
        Character ch = getNextCharacter(); 
        switch (ch) {
            case 'a':
                if (expect("abs"))
                    return MathFunctions.ABS;
                if (expect("arccos"))
                    return MathFunctions.ACOS;
                if (expect("arcsin"))
                    return MathFunctions.ASIN;
                if (expect("arctan"))
                    return MathFunctions.ATAN;
            case 'c':
                if (expect("cos"))
                    return MathFunctions.COS;
                if (expect("ceil"))
                    return MathFunctions.CEIL;
            case 'e':
                if (expect("exp"))
                    return MathFunctions.EXP;
            case 'f':
                if (expect("floor"))
                    return MathFunctions.FLOOR;
                if (expect("factorial"))
                    return MathFunctions.FACTORIAL;
            case 'l':
                if (expect("log"))
                    return MathFunctions.LOG;
                if (expect("ln"))
                    return MathFunctions.LN;
            case 'r':
                if (expect("round"))
                    return MathFunctions.ROUND;
            case 's':  
                if (expect("sin"))
                    return MathFunctions.SIN;
                if (expect("sqrt"))
                    return MathFunctions.SQRT;
            case 't':
                if (expect("tan"))
                    return MathFunctions.TAN;
        }
        return -1;        
    }
    
     /**
     * Attempts to parse a constant name and returns the 
     * constant if successful.
     * 
     * @return a defined constant of double value if a valid constant name is parsed
     * or MathFunctions.UNDEFINED otherwise
     */
    public double getConstant() {
        Character ch = getNextCharacter(); 
        switch (ch) {
            case 'p':  
                if (expect("pi"))
                    return MathFunctions.PI;
        }
        return MathFunctions.UNDEFINED;        
    }
    
    public boolean expect(String name) {
        if (name == null || name.equals(""))
            return false;
        Character ch = getNextCharacter();
        int oldIndex = index;
        int i = 0;
        while (i < name.length() && ch != null) {
            if (ch.charValue() != name.charAt(i)) {
                index = oldIndex;   // move index back to previous position
                return false;
            }
            i++;
            consumeNextCharacter();
            ch = getNextCharacter();            
        }

        return true;
    }
    
    /** 
     * Returns the next string (delimited by single quotes)
     * 
     * @return string
     */
    public String getString() {
        consumeNextCharacter();
        String str = "";
        Character ch = getNextCharacter();
        
        while (ch != null && ch != '\"') {
            str += ch;
            consumeNextCharacter();
            ch = getNextCharacter();
        }
        if (ch == null)
            return "";
        if (ch == '\"') {
            consumeNextCharacter(); // consume '
            return str; // complete string
        }
            
        return "";  // incomplete
    }
    
    /**
     * Returns the double value at the current scan position.
     * 
     * @return double value
     */
    public double getNumber() {
        
        Character ch = getNextCharacter();
        String num = "";
        while (ch != null && Character.isDigit(ch)) {
            num += ch;
            consumeNextCharacter();
            ch = getNextCharacter();
        }
        ch = getNextCharacter();
        if (ch != null && ch.equals('.')) {
            num += ".";
            consumeNextCharacter(); // move pass .
            ch = getNextCharacter();
            while (ch != null && Character.isDigit(ch)) {
                num += ch;
                consumeNextCharacter();
                ch = getNextCharacter();
            }
        }
        
        return Double.parseDouble(num);
    }
    
    /**
     * Returns the integer value at the current scan position.
     * 
     * @return integer value
     */
    public int getInteger() {
        
        Character ch = getNextCharacter();
        String num = "";
        while (ch != null && Character.isDigit(ch)) {
            num += ch;
            consumeNextCharacter();
            ch = getNextCharacter();
        }

        return Integer.parseInt(num);
    }
    
    /**
     * Returns the double value that begins with '.' at the current scan position.
     * 
     * @return double value
     */
    public double getDecimal() {        
        consumeNextCharacter(); // move pass .
        Character ch = getNextCharacter();
        if (!Character.isDigit(ch))
            return Double.NaN;
        String num = "0.";
        while (ch != null && Character.isDigit(ch)) {
            num += ch;
            consumeNextCharacter();
            ch = getNextCharacter();
        }
        return Double.parseDouble(num);
    }
    
    /**
     * Adds the given string to the error string.
     * 
     * @param msg
     */
    public void error(String msg) {
        error += msg + "<br>";
    }
}
