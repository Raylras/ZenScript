package stanhebben.zenscript.definitions;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.statements.Statement;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

import static stanhebben.zenscript.ZenTokener.*;

/**
 * @author Stanneke
 */
public class ParsedFunction {

    private final ZenPosition start;
    private final ZenPosition end;
    private final Token name;
    private final List<ParsedFunctionArgument> arguments;
    private final ZenType returnType;
    private final Statement[] statements;
    private final String signature;

    public ParsedFunction(ZenPosition start, ZenPosition end, Token name, List<ParsedFunctionArgument> arguments, ZenType returnType, Statement[] statements) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.arguments = arguments;
        this.returnType = returnType;
        this.statements = statements;

        StringBuilder sig = new StringBuilder();
        sig.append("(");
        for(ParsedFunctionArgument argument : arguments) {
            sig.append(argument.getType().getSignature());
        }
        sig.append(")");
        sig.append(returnType.getSignature());
        signature = sig.toString();
    }

    public static ParsedFunction parse(ZenTokener parser, IEnvironmentGlobal environment) {
        parser.next();
        Token tName = parser.required(ZenTokener.T_ID, "identifier expected");

        // function (argname [as type], argname [as type], ...) [as type] {
        // ...contents... }
        parser.required(T_BROPEN, "( expected");

        List<ParsedFunctionArgument> arguments = new ArrayList<>();
        if(parser.optional(T_BRCLOSE) == null) {
            Token argName = parser.required(T_ID, "identifier expected");
            ZenType type = ZenTypeAny.INSTANCE;
            if(parser.optional(T_AS) != null) {
                type = ZenType.read(parser, environment);
            }

            arguments.add(new ParsedFunctionArgument(argName, type));

            while(parser.optional(T_COMMA) != null) {
                Token argName2 = parser.required(T_ID, "identifier expected");
                ZenType type2 = ZenTypeAny.INSTANCE;
                if(parser.optional(T_AS) != null) {
                    type2 = ZenType.read(parser, environment);
                }

                arguments.add(new ParsedFunctionArgument(argName2, type2));
            }

            parser.required(T_BRCLOSE, ") expected");
        }

        ZenType type = ZenTypeAny.INSTANCE;
        if(parser.optional(T_AS) != null) {
            type = ZenType.read(parser, environment);
        }

        parser.required(T_AOPEN, "{ expected");

        Statement[] statements;
        Token tAClose = null;
        if(parser.optional(T_ACLOSE) != null) {
            statements = new Statement[0];
        } else {
            ArrayList<Statement> statementsAL = new ArrayList<>();

            while((tAClose = parser.optional(T_ACLOSE)) == null) {
                statementsAL.add(Statement.read(parser, environment, type));
            }
            statements = statementsAL.toArray(new Statement[statementsAL.size()]);
        }

        return new ParsedFunction(tName.getStart(), tAClose == null ? tName.getEnd() : tAClose.getEnd(), tName, arguments, type, statements);
    }

    public ZenPosition getStart() {
        return start;
    }

    public ZenPosition getEnd() {
        return end;
    }

    public Token getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public ZenType getReturnType() {
        return returnType;
    }

    public List<ParsedFunctionArgument> getArguments() {
        return arguments;
    }

    public ZenType[] getArgumentTypes() {
        ZenType[] result = new ZenType[arguments.size()];
        for(int i = 0; i < arguments.size(); i++) {
            result[i] = arguments.get(i).getType();
        }
        return result;
    }

    public Statement[] getStatements() {
        return statements;
    }
}
