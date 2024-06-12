import { Button, Icon } from '@trussworks/react-uswds';
import styles from './section.module.scss';
import classNames from 'classnames';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';
import { useState } from 'react';

type Props = {
    name: string;
    subsectionCount: number;
    onAddSubsection: () => void;
    onExpandedChange: (expanded: boolean) => void;
    handleEditSection: () => void;
    handleDeleteSection: () => void;
    handleManageSubsection: () => void;
    isExpanded: boolean;
};
export const SectionHeader = ({
    name,
    subsectionCount,
    onAddSubsection,
    onExpandedChange,
    handleEditSection,
    handleDeleteSection,
    handleManageSubsection,
    isExpanded
}: Props) => {
    const [close, setClose] = useState(false);
    const handleDeleteSectionClick = () => {
        setClose(true);
        handleDeleteSection();
    };
    return (
        <div className={classNames(styles.header, { [styles.expanded]: isExpanded })}>
            <div className={styles.info}>
                <div className={styles.name}>
                    <h3>{name}</h3>
                </div>
                <div className={styles.subsectionCount}>
                    {subsectionCount} subsection{subsectionCount > 1 ? 's' : ''}
                </div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" onClick={onAddSubsection} outline data-testid="addNewSubsection">
                    Add subsection
                </Button>
                <Button
                    type="button"
                    onClick={handleManageSubsection}
                    outline
                    data-testid={`manageSubsectionGearIcon-${subsectionCount ? 'yes' : 'no'}`}
                    className={styles.settingBtn}>
                    <Icon.Settings size={3} />
                </Button>
                <MoreOptions
                    header={<Icon.MoreVert size={4} onClick={() => setClose(false)} />}
                    close={close}
                    className={`moreOptionsSection-${subsectionCount ? 'yes' : 'no'}`}>
                    <Button type="button" onClick={handleEditSection}>
                        <Icon.Edit size={3} /> Edit section
                    </Button>

                    <Button type="button" onClick={handleDeleteSectionClick} className="deleteSectionBtn">
                        <Icon.Delete size={3} /> Delete section
                    </Button>
                </MoreOptions>
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
