import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { useMemo } from 'react';
import { DataElements } from '../../api/model/DataElement';
import { PersonMatchHeader } from './header/PersonMatchHeader';
import { AlgorithmNotConfigured } from './notification-cards/AlgorithmNotConfigured';
import { PassConfiguration } from './pass-configuration/PassConfiguration';

type Props = {
    loading: boolean;
    dataElements?: DataElements;
    onImportClick: () => void;
};
export const MatchConfiguration = ({ loading, dataElements, onImportClick }: Props) => {
    const dataElementsConfigured = useMemo(() => {
        if (dataElements === undefined) {
            return false;
        }
        // attempt to find an entry that is active
        return Object.values(dataElements).find((d) => d.active) !== undefined;
    }, [dataElements]);

    return (
        <>
            <Shown when={loading || !dataElementsConfigured}>
                <PersonMatchHeader />
            </Shown>
            <Shown when={!loading} fallback={<Loading center />}>
                <Shown
                    when={dataElementsConfigured}
                    fallback={<AlgorithmNotConfigured onImportClick={onImportClick} />}>
                    {dataElements && <PassConfiguration dataElements={dataElements} onImportClick={onImportClick} />}
                </Shown>
            </Shown>
        </>
    );
};
