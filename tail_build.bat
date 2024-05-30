npm uninstall tailwindcss postcss autoprefixer
npm install -D tailwindcss postcss autoprefixer
npm cache clean --force
npm install
npx tailwindcss -i ./src/main/resources/static/input.css -o ./src/main/resources/static/dist/output.css --watch
npm run bundle
