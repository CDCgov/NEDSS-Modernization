import { Heading } from 'components/heading';
import styles from './how-to.module.scss';

export const HowTo = () => {
    return (
        <aside className={styles.helpText}>
            <Heading level={2}>How to</Heading>
            <div>
                <Heading level={4}>Data elements</Heading>
                <p>
                    This table represents all the possible data elements that are available for use as person matching
                    criteria.
                </p>
                <ol>
                    <li>Checking a data element will make it available to be selected during pass configuration.</li>
                    <li>
                        Once checked, enter the predetermined odds ratio value for each data element as calculated from
                        previous testing of local data.
                    </li>
                </ol>
                <p>The corresponding log odds value used by the algorithm will be calculated and displayed.</p>
                <Heading level={4}>Threshold</Heading>
                <p>
                    Values above which two strings are said to be “similar enough” that they’re probably the same thing.
                    Values that are less than the threshold will be calculated as 0.
                </p>
            </div>
        </aside>
    );
};
