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
package tyRuBa.util;

import java.util.Vector;

/** This composite class was introduced as a more efficient way of
    representing long recursive chains of appended small ElementSources.
    THese occur for example in unifying something with a long ComponentVector
    */
public class CompositeElementSource extends ElementSource {
    Vector children = new Vector();
    
    /** Composite: adding a child */
    public void add(ElementSource child) {
        children.addElement(child);
    }

    /** Composite: child access */
    public ElementSource get(int i) {
        return (ElementSource)children.elementAt(i);
    }
    
    /** Composite: child count */
    public int numberOfChildren() {
        return children.size();
    }

    public int i=-1;
        
    public int status() {
        for (i=0;i<numberOfChildren();) {
            int stat = get(i).status();
            if (stat==ELEMENT_READY)
                return stat;
            else if (stat==NO_MORE_ELEMENTS) {
                children.removeElementAt(i); 
		//		if (i>0) {
		//    System.err.println();
		//    System.err.print("Inefficient removeElement at "+i+" from "+numberOfChildren());
		//}
                //XXX Inefficient: removal O(n) actually it is not to bad because usually it
                // is the first element which is removed and that is fast in a Vector. 
                // Perhaps linked list may be better though (frees up memory gradually) and also
                // efficient when not the first element.
            }
            else 
                i++;
        }
        if (numberOfChildren()==0) 
            return NO_MORE_ELEMENTS;
        else
            return NO_ELEMENTS_READY;
    }
    
    public Object nextElement() {
        int stat;
        if (!((i>=0) && (i<numberOfChildren())))
            stat=this.status();
        else
            stat = ELEMENT_READY;
        if (stat == ELEMENT_READY) {
            return get(i).nextElement();
        }
        else
            throw new java.lang.Error("No nextElement found in CompositeElementSource");
    }
    
    public void print(PrintingState p) {
    	p.print("Composite(");
    	p.indent();p.newline();
    	for (int i = 0; i < numberOfChildren(); i++) {
			get(i).print(p);
		}
    	p.outdent();
    	p.print(")");
    }

	public ElementSource simplify() {
		if (children.size() == 0)
			return ElementSource.theEmpty;
		if (children.size() == 1)
			return get(0);
		return this;
	}
}
