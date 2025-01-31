package org.immregistries.smm.tester.manager.query;

public class QueryRequest extends Patient {
  private QueryType queryType = QueryType.QBP_Z34;
  
  public QueryType getQueryType() {
    return queryType;
  }
  public void setQueryType(QueryType query) {
    this.queryType = query;
  }


}
