import { defineConfig } from 'vite';
import path from 'path';

export default defineConfig({
    resolve: {
        alias: {
            '~bootstrap': path.resolve(__dirname, 'node_modules/bootstrap'),
        }
    },
    build: {
        outDir: 'src/main/resources/static',
        rollupOptions: {
            input: path.resolve(__dirname, 'src', 'frontend', 'main.ts'),
            output: {
                sourcemap: true,
                entryFileNames: '[name].js',
                assetFileNames: '[name].[ext]'
            },
        },
    }
});
