package gov.cdc.nedss.util;

import  java.lang.reflect.*;
import  java.util.*;

/**
 *  Inspector.
 *  @author Ed Jenkins
 */
public class Inspector implements Comparator, Comparable
{

    /**
     *  Singleton.
     */
    public static final Inspector index = new Inspector();

    /**
     *  Constructor.
     */
    private Inspector()
    {
    }

    /**
     *  Gets the singleton instance.
     *  @return an Inspector.
     */
    public Inspector getInstance()
    {
        return index;
    }

    /**
     *  Performs a field-by-field comparison between the specified objects.
     *  Both objects must be from the same class.
     *  First it compares all public fields.
     *  Then it calls all getter methods and compares their return values.
     *  @param o1 the first object.
     *  @param o2 the second object.
     */
    public boolean equals(Object o1, Object o2)
    {
        if(o1 == o2)
        {
            return true;
        }
        if(o1 == null)
        {
            return false;
        }
        if(o2 == null)
        {
            return false;
        }
        Class c1 = o1.getClass();
        Class c2 = o2.getClass();
        String s1 = c1.getName();
        String s2 = c2.getName();
        if(!s1.equals(s2))
        {
            return false;
        }
        Field[] f1 = c1.getFields();
        Field[] f2 = c2.getFields();
        if(compareFields(f1, f2) == false)
        {
            return false;
        }
        return true;
    }

    /**
     *  Compares the values of fields in two objects.
     *  Compares only with fields that are public.
     *  Will not compare fields that are static, final, synchronized, transient, or volatile.
     *  Will not compare fields that are an interface or an array.
     *  @param p1 an array of fields from the first object.
     *  @param p2 an array of fields from the second object.
     *  @return true if all fields have equal values.
     */
    private boolean compareFields(Field[] p1, Field[] p2)
    {
        //  Verify parameters.
        if(p1 == null)
        {
            return false;
        }
        if(p2 == null)
        {
            return false;
        }
        //  Create return variable.
        boolean b = false;
        //  Create temp variables.
        Field f1 = null;
        Field f2 = null;
        Class c = null;
        int x = 0;
        int m = 0;
        try
        {
            //  Loop through all fields.
            for(x=0; x<p1.length; x++)
            {
                //  Get a pair of fields.
                f1 = p1[x];
                f2 = p2[x];
                //  Get modifiers.
                m = f1.getModifiers();
                //  Must be public.
                if(!Modifier.isPublic(m))
                {
                    continue;
                }
                //  Do not process if static.
                if(Modifier.isStatic(m))
                {
                    continue;
                }
                //  Do not process if final.
                if(Modifier.isFinal(m))
                {
                    continue;
                }
                //  Do not process if synchronized.
                if(Modifier.isSynchronized(m))
                {
                    continue;
                }
                //  Do not process if transient.
                if(Modifier.isTransient(m))
                {
                    continue;
                }
                //  Do not process if volatile.
                if(Modifier.isVolatile(m))
                {
                    continue;
                }
                //  Get class.
                c = f1.getType();
                //  Do not process if it is a reference to an interface.
                if(c.isInterface())
                {
                    continue;
                }
                //  Do not process if it is an array.
                if(c.isArray())
                {
                    continue;
                }
                //  Process primitive types.
                //  Listed in order of expected use,
                //  possibly providing slightly better performance.
                if(c == Integer.TYPE)
                {
                    int xInteger1 = f1.getInt(null);
                    int xInteger2 = f2.getInt(null);
                    if(xInteger1 == xInteger2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Long.TYPE)
                {
                    long xLong1 = f1.getLong(null);
                    long xLong2 = f2.getLong(null);
                    if(xLong1 == xLong2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Boolean.TYPE)
                {
                    boolean xBoolean1 = f1.getBoolean(null);
                    boolean xBoolean2 = f2.getBoolean(null);
                    if(xBoolean1 == xBoolean2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Float.TYPE)
                {
                    float xFloat1 = f1.getFloat(null);
                    float xFloat2 = f2.getFloat(null);
                    if(xFloat1 == xFloat2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Double.TYPE)
                {
                    double xDouble1 = f1.getDouble(null);
                    double xDouble2 = f2.getDouble(null);
                    if(xDouble1 == xDouble2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Byte.TYPE)
                {
                    byte xByte1 = f1.getByte(null);
                    byte xByte2 = f1.getByte(null);
                    if(xByte1 == xByte2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Character.TYPE)
                {
                    char xChar1 = f1.getChar(null);
                    char xChar2 = f2.getChar(null);
                    if(xChar1 == xChar2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(c == Short.TYPE)
                {
                    short xShort1 = f1.getShort(null);
                    short xShort2 = f2.getShort(null);
                    if(xShort1 == xShort2)
                    {
                        continue;
                    }
                    else
                    {
                        return false;
                    }
                }
                //  Process objects.
                Object o1 = f1.get(null);
                Object o2 = f2.get(null);
                if( (o1 == null) && (o2 == null) )
                {
                    continue;
                }
                if( (o1 == null) && (o2 != null) )
                {
                    return false;
                }
                if( (o1 != null) && (o2 == null) )
                {
                    return false;
                }
                Class<?> c1 = o1.getClass();
                Class<?> c2 = o2.getClass();
                Field[] x1 = c1.getFields();
                Field[] x2 = c2.getFields();
                if(compareFields(x1, x2) == false)
                {
                    return false;
                }
            }
            b = true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //  Return result.
        return b;
    }

    /**
     *  Compares return values from getter methods in two objects.
     *  Compares only methods that are public.
     *  Will not compare methods that are static, abstract, or native.
     *  The method name must start with 'get'.
     *  @param p1 the first object.
     *  @param p2 the second object.
     */
    private boolean compareMethods(Object p1, Object p2)
    {
        return true;
/*
        //  Verify parameters.
        if(methods == null)
        {
            return;
        }
        if(sb == null)
        {
            return;
        }
        //  Create temp variables.
        Method method = null;
        Class c = null;
        Class[] classes = null;
        String nl = System.getProperty("line.separator");
        String strName = null;
        String strValue = null;
        int x = 0;
        int m = 0;
        //  Loop through all methods.
        for(x=0; x<methods.length; x++)
        {
            //  Get a method.
            method = methods[x];
            m = method.getModifiers();
            //  Must be public.
            if(!Modifier.isPublic(m))
            {
                continue;
            }
            //  Do not process if static.
            if(Modifier.isStatic(m))
            {
                continue;
            }
            //  Do not process if abstract.
            if(Modifier.isAbstract(m))
            {
                continue;
            }
            //  Do not process if native.
            if(Modifier.isNative(m))
            {
                continue;
            }
            //  Get method name.
            strName = method.getName();
            //  Must be a getter.
            if(!strName.startsWith("get"))
            {
                continue;
            }
            //  Must take no parameters.
            classes = method.getParameterTypes();
            if(classes.length != 0)
            {
                continue;
            }
            //  Run it to get the return value.
            Object o = null;
            try
            {
                o = method.invoke(this, null);
            }
            catch(Exception ex)
            {
                System.out.println("Can't run " + this.getClass().getName() + "." + strName + ".");
                ex.printStackTrace();
                ErrorMessage.show(ex, true);
            }
            c = method.getReturnType();
            //  Process String objects.
            if(c == String.class)
            {
                strValue = (String)o;
                sb.append(strName + "() = " + strValue);
                sb.append(nl);
                continue;
            }
            //  Process primitive types.
            if(c == Integer.TYPE)
            {
                Integer I = (Integer)o;
                int i = I.intValue();
                sb.append(strName + "() = " + i);
                sb.append(nl);
                continue;
            }
            if(c == Long.TYPE)
            {
                Long L = (Long)o;
                long l = L.longValue();
                sb.append(strName + "() = " + l);
                sb.append(nl);
                continue;
            }
            if(c == Boolean.TYPE)
            {
                Boolean B = (Boolean)o;
                boolean b = B.booleanValue();
                sb.append(strName + "() = " + b);
                sb.append(nl);
                continue;
            }
            if(c == Float.TYPE)
            {
                Float F = (Float)o;
                float f = F.floatValue();
                sb.append(strName + "() = " + f);
                sb.append(nl);
                continue;
            }
            if(c == Double.TYPE)
            {
                Double D = (Double)o;
                double d = D.doubleValue();
                sb.append(strName + "() = " + d);
                sb.append(nl);
                continue;
            }
            if(c == Byte.TYPE)
            {
                Byte Y = (Byte)o;
                byte y = Y.byteValue();
                sb.append(strName + "() = " + y);
                sb.append(nl);
                continue;
            }
            if(c == Character.TYPE)
            {
                Character R = (Character)o;
                char r = R.charValue();
                sb.append(strName + "() = " + r);
                sb.append(nl);
                continue;
            }
            if(c == Short.TYPE)
            {
                Short T = (Short)o;
                short t = T.shortValue();
                sb.append(strName + "() = " + t);
                sb.append(nl);
                continue;
            }
        }
*/
    }

    //  From Comparator.
    public int compare(Object o1, Object o2) throws ClassCastException
    {
        if(o1 == null)
        {
            throw new ClassCastException("o1 is null.");
        }
        if(o2 == null)
        {
            throw new ClassCastException("o2 is null.");
        }
        if(!(o1 instanceof Equator))
        {
            throw new ClassCastException("Wrong class for o1.");
        }
        if(!(o2 instanceof Equator))
        {
            throw new ClassCastException("Wrong class for o2.");
        }
        if(o1 == o2)
            return 0;
        Equator e1 = (Equator)o1;
        Equator e2 = (Equator)o2;
        if(e1.isLessThan(e2))
        {
            return -1;
        }
        if(e1.isGreaterThan(e2))
        {
            return  1;
        }
        return 0;
    }

    //  From Comparable.
    //  If you want to implement Comparable,
    //  just copy this code into your class.
    public int compareTo(Object o) throws ClassCastException
    {
        int x = Inspector.index.compare(this, o);
        return x;
    }

}
