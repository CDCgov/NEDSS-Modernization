import { ApiError } from 'generated';
import { ApiResult } from 'generated/core/ApiResult';
import { PatientRaceService } from 'generated/services/PatientRaceService';
import { validateCategory } from './validateCategory';

jest.mock('generated/services/PatientRaceService');

describe('when validating a race category', () => {
    it('should pass when the patient profile does not already include the race category', async () => {
        PatientRaceService.validateCategory = jest.fn().mockReturnValue(Promise.resolve());

        const actual = await validateCategory(3)('race-category');

        expect(actual).toBe(true);
    });

    it('should not pass when the patient profile already includes the race category', async () => {
        const result: ApiResult = {
            body: { description: 'existing-race-category' },
            url: '',
            ok: false,
            status: 0,
            statusText: ''
        };

        const error = new ApiError({ method: 'POST', url: 'url' }, result, 'error');

        PatientRaceService.validateCategory = jest.fn().mockReturnValue(Promise.reject(error));

        const actual = await validateCategory(4)('existing-race-category');

        expect(actual).toEqual(`The patient has an existing race demographic for "existing-race-category"`);
    });

    it('should skip validation when the category is allowed', async () => {
        PatientRaceService.validateCategory = jest.fn();

        const actual = await validateCategory(4, 'allowed-race-category')('allowed-race-category');

        expect(actual).toBe(true);
        expect(PatientRaceService.validateCategory).not.toHaveBeenCalled();
    });

    it('should not skip validation for category not matching the allowed category', async () => {
        const result: ApiResult = {
            body: { description: 'existing-race-category' },
            url: '',
            ok: false,
            status: 0,
            statusText: ''
        };

        const error = new ApiError({ method: 'POST', url: 'url' }, result, 'error');

        PatientRaceService.validateCategory = jest.fn().mockReturnValue(Promise.reject(error));

        const actual = await validateCategory(4, 'allowed-race-category')('existing-race-category');

        expect(actual).toEqual(`The patient has an existing race demographic for "existing-race-category"`);
    });
});
