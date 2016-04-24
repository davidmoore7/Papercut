# Papercut
Papercut code test

Create Processing class (ProcessPrintJob) that will have read the file(s) and process the print jobs that it contains. 

Create the abstract Paper class that will be extended by all classes that represent the Papersize. Although A4 is the only size supported right now, other sizes will be supported in the future and the code arhitecture needs to support this. 

Create the PaperA4 class that extendes that Paper class. 

Create the PrintJobData object that will contain the data a printjob to include the paper size object (now only PaperA4) as the object will also support the calcuation of the cost for the black and white pages and color pages printed as well as the total calculation for the current job.

Modify the ProcessPrintJob class, add the methods that load the file(s) in the directory that is passed in as one of the arguments in the main method. The path, along with the delimiter to use and the paper size are the three arguments passed in when the class is created. These values are then passed in to the consturctor when the object is created. 

Modify the ProcessPrintJob class object, add method that will read the current file to be processed and read each line from it.

Modify the ProcessPrintJob class object, add method that will read current line and set the appropriate values in the PrintDataObject. Values include the number of black and white pages to be printed, the number of color pages to be printed, if the doublesided printing will be done and the file name that the current line came from. 

Modify the ProcessPrintJob class object, modify the main method to create a report to the console that contains the printjob information contained in the CSV file(s) processed. Report includes the number of black and white pages to be printed, the number of color pages to be printed, is doublesided printing to be used, the cost for the total black and white pages to be printed, the cost for the number of color pages to be printed and the total cost of the entire print job. 
Lastly a line will be printed that contains the total for all print jobs. 




