import {
    ActId2,
    AssociatedInvestigation2,
    OrganizationParticipation2,
    PersonParticipation2,
    Observation2
} from 'generated/graphql/schema';

type Maybe<T> = T | null;

export enum Headers {
    DateReceived = 'Date received',
    FacilityProvider = 'Facility / provider',
    DateCollected = 'Date collected',
    TestResults = 'Test results',
    AssociatedWith = 'Associated with',
    ProgramArea = 'Program area',
    Jurisdiction = 'Jurisdiction',
    EventID = 'Event #'
}

export type LabReport = {
    observationUid?: string | any;
    lastChange?: Date | null;
    classCd?: Maybe<string>;
    moodCd?: Maybe<string> | undefined;
    observationLastChgTime?: Maybe<string> | undefined;
    observations?: Maybe<Array<Maybe<Observation2>>>;
    organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation2>>>;
    cdDescTxt?: Maybe<string> | undefined;
    recordStatusCd?: Maybe<string> | undefined;
    programAreaCd?: Maybe<string> | undefined;
    jurisdictionCd?: Maybe<number> | undefined;
    jurisdictionCodeDescTxt?: Maybe<string> | undefined;
    associatedInvestigations?: Maybe<Array<Maybe<AssociatedInvestigation2>>>;
    personParticipations?: Maybe<Array<Maybe<PersonParticipation2>>>;
    pregnantIndCd?: Maybe<string> | undefined;
    localId?: Maybe<string> | undefined;
    activityToTime?: Maybe<string> | undefined;
    effectiveFromTime?: Maybe<string> | undefined;
    rptToStateTime?: Maybe<string> | undefined;
    addTime?: Date | null;
    electronicInd?: Maybe<string> | undefined;
    versionCtrlNbr?: Maybe<number> | undefined;
    addUserId?: Maybe<number> | undefined;
    lastChgUserId?: Maybe<number> | undefined;
    actIds?: Maybe<Maybe<ActId2>[]> | undefined;
};
