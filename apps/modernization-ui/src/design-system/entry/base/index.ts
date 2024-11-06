import { Orientation, Sizing, EntryWrapper } from 'components/Entry';

type EntryComponentProps = {
    orientation?: Orientation;
    sizing?: Sizing;
    label: string;
    error?: string;
    required?: boolean;
};

export { EntryWrapper };

export type { EntryComponentProps, Orientation, Sizing };
