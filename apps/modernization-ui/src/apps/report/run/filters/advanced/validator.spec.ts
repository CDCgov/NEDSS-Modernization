import { validateRule, ValidationResultMap } from './validator';
import { RuleGroupType, RuleType } from 'react-querybuilder';

describe('validateRule', () => {
    let mockResult: ValidationResultMap;

    beforeEach(() => {
        mockResult = {
            'rule-1': { valid: false, reasons: [] },
            'rule-2': { valid: false, reasons: [] },
        } as ValidationResultMap;
    });

    it('should be valid for an empty rule', () => {
        validateRule(
            {
                id: 'rule-1',
                field: '~',
                operator: '~',
                value: '',
            } as RuleType,
            mockResult
        );

        expect(mockResult['rule-1']['valid']).toBe(true);
    });

    it('should be valid for an empty ruleGroup and rule', () => {
        validateRule(
            {
                id: 'ruleGroup-1',
                rules: [
                    {
                        id: 'rule-1',
                        field: '~',
                        operator: '~',
                        value: '',
                    },
                ],
                combinator: 'and',
                not: false,
            } as RuleGroupType,
            mockResult
        );

        expect(mockResult['rule-1']['valid']).toBe(true);
    });

    it('should be invalid when no operator is selected', () => {
        validateRule(
            {
                id: 'ruleGroup-1',
                rules: [
                    {
                        id: 'rule-1',
                        field: 'public_health_case_uid',
                        operator: '~',
                        value: '',
                    },
                ],
                combinator: 'and',
                not: false,
            } as RuleGroupType,
            mockResult
        );

        expect(mockResult['rule-1']['valid']).toBe(false);
        expect(mockResult['rule-1']['reasons']).toContain('Enter a logic value for public_health_case_uid.');
    });

    it('should be invalid when the between value is not a string', () => {
        validateRule(
            {
                id: 'ruleGroup-1',
                rules: [
                    {
                        id: 'rule-1',
                        field: 'public_health_case_uid',
                        operator: 'between',
                        value: 10,
                    },
                ],
                combinator: 'and',
                not: false,
            } as RuleGroupType,
            mockResult
        );

        expect(mockResult['rule-1']['valid']).toBe(false);
        expect(mockResult['rule-1']['reasons']).toContain('Enter valid from and to values for public_health_case_uid.');
    });

    it('should be invalid when the between value is empty', () => {
        validateRule(
            {
                id: 'ruleGroup-1',
                rules: [
                    {
                        id: 'rule-1',
                        field: 'public_health_case_uid',
                        operator: 'between',
                        value: '',
                    },
                    {
                        id: 'rule-2',
                        field: 'public_health_case_uid',
                        operator: 'between',
                        value: ' , ',
                    },
                ],
                combinator: 'and',
                not: false,
            } as RuleGroupType,
            mockResult
        );

        expect(mockResult['rule-1']['valid']).toBe(false);
        expect(mockResult['rule-1']['reasons']).toContain('Enter from and to values for public_health_case_uid.');
        expect(mockResult['rule-2']['valid']).toBe(false);
        expect(mockResult['rule-2']['reasons']).toContain('Enter from and to values for public_health_case_uid.');
    });
});
