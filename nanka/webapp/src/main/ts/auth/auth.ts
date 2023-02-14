import {setDisplay} from "./common";

export async function updateAuthStatus() {
    const response = await fetch('/user/', {
        headers: {
            // きちんと指定しないと、↓に引っかからずに、ログイン後の戻り先として /user/ が保存されてしまう
            // https://github.com/spring-projects/spring-security/blob/6.0.1/config/src/main/java/org/springframework/security/config/annotation/web/configurers/RequestCacheConfigurer.java#L157
            'Accept': 'application/json'
        }
    });
    if (response.ok) {
        const data = await response.json();
        const userEl = document.getElementById('user');
        if (userEl) {
            userEl.innerHTML = data.name;
        }
        setDisplay('.unauthenticated', 'none');
        setDisplay('.authenticated', '');
    }
};
