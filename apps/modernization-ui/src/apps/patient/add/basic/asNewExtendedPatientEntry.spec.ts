import { today } from 'date';
import { BasicNewPatientEntry } from './entry';
import { asSelectable } from 'options';
import { asNewExtendedPatientEntry } from './asNewExtendedPatientEntry';

describe('Basic form to extended transfer', () => {
    it('should transfer name to extended', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            name: {
                first: 'testFirst',
                middle: 'testMiddle',
                last: 'testLast',
                suffix: asSelectable('ESQ', 'Esquire')
            }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.names).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    first: 'testFirst',
                    last: 'testLast',
                    middle: 'testMiddle',
                    suffix: expect.objectContaining({ value: 'ESQ' })
                })
            ])
        );
    });
    it('should transfer address', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            address: {
                address1: 'test address 1',
                address2: 'test address 2',
                city: 'city',
                country: asSelectable('US', 'United states'),
                state: asSelectable('AL', 'Alabama'),
                zipcode: '12345'
            }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.addresses).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    asOf: date,
                    address1: 'test address 1',
                    address2: 'test address 2',
                    country: expect.objectContaining({
                        name: 'United states'
                    }),
                    state: expect.objectContaining({
                        name: 'Alabama'
                    }),
                    zipcode: '12345',
                    city: 'city'
                })
            ])
        );
    });
    it('should transfer phone and emails', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            phoneEmail: {
                cell: '1231231234',
                work: { phone: '1231231234' },
                home: '1231231234',
                email: 'test@test.com'
            }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.phoneEmails).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    phoneNumber: '1231231234',
                    type: expect.objectContaining({ name: 'Phone' }),
                    use: expect.objectContaining({ name: 'Home' })
                }),
                expect.objectContaining({
                    phoneNumber: '1231231234',
                    type: expect.objectContaining({ name: 'Cellular phone' }),
                    use: expect.objectContaining({ name: 'Mobile contact' })
                }),
                expect.objectContaining({
                    phoneNumber: '1231231234',
                    type: expect.objectContaining({ name: 'Phone' }),
                    use: expect.objectContaining({ name: 'Primary work place' })
                }),
                expect.objectContaining({
                    email: 'test@test.com'
                })
            ])
        );
    });
    it('should transfer identifications', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            identifications: [{ type: asSelectable('ID'), issuer: asSelectable('test authority'), id: '12344' }]
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.identifications).toBeTruthy();
        expect(result.identifications?.at(0)?.id).toBe('12344');
        expect(result.identifications?.at(0)?.issuer?.name).toBe('test authority');
        expect(result.identifications?.at(0)?.type?.name).toBe('ID');
    });
    it('should transfer race', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            ethnicityRace: { races: [asSelectable('A', 'Asian')], ethnicity: asSelectable('T', 'Test') }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.races).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    race: expect.objectContaining({
                        name: 'Asian'
                    })
                })
            ])
        );
    });
    it('should transfer ethnicity', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date },
            ethnicityRace: { ethnicity: asSelectable('T', 'Test') }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.ethnicity).toEqual(
            expect.objectContaining({
                detailed: [],
                asOf: date,
                ethnicGroup: { name: 'Test', value: 'T' }
            })
        );
    });
    it('should transfer other information', () => {
        const date = today();
        const initial: BasicNewPatientEntry = {
            administrative: { asOf: date, comment: 'test comments' },
            personalDetails: {
                bornOn: '01/01/1999',
                currentSex: asSelectable('M', 'Male'),
                birthSex: asSelectable('M', 'Male'),
                deceased: asSelectable('Y'),
                deceasedOn: '01/01/2004',
                maritalStatus: asSelectable('Anulled'),
                stateHIVCase: '1234'
            }
        };

        const result = asNewExtendedPatientEntry(initial);

        expect(result.administrative?.comment).toBe('test comments');

        expect(result.birthAndSex).toEqual(
            expect.objectContaining({
                bornOn: '01/01/1999',
                sex: expect.objectContaining({
                    name: 'Male'
                }),
                current: expect.objectContaining({
                    name: 'Male'
                })
            })
        );

        expect(result.mortality).toEqual(
            expect.objectContaining({
                deceased: expect.objectContaining({
                    name: 'Y'
                }),
                deceasedOn: '01/01/2004'
            })
        );

        expect(result.general).toEqual(
            expect.objectContaining({
                maritalStatus: expect.objectContaining({ name: 'Anulled' }),
                stateHIVCase: '1234'
            })
        );
    });
});
