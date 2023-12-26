import { Direction } from 'sorting';
import { sort } from './PatientInvestigationSorter';
import { Headers } from './PatientInvestigation';

describe('when sorting investigations', () => {
    it('should default sorting to by Start date descending', () => {
        const investigations = [
            {
                investigation: '1583',
                condition: 'condition',
                status: 'status',
                jurisdiction: 'jurisdiction',
                event: 'event',
                comparable: true,
                startedOn: new Date('2023-03-27T00:00:00Z')
            },
            {
                investigation: '617',
                condition: 'condition',
                status: 'status',
                jurisdiction: 'jurisdiction',
                event: 'event',
                comparable: true
            },
            {
                investigation: '727',
                condition: 'condition',
                status: 'status',
                jurisdiction: 'jurisdiction',
                event: 'event',
                comparable: true,
                startedOn: new Date('2023-01-27T00:00:00Z')
            }
        ];

        const actual = sort(investigations, {});

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Start date', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            startedOn: new Date('2023-03-27T00:00:00Z')
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            startedOn: new Date('2023-01-27T00:00:00Z')
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.StartDate, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.StartDate, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Condition', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'C',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        },
        {
            investigation: '617',
            condition: 'A',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        },
        {
            investigation: '727',
            condition: 'B',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.Condition, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.Condition, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Status', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'C',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'A',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'B',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.Status, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.Status, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Case status', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            caseStatus: 'C'
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            caseStatus: 'A'
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            caseStatus: 'B'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.CaseStatus, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.CaseStatus, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Jurisdiction', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'C',
            event: 'event',
            comparable: true
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'A',
            event: 'event',
            comparable: true
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'B',
            event: 'event',
            comparable: true
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.Jurisdiction, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.Jurisdiction, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Investigator', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            investigator: 'C'
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            investigator: 'A'
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            investigator: 'B'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.Investigator, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.Investigator, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Investigation', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'C',
            comparable: true
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: '1',
            comparable: true
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'B',
            comparable: true
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.Investigation, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.Investigation, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});

describe('when sorting investigations by Co-infection', () => {
    const investigations = [
        {
            investigation: '1583',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            coInfection: 'C'
        },
        {
            investigation: '617',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            coninfection: '1'
        },
        {
            investigation: '727',
            condition: 'condition',
            status: 'status',
            jurisdiction: 'jurisdiction',
            event: 'event',
            comparable: true,
            coInfection: 'B'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(investigations, { name: Headers.CoInfection, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '617' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(investigations, { name: Headers.CoInfection, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ investigation: '1583' }),
            expect.objectContaining({ investigation: '727' }),
            expect.objectContaining({ investigation: '617' })
        ]);
    });
});
