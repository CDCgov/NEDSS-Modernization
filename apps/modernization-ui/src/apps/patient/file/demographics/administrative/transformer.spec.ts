import { transformer } from './transformer';

describe('Patient File Administrative Information transformer', () => {
    it('should transform with as of date if present', () => {
        const actual = transformer({ asOf: '2025-06-09T06:38:09' });

        expect(actual).toEqual(expect.objectContaining({ asOf: new Date('2025-06-09T06:38:09') }));
    });

    it('should transform with comment if present', () => {
        const actual = transformer({ comment: 'comment value' });

        expect(actual).toEqual(expect.objectContaining({ comment: 'comment value' }));
    });
});
