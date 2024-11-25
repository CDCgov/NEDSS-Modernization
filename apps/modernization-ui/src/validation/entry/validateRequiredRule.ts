const validateRequiredRule = (name: string) => ({
    required: { value: true, message: `The ${name} is required.` }
});

export { validateRequiredRule };
