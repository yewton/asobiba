export function setDisplay (selectors: string, display: 'none' | '') {
  document.querySelectorAll<HTMLElement>(selectors)
    .forEach((e) => {
      e.style.display = display;
    });
}
