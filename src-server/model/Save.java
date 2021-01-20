package model;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.board.BoardChess;
import view.Journal; 

/**
 * Save class to save and load the game
 * @version 1.0
 * @author axel gauthier
 */
public class Save {
	
	/**
	 * The path of the save file
	 */
    private String pathFile;
    
    /**
     * The file output object
     */
    private ObjectOutputStream outFile;
    
    /**
     * The file input object
     */
    private ObjectInputStream inFile;
    
    /**
     * The file object
     */
    private File file;
    
    /**
     * The number of saved objects
     */
    private int saveObjectsNb;

    /**
     * Constructor of Save
     * @param pathFile the path of the save file
     */
    public Save(String pathFile) {
    	this.pathFile = pathFile;
    	file = null;
    	outFile = null;
    	inFile = null;
    	saveObjectsNb = 0;
    }
    
    /**
     * Save a list of objects
     * @param listToSave the list to save
     * @return a return code:
     *  0 - OK
     *  1 - The file can not be created
     *  2 - An object has not been able to be saved inside the file
     *  3 - The file has not been properly closed
     */
    public int save(ArrayList<Object> listToSave) {
    	if(outFile == null) {
    		if(createFile() != 0) {
    			return 1;
    		}
    	}
    	
    	Iterator<Object> it = listToSave.iterator();
    	saveObjectsNb = listToSave.size();
    	
		try {
			outFile.writeObject(saveObjectsNb);
		} catch (IOException e) {
			Journal.displayTextError(e.getMessage());
			return 2;
		}    	
    	
    	while(it.hasNext()) {
    			Object o = it.next();
    			try {
					outFile.writeObject(""+o.getClass());
				} catch (IOException e) {
					Journal.displayTextError(e.getMessage());
					return 2;
				}
    			
				try {
					outFile.writeObject(o);
				} catch (IOException e) {
					Journal.displayTextError(e.getMessage());
					return 2;
				}
    	}
    	
    	try {
			outFile.close();
		} catch (IOException e) {
			return 3;
		} return 0;
    }

    /**
     * Load a list of objects from a file
     * @return the list of objects loaded if everything went great, null if not
     */
    public ArrayList<Object> load() {
    	if(inFile == null) {
    		if(openFile() != 0) {
    			return null;
    		}
    	}
    	
    	ArrayList<Object> listToLoad = new ArrayList<Object>();
    	
    	try {
			saveObjectsNb = (int)inFile.readObject();
		} catch (ClassNotFoundException e) {
			Journal.displayTextError(e.getMessage());
			return null;
		} catch (IOException e) {
			Journal.displayTextError(e.getMessage());
			return null;
		}
    	
    	String objectType = null;
    	while(saveObjectsNb-- > 0) {
    		try {
    			objectType = (String)inFile.readObject();
    		} catch (ClassNotFoundException e) {
    			Journal.displayTextError(e.getMessage());
    			return null;
    		} catch (IOException e) {
    			Journal.displayTextError(e.getMessage());
    			return null;
    		}
    		switch (objectType) {
				case "class model.board.BoardChess":
					try {
		    			listToLoad.add((BoardChess)inFile.readObject());
		    		} catch (ClassNotFoundException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		} catch (IOException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		}	
				break;
				case "class [Lmodel.Army;":
					try {
						listToLoad.add((Army[])inFile.readObject());
		    		} catch (ClassNotFoundException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		} catch (IOException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		}
				break;
				case "class java.lang.Integer":
					try {
						listToLoad.add((Integer)inFile.readObject());
		    		} catch (ClassNotFoundException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		} catch (IOException e) {
		    			Journal.displayTextError(e.getMessage());
		    			return null;
		    		}
				break;
				default:
					return null;
			}
    	}
    	return listToLoad;
    }
    
    /**
     * Create the save file
     * @return a return code:
     *  0 - OK
     *  1 - the pathFile attribute is null
     *  2 - the file cannot be created here
     *  3 - security issues has been raised
     *  4 - an input / output error has been raised
     */
    private int createFile() {
    	try {
    		file = new File(pathFile);
			outFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		} catch(NullPointerException e) {
			Journal.displayTextError(e.getMessage());
			return 1; 
		}catch(FileNotFoundException e) { 
			Journal.displayTextError(e.getMessage());
			return 2; 
		} catch(SecurityException e) {
			Journal.displayTextError(e.getMessage());
			return 3; 
		} catch(IOException e) {
			Journal.displayTextError(e.getMessage());
			return 4;
		}
    	return 0;
    }
    
    /**
     * Open the save file
     * @return a return code:
     *  0 - OK
     *  1 - the pathFile attribute is null
     *  2 - the file has not been found
     *  3 - security issues has been raised
     *  4 - an input / output error has been raised
     */
    private int openFile() {
    	try {
    		file = new File(pathFile);
			inFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		} catch(NullPointerException e) {
			Journal.displayTextError(e.getMessage());
			return 1; 
		}catch(FileNotFoundException e) { 
			Journal.displayTextError(e.getMessage());
			return 2; 
		} catch(SecurityException e) {
			Journal.displayTextError(e.getMessage());
			return 3; 
		} catch(IOException e) {
			Journal.displayTextError(e.getMessage());
			return 4;
		}
    	return 0;
    }
    
    /**
     * Open the save file with a different path
     * @param newPath the new path to open the file
     * @return a return code:
     *  0 - OK
     *  1 - the pathFile attribute is null
     *  2 - the file has not been found
     *  3 - security issues has been raised
     *  4 - an input / output error has been raised
     */
    public int openFile(String newPath) {
    	try {
    		file = new File(newPath);
			inFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		} catch(NullPointerException e) {
			Journal.displayTextError(e.getMessage());
			return 1; 
		}catch(FileNotFoundException e) { 
			Journal.displayTextError(e.getMessage());
			return 2; 
		} catch(SecurityException e) {
			Journal.displayTextError(e.getMessage());
			return 3; 
		} catch(IOException e) {
			Journal.displayTextError(e.getMessage());
			return 4;
		}
    	return 0;
    }

}
