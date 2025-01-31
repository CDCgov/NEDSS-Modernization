//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\NBSSecurityObj.java
package gov.cdc.nedss.systemservice.nbssecurity;

import java.io.*;
import java.util.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.util.*;

public class NBSSecurityObj
    implements Serializable {
	private static final long serialVersionUID = 1L;
	public UserProfile theUserProfile;
	static final LogUtils logger = new LogUtils(NBSSecurityObj.class.getName());
	private String errMessage = null;
  /**
   * This will contain an element for each unique SecurityRole Realized
   */

  public Collection<Object> thePermissionSetCollection;
 
  /**
       * The permissionSetCollection  is a collection of PermissionSet objects.  After
       * initializing theUserProfile and thePermissionSetCollection, this method should
       * call the private method findPermissionSets to establish the links between the
       * realized roles in the user profile and the permission sets in the collection.
   * @param userProfile
   * @param permissionSetCollection
   * @roseuid 3CDA9583013A
   */
  public NBSSecurityObj(UserProfile userProfile,
                        Collection<Object> permissionSetCollection) {
    theUserProfile = userProfile;
    thePermissionSetCollection  = permissionSetCollection;
    findPermissionSets();
  }

  /**
   * Access method for the theUserProfile property.
   *
   * @return   the current value of the theUserProfile property
   */
  public UserProfile getTheUserProfile() {

    return theUserProfile;
  }

  /**
   * Access method for the thePermissionSetCollection  property.
   *
   * @return   the current value of the thePermissionSetCollection  property
   */
  public Collection<Object> getThePermissionSetCollection() {

    return thePermissionSetCollection;
  }

  /**
   * Returns true if the user has the requested permission.  This method can be used
   * for all business objects.  If the name of a business object that  is not
       * secured by program area and/or jurisdiction is encountered, the program area
   * and/or jurisdiction passed in are ignored.  If working with a specific instance
   * of a business object that is secured by program area and/or jurisdiction,
   * please use the version of getPermission that takes shared as a parameter.
   * @param businessObjLookupName
   * @param operation
   * @return boolean
   * @roseuid 3CDA8C0A029C
   */
  public boolean getPermission(String businessObjLookupName,
                               String operation) {

    //logger.debug("You have called the getPermission method with only a object and operation");
    return this.getPermission(businessObjLookupName, operation,
                              ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                              ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
                              ProgramAreaJurisdictionUtil.SHAREDISTRUE);
  }

  /**
   * Returns the user's first and last name concatenated together.
   * @return String
   * @roseuid 3CDA8C1B0336
   */
  public String getFullName() {

    String fName = theUserProfile.getTheUser().getFirstName();
    String lName = theUserProfile.getTheUser().getLastName();
    String fullName = fName + " " + lName;

    return fullName;
  }

  /**
   * Returns a TreeMap<Object,Object> containing all the program areas the user has access to. Call
   * ProgramAreaJurisdictionUtil.getProgramAreas() to get the TreeMap.
   * @return TreeMap
   * @roseuid 3CDA94480119
   */
  public TreeMap<Object, Object> getProgramAreas() {

    return ProgramAreaJurisdictionUtil.getProgramAreas();
  }

  /**
   * Returns a TreeMap<Object,Object> containing all the program areas the user has access to. Call
   * ProgramAreaJurisdictionUtil.getProgramAreas() to get the TreeMap.
   * @return TreeMap
   * @roseuid 3CDA94480119
   */
  public TreeMap<Object, Object> getProgramAreas(String businessObjectLookupName,
                                 String operation) {

    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();

    for (Iterator<Object> it = theUserProfile.getTheRealizedRoleCollection().iterator();
         it.hasNext(); ) {

      RealizedRole realizedRole = (RealizedRole) it.next();
      boolean guest = realizedRole.getGuest();
      int businesObjOperationIndex = BusinessObjOperationUtil.
          getBusinesObjOperationIndex(
          businessObjectLookupName,
          operation);
      boolean permissionValue;

      if (guest) {
        permissionValue = realizedRole.getThePermissionSet().
            isOperationAvailableToGuest(
            businesObjOperationIndex);
      }
      else {
        permissionValue = realizedRole.getThePermissionSet().
            isOperationAvailableToOwner(
            businesObjOperationIndex);

      }
      String programAreaCode = "";

      if (permissionValue) {
        programAreaCode = realizedRole.getProgramAreaCode();

        String keyValue = ProgramAreaJurisdictionUtil.getProgramAreas()
            .get(programAreaCode).toString();
        treeMap.put(programAreaCode, keyValue);
      }
    }

    return treeMap;
  }
  /**
   * Returns a TreeMap<Object,Object> containing all the Jurisdiction the user has access to. Call
   * ProgramAreaJurisdictionUtil.getJurisdiction() to get the TreeMap.
   * @return TreeMap
   * @roseuid 3CDA94480119
   */
  public TreeMap<Object, Object> getJurisdiction(String businessObjectLookupName,
                                 String operation) {

    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();

    for (Iterator<Object> it = theUserProfile.getTheRealizedRoleCollection().iterator();
         it.hasNext(); ) {

      RealizedRole realizedRole = (RealizedRole) it.next();
      boolean guest = realizedRole.getGuest();
      int businesObjOperationIndex = BusinessObjOperationUtil.
          getBusinesObjOperationIndex(
          businessObjectLookupName,
          operation);
      boolean permissionValue;

      if (guest) {
        permissionValue = realizedRole.getThePermissionSet().
            isOperationAvailableToGuest(
            businesObjOperationIndex);
      }
      else {
        permissionValue = realizedRole.getThePermissionSet().
            isOperationAvailableToOwner(
            businesObjOperationIndex);

      }
      String jurisdictionCode = "";

      if (permissionValue) {
    	  jurisdictionCode = realizedRole.getJurisdictionCode();
   
    	  if(jurisdictionCode.equalsIgnoreCase("ALL")){
              treeMap=getJurisdictions();
              break;
        }

 
    
        String keyValue = ProgramAreaJurisdictionUtil.getJurisdictions()
            .get(jurisdictionCode).toString();        treeMap.put(jurisdictionCode, keyValue);
      }
    }

    return treeMap;
  }

  /**
   * Returns a TreeMap<Object,Object> of all the jurisdictions the user has access to. Call
   * ProgramAreaJurisdictionUtil.getJurisdictions() to get the TreeMap.
   * @return TreeMap
   * @roseuid 3CDA9490023F
   */
  public TreeMap<Object, Object> getJurisdictions() {

    return ProgramAreaJurisdictionUtil.getJurisdictions();
  }

  /**
       * Returns a where clause string that will restrict the records returned by the
       * query to the records owned by the program area jurisdiction combinations for
       * which the user has the requested business object operation.  The where clause
       * will take on the form of "PAJ in ( x,y,z)" where PAJ is a column containing a
   * hashed value for the program area and jurisdiction for the record and x,y,z are
       * similar hash values generated for the program area / jurisdiction combinations
   * for which the user has the requested business object operation.
   * @param businessObjLookupName
   * @param operation
   * @return String
   * @roseuid 3CDA949C0084
   */
  public String getDataAccessWhereClause(String businessObjLookupName,
                                         String operation) {

    return getDataAccessWhereClause(businessObjLookupName, operation, "");
  }

  /**
   * @param theRootDTInterface
   * @param businessObjLookupName
   * @param operation
   * @return boolean
   * @roseuid 3CDA94D00255
   */
  public boolean checkDataAccess(RootDTInterface theRootDTInterface,
                                 String businessObjLookupName,
                                 String operation) {

    String pa = theRootDTInterface.getProgAreaCd();
    String jd = theRootDTInterface.getJurisdictionCd();

    if ( (pa == null || jd == null) &&
        (getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
                       NBSOperationLookup.ASSIGNSECURITY)
         || getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
                          NBSOperationLookup.ASSIGNSECURITY))) {
      return true;
    }

    if (pa != null) {
      pa.trim();

    }
    if (jd != null) {
      jd.trim();

    }
    return getPermission(businessObjLookupName, operation, pa, jd,
                         theRootDTInterface.getSharedInd()
                         );
  }

  /**
   * Used to fill in the thePermissionSet field for each RealizedRole in the
   * theRealizedRoleCollection.
   * @roseuid 3CDA95C200CC
   */
  private void findPermissionSets() {
    if (theUserProfile == null || theUserProfile.getTheRealizedRoleCollection() == null || thePermissionSetCollection  == null) {
      return;
    }
    //logger.debug("Number of realized roles: " + theUserProfile.getTheRealizedRoleCollection().size());
    for (Iterator<Object> it = theUserProfile.getTheRealizedRoleCollection().iterator();
         it.hasNext(); ) {

      RealizedRole rRole = (RealizedRole) it.next();
      String roleName = rRole.getRoleName();
      rRole.thePermissionSet = fillPermissionSet(
          thePermissionSetCollection,
          roleName);
    }
  }

  /**
   * Returns true if the user has the requested permission.  This method can be used
   * for all business objects.  If the name of a business object that  is not
       * secured by program area and/or jurisdiction is encountered, the program area
   * and/or jurisdiction passed in are ignored.
   * @param businessObjLookupName
   * @param operation
   * @param programAreaCode
   * @param jurisdictionCode
   * @param shared
   * @return boolean
   * @roseuid 3CDAD151037E
   */
  public boolean getPermission(String businessObjLookupName,
                               String operation,
                               String programAreaCode,
                               String jurisdictionCode,
                               String shared) {
    //logger.debug("You have called the getPermission with all 5 parameters.");
    boolean permissionValue = false;
    boolean guest = false;
    //logger.debug("businessObjLookupName is: " + businessObjLookupName);
    //logger.debug("operation is: " + operation);
    //logger.debug("programAreaCode is: " + programAreaCode);
    //logger.debug("jurisdictionCode is: " + jurisdictionCode);
    //logger.debug("shared is: " + shared);

    try {

      if (!NBSBOLookup.isSecuredByProgramArea(businessObjLookupName)) {
        //logger.debug(businessObjLookupName + " is not secured by program area");
        programAreaCode = ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA;
      }

      if (!NBSBOLookup.isSecuredByJurisdiction(businessObjLookupName)) {
        //logger.debug( businessObjLookupName +  " is not secured by jurisdiction");
        jurisdictionCode = ProgramAreaJurisdictionUtil.ANY_JURISDICTION;
      }

      if (programAreaCode.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA) &&
          jurisdictionCode.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
        //logger.debug(businessObjLookupName + ": Both ******ANY PA and ANY J.");
        shared = ProgramAreaJurisdictionUtil.SHAREDISTRUE;

        ArrayList<Object>  theRealizedRoleCollection  = new ArrayList<Object> ();
        theRealizedRoleCollection  = (ArrayList<Object> ) theUserProfile.
            getTheRealizedRoleCollection();

        Iterator<Object> it = theRealizedRoleCollection.iterator();
        //logger.debug("NUMBER OF ROLES: " + theRealizedRoleCollection.size());

        int count = 1;

        while (it.hasNext()) {
          //  logger.debug("LOOP NUMBER: " + count);
          count = count + 1;

          RealizedRole realizedRole = (RealizedRole) it.next();
          //logger.debug("PERMISSION SET NAME IS: " + realizedRole.getRoleName());
          guest = realizedRole.getGuest();

          if ( (guest == false) ||
              ( (guest == true) &&
               (shared.equalsIgnoreCase(ProgramAreaJurisdictionUtil.SHAREDISTRUE)))) {

            int index = BusinessObjOperationUtil.getBusinesObjOperationIndex(
                businessObjLookupName, operation);
            //  logger.debug("CHECK 1");
            //logger.debug("VALUE OF THE INDEX IS: " + index);

            //Finds the permissionset for this particular role
            PermissionSet permissionSet = null;

            for (Iterator<Object> permIt = thePermissionSetCollection.iterator();
                 permIt.hasNext(); ) {

              PermissionSet set = (PermissionSet) permIt.next();
              //  logger.debug("CHECK GETPERMISSION FOR PERMISSIONSET: " + set.getRoleName());

              if (set != null && set.getRoleName() != null &&
                  (set.getRoleName().equalsIgnoreCase(realizedRole.getRoleName()))) {
                permissionSet = set;

                break;
              }
            }

            if (guest == true && permissionSet != null) {
              //logger.debug("permissionSet.isOperationAvailableToGuest(index): " + permissionSet.isOperationAvailableToGuest(index));
              permissionValue = permissionSet.isOperationAvailableToGuest(
                  index);
            }
            else if (permissionSet != null) {
              //logger.debug("permissionSet.isOperationAvailableToOwner(index): " + permissionSet.isOperationAvailableToOwner(index));
              permissionValue = permissionSet.isOperationAvailableToOwner(
                  index);

              if (permissionValue) {

                return true;
              }
            }

            //if(permissionValue==true)
            // return permissionValue;
          }
        }
      }
      else if (programAreaCode.equalsIgnoreCase(
          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA) ||
               jurisdictionCode.equalsIgnoreCase(
          ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
        //logger.debug(businessObjLookupName +": ONE -----ANY PA or ANY J.");
        shared = ProgramAreaJurisdictionUtil.SHAREDISTRUE;

        ArrayList<Object>  theRealizedRoleCollection  = new ArrayList<Object> ();
        theRealizedRoleCollection  = (ArrayList<Object> ) theUserProfile.
            getTheRealizedRoleCollection();

        Iterator<Object> it = theRealizedRoleCollection.iterator();

        while (it.hasNext()) {

          RealizedRole realizedRole = (RealizedRole) it.next();
          String realizedProgramAreaCode = "";
          String realizedJurisdictionCode = "";
          //  logger.debug("programAreaCode is: " + programAreaCode);
          //logger.debug("jurisdictionCode is: " + jurisdictionCode);

          if (!programAreaCode.equalsIgnoreCase(
              ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA)) {
            realizedProgramAreaCode = realizedRole.getProgramAreaCode();
            realizedJurisdictionCode = jurisdictionCode;
            //  logger.debug("realizedJurisdictionCode: " + realizedJurisdictionCode);
            //logger.debug("realizedProgramAreaCode: " + realizedProgramAreaCode);
          }

          if (!jurisdictionCode.equalsIgnoreCase(
              ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
            realizedJurisdictionCode = realizedRole.getJurisdictionCode();
            realizedProgramAreaCode = programAreaCode;
            //logger.debug("realizedJurisdictionCode: " + realizedJurisdictionCode);
            //logger.debug("realizedProgramAreaCode: " + realizedProgramAreaCode);
          }

          if (!programAreaCode.equalsIgnoreCase(
              ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA) ||
              !jurisdictionCode.equalsIgnoreCase(
              ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {

            //logger.debug("Do the following PA Code values match?");
            //logger.debug("realizedPAcode: " + realizedProgramAreaCode);
            //logger.debug("programAreaCode: " + programAreaCode);
            //logger.debug("Do the following Juris codes values match?");
            //logger.debug("realizedJurisdictionCode: " + realizedJurisdictionCode);
            //logger.debug("jurisdictionCode: " + jurisdictionCode);
            if (realizedProgramAreaCode.equalsIgnoreCase(programAreaCode) &&
                realizedJurisdictionCode.equalsIgnoreCase(jurisdictionCode)) {
              guest = realizedRole.getGuest();

              if ( (guest == false) || ( (guest == true) &&
                                        (shared.equalsIgnoreCase(
                  ProgramAreaJurisdictionUtil.SHAREDISTRUE)))) {
                int index = BusinessObjOperationUtil.
                    getBusinesObjOperationIndex(
                    businessObjLookupName,
                    operation);

                //Finds the permissionset for this particular role
                PermissionSet permissionSet = null;

                for (Iterator<Object> permIt = thePermissionSetCollection.iterator();
                     permIt.hasNext(); ) {

                  PermissionSet set = (PermissionSet) permIt.next();

                  if (set != null &&
                      set.getRoleName() != null &&
                      (set.getRoleName().equalsIgnoreCase(realizedRole.getRoleName()))) {
                    permissionSet = set;

                    break;
                  }
                }

                if (guest == true) {
                  permissionValue = permissionSet.isOperationAvailableToGuest(
                      index);
                }
                else {
                  permissionValue = permissionSet.isOperationAvailableToOwner(
                      index);
                }

                if (permissionValue == true) {

                  return permissionValue;
                }
              }
              //the following "else if" is because there was not logic that allowed
              //a user with global permissions to view an ELR observation needing ProgramArea Assignment
              //when a jurisdiction was assigned, but there was not a Program Area Assigned.
              //Issue brought to Xianon Zheng, who stated he would begin looking at the Security issues.
            }
            else if (realizedProgramAreaCode.equalsIgnoreCase("ANY") &&
                     (realizedJurisdictionCode.equalsIgnoreCase(jurisdictionCode) ||
                      realizedJurisdictionCode.equalsIgnoreCase("ALL"))) {
              return true;
            }

          }
        }
      }
      else {

        ArrayList<Object>  theRealizedRoleCollection  = new ArrayList<Object> ();
        theRealizedRoleCollection  = (ArrayList<Object> ) theUserProfile.
            getTheRealizedRoleCollection();

        Iterator<Object> it = theRealizedRoleCollection.iterator();

        while (it.hasNext()) {

          RealizedRole realizedRole = (RealizedRole) it.next();
          //logger.debug("realizedRole.getProgramAreaCode(): " + realizedRole.getProgramAreaCode());
          //logger.debug("realizedRole.getJurisdictionCode(): " + realizedRole.getJurisdictionCode());
          //logger.debug("ProgramAreaCode " + programAreaCode);
          //logger.debug("getJurisdictionCode " + jurisdictionCode);

          if ( (programAreaCode.equalsIgnoreCase(realizedRole.getProgramAreaCode()) &&
                realizedRole.getJurisdictionCode().equalsIgnoreCase(
              ProgramAreaJurisdictionUtil.ALL_JURISDICTIONS)) ||
              (programAreaCode.equalsIgnoreCase(realizedRole.getProgramAreaCode()) &&
               jurisdictionCode.equalsIgnoreCase(realizedRole.getJurisdictionCode()))) {
            guest = realizedRole.getGuest();

            if ( (guest == true) &&
                (shared.equalsIgnoreCase(
                ProgramAreaJurisdictionUtil.SHAREDISFALSE))) {

              return permissionValue = false;
            }

            int index = BusinessObjOperationUtil.getBusinesObjOperationIndex(
                businessObjLookupName, operation);

            //Finds the permissionset for this particular role
            PermissionSet permissionSet = null;

            for (Iterator<Object> pit = thePermissionSetCollection.iterator();
                 pit.hasNext(); ) {

              PermissionSet set = (PermissionSet) pit.next();

              if (set != null && set.getRoleName() != null &&
                  (set.getRoleName().equalsIgnoreCase(realizedRole.getRoleName()))) {
                permissionSet = set;
                if (guest == true) {

                  permissionValue = permissionSet.isOperationAvailableToGuest(
                      index);
                }
                else {

                  permissionValue = permissionSet.isOperationAvailableToOwner(
                      index);
                }
                if (permissionValue == true) {
                  break;
                }
              }
            }
          }
          if (permissionValue == true) {
            break;
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return permissionValue;
  } //end of getPermission with all 5 parameters

  /**
       * Returns a where clause string that will restrict the records returned by the
       * query to the records owned by the program area jurisdiction combinations for
   * which the user has the requested business object operation.  The alias
       * parameter is used to refer to a specific table in the query. The where clause
   * will take on the form of "alias.PAJ in ( x,y,z)" where alias is the alias
       * passed in, PAJ is a column containing a hashed value for the program area and
   * jurisdiction for the record and x,y,z are similar hash values generated for the
       * program area / jurisdiction combinations for which the user has the requested
   * business object operation.
   * @param businessObjLookupName
   * @param operation
   * @param alias
   * @return String
   * @roseuid 3CDAEBDE015B
   */
  public String getDataAccessWhereClause(String businessObjLookupName,
                                         String operation, String alias) {

    String whereClause = null;
    String columnName = null;
    String ownerList = null;
    String guestList = null;
    boolean paSecured = NBSBOLookup.isSecuredByProgramArea(
        businessObjLookupName);

    boolean jSecured = NBSBOLookup.isSecuredByJurisdiction(
        businessObjLookupName);
    //logger.debug("paSecured: " + paSecured);
    //logger.debug("jSecured: " + jSecured);

    //If the record is secured by both program area and jurisdiction, do the following
    if (paSecured && jSecured) {
      columnName = "program_jurisdiction_oid";
      ownerList = getHashedPAJList(businessObjLookupName, operation,
                                   false);
      // logger.debug("ownerList: " + ownerList);
      guestList = getHashedPAJList(businessObjLookupName, operation,
                                   true);
      //logger.debug("guestList: " + guestList);
      whereClause = buildWhereClause(ownerList, guestList, columnName,
                                     alias,true, businessObjLookupName);
    }
    else if (paSecured || jSecured) {
      //If the record is secured by program area only, do the following
      if (paSecured) {
        columnName = "prog_area_cd";
        ownerList = getPAList(businessObjLookupName, operation, false);

        guestList = getPAList(businessObjLookupName, operation, true);
        whereClause = buildWhereClause(ownerList, guestList,
                                       columnName, alias, false, businessObjLookupName);
      }

      //if the record is secured by jurisdiction only, do the following
      else if (jSecured) {
        columnName = "jurisdiction_cd";
        ownerList = getJList(businessObjLookupName, operation, false);
        guestList = getJList(businessObjLookupName, operation, true);
        whereClause = buildWhereClause(ownerList, guestList,
                                       columnName, alias, false, businessObjLookupName);
      }
    }

    //If the record is not secured by program area or jurisdiction, do this
    else if (!paSecured && !jSecured) {
      whereClause = null;
    }

    return whereClause;
  }

  /**
   * Returns a comma delimited list of hashed program area jurisdiction combinations
   * for which the user has the specified privileges.  Used by
   * getDatAccessWhereClause.
   * @param businessObjLookupName
   * @param operation
   * @param guest
   * @return String
   * @roseuid 3CDB20B50257
   */
  private String getHashedPAJList(String businessObjLookupName,
                                  String operation, boolean guest) {
	PropertyUtil propertyUtil= PropertyUtil.getInstance();
    Collection<Object> allPAJList = new HashSet<Object>();
    StringBuffer hashedPAJList = new StringBuffer();
    int index = BusinessObjOperationUtil.getBusinesObjOperationIndex(
        businessObjLookupName, operation);

    for (Iterator<Object> it = theUserProfile.getTheRealizedRoleCollection().iterator();
         it.hasNext(); ) {
      RealizedRole rRole = (RealizedRole) it.next();

      if (rRole.getGuest() == guest) { //only consider roles that match the requested guest status
        PermissionSet set = rRole.getThePermissionSet();
        boolean isOpAvailable = false;
        //logger.debug("rRole.getRoleName() = " + rRole.getRoleName());
        if (guest) {
          isOpAvailable = set.isOperationAvailableToGuest(index);
          //              logger.debug("guest isOpAvailable = " + isOpAvailable);
        }
        else {
          isOpAvailable = set.isOperationAvailableToOwner(index);
          //            logger.debug("owner isOpAvailable = " + isOpAvailable);
        }

        if (isOpAvailable) {
          String paCd = rRole.getProgramAreaCode();
          String jCd = rRole.getJurisdictionCode();
          Collection<Object> pajCds = ProgramAreaJurisdictionUtil.getPAJHashList(paCd,
              jCd);
          allPAJList.addAll(pajCds);
        }
      }
    }

    for (Iterator<Object> allIt = allPAJList.iterator(); allIt.hasNext(); ) {

      Long cd = (Long) allIt.next();
      String databaseType = NEDSSConstants.SQL_SERVER_ID;
      // Execute the SQL Server Queries if reporting module, SAS Queries does not execute the Oracle Syntax below for security clause
      if(businessObjLookupName!=null && businessObjLookupName.equalsIgnoreCase(NBSBOLookup.REPORTING))
    	  databaseType=NEDSSConstants.SQL_SERVER_ID;
      if(databaseType != null && !databaseType.equalsIgnoreCase(NEDSSConstants.SQL_SERVER_ID)){
    	  if (cd != null) {
              if (cd.toString().trim().length() != 0) {
                hashedPAJList = hashedPAJList.append("(1,").append(cd).append("), ");
              }
          }
      }else{
          if (cd != null) {
              if (cd.toString().trim().length() != 0) {
                hashedPAJList = hashedPAJList.append(cd).append(", ");
              }
          }
      }
    	  
      }


    if (hashedPAJList.toString().trim().length() > 0) {
      //logger.debug("hashedPAJList.toString().length(): " + hashedPAJList.toString().trim().substring(0,(hashedPAJList.toString().trim().length() - 1)));

      return hashedPAJList.toString().trim().substring(0,
          (hashedPAJList.toString()
           .trim().length() - 1));
    }
    else {
      //logger.debug("hashedPAJList.toString().length(): " + hashedPAJList.toString());

      return hashedPAJList.toString();
    }
  }

  /**
   * Returns a comma delimited list of program area codes for which the user has the
   * given business object operation. Used by getDatAccessWhereClause.
   * @param businessObjLookupName
   * @param operation
   * @param guest
   * @return String
   * @roseuid 3CE118E0017E
   */
  private String getPAList(String businessObjLookupName, String operation,
                           boolean guest) {

    ArrayList<Object>  arPAList = new ArrayList<Object> ();
    int businesObjOperationIndex = BusinessObjOperationUtil.
        getBusinesObjOperationIndex(
        businessObjLookupName,
        operation);
    ArrayList<Object>  theRealizedRoleCollection  = new ArrayList<Object> ();
    theRealizedRoleCollection  = (ArrayList<Object> ) theUserProfile.
        getTheRealizedRoleCollection();

    Iterator<Object> it = theRealizedRoleCollection.iterator();

    while (it.hasNext()) {

      RealizedRole realizedRole = (RealizedRole) it.next();
      boolean realizedGuest = realizedRole.getGuest();

      if (guest == realizedGuest) {

        PermissionSet permissionSet = realizedRole.getThePermissionSet();

        if (guest) {

          if (permissionSet.isOperationAvailableToGuest(
              businesObjOperationIndex)) {

            String programAreaCode = realizedRole.getProgramAreaCode();

            if (!arPAList.contains(programAreaCode)) {
              arPAList.add(programAreaCode);
            }
          }
        }
        else {

          if (permissionSet.isOperationAvailableToOwner(
              businesObjOperationIndex)) {

            String programAreaCode = realizedRole.getProgramAreaCode();

            if (!arPAList.contains(programAreaCode)) {
              arPAList.add(programAreaCode);
            }
          }
        }
      }
    }

    String sPAList = "";

    if (arPAList.size() > 0) {
      sPAList = "\'" + arPAList.get(0).toString() + "\'";

      for (int x = 1; x < arPAList.size(); x++) {
        sPAList += "," + "\'" + arPAList.get(x).toString() + "\'";
      }
    }

    return sPAList;
  }

  /**
   * Returns a comma delimited list of jurisdiction codes for which the user has the
   * given business object operation. Used by getDatAccessWhereClause.
   * @param businessObjLookupName
   * @param operation
   * @param guest
   * @return String
   * @roseuid 3CE1193700ED
   */
  private String getJList(String businessObjLookupName, String operation,
                          boolean guest) {

    // get the Objects and Operation tree maps from the static class
    String jurisList = null;
    String juris = null;
    Iterator<Object> tmKeys;
    Object tmKey;
    int index;
    boolean all_jurisdictions = false;

    // initialize reference objects
    BusinessObjOperationUtil boputil = null;
    ProgramAreaJurisdictionUtil progutil = null;

    // get the index of the bo lookup name, operation
    index = boputil.getBusinesObjOperationIndex(businessObjLookupName,
                                                operation);

    // build a list of jurisdictions for each realized role in the realized role collection
    ArrayList<Object>  realRoleCollection  = (ArrayList<Object> ) theUserProfile.
        getTheRealizedRoleCollection();

    for (Iterator<Object> anIterator = realRoleCollection.iterator();
         anIterator.hasNext(); ) {

      //getGuest from the Realized role
      RealizedRole realRole = (RealizedRole) anIterator.next();

      if (realRole.getGuest() == guest) {

        if ( (guest && realRole.thePermissionSet.isOperationAvailableToGuest(
            index)) ||
            ( (!guest) &&
             realRole.thePermissionSet.isOperationAvailableToOwner(
            index))) { // if guest parameter is true
          juris = realRole.getJurisdictionCode();

          if (! (juris.equalsIgnoreCase(progutil.ALL_JURISDICTIONS))) {
            jurisList = jurisList + "," + juris; //concatenate to the jurisdiction list
          }
          else {
            all_jurisdictions = true;

            break; // break out of this for loop and return a list of all jurisdictions
          }
        }
      }
    } // end of for

    if (all_jurisdictions) {
      jurisList = ""; // blank out the juris list

      TreeMap<Object, Object> tmjuris = progutil.getJurisdictions();

      // parse the tree map and concatenate to the jurislist
      tmKeys = tmjuris.keySet().iterator();

      while (tmKeys.hasNext()) {
        tmKey = tmKeys.next();
        jurisList = tmjuris.get(tmKey).toString() + jurisList;
      }
    }

    return jurisList;
  }

  /**
   * Returns true if the user has the requested permission.  This method can be used
   * for all business objects.  If the name of a business object that  is not
       * secured by program area and/or jurisdiction is encountered, the program area
   * and/or jurisdiction passed in are ignored.  If working with a specific instance
   * of a business object that is secured by program area and/or jurisdiction,
   * please use the version of getPermission that takes shared as a parameter.
   * @param businessObjLookupName
   * @param operation
   * @param programAreaCode
   * @param jurisdictionCode
   * @return boolean
   * @roseuid 3CE184F70390
   */
  public boolean getPermission(String businessObjLookupName,
                               String operation, String programAreaCode,
                               String jurisdictionCode) {

    //logger.debug("You have called the getPermission method with 4 parameters.");
    return this.getPermission(businessObjLookupName, operation,
                              programAreaCode, jurisdictionCode,
                              ProgramAreaJurisdictionUtil.SHAREDISTRUE);
  }

  /**
   * Returns a String composed of program area jurisdiction combinations in the
   * following format: "PA-J|PA-J|PA-J|"
   * @param businessObjLookupName
   * @param operation
   * @return String
   * @roseuid 3CE1DFF80104
   */
  public String getProgramAreaJurisdictions(String businessObjLookupName,
                                            String operation) {

    String returnString = "";
    String paCode = null;
    String jCode = null;

    for (Iterator<Object> it = theUserProfile.getTheRealizedRoleCollection().iterator();
         it.hasNext(); ) {

      RealizedRole role = (RealizedRole) it.next();
      boolean guest = role.getGuest();
      int booIndex = BusinessObjOperationUtil.getBusinesObjOperationIndex(
          businessObjLookupName, operation);
      boolean permitted = false;

      // commented out guest checking as guest may not have create persmission
      // for some of business objects, still he can crete them and view them.
      // Defect 5743. SD
      /*  if (guest)
        {
            permitted = role.getThePermissionSet().isOperationAvailableToGuest(
           booIndex);
        }
        else
        {*/
      permitted = role.getThePermissionSet().isOperationAvailableToOwner(
          booIndex);
      // }
      //  System.out.println(permitted);
      if (permitted) {
        paCode = role.getProgramAreaCode();
        jCode = role.getJurisdictionCode();

        if (jCode != null &&
            jCode.equalsIgnoreCase(ProgramAreaJurisdictionUtil.ALL_JURISDICTIONS)) {

          TreeMap<Object, Object> jCodes = ProgramAreaJurisdictionUtil.getJurisdictions();

          for (Iterator<Object> i = jCodes.keySet().iterator(); i.hasNext(); ) {

            String aJCode = (String) i.next();
            returnString += paCode + "$" + aJCode + "|";
          }
        }
        else {
          returnString += paCode + "$" + jCode + "|";
        }
      }
    }

    return returnString;
  }

  /**
       * Returns the user's EntryID.  This was originaly the LDAP Entry ID, but it does
       * not necessarily have to remain that.  It can be any unique numeric ID assigned
   * to a user when the user is created.  This ID is used for such things as
   * identifying the owner of a specific Report in the system.
   * @return String
   * @roseuid 3CE3138E0377
   */
  public String getEntryID() {

    String strEntryID = theUserProfile.getTheUser().getEntryID();

    return strEntryID;
  }

  /**
   * @param psColl
   * @param roleName
   * @return gov.cdc.nedss.nbssecurity.helpers.PermissionSet
   * @roseuid 3CEB253B0232
   */
  private PermissionSet fillPermissionSet(Collection<Object> psColl, String roleName) {

    PermissionSet findPermissionSet = null;

    for (Iterator<Object> it = psColl.iterator(); it.hasNext(); ) {

      PermissionSet ps = (PermissionSet) it.next();

      if (roleName != null && ps.getRoleName() != null &&
          ps.getRoleName().trim().equalsIgnoreCase(roleName.trim())) {
        findPermissionSet = ps;

        break;
      }
    }

    return findPermissionSet;
  }

  /**
   * @param ownerList
   * @param columnName
   * @param alias
   * @return String
   * @roseuid 3CEB25400059
   */
  private String buildOwnerWhereClause(String ownerList, String columnName,
                                       String alias, boolean OIDFlag, String businessObjLookupName) {
    String whereClauseOwner = "";
	  String databaseType = NEDSSConstants.SQL_SERVER_ID;
	  if(businessObjLookupName!=null && businessObjLookupName.equalsIgnoreCase(NBSBOLookup.REPORTING))
    	  databaseType=NEDSSConstants.SQL_SERVER_ID;
      if(databaseType != null && !databaseType.equalsIgnoreCase(NEDSSConstants.SQL_SERVER_ID) && OIDFlag){
  	    if (ownerList != null && ownerList.trim().length() != 0) {

	        if (alias == null || alias.trim().length() == 0) {
	          whereClauseOwner = "((1," + columnName + ") in (" + ownerList +
	              "))";
	        }
	        else {
	          whereClauseOwner = "((1," + alias + "." + columnName + ") in (" +
	              ownerList + "))";
	        }
	      }
	      else {
	        whereClauseOwner = null;
	      }

      }else{
    	    if (ownerList != null && ownerList.trim().length() != 0) {

    	        if (alias == null || alias.trim().length() == 0) {
    	          whereClauseOwner = "(" + columnName + " in (" + ownerList +
    	              "))";
    	        }
    	        else {
    	          whereClauseOwner = "(" + alias + "." + columnName + " in (" +
    	              ownerList + "))";
    	        }
    	      }
    	      else {
    	        whereClauseOwner = null;
    	      }
      }
    return whereClauseOwner;
  }

  /**
   * @param guestList
   * @param columnName
   * @param alias
   * @return String
   * @roseuid 3CEB2540022F
   */
  private String buildGuestWhereClause(String guestList, String columnName,
                                       String alias, boolean OIDFlag, String businessObjLookupName) {

    //logger.debug("alias = " + alias);
    String whereClauseGuest = "";
    PropertyUtil propertyUtil= PropertyUtil.getInstance();
 //   if(databaseType != null && databaseType.equalsIgnoreCase(NEDSSConstants.ORACLE_ID)){
    String databaseType = NEDSSConstants.SQL_SERVER_ID;
    if(businessObjLookupName!=null && businessObjLookupName.equalsIgnoreCase(NBSBOLookup.REPORTING))
  	  databaseType=NEDSSConstants.SQL_SERVER_ID;
    if(databaseType != null && !databaseType.equalsIgnoreCase(NEDSSConstants.SQL_SERVER_ID) && OIDFlag){
    if (guestList != null && guestList.trim().length() != 0) {

      if (alias == null || alias.trim().length() == 0) {
        whereClauseGuest = "(((1," + columnName + ") in (" + guestList +
            ")) and  shared_ind = '" +
            ProgramAreaJurisdictionUtil.SHAREDISTRUE +
            "')";
      }
      else {
        whereClauseGuest = "(((1," + alias + "." + columnName + ") in (" + 
        		guestList +
            ")) and " + alias + ".shared_ind = '" +
            ProgramAreaJurisdictionUtil.SHAREDISTRUE +
            "')";
      }
    }
    
    else {
      whereClauseGuest = null;
    }
    }else{
    	
    	if (guestList != null && guestList.trim().length() != 0) {

    	      if (alias == null || alias.trim().length() == 0) {
    	        whereClauseGuest = "(("+ columnName + " in (" + guestList +
    	            ")) and  shared_ind = '" +
    	            ProgramAreaJurisdictionUtil.SHAREDISTRUE +
    	            "')";
    	      }
    	      else {
    	        whereClauseGuest = "((" + alias + "." + columnName +
    	            " in (" + guestList +
    	            ")) and " + alias + ".shared_ind = '" +
    	            ProgramAreaJurisdictionUtil.SHAREDISTRUE +
    	            "')";
    	      }
    	    }
    	    else {
    	      whereClauseGuest = null;
    	    }
    	
    }
    

    return whereClauseGuest;
  }

  /**
   * @param ownerList
   * @param guestList
   * @param columnName
   * @param alias
   * @return String
   * @roseuid 3CEB2541000A
   */
  private String buildWhereClause(String ownerList, String guestList,
                                  String columnName, String alias, boolean OIDFlag, String businessObjLookupName) {

    String finalWhereClause = "";
    String whereClauseOwner = buildOwnerWhereClause(ownerList, columnName,
        alias, OIDFlag, businessObjLookupName);
    String whereClauseGuest = buildGuestWhereClause(guestList, columnName,
        alias, OIDFlag, businessObjLookupName);
    //logger.debug("whereClauseOwner: " + whereClauseOwner);
    //logger.debug("whereClauseGuest: " + whereClauseGuest);

    if ( (whereClauseOwner != null &&
          whereClauseOwner.trim().length() != 0) &&
        (whereClauseGuest != null &&
         whereClauseGuest.trim().length() != 0)) {
      finalWhereClause = "(" + whereClauseOwner + " or " +
          whereClauseGuest + ")";
    }
    else if ( (whereClauseOwner != null &&
               whereClauseOwner.trim().length() != 0) &&
             (whereClauseGuest == null ||
              whereClauseGuest.trim().length() == 0)) {
      finalWhereClause = "(" + whereClauseOwner + ")";
    }
    else if ( (whereClauseOwner == null ||
               whereClauseOwner.trim().length() == 0) &&
             (whereClauseGuest != null &&
              whereClauseGuest.trim().length() != 0)) {
      finalWhereClause = "(" + whereClauseGuest + ")";
    }
    else if ( (whereClauseOwner == null ||
               whereClauseOwner.trim().length() == 0) &&
             (whereClauseGuest == null ||
              whereClauseGuest.trim().length() == 0)) {
      finalWhereClause = "(0=1)";
    }

    return finalWhereClause;
  }

  public void setMessage(String message) {
    errMessage = message;
  }

  public String getMessage() {
    return errMessage;
  }

  /**
       * Check if login user is a msa or not by the information stored in userProfile
   * @return boolean
   */

  public boolean isUserMSA() {
    String strAdminUserTypes = theUserProfile.getTheUser().getAdminUserType();
    if (strAdminUserTypes == null) {
      return false;
    }
    else if (strAdminUserTypes.length() > 3 || strAdminUserTypes.startsWith("M")) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
       * Check if login user is a paa or not by the information stored in userProfile
   * @return boolean
   */
  public boolean isUserPAA() {
    String strAdminUserTypes = theUserProfile.getTheUser().getAdminUserType();
    if (strAdminUserTypes == null) {
      return false;
    }
    if (strAdminUserTypes.length() > 3 || strAdminUserTypes.startsWith("P")) {
      return true;
    }
    else {
      return false;
    }
  }

  public static void main(String[] args) {
  }
}
