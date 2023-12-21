import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { Heading } from 'components/heading';
import styles from './question-content.module.scss';

type Props = {
    name: string;
    type: string;
    displayComponent?: number;
};
export const QuestionContent = ({ name, type, displayComponent }: Props) => {
    return (
        <div className={styles.question}>
            <AlertBanner type="warning">Work in progress</AlertBanner>
            <Heading level={2}>{name}</Heading>
            <div>Type: {type}</div>
            <div>Display component: {displayComponent}</div>
        </div>
    );
};
