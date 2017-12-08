/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.statcato.calculator;

import java.util.Vector;
import org.statcato.spreadsheet.*;
import org.statcato.utils.HelperFunctions;

/**
 * A parser for mathematical expressions using the recurive descent
 * algorithm.  Acceptable expressions are defined by the following grammar:
 * <p>
 * E -> T { ("+" | "-") T}<br>
 * T -> F { ("*" | "/") F}<br>
 * F -> P {"^" F}<br>
 * P -> v | "(" E ")" | "-" T | func_name"(" E ")" | Cx | "'" L "'" <br>
 * L -> column_label <br>
 * <p>
 * where expressions enclosed by { } can appear zero or more times.
 * 
 * @author Margaret Yau
 * @version %I%, %G%
 * @since 1.0
 */
public class MathExpParserComplete {
    private TokenScanner scanner;
    private final boolean debug = false;
    private Spreadsheet spreadsheet;
    private boolean isRadian = true;
    
    public MathExpParserComplete(Spreadsheet sp) {
        spreadsheet = sp;
    }
    
    /**
     * Parses the given expression.
     * 
     * @param exp expression in string
     * @param isRadian boolean value of whether radians are used
     */
    public CompUnit parse(String exp, boolean isRadian) {
        scanner = new TokenScanner(exp, spreadsheet);
        this.isRadian = isRadian;
        print("\nparsing " + exp);
        CompUnit result = S();
        print("result = " + result);
        return result;
    }
    
    /**
     * Returns error message from the scanner.
     * 
     * @return error string
     */
    public String getError() {
        return scanner.error;
    }
    
    /**
     * Parses a statement. S->E
     * 
     * @return the value of a statement
     */
    public CompUnit S() {
        print("S:");
        CompUnit value = E();
        
        if (scanner.getToken() != Token.LAST) {
            print("token=" + scanner.getToken());
            scanner.error("Expression syntax error: unexpected " + 
                    Token.toString(scanner.getToken()) + " found");
            return null;
        }
        print("last token = " + Token.toString(scanner.getToken()));
        print("S = " + value);
        return value;
    }
    
    /**
     * Parses an expression.  E -> T { ("+" | "-") T}
     * 
     * @return value of an expression
     */
    private CompUnit E() {
        print("  E:");
        CompUnit left = T();
        if (left == null)
            return null;
        print("  Left = " + left);
        CompUnit right;
        int op = scanner.getToken();
        while (op == Token.ADD || op == Token.SUBTRACT) {
            scanner.consumeToken(); // move pass the operator
            right = T();
            if (right == null)
                return null;
            print("  op = " + op + ", right = " + right);
            switch (op) { 
                case Token.ADD:
                    left = MathOperations.add(left, right);
                    break;
                case Token.SUBTRACT:
                    left = MathOperations.subtract(left, right);
                    break;
            }
            op = scanner.getToken();
        }
        print("  E = " + left);
        return left;
    }
    
    /**
     * Parses a term T -> F { ("*" | "/") F}
     * 
     * @return value of a term
     */
    private CompUnit T() {
        print("    T:");
        CompUnit left = F();
        if (left == null)
            return null;
        print("    left = " + left);
        CompUnit right;
        int op = scanner.getToken();
        while (op == Token.MULTIPLY || op == Token.DIVIDE) {
            scanner.consumeToken(); // move pass the operator
            right = F();
            if (right == null)
                return null;
            print("    op = " + op + ", right = " + right);
            switch (op) {
                case Token.MULTIPLY:
                    left = MathOperations.multiply(left, right);
                    break;
                case Token.DIVIDE:
                    left = MathOperations.divide(left, right);
                    break;
            }            
            op = scanner.getToken();
        }
        print("    T = " + left);
        return left;
    }
    
    /**
     * Parses a powered term  F -> P {"^" F}
     * 
     * @return value of a powered term
     */
    private CompUnit F() {
        print("      F:");
        CompUnit left = P();
        if (left == null)
            return null;
        print("      left = " + left);
        while (scanner.getToken() == Token.POWER) {
            scanner.consumeToken(); // move pass ^
            CompUnit right = F();
            print("      right = " + right);
            if (right == null)
                return null;
            left = MathOperations.power(left, right);            
        }
        print("      F = " + left);
        return left;
    }
    
    /**
     * Parses a terminal unit P -> v | "(" E ")" | "-" T | func_name"(" E ") | Cx | "'" column label "'"
     * 
     * @return value of a terminal
     */
    private CompUnit P() {
        print("        P:");
        CompUnit unit = null;
        double num;
        int token = scanner.getToken();
        print("token type = " + Token.toString(token));
        switch (token) {
            case Token.NUMBER:
                num = scanner.consumeNumber();
                unit = new CompUnit(Token.NUMBER, num);
                print("        P is number");
                break;
            case Token.COLUMN:
                int col = scanner.consumeColumn();
                // get column
                Vector<Cell> column = spreadsheet.getColumn(col);
                
                Vector<Double> value = HelperFunctions.ConvertInputVectorToDoubles(column);   
                if (value != null) {
                    value = HelperFunctions.removeEndingNullValues(value);
                    unit = new CompUnit(Token.COLUMN, value);
                    print("        P is column");
                }
                else
                    scanner.error("invalid column input");
                break;
            case Token.LEFTPAREN:
                scanner.consumeToken();
                print("        P is (E)");
                unit = E();
                if (scanner.getToken() != Token.RIGHTPAREN) {
                    scanner.error("        Missing )");
                }
                else {
                    scanner.consumeToken(); // move pass )
                }
                break;
            case Token.SUBTRACT:
                scanner.consumeToken();
                print("        P is -T");
                unit = MathOperations.negate(T());
                break;
            case Token.CONSTANT:
                num = scanner.consumeConstant();
                unit = new CompUnit(Token.NUMBER, num);
                print("        P is a constant");
                break;
            case Token.FUNCTION:
                int function = scanner.consumeFunction();
                if (scanner.getToken() != Token.LEFTPAREN) {
                    scanner.error("        function name must be followed by (");
                }
                else {
                    scanner.consumeToken(); // move pass (
                    CompUnit argument = E(); // function argument
                    if (argument == null)
                        break;
                    print("        P is f(E)");
                    if (scanner.getToken() != Token.RIGHTPAREN) {
                        scanner.error("        missing ) after function argument");
                    }
                    else {
                        // perform function call                        
                        switch (function) {
                            case MathFunctions.SIN:  
                                print("sin of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.SIN, 
                                        isRadian));
                                break;
                            case MathFunctions.COS:  
                                print("cos of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.COS,
                                        isRadian));
                                break;
                            case MathFunctions.EXP:
                                print("exp of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.EXP,
                                        isRadian));
                                break;
                            case MathFunctions.ABS:
                                print("abs of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.ABS,
                                        isRadian));
                                break;
                            case MathFunctions.TAN:
                                print("tan of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.TAN,
                                        isRadian));
                                break;
                            case MathFunctions.ACOS:
                                print("acos of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.ACOS,
                                        isRadian));
                                break;
                            case MathFunctions.ASIN:
                                print("asin of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.ASIN,
                                        isRadian));
                                break;
                            case MathFunctions.ATAN:
                                print("atan of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.ATAN,
                                        isRadian));
                                break;
                            case MathFunctions.CEIL:
                                print("ceil of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.CEIL,
                                        isRadian));
                                break;
                            case MathFunctions.FLOOR:
                                print("floor of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.FLOOR,
                                        isRadian));
                                break;
                            case MathFunctions.LOG:
                                print("log of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.LOG,
                                        isRadian));
                                break;
                            case MathFunctions.ROUND:
                                print("round of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.ROUND,
                                        isRadian));
                                break;
                            case MathFunctions.SQRT:
                                print("sqrt of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.SQRT,
                                        isRadian));
                                break;
                            case MathFunctions.LN:
                                print("ln of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.LN,
                                        isRadian));
                                break;
                            case MathFunctions.FACTORIAL:
                                print("factorial of " + argument);
                                unit = MathOperations.functionCall(argument,
                                        new MathFunction(MathFunctions.FACTORIAL,
                                        isRadian));
                                break;
                        }                           
                         
                        scanner.consumeToken(); // move pass )
                    }
                }
                break;
            default:
                break;
        }
        print("        P = " + unit);
        if (unit == null)
            scanner.error("Unexpected token");
        return unit;
    }

    
    /** 
     * Print a debug message.
     * 
     * @param msg debug string
     */
    private void print(String msg) {
        if (debug)
            System.out.println(msg);
    }
}
