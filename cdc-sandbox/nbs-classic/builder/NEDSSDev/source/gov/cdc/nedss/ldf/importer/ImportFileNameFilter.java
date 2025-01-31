package gov.cdc.nedss.ldf.importer;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ImportFileNameFilter implements FilenameFilter{
  public ImportFileNameFilter() {
  }

  public boolean accept(File dir, String name){
    if(name.endsWith("xml"))
      return true;
    else
       return false;
  }

}