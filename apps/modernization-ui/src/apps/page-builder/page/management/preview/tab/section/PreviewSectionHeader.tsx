import styles from './preview-section.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { PagesSection } from '../../../../../generated';
import { Heading } from '../../../../../../../components/heading';
import classNames from 'classnames';

type Props = {
    section: PagesSection;
    isExpanded: boolean;
    onExpandedChange: (isExpanded: boolean) => void;
};

export const PreviewSectionHeader = ({ section, isExpanded, onExpandedChange }: Props) => {
    return (
        <div className={classNames(styles.header, { [styles.expanded]: isExpanded })}>
            <header>
                <Heading level={2}>{section.name}</Heading>
                <p>{section.subSections.length} sub sections</p>
            </header>
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
