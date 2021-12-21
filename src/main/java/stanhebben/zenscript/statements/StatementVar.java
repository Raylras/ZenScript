package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class StatementVar extends Statement {

    private final Token name;
    private final ZenType type;
    private final ParsedExpression initializer;
    private final boolean isFinal;

    public StatementVar(ZenPosition position, Token name, ZenType type, ParsedExpression initializer, boolean isFinal) {
        super(position);

        this.name = name;
        this.type = type;
        this.initializer = initializer;
        this.isFinal = isFinal;
    }

    public Token getName() {
        return name;
    }

    public ZenType getType() {
        return type;
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void compile(IEnvironmentMethod environment) {
        environment.getOutput().position(getPosition());

        Expression cInitializer = initializer == null ? null : initializer.compile(environment, type).eval(environment);
        ZenType cType = type == null ? (cInitializer == null ? ZenTypeAny.INSTANCE : cInitializer.getType()) : type;
        SymbolLocal symbol = new SymbolLocal(cType, isFinal);

        environment.putValue(name.getValue(), symbol, getPosition());

        if(cInitializer != null) {
            cInitializer.compile(true, environment);
            environment.getOutput().store(symbol.getType().toASMType(), environment.getLocal(symbol));
        }
    }
}
