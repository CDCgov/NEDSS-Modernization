import { ExtendedNewPatientEntry } from './entry';
import { transformer } from './transformer';

describe('when transforming entered extended patient data', () => {
    it('should transform administrative to a format accepted by the API', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered-value' }
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({ administrative: { asOf: '04/13/2017', comment: 'entered-value' } })
        );
    });

    it('should transform names', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017' },
            names: [
                {
                    asOf: '04/13/2017',
                    type: { value: 'name-type-value', name: 'name-type-name' }
                }
            ]
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({
                names: expect.arrayContaining([expect.objectContaining({ type: 'name-type-value' })])
            })
        );
    });

    it('should transform addresses', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017' },
            addresses: [
                {
                    asOf: '04/13/2017',
                    type: { value: 'address-type-value', name: 'address-type-name' },
                    use: { value: 'address-use-value', name: 'address-use-name' }
                }
            ]
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({
                addresses: expect.arrayContaining([
                    expect.objectContaining({ type: 'address-type-value', use: 'address-use-value' })
                ])
            })
        );
    });
});
