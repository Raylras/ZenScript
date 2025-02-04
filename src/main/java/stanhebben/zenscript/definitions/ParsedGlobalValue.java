package stanhebben.zenscript.definitions;

import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import static stanhebben.zenscript.ZenTokener.*;

public class ParsedGlobalValue {
	private final ZenPosition position;
	private final Token name;
	private final ZenType type;
	private final ParsedExpression value;
	private final String owner;
	private final boolean global;
	
	ParsedGlobalValue(ZenPosition position, Token name, ZenType type, ParsedExpression value, String owner, boolean global){
		this.position = position;
		this.name = name;
		this.type = type;
		this.value = value;
		this.owner = owner;
        
        this.global = global;
    }

	public Token getName() {
		return name;
	}

	public ZenPosition getPosition() {
		return position;
	}
	
	public ZenType getType() {
		return type;
	}
	
	public ParsedExpression getValue() {
		return value;
	}
	
	public String getOwner() {
		return owner;
	}
	

	public static ParsedGlobalValue parse(ZenTokener parser, IEnvironmentGlobal environment, String owner, boolean global) {
		//Start ("GLOBAL")
		Token startingPoint = parser.next();
		
		//Name ("globalName", "test")
		Token name = parser.required(T_ID, "Global value requires a name!");
		
		//Type ("as type", "as IItemStack")
		ZenType type = ZenType.ANY;
		Token nee = parser.optional(T_AS);
		if(nee!=null) {
			type = ZenType.read(parser, environment);
		}
		
		//"="
		parser.required(T_ASSIGN, "Global values have to be initialized!");
		
		//"value, <minecraft:dirt>"
		ParsedExpression value = ParsedExpression.read(parser, environment);
		
		//";"
		parser.required(T_SEMICOLON, "; expected");
		
		//throw it together
		return new ParsedGlobalValue(startingPoint.getPosition(), name, type, value, owner, global);
	}
    
    public boolean isGlobal() {
        return global;
    }
}
