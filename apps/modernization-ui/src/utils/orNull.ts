function orNull<T>(value: T | null | undefined): T | null {
    return (value && value) || null;
}

export { orNull };
