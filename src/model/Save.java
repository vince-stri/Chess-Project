package model;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


import model.board.Board;
import model.board.BoardChess;
import view.Journal; 

public class Save {
    private String pathFile;
    private ObjectOutputStream outFile;
    private ObjectInputStream inFile;
    private File file;
    private int saveObjectsNb;

    public Save(String pathFile) {
    	this.pathFile = pathFile;
    	file = null;
    	outFile = null;
    	inFile = null;
    	saveObjectsNb = 0;
    }
    
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
				default:
					return null;
			}
    	}
    	return listToLoad;
    }
    
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
    
    public int openFile() {
    	try {
    		file = new File(pathFile);
			inFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		} catch(NullPointerException e) {
			Journal.displayTextError(e.getMessage() + 5);
			return 5; 
		}catch(FileNotFoundException e) { 
			Journal.displayTextError(e.getMessage() + 6);
			return 6; 
		} catch(SecurityException e) {
			Journal.displayTextError(e.getMessage() + 7);
			return 7; 
		} catch(IOException e) {
			Journal.displayTextError(e.getMessage() + 8);
			return 8;
		}
    	return 0;
    }

}
