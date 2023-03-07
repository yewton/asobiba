import { defineConfig } from 'vite';
import path from 'path';
import { globSync } from 'glob';

const isPreview = process.env.PREVIEW!!;
const root = path.resolve(__dirname, 'src/' + (isPreview ? 'preview' : 'main'))
const outDir = isPreview ? 'preview' : 'front';

export default defineConfig({
    root: root,
    resolve: {
        alias: {
            '~bootstrap': path.resolve(__dirname, 'node_modules/bootstrap'),
            '@': path.resolve(root, 'front')
        }
    },
    build: {
        outDir: `resources/static/${outDir}`,
        rollupOptions: {
            input: {
                ...Object.fromEntries(
                    globSync(`${root}/front/**/index.ts`).map(file => [
                        path.basename(path.dirname(file)), file
                    ])),
                ...Object.fromEntries(
                    globSync(`${root}/front/styles/*.scss`).map(file => [
                        path.basename(path.dirname(file)), file
                    ]))
            },
            output: {
                entryFileNames: '[name].bundle.js',
                assetFileNames: '[name].[ext]'
            }
        },
        sourcemap: true
    }
});
