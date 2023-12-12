import { Direction } from 'sorting';
import { Column } from './NameEntry';
import { sort } from './NameSorter';

describe('when sorting ', () => {
    it('should default sorting to by Date created descending', () => {
        const mockNames = [
            {
                patient: 10014282,
                sequence: 20,
                version: 7,
                asOf: new Date('2023-01-17T00:00:00Z'),
                prefix: {
                    id: 'REV',
                    description: 'Reverend'
                },                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Bob',
                middle: 'John',
                secondMiddle: 'David',
                last: "Smith",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
                
            },
            {
                patient: 10000001,
                sequence: 6,
                version: 12,
                asOf: new Date('2023-11-29T18:30:00Z'),
                prefix: {
                    id: 'BSHP',
                    description: 'Bishop'
                },                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Mike',
                middle: 'John',
                secondMiddle: 'David',
                last: "Larry",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
            },
            {
                patient: 10000008,
                sequence: 7,
                version: 12,
                asOf: new Date('2023-12-06T18:30:00Z'),
                prefix: null,                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Jameseson',
                middle: 'John',
                secondMiddle: 'David',
                last: "Jimmy",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
            }
        ];

        const actual = sort(mockNames, {});
        expect(actual).toEqual([
            expect.objectContaining({ patient: 10000008 }),
            expect.objectContaining({ patient: 10000001 }),
            expect.objectContaining({ patient: 10014282 })
        ]);
    });
});

describe('when sorting by AsOf date', () => {
        const mockNames = [
            {
                patient: 10014282,
                sequence: 20,
                version: 7,
                asOf: new Date('2023-01-17T00:00:00Z'),
                prefix: {
                    id: 'REV',
                    description: 'Reverend'
                },                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Bob',
                middle: 'John',
                secondMiddle: 'David',
                last: "Smith",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
                
            },
            {
                patient: 10000001,
                sequence: 6,
                version: 12,
                asOf: new Date('2023-11-29T18:30:00Z'),
                prefix: {
                    id: 'BSHP',
                    description: 'Bishop'
                },                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Mike',
                middle: 'John',
                secondMiddle: 'David',
                last: "Larry",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
            },
            {
                patient: 10000008,
                sequence: 7,
                version: 12,
                asOf: new Date('2023-12-06T18:30:00Z'),
                prefix: null,                        
                use: {
                    id: 'AD',
                    description: 'Adopted Name'
                },
                first: 'Jameseson',
                middle: 'John',
                secondMiddle: 'David',
                last: "Jimmy",
                secondLast: 'Sarah', 
                suffix: {
                    id: 'ESQ',
                    description: 'Esquire'
                },                                            
                degree: {
                    id: 'PHD',
                    description: 'Doctor of Philosophy'
                }
            }
        ];

    it('should sort by asOf date in ascending order', () => {
        const sorted = sort(mockNames, { name: Column.AsOf, type: Direction.Ascending });

        expect(sorted).toEqual([
            expect.objectContaining({ patient: 10014282 }),
            expect.objectContaining({ patient: 10000001 }),
            expect.objectContaining({ patient: 10000008 })
        ]);
    });

    it('should sort by asOf date in descending order', () => {
        const actual = sort(mockNames, { name: Column.AsOf, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ patient: 10000008 }),
            expect.objectContaining({ patient: 10000001 }),
            expect.objectContaining({ patient: 10014282 })
        ]);
    });
});
