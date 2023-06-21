const maybeNumber = (value: number | string | null | undefined): number | null => (value ? +value : null);

export { maybeNumber };
