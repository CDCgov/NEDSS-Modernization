import { Direction } from 'sorting';
import { sort } from './PatientVaccinationSorter';
import { Headers, isAssociatedWith } from './PatientVaccination';

describe('when sorting vaccinations', () => {
    it('should default sorting to by Date created descending', () => {
        const vaccinations = [
            {
                vaccination: '1583',
                createdOn: new Date('2023-10-07T00:00:00Z'),
                administered: 'administered',
                event: 'event'
            },
            {
                vaccination: '617',
                createdOn: new Date('2021-10-07T00:00:00Z'),
                administered: 'administered',
                event: 'event'
            },
            {
                vaccination: '727',
                createdOn: new Date('2022-02-06T00:00:00Z'),
                administered: 'administered',
                event: 'event'
            }
        ];

        const actual = sort(vaccinations, {});

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' })
        ]);
    });
});

describe('when sorting vaccinations by Date Created', () => {
    const vaccinations = [
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event'
        },
        {
            vaccination: '617',
            createdOn: new Date('2022-03-05T01:41:47Z'),
            administered: 'administered',
            event: 'event'
        },
        {
            vaccination: '727',
            createdOn: new Date('2023-08-15T00:00:00Z'),
            administered: 'administered',
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.DateCreated, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.DateCreated, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});

describe('when sorting vaccinations by Provider', () => {
    const vaccinations = [
        {
            vaccination: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            provider: 'B'
        },
        {
            vaccination: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            provider: 'C'
        },
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            provider: 'A'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.Provider, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.Provider, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});

describe('when sorting vaccinations by Date administered', () => {
    const vaccinations = [
        {
            vaccination: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            administeredOn: new Date('2022-08-01T11:00:00Z')
        },
        {
            vaccination: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            administeredOn: new Date('2023-10-15T00:00:00Z')
        },
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            administeredOn: new Date('2022-06-21T11:17:19Z')
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.DateAdministered, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.DateAdministered, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});

describe('when sorting vaccinations by Vaccine administered', () => {
    const vaccinations = [
        {
            vaccination: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'B',
            event: 'event'
        },
        {
            vaccination: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'C',
            event: 'event'
        },
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: '01',
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.VaccineAdministered, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.VaccineAdministered, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});

describe('when sorting vaccinations by Associated with', () => {
    const vaccinations = [
        {
            vaccination: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            associatedWith: {
                id: 'association',
                condition: 'condition',
                local: 'B'
            }
        },
        {
            vaccination: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            associatedWith: {
                id: 'association',
                condition: 'condition',
                local: 'X'
            }
        },
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'event',
            associatedWith: {
                id: 'association',
                condition: 'condition',
                local: '97'
            }
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.AssociatedWith, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.AssociatedWith, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});

describe('when sorting vaccinations by Event', () => {
    const vaccinations = [
        {
            vaccination: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'B'
        },
        {
            vaccination: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: 'X'
        },
        {
            vaccination: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            administered: 'administered',
            event: '16'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(vaccinations, { name: Headers.Event, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '1583' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(vaccinations, { name: Headers.Event, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ vaccination: '727' }),
            expect.objectContaining({ vaccination: '617' }),
            expect.objectContaining({ vaccination: '1583' })
        ]);
    });
});
