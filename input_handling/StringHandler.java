package input_handling;

import java.util.Scanner;

public class StringHandler
{    
    public static String[] takeString(String s)
    {
	if(s == null)
	    {
		throw new IllegalArgumentException("Input must not be null");
	    }
	String[] end = s.split(",");
	String[] toReturn = new String[end.length + 1];
	toReturn[0] = "";
	for(int i = 0; i < end.length; i++)
	    {
		toReturn[i+1] = end[i];
	    }
	return toReturn;
    }
}
