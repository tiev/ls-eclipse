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
package serp.util;


import java.io.*;


/**
 *	<p>Semaphore implementation.</p>
 *
 *	@author		Abe White
 */
public class Semaphore
	implements Serializable
{
	private int _available = 1;


	/**
	 *	Construct the semaphore with an initial available count of 1.
	 */
	public Semaphore ()
	{
	}


	/**
	 *	Construct the semaphore with an initial available count of 
	 *	<code>avaiable</code>.
	 */
	public Semaphore (int available)
	{
		if (available < 0)
			throw new IllegalArgumentException ("available = " + available);
		_available = available;
	}

	
	/**
	 *	Returns the current available count.
	 */
	public int getAvailable ()
	{
		return _available;
	}


	/**
	 *	Down the semaphore's available count.  If the count is at 0, this call
	 *	will block until the semaphore is {@link #up}'d.
	 */
	public synchronized void down ()
	{
		while (_available == 0)
			try { wait (); } catch (InterruptedException ie) {}
		_available--;
	}


	/**
	 *	Down the semaphore's available count.  If the count is at 0, this call
	 *	will block until the semaphore is {@link #up}'d or the given timeout
	 *	elapses.
	 *
	 *	@para	timeout		the number of milliseconds to wait before timing out
	 *	@return				true if the semaphore was down'd, false on timeout
	 */
	public synchronized boolean down (long timeout)
	{
		// use version that doesn't need to check time; more efficient
		if (timeout == 0)
		{
			down ();
			return true;
		}

		if (_available == 0)
		{
			long time = System.currentTimeMillis ();
			long end = time + timeout;
			while (_available == 0 && time < end)
			{
				try
				{
					wait (end - time);
				}
				catch (InterruptedException ie)
				{
				}
				time = System.currentTimeMillis ();
			}
		}

		if (_available == 0)
			return false;

		_available--;
		return true;
	}


	/**
	 *	Up the semaphore.
	 */
	public synchronized void up ()
	{
		_available++;
		if (_available == 1)
			notify ();
	}
}
