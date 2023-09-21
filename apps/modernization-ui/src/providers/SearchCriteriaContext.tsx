import { StateCodedValue } from 'location';
import React, { useContext, useEffect } from 'react';
import {
    ConditionCode,
    Ethnicity,
    FindAllConditionCodesQuery,
    FindAllEthnicityValuesQuery,
    FindAllJurisdictionsQuery,
    FindAllOutbreaksQuery,
    FindAllPatientIdentificationTypesQuery,
    FindAllProgramAreasQuery,
    FindAllRaceValuesQuery,
    FindAllUsersQuery,
    IdentificationType,
    Jurisdiction,
    Outbreak,
    ProgramAreaCode,
    Race,
    StatesQuery,
    User,
    useFindAllConditionCodesLazyQuery,
    useFindAllEthnicityValuesLazyQuery,
    useFindAllJurisdictionsLazyQuery,
    useFindAllOutbreaksLazyQuery,
    useFindAllPatientIdentificationTypesLazyQuery,
    useFindAllProgramAreasLazyQuery,
    useFindAllRaceValuesLazyQuery,
    useFindAllUsersLazyQuery,
    useStatesLazyQuery
} from '../generated/graphql/schema';
import { UserContext } from './UserContext';

export interface SearchCriteria {
    programAreas: ProgramAreaCode[];
    conditions: ConditionCode[];
    jurisdictions: Jurisdiction[];
    userResults: User[];
    outbreaks: Outbreak[];
    ethnicities: Ethnicity[];
    races: Race[];
    identificationTypes: IdentificationType[];
    states: StateCodedValue[];
}

const initialState: SearchCriteria = {
    programAreas: [],
    conditions: [],
    jurisdictions: [],
    userResults: [],
    outbreaks: [],
    ethnicities: [],
    races: [],
    identificationTypes: [],
    states: []
};

export const SearchCriteriaContext = React.createContext<{
    searchCriteria: SearchCriteria;
}>({
    searchCriteria: initialState
});

export const SearchCriteriaProvider = (props: any) => {
    const { state } = useContext(UserContext);
    const [searchCriteria, setSearchCriteria] = React.useState({ ...initialState });
    const [getProgramAreas] = useFindAllProgramAreasLazyQuery({ onCompleted: setProgramAreas });
    const [getConditions] = useFindAllConditionCodesLazyQuery({ onCompleted: setConditions });
    const [getJurisdictions] = useFindAllJurisdictionsLazyQuery({ onCompleted: setJurisdictions });
    const [getOutbreaks] = useFindAllOutbreaksLazyQuery({ onCompleted: setOutbreaks });
    const [getEthnicities] = useFindAllEthnicityValuesLazyQuery({ onCompleted: setEthnicities });
    const [getIdentificationTypes] = useFindAllPatientIdentificationTypesLazyQuery({
        onCompleted: setIdentificationTypes
    });
    const [getRaces] = useFindAllRaceValuesLazyQuery({ onCompleted: setRaces });
    const [getAllUsers] = useFindAllUsersLazyQuery({ onCompleted: setAllUSers });
    const [getStates] = useStatesLazyQuery({ onCompleted: setStates });

    // on init, load search data from API
    useEffect(() => {
        if (state.isLoggedIn) {
            getProgramAreas();
            getConditions();
            getJurisdictions();
            getAllUsers();
            getOutbreaks();
            getEthnicities();
            getRaces();
            getIdentificationTypes();
            getStates();
        }
    }, [state.isLoggedIn]);

    function setOutbreaks(results: FindAllOutbreaksQuery): void {
        if (results.findAllOutbreaks) {
            const outbreaks: Outbreak[] = [];
            results.findAllOutbreaks.content.forEach((o) => o && outbreaks.push(o));
            outbreaks.sort((a, b) => {
                if (a.codeShortDescTxt && b.codeShortDescTxt) {
                    return a.codeShortDescTxt?.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, outbreaks });
        }
    }

    function setEthnicities(results: FindAllEthnicityValuesQuery): void {
        if (results.findAllEthnicityValues) {
            const ethnicities: Ethnicity[] = [];
            results.findAllEthnicityValues.content.forEach((e) => e && ethnicities.push(e));
            ethnicities.sort((a, b) => {
                if (a.codeDescTxt && b.codeDescTxt) {
                    return a.codeDescTxt?.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, ethnicities });
        }
    }
    function setIdentificationTypes(results: FindAllPatientIdentificationTypesQuery): void {
        if (results.findAllPatientIdentificationTypes) {
            const identificationTypes: IdentificationType[] = [];
            results.findAllPatientIdentificationTypes.content.forEach((id) => id && identificationTypes.push(id));
            identificationTypes.sort((a, b) => {
                if (a.codeDescTxt && b.codeDescTxt) {
                    return a.codeDescTxt?.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, identificationTypes });
        }
    }

    function setRaces(results: FindAllRaceValuesQuery): void {
        if (results.findAllRaceValues) {
            const races: Race[] = [];
            results.findAllRaceValues.content.forEach((r) => r && races.push(r));
            races.sort((a, b) => {
                if (a.codeDescTxt && b.codeDescTxt) {
                    return a.codeDescTxt?.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, races });
        }
    }

    function setProgramAreas(results: FindAllProgramAreasQuery): void {
        if (results.findAllProgramAreas) {
            const programAreas: ProgramAreaCode[] = [];
            results.findAllProgramAreas.forEach((pa) => pa && programAreas.push(pa));
            programAreas.sort((a, b) => {
                if (a.progAreaDescTxt && b.progAreaDescTxt) {
                    return a.progAreaDescTxt?.localeCompare(b.progAreaDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, programAreas });
        }
    }

    function setAllUSers(results: FindAllUsersQuery): void {
        if (results.findAllUsers) {
            const userResults: User[] = [];
            results.findAllUsers.content.forEach((pa) => pa && userResults.push(pa));
            setSearchCriteria({ ...searchCriteria, userResults });
        }
    }

    function setConditions(results: FindAllConditionCodesQuery): void {
        if (results.findAllConditionCodes) {
            const conditions: ConditionCode[] = [];
            results.findAllConditionCodes.forEach((cc) => cc && conditions.push(cc));
            conditions.sort((a, b) => {
                if (a.conditionDescTxt && b.conditionDescTxt) {
                    return a.conditionDescTxt.localeCompare(b.conditionDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, conditions });
        }
    }

    function setJurisdictions(results: FindAllJurisdictionsQuery): void {
        if (results.findAllJurisdictions) {
            const jurisdictions: Jurisdiction[] = [];
            results.findAllJurisdictions.forEach((j) => j && jurisdictions.push(j));
            jurisdictions.sort((a, b) => {
                if (a.codeDescTxt && b.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, jurisdictions });
        }
    }

    function setStates(results: StatesQuery): void {
        setSearchCriteria((previous) => ({ ...previous, states: results.states }));
    }

    return <SearchCriteriaContext.Provider value={{ searchCriteria }}>{props.children}</SearchCriteriaContext.Provider>;
};
