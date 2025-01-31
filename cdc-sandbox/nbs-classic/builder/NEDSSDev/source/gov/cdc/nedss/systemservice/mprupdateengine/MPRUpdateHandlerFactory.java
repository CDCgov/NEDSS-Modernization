package gov.cdc.nedss.systemservice.mprupdateengine;

import java.util.*;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.LogUtils;
/**
 *
 * <p>Title: MPRUpdateHandlerFactory</p>
 * <p>Description: This is the object factory for classes of MPRUpdateHandler </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Shailender
 * @version 1.0
 */

public class MPRUpdateHandlerFactory {

        static final LogUtils logger = new LogUtils(MPRUpdateHandlerFactory.class.getName());
	private static MPRUpdateHandlerFactory instance =
		new MPRUpdateHandlerFactory();
	private Map<Object,Object> handlers = new HashMap<Object,Object>();

	/**
	 * @roseuid 3E4425EE02A2
	 */

	private MPRUpdateHandlerFactory() {
		MPRUpdateHandlersBuilder handlersBuilder =
			new MPRUpdateHandlersBuilder(
				MPRUpdateEngineConstants.HANDLERS_CONFIG_FILENAME);
		if (handlersBuilder.getUpdateHandlers() != null) {
			handlers = handlersBuilder.getUpdateHandlers();
		}
	}

	/**
         * This method returns an object instance of MPRUpdateHandlerFactory type.
	 * @return
	 * nedss.NEDSSBusinessServicesLayer.CDM.MPRUpdateEngine.MPRUpdateEngineDesign.updatehandler.MPRUpd
	 * ateHandlerFactory
	 * @roseuid 3E4425EE02AF
	 */
	public static MPRUpdateHandlerFactory getInstance() {
		return instance;
	}

	/**
         * This method returns an MPRUpdateHandler object based on a String key.
	 * @param id
	 * @return
	 * nedss.NEDSSBusinessServicesLayer.CDM.MPRUpdateEngine.MPRUpdateEngineDesign.updatehandler.MPRUpd
	 * ateHandler
	 * @roseuid 3E4BB7D903E7
	 */
	public MPRUpdateHandler getHandler(String id) {
		return (MPRUpdateHandler) handlers.get(id);
	}
}
