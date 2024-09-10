type Optional<V, VOptional extends keyof V> = Omit<V, VOptional> & Partial<Pick<V, VOptional>>;

export type { Optional };
