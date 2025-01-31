	<%@page import= "java.util.*"%>
	 <SCRIPT LANGUAGE="JavaScript">
 		
 		function previewMultiSelectRule(opt,tub){
			var sel= getElementByIdOrByName("mulSrcF");
			var srcmul=",";
			//alert(sel);
				var selected = new Array();
            var index = 0;
            var inp=tub;
			for (var i = 0; i < sel.length; i++) {
            if (sel.options[i].selected) {
             //alert(sel.options[i].value);
			 srcmul = srcmul + sel.options[i].value+",";
           }
         }

            /*for (var intLoop = 0; intLoop < opt.length; intLoop++) {
               if ((opt[intLoop].selected)) {
                  index = selected.length;
                  selected[index] = new Object;
                  selected[index].value = opt[intLoop].value;
                  selected[index].index = intLoop;
               }
            }*/
            document.forms[0].action ="/nbs/RuleAdmin.do?method=firepreviewRule&srcMulSelIden="+inp+"&srcIdenVal="+srcmul;
 		    document.forms[0].submit();
 		
 		}
 			
 		function previewRule(tub,size) {
 		         var inp=tub;
                 var srcFldSet = getElementByIdOrByName("srcFlds");
			     var j = 1;				
				 var k=0;
				
		         var select = srcFldSet.getElementsByTagName("select");
				    for(var i1 = 0; i1 < select.length; i1++)	{
					  if(select[i1].value == "SELECTED"){
						  
                       select[i1].value = "";						
					  }	
					}
				  if(j <   select.length) {
		           for(var i = 0; i < select.length; i++)	{					
					 if(select[i].value != "" & select[i].value != "SELECTED"){
						
                        inp = select[i].value;
						k=i;
						break;
					  }				     
			         }
				  } else {
					  k=0;

				  }
			    
 			       document.forms[0].action ="/nbs/RuleAdmin.do?method=firepreviewRule&srcIden="+select[k].name+"&srcIdenVal="+select[k].value;
 			      document.forms[0].submit();
 		}	

			function previewTextRule(tub) {
 		         var inp=tub;    
				 var val = getElementByIdOrByName(tub).value;
				 alert(val);

			    
 			       document.forms[0].action ="/nbs/RuleAdmin.do?method=firepreviewRule&srcIden="+tub+"&srcIdenVal="+val;
 			      document.forms[0].submit();
 		}	
 		function cancelPreview() { 		          		
 				document.forms[0].action ="/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result";
 			    document.forms[0].submit();
 		}				
		
		
 </SCRIPT>   
     
         	          		      		
      <% int i=0;
	    ArrayList tarList = new ArrayList();
		ArrayList srcList = new ArrayList();		

	  if(request.getAttribute("manageTFList") != null){         
            tarList = (ArrayList)request.getAttribute("manageTFList");
           }

       if(request.getAttribute("manageList") != null){         
            srcList = (ArrayList)request.getAttribute("manageList");
           }
			request.setAttribute("manageTFList",tarList);
			request.setAttribute("manageList",srcList);
	   %>	
 
	   <display:table name="manageListold" class="its" pagesize="10"  id="parent" requestURI="">
                          <display:column property="ruleInstanceUid" title="Rule InstanceUid"  class="dstag"/>
						 <display:column property="ruleName" title="Rule Name" class="dstag"/>
					    <display:column property="conseqIndtxt" title="Conseq Ind" class="dstag"/>
				         <display:column property="comments" title="Comment" class="dstag"/>		             
						  <display:setProperty name="basic.empty.showtable" value="true"/>
			          </display:table>

	 <%
	 String sb1 = (String) request.getAttribute("previewR");
                  out.println(sb1);
				  
				  %> 

				