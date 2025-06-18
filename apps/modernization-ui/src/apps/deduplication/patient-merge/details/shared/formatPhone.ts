export const formatPhone = (phone?: string) => {
    if (phone == undefined) {
        return '---';
    }
    if (phone.length !== 10) {
        return phone;
    }

    return `${phone.substring(0, 3)}-${phone.substring(3, 6)}-${phone.substring(6)}`;
};
