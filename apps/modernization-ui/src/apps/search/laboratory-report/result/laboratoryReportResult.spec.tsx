import { LabReport } from 'generated/graphql/schema';
import {
    getAssociatedInvestigations,
    getDescription,
    getOrderingProviderName,
    getPatient,
    getPatientName,
    getReportingFacility
} from './laboratoryReportResult';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';

describe('when displaying a Laboratory Search Result', () => {
    it('should resolve the patient when present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '1979-05-19',
                    currSexCd: 'M',
                    typeCd: 'PATSBJ',
                    firstName: 'patient-first-name',
                    lastName: 'patient-last-name',
                    personCd: 'PAT',
                    personParentUid: 349,
                    shortId: 919
                },
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '2001-11-17',
                    currSexCd: 'F',
                    typeCd: 'ORD',
                    firstName: 'provider-first-name',
                    lastName: 'provider-last-name',
                    personCd: 'PRV',
                    personParentUid: 503,
                    shortId: 571
                }
            ],
            relevance: 1
        };

        const actual = getPatient(result);

        expect(actual).toEqual(expect.objectContaining({ shortId: 919 }));
    });

    it('should resolve the patient name when present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '1979-05-19',
                    currSexCd: 'M',
                    typeCd: 'PATSBJ',
                    firstName: 'patient-first-name',
                    lastName: 'patient-last-name',
                    personCd: 'PAT',
                    personParentUid: 349,
                    shortId: 919
                },
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '2001-11-17',
                    currSexCd: 'F',
                    typeCd: 'ORD',
                    firstName: 'provider-first-name',
                    lastName: 'provider-last-name',
                    personCd: 'PRV',
                    personParentUid: 503,
                    shortId: 571
                }
            ],
            relevance: 1
        };

        const { getByRole } = render(<MemoryRouter>{getPatientName(result)}</MemoryRouter>);

        const actual = getByRole('link', { name: 'patient-first-name patient-last-name' });

        expect(actual).toHaveAttribute('href', '/patient-profile/919');
    });

    it('should resolve the Ordering Provider when present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '1979-05-19',
                    currSexCd: 'M',
                    typeCd: 'PATSBJ',
                    firstName: 'patient-first-name',
                    lastName: 'patient-last-name',
                    personCd: 'PAT',
                    personParentUid: 349,
                    shortId: 919
                },
                {
                    __typename: 'LabReportPersonParticipation',
                    birthTime: '2001-11-17',
                    currSexCd: 'F',
                    typeCd: 'ORD',
                    firstName: 'provider-first-name',
                    lastName: 'provider-last-name',
                    personCd: 'PRV',
                    personParentUid: 503,
                    shortId: 571
                }
            ],
            relevance: 1
        };

        const actual = getOrderingProviderName(result);

        expect(actual).toEqual('provider-first-name provider-last-name');
    });

    it('should resolve the Reporting Facility when present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [],
            organizationParticipations: [
                {
                    __typename: 'LabReportOrganizationParticipation',
                    typeCd: 'AUT',
                    name: 'ordering-facility-value'
                }
            ],
            personParticipations: [],
            relevance: 1
        };

        const actual = getReportingFacility(result);

        expect(actual).toEqual('ordering-facility-value');
    });

    it('should resolve the description when lab results are present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [
                {
                    __typename: 'Observation',
                    cdDescTxt: 'No Information Given',
                    statusCd: null,
                    altCd: null,
                    displayName: null
                },
                {
                    __typename: 'Observation',
                    cdDescTxt: 'lab-test-name',
                    altCd: 'lab-test-code',
                    displayName: 'lab-test-value'
                }
            ],
            organizationParticipations: [],
            personParticipations: [],
            relevance: 1
        };

        const actual = getDescription(result);

        expect(actual).toEqual('lab-test-name = lab-test-value');
    });

    it('should resolve associated investigations when present', () => {
        const result: LabReport = {
            __typename: 'LabReport',
            addTime: '2015-09-22',
            associatedInvestigations: [
                {
                    __typename: 'AssociatedInvestigation',
                    cdDescTxt: 'condition-one-name',
                    localId: 'condition-one-local'
                },
                {
                    __typename: 'AssociatedInvestigation',
                    cdDescTxt: 'condition-two-name',
                    localId: 'condition-two-local'
                }
            ],
            id: 'id-value',
            jurisdictionCd: 1063,
            localId: 'local-id-value',
            observations: [],
            organizationParticipations: [],
            personParticipations: [],
            relevance: 1
        };

        const actual = getAssociatedInvestigations(result);

        expect(actual).toEqual(expect.stringContaining('condition-one-local\ncondition-one-name'));
        expect(actual).toEqual(expect.stringContaining('condition-two-local\ncondition-two-name'));
    });
});