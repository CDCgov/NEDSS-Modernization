import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { PageRuleControllerService } from 'apps/page-builder/generated';
import { Rule } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { Breadcrumb } from 'breadcrumb';
import styles from './view-business-rule.module.scss';
import { mapLogicForDateCompare } from '../helpers/mapLogicForDateCompare';

export const ViewBusinessRule = () => {
    const { ruleId } = useParams();
    const [rule, setRule] = useState<Rule | undefined>(undefined);

    useEffect(() => {
        if (ruleId) {
            PageRuleControllerService.viewRuleResponseUsingGet({
                authorization: authorization(),
                ruleId: Number(ruleId)
            }).then((response: Rule) => {
                setRule(response);
            });
        }
    }, [ruleId]);

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
                                {rule?.sourceQuestion.label} ({rule?.sourceQuestion.questionIdentifier})
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
                                {rule?.targets.map((target, key) => (
                                    <span key={key}>
                                        {target.label} ({target.targetIdentifier})
                                    </span>
                                ))}
                            </td>
                        </tr>
                        <tr>
                            <td>Rule description</td>
                            <td>{rule?.description}</td>
                        </tr>
                        <tr>
                            <td>Error message</td>
                            <td>
                                {rule?.targets.map((target, i) => (
                                    <span key={i} className={styles.full}>
                                        ${rule?.sourceQuestion.label} (${rule?.sourceQuestion.questionIdentifier})' must
                                        be ${mapLogicForDateCompare(rule!.comparator)} '${target.label} ($
                                        {target.targetIdentifier})
                                    </span>
                                ))}
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    );
};
