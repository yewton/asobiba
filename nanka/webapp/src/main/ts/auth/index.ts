import 'vite/modulepreload-polyfill'

import {updateAuthStatus} from "./auth";
import {setupLogoutButton} from "./logout";

setupLogoutButton();

(async () => {
    await updateAuthStatus();
})();
