package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AjaxAutocompleterChoicesServlet extends HttpServlet {

    public static final String KEY_VALUE = "value";
    public static final String KEY_DELAY = "delay";
    public static final String KEY_TYPE = "type";

    public static final String VAR_LIST = "list";

    public void doGet( HttpServletRequest request,
                       HttpServletResponse response )
            throws IOException, ServletException {
        if (request.getParameter( KEY_DELAY ) != null) {
            delay(request.getParameter( KEY_DELAY ));
        }
        String prefix = request.getParameter( KEY_VALUE );
        String type = request.getParameter( KEY_TYPE );
        
        List<Object> matches = new ArrayList<Object> ();
        ArrayList<Object> list = null;
        if(type != null && type.equalsIgnoreCase("city"))
        	list = (ArrayList<Object> )request.getSession().getAttribute("cityList");
        else if(type != null && type.equalsIgnoreCase("investigator"))
        	list = (ArrayList<Object> )request.getSession().getAttribute("qecList");
        else if(type != null && type.equalsIgnoreCase("organization"))
        	list = (ArrayList<Object> )request.getSession().getAttribute("qecListORG");
        if (list != null){
       Iterator<Object>  it = list.iterator();
        int i = 0;
        
        while(it.hasNext()){
        	DropDownCodeDT dropDownCodeDT = (DropDownCodeDT)it.next();
        	if(dropDownCodeDT.getValue() != null && dropDownCodeDT.getValue().toLowerCase().startsWith(prefix.toLowerCase())){
        		i++;
        		matches.add( dropDownCodeDT);
        		if(i == 10 || i>10)
        			break;
        	}
        }
        String jspPath = "/jsp/choice-list.jsp";
        request.setAttribute( VAR_LIST, matches );
        request.getRequestDispatcher( jspPath )
            .forward( request, response );
        }
    }

    public void doPost( HttpServletRequest request,
                        HttpServletResponse response )
            throws IOException, ServletException {
        doGet( request, response );
    }

    private void delay( String delayValue ) {
        try {
            Thread.sleep( Integer.parseInt( delayValue ) );
        }
        catch (Exception e) {
            //On failure, perform no delay
        }
    }

}
