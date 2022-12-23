import React, { useEffect } from 'react';
import {
    ConditionCode,
    FindAllConditionCodesQuery,
    FindAllJurisdictionsQuery,
    FindAllProgramAreasQuery,
    FindAllUsersQuery,
    Jurisdiction,
    ProgramAreaCode,
    User,
    useFindAllConditionCodesLazyQuery,
    useFindAllJurisdictionsLazyQuery,
    useFindAllProgramAreasLazyQuery,
    useFindAllUsersLazyQuery
} from '../generated/graphql/schema';

interface SearchCriteria {
    programAreas: ProgramAreaCode[];
    conditions: ConditionCode[];
    jurisdictions: Jurisdiction[];
    userResults: User[];
}

const initialState: SearchCriteria = {
    programAreas: [],
    conditions: [],
    jurisdictions: [],
    userResults: []
};

export const SearchCriteriaContext = React.createContext<{
    searchCriteria: SearchCriteria;
}>({
    searchCriteria: initialState
});

export const SearchCriteriaProvider = (props: any) => {
    const [searchCriteria, setSearchCriteria] = React.useState({ ...initialState });
    const [getProgramAreas] = useFindAllProgramAreasLazyQuery({ onCompleted: setProgramAreas });
    const [getConditions] = useFindAllConditionCodesLazyQuery({ onCompleted: setConditions });
    const [getJurisdictions] = useFindAllJurisdictionsLazyQuery({ onCompleted: setJurisdictions });
    const [getAllUsers] = useFindAllUsersLazyQuery({ onCompleted: setAllUSers });

    // on init, load search data from API
    useEffect(() => {
        getProgramAreas();
        getConditions();
        getJurisdictions();
        getAllUsers();
    }, []);

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
            console.log(userResults[0]);
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

    return <SearchCriteriaContext.Provider value={{ searchCriteria }}>{props.children}</SearchCriteriaContext.Provider>;
};
