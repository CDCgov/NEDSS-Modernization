import { DateBetweenCriteria, DateEqualsCriteria } from 'design-system/date/entry';
import { Term } from './terms';

const fromDateEqualsCriteria =
    (source: string, title: string) =>
    (criteria: DateEqualsCriteria): Term => ({
        source,
        title,
        name: `${criteria.equals?.month || '--'}/${criteria.equals?.day || '--'}/${criteria.equals?.year || '----'}`,
        value: `${criteria.equals?.month || '--'}/${criteria.equals?.day || '--'}/${criteria.equals?.year || '----'}`,
        operator: 'Equal'
    });

const fromDateBetweenCriteria =
    (source: string, title: string) =>
    (criteria: DateBetweenCriteria): Term => ({
        source,
        title,
        name: `${criteria.between.from}${(criteria.between.to && '-' + criteria.between.to) ?? ''}`,
        value: `${criteria.between.from}${(criteria.between.to && '-' + criteria.between.to) ?? ''}`,
        operator: 'Between'
    });

export { fromDateEqualsCriteria, fromDateBetweenCriteria };
