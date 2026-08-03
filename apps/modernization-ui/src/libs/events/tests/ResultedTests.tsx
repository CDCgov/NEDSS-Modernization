import { Shown } from 'conditional-render';
import { LabeledValue } from 'design-system/value';
import { exists } from 'utils/exists';

import styles from './resulted-tests.module.scss';
import { ResultedTest } from './tests';


type ResultedTestsProps = {
    children?: ResultedTest[];
};

const ResultedTests = ({ children }: ResultedTestsProps) => (
    <Shown when={exists(children)}>
        <span className={styles.tests}>
            {children?.map((test, index) => (
                <span className={styles.test} key={index}>
                    <strong>{test.name}</strong>
                    {test.result}
                    {test.reference && <LabeledValue label="Reference Range:">{test.reference}</LabeledValue>}
                </span>
            ))}
        </span>
    </Shown>
);

export { ResultedTests };
