import { PageRuleControllerService, Rule } from 'apps/page-builder/generated';
import { Breadcrumb } from 'breadcrumb';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
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
        const targetIdentifiers = rule?.targets.map((target) => target.targetIdentifier || '') || [];

        if (rule?.targetType == QUESTION) {
            const targetSearch = findTargetQuestion(targetIdentifiers, page);
            setTargetQuestions(targetSearch);
        }

        if (rule?.targetType == SUBSECTION) {
            const targetSearch = findTargetSubsection(targetIdentifiers, page);
            setTargetSubSections(targetSearch);
        }
    }, [rule]);

    return (
        <div className={styles.view}>
            <Breadcrumb start="../">Business rules</Breadcrumb>
            <div className={styles.container}>
                <h2>View business rule</h2>
                <div className={styles.body}>
                    <table cellSpacing={0}>
                        <thead>
                            <th>Function</th>
                            <th>
                                {rule?.ruleFunction === 'DATE_COMPARE'
                                    ? 'Date validation'
                                    : rule?.ruleFunction
                                    ? rule.ruleFunction.charAt(0).toUpperCase() +
                                      rule.ruleFunction.slice(1).replaceAll('_', ' ').toLowerCase()
                                    : ''}
                            </th>
                        </thead>
                        <tr>
                            <td>Rule Id</td>
                            <td>{rule?.id}</td>
                        </tr>
                        <tr>
                            <td>Source question</td>
                            <td>
                                {checkForSemicolon(removeNumericAndSymbols(rule?.sourceQuestion.label))} (
                                {checkForSemicolon(removeNumericAndSymbols(rule?.sourceQuestion.questionIdentifier))})
                            </td>
                        </tr>
                        {rule?.ruleFunction !== 'DATE_COMPARE' ? (
                            <tr>
                                <td>Any source value</td>
                                <td>{rule?.anySourceValue ? 'True' : 'False'}</td>
                            </tr>
                        ) : null}
                        <tr>
                            <td>Logic</td>
                            <td>
                                {rule?.comparator
                                    ? rule.comparator.charAt(0).toUpperCase() +
                                      rule.comparator.slice(1).replaceAll('_', ' ').toLowerCase()
                                    : ''}
                            </td>
                        </tr>
                        {rule?.ruleFunction !== 'DATE_COMPARE' ? (
                            <tr>
                                <td>Source value(s)</td>
                                <td>
                                    {rule?.sourceValues?.map((value, key) => (
                                        <span key={key}>{value}</span>
                                    ))}
                                </td>
                            </tr>
                        ) : null}
                        {rule?.targetType ? (
                            <tr>
                                <td>Target type</td>
                                <td>{rule?.targetType}</td>
                            </tr>
                        ) : null}
                        <tr>
                            <td>Target(s)</td>
                            <td>
                                {rule?.targetType == QUESTION
                                    ? targetQuestions.map((target, key) => (
                                          <span key={key}>
                                              {target.name} ({target.question})
                                          </span>
                                      ))
                                    : null}

                                {rule?.targetType == SUBSECTION
                                    ? targetSubSections.map((target, key) => (
                                          <span key={key}>
                                              {target.name} ({target.questionIdentifier})
                                          </span>
                                      ))
                                    : null}
                            </td>
                        </tr>
                        <tr>
                            <td>Rule description</td>
                            <td>{rule?.description}</td>
                        </tr>
                        {rule?.ruleFunction === Rule.ruleFunction.DATE_COMPARE ? (
                            <tr>
                                <td>Error message</td>
                                <td>{rule?.description}</td>
                            </tr>
                        ) : null}
                    </table>
                </div>
            </div>
        </div>
    );
};
