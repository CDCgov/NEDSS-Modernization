package gov.cdc.nedss.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.sas.rmi.Logger;

import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAPHCProcessor;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;

public class NBSBeanUtils extends BeanUtilsBean {
	static final LogUtils logger = new LogUtils(NBSBeanUtils.class.getName());

    
    public void copyNonNullProperties(Object dest, Object orig)
            throws IllegalAccessException, InvocationTargetException 
    {
        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (getPropertyUtils().isReadable(orig, name) &&
                getPropertyUtils().isWriteable(dest, name)) {
                try {
                    Object value = getPropertyUtils().getSimpleProperty(orig, name);
                    
                    // perform copy only if the source value is not null
                    if (value != null) {
                        copyProperty(dest, name, value);
                    }
                    
                } catch (NoSuchMethodException e) {
                    // Should not happen
                }
            }
        }
     }
    
	public void copyNonNullPropertiesForNullDest(Object newObj, Object oldObj, Map<Object, Object> ignoreMethodMap)
			throws IllegalAccessException, InvocationTargetException {
		// Validate existence of the specified beans
		if (newObj == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (oldObj == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(oldObj);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			String methodName = "";
			if (origDescriptors[i].getReadMethod() != null)
				methodName = origDescriptors[i].getReadMethod().getName();
			if (methodName == null)
				methodName = "";
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (getPropertyUtils().isReadable(oldObj, name) && getPropertyUtils().isWriteable(newObj, name)) {
				try {
					Object sourceValue = getPropertyUtils().getSimpleProperty(oldObj, name);
					Object destValue = getPropertyUtils().getSimpleProperty(newObj, name);
					if (ignoreMethodMap!=null && ignoreMethodMap.containsKey(methodName)) {
						if (sourceValue == null)
							DynamicBeanBinding.populateBean(newObj, (String) ignoreMethodMap.get(methodName), null);
						else
							copyProperty(newObj, name, sourceValue);
					}
					// perform copy only if the source value is not null and destination is null
					else if (destValue == null && sourceValue != null) {
						copyProperty(newObj, name, sourceValue);
					}

				} catch (Exception e) {
					logger.debug("Cannot copy bean value for method :" + methodName + " name :" + name);
				}
			}
		}
	}

}