type ValidationResult = boolean | string;
type Validator<I> = (value: I) => ValidationResult;

export type { ValidationResult, Validator };
