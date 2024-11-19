const validateRequiredRule = (name: string) => ({
    required: { value: true, message: `${name}: is required` }
});

export { validateRequiredRule };
