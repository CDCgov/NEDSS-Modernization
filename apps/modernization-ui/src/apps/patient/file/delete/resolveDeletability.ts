enum DeletabilityResult {
    Deletable,
    Has_Associations,
    Is_Inactive,
    Indeterminable
}

type Input = {
    deletable: boolean;
    status: string;
};

const resolveDeletability = (profile: Input | undefined): DeletabilityResult =>
    profile ? determineDeletability(profile) : DeletabilityResult.Indeterminable;

const determineDeletability = (profile: Input): DeletabilityResult =>
    profile.deletable ? DeletabilityResult.Deletable : determineBasedOnStatus(profile);

//  the reason for deletion is determined by the status
const determineBasedOnStatus = (profile: Input): DeletabilityResult =>
    profile.status === 'ACTIVE' ? DeletabilityResult.Has_Associations : DeletabilityResult.Is_Inactive;

export { DeletabilityResult, resolveDeletability };
