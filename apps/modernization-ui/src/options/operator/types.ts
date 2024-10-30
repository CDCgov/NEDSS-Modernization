export type TextOperation = 'contains' | 'equals' | 'not' | 'startsWith' | 'soundsLike';

/**
 * Represents an operation on a value, used in Operator input fields.
 * Examples: { "startsWith": "Bob" }, { "contains": "john" }
 */
export type TextCriteria = {
    [K in TextOperation]?: string | null | undefined;
};
