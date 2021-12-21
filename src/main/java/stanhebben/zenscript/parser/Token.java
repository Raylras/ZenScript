package stanhebben.zenscript.parser;

import stanhebben.zenscript.util.ZenPosition;

/**
 * Represents a token in a token stream.
 *
 * @author Stan Hebben
 */
public class Token implements Comparable<Token> {
    
    private final ZenPosition start;
    private final ZenPosition end;
    private final String value;
    private final int type;
    
    /**
     * Constructs a new token.
     *
     * @param value    token string value
     * @param type     token type
     * @param start    start position of token
     * @param end      end position of token
     */
    public Token(String value, int type, ZenPosition start, ZenPosition end) {
        this.value = value;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    
    public ZenPosition getPosition() {
        return start;
    }

    public ZenPosition getStart() {
        return start;
    }

    public ZenPosition getEnd() {
        return end;
    }

    public int getLength() {
        return (end.getLine() - start.getLine()) + (end.getLineOffset() - start.getLineOffset());
    }

    /**
     * Returns the string value of this token.
     *
     * @return token value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Returns the token type of this token.
     *
     * @return token type
     */
    public int getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return start.getLine() + ":" + start.getLineOffset() + " (" + type + ") " + value;
    }

    @Override
    public int compareTo(Token o) {
        int t1Line = this.getStart().getLine();
        int t1Column = this.getStart().getLineOffset();
        int t2Line = o.getStart().getLine();
        int t2Column = o.getStart().getLineOffset();

        if (t1Line != t2Line) {
            return t1Line - t2Line;
        } else {
            return t1Column - t2Column;
        }
    }

}
