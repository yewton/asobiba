import {setDisplay} from "./common";

const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || '';
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || '';

const logoutButton = document.getElementById('button-logout');

export function setupLogoutButton() {
    if (!logoutButton) {
        return;
    }
    logoutButton.onclick = async () => {
        const headers = new Headers();
        if (0 < csrfHeader.length) {
            headers.append(csrfHeader, csrfToken);
        }
        const response = await fetch('/logout', {
            method: 'POST',
            headers: headers
        });
        if (response.ok) {
            setDisplay('.unauthenticated', '');
            setDisplay('.authenticated', 'none');
        }
    };
}
