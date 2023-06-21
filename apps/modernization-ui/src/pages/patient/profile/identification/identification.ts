import { Maybe, PatientCodedValue, Scalars } from 'generated/graphql/schema';

export type Identification = {
    __typename?: 'PatientIdentification';
    asOf: Scalars['DateTime'];
    authority?: Maybe<PatientCodedValue>;
    patient: Scalars['Int'];
    sequence: Scalars['Int'];
    type?: PatientCodedValue;
    value?: Maybe<Scalars['String']>;
    version: Scalars['Int'];
};

export type IdentificationEntry = {
    patient: number;
    asOf: string | null;
    type: Maybe<Scalars['String']>;
    value?: Maybe<Scalars['String']>;
    state: string | null;
    sequence?: Scalars['Int'];
};
