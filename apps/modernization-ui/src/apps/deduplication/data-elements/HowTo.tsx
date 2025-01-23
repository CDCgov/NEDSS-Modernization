import { Heading } from 'components/heading';
import styles from './how-to.module.scss';

export const HowTo = () => {
    return (
        <aside className={styles.helpText}>
            <Heading level={2}>How to</Heading>
            <div>
                <Heading level={4}>Data elements</Heading>
                <p>
                    This table represents all the possible data elements that are available to the patient match
                    algorithm.
                </p>
                <ol>
                    <li>Checking a data element will make it available to be selected during pass configuration.</li>
                    <li>
                        Once checked, the data element requires the M, U and Threshold values to be set before saving.
                    </li>
                </ol>
                <ul>
                    <li>
                        <strong>M - </strong>Probability that a given field is the same in a pair of matching records.
                    </li>
                    <li>
                        <strong>U - </strong>Probability that a given field is the same in a pair of non-matching
                        records
                    </li>
                    <li>
                        <strong>Threshold - </strong>Values above which two strings are said to be “similar enough” that
                        they’re probably the same thing. Values that are less than the threshold will be calculated as
                        0.
                    </li>
                </ul>
                <Heading level={4}>Log odds</Heading>
                <p>
                    A score of the likelihood that something observed in a data set is due to a statistically meaningful
                    relationship or cause, rather than random chance. Calculated based on odds ratio.
                </p>
            </div>
        </aside>
    );
};
