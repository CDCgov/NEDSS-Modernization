import { DeletabilityResult, resolveDeletability } from './resolveDeletability';

describe('when a profile is not present', () => {
    it('should resolve Indeterminable', () => {
        const actual = resolveDeletability(undefined);

        expect(actual).toBe(DeletabilityResult.Indeterminable);
    });
});

describe('when a profile is present', () => {
    it('should resolve Deletable when allowed by the profile', () => {
        const profile = {
            deletable: true,
            status: 'ACTIVE'
        };

        const actual = resolveDeletability(profile);

        expect(actual).toBe(DeletabilityResult.Deletable);
    });

    it('should resolve Has_Associations when the profile is not deletable and the status is ACTIVE', () => {
        const profile = {
            deletable: false,
            status: 'ACTIVE'
        };

        const actual = resolveDeletability(profile);

        expect(actual).toBe(DeletabilityResult.Has_Associations);
    });

    it('should resolve Is_Inactive when the profile is not deletable and the status is not ACTIVE', () => {
        const profile = {
            deletable: false,
            status: 'INACTIVE'
        };

        const actual = resolveDeletability(profile);

        expect(actual).toBe(DeletabilityResult.Is_Inactive);
    });
});
