import 'vite/modulepreload-polyfill';

const userEl = document.querySelector('#user');
userEl.innerHTML = 'ダミーユーザー';

const debugLoginSwitch = document.querySelector<HTMLInputElement>('#debugLoginSwitch');

updateLoginStatus();

debugLoginSwitch.onclick = () => {
  if (debugLoginSwitch.checked) {
    logIn();
  } else {
    logOut();
  }
};

const logoutButton = document.querySelector<HTMLElement>('#buttonLogout');
logoutButton.onclick = () => {
  logOut();
};

function updateLoginStatus () {
  let unauthenticated = false;
  let authenticated = false;
  if (localStorage.getItem('login')) {
    authenticated = true;
  } else {
    unauthenticated = true;
  }
  if (debugLoginSwitch) { debugLoginSwitch.checked = authenticated; }
  setDisplay('.unauthenticated', unauthenticated ? '' : 'none');
  setDisplay('.authenticated', authenticated ? '' : 'none');
}

function logIn () {
  localStorage.setItem('login', 'login');
  updateLoginStatus();
}

function logOut () {
  localStorage.removeItem('login');
  updateLoginStatus();
}

function setDisplay (selectors: string, display: 'none' | '') {
  document.querySelectorAll<HTMLElement>(selectors)
    .forEach((e) => {
      e.style.display = display;
    });
}
