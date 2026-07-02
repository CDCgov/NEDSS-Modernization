import { redirectToNBS6 } from './redirectToNBS6';

const locationMock = {
    href: '',
} as Location;

let originalWindow: Location;
beforeEach((): void => {
    locationMock.href = '';
    originalWindow = window.location;
    (window as any).location = locationMock;
});

afterEach((): void => {
    (window as any).location = originalWindow;
});

describe('redirectToNBS6', () => {
    it('should update the window.location.href with a valid pathname', () => {
        redirectToNBS6('/nbs/ManageReports.do');
        expect(window.location.href).toBe('/nbs/ManageReports.do');
    });
});
