import React, { useContext, useEffect, useState } from 'react';
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
    useFindAllUsersLazyQuery,
    useFindAllOutbreaksLazyQuery,
    FindAllOutbreaksQuery,
    Outbreak,
    useFindAllEthnicityValuesLazyQuery,
    FindAllEthnicityValuesQuery,
    Ethnicity,
    useFindAllRaceValuesLazyQuery,
    Race,
    FindAllRaceValuesQuery,
    IdentificationType,
    useFindAllPatientIdentificationTypesLazyQuery,
    FindAllPatientIdentificationTypesQuery,
    StateCode,
    useFindAllStateCodesLazyQuery,
    FindAllStateCodesQuery,
    StateCountyCodeValue,
    useFindAllCountryCodesLazyQuery,
    CountryCode,
    FindAllCountryCodesQuery,
    useFindAllAssigningAuthoritiesLazyQuery,
    FindAllAssigningAuthoritiesQuery,
    AssigningAuthor,
    useFindAllNameTypesLazyQuery,
    FindAllNameTypesQuery,
    NameType,
    useFindAllNamePrefixesLazyQuery,
    NamePrefix,
    FindAllNamePrefixesQuery,
    useFindAllDegreesLazyQuery,
    FindAllDegreesQuery,
    Degree,
    AddressType,
    AddressUse,
    FindAllAddressTypesQuery,
    useFindAllAddressTypesLazyQuery,
    useFindAllAddressUsesLazyQuery,
    FindAllAddressUsesQuery,
    PhoneAndEmailUse,
    PhoneAndEmailType,
    useFindAllPhoneAndEmailTypeLazyQuery,
    useFindAllPhoneAndEmailUseLazyQuery,
    FindAllPhoneAndEmailTypeQuery,
    FindAllPhoneAndEmailUseQuery,
    useFindAllIdentificationTypesLazyQuery,
    FindAllIdentificationTypesQuery,
    IdentificationTypes
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
    states: StateCode[];
    counties: StateCountyCodeValue[];
    countries: CountryCode[] | null;
    authorities: AssigningAuthor[];
    nameTypes?: NameType[];
    prefixes?: NamePrefix[];
    degree?: Degree[];
    addressType?: AddressType[];
    addressUse?: AddressUse[];
    phoneType?: PhoneAndEmailType[];
    phoneUse?: PhoneAndEmailUse[];
    identificationType?: IdentificationTypes[];
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
    states: [],
    counties: [],
    countries: [],
    authorities: [],
    nameTypes: [],
    prefixes: [],
    degree: [],
    addressType: [],
    addressUse: [],
    phoneType: [],
    phoneUse: [],
    identificationType: []
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
    const [getStates] = useFindAllStateCodesLazyQuery({ onCompleted: setStates });
    const [getAllState] = useFindAllStateCodesLazyQuery({ onCompleted: setAllState });
    const [getCountries1] = useFindAllCountryCodesLazyQuery({ onCompleted: setCountry1 });
    const [getCountries2] = useFindAllCountryCodesLazyQuery({ onCompleted: setCountry1 });
    const [getCountries3] = useFindAllCountryCodesLazyQuery({ onCompleted: setCountry1 });
    const [getCountries4] = useFindAllCountryCodesLazyQuery({ onCompleted: setCountry1 });
    const [getCountries5] = useFindAllCountryCodesLazyQuery({ onCompleted: setCountry1 });
    const [getAuthorities] = useFindAllAssigningAuthoritiesLazyQuery({ onCompleted: setAuthorities });

    const [getNameTypes] = useFindAllNameTypesLazyQuery({ onCompleted: setNameTypes });
    const [getPrefix] = useFindAllNamePrefixesLazyQuery({ onCompleted: setPrefix });
    const [getDegree] = useFindAllDegreesLazyQuery({ onCompleted: setDegree });

    const [getAddressType] = useFindAllAddressTypesLazyQuery({ onCompleted: setAddressType });
    const [getAddressUse] = useFindAllAddressUsesLazyQuery({ onCompleted: setAddressUse });

    const [getPhoneEmailType] = useFindAllPhoneAndEmailTypeLazyQuery({ onCompleted: setPhoneEmailType });
    const [getPhoneEmailUse] = useFindAllPhoneAndEmailUseLazyQuery({ onCompleted: setPhoneEmailUse });

    const [getIdentificationType] = useFindAllIdentificationTypesLazyQuery({ onCompleted: setIdentificationType });
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
            getAllState({ variables: { page: { pageNumber: 1, pageSize: 50 } } });
            getCountries1({ variables: { page: { pageNumber: 0, pageSize: 50 } } });
            getCountries2({ variables: { page: { pageNumber: 1, pageSize: 50 } } });
            getCountries3({ variables: { page: { pageNumber: 2, pageSize: 50 } } });
            getCountries4({ variables: { page: { pageNumber: 3, pageSize: 50 } } });
            getCountries5({ variables: { page: { pageNumber: 4, pageSize: 50 } } });
            getAuthorities({ variables: { page: { pageNumber: 0, pageSize: 50 } } });
            getNameTypes();
            getPrefix();
            getDegree({ variables: { page: { pageNumber: 0, pageSize: 50 } } });
            getAddressType({ variables: { page: { pageNumber: 0, pageSize: 50 } } });
            getAddressUse();
            getPhoneEmailType();
            getPhoneEmailUse();
            getIdentificationType();
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

    const [stateList, setStateList] = useState<FindAllStateCodesQuery['findAllStateCodes']>();
    const [stateListAll, setStateListAll] = useState<FindAllStateCodesQuery['findAllStateCodes']>();

    const [countryList1, setCountryList1] = useState<FindAllCountryCodesQuery['findAllCountryCodes']>([]);

    const getAllStates = (results: FindAllStateCodesQuery) => {
        if (results.findAllStateCodes) {
            const states: StateCode[] = [];
            results.findAllStateCodes.forEach((i) => i && states.push(i));
            states.sort((a, b) => {
                if (a?.codeDescTxt && b?.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            return states;
        }
    };

    const getAllCountries = (results: FindAllCountryCodesQuery): CountryCode[] => {
        if (results.findAllCountryCodes) {
            const countries: CountryCode[] = [];
            results.findAllCountryCodes.forEach((i) => i && countries.push(i));
            countries.sort((a, b) => {
                if (a?.codeDescTxt && b?.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            return countries;
        } else {
            return [];
        }
    };

    function setCountry1(results: FindAllCountryCodesQuery): void {
        setCountryList1([...countryList1, ...getAllCountries(results)]);
    }

    function setStates(results: FindAllStateCodesQuery): void {
        setStateList(getAllStates(results));
    }

    function setAllState(results: FindAllStateCodesQuery): void {
        setStateListAll(getAllStates(results));
    }

    useEffect(() => {
        if (stateList && stateListAll) {
            const states = [...stateList, ...stateListAll];
            states.sort((a, b) => {
                if (a?.codeDescTxt && b?.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, states });
        }
    }, [stateList, stateListAll]);

    useEffect(() => {
        if (countryList1) {
            countryList1.sort((a, b) => {
                if (a?.codeDescTxt && b?.codeDescTxt) {
                    return a.codeDescTxt.localeCompare(b.codeDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, countries: countryList1 as CountryCode[] });
        }
    }, [countryList1]);

    function setAuthorities(results: FindAllAssigningAuthoritiesQuery): void {
        if (results.findAllAssigningAuthorities) {
            const authorities: AssigningAuthor[] = [];
            results.findAllAssigningAuthorities.content.forEach((i) => i && authorities.push(i));
            authorities.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, authorities });
        }
    }

    function setNameTypes(results: FindAllNameTypesQuery): void {
        if (results.findAllNameTypes) {
            const nameTypes: NameType[] = [];
            results.findAllNameTypes.content.forEach((i) => i && nameTypes.push(i));
            nameTypes.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, nameTypes });
        }
    }

    function setPrefix(results: FindAllNamePrefixesQuery): void {
        if (results.findAllNamePrefixes) {
            const prefixes: NamePrefix[] = [];
            results.findAllNamePrefixes.content.forEach((i) => i && prefixes.push(i));
            prefixes.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, prefixes });
        }
    }

    function setDegree(results: FindAllDegreesQuery): void {
        if (results.findAllDegrees) {
            const degree: Degree[] = [];
            results.findAllDegrees.content.forEach((i) => i && degree.push(i));
            degree.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, degree });
        }
    }

    function setAddressType(results: FindAllAddressTypesQuery): void {
        if (results.findAllAddressTypes) {
            const addressType: AddressType[] = [];
            results.findAllAddressTypes.content.forEach((i) => i && addressType.push(i));
            addressType.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, addressType });
        }
    }

    function setAddressUse(results: FindAllAddressUsesQuery): void {
        if (results.findAllAddressUses) {
            const addressUse: AddressUse[] = [];
            results.findAllAddressUses.content.forEach((i) => i && addressUse.push(i));
            addressUse.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, addressUse });
        }
    }

    function setPhoneEmailType(results: FindAllPhoneAndEmailTypeQuery): void {
        if (results.findAllPhoneAndEmailType) {
            const phoneType: PhoneAndEmailType[] = [];
            results.findAllPhoneAndEmailType.content.forEach((i) => i && phoneType.push(i));
            phoneType.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, phoneType });
        }
    }

    function setPhoneEmailUse(results: FindAllPhoneAndEmailUseQuery): void {
        if (results.findAllPhoneAndEmailUse) {
            const phoneUse: PhoneAndEmailUse[] = [];
            results.findAllPhoneAndEmailUse.content.forEach((i) => i && phoneUse.push(i));
            phoneUse.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, phoneUse });
        }
    }

    function setIdentificationType(results: FindAllIdentificationTypesQuery): void {
        if (results.findAllIdentificationTypes) {
            const identificationType: IdentificationTypes[] = [];
            results.findAllIdentificationTypes.content.forEach((i) => i && identificationType.push(i));
            identificationType.sort((a, b) => {
                if (a?.codeShortDescTxt && b?.codeShortDescTxt) {
                    return a.codeShortDescTxt.localeCompare(b.codeShortDescTxt);
                }
                return 0;
            });
            setSearchCriteria({ ...searchCriteria, identificationType });
        }
    }

    return <SearchCriteriaContext.Provider value={{ searchCriteria }}>{props.children}</SearchCriteriaContext.Provider>;
};
