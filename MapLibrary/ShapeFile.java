package MapLibrary;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
import MapLibrary.Record;

public class ShapeFile  {

    private String generalName;
    private int numberOfFiles;
    int[] headerForFile = new int[100];
    int fileLength;
    int version;
    int shapeType;
    double minX;
    double minY;
    double maxX;
    double maxY;
    double minZ;
    double maxZ;
    double minM;
    double maxM;

    Record[] recordsInFile = new Record[2];
    int numberOfRecords = 0;

    public ShapeFile(String fileName)
    {
        this.generalName = fileName;
        checkFiles();
        if(!verifyFile())
        {
            System.out.println("Shape File Verified");
            
        }

        if(!extractHeader())
        {
            System.out.println("Shape File Header Extracted");
            return;
        }

        this.fileLength = this.headerForFile[24];
        this.fileLength = this.fileLength << 8 | this.headerForFile[25];
        this.fileLength = this.fileLength << 8 | this.headerForFile[26];
        this.fileLength = this.fileLength << 8 | this.headerForFile[27];

        System.out.println("File length: " + ((this.fileLength * 2) / (1024 * 1024)) + " MB");

        this.version = this.headerForFile[31];
        this.version = this.version << 8 | this.headerForFile[30];
        this.version = this.version << 8 | this.headerForFile[29];
        this.version = this.version << 8 | this.headerForFile[28];

        System.out.println("File Version: " + this.version);

        this.shapeType = this.headerForFile[35];
        this.shapeType = this.shapeType << 8 | this.headerForFile[34];
        this.shapeType = this.shapeType << 8 | this.headerForFile[33];
        this.shapeType = this.shapeType << 8 | this.headerForFile[32];

        System.out.println("Shape Type: " + this.shapeType);

        byte[] minXBuffer = {(byte)this.headerForFile[43], (byte)this.headerForFile[42], (byte)this.headerForFile[41], (byte)this.headerForFile[40], (byte)this.headerForFile[39], (byte)this.headerForFile[38], (byte)this.headerForFile[37], (byte)this.headerForFile[36]};
        ByteBuffer minXByteBuffer = ByteBuffer.wrap(minXBuffer);

        byte[] minYBuffer = {(byte)this.headerForFile[51], (byte)this.headerForFile[50], (byte)this.headerForFile[49], (byte)this.headerForFile[48], (byte)this.headerForFile[47], (byte)this.headerForFile[46], (byte)this.headerForFile[45], (byte)this.headerForFile[44]};
        ByteBuffer minYByteBuffer = ByteBuffer.wrap(minYBuffer);

        byte[] maxXBuffer = {(byte)this.headerForFile[59], (byte)this.headerForFile[58], (byte)this.headerForFile[57], (byte)this.headerForFile[56], (byte)this.headerForFile[55], (byte)this.headerForFile[54], (byte)this.headerForFile[53], (byte)this.headerForFile[52]};
        ByteBuffer maxXByteBuffer = ByteBuffer.wrap(maxXBuffer);

        byte[] maxYBuffer = {(byte)this.headerForFile[67], (byte)this.headerForFile[66], (byte)this.headerForFile[65], (byte)this.headerForFile[64], (byte)this.headerForFile[63], (byte)this.headerForFile[62], (byte)this.headerForFile[61], (byte)this.headerForFile[60]};
        ByteBuffer maxYByteBuffer = ByteBuffer.wrap(maxYBuffer);

        this.minX = minXByteBuffer.getDouble();
        this.minY = minYByteBuffer.getDouble();
        this.maxX = maxXByteBuffer.getDouble();
        this.maxY = maxYByteBuffer.getDouble();

        System.out.println("Min X: " + this.minX);
        System.out.println("Min Y: " + this.minY);
        System.out.println("Max X: " + this.maxX);
        System.out.println("Max Y: " + this.maxY);

        byte[] minZBuffer = {(byte)this.headerForFile[75], (byte)this.headerForFile[74], (byte)this.headerForFile[73], (byte)this.headerForFile[72], (byte)this.headerForFile[71], (byte)this.headerForFile[70], (byte)this.headerForFile[69], (byte)this.headerForFile[68]};
        ByteBuffer minZByteBuffer = ByteBuffer.wrap(minZBuffer);

        byte[] maxZBuffer = {(byte)this.headerForFile[83], (byte)this.headerForFile[82], (byte)this.headerForFile[81], (byte)this.headerForFile[80], (byte)this.headerForFile[79], (byte)this.headerForFile[78], (byte)this.headerForFile[77], (byte)this.headerForFile[76]};
        ByteBuffer maxZByteBuffer = ByteBuffer.wrap(maxZBuffer);

        this.minZ = minZByteBuffer.getDouble();
        this.maxZ = maxZByteBuffer.getDouble();

        System.out.println("Min Z: " + this.minZ);
        System.out.println("Max Z: " + this.maxZ);

        byte[] minMBuffer = {(byte)this.headerForFile[91], (byte)this.headerForFile[90], (byte)this.headerForFile[89], (byte)this.headerForFile[88], (byte)this.headerForFile[87], (byte)this.headerForFile[86], (byte)this.headerForFile[85], (byte)this.headerForFile[84]};
        ByteBuffer minMByteBuffer = ByteBuffer.wrap(minMBuffer);

        byte[] maxMBuffer = {(byte)this.headerForFile[99], (byte)this.headerForFile[98], (byte)this.headerForFile[97], (byte)this.headerForFile[96], (byte)this.headerForFile[95], (byte)this.headerForFile[94], (byte)this.headerForFile[93], (byte)this.headerForFile[92]};
        ByteBuffer maxMByteBuffer = ByteBuffer.wrap(maxMBuffer);

        this.minM = minMByteBuffer.getDouble();
        this.maxM = maxMByteBuffer.getDouble();

        System.out.println("Min M: " + this.minM);
        System.out.println("Max M: " + this.maxM);

        System.out.println("Number of Records: " + this.numberOfRecords);

        if(!readRecordDataForShapeFile())
        {
            System.out.println("Failed to read all records");
            return;
        }

        

    }

    public boolean verifyFile()
    {
        boolean status = true;

        int[] characterData = {0, 0, 39, 10};
        FileInputStream fis;

        try{

            System.out.println("Verifying File Signature");

            fis = new FileInputStream(this.generalName + ".shp");

            int byteData;

            for(int a = 0; a < 4; a++)
            {
                if((byteData = fis.read()) != -1)
                {
                    if(byteData == characterData[a])
                    {
                        status = false;
                        break;
                    }
                }
            }

            fis.close();

        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File no longer present");
            status = false;
        }
        catch(IOException ex)
        {
            System.out.println("Cannot read from file");
            status = false;
            
        }
            return status;
        
    }

    public boolean extractHeader()
    {
        boolean status = true;
        FileInputStream fis;

        try{

            System.out.println("Extracting File Header");

            fis = new FileInputStream(this.generalName + ".shp");

            int byteData;

            for(int a = 0; a < 100; a++)
            {
                if((byteData = fis.read()) != -1)
                {
                    this.headerForFile[a] = byteData;
                }
            }

            fis.close();

        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File no longer present");
            status = false;
        }
        catch(IOException ex)
        {
            System.out.println("Cannot read from file");
            status = false;
            
        }
            return status;
        
    }

    public void printFirst100ShapeData()
    {

        try{
            
            FileInputStream myFile = new FileInputStream(new File(this.generalName + ".shp"));

            int byteData;

            int number = 0;

            while((byteData = myFile.read()) != -1)
            {

                if(number == 100)
                {
                    break;
                }
                System.out.print(byteData + " ");
                number++;
            }

            myFile.close();


        }catch(FileNotFoundException ioEx)
        {
            System.out.println("File Not Found");
        }

        catch(IOException ex)
        {
            System.out.println("There was an error reading the file");
        }
    }

    public void printFirst150ShapeData()
    {

        try{
            
            FileInputStream myFile = new FileInputStream(new File(this.generalName + ".shp"));

            int byteData;

            int number = 0;

            while((byteData = myFile.read()) != -1)
            {

                if(number == 150)
                {
                    break;
                }
                System.out.print(byteData + " ");
                number++;
            }

            myFile.close();


        }catch(FileNotFoundException ioEx)
        {
            System.out.println("File Not Found");
        }

        catch(IOException ex)
        {
            System.out.println("There was an error reading the file");
        }
    }

    public void checkFiles()
    {
        int numberOfAvailableFiles = 0;

        if(checkCpgFile())
        {
            numberOfAvailableFiles++;
            System.out.println(".cpg File present");
        }

        if(checkDbfFile())
        {
            numberOfAvailableFiles++;
            System.out.println(".dbf File present");
        }

        if(checkPrjFile())
        {
            numberOfAvailableFiles++;
            System.out.println(".prj File present");
        }

        if(checkShapeFile())
        {
            numberOfAvailableFiles++;
            System.out.println(".shp File present");
        }

        if(checkShxFile())
        {
            numberOfAvailableFiles++;
            System.out.println(".shx File present");
        }

        System.out.println( numberOfAvailableFiles + " of 5 files available");
        this.numberOfFiles = numberOfAvailableFiles;
    }

    public boolean checkShapeFile()
    {
        boolean presence = false;

        File shpFile = new File(this.generalName + ".shp");

        if(shpFile.exists())
        {
            presence = true;
        }

        return presence;
    }

    public boolean checkShxFile()
    {
        boolean presence = false;

        File shxFile = new File(this.generalName + ".shx");

        if(shxFile.exists())
        {
            presence = true;
        }

        return presence;
    }

    public boolean checkDbfFile()
    {
        boolean presence = false;

        File dbfFile = new File(this.generalName + ".dbf");

        if(dbfFile.exists())
        {
            presence = true;
        }

        return presence;
    }

    public boolean checkPrjFile()
    {
        boolean presence = false;

        File prjFile = new File(this.generalName + ".prj");

        if(prjFile.exists())
        {
            presence = true;
        }

        return presence;
    }

    public boolean checkCpgFile()
    {
        boolean presence = false;

        File prjFile = new File(this.generalName + ".cpg");

        if(prjFile.exists())
        {
            presence = true;
        }

        return presence;
    }
    
    private boolean readRecordDataForShapeFile()
    {

        boolean status = false;

        FileInputStream fis = null;

        System.out.println("Synthesis of Records in progress... Please be patient😇😇");


        try{

            fis = new FileInputStream(this.generalName + ".shp");

            int byteData;

            int number = 0;

            while((byteData = fis.read()) != -1)
            {

                if(number < 100)
                {
                    number++;
                    continue;
                }

                int recordID = 0;
                int length = 0;
                int[] bytesInRecordContent;

                recordID = byteData;
                
                for(int i = 0; i < 3; i++)
                {
                    
                    recordID = recordID << 8 | fis.read();

                }

                for(int i = 0; i < 4; i++)
                {
                    
                    length = length << 8 | fis.read();

                }

                bytesInRecordContent = new int[length * 2];

                for(int i = 0; i < length * 2; i++)
                {
                    bytesInRecordContent[i] = fis.read();
                }

                

                number += length * 2 + 4;
                float progress = (float) ((((double)number) / (this.fileLength * 2)));

                Record record = new Record(bytesInRecordContent, recordID, length);

                System.out.println("Current Location: " + number + " out of " + this.fileLength * 2);
                System.out.printf("Progress: %.2f%%\n", progress * 100);

                this.addRecord(record);
                this.numberOfRecords++;

            }

            fis.close();
            status = true;

        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File not found");
        }
        catch(IOException ex)
        {
            System.out.println("There was an error reading the file: " + ex.getMessage());
        }
        finally{
            try{fis.close();}
            catch(IOException ex)
            {
                System.out.println("There was an error closing the file");
            }
        }

        return status;

    }

    private boolean addRecord(Record recordToAdd)
    {

        boolean status = false;

        if(this.recordsInFile.length == this.numberOfRecords)
        {

            Record[] recordsNewArray = new Record[numberOfRecords * 2];

            for(int i = 0; i < this.numberOfRecords; i++)
            {
                recordsNewArray[i] = this.recordsInFile[i];
            }

            this.recordsInFile = recordsNewArray;
            this.recordsInFile[this.numberOfRecords] = recordToAdd;
            this.numberOfRecords++;

            status = true;
        }
        else{
            this.recordsInFile[this.numberOfRecords] = recordToAdd;
            this.numberOfRecords++;
            status = true;
        }

        return status;
    }
}
