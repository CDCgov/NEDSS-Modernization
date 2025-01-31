//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbscontext\\helpers\\NBSContext.java
package gov.cdc.nedss.systemservice.nbscontext;

import gov.cdc.nedss.util.*;

import java.util.*;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.*;
import javax.xml.transform.dom.*;

import org.w3c.dom.*;


/**
 * This is the NBS Context Manager.  At system startup, the initializeNBSContext
 * method is called to initialize the object.  All methods are static so there is
 * no need to create an instance of the object.  The ContextMap is static and
 * shared across all sessions.  The Object Store is implemented in the Session
 * Object of each individual session.  All user related information is stored in
 * the Object Store.
 */
public class NBSContext
{

    static final LogUtils logger = new LogUtils(NBSContext.class.getName());
    public static final String configFileName = NBSConstantUtil.configFileName;

    /**
    * A TreeMap<Object,Object> containing an element for each page in the system.  The Key is the
    * PageID and the Value is an NBSPage object.
    */
    public static TreeMap<Object,Object> theContextMap;
    public static NBSPage theGlobalPage;
    private static boolean Initialized = false;

    /**
    * @roseuid 3CEA52700197
    */
    public NBSContext()
    {
    }

    /**
    * Access method for the theContextMap property.
    *
    * @return   the current value of the theContextMap property
    */
    public static TreeMap<Object,Object> getTheContextMap()
    {

	return theContextMap;
    }

    /**
    * Places an object into the Object Store under the given name.
    *
    * @param sessionObj
    * @param objName
    * @param objToStore
    * @roseuid 3CE334FA008B
    */
    public static void store(HttpSession sessionObj, String objName,
			     Object objToStore)
    {
       if (objToStore == null)
       {
          throw new NullPointerException (
              "NBSContext.OBJECT_STORE:Attempt to store a Null object to " + objName);
        }
	NBSObjectStore map = (NBSObjectStore)sessionObj.getAttribute(
			      NBSConstantUtil.OBJECT_STORE);
	map.put(objName, objToStore);

	return;
    }

    /**
    * Retrieves an object from the Object Store.  This does not remove the object
    * from the object store.
    *
    * @param sessionObj
    * @param objName
    * @return Object
    * @roseuid 3CE3351801E2
    */
    public static Object retrieve(HttpSession sessionObj, String objName)
    {

	NBSObjectStore NBSObjectStore = (NBSObjectStore)sessionObj.getAttribute(
					 NBSConstantUtil.OBJECT_STORE);
	Object object = NBSObjectStore.get(objName);
        if (object == null) {
          if (NBSObjectStore.containsKey(objName))
          {
              throw new NullPointerException ("NBSContext.OBJECT_STORE: the retrieved object (" +
                objName + ")is NULL");
          } else {
               throw new NullPointerException ("NBSContext.OBJECT_STORE: the object (" +
            objName + ") is not in the object store");
          }
        }
	return object;
    }

    /**
    * Removes the named object from the object store.  This method is private because

    * Removes the named object from the object store.  This method is private because
    * context management controls when objects are removed from the object store.
    *
    * @param theNBSObjectStore
    * @param objName
    * @roseuid 3CE335240121
    */
    private static void remove(NBSObjectStore theNBSObjectStore, String objName)
    {
	Object obj = theNBSObjectStore.remove(objName);
        obj = null;
	return;
    }

    /**
    * Removes all user stored objects from the object store except for those whose
    * names are listed in the objNameExceptions collection.  This is a collection of
    * Strings. This method is private because context management controls when

   /**
    * Removes all user stored objects from the object store except for those whose
    * names are listed in the objNameExceptions collection.  This is a collection of
    * Strings. This method is private because context management controls when
    * objects are removed from the object store.
    *
    * @param theNBSObjectStore
    * @param objNameExceptions
    * @roseuid 3CE3352E0126
    */
    private static void removeAllExcept(NBSObjectStore theNBSObjectStore,
					Collection<Object>  objNameExceptions)
    {
	//objNameExceptions.add(NBSConstantUtil.CONTEXT_INFO);
	Set<Object> set = theNBSObjectStore.keySet();
	String [] arrkeys = (String []) set.toArray(new String [0]);
	for (int i=0; i<arrkeys.length; i++)
	{
	  if (!arrkeys[i].equals(NBSConstantUtil.CONTEXT_INFO)
          && !objNameExceptions.contains(arrkeys[i]))
	  {
	    Object obj = theNBSObjectStore.remove(arrkeys[i]);
            obj= null;
	  }
	}
    }

    /**
    * Starts a new task.  The TaskName is stored in the object store as a system
    * stored object with the name SYS_CURRENT_TASK.
    *
    * @param theNBSObjectStore
    * @param newTaskName
    * @param currentPage
    * @roseuid 3CE335380166
    */
    private static void startTask(NBSObjectStore theNBSObjectStore,
				  String newTaskName, NBSPage currentPage)
    {

	Collection<Object>  preservedObjects = null;
	NBSPageContext nBSPageContext = null;
	NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)theNBSObjectStore.get(
							 NBSConstantUtil.NBSCONTEXTINFO);
	String currentTaskName = contextSystemInfo.getCurrentTaskName();
	//logger.debug("startTask.1:currentTaskName:= " + currentTaskName);

	if (currentTaskName != NBSConstantUtil.NONE)
	    endTask(theNBSObjectStore, currentTaskName, currentPage);
//	    endTask(theNBSObjectStore, newTaskName, currentPage);

	contextSystemInfo.setCurrentTaskName(newTaskName);
    }

    /**
    * Ends the named task.
    *
    * @param theNBSObjectStore
    * @param taskName
    * @param currentPage
    * @roseuid 3CE3353F0239
    */
    private static void endTask(NBSObjectStore theNBSObjectStore, String taskName,
				NBSPage currentPage)
    {

	NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)theNBSObjectStore.get(
							 NBSConstantUtil.NBSCONTEXTINFO);
	String currentTaskName = contextSystemInfo.getCurrentTaskName();
	Collection<Object>  preserveObjects = null;

	if (currentTaskName.equals(taskName))
	{

	    String pageID = currentPage.getPageID();

	    if (pageID.equals(NBSConstantUtil.GLOBAL_CONTEXT))
	    {

		if (currentPage.theTaskOverridesTreeMap.get(currentTaskName) != null)
		{

		    NBSPageContext pgContext = (NBSPageContext)currentPage.theTaskOverridesTreeMap.get(
						       currentTaskName);
		    preserveObjects = pgContext.getPreserveObjs();
		} //end if
		else
		{
		    preserveObjects = currentPage.getTheDefaultPageContext().getPreserveObjs();
		} //end else

		removeAllExcept(theNBSObjectStore, preserveObjects);
	    } //end if(pageID.equals("GLOBAL_CONTEXT"))

	    contextSystemInfo.setPrevTaskName(currentTaskName);
	    contextSystemInfo.setCurrentTaskName(NBSConstantUtil.NONE);
	} //end if(currentTaskName.equals(taskName))
    } //end endTask

    /**
    * Returns the name of the current task.
    *
    * @param sessionObj
    * @return String
    * @roseuid 3CE335490189
    */
    public static String getCurrentTask(HttpSession sessionObj)
    {

	NBSObjectStore map = (NBSObjectStore)sessionObj.getAttribute(
			      NBSConstantUtil.OBJECT_STORE);
	NBSContextSystemInfo nbsCSInfo = (NBSContextSystemInfo)map.get(
						 NBSConstantUtil.CONTEXT_INFO);

	return nbsCSInfo.getCurrentTaskName() == null
		   ? null : nbsCSInfo.getCurrentTaskName();
    }

    /**
    * Returns the ID of the previous page.
    *
    * @param sessionObj
    * @return String
    * @roseuid 3CE3355301B5
    */
    public static String getPrevPageID(HttpSession sessionObj)
    {

	NBSObjectStore NbsObjectStore = (NBSObjectStore)sessionObj.getAttribute(
					 NBSConstantUtil.OBJECT_STORE);
	NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)NbsObjectStore.get(
							 NBSConstantUtil.NBSCONTEXTINFO);
	String PreviousPageID = contextSystemInfo.getPrevPageID();
	//logger.debug(
		//"getPrevPageID.1 NBSPageContext.getPrevPageID:PreviousPageID :" +
		//PreviousPageID);

	return PreviousPageID;
    }

    /**
    * Returns the name of the previous page.
    *
    * @param sessionObj
    * @return String
    * @roseuid 3CE335AE03D3
    */
    public static String getPrevPageName(HttpSession sessionObj)
    {

	NBSObjectStore NbsObjectStore = (NBSObjectStore)sessionObj.getAttribute(
					 NBSConstantUtil.OBJECT_STORE);
	NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)NbsObjectStore.get(
							 NBSConstantUtil.NBSCONTEXTINFO);
	String PreviousPageName = contextSystemInfo.getPrevPageName();
	//logger.debug(
		//"getPrevPageName.1 NBSPageContext.getPrevPageName:PrevPageName :" +
		//PreviousPageName);

	return PreviousPageName;
    }

    /**
    * This method is called once at the start of the page generation process.  The
    * newPageID is the ID of the page being rendered.  The action is the action on
    * the previous page that caused this page to be rendered.  The sessionObj is the
    * user's session object.
    * The method handles the bookkeeping for context (moving the current page to the
    * previous page, etc..) as well as cleans the Object Store and returns a TreeMap
    * of page elements and the corresponding actions to take for each.
    * This method must be called before any other public methods are called.
    *
    * @param sessionObj
    * @param newPageID
    * @param action
    * @return TreeMap
    * @roseuid 3CE3386D012B
    */
    public static TreeMap<Object,Object> getPageContext(HttpSession sessionObj,
					 String newPageID, String action)
    {

	String objStore = null;
	NBSObjectStore theNBSObjectStore = null;
	NBSContextSystemInfo theNBSContextInfo = null;
	objStore = sessionObj.getAttribute(NBSConstantUtil.OBJECT_STORE) == null
		       ? null
		       : sessionObj.getAttribute(NBSConstantUtil.OBJECT_STORE).toString();

	//Get the object store
	if (objStore == null || objStore.equals("") ||
	    objStore.length() == 0)
	{
	    theNBSContextInfo = new NBSContextSystemInfo();
	    theNBSObjectStore = new NBSObjectStore();
	    theNBSObjectStore.put(NBSConstantUtil.CONTEXT_INFO,
				  theNBSContextInfo);
	    sessionObj.setAttribute(NBSConstantUtil.OBJECT_STORE,
				    theNBSObjectStore);
	}
	else
	{
	    theNBSObjectStore = (NBSObjectStore)sessionObj.getAttribute(
					NBSConstantUtil.OBJECT_STORE);
	    theNBSContextInfo = (NBSContextSystemInfo)theNBSObjectStore.get(
					NBSConstantUtil.CONTEXT_INFO);
	}

	//*** Current Page **//
	//get current context information
	String currentPageID = theNBSContextInfo.getCurrentPageID();
	String currentTaskName = theNBSContextInfo.getCurrentTaskName();

	if (currentPageID.equalsIgnoreCase("NONE"))
	{

	    //do nothing
	} //LPF: Execute the End Task first and then Start Task
	else
	{

	    NBSPage currentPage = (NBSPage)theContextMap.get(currentPageID);
	    NBSPageContext currentTaskOverrideContext = (NBSPageContext)currentPage.theTaskOverridesTreeMap.get(
								currentTaskName);

	    //debug info
	    //logger.debug("in getPageContext() : The current page, before new page = " + currentPage.getPageName() + " " + currentPage.getPageID());
	    //logger.debug ("in getPageContext() : The current action (becomes last action) is = " + action);
	    //logger.debug("in getPageContext() : The current task overrides before newpage = " + currentTaskOverrideContext);
	    //lookInsideContext(currentTaskOverrideContext);
	    //end debug info
	    //first execute End Task
	    String theTaskToEnd = (String)theGlobalPage.getTheDefaultPageContext()
	     .getTaskEnds().get(action);

	    if (theTaskToEnd != null) //execute Global task
	    {
		NBSContext.endTask(theNBSObjectStore, theTaskToEnd,
				   theGlobalPage);
	    } //otherwise use the TaskOverride or default end task
	    else
	    {

		if (currentTaskOverrideContext != null)
		{
		    theTaskToEnd = (String)currentTaskOverrideContext.getTaskEnds()
			  .get(action);
		}
		else
		{
		    theTaskToEnd = (String)currentPage.getTheDefaultPageContext()
			       .getTaskEnds().get(action);
		}
	    }

	    if (theTaskToEnd != null)
	    {

		//logger.debug ("The Task To Start is " + theTaskToEnd);
		//logger.debug ("The Task To Start is " + theTaskToEnd);
		NBSContext.endTask(theNBSObjectStore, theTaskToEnd,
				   currentPage);
	    }

	    //then execute start Task.
	    //LPF: in this version the end task is implicitly called within startTask
	    String theTaskToStart = (String)theGlobalPage.getTheDefaultPageContext()
	     .getTaskStarts().get(action);

	    if (theTaskToStart != null) //execute Global task
	    {
		NBSContext.startTask(theNBSObjectStore, theTaskToStart,
				     theGlobalPage);
	    }
	    else
	    {

		if (currentTaskOverrideContext != null)
		{
		    theTaskToStart = (String)currentTaskOverrideContext.getTaskStarts()
			  .get(action);
		}
		else
		{
		    theTaskToStart = (String)currentPage.getTheDefaultPageContext()
			       .getTaskStarts().get(action);
		}
	    }

	    if (theTaskToStart != null)
	    {

		//logger.debug ("The Task To Start is " + theTaskToStart);
		//logger.debug ("The Task To Start is " + theTaskToStart);
		NBSContext.startTask(theNBSObjectStore, theTaskToStart,
				     currentPage);
	    }
	} //else

	//*** Move to  New Page ***//
	//LPF: Move to the new page and find out what the next page context is and build the page elements for this page
	NBSPage theCurrentPage = (NBSPage)theContextMap.get(newPageID);

	//LPF Note the new page becomes the current page Now!!
	NBSContext.newPage(theNBSObjectStore, newPageID,
			   theCurrentPage.getPageName());

	//Note this is a new currrent page and new current overrides
	//logger.debug("After moving to the new page, the new current page is = " + theCurrentPage.getPageName() + " " + theCurrentPage.getPageID());
	currentTaskName = theNBSContextInfo.getCurrentTaskName();

	//logger.debug("what is the current taskname = " + currentTaskName);
	//logger.debug("The current page's theTaskOverridesTreeMap size = " + theCurrentPage.theTaskOverridesTreeMap.size());
	NBSPageContext theCurrentTaskOverrideContext = (NBSPageContext)theCurrentPage.theTaskOverridesTreeMap.get(
							       currentTaskName);

	//logger.debug("The new current task over rides   = " + theCurrentTaskOverrideContext);
	lookInsideContext(theCurrentTaskOverrideContext);

	//Clean up object store
	ArrayList<Object> preserveObjs = null;

	if (theCurrentTaskOverrideContext != null)
	    preserveObjs = (ArrayList<Object> )theCurrentTaskOverrideContext.getPreserveObjs();
	else
	    preserveObjs = (ArrayList<Object> )theCurrentPage.theDefaultPageContext.getPreserveObjs();

	NBSContext.removeAllExcept(theNBSObjectStore, preserveObjs);

	//build PageElements for this new current page
        //first copy the page elements from GlobalPage
	TreeMap<Object,Object> pageElements = new TreeMap<Object,Object> (theGlobalPage.theDefaultPageContext.getPageElements());
        //logger.debug("Global page elements");
        //lookInsideTreeMap(pageElements);

        TreeMap<Object,Object> currentPageElements = theCurrentPage.theDefaultPageContext.getPageElements();
        //over-lay with the current page defaults
        pageElements.putAll(currentPageElements);
        //logger.debug("Current page elements task after current defaults ");
        //lookInsideTreeMap(pageElements);

	//over-lay with the overrides
	if (theCurrentTaskOverrideContext != null)
	{

	    TreeMap<Object,Object> currentPageElements2 = theCurrentTaskOverrideContext.getPageElements();
            pageElements.putAll(currentPageElements2);
            //logger.debug ("Current page elements after override tasks");
	    lookInsideTreeMap(pageElements);
	}

	//Lastly, sync up the sys info
	theNBSContextInfo.setLastAction(action);

	return pageElements;
    }

    /**
    * This method is used to initialize context management.  The configFileName
    * contains the name and path to the context configuration file.  The
    * configuration information is in XML format.  The file is read in and the
    * information is stored in theContextMap attribute.  The server must be restarted

    * This method is used to initialize context management.  The configFileName
    * contains the name and path to the context configuration file.  The
    * configuration information is in XML format.  The file is read in and the
    * information is stored in theContextMap attribute.  The server must be restarted
    * to read the file again.
    *
    * @param configFileName
    * @throws java.lang.Exception
    * @roseuid 3CE45514002A
    */
    public static void initializeNBSContext(String configFileName)
				     throws Exception
    {

	Document doc;

	if(Initialized)
	{
	//##!! System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Skipping additional Context Init @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
	else
	{
	Initialized = true;
	theContextMap = new TreeMap<Object,Object>();


	//##!! System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Initializing Context @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	DocumentBuilderFactory domf = DocumentBuilderFactory.newInstance();
	DocumentBuilder dbuilder = domf.newDocumentBuilder();
	doc = dbuilder.parse(configFileName);

	Element rootElement = doc.getDocumentElement();
	NodeList firstRoot = rootElement.getChildNodes();

	//for loop to reach 2nd level of loop(NBS Pages)
	for (int i = 0; i < firstRoot.getLength(); i++)
	{

	    Node child = firstRoot.item(i);
	    Node innerText = child.getFirstChild();

	    if (innerText == null)
	    {

		continue; //do nothing in case null
	    }

	    //logger.debug(
		  //  "initializeNBSContext.1 child.getNodeName:" +
		    //child.getNodeName());

	    NodeList nBSPageList = child.getChildNodes();

	    //for loop to reach 3rd level of loop(NBS Pages with id and name)
	    //LPF: for each NBSPage
	    for (int j = 0; j < nBSPageList.getLength(); j++)
	    {

		Node nBSPageElement = nBSPageList.item(j);

		//To check if the node is of element type then use it for casting purpose
		if (nBSPageElement.getNodeType() == nBSPageElement.ELEMENT_NODE)
		{

		    Element elementUnderNBSPage = (Element)nBSPageElement; //casting to element
		    //logger.debug(
			   // "initializeNBSContext.2 elementUnderNBSPage.getNodeName():" +
			  //  elementUnderNBSPage.getNodeName());

		    //LPF: NBSPage
		    if (elementUnderNBSPage.getNodeName().equals(
				NBSConstantUtil.NBSPAGE))
		    {
			//logger.debug(
				//"initializeNBSContext.3 elementUnderNBSPage.getNodeName()" +
				//elementUnderNBSPage.getNodeName() + "    " +
				//elementUnderNBSPage.getAttribute("id") +
				//"   " +
				//elementUnderNBSPage.getAttribute("name") +
				//" inner " + j);

			NBSPage page = new NBSPage();
			page.setPageID(elementUnderNBSPage.getAttribute(
					       NBSConstantUtil.NBS_PAGE_ID));
			page.setPageName(elementUnderNBSPage.getAttribute(
						 NBSConstantUtil.NAME_FOR_NBSPAGE));

			//LPF create the empty Overrides Treemap for each page.
			TreeMap<Object,Object> taskOverRidesTreeMap = new TreeMap<Object,Object>();
			page.theTaskOverridesTreeMap = taskOverRidesTreeMap;

			NodeList nbsPageContextNode = nBSPageElement.getChildNodes();

			//for loop to reach 4th level of loop(NBSPageContext)
			for (int k = 0;
			     k < nbsPageContextNode.getLength();
			     k++)
			{

			    Node nBSPageNode = nbsPageContextNode.item(k);

			    if (nBSPageNode.getNodeType() == nBSPageNode.ELEMENT_NODE)
			    {
				//logger.debug(
					//"initializeNBSContext.4 @@@@@@@@@!!!!!" +
					//nBSPageNode.getNodeName() + "" +
					//((Element)nBSPageNode).getAttribute(
						//NBSConstantUtil.NAME));

				//NBSPageContext
				if (nBSPageNode.getNodeName().equals(
					    NBSConstantUtil.NBS_PAGE_CONTEXT))
				{

				    NBSPageContext nbsPageContext =
					    new NBSPageContext();
				    TreeMap<Object,Object> tMap = (TreeMap<Object,Object>)resultFromNBSPageContext(
							   nBSPageNode);
				    nbsPageContext.setPageElements(
					    (TreeMap<Object,Object>)tMap.get(
						    NBSConstantUtil.PAGE_ELEMENT_TREEMAP));
				    nbsPageContext.setPreserveObjs(
					    (ArrayList<Object> )tMap.get(
						    NBSConstantUtil.PRESERVEOBJS));
				    nbsPageContext.setTaskEnds(
					    (TreeMap<Object,Object>)tMap.get(
						    NBSConstantUtil.TASK_END_MAP));
				    nbsPageContext.setTaskStarts(
					    (TreeMap<Object,Object>)tMap.get(
						    NBSConstantUtil.TASK_START_MAP));

				    //logger.debug ("look at the treemap gettting default stored");
				    lookInsideTreeMap(
					    (TreeMap<Object,Object>)tMap.get(
						    NBSConstantUtil.TASK_START_MAP));
				    page.setTheDefaultPageContext(
					    nbsPageContext);
				}

				//Overrides
				if (nBSPageNode.getNodeName().equals(
					    NBSConstantUtil.NBS_TASK_OVERRIDES))
				{

				    NodeList taskOverRideSubNode =
					    nBSPageNode.getChildNodes();

				    //for each override
				    for (int l = 0;
					 l < taskOverRideSubNode.getLength();
					 l++)
				    {

					Node taskOverRideTaskOverRideNode =
						taskOverRideSubNode.item(l);

					if (taskOverRideTaskOverRideNode.getNodeType() == taskOverRideTaskOverRideNode.ELEMENT_NODE)
					{

					    Element elementUnderOverride =
						    (Element)taskOverRideTaskOverRideNode;

					    //LPF: Override
					    if (elementUnderOverride.getNodeName()
								.equals(
							NBSConstantUtil.NBS_TASK_OVERRIDE))
					    {
						//logger.debug(
							//"initializeNBSContext.5########" +
							//taskOverRideTaskOverRideNode.getNodeName());

						NodeList taskOverRidePageContextNodeList =
							taskOverRideTaskOverRideNode.getChildNodes();

						//for each NBSPageContext
						for (int m = 0;
						     m < taskOverRidePageContextNodeList.getLength();
						     m++)
						{

						    Node taskOverRidePageContextNode =
							    taskOverRidePageContextNodeList.item(
								    m);

						    if (taskOverRidePageContextNode.getNodeType() == taskOverRidePageContextNode.ELEMENT_NODE)
						    {

							//NBSPageContext
							if (taskOverRidePageContextNode
										   .getNodeName()
										   .equals(
								    NBSConstantUtil.NBS_PAGE_CONTEXT))
							{

							    //logger.debug ("Got override NBSPagecontext node for the TaskOverride " + elementUnderOverride.getAttribute(NBSConstantUtil.TASKNAME));
							    NBSPageContext nbsPageContextOverRide =
								    new NBSPageContext();
							    TreeMap<Object,Object> tMap =
								    (TreeMap<Object,Object>)resultFromNBSPageContext(
									    taskOverRidePageContextNode);

							    //logger.debug ("resultFromNBSPageContext return a treemap of the following");
							    //lookInsideTreeMap(tMap);
							    nbsPageContextOverRide.setPageElements(
								    (TreeMap<Object,Object>)tMap.get(
									    NBSConstantUtil.PAGE_ELEMENT_TREEMAP));
							    nbsPageContextOverRide.setPreserveObjs(
								    (ArrayList<Object> )tMap.get(
									    NBSConstantUtil.PRESERVEOBJS));
							    nbsPageContextOverRide.setTaskEnds(
								    (TreeMap<Object,Object>)tMap.get(
									    NBSConstantUtil.TASK_END_MAP));
							    nbsPageContextOverRide.setTaskStarts(
								    (TreeMap<Object,Object>)tMap.get(
									    NBSConstantUtil.TASK_START_MAP));

							    //logger.debug ("The Override taskName is " + elementUnderOverride.getAttribute(NBSConstantUtil.TASKNAME));
							    //logger.debug ("look at the  Page Context Override gettting stored");
							    lookInsideContext(
								    nbsPageContextOverRide);
							    taskOverRidesTreeMap.put(elementUnderOverride.getAttribute(
											     NBSConstantUtil.TASKNAME),
										     nbsPageContextOverRide);
							}
						    }
						}
					    }
					}
				    }
				}
			    }
			}

			theContextMap.put(elementUnderNBSPage.getAttribute(
						  NBSConstantUtil.NBS_PAGE_ID),
					  page);

			String pageID = elementUnderNBSPage.getAttribute(
						NBSConstantUtil.NBS_PAGE_ID);
			//logger.debug("Page ID is" + pageID);

			if (pageID.equals(NBSConstantUtil.GLOBAL_CONTEXT))
			{
			    theGlobalPage = page;
			   // logger.debug("global page !!!!");
			    lookInsideContext(theGlobalPage.getTheDefaultPageContext());
			}
		    }
		}
	    }
	}
	}
    }

    /**
    * This is a convenient private method that returns the Tremap of nodes under NBSPageContext
    * so that it can be reused again(for TaskOverrides) and to make the code more managable
    */
    private static TreeMap<Object,Object> resultFromNBSPageContext(Node nBSPageNode)
    {

	TreeMap<Object,Object> pageElementMap = new TreeMap<Object,Object>();
	TreeMap<Object,Object> taskStartsMap = new TreeMap<Object,Object>();
	TreeMap<Object,Object> taskEndsMap = new TreeMap<Object,Object>();
	Collection<Object>  preserverObjsColl = new ArrayList<Object> ();
	TreeMap<Object,Object> ObjectsFromNBSPageContext = new TreeMap<Object,Object>();
	Element nbsPageContextElement = (Element)nBSPageNode; //casting to element
	//logger.debug(
		//"resultFromNBSPageContext.1 nbsPageContextElement.getNodeName():" +
		//nbsPageContextElement.getNodeName());

	NodeList nbsPageElements = nBSPageNode.getChildNodes();

	//for loop to find out (basic elements elements/preserveObjects/TaskStarts/TaskEnds)
	for (int l = 0; l < nbsPageElements.getLength(); l++)
	{

	    Node nbsPageElement2 = nbsPageElements.item(l);

	    if (nbsPageElement2.getNodeType() == nbsPageElement2.ELEMENT_NODE)
	    {

		Element elementUnderPageContext = (Element)nbsPageElement2; //casting to element
		//logger.debug(
		//	"resultFromNBSPageContext.2 elementUnderPageContext.getNodeName():" +
		//	elementUnderPageContext.getNodeName());

		NodeList nbsElements = nbsPageElement2.getChildNodes();

		//for loop to reach last level of loop((to reach out for the elements for Elements/PreserveObjects/TaskStarts/TaskEnds)
		for (int m = 0; m < nbsElements.getLength(); m++)
		{

		    Node nbsElementNode = nbsElements.item(m);

		    //logger.debug(nbsElements.getLength());
		    if (elementUnderPageContext.getNodeName().equals(
				NBSConstantUtil.PAGE_ELEMENTS) &&
			nbsElementNode.getNodeType() == nbsElementNode.ELEMENT_NODE)
		    {

			Element pageElements = (Element)nbsElementNode; //casting to element
			pageElementMap.put(pageElements.getAttribute(
						   NBSConstantUtil.NAME),
					   pageElements.getAttribute(
						   NBSConstantUtil.ACTION));
			//logger.debug(
			//	"resultFromNBSPageContext.3 pageElements.getAttribute for name and action:" +
			//	pageElements.getAttribute(NBSConstantUtil.NAME) +
			//	"  " +
			//	pageElements.getAttribute(
			//		NBSConstantUtil.ACTION));
		    }

		    if (elementUnderPageContext.getNodeName().equals(
				NBSConstantUtil.PRESERVEOBJS) &&
			nbsElementNode.getNodeType() == nbsElementNode.ELEMENT_NODE)
		    {

			Element PreserveObjs = (Element)nbsElementNode; //casting to element
			preserverObjsColl.add(PreserveObjs.getAttribute(NBSConstantUtil.NAME));
			//logger.debug(
			//	"resultFromNBSPageContext.4 PreserveObjs.getAttribute(NAME):" +
			//	PreserveObjs.getAttribute(NBSConstantUtil.NAME));
		    }

		    if (elementUnderPageContext.getNodeName().equals(
				NBSConstantUtil.TASKSTARTS) &&
			nbsElementNode.getNodeType() == nbsElementNode.ELEMENT_NODE)
		    {

			Element taskStarts = (Element)nbsElementNode; //casting to element
			taskStartsMap.put(taskStarts.getAttribute(
						  NBSConstantUtil.ACTION),
					  taskStarts.getAttribute(
						  NBSConstantUtil.TASKNAME));
			//logger.debug(
			//	"resultFromNBSPageContext.5 :" +
			//	taskStarts.getAttribute(NBSConstantUtil.ACTION) +
			//	"  " +
			//	taskStarts.getAttribute(
			//		NBSConstantUtil.TASKNAME));
		    }

		    if (elementUnderPageContext.getNodeName().equals(
				NBSConstantUtil.TASKENDS) &&
			nbsElementNode.getNodeType() == nbsElementNode.ELEMENT_NODE)
		    {

			Element taskEnds = (Element)nbsElementNode; //casting to element
			taskEndsMap.put(taskEnds.getAttribute(
						NBSConstantUtil.ACTION),
					taskEnds.getAttribute(
						NBSConstantUtil.TASKNAME));
			//logger.debug(
			//	"resultFromNBSPageContext.6 :" +
			//	taskEnds.getAttribute(NBSConstantUtil.ACTION) +
			//	"  " +
			//	taskEnds.getAttribute(NBSConstantUtil.TASKNAME));
		    }
		}
	    }
	}

	ObjectsFromNBSPageContext.put(NBSConstantUtil.PAGE_ELEMENT_TREEMAP,
				      pageElementMap);
	ObjectsFromNBSPageContext.put(NBSConstantUtil.PRESERVEOBJS,
				      preserverObjsColl);
	ObjectsFromNBSPageContext.put(NBSConstantUtil.TASK_START_MAP,
				      taskStartsMap);
	//logger.debug("look at the treemap gettting put");
	lookInsideTreeMap(taskStartsMap);
	ObjectsFromNBSPageContext.put(NBSConstantUtil.TASK_END_MAP,
				      taskEndsMap);

	return ObjectsFromNBSPageContext;
    }

    /**
    * This method updates the NBSContextInfo for a new page.
    *
    * @param theNBSObjectStore
    * @param newPageID
    * @param newPageName
    * @roseuid 3CE476DD0199
    */
    private static void newPage(NBSObjectStore theNBSObjectStore, String newPageID,
				String newPageName)
    {

	NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)theNBSObjectStore.get(
							 NBSConstantUtil.NBSCONTEXTINFO);
	String currentPageName = contextSystemInfo.getCurrentPageName();
	String currentPageID = contextSystemInfo.getCurrentPageID();
	contextSystemInfo.setPrevPageName(currentPageName);
	contextSystemInfo.setPrevPageID(currentPageID);
	contextSystemInfo.setCurrentPageName(newPageName);
	contextSystemInfo.setCurrentPageID(newPageID);
    }

    public static void lookInsideContext(NBSPageContext page)
    {

	if (page != null)
	{

	    Set<Object> pageElements = page.getPageElements().keySet();

	    //logger.debug("size of page elements treemap = " + page.getPageElements().size());
	   Iterator<Object>  itrPageElements = pageElements.iterator();

	    while (itrPageElements.hasNext())
	    {

		String sKey = (String)itrPageElements.next();

		//logger.debug("Page Elements Value is  :" + page.getPageElements().get(sKey) + " and key is :" + sKey);
	    }

	    Set<Object> taskStarts = page.getTaskStarts().keySet();

	    //logger.debug("size of taskStarts treemap = " + page.getTaskStarts().size());
	   Iterator<Object>  itrTaskStarts = taskStarts.iterator();

	    while (itrTaskStarts.hasNext())
	    {

		String sKey = (String)itrTaskStarts.next();

		//logger.debug("tasks starts Value is  :" + page.getTaskStarts().get(sKey) + " and key is :" + sKey);
	    }
	}
	else
	    logger.debug("the NBSPageContext is NULL#####3");
    }

    public static void lookInsideTreeMap(TreeMap<Object,Object> treemap)
    {

	if (treemap != null)
	{
	    //logger.debug("size of treemap = " + treemap.size());

	    Set<Object> set = treemap.keySet();
	   Iterator<Object>  itr = set.iterator();

	    while (itr.hasNext())
	    {

		String sKey = (String)itr.next();
		//logger.debug(
		//	"Page Elements Value is  :" + treemap.get(sKey) +
		//	" and key is :" + sKey);
	    }
	}
	else
	    logger.debug("null treemap!!!!!!!!!!!");
    }

     /**
    * This method is called from the Front Controller.  It is used to detect cases when
    * the context engine is out of synchronization with the page the user performed an action on.
    * This occurs when the user has used the back button or the refresh button to navigate
    * to another page.
    *
    * @param sessionObj
    * @param requestTaskName
    * @param requestContextAction
    * @return boolean
    *
    */
   public static boolean checkContext(HttpSession sessionObj, String requestTaskName, String requestContextAction)
    {
        boolean bRetVal = false;
	NBSObjectStore NbsObjectStore = (NBSObjectStore)sessionObj.getAttribute(
					 NBSConstantUtil.OBJECT_STORE);
        //If there is no object store, context is not initialized for the user so return true
        if((NbsObjectStore == null) || (requestContextAction == null))
        {
	   // logger.debug("Context Check  PASS - No Object Store");
          bRetVal = true;
        }
        else
        {
          //Check to see if the action is a global action (context will automatically be reset)
      	  if(theGlobalPage.getTheDefaultPageContext().getTaskStarts().get(requestContextAction) != null)
          {
	   // logger.debug("Context Check PASS- Global Action: " + requestContextAction);
            //Global Action so return true
            bRetVal = true;
          }
          else
          {
            NBSContextSystemInfo contextSystemInfo = (NBSContextSystemInfo)NbsObjectStore.get(
                                                             NBSConstantUtil.NBSCONTEXTINFO);
            String currentTaskName = contextSystemInfo.getCurrentTaskName();
            if(currentTaskName.compareToIgnoreCase(requestTaskName) == 0)
            {
  	     // logger.debug("Context Check PASS- Task is current Task: " + requestTaskName);
              //the task names match so we are on the right path
              bRetVal = true;
            }
            else
            {
              //check to see if it matches the previous task and last action (refresh?)
//This check has been commented out so that the user will get the context error more consistently
              String prevTaskName = contextSystemInfo.getPrevTaskName();
              String lastAction = contextSystemInfo.getLastAction();
			  logger.debug("********#Previous Page Name :"+contextSystemInfo.getPrevPageName());
              logger.debug("********#Previous Task Name :"+contextSystemInfo.getPrevTaskName());
              if((prevTaskName.compareToIgnoreCase(requestTaskName) == 0)
              && (lastAction.compareToIgnoreCase(requestContextAction) == 0))
              {
                contextSystemInfo.setCurrentPageID(contextSystemInfo.getPrevPageID());
                contextSystemInfo.setCurrentPageName(contextSystemInfo.getPrevPageName());
                contextSystemInfo.setCurrentTaskName(contextSystemInfo.getPrevTaskName());
                //logger.debug("Context Check PASS- Task and action match previous: " + requestTaskName + ", " + requestContextAction);
                //the task names match so we are on the right path
                bRetVal = true;
              }
              else
              {

                  //check to see if the task name is one of the existing tasks for context (some pages are not under context)
                  Set<Object> contextPages = theContextMap.keySet();
                 Iterator<Object>  itrPages = contextPages.iterator();
                  //set the return value to true because if we don't find the task then it is not a context task
                  bRetVal = true;
                  while(itrPages.hasNext())
                  {
                    String sPageKey = (String)itrPages.next();
                    NBSPage curPage = (NBSPage)theContextMap.get(sPageKey);
                    if(curPage.theTaskOverridesTreeMap.get(requestTaskName) != null)
                    {
                      //logger.debug("Context Check FAIL- Task Name ( " + requestTaskName + ") does not match current task (" + currentTaskName + ")");
                      //task is one of the context tasks so stop searching
                      bRetVal = false;
                      break;
                    }
                  }
                  if(bRetVal)
                  {
                    //logger.debug("Context Check PASS- Task is not a context task: " + requestTaskName);
                  }
              }
            }
          }
        }
	return bRetVal;
    }

    /*
       public static void main(String args[]) throws Exception{

    initializeNBSContext("C:\\ContextMaps\\PersonContextMap.xml");


       }
    */
}