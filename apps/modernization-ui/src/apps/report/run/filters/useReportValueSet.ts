import { ReportColumn } from "generated";
import { useCallback } from "react";
import { Field, OptionList } from "react-querybuilder";

const useReportValueSet = (columns: ReportColumn[]) => {
    const { }

    const getValues = useCallback((field: string, operator: string, misc: { fieldData: Field }): OptionList => {
        const column = columns.find(({name}) => name === field);
        if (!column) return [];

        const {codeDescCd, codesetNm, id} = column;

        const cacheId = `report.valueset.${id}`
        
        let endpoint = ''
        if (codeDescCd?.toLowerCase() === 'h') {
            endpoint = `/report/distinct/${id}`
        } else if (codesetNm?.includes('.')) {
            const [codeset, valueset] = codesetNm.split('.')
            if (codeset.toLowerCase() === "code_value_general") {
                endpoint = `/concepts/${valueset}`
            } else if (codeset.toLowerCase() === "code_value_clinical") {
                // TODO 
                endpoint = ''
            } else {
                console.error(`unknown code set ${codeset}`)
                return []
            }
        }
        
        console.log({field, operator, misc, columns})
        return []
    }, [columns])

    return getValues
}

export { useReportValueSet }

// if(codeOrDesc != null && codeOrDesc.equalsIgnoreCase("h")) {
    		
//     		map = fetchHardCodedValues(columnUid, reportVO, session);
    		
//     	} else {
    		
//         	if(codesetNm.indexOf(".") == -1) {
        		
//         		if(codesetNm.trim().equalsIgnoreCase("PHC_TYPE") || codesetNm.trim().equalsIgnoreCase("Condition_code")) {
        			
//         			map = cachedDropDownValues.getReportBasicPageConditionMap();
        			
//         		} else if(codesetNm.trim().equalsIgnoreCase("State_code")) {
        			
//         			map = cachedDropDownValues.getStateCodes1("STATE_CCD");
        			
//         		} else if(codesetNm.trim().equalsIgnoreCase("COUNTY_CCD") || codesetNm.trim().equalsIgnoreCase("State_county_code_value")) {
        			
//         			String state = PropertyUtil.getInstance().getNBS_STATE_CODE();
//         			map = cachedDropDownValues.getCountyCodesAsTreeMap(state);
        		
//         		} else if(codesetNm.trim().equalsIgnoreCase("S_JURDIC_C") || codesetNm.trim().equalsIgnoreCase("JURISDICTION_CODE")) {//NBSCentral defect #12524
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getJurisdictionCodedValues());
        			
//         		} else if(codesetNm.trim().equalsIgnoreCase("Program_area_code") || codesetNm.trim().equalsIgnoreCase("S_PROGRA_C")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getProgramAreaCodedValues());
        			
//         		} else if(codesetNm.trim().equalsIgnoreCase("NAICS_Industry_code")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getNAICSGetIndustryCodeAsTreeMap());

//         		} else if(codesetNm.trim().equalsIgnoreCase("Language_code")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getLanguageCodeAsTreeMap());

//         		} else if(codesetNm.trim().equalsIgnoreCase("country_code")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getCountryCodesAsTreeMap());
//         		}
        		
//         		else if(codesetNm.trim().equalsIgnoreCase("PLACE_LIST")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getPlacesWithQEC());
//         		}
// 				else if(codesetNm.trim().equalsIgnoreCase("Race_Code")) {
        			
//         			map = cachedDropDownValues.reverseMap(cachedDropDownValues.getRaceCodes());
//         		}
        		
//         	} else {
//         		String srtTable = codesetNm.substring(0,codesetNm.indexOf("."));        		       		
//         		codesetNm = codesetNm.substring(codesetNm.indexOf(".")+1);
        		
//         		if(srtTable.equalsIgnoreCase("CODE_VALUE_GENERAL"))
//         			map =cachedDropDownValues.getCodedValuesAsTreeMap(codesetNm);
        		
//         		else if(srtTable.equalsIgnoreCase("CODE_VALUE_CLINICAL"))
//         			map = cachedDropDownValues.getSAICDefinedCodedValuesAsTreeMap(codesetNm);        		
//         	}
    		
//     	}
