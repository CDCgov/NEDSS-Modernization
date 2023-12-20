import { PatientLabReport as GraphQLPatientLabReport } from 'generated/graphql/schema';
import { transform } from './PatientLabReportTransformer';

describe('when the result is empty', () => {
    it('should return an empty array of reports', () => {
        const result: GraphQLPatientLabReport[] = [];

        const actual = transform(result);

        expect(actual).toHaveLength(0);
    });
});

describe('when the result has content', () => {
    it('should return transformed lab reports', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [],
                personParticipations: [
                    {
                        typeCd: 'PATSBJ',
                        personCd: 'PAT',
                        firstName: 'provider-first-name',
                        lastName: 'provider-last-name'
                    }
                ],
                organizationParticipations: [
                    {
                        typeCd: 'AUT',
                        name: 'reporting-Facility-value'
                    },
                    {
                        typeCd: 'ORD',
                        name: 'ordering-Facility-value'
                    }
                ],
                observations: [
                    {
                        domainCd: 'OTHER',
                        cdDescTxt: 'No Information Given',
                        displayName: null
                    },
                    {
                        domainCd: 'Result',
                        cdDescTxt: 'Acid-Fast Stain',
                        displayName: 'abnormal presence of'
                    }
                ]
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    report: '10066376',
                    receivedOn: new Date('2023-03-27T05:00:00Z'),
                    collectedOn: new Date('2023-03-29T05:00:00Z'),
                    programArea: 'program-area-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'local-id-value'
                })
            ])
        );
    });

    it('should return a transformed lab report with a reporting facility', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [],
                personParticipations: [],
                organizationParticipations: [
                    {
                        typeCd: 'AUT',
                        name: 'reporting-Facility-value'
                    }
                ],
                observations: []
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    reportingFacility: 'reporting-Facility-value'
                })
            ])
        );
    });

    it('should return a transformed lab report with an ordering provider', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [],
                personParticipations: [
                    {
                        typeCd: 'PATSBJ',
                        personCd: 'PAT',
                        firstName: 'patient-first-name',
                        lastName: 'patient-last-name'
                    },
                    {
                        typeCd: 'ORD',
                        personCd: 'PRV',
                        firstName: 'provider-first-name',
                        lastName: 'provider-last-name'
                    }
                ],
                organizationParticipations: [],
                observations: []
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    orderingProvider: 'provider-first-name provider-last-name'
                })
            ])
        );
    });

    it('should return a transformed lab report with an ordering facility', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [],
                personParticipations: [
                    {
                        typeCd: 'PATSBJ',
                        personCd: 'PAT',
                        firstName: 'provider-first-name',
                        lastName: 'provider-last-name'
                    }
                ],
                organizationParticipations: [
                    {
                        typeCd: 'ORD',
                        name: 'ordering-Facility-value'
                    }
                ],
                observations: []
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    orderingFacility: 'ordering-Facility-value'
                })
            ])
        );
    });

    it('should return a transformed lab report with a Test Result', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [],
                personParticipations: [],
                organizationParticipations: [],
                observations: [
                    {
                        domainCd: 'OTHER',
                        cdDescTxt: 'No Information Given',
                        displayName: null
                    },
                    {
                        domainCd: 'Result',
                        cdDescTxt: 'test-name',
                        displayName: 'result-value'
                    }
                ]
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    results: expect.arrayContaining([
                        {
                            test: 'test-name',
                            result: 'result-value'
                        }
                    ])
                })
            ])
        );
    });

    it('should return a transformed lab report associated with an investigation', () => {
        const result: GraphQLPatientLabReport[] = [
            {
                id: '10066376',
                observationUid: 10066376,
                addTime: '2023-03-27T00:00:00Z',
                effectiveFromTime: '2023-03-29T00:00:00Z',
                programAreaCd: 'program-area-value',
                jurisdictionCodeDescTxt: 'jurisdiction-value',
                localId: 'local-id-value',
                electronicInd: 'N',
                associatedInvestigations: [
                    { publicHealthCaseUid: 571, localId: 'investigation-local', cdDescTxt: 'condition-value' }
                ],
                personParticipations: [],
                organizationParticipations: [],
                observations: []
            }
        ];
        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    associatedWith: expect.arrayContaining([
                        expect.objectContaining({
                            id: '571',
                            local: 'investigation-local',
                            condition: 'condition-value'
                        })
                    ])
                })
            ])
        );
    });
});
