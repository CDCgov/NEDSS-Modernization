import { PersonFilter, RecordStatus } from 'generated/graphql/schema';
import { internalize } from './InternalizePersonFilter';

describe('when a PersonFilter is internalized', () => {
    it('should format dateOfBirth to mm/dd/yyyy Date format', () => {
        const filter: PersonFilter = {
            recordStatus: [RecordStatus.Active],
            dateOfBirth: '1990-01-01'
        };

        const actual = internalize(filter);

        expect(actual).toEqual(
            expect.objectContaining({
                dateOfBirth: '01/01/1990'
            })
        );
    });

    it('should not format dateOfBirth if null', () => {
        const filter: PersonFilter = {
            recordStatus: [RecordStatus.Active],
            dateOfBirth: null
        };

        const actual = internalize(filter);

        expect(actual).toEqual(
            expect.objectContaining({
                dateOfBirth: null
            })
        );
    });

    it('should not format dateOfBirth if not present', () => {
        const filter: PersonFilter = {
            recordStatus: [RecordStatus.Active]
        };

        const actual = internalize(filter);

        expect(actual).not.toHaveProperty('dateOfBirth');
    });
});
