import { PersonFilter, RecordStatus } from 'generated/graphql/schema';
import { externalize } from './ExternalizePersonFilter';

describe('when a PersonFilter is externalized', () => {
    it('should format dateOfBirth to ISO-8601 Date format', () => {
        const filter: PersonFilter = {
            recordStatus: [RecordStatus.Active],
            dateOfBirth: '01/01/1990'
        };

        const actual = externalize(filter);

        expect(actual).toEqual(
            expect.objectContaining({
                dateOfBirth: '1990-01-01'
            })
        );
    });

    it('should not format dateOfBirth if null', () => {
        const filter: PersonFilter = {
            recordStatus: [RecordStatus.Active],
            dateOfBirth: null
        };

        const actual = externalize(filter);

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

        const actual = externalize(filter);

        expect(actual).not.toHaveProperty('dateOfBirth');
    });
});
