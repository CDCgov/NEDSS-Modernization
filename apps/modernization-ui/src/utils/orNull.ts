const orNull = (value: string | undefined | null): string | null => (value && value) || null;

export { orNull };
