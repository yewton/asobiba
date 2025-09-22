import eslint from '@eslint/js';
import tseslint from 'typescript-eslint';
import globals from 'globals';

export default tseslint.config(
  {
    ignores: [
      "build/",
      "dist/",
      "node_modules/",
      "**/*.bundle.js",
      "src/main/resources/static/",
      "src/preview/resources/static/"
    ],
  },
  eslint.configs.recommended,
  ...tseslint.configs.recommended,
  {
    files: ['src/**/*.ts'],
    languageOptions: {
      globals: {
        ...globals.browser,
      }
    },
  }
);
