import { maybeMap } from 'utils/mapping';
import { Investigator } from './investigator';
import { exists } from 'utils/exists';

const displayInvestigator = maybeMap((investigator: Investigator) => {
    return [investigator.first, investigator.last].filter(exists).join(' ');
});

export { displayInvestigator };
