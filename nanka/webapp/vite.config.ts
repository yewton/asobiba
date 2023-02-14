import { defineConfig } from 'vite';
import path from 'path';
import glob from 'glob';

const root = path.resolve(__dirname, 'src/main')

export default defineConfig({
    root: root,
    build: {
        outDir: 'resources/js',
        rollupOptions: {
            input: Object.fromEntries(
                glob.sync(`${root}/ts/**/index.ts`).map(file => [
                    path.basename(path.dirname(file)), file
                ])),
            output: {
                entryFileNames: '[name].bundle.js'
            }
        },
        sourcemap: true
    }
});
