import { transform } from './LabReportTransformer';

describe('when the result is empty', () => {
    it('should return an empty array of reports', () => {
        const result = {
            content: [],
            total: 0,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toHaveLength(0);
    });
});

describe('when the result has content', () => {
    it('should return an array of reports', () => {
        const result = {
            [
                {
                    observationUid: '691',
                    addTime: new Date('2023-03-27T05:00:00Z'),
                    organizationParticipations: [],
                    observations: [],
                    associatedInvestigations: [],
                    actIds: [],
                    associatedInvestigations: [],
                    programAreaCd: 'BMIRD',
                    jurisdictionCd: '999999',
                    localId: 'OBS10001022GA01'
                },
                {
                    observationUid: '692',
                    addTime: new Date('2023-03-27T05:00:00Z'),
                    organizationParticipations: [],
                    observations: [],
                    associatedInvestigations: [],
                    actIds: [],
                    associatedInvestigations: [],
                    programAreaCd: 'STD',
                    jurisdictionCd: '130006',
                    localId: 'OBS10001022GA01'
                }
            ],
            total: 1,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    observationUid: '692',
                    addTime: new Date('2023-03-27T05:00:00Z'),
                    organizationParticipations: [],
                    observations: [],
                    associatedInvestigations: [],
                    actIds: [],
                    associatedInvestigations: [],
                    programAreaCd: 'STD',
                    jurisdictionCd: '130006',
                    localId: 'OBS10001022GA01'
                }),
                expect.objectContaining({
                    observationUid: '691',
                    addTime: new Date('2023-03-27T05:00:00Z'),
                    organizationParticipations: [],
                    observations: [],
                    associatedInvestigations: [],
                    actIds: [],
                    associatedInvestigations: [],
                    programAreaCd: 'BMIRD',
                    jurisdictionCd: '999999',
                    localId: 'OBS10001022GA01'
                })
            ])
        );
    });
});
