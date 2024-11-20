import { categoryValidator } from './categoryValidator';

describe('categoryValidator', () => {
    it('should not allow the a null race category because it is required', async () => {
        const entries = [
            {
                id: 3,
                asOf: '06/05/2024',
                race: { value: 'race-one-value', name: 'race one name' },
                detailed: []
            }
        ];

        const actual = categoryValidator(entries)(5, null);

        await expect(actual).resolves.toBe('The Race is required.');
    });

    it('should allow the same race category when validating the entry that contains the race category', async () => {
        const entries = [
            {
                id: 3,
                asOf: '06/05/2024',
                race: { value: 'race-one-value', name: 'race one name' },
                detailed: []
            }
        ];

        const actual = categoryValidator(entries)(3, { value: 'race-one-value', name: 'race one name' });

        await expect(actual).resolves.toBe(true);
    });

    it('should allow differing race categories across entries', async () => {
        const entries = [
            {
                id: 3,
                asOf: '06/05/2024',
                race: { value: 'race-one-value', name: 'race one name' },
                detailed: []
            }
        ];

        const actual = categoryValidator(entries)(5, { value: 'race-two-value', name: 'race two name' });

        await expect(actual).resolves.toBe(true);
    });

    it('should not allow the same race category more than once across multiple entries', async () => {
        const entries = [
            {
                id: 3,
                asOf: '06/05/2024',
                race: { value: 'race-one-value', name: 'race one name' },
                detailed: []
            }
        ];

        const actual = categoryValidator(entries)(5, { value: 'race-one-value', name: 'race one name' });

        await expect(actual).resolves.toBe(
            'Race race one name has already been added to the repeating block. Please select another race to add.'
        );
    });
});
