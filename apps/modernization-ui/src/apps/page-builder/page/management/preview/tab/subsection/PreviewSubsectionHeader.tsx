import styles from './preview-subsection.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { PagesSubSection } from '../../../../../generated';

type Props = {
    subsection: PagesSubSection;
    isExpanded: boolean;
    onExpandedChange: (isExpanded: boolean) => void;
};

export const PreviewSubsectionHeader = ({ subsection, isExpanded, onExpandedChange }: Props) => {
    return (
        <div className={`${styles.header} ${subsection.isGrouped ? styles.grouped : ''}`}>
            <div className={styles.info}>
                {subsection.isGrouped ? <div className={styles.indicator}>R</div> : null}
                <div>
                    <div className={styles.name}>
                        <h4>{subsection.name}</h4>
                    </div>
                    <div className={styles.count}>
                        {`${subsection.questions?.length} question${subsection.questions?.length > 1 ? 's' : ''}`}
                    </div>
                </div>
            </div>
            <div className={styles.buttons}>
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
