import { defineConfig } from 'vite';
import eslint from 'vite-plugin-eslint';
import path from 'path';
import { globSync } from 'glob';

const root = path.resolve(__dirname, 'src/main')

export default defineConfig({
    plugins: [eslint()],
    root: root,
    build: {
        outDir: 'resources/static/js',
        rollupOptions: {
            input: Object.fromEntries(
                globSync(`${root}/ts/**/index.ts`).map(file => [
                    path.basename(path.dirname(file)), file
                ])),
            output: {
                entryFileNames: '[name].bundle.js'
            }
        },
        sourcemap: true
    }
});
