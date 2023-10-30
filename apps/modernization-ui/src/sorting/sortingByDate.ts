type PointInTime = { asOf: string };

export const sortingByDate = (data: PointInTime[]) => {
    return data?.slice().sort((a, b) => {
        const dateA: any = new Date(a?.asOf);
        const dateB: any = new Date(b?.asOf);
        return dateB.getTime() - dateA.getTime();
    });
};
