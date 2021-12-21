package stanhebben.zenscript.definitions;

import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stanneke
 */
public class ParsedFunctionArgument {

    private final Token name;
    private final ZenType type;

    public ParsedFunctionArgument(Token name, ZenType type) {
        this.name = name;
        this.type = type;
    }

    public Token getName() {
        return name;
    }

    public ZenType getType() {
        return type;
    }
}
