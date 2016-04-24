package com.papercut.print;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.papercut.print.printjob.Paper;
import com.papercut.print.printjob.PaperA4;
import com.papercut.print.printjob.PrintJobData;



public class ProcessPrintJob {
	
	Paper paperSizeObject;
	private Vector<PrintJobData> printJobsInFiles = new Vector<PrintJobData>();
	private String filePath  = "";
	private String delimiter = ",";
	private String paperSize = "A4";
	
	
	public ProcessPrintJob(String filePath,String delimiter,String paperSize){
		setFilePath(filePath);
		setDelimiter(delimiter);
		setPaperSize(paperSize);
		setPaperFromSize();
	}
	
	
	public void setPaperFromSize(){
		PaperA4 paper = new PaperA4();
		if (this.getPaperSize()!=null){
			if (this.getPaperSize().equalsIgnoreCase("A4")){
				paper = new PaperA4();
			}					
		}
		
		setPaperSizeObject(paper);
	}
	
	public Paper getPaperSizeObject(){
		return paperSizeObject;
	}
	
	public void setPaperSizeObject(Paper paperSizeObjectIn){
		paperSizeObject = paperSizeObjectIn;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector<PrintJobData> getprintJobsInFiles(){
		return printJobsInFiles;
	}//end getprintJobsInFiles
	
	/**
	 * 
	 * @param printJobsInFilesIn
	 */
	public void setprintJobsInFiles(Vector<PrintJobData> printJobsInFilesIn){
		printJobsInFiles = printJobsInFilesIn;
	}//end setprintJobsInFiles
	
	/**
	 * 
	 * @return
	 */
	public String getFilePath(){
		return filePath;
	}
	
	/**
	 * 
	 * @param filePathIn
	 */
	public void setFilePath(String filePathIn){
		filePath = filePathIn;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDelimiter(){
		return delimiter;
	}//end getDelimiter
	
	/**
	 * 
	 * @param delimiterIn
	 */
	public void setDelimiter(String delimiterIn){
		delimiter = delimiterIn;
	}//end setDelimiter
	
	/**
	 * 
	 * @return
	 */
	public String getPaperSize(){
		return paperSize;
	}
	
	/**
	 * 
	 * @param paperSizeIn
	 */
	public void setPaperSize(String paperSizeIn){
		paperSize=paperSizeIn;
	}
	
	/**
	 * 
	 * @param valueToCheck
	 * @return
	 */
	private int checkAndConvertNumber(String valueToCheck){
		int retVal = -1;
	    Pattern numberPat = Pattern.compile("\\d+");
	    Matcher matcher = numberPat.matcher(valueToCheck);

	    

	    if (matcher.find()){
	    	
	    	retVal = Integer.parseInt(matcher.group());            
	       
	    }//end if (matcher.find())
	    
	    return retVal;
	}//end checkAndConvertNumber

	/**
	 * 
	 * @param directory
	 * @return
	 */
	private Vector<File>getFilesInDir(){
		 //printDebugInfo("Directory "+directory);
	
		 Vector<File>files = new Vector<File>();
		 File file = new File(this.getFilePath());		 
		 if (file!=null && file.listFiles()!=null){
			 files = new Vector<File>(Arrays.asList(file.listFiles()));		     
		 }//end if (file!=null && file.listFiles()!=null)   
		 return files;
	 }//end getFilesInDir
	
	
	/**
	 * 
	 * @param lineToRead
	 * @return
	 */
	private PrintJobData readCurrentLine(String[] lineToRead){
	     String tempNumberOfPages = "";
	     String tempColorPages    = "";
	     String tempBothSides     = "";
	     int numberOfPages = -1;
	     int colorPages    = -1;
	     PrintJobData currentPrintJobData = null;
	     
	     if (lineToRead!=null){
	    	 System.out.println("readCurrentLine ");
	     }
	    
	    	if (lineToRead!=null){
	    		currentPrintJobData = new PrintJobData();
	    		currentPrintJobData.setPaperSize(getPaperSizeObject());
	    		tempNumberOfPages = lineToRead[0].trim();	    	
	    		tempColorPages    = lineToRead[1].trim();	    		
	    		tempBothSides     = lineToRead[2].trim();
	    		
	    		
	    		if (tempNumberOfPages!=null){
	    			numberOfPages=checkAndConvertNumber(tempNumberOfPages);	 
	    			currentPrintJobData.setNumberOfPages(numberOfPages);
	    		}//end if (tempNumberOfPages!=null)
	    		
	    		
	    		//check to ensure that a value was extracted. If it is not set the object to null so that when it is returned, it will be reported along with the corresponding line
	    		//number for troubleshooting.
	    		if (numberOfPages < 0){
	    			currentPrintJobData = null;
	    		}//end if (numberOfPages < 0)
	    		
	    		if (currentPrintJobData!=null && tempColorPages!=null){
	    			colorPages=checkAndConvertNumber(tempColorPages);
	    			currentPrintJobData.setNumberColorPages(colorPages);
	    		}//end if (currentPrintJobData!=null && tempColorPages!=null)
	    		
	    		//check to ensure that a numeric value was extracted. If it is not set the object to null so that when it is returned, it will be reported along with the corresponding line
	    		//number for troubleshooting.
	    		
	    		if (colorPages < 0){
	    			currentPrintJobData = null;
	    		}//end if (colorPages < 0)
	    		
	    		//check to ensure that the PrintJobData has not been set to null and that the value of the third column retrieved is an actual
	    		//boolean. If it is not set the object to null so that when it is returned, it will be reported along with the corresponding line
	    		//number for troubleshooting. 
	    		
	    		if (currentPrintJobData!=null){
		    		if (tempBothSides.equalsIgnoreCase("true")){
		    			currentPrintJobData.setPrintDoublesided(true);
		    		}else if (tempBothSides.equalsIgnoreCase("false")){
		    			currentPrintJobData.setPrintDoublesided(false);
		    		}else{		    			
		    			currentPrintJobData = null;
		    		}//end if (tempBothSides.equalsIgnoreCase("true"))
	    		}//end if (currentPrintJobData!=null)
	    		
	    	}//end if (lineToRead!=null)
	    	
	    	
	    	
	    	return currentPrintJobData;
	    }//end readCurrentLine
	    	 
	    
	  
		

	/**
	 * 
	 * @param csvFileToRead
	 */
	
	private void readFileSetObject(String csvFileToRead){
		
		BufferedReader bufferedReader = null; 
		PrintJobData printJobData = new PrintJobData();
		
		int currentLine = 0;
		String line = ""; 
		
			try{
				bufferedReader = new BufferedReader(new FileReader(csvFileToRead));  
			   while ((line = bufferedReader.readLine()) != null) {  
				   currentLine = currentLine++;
			       String[] currentPrintJob = line.split(getDelimiter()); 
				    //if (currentPrintJob!=null && currentPrintJob.length==3){
			       if (currentPrintJob!=null){
			    	    
				    	printJobData=readCurrentLine(currentPrintJob);
				    	//check the PrintJobData object, if it not null add it to the Vector class. If it is there was a problem with the data which
				    	//needs to be looked at. 
				    	if (printJobData!=null){
				    		printJobData.setFileName(csvFileToRead);
				    		getprintJobsInFiles().add(printJobData);
				    	}else{
				    		System.out.println("<<<Line number "+currentLine+" in PrintJobFile "+csvFileToRead+" contains invaliad data.>>>");
				    	}
				    	
				    }else{
				    	System.out.println("<<<Line number "+currentLine+" in PrintJobFile "+csvFileToRead+" is in incorrect format.>>>");
				    }
			    
			   
			    }
			  
			 
		  
		  } catch (FileNotFoundException fne) { 
			   System.out.println("FileNotFoundException in readFileSetObject method while processing "+csvFileToRead);	
			   System.out.println("FileNotFoundException message is "+fne.getMessage());
			   fne.printStackTrace();  
		  } catch (IOException ioe) { 
				System.out.println("IOException in readFileSetObject method while processing "+csvFileToRead);	
				System.out.println("IOException message is "+ioe.getMessage());
			    ioe.printStackTrace(); 
		  }catch(Exception e){
				System.out.println("General Exception in readFileSetObject method while processing "+csvFileToRead);	
				System.out.println("FileNotFoundException message is "+e.getMessage()); 
				e.printStackTrace();		  
		  } finally {  
			   if (bufferedReader != null) {  
				    try {  
				    	bufferedReader.close();  
				    } catch (IOException e) { 
				    	System.out.println("IOException Exception in readFileSetObject while trying to close BufferedReader object.");	
						System.out.println("IOException message is "+e.getMessage());  	
				        e.printStackTrace();  
				    }//end try catch  
			   }//end if (bufferedReader != null)  
		  }//end try catch catch catch finally  
		
		
	}//end readFileSetObject

	/**
	 * 
	 * @param printjobFileLocation
	 */
	public void readCsv() {  
		  
		   

		  Vector<PrintJobData>printJobs = null;
		  PrintJobData printJobToAdd = new PrintJobData();
		  
	 

           Vector<File>filesToProcess = getFilesInDir();	  
           
           if (filesToProcess!=null){
        	  printJobs = new Vector<PrintJobData>(); 
        	  for (int i=0; i< filesToProcess.size(); i++){ 
        		  File currentFile = filesToProcess.get(i);
        		  if (currentFile!=null && currentFile.length() > 0){
        		      readFileSetObject(currentFile.getAbsolutePath());
        		      
	          
        	      }//end if (currentFile!=null && currentFile.length() > 0) 
			   
		  
		     }//end for (int i=0; i< filesToProcess.size(); i++)
		  
		  
		 }//end if (filesToProcess!=null)  
           
               
    }//end readCsv      

	
	public static void main(String[] args) {
	
		
		PrintJobData currentPrintJobData;
		BigDecimal printJobCost = new BigDecimal(0.00D);
		
		if (args.length < 3 ){
			System.out.println("Parameters are missing, path where print job file(s) are located, delimiter to use (i.e. comma (,) pipe (|) etc..) and the size of the paper (currently only A-4 is supported)");
			
		    System.exit(1);
			
		}
		
		String path =     args[0].trim();
		String delimiterToUse   = args[1].trim();
		String paperSize  = args[2].trim();
		String outPut = "";
		
		ProcessPrintJob processPrintJob = new ProcessPrintJob(path,delimiterToUse,paperSize);
		processPrintJob.readCsv();
		
		if (processPrintJob.getprintJobsInFiles()!=null && processPrintJob.getprintJobsInFiles().size()>0){
			System.out.println("*************************************************************");
			System.out.println("*************************************************************");
			System.out.println("Print job details ");
			System.out.println("--------------------------------------------------------------");
			for (int i=0; i < processPrintJob.getprintJobsInFiles().size(); i++){
				currentPrintJobData = processPrintJob.getprintJobsInFiles().get(i);
				
				outPut = "No. of Black and White pages = "+ currentPrintJobData.getNumberOfPages();
				outPut = outPut + " No. of Color pages = " + currentPrintJobData.getNumberColorPages();
				outPut = outPut + " Print double sided = " + currentPrintJobData.getPrintDoublesided();				
				outPut = outPut + " Cost black and white pages = " + currentPrintJobData.getCostBlackAndWhitePages();
				outPut = outPut + " Cost for color pages = " + currentPrintJobData.getCostColorPages();
				outPut = outPut + " Total cost for job = " + currentPrintJobData.getTotalPrintJobCost();
				outPut = outPut + " From file name = " + currentPrintJobData.getFileName();
				printJobCost=printJobCost.add(currentPrintJobData.getTotalPrintJobCost());
				
				System.out.println(outPut);
				System.out.println("--------------------------------------------------------------");
				
			}//end for loop
			System.out.println("=============================================================");
			System.out.println("Total cost for all jobs is "+printJobCost);
			System.out.println("=============================================================");
			System.out.println("*************************************************************");
			System.out.println("*************************************************************");			
		}else{
			System.out.println("*************************************************************");
			System.out.println("*************************************************************");
			System.out.println("No jobs were processed from file(s) located in "+processPrintJob.getFilePath());
			System.out.println("*************************************************************");
			System.out.println("*************************************************************");
		}//end if (processPrintJob.getprintJobsInFiles()!=null && processPrintJob.getprintJobsInFiles().size()>0)
    
		

	}

}
