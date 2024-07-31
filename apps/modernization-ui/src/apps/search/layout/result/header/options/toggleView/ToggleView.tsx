import { ButtonGroup, Icon } from '@trussworks/react-uswds';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
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
                    icon={view === 'table' ? <Icon.CheckCircle /> : <Icon.GridView />}
                    onClick={asTable}
                    type="button"
                    outline={view !== 'table'}>
                    Table
                </Button>
                <Button
                    labelPosition="right"
                    icon={view === 'list' ? <Icon.CheckCircle /> : <Icon.List />}
                    onClick={asList}
                    outline={view !== 'list'}
                    type="button">
                    List
                </Button>
            </ButtonGroup>
        </div>
    );
};
