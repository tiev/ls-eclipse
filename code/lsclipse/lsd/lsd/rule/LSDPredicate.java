/* 
*    Logical Structural Diff (LSDiff)  
*    Copyright (C) <2015>  <Dr. Miryung Kim miryung@cs.ucla.edu>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package lsd.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import lsclipse.RestrictionOption;


public class LSDPredicate {
	public static int DELETED = 1; 
	public static int ADDED = 2;
	public static int BEFORE = 3;
	public static int AFTER = 4; 
	public static int MODIFIED = 5; 
	public static int DELETED_P = 6; 
	public static int ADDED_P = 7;
	public static int MODIFIED_P = 8; 
	public static int UNDEFINED = 9;
	
	public static int PACKAGELEVEL = 0;
	public static int CLASSLEVEL = 1;
	public static int METHODLEVEL = 2;
	
	private static final String[] conclusionConstraints = { "modified_method",
			"modified_type", "added_accesses", "deleted_accesses",
			"added_calls", "deleted_calls", "added_return", "deleted_return",
			"added_fieldoftype", "deleted_fieldoftype" };

	public boolean conclusionConstraints() {
		if (RestrictionOption.OPTION_LimitConsequentPredicate) {
			for (String c : conclusionConstraints) {
				if (this.predName.equals(c)) {
					return true;
				}
			}
			return false; 
		}
		return true; 
	}
	
	private static HashMap<String, LSDPredicate> allowedPredicate;
	
	static { 
		newPredicates();
	}
	
	private static void newPredicates(){
		allowedPredicate = new HashMap<String, LSDPredicate>();
		
		addAllowedPredicate("package", "p");
		//addAllowedPredicate("type", "t"); 
		addAllowedPredicate("type", "tap" ); // a is the name of type
		//addAllowedPredicate("field", "f");
		addAllowedPredicate("field", "fbt"); // b is the name of field 
		//addAllowedPredicate("method", "m");
		addAllowedPredicate("method", "mct"); // c is the name of method
		
		addAllowedPredicate("return", "mt");
		addAllowedPredicate("subtype", "tt"); //super and sub 
		
		addAllowedPredicate("accesses", "fm");
		addAllowedPredicate("calls", "mm");
		//addAllowedPredicate("inheritedfield", "ftt");
		addAllowedPredicate("inheritedfield", "btt"); // super sub
		//addAllowedPredicate("inheritedmethod", "mtt");
		addAllowedPredicate("inheritedmethod", "ctt");//super sub
		addAllowedPredicate("fieldoftype", "ft");
		//addAllowedPredicate("methodintype", "mt");
		//addAllowedPredicate("fieldintype", "ft");
		addAllowedPredicate("typeintype", "tt");
		//addAllowedPredicate("typeinpackage", "tp");
		
		// MK 
		addAllowedPredicate("extends", "tt"); //super and sub 
		addAllowedPredicate("implements", "tt"); //super and sub 

		
		addAllowedPredicate("before_package", "p");
		addAllowedPredicate("before_type", "tap" ); // a is the name of type
		addAllowedPredicate("before_field", "fbt"); // b is the name of field 
		addAllowedPredicate("before_method", "mct"); // c is the name of method
		
		addAllowedPredicate("before_return", "mt");
		addAllowedPredicate("before_subtype", "tt"); //super and sub 
		addAllowedPredicate("before_accesses", "fm");
		addAllowedPredicate("before_calls", "mm");
		
		addAllowedPredicate("before_inheritedfield", "btt");
		addAllowedPredicate("before_inheritedmethod", "ctt");
		addAllowedPredicate("before_fieldoftype", "ft");
		addAllowedPredicate("before_typeintype", "tt");

		// MK 
		addAllowedPredicate("before_extends", "tt"); //super and sub 
		addAllowedPredicate("before_implements", "tt"); //super and sub 

		addAllowedPredicate("after_package", "p");
		addAllowedPredicate("after_type", "tap" ); // a is the name of type
		addAllowedPredicate("after_field", "fbt"); // b is the name of field 
		addAllowedPredicate("after_method", "mct"); // c is the name of method
		
		addAllowedPredicate("after_return", "mt");
		addAllowedPredicate("after_subtype", "tt"); //super and sub 
		addAllowedPredicate("after_accesses", "fm");
		addAllowedPredicate("after_calls", "mm");
		
		addAllowedPredicate("after_inheritedfield", "btt");
		addAllowedPredicate("after_inheritedmethod", "ctt");
		addAllowedPredicate("after_fieldoftype", "ft");
		addAllowedPredicate("after_typeintype", "tt");
		
		// MK 
		addAllowedPredicate("after_extends", "tt"); //super and sub 
		addAllowedPredicate("after_implements", "tt"); //super and sub 

		addAllowedPredicate("deleted_package", "p");
		addAllowedPredicate("deleted_type", "tap"); // a is the name of type
		addAllowedPredicate("deleted_field", "fbt"); // b is the name of field
		addAllowedPredicate("deleted_method", "mct"); // c is the name of
														// method

		addAllowedPredicate("deleted_return", "mt");
		addAllowedPredicate("deleted_subtype", "tt"); // super and sub
		addAllowedPredicate("deleted_accesses", "fm");
		addAllowedPredicate("deleted_calls", "mm");
		addAllowedPredicate("deleted_inheritedfield", "btt");
		addAllowedPredicate("deleted_inheritedmethod", "ctt");
		addAllowedPredicate("deleted_fieldoftype", "ft");
		addAllowedPredicate("deleted_typeintype", "tt");
		// MK 
		addAllowedPredicate("deleted_extends", "tt"); //super and sub 
		addAllowedPredicate("deleted_implements", "tt"); //super and sub 
		
		
		addAllowedPredicate("added_package", "p");
		addAllowedPredicate("added_type", "tap" ); // a is the name of type
		addAllowedPredicate("added_field", "fbt"); // b is the name of field 
		addAllowedPredicate("added_method", "mct"); // c is the name of method
		
		addAllowedPredicate("added_return", "mt");
		addAllowedPredicate("added_subtype", "tt"); //super and sub 
		addAllowedPredicate("added_accesses", "fm");
		addAllowedPredicate("added_calls", "mm");
		addAllowedPredicate("added_inheritedfield", "btt");
		addAllowedPredicate("added_inheritedmethod", "ctt");
		addAllowedPredicate("added_fieldoftype", "ft");
		addAllowedPredicate("added_typeintype", "tt");
		
//		 MK 
		addAllowedPredicate("added_extends", "tt"); //super and sub 
		addAllowedPredicate("added_implements", "tt"); //super and sub 
		
		addAllowedPredicate("modified_package", "p");
		addAllowedPredicate("modified_type", "tap");
		addAllowedPredicate("modified_method", "mct"); 
		addAllowedPredicate("modified_field", "fbt");
		
	}
	
	
	private int kind = UNDEFINED;
	private final String predName;
	private final char[] types;
	private int level; 
	public int arity () {
		return this.types.length;
	}
	private LSDPredicate(String pred, char[] types) throws LSDInvalidTypeException{
		this.types = types;
		
			
		// 
		for (int i=0; i< types.length; i++) {
			if (!LSDVariable.isValidType(types[i]))
				throw new LSDInvalidTypeException();
		}
		this.predName = pred;
		if (pred.indexOf("before_")!=-1) {
			this.kind = BEFORE;
		}else if (pred.indexOf("after_")!=-1) {
			this.kind = AFTER;
		}else if (pred.indexOf("deleted_")!=-1) {
			this.kind = DELETED;
		}else if (pred.indexOf("added_")!=-1) {
			this.kind = ADDED;
		}else if (pred.indexOf("modified_")!=-1) {
			this.kind = MODIFIED;
		}else { 
			this.kind = UNDEFINED;
		}
		level = setLevel(pred,types);
		// check 
	}
	private int setLevel(String pred, char[] types) {
		if(types[0] == 'p')
			return PACKAGELEVEL;
		if(types[0] == 't')
			return CLASSLEVEL;
		return METHODLEVEL;
		
	}
	
	public boolean isElement() { return arity() == 1; }
	public String getName() { return predName; }
	public String getDisplayName() { return (is_pPredicate() ? predName.replaceFirst("_p_", "_") : predName); }
	public char[] getTypes() {return types;}
	public String toString() {
		String typeString="";
		for (int i=0; i< types.length; i++) {
			if (i>=1) { 
				typeString += ",";
			}
			typeString += types[i];
		}
		return predName + "(" + typeString + ")";
	}
	
	private static void addAllowedPredicate(String predName, String types) {
		try {
			allowedPredicate.put(predName, new LSDPredicate(predName, types.toCharArray()));
//			if (allowedPredicate.get(predName).isConclusionPredicate())
//				addAllowedPredicate(predName.replaceFirst("_", "_p_"), types);
		}	catch (LSDInvalidTypeException e)
		{
			e.printStackTrace();
		}
	}
	
	public static LSDPredicate getPredicate(String predName){
		 return allowedPredicate.get(predName);
	}
	
	public LSDPredicate getPrefixPredicate(String prefix) {
		return getPredicate(prefix + "_" + getSuffix());
	}
	
	public static List<LSDPredicate> getPredicates()
	{
		List<LSDPredicate> predicates = new ArrayList<LSDPredicate>();
		for (LSDPredicate predicate : allowedPredicate.values()) {
			if (predicate.kind != UNDEFINED)
				predicates.add(predicate);
		}
		return predicates;
		//return new ArrayList<LSDPredicate>(allowedPredicate.values());
	}
	
	public boolean isKBBeforePredicate() {
		return kind == DELETED || kind == BEFORE || kind == DELETED_P;
	}
	public boolean isKBAfterPredicate() {
		return kind == ADDED || kind == AFTER || kind == ADDED_P;
	}
	public boolean isConclusionPredicate() {
		return kind == DELETED || kind == ADDED || kind == MODIFIED;
	}
	public boolean is2KBPredicate() {
		return kind == BEFORE || kind == AFTER || is_pPredicate();
	}
	public boolean isAntecedentPredicate() {
		return kind == BEFORE; 
	} 
	
	public boolean is_pPredicate() {
		return kind == ADDED_P || kind == DELETED_P || kind == MODIFIED_P;
	}
	
	 
//	public static List<LSDPredicate> getAntecedentPredicates()
//	{
//		List<LSDPredicate> twoKBPredicates = new ArrayList<LSDPredicate>();
//		for (LSDPredicate predicate : allowedPredicate.values()) {
//			if (predicate.isAntecedentPredicate())
//				twoKBPredicates.add(predicate);
//		}
	// return twoKBPredicates;
	// }

	public boolean allowedInSameRule(LSDPredicate conclusion,
			LSDPredicate antecedant) {
		if (antecedant != null) {
			boolean x = (this.kind == antecedant.kind);
			return x;
		}

		// 1. C: added_calls must have an A:
		// deleted_accesses or deleted_calls or deleted_fieldoftype or
		// inherited_method
		// 2. C: deleted_accesses or added_accesses must have an A:
		// past_accesses or past_calls.
		// 3. C: added_return or deleted_return must have deleted_return
		// or added_return.
		// 4. C: modified_method must have A: inherited_method or
		// before_accesses, before_calls.
		// 5. C: modified_type must have A: subtype.
		if (RestrictionOption.OPTION_PreferDependencePredicates) {
			if (conclusion.getName().indexOf("added_calls") != -1) {
				return (this.getName().indexOf("deleted_accesses") != 1
						|| this.getName().indexOf("deleted_calls") != -1
						|| this.getName().indexOf("deleted_fieldoftype") != -1 || this
						.getName().indexOf("deleted_inheritedmethod") != -1);
			}
			if (conclusion.getName().indexOf("deleted_accesses") != -1
					|| conclusion.getName().indexOf("added_accesses") != -1) {
				return (this.getName().indexOf("before_calls") != -1);
			}
			if (conclusion.getName().indexOf("added_return") != -1) {
				return (this.getName().indexOf("deleted_return") != -1);
			}
			if (conclusion.getName().indexOf("deleted_return") != -1) {
				return (this.getName().indexOf("add_return") != -1);
			}
			if (conclusion.getName().indexOf("_method") != -1) {
				return (this.getName().indexOf("before_calls") != -1
						|| this.getName().indexOf("before_accesses") != -1 || this
						.getName().indexOf("before_inheritedmethod") != -1);
			}
			if (conclusion.getName().indexOf("_type") != -1) {
				return (this.getName().indexOf("before_subtype") != -1
						|| this.getName().indexOf("before_extends") != -1 || this
						.getName().indexOf("before_implements") != -1);
			}
			if (conclusion.getName().indexOf("_inherited") != -1) {
				return (this.getName().indexOf("_subtype") != -1
						|| this.getName().indexOf("_extends") != -1 || this
						.getName().indexOf("_implements") != -1);
			}
			
		}
		
		
		// MK Change  022310
		if (conclusion.kind == DELETED) {
			return (this.kind == BEFORE);
		}
		if (RestrictionOption.OPTION_AllowAfterInAntecedent == true) {
			if (conclusion.kind == ADDED) {
				return (this.kind == DELETED || this.kind == BEFORE || this.kind == AFTER);
			}
		} else {
			if (conclusion.kind == ADDED) {
				return (this.kind == DELETED || this.kind == BEFORE);
			}
		}
		if (conclusion.kind ==MODIFIED) { 
			return (this.kind == BEFORE );
		}
		return false;
	}

	public boolean typeChecks (ArrayList<LSDBinding> bindings) {
		if (bindings.size() != types.length) return false;
		for ( int i=0; i< bindings.size(); i++) {
			char type = types[i];
			LSDBinding binding = bindings.get(i);
			if (!binding.typeChecks(type)) return false;
		}
		return true;
	}
	
	public boolean typeMatches(Collection<Character> types) {
		for (char type: this.types)
			if (types.contains(type))
				return true;
		return false;
	}
	
	public boolean equalsIgnoringPrimes(Object other)
	{
		if (!(other instanceof LSDPredicate))
			return false;
		return this.getDisplayName().equals(((LSDPredicate)other).getDisplayName()); 
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof LSDPredicate))
			return false;
		return this.toString().equals(((LSDPredicate)other).toString());
	}
	
	
	/**
	 * @param args
	 */   
	public static void main(String[] args) {
		LSDPredicate foo = LSDPredicate.getPredicate("deleted_field");
		assert foo.getName() == "deleted_field";
		assert foo.arity() == 1;
		assert null == LSDPredicate.getPredicate("added_bogusMethod");
		foo = LSDPredicate.getPredicate("added_inheritedmethod");
		assert foo.getName() == "added_inheritedmethod";
		assert foo.arity() == 3;
		ArrayList<LSDBinding> bindings = new ArrayList<LSDBinding>();
		LSDBinding binding = new LSDBinding(new LSDVariable("a", 'm'));
		bindings.add(binding);
		binding = new LSDBinding(new LSDVariable("c", 't'));
		bindings.add(binding);
		assert false == foo.typeChecks(bindings);
		binding = new LSDBinding(new LSDVariable("b", 't'));
		bindings.add(binding);
		assert true == foo.typeChecks(bindings);
		bindings = new ArrayList<LSDBinding>();
		binding = new LSDBinding(new LSDVariable("a", 'f'));
		bindings.add(binding);
		binding = new LSDBinding(new LSDVariable("c", 't'));
		bindings.add(binding);
		binding = new LSDBinding(new LSDVariable("b", 't'));
		bindings.add(binding);
		assert false == foo.typeChecks(bindings);
		assert foo.toString().equals("added_inheritedmethod(m,t,t)");
		System.out.println("Predicate tests succeeded.");
}
	

	// HELPER FUNCTIONS for rule style generator 
	
	public static ArrayList<LSDPredicate> getPredicates(int kind, int arity) { 
		Collection<LSDPredicate> predicates = allowedPredicate.values();
		ArrayList<LSDPredicate> results = new ArrayList<LSDPredicate>();
		for (LSDPredicate pred : predicates) { 
			if (pred.kind==kind && pred.arity()==arity) { 
				results.add(pred);
			}
		}
		if (results.size()==0) return null; 
		return results;
	}	
	
	public static ArrayList<LSDPredicate> getPredicates(int kind, char type) { 
		Collection<LSDPredicate> predicates = allowedPredicate.values();
		ArrayList<LSDPredicate> results = new ArrayList<LSDPredicate>();
		for (LSDPredicate pred : predicates) {
			// check whether the type appears in the pred.types
			boolean includeType = false;
			for (int i=0; i<pred.types.length; i++ ){ 
				if (pred.types[i] == type) {
					includeType=true;
				}
			}
			if (pred.kind==kind && includeType) { 
				results.add(pred);
			}
		}
		if (results.size()==0) return null; 
		return results;
	}
	// added for refactoring support 
	// FIXME: Predicate dependent part
	public char[] getPrimaryTypes() {
		String name = this.getName();
		if (name.indexOf("_") > 0)
			name = name.substring(name.indexOf("_") + 1);
		if (name.equals("type")) {
			return "t".toCharArray();
		}else if (name.equals("dependency")) {
			return "t".toCharArray();
		} else if (name.equals("field")) {
			return "f".toCharArray();
		} else if (name.equals("method")) {
			return "m".toCharArray();
		} else if (name.equals("typeintype")) {
			return "t".toCharArray();
		} else if (name.equals("inheritedmethod")) {
			return "mt".toCharArray();
		} else if (name.equals("inheritedfield")) {
			return "ft".toCharArray();
		}
		return this.getTypes();
	}
	
	protected String getSuffix() { 
		String name = this.getName();
		if (name.lastIndexOf("_")>0) name = name.substring(name.lastIndexOf("_")+1);
		return name;
	}
	
	protected String getPrefix() { 
		String name = this.getName();
		if (name.lastIndexOf("_") > 0) name = name.substring(0,name.lastIndexOf("_"));
		return name;
	}

	
	public boolean isMethodLevel() {
		if (this.level == LSDPredicate.METHODLEVEL)
			return true;
		return false;
	}
	
	public String getConvertedArgs(String arg) {
		//TODO this can be better
		StringTokenizer tokenizer = new StringTokenizer(arg,",",false);
		if (tokenizer.countTokens()!=2)
		{
			while (arg.contains("(")){
				String temp = arg.substring(0,arg.indexOf("("));
				arg = arg.substring(arg.indexOf("(") + 1);
				arg = temp + arg.substring(arg.indexOf(")")+1);
			}
			while (arg.contains("<")){
				String temp = arg.substring(0,arg.indexOf("<"));
				arg = arg.substring(arg.indexOf("<") + 1);
				arg = temp + arg.substring(arg.indexOf(">")+1);
			}
		}
		tokenizer = new StringTokenizer(arg,",",false);
		if (isConclusionPredicate()){
			String arg0 = tokenizer.nextToken(),arg1,arg2;
			if (getSuffix().equalsIgnoreCase("typeintype") || getSuffix().equalsIgnoreCase("accesses"))		
				arg0 = tokenizer.nextToken();
			if (getSuffix().contains("inherited"))
			{
				arg0 = tokenizer.nextToken();
				arg0 = tokenizer.nextToken();
			}
			if (arg0.indexOf("#") != -1)
				arg0 = arg0.substring(1,arg0.indexOf("#"));
			else if (arg0.indexOf("\"") != -1)
				arg0 = arg0.substring(1,arg0.lastIndexOf("\""));
			arg1 = arg0.substring(arg0.indexOf("%.") + 2);
			if (arg0.indexOf("%") == 0)
				arg2="null";
			else
				arg2 = arg0.substring(0,arg0.indexOf("%."));
			if (arg2 == null || arg2.length()==0) 
				arg2 = "null";
			arg0 = "\""+arg0+"\",\""+arg1+"\",\""+arg2+"\"";
			return arg0;
		}
		else{
			//it's a convertable 2kb
			String arg0 = tokenizer.nextToken();
			String arg1 = tokenizer.nextToken();
			if (getSuffix().equalsIgnoreCase("accesses"))
			{
				String temp = arg1;
				arg1 = arg0;
				arg0 = temp;
			}
			if (arg0.indexOf("#") != -1)
				arg0 = arg0.substring(1,arg0.indexOf("#"));
			else if (arg0.indexOf("\"") != -1)
				arg0 = arg0.substring(1,arg0.lastIndexOf("\""));
			if (arg1.indexOf("#") != -1)
				arg1 = arg1.substring(1,arg1.indexOf("#"));
			else if (arg1.indexOf("\"") != -1)
				arg1 = arg1.substring(1,arg1.lastIndexOf("\""));
	
			arg0 = "\""+arg0+"\",\""+arg1+"\"";
			return arg0;
			
		}
		
	}
	public LSDPredicate toClassLevel() {
		if (isConclusionPredicate())
			return LSDPredicate.getPredicate("changed_type");
		else{
			if (predName.contains("accesses") || predName.contains("calls")){
				String newPred = predName.substring(0,predName.indexOf('_')) + "_dependency";
				return  LSDPredicate.getPredicate(newPred);
			}
			else
				return this;
		}
	}
	public boolean isDependencyPredicate() {
		if (predName.contains("accesses") || predName.contains("calls"))
			return true;
		return false;
	}
	
	public boolean isCompatibleMethodLevel() {
		if (this.getSuffix().equalsIgnoreCase("dependency"))
			return false;
		return true;
	}
	
	public ArrayList<LSDPredicate> getMethodLevelDependency() {
		String prefix = this.getPrefix();
		ArrayList<LSDPredicate> preds = new ArrayList<LSDPredicate>();
		preds.add(LSDPredicate.getPredicate(prefix + "_calls"));
		preds.add(LSDPredicate.getPredicate(prefix + "_accesses"));
		return preds;
	}
	public void updateBindings(ArrayList<LSDBinding> bindings) {
		if (getSuffix() == "accesses"){
			ArrayList<LSDBinding> temp = new ArrayList<LSDBinding>();
			temp.add(bindings.get(1));
			temp.add(bindings.get(0));
			bindings = temp;
		}
		
	}	
	public static String combineArguments (String arg0, String arg1, String arg2) { 

		return "\"" +arg0+ "\", \""+arg1+"\" ,\""+arg2+"\"";
	}
	public List<LSDBinding> getFreshBindingsForConclusion () { 
		
		ArrayList<LSDBinding> bindings = new ArrayList<LSDBinding>();
		ArrayList<LSDVariable> variables = new ArrayList<LSDVariable>();
		ArrayList<LSDVariable> constHolders = new ArrayList<LSDVariable>(); 
		for (int i=0; i<this.getTypes().length; i++) {

			char type = this.getTypes()[i];
			LSDVariable nextVar = null; 
			if (i == this.getKeyArgument()) {
				nextVar = LSDVariable.newFreeVariable(variables, type);
				variables.add(nextVar);
			}else { 
				nextVar= LSDVariable.newConstantHolder(constHolders, type); 
				constHolders.add(nextVar);
			}
			bindings.add(new LSDBinding(nextVar));
		}
		return bindings; 
	}
// MK 020811. Basically I made each variable to contain its own information about whether it should be a constant holder or not.	
//	public boolean toBeRemovedDueToUndesirableUniversalQuantifiers(LSDVariable v) {
//		int index = -1;
//		// get the argument index of the variable.
//		for (int i = 0; i < bindings.size(); i++) {
//			LSDBinding b = bindings.get(i);
//			if (b.isBound() == false && b.getType() == v.getType()
//					&& b.getVariable().getName().equals(v.getName())) {
//				index = i;
//			}
//		}
//		// MK 020611 : How can I force rule styles so that I can prevent
//		// universal quantifiers in certain argument positions?
//
//		// extends, subtype, implements index must be 1
//		// accesses, calls index must be 1
//		String predName = this.getPredicate().getName();
//		if (predName.indexOf("extends") != -1
//				|| predName.indexOf("implements") != -1
//				|| predName.indexOf("subtype") != -1
//				|| predName.indexOf("calls") != -1
//				|| predName.indexOf("accesses") != -1) {
//			return index == 1; // this will prevent universal quantifiers to
//								// exist on position 1.
//		}
//		return false;
//	}
	
//	 FIXME:PREDICATE CONTENT DEPENT
	public int[][] getPrimaryArguments () {
		String name = this.getSuffix();		
		if (name.equals("type")) {
			int s[][] = { { 0 }};// { 1, 2 } };
			return s;
		} else if (name.equals("field")) {
			int s[][] = { { 0 }, { 1, 2 } };
			return s;
		} else if (name.equals("method")) {
			int s[][] = { { 0 }, { 1, 2 } };
			return s;
		} else if (name.equals("typeintype")) {
			int s[][] = { { 0 } };
			return s;
		} 
		int s[] = new int[this.getTypes().length];
		for (int i = 0; i < s.length; i++) {
			s[i] = i;
		}
		int ss[][] = { s };
		return ss;
	}
	
//	 FIXME:PREDICATE CONTENT DEPENT
	public int getReferenceArgument () {
		String name = this.getSuffix();		
		if (name.equals("subtype")) {
			return 1;
		} else if (name.equals("accesses")) {
			return 1;
		} else if (name.equals("inheritedfield")) {
			return 2;
		} else if (name.equals("inheritedmethod")) {
			return 2;
		}
		return 0;
	}
	public int getKeyArgument() {
		if (predName.indexOf("_package")!=-1) { 
			return 0; 
				}
		if (predName.indexOf("_type") != -1) {
			return 0; 
			}
		if (predName.indexOf("_method") != -1) {
			return 0; 
		}
		if (predName.indexOf("_field") != -1){ 
			return 0; 
		}
		if (predName.indexOf("_calls") != -1) {
			return 0; 
		}
		if (predName.indexOf("_accesses") != -1) {
			return 1; 
		}
		if (predName.indexOf("_extends") != -1
				|| predName.indexOf("_implements") != -1 
				|| predName.indexOf("_subtype")!=-1) {
			return 0;
		}
		if (predName.indexOf("_return") != -1) {
			return 0;  
		}
		if (predName.indexOf("_inheritedmethod")!=-1) { 
			return 1; 
		}
		if (predName.indexOf("_inheritedfield")!=-1) { 
			return 1; 
		}
		return -1; 
	}
}
