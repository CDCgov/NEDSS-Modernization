const withProperty =
    <T extends object, V>(id: keyof T, value?: V) =>
    (current?: T) => ({ ...current, [id]: value });

export { withProperty };
