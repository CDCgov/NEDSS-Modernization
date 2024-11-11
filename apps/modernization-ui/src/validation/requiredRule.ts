const requiredRule = (name: string) => ({
    required: { value: true, message: `${name} is required.` }
});

export { requiredRule };
