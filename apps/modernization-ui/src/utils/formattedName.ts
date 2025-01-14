import { exists } from 'utils';

const formattedName = (lastNm: string | undefined | null, firstNm: string | undefined | null) => {
    return [lastNm, firstNm].filter(exists).join(', ') || '--';
};

export { formattedName };
