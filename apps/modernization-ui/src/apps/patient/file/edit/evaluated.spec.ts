import {
    evaluateAdministrative,
    evaluateEthnicity,
    evaluateGeneralInformation,
    evaluateMortality,
    evaluateSexBirth
} from './evaluated';

vi.mock('design-system/date/clock', () => ({
    now: () => '2013-09-17T00:00:00'
}));

describe('when evaluating PatientDemographicEntry', () => {
    it('should initialize administrative when not present', () => {
        const actual = evaluateAdministrative(undefined);

        expect(actual).toEqual(
            expect.objectContaining({ administrative: expect.objectContaining({ asOf: '09/17/2013' }) })
        );
    });

    it('should use values from administrative', () => {
        const actual = evaluateAdministrative({ asOf: '11/19/2023' });

        expect(actual).toEqual(
            expect.objectContaining({ administrative: expect.objectContaining({ asOf: '11/19/2023' }) })
        );
    });

    it('should initialize ethnicity when not present', () => {
        const actual = evaluateEthnicity(undefined);

        expect(actual).toEqual(expect.objectContaining({ ethnicity: expect.objectContaining({ asOf: '09/17/2013' }) }));
    });

    it('should use values from ethnicity', () => {
        const actual = evaluateEthnicity({ asOf: '11/19/2023' });

        expect(actual).toEqual(expect.objectContaining({ ethnicity: expect.objectContaining({ asOf: '11/19/2023' }) }));
    });

    it('should initialize sex and birth when not present', () => {
        const actual = evaluateSexBirth(undefined);

        expect(actual).toEqual(expect.objectContaining({ sexBirth: expect.objectContaining({ asOf: '09/17/2013' }) }));
    });

    it('should use values from sex and birth', () => {
        const actual = evaluateSexBirth({ asOf: '11/19/2023' });

        expect(actual).toEqual(expect.objectContaining({ sexBirth: expect.objectContaining({ asOf: '11/19/2023' }) }));
    });

    it('should initialize mortality when not present', () => {
        const actual = evaluateMortality(undefined);

        expect(actual).toEqual(expect.objectContaining({ mortality: expect.objectContaining({ asOf: '09/17/2013' }) }));
    });

    it('should use values from mortality', () => {
        const actual = evaluateMortality({ asOf: '11/19/2023' });

        expect(actual).toEqual(expect.objectContaining({ mortality: expect.objectContaining({ asOf: '11/19/2023' }) }));
    });

    it('should initialize general when not present', () => {
        const actual = evaluateGeneralInformation(undefined);

        expect(actual).toEqual(expect.objectContaining({ general: expect.objectContaining({ asOf: '09/17/2013' }) }));
    });

    it('should use values from general', () => {
        const actual = evaluateGeneralInformation({ asOf: '11/19/2023' });

        expect(actual).toEqual(expect.objectContaining({ general: expect.objectContaining({ asOf: '11/19/2023' }) }));
    });
});
