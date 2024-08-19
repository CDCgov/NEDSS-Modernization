import { ButtonGroup, Icon } from '@trussworks/react-uswds';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { Icon as IconComponent } from 'components/Icon/Icon';
import classNames from 'classnames';
import { Button } from 'components/button';

import styles from './toggle-view.module.scss';

export const ToggleView = () => {
    const { view, asList, asTable } = useSearchResultDisplay();

    return (
        <div className={classNames(styles.toggle)}>
            <strong>View as: </strong>
            <ButtonGroup type="segmented">
                <Button
                    labelPosition="right"
                    className={view === 'table' ? styles.active : ''}
                    icon={view === 'table' ? <Icon.Check /> : <IconComponent name="table" />}
                    onClick={asTable}
                    type="button"
                    outline={view !== 'table'}>
                    Table
                </Button>
                <Button
                    labelPosition="right"
                    className={view === 'list' ? styles.active : ''}
                    icon={view === 'list' ? <Icon.Check /> : <Icon.List />}
                    onClick={asList}
                    outline={view !== 'list'}
                    type="button">
                    List
                </Button>
            </ButtonGroup>
        </div>
    );
};
