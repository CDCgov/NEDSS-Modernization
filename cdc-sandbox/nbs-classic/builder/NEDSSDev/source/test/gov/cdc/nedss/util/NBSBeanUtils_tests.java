package test.gov.cdc.nedss.util;
import static org.mockito.Matchers.any;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NBSBeanUtils;
import gov.cdc.nedss.util.PropertyUtil;

import org.mockito.Mockito;
import org.mockito.Spy;


@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.NBSBeanUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(Enclosed.class)
@PrepareForTest({NBSBeanUtils.class})
@PowerMockIgnore("javax.management.*")
public class NBSBeanUtils_tests{
	

@SuppressStaticInitializationFor ({"gov.cdc.nedss.util.NBSBeanUtils","gov.cdc.nedss.util.PropertyUtil"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({NBSBeanUtils.class})
@PowerMockRunnerDelegate(Parameterized.class)
@PowerMockIgnore("javax.management.*")
public static class CopyNonNullPropertiesForNullDest_test{
	int it;
	boolean value;
	String source;
	String target;
	
	public CopyNonNullPropertiesForNullDest_test(boolean val, String source, String target, int iteration){
		this.value=val;
		this.source=source;
		this.target=target;
		this.it=iteration;
	}
	
	@Mock
	LogUtils logger;
	
	@Mock
	PropertyUtil propertyUtil;
	
	@Mock
	PropertyUtilsBean  propUtilBean;
 
	
	@InjectMocks
	@Spy
	NBSBeanUtils nbsBeanUtils= Mockito.spy(NBSBeanUtils.class);
	

	
	 @Before
	 public void initMocks() throws Exception {
		 logger = Mockito.mock(LogUtils.class);
		 propertyUtil = Mockito.mock(PropertyUtil.class);
		 Whitebox.setInternalState(PropertyUtil.class, "logger", logger);
	 }
		 

	 @Test
	 public void copyNonNullPropertiesForNullDest_test() throws Exception { 
		 Map<Object, Object> ignoreMethodMap = new HashMap<Object, Object>();
		 ignoreMethodMap.put("getSSN","890SSN");
		 PersonDT oldPersonDT = new PersonDT();
		 PersonDT newPersonDT = new PersonDT();
		 Mockito.doReturn(propUtilBean).when(nbsBeanUtils).getPropertyUtils();
		 Mockito.when(propUtilBean.getPropertyDescriptors(any(PersonDT.class))).thenReturn(getBeanMethodDescriptors());
		 Mockito.when(propUtilBean.isReadable(any(PersonDT.class), any(String.class))).thenReturn(this.value);
		 Mockito.when(propUtilBean.isWriteable(any(PersonDT.class), any(String.class))).thenReturn(this.value);
		 Mockito.when(propUtilBean.getSimpleProperty(any(PersonDT.class), any(String.class))).thenReturn(this.source).thenReturn(this.target);
		 Whitebox.invokeMethod(nbsBeanUtils, "copyNonNullPropertiesForNullDest", newPersonDT, oldPersonDT, ignoreMethodMap);
		 System.out.println("Class Name: NBSBeanUtils.java, Method Name: copyNonNullPropertiesForNullDest, Iteration::::"+this.it+", Result: PASSED");
	 }
	 
	 
    private PropertyDescriptor[] getBeanMethodDescriptors() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	PersonDT oldPersonDT= new PersonDT();
    	PropertyDescriptor origDescriptors[]= new PropertyDescriptor[2];
    	origDescriptors[0] =  org.apache.commons.beanutils.BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(oldPersonDT, "SSN");
    	origDescriptors[1] =  org.apache.commons.beanutils.BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(oldPersonDT, "addReasonCd");
    	return origDescriptors;
    }

 
    @Parameterized.Parameters
	   public static Collection<Object[]> input() {
	      return Arrays.asList(new Object[][]{
	    		  {true,null,"1234",1},{true,"1234",null,2},{false,"1234",null,3}
	  
	       });
	   }
	 
}
		
}
