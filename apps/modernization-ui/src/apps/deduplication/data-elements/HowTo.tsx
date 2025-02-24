import { Heading } from 'components/heading';
import styles from './how-to.module.scss';

export const HowTo = () => {
    return (
        <aside className={styles.helpText}>
            <Heading level={2}>How to</Heading>
            <div>
                <Heading level={4}>
                    <strong>Data elements</strong>
                </Heading>
                <p>
                    This table represents all the possible data elements that are available for use as person matching
                    criteria.
                </p>
                <p>
                    Once checked, enter the predetermined odds ratio value for each data element as calculated from
                    previous testing of local data.
                </p>
                <p>The corresponding log odds value used by the algorithm will be calculated and displayed.</p>
                <Heading level={4}>
                    <strong>Threshold</strong>
                </Heading>
                <p>
                    Values above which two strings are said to be “similar enough” that they’re probably the same thing.
                    Values that are less than the threshold will be calculated as 0.
                </p>
            </div>
        </aside>
    );
};
