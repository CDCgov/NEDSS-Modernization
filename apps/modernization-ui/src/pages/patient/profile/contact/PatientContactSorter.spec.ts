import { Direction } from 'sorting';
import { sort } from './PatientContactSorter';
import { Headers } from './PatientContacts';

describe('when sorting contacts', () => {
    it('should default sorting to by Date created descending', () => {
        const contacts = [
            {
                contactRecord: '1583',
                createdOn: new Date('2023-10-07T00:00:00Z'),
                contact: {
                    id: 'contact-id',
                    name: 'contact-name'
                },
                namedOn: new Date('2023-01-17T00:00:00Z'),
                event: 'event'
            },
            {
                contactRecord: '617',
                createdOn: new Date('2021-10-07T00:00:00Z'),
                contact: {
                    id: 'contact-id',
                    name: 'contact-name'
                },
                namedOn: new Date('2023-01-17T00:00:00Z'),
                event: 'event'
            },
            {
                contactRecord: '727',
                createdOn: new Date('2022-02-06T00:00:00Z'),
                contact: {
                    id: 'contact-id',
                    name: 'contact-name'
                },
                namedOn: new Date('2023-01-17T00:00:00Z'),
                event: 'event'
            }
        ];

        const actual = sort(contacts, {});

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' })
        ]);
    });
});

describe('when sorting contacts by Date created', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '727',
            createdOn: new Date('2023-08-15T00:00:00Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '617',
            createdOn: new Date('2022-03-05T01:41:47Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.DateCreated, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.DateCreated, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Named by', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'A'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'C'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'B'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.NamedBy, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.NamedBy, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Contacts named', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'A'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'C'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'B'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.ContactsNamed, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.ContactsNamed, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Date created', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2023-01-17T00:00:00Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2021-10-07T15:01:10Z'),
            event: 'event'
        },
        {
            contactRecord: '727',
            createdOn: new Date('2023-01-17T00:00:00Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-08-15T00:00:00Z'),
            event: 'event'
        },
        {
            contactRecord: '617',
            createdOn: new Date('2023-01-17T00:00:00Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2022-03-05T01:41:47Z'),
            event: 'event'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.DateNamed, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.DateNamed, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Description', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            condition: {
                id: 'condition-id',
                description: 'A'
            }
        },
        {
            contactRecord: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            condition: {
                id: 'condition-id',
                description: 'C'
            }
        },
        {
            contactRecord: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            condition: {
                id: 'condition-id',
                description: 'B'
            }
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.Description, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.Description, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Associated with', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            associatedWith: {
                id: 'associated-id',
                local: 'A',
                condition: 'associated-condition'
            }
        },
        {
            contactRecord: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            associatedWith: {
                id: 'associated-id',
                local: 'C',
                condition: 'associated-condition'
            }
        },
        {
            contactRecord: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'event',
            associatedWith: {
                id: 'associated-id',
                local: 'B',
                condition: 'associated-condition'
            }
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.AssociatedWith, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.AssociatedWith, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});

describe('when sorting contacts by Event #', () => {
    const contacts = [
        {
            contactRecord: '1583',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'A'
        },
        {
            contactRecord: '727',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'C'
        },
        {
            contactRecord: '617',
            createdOn: new Date('2021-10-07T15:01:10Z'),
            contact: {
                id: 'contact-id',
                name: 'contact-name'
            },
            namedOn: new Date('2023-01-17T00:00:00Z'),
            event: 'B'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(contacts, { name: Headers.Event, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '1583' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(contacts, { name: Headers.Event, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ contactRecord: '727' }),
            expect.objectContaining({ contactRecord: '617' }),
            expect.objectContaining({ contactRecord: '1583' })
        ]);
    });
});
