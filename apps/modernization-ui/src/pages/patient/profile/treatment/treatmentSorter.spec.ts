import { Direction } from 'sorting';
import { Column } from './treatment';
import { sort } from './treatmentSorter';

describe('when sorting identifications', () => {
    it('should default sorting to by Date created descending', () => {
        const mockIdentifications = [
            {
                treatment: '10055291',
                createdOn: new Date('2023-11-29T18:30:00Z'),
                provider: 'Supervisor StateUser',
                treatedOn: new Date('2023-11-30T18:30:00Z'),
                description: ' Amoxicillin, 50 mg/kg/day, x 14-28',
                event: 'TRT10000000GA01',
                associatedWith: {
                    id: '10055290',
                    local: 'CAS10001000GA01',
                    condition: 'Bacterial Vaginosis'
                }
            },
            {
                treatment: '10055292',
                createdOn: new Date('2023-11-23T18:30:00Z'),
                provider: 'Florence Nightingale',
                treatedOn: new Date('2023-11-22T18:30:00Z'),
                description: ' Ceftriaxone, 75-100 mg/kg IV x 14-28',
                event: 'TRT10000001GA01',
                associatedWith: {
                    id: '10055290',
                    local: 'CAS10001000GA01',
                    condition: 'Bacterial Vaginosis'
                }
            },
            {
                treatment: '10055293',
                createdOn: new Date('2023-10-12T18:30:00Z'),
                provider: 'MRS Jane Cohn',
                treatedOn: new Date('2023-11-26T18:30:00Z'),
                description: ' Levofloxacin, 500 mg, PO, QD x 14 days',
                event: 'TRT10000002GA01',
                associatedWith: {
                    id: '10055290',
                    local: 'CAS10001000GA01',
                    condition: 'Bacterial Vaginosis'
                }
            }
        ];

        const actual = sort(mockIdentifications, {});

        expect(actual).toEqual([
            expect.objectContaining({ event: 'TRT10000000GA01' }),
            expect.objectContaining({ event: 'TRT10000001GA01' }),
            expect.objectContaining({ event: 'TRT10000002GA01' })
        ]);
    });
});

describe('when sorting by CreatedOn date', () => {
    const mockIdentifications = [
        {
            treatment: '10055291',
            createdOn: new Date('2023-11-29T18:30:00Z'),
            provider: 'Supervisor StateUser',
            treatedOn: new Date('2023-11-30T18:30:00Z'),
            description: ' Amoxicillin, 50 mg/kg/day, x 14-28',
            event: 'TRT10000000GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        },
        {
            treatment: '10055292',
            createdOn: new Date('2023-11-23T18:30:00Z'),
            provider: 'Florence Nightingale',
            treatedOn: new Date('2023-11-22T18:30:00Z'),
            description: ' Ceftriaxone, 75-100 mg/kg IV x 14-28',
            event: 'TRT10000001GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        },
        {
            treatment: '10055293',
            createdOn: new Date('2023-10-12T18:30:00Z'),
            provider: 'MRS Jane Cohn',
            treatedOn: new Date('2023-11-26T18:30:00Z'),
            description: ' Levofloxacin, 500 mg, PO, QD x 14 days',
            event: 'TRT10000002GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        }
    ];

    it('should sort by CreatedOn date in ascending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.DateCreated, type: Direction.Ascending });

        expect(sorted).toEqual([
            expect.objectContaining({ event: 'TRT10000002GA01' }),
            expect.objectContaining({ event: 'TRT10000001GA01' }),
            expect.objectContaining({ event: 'TRT10000000GA01' })
        ]);
    });

    it('should sort by CreatedOn date in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.DateCreated, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ event: 'TRT10000000GA01' }),
            expect.objectContaining({ event: 'TRT10000001GA01' }),
            expect.objectContaining({ event: 'TRT10000002GA01' })
        ]);
    });
});

describe('when sorting by Treatment date', () => {
    const mockIdentifications = [
        {
            treatment: '10055291',
            createdOn: new Date('2023-11-29T18:30:00Z'),
            provider: 'Supervisor StateUser',
            treatedOn: new Date('2023-11-30T18:30:00Z'),
            description: ' Amoxicillin, 50 mg/kg/day, x 14-28',
            event: 'TRT10000000GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        },
        {
            treatment: '10055292',
            createdOn: new Date('2023-11-23T18:30:00Z'),
            provider: 'Florence Nightingale',
            treatedOn: new Date('2023-11-22T18:30:00Z'),
            description: ' Ceftriaxone, 75-100 mg/kg IV x 14-28',
            event: 'TRT10000001GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        },
        {
            treatment: '10055293',
            createdOn: new Date('2023-10-12T18:30:00Z'),
            provider: 'MRS Jane Cohn',
            treatedOn: new Date('2023-11-26T18:30:00Z'),
            description: ' Levofloxacin, 500 mg, PO, QD x 14 days',
            event: 'TRT10000002GA01',
            associatedWith: {
                id: '10055290',
                local: 'CAS10001000GA01',
                condition: 'Bacterial Vaginosis'
            }
        }
    ];

    it('should sort by Treatment in ascending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.Treatment, type: Direction.Ascending });

        expect(sorted).toEqual([
            expect.objectContaining({ event: 'TRT10000000GA01' }),
            expect.objectContaining({ event: 'TRT10000001GA01' }),
            expect.objectContaining({ event: 'TRT10000002GA01' })
        ]);
    });

    it('should sort by Treatment in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Treatment, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ event: 'TRT10000002GA01' }),
            expect.objectContaining({ event: 'TRT10000001GA01' }),
            expect.objectContaining({ event: 'TRT10000000GA01' })
        ]);
    });
});
