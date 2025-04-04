import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { Pass } from './Pass';

export type AlgorithmExport = {
    dataElements: DataElements;
    algorithm: { passes: Pass[] };
};
