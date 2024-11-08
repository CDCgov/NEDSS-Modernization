import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import styles from './data-elements.module.scss';

export const DataElementConfig = () => {
    return (
        <div className={styles.dataElements}>
            <div className={styles.heading}>
                <Icon name="arrow_back" size="large" />
                <Heading level={1}>Data elements configuration</Heading>
            </div>
            <div className={styles.content}>
                <main>
                    <section className={styles.globalSettings}>Global settings</section>
                    <section className={styles.dataElements}>Data elements</section>
                </main>
                <aside>
                    <Heading level={2}>How to</Heading>
                    <div>
                        <Heading level={4}>Belongingness ratio</Heading>
                        <p>
                            The belongingness ratio of a new record to an existing cluster of data is the percentage of
                            records already belonging to that cluster that the new record is a match with.
                        </p>
                        <br />
                        <Heading level={4}>Data elements</Heading>
                        <p>
                            This table represents all the possible data elements that are available to the patient match
                            algorithm.
                        </p>
                        <ol>
                            <li>
                                Checking a data element will make it available to be selected during pass configuration.
                            </li>
                            <li>
                                Once checked, the data element requires the M, U and Threshold values to be set before
                                saving.
                            </li>
                        </ol>
                        <ul>
                            <li>
                                <strong>M - </strong>Probability that a given field is the same in a pair of matching
                                records.
                            </li>
                            <li>
                                <strong>U - </strong>Probability that a given field is the same in a pair of
                                non-matching records
                            </li>
                            <li>
                                <strong>Threshold - </strong>Values above which two strings are said to be “similar
                                enough” that they’re probably the same thing. Values that are less than the threshold
                                will be calculated as 0.
                            </li>
                        </ul>
                        <Heading level={4}>Log odds</Heading>
                        <p>
                            A score of the likelihood that something observed in a data set is due to a statistically
                            meaningful relationship or cause, rather than random chance. Calculated based on odds ratio.
                        </p>
                    </div>
                </aside>
            </div>
        </div>
    );
};
