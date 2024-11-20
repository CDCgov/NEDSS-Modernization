import { Selectable } from 'options';
import { categoryRequiredValidator } from './categoryRequiredValidator';

describe('when validating the entry of a race category', () => {
    it('should pass validation when the race category is present', async () => {
        const actual = categoryRequiredValidator(5, null);

        await expect(actual).resolves.toBe('The Race is required.');
    });

    it('should fail validation when the race category is empty because it is required', async () => {
        //  A Selectable should never be empty however, something is creating them (might be react-hook-form)
        const actual = categoryRequiredValidator(5, {} as Selectable);

        await expect(actual).resolves.toBe('The Race is required.');
    });

    it('should fail validation when the race category is not present because it is required', async () => {
        const actual = categoryRequiredValidator(5, { value: 'race-value', name: 'race name' });

        await expect(actual).resolves.toBe(true);
    });
});
