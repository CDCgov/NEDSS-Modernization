export type TextOperation = 'contains' | 'equals' | 'not' | 'startsWith' | 'soundsLike';
export type AlphaTextOperation = 'contains' | 'equals' | 'not';

/**
 * Represents an operation on a value, used in Operator input fields.
 * Examples: { "startsWith": "Bob" }, { "contains": "john" }
 */
export type TextCriteria = {
    [K in TextOperation]?: string | null | undefined;
};

/**
 * Represents an operation on a value, used in Operator input fields.
 * Examples: { "startsWith": "Bob" }, { "contains": "john" }
 */
export type AlphaTextCriteria = {
    [K in AlphaTextOperation]?: string | null | undefined;
};
