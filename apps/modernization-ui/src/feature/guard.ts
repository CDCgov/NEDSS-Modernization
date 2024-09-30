import { Features } from 'configuration';

type Guard = (features: Features) => boolean | undefined;

export type { Guard };
