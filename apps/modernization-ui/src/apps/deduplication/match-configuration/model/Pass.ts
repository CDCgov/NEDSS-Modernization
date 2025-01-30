import { BlockingCriteria } from './Blocking';
import { MatchingCriteria } from './Matching';

export type MatchingConfiguration = {
    passes: Pass[];
};

export type Pass = {
    name: string;
    description: string;
    active: boolean;
    blockingCriteria: BlockingCriteria[];
    matchingCriteria: MatchingCriteria[];
    lowerBound: number;
    upperBound: number;
};
