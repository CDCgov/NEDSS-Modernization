import { StateCodedValue } from 'location';
import React, { useContext, useEffect } from 'react';
import { StatesQuery, useStatesLazyQuery } from '../generated/graphql/schema';
import { UserContext } from './UserContext';

export interface SearchCriteria {
    states: StateCodedValue[];
}

const initialState: SearchCriteria = {
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

    function setStates(results: StatesQuery): void {
        setSearchCriteria((previous) => ({ ...previous, states: results.states }));
    }

    return <SearchCriteriaContext.Provider value={{ searchCriteria }}>{props.children}</SearchCriteriaContext.Provider>;
};
