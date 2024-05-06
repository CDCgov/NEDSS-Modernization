import { asPersonInput } from './asPersonInput';

describe('when asPersonInput is given a new patient with phone numbers', () => {
    it('should have the right values for home phone', () => {
        const data = {
            asOf: '12/17/2021',
            identification: [],
            homePhone: '111-111-1111',
            phoneNumbers: [],
            emailAddresses: []
        };

        const result = asPersonInput(data);
        expect(result).toEqual(
            expect.objectContaining({
                phoneNumbers: expect.arrayContaining([
                    {
                        number: '111-111-1111',
                        type: 'PH',
                        use: 'H'
                    }
                ])
            })
        );
    });

    it('should have the right values for work phone', () => {
        const data = {
            asOf: '12/17/2021',
            identification: [],
            workPhone: '111-111-1111',
            phoneNumbers: [],
            emailAddresses: []
        };

        const result = asPersonInput(data);
        expect(result).toEqual(
            expect.objectContaining({
                phoneNumbers: expect.arrayContaining([
                    {
                        number: '111-111-1111',
                        type: 'PH',
                        use: 'WP'
                    }
                ])
            })
        );
    });

    it('should have the right values for cell phone', () => {
        const data = {
            asOf: '12/17/2021',
            identification: [],
            cellPhone: '111-111-1111',
            phoneNumbers: [],
            emailAddresses: []
        };

        const result = asPersonInput(data);
        expect(result).toEqual(
            expect.objectContaining({
                phoneNumbers: expect.arrayContaining([
                    {
                        number: '111-111-1111',
                        type: 'CP',
                        use: 'MC'
                    }
                ])
            })
        );
    });

    it('should include additional phone numbers', () => {
        const data = {
            asOf: '12/17/2021',
            identification: [],
            cellPhone: '111-111-1111',
            phoneNumbers: [
                {
                    number: '222-222-2222',
                    type: 'CP',
                    use: 'MC'
                }
            ],
            emailAddresses: []
        };

        const result = asPersonInput(data);
        expect(result).toEqual(
            expect.objectContaining({
                phoneNumbers: expect.arrayContaining([
                    {
                        number: '111-111-1111',
                        type: 'CP',
                        use: 'MC'
                    },
                    {
                        number: '222-222-2222',
                        type: 'CP',
                        use: 'MC'
                    }
                ])
            })
        );
    });
});
