import { PageRuleControllerService, PagesQuestion, PagesSubSection, Rule } from 'apps/page-builder/generated';
import { Breadcrumb } from 'breadcrumb';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { checkForSemicolon, removeNumericAndSymbols } from '../helpers/errorMessageUtils';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { findTargetQuestion, findTargetSubsection } from '../helpers/findTargetQuestions';
import styles from './view-business-rule.module.scss';

export const ViewBusinessRule = () => {
    const { ruleId } = useParams();
    const [rule, setRule] = useState<Rule | undefined>(undefined);
    const { page } = useGetPageDetails();
    const [targetQuestions, setTargetQuestions] = useState<PagesQuestion[]>([]);
    const [targetSubSections, setTargetSubSections] = useState<PagesSubSection[]>([]);
    const { QUESTION, SUBSECTION } = Rule.targetType;

    useEffect(() => {
        if (ruleId) {
            PageRuleControllerService.viewRuleResponse({
                ruleId: Number(ruleId)
            }).then((response: Rule) => {
                setRule(response);
            });
        }
    }, [ruleId]);

    useEffect(() => {
        const targetIdentifiers = rule?.targets.map((target) => target.targetIdentifier ?? '') ?? [];

        if (rule?.targetType == QUESTION) {
            const targetSearch = findTargetQuestion(targetIdentifiers, page);
            setTargetQuestions(targetSearch);
        }

        if (rule?.targetType == SUBSECTION) {
            const targetSearch = findTargetSubsection(targetIdentifiers, page);
            setTargetSubSections(targetSearch);
        }
    }, [rule, page?.id]);

    const getRuleFunctionDisplay = () => {
        const ruleFunction = rule?.ruleFunction;
        switch (ruleFunction) {
            case undefined:
                return '';
            case Rule.ruleFunction.DATE_COMPARE:
                return 'Date validation';
            default:
                return ruleFunction.charAt(0).toUpperCase() + ruleFunction.slice(1).replaceAll('_', ' ').toLowerCase();
        }
    };

    return (
        <div className={styles.view}>
            <Breadcrumb start="../">Business rules</Breadcrumb>
            <div className={styles.container}>
                <h2>View business rule</h2>
                <div className={styles.body}>
                    <table cellSpacing={0}>
                        <thead>
                            <tr>
                                <th>Function</th>
                                <th>{getRuleFunctionDisplay()}</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Rule Id</td>
                                <td>{rule?.id}</td>
                            </tr>
                            <tr>
                                <td>Source question</td>
                                <td>
                                    {checkForSemicolon(removeNumericAndSymbols(rule?.sourceQuestion.label))} (
                                    {checkForSemicolon(
                                        removeNumericAndSymbols(rule?.sourceQuestion.questionIdentifier)
                                    )}
                                    )
                                </td>
                            </tr>
                            {rule?.ruleFunction !== 'DATE_COMPARE' && (
                                <tr>
                                    <td>Any source value</td>
                                    <td>{rule?.anySourceValue ? 'True' : 'False'}</td>
                                </tr>
                            )}
                            <tr>
                                <td>Logic</td>
                                <td>
                                    {rule?.comparator
                                        ? rule.comparator.charAt(0).toUpperCase() +
                                          rule.comparator.slice(1).replaceAll('_', ' ').toLowerCase()
                                        : ''}
                                </td>
                            </tr>
                            {rule?.ruleFunction !== 'DATE_COMPARE' && (
                                <tr>
                                    <td>Source value(s)</td>
                                    <td>{rule?.sourceValues?.map((value, key) => <span key={key}>{value}</span>)}</td>
                                </tr>
                            )}
                            {rule?.targetType && (
                                <tr>
                                    <td>Target type</td>
                                    <td>{rule?.targetType}</td>
                                </tr>
                            )}
                            <tr>
                                <td>Target(s)</td>
                                <td>
                                    {rule?.targetType == QUESTION &&
                                        targetQuestions.map((target, key) => (
                                            <span key={key}>
                                                {target.name} ({target.question})
                                            </span>
                                        ))}

                                    {rule?.targetType == SUBSECTION &&
                                        targetSubSections.map((target, key) => (
                                            <span key={key}>
                                                {target.name} ({target.questionIdentifier})
                                            </span>
                                        ))}
                                </td>
                            </tr>
                            <tr>
                                <td>Rule description</td>
                                <td>{rule?.description}</td>
                            </tr>
                            {rule?.ruleFunction === Rule.ruleFunction.DATE_COMPARE && (
                                <tr>
                                    <td>Error message</td>
                                    <td>{rule?.description}</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};
