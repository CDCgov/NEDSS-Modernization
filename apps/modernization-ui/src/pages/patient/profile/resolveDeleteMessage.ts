import { DeletabilityResult } from './resolveDeletability';

const HAS_ASSOCIATIONS_MESSAGE = {
    message: 'This patient file has associated event records.',
    detail: 'The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator.'
};

const IS_INACTIVE_MESSAGE = 'This patient file is inactive and cannot be deleted.';

const resolveDeleteMessage = (deletability: DeletabilityResult) => {
    switch (deletability) {
        case DeletabilityResult.Has_Associations: {
            return HAS_ASSOCIATIONS_MESSAGE;
        }
        case DeletabilityResult.Is_Inactive: {
            return IS_INACTIVE_MESSAGE;
        }
        default: {
            return '';
        }
    }
};

export { resolveDeleteMessage };
