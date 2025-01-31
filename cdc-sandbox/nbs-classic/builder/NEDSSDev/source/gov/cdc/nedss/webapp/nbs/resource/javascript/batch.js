/**
 * Replace methods in xsl. Popualate BATCH_RECORS in xsl.
 */

var BATCH_RECORDS = [];

function writeQuestion(subSecNm,pattern,questionbody) 
{
  var t = getElementByIdOrByName(subSecNm);
  var len=0;
  $j("#" + subSecNm ).show();
  var ssArr = getSubSectionElements(subSecNm);
  len = ssArr.length;
  for (var i = 0; i <len+1; i++){
    $j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
    $j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
  }
  var map = {}; var emptyrow="yes"; var code = "1013";
  dwr.engine.beginBatch();
  for(var i = 0; i < ssArr.length; i++){
    var qId = ssArr[i].qid; 
    var componentId = ssArr[i].cid;
    if( qId != null ){
      if(getElementByIdOrByName(qId) != null){
        map[qId] = getRepeatingBlockUtilDispText(qId, componentId);
        if( "1017" == componentId)
           map[qId + "Disp"] = $j("#" + qId + "Disp").html();
        emptyrow = repeatingBlockCheckForEmptyRow(qId, emptyrow);
      }
    }  
  }//for
  
  var batchentry = { subsecNm:subSecNm, id:viewed,answerMap:map};  
  if (emptyrow == "yes") {
    var errorrow = subSecNm + "errorMessages";
    displayErrors(errorrow, " At least one field must be entered when adding a repeating block.");
    dwr.engine.endBatch();
    return false;
  }
  JPageForm.setAnswer(batchentry, subSecNm);
  fillTable(subSecNm,pattern,questionbody);
  clearClicked(subSecNm);
  viewed = -1;
  dwr.engine.endBatch();
}

function fillTable(subSecNm,pattern,questionbody) 
{
  JPageForm.getAllAnswer(subSecNm,function(answer) {
    // Delete all the rows except for the "pattern" row
    dwr.util.removeAllRows(questionbody, { filter:function(tr) { return (tr.id != pattern); }});
    dwr.util.setEscapeHtml(false);
    // Create a new set cloned from the pattern row
    var ans, id,rowclass="";
    var ssArr = getSubSectionElements(subSecNm);
    if( ssArr.length == 0 )
      return;
    
    if(answer !=null && answer.length != 0){
      for (var i = 0; i < answer.length; i++){
          ans = answer[i]; 
          id = ans.id;         
          dwr.util.cloneNode(pattern, { idSuffix:id });
          for (var key in ans.answerMap) {
            var qArr = $j.grep(ssArr, function(e){ return (e.qid == key); } );
            if( qArr.length > 0 ){
              var val = ans.answerMap[key];   
              if(qArr[0].cid == '1017'){
                val = ans.answerMap[key + "Disp"] ? ans.answerMap[key + "Disp"] : val ;
              }
              val = repeatingBlockFillValue(val);
              dwr.util.setValue("table" + key + id, val);
            }
          }
          $(pattern + id).show();   
          answerCache[id] = ans;
          if(rowclass=="")
            rowclass="odd"; 
            getElementByIdOrByName(pattern  + id).setAttribute("className",rowclass); 
            $j("#" + pattern  + id).css("background-color","white"); 
            if(rowclass=="even"){
              rowclass = "odd";
            } 
      }
      $j("#no"+pattern).hide();
    } else {
      $j("#no"+pattern).show();
    }
  });
}

function viewClicked(eleid,subSecNm)
{
  viewEditClicked(eleid,subSecNm, 'v');
}

function editClicked(eleid,subSecNm)
{
  viewEditClicked(eleid,subSecNm, 'e');
}

function viewEditClicked(eleid,subSecNm,mode) 
{  
  var t = getElementByIdOrByName(subSecNm);
  var len=0;
  $j("#" + subSecNm ).show();    
  var ssArr = getSubSectionElements(subSecNm); 
  len = ssArr.length;
  for (var i = 0; i <len+1; i++){
    $j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
    $j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
  }
  for (var i = 0; i < len+1; i ++) {
    $j($j("#" + "questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");
  }
  var key;
  dwr.engine.beginBatch(); 
  clearClicked(subSecNm); 
  // id of the form "edit{id}", eg "edit42". We lookup the "42"
  var answer = answerCache[eleid.substring(4+subSecNm.length)];
  if(answer==null || answer==''){
	  answer = answerCache[eleid.substring(4)];
  }
  viewed = answer.id;  
  var map = answer.answerMap;  
  var mulVal;
  var partVal;
  var selectedmulVal;
  var handlemulVal;
  var code = "1013";  
  for(var i = 0; i < ssArr.length; i++){
    var key = ssArr[i].qid;
    dwr.util.setValue( key, "");
    dwrUtilSetValue( key+"UNIT", "");
    dwrUtilSetValue( key+"Oth", "");
    dwrUtilSetValue( key+"Date", "");
    dwrUtilSetValue( key+"User", "");
    var type = ssArr[i].cid;
    if( type == "1007" || type == "1013"){
      if(getElementByIdOrByName(key) != null){
        autocompTxtValuesForJSPByElement(key);
      }
    }
    if(getElementByIdOrByName( key + "UNIT") != null ) {
      autocompTxtValuesForJSPByElement(key + "UNIT"); 
    }
  }    
  JPageForm.updateAnswer(answer,function(answer) {
      for (var key in answer.answerMap) {
        var uiComponent = "";
        var qArr =  $j.grep(ssArr, function(e){ return (e.qid == key) ; } );
        if( qArr.length == 0 )
            continue;
        var brObj = qArr[0];
        uiComponent = brObj.cid; 
          
        if(answer.answerMap[key] != null && answer.answerMap[key] != '' && (uiComponent == "1013" || uiComponent == "1017")){
          if( (code == "1019" || code  == "1008")){
            mulVal = answer.answerMap[key];
            repeatingBlockHandleMultiVal (mulVal, key);
          } else if( uiComponent == "1017"){
            partVal = answer.answerMap[key];
            if( mode == 'v')
              repeatingBlockHandleViewParticipant (partVal, key, answer.answerMap[key+"Disp"]);
            else
              repeatingBlockHandleEditParticipant (partVal, key, answer.answerMap[key+"Disp"]);
          }
          if(key+"-selectedValues" != null && getElementByIdOrByName(key+"-selectedValues") != null){
            displaySelectedOptions(getElementByIdOrByName(key), key+"-selectedValues")
          } 
        } 
        else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf(":") !=  -1 && getElementByIdOrByName(key+"Oth") != undefined){
          var otherVal = answer.answerMap[key];
          dwr.util.setValue(key,otherVal.substring(0,otherVal.indexOf(":")));
          dwr.util.setValue(key+"Oth", otherVal.substring(otherVal.indexOf(":")+1));
          getElementByIdOrByName(key+"Oth").disabled=false;
        }
        else if(answer.answerMap[key] != null && answer.answerMap[key] != '' && answer.answerMap[key].indexOf("$sn$") !=  -1){  
          var fval = answer.answerMap[key];
          dwr.util.setValue(key,fval.substring(0,fval.indexOf("$sn$"))); 
          dwr.util.setValue(key+"UNIT", fval.substring(fval.indexOf("$sn$")+4,fval.length));
        } else { 
          mulVal = answer.answerMap[key];             
          if(mulVal != null && mulVal.indexOf("$MulOth$") != -1){
            othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
            mulVal = mulVal.substring(0,mulVal.indexOf("$MulOth$") );
            if(mulVal  != null && mulVal  != ''){ 
              getElementByIdOrByName(key).value  = othVal ;
            }
            if(othVal != null && othVal != ''){
             getElementByIdOrByName(key+"Oth").value = othVal ;
            }
          } else{
            if(answer.answerMap[key] != null && answer.answerMap[key] != ''){
            	dwr.util.setValue(key,answer.answerMap[key]);
            }
          } 
        }
        var disabled = ( mode == 'v') ? true : false;
        var keyL =  key + "L";
        var keyOth = key + "Oth";
        if(getElementByIdOrByName(key) != null){
          if (uiComponent != "1019"){ 
             getElementByIdOrByName(key).disabled = disabled;
          }
          getElementByIdOrByName(keyL).disabled = disabled;
          $j("#"+key).parent().parent().find("img").attr("disabled", disabled);
          $j("#"+key).parent().parent().find("img").attr("tabIndex", "-1");
          
          $j("#"+key).parent().parent().find("input").attr("disabled", disabled);
          $j("#"+key).parent().parent().find("textarea").attr("disabled", disabled);
        }
        if(key+"-selectedValues" != null && getElementByIdOrByName(key+"-selectedValues") != null){    
          getElementByIdOrByName(key+"-selectedValues").disabled = trdisabledue;
        }
        if(getElementByIdOrByName(key+"Oth") != null){
          getElementByIdOrByName(key+"Oth").disabled = disabled;
          getElementByIdOrByName(key+"OthL").disabled = disabled;
          $j("#"+key+"Oth").parent().parent().find("input").attr("disabled",  disabled);
        } 
        
      } //for
      
      $j("#AddButtonToggle"+subSecNm).hide();
      if( mode == 'v'){
        $j("#AddNewButtonToggle"+subSecNm).show();
        $j("#UpdateButtonToggle"+subSecNm).hide();
      } else {
        $j("#AddNewButtonToggle"+subSecNm).hide();
        $j("#UpdateButtonToggle"+subSecNm).show();
      }
      
  }); //JPageForm
  dwr.engine.endBatch();
}
           
function deleteClicked(eleid,subSecNm,pattern,questionbody) 
{ 
  var t = getElementByIdOrByName(subSecNm);    
  var len=0;
  $j("#" + subSecNm).show();      
  var ssArr = getSubSectionElements(subSecNm);
  len = ssArr.length;
  for (var i = 0; i <len+1; i++){
    $j($j(t).find("tbody > tr:odd").get(i)).css("background-color","#DCE7F7");
    $j($j(t).find("tbody > tr:even").get(i)).css("background-color","#DCE7F7");
  }
  for (var i = 0; i < len+1; i ++)   {
    $j($j("#questionbody"  +subSecNm).find("tr").get(i)).css("background-color","white");  
  }
  var answer = answerCache[eleid.substring(6+subSecNm.length)];
  if (confirm("You have indicated that you would like to delete this row. Would you like to continue with this action?")) {
    dwr.engine.beginBatch();
    JPageForm.deleteAnswer(answer);
    fillTable(subSecNm,pattern,questionbody);
    for(var i = 0; i < ssArr.length; i++){
      var key = ssArr[i].qid;
      if(key != null && getElementByIdOrByName(key) != null){
        dwr.util.setValue(key, "");
        dwrUtilSetValue(key+"Oth", "");  
        dwrUtilSetValue(key+"UNIT", ""); 
        if(getElementByIdOrByName(key+"-selectedValues") != null){
            displaySelectedOptions(getElementByIdOrByName(key), key+"-selectedValues")
        }
        var type = ssArr[i].cid;;
        if(type == "1007" || type == "1013"){
          autocompTxtValuesForJSPByElement(key);
        }
        if(getElementByIdOrByName(key+"UNIT") != null ) {
          autocompTxtValuesForJSPByElement(key+"UNIT"); 
        }
      }
    }
    $j("#AddButtonToggle"+subSecNm).show();                               
    $j("#AddNewButtonToggle"+subSecNm).hide();
    $j("#UpdateButtonToggle"+subSecNm).hide();
    clearClicked(subSecNm);
    viewed = -1;
    dwr.engine.endBatch();
  }//if:confirm
}

function unhideBatchImg(subSecNm)
{
  var t = getElementByIdOrByName(subSecNm);
  var len = 0;
  $j("#" + subSecNm).show();
  var ssArr = getSubSectionElements(subSecNm);
  len = ssArr.length;
  for (var i = 0; i < len + 1; i++) {
    $j($j(t).find("tbody > tr:odd").get(i)).css("background-color", "#BCD4F5");
    $j($j(t).find("tbody > tr:even").get(i)).css("background-color", "#BCD4F5");
  }
  for (var i = 0; i < len + 1; i++) {
    $j($j("#questionbody" + subSecNm).find("tr").get(i)).css("background-color", "white");
  }                
  $j("#nopattern"  +subSecNm).css("background-color","white");
}

function clearClicked(subSecNm) 
{ 
  var ssArr = getSubSectionElements(subSecNm); 
  for(var i=0;i<ssArr.length;i++){     
    key =   ssArr[i].qid; 
    if(key != null && getElementByIdOrByName(key) != null){
       repeatingBatchClearFields(key, ssArr[i].cid);
    }
  }    
  
  viewed = -1;
  $j("#AddButtonToggle"+subSecNm).show(); 
  $j("#AddNewButtonToggle"+subSecNm).hide();             
  $j("#UpdateButtonToggle"+subSecNm).hide();

  
} // clearClicked

function getSubSectionElements(subSecNm)
{
  return $j.grep(BATCH_RECORDS, function(e) { return (e.ssName == subSecNm); });
}

function dwrUtilSetValue(key, val)
{
  if(getElementByIdOrByName(key) != null )
    dwr.util.setValue(key, val);
}