package gov.cdc.nedss.util;

import java.text.*;


public class NumberUtil extends NumberFormat
{


/** This method returns null. I have found no meaningful translation
    of floating point numbers to Roman numerals, however the NumberFormat
    method requires that it is implemented. Perhaps should just cast
    the double to a long and format accordingly.
*/

	public StringBuffer format(double n, StringBuffer a, FieldPosition p)
	{
		return null;
	}


/** This nested class is used to map Roman symbols onto their numerical
    values. Used in the Roman class.
*/

	public static class SymTab
	{
		/** Roman symbol */
		char symbol;

		/** Numerical value */
		long value;

		/** Constructor to build a SymTab from supplied symbol and value
		    @param s Roman symbol
		    @param v Numerical value
   */
      public SymTab(char s, long v) { this.symbol=s; this.value=v; }
   };


/** This table maps individual Roman symbols onto their numerical
    values.

*/

		public static NumberUtil.SymTab syms[]= {
   		new NumberUtil.SymTab('M',1000),
			new NumberUtil.SymTab('D',500),
   		new NumberUtil.SymTab('C',100),
   		new NumberUtil.SymTab('L',50),
   		new NumberUtil.SymTab('X',10),
   		new NumberUtil.SymTab('V',5),
   		new NumberUtil.SymTab('I',1)
		};


/** This method converts a Roman Numeral string to a long integer. It does
    not check that the string is in the correct format - for some incorrectly
    formatted numbers, i.e. iix, it will produce a number. For others,
    it will throw an exception.

    @param s string of Roman Numerals
		@param parsePosition the place to start parsing
    @return A Long object containing the parsed Roman numeral
*/

	public Number parse(String text, ParsePosition parsePosition)
	{

	String s = text.substring(parsePosition.getIndex());

 	long tot=0,max=0;
 	char ch[]=s.toUpperCase().toCharArray();
	int i,p;
	for(p=ch.length-1;p>=0;p--)
	{
		for(i=0;i<syms.length;i++)
		{
			if(syms[i].symbol==ch[p])
			{
				if(syms[i].value>=max)
					tot+= (max = syms[i].value);
				else
					tot-= syms[i].value;
			}
		}
	}

	// say that we parsed the whole string
	parsePosition.setIndex(s.length());
	return new Long(tot);
	}


/** This method converts a Roman Numeral string to a long integer. It does
    not check that the string is in the correct format - for some incorrectly
    formatted numbers, i.e. iix, it will produce a number. For others,
    it will throw an exception.

    @param s string of Roman Numerals
    @return The integer representation of the Numerals
*/

	public static long toLong(String s)
	{
		long tot=0,max=0;
		char ch[]=s.toUpperCase().toCharArray();
		int i,p;
		for(p=ch.length-1;p>=0;p--)
 		{
			for(i=0;i<syms.length;i++)
			{
				if(syms[i].symbol==ch[p])
				{
					if(syms[i].value>=max)
						tot+= (max = syms[i].value);
					else
						tot-= syms[i].value;
				}
			}
		}
		return tot;
	};


/** This method converts the supplied long into capitalised Roman
    numerals.<br>
    BUG: the method does not take account of the
    <code>FieldPosition p</code> parameter.

    @param n The number to be converted into Roman numerals
    @param s The StringBuffer into which the output is to be placed.
    @return The StringBuffer s
*/

		public StringBuffer format(long n, StringBuffer s, FieldPosition p)
   	{
			int i;
			while(n>0)
			{
				for(i=0;i<syms.length;i++)
				{
					if(syms[i].value<=n)
					{
						int shift=i+(i%2);
						if( i>0 && shift<syms.length &&
                (syms[i-1].value-syms[shift].value) <= n )
						{
						s.append( syms[shift].symbol);
						s.append( syms[i-1].symbol);
						n= n - syms[i-1].value + syms[shift].value;

						i=-1;
					}
					else
					{
						s.append(syms[i].symbol);
						n-=syms[i].value;
						i=-1;
					}
				}
			}
		}
		return s;
	}


/** This method converts a long integer to capitalised Roman notation.

    @param n The integer to convert to Roman Numerals.
    @return A String object containing the Roman Numerals.

*/

   public static String toRoman(long n)
   {
   	int i;
		String s;
      s="";
      while(n>0)
      {
			for(i=0;i<syms.length;i++)
	      {
				if(syms[i].value<=n)
				{
					int shift=i+(i%2);
					if( i>0 && shift<syms.length &&
 					    (syms[i-1].value-syms[shift].value) <= n )
					{
						s= s+ syms[shift].symbol + syms[i-1].symbol;
						n= n - syms[i-1].value + syms[shift].value;

						i=-1;


					}
					else
					{
						s+=syms[i].symbol;
						n-=syms[i].value;
						i=-1;
					}
				}
			}
		}
		return s;
	}

}
