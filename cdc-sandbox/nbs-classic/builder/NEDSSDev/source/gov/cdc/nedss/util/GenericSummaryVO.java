package gov.cdc.nedss.util;

public class GenericSummaryVO extends UidSummaryVO{

  private String str1 = null;
  private String str2 = null;
  private String str3 = null;
  private String str4 = null;
  private Long uid1;
   private Long uid2;

  public GenericSummaryVO() {
  }

  public String getStr1()
  {
    return str1;
  }

  public void setStr1(String aStr1)
  {
    str1 = aStr1;
    setItDirty(true);
  }


  public String getStr2()
 {
   return str2;
 }

 public void setStr2(String aStr2)
 {
   str2 = aStr2;
   setItDirty(true);
 }

 public String getStr3()
 {
   return str3;
 }

 public void setStr3(String aStr3)
 {
   str3 = aStr3;
   setItDirty(true);
 }

 public String getStr4()
 {
   return str4;
 }

 public void setStr4(String aStr4)
 {
   str4 = aStr4;
   setItDirty(true);
 }
 public Long getUid2()
{
  return uid2;
}
public void setUid2 (Long aUid2)
{
  uid2= aUid2;
}

public Long getUid1()
{
  return uid1;
}
public void setUid1 (Long aUid1)
{
  uid1= aUid1;
}




}