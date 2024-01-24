import { PagesSubSection } from 'apps/page-builder/generated';
import styles from './managesubsectiontile.module.scss';
import { Icon as NbsIcon } from 'components/Icon/Icon';
import { Icon, Button } from '@trussworks/react-uswds';

type ManageSubsectionTileProps = {
    subsection: PagesSubSection;
    setOnAction?: (action: boolean) => void;
    action: boolean;
};

export const ManageSubsectionTile = ({ subsection, setOnAction, action }: ManageSubsectionTileProps) => {
    return (
        <div className={styles.manageSubsectionTile}>
            <div className={styles.handle}>
                <NbsIcon name="drag" size="3" />
            </div>
            <div className={styles.label}>
                <NbsIcon name="group" size="3" />
                <span data-testid="manageSectionTileId">{`${subsection.name}(${subsection.questions.length})`}</span>
            </div>

            <div className={styles.buttons}>
                <Button
                    type="button"
                    onClick={() => {
                        setOnAction?.(true);
                        console.log('edit');
                    }}
                    outline
                    disabled={action}
                    className={styles.iconBtn}>
                    <Icon.Edit style={{ cursor: 'pointer' }} size={3} />
                </Button>
                <Button
                    type="button"
                    className={styles.iconBtn}
                    outline
                    disabled={action}
                    onClick={() => {
                        setOnAction?.(true);
                        console.log('delete');
                    }}>
                    <Icon.Delete style={{ cursor: 'pointer' }} size={3} />
                </Button>
                <Button
                    type="button"
                    outline
                    disabled={action}
                    className={styles.iconBtn}
                    onClick={() => {
                        setOnAction?.(true);
                        console.log('visbility');
                    }}>
                    <Icon.Visibility style={{ cursor: 'pointer' }} size={3} />
                </Button>
            </div>
        </div>
    );
};
