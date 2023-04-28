import { asTableBody } from './PatientContactTableRenderer';

describe('when rendering contact tracings', () => {
    const tracing = {
        contactRecord: '10055380',
        createdOn: '2023-03-17T20:08:45.213Z',
        contact: { id: '10000008', name: 'Contact Name' },
        namedOn: '2023-01-17T05:00:00Z',
        condition: 'condition-value',
        priority: 'priority-value',
        disposition: 'disposition-value',
        event: 'event-value',
        associatedWith: {
            id: '10000013',
            local: 'CAS10000000GA01',
            condition: 'associated-condition'
        }
    };
    it('should display as table details', () => {
        const actual = asTableBody(tracing);

        expect(actual.checkbox).toBeFalsy();
        expect(actual.id).toBe('event-value');
        expect(actual.tableDetails).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    id: 1,
                    class: 'link'
                }),
                expect.objectContaining({
                    id: 2,
                    title: 'Contact Name'
                }),
                expect.objectContaining({
                    id: 3,
                    title: '01/17/2023'
                }),
                expect.objectContaining({
                    id: 6,
                    title: 'event-value'
                })
            ])
        );
    });
});
