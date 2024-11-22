const fromValue = (source: string, title: string) => (value: string, operator?: string) => ({
    source,
    title,
    name: value,
    value,
    operator
});

export { fromValue };
