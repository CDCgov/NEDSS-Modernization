import { ButtonGroup, Icon } from '@trussworks/react-uswds';
import styles from '../search-results-options.module.scss';
import classNames from 'classnames';
import { Button } from 'components/button';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';

export const ToggleView = () => {
    const { view, asList, asTable } = useSearchResultDisplay();

    return (
        <div className={classNames(styles.options)}>
            <strong>View as: </strong>
            <ButtonGroup type="segmented">
                <Button
                    className={classNames(styles.toggleButton)}
                    labelPosition="right"
                    icon={view === 'table' ? <Icon.CheckCircle /> : <Icon.GridView />}
                    onClick={asTable}
                    type="button"
                    outline={view !== 'table'}>
                    Table
                </Button>
                <Button
                    className={classNames(styles.toggleButton)}
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
