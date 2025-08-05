export { initial, labels } from './mortality';
export type { MortalityDemographic, HasMortalityDemographic } from './mortality';

export { MortalityDemographicCard } from './view';
export type { MortalityDemographicCardProps } from './view';

export { EditMortalityDemographicCard } from './edit/EditMortalityDemographicCard';
export { useMortalityOptions } from './edit/useMortalityOptions';
export type { MoralityOptions } from './edit/useMortalityOptions';

export type { MortalityDemographicRequest } from './request/mortalityRequest';
export { asMortality } from './request/asMortality';
