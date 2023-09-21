function orNull<T>(value: T | null | undefined): T | null {
    return value || value === 0 ? value : null;
}

export { orNull };
