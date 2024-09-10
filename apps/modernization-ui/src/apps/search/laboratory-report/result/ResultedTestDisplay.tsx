import { LabTestSummary } from 'generated/graphql/schema';
import { ReactNode } from 'react';
import styles from './resulted-test.module.scss';
type Props = {
    test: LabTestSummary;
};
export const ResultedTestDisplay = ({ test }: Props) => {
    const numericResult = (): ReactNode => {
        if (test.numeric) {
            return (
                <div className={styles.testResult}>
                    Numeric result: <span>{`${test.numeric} ${test.unit ? test.unit : ''}`}</span>
                </div>
            );
        } else {
            return <></>;
        }
    };

    const referenceRange = (): ReactNode => {
        if (!test.high && test.high !== '0' && !test.low && test.low !== '0') {
            return <></>;
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
                Reference range: <span>{display}</span>
            </div>
        );
    };

    const codedResult = (): ReactNode => {
        if (test.coded) {
            return (
                <div className={styles.testResult}>
                    Coded result: <span>{test.coded}</span>
                </div>
            );
        } else {
            return <></>;
        }
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
