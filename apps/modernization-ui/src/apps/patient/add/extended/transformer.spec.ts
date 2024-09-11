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

    it('should transform phone and emails', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017' },
            phoneEmails: [
                {
                    asOf: '04/13/2017',
                    type: { value: 'phone-email-type-value', name: 'phone-email-type-name' },
                    use: { value: 'phone-email-use-value', name: 'phone-email-use-name' }
                }
            ]
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({
                phoneEmails: expect.arrayContaining([
                    expect.objectContaining({ type: 'phone-email-type-value', use: 'phone-email-use-value' })
                ])
            })
        );
    });

    it('should transform identifictions', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017' },
            identifications: [
                {
                    asOf: '04/13/2017',
                    type: { value: 'identification-type-value', name: 'identification-type-name' },
                    id: 'id-value'
                }
            ]
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({
                identifications: expect.arrayContaining([
                    expect.objectContaining({ type: 'identification-type-value' })
                ])
            })
        );
    });

    it('should transform races', () => {
        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017' },
            races: [
                {
                    asOf: '04/13/2017',
                    race: { value: 'race-value', name: 'race-name' },
                    detailed: []
                }
            ]
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({
                races: expect.arrayContaining([expect.objectContaining({ race: 'race-value' })])
            })
        );
    });
});
