import { isRuleGroupType, isRuleType, RuleGroupTypeAny, RuleType, ValidationResult } from 'react-querybuilder';
import { getRangeValErrorMsg, isDateFormat, validateDateRange, validateNumericRange } from '../utils/rangeValidator.ts';
import { BINARY_OPERATORS } from './operators.ts';

export type ValidationResultMap = Record<string, ValidationResult>;

export const validateRule = (rule: RuleGroupTypeAny | RuleType | string, result: ValidationResultMap) => {
    const setInvalid = (id: string, reason: string) => {
        result[id]['valid'] = false;
        result[id]['reasons'] = [reason];
    };
    if (isRuleType(rule)) {
        const { id, field, operator, value, label, type } = rule;

        if (!id) {
            // no key for the map, shouldn't happen in practice
            console.warn('Advanced query filter rule id is empty.');
            return;
        }

        // default valid
        result[id] = { valid: true };

        // empty rules are fine
        if (!field || field === '~') return;

        // start check for exception
        if (operator === '~') {
            setInvalid(id, getMissingValErrorMsg(label, true));
            return;
        }

        if (operator === 'between') {
            // this shouldn't happen with how we are handling the value in the UI
            if (typeof value !== 'string') {
                setInvalid(id, getRangeValErrorMsg(label, true));
                return;
            }

            if (isEmptyBetweenValue(value)) {
                setInvalid(id, getRangeValErrorMsg(label, false));
                return;
            }

            const parts: string[] = value.split(',');
            let rangeErrorMsg;
            if (type === 'DATETIME') {
                rangeErrorMsg = validateDateRange(parts, label);
            } else {
                rangeErrorMsg = validateNumericRange(parts, label);
            }

            if (rangeErrorMsg) setInvalid(id, rangeErrorMsg);
            return;
        } else if (BINARY_OPERATORS.find((name) => name === operator)) {
            if (typeof value === 'string') {
                if ((value as string).trim() === '') {
                    setInvalid(id, getMissingValErrorMsg(label, false));
                }

                if (type === 'DATETIME') {
                    if (!isDateFormat(value)) setInvalid(id, getMissingValErrorMsg(label, false));
                }
            }
        }
    } else if (isRuleGroupType(rule)) {
        rule.rules.forEach((r) => validateRule(r, result));
    }
};

const getMissingValErrorMsg = (field: string, isOperator: boolean) => {
    const typeMsg = isOperator ? 'logic ' : '';
    return `Enter a ${typeMsg}value for ${field}.`;
};

const isEmptyBetweenValue = (val: string) => {
    const trimmedVal = val.trim();
    return trimmedVal === '' || trimmedVal === ',';
};
