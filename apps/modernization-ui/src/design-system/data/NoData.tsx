const displayNoData = () => '---';

const NoData = displayNoData;

const orNoData = (value?: string | null) => (value ? value : displayNoData());

export { NoData, displayNoData, orNoData };
