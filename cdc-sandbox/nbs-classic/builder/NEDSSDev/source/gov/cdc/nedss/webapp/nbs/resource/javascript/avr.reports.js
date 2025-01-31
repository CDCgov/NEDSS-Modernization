/**
 *  avr.reports.js.
 *  JavaScript for reports.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

//  Arrays for storing data.
var aPrivate = new Array();
var aPublic = new Array();
var aTemplates = new Array();
var aReportingFacility = new Array();
var a = null;

//  Flags used for sorting.
var AVR_SORT_LIST_PRIVATE  = 0;
var AVR_SORT_LIST_PUBLIC   = 1;
var AVR_SORT_LIST_TEMPLATE = 2;
var AVR_SORT_FIELD_NAME    = 0;
var AVR_SORT_FIELD_DATE    = 1;
var AVR_SORT_FIELD_STATUS  = 2;
var varSortField = [ 99, 99, 99 ];
var varSortDirection = [ true, true, true ];

function SortByNameAscending(a, b)
{
    var x = a[2];
    var y = b[2];
    var z = SortAscending(x, y);
    if(z == 0)
    {
        x = a[8];
        y = b[8];
        z = SortAscending(x, y);
    }
    return z;
}

function SortByNameDescending(a, b)
{
    var x = a[2];
    var y = b[2];
    var z = SortDescending(x, y);
    if(z == 0)
    {
        x = a[8];
        y = b[8];
        z = SortDescending(x, y);
    }
    return z;
}

function SortByDateAscending(a, b)
{
    var x = a[8];
    var y = b[8];
    var z = SortAscending(x, y);
    if(z == 0)
    {
        x = a[2];
        y = b[2];
        z = SortAscending(x, y);
    }
    return z;
}

function SortByDateDescending(a, b)
{
    var x = a[8];
    var y = b[8];
    var z = SortDescending(x, y);
    if(z == 0)
    {
        x = a[2];
        y = b[2];
        z = SortDescending(x, y);
    }
    return z;
}

function SortByStatusAscending(a, b)
{
    var x = a[5];
    var y = b[5];
    var z = SortAscending(x, y);
    if(z == 0)
    {
        x = a[2];
        y = b[2];
        z = SortAscending(x, y);
        if(z == 0)
        {
            x = a[8];
            y = b[8];
            z = SortAscending(x, y);
        }
    }
    return z;
}

function SortByStatusDescending(a, b)
{
    var x = a[5];
    var y = b[5];
    var z = SortDescending(x, y);
    if(z == 0)
    {
        x = a[2];
        y = b[2];
        z = SortDescending(x, y);
        if(z == 0)
        {
            x = a[8];
            y = b[8];
            z = SortDescending(x, y);
        }
    }
    return z;
}

function UpdatePrivateTable()
{
    var x = 0;
    var y = aPrivate.length;
    var z = null;
    var sDetails = "id_PrivateReportsTable_Details_";
    var sRun     = "id_PrivateReportsTable_Run_";
    var sDelete  = "id_PrivateReportsTable_Delete_";
    var sName    = "id_PrivateReportsTable_Name_";
    var sDate    = "id_PrivateReportsTable_Date_";
    var sStatus  = "id_PrivateReportsTable_Status_";
    var s = null;
    var e = null;
    var varStatus = null;
    var varDetails = null;
    var varRun = null;
    var varDelete = null;
    for(x=0; x<y; x++)
    {
        a = aPrivate[x];
        z = new String(x);
        varStatus = a[5];
        if(varStatus == "Public")
        {
            varDetails = varSEC148 ? "Visible" : "Invisible";
            varRun     = varSEC147 ? "Visible" : "Invisible";
            varDelete  = varSEC142 ? "Visible" : "Invisible";
        }
        if(varStatus == "Private")
        {
            varDetails = varSEC156 ? "Visible" : "Invisible";
            varRun     = varSEC155 ? "Visible" : "Invisible";
            varDelete  = varSEC150 ? "Visible" : "Invisible";
        }
        s = sDetails + z;
        e = getElementByIdOrByName(s);
        e.className = varDetails;
        s = sRun + z;
        e = getElementByIdOrByName(s);
        e.className = varRun;
        s = sDelete + z;
        e = getElementByIdOrByName(s);
        e.className = varDelete;
        s = sName + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[2];
        s = sDate + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[4];
        s = sStatus + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[5];
    }
}

function ShowPrivateDetails(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aPrivate.length-1)
    {
        return;
    }
    a = aPrivate[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    var varTitle = a[2];
    var varDescription = a[3];
    var varDateCreated = a[4];
    var varStatus = a[5];
    var varReportType = a[6];
    var varOwnerUID = a[7];
    var varDateCreatedNum = a[8];
    var n = getElementByIdOrByName("id_Private_Name");
    var d = getElementByIdOrByName("id_Private_Description");
    var c = getElementByIdOrByName("id_Private_Created");
    var s = getElementByIdOrByName("id_Private_Status");
    n.innerHTML = varTitle;
    d.innerHTML = varDescription;
    c.innerHTML = varDateCreated;
    s.innerHTML = varStatus;
}

function RunReportPrivate(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aPrivate.length-1)
    {
        return;
    }
    a = aPrivate[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    document.frm.mode.value = "edit";
    document.frm.ObjectType.value = REPORT;
    document.frm.OperationType.value = REPORT_BASIC;
    document.frm.DataSourceUID.value = varDataSourceUID;
    document.frm.ReportUID.value = varReportUID;
    post();
}

function DeleteReport(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aPrivate.length-1)
    {
        return;
    }
    a = aPrivate[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    document.frm.mode.value = "edit";
    document.frm.ObjectType.value = REPORT;
    document.frm.OperationType.value = REPORT_DELETE;
    document.frm.DataSourceUID.value = varDataSourceUID;
    document.frm.ReportUID.value = varReportUID;
    post();
}

function UpdatePublicTable()
{
    var x = 0;
    var y = aPublic.length;
    var z = null;
    var sName = "id_PublicReportsTable_Name_";
    var sDate = "id_PublicReportsTable_Date_";
    var s = null;
    var e = null;
    for(x=0; x<y; x++)
    {
        a = aPublic[x];
        z = new String(x);
        s = sName + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[2];
        s = sDate + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[4];
    }
}

function ShowPublicDetails(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aPublic.length-1)
    {
        return;
    }
    a = aPublic[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    var varTitle = a[2];
    var varDescription = a[3];
    var varDateCreated = a[4];
    var varStatus = a[5];
    var varReportType = a[6];
    var varOwnerUID = a[7];
    var varDateCreatedNum = a[8];
    var n = getElementByIdOrByName("id_Public_Name");
    var d = getElementByIdOrByName("id_Public_Description");
    var c = getElementByIdOrByName("id_Public_Created");
    n.innerHTML = varTitle;
    d.innerHTML = varDescription;
    c.innerHTML = varDateCreated;
}

function RunReportPublic(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aPublic.length-1)
    {
        return;
    }
    a = aPublic[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    document.frm.mode.value = "edit";
    document.frm.ObjectType.value = REPORT;
    document.frm.OperationType.value = REPORT_BASIC;
    document.frm.DataSourceUID.value = varDataSourceUID;
    document.frm.ReportUID.value = varReportUID;
    post();
}

function UpdateTemplateTable()
{
    var x = 0;
    var y = aTemplates.length;
    var z = null;
    var sName = "id_ReportTemplatesTable_Name_";
    var sDate = "id_ReportTemplatesTable_Date_";
    var s = null;
    var e = null;
    for(x=0; x<y; x++)
    {
        a = aTemplates[x];
        z = new String(x);
        s = sName + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[2];
        s = sDate + z;
        e = getElementByIdOrByName(s);
        e.innerHTML = a[4];
    }
}

function ShowTemplateDetails(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aTemplates.length-1)
    {
        return;
    }
    a = aTemplates[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    var varTitle = a[2];
    var varDescription = a[3];
    var varDateCreated = a[4];
    var varStatus = a[5];
    var varReportType = a[6];
    var varOwnerUID = a[7];
    var varDateCreatedNum = a[8];
    var n = getElementByIdOrByName("id_Templates_Name");
    var d = getElementByIdOrByName("id_Templates_Description");
    var c = getElementByIdOrByName("id_Templates_Created");
    n.innerHTML = varTitle;
    d.innerHTML = varDescription;
    c.innerHTML = varDateCreated;
}

function RunReportTemplate(pRecord)
{
    if(pRecord < 0)
    {
        return;
    }
    if(pRecord > aTemplates.length-1)
    {
        return;
    }
    a = aTemplates[pRecord];
    var varDataSourceUID = a[0];
    var varReportUID = a[1];
    document.frm.mode.value = "edit";
    document.frm.ObjectType.value = REPORT;
    document.frm.OperationType.value = REPORT_BASIC;
    document.frm.DataSourceUID.value = varDataSourceUID;
    document.frm.ReportUID.value = varReportUID;
    post();
}
