const fromValue = (source: string, title: string) => (value: string, operator?: string, partial?: boolean) => ({
    source,
    title,
    name: value,
    value,
    operator,
    partial
});

export { fromValue };
