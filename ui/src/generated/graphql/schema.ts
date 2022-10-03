/* eslint-disable no-unused-vars */
/* eslint-disable valid-jsdoc */
import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
const defaultOptions = {} as const;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
    ID: string;
    String: string;
    Boolean: boolean;
    Int: number;
    Float: number;
    Date: any;
};

export enum Deceased {
    N = 'N',
    Unk = 'UNK',
    Y = 'Y'
}

export enum Operator {
    After = 'AFTER',
    Before = 'BEFORE',
    Equal = 'EQUAL'
}

export type Page = {
    pageNumber?: number;
    pageSize?: number;
};

export type Person = {
    __typename?: 'Person';
    addReasonCd?: Maybe<Scalars['String']>;
    addTime?: Maybe<Scalars['Date']>;
    addUserId?: Maybe<Scalars['ID']>;
    additionalGenderCd?: Maybe<Scalars['String']>;
    administrativeGenderCd?: Maybe<Scalars['String']>;
    adultsInHouseNbr?: Maybe<Scalars['Int']>;
    ageCalc?: Maybe<Scalars['Int']>;
    ageCalcTime?: Maybe<Scalars['Date']>;
    ageCalcUnitCd?: Maybe<Scalars['String']>;
    ageCategoryCd?: Maybe<Scalars['String']>;
    ageReported?: Maybe<Scalars['String']>;
    ageReportedTime?: Maybe<Scalars['Date']>;
    ageReportedUnitCd?: Maybe<Scalars['String']>;
    asOfDateAdmin?: Maybe<Scalars['Date']>;
    asOfDateEthnicity?: Maybe<Scalars['Date']>;
    asOfDateGeneral?: Maybe<Scalars['Date']>;
    asOfDateMorbidity?: Maybe<Scalars['Date']>;
    asOfDateSex?: Maybe<Scalars['Date']>;
    birthCityCd?: Maybe<Scalars['String']>;
    birthCityDescTxt?: Maybe<Scalars['String']>;
    birthCntryCd?: Maybe<Scalars['String']>;
    birthGenderCd?: Maybe<Scalars['String']>;
    birthOrderNbr?: Maybe<Scalars['Int']>;
    birthStateCd?: Maybe<Scalars['String']>;
    birthTime?: Maybe<Scalars['Date']>;
    birthTimeCalc?: Maybe<Scalars['Date']>;
    cd?: Maybe<Scalars['String']>;
    cdDescTxt?: Maybe<Scalars['String']>;
    cellPhoneNbr?: Maybe<Scalars['String']>;
    childrenInHouseNbr?: Maybe<Scalars['Int']>;
    currSexCd?: Maybe<Scalars['String']>;
    deceasedIndCd?: Maybe<Scalars['String']>;
    deceasedTime?: Maybe<Scalars['Date']>;
    dedupMatchInd?: Maybe<Scalars['String']>;
    description?: Maybe<Scalars['String']>;
    dlNum?: Maybe<Scalars['String']>;
    dlStateCd?: Maybe<Scalars['String']>;
    educationLevelCd?: Maybe<Scalars['String']>;
    educationLevelDescTxt?: Maybe<Scalars['String']>;
    edxInd?: Maybe<Scalars['String']>;
    eharsId?: Maybe<Scalars['String']>;
    electronicInd?: Maybe<Scalars['String']>;
    ethnicGroupDescTxt?: Maybe<Scalars['String']>;
    ethnicGroupInd?: Maybe<Scalars['String']>;
    ethnicGroupSeqNbr?: Maybe<Scalars['Int']>;
    ethnicUnkReasonCd?: Maybe<Scalars['String']>;
    ethnicityGroupCd?: Maybe<Scalars['String']>;
    firstNm?: Maybe<Scalars['String']>;
    groupNbr?: Maybe<Scalars['Int']>;
    groupTime?: Maybe<Scalars['Date']>;
    hmCityCd?: Maybe<Scalars['String']>;
    hmCityDescTxt?: Maybe<Scalars['String']>;
    hmCntryCd?: Maybe<Scalars['String']>;
    hmCntyCd?: Maybe<Scalars['String']>;
    hmEmailAddr?: Maybe<Scalars['String']>;
    hmPhoneCntryCd?: Maybe<Scalars['String']>;
    hmPhoneNbr?: Maybe<Scalars['String']>;
    hmStateCd?: Maybe<Scalars['String']>;
    hmStreetAddr1?: Maybe<Scalars['String']>;
    hmStreetAddr2?: Maybe<Scalars['String']>;
    hmZipCd?: Maybe<Scalars['String']>;
    id?: Maybe<Scalars['ID']>;
    lastChgReasonCd?: Maybe<Scalars['String']>;
    lastChgTime?: Maybe<Scalars['Date']>;
    lastChgUserId?: Maybe<Scalars['ID']>;
    lastNm?: Maybe<Scalars['String']>;
    localId?: Maybe<Scalars['String']>;
    maritalStatusCd?: Maybe<Scalars['String']>;
    maritalStatusDescTxt?: Maybe<Scalars['String']>;
    medicaidNum?: Maybe<Scalars['String']>;
    middleNm?: Maybe<Scalars['String']>;
    mothersMaidenNm?: Maybe<Scalars['String']>;
    multipleBirthInd?: Maybe<Scalars['String']>;
    nmPrefix?: Maybe<Scalars['String']>;
    nmSuffix?: Maybe<Scalars['String']>;
    occupationCd?: Maybe<Scalars['String']>;
    personParentUid?: Maybe<Scalars['ID']>;
    preferredGenderCd?: Maybe<Scalars['String']>;
    preferredNm?: Maybe<Scalars['String']>;
    primLangCd?: Maybe<Scalars['String']>;
    primLangDescTxt?: Maybe<Scalars['String']>;
    raceCategoryCd?: Maybe<Scalars['String']>;
    raceCd?: Maybe<Scalars['String']>;
    raceDescTxt?: Maybe<Scalars['String']>;
    raceSeqNbr?: Maybe<Scalars['Int']>;
    recordStatusCd?: Maybe<Scalars['String']>;
    recordStatusTime?: Maybe<Scalars['Date']>;
    sexUnkReasonCd?: Maybe<Scalars['String']>;
    speaksEnglishCd?: Maybe<Scalars['String']>;
    ssn?: Maybe<Scalars['String']>;
    statusCd?: Maybe<Scalars['String']>;
    statusTime?: Maybe<Scalars['Date']>;
    survivedIndCd?: Maybe<Scalars['String']>;
    userAffiliationTxt?: Maybe<Scalars['String']>;
    versionCtrlNbr?: Maybe<Scalars['Int']>;
    wkCityCd?: Maybe<Scalars['String']>;
    wkCityDescTxt?: Maybe<Scalars['String']>;
    wkCntryCd?: Maybe<Scalars['String']>;
    wkCntyCd?: Maybe<Scalars['String']>;
    wkEmailAddr?: Maybe<Scalars['String']>;
    wkPhoneCntryCd?: Maybe<Scalars['String']>;
    wkPhoneNbr?: Maybe<Scalars['String']>;
    wkStateCd?: Maybe<Scalars['String']>;
    wkStreetAddr1?: Maybe<Scalars['String']>;
    wkStreetAddr2?: Maybe<Scalars['String']>;
    wkZipCd?: Maybe<Scalars['String']>;
};

export type PersonFilter = {
    DateOfBirth?: Date;
    DateOfBirthOperator?: Operator;
    address?: string;
    city?: string;
    country?: string;
    deceased?: Deceased;
    ethnicity?: string;
    firstName?: string;
    gender?: string;
    id?: Scalars['ID'];
    lastName?: string;
    mortalityStatus?: string;
    page?: Page;
    phoneNumber?: string;
    recordStatus?: RecordStatus;
    ssn?: string;
    state?: string;
    zip?: string;
};

export type Query = {
    __typename?: 'Query';
    findAllPatients?: Maybe<Array<Maybe<Person>>>;
    findPatientById?: Maybe<Person>;
    findPatientsByFilter?: Maybe<Array<Maybe<Person>>>;
};

export type QueryFindAllPatientsArgs = {
    page?: InputMaybe<Page>;
};

export type QueryFindPatientByIdArgs = {
    id: Scalars['ID'];
};

export type QueryFindPatientsByFilterArgs = {
    filter: PersonFilter;
};

export enum RecordStatus {
    Active = 'ACTIVE',
    LogDel = 'LOG_DEL'
}

export type FindAllPatientsQueryVariables = {
    page?: Page;
};

export type FindAllPatientsQuery = {
    __typename?: 'Query';
    findAllPatients?: Array<{
        __typename?: 'Person';
        id?: string | null;
        addReasonCd?: string | null;
        addTime?: any | null;
        addUserId?: string | null;
        administrativeGenderCd?: string | null;
        ageCalc?: number | null;
        ageCalcTime?: any | null;
        ageCalcUnitCd?: string | null;
        ageCategoryCd?: string | null;
        ageReported?: string | null;
        ageReportedTime?: any | null;
        ageReportedUnitCd?: string | null;
        birthGenderCd?: string | null;
        birthOrderNbr?: number | null;
        birthTime?: any | null;
        birthTimeCalc?: any | null;
        cd?: string | null;
        cdDescTxt?: string | null;
        currSexCd?: string | null;
        deceasedIndCd?: string | null;
        deceasedTime?: any | null;
        description?: string | null;
        educationLevelCd?: string | null;
        educationLevelDescTxt?: string | null;
        ethnicGroupInd?: string | null;
        lastChgReasonCd?: string | null;
        lastChgTime?: any | null;
        lastChgUserId?: string | null;
        localId?: string | null;
        maritalStatusCd?: string | null;
        maritalStatusDescTxt?: string | null;
        mothersMaidenNm?: string | null;
        multipleBirthInd?: string | null;
        occupationCd?: string | null;
        preferredGenderCd?: string | null;
        primLangCd?: string | null;
        primLangDescTxt?: string | null;
        recordStatusCd?: string | null;
        recordStatusTime?: any | null;
        statusCd?: string | null;
        statusTime?: any | null;
        survivedIndCd?: string | null;
        userAffiliationTxt?: string | null;
        firstNm?: string | null;
        lastNm?: string | null;
        middleNm?: string | null;
        nmPrefix?: string | null;
        nmSuffix?: string | null;
        preferredNm?: string | null;
        hmStreetAddr1?: string | null;
        hmStreetAddr2?: string | null;
        hmCityCd?: string | null;
        hmCityDescTxt?: string | null;
        hmStateCd?: string | null;
        hmZipCd?: string | null;
        hmCntyCd?: string | null;
        hmCntryCd?: string | null;
        hmPhoneNbr?: string | null;
        hmPhoneCntryCd?: string | null;
        hmEmailAddr?: string | null;
        cellPhoneNbr?: string | null;
        wkStreetAddr1?: string | null;
        wkStreetAddr2?: string | null;
        wkCityCd?: string | null;
        wkCityDescTxt?: string | null;
        wkStateCd?: string | null;
        wkZipCd?: string | null;
        wkCntyCd?: string | null;
        wkCntryCd?: string | null;
        wkPhoneNbr?: string | null;
        wkPhoneCntryCd?: string | null;
        wkEmailAddr?: string | null;
        ssn?: string | null;
        medicaidNum?: string | null;
        dlNum?: string | null;
        dlStateCd?: string | null;
        raceCd?: string | null;
        raceSeqNbr?: number | null;
        raceCategoryCd?: string | null;
        ethnicityGroupCd?: string | null;
        ethnicGroupSeqNbr?: number | null;
        adultsInHouseNbr?: number | null;
        childrenInHouseNbr?: number | null;
        birthCityCd?: string | null;
        birthCityDescTxt?: string | null;
        birthCntryCd?: string | null;
        birthStateCd?: string | null;
        raceDescTxt?: string | null;
        ethnicGroupDescTxt?: string | null;
        versionCtrlNbr?: number | null;
        asOfDateAdmin?: any | null;
        asOfDateEthnicity?: any | null;
        asOfDateGeneral?: any | null;
        asOfDateMorbidity?: any | null;
        asOfDateSex?: any | null;
        electronicInd?: string | null;
        personParentUid?: string | null;
        dedupMatchInd?: string | null;
        groupNbr?: number | null;
        groupTime?: any | null;
        edxInd?: string | null;
        speaksEnglishCd?: string | null;
        additionalGenderCd?: string | null;
        eharsId?: string | null;
        ethnicUnkReasonCd?: string | null;
        sexUnkReasonCd?: string | null;
    } | null> | null;
};

export type FindPatientByIdQueryVariables = Exact<{
    id: Scalars['ID'];
}>;

export type FindPatientByIdQuery = {
    __typename?: 'Query';
    findPatientById?: {
        __typename?: 'Person';
        id?: string | null;
        addReasonCd?: string | null;
        addTime?: any | null;
        addUserId?: string | null;
        administrativeGenderCd?: string | null;
        ageCalc?: number | null;
        ageCalcTime?: any | null;
        ageCalcUnitCd?: string | null;
        ageCategoryCd?: string | null;
        ageReported?: string | null;
        ageReportedTime?: any | null;
        ageReportedUnitCd?: string | null;
        birthGenderCd?: string | null;
        birthOrderNbr?: number | null;
        birthTime?: any | null;
        birthTimeCalc?: any | null;
        cd?: string | null;
        cdDescTxt?: string | null;
        currSexCd?: string | null;
        deceasedIndCd?: string | null;
        deceasedTime?: any | null;
        description?: string | null;
        educationLevelCd?: string | null;
        educationLevelDescTxt?: string | null;
        ethnicGroupInd?: string | null;
        lastChgReasonCd?: string | null;
        lastChgTime?: any | null;
        lastChgUserId?: string | null;
        localId?: string | null;
        maritalStatusCd?: string | null;
        maritalStatusDescTxt?: string | null;
        mothersMaidenNm?: string | null;
        multipleBirthInd?: string | null;
        occupationCd?: string | null;
        preferredGenderCd?: string | null;
        primLangCd?: string | null;
        primLangDescTxt?: string | null;
        recordStatusCd?: string | null;
        recordStatusTime?: any | null;
        statusCd?: string | null;
        statusTime?: any | null;
        survivedIndCd?: string | null;
        userAffiliationTxt?: string | null;
        firstNm?: string | null;
        lastNm?: string | null;
        middleNm?: string | null;
        nmPrefix?: string | null;
        nmSuffix?: string | null;
        preferredNm?: string | null;
        hmStreetAddr1?: string | null;
        hmStreetAddr2?: string | null;
        hmCityCd?: string | null;
        hmCityDescTxt?: string | null;
        hmStateCd?: string | null;
        hmZipCd?: string | null;
        hmCntyCd?: string | null;
        hmCntryCd?: string | null;
        hmPhoneNbr?: string | null;
        hmPhoneCntryCd?: string | null;
        hmEmailAddr?: string | null;
        cellPhoneNbr?: string | null;
        wkStreetAddr1?: string | null;
        wkStreetAddr2?: string | null;
        wkCityCd?: string | null;
        wkCityDescTxt?: string | null;
        wkStateCd?: string | null;
        wkZipCd?: string | null;
        wkCntyCd?: string | null;
        wkCntryCd?: string | null;
        wkPhoneNbr?: string | null;
        wkPhoneCntryCd?: string | null;
        wkEmailAddr?: string | null;
        ssn?: string | null;
        medicaidNum?: string | null;
        dlNum?: string | null;
        dlStateCd?: string | null;
        raceCd?: string | null;
        raceSeqNbr?: number | null;
        raceCategoryCd?: string | null;
        ethnicityGroupCd?: string | null;
        ethnicGroupSeqNbr?: number | null;
        adultsInHouseNbr?: number | null;
        childrenInHouseNbr?: number | null;
        birthCityCd?: string | null;
        birthCityDescTxt?: string | null;
        birthCntryCd?: string | null;
        birthStateCd?: string | null;
        raceDescTxt?: string | null;
        ethnicGroupDescTxt?: string | null;
        versionCtrlNbr?: number | null;
        asOfDateAdmin?: any | null;
        asOfDateEthnicity?: any | null;
        asOfDateGeneral?: any | null;
        asOfDateMorbidity?: any | null;
        asOfDateSex?: any | null;
        electronicInd?: string | null;
        personParentUid?: string | null;
        dedupMatchInd?: string | null;
        groupNbr?: number | null;
        groupTime?: any | null;
        edxInd?: string | null;
        speaksEnglishCd?: string | null;
        additionalGenderCd?: string | null;
        eharsId?: string | null;
        ethnicUnkReasonCd?: string | null;
        sexUnkReasonCd?: string | null;
    } | null;
};

export type FindPatientsByFilterQueryVariables = {
    filter: PersonFilter;
};

export type FindPatientsByFilterQuery = {
    __typename?: 'Query';
    findPatientsByFilter?: Array<{
        __typename?: 'Person';
        id?: string | null;
        addReasonCd?: string | null;
        addTime?: any | null;
        addUserId?: string | null;
        administrativeGenderCd?: string | null;
        ageCalc?: number | null;
        ageCalcTime?: any | null;
        ageCalcUnitCd?: string | null;
        ageCategoryCd?: string | null;
        ageReported?: string | null;
        ageReportedTime?: any | null;
        ageReportedUnitCd?: string | null;
        birthGenderCd?: string | null;
        birthOrderNbr?: number | null;
        birthTime?: any | null;
        birthTimeCalc?: any | null;
        cd?: string | null;
        cdDescTxt?: string | null;
        currSexCd?: string | null;
        deceasedIndCd?: string | null;
        deceasedTime?: any | null;
        description?: string | null;
        educationLevelCd?: string | null;
        educationLevelDescTxt?: string | null;
        ethnicGroupInd?: string | null;
        lastChgReasonCd?: string | null;
        lastChgTime?: any | null;
        lastChgUserId?: string | null;
        localId?: string | null;
        maritalStatusCd?: string | null;
        maritalStatusDescTxt?: string | null;
        mothersMaidenNm?: string | null;
        multipleBirthInd?: string | null;
        occupationCd?: string | null;
        preferredGenderCd?: string | null;
        primLangCd?: string | null;
        primLangDescTxt?: string | null;
        recordStatusCd?: string | null;
        recordStatusTime?: any | null;
        statusCd?: string | null;
        statusTime?: any | null;
        survivedIndCd?: string | null;
        userAffiliationTxt?: string | null;
        firstNm?: string | null;
        lastNm?: string | null;
        middleNm?: string | null;
        nmPrefix?: string | null;
        nmSuffix?: string | null;
        preferredNm?: string | null;
        hmStreetAddr1?: string | null;
        hmStreetAddr2?: string | null;
        hmCityCd?: string | null;
        hmCityDescTxt?: string | null;
        hmStateCd?: string | null;
        hmZipCd?: string | null;
        hmCntyCd?: string | null;
        hmCntryCd?: string | null;
        hmPhoneNbr?: string | null;
        hmPhoneCntryCd?: string | null;
        hmEmailAddr?: string | null;
        cellPhoneNbr?: string | null;
        wkStreetAddr1?: string | null;
        wkStreetAddr2?: string | null;
        wkCityCd?: string | null;
        wkCityDescTxt?: string | null;
        wkStateCd?: string | null;
        wkZipCd?: string | null;
        wkCntyCd?: string | null;
        wkCntryCd?: string | null;
        wkPhoneNbr?: string | null;
        wkPhoneCntryCd?: string | null;
        wkEmailAddr?: string | null;
        ssn?: string | null;
        medicaidNum?: string | null;
        dlNum?: string | null;
        dlStateCd?: string | null;
        raceCd?: string | null;
        raceSeqNbr?: number | null;
        raceCategoryCd?: string | null;
        ethnicityGroupCd?: string | null;
        ethnicGroupSeqNbr?: number | null;
        adultsInHouseNbr?: number | null;
        childrenInHouseNbr?: number | null;
        birthCityCd?: string | null;
        birthCityDescTxt?: string | null;
        birthCntryCd?: string | null;
        birthStateCd?: string | null;
        raceDescTxt?: string | null;
        ethnicGroupDescTxt?: string | null;
        versionCtrlNbr?: number | null;
        asOfDateAdmin?: any | null;
        asOfDateEthnicity?: any | null;
        asOfDateGeneral?: any | null;
        asOfDateMorbidity?: any | null;
        asOfDateSex?: any | null;
        electronicInd?: string | null;
        personParentUid?: string | null;
        dedupMatchInd?: string | null;
        groupNbr?: number | null;
        groupTime?: any | null;
        edxInd?: string | null;
        speaksEnglishCd?: string | null;
        additionalGenderCd?: string | null;
        eharsId?: string | null;
        ethnicUnkReasonCd?: string | null;
        sexUnkReasonCd?: string | null;
        checked: boolean;
    } | null> | null;
};

export const FindAllPatientsDocument = gql`
    query findAllPatients($page: Page) {
        findAllPatients(page: $page) {
            id
            addReasonCd
            addTime
            addUserId
            administrativeGenderCd
            ageCalc
            ageCalcTime
            ageCalcUnitCd
            ageCategoryCd
            ageReported
            ageReportedTime
            ageReportedUnitCd
            birthGenderCd
            birthOrderNbr
            birthTime
            birthTimeCalc
            cd
            cdDescTxt
            currSexCd
            deceasedIndCd
            deceasedTime
            description
            educationLevelCd
            educationLevelDescTxt
            ethnicGroupInd
            lastChgReasonCd
            lastChgTime
            lastChgUserId
            localId
            maritalStatusCd
            maritalStatusDescTxt
            mothersMaidenNm
            multipleBirthInd
            occupationCd
            preferredGenderCd
            primLangCd
            primLangDescTxt
            recordStatusCd
            recordStatusTime
            statusCd
            statusTime
            survivedIndCd
            userAffiliationTxt
            firstNm
            lastNm
            middleNm
            nmPrefix
            nmSuffix
            preferredNm
            hmStreetAddr1
            hmStreetAddr2
            hmCityCd
            hmCityDescTxt
            hmStateCd
            hmZipCd
            hmCntyCd
            hmCntryCd
            hmPhoneNbr
            hmPhoneCntryCd
            hmEmailAddr
            cellPhoneNbr
            wkStreetAddr1
            wkStreetAddr2
            wkCityCd
            wkCityDescTxt
            wkStateCd
            wkZipCd
            wkCntyCd
            wkCntryCd
            wkPhoneNbr
            wkPhoneCntryCd
            wkEmailAddr
            ssn
            medicaidNum
            dlNum
            dlStateCd
            raceCd
            raceSeqNbr
            raceCategoryCd
            ethnicityGroupCd
            ethnicGroupSeqNbr
            adultsInHouseNbr
            childrenInHouseNbr
            birthCityCd
            birthCityDescTxt
            birthCntryCd
            birthStateCd
            raceDescTxt
            ethnicGroupDescTxt
            versionCtrlNbr
            asOfDateAdmin
            asOfDateEthnicity
            asOfDateGeneral
            asOfDateMorbidity
            asOfDateSex
            electronicInd
            personParentUid
            dedupMatchInd
            groupNbr
            groupTime
            edxInd
            speaksEnglishCd
            additionalGenderCd
            eharsId
            ethnicUnkReasonCd
            sexUnkReasonCd
        }
    }
`;

/**
 * __useFindAllPatientsQuery__
 *
 * To run a query within a React component, call `useFindAllPatientsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPatientsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPatientsQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPatientsQuery(
    baseOptions?: Apollo.QueryHookOptions<FindAllPatientsQuery, FindAllPatientsQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useQuery<FindAllPatientsQuery, FindAllPatientsQueryVariables>(FindAllPatientsDocument, options);
}
export function useFindAllPatientsLazyQuery(
    baseOptions?: Apollo.LazyQueryHookOptions<FindAllPatientsQuery, FindAllPatientsQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useLazyQuery<FindAllPatientsQuery, FindAllPatientsQueryVariables>(FindAllPatientsDocument, options);
}
export type FindAllPatientsQueryHookResult = ReturnType<typeof useFindAllPatientsQuery>;
export type FindAllPatientsLazyQueryHookResult = ReturnType<typeof useFindAllPatientsLazyQuery>;
export type FindAllPatientsQueryResult = Apollo.QueryResult<FindAllPatientsQuery, FindAllPatientsQueryVariables>;
export const FindPatientByIdDocument = gql`
    query findPatientById($id: ID!) {
        findPatientById(id: $id) {
            id
            addReasonCd
            addTime
            addUserId
            administrativeGenderCd
            ageCalc
            ageCalcTime
            ageCalcUnitCd
            ageCategoryCd
            ageReported
            ageReportedTime
            ageReportedUnitCd
            birthGenderCd
            birthOrderNbr
            birthTime
            birthTimeCalc
            cd
            cdDescTxt
            currSexCd
            deceasedIndCd
            deceasedTime
            description
            educationLevelCd
            educationLevelDescTxt
            ethnicGroupInd
            lastChgReasonCd
            lastChgTime
            lastChgUserId
            localId
            maritalStatusCd
            maritalStatusDescTxt
            mothersMaidenNm
            multipleBirthInd
            occupationCd
            preferredGenderCd
            primLangCd
            primLangDescTxt
            recordStatusCd
            recordStatusTime
            statusCd
            statusTime
            survivedIndCd
            userAffiliationTxt
            firstNm
            lastNm
            middleNm
            nmPrefix
            nmSuffix
            preferredNm
            hmStreetAddr1
            hmStreetAddr2
            hmCityCd
            hmCityDescTxt
            hmStateCd
            hmZipCd
            hmCntyCd
            hmCntryCd
            hmPhoneNbr
            hmPhoneCntryCd
            hmEmailAddr
            cellPhoneNbr
            wkStreetAddr1
            wkStreetAddr2
            wkCityCd
            wkCityDescTxt
            wkStateCd
            wkZipCd
            wkCntyCd
            wkCntryCd
            wkPhoneNbr
            wkPhoneCntryCd
            wkEmailAddr
            ssn
            medicaidNum
            dlNum
            dlStateCd
            raceCd
            raceSeqNbr
            raceCategoryCd
            ethnicityGroupCd
            ethnicGroupSeqNbr
            adultsInHouseNbr
            childrenInHouseNbr
            birthCityCd
            birthCityDescTxt
            birthCntryCd
            birthStateCd
            raceDescTxt
            ethnicGroupDescTxt
            versionCtrlNbr
            asOfDateAdmin
            asOfDateEthnicity
            asOfDateGeneral
            asOfDateMorbidity
            asOfDateSex
            electronicInd
            personParentUid
            dedupMatchInd
            groupNbr
            groupTime
            edxInd
            speaksEnglishCd
            additionalGenderCd
            eharsId
            ethnicUnkReasonCd
            sexUnkReasonCd
        }
    }
`;

/**
 * __useFindPatientByIdQuery__
 *
 * To run a query within a React component, call `useFindPatientByIdQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientByIdQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientByIdQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useFindPatientByIdQuery(
    baseOptions: Apollo.QueryHookOptions<FindPatientByIdQuery, FindPatientByIdQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useQuery<FindPatientByIdQuery, FindPatientByIdQueryVariables>(FindPatientByIdDocument, options);
}
export function useFindPatientByIdLazyQuery(
    baseOptions?: Apollo.LazyQueryHookOptions<FindPatientByIdQuery, FindPatientByIdQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useLazyQuery<FindPatientByIdQuery, FindPatientByIdQueryVariables>(FindPatientByIdDocument, options);
}
export type FindPatientByIdQueryHookResult = ReturnType<typeof useFindPatientByIdQuery>;
export type FindPatientByIdLazyQueryHookResult = ReturnType<typeof useFindPatientByIdLazyQuery>;
export type FindPatientByIdQueryResult = Apollo.QueryResult<FindPatientByIdQuery, FindPatientByIdQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!) {
        findPatientsByFilter(filter: $filter) {
            id
            addReasonCd
            addTime
            addUserId
            administrativeGenderCd
            ageCalc
            ageCalcTime
            ageCalcUnitCd
            ageCategoryCd
            ageReported
            ageReportedTime
            ageReportedUnitCd
            birthGenderCd
            birthOrderNbr
            birthTime
            birthTimeCalc
            cd
            cdDescTxt
            currSexCd
            deceasedIndCd
            deceasedTime
            description
            educationLevelCd
            educationLevelDescTxt
            ethnicGroupInd
            lastChgReasonCd
            lastChgTime
            lastChgUserId
            localId
            maritalStatusCd
            maritalStatusDescTxt
            mothersMaidenNm
            multipleBirthInd
            occupationCd
            preferredGenderCd
            primLangCd
            primLangDescTxt
            recordStatusCd
            recordStatusTime
            statusCd
            statusTime
            survivedIndCd
            userAffiliationTxt
            firstNm
            lastNm
            middleNm
            nmPrefix
            nmSuffix
            preferredNm
            hmStreetAddr1
            hmStreetAddr2
            hmCityCd
            hmCityDescTxt
            hmStateCd
            hmZipCd
            hmCntyCd
            hmCntryCd
            hmPhoneNbr
            hmPhoneCntryCd
            hmEmailAddr
            cellPhoneNbr
            wkStreetAddr1
            wkStreetAddr2
            wkCityCd
            wkCityDescTxt
            wkStateCd
            wkZipCd
            wkCntyCd
            wkCntryCd
            wkPhoneNbr
            wkPhoneCntryCd
            wkEmailAddr
            ssn
            medicaidNum
            dlNum
            dlStateCd
            raceCd
            raceSeqNbr
            raceCategoryCd
            ethnicityGroupCd
            ethnicGroupSeqNbr
            adultsInHouseNbr
            childrenInHouseNbr
            birthCityCd
            birthCityDescTxt
            birthCntryCd
            birthStateCd
            raceDescTxt
            ethnicGroupDescTxt
            versionCtrlNbr
            asOfDateAdmin
            asOfDateEthnicity
            asOfDateGeneral
            asOfDateMorbidity
            asOfDateSex
            electronicInd
            personParentUid
            dedupMatchInd
            groupNbr
            groupTime
            edxInd
            speaksEnglishCd
            additionalGenderCd
            eharsId
            ethnicUnkReasonCd
            sexUnkReasonCd
        }
    }
`;

/**
 * __useFindPatientsByFilterQuery__
 *
 * To run a query within a React component, call `useFindPatientsByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientsByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientsByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *   },
 * });
 */
export function useFindPatientsByFilterQuery(
    baseOptions: Apollo.QueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(
        FindPatientsByFilterDocument,
        options
    );
}
export function useFindPatientsByFilterLazyQuery(
    baseOptions?: Apollo.LazyQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>
) {
    const options = { ...defaultOptions, ...baseOptions };
    return Apollo.useLazyQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(
        FindPatientsByFilterDocument,
        options
    );
}
export type FindPatientsByFilterQueryHookResult = ReturnType<typeof useFindPatientsByFilterQuery>;
export type FindPatientsByFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByFilterLazyQuery>;
export type FindPatientsByFilterQueryResult = Apollo.QueryResult<
    FindPatientsByFilterQuery,
    FindPatientsByFilterQueryVariables
>;
