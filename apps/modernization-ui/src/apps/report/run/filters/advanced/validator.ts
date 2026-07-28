import { isRuleGroupType, ValidationResult } from 'react-querybuilder';
import { getRangeValErrorMsg, isDateFormat, validateDateRange, validateNumericRange } from '../utils/rangeValidator.ts';
import { BINARY_OPERATORS } from './operators.ts';
import { logErrorToUserConsole } from 'utils/logging.ts';
import { isQbRuleGroupType, isQbRuleType, QbQuery } from './AdvancedFilter.tsx';

export type ValidationResultMap = Record<string, ValidationResult>;

export const validateRule = (rule: QbQuery, result: ValidationResultMap) => {
    const setInvalid = (id: string, reason: string) => {
        result[id] = { valid: false, reasons: [reason] };
    };
    if (isQbRuleType(rule)) {
        const { id, field, operator, value, type } = rule;
        const label = rule.label!; // this will be there, but need to convince ts

        if (!id) {
            // no key for the map, shouldn't happen in practice
            logErrorToUserConsole('Advanced query filter rule id is empty.');
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
        } else if (BINARY_OPERATORS.includes(operator)) {
            if (typeof value === 'string') {
                if ((value as string).trim() === '') {
                    setInvalid(id, getMissingValErrorMsg(label, false));
                    return;
                }

                if (type === 'DATETIME') {
                    if (!isDateFormat(value)) setInvalid(id, getInvalidDateErrorMsg(label, value));
                }
            } else if (Array.isArray(value)) {
                if (value.length === 0) {
                    setInvalid(id, getMissingValErrorMsg(label, false));
                }
            }
        }
    } else if (isQbRuleGroupType(rule)) {
        rule.rules.forEach((r) => validateRule(r, result));

        if (!rule.id) {
            // no key for the map, shouldn't happen in practice
            logErrorToUserConsole('Advanced query filter rule group id is empty.');
            return;
        }

        // catch when rule group is empty or contain only another rule group
        const isInvalidRuleGroup =
            rule.rules.length === 0 || (rule.rules.length === 1 && isRuleGroupType(rule.rules[0]));

        if (isInvalidRuleGroup) {
            setInvalid(rule.id, 'Remove or add rules to the empty rule group.');
        }
    }
};

const getInvalidDateErrorMsg = (field: string, value: string) => {
    return `Date of "${value}" is not a valid mm/dd/yyyy formatted date for ${field}.`;
};

const getMissingValErrorMsg = (field: string, isOperator: boolean) => {
    const typeMsg = isOperator ? 'logic ' : '';
    return `Enter a ${typeMsg}value for ${field}.`;
};

const isEmptyBetweenValue = (val: string) => {
    const trimmedVal = val.trim();
    return trimmedVal === '' || trimmedVal === ',';
};
