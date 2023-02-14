export function setDisplay(selectors: string, display: 'none' | '') {
    document.querySelectorAll<HTMLInputElement>(selectors)
        .forEach((e) => {
            e.style.display = display;
        });
}
