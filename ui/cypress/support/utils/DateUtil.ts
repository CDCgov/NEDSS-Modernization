export default class DateUtil {
    // MMddyyyy OR MM/dd/yyyy
    static getNBSFormattedDate(date: Date, includeSlashes: boolean): string {
        const month = date.getMonth() + 1;
        let monthString = month < 10 ? '0' + month : month.toString();
        const day = date.getDate();
        let dayString = day < 10 ? '0' + day : day.toString();
        if (includeSlashes) {
            return `${monthString}/${dayString}/${date.getFullYear()}`;
        } else {
            return `${monthString}${dayString}${date.getFullYear()}`;
        }
    }
}
