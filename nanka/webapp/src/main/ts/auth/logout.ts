import { setDisplay } from './common';

const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || '';
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || '';

const logoutButton = document.querySelector<HTMLElement>('#buttonLogout');

export function setupLogoutButton () {
  if (!logoutButton) {
    return;
  }
  logoutButton.onclick = async () => {
    const headers = new Headers();
    if (csrfHeader.length > 0) {
      headers.append(csrfHeader, csrfToken);
    }
    const response = await fetch('/logout', {
      method: 'POST',
      headers
    });
    if (response.ok) {
      setDisplay('.unauthenticated', '');
      setDisplay('.authenticated', 'none');
    }
  };
}
