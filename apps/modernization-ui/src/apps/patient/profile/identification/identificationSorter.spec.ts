import { Direction } from 'sorting';
import { Column } from './identification';
import { sort } from './identificationSorter';

describe('when sorting identifications', () => {
    it('should default sorting to by Date created descending', () => {
        const mockIdentifications = [
            {
                patient: 10000001,
                sequence: 2,
                version: 12,
                asOf: new Date('2023-11-29T18:30:00Z'),
                authority: {
                    id: 'GA',
                    description: 'GA'
                },
                value: '059687945',
                type: {
                    id: 'APT',
                    description: 'Alternate person number'
                }
            },
            {
                patient: 10000001,
                sequence: 6,
                version: 12,
                asOf: new Date('2023-12-06T18:30:00Z'),
                authority: {
                    id: 'SSA',
                    description: 'Social Security Administration'
                },
                value: '3298745',
                type: {
                    id: 'MR',
                    description: 'Medical record number'
                }
            },
            {
                patient: 10000001,
                sequence: 7,
                version: 12,
                asOf: new Date('2023-12-06T18:30:00Z'),
                authority: {
                    id: 'CA',
                    description: 'CA'
                },
                value: '9384065',
                type: {
                    id: 'PSID',
                    description: 'Partner Services Patient Number'
                }
            }
        ];

        const actual = sort(mockIdentifications, {});

        expect(actual).toEqual([
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '9384065' }),
            expect.objectContaining({ value: '059687945' })
        ]);
    });
});

describe('when sorting by AsOf date', () => {
    const mockIdentifications = [
        {
            patient: 10000001,
            sequence: 2,
            version: 12,
            asOf: new Date('2023-11-29T18:30:00Z'),
            authority: {
                id: 'GA',
                description: 'GA'
            },
            value: '059687945',
            type: {
                id: 'APT',
                description: 'Alternate person number'
            }
        },
        {
            patient: 10000001,
            sequence: 6,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'SSA',
                description: 'Social Security Administration'
            },
            value: '3298745',
            type: {
                id: 'MR',
                description: 'Medical record number'
            }
        },
        {
            patient: 10000001,
            sequence: 7,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'CA',
                description: 'CA'
            },
            value: '9384065',
            type: {
                id: 'PSID',
                description: 'Partner Services Patient Number'
            }
        }
    ];

    it('should sort by asOf date in ascending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.AsOf, type: Direction.Ascending });

        expect(sorted).toEqual([
            expect.objectContaining({ value: '059687945' }),
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '9384065' })
        ]);
    });

    it('should sort by asOf date in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.AsOf, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '9384065' }),
            expect.objectContaining({ value: '059687945' })
        ]);
    });
});

describe('when sorting by Type', () => {
    const mockIdentifications = [
        {
            patient: 10000001,
            sequence: 2,
            version: 12,
            asOf: new Date('2023-11-29T18:30:00Z'),
            authority: {
                id: 'GA',
                description: 'GA'
            },
            value: '059687945',
            type: {
                id: 'APT',
                description: 'Alternate person number'
            }
        },
        {
            patient: 10000001,
            sequence: 6,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'SSA',
                description: 'Social Security Administration'
            },
            value: '3298745',
            type: {
                id: 'MR',
                description: 'Medical record number'
            }
        },
        {
            patient: 10000001,
            sequence: 7,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'CA',
                description: 'CA'
            },
            value: '9384065',
            type: {
                id: 'PSID',
                description: 'Partner Services Patient Number'
            }
        }
    ];

    it('should sort by Type in ascending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Type, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '059687945' }),
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '9384065' })
        ]);
    });

    it('should sort by Type in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Type, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '9384065' }),
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '059687945' })
        ]);
    });
});

describe('when sorting by Authority', () => {
    const mockIdentifications = [
        {
            patient: 10000001,
            sequence: 2,
            version: 12,
            asOf: new Date('2023-11-29T18:30:00Z'),
            authority: {
                id: 'GA',
                description: 'GA'
            },
            value: '059687945',
            type: {
                id: 'APT',
                description: 'Alternate person number'
            }
        },
        {
            patient: 10000001,
            sequence: 6,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'SSA',
                description: 'Social Security Administration'
            },
            value: '3298745',
            type: {
                id: 'MR',
                description: 'Medical record number'
            }
        },
        {
            patient: 10000001,
            sequence: 7,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'CA',
                description: 'CA'
            },
            value: '9384065',
            type: {
                id: 'PSID',
                description: 'Partner Services Patient Number'
            }
        }
    ];

    it('should sort by Authority in ascending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Authority, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '9384065' }),
            expect.objectContaining({ value: '059687945' }),
            expect.objectContaining({ value: '3298745' })
        ]);
    });

    it('should sort by Authority in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Authority, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '059687945' }),
            expect.objectContaining({ value: '9384065' })
        ]);
    });
});

describe('when sorting by Value', () => {
    const mockIdentifications = [
        {
            patient: 10000001,
            sequence: 2,
            version: 12,
            asOf: new Date('2023-11-29T18:30:00Z'),
            authority: {
                id: 'GA',
                description: 'GA'
            },
            value: '059687945',
            type: {
                id: 'APT',
                description: 'Alternate person number'
            }
        },
        {
            patient: 10000001,
            sequence: 6,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'SSA',
                description: 'Social Security Administration'
            },
            value: '3298745',
            type: {
                id: 'MR',
                description: 'Medical record number'
            }
        },
        {
            patient: 10000001,
            sequence: 7,
            version: 12,
            asOf: new Date('2023-12-06T18:30:00Z'),
            authority: {
                id: 'CA',
                description: 'CA'
            },
            value: '9384065',
            type: {
                id: 'PSID',
                description: 'Partner Services Patient Number'
            }
        }
    ];

    it('should sort by Value in ascending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Value, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '059687945' }),
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '9384065' })
        ]);
    });

    it('should sort by Value in descending order', () => {
        const actual = sort(mockIdentifications, { name: Column.Value, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ value: '9384065' }),
            expect.objectContaining({ value: '3298745' }),
            expect.objectContaining({ value: '059687945' })
        ]);
    });
});
