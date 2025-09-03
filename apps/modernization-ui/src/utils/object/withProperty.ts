const withProperty =
    <T extends object, V>(id: keyof T, value?: V | null) =>
    (current?: T) => ({ ...current, [id]: value });

export { withProperty };
