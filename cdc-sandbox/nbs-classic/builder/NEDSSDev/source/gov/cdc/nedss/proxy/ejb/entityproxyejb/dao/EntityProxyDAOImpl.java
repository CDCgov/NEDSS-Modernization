/**
 * Title: EntityProxyDAOImpl data access object.
 * Description: A data access object used to set values on a PersonVO object.  This class currently sets the
 * value of the CityCode on the PersonVO.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author wsteele
 * @version 1.0
 */
package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityProxyDAOImpl extends BMPBase
{
    static final LogUtils logger = new LogUtils(EntityProxyDAOImpl.class.getName());

    public EntityProxyDAOImpl() throws NEDSSSystemException
    {
    }

    /**
    * method to set city code
    * This method sets the value of the city code onto the personVO object.
    * @param    personVO
    * @return   personVO
    * @throws   NEDSSSystemException
    */
    public PersonVO setCityCode(PersonVO personVO) throws NEDSSSystemException
    {
        ArrayList<Object>  entityLocatorParticipationDTCollection  = (ArrayList<Object> )personVO.getTheEntityLocatorParticipationDTCollection();
        if(entityLocatorParticipationDTCollection==null)
        {
            return personVO;
        }
        ArrayList<Object>  newCollection  = new ArrayList<Object> ();
        try
        {
            ArrayList<Object> list = new ArrayList<Object> ();
            for(int i=0;i<entityLocatorParticipationDTCollection.size();i++)
            {
              EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDTCollection.get(i);
              if (entityLocatorParticipationDT.getUseCd() != null &&
                    entityLocatorParticipationDT.getClassCd() != null &&
                     entityLocatorParticipationDT.getCd() != null)
              {
                if(entityLocatorParticipationDT.getUseCd().equals("H") &&
                    entityLocatorParticipationDT.getClassCd().equals("PST"))
                {
                    if(entityLocatorParticipationDT.getCd().equals("C")
                        ||entityLocatorParticipationDT.getCd().equals("H")
                            ||entityLocatorParticipationDT.getCd().equals("A"))
                    {
                        logger.debug("entityLocatorParticipationDT.getCd() = " +  entityLocatorParticipationDT.getCd());
                        list.add(entityLocatorParticipationDT);
                    }
                    else
                        newCollection.add(entityLocatorParticipationDT);
                }
                else
                    newCollection.add(entityLocatorParticipationDT);
              }
              else
               newCollection.add(entityLocatorParticipationDT);
            }
            boolean gotCode = false;

           Iterator<Object>  iter  = list.iterator();
           for(iter = list.iterator(); iter.hasNext(); )
           {
              EntityLocatorParticipationDT codeEntityLocatorParticipationDT = (EntityLocatorParticipationDT) iter.next();
	      if(codeEntityLocatorParticipationDT!=null && codeEntityLocatorParticipationDT.getThePostalLocatorDT()!= null)
              {
		if (codeEntityLocatorParticipationDT.getThePostalLocatorDT().getCityDescTxt() != null
                   && codeEntityLocatorParticipationDT.getThePostalLocatorDT().getStateCd()!= null)
             {
                  codeEntityLocatorParticipationDT.getThePostalLocatorDT().setCityCd(getCode(codeEntityLocatorParticipationDT));
             }
              }
            newCollection.add(codeEntityLocatorParticipationDT);
           }

            personVO.setTheEntityLocatorParticipationDTCollection(newCollection);
        }
        catch(Exception e)
        {
        	logger.fatal("Exception  = "+e.getMessage(), e);
            throw new NEDSSSystemException("error in " + EntityProxyDAOImpl.class.getName() + " " + e);
        }
        return personVO;
    }

     /**
     * Gets the code given an EntityLocatorParticipationDT
     * @param   entityLocatorParticipationDT
     * @return  String value for the code
     * @throws  Exception
     */
     @SuppressWarnings("resource")
	private String getCode(EntityLocatorParticipationDT entityLocatorParticipationDT) throws Exception
     {
        logger.debug("---- in the getCode method -----");
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        String stateCd = entityLocatorParticipationDT.getThePostalLocatorDT().getStateCd();
        logger.debug("State Code = " + stateCd);
        String cityDescTxt = entityLocatorParticipationDT.getThePostalLocatorDT().getCityDescTxt();
        logger.debug("City Description Text = " + cityDescTxt);
        String code = null;
        String aQuery = null;
        
            aQuery = "SELECT state_nm FROM  nbs_srte..State_code WHERE state_cd = ?";
       
        logger.debug(aQuery);

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining dbConnection " +
                "in EntityProxyDAOImpl getCode method for querry: " + aQuery, nsex);
            throw new Exception(nsex.toString());
        }

        try
        {
            String stateNm = null;
            preparedStmt = dbConnection.prepareStatement(aQuery);
            preparedStmt.setString(1, stateCd);
            resultSet = preparedStmt.executeQuery();

            if(resultSet.next())
                stateNm = resultSet.getString(1);

            logger.debug("stateNm  = " + stateNm);
            if(stateNm != null)
            {
              if(cityDescTxt != null)
              {
                String codeDescTxt = cityDescTxt.toUpperCase() + ", " + stateNm.toUpperCase();
                logger.debug("codeDescTxt = " + codeDescTxt);
                aQuery = "SELECT code FROM  nbs_srte..City_code_value WHERE upper(code_desc_txt) = (?)";
             
                logger.debug(aQuery);
                preparedStmt = dbConnection.prepareStatement(aQuery);
                preparedStmt.setString(1, codeDescTxt); 
                resultSet = preparedStmt.executeQuery();
                for (int i=0;resultSet.next();i++)
                {
                 code = resultSet.getString(1);
                  if(i>0)
                  {
                    code = null;
                    break;
                  }
                }
              }
            }

            logger.debug("getCode - code = " + code);
        }
        catch(SQLException sex)
        {
            logger.fatal("Error in EntityProxyDAOImpl getCode method doing query : " + aQuery, sex);
            throw new Exception(sex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return code;
     }


    public static void main(String args[])
    {
        try
        {
            EntityProxyDAOImpl entityProxyDAOImpl = new EntityProxyDAOImpl();
            PersonVO personVO = new PersonVO();
            ArrayList<Object>  entityLocatorParticipationDTCollection  = new ArrayList<Object> ();

            EntityLocatorParticipationDT entityLocatorParticipationDT1 = new EntityLocatorParticipationDT();
            PostalLocatorDT postalLocatorDT1 = new PostalLocatorDT();
            postalLocatorDT1.setStateCd("48");
            postalLocatorDT1.setCityDescTxt("Whitney");
            entityLocatorParticipationDT1.setThePostalLocatorDT(postalLocatorDT1);
            entityLocatorParticipationDT1.setUseCd("H");
            entityLocatorParticipationDT1.setClassCd("PST");
            entityLocatorParticipationDT1.setCd("C");
            entityLocatorParticipationDTCollection.add(entityLocatorParticipationDT1);

            EntityLocatorParticipationDT entityLocatorParticipationDT2 = new EntityLocatorParticipationDT();
            PostalLocatorDT postalLocatorDT2 = new PostalLocatorDT();
            postalLocatorDT2.setStateCd("01");
            postalLocatorDT2.setCityDescTxt("Acton");
            entityLocatorParticipationDT2.setThePostalLocatorDT(postalLocatorDT2);
            entityLocatorParticipationDT2.setUseCd("H");
            entityLocatorParticipationDT2.setClassCd("PST");
            entityLocatorParticipationDT2.setCd("H");
            entityLocatorParticipationDTCollection.add(entityLocatorParticipationDT2);

            EntityLocatorParticipationDT entityLocatorParticipationDT3 = new EntityLocatorParticipationDT();
            PostalLocatorDT postalLocatorDT3 = new PostalLocatorDT();
            postalLocatorDT3.setStateCd("04");
            postalLocatorDT3.setCityDescTxt("Fredonia");
            entityLocatorParticipationDT3.setThePostalLocatorDT(postalLocatorDT3);
            entityLocatorParticipationDT3.setUseCd("H");
            entityLocatorParticipationDT3.setClassCd("PST");
            entityLocatorParticipationDT3.setCd("A");
            entityLocatorParticipationDTCollection.add(entityLocatorParticipationDT3);


            EntityLocatorParticipationDT entityLocatorParticipationDT4 = new EntityLocatorParticipationDT();
            PostalLocatorDT postalLocatorDT4 = new PostalLocatorDT();
            postalLocatorDT4.setStateCd("04");
            postalLocatorDT4.setCityDescTxt("Fredonia");
            entityLocatorParticipationDT4.setThePostalLocatorDT(postalLocatorDT4);
            entityLocatorParticipationDT4.setUseCd("H");
            entityLocatorParticipationDT4.setClassCd("PST");
            entityLocatorParticipationDT4.setCd("E");
            entityLocatorParticipationDTCollection.add(entityLocatorParticipationDT4);


            personVO.setTheEntityLocatorParticipationDTCollection(entityLocatorParticipationDTCollection);

            personVO = entityProxyDAOImpl.setCityCode(personVO);

            ArrayList<Object> ar = (ArrayList<Object> )personVO.getTheEntityLocatorParticipationDTCollection();

            for (int i=0;i<ar.size();i++)
            {
                EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)ar.get(i);

                logger.debug("--------------------");
                logger.debug("CityDescTxt = " + entityLocatorParticipationDT.getThePostalLocatorDT().getCityDescTxt());
                logger.debug("StateCd = " + entityLocatorParticipationDT.getThePostalLocatorDT().getStateCd());
                logger.debug("city code = " + entityLocatorParticipationDT.getThePostalLocatorDT().getCityCd());
                logger.debug("piority code = " + entityLocatorParticipationDT.getCd());
            }
        }
        catch(Exception e)
        {
            logger.error("error in main");
            e.printStackTrace();
        }
    }

}
