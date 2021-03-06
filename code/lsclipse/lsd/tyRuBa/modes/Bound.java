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
/*****************************************************************\
 * File:        Bound.java
 * Author:      TyRuBa
 * Meta author: Kris De Volder <kdvolder@cs.ubc.ca>
\*****************************************************************/
package tyRuBa.modes;

public class Bound extends BindingMode {
	
	static public Bound the = new Bound();

	private Bound() {}

	public int hashCode() {
		return this.getClass().hashCode();
	}

	public boolean equals(Object other) {
		return other instanceof Bound;
	}

	public String toString() {
		return "B";
	}

	public boolean satisfyBinding(BindingMode mode) {
		return true;
	}

	public boolean isBound() {
		return true;
	}
	public boolean isFree() {
		return false;
	}
	
}
