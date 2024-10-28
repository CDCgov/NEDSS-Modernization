export type Operation = 'contains' | 'equals' | 'not' | 'startsWith' | 'soundsLike';

/**
 * Represents an operation on a value, used in Operator input fields.
 * Examples: { "startsWith": "Bob" }, { "contains": "john" }
 */
export type OperationValue = {
    [K in Operation]?: string | null | undefined;
};
