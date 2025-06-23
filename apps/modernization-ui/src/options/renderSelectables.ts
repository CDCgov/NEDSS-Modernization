import { asName, Selectable } from 'options';
import { maybeMapAll } from 'utils/mapping';

const names = maybeMapAll(asName);

const renderSelectables = (values?: Selectable[]) => names(values).join(', ');

export { renderSelectables };
