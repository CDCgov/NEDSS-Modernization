package gov.cdc.nedss.rhapsody.customfilters;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import com.orchestral.rhapsody.configuration.auxiliaryfiles.AuxiliaryFilesService;
import com.orchestral.rhapsody.configuration.security.SecurityObjectsService;
import com.orchestral.rhapsody.configuration.variables.VariablesService;
import com.orchestral.rhapsody.idgenerator.IdGeneratorFactory;
import com.orchestral.rhapsody.messageparsing.MessageParsingService;
import com.orchestral.rhapsody.model.definition.DefinitionService;
import com.orchestral.rhapsody.module.CommunicationPointRegistration;
import com.orchestral.rhapsody.module.FilterRegistration;
import com.orchestral.rhapsody.module.communicationpoint.CommunicationPointInfo;
import com.orchestral.rhapsody.persistentmap.PersistentMap;
import com.orchestral.rhapsody.persistentmap.PersistentMapService;
import com.orchestral.rhapsody.security.SecurityException;

public class Activator {

	private static AuxiliaryFilesService auxiliaryFilesService;
	private static DefinitionService definitionService;
	private static MessageParsingService messageParsingService;
	private static PersistentMapService persistentMapService;
	private static SecurityObjectsService securityObjectsService;
	private static DocumentBuilderFactory documentBuilderFactory;
	private static IdGeneratorFactory idGeneratorFactory;
	private static VariablesService variablesService;

	private Set<ServiceRegistration> registrations = new HashSet<ServiceRegistration>();

	protected void setAuxiliaryFilesService(
			final AuxiliaryFilesService auxiliaryFilesService) {
		Activator.auxiliaryFilesService = auxiliaryFilesService;
	}

	protected void unsetAuxiliaryFilesService(
			final AuxiliaryFilesService auxiliaryFilesService) {
		Activator.auxiliaryFilesService = null;
	}

	protected void setDefinitionService(
			final DefinitionService definitionService) {
		Activator.definitionService = definitionService;
	}

	protected void unsetDefinitionService(
			final DefinitionService definitionService) {
		Activator.definitionService = null;
	}

	protected void setMessageParsingService(
			final MessageParsingService messageParsingService) {
		Activator.messageParsingService = messageParsingService;
	}

	protected void unsetMessageParsingService(
			final MessageParsingService messageParsingService) {
		Activator.messageParsingService = null;
	}

	protected void setPersistentMapService(
			final PersistentMapService persistentMapService) {
		Activator.persistentMapService = persistentMapService;
	}

	protected void unsetPersistentMapService(
			final PersistentMapService persistentMapService) {
		Activator.persistentMapService = null;
	}

	protected void setSecurityObjectsService(
			final SecurityObjectsService securityObjectsService) {
		Activator.securityObjectsService = securityObjectsService;
	}

	protected void unsetSecurityObjectsService(
			final SecurityObjectsService securityObjectsService) {
		Activator.securityObjectsService = null;
	}

	public void setDocumentBuilderFactory(final DocumentBuilderFactory factory) {
		Activator.documentBuilderFactory = factory;
	}

	protected void unsetDocumentBuilderFactory(
			final DocumentBuilderFactory factory) {
		Activator.documentBuilderFactory = null;
	}

	protected void setIdGeneratorFactory(
			final IdGeneratorFactory idGeneratorFactory) {
		Activator.idGeneratorFactory = idGeneratorFactory;
	}

	protected void unsetIdGeneratorFactory(
			final IdGeneratorFactory idGeneratorFactory) {
		Activator.idGeneratorFactory = null;
	}

	protected void setVariablesService(final VariablesService variablesService) {
		Activator.variablesService = variablesService;
	}

	protected void unsetVariablesService(final VariablesService variablesService) {
		Activator.variablesService = null;
	}

	public static AuxiliaryFilesService getAuxiliaryFilesService()
			throws FileNotFoundException, SecurityException {
		return Activator.auxiliaryFilesService;
	}

	public static DefinitionService getDefinitionService() {
		return Activator.definitionService;
	}

	public static MessageParsingService getMessageParsingService() {
		return Activator.messageParsingService;
	}

	public static PersistentMap getPersistentMap(
			final CommunicationPointInfo communicationPointInfo) {
		return Activator.persistentMapService
				.getPersistentMap(communicationPointInfo);
	}

	public static SecurityObjectsService getSecurityObjectsService() {
		return Activator.securityObjectsService;
	}

	public static DocumentBuilderFactory getDocumentBuilderFactory() {
		return Activator.documentBuilderFactory;
	}
	
	public static IdGeneratorFactory getIdGeneratorFactory() {
		return Activator.idGeneratorFactory;
	}

	public static VariablesService getVariablesService() {
		return Activator.variablesService;
	}
	

	protected void activate(final ComponentContext componentContext) {
		final BundleContext context = componentContext.getBundleContext();
		final String cpr = CommunicationPointRegistration.class.getName();
		final String fr = FilterRegistration.class.getName();

		// Register the communication point types in this module
		// this.registrations.add(context.registerService(cpr, new
		// CommunicationPointRegistration("Directory", "Directory",
		// DirectoryFileCommunicationPoint.class, "/file.bmp", null,
		// DirectoryFileCommunicationPoint.RECORDS), null));

		// Register the filter types in this module
		// this.registrations.add(context.registerService(fr, new
		// FilterRegistration("AsymmetricCrypto", "Asymmetric Cryptography",
		// "Utility", AsymmetricCryptoFilter.class, "/crypto.bmp", null),
		// null));

		
		/* <START> -CODE CONTROLLED BY WIZZARDS DO NOT REMOVE COMMENT */
		this.registrations.add(context.registerService(fr, new FilterRegistration("NbsInterface Filter", "NbsInterface Filter", "Nedss", gov.cdc.nedss.rhapsody.customfilters.NbsInterfaceFilter.class, "/customfilter.bmp", "/customfilter_small.bmp"), null));
		this.registrations.add(context.registerService(fr, new FilterRegistration("ELRWorkerQueue Filter", "ELRWorkerQueue Filter", "Nedss", ELRWorkerQueueFilter.class, "/customfilter.bmp", "/customfilter_small.bmp"), null));
	    this.registrations.add(context.registerService(fr, new FilterRegistration("TransportQOut Filter", "TransportQOut Filter", "Nedss", TransportqOutFilter.class, "/customfilter.bmp", "/customfilter_small.bmp"), null));
	    /* <END> -CODE CONTROLLED BY WIZZARDS DO NOT REMOVE COMMENT */

	}

	protected void deactivate(final ComponentContext context) {
		for (final ServiceRegistration registration : this.registrations) {
			registration.unregister();
		}
		this.registrations.clear();

	}
}
