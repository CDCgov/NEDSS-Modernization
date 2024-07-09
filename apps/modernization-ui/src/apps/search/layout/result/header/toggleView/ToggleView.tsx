import { ButtonGroup, Icon } from '@trussworks/react-uswds';
import { View } from 'apps/search';
import styles from '../search-results-header.module.scss';
import classNames from 'classnames';
import { Button } from 'components/button';

type ToggleViewProps = {
    view: View;
    setView: (view: View) => void;
};

export const ToggleView = ({ view, setView }: ToggleViewProps) => {
    return (
        <div className={classNames(styles.flexContainer)}>
            <strong>View as: </strong>
            <ButtonGroup type="segmented">
                <Button
                    className={classNames(styles.toggleButton)}
                    labelPosition="right"
                    icon={<Icon.GridView />}
                    onClick={() => setView('table')}
                    type="button"
                    outline={view !== 'table'}>
                    Table
                </Button>
                <Button
                    className={classNames(styles.toggleButton)}
                    labelPosition="right"
                    icon={<Icon.CheckCircle />}
                    onClick={() => setView('list')}
                    outline={view !== 'list'}
                    type="button">
                    List
                </Button>
            </ButtonGroup>
        </div>
    );
};
