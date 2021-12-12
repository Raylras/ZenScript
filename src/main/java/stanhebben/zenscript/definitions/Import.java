package stanhebben.zenscript.definitions;

import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/**
 * @author Stanneke
 */
public class Import {

    private final ZenPosition start;
    private final ZenPosition end;
    private final List<Token> name;
    private final Token rename;

    public Import(ZenPosition position, List<Token> name, Token rename) {
        this.start = position;
        this.end = position;
        this.name = name;
        this.rename = rename;
    }

    public Import(ZenPosition start, ZenPosition end, List<Token> name, Token rename) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.rename = rename;
    }

    public ZenPosition getStart() {
        return start;
    }

    public ZenPosition getEnd() {
        return end;
    }

    public List<Token> getName() {
        return name;
    }

    public Token getRename() {
        return rename == null ? name.get(name.size() - 1) : rename;
    }

}
