package file_io;

import java.io.*;

public class SerializableExporter
{
    public static <O extends Serializable> void write(O toWrite, String fileName) throws RuntimeException
    {
    	File fileWrite = null;
    	try{
    		fileWrite = new File(fileName);
    		fileWrite.createNewFile();
    	} catch (IOException e){
    		throw new RuntimeException(e);
    	} finally{
			FileOutputStream fileOut = null;
			try{
			     fileOut =
					new FileOutputStream(fileWrite, false);
			} catch(FileNotFoundException e){
			    throw new RuntimeException(e);
			}
			try{
			    ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(toWrite);
				out.close();
				fileOut.close();
			} catch(IOException e){
			    throw new RuntimeException(e);
			}
		}
    }
}
