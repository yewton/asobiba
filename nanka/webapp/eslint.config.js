import js from "@eslint/js";
import ts from "typescript-eslint";

export default ts.config(
  {
    ignores: ["build/**", "dist/**", "vite.config.ts", "eslint.config.js", "**/*.bundle.js"],
  },
  js.configs.recommended,
  ...ts.configs.recommended,
);
