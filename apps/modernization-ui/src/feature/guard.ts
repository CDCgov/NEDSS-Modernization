import { Features } from 'generated';

type Guard = (features: Features) => boolean | undefined;

export type { Guard };
