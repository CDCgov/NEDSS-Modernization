export type NameFormat = 'full' | 'short' | 'fullLastFirst';

export type DisplayableName = {
    type?: string | null;
    first?: string | null;
    middle?: string | null;
    last?: string | null;
    suffix?: string | null;
};
