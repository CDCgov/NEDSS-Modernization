function ToggleRace(pCHK, pTR, pLST)
{
    var b;
    b = pCHK.checked;
    if(b == true)
    {
        pTR.className = "visible";
    }
    else
    {
        pLST.selectedIndex = -1;
        pTR.className = "none";
    }
}

function GetMonthMax(pMonth, pYear)
{
    if
    (
        (pMonth < 1)
    ||  (pMonth > 12)
    )
    {
        return 0;
    }
    if
    (
        (pYear < 1000)
    ||  (pYear > 9999)
    )
    {
        return 0;
    }
    var varMonths = new Array(13);
    varMonths[0]  = 0;
    varMonths[1]  = 31;
    varMonths[2]  = 28;
    varMonths[3]  = 31;
    varMonths[4]  = 30;
    varMonths[5]  = 31;
    varMonths[6]  = 30;
    varMonths[7]  = 31;
    varMonths[8]  = 31;
    varMonths[9]  = 30;
    varMonths[10] = 31;
    varMonths[11] = 30;
    varMonths[12] = 31;
    var varMod4   = pYear %   4;
    var varMod100 = pYear % 100;
    var varMod400 = pYear % 400;
    if(varMod4   == 0) varMonths[1] = 29;
    if(varMod100 == 0) varMonths[1] = 28;
    if(varMod400 == 0) varMonths[1] = 29;
    return varMonths[pMonth];
}

function CalculateDOB()
{
    var varAGE = frm.per_txtPER135.value;
    var varReported = frm.per_txtPER136.value;
    if(isEmpty(varAGE) == true)
    {
        frm.per_txtPER109.value = "";
        return false;
    }
    if(isNumeric(varAGE) == false)
    {
        frm.per_txtPER109.value = "";
        return false;
    }
    if(varAGE > 150)
    {
        frm.per_txtPER109.value = "";
        return false;
    }
    if(isEmpty(frm.per_txtPER136.value) == true)
    {
        frm.per_txtPER109.value = "";
        return false;
    }
    if(isDate(frm.per_txtPER136.value) == false)
    {
        frm.per_txtPER109.value = "";
        return false;
    }
    var d = StringToDate(varReported);
    var varM = d.getMonth() + 1;
    var varD = d.getDate();
    var varY = d.getFullYear();
    var varH = 0;
    var x = 0;
    var y = 0;
    if(isOptionSelected(frm.per_lstPER105, "years") == true)
    {
        varY -= varAGE;
    }
    if(isOptionSelected(frm.per_lstPER105, "months") == true)
    {
        for(x=0; x<varAGE; x++)
        {
            varM--;
            if(varM < 1)
            {
                varY--;
                varM = 12;
            }
        }
    }
    if(isOptionSelected(frm.per_lstPER105, "weeks") == true)
    {
        varAGE *= 7;
        for(x=0; x<varAGE; x++)
        {
            varD--;
            if(varD < 1)
            {
                varM--;
                if(varM < 1)
                {
                    varY--;
                    varM = 12;
                }
                varD = GetMonthMax(varM, varY);
            }
        }
    }
    if(isOptionSelected(frm.per_lstPER105, "days") == true)
    {
        for(x=0; x<varAGE; x++)
        {
            varD--;
            if(varD < 1)
            {
                varM--;
                if(varM < 1)
                {
                    varY--;
                    varM = 12;
                }
                varD = GetMonthMax(varM, varY);
            }
        }
    }
    if(isOptionSelected(frm.per_lstPER105, "hours") == true)
    {
        for(x=0; x<varAGE; x++)
        {
            varH--;
            if(varH < 1)
            {
                varD--;
                if(varD < 1)
                {
                    varM--;
                    varD = GetMonthMax(varM, varY);
                }
                varH = 23;
            }
        }
    }
    var varMM = "" + varM;
    var varDD = "" + varD;
    var varYY = "" + varY;
    if(varM < 10)   varMM = "0" + varMM;
    if(varD < 10)   varDD = "0" + varDD;
    if(varY < 10)   varYY = "0" + varYY;
    if(varY < 100)  varYY = "0" + varYY;
    if(varY < 1000) varYY = "0" + varYY;
    var s = varMM + "/" + varDD + "/" + varYY;
    d = StringToDate(s);
    s = DateToString(d);
    frm.per_txtPER109.value = s;
    return true;
}

function per_cmdAdd_onclick()
{
    var x = 0;
    var varMSG = "";
    HideTopError();
    HideError(per_lstPER320_error);
    HideError(per_txtPER307_error);
    HideError(per_txtPER303_error);
    x += CheckSelectedGeneric(frm.per_lstPER320, per_lstPER320_error, "a Name Type");
    x += CheckDates
    (
        frm.per_txtPER307.value,
        frm.per_txtPER327.value,
        "Valid From",
        "Valid To",
        per_txtPER307_error
    );
    x += CheckNumericGeneric(frm.per_txtPER303.value, false, per_txtPER303_error, "Duration");
    if(x > 0)
    {
        return false;
    }
    return true;
}

function per_txtPER135_onblur()
{
    CalculateDOB();
}

function per_lstPER105_onchange()
{
    CalculateDOB();
}

function per_txtPER136_onblur()
{
    CalculateDOB();
}

function id_cmdAdd_onclick()
{
    var x = 0;
    HideTopError();
    HideError(id_lstEID117_error);
    HideError(id_txtEID114_error);
    HideError(id_lstEID103_error);
    HideError(id_txtEID120_error);
    HideError(id_txtEID105_error);
    x += CheckSelectedGeneric(frm.id_lstEID117, id_lstEID117_error, "an ID Type");
    if
    (
        (isOptionSelected(frm.id_lstEID117, "SSN") == true)
    ||  (isOptionSelected(frm.id_lstEID117, "Mother's SSN") == true)
    )
    {
        x += CheckSSNGeneric(frm.id_txtEID114.value, true, id_txtEID114_error, "ID Value");
    }
    x += CheckSelectedGeneric(frm.id_lstEID103, id_lstEID103_error, "an Assigning Authority");
    x += CheckDates
    (
        frm.id_txtEID120.value,
        frm.id_txtEID121.value,
        "Valid From",
        "Valid To",
        id_txtEID120_error
    );
    x += CheckNumericGeneric(frm.id_txtEID105.value, false, id_txtEID105_error, "Duration");
    if(x > 0)
    {
        return false;
    }
    return true;
}

function per_lstPER116_onchange()
{
    ToggleListText(frm.per_lstPER116, frm.per_txtPER117);
}

function per_lstPER122_onchange()
{
    ToggleListText(frm.per_lstPER122, frm.per_txtPER123);
}

function per_lstPER130_onchange()
{
    ToggleListText(frm.per_lstPER130, frm.per_txtPER131);
}

function id_lstEID117_onchange()
{
    ToggleListText(frm.id_lstEID117, frm.id_txtEID118);
}

function id_lstEID103_onchange()
{
    ToggleListText(frm.id_lstEID103, frm.id_txtEID104);
}

function chkAmericanIndian_onclick()
{
    ToggleRace(frm.chkAmericanIndian, trAmericanIndian, frm.lstAmericanIndian);
}

function chkWhite_onclick()
{
    ToggleRace(frm.chkWhite, trWhite, frm.lstWhite);
}

function chkAfricanAmerican_onclick()
{
    ToggleRace(frm.chkAfricanAmerican, trAfricanAmerican, frm.lstAfricanAmerican);
}

function chkAsian_onclick()
{
    ToggleRace(frm.chkAsian, trAsian, frm.lstAsian);
}

function chkHawaiian_onclick()
{
    ToggleRace(frm.chkHawaiian, trHawaiian, frm.lstHawaiian);
}

//function race_chkOther_onclick()
//{
//    ToggleCheckText(frm.race_chkOther, frm.race_txtPER408);
//}

function chkHispanic_onclick()
{
    var b;
    b = frm.chkHispanic.checked;
    if(b == true)
    {
        trHispanic.className = "visible";
    }
    else
    {
        frm.txtHispanicDescription.value = "";
        divHispanic.className = "none";
        frm.lstHispanic.selectedIndex = -1;
        trHispanic.className = "none";
    }
}

function lstHispanic_onchange()
{
    var varOther = document.all.optHispanicOther;
    var varSelected = varOther.selected;
    if(varSelected == true)
    {
        divHispanic.className = "visible";
        frm.txtHispanicDescription.value = "";
        frm.txtHispanicDescription.focus();
    }
    else
    {
        frm.txtHispanicDescription.value = "";
        divHispanic.className = "none";
    }
}

function address_cmdAdd_onclick()
{
    var x = 0;
    var varMSG = "";
    HideTopError();
    HideError(address_lstLOC121_error);
    HideError(address_txtLOC110_error);
    HideError(address_txtLOC106_error);
    HideError(address_txtLOC123_error);
    HideError(address_txtLOC114_error);
    x += CheckSelectedGeneric(frm.address_lstLOC121, address_lstLOC121_error, "an Address Use");
    x += CheckSelectedGeneric(frm.address_lstLOC103, address_lstLOC121_error, "an Address Type");
    x += CheckDates
    (
        frm.address_txtLOC110.value,
        frm.address_txtLOC120.value,
        "Valid From",
        "Valid To",
        address_txtLOC110_error
    );
    x += CheckNumericGeneric(frm.address_txtLOC106.value, false, address_txtLOC106_error, "Duration");
    x += CheckLenGeneric(frm.address_txtLOC123.value, 80, address_txtLOC123_error, "Best Time To Contact");
    x += CheckLenGeneric(frm.address_txtLOC114.value, 2000, address_txtLOC114_error, "Address Comments");
    if(x > 0)
    {
        return false;
    }
    return true;
}

function phone_cmdAdd_onclick()
{
    var x = 0;
    HideTopError();
    HideError(phone_lstLOC121_error);
    HideError(phone_txtLOC110_error);
    HideError(phone_txtLOC106_error);
    HideError(phone_txtLOC123_error);
    HideError(phone_txtLOC114_error);
    x += CheckSelectedGeneric(frm.phone_lstLOC121, phone_lstLOC121_error, "a Telephone Use");
    x += CheckSelectedGeneric(frm.phone_lstLOC103, phone_lstLOC121_error, "a Telephone Type");
    x += CheckDates
    (
        frm.phone_txtLOC110.value,
        frm.phone_txtLOC120.value,
        "Valid From",
        "Valid To",
        phone_txtLOC110_error
    );
    x += CheckNumericGeneric(frm.phone_txtLOC106.value, false, phone_txtLOC106_error, "Duration");
    x += CheckLenGeneric(frm.phone_txtLOC123.value, 80, phone_txtLOC123_error, "Best Time To Contact");
    x += CheckLenGeneric(frm.phone_txtLOC114.value, 2000, phone_txtLOC114_error, "Telephone Comments");
    if(x > 0)
    {
        return false;
    }
    return true;
}

function physical_cmdAttachment_onclick()
{
    window.alert("This button does nothing.\nHave a nice day.");
}

function find_onclick()
{
    window.alert("This button does nothing.\nHave a nice day.");
}

function rel_cmdAdd_onclick()
{
    var x = 0;
    HideTopError();
    HideError(rel_txtROL108_error);
    HideError(rel_txtROL106_error);
    x += CheckDates
    (
        frm.rel_txtROL108.value,
        frm.rel_txtROL109.value,
        "Valid From",
        "Valid To",
        rel_txtROL108_error
    );
    x += CheckNumericGeneric(frm.rel_txtROL106.value, false, rel_txtROL106_error, "Duration");
    if(x > 0)
    {
        return false;
    }
    return true;
}

function frm_submit()
{
    var x = 0;
    var varMSG;
    HideTopError();
    HideError(per_txtPER115_error);
    HideError(per_txtPER108_error);
    HideError(per_txtPER135_error);
    HideError(per_txtPER114_error);
    HideError(per_txtEID114_error);
    HideError(per_txtPER144_error);
    HideError(id_txtEID114_error);
    x += CheckLenGeneric(frm.per_txtPER115.value, 255, per_txtPER115_error, "Comments");
    x += CheckDate(frm.per_txtPER108.value, false, per_txtPER108_error, "Date of Birth");
    x += CheckNumericGeneric(frm.per_txtPER107.value, false, per_txtPER108_error, "Birth Order");
    x += CheckNumericGeneric(frm.per_txtPER135.value, false, per_txtPER135_error, "Age");
    var n = frm.per_txtPER135.value;
    if(isNumeric(n) == true)
    {
        if(n > 150)
        {
            varMSG = "Please enter a Current Age between 0 and 150 and try again.";
            ShowError(per_txtPER135_error, varMSG);
            x++;
        }
    }
    if(isEmpty(n) == false)
    {
        x += CheckSelectedGeneric(frm.per_lstPER105, per_txtPER135_error, "an Age Unit");
    }
    x += CheckDate(frm.per_txtPER136.value, false, per_txtPER135_error, "Age Reported Date");
    x += CheckDate(frm.per_txtPER114.value, false, per_txtPER114_error, "Deceased Date");
    x += CheckSSNGeneric(frm.per_txtEID114.value, false, per_txtEID114_error, "SSN");
    x += CheckNumericGeneric(frm.per_txtPER144.value, false, per_txtPER144_error, "Number of Adults in Residence");
    x += CheckNumericGeneric(frm.per_txtPER145.value, false, per_txtPER144_error, "Number of Children in Residence");
    if
    (
        (isOptionSelected(frm.id_lstEID117, "SSN") == true)
    &&  (isEmpty(frm.per_txtEID114.value) == false)
    )
    {
        varMSG = "You cannot enter SSN twice.";
        ShowError(id_txtEID114_error, varMSG);
        x++;
    }
    if(x > 0)
    {
        return false;
    }
    return DoSubmit("submit");
}
