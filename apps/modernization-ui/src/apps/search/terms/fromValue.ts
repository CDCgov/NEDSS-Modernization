const fromValue = (source: string, title: string) => (value: string) => ({
    source,
    title,
    name: value,
    value
});

export { fromValue };
