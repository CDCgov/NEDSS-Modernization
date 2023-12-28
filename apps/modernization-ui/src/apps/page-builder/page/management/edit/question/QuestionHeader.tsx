import styles from './question-header.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { ToggleButton } from 'apps/page-builder/components/ToggleButton';
import { Heading } from 'components/heading';
type Props = {
    questionLabel: string;
    isStandard: boolean;
    isRequired: boolean;
    onRequiredChange?: () => void;
    onEditQuestion?: () => void;
    onDeleteQuestion?: () => void;
};
export const QuestionHeader = ({
    isStandard,
    isRequired,
    questionLabel,
    onRequiredChange,
    onEditQuestion,
    onDeleteQuestion
}: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.typeDisplay}>
                {isStandard && <div className={styles.standardIndicator}>S</div>}
                <Heading level={3}>{questionLabel}</Heading>
            </div>
            <div className={styles.questionButtons}>
                <Icon.Edit onClick={onEditQuestion} />
                <Icon.Delete onClick={onDeleteQuestion} />
                <div className={styles.divider}>|</div>
                <div className={styles.requiredToggle}>Required</div>
                <ToggleButton defaultChecked={isRequired} onChange={onRequiredChange} />
            </div>
        </div>
    );
};
