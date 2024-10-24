import { asAdministrative } from './asAdministrative';

describe('when mapping an AdministrativeEntry to an Administrative', () => {
    it('should include the as of date', () => {
        const entry = { asOf: '04/13/2017' };

        const actual = asAdministrative(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the comment', () => {
        const entry = { asOf: '04/13/2017', comment: 'entered-value' };

        const actual = asAdministrative(entry);

        expect(actual).toEqual(expect.objectContaining({ comment: 'entered-value' }));
    });

    it('should not include a blank comment', () => {
        const entry = { asOf: '04/13/2017', comment: '' };

        const actual = asAdministrative(entry);

        expect(actual).toEqual(expect.objectContaining({ comment: undefined }));
    });
});
