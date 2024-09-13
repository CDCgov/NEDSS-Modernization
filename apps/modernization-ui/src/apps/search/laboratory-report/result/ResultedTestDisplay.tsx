import { LabTestSummary } from 'generated/graphql/schema';
import { ReactNode } from 'react';
import styles from './resulted-test.module.scss';
type Props = {
    test: LabTestSummary;
};
export const ResultedTestDisplay = ({ test }: Props) => {
    const numericResult = (): ReactNode | undefined => {
        return (
            test.numeric && <div className={styles.testResult}>{`${test.numeric} ${test.unit ? test.unit : ''}`}</div>
        );
    };

    const referenceRange = (): ReactNode | undefined => {
        if (!test.high && test.high !== '0' && !test.low && test.low !== '0') {
            return undefined;
        }
        let display: string = '';
        const status = test.status ? ` - (${test.status})` : '';
        if ((test.high || test.high === '0') && (test.low || test.low === '0')) {
            display = `(${test.low} - ${test.high})${status}`;
        } else if (test.high || test.high === '0') {
            display = `(${test.high})${status}`;
        } else if (test.low || test.low === '0') {
            display = `(${test.low})${status}`;
        }

        return (
            <div className={styles.testResult}>
                <b>Reference range:</b> <span>{display}</span>
            </div>
        );
    };

    const codedResult = (): ReactNode | undefined => {
        return test.coded && <div className={styles.testResult}>{test.coded}</div>;
    };

    return (
        <>
            <div className={styles.testName}>{test.name}:</div>
            {numericResult()}
            {referenceRange()}
            {codedResult()}
        </>
    );
};
