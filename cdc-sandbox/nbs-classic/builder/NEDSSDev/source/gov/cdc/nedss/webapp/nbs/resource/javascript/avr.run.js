/**
 *  avr.run.js.
 *  JavaScript for run.xsp.
 *  Calls functions that are in sniffer.js and nedss.js.
 *  @author Ed Jenkins
 */

/**
 *  Runs a report.
 */
function RunReport()
{
/*
    var varURL = "/nedss/nfc?\u0026mode=edit\u0026ObjectType=" + REPORT + "\u0026OperationType=" + RUN_REPORT;
    window.alert(varURL);
    window.open("www.csc.com", "test", "resizable");
*/
    document.frm.id_mode.value = "edit";
    document.frm.id_ObjectType.value = REPORT;
    document.frm.id_OperationType.value = RUN_REPORT;
    document.frm.action = "/nbs/nfc";
    document.frm.method = "post";
    document.frm.target = "_blank";
    document.frm.submit();
}
