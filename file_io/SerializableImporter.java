package file_io;

import java.io.*;

public class SerializableImporter
{
    public static <O extends Serializable> O read(String fileName) throws RuntimeException
    {
	O toReturn;
	try
	  {
	      FileInputStream fileIn = new FileInputStream(fileName);
	      ObjectInputStream in = new ObjectInputStream(fileIn);
	      toReturn = (O) in.readObject();
	      in.close();
	      fileIn.close();
	  }catch(IOException i)
	  {
	      throw new RuntimeException(i);
	  }catch(ClassNotFoundException c)
	  {
	      throw new RuntimeException("Loaded class unknown", c);
	  }
	return toReturn;
    }
}