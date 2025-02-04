package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.definitions.ParsedFunctionArgument;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

/**
 * @author Stanneke
 */

@Deprecated
public class ExpressionJavaMethodStatic extends Expression {
    
    private final JavaMethod method;
    private final ZenType type;
    
    public ExpressionJavaMethodStatic(ZenPosition position, JavaMethod method, IEnvironmentGlobal environment) {
        super(position);
        
        this.method = method;
        
        List<ParsedFunctionArgument> arguments = new ArrayList<>();
        for(int i = 0; i < method.getParameterTypes().length; i++) {
            arguments.add(new ParsedFunctionArgument(new Token("p" + i, getType().getNumberType(), null, null), environment.getType(method.getMethod().getGenericParameterTypes()[i])));
        }
        
        this.type = new ZenTypeFunction(environment.getType(method.getMethod().getGenericReturnType()), arguments);
    }
    
    @Override
    public ZenType getType() {
        return type;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        // TODO: compile
    }
}
